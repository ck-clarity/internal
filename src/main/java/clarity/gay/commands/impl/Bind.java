package clarity.gay.commands.impl;

import clarity.gay.commands.api.Command;
import clarity.gay.commands.api.CommandInfo;
import clarity.gay.modules.api.Module;
import clarity.gay.modules.impl.ModuleManager;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.List;

@CommandInfo(name = "bind", description = "Bind modules to keys.")
public class Bind extends Command {
    public Bind() {
        super("bind", "Bind modules to keys.");
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