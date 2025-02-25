package clarity.wtf.modules.ui;

import clarity.wtf.events.TickEvent;
import clarity.wtf.modules.Category;
import clarity.wtf.modules.Module;
import clarity.wtf.modules.ModuleInfo;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "ClickGui", description = "ui", category = Category.UI)
public class ClickGui extends Module {
    public ClickGui(){
        super("ClickGui","ui", Category.UI);
    }
    @Override
    public void onEnable(){
        if(mc.currentScreen == null) {
            mc.displayGuiScreen(new ClickGuiScreen());
        }
        this.setEnabled(false);
        super.onEnable();
    }

    @Subscribe
    public void onTick(TickEvent event) {
    }
}
