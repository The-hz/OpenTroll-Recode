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
public class Direction
extends AbstractHudModule {
    public Direction() {
        super("Direction", "Shows your horizontal facing.", 6, 138);
    }

    @Override
    protected List lines() {
        String string;
        String string2;
        block6: {
            block12: {
                float f;
                block11: {
                    block10: {
                        block9: {
                            block8: {
                                block7: {
                                    block5: {
                                        block4: {
                                            boolean bl = true;
                                            if (MC.mc.player == null) {
                                                return List.of("Direction N/A");
                                            }
                                            f = MC.mc.player.getYaw();
                                            f = (f % 360.0f + 360.0f) % 360.0f;
                                            if ((double)f >= 337.5) break block4;
                                            if (!((double)f < 22.5)) break block5;
                                        }
                                        string2 = "South";
                                        string = "+Z";
                                        if (true) break block6;
                                    }
                                    if (!((double)f < 67.5)) break block7;
                                    string2 = "Southwest";
                                    string = "-X +Z";
                                    if (true) break block6;
                                }
                                if (!((double)f < 112.5)) break block8;
                                string2 = "West";
                                string = "-X";
                                if (true) break block6;
                            }
                            if (!((double)f < 157.5)) break block9;
                            string2 = "Northwest";
                            string = "-X -Z";
                            if (true) break block6;
                        }
                        if (!((double)f < 202.5)) break block10;
                        string2 = "North";
                        string = "-Z";
                        if (true) break block6;
                    }
                    if (!((double)f < 247.5)) break block11;
                    string2 = "Northeast";
                    string = "+X -Z";
                    if (true) break block6;
                }
                if (!((double)f < 292.5)) break block12;
                string2 = "East";
                string = "+X";
                if (true) break block6;
            }
            string2 = "Southeast";
            string = "+X +Z";
        }
        return List.of(string2 + " [" + string + "]");
    }
}

