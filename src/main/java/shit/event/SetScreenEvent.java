/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import shit.event.Event;

@Environment(value=EnvType.CLIENT)
public class SetScreenEvent
extends Event {
    private final Screen screen2;

    public SetScreenEvent(Screen screen) {
        this.screen2 = screen;
    }
}

