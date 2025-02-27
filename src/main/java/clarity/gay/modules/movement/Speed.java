package clarity.gay.modules.movement;

import clarity.gay.commands.CommandManager;
import clarity.gay.events.PacketRecieveEvent;
import clarity.gay.events.PacketSendEvent;
import clarity.gay.events.PacketSendEventFinal;
import clarity.gay.events.TickEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import clarity.gay.utils.MoveUtil;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Speed",
        category = Category.MOVEMENT,
        description = "zoom",
        bind = Keyboard.KEY_X
)
public class Speed extends Module {

//    public Speed(){
//        super("Speed", Category.MOVEMENT, "zoom");
//    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (mc.thePlayer.onGround && MoveUtil.isMoving()) mc.thePlayer.jump();
        MoveUtil.strafe(1.5);
    }
}
