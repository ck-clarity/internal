package clarity.gay.events;

public class KeyPressEvent {
    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public int keybind;
    public KeyPressEvent(int keybind) {
        this.keybind = keybind;
    }
}
