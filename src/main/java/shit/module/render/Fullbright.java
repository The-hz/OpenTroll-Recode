/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Fullbright
extends Module {
    private final NumberSetting gamma = (NumberSetting)this.m28(new NumberSetting("Gamma", 1.0, 0.0, 1.0, 0.05));
    private double value142 = -1.0;

    public Fullbright() {
        super("Fullbright", "Raises client gamma.", Category.RENDER);
    }

    @Override
    public void onEnable() {
        this.value142 = (Double)MC.client3.options.getGamma().getValue();
    }

    @Override
    public void m709() {
        block0: {
            if (!(this.value142 >= 0.0)) break block0;
            MC.client3.options.getGamma().setValue(this.value142);
        }
    }

    @EventHandler
    private void setEvent2Inner227(Event2.Event2Inner2 event2Inner2) {
        MC.client3.options.getGamma().setValue((Double)this.gamma.getObj());
    }
}

