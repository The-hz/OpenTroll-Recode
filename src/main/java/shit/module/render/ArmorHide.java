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
public class ArmorHide
extends Module {
    public static ArmorHide INSTANCE;
    public final BooleanSetting parts = (BooleanSetting)this.m28(new BooleanSetting("Parts", true));
    public final BooleanSetting trim = (BooleanSetting)this.m28(new BooleanSetting("Trim", true));
    public final BooleanSetting glint = (BooleanSetting)this.m28(new BooleanSetting("Glint", true));

    public ArmorHide() {
        super("ArmorHide", "Hides rendered armor layers and armor decorations.", Category.RENDER);
        INSTANCE = this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet126() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isSet19()) return false;
        if ((Boolean)ArmorHide.INSTANCE.parts.getObj() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet158() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isSet19()) return false;
        if ((Boolean)ArmorHide.INSTANCE.trim.getObj() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet136() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isSet19()) return false;
        if ((Boolean)ArmorHide.INSTANCE.glint.getObj() == false) return false;
        return true;
    }
}

