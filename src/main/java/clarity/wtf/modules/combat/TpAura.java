package clarity.wtf.modules.combat;

import clarity.wtf.events.TickEvent;
import clarity.wtf.modules.Category;
import clarity.wtf.modules.Module;
import clarity.wtf.modules.ModuleInfo;
import clarity.wtf.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MathHelper;
import org.greenrobot.eventbus.Subscribe;

import java.util.Comparator;

@ModuleInfo(name = "TpAura", description = "Teleports around entities while attacking", category = Category.COMBAT)
public class TpAura extends Module {
    private long lastAttackTime;
    private int ticks = 0;
    private EntityLivingBase target;

    public static final int MAX_TICKS = 0;
    private static final int ATTACK_DELAY = 0;
    private static final float RANGE = 50.0f;
    private static final int TP_POINTS = 60;

    public TpAura() {
        super("TpAura", "Teleports around entities while attacking", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        ticks = 0;
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (!isEnabled()) return;
        
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime < ATTACK_DELAY) return;
        
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer == null || mc.theWorld == null) return;

        target = mc.theWorld.getEntities(EntityLivingBase.class, entity -> 
            entity != mc.thePlayer && 
            entity.isEntityAlive() && 
            ((entity instanceof EntitySheep)) && entity.isEntityAlive() &&
            mc.thePlayer.getDistanceToEntity(entity) <= RANGE
        ).stream().min(Comparator.comparingDouble(entity -> 
            mc.thePlayer.getDistanceToEntity(entity))).orElse(null);

        if (target == null) return;

        double radius = (Math.random() * 10) + 1;
        double randomAngle = Math.random() * Math.PI * 2;
        
        double x = target.posX + (radius * Math.cos(randomAngle));
        double z = target.posZ + (radius * Math.sin(randomAngle));
        double y = target.posY + 1;

        x += (Math.random() - 0.5) * 2;
        z += (Math.random() - 0.5) * 2;

        mc.thePlayer.setPosition(x, y, z);
        mc.thePlayer.motionY = 0;

        float[] rotations = getRotations(target);
        mc.thePlayer.rotationYaw = rotations[0];
        mc.thePlayer.rotationPitch = rotations[1];

        mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
        mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
        mc.thePlayer.swingItem();

        // Gotta fix block hitting
        if (mc.thePlayer.getHeldItem() != null) {
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
        }

        lastAttackTime = currentTime;
        if (MAX_TICKS != 0) {
                ticks++;
                if (ticks >= MAX_TICKS) {
                    ModuleManager.getModuleByName("TpAura").setEnabled(false);
                }
        }
    }

    private float[] getRotations(EntityLivingBase entity) {
        double x = entity.posX - mc.thePlayer.posX;
        double y = entity.posY + entity.getEyeHeight() - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = entity.posZ - mc.thePlayer.posZ;

        double dist = Math.sqrt(x * x + z * z);

        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(y, dist) * 180.0D / Math.PI);

        return new float[]{
            mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw),
            mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)
        };
    }
} 