package clarity.gay.utils;

public class MoveUtil extends Util {

    public static boolean canSprint() {
        return mc.thePlayer.moveForward >= 0.8 && !mc.thePlayer.isCollidedHorizontally && mc.thePlayer.getFoodStats().getFoodLevel() > 6 && !mc.thePlayer.isSneaking() && !mc.thePlayer.isUsingItem() && isMoving();
    }

    public static boolean isMoving() {
        return mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
    }

    // Code by @Renovsk for Speed, moved here for convenience;
    public static void strafe(Double speed) {
        double baseSpeed = 0.3;
        double speedMultiplier = speed;

        double forward = mc.thePlayer.moveForward;
        double strafe = mc.thePlayer.moveStrafing;

//        float yaw = mc.thePlayer.rotationYaw * (float) Math.PI / 180.0F;
        double yaw = Math.toRadians(mc.thePlayer.rotationYaw);

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
