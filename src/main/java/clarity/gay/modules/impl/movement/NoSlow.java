package clarity.gay.modules.impl.movement;

import clarity.gay.events.TickEvent;
import clarity.gay.modules.api.Category;
import clarity.gay.modules.api.Module;
import clarity.gay.modules.api.ModuleInfo;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "NoSlow", description = "Prevents items from slowing you down.", category = Category.MOVEMENT)
public class NoSlow extends Module {
    @Subscribe
    public void onTick(TickEvent event) {}
}
