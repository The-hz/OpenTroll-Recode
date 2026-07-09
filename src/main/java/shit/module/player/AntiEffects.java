/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;

@Environment(value=EnvType.CLIENT)
public class AntiEffects
extends Module {
    public static AntiEffects INSTANCE;
    public final BooleanSetting levitation = (BooleanSetting)this.m28(new BooleanSetting("Levitation", true));
    public final BooleanSetting slowFalling = (BooleanSetting)this.m28(new BooleanSetting("SlowFalling", true));

    public AntiEffects() {
        super("AntiEffects", "Provides anti levitation and slow falling toggles for movement hooks.", Category.PLAYER);
        INSTANCE = this;
    }
}

