/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class AntiFog
extends Module {
    public static AntiFog INSTANCE;

    public AntiFog() {
        super("AntiFog", "Removes world and blindness fog.", Category.RENDER);
        INSTANCE = this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet25() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isEnabled()) return false;
        return true;
    }
}

