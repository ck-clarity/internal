package clarity.gay.commands.api;

import clarity.gay.commands.impl.CommandManager;

import java.util.Collection;
import java.util.List;

@CommandInfo(name = "help", description = "Shows info about other commands.")
public class Help extends Command {
    public Help() {
        super("help", "Shows information about other commands.");
    }

    @Override
    public void runCommand(List<String> args) {
        Collection<Command> commands = CommandManager.getCommands().values();
        mc.thePlayer.sendMessage("Available Commands (" + commands.size() + "):");
        for (Command command : commands) {
            mc.thePlayer.sendMessage(command.getName() + ": " + command.description);
        }
        super.runCommand(args);
    }
}
