package clarity.gay.commands;

import clarity.gay.commands.Command;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
@CommandInfo(name = "test", description = "test")

public class test extends Command {

    public test() {
        super("test", "test");
    }

    @Override
    public void runCommand(List<String> args) {
        if(args.size() > 0) {
            mc.thePlayer.motionY = Double.parseDouble(args.get(0));
        }
        super.runCommand(args);
    }
}
