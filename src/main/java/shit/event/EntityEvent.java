/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.Entity;

@Environment(value=EnvType.CLIENT)
public class EntityEvent
extends shit.event.Event {
    private final Entity entity;
    private VertexConsumerProvider field53;

    public EntityEvent(Entity entity, VertexConsumerProvider vertexConsumerProvider) {
        this.entity = entity;
        this.field53 = vertexConsumerProvider;
    }

    @Environment(value=EnvType.CLIENT)
    public static class Event
    extends shit.event.Event {
    }
}

