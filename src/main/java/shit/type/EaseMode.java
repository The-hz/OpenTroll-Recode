/*
 * Decompiled with CFR 0.152.
 */
package shit.type;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.misc.Stopwatch;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public enum EaseMode {
      CubicInOut;

      private EaseMode() {}



    public double m799(double var1) {
        if (var1 < 0.5) {
            return 4.0 * var1 * var1 * var1;
        }
        return 1.0 - Math.pow(-2.0 * var1 + 2.0, 3.0) / 2.0;
    }

    private static EaseMode[] getEaseModeArray() {
        return new EaseMode[]{CubicInOut};
    }


   }

