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
public class Rotation
extends AbstractHudModule {
    public Rotation() {
        super("Rotation", "Shows yaw and pitch.", 6, 150);
    }

    @Override
    protected List lines() {
        if (MC.client3.player == null) {
            return List.of("Rotation 0.0 0.0");
        }
        return List.of(String.format(Locale.ROOT, "Rotation %.1f %.1f", Float.valueOf(MC.client3.player.getYaw()), Float.valueOf(MC.client3.player.getPitch())));
    }

    private static MatchException a(MatchException matchException) {
        return matchException;
    }
}

