package clarity.gay.modules.render;

import clarity.gay.events.TickEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "Animations", description = "Returns 1.7.10's blocking animations.", category = Category.RENDER)
public class Animations extends Module {
    @Subscribe public void onTick(TickEvent event) {}
}
