package clarity.wtf.modules;

import clarity.wtf.Clarity;
import clarity.wtf.events.KeyPressEvent;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.input.Keyboard;
import org.reflections.Reflections;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ModuleManager {
    public static TreeMap<String, Module> modules = new TreeMap<>();

    public void initModule() {
        Clarity.getInstance().eventBus.register(this);
        registerModules();
        getModuleByName("ClickGUI").setBind(Keyboard.KEY_RSHIFT);
        getModuleByName("Speed").setBind(Keyboard.KEY_O);
        getModuleByName("Music").setBind(Keyboard.KEY_P);
        getModuleByName("Lawnmower").setBind(Keyboard.KEY_K);
        getModuleByName("Criticals").setBind(Keyboard.KEY_C);
    }

    private void registerModules() {
        // shit code yes yes kys ik
        Reflections reflections = new Reflections("clarity.wtf.modules");
        Set<Class<? extends Module>> moduleClasses = reflections.getSubTypesOf(Module.class);
        for (Class<? extends Module> clazz : moduleClasses) {
            ModuleInfo moduleInfo = clazz.getAnnotation(ModuleInfo.class);
            if (moduleInfo != null) {
                try {
                    Module module = clazz.getDeclaredConstructor().newInstance();
                    modules.put(moduleInfo.name(), module);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Subscribe
    public void onKeyPress(KeyPressEvent event) {
        for (Module module : modules.values()) {
            if (module.getBind() == event.keybind) {
                module.setEnabled(!module.isEnabled());
            }
        }
    }

    public static Module getModuleByName(String name) {
        return modules.values().stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    public static Set<Module> getAllModules() {
        return new TreeSet<>(Comparator.comparing(Module::getName));
    }
}
