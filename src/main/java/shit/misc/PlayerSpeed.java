/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.List;
import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.hud.AbstractHudModule;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class PlayerSpeed
extends AbstractHudModule {
    public PlayerSpeed() {
        super("PlayerSpeed", "Shows horizontal movement speed.", 6, 162);
    }

    @Override
    protected List lines() {
        boolean bl = true;
        if (MC.mc.player == null) {
            return List.of("Speed 0.00 km/h");
        }
        double d = MC.mc.player.getX() - MC.mc.player.lastRenderX;
        double d2 = MC.mc.player.getZ() - MC.mc.player.lastRenderZ;
        double d3 = Math.sqrt(d * d + d2 * d2) * 20.0;
        return List.of(String.format(Locale.ROOT, "Speed %.2f km/h", d3 * 3.6));
    }
}

