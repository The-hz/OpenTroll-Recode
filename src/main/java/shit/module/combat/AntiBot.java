/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;

@Environment(value=EnvType.CLIENT)
public class AntiBot
extends Module {
    public static AntiBot INSTANCE;
    private final BooleanSetting tabList = (BooleanSetting)this.m28(new BooleanSetting("TabList", true));
    private final BooleanSetting invalidName = (BooleanSetting)this.m28(new BooleanSetting("InvalidName", true));
    private final BooleanSetting zeroHealth = (BooleanSetting)this.m28(new BooleanSetting("ZeroHealth", true));

    public AntiBot() {
        super("AntiBot", "Provides simple bot checks for combat modules.", Category.COMBAT);
        INSTANCE = this;
    }
}

