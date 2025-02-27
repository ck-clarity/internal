package clarity.gay.commands;


import clarity.gay.Clarity;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import lombok.Getter;
import org.reflections.Reflections;

import java.util.Set;
import java.util.TreeMap;

public class CommandManager {
    @Getter
    public static TreeMap<String, Command> commands = new TreeMap<>();
    public void initCommands(){
        registerCommands();
    }
    private void registerCommands() {
        Reflections reflections = new Reflections("clarity.gay.commands");
        Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> clazz : commandClasses) {
            CommandInfo commandInfo = clazz.getAnnotation(CommandInfo.class);
            if (commandInfo != null) {
                try {
                    Command command = clazz.getDeclaredConstructor().newInstance();
                    commands.put(commandInfo.name(), command);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Command getCommand(String name){
        return commands.get(name);
    }
}



