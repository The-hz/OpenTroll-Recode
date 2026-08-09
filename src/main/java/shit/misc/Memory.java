/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.hud.AbstractHudModule;

@Environment(value=EnvType.CLIENT)
public class Memory
extends AbstractHudModule {
    public Memory() {
        super("Memory", "Shows JVM memory usage.", 6, 90);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    protected List lines() {
        Runtime runtime = Runtime.getRuntime();
        long used = runtime.totalMemory() - runtime.freeMemory();
        long max = runtime.maxMemory();
        int percent = max <= 0L ? 0 : (int)Math.round((double)used * 100.0 / (double)max);
        return List.of("Memory " + this.m457(used) + "/" + this.m457(max) + "MB " + percent + "%");
    }

    private long m457(long l) {
        long l2 = l;
        return l2 / 1024L / 1024L;
    }
}

