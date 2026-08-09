/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.block.BlockState
 *  net.minecraft.client.network.ClientPlayerEntity
 *  net.minecraft.enchantment.Enchantments
 *  net.minecraft.entity.attribute.EntityAttributes
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket$Action
 *  net.minecraft.registry.entry.RegistryEntry
 *  net.minecraft.util.Hand
 *  net.minecraft.util.hit.BlockHitResult
 *  net.minecraft.util.hit.HitResult
 *  net.minecraft.util.hit.HitResult$Type
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Box
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.BlockView
 *  org.joml.Matrix4f
 */
package shit.module.player;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.text.DecimalFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;
import org.joml.Matrix4f;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.event.RenderLevelEvent;
import shit.event.StartAttackEvent;
import shit.misc.Helper7;
import shit.misc.MathUtil;
import shit.misc.Timer;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.module.player.AutoArmor;
import shit.render.EspRenderLayers;
import shit.render.LineRenderer2;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.type.EaseMode;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class SpeedMine
extends Module {
    public static SpeedMine INSTANCE;
    public final NumberSetting range;
    private final NumberSetting damage;
    private final NumberSetting stopDelay;
    private final NumberSetting startDelay;
    private final BooleanSetting instant;
    private final BooleanSetting wait;
    private final BooleanSetting swing;
    private final BooleanSetting endSwing;
    private final BooleanSetting onlyGround;
    private final BooleanSetting farCancel;
    private final BooleanSetting eatPause;
    private final EnumSetting switchMode;
    public final BooleanSetting doubleBreak;
    public final BooleanSetting booleanSetting6;
    private final NumberSetting doubleDelay;
    private final NumberSetting start;
    private final NumberSetting timeOut;
    private final BooleanSetting debugLog;
    private final BooleanSetting startRotate;
    private final BooleanSetting endRotate;
    private final BooleanSetting doubleStartRotate;
    private final BooleanSetting doubleEndRotate;
    private final NumberSetting rotateMs;
    private final EnumSetting rotateMode;
    private final EnumSetting animation;
    private final EnumSetting ease;
    private final EnumSetting fadeEase;
    private final BooleanSetting box;
    private final BooleanSetting outline;
    private final BooleanSetting through;
    private final ColorSetting startFill;
    private final ColorSetting startOutline;
    private final ColorSetting endFill;
    private final ColorSetting endOutline;
    private final ColorSetting doubleFill;
    private final ColorSetting doubleOutline;
    public static double value189;
    public static boolean flag95;
    public static boolean flag73;
    public static BlockPos blockPos7;
    BlockPos blockPos12;
    Direction direction8;
    Direction direction7;
    boolean flag98;
    int count133;
    double value120;
    double value113;
    boolean flag172;
    public boolean flag180;
    public boolean flag128;
    public long time10;
    private final Helper7 helper729;
    private boolean flag24;
    private final Helper7 helper730;
    private boolean flag168;
    private final Helper7 helper715;
    private boolean flag131;
    private final Helper7 helper736;
    private boolean flag91;
    private BlockPos blockPos14;
    private Direction direction6;
    private BlockPos blockPos9;
    private Direction direction5;
    private final Helper7 helper733;
    private final Helper7 helper740;
    private final Helper7 helper716;
    private final Helper7 helper727;
    private final Timer timer4;
    private final Timer timer2;
    private final DecimalFormat decimalFormat2;

        public SpeedMine() {
        super("SpeedMine", "Sends mine packets for faster block breaking.", Category.PLAYER);
        this.range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 6.0, 3.0, 10.0, 0.1));
        this.damage = (NumberSetting)this.registerSetting(new NumberSetting("Damage", 0.7, 0.1, 2.0, 0.01));
        this.stopDelay = (NumberSetting)this.registerSetting(new NumberSetting("StopDelay", 50.0, 0.0, 500.0, 1.0));
        this.startDelay = (NumberSetting)this.registerSetting(new NumberSetting("StartDelay", 200.0, 0.0, 500.0, 1.0));
        this.instant = (BooleanSetting)this.registerSetting(new BooleanSetting("Instant", false));
        this.wait = (BooleanSetting)this.registerSetting(new BooleanSetting("Wait", true));
        this.swing = (BooleanSetting)this.registerSetting(new BooleanSetting("Swing", true));
        this.endSwing = (BooleanSetting)this.registerSetting(new BooleanSetting("EndSwing", false));
        this.onlyGround = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyGround", true));
        this.farCancel = (BooleanSetting)this.registerSetting(new BooleanSetting("FarCancel", false));
        this.eatPause = (BooleanSetting)this.registerSetting(new BooleanSetting("EatPause", true));
        this.switchMode = (EnumSetting)this.registerSetting(new EnumSetting("SwitchMode", SwitchMode.DEFAULT));
        this.doubleBreak = (BooleanSetting)this.registerSetting(new BooleanSetting("DoubleBreak", true));
        this.booleanSetting6 = (BooleanSetting)this.registerSetting(new BooleanSetting("1.21", false));
        this.doubleDelay = (NumberSetting)this.registerSetting(new NumberSetting("DoubleDelay", 50.0, 0.0, 500.0, 1.0));
        this.start = (NumberSetting)this.registerSetting(new NumberSetting("Start", 0.9, 0.0, 2.0, 0.01));
        this.timeOut = (NumberSetting)this.registerSetting(new NumberSetting("TimeOut", 1.2, 0.5, 5.0, 0.01));
        this.debugLog = (BooleanSetting)this.registerSetting(new BooleanSetting("DebugLog", false));
        this.startRotate = (BooleanSetting)this.registerSetting(new BooleanSetting("StartRotate", true));
        this.endRotate = (BooleanSetting)this.registerSetting(new BooleanSetting("EndRotate", false));
        this.doubleStartRotate = (BooleanSetting)this.registerSetting(new BooleanSetting("DoubleStartRotate", false));
        this.doubleEndRotate = (BooleanSetting)this.registerSetting(new BooleanSetting("DoubleEndRotate", false));
        this.rotateMs = (NumberSetting)this.registerSetting(new NumberSetting("RotateMs", 150.0, 0.0, 1500.0, 10.0));
        this.rotateMode = (EnumSetting)this.registerSetting(new EnumSetting("RotateMode", RotateMode.DEFAULT));
        this.animation = (EnumSetting)this.registerSetting(new EnumSetting("Animation", AnimationMode.Up));
        this.ease = (EnumSetting)this.registerSetting(new EnumSetting("Ease", EaseMode.CubicInOut));
        this.fadeEase = (EnumSetting)this.registerSetting(new EnumSetting("FadeEase", EaseMode.CubicInOut));
        this.box = (BooleanSetting)this.registerSetting(new BooleanSetting("Box", true));
        this.outline = (BooleanSetting)this.registerSetting(new BooleanSetting("Outline", true));
        this.through = (BooleanSetting)this.registerSetting(new BooleanSetting("Through", false));
        this.startFill = (ColorSetting)this.registerSetting(new ColorSetting("StartFill", 0x64FFFFFF));
        this.startOutline = (ColorSetting)this.registerSetting(new ColorSetting("StartOutline", -1));
        this.endFill = (ColorSetting)this.registerSetting(new ColorSetting("EndFill", 1677765375));
        this.endOutline = (ColorSetting)this.registerSetting(new ColorSetting("EndOutline", -16733441));
        this.doubleFill = (ColorSetting)this.registerSetting(new ColorSetting("DoubleFill", 1683513087));
        this.doubleOutline = (ColorSetting)this.registerSetting(new ColorSetting("DoubleOutline", -10985729));
        this.blockPos12 = null;
        this.direction8 = null;
        this.direction7 = null;
        this.flag98 = false;
        this.count133 = 0;
        this.value120 = 0.0;
        this.value113 = 0.0;
        this.flag172 = false;
        this.flag180 = false;
        this.flag128 = false;
        this.time10 = 0L;
        this.helper729 = new Helper7();
        this.flag24 = false;
        this.helper730 = new Helper7();
        this.flag168 = false;
        this.helper715 = new Helper7();
        this.flag131 = false;
        this.helper736 = new Helper7();
        this.flag91 = false;
        this.blockPos14 = null;
        this.direction6 = null;
        this.blockPos9 = null;
        this.direction5 = null;
        this.helper733 = new Helper7();
        this.helper740 = new Helper7();
        this.helper716 = new Helper7();
        this.helper727 = new Helper7();
        this.timer4 = new Timer(1000L);
        this.timer2 = new Timer(1000L);
        this.decimalFormat2 = new DecimalFormat("0.0");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static BlockPos getBlockPos7() {
        boolean bl = false;
        if (INSTANCE == null) return null;
        if (!INSTANCE.isEnabled()) return null;
        BlockPos blockPos = SpeedMine.INSTANCE.blockPos12;
        return blockPos;
    }

    public static void setObj27(Object object) {
        BlockPos blockPos;
        blockPos7 = blockPos = (BlockPos)object;
        boolean bl = false;
        if (INSTANCE != null) {
            SpeedMine.INSTANCE.flag128 = false;
            SpeedMine.INSTANCE.flag180 = false;
            SpeedMine.INSTANCE.helper740.resetTimer();
            SpeedMine.INSTANCE.direction7 = null;
        }
    }

    public void setObj84(Object object) {
        BlockPos blockPos;
        block7: {
            block6: {
                blockPos = (BlockPos)object;
                boolean bl = false;
                if (Module.isNotInGame() || MC.mc.player.isCreative()) {
                    return;
                }
                if (blockPos == null) break block6;
                if (!blockPos.equals((Object)this.blockPos12)) break block7;
            }
            return;
        }
        if (this.m193(blockPos) || SpeedMine.m822(blockPos)) {
            return;
        }
        if (this.m561(blockPos) == null) {
            return;
        }
        this.blockPos12 = blockPos;
        this.direction8 = null;
        this.flag172 = false;
        this.count133 = 0;
        this.flag98 = false;
        flag95 = false;
        flag73 = false;
        this.helper733.resetTimer();
        this.timer4.m136();
    }

    public boolean isSet149() {
        block5: {
            block4: {
                boolean bl = false;
                if (!this.flag172) {
                    return false;
                }
                if (this.blockPos12 == null) break block4;
                if (SpeedMine.m822(this.blockPos12)) break block4;
                if (this.m193(this.blockPos12)) break block4;
                if (!(MC.mc.player.getEyePos().distanceTo(Vec3d.ofCenter((Vec3i)this.blockPos12)) > (Double)this.range.getValue())) break block5;
            }
            this.flag172 = false;
            return false;
        }
        return true;
    }

    @Override
    public void onEnable() {
        this.m989();
    }

    @Override
    public void onDisable() {
        this.m989();
        Client.itemSwitcher.restoreSlot();
    }

    private void m989() {
        this.flag98 = false;
        flag95 = false;
        flag73 = false;
        this.count133 = 0;
        this.blockPos12 = null;
        this.direction8 = null;
        this.flag172 = false;
        blockPos7 = null;
        this.direction7 = null;
        this.flag180 = false;
        this.flag128 = false;
        this.time10 = 0L;
        this.flag24 = false;
        this.flag168 = false;
        this.flag131 = false;
        this.flag91 = false;
        this.blockPos14 = null;
        this.direction6 = null;
        this.blockPos9 = null;
        this.direction5 = null;
        value189 = 0.0;
        this.helper740.resetTimer();
        this.helper733.resetTimer();
    }

    @EventHandler
    private void setStartAttackEvent2(StartAttackEvent startAttackEvent) {
        Direction direction;
        if (Module.isNotInGame() || MC.mc.player.isCreative()) {
            return;
        }
        if (((Boolean)this.eatPause.getValue()).booleanValue() && MC.mc.player.isUsingItem()) {
            return;
        }
        HitResult hitResult = MC.mc.crosshairTarget;
        if (!(hitResult instanceof BlockHitResult)) {
            return;
        }
        BlockHitResult blockHitResult = (BlockHitResult)hitResult;
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return;
        }
        BlockPos blockPos349 = blockHitResult.getBlockPos();
        if (SpeedMine.m822(blockPos349)) {
            return;
        }
        if (MC.mc.player.getEyePos().distanceTo(Vec3d.ofCenter((Vec3i)blockPos349)) > (Double)this.range.getValue()) {
            return;
        }
        if (this.m561(blockPos349) == null) {
            return;
        }
        if (blockPos349.equals((Object)this.blockPos12)) {
            return;
        }
        this.blockPos12 = blockPos349;
        this.direction8 = blockHitResult.getSide();
        this.flag172 = true;
        this.count133 = 0;
        this.flag98 = false;
        flag95 = false;
        flag73 = false;
        this.helper733.resetTimer();
        this.timer4.m136();
        if (!this.helper727.hasPassedMs((Double)this.startDelay.getValue())) {
            return;
        }
        this.direction8 = direction = this.m138(this.blockPos12);
        if (((Boolean)this.swing.getValue()).booleanValue()) {
            MC.mc.player.swingHand(Hand.MAIN_HAND);
        }
        if (((Boolean)this.doubleBreak.getValue()).booleanValue()) {
            if (blockPos7 == null || this.m193(blockPos7)) {
                int n = this.m862(this.blockPos12);
                if (n == -1) {
                    n = MC.mc.player.getInventory().getSelectedSlot();
                }
                this.value113 = this.m710(this.blockPos12, n, 1.0);
                this.timer2.m136();
                this.timer2.setLong4((long)Math.max(this.value113, 1.0));
                SpeedMine.setObj27(this.blockPos12);
            }
            this.setObj99(direction);
        }
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, this.blockPos12, direction));
        this.setObj36("onAttack START breakPos=" + String.valueOf(this.blockPos12) + " side=" + String.valueOf(direction));
        this.helper727.resetTimer();
    }

    @EventHandler
    private void setPacketEventInner213(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (Module.isNotInGame() || MC.mc.player.isCreative()) {
            return;
        }
        Packet packet = packetEventInner2.getPacket();
        if (!(packet instanceof PlayerActionC2SPacket)) {
            return;
        }
        PlayerActionC2SPacket playerActionC2SPacket = (PlayerActionC2SPacket)packet;
        if (playerActionC2SPacket.getAction() == PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) {
            if (playerActionC2SPacket.getPos().equals((Object)this.blockPos12)) {
                this.flag98 = true;
            }
        } else if (playerActionC2SPacket.getAction() == PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK && playerActionC2SPacket.getPos().equals((Object)this.blockPos12) && !((Boolean)this.instant.getValue()).booleanValue()) {
            this.flag98 = false;
            flag95 = false;
            flag73 = false;
        }
    }

    @EventHandler
    private void onTick3(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (MC.mc.player.isCreative()) {
            this.m989();
            return;
        }
        long rotMs = ((Double)this.rotateMs.getValue()).longValue();
        boolean rotated = false;
        if (this.flag24) {
            if (this.helper729.hasPassedMs(rotMs)) {
                this.flag24 = false;
                this.m244();
            } else {
                BlockPos bp = this.blockPos12 != null ? this.blockPos12 : this.blockPos14;
                Direction dir = this.direction8 != null ? this.direction8 : this.direction6;
                if (bp != null) {
                    this.m400(bp, dir);
                    rotated = true;
                }
            }
        }
        if (this.flag168) {
            if (this.helper730.hasPassedMs(rotMs)) {
                this.flag168 = false;
                if (!this.flag24 && !this.flag131 && !this.flag91) {
                    this.m244();
                }
            } else {
                BlockPos bp = this.blockPos14 != null ? this.blockPos14 : this.blockPos12;
                Direction dir = this.direction6 != null ? this.direction6 : this.direction8;
                if (bp != null) {
                    this.m400(bp, dir);
                    rotated = true;
                }
            }
        }
        if (this.flag131) {
            if (this.helper715.hasPassedMs(rotMs)) {
                this.flag131 = false;
                if (!this.flag24 && !this.flag168 && !this.flag91) {
                    this.m244();
                }
            } else {
                BlockPos bp = blockPos7 != null ? blockPos7 : this.blockPos9;
                Direction dir = this.direction5;
                if (bp != null) {
                    this.m400(bp, dir);
                    rotated = true;
                }
            }
        }
        if (this.flag91) {
            if (this.helper736.hasPassedMs(rotMs)) {
                this.flag91 = false;
                if (!this.flag24 && !this.flag168 && !this.flag131) {
                    this.m244();
                }
            } else {
                BlockPos bp = this.blockPos9 != null ? this.blockPos9 : blockPos7;
                Direction dir = this.direction5;
                if (bp != null) {
                    this.m400(bp, dir);
                    rotated = true;
                }
            }
        }
        if (!rotated && Client.mathUtil.hasPendingRotation()) {
            this.m244();
        }
        if (blockPos7 != null) {
            int slot = this.m862(blockPos7);
            if (slot == -1) {
                slot = MC.mc.player.getInventory().getSelectedSlot();
            }
            this.value113 = this.m710(blockPos7, slot, 1.0);
            if (!this.m193(blockPos7) && !SpeedMine.m822(blockPos7)) {
                double need = this.m710(blockPos7, MC.mc.player.getInventory().getSelectedSlot(), 1.0);
                if (this.helper740.hasPassedMs((long)(need * (Double)this.timeOut.getValue()))) {
                    long elapsed = this.helper740.getElapsed();
                    long limit = (long)(need * (Double)this.timeOut.getValue());
                    this.setObj36("secondPos TIMEOUT elapsed=" + elapsed + "ms limit=" + limit + "ms");
                    SpeedMine.setObj27(null);
                }
            } else {
                this.setObj36("secondPos cleared: isAir/unbreakable");
                SpeedMine.setObj27(null);
            }
        }
        if (blockPos7 != null && !this.flag128) {
            this.helper740.resetTimer();
            if (!blockPos7.equals((Object)this.blockPos12)) {
                Direction dir = this.getDirection2();
                this.blockPos9 = blockPos7;
                this.direction5 = dir;
                if (((Boolean)this.doubleStartRotate.getValue()).booleanValue()) {
                    this.m400(blockPos7, dir);
                    this.flag131 = true;
                    this.helper715.resetTimer();
                }
                MC.mc.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos7, dir));
                this.setObj36("secondPos START secondPos=" + String.valueOf(blockPos7) + " side=" + String.valueOf(dir));
            }
            this.flag128 = true;
        }
        this.doAutoSwitch();
        if (this.blockPos12 == null) {
            this.count133 = 0;
            this.flag98 = false;
            flag95 = false;
            flag73 = false;
            this.direction8 = null;
            value189 = 0.0;
        } else {
            if (MC.mc.world.isAir(this.blockPos12)) {
                flag73 = true;
            }
            int slot = this.m862(this.blockPos12);
            if (slot == -1) {
                slot = MC.mc.player.getInventory().getSelectedSlot();
            }
            this.value120 = this.m975(this.blockPos12, slot);
            value189 = (double)this.helper733.getElapsed() / this.value120;
            if (SpeedMine.m822(this.blockPos12)) {
                this.blockPos12 = null;
                this.direction8 = null;
                this.flag98 = false;
                flag95 = false;
                flag73 = false;
            } else if (((Boolean)this.farCancel.getValue()).booleanValue()
                    && MC.mc.player.getEyePos().distanceTo(Vec3d.ofCenter((Vec3i)this.blockPos12)) > (Double)this.range.getValue()) {
                this.flag98 = false;
                flag95 = false;
                flag73 = false;
                this.count133 = 0;
                this.blockPos12 = null;
                this.direction8 = null;
            } else if (this.helper716.hasPassedMs((Double)this.stopDelay.getValue())) {
                if (!((Boolean)this.eatPause.getValue()).booleanValue() || !MC.mc.player.isUsingItem()) {
                    if (this.flag98) {
                        if (this.m193(this.blockPos12)) {
                            return;
                        }
                        if (((Boolean)this.onlyGround.getValue()).booleanValue() && !MC.mc.player.isOnGround()) {
                            return;
                        }
                        if (this.helper733.hasPassedMs(this.value120)) {
                            if (System.currentTimeMillis() - this.time10 < (Double)this.doubleDelay.getValue()) {
                                return;
                            }
                            ClientSetting.SwitchMode sm = this.getSwitchMode4();
                            BlockPos bp = this.blockPos12;
                            Direction dir = this.getDirection4();
                            this.blockPos14 = bp;
                            this.direction6 = dir;
                            if (((Boolean)this.endRotate.getValue()).booleanValue()) {
                                this.m400(bp, dir);
                            }
                            if (sm != ClientSetting.SwitchMode.NONE) {
                                Client.itemSwitcher.switchToItem((java.util.function.Predicate<ItemStack>) itemStack -> {
                                    int nInner = AutoArmor.getSwitchFlag();
                                    float f = itemStack.getMiningSpeedMultiplier(MC.mc.world.getBlockState(bp)) - 1.0f;
                                    float f2 = f == 0.0f ? 0 : (f > 0.0f ? 1 : -1);
                                    if (nInner != 0) {
                                        f2 = f2 > 0 ? 1.0f : 0.0f;
                                    }
                                    return f2 != 0.0f;
                                }, (Object)sm);
                            }
                            MC.mc.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, bp, dir));
                            this.setObj36("STOP breakPos=" + String.valueOf(bp));
                            this.time10 = System.currentTimeMillis();
                            if (((Boolean)this.endSwing.getValue()).booleanValue()) {
                                MC.mc.player.swingHand(Hand.MAIN_HAND);
                            }
                            if (sm != ClientSetting.SwitchMode.NONE) {
                                Client.itemSwitcher.restoreSlot();
                            }
                            this.flag24 = false;
                            if (((Boolean)this.endRotate.getValue()).booleanValue()) {
                                this.flag168 = true;
                                this.helper730.resetTimer();
                            } else {
                                this.m244();
                            }
                            this.count133++;
                            this.helper716.resetTimer();
                            this.helper727.resetTimer();
                            flag95 = true;
                        }
                    } else {
                        if (!this.helper727.hasPassedMs((Double)this.startDelay.getValue())) {
                            return;
                        }
                        if (!((Boolean)this.wait.getValue()).booleanValue() && this.m193(this.blockPos12)) {
                            return;
                        }
                        Direction dir = this.m138(this.blockPos12);
                        this.direction8 = dir;
                        this.blockPos14 = this.blockPos12;
                        this.direction6 = dir;
                        if (((Boolean)this.startRotate.getValue()).booleanValue()) {
                            this.m400(this.blockPos12, dir);
                        }
                        this.helper733.resetTimer();
                        this.timer4.m136();
                        if (((Boolean)this.swing.getValue()).booleanValue()) {
                            MC.mc.player.swingHand(Hand.MAIN_HAND);
                        }
                        boolean instantBreak = ((Boolean)this.instant.getValue()).booleanValue() || this.m975(this.blockPos12, slot) <= 50.0;
                        if (((Boolean)this.doubleBreak.getValue()).booleanValue()) {
                            if (blockPos7 == null || this.m193(blockPos7)) {
                                int slot2 = this.m862(this.blockPos12);
                                if (slot2 == -1) {
                                    slot2 = MC.mc.player.getInventory().getSelectedSlot();
                                }
                                this.value113 = this.m710(this.blockPos12, slot2, 1.0);
                                this.timer2.m136();
                                this.timer2.setLong4((long)Math.max(this.value113, 1.0));
                                SpeedMine.setObj27(this.blockPos12);
                            }
                            this.setObj99(dir);
                        }
                        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, this.blockPos12, dir));
                        this.setObj36("START breakPos=" + String.valueOf(this.blockPos12) + " side=" + String.valueOf(dir) + " instant=" + instantBreak);
                        if (instantBreak) {
                            ClientSetting.SwitchMode sm = this.getSwitchMode4();
                            if (sm != ClientSetting.SwitchMode.NONE) {
                                BlockPos bp = this.blockPos12;
                                Client.itemSwitcher.switchToItem((java.util.function.Predicate<ItemStack>) itemStack -> {
                                    int nInner = AutoArmor.getSwitchFlag();
                                    float f = itemStack.getMiningSpeedMultiplier(MC.mc.world.getBlockState(bp)) - 1.0f;
                                    float f2 = f == 0.0f ? 0 : (f > 0.0f ? 1 : -1);
                                    if (nInner != 0) {
                                        f2 = f2 > 0 ? 1.0f : 0.0f;
                                    }
                                    return f2 != 0.0f;
                                }, (Object)sm);
                            }
                            MC.mc.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, this.blockPos12, dir));
                            this.time10 = System.currentTimeMillis();
                            if (((Boolean)this.endSwing.getValue()).booleanValue()) {
                                MC.mc.player.swingHand(Hand.MAIN_HAND);
                            }
                            if (sm != ClientSetting.SwitchMode.NONE) {
                                Client.itemSwitcher.restoreSlot();
                            }
                            this.setObj36("INSTANT STOP breakPos=" + String.valueOf(this.blockPos12));
                            flag95 = true;
                            this.flag24 = false;
                            if (((Boolean)this.endRotate.getValue()).booleanValue()) {
                                this.flag168 = true;
                                this.helper730.resetTimer();
                            } else {
                                this.m244();
                            }
                        } else if (((Boolean)this.startRotate.getValue()).booleanValue()) {
                            this.flag24 = true;
                            this.helper729.resetTimer();
                        } else {
                            this.m244();
                        }
                        this.helper727.resetTimer();
                    }
                }
            }
        }
    }

    private void setObj99(Object object) {
        Direction direction = (Direction)object;
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, this.blockPos12, direction));
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, this.blockPos12, direction));
        this.setObj36("doDoubleBreak START+STOP breakPos=" + String.valueOf(this.blockPos12) + " side=" + String.valueOf(direction));
    }

    private void doAutoSwitch() {
        block38: {
            SpeedMine speedMine;
            Object object;
            block37: {
                ClientSetting.SwitchMode switchMode;
                int n;
                block36: {
                    ClientPlayerEntity clientPlayerEntity;
                    block35: {
                        boolean bl;
                        block34: {
                            BlockPos blockPos;
                            int n2;
                            block33: {
                                int n3;
                                block32: {
                                    int n4;
                                    block30: {
                                        block31: {
                                            block29: {
                                                block28: {
                                                    block26: {
                                                        block27: {
                                                            block25: {
                                                                n = AutoArmor.getSwitchFlag();
                                                                if (!((Boolean)this.doubleBreak.getValue()).booleanValue()) {
                                                                    return;
                                                                }
                                                                if (blockPos7 == null) break block25;
                                                                n4 = this.m193(blockPos7) ? 1 : 0;
                                                                if (n == 0) break block26;
                                                                if (n4 == 0) break block27;
                                                            }
                                                            return;
                                                        }
                                                        n4 = blockPos7.equals((Object)this.blockPos12) ? 1 : 0;
                                                    }
                                                    if (n != 0) {
                                                        if (n4 != 0) {
                                                            return;
                                                        }
                                                        n4 = this.flag180 ? 1 : 0;
                                                    }
                                                    if (n == 0) break block28;
                                                    if (n4 != 0) break block29;
                                                    n4 = this.flag128 ? 1 : 0;
                                                }
                                                if (n == 0) break block30;
                                                if (n4 != 0) break block31;
                                            }
                                            return;
                                        }
                                        n4 = this.m862(blockPos7);
                                    }
                                    n3 = n2 = n4;
                                    if (n == 0) break block32;
                                    if (n3 != -1) break block33;
                                    n3 = MC.mc.player.getInventory().getSelectedSlot();
                                }
                                n2 = n3;
                            }
                            long l = (long)this.m710(blockPos7, n2, (Double)this.start.getValue());
                            String string = String.valueOf(blockPos7);
                            long l2 = l;
                            long l3 = this.helper740.getElapsed();
                            this.setObj36("autoSwitch: elapsed=" + l3 + "ms need=" + l2 + "ms secondPos=" + string);
                            int n5 = this.helper740.hasPassedMs(l) ? 1 : 0;
                            if (n != 0) {
                                if (n5 == 0) {
                                    return;
                                }
                                double d = (double)(System.currentTimeMillis() - this.time10) - (Double)this.doubleDelay.getValue();
                                n5 = d == 0.0 ? 0 : (d < 0.0 ? -1 : 1);
                            }
                            if (n5 < 0) {
                                return;
                            }
                            switchMode = this.getSwitchMode4();
                            if (switchMode != ClientSetting.SwitchMode.NONE) {
                                blockPos = blockPos7;
                                net.minecraft.util.math.BlockPos blockPosF = blockPos;
                                Client.itemSwitcher.switchToItem((java.util.function.Predicate<ItemStack>) itemStack -> {
                                    int nInner = AutoArmor.getSwitchFlag();
                                    float f = itemStack.getMiningSpeedMultiplier(MC.mc.world.getBlockState(blockPosF)) - 1.0f;
                                    float f2 = f == 0.0f ? 0 : (f > 0.0f ? 1 : -1);
                                    if (nInner != 0) {
                                        f2 = f2 > 0 ? 1.0f : 0.0f;
                                    }
                                    return f2 != 0.0f;
                                }, (Object)switchMode);
                            }
                            blockPos = blockPos7;
                            Direction direction = this.getDirection2();
                            this.blockPos9 = blockPos;
                            this.direction5 = direction;
                            bl = (Boolean)this.doubleEndRotate.getValue();
                            if (n == 0) break block34;
                            if (bl) {
                                this.m400(blockPos, direction);
                            }
                            clientPlayerEntity = MC.mc.player;
                            if (n == 0) break block35;
                            clientPlayerEntity.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, direction));
                            long l4 = l;
                            long l5 = this.helper740.getElapsed();
                            String string2 = String.valueOf(direction);
                            String string3 = String.valueOf(blockPos);
                            this.setObj36("STOP secondPos=" + string3 + " side=" + string2 + " elapsed=" + l5 + "ms needed=" + l4 + "ms");
                            this.time10 = System.currentTimeMillis();
                            bl = (Boolean)this.endSwing.getValue();
                        }
                        if (!bl) break block36;
                        clientPlayerEntity = MC.mc.player;
                    }
                    clientPlayerEntity.swingHand(Hand.MAIN_HAND);
                }
                object = switchMode;
                if (n == 0) break block37;
                if (object != ClientSetting.SwitchMode.NONE) {
                    Client.itemSwitcher.restoreSlot();
                }
                speedMine = this;
                if (n == 0) break block38;
                object = speedMine.doubleEndRotate.getValue();
            }
            if (((Boolean)object).booleanValue()) {
                this.flag131 = false;
                this.flag91 = true;
                this.helper736.resetTimer();
            }
            speedMine = this;
        }
        this.flag180 = true;
    }

    /*
     * Unable to fully structure code
     */
    private void m400(Object var1_1, Object var2_2) {
        BlockPos blockPos = (BlockPos)var1_1;
        Direction direction = (Direction)var2_2;
        Vec3d vec3d = direction != null
                ? Vec3d.ofCenter((Vec3i)blockPos).add(direction.getOffsetX() * 0.5, direction.getOffsetY() * 0.5, direction.getOffsetZ() * 0.5)
                : Vec3d.ofCenter((Vec3i)blockPos);
        float[] fArray = MathUtil.getLookAngles(MC.mc.player.getEyePos(), vec3d);
        switch (Lambda.counts14[this.getRotateMode6().ordinal()]) {
            case 1: {
                Client.mathUtil.setRotationSilent(fArray[0], fArray[1]);
                break;
            }
            case 2: {
                Client.mathUtil.setRotationVisible(fArray[0], fArray[1]);
                break;
            }
            case 3: {
                Client.mathUtil.setTargetRotation(fArray[0], fArray[1]);
                Client.mathUtil.setFloat6(45.0f);
                break;
            }
        }
    }

    private void m244() {
        boolean bl = false;
        switch (Lambda.counts14[this.getRotateMode6().ordinal()]) {
            case 1: {
                Client.mathUtil.resetRotationSilent();
                if (!false) break;
            }
            case 2: {
                Client.mathUtil.resetRotationVisible();
                break;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private ClientSetting.RotateMode getRotateMode6() {
        ClientSetting.RotateMode rotateMode;
        int n = AutoArmor.getSwitchFlag();
        Object object = this.rotateMode.getValue();
        if (n != 0) {
            if (object == RotateMode.DEFAULT) {
                ClientSetting.RotateMode rotateMode2;
                Object object2 = ClientSetting.INSTANCE;
                if (n != 0) {
                    if (object2 == null) {
                        rotateMode2 = ClientSetting.RotateMode.NONE;
                        return rotateMode2;
                    }
                    object2 = ClientSetting.INSTANCE.rotateMode.getValue();
                }
                rotateMode2 = (ClientSetting.RotateMode)((Object)object2);
                return rotateMode2;
            }
            object = this.rotateMode.getValue();
        }
        switch (((RotateMode)((Object)object)).ordinal()) {
            case 1: {
                rotateMode = ClientSetting.RotateMode.NONE;
                return rotateMode;
            }
            case 2: {
                rotateMode = ClientSetting.RotateMode.SMOOTH;
                return rotateMode;
            }
            case 3: {
                rotateMode = ClientSetting.RotateMode.ONTICK;
                return rotateMode;
            }
            case 4: {
                rotateMode = ClientSetting.RotateMode.rotateMode;
                return rotateMode;
            }
        }
        rotateMode = ClientSetting.RotateMode.NONE;
        return rotateMode;
    }

    /*
     * Enabled aggressive block sorting
     */
    private ClientSetting.SwitchMode getSwitchMode4() {
        ClientSetting.SwitchMode switchMode;
        int n = AutoArmor.getSwitchFlag();
        Object object = this.switchMode.getValue();
        if (n != 0) {
            if (object == SwitchMode.DEFAULT) {
                ClientSetting.SwitchMode switchMode2;
                Object object2 = ClientSetting.INSTANCE;
                if (n != 0) {
                    if (object2 == null) {
                        switchMode2 = ClientSetting.SwitchMode.NONE;
                        return switchMode2;
                    }
                    object2 = ClientSetting.INSTANCE.switchMode.getValue();
                }
                switchMode2 = (ClientSetting.SwitchMode)((Object)object2);
                return switchMode2;
            }
            object = this.switchMode.getValue();
        }
        switch (((SwitchMode)((Object)object)).ordinal()) {
            case 1: {
                switchMode = ClientSetting.SwitchMode.NONE;
                return switchMode;
            }
            case 2: {
                switchMode = ClientSetting.SwitchMode.NORMAL;
                return switchMode;
            }
            case 3: {
                switchMode = ClientSetting.SwitchMode.SILENT;
                return switchMode;
            }
            case 4: {
                switchMode = ClientSetting.SwitchMode.INVENTORY;
                return switchMode;
            }
        }
        switchMode = ClientSetting.SwitchMode.NONE;
        return switchMode;
    }

    @EventHandler
    private void setRenderLevelEvent3(RenderLevelEvent renderLevelEvent) {
        double d;
        if (!((Boolean)this.box.getValue()).booleanValue() && !((Boolean)this.outline.getValue()).booleanValue()) {
            return;
        }
        Matrix4f matrix4f = renderLevelEvent.getMatrix4f3();
        if (this.blockPos12 != null && MC.mc.world != null && MC.mc.world.isAir(this.blockPos12)) {
            flag73 = true;
        }
        if (blockPos7 != null) {
            if (this.m193(blockPos7)) {
                Object[] objectArray = new Object[1];
                objectArray[0] = null;
                Object[] objectArray2 = objectArray;
                SpeedMine.setObj27(objectArray2[0]);
            } else {
                this.timer2.setLong4((long)Math.max(this.value113, 1.0));
                d = this.timer2.m1037((Object)((EaseMode)((Object)this.ease.getValue())));
                if (((Boolean)this.box.getValue()).booleanValue()) {
                    EspRenderLayers.drawBoxFilled(matrix4f, this.m556(blockPos7, d), (Integer)this.doubleFill.getValue(), (Boolean)this.through.getValue());
                }
                if (((Boolean)this.outline.getValue()).booleanValue()) {
                    EspRenderLayers.drawBoxOutline(matrix4f, this.m460(blockPos7, d), (Integer)this.doubleOutline.getValue(), (Boolean)this.through.getValue());
                }
            }
        }
        if (this.blockPos12 != null) {
            value189 = (double)this.helper733.getElapsed() / Math.max(this.value120, 1.0);
            this.timer4.setLong4((long)Math.max(this.value120, 1.0));
            d = this.timer4.m1037((Object)((EaseMode)((Object)this.ease.getValue())));
            double d2 = this.timer4.m1037((Object)((EaseMode)((Object)this.fadeEase.getValue())));
            int n = SpeedMine.m425((Integer)this.startFill.getValue(), (Integer)this.endFill.getValue(), d2);
            int n2 = SpeedMine.m425((Integer)this.startOutline.getValue(), (Integer)this.endOutline.getValue(), d2);
            if (((Boolean)this.box.getValue()).booleanValue()) {
                EspRenderLayers.drawBoxFilled(matrix4f, this.m556(this.blockPos12, d), n, (Boolean)this.through.getValue());
            }
            if (((Boolean)this.outline.getValue()).booleanValue()) {
                EspRenderLayers.drawBoxOutline(matrix4f, this.m460(this.blockPos12, d), n2, (Boolean)this.through.getValue());
            }
        } else {
            value189 = 0.0;
        }
        EspRenderLayers.drawBuffers();
    }

    private Box m556(Object object, double d) {
        BlockPos blockPos = (BlockPos)object;
        double d2 = d;
        double d3 = blockPos.getX();
        double d4 = blockPos.getY();
        double d5 = blockPos.getZ();
        return switch (((AnimationMode)((Object)this.animation.getValue())).ordinal()) {
            default -> throw new MatchException(null, null);
            case 0, 4 -> {
                double var14_8 = (1.0 - d2) / 2.0;
                yield new Box(d3 + var14_8, d4 + var14_8, d5 + var14_8, d3 + 1.0 - var14_8, d4 + 1.0 - var14_8, d5 + 1.0 - var14_8);
            }
            case 1 -> {
                double var14_9 = (1.0 - d2) / 2.0;
                yield new Box(d3 + var14_9, d4, d5 + var14_9, d3 + 1.0 - var14_9, d4 + 1.0, d5 + 1.0 - var14_9);
            }
            case 2 -> new Box(d3, d4, d5, d3 + 1.0, d4 + d2, d5 + 1.0);
            case 3 -> new Box(d3, d4 + 1.0 - d2, d5, d3 + 1.0, d4 + 1.0, d5 + 1.0);
            case 5 -> new Box(blockPos);
        };
    }

    private Box m460(Object object, double d) {
        BlockPos blockPos = (BlockPos)object;
        double d2 = d;
        double d3 = blockPos.getX();
        double d4 = blockPos.getY();
        double d5 = blockPos.getZ();
        return switch (((AnimationMode)((Object)this.animation.getValue())).ordinal()) {
            default -> throw new MatchException(null, null);
            case 0, 4 -> {
                double var14_8 = (1.0 - d2) / 2.0;
                yield new Box(d3 + var14_8, d4 + var14_8, d5 + var14_8, d3 + 1.0 - var14_8, d4 + 1.0 - var14_8, d5 + 1.0 - var14_8);
            }
            case 1 -> {
                double var14_9 = (1.0 - d2) / 2.0;
                yield new Box(d3 + var14_9, d4, d5 + var14_9, d3 + 1.0 - var14_9, d4 + 1.0, d5 + 1.0 - var14_9);
            }
            case 2 -> new Box(d3, d4, d5, d3 + 1.0, d4 + d2, d5 + 1.0);
            case 3 -> new Box(d3, d4 + 1.0 - d2, d5, d3 + 1.0, d4 + 1.0, d5 + 1.0);
            case 5 -> new Box(blockPos).expand(0.001);
        };
    }

    Direction m138(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Direction[] directionArray = Direction.values();
        boolean bl = false;
        int n = directionArray.length;
        for (int i = 0; i < n; ++i) {
            Direction direction = directionArray[i];
            if (MC.mc.world.getBlockState(blockPos.offset(direction)).isAir()) continue;
            return direction;
        }
        return Direction.UP;
    }

    Direction m561(Object object) {
        BlockPos blockPos = (BlockPos)object;
        if (MC.mc.player.getEyePos().distanceTo(Vec3d.ofCenter((Vec3i)blockPos)) > (Double)this.range.getValue()) {
            return null;
        }
        return this.m138(blockPos);
    }

    private Direction getDirection4() {
        Direction direction;
        block4: {
            SpeedMine speedMine;
            block6: {
                block5: {
                    int n = AutoArmor.getSwitchFlag();
                    direction = this.direction8;
                    if (n == 0) break block4;
                    if (direction != null) break block5;
                    speedMine = this;
                    if (n == 0) break block6;
                    if (speedMine.blockPos12 != null) {
                        this.direction8 = this.m138(this.blockPos12);
                    }
                }
                speedMine = this;
            }
            direction = speedMine.direction8;
        }
        return direction;
    }

    private Direction getDirection2() {
        block1: {
            boolean bl = false;
            if (blockPos7 == null) {
                return Direction.UP;
            }
            if (this.direction7 != null) break block1;
            this.direction7 = this.m138(blockPos7);
        }
        return this.direction7;
    }

    double m975(Object object, int n) {
        BlockPos blockPos = (BlockPos)object;
        int n2 = n;
        return this.m710(blockPos, n2, (Double)this.damage.getValue());
    }

    double m710(Object object, int n, double d) {
        float f;
        int n2 = 0;
        BlockPos blockPos = (BlockPos)object;
        int n3 = n;
        double d2 = d;
        int n4 = AutoArmor.getSwitchFlag();
        int n5 = Module.isNotInGame() ? 1 : 0;
        if (n4 != 0) {
            if (n5 != 0) {
                return Double.MAX_VALUE;
            }
            n5 = n3;
        }
        if (n4 != 0) {
            n5 = n2 = n5 < 0 ? MC.mc.player.getInventory().getSelectedSlot() : n3;
        }
        if ((f = this.m964(blockPos, MC.mc.player.getInventory().getStack(n2))) <= 0.0f) {
            return Double.MAX_VALUE;
        }
        return 1.0 / (double)f / 20.0 * 1000.0 * d2;
    }

    /*
     * Unable to fully structure code
     */
    private float m964(Object var1_1, Object var2_2) {
        BlockPos blockPos = (BlockPos)var1_1;
        ItemStack itemStack = (ItemStack)var2_2;
        BlockState blockState = MC.mc.world.getBlockState(blockPos);
        float hardness = blockState.getHardness((BlockView)MC.mc.world, blockPos);
        if (hardness < 0.0f) {
            return 0.0f;
        }
        float divisor = blockState.isToolRequired() && !itemStack.isSuitableFor(blockState) ? 100.0f : 30.0f;
        return this.m993(blockState, itemStack) / hardness / divisor;
    }

    /*
     * Unable to fully structure code
     */
    private float m993(Object var1_1, Object var2_2) {
        BlockState blockState = (BlockState)var1_1;
        ItemStack itemStack = (ItemStack)var2_2;
        float speed = itemStack != null && !itemStack.isEmpty() ? itemStack.getMiningSpeedMultiplier(blockState) : 1.0f;
        if (speed > 1.0f && itemStack != null && !itemStack.isEmpty()) {
            int efficiency = 0;
            for (Object2IntMap.Entry entry : itemStack.getEnchantments().getEnchantmentEntries()) {
                if (((RegistryEntry)entry.getKey()).matchesKey(Enchantments.EFFICIENCY)) {
                    efficiency = entry.getIntValue();
                    break;
                }
            }
            if (efficiency > 0) {
                speed += (float)(Math.pow(efficiency, 2.0) + 1.0);
            }
        }
        if (MC.mc.player.hasStatusEffect(StatusEffects.HASTE)) {
            net.minecraft.entity.effect.StatusEffectInstance haste = MC.mc.player.getStatusEffect(StatusEffects.HASTE);
            if (haste != null) {
                speed *= 1.0f + (float)(haste.getAmplifier() + 1) * 0.2f;
            }
        }
        if (MC.mc.player.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
            net.minecraft.entity.effect.StatusEffectInstance fatigue = MC.mc.player.getStatusEffect(StatusEffects.MINING_FATIGUE);
            if (fatigue != null) {
                speed *= switch (fatigue.getAmplifier()) {
                    case 0 -> 0.3f;
                    case 1 -> 0.09f;
                    case 2 -> 0.0027f;
                    default -> 8.1E-4f;
                };
            }
        }
        if (MC.mc.player.isSubmergedInWater()) {
            speed *= (float)MC.mc.player.getAttributeValue(EntityAttributes.SUBMERGED_MINING_SPEED);
        }
        if (!MC.mc.player.isOnGround()) {
            speed /= 5.0f;
        }
        return Math.max(speed, 0.0f);
    }

    int m862(Object object) {
        BlockPos blockPos = (BlockPos)object;
        boolean bl = false;
        if (Module.isNotInGame()) {
            return -1;
        }
        BlockState blockState = MC.mc.world.getBlockState(blockPos);
        int n = -1;
        float f = 1.0f;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = MC.mc.player.getInventory().getStack(i);
            if (itemStack.isEmpty()) continue;
            int n2 = 0;
            for (Object2IntMap.Entry entry : itemStack.getEnchantments().getEnchantmentEntries()) {
                if (((RegistryEntry)entry.getKey()).matchesKey(Enchantments.EFFICIENCY)) {
                    n2 = entry.getIntValue();
                    if (!false) break;
                }
                if (!false) continue;
            }
            float f2 = itemStack.getMiningSpeedMultiplier(blockState) + (float)n2;
            if (!(f2 > f)) continue;
            f = f2;
            n = i;
            if (!false) continue;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean m822(Object object) {
        BlockPos blockPos = (BlockPos)object;
        int n = AutoArmor.getSwitchFlag();
        SpeedMine speedMine = INSTANCE;
        if (n != 0) {
            if (speedMine == null) return false;
            speedMine = INSTANCE;
        }
        if (n != 0 && Module.isNotInGame()) {
            return false;
        }
        float f = MC.mc.world.getBlockState(blockPos).getHardness((BlockView)MC.mc.world, blockPos);
        float f2 = f - 0.0f;
        float f3 = f2 == 0.0f ? 0 : (f2 < 0.0f ? -1 : 1);
        if (n == 0) return f3 != 0.0f;
        if (f3 >= 0) {
            float f4 = f - 100.0f;
            f3 = f4 == 0.0f ? 0 : (f4 > 0.0f ? 1 : -1);
            if (n == 0) return f3 != 0.0f;
            if (f3 < 0) {
                f3 = 0.0f;
                return f3 != 0.0f;
            }
        }
        f3 = 1.0f;
        return f3 != 0.0f;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean m193(Object object) {
        BlockPos blockPos = (BlockPos)object;
        int n = AutoArmor.getSwitchFlag();
        if (blockPos == null) return true;
        boolean bl = Module.isNotInGame();
        if (n == 0) return bl;
        if (bl) return true;
        bl = MC.mc.world.isAir(blockPos);
        if (n == 0) return bl;
        if (!bl) return false;
        return true;
    }

    private void setObj36(Object object) {
        block0: {
            String string = (String)object;
            if (!((Boolean)this.debugLog.getValue()).booleanValue()) break block0;
            System.out.println("[SpeedMine] " + string);
        }
    }

    private static int m425(int n, int n2, double d) {
        int n3 = n;
        int n4 = n2;
        double d2 = d;
        int n5 = n3 >> 24 & 0xFF;
        int n6 = n3 >> 16 & 0xFF;
        int n7 = n3 >> 8 & 0xFF;
        int n8 = n3 & 0xFF;
        int n9 = n4 >> 24 & 0xFF;
        int n10 = n4 >> 16 & 0xFF;
        int n11 = n4 >> 8 & 0xFF;
        int n12 = n4 & 0xFF;
        return (int)((double)n5 + (double)(n9 - n5) * d2) << 24 | (int)((double)n6 + (double)(n10 - n6) * d2) << 16 | (int)((double)n7 + (double)(n11 - n7) * d2) << 8 | (int)((double)n8 + (double)(n12 - n8) * d2);
    }

    @Override
    public String getInfo() {
        if (this.blockPos12 == null) {
            return null;
        }
        return value189 >= 1.0 ? "Done" : this.decimalFormat2.format(value189 * 100.0) + "%";
    }

    static {
        value189 = 0.0;
        blockPos7 = null;
    }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts14 = new int[ClientSetting.RotateMode.values().length];

        static {
            try {
                Lambda.counts14[ClientSetting.RotateMode.ONTICK.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts14[ClientSetting.RotateMode.rotateMode.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts14[ClientSetting.RotateMode.SMOOTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum AnimationMode {
        Center,
        Grow,
        Up,
        Down,
        Oscillation,
        None;


        private static AnimationMode[] getAnimationModeArray2() {
            return new AnimationMode[]{Center, Grow, Up, Down, Oscillation, None};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum RotateMode {
        DEFAULT,
        NONE,
        SMOOTH,
        ONTICK,
        rotateMode13;


        private static RotateMode[] getRotateModeArray8() {
            return new RotateMode[]{DEFAULT, NONE, SMOOTH, ONTICK, rotateMode13};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum SwitchMode {
        DEFAULT,
        NONE,
        NORMAL,
        SILENT,
        INVENTORY;


        private static SwitchMode[] getSwitchModeArray7() {
            return new SwitchMode[]{DEFAULT, NONE, NORMAL, SILENT, INVENTORY};
        }
    }
}
