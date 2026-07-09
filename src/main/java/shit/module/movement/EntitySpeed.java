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
    private final NumberSetting speed = (NumberSetting)this.m28(new NumberSetting("Speed", 1.0, 0.0, 5.0, 0.1));
    private final NumberSetting vertical = (NumberSetting)this.m28(new NumberSetting("Vertical", 0.5, 0.0, 3.0, 0.1));
    private final BooleanSetting flight = (BooleanSetting)this.m28(new BooleanSetting("Flight", false));

    public EntitySpeed() {
        super("EntitySpeed", "Controls ridden entity speed.", Category.MOVEMENT);
    }

    @EventHandler
    private void setEvent2Inner19(Event2.Event2Inner event2Inner) {
        double d = 0.0;
        if (Module.isSet37() || !MC.client3.player.hasVehicle()) {
            return;
        }
        Entity entity = MC.client3.player.getVehicle();
        if (entity == null) {
            return;
        }
        double d2 = MC.client3.options.forwardKey.isPressed() ? 1.0 : (d = MC.client3.options.backKey.isPressed() ? -1.0 : 0.0);
        double d3 = MC.client3.options.leftKey.isPressed() ? 1.0 : (MC.client3.options.rightKey.isPressed() ? -1.0 : 0.0);
        float f = MC.client3.player.getYaw();
        if (d != 0.0 && d3 != 0.0) {
            f += d > 0.0 ? (d3 > 0.0 ? -45.0f : 45.0f) : (d3 > 0.0 ? 45.0f : -45.0f);
            d3 = 0.0;
            d = Math.signum(d);
        }
        double d4 = Math.sin(Math.toRadians(f + 90.0f));
        double d5 = Math.cos(Math.toRadians(f + 90.0f));
        double d6 = d * (Double)this.speed.getObj() * d5 + d3 * (Double)this.speed.getObj() * d4;
        double d7 = d * (Double)this.speed.getObj() * d4 - d3 * (Double)this.speed.getObj() * d5;
        double d8 = entity.getVelocity().y;
        if (((Boolean)this.flight.getObj()).booleanValue()) {
            d8 = MC.client3.options.jumpKey.isPressed() ? (Double)this.vertical.getObj() : (MC.client3.options.sneakKey.isPressed() ? -((Double)this.vertical.getObj()).doubleValue() : 0.0);
        }
        entity.setVelocity(new Vec3d(d6, d8, d7));
    }
}

