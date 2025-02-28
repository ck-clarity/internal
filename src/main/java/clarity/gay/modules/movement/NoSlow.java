package clarity.gay.modules.movement;

import clarity.gay.events.TickEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "NoSlow", description = "Prevents items from slowing you down.", category = Category.MOVEMENT)
public class NoSlow extends Module {
    @Subscribe
    public void onTick(TickEvent event) {}
}
