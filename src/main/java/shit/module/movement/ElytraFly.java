/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EquipmentSlot
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket
 *  net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket$Mode
 *  net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket
 *  net.minecraft.screen.slot.SlotActionType
 *  net.minecraft.util.Hand
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.TravelHeadEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ElytraFly
extends Module {
    public static ElytraFly INSTANCE;
    private int count105;
    public final EnumSetting mode;
    private final NumberSetting fireWorkDelay;
    private final NumberSetting packetDelay;
    private final BooleanSetting noAnimation;
    private final BooleanSetting setFlag;
    private final NumberSetting defaultPitch;
    private final NumberSetting upPitch;
    private final NumberSetting downPitch;
    private final BooleanSetting controlRotation;
    private final BooleanSetting airStop;
    private final NumberSetting fireWorkDelay2;
    private final BooleanSetting firework;
    private final NumberSetting upFactor;
    private final NumberSetting fallSpeed;
    private final NumberSetting speed;
    private final BooleanSetting slownessModifier;
    private final NumberSetting slownessSpeed;
    private final BooleanSetting slownessTimer;
    private final NumberSetting timerSpeed;
    private final BooleanSetting speedLimit;
    private final NumberSetting maxSpeed;
    private final BooleanSetting noDrag;
    private final NumberSetting downSpeed;
    private final BooleanSetting autoMend;
    private final NumberSetting mendThreshold;
    private final NumberSetting mendDelay;
    private long time72;
    private int count197;
    private float value110;
    private float value153;

        public ElytraFly() {
        super("ElytraFly", "Enhances elytra movement with Grim/NCP bypass.", Category.MOVEMENT);
        this.count105 = 0;
        this.mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.GRIM));
        this.fireWorkDelay = (NumberSetting)this.registerSetting(new NumberSetting("FireWorkDelay", 1500.0, 200.0, 10000.0, 50.0));
        this.packetDelay = (NumberSetting)this.registerSetting(new NumberSetting("PacketDelay", 0.0, 0.0, 20.0, 1.0));
        this.noAnimation = (BooleanSetting)this.registerSetting(new BooleanSetting("NoAnimation", false));
        this.setFlag = (BooleanSetting)this.registerSetting(new BooleanSetting("SetFlag", false));
        this.defaultPitch = (NumberSetting)this.registerSetting(new NumberSetting("DefaultPitch", -10.0, -90.0, 90.0, 1.0));
        this.upPitch = (NumberSetting)this.registerSetting(new NumberSetting("UpPitch", -45.0, -90.0, 90.0, 1.0));
        this.downPitch = (NumberSetting)this.registerSetting(new NumberSetting("DownPitch", 45.0, -90.0, 90.0, 1.0));
        this.controlRotation = (BooleanSetting)this.registerSetting(new BooleanSetting("ControlRotation", false));
        this.airStop = (BooleanSetting)this.registerSetting(new BooleanSetting("AirStop", false));
        this.fireWorkDelay2 = (NumberSetting)this.registerSetting(new NumberSetting("FireWorkDelay", 1500.0, 200.0, 10000.0, 50.0));
        this.firework = (BooleanSetting)this.registerSetting(new BooleanSetting("Firework", true));
        this.upFactor = (NumberSetting)this.registerSetting(new NumberSetting("UpFactor", 1.0, 0.0, 10.0, 0.1));
        this.fallSpeed = (NumberSetting)this.registerSetting(new NumberSetting("FallSpeed", 1.0, 0.0, 10.0, 0.1));
        this.speed = (NumberSetting)this.registerSetting(new NumberSetting("Speed", 1.0, 0.1, 10.0, 0.1));
        this.slownessModifier = (BooleanSetting)this.registerSetting(new BooleanSetting("SlownessModifier", false));
        this.slownessSpeed = (NumberSetting)this.registerSetting(new NumberSetting("SlownessSpeed", 1.0, 0.1, 10.0, 0.1));
        this.slownessTimer = (BooleanSetting)this.registerSetting(new BooleanSetting("SlownessTimer", false));
        this.timerSpeed = (NumberSetting)this.registerSetting(new NumberSetting("TimerSpeed", 1.5, 0.1, 10.0, 0.1));
        this.speedLimit = (BooleanSetting)this.registerSetting(new BooleanSetting("SpeedLimit", true));
        this.maxSpeed = (NumberSetting)this.registerSetting(new NumberSetting("MaxSpeed", 2.5, 0.1, 10.0, 0.1));
        this.noDrag = (BooleanSetting)this.registerSetting(new BooleanSetting("NoDrag", false));
        this.downSpeed = (NumberSetting)this.registerSetting(new NumberSetting("DownSpeed", 1.0, 0.1, 10.0, 0.1));
        this.autoMend = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoMend", false));
        this.mendThreshold = (NumberSetting)this.registerSetting(new NumberSetting("MendThreshold", 30.0, 10.0, 90.0, 1.0));
        this.mendDelay = (NumberSetting)this.registerSetting(new NumberSetting("MendDelay", 3.0, 0.0, 10.0, 1.0));
        this.time72 = 0L;
        this.count197 = 0;
        this.value110 = 0.0f;
        this.value153 = 0.0f;
    }

    @Override
    public void onEnable() {
        Object var2_1 = null;
        if (MC.mc.player == null) {
            return;
        }
        this.time72 = 0L;
        this.count197 = 0;
    }

    @Override
    public void onDisable() {
        Client.mathUtil.resetRotationSilent();
        Client.renderUtil3.restoreSlot();
    }

    private boolean isSet137() {
        Object var2_1 = null;
        if (MC.mc.player == null) {
            return false;
        }
        return MC.mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA);
    }

    private double getDouble21() {
        Object var2_1 = null;
        if (((Boolean)this.slownessModifier.getValue()).booleanValue()) {
            if (MC.mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                return (Double)this.slownessSpeed.getValue();
            }
        }
        return (Double)this.speed.getValue();
    }

    @EventHandler
    public void setEvent2Inner47(Event2.Event2Inner event2Inner) {
        if (MC.mc.player == null || MC.mc.world == null) {
            return;
        }
        if (this.mode.getValue() == Mode.GRIM) {
            this.m860();
        } else if (this.mode.getValue() == Mode.NCP) {
            this.m566();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void m860() {
        block13: {
            block12: {
                Object var2_1 = null;
                if (MC.mc.player.isOnGround()) {
                    this.count197 = 0;
                    return;
                }
                if (!this.isSet137()) {
                    if (this.m352(Items.ELYTRA) == -1) return;
                }
                boolean bl = true;
                boolean bl2 = bl;
                if (!bl2) {
                    return;
                }
                ++this.count197;
                if (this.count197 <= this.packetDelay.getInt()) {
                    return;
                }
                this.count197 = 0;
                if (this.isSet137()) break block12;
                int n = this.m352(Items.ELYTRA);
                if (n != -1) {
                    MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, n, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, 6, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    this.m279();
                    if (this.isSet17()) {
                        this.m579();
                    }
                    MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, 6, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, n, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                }
                if (null == null) break block13;
            }
            this.m279();
            if (this.isSet17()) {
                this.m579();
            }
        }
        if (((Boolean)this.controlRotation.getValue()).booleanValue()) {
            if (this.isSet35()) {
                this.m290();
                Client.mathUtil.setTargetRotation(this.value110, this.value153);
            }
        }
        if ((Boolean)this.airStop.getValue() == false) return;
        if (!this.isSet35()) return;
        if (this.isSet17()) return;
        if (MC.mc.options.sneakKey.isPressed()) return;
        MC.mc.player.setVelocity(0.0, 0.0, 0.0);
    }

    /*
     * Unable to fully structure code
     */
    private void m290() {
        boolean forward = MC.mc.options.forwardKey.isPressed();
        boolean back = MC.mc.options.backKey.isPressed();
        boolean left = MC.mc.options.leftKey.isPressed();
        boolean right = MC.mc.options.rightKey.isPressed();
        boolean jump = MC.mc.options.jumpKey.isPressed();
        boolean sneak = MC.mc.options.sneakKey.isPressed();
        boolean moving = forward || back || left || right;
        boolean vertical = jump || sneak;
        if (moving) {
            this.value110 = this.m265(MC.mc.player.getYaw());
        } else {
            this.value110 = MC.mc.player.getYaw();
        }
        if (vertical) {
            if (moving) {
                if (jump && !sneak) {
                    this.value153 = ((Double)this.upPitch.getValue()).floatValue();
                } else if (sneak && !jump) {
                    this.value153 = ((Double)this.downPitch.getValue()).floatValue();
                } else {
                    this.value153 = ((Double)this.defaultPitch.getValue()).floatValue();
                }
            } else if (jump && !sneak) {
                this.value153 = -90.0f;
            } else if (sneak && !jump) {
                this.value153 = 90.0f;
            } else {
                this.value153 = ((Double)this.defaultPitch.getValue()).floatValue();
            }
        } else {
            this.value153 = ((Double)this.defaultPitch.getValue()).floatValue();
        }
    }

    private void m566() {
        Object var2_1 = null;
        if (!this.isSet137()) {
            return;
        }
        boolean bl = MC.mc.options.sneakKey.isPressed();
        if (!this.isSet35()) {
            if (!MC.mc.player.isOnGround()) {
                if (MC.mc.player.getVelocity().y < -0.1) {
                    if (MC.mc.player.fallDistance > (double)0.2f) {
                        if (!bl) {
                            MC.mc.getNetworkHandler().sendPacket((Packet)new ClientCommandC2SPacket((Entity)MC.mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
                            MC.mc.player.startGliding();
                        }
                    }
                }
            }
        }
        if (this.isSet35()) {
            if (((Boolean)this.firework.getValue()).booleanValue()) {
                if (this.isSet71()) {
                    if (this.isSet14()) {
                        this.m713();
                    }
                }
            }
        }
        Client.mathUtil.resetRotation();
    }

    @EventHandler
    public void setTravelHeadEvent2(TravelHeadEvent travelHeadEvent) {
        boolean bl;
        if (MC.mc.player == null || !this.isEnabled() || this.mode.getValue() != Mode.NCP) {
            return;
        }
        if (!this.isSet137() || !this.isSet35()) {
            return;
        }
        boolean bl2 = MC.mc.options.sneakKey.isPressed();
        boolean bl3 = MC.mc.options.jumpKey.isPressed();
        double d = travelHeadEvent.getDouble3();
        double d2 = travelHeadEvent.getDouble5();
        double d3 = travelHeadEvent.getDouble11();
        if (!MC.mc.player.isOnGround() && d2 < -0.05) {
            d2 = 0.0;
        }
        double d4 = this.getDouble21();
        boolean bl4 = bl = (Boolean)this.firework.getValue() != false && this.isSet71();
        if (bl) {
            if (bl2 && bl3) {
                d2 = 0.0;
            } else if (bl2) {
                d2 = -((Double)this.downSpeed.getValue()).doubleValue();
            } else if (bl3) {
                d2 = (Double)this.upFactor.getValue();
            } else {
                if (MC.mc.player.isOnGround() || d2 < -0.05) {
                }
                d2 = -3.0E-11 * (Double)this.fallSpeed.getValue();
            }
            double[] var13_9 = this.m538(d4);
            d = (double)var13_9[0];
            d3 = (double)var13_9[1];
        } else {
            Vec3d vec3d = this.getVec3d3();
            double d5 = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z);
            double d6 = Math.sqrt(d * d + d3 * d3);
            if (bl2) {
                d2 = -((Double)this.downSpeed.getValue()).doubleValue();
            } else if (!bl3) {
                d2 = -3.0E-11 * (Double)this.fallSpeed.getValue();
            }
            if (bl3) {
                double d7 = (Double)this.upFactor.getValue() / 10.0;
                if (d6 > d7) {
                    double d8 = d6 * 0.01325;
                    d2 += d8 * 3.2;
                    d -= vec3d.x * d8 / (d5 > 0.0 ? d5 : 1.0);
                    d3 -= vec3d.z * d8 / (d5 > 0.0 ? d5 : 1.0);
                } else {
                    double[] dArray = this.m538(d4);
                    d = dArray[0];
                    d3 = dArray[1];
                }
            }
            if (d5 > 0.0) {
                d += (vec3d.x / d5 * d6 - d) * 0.1;
                d3 += (vec3d.z / d5 * d6 - d3) * 0.1;
            }
            if (!bl3) {
                double[] dArray = this.m538(d4);
                d = dArray[0];
                d3 = dArray[1];
            }
        }
        if (!((Boolean)this.noDrag.getValue()).booleanValue()) {
            d2 *= 0.99;
            d *= 0.98;
            d3 *= 0.99;
        }
        double d9 = Math.sqrt(d * d + d3 * d3);
        if (((Boolean)this.speedLimit.getValue()).booleanValue() && d9 > (Double)this.maxSpeed.getValue()) {
            double d10 = (Double)this.maxSpeed.getValue() / d9;
            d *= d10;
            d3 *= d10;
        }
        travelHeadEvent.setDouble5(d);
        travelHeadEvent.setDouble3(d2);
        travelHeadEvent.setDouble6(d3);
        travelHeadEvent.cancel();
    }

    private Vec3d getVec3d3() {
        float f = MC.mc.player.getYaw();
        float f2 = -f * ((float)Math.PI / 180);
        float f3 = MathHelper.cos((double)f2);
        float f4 = MathHelper.sin((double)f2);
        float f5 = MathHelper.cos((double)0.0);
        float f6 = MathHelper.sin((double)0.0);
        return new Vec3d((double)(f4 * f5), (double)(-f6), (double)(f3 * f5));
    }

    private void m713() {
        Object var2_1 = null;
        if (MC.mc.player.isUsingItem() || System.currentTimeMillis() - this.time72 < ((Double)this.fireWorkDelay2.getValue()).longValue()) {
            return;
        }
        this.useFirework();
    }

    private void m579() {
        Object var2_1 = null;
        if (MC.mc.player.isUsingItem() || System.currentTimeMillis() - this.time72 < ((Double)this.fireWorkDelay.getValue()).longValue()) {
            return;
        }
        this.useFirework();
    }

    private void useFirework() {
        boolean bl = Client.renderUtil3.switchToItem((java.util.function.Predicate<net.minecraft.item.ItemStack>)(itemStack -> itemStack.isOf(Items.FIREWORK_ROCKET)), (Object)ClientSetting.SwitchMode.NORMAL);
        Object var2_2 = null;
        if (bl) {
            MC.mc.getNetworkHandler().sendPacket((Packet)new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, MC.mc.player.getYaw(), MC.mc.player.getPitch()));
            Client.renderUtil3.restoreSlot();
            this.time72 = System.currentTimeMillis();
        }
    }

    private void m279() {
        Object var2_1 = null;
        MC.mc.getNetworkHandler().sendPacket((Packet)new ClientCommandC2SPacket((Entity)MC.mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
        if (((Boolean)this.setFlag.getValue()).booleanValue()) {
            MC.mc.player.startGliding();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet17() {
        Object var2_1 = null;
        if (MC.mc.options.forwardKey.isPressed()) return true;
        if (MC.mc.options.backKey.isPressed()) return true;
        if (MC.mc.options.leftKey.isPressed()) return true;
        if (MC.mc.options.rightKey.isPressed()) return true;
        if (!MC.mc.options.jumpKey.isPressed()) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet14() {
        Object var2_1 = null;
        if (MC.mc.options.forwardKey.isPressed()) return true;
        if (MC.mc.options.backKey.isPressed()) return true;
        if (MC.mc.options.leftKey.isPressed()) return true;
        if (!MC.mc.options.rightKey.isPressed()) return false;
        return true;
    }

    private boolean isSet71() {
        Object var2_1 = null;
        if (MC.mc.player == null) {
            return false;
        }
        for (int i = 0; i < 36; ++i) {
            if (!MC.mc.player.getInventory().getStack(i).isOf(Items.FIREWORK_ROCKET)) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet35() {
        Object var2_1 = null;
        if (this.mode.getValue() == Mode.GRIM) {
            if (MC.mc.player == null) return false;
            if (MC.mc.player.isGliding()) return true;
            if (!this.isSet137()) return false;
            if (MC.mc.player.isOnGround()) return false;
            return true;
        }
        if (MC.mc.player == null) return false;
        if (!MC.mc.player.isGliding()) return false;
        return true;
    }

    private float m265(float f) {
        float f2;
        block6: {
            block9: {
                block7: {
                    block8: {
                        block4: {
                            block5: {
                                f2 = f;
                                Object var4_3 = null;
                                if (!MC.mc.options.forwardKey.isPressed()) break block4;
                                if (MC.mc.options.backKey.isPressed()) break block4;
                                if (!MC.mc.options.leftKey.isPressed()) break block5;
                                if (MC.mc.options.rightKey.isPressed()) break block5;
                                f2 -= 45.0f;
                                if (null == null) break block6;
                            }
                            if (!MC.mc.options.rightKey.isPressed() || MC.mc.options.leftKey.isPressed()) break block6;
                            f2 += 45.0f;
                            if (null == null) break block6;
                        }
                        if (!MC.mc.options.backKey.isPressed()) break block7;
                        if (MC.mc.options.forwardKey.isPressed()) break block7;
                        f2 += 180.0f;
                        if (!MC.mc.options.leftKey.isPressed()) break block8;
                        if (MC.mc.options.rightKey.isPressed()) break block8;
                        f2 += 45.0f;
                        if (null == null) break block6;
                    }
                    if (!MC.mc.options.rightKey.isPressed() || MC.mc.options.leftKey.isPressed()) break block6;
                    f2 -= 45.0f;
                    if (null == null) break block6;
                }
                if (!MC.mc.options.leftKey.isPressed()) break block9;
                if (MC.mc.options.rightKey.isPressed()) break block9;
                f2 -= 90.0f;
                if (null == null) break block6;
            }
            if (MC.mc.options.rightKey.isPressed() && !MC.mc.options.leftKey.isPressed()) {
                f2 += 90.0f;
            }
        }
        return MathHelper.wrapDegrees((float)f2);
    }

    private double[] m538(double d) {
        float f;
        float f2;
        float f3;
        double d2;
        block10: {
            block13: {
                block12: {
                    block11: {
                        d2 = d;
                        Object var6_3 = null;
                        f3 = MC.mc.options.forwardKey.isPressed() ? 1.0f : (MC.mc.options.backKey.isPressed() ? -1.0f : 0.0f);
                        f2 = MC.mc.options.leftKey.isPressed() ? 1.0f : (MC.mc.options.rightKey.isPressed() ? -1.0f : 0.0f);
                        f = MC.mc.player.getYaw();
                        if (f3 == 0.0f) break block10;
                        if (!(f2 > 0.0f)) break block11;
                        f += (float)(f3 > 0.0f ? -45 : 45);
                        if (null == null) break block12;
                    }
                    if (f2 < 0.0f) {
                        f += (float)(f3 > 0.0f ? 45 : -45);
                    }
                }
                f2 = 0.0f;
                if (!(f3 > 0.0f)) break block13;
                f3 = 1.0f;
                if (null == null) break block10;
            }
            if (f3 < 0.0f) {
                f3 = -1.0f;
            }
        }
        double d3 = Math.sin(Math.toRadians(f + 90.0f));
        double d4 = Math.cos(Math.toRadians(f + 90.0f));
        double d5 = (double)f3 * d2 * d4 + (double)f2 * d2 * d3;
        double d6 = (double)f3 * d2 * d3 - (double)f2 * d2 * d4;
        if (f3 == 0.0f) {
            if (f2 == 0.0f) {
                d5 = 0.0;
                d6 = 0.0;
            }
        }
        return new double[]{d5, d6};
    }

    private int m352(Object object) {
        Item item = (Item)object;
        Object var4_4 = null;
        for (int i = 9; i < 36; ++i) {
            if (!MC.mc.player.getInventory().getStack(i).isOf(item)) continue;
            return i;
        }
        return -1;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
        GRIM,
        NCP;


        private static Mode[] getModeArray() {
            return new Mode[]{GRIM, NCP};
        }
    }
}
