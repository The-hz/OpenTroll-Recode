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
    private final BooleanSetting water = (BooleanSetting)this.registerSetting(new BooleanSetting("Water", true));
    private final BooleanSetting lava = (BooleanSetting)this.registerSetting(new BooleanSetting("Lava", true));
    private final NumberSetting horizontal = (NumberSetting)this.registerSetting(new NumberSetting("Horizontal", 1.35, 1.0, 3.0, 0.05));
    private final NumberSetting vertical = (NumberSetting)this.registerSetting(new NumberSetting("Vertical", 0.12, 0.01, 0.6, 0.01));

    public FastSwim() {
        super("FastSwim", "Speeds up movement in liquids.", Category.MOVEMENT);
    }

    @EventHandler
    private void setEvent2Inner38(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        boolean bl = MC.mc.player.isTouchingWater();
        boolean bl2 = MC.mc.player.isInLava();
        if (!(((Boolean)this.water.getValue()).booleanValue() && bl || ((Boolean)this.lava.getValue()).booleanValue() && bl2)) {
            return;
        }
        if (MathUtil.isMoving()) {
            MC.mc.player.setVelocity(MC.mc.player.getVelocity().x * (Double)this.horizontal.getValue(), MC.mc.player.getVelocity().y, MC.mc.player.getVelocity().z * (Double)this.horizontal.getValue());
        }
        if (MC.mc.options.jumpKey.isPressed()) {
            MC.mc.player.setVelocity(MC.mc.player.getVelocity().x, ((Double)this.vertical.getValue()).doubleValue(), MC.mc.player.getVelocity().z);
        } else if (MC.mc.options.sneakKey.isPressed()) {
            MC.mc.player.setVelocity(MC.mc.player.getVelocity().x, -((Double)this.vertical.getValue()).doubleValue(), MC.mc.player.getVelocity().z);
        }
    }
}

