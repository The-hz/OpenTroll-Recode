/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.List;
import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.hud.AbstractHudModule;
import shit.module.movement.Timer;

@Environment(value=EnvType.CLIENT)
public class TimerSpeed
extends AbstractHudModule {
    public TimerSpeed() {
        super("TimerSpeed", "Shows the Timer module speed.", 6, 174);
    }

    @Override
    protected List lines() {
        boolean bl = true;
        if (Timer.INSTANCE == null || !Timer.INSTANCE.isSet19()) {
            return List.of("Timer 1.0x");
        }
        return List.of(String.format(Locale.ROOT, "Timer %.2fx", Timer.INSTANCE.speed.getObj()));
    }
}

