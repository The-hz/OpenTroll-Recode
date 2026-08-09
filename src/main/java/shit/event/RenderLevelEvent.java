/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import org.joml.Matrix4f;
import shit.event.Event;
import shit.event.TickEvent;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class RenderLevelEvent
extends Event {
    private final RenderTickCounter field39;
    private final Camera field49;
    private final Matrix4f matrix4f6;
    private final Matrix4f matrix4f11;

    public RenderLevelEvent(RenderTickCounter renderTickCounter, Camera camera, Matrix4f matrix4f, Matrix4f matrix4f2) {
        boolean bl = TickEvent.isSet103();
        boolean bl2 = bl;
        this.field39 = renderTickCounter;
        this.field49 = camera;
        this.matrix4f6 = matrix4f;
        this.matrix4f11 = matrix4f2;
        if (Module.getTextArray9() == null) {
            TickEvent.setFlag9(!bl2);
        }
    }

    public Camera getObj4() {
        return this.field49;
    }

    public Matrix4f getMatrix4f3() {
        return this.matrix4f6;
    }

    public Matrix4f getMatrix4f() {
        return this.matrix4f11;
    }
}

