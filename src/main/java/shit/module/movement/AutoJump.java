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
    private final NumberSetting delay = (NumberSetting)this.m28(new NumberSetting("Delay", 10.0, 0.0, 40.0, 1.0));
    private int count61;

    public AutoJump() {
        super("AutoJump", "Automatically jumps when possible.", Category.MOVEMENT);
    }

    @EventHandler
    private void setEvent2Inner20(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (MC.client3.player.isTouchingWater() || MC.client3.player.isInLava()) {
            MC.client3.player.setVelocity(MC.client3.player.getVelocity().x, 0.1, MC.client3.player.getVelocity().z);
            return;
        }
        if (!MC.client3.player.isOnGround()) {
            return;
        }
        if (this.count61++ < this.delay.getInt50()) {
            return;
        }
        this.count61 = 0;
        MC.client3.player.jump();
    }
}

