package clarity.gay.modules.ui;

import clarity.gay.events.TickEvent;
import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "ClickGui",
        category = Category.UI,
        description = "ui",
        bind = Keyboard.KEY_RSHIFT
)
public class ClickGui extends Module {
//    public ClickGui(){
//        super("ClickGui","ui", Category.UI);
//    }
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
