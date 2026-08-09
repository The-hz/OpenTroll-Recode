/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.memory.ObjectAllocator;
import shit.event.Event;

@Environment(value=EnvType.CLIENT)
public class InterceptEntityOutlineEvent
extends Event {
    private final WorldRenderer field45;
    private final ObjectAllocator field43;

    public InterceptEntityOutlineEvent(WorldRenderer worldRenderer, ObjectAllocator objectAllocator) {
        this.field45 = worldRenderer;
        this.field43 = objectAllocator;
    }

    public WorldRenderer getObj17() {
        return this.field45;
    }

    public ObjectAllocator getObj5() {
        return this.field43;
    }
}

