package clarity.gay.modules;


import clarity.gay.Clarity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Module {

    public Minecraft mc = Minecraft.getMinecraft();

    @Getter
    private final String name;

    @Getter
    private final String description;

    @Getter
    private final Category category;

    @Setter
    @Getter
    private int bind;

    private final boolean startup;

    @Getter
    private boolean enabled;

    public Moduledata data;
    public ModuleInfo info = getClass().getAnnotation(ModuleInfo.class);

    public Module() {
        this.name = this.info.name();
        this.category = this.info.category();
        this.description = this.info.description();
        this.bind = this.info.bind();
        this.startup = this.info.startup();
    }

    public Module(String name, Category category, String description, int bind) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.bind = bind;
        this.startup = false;
    }

    public Module(String name, Category category, String description) {
        this(name, category, description, Keyboard.KEY_NONE);
    }

    public Module(String name, Category category) {
        this(name, category, "", Keyboard.KEY_NONE);
    }

    @Getter
    ArrayList<String> mode = new ArrayList<>();

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

    public boolean getStartup() {
        return this.startup;
    }

    public void enableOnStartUp() {
        try {
            setEnabled(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}