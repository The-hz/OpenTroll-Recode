/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffects;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AntiLevitation
extends Module {
    public AntiLevitation() {
        super("AntiLevitation", "Removes the levitation effect locally.", Category.MOVEMENT);
    }

    @EventHandler
    private void setEvent2Inner32(TickEvent.PreTick event2Inner) {
        if (!Module.isNotInGame()) {
            MC.mc.player.removeStatusEffect(StatusEffects.LEVITATION);
        }
    }
}

