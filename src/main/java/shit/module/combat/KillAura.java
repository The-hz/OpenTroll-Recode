/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class KillAura
extends Module {
    private final NumberSetting range = (NumberSetting)this.m28(new NumberSetting("Range", 4.5, 1.0, 6.0, 0.1));
    private final BooleanSetting rotate = (BooleanSetting)this.m28(new BooleanSetting("Rotate", true));

    public KillAura() {
        super("KillAura", "Attacks nearby entities. Placeholder logic.", Category.COMBAT);
    }
}

