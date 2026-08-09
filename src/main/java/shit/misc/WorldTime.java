/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.hud.AbstractHudModule;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class WorldTime
extends AbstractHudModule {
    public WorldTime() {
        super("WorldTime", "Shows Minecraft world time.", 6, 198);
    }

    @Override
    protected List lines() {
        Object var2_1 = null;
        if (MC.mc.world == null) {
            return List.of("WorldTime 00:00");
        }
        long l = MC.mc.world.getTimeOfDay() % 24000L;
        long l2 = (l / 1000L + 6L) % 24L;
        long l3 = l % 1000L * 60L / 1000L;
        return List.of(String.format("WorldTime %02d:%02d", l2, l3));
    }
}

