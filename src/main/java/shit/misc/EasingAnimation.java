/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Module;
import shit.type.EasingMode;

@Environment(value=EnvType.CLIENT)
public class EasingAnimation {
    private final EasingMode field26;
    private final float value198;
    private float value187;
    private float value168;
    private long time13;
    private static String text1269;

    public EasingAnimation(EasingMode enum_NuWsin, float f) {
        block0: {
            String string = EasingAnimation.getText18();
            String string2 = string;
            this.time13 = System.currentTimeMillis();
            this.field26 = enum_NuWsin;
            this.value198 = f;
            if (string2 == null) break block0;
            Module.setTextArray9(new String[4]);
        }
    }

    public float m170(float f) {
        float f2;
        block4: {
            float f3;
            block5: {
                float f4 = f;
                f3 = this.getFloat67();
                String string = EasingAnimation.getText18();
                f2 = f4;
                if (string != null) break block4;
                if (Float.isNaN(f2)) break block5;
                f2 = this.value168;
                if (string != null) break block4;
                if (f2 != f4) {
                    this.value187 = f3;
                    this.value168 = f4;
                    this.time13 = System.currentTimeMillis();
                }
            }
            f2 = f3;
        }
        return f2;
    }

    public float getFloat67() {
        return this.field26.m220(EasingMode.m164(this.time13, this.value198), this.value187, this.value168);
    }

    public void setFloat(float f) {
        float f2;
        this.value187 = f2 = f;
        this.value168 = f2;
        this.time13 = System.currentTimeMillis();
    }

    public static void setText2(String string) {
        text1269 = string;
    }

    public static String getText18() {
        return text1269;
    }

    static {
        if (EasingAnimation.getText18() != null) {
            EasingAnimation.setText2("EasingAnimation");
        }
    }
}

