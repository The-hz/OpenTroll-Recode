/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;

@Environment(value=EnvType.CLIENT)
public class AntiOverlay
extends Module {
    public static AntiOverlay INSTANCE;
    public final BooleanSetting fire = (BooleanSetting)this.m28(new BooleanSetting("Fire", true));
    public final BooleanSetting water = (BooleanSetting)this.m28(new BooleanSetting("Water", true));
    public final BooleanSetting block = (BooleanSetting)this.m28(new BooleanSetting("Block", true));

    public AntiOverlay() {
        super("AntiOverlay", "Hides first-person screen overlays.", Category.RENDER);
        INSTANCE = this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet175() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isSet19()) return false;
        if ((Boolean)AntiOverlay.INSTANCE.fire.getObj() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet147() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isSet19()) return false;
        if ((Boolean)AntiOverlay.INSTANCE.water.getObj() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet28() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isSet19()) return false;
        if ((Boolean)AntiOverlay.INSTANCE.block.getObj() == false) return false;
        return true;
    }
}

