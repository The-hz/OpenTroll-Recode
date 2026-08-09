/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class EntitySpeed
extends Module {
    private final NumberSetting speed = (NumberSetting)this.registerSetting(new NumberSetting("Speed", 1.0, 0.0, 5.0, 0.1));
    private final NumberSetting vertical = (NumberSetting)this.registerSetting(new NumberSetting("Vertical", 0.5, 0.0, 3.0, 0.1));
    private final BooleanSetting flight = (BooleanSetting)this.registerSetting(new BooleanSetting("Flight", false));

    public EntitySpeed() {
        super("EntitySpeed", "Controls ridden entity speed.", Category.MOVEMENT);
    }

    @EventHandler
    private void setEvent2Inner19(Event2.Event2Inner event2Inner) {
        double d = 0.0;
        if (Module.isNotInGame() || !MC.mc.player.hasVehicle()) {
            return;
        }
        Entity entity = MC.mc.player.getVehicle();
        if (entity == null) {
            return;
        }
        double d2 = MC.mc.options.forwardKey.isPressed() ? 1.0 : (d = MC.mc.options.backKey.isPressed() ? -1.0 : 0.0);
        double d3 = MC.mc.options.leftKey.isPressed() ? 1.0 : (MC.mc.options.rightKey.isPressed() ? -1.0 : 0.0);
        float f = MC.mc.player.getYaw();
        if (d != 0.0 && d3 != 0.0) {
            f += d > 0.0 ? (d3 > 0.0 ? -45.0f : 45.0f) : (d3 > 0.0 ? 45.0f : -45.0f);
            d3 = 0.0;
            d = Math.signum(d);
        }
        double d4 = Math.sin(Math.toRadians(f + 90.0f));
        double d5 = Math.cos(Math.toRadians(f + 90.0f));
        double d6 = d * (Double)this.speed.getValue() * d5 + d3 * (Double)this.speed.getValue() * d4;
        double d7 = d * (Double)this.speed.getValue() * d4 - d3 * (Double)this.speed.getValue() * d5;
        double d8 = entity.getVelocity().y;
        if (((Boolean)this.flight.getValue()).booleanValue()) {
            d8 = MC.mc.options.jumpKey.isPressed() ? (Double)this.vertical.getValue() : (MC.mc.options.sneakKey.isPressed() ? -((Double)this.vertical.getValue()).doubleValue() : 0.0);
        }
        entity.setVelocity(new Vec3d(d6, d8, d7));
    }
}

