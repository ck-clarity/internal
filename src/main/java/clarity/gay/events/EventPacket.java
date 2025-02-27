package clarity.gay.events;

import lombok.AccessLevel;
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
    @Getter(AccessLevel.NONE) private boolean incoming;

    public boolean isSending() {
        return !incoming;
    }
    public boolean isReceiving() {
        return incoming;
    }
}