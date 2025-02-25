package clarity.wtf.modules.fun;

import clarity.wtf.modules.Category;
import clarity.wtf.modules.Module;
import clarity.wtf.modules.ModuleInfo;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.InputStream;
import clarity.wtf.events.TickEvent;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(name = "Music", description = "Plays Clarity on loop", category = Category.FUN)
public class Music extends Module {
    private Thread audioThread;
    private volatile boolean playing = false;
    private Player player;

    public Music(){
        super("Music","Plays Clarity on loop",Category.FUN);
    }

    @Override
    public void onEnable() {
        System.out.println("Enabled audio");
        playing = true;
        audioThread = new Thread(() -> {
            while (playing) {
                try {
                    InputStream input = Music.class.getResourceAsStream("/assets/clarity.mp3");
                    BufferedInputStream bufferedInput = new BufferedInputStream(input);
                    player = new Player(bufferedInput);
                    player.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        audioThread.start();
    }

    @Override
    public void onDisable() {
        playing = false;
        if (player != null) {
            player.close();
        }
        if (audioThread != null) {
            audioThread.interrupt();
        }
    }

    @Subscribe
    public void onTick(TickEvent event) {}
} 