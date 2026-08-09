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
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoJump
extends Module {
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 10.0, 0.0, 40.0, 1.0));
    private int count61;

    public AutoJump() {
        super("AutoJump", "Automatically jumps when possible.", Category.MOVEMENT);
    }

    @EventHandler
    private void setEvent2Inner20(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (MC.mc.player.isTouchingWater() || MC.mc.player.isInLava()) {
            MC.mc.player.setVelocity(MC.mc.player.getVelocity().x, 0.1, MC.mc.player.getVelocity().z);
            return;
        }
        if (!MC.mc.player.isOnGround()) {
            return;
        }
        if (this.count61++ < this.delay.getInt()) {
            return;
        }
        this.count61 = 0;
        MC.mc.player.jump();
    }
}

