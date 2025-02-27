package clarity.gay.modules.ui;

import clarity.gay.Clarity;
import clarity.gay.events.Render2DEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import clarity.gay.modules.ModuleManager;
import clarity.gay.modules.utils.FontRenderer;
import clarity.gay.modules.utils.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.EnumChatFormatting;
import org.greenrobot.eventbus.Subscribe;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ModuleInfo(name = "HUD", description = "HUD", category = Category.UI)
public class Hud extends Module {
    private long ping;
    private String ip;
    private static final List<Module> moduleList = new ArrayList<>();
    Color rainbow;
    public Hud() {
        super("HUD", "HUD", Category.UI);
    }

    private void updateServerInfo() {
        if (!mc.isSingleplayer()) {
            ServerData serverData = mc.getCurrentServerData();
            ip = serverData.serverIP;
            ping = Math.max(serverData.pingToServer, 0L);
        } else {
            ip = "SinglePlayer";
            ping = 0L;
        }
    }

    private static int interpolate(int start, int end, float factor) {
        return Math.min(255, Math.max(0, (int) (start + (end - start) * factor)));
    }



    private void drawWatermark() {
        updateServerInfo();
        FontUtil.drawStringWithShadow(Clarity.name.charAt(0) + EnumChatFormatting.WHITE.toString() + Clarity.name.substring(1),3,3,rainbow.getRGB());
    }


    private void drawArrayList() {
        moduleList.clear();
        ModuleManager.modules.values().stream()
                .filter(Module::isEnabled)
                .sorted((m1, m2) -> Float.compare(FontUtil.getStringWidth(m2.getName()), FontUtil.getStringWidth(m1.getName())))
                .forEach(moduleList::add);

        ScaledResolution sr = new ScaledResolution(mc);
        int offset = 0;
        int padding = 2;
        int boxHeight = 10;
        float spacing = 0.15f;

        for (Module module : moduleList) {
            String text = module.getCurrentMode().isEmpty() ? module.getName() : module.getName() + " " + module.getCurrentMode();
            float width = FontUtil.getStringWidth(text);
            float x = sr.getScaledWidth() - width - padding * 2;

            float yStart = offset * (boxHeight + spacing);
            float yEnd = yStart + boxHeight;

            Gui.drawRect((int) x, (int) yStart, (int) (x + width + padding * 2), (int) yEnd, new Color(0, 0, 0, 75).getRGB());

            rainbow = Color.getHSBColor((float) ((offset * 0.05 + Math.sin(System.currentTimeMillis() / 1000.0) * 0.25) % 1.0), 1, 1);
            Gui.drawRect((int) (x + width + padding * 2 - 1), (int) yStart, (int) (x + width + padding * 2), (int) yEnd, rainbow.getRGB());

            FontUtil.drawString(text, x + padding, yStart + (boxHeight / 2F) - (FontUtil.getFontHeight() / 2), rainbow.getRGB());
            offset++;
        }
    }


    @Subscribe
    public void onDraw(Render2DEvent event) {
        if (!mc.gameSettings.showDebugInfo) {
            drawArrayList();
            drawWatermark();
        }
    }
}