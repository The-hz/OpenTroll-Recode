/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.movement.Timer;

@Environment(value=EnvType.CLIENT)
public class Helper4 {
    public float value121 = 1.0f;
    public float value143;

    public void setFloat5(float f) {
        float f2 = f;
        Object var4_3 = null;
        if (f2 < 0.1f) {
            f2 = 0.1f;
        }
        this.value121 = f2;
    }

    public void m502() {
        this.value143 = this.value121 = this.getFloat61();
    }

    public void m64() {
        Object var2_1 = null;
        if (this.value143 != this.getFloat61()) {
            this.m502();
        }
    }

    public float getFloat62() {
        return this.value121;
    }

    public float getFloat61() {
        Object var2_1 = null;
        if (Timer.INSTANCE != null) {
            if (Timer.INSTANCE.isEnabled()) {
                return ((Double)Timer.INSTANCE.speed.getValue()).floatValue();
            }
        }
        return 1.0f;
    }
}

