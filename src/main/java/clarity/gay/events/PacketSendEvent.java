package clarity.gay.events;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

@Setter
@Getter
public class PacketSendEvent {
    private Packet<?> packet;

    public boolean cancelled;

    public PacketSendEvent(Packet<?> packet){
        this.packet = packet;
    }
}
