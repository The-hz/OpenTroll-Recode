/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.MoveEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class BlockStrafe
extends Module {
    public static BlockStrafe INSTANCE;
    private final NumberSetting speed = (NumberSetting)this.registerSetting(new NumberSetting("Speed", 10.0, 0.0, 20.0, 1.0));
    private final NumberSetting anchorSpeed = (NumberSetting)this.registerSetting(new NumberSetting("AnchorSpeed", 3.0, 0.0, 20.0, 1.0));
    private final BooleanSetting antiSlowFalling = (BooleanSetting)this.registerSetting(new BooleanSetting("AntiSlowFalling", false));
    private final EnumSetting aSFMode = (EnumSetting)this.registerSetting(new EnumSetting("ASF Mode", EMode.NCPAlign));
    private final NumberSetting aSFPushUp = (NumberSetting)this.registerSetting(new NumberSetting("ASF PushUp", 0.05, 0.01, 0.2, 0.001, 0.001, () -> {
        Object var1_1 = null;
        return (Boolean)this.antiSlowFalling.getValue() != false && this.aSFMode.getValue() == EMode.SendOffsetPacket;
    }, null, "", false));
    private final NumberSetting aSFPushDown = (NumberSetting)this.registerSetting(new NumberSetting("ASF PushDown", 0.05, 0.01, 0.2, 0.001, 0.001, () -> {
        Object var1_1 = null;
        return (Boolean)this.antiSlowFalling.getValue() != false && this.aSFMode.getValue() == EMode.SendOffsetPacket;
    }, null, "", false));
    private final NumberSetting aSFPushDelay = (NumberSetting)this.registerSetting(new NumberSetting("ASF PushDelay", 10.0, 1.0, 50.0, 1.0, 1.0, () -> {
        Object var1_1 = null;
        return (Boolean)this.antiSlowFalling.getValue() != false && this.aSFMode.getValue() == EMode.SendOffsetPacket;
    }, null, "", false));
    private final BooleanSetting aSFExtraPackets = (BooleanSetting)this.registerSetting(new BooleanSetting("ASF ExtraPackets", false, () -> {
        Object var1_1 = null;
        return (Boolean)this.antiSlowFalling.getValue() != false && this.aSFMode.getValue() == EMode.SendOffsetPacket;
    }, null, "", false));
    private final NumberSetting aSFAlignY = (NumberSetting)this.registerSetting(new NumberSetting("ASF AlignY", 0.015625, 0.001, 0.1, 1.0E-6, 1.0E-6, () -> {
        Object var1_1 = null;
        return (Boolean)this.antiSlowFalling.getValue() != false && this.aSFMode.getValue() == EMode.NCPAlign;
    }, null, "", false));
    private final BooleanSetting aSFAutoJump = (BooleanSetting)this.registerSetting(new BooleanSetting("ASF AutoJump", true, () -> {
        Object var1_1 = null;
        return (Boolean)this.antiSlowFalling.getValue() != false && this.aSFMode.getValue() == EMode.NCPAlign;
    }, null, "", false));
    private final NumberSetting aSFJumpHeight = (NumberSetting)this.registerSetting(new NumberSetting("ASF JumpHeight", 0.2, 0.01, 0.42, 0.01, 0.01, () -> {
        Object var1_1 = null;
        if ((Boolean)this.antiSlowFalling.getValue() == false) return false;
        if (this.aSFMode.getValue() != EMode.NCPAlign) return false;
        if ((Boolean)this.aSFAutoJump.getValue() == false) return false;
        return true;
    }, null, "", false));
    private final BooleanSetting aSFAutoSneak = (BooleanSetting)this.registerSetting(new BooleanSetting("ASF AutoSneak", false, () -> (Boolean)this.antiSlowFalling.getValue(), null, "", false));
    private boolean flag171 = false;
    private boolean flag41 = false;
    private int count179 = 0;
    private boolean flag45 = false;
    private Random random9 = new Random();

    public BlockStrafe() {
        super("BlockStrafe", "Modifies movement speed inside blocks.", Category.MOVEMENT);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.flag171 = false;
        this.flag41 = false;
        this.flag45 = false;
        this.count179 = 0;
        this.random9 = new Random();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Object var2_1 = null;
        if (this.flag171) {
            this.m771();
        }
        if (this.flag41) {
            this.setFlag11(false);
        }
    }

    @EventHandler
    public void setMoveEvent3(MoveEvent moveEvent) {
        if (Module.isNotInGame()) {
            return;
        }
        if (ItemUtil.isInGame()) {
            double d = (Double)this.speed.getValue();
            double d2 = 0.002873 * d;
            double d3 = MC.mc.player.forwardSpeed;
            double d4 = MC.mc.player.sidewaysSpeed;
            double d5 = MC.mc.player.getYaw();
            if (d3 == 0.0 && d4 == 0.0) {
                moveEvent.setDouble2(0.0);
                moveEvent.setDouble(0.0);
            } else {
                if (d3 != 0.0 && d4 != 0.0) {
                    d3 *= Math.sin(0.7853981633974483);
                    d4 *= Math.cos(0.7853981633974483);
                }
                moveEvent.setDouble2(d3 * d2 * -Math.sin(Math.toRadians(d5)) + d4 * d2 * Math.cos(Math.toRadians(d5)));
                moveEvent.setDouble(d3 * d2 * Math.cos(Math.toRadians(d5)) - d4 * d2 * -Math.sin(Math.toRadians(d5)));
            }
        }
    }

    @EventHandler
    public void setEvent2Inner12(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame() || !((Boolean)this.antiSlowFalling.getValue()).booleanValue()) {
            return;
        }
        boolean bl = MC.mc.player.hasStatusEffect(StatusEffects.SLOW_FALLING);
        boolean bl2 = ItemUtil.isInGame();
        if (bl && bl2) {
            this.m801();
        } else {
            this.m629();
        }
        if (this.flag41 && !MC.mc.options.sneakKey.isPressed()) {
            MC.mc.options.sneakKey.setPressed(true);
        }
    }

    private void m801() {
        Object var2_1 = null;
        if (!this.flag171) {
            this.flag171 = true;
            this.count179 = 0;
            this.flag45 = false;
            if (((Boolean)this.aSFAutoSneak.getValue()).booleanValue()) {
                this.setFlag11(true);
            }
            if (this.aSFMode.getValue() == EMode.SendOffsetPacket) {
                this.m771();
                this.m451();
                this.flag45 = true;
            }
        }
        switch (((EMode)((Object)this.aSFMode.getValue())).ordinal()) {
            case 0: {
                if (!this.flag45) break;
                int n = this.count179;
                this.count179 = n + 1;
                if (n < ((Double)this.aSFPushDelay.getValue()).intValue()) break;
                this.m451();
                this.count179 = 0;
                if (null == null) break;
            }
            case 1: {
                this.m1000();
                break;
            }
        }
    }

    private void m629() {
        Object var2_1 = null;
        if (this.flag171) {
            this.m771();
            if (((Boolean)this.aSFAutoSneak.getValue()).booleanValue()) {
                if (this.flag41) {
                    this.setFlag11(false);
                }
            }
            this.flag171 = false;
            this.flag45 = false;
        }
        this.count179 = 0;
    }

    private void m1000() {
        block4: {
            Object var2_1 = null;
            if (MC.mc.player == null) {
                return;
            }
            if (((Boolean)this.aSFAutoJump.getValue()).booleanValue()) {
                if (this.isSet65()) {
                    if (MC.mc.player.isOnGround()) {
                        double d = (Double)this.aSFJumpHeight.getValue();
                        MC.mc.player.setVelocity(MC.mc.player.getVelocity().x, d, MC.mc.player.getVelocity().z);
                        return;
                    }
                }
            }
            double d = MC.mc.player.getY();
            double d2 = (Double)this.aSFAlignY.getValue();
            double d3 = Math.floor(d / d2) * d2;
            double d4 = d - d3;
            if (!(d4 > d2 * 0.6) || !(d4 < 0.05)) break block4;
            MC.mc.player.updatePosition(MC.mc.player.getX(), d3, MC.mc.player.getZ());
            MC.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.PositionAndOnGround(MC.mc.player.getX(), d3, MC.mc.player.getZ(), MC.mc.player.isOnGround(), false));
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet65() {
        Object var2_1 = null;
        if (MC.mc.options.forwardKey.isPressed()) return true;
        if (MC.mc.options.backKey.isPressed()) return true;
        if (MC.mc.options.leftKey.isPressed()) return true;
        if (!MC.mc.options.rightKey.isPressed()) return false;
        return true;
    }

    private void m451() {
        Object var2_1 = null;
        if (MC.mc.player == null) {
            return;
        }
        double d = MC.mc.player.getX();
        double d2 = MC.mc.player.getY();
        double d3 = MC.mc.player.getZ();
        float f = MC.mc.player.getYaw();
        float f2 = MC.mc.player.getPitch();
        double d4 = (Double)this.aSFPushUp.getValue();
        double d5 = (Double)this.aSFPushDown.getValue();
        this.m613(d, d2, d3, d4, d5);
        if (((Boolean)this.aSFExtraPackets.getValue()).booleanValue()) {
            this.m1057(d, d2, d3, d4, d5, f, f2);
        }
        this.m708(d, d2 - d5 * 0.2, d3, true);
    }

    private void m613(double d, double d2, double d3, double d4, double d5) {
        double d6 = d;
        double d7 = d2;
        double d8 = d3;
        double d9 = d4;
        double d10 = d5;
        this.m708(d6, d7 + d9, d8, false);
        Object var22_11 = null;
        if (this.random9.nextBoolean()) {
            if (MC.mc.player != null) {
                MC.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.LookAndOnGround(MC.mc.player.getYaw(), MC.mc.player.getPitch(), false, false));
            }
        }
        this.m708(d6, d7 - d10, d8, true);
    }

    private void m1057(double d, double d2, double d3, double d4, double d5, float f, float f2) {
        double d6 = d;
        double d7 = d2;
        double d8 = d3;
        double d9 = d4;
        double d10 = d5;
        float f3 = f;
        float f4 = f2;
        int n = this.random9.nextInt(3);
        Object var26_16 = null;
        for (int i = 0; i < n; ++i) {
            double d11 = (this.random9.nextDouble() - 0.5) * d9 * 0.3;
            double d12 = (this.random9.nextDouble() - 0.5) * d9 * 0.3;
            double d13 = this.random9.nextBoolean() ? this.random9.nextDouble() * d9 * 0.5 : -this.random9.nextDouble() * d10 * 0.3;
            boolean bl = this.random9.nextDouble() < 0.3;
            this.m708(d6 + d11, d7 + d13, d8 + d12, bl);
            if (!(this.random9.nextFloat() < 0.4f)) continue;
            if (MC.mc.player == null) continue;
            MC.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.LookAndOnGround(f3 + (this.random9.nextFloat() - 0.5f) * 10.0f, f4 + (this.random9.nextFloat() - 0.5f) * 10.0f, bl, false));
            if (null == null) continue;
        }
    }

    private void m708(double d, double d2, double d3, boolean bl) {
        double d4 = d;
        double d5 = d2;
        double d6 = d3;
        boolean bl2 = bl;
        Object var16_9 = null;
        if (MC.mc.player == null) {
            return;
        }
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.PositionAndOnGround(d4, d5, d6, bl2, false));
    }

    private void m771() {
        Object var2_1 = null;
        if (MC.mc.player == null) {
            return;
        }
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.PositionAndOnGround(MC.mc.player.getX(), MC.mc.player.getY(), MC.mc.player.getZ(), MC.mc.player.isOnGround(), false));
    }

    private void setFlag11(boolean bl) {
        boolean bl2 = bl;
        Object var4_3 = null;
        if (MC.mc.options == null) {
            return;
        }
        MC.mc.options.sneakKey.setPressed(bl2);
        this.flag41 = bl2;
    }

    @Override
    public String getInfo() {
        Object var2_1 = null;
        if (((Boolean)this.antiSlowFalling.getValue()).booleanValue()) {
            if (this.flag171) {
                return "ASF:" + ((EMode)((Object)this.aSFMode.getValue())).toString();
            }
        }
        return null;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode  {
        SendOffsetPacket, NCPAlign;

        private final String text2676;

        

        

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private EMode() {
            this.text2676 = name();
        }

        public String toString() {
            return this.text2676;
        }

        

        /*
         * Unable to fully structure code
         */
        
    }
}

