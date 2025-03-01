package clarity.gay.events;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent {
    @Getter
    private final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
}
