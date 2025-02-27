package clarity.gay.modules.ui;

import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleManager;
import clarity.gay.modules.utils.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClickGuiScreen extends GuiScreen {
    private final Map<Category, int[]> positions = new HashMap<>();
    private final Map<Category, Boolean> expanded = new HashMap<>();
    private final Map<Module, Boolean> moduleExpanded = new HashMap<>();
    private final Map<Category, Integer> scrollOffsets = new HashMap<>();

    private Category dragCategory = null;
    private int dragX, dragY;

    public ClickGuiScreen() {
        int x = 10;
        for (Category cat : Category.values()) {
            positions.put(cat, new int[]{x, 10});
            expanded.put(cat, true);
            scrollOffsets.put(cat, 0);
            x += 120;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        handleScroll();

        drawRect(0, 0, width, height, new Color(0, 0, 0, 150).getRGB());

        if (dragCategory != null) {
            int[] pos = positions.get(dragCategory);
            pos[0] = mouseX - dragX;
            pos[1] = mouseY - dragY;
        }

        for (Category category : Category.values()) {
            int[] pos = positions.get(category);
            int px = pos[0];
            int py = pos[1];
            boolean isExpanded = expanded.get(category);
            int scrollOffset = scrollOffsets.get(category);

            int panelHeight = isExpanded ? Math.min(calculatePanelHeight(category), 300) : 20;

            Gui.drawRect(px, py, px + 110, py + panelHeight, new Color(16, 16, 16, 235).getRGB());

            Gui.drawRect(px, py, px + 110, py + 20, new Color(10, 10, 10, 255).getRGB());

            String categoryName = category.name().toLowerCase();
            FontUtil.drawString(categoryName,
                    px + 5, py + 6, new Color(200, 200, 200).getRGB());

            if (isExpanded) {
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                ScaledResolution sr = new ScaledResolution(mc);
                double scale = sr.getScaleFactor();
                GL11.glScissor((int)(px * scale),
                        (int)(mc.displayHeight - (py + panelHeight) * scale),
                        (int)(110 * scale),
                        (int)((panelHeight - 20) * scale));

                int moduleY = py + 20 - scrollOffset;
                for (Module module : ModuleManager.modules.values()) {
                    if (module.getCategory() == category && !module.getName().equals("ClickGui")) {
                        boolean isModuleExpanded = moduleExpanded.getOrDefault(module, false);

                        if (moduleY + 18 < py + 20 || moduleY > py + panelHeight) {
                            moduleY += 18 + (isModuleExpanded && !module.getMode().isEmpty() ?
                                    (module.getMode().size() * 15) : 0);
                            continue;
                        }

                        boolean hover = isInBox(px, px + 110, moduleY, moduleY + 18, mouseX, mouseY);

                        Gui.drawRect(px, moduleY, px + 110, moduleY + 18,
                                module.isEnabled() ? new Color(35, 108, 195).getRGB() :
                                        (hover ? new Color(30, 30, 30, 255).getRGB() : new Color(21, 21, 21, 255).getRGB()));

                        String moduleName = module.getName().toLowerCase();
                        FontUtil.drawString(moduleName,
                                px + 5, moduleY + 5,
                                module.isEnabled() ? new Color(255, 255, 255).getRGB() : new Color(200, 200, 200).getRGB());

                        if (isModuleExpanded && !module.getMode().isEmpty()) {
                            int modeY = moduleY + 18;
                            for (String mode : module.getMode()) {
                                if (modeY + 15 < py + 20 || modeY > py + panelHeight) {
                                    modeY += 15;
                                    continue;
                                }

                                boolean isCurrentMode = mode.equals(module.getCurrentMode());
                                boolean modeHover = isInBox(px, px + 110, modeY, modeY + 15, mouseX, mouseY);

                                Gui.drawRect(px + 2, modeY, px + 110 - 2, modeY + 15,
                                        isCurrentMode ? new Color(35, 108, 195).darker().getRGB() :
                                                (modeHover ? new Color(24, 24, 24, 255).brighter().getRGB() : new Color(24, 24, 24, 255).getRGB()));

                                String modeName = mode.toLowerCase();
                                FontUtil.drawString(modeName,
                                        px + 10, modeY + 3,
                                        isCurrentMode ? new Color(255, 255, 255).getRGB() : new Color(200, 200, 200).getRGB());

                                modeY += 15;
                            }
                        }

                        moduleY += 18 + (isModuleExpanded && !module.getMode().isEmpty() ?
                                (module.getMode().size() * 15) : 0);
                    }
                }

                GL11.glDisable(GL11.GL_SCISSOR_TEST);

                int totalHeight = calculatePanelHeight(category);
                if (totalHeight > 300) {
                    int maxScroll = totalHeight - 300 + 20;
                    float scrollPercentage = (float)scrollOffset / maxScroll;
                    int scrollBarHeight = 40;
                    int scrollBarY = py + 20 + (int)((panelHeight - 20 - scrollBarHeight) * scrollPercentage);

                    Gui.drawRect(px + 110 - 1, scrollBarY, px + 110, scrollBarY + scrollBarHeight,
                            new Color(100, 100, 100, 100).getRGB());
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void handleScroll() {
        int scroll = Mouse.getDWheel();
        if (scroll != 0) {
            for (Category category : Category.values()) {
                if (expanded.get(category)) {
                    int[] pos = positions.get(category);
                    int px = pos[0];
                    int py = pos[1];

                    int panelHeight = Math.min(calculatePanelHeight(category), 300);

                    if (isInBox(px, px + 110, py, py + panelHeight,
                            Mouse.getX() * width / mc.displayWidth,
                            height - Mouse.getY() * height / mc.displayHeight - 1)) {

                        int scrollOffset = scrollOffsets.get(category);
                        int totalHeight = calculatePanelHeight(category);
                        int maxScroll = Math.max(0, totalHeight - 300 + 20);

                        scrollOffset = Math.max(0, Math.min(maxScroll,
                                scrollOffset - (int)Math.signum(scroll) * 15));

                        scrollOffsets.put(category, scrollOffset);
                        break;
                    }
                }
            }
        }
    }

    private int calculatePanelHeight(Category category) {
        int height = 20;
        for (Module module : ModuleManager.modules.values()) {
            if (module.getCategory() == category && !module.getName().equals("ClickGui")) {
                height += 18;
                if (moduleExpanded.getOrDefault(module, false) && !module.getMode().isEmpty()) {
                    height += (module.getMode().size() * 15);
                }
            }
        }
        return height;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Category category : Category.values()) {
            int[] pos = positions.get(category);
            int px = pos[0];
            int py = pos[1];
            boolean isExpanded = expanded.get(category);
            int scrollOffset = scrollOffsets.get(category);

            if (isInBox(px, px + 110, py, py + 20, mouseX, mouseY)) {
                if (mouseButton == 0) {
                    dragCategory = category;
                    dragX = mouseX - px;
                    dragY = mouseY - py;
                    return;
                } else if (mouseButton == 1) {
                    expanded.put(category, !isExpanded);
                    return;
                }
            }

            if (isExpanded) {
                int visibleHeight = Math.min(calculatePanelHeight(category) - 20, 300 - 20);

                if (isInBox(px, px + 110, py + 20, py + 20 + visibleHeight, mouseX, mouseY)) {
                    int moduleY = py + 20 - scrollOffset;

                    for (Module module : ModuleManager.modules.values()) {
                        if (module.getCategory() == category && !module.getName().equals("ClickGui")) {
                            boolean isModuleExpanded = moduleExpanded.getOrDefault(module, false);

                            if (isInBox(px, px + 110, moduleY, moduleY + 18, mouseX, mouseY)) {
                                if (mouseButton == 0) {
                                    module.setEnabled(!module.isEnabled());
                                    return;
                                } else if (mouseButton == 1 && !module.getMode().isEmpty()) {
                                    moduleExpanded.put(module, !isModuleExpanded);
                                    return;
                                }
                            }

                            if (isModuleExpanded && !module.getMode().isEmpty()) {
                                int modeY = moduleY + 18;
                                for (String mode : module.getMode()) {
                                    if (isInBox(px + 2, px + 110 - 2, modeY, modeY + 15, mouseX, mouseY)) {
                                        if (mouseButton == 0) {
                                            module.setCurrentMode(mode);
                                            return;
                                        }
                                    }
                                    modeY += 15;
                                }
                            }

                            moduleY += 18 + (isModuleExpanded && !module.getMode().isEmpty() ?
                                    (module.getMode().size() * 15) : 0);
                        }
                    }
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            dragCategory = null;
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    private boolean isInBox(int left, int right, int top, int bottom, int mouseX, int mouseY) {
        return mouseX >= left && mouseX <= right && mouseY >= top && mouseY <= bottom;
    }
}