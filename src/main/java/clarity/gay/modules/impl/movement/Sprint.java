package clarity.gay.modules.impl.movement;

import clarity.gay.events.TickEvent;
import clarity.gay.modules.api.Category;
import clarity.gay.modules.api.Module;
import clarity.gay.modules.api.ModuleInfo;
import clarity.gay.utils.MoveUtil;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "Sprint", description = "Automatically sprints for you.", category = Category.PLAYER)
public class Sprint extends Module {

    @Subscribe
    public void onTick(TickEvent event) {
        mc.thePlayer.setSprinting(MoveUtil.canSprint());
    }
}