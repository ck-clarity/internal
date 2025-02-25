package clarity.wtf.modules.movement;

import clarity.wtf.events.TickEvent;
import clarity.wtf.modules.Category;
import clarity.wtf.modules.Module;
import clarity.wtf.modules.ModuleInfo;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "Speed", description = "zoom", category = Category.MOVEMENT)
public class Speed extends Module {

    public Speed(){
        super("Speed","zoom",Category.MOVEMENT);
    }

    @Override
    public void onEnable(){
        System.out.println("Test");
    }
    @Override
    public void onDisable(){
        System.out.println("Test2");
    }
    @Subscribe
    public void onTick(TickEvent event) {
        mc.thePlayer.motionY = 0;
        }
}
