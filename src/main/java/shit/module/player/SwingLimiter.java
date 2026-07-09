/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class SwingLimiter
extends Module {
    public static SwingLimiter INSTANCE;
    private final NumberSetting duration = (NumberSetting)this.m28(new NumberSetting("Duration", 6.0, 1.0, 50.0, 1.0));

    public SwingLimiter() {
        super("SwingLimiter", "Limits the hand swing animation duration.", Category.PLAYER);
        INSTANCE = this;
    }

    public int getInt2() {
        return this.duration.getInt50();
    }
}

