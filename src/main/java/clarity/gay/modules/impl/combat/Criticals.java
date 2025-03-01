package clarity.gay.modules.impl.combat;

import clarity.gay.events.AttackEvent;
import clarity.gay.modules.api.Category;
import clarity.gay.modules.api.ModuleInfo;
import clarity.gay.modules.api.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Criticals",
        category = Category.COMBAT,
        description = "Makes all attacks critical hits",
        bind = Keyboard.KEY_C
)
public class Criticals extends Module {

//    public Criticals() {
//        super("Criticals", Category.COMBAT, "Makes all attacks critical hits");
//    }

    // no clue how to do this sooo its someone elses problem now...fuck you - joshieman

    // note this is POST for some reason idfk someone elses problem now - kwenma

    // i did criticals :333333333333 - crxelty

    @Subscribe
    public void onAttack(AttackEvent event) {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.0625, z, true));
        System.out.println(x + " " + y + 0.0625 + " " + z);
        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
        System.out.println("Reset position to previous one");
    }
}