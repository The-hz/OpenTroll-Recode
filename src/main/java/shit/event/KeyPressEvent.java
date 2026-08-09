/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.input.KeyInput;
import shit.event.Event;

@Environment(value=EnvType.CLIENT)
public class KeyPressEvent
extends Event {
    private final int count125;
    private final KeyInput field44;

    public KeyPressEvent(int n, KeyInput keyInput) {
        this.count125 = n;
        this.field44 = keyInput;
    }
}

