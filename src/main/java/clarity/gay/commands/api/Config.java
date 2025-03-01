package clarity.gay.commands.api;


import clarity.gay.Clarity;

import java.util.List;
@CommandInfo(name = "config", description = "config system on my cousin")

public class Config extends Command {
    public Config() {
        super("config", "config system on my cousin");
    }

    @Override
    public void runCommand(List<String> args) {
        if (args.isEmpty()) {
            mc.thePlayer.sendMessage("Usage: config <save/load/list> [filename]");
            return;
        }

        String action = args.get(0).toLowerCase();

        switch (action) {
            case "save":
                if (args.size() < 2) {
                    mc.thePlayer.sendMessage("Usage: config save <filename>");
                    return;
                }
                String saveFilename = args.get(1) + ".json";
                Clarity.fileManager.saveConfig(saveFilename);
                mc.thePlayer.sendMessage("Config saved to " + saveFilename);
                break;

            case "load":
                if (args.size() < 2) {
                    mc.thePlayer.sendMessage("Usage: config load <filename>");
                    return;
                }
                String loadFilename = args.get(1) + ".json";
                Clarity.fileManager.loadConfig(loadFilename);
                mc.thePlayer.sendMessage("cfg loaded from " + loadFilename);
                break;

            case "list":
                List<String> configs = Clarity.fileManager.getConfigs();
                if (configs.isEmpty()) {
                    mc.thePlayer.sendMessage("No cfgs found.");
                } else {
                    mc.thePlayer.sendMessage("Available cfgs:");
                    for (String config : configs) {
                        mc.thePlayer.sendMessage(config);
                    }
                }
                break;

            default:
                mc.thePlayer.sendMessage("Unknown action. Use: save, load, or list.");
                break;
        }
    }
}
