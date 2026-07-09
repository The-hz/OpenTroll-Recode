/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import shit.event.Event;

@Environment(value=EnvType.CLIENT)
public class PlayerEvent
extends Event {
    private final PlayerEntity player3;

    public PlayerEvent(PlayerEntity playerEntity) {
        this.player3 = playerEntity;
    }

    public PlayerEntity getPlayer4() {
        return this.player3;
    }
}

