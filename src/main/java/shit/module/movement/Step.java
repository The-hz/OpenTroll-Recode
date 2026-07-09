/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;
import shit.util.MathUtil;

@Environment(value=EnvType.CLIENT)
public class Step
extends Module {
    private final EnumSetting mode = (EnumSetting)this.m28(new EnumSetting("Mode", Mode.Vanilla));
    private final NumberSetting height = (NumberSetting)this.m28(new NumberSetting("Height", 1.0, 0.0, 5.0, 0.5));
    private final BooleanSetting timer = (BooleanSetting)this.m28(new BooleanSetting("Timer", true, () -> {
        Object var1_1 = null;
        return this.mode.getObj() == Mode.OldNCP || this.mode.getObj() == Mode.NCP;
    }, null, "", false));
    private final BooleanSetting fast = (BooleanSetting)this.m28(new BooleanSetting("Fast", true, () -> {
        Object var1_1 = null;
        if (this.mode.getObj() != Mode.NCP) return false;
        if ((Boolean)this.timer.getObj() == false) return false;
        return true;
    }, null, "", false));
    private final BooleanSetting onlyMoving = (BooleanSetting)this.m28(new BooleanSetting("OnlyMoving", true));
    private final BooleanSetting inWebPause = (BooleanSetting)this.m28(new BooleanSetting("InWebPause", true));
    private final BooleanSetting inBlockPause = (BooleanSetting)this.m28(new BooleanSetting("InBlockPause", true));
    private final BooleanSetting sneakingPause = (BooleanSetting)this.m28(new BooleanSetting("SneakingPause", true));
    private boolean flag15;
    private int count127 = 0;

    public Step() {
        super("Step", "Steps up blocks automatically.", Category.MOVEMENT);
    }

    public static void setFloat8(float f) {
        float f2 = f;
        Object var3_2 = null;
        if (MC.client3.player != null) {
            if (MC.client3.player.getAttributeInstance(EntityAttributes.STEP_HEIGHT) != null) {
                MC.client3.player.getAttributeInstance(EntityAttributes.STEP_HEIGHT).setBaseValue((double)f2);
            }
        }
    }

    @Override
    public void m709() {
        super.m709();
        Step.setFloat8(0.6f);
    }

    @EventHandler
    public void setEvent2Inner30(Event2.Event2Inner event2Inner) {
        boolean bl;
        if (Module.isSet37()) {
            return;
        }
        if (!(((Boolean)this.sneakingPause.getObj()).booleanValue() && MC.client3.player.isInSneakingPose() || ((Boolean)this.inBlockPause.getObj()).booleanValue() && ItemUtil.isSet26() || MC.client3.player.isInLava() || MC.client3.player.isTouchingWater() || ((Boolean)this.inWebPause.getObj()).booleanValue() && MC.client3.player.isClimbing() || !MC.client3.player.isOnGround() || ((Boolean)this.onlyMoving.getObj()).booleanValue() && !MathUtil.isSet7())) {
            Step.setFloat8(((Double)this.height.getObj()).floatValue());
        } else {
            Step.setFloat8(0.6f);
        }
        if (this.flag15 && this.count127 <= 0) {
            this.flag15 = false;
        }
        boolean bl2 = bl = this.mode.getObj() == Mode.NCP;
        if (this.mode.getObj() == Mode.OldNCP || bl) {
            double d = MC.client3.player.getY() - MC.client3.player.lastY;
            if (d <= 0.75 || d > (Double)this.height.getObj()) {
                return;
            }
            double[] dArray = this.m420(d);
            if (dArray != null && dArray.length > 1) {
                if (((Boolean)this.timer.getObj()).booleanValue()) {
                    this.flag15 = true;
                    this.count127 = 2;
                }
                for (double d2 : dArray) {
                    MC.client3.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.PositionAndOnGround(MC.client3.player.lastX, MC.client3.player.lastY + d2, MC.client3.player.lastZ, false, false));
                }
            }
        }
    }

    @EventHandler
    private void setEvent2Inner211(Event2.Event2Inner2 event2Inner2) {
        if (Module.isSet37()) {
            return;
        }
        --this.count127;
    }

    public double[] m420(double d) {
        double[] dArray;
        double d2 = d;
        Object var6_3 = null;
        boolean bl = this.mode.getObj() == Mode.NCP;
        if (d2 == 0.75) {
            double[] dArray2;
            if (bl) {
                double[] dArray3 = new double[3];
                dArray3[0] = 0.42;
                dArray3[1] = 0.753;
                dArray2 = dArray3;
                dArray3[2] = 0.75;
            } else {
                double[] dArray4 = new double[2];
                dArray4[0] = 0.42;
                dArray2 = dArray4;
                dArray4[1] = 0.753;
            }
            return dArray2;
        }
        if (d2 == 0.8125) {
            double[] dArray5;
            if (bl) {
                double[] dArray6 = new double[3];
                dArray6[0] = 0.39;
                dArray6[1] = 0.7;
                dArray5 = dArray6;
                dArray6[2] = 0.8125;
            } else {
                double[] dArray7 = new double[2];
                dArray7[0] = 0.39;
                dArray5 = dArray7;
                dArray7[1] = 0.7;
            }
            return dArray5;
        }
        if (d2 == 0.875) {
            double[] dArray8;
            if (bl) {
                double[] dArray9 = new double[3];
                dArray9[0] = 0.39;
                dArray9[1] = 0.7;
                dArray8 = dArray9;
                dArray9[2] = 0.875;
            } else {
                double[] dArray10 = new double[2];
                dArray10[0] = 0.39;
                dArray8 = dArray10;
                dArray10[1] = 0.7;
            }
            return dArray8;
        }
        if (d2 == 1.0) {
            double[] dArray11;
            if (bl) {
                double[] dArray12 = new double[3];
                dArray12[0] = 0.42;
                dArray12[1] = 0.753;
                dArray11 = dArray12;
                dArray12[2] = 1.0;
            } else {
                double[] dArray13 = new double[2];
                dArray13[0] = 0.42;
                dArray11 = dArray13;
                dArray13[1] = 0.753;
            }
            return dArray11;
        }
        if (d2 == 1.5) {
            return new double[]{0.42, 0.75, 1.0, 1.16, 1.23, 1.2};
        }
        if (d2 == 2.0) {
            return new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};
        }
        if (d2 == 2.5) {
            double[] dArray14 = new double[10];
            dArray14[0] = 0.425;
            dArray14[1] = 0.821;
            dArray14[2] = 0.699;
            dArray14[3] = 0.599;
            dArray14[4] = 1.022;
            dArray14[5] = 1.372;
            dArray14[6] = 1.652;
            dArray14[7] = 1.869;
            dArray14[8] = 2.019;
            dArray = dArray14;
            dArray14[9] = 1.907;
        } else {
            dArray = null;
        }
        return dArray;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      Vanilla, OldNCP, NCP;

      private Mode() {}



        private static Mode[] getModeArray19() {
            return new Mode[]{Vanilla, OldNCP, NCP};
        }
    
   }
}

