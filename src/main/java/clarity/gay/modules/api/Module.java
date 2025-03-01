package clarity.gay.modules.api;


import clarity.gay.Clarity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

// what is this aids fucking formatting
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
    @Setter
    public String currentMode = "";

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
    ArrayList<String> modes = new ArrayList<>();

    public String getModeCase(String modeName) {
        for (String mode : modes) {
            if (mode.equalsIgnoreCase(modeName)) { return mode; }
        }
        return modeName;
    }

    public String getModesString() {
        return Arrays.toString(modes.toArray());
    }

    public void setModes(ArrayList<String> modes) {
        this.modes = modes;
    }

    // very needed methods
    public void setModes(String modeName) {
        modes.clear();
        modes.add(modeName);
    }

    public void addMode(String mode) {
        if (!this.modes.contains(mode)) {
            this.modes.add(mode);
        }
        if (Objects.equals(getCurrentMode(), "")) { setCurrentMode(this.modes.get(0)); }
    }

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