/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public final class Timer
implements MC {
    private static long time19;

    private Timer() {
    }

    public static void m537() {
        ++time19;
    }

    public static void m816() {
    }

    public static long getLong10() {
        return time19;
    }
}

