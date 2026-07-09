/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.MathHelper;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.misc.MathUtil;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoSprint
extends Module {
    public static AutoSprint INSTANCE;
    private final EnumSetting mode = (EnumSetting)this.m28(new EnumSetting("Mode", Mode.Legit));
    private final BooleanSetting inWaterPause = (BooleanSetting)this.m28(new BooleanSetting("InWaterPause", true));
    private final BooleanSetting inWebPause = (BooleanSetting)this.m28(new BooleanSetting("InWebPause", true));
    private final BooleanSetting sneakingPause = (BooleanSetting)this.m28(new BooleanSetting("SneakingPause", false));
    private final BooleanSetting blindnessPause = (BooleanSetting)this.m28(new BooleanSetting("BlindnessPause", false));
    private final BooleanSetting usingPause = (BooleanSetting)this.m28(new BooleanSetting("UsingPause", false));
    private final BooleanSetting lagPause = (BooleanSetting)this.m28(new BooleanSetting("LagPause", true));
    private boolean flag121;

    public AutoSprint() {
        super("AutoSprint", "Keeps sprint enabled while moving.", Category.MOVEMENT);
        INSTANCE = this;
    }

    public boolean isSet142() {
        Object var2_1 = null;
        return this.isSet19() && this.mode.getObj() == Mode.Rage;
    }

    @EventHandler
    private void setPacketEventInner5(PacketEvent.PacketEventInner packetEventInner) {
        if (((Boolean)this.lagPause.getObj()).booleanValue() && packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket) {
            this.flag121 = true;
        }
    }

    public boolean isSet160() {
        Object var2_1 = null;
        return this.isSet19() && this.mode.getObj() == Mode.Rotation;
    }

    @EventHandler
    private void setEvent2Inner44(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (MC.client3.player == null || MC.client3.player.input == null) {
            return;
        }
        if (this.flag121) {
            return;
        }
        if (this.mode.getObj() == Mode.Rotation && Client.mathUtil.getType2() != MathUtil.Type.CUSTOM) {
            boolean bl;
            float f = AutoSprint.m821(MC.client3.player.getYaw());
            boolean bl2 = bl = AutoSprint.isSet177() && AutoSprint.m325(f, MC.client3.player.getYaw()) > 1.0f;
            if (bl) {
                Client.mathUtil.m24(f, MC.client3.player.getPitch());
            } else if (Client.mathUtil.getType2() == MathUtil.Type.SPRINT) {
                Client.mathUtil.m844();
            }
        }
        if (!this.isSet80()) {
            MC.client3.player.setSprinting(false);
            return;
        }
        MC.client3.player.setSprinting(true);
    }

    @EventHandler
    private void setEvent2Inner223(Event2.Event2Inner2 event2Inner2) {
        this.flag121 = false;
    }

    private boolean isSet80() {
        block23: {
            block22: {
                Object var2_1 = null;
                if (MC.client3.player == null) break block22;
                if (MC.client3.player.input != null) break block23;
            }
            return false;
        }
        if (MC.client3.player.getHungerManager().getFoodLevel() <= 6) {
            if (!MC.client3.player.isCreative()) {
                return false;
            }
        }
        if (!AutoSprint.isSet177()) {
            return false;
        }
        if (MC.client3.player.hasVehicle()) {
            return false;
        }
        if (MC.client3.player.isUsingItem()) {
            if (((Boolean)this.usingPause.getObj()).booleanValue()) {
                return false;
            }
        }
        if (MC.client3.player.isInSneakingPose()) {
            if (((Boolean)this.sneakingPause.getObj()).booleanValue()) {
                return false;
            }
        }
        if (MC.client3.player.hasStatusEffect(StatusEffects.BLINDNESS)) {
            if (((Boolean)this.blindnessPause.getObj()).booleanValue()) {
                return false;
            }
        }
        if (AutoSprint.isSet18()) {
            if (((Boolean)this.inWaterPause.getObj()).booleanValue()) {
                return false;
            }
        }
        if (AutoSprint.isInWeb()) {
            if (((Boolean)this.inWebPause.getObj()).booleanValue()) {
                return false;
            }
        }
        return switch (((Mode)((Object)this.mode.getObj())).ordinal()) {
            default -> throw new MatchException(null, null);
            case 1 -> true;
            case 0 -> this.isSet133();
            case 2 -> Client.mathUtil.getType2() == MathUtil.Type.CUSTOM ? this.isSet133() : this.isSet179();
        };
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet133() {
        Object var2_1 = null;
        if (AutoSprint.isSet102()) {
            if (!(AutoSprint.getFloat20() > 0.0f)) return false;
            return true;
        }
        if (!AutoSprint.isSet73()) return false;
        if (!(AutoSprint.m325(MC.client3.player.getYaw(), AutoSprint.getFloat64()) < 40.0f)) return false;
        return true;
    }

    private boolean isSet179() {
        Object var2_1 = null;
        if (AutoSprint.isSet102()) {
            return AutoSprint.getFloat20() > 0.0f;
        }
        return AutoSprint.m325(AutoSprint.m821(MC.client3.player.getYaw()), AutoSprint.getFloat64()) < 40.0f;
    }

    public static float m821(float f) {
        float f2;
        block6: {
            block9: {
                block7: {
                    block8: {
                        block4: {
                            block5: {
                                f2 = f;
                                Object var3_2 = null;
                                if (!AutoSprint.isSet73()) break block4;
                                if (AutoSprint.isSet113()) break block4;
                                if (!AutoSprint.isSet146()) break block5;
                                if (AutoSprint.isSet76()) break block5;
                                f2 -= 45.0f;
                                if (null == null) break block6;
                            }
                            if (!AutoSprint.isSet76() || AutoSprint.isSet146()) break block6;
                            f2 += 45.0f;
                            if (null == null) break block6;
                        }
                        if (!AutoSprint.isSet113()) break block7;
                        if (AutoSprint.isSet73()) break block7;
                        f2 += 180.0f;
                        if (!AutoSprint.isSet146()) break block8;
                        if (AutoSprint.isSet76()) break block8;
                        f2 += 45.0f;
                        if (null == null) break block6;
                    }
                    if (!AutoSprint.isSet76() || AutoSprint.isSet146()) break block6;
                    f2 -= 45.0f;
                    if (null == null) break block6;
                }
                if (!AutoSprint.isSet146()) break block9;
                if (AutoSprint.isSet76()) break block9;
                f2 -= 90.0f;
                if (null == null) break block6;
            }
            if (AutoSprint.isSet76() && !AutoSprint.isSet146()) {
                f2 += 90.0f;
            }
        }
        return MathHelper.wrapDegrees((float)f2);
    }

    private static boolean isSet73() {
        return MC.client3.options.forwardKey.isPressed();
    }

    private static boolean isSet113() {
        return MC.client3.options.backKey.isPressed();
    }

    private static boolean isSet146() {
        return MC.client3.options.leftKey.isPressed();
    }

    private static boolean isSet76() {
        return MC.client3.options.rightKey.isPressed();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isSet177() {
        Object var1 = null;
        if (AutoSprint.isSet73()) return true;
        if (AutoSprint.isSet113()) return true;
        if (AutoSprint.isSet146()) return true;
        if (!AutoSprint.isSet76()) return false;
        return true;
    }

    private static float getFloat20() {
        block3: {
            block2: {
                Object var1 = null;
                if (MC.client3.player == null) break block2;
                if (MC.client3.player.input != null) break block3;
            }
            return 0.0f;
        }
        return MC.client3.player.input.getMovementInput().y;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isInWeb() {
        Object var1 = null;
        if (MC.client3.player == null) return false;
        if (MC.client3.world == null) return false;
        if (!MC.client3.world.getStatesInBox(MC.client3.player.getBoundingBox()).anyMatch(blockState -> blockState.isOf(Blocks.COBWEB))) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isSet18() {
        Object var1 = null;
        if (MC.client3.player == null) return false;
        if (!MC.client3.player.isTouchingWater()) return false;
        return true;
    }

    /*
     * Unable to fully structure code
     */
    private static boolean isSet102() {
        Object var1 = null;
        if (!Client.mathUtil.isSet111()) {
            return false;
        }
        boolean bl = ClientSetting.INSTANCE != null && ((Boolean)ClientSetting.INSTANCE.movementSync.getObj()).booleanValue();
        boolean bl2 = INSTANCE != null && INSTANCE.isSet160();
        return bl || bl2;
    }

    private static float getFloat64() {
        MathUtil mathUtil = Client.mathUtil;
        Object var1_1 = null;
        return mathUtil.isSet111() ? mathUtil.getFloat55() : MC.client3.player.getYaw();
    }

    private static float m325(float f, float f2) {
        float f3 = f;
        float f4 = f2;
        return Math.abs(MathHelper.wrapDegrees((float)(f3 - f4)));
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      Legit, Rage, Rotation;

      private Mode() {}



        private static Mode[] getModeArray7() {
            return new Mode[]{Legit, Rage, Rotation};
        }
    
   }
}

