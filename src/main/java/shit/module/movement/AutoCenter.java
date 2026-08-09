/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.EventHandler;
import shit.event.InputTickEvent;
import shit.event.MoveEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoCenter
extends Module {
    public static AutoCenter INSTANCE;
    private final NumberSetting timeout = (NumberSetting)this.registerSetting(new NumberSetting("Timeout", 5.0, 1.0, 40.0, 1.0));
    private final NumberSetting speed = (NumberSetting)this.registerSetting(new NumberSetting("Speed", 0.2873, 0.05, 1.0, 0.01));
    private double value185;
    private double value146;
    private int count121;

    public AutoCenter() {
        super("AutoCenter", "Moves the player to the center of the current block.", Category.MOVEMENT);
        INSTANCE = this;
    }

    public void m47() {
        Object var2_1 = null;
        if (MC.mc.player == null) {
            return;
        }
        this.value185 = Math.floor(MC.mc.player.getX()) + 0.5;
        this.value146 = Math.floor(MC.mc.player.getZ()) + 0.5;
        this.count121 = 0;
        this.setEnabled(true);
    }

    @Override
    public void onEnable() {
        this.m47();
    }

    @EventHandler
    private void setInputTickEvent(InputTickEvent inputTickEvent) {
        if (MC.mc.options == null) {
            return;
        }
        MC.mc.options.forwardKey.setPressed(false);
        MC.mc.options.backKey.setPressed(false);
        MC.mc.options.leftKey.setPressed(false);
        MC.mc.options.rightKey.setPressed(false);
        MC.mc.options.jumpKey.setPressed(false);
        MC.mc.options.sneakKey.setPressed(false);
    }

    @EventHandler
    private void setMoveEvent6(MoveEvent moveEvent) {
        double d;
        if (Module.isNotInGame()) {
            return;
        }
        if (this.count121++ > this.timeout.getInt()) {
            this.setEnabled(false);
            return;
        }
        double d2 = this.value185 - MC.mc.player.getX();
        double d3 = Math.hypot(d2, d = this.value146 - MC.mc.player.getZ());
        if (d3 < 0.05) {
            this.setEnabled(false);
            return;
        }
        double d4 = Math.min((Double)this.speed.getValue(), d3);
        moveEvent.setDouble2(d2 / d3 * d4);
        moveEvent.setDouble(d / d3 * d4);
    }
}

