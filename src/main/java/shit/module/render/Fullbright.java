/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class Fullbright
extends Module {
    public static Fullbright INSTANCE;
    public final NumberSetting gamma = (NumberSetting)this.m28(new NumberSetting("Brightness", 1.0, 0.1, 1.0, 0.05));

    public Fullbright() {
        super("Fullbright", "Fully brightens the world (forces the lightmap).", Category.RENDER);
        INSTANCE = this;
    }
}
