/**
 * Not sure how to implement it but I tried
 */
package clarity.wtf.events;

import net.minecraft.entity.Entity;

public class AttackEvent {
    private final Entity target;

    public AttackEvent(Entity target) {
        this.target = target;
    }

    public Entity getTarget() {
        return target;
    }
}