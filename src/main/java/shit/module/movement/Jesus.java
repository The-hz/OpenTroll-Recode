/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.EventHandler;
import shit.event.TravelHeadEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.MathUtil;

@Environment(value=EnvType.CLIENT)
public class Jesus
extends Module {
    private final NumberSetting speed = (NumberSetting)this.registerSetting(new NumberSetting("Speed", 0.25, 0.0, 2.0, 0.05));
    private final NumberSetting lift = (NumberSetting)this.registerSetting(new NumberSetting("Lift", 0.1, 0.0, 0.5, 0.01));

    public Jesus() {
        super("Jesus", "Lets you move across liquid surfaces.", Category.MOVEMENT);
    }

    @EventHandler
    private void setTravelHeadEvent(TravelHeadEvent travelHeadEvent) {
        double[] dArray;
        if (Module.isNotInGame() || !MC.mc.player.isTouchingWater() && !MC.mc.player.isInLava()) {
            return;
        }
        if (MC.mc.options.sneakKey.isPressed()) {
            return;
        }
        if (MathUtil.isMoving()) {
            dArray = MathUtil.getMotionVector((Double)this.speed.getValue());
        } else {
            double[] dArray2 = new double[2];
            dArray2[0] = 0.0;
            dArray = dArray2;
            dArray2[1] = 0.0;
        }
        double[] dArray3 = dArray;
        travelHeadEvent.setDouble5(dArray3[0]);
        travelHeadEvent.setDouble3(MC.mc.options.jumpKey.isPressed() ? (Double)this.lift.getValue() : 0.0);
        travelHeadEvent.setDouble6(dArray3[1]);
        travelHeadEvent.cancel();
    }
}

