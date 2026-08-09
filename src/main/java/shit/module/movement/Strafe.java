/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffects;
import shit.event.EventHandler;
import shit.event.MoveEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.ItemUtil;
import shit.util.MC;
import shit.util.MathUtil;

@Environment(value=EnvType.CLIENT)
public class Strafe
extends Module {
    public static Strafe INSTANCE;
    private final BooleanSetting airStop = (BooleanSetting)this.registerSetting(new BooleanSetting("AirStop", true));
    private final BooleanSetting slowCheck = (BooleanSetting)this.registerSetting(new BooleanSetting("SlowCheck", true));

    public Strafe() {
        super("Strafe", "Modifies movement to allow sharp strafing in mid-air.", Category.MOVEMENT);
        INSTANCE = this;
    }

    @EventHandler
    public void setMoveEvent(MoveEvent moveEvent) {
        if (Module.isNotInGame()) {
            return;
        }
        if (!(MC.mc.player.isInSneakingPose() || MC.mc.player.isGliding() || ItemUtil.isSet26() || MC.mc.player.isInLava() || MC.mc.player.isTouchingWater() || MC.mc.player.getAbilities().flying)) {
            if (!MathUtil.isSet7()) {
                if (((Boolean)this.airStop.getValue()).booleanValue()) {
                    MathUtil.setDouble8(0.0);
                    MathUtil.setDouble9(0.0);
                }
            } else {
                double[] dArray = MathUtil.m246(this.getDouble10());
                moveEvent.setDouble2(dArray[0]);
                moveEvent.setDouble(dArray[1]);
            }
        }
    }

    public double getDouble10() {
        double d;
        block2: {
            block3: {
                d = 0.2873;
                Object var2_2 = null;
                if (MC.mc.player == null) break block2;
                if (!MC.mc.player.hasStatusEffect(StatusEffects.SPEED)) break block2;
                if (!((Boolean)this.slowCheck.getValue()).booleanValue()) break block3;
                if (MC.mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) break block2;
            }
            int n = MC.mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier();
            d *= 1.0 + 0.2 * (double)(n + 1);
        }
        return d;
    }
}

