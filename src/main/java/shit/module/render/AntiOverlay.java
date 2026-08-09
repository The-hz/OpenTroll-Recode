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
    public final BooleanSetting fire = (BooleanSetting)this.registerSetting(new BooleanSetting("Fire", true));
    public final BooleanSetting water = (BooleanSetting)this.registerSetting(new BooleanSetting("Water", true));
    public final BooleanSetting block = (BooleanSetting)this.registerSetting(new BooleanSetting("Block", true));

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
        if (!INSTANCE.isEnabled()) return false;
        if ((Boolean)AntiOverlay.INSTANCE.fire.getValue() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet147() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isEnabled()) return false;
        if ((Boolean)AntiOverlay.INSTANCE.water.getValue() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet28() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isEnabled()) return false;
        if ((Boolean)AntiOverlay.INSTANCE.block.getValue() == false) return false;
        return true;
    }
}

