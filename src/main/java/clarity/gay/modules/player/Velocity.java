package clarity.gay.modules.player;

import clarity.gay.events.Event;
import clarity.gay.events.EventPacket;
import clarity.gay.events.PacketRecieveEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Velocity",
        category = Category.PLAYER,
        description = "we wuzz knockback and sheet",
        bind = Keyboard.KEY_V
)
public class Velocity extends Module {

//    public Velocity() {
//        super("Velocity", Category.PLAYER, "we wuzz knockback and sheet");
//    }

    @Subscribe
    public void onEvent(PacketRecieveEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity) event.getPacket();
            if (packet.getEntityID() != mc.thePlayer.getEntityId()) return;
            event.setCancelled(true);
        }
    }
}
