package clarity.gay.modules;


import clarity.gay.Clarity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Module {

    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final Category category;

    @Setter
    @Getter
    private int bind;

    @Getter
    private boolean enabled;

    public Moduledata data;

    ModuleInfo info = getClass().getAnnotation(ModuleInfo.class);
    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    ArrayList<String> mode = new ArrayList<>();

    public ArrayList<String> getMode() {
        return mode;
    }

    public String getModeCase(String modeName) {
        for (String mode : mode) {
            if (mode.equalsIgnoreCase(modeName)) { return mode; }
        }
        return modeName;
    }

    public String getModes() {
        return Arrays.toString(mode.toArray());
    }

    public void setMode(ArrayList<String> mode) {
        this.mode = mode;
    }

    public void setMode(String modeName) {
        mode.clear();
        mode.add(modeName);
    }

    public void addMode(String modes) {
        if (!mode.contains(modes)) {
            mode.add(modes);
        }
        if (Objects.equals(current, "")) { this.current = mode.get(0); }
    }

    public String getCurrentMode() {
        return current;
    }

    public void setCurrentMode(String current) {
        this.current = current;
    }

    String current = "";

    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;

        this.enabled = enabled;
        if (enabled) {
            Clarity.getInstance().eventBus.register(this);
            onEnable();
        } else {
            Clarity.getInstance().eventBus.unregister(this);
            onDisable();
        }
    }
    public void onEnable() {}
    public void onDisable() {}

    public Minecraft mc = Minecraft.getMinecraft();

    public void enableOnStartUp(){
        this.enabled = true;
        try {
            onEnable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
