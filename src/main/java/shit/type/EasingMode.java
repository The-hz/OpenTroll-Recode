/*
 * Decompiled with CFR 0.152.
 */
package shit.type;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.misc.EasingAnimation;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public enum EasingMode {
      OUT_CUBIC, OUT_QUART, OUT_EXPO;

      private EasingMode() {}



    public float m220(float f, float f2, float f3) {
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        return f5 + (f6 - f5) * this.m984(f4);
    }

    public float m984(float f) {
        float f2;
        block6: {
            float f3;
            float f4;
            block5: {
                f4 = f;
                String string = EasingAnimation.getText18();
                float f5 = f4 - 0.0f;
                f3 = f5 == 0.0f ? 0 : (f5 < 0.0f ? -1 : 1);
                if (string != null) break block5;
                if (f3 <= 0) {
                    return 0.0f;
                }
                f2 = f4;
                if (string != null) break block6;
                float f6 = f2 - 1.0f;
                f3 = f6 == 0.0f ? 0 : (f6 > 0.0f ? 1 : -1);
            }
            if (f3 >= 0) {
                return 1.0f;
            }
            f2 = this.m829(f4);
        }
        return f2;
    }

        
        protected float m829(float f) {
            float f2;
            block0: {
                float f3 = f;
                EasingAnimation.getText18();
                f2 = 1.0f - f3;
                if (Module.getTextArray9() != null) break block0;
                EasingAnimation.setText2("IMZEf");
            }
            return 1.0f - f2 * f2 * f2 * f2;
        }

    public static float m164(long l, float f) {
        long l2 = l;
        float f2 = f;
        return Math.max(0.0f, Math.min(1.0f, (float)(System.currentTimeMillis() - l2) / f2));
    }

    private static float m231(float f) {
        float f2 = f;
        return f2 * f2 * f2;
    }

    private static EasingMode[] getObjArray12() {
        return new EasingMode[]{OUT_CUBIC, OUT_QUART, OUT_EXPO};
    }




   }

