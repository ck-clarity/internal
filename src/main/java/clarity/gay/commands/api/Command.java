package clarity.gay.commands.api;

import net.minecraft.client.Minecraft;

import java.util.List;

public class Command {

    public String getName() {
        return name;
    }

    public String name;
    public String description;
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Minecraft mc = Minecraft.getMinecraft();


    public void runCommand(List<String> args){

    }
}
