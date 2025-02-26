package clarity.gay.modules.combat;

import clarity.gay.events.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Comparator;

@ModuleInfo(name = "Lawnmower", description = "Automatically attacks nearby entities", category = Category.COMBAT)
public class Lawnmower extends Module {
    private long lastAttackTime;
    private static final int ATTACK_DELAY = 100;
    private static final float RANGE = 6.0f;

    public Lawnmower() {super("Lawnmower", "Automatically attacks nearby entities", Category.COMBAT);}

    @Subscribe
    public void onTick(TickEvent event) {
        if (!isEnabled()) return;
        
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime < ATTACK_DELAY) return;
        
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;

        
        List<EntityLivingBase> targets = mc.theWorld.getEntities(EntityLivingBase.class, entity -> 
            entity != mc.thePlayer && 
            entity.isEntityAlive() && 
            mc.thePlayer.getDistanceToEntity(entity) <= RANGE
        );

        targets.sort(Comparator.comparingDouble(entity -> 
            mc.thePlayer.getDistanceToEntity(entity)));

        for (EntityLivingBase target : targets) {
            float[] rotations = getRotations(target);
//            mc.thePlayer.rotationYaw = rotations[0];
//            mc.thePlayer.rotationPitch = rotations[1];

            mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
            mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            mc.thePlayer.swingItem();
            
            lastAttackTime = currentTime;
        }
    }

    private float[] getRotations(EntityLivingBase entity) {
        double x = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        double y = entity.posY + entity.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        double z = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;

        double dist = MathHelper.sqrt_double(x * x + z * z);

        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(y, dist) * 180.0D / Math.PI);

        return new float[]{
            Minecraft.getMinecraft().thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().thePlayer.rotationYaw),
            Minecraft.getMinecraft().thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().thePlayer.rotationPitch)
        };
    }
} 