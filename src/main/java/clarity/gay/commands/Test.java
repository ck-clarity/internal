package clarity.gay.commands;

import java.util.List;
@CommandInfo(name = "test", description = "Test command.")

public class Test extends Command {

    public Test() {
        super("test", "Test command.");
    }

    @Override
    public void runCommand(List<String> args) {
        if(args.size() > 0) {
            mc.thePlayer.motionY = Double.parseDouble(args.get(0));
        }
        super.runCommand(args);
    }
}
