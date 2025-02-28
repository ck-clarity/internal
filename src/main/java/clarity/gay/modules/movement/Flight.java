package clarity.gay.modules.movement;

import clarity.gay.events.TickEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import clarity.gay.utils.MoveUtil;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "Flight", description = "NYOOOOM!", category = Category.MOVEMENT)
public class Flight extends Module {
    @Subscribe
    public void onTick(TickEvent event) {
        mc.thePlayer.motionY = mc.gameSettings.keyBindJump.isKeyDown() ? 0.5 : mc.gameSettings.keyBindSneak.isKeyDown() ? -0.5 : 0;
        MoveUtil.strafe(3D);
    }
}
