package clarity.gay.modules.player;

import clarity.gay.events.TickEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import clarity.gay.utils.MoveUtil;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "Sprint", description = "Automatically sprints for you.", category = Category.PLAYER)
public class Sprint extends Module {

    @Subscribe
    public void onTick(TickEvent event) {
        mc.thePlayer.setSprinting(MoveUtil.canSprint());
    }
}