package clarity.gay.modules.fun;

import clarity.gay.modules.Category;
import clarity.gay.modules.Module;
import clarity.gay.modules.ModuleInfo;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.InputStream;
import clarity.gay.events.TickEvent;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.input.Keyboard;

@ModuleInfo(
        name = "Music",
        category = Category.FUN,
        description = "Plays Clarity on loop",
        bind = Keyboard.KEY_P
)
public class Music extends Module {
    private Thread audioThread;
    private volatile boolean playing = false;
    private Player player;

//    public Music(){
//        super("Music", Category.FUN, "Plays Clarity on loop");
//    }

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