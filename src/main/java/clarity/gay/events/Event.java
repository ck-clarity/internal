package clarity.gay.events;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Event {

    private Type type;
    private Direction direction;

    @Setter
    private boolean cancelled;

    public Event(Type type, Direction direction) {
        this.type = type;
        this.direction = direction;
        this.cancelled = false;
    }

    public Event() {
        this(Type.PRE, Direction.IN);
    }

    public Event setType(Type type) {
        this.type = type;
        return this;
    }

    public Event setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public enum Type {

        PRE,
        POST;

    }

    public enum Direction {

        IN,
        OUT;

    }

}