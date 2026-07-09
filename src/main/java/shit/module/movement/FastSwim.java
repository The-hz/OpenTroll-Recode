/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.MathUtil;

@Environment(value=EnvType.CLIENT)
public class FastSwim
extends Module {
    private final BooleanSetting water = (BooleanSetting)this.m28(new BooleanSetting("Water", true));
    private final BooleanSetting lava = (BooleanSetting)this.m28(new BooleanSetting("Lava", true));
    private final NumberSetting horizontal = (NumberSetting)this.m28(new NumberSetting("Horizontal", 1.35, 1.0, 3.0, 0.05));
    private final NumberSetting vertical = (NumberSetting)this.m28(new NumberSetting("Vertical", 0.12, 0.01, 0.6, 0.01));

    public FastSwim() {
        super("FastSwim", "Speeds up movement in liquids.", Category.MOVEMENT);
    }

    @EventHandler
    private void setEvent2Inner38(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        boolean bl = MC.client3.player.isTouchingWater();
        boolean bl2 = MC.client3.player.isInLava();
        if (!(((Boolean)this.water.getObj()).booleanValue() && bl || ((Boolean)this.lava.getObj()).booleanValue() && bl2)) {
            return;
        }
        if (MathUtil.isSet7()) {
            MC.client3.player.setVelocity(MC.client3.player.getVelocity().x * (Double)this.horizontal.getObj(), MC.client3.player.getVelocity().y, MC.client3.player.getVelocity().z * (Double)this.horizontal.getObj());
        }
        if (MC.client3.options.jumpKey.isPressed()) {
            MC.client3.player.setVelocity(MC.client3.player.getVelocity().x, ((Double)this.vertical.getObj()).doubleValue(), MC.client3.player.getVelocity().z);
        } else if (MC.client3.options.sneakKey.isPressed()) {
            MC.client3.player.setVelocity(MC.client3.player.getVelocity().x, -((Double)this.vertical.getObj()).doubleValue(), MC.client3.player.getVelocity().z);
        }
    }
}

