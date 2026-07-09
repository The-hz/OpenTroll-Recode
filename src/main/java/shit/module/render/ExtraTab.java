/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class ExtraTab
extends Module {
    public static ExtraTab INSTANCE;
    private final NumberSetting maxPlayers = (NumberSetting)this.m28(new NumberSetting("MaxPlayers", 265.0, 80.0, 400.0, 5.0));

    public ExtraTab() {
        super("ExtraTab", "Expands the player tab list.", Category.RENDER);
        INSTANCE = this;
    }

    @Override
    public String getText57() {
        return Integer.toString(this.maxPlayers.getInt50());
    }

    public static List m762(Object object) {
        ExtraTab extraTab;
        List list;
        block3: {
            block2: {
                list = (List)object;
                extraTab = INSTANCE;
                Object var3_3 = null;
                if (extraTab == null) break block2;
                if (extraTab.isSet19()) break block3;
            }
            return list;
        }
        int n = Math.min(extraTab.maxPlayers.getInt50(), list.size());
        return list.subList(0, n);
    }
}

