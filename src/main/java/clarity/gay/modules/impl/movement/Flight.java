package clarity.gay.modules.impl.movement;

import clarity.gay.events.Render2DEvent;
import clarity.gay.events.TickEvent;
import clarity.gay.modules.api.Category;
import clarity.gay.modules.api.Module;
import clarity.gay.modules.api.ModuleInfo;
import clarity.gay.utils.FontUtil;
import clarity.gay.utils.MoveUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.Vec3;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.text.DecimalFormat;

@ModuleInfo(
        name = "Flight",
        category = Category.MOVEMENT,
        description = "zoom but different",
        bind = Keyboard.KEY_G
)
public class Flight extends Module {
    private DecimalFormat decimalFormat = new DecimalFormat("0.##");
    private Vec3 startPos;

    @Subscribe
    public void onRender(Render2DEvent event) {
        FontUtil.drawStringWithShadow("Distance: " + decimalFormat.format(startPos.distanceTo(mc.thePlayer.getPositionVector())), (event.getSr().getScaledWidth() / 2f) + 5, (event.getSr().getScaledHeight() / 2f) + 5, Color.white.getRGB());
    }

    public void onEnable() {
        this.startPos = mc.thePlayer.getPositionVector();
    }

    @Subscribe
    public void onTick(TickEvent event) {
        // no utils moment
        mc.thePlayer.motionY = mc.gameSettings.keyBindJump.isKeyDown() ? 0.5 : mc.gameSettings.keyBindSneak.isKeyDown() ? -0.5 : 0;
        MoveUtil.strafe(1.2D);

        // pls use better mode system
    }
}
