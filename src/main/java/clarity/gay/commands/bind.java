package clarity.gay.commands;

import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleManager;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.List;

@CommandInfo(name = "bind", description = "binds shit on my cousin bro")
public class bind extends Command {
    public bind() {
        super("bind", "binds shit on my cousin bro");
    }

    @Override
    public void runCommand(List<String> args) {
        if (args.size() != 2) {
            mc.thePlayer.sendMessage(EnumChatFormatting.RED + "Usage: .bind <module> <key>");
            return;
        }

        String module = args.get(0);
        String key = args.get(1);

        Module module1 = ModuleManager.getModuleByName(module);
        if (module1 == null) {
            mc.thePlayer.sendMessage(EnumChatFormatting.RED + "Module not found: " + EnumChatFormatting.GRAY + module);
            return;
        }

        int keycode = Keyboard.getKeyIndex(key.toUpperCase());
        if (key.equalsIgnoreCase("NONE") || key.equalsIgnoreCase("ESC") || key.equalsIgnoreCase("REMOVE")) {
            module1.setBind(-1);
            mc.thePlayer.sendMessage(EnumChatFormatting.GREEN + "Unbound " + EnumChatFormatting.GRAY + module);
            return;
        }

        if (keycode == 0) {
            mc.thePlayer.sendMessage(EnumChatFormatting.RED + "Invalid key: " + EnumChatFormatting.GRAY + key);
            return;
        }

        module1.setBind(keycode);
        mc.thePlayer.sendMessage("Bound " +  module  + " to " + key);
    }
}