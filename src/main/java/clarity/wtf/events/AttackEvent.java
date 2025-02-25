package clarity.wtf.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.Entity;
//note to joshie man.... this is how u do attack event...
@Getter
@Setter
@AllArgsConstructor
public class AttackEvent {
    private Entity targetEntity;
}
