package clarity.gay.events;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

@Setter
@Getter
public class PacketRecieveEvent {
    private Packet<?> packet;

    public boolean cancelled;

    public PacketRecieveEvent(Packet packet){
        this.packet = packet;
    }
}
