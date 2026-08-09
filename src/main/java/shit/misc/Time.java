/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.hud.AbstractHudModule;

@Environment(value=EnvType.CLIENT)
public class Time
extends AbstractHudModule {
    private static final DateTimeFormatter dateTimeFormatter = null;

    public Time() {
        super("Time", "Shows local time.", 6, 102);
    }

    @Override
    protected List lines() {
        return List.of("Time " + LocalTime.now().format(dateTimeFormatter));
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

