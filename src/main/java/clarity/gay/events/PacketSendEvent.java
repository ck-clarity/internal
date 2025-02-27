package clarity.gay.events;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketSendEvent {
    private Packet<?> packet;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean cancelled;
    public Packet getPacket() {
        return packet;
    }
    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public PacketSendEvent(Packet packet){
        this.packet = packet;
    }
}
