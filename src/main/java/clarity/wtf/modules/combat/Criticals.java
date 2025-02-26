package clarity.wtf.modules.combat;

import clarity.wtf.events.AttackEvent;
import clarity.wtf.modules.Category;
import clarity.wtf.modules.ModuleInfo;
import clarity.wtf.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "Criticals", description = "Makes all attacks critical hits", category = Category.COMBAT)
public class Criticals extends Module {

    public Criticals() {
        super("Criticals", "Makes all attacks critical hits", Category.COMBAT);
    }

    // no clue how to do this sooo its someone elses problem now...fuck you joshieman

    // note this is POST for some reason idfk someone elses problem now - kwenma

    // i did criticals :333333333333 - crxelty

    @Subscribe
    public void onAttack(AttackEvent event) {
        Entity targetEntity = event.getTargetEntity();
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.0625, z, true));
        System.out.println(x + " " + y + 0.0625 + " " + z);
        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
        System.out.println("Reset position to previous one");
    }
}