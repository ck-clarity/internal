package clarity.gay.modules.combat;

import clarity.gay.events.TickEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import clarity.gay.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MathHelper;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;

@ModuleInfo(
        name = "TpAura",
        category = Category.COMBAT,
        description = "Teleports around entities while attacking",
        bind = Keyboard.KEY_O
)
public class TpAura extends Module {
    private long lastAttackTime;
    private int ticks = 0;
    private EntityLivingBase target;

    public static final int MAX_TICKS = 0;
    private static final int ATTACK_DELAY = 0;
    private static final float RANGE = 50.0f;

    public TpAura() {
        super("TpAura", Category.COMBAT, "Teleports around entities while attacking");
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
            mc.thePlayer.getDistanceToEntity(entity) <= RANGE
        ).stream().min(Comparator.comparingDouble(entity -> 
            mc.thePlayer.getDistanceToEntity(entity))).orElse(null);

        if (target == null) return;

        
        double x = target.posX;
        double z = target.posZ ;
        double y = target.posY;
        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(x,y,z,mc.thePlayer.rotationYaw,mc.thePlayer.rotationPitch,true));


        mc.thePlayer.swingItem();
        mc.playerController.attackEntity(mc.thePlayer,target);
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

} 