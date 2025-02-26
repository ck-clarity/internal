package clarity.gay.modules.movement;

import clarity.gay.events.TickEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Speed",
        category = Category.MOVEMENT,
        description = "zoom",
        bind = Keyboard.KEY_X
)
public class Speed extends Module {

//    public Speed(){
//        super("Speed", Category.MOVEMENT, "zoom");
//    }

    @Override
    public void onEnable(){
        System.out.println("Test");
    }
    @Override
    public void onDisable(){
        System.out.println("Test2");
    }
    @Subscribe
    public void onTick(TickEvent event) {
        double baseSpeed = 0.3;
        double speedMultiplier = 1.5;

        if (mc.thePlayer.onGround) {
            mc.thePlayer.motionY = 0.42;
        }
        double forward = mc.thePlayer.moveForward;
        double strafe = mc.thePlayer.moveStrafing;
        float yaw = mc.thePlayer.rotationYaw * (float) Math.PI / 180.0F;

        double motionX = 0;
        double motionZ = 0;

        if (forward != 0 || strafe != 0) {
            if (forward != 0) {
                motionX -= Math.sin(yaw) * forward * baseSpeed * speedMultiplier;
                motionZ += Math.cos(yaw) * forward * baseSpeed * speedMultiplier;
            }

            if (strafe != 0) {
                motionX += Math.sin(yaw + Math.PI / 2) * strafe * baseSpeed * speedMultiplier;
                motionZ -= Math.cos(yaw + Math.PI / 2) * strafe * baseSpeed * speedMultiplier;
            }

            double length = Math.sqrt(motionX * motionX + motionZ * motionZ);
            if (length > 1) {
                motionX /= length;
                motionZ /= length;
            }
        }

        mc.thePlayer.motionX = motionX;
        mc.thePlayer.motionZ = motionZ;
    }



}
