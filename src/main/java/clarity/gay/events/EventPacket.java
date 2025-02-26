package clarity.gay.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.Packet;

@Getter
@Setter
@AllArgsConstructor
public class EventPacket {
    public boolean cancelled;
    private Packet packet;
    private boolean incoming;

    public boolean isSending() {
        return !incoming;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isReceiving() {
        return incoming;
    }

    public Packet getPacket() {
        return packet;
    }
}