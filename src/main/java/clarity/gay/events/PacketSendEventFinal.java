package clarity.gay.events;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.Packet;

@Getter
@Setter
public class PacketSendEventFinal {
    private Packet<?> packet;
    private boolean cancelled;

    public PacketSendEventFinal(Packet<?> packet) {
        this.packet = packet;
        this.cancelled = false;
    }
}