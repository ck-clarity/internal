package clarity.gay.modules.player;

import clarity.gay.events.Event;
import clarity.gay.events.EventPacket;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "Velocity", description = "we wuzz knockback and sheet", category = Category.PLAYER)
public class Velocity extends Module {

    public Velocity() {
        super("Velocity", "we wuzz knockback and sheet", Category.PLAYER);
    }

    @Subscribe
    public void onEvent(EventPacket event) {
        if (event.isReceiving()) {
            Packet<?> packet = event.getPacket();
            System.out.println("got packet: " + packet.getClass().getSimpleName());

            if (packet instanceof S12PacketEntityVelocity) {
                S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) packet;
                if (s12.getEntityID() == mc.thePlayer.getEntityId()) {
                    System.out.println("velocity");
                    event.setCancelled(true);
                }
            }

            if (packet instanceof S27PacketExplosion) {
                System.out.println("velocity");
                event.setCancelled(true);
            }
        }
    }

}
