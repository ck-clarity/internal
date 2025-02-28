package clarity.gay.modules.impl.render;

import clarity.gay.events.TickEvent;
import clarity.gay.modules.api.Category;
import clarity.gay.modules.api.Module;
import clarity.gay.modules.api.ModuleInfo;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "Animations", description = "Returns 1.7.10's blocking animations.", category = Category.RENDER)
public class Animations extends Module {
    @Subscribe public void onTick(TickEvent event) {}
}
