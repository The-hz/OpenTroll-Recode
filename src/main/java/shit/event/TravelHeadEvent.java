/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3d;
import shit.event.Event;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class TravelHeadEvent
extends Event {
    private final Vec3d vec3d6;
    private final boolean flag92;
    private final boolean flag147;
    private double value203;
    private double value145;
    private double value139;

    public TravelHeadEvent(Vec3d vec3d, boolean bl, boolean bl2, double d, double d2, double d3) {
        this.vec3d6 = vec3d;
        this.flag92 = bl;
        this.flag147 = bl2;
        this.value203 = d;
        this.value145 = d2;
        this.value139 = d3;
        boolean bl3 = false;
        Module.setTextArray9(new String[5]);
    }

    public double getDouble3() {
        return this.value203;
    }

    public double getDouble5() {
        return this.value145;
    }

    public double getDouble11() {
        return this.value139;
    }

    public void setDouble5(double d) {
        double d2;
        this.value203 = d2 = d;
    }

    public void setDouble3(double d) {
        double d2;
        this.value145 = d2 = d;
    }

    public void setDouble6(double d) {
        double d2;
        this.value139 = d2 = d;
    }
}

