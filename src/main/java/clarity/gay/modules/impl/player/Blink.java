package clarity.gay.modules.impl.player;

import clarity.gay.events.PacketSendEvent;
import clarity.gay.events.Render2DEvent;
import clarity.gay.events.TickEvent;
import clarity.gay.modules.api.Category;
import clarity.gay.modules.api.Module;
import clarity.gay.modules.api.ModuleInfo;
import clarity.gay.utils.FontUtil;
import clarity.gay.utils.MoveUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@ModuleInfo(
        name = "Blink",
        category = Category.PLAYER,
        description = ""
)
public class Blink extends Module {
    // theory finally helped
    private Queue<Packet<?>> packets = new ConcurrentLinkedDeque<>();
    private EntityOtherPlayerMP fakePlayer;
    private int timer;

    public void onEnable() {
        this.fakePlayer = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.getGameProfile());
        // doesnt copy pos
        this.fakePlayer.clonePlayer(mc.thePlayer, true);
        this.fakePlayer.copyLocationAndAnglesFrom(mc.thePlayer);
        this.fakePlayer.setEntityId(42069);
        mc.theWorld.addEntityToWorld(this.fakePlayer.getEntityId(), this.fakePlayer);
    }

    public void onDisable() {
        timer = 0;
        packets.forEach(mc.getNetHandler()::addToSendQueue);
        packets.clear();

        if (this.fakePlayer == null) return;
        mc.theWorld.removeEntityFromWorld(this.fakePlayer.getEntityId());
        this.fakePlayer = null;
    }

    @Subscribe
    public void onRender(Render2DEvent event) {
        FontUtil.drawStringWithShadow("Ticks: %d | Seconds: %s (%d)".formatted(timer, String.format("%.1f", timer / 20f), packets.size()), (event.getSr().getScaledWidth() / 2f) + 5, (event.getSr().getScaledHeight() / 2f) + 5, Color.white.getRGB());
    }

    @Subscribe
    public void onTick(TickEvent event) { timer++; }

    @Subscribe
    public void onUpdate(PacketSendEvent event) {
        if (!(event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition || event.getPacket() instanceof C02PacketUseEntity))
            return;

        packets.add(event.getPacket());
        event.setCancelled(true);
    }
}
