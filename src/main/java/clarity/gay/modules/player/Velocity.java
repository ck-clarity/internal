package clarity.gay.modules.player;

import clarity.gay.events.HurtEvent;
import clarity.gay.events.TickEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.ModuleInfo;
import clarity.gay.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "Velocity", description = "we wuzz knockback and sheet", category = Category.PLAYER)
public class Velocity extends Module {

    public Velocity() {
        super("Velocity", "we wuzz knockback and sheet", Category.PLAYER);
    }
    @Override
    public void onEnable(){
        System.out.println("Test");
    }
    @Override
    public void onDisable(){
        System.out.println("Test2");
    }
    // please i need hurt event :'(((( - crxelty
    
    @Subscribe
    public void onPlayerHurt(HurtEvent event) {
        Entity targetEntity = event.getTargetEntity();
        if (targetEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) targetEntity;
            player.motionX = 0;
            player.motionY = 0;
            player.motionZ = 0;
            double x = player.posX;
            double y = player.posY;
            double z = player.posZ;
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.0625, z, true));
            System.out.println("reset pos: " + x + " " + y + " " + z);
        }
    }
}