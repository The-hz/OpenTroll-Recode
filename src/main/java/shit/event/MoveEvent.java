/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event;

@Environment(value=EnvType.CLIENT)
public class MoveEvent
extends Event {
    public boolean flag112 = false;
    private double value154;
    private double value178;
    private double value206;

    public MoveEvent(double d, double d2, double d3) {
        this.value154 = d;
        this.value178 = d2;
        this.value206 = d3;
    }

    public double getDouble13() {
        return this.value154;
    }

    public void setDouble2(double d) {
        double d2 = d;
        this.flag112 = true;
        this.value154 = d2;
    }

    public double getDouble12() {
        return this.value178;
    }

    public void setDouble4(double d) {
        double d2 = d;
        this.flag112 = true;
        this.value178 = d2;
    }

    public double getDouble2() {
        return this.value206;
    }

    public void setDouble(double d) {
        double d2 = d;
        this.flag112 = true;
        this.value206 = d2;
    }
}

