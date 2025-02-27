package clarity.gay.extensions;

import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleManager;
import clarity.gay.modules.ui.ClickGui;
import com.google.gson.*;
import java.io.*;
import java.util.*;
import com.google.common.collect.Sets;

public class FileManager {
    public static File rootdir = new File("Clarity");
    public static File modules = new File(rootdir, "modules.json");

    public void init() {
        if (!rootdir.exists()) {
            rootdir.mkdirs();
        }

        if (!modules.exists())
            saveMods();
        else
            loadMods();
    }

    public void saveMods() {
        saveConfig("modules.json");
    }

    public void saveConfig(String filename) {
        try {
            File configFile = new File(rootdir, filename);
            JsonObject json = new JsonObject();
            for (Module mod : ModuleManager.modules.values()) {
                JsonObject jsonModule = new JsonObject();
                jsonModule.addProperty("enabled", mod.isEnabled());
                jsonModule.addProperty("bind", mod.getBind());
                json.add(mod.getName(), jsonModule);
            }
            PrintWriter save = new PrintWriter(new FileWriter(configFile));
            save.println(JsonUtils.prettyGsonLikeMeKwenmaFr.toJson(json));
            save.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HashSet<String> blacklist = Sets.newHashSet(ClickGui.class.getName());

    public boolean isblacklisted(Module m) {
        return blacklist.contains(m.getClass().getName());
    }

    public void loadMods() {
        loadConfig("modules.json");
    }
    public List<String> getConfigs() {
        List<String> configFiles = new ArrayList<>();
        if (rootdir.exists() && rootdir.isDirectory()) {
            File[] files = rootdir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".json") && !file.getName().equals("modules.json")) {
                        configFiles.add(file.getName());
                    }
                }
            }
        }
        return configFiles;
    }


    public void loadConfig(String filename) {
        try {
            File configFile = new File(rootdir, filename);
            if (!configFile.exists()) {
                System.out.println("Config file " + filename + " does not exist!");
                return;
            }

            BufferedReader load = new BufferedReader(new FileReader(configFile));
            JsonObject json = (JsonObject) JsonUtils.jsonParser.parse(load);
            load.close();

            for (Module mod : ModuleManager.modules.values()) {
                if (!blacklist.contains(mod.getClass().getName())) {
                    if (mod.isEnabled()) {
                        mod.setEnabled(false);
                    }
                }
            }

            Iterator<Map.Entry<String, JsonElement>> itr = json.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<String, JsonElement> entry = itr.next();
                Module mod = ModuleManager.getModuleByName(entry.getKey());
                if (mod != null && !blacklist.contains(mod.getClass().getName())) {
                    JsonObject jsonMod = (JsonObject) entry.getValue();
                    boolean enabled = jsonMod.get("enabled").getAsBoolean();
                    if (mod.isEnabled() == enabled) {
                        continue;
                    }
                    int bind = jsonMod.get("bind").getAsInt();
                    ArrayList<String> modes = new ArrayList<>();
                    mod.setMode(modes);
                    mod.setBind(bind);
                    mod.setEnabled(enabled);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}