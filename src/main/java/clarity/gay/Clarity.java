package clarity.gay;


import clarity.gay.commands.impl.CommandManager;
import clarity.gay.extensions.FileManager;
import clarity.gay.modules.api.Module;
import clarity.gay.modules.impl.ModuleManager;
import clarity.gay.utils.Fonts;
import org.greenrobot.eventbus.EventBus;
import org.lwjgl.opengl.Display;

public class Clarity {
    private static final Clarity INSTANCE = new Clarity();
    public static FileManager fileManager;
    public static final String name = "Clarity";
    public static final String ver = "v0.0.1";

    public final EventBus eventBus = EventBus.builder().logNoSubscriberMessages(false).build();

    public CommandManager commandManager = new CommandManager();

    private Clarity() {}

    public static Clarity getInstance() {
        return INSTANCE;
    }

    public void init() {
        ModuleManager moduleManager = new ModuleManager();
        Display.setTitle(name + " | " + ver);
        Fonts.INSTANCE.setup();
        moduleManager.initModule();
        fileManager = new FileManager();
        fileManager.init();
        commandManager.initCommands();
        ModuleManager.modules.values().stream().filter(Module::getStartup).forEach(Module::enableOnStartUp);
    }
}
