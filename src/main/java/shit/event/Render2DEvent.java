/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import shit.event.Event;

@Environment(value=EnvType.CLIENT)
public class Render2DEvent
extends Event {
    private final DrawContext drawContext;
    private final RenderTickCounter field65;

    public Render2DEvent(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        this.drawContext = drawContext;
        this.field65 = renderTickCounter;
    }

    public DrawContext getDrawContext() {
        return this.drawContext;
    }
}

