/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event;

@Environment(value=EnvType.CLIENT)
public class HookTotemParticleInitEvent
extends Event {
    public double value129;
    public double value200;
    public double value123;
    public int count184;

    public HookTotemParticleInitEvent(double d, double d2, double d3) {
        this.value129 = d;
        this.value200 = d2;
        this.value123 = d3;
        this.count184 = 0;
    }
}

