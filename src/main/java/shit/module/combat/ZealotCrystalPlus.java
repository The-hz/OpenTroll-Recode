/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.block.BlockState
 *  net.minecraft.block.Blocks
 *  net.minecraft.client.gui.screen.ingame.HandledScreen
 *  net.minecraft.client.gui.screen.ingame.InventoryScreen
 *  net.minecraft.enchantment.Enchantments
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.Entity$RemovalReason
 *  net.minecraft.entity.EntityType
 *  net.minecraft.entity.EquipmentSlot
 *  net.minecraft.entity.ItemEntity
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.attribute.EntityAttributes
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.entity.effect.StatusEffectInstance
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$LookAndOnGround
 *  net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket
 *  net.minecraft.registry.entry.RegistryEntry
 *  net.minecraft.util.Hand
 *  net.minecraft.util.hit.BlockHitResult
 *  net.minecraft.util.hit.HitResult$Type
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Box
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.Difficulty
 *  net.minecraft.world.RaycastContext
 *  net.minecraft.world.RaycastContext$FluidHandling
 *  net.minecraft.world.RaycastContext$ShapeType
 *  net.minecraft.world.World
 *  org.joml.Matrix4f
 *  org.joml.Matrix4fc
 *  org.joml.Vector4f
 */
package shit.module.combat;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Difficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import shit.Client;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.event.Render2DEvent;
import shit.event.RenderLevelEvent;
import shit.manager.FontManager2;
import shit.misc.Stopwatch;
import shit.misc.MathUtil;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.render.EspRenderLayers;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;
import shit.util.VanillaTextHelper;

@Environment(value=EnvType.CLIENT)
public class ZealotCrystalPlus
extends Module {
    public static ZealotCrystalPlus INSTANCE;
    public final EnumSetting page;
    private final NumberSetting placeDelay;
    private final EnumSetting placeMode;
    private final NumberSetting placeRange;
    private final NumberSetting minDamage;
    private final NumberSetting maxSelfDmg;
    private final NumberSetting reserve;
    private final NumberSetting breakDelay;
    private final EnumSetting explodeMode;
    private final BooleanSetting packetSync;
    private final NumberSetting packetDelay;
    private final BooleanSetting predictAttack;
    private final NumberSetting breakRange;
    private final BooleanSetting remove;
    private final NumberSetting minExisted;
    private final BooleanSetting basePlace;
    private final NumberSetting basePlaceRange;
    private final NumberSetting baseDelay;
    private final NumberSetting baseMin;
    private final NumberSetting baseMax;
    private final NumberSetting overrideMax;
    private final BooleanSetting baseBalance;
    private final BooleanSetting onlyBelow;
    private final BooleanSetting onPlace;
    private final BooleanSetting onBreak;
    private final EnumSetting rotationBypass;
    private final NumberSetting hitboxExpand;
    private final EnumSetting rotateMode;
    private final EnumSetting switchMode;
    private final BooleanSetting placeTWall;
    private final NumberSetting pWallRange;
    private final BooleanSetting breakTWall;
    private final NumberSetting bWallRange;
    private final NumberSetting extrapolate;
    private final NumberSetting maxMotionXZ;
    private final NumberSetting maxMotionY;
    private final NumberSetting selfPredTicks;
    private final EnumSetting placePosMode;
    private final NumberSetting posUpdateDelay;
    private final NumberSetting targetRange;
    private final BooleanSetting balance;
    private final NumberSetting balanceOffset;
    private final BooleanSetting eatingPause;
    private final BooleanSetting guiPause;
    private final BooleanSetting ignoreTerrain;
    private final BooleanSetting render;
    private final BooleanSetting shrink;
    private final ColorSetting fill;
    private final ColorSetting box;
    private final NumberSetting sliderSpeed;
    private final NumberSetting startFade;
    private final NumberSetting fadeSpeed;
    private final BooleanSetting damageText;
    private final ColorSetting textColor;
    private boolean flag68;
    private final Stopwatch helper724;
    private final Stopwatch helper79;
    private volatile BlockPos blockPos6;
    private volatile BlockPos blockPos13;
    private volatile BlockPos blockPos11;
    private volatile EndCrystalEntity field61;
    private volatile float value162;
    private boolean flag81;
    private long time26;
    private long time41;
    private long time8;
    private long time9;
    private long time39;
    private Vec3d vec3d8;
    private Vec3d vec3d11;
    private double value157;
    private Matrix4f matrix4f8;
    private Matrix4f matrix4f13;
    private Vec3d vec3d7;
    private Vec3d vec3d5;
    private Vec3d vec3d12;
    private final DecimalFormat decimalFormat2;
    private static String text3500;

        public ZealotCrystalPlus() {
        super("ZealotCrystalPlus", "High performance Crystal Aura with fixed Sync/Both.", Category.COMBAT);
        this.page = (EnumSetting)this.registerSetting(new EnumSetting("Page", PageMode.Place));
        this.placeDelay = (NumberSetting)this.registerSetting(new NumberSetting("PlaceDelay", 50.0, 0.0, 1000.0, 10.0));
        this.placeMode = (EnumSetting)this.registerSetting(new EnumSetting("PlaceMode", EMode5.Strong));
        this.placeRange = (NumberSetting)this.registerSetting(new NumberSetting("PlaceRange", 5.0, 0.0, 6.0, 0.1));
        this.minDamage = (NumberSetting)this.registerSetting(new NumberSetting("MinDamage", 5.0, 0.0, 36.0, 0.5));
        this.maxSelfDmg = (NumberSetting)this.registerSetting(new NumberSetting("MaxSelfDmg", 12.0, 0.0, 36.0, 0.5));
        this.reserve = (NumberSetting)this.registerSetting(new NumberSetting("Reserve", 2.0, 0.0, 10.0, 0.1));
        this.breakDelay = (NumberSetting)this.registerSetting(new NumberSetting("BreakDelay", 50.0, 0.0, 1000.0, 10.0));
        this.explodeMode = (EnumSetting)this.registerSetting(new EnumSetting("ExplodeMode", EMode.Both));
        this.packetSync = (BooleanSetting)this.registerSetting(new BooleanSetting("PacketSync", false));
        this.packetDelay = (NumberSetting)this.registerSetting(new NumberSetting("PacketDelay", 35.0, 0.0, 50.0, 1.0));
        this.predictAttack = (BooleanSetting)this.registerSetting(new BooleanSetting("PredictAttack", true));
        this.breakRange = (NumberSetting)this.registerSetting(new NumberSetting("BreakRange", 4.0, 0.0, 6.0, 0.1));
        this.remove = (BooleanSetting)this.registerSetting(new BooleanSetting("Remove", false));
        this.minExisted = (NumberSetting)this.registerSetting(new NumberSetting("MinExisted", 0.0, 0.0, 10.0, 1.0));
        this.basePlace = (BooleanSetting)this.registerSetting(new BooleanSetting("BasePlace", true));
        this.basePlaceRange = (NumberSetting)this.registerSetting(new NumberSetting("BasePlaceRange", 5.0, 0.0, 6.0, 0.1));
        this.baseDelay = (NumberSetting)this.registerSetting(new NumberSetting("BaseDelay", 3000.0, 0.0, 10000.0, 100.0));
        this.baseMin = (NumberSetting)this.registerSetting(new NumberSetting("BaseMin", 6.0, 0.0, 36.0, 0.1));
        this.baseMax = (NumberSetting)this.registerSetting(new NumberSetting("BaseMax", 12.0, 0.0, 36.0, 0.1));
        this.overrideMax = (NumberSetting)this.registerSetting(new NumberSetting("OverrideMax", 8.0, 0.0, 36.0, 0.1));
        this.baseBalance = (BooleanSetting)this.registerSetting(new BooleanSetting("BaseBalance", true));
        this.onlyBelow = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyBelow", true));
        this.onPlace = (BooleanSetting)this.registerSetting(new BooleanSetting("OnPlace", true));
        this.onBreak = (BooleanSetting)this.registerSetting(new BooleanSetting("OnBreak", true));
        this.rotationBypass = (EnumSetting)this.registerSetting(new EnumSetting("RotationBypass", EMode2.Off));
        this.hitboxExpand = (NumberSetting)this.registerSetting(new NumberSetting("HitboxExpand", 0.5, 0.1, 2.0, 0.1));
        this.rotateMode = (EnumSetting)this.registerSetting(new EnumSetting("RotateMode", EMode3.DEFAULT));
        this.switchMode = (EnumSetting)this.registerSetting(new EnumSetting("SwitchMode", EMode4.DEFAULT));
        this.placeTWall = (BooleanSetting)this.registerSetting(new BooleanSetting("PlaceTWall", false));
        this.pWallRange = (NumberSetting)this.registerSetting(new NumberSetting("PWallRange", 3.0, 0.0, 6.0, 0.1));
        this.breakTWall = (BooleanSetting)this.registerSetting(new BooleanSetting("BreakTWall", false));
        this.bWallRange = (NumberSetting)this.registerSetting(new NumberSetting("BWallRange", 3.0, 0.0, 6.0, 0.1));
        this.extrapolate = (NumberSetting)this.registerSetting(new NumberSetting("Extrapolate", 0.0, 0.0, 10.0, 1.0));
        this.maxMotionXZ = (NumberSetting)this.registerSetting(new NumberSetting("MaxMotionXZ", 0.6, 0.0, 2.0, 0.01));
        this.maxMotionY = (NumberSetting)this.registerSetting(new NumberSetting("MaxMotionY", 0.34, 0.0, 2.0, 0.01));
        this.selfPredTicks = (NumberSetting)this.registerSetting(new NumberSetting("SelfPredTicks", 0.0, 0.0, 10.0, 1.0));
        this.placePosMode = (EnumSetting)this.registerSetting(new EnumSetting("PlacePosMode", EMode6.Single));
        this.posUpdateDelay = (NumberSetting)this.registerSetting(new NumberSetting("PosUpdateDelay", 50.0, 0.0, 5000.0, 10.0));
        this.targetRange = (NumberSetting)this.registerSetting(new NumberSetting("TargetRange", 12.0, 0.0, 20.0, 0.1));
        this.balance = (BooleanSetting)this.registerSetting(new BooleanSetting("Balance", true));
        this.balanceOffset = (NumberSetting)this.registerSetting(new NumberSetting("BalanceOffset", 0.0, -20.0, 20.0, 0.1));
        this.eatingPause = (BooleanSetting)this.registerSetting(new BooleanSetting("EatingPause", true));
        this.guiPause = (BooleanSetting)this.registerSetting(new BooleanSetting("GuiPause", true));
        this.ignoreTerrain = (BooleanSetting)this.registerSetting(new BooleanSetting("IgnoreTerrain", false));
        this.render = (BooleanSetting)this.registerSetting(new BooleanSetting("Render", true));
        this.shrink = (BooleanSetting)this.registerSetting(new BooleanSetting("Shrink", true));
        this.fill = (ColorSetting)this.registerSetting(new ColorSetting("Fill", 0x64FFFFFF));
        this.box = (ColorSetting)this.registerSetting(new ColorSetting("Box", -1));
        this.sliderSpeed = (NumberSetting)this.registerSetting(new NumberSetting("SliderSpeed", 0.2, 0.01, 1.0, 0.01));
        this.startFade = (NumberSetting)this.registerSetting(new NumberSetting("StartFade", 0.3, 0.0, 2.0, 0.01));
        this.fadeSpeed = (NumberSetting)this.registerSetting(new NumberSetting("FadeSpeed", 0.2, 0.01, 1.0, 0.01));
        this.damageText = (BooleanSetting)this.registerSetting(new BooleanSetting("DamageText", true));
        this.textColor = (ColorSetting)this.registerSetting(new ColorSetting("TextColor", -1));
        this.flag68 = false;
        this.helper724 = new Stopwatch();
        this.helper79 = new Stopwatch();
        this.blockPos6 = null;
        this.blockPos13 = null;
        this.blockPos11 = null;
        this.field61 = null;
        this.value162 = 0.0f;
        this.flag81 = false;
        this.time26 = 0L;
        this.time41 = 0L;
        this.time8 = 0L;
        this.time9 = 0L;
        this.time39 = 0L;
        this.vec3d8 = null;
        this.vec3d11 = null;
        this.value157 = 0.0;
        this.vec3d7 = null;
        this.vec3d5 = null;
        this.vec3d12 = null;
        this.decimalFormat2 = new DecimalFormat("0.0");
    }

    @Override
    public String getInfo() {
        Object var2_1 = null;
        return this.value162 > 0.0f ? this.decimalFormat2.format(this.value162) : null;
    }

    @Override
    public void onDisable() {
        Client.mathUtil.resetRotation();
        Client.itemSwitcher.restoreSlot();
        this.blockPos6 = null;
        this.blockPos13 = null;
        this.blockPos11 = null;
        this.field61 = null;
        this.value162 = 0.0f;
        this.flag81 = false;
        this.vec3d8 = null;
        this.vec3d11 = null;
        this.value157 = 0.0;
        this.vec3d7 = null;
        this.vec3d5 = null;
        this.vec3d12 = null;
        this.flag68 = false;
    }

    private boolean isSet153() {
        Object var2_1 = null;
        if (((Boolean)this.eatingPause.getValue()).booleanValue()) {
            if (MC.mc.player.isUsingItem()) {
                return true;
            }
        }
        if (((Boolean)this.guiPause.getValue()).booleanValue()) {
            if (MC.mc.currentScreen instanceof HandledScreen) {
                if (!(MC.mc.currentScreen instanceof InventoryScreen)) {
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler
    private void setEvent2Inner54(TickEvent.PreTick event2Inner) {
        long l;
        if (Module.isNotInGame() || MC.mc.player.isSpectator()) {
            return;
        }
        if (this.isSet153()) {
            this.blockPos6 = null;
            this.blockPos13 = null;
            this.field61 = null;
            this.vec3d5 = null;
            this.vec3d12 = null;
            return;
        }
        if ((this.explodeMode.getValue() == EMode.Sync || this.explodeMode.getValue() == EMode.Both) && ((Boolean)this.packetSync.getValue()).booleanValue() && this.helper724.hasPassedMs(this.packetDelay.getLong())) {
            this.flag68 = true;
            this.helper724.resetTimer();
        }
        if ((double)((l = System.currentTimeMillis()) - this.time9) >= (Double)this.posUpdateDelay.getValue()) {
            this.time9 = l;
            this.m426();
            if (this.blockPos6 != null) {
                this.time39 = l;
            }
        }
        this.vec3d5 = null;
        this.vec3d12 = null;
        this.m546();
    }

    @EventHandler
    private void setPacketEventInner11(PacketEvent.PacketEventInner packetEventInner) {
        float f;
        double d;
        double d2;
        boolean bl;
        boolean bl2;
        if (Module.isNotInGame() || this.isSet153() || this.blockPos13 != null) {
            return;
        }
        Packet packet = packetEventInner.getPacket();
        if (!(packet instanceof EntitySpawnS2CPacket)) {
            return;
        }
        EntitySpawnS2CPacket entitySpawnS2CPacket = (EntitySpawnS2CPacket)packet;
        if (entitySpawnS2CPacket.getEntityType() != EntityType.END_CRYSTAL) {
            return;
        }
        if (this.explodeMode.getValue() == EMode.Normal) {
            return;
        }
        boolean bl3 = bl2 = this.explodeMode.getValue() == EMode.Sync || this.explodeMode.getValue() == EMode.Both;
        if (!bl2) {
            return;
        }
        boolean bl4 = bl = (Boolean)this.packetSync.getValue() == false && this.helper79.hasPassedMs(this.packetDelay.getLong()) || (Boolean)this.packetSync.getValue() != false && this.flag68;
        if (!bl) {
            return;
        }
        BlockPos blockPos = this.blockPos6;
        if (blockPos == null) {
            return;
        }
        double d3 = entitySpawnS2CPacket.getX();
        if (!this.m532(blockPos, d3, d2 = entitySpawnS2CPacket.getY(), d = entitySpawnS2CPacket.getZ())) {
            return;
        }
        Vec3d vec3d = new Vec3d((double)blockPos.getX() + 0.5, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5);
        float f2 = this.maxMotionXZ.getFloat();
        float f3 = this.maxMotionY.getFloat();
        Vec3d vec3d2 = this.m1020(MC.mc.player, f2, f3, this.selfPredTicks.getInt());
        Box box = this.m448(MC.mc.player.getBoundingBox(), vec3d2, MC.mc.player.getEntityPos());
        Vec4f vec4f = this.m1014(MC.mc.player);
        float f4 = f = MC.mc.player.isCreative() ? 0.0f : this.m877(vec3d, vec3d2, box, vec4f, (Boolean)this.ignoreTerrain.getValue(), MC.mc.world.getDifficulty());
        if ((double)f > (Double)this.maxSelfDmg.getValue()) {
            return;
        }
        if ((Double)this.reserve.getValue() > 0.0 && (double)f > (double)ItemUtil.m158(MC.mc.player) - (Double)this.reserve.getValue()) {
            return;
        }
        EndCrystalEntity endCrystalEntity = new EndCrystalEntity((World)MC.mc.world, d3, d2, d);
        endCrystalEntity.setId(entitySpawnS2CPacket.getEntityId());
        MC.mc.player.networkHandler.sendPacket((Packet)PlayerInteractEntityC2SPacket.attack((Entity)endCrystalEntity, (boolean)MC.mc.player.isSneaking()));
        MC.mc.player.swingHand(this.getObj7(), false);
        this.time26 = System.currentTimeMillis();
        if (((Boolean)this.packetSync.getValue()).booleanValue()) {
            this.flag68 = false;
        }
        this.helper79.resetTimer();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m532(Object object, double d, double d2, double d3) {
        BlockPos blockPos = (BlockPos)object;
        double d4 = d;
        double d5 = d2;
        double d6 = d3;
        Object var16_9 = null;
        if (!(Math.abs(d4 - ((double)blockPos.getX() + 0.5)) < 1.0)) return false;
        if (!(Math.abs(d6 - ((double)blockPos.getZ() + 0.5)) < 1.0)) return false;
        if (!(Math.abs(d5 - (double)blockPos.getY()) < 2.0)) return false;
        return true;
    }

    private void m546() {
        BlockPos blockPos;
        block13: {
            EndCrystalEntity endCrystalEntity;
            block14: {
                blockPos = this.blockPos6;
                BlockPos blockPos2 = this.blockPos13;
                endCrystalEntity = this.field61;
                Object var2_4 = null;
                if (blockPos2 != null) {
                    if (((Boolean)this.onPlace.getValue()).booleanValue()) {
                        this.setObj101(this.m4(blockPos2));
                    }
                    this.setObj65(blockPos2);
                    return;
                }
                if (endCrystalEntity == null) break block13;
                if (this.explodeMode.getValue() == EMode.Normal) break block14;
                if (this.explodeMode.getValue() != EMode.Both) break block13;
            }
            if (((Boolean)this.onBreak.getValue()).booleanValue()) {
                this.m551(this.m949(endCrystalEntity), endCrystalEntity);
            }
            this.setObj112(endCrystalEntity);
            if (this.placePosMode.getValue() == EMode6.Double) {
                this.flag81 = !this.flag81;
            }
        }
        BlockPos blockPos3 = blockPos;
        if (this.placePosMode.getValue() == EMode6.Double) {
            if (this.flag81) {
                if (this.blockPos11 != null) {
                    blockPos3 = this.blockPos11;
                }
            }
        }
        if (blockPos3 != null) {
            if (this.m758(blockPos3)) {
                return;
            }
            if (((Boolean)this.onPlace.getValue()).booleanValue()) {
                this.setObj101(this.m240(blockPos3));
            }
            this.setObj33(blockPos3);
        }
    }

    private Vec3d m783(Object object) {
        BlockPos blockPos = (BlockPos)object;
        return new Vec3d((double)blockPos.getX() + 0.5, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5);
    }

    private Vec3d m997(Object object, Object object2) {
        BlockPos blockPos = (BlockPos)object;
        Direction direction = (Direction)object2;
        return Vec3d.ofCenter((Vec3i)blockPos).add((double)direction.getOffsetX() * 0.5, (double)direction.getOffsetY() * 0.5, (double)direction.getOffsetZ() * 0.5);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m386(Object object, Object object2) {
        BlockPos blockPos = (BlockPos)object;
        Direction direction = (Direction)object2;
        Vec3d vec3d = MC.mc.player.getEyePos();
        Vec3d vec3d2 = this.m997(blockPos, direction);
        Object var6_7 = null;
        BlockHitResult blockHitResult = MC.mc.world.raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, (Entity)MC.mc.player));
        if (blockHitResult.getType() != HitResult.Type.BLOCK) return false;
        if (!blockHitResult.getBlockPos().equals((Object)blockPos)) return false;
        return true;
    }

    private Direction m779(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Direction direction = null;
        double d = Double.MAX_VALUE;
        Vec3d vec3d = MC.mc.player.getEyePos();
        Direction[] directionArray = Direction.values();
        int n = directionArray.length;
        Object var4_9 = null;
        for (int i = 0; i < n; ++i) {
            Direction direction2 = directionArray[i];
            Vec3d vec3d2 = this.m997(blockPos, direction2);
            double d2 = vec3d.squaredDistanceTo(vec3d2);
            if (!this.m386(blockPos, direction2) || !(d2 < d)) continue;
            d = d2;
            direction = direction2;
            if (null == null) continue;
        }
        return direction;
    }

    private Direction m999(Object object, double d) {
        BlockPos blockPos = (BlockPos)object;
        double d2 = d;
        Direction direction = null;
        double d3 = Double.MAX_VALUE;
        Vec3d vec3d = MC.mc.player.getEyePos();
        double d4 = d2 * d2;
        Direction[] directionArray = Direction.values();
        Object var8_10 = null;
        int n = directionArray.length;
        for (int i = 0; i < n; ++i) {
            Direction direction2 = directionArray[i];
            BlockPos blockPos2 = blockPos.offset(direction2);
            BlockState blockState = MC.mc.world.getBlockState(blockPos2);
            if (blockState.isAir() || blockState.isReplaceable()) continue;
            Direction direction3 = direction2.getOpposite();
            Vec3d vec3d2 = this.m997(blockPos2, direction3);
            double d5 = vec3d.squaredDistanceTo(vec3d2);
            if (d5 > d4 || !this.m386(blockPos2, direction3) || !(d5 < d3)) continue;
            d3 = d5;
            direction = direction2;
            if (null == null) continue;
        }
        return direction;
    }

    private boolean m957(Object object, double d) {
        BlockPos blockPos = (BlockPos)object;
        double d2 = d;
        Direction direction = this.m779(blockPos);
        Object var8_6 = null;
        if (direction == null) {
            return false;
        }
        return MC.mc.player.getEyePos().distanceTo(this.m997(blockPos, direction)) <= d2;
    }

    private boolean m269(Object object, Object object2) {
        BlockPos blockPos = (BlockPos)object;
        Vec3d vec3d = (Vec3d)object2;
        Vec3d vec3d2 = MC.mc.player.getEyePos();
        Object var6_6 = null;
        Vec3d vec3d3 = new Vec3d((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 1.7, (double)blockPos.getZ() + 0.5);
        BlockHitResult blockHitResult = MC.mc.world.raycast(new RaycastContext(vec3d2, vec3d3, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, (Entity)MC.mc.player));
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return false;
        }
        if (((Boolean)this.placeTWall.getValue()).booleanValue()) {
            return vec3d2.distanceTo(vec3d) > (Double)this.pWallRange.getValue();
        }
        return true;
    }

    private List m86(double d, Object object, boolean bl) {
        int n;
        int n2;
        double d2;
        ArrayList<BlockPos> arrayList;
        Vec3d vec3d;
        double d3;
        int n3;
        int n4;
        block6: {
            block5: {
                d3 = d;
                vec3d = (Vec3d)object;
                boolean bl2 = bl;
                arrayList = new ArrayList<BlockPos>();
                Object var10_8 = null;
                d2 = d3 * d3;
                n4 = MathHelper.floor((double)(vec3d.x - d3));
                n2 = MathHelper.floor((double)(vec3d.x + d3));
                n = Math.max(MC.mc.world.getBottomY(), MathHelper.floor((double)(vec3d.y - d3)));
                if (!bl2) break block5;
                n3 = Math.min(MC.mc.world.getBottomY() + MC.mc.world.getHeight(), MathHelper.floor((double)(vec3d.y + d3)));
                if (null == null) break block6;
            }
            n3 = Math.min(MC.mc.world.getBottomY() + MC.mc.world.getHeight() - 1, MathHelper.floor((double)(vec3d.y + d3)));
        }
        int n5 = MathHelper.floor((double)(vec3d.z - d3));
        int n6 = MathHelper.floor((double)(vec3d.z + d3));
        for (int i = n3; i >= n; --i) {
            for (int j = n4; j <= n2; ++j) {
                for (int k = n5; k <= n6; ++k) {
                    BlockPos blockPos = new BlockPos(j, i, k);
                    if (!(Vec3d.ofCenter((Vec3i)blockPos).squaredDistanceTo(vec3d) <= d2)) continue;
                    arrayList.add(blockPos);
                    if (null == null) continue;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
        return arrayList;
    }

    private boolean m40(Object object) {
        BlockPos blockPos = (BlockPos)object;
        BlockPos blockPos2 = blockPos.down();
        BlockState blockState = MC.mc.world.getBlockState(blockPos2);
        Object var4_5 = null;
        if (!blockState.isOf(Blocks.OBSIDIAN)) {
            if (!blockState.isOf(Blocks.BEDROCK)) {
                return false;
            }
        }
        if (this.m779(blockPos2) == null) {
            return false;
        }
        BlockState blockState2 = MC.mc.world.getBlockState(blockPos);
        if (!blockState2.isAir()) {
            if (!blockState2.isOf(Blocks.FIRE)) {
                return false;
            }
        }
        if (!this.m925(blockPos)) {
            return false;
        }
        return this.m925(blockPos.up());
    }

    private boolean m205(Object object) {
        BlockPos blockPos = (BlockPos)object;
        BlockState blockState = MC.mc.world.getBlockState(blockPos);
        Object var4_4 = null;
        if (!blockState.isAir()) {
            if (!blockState.isOf(Blocks.FIRE)) {
                return false;
            }
        }
        if (!this.m925(blockPos)) {
            return false;
        }
        return this.m925(blockPos.up());
    }

    private boolean m662(Object object) {
        BlockPos blockPos = (BlockPos)object;
        BlockState blockState = MC.mc.world.getBlockState(blockPos);
        Object var4_4 = null;
        if (!blockState.isAir()) {
            if (!blockState.isReplaceable()) {
                return false;
            }
        }
        for (Entity entity : MC.mc.world.getOtherEntities(null, new Box(blockPos))) {
            if (!entity.isAlive() || entity instanceof ItemEntity) continue;
            return false;
        }
        return this.m999(blockPos, (Double)this.basePlaceRange.getValue()) != null;
    }

    private boolean m925(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Iterator iterator = MC.mc.world.getOtherEntities(null, new Box(blockPos)).iterator();
        Object var4_4 = null;
        while (iterator.hasNext()) {
            Entity entity = (Entity)iterator.next();
            if (!entity.isAlive() || entity instanceof ItemEntity || entity instanceof EndCrystalEntity) continue;
            return false;
        }
        return true;
    }

    private boolean m982(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Box box = new Box((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), (double)(blockPos.getX() + 1), (double)(blockPos.getY() + 2), (double)(blockPos.getZ() + 1));
        Object var4_4 = null;
        for (Entity entity : MC.mc.world.getOtherEntities(null, box)) {
            if (entity instanceof EndCrystalEntity) {
                EndCrystalEntity endCrystalEntity = (EndCrystalEntity)entity;
                if (endCrystalEntity.isAlive()) {
                    return true;
                }
            }
            if (null == null) continue;
        }
        return false;
    }

    private List m679(double d) {
        double d2 = d;
        ArrayList<PlayerEntity> arrayList = new ArrayList<PlayerEntity>();
        Object var6_4 = null;
        if (MC.mc.world == null || MC.mc.player == null) {
            return arrayList;
        }
        double d3 = d2 * d2;
        for (Entity entity : MC.mc.world.getOtherEntities((Entity)MC.mc.player, MC.mc.player.getBoundingBox().expand(d2))) {
            if (!(entity instanceof PlayerEntity)) continue;
            PlayerEntity playerEntity = (PlayerEntity)entity;
            if (playerEntity == MC.mc.player || !playerEntity.isAlive()) continue;
            if (playerEntity.isSpectator() || Client.friendManager.isFriend(playerEntity.getName().getString()) || MC.mc.player.squaredDistanceTo((Entity)playerEntity) > d3) continue;
            arrayList.add(playerEntity);
            if (null == null) continue;
        }
        return arrayList;
    }

    private float m1017(Object object, Object object2) {
        double d;
        double d2;
        double d3;
        double d4;
        double d5;
        Box box;
        Vec3d vec3d;
        block8: {
            block7: {
                vec3d = (Vec3d)object;
                box = (Box)object2;
                d5 = box.maxX - box.minX;
                double d6 = box.maxY - box.minY;
                d4 = box.maxZ - box.minZ;
                d3 = 1.0 / (d5 * 2.0 + 1.0);
                Object var6_9 = null;
                d2 = 1.0 / (d6 * 2.0 + 1.0);
                d = 1.0 / (d4 * 2.0 + 1.0);
                if (d3 <= 0.0) break block7;
                if (d2 <= 0.0) break block7;
                if (!(d <= 0.0)) break block8;
            }
            return 0.0f;
        }
        double d7 = (1.0 - Math.floor(1.0 / d3) * d3) / 2.0;
        double d8 = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
        int n = 0;
        int n2 = 0;
        for (double d9 = 0.0; d9 <= 1.0; d9 += d3) {
            for (double d10 = 0.0; d10 <= 1.0; d10 += d2) {
                for (double d11 = 0.0; d11 <= 1.0; d11 += d) {
                    double d12 = box.minX + (box.maxX - box.minX) * d9 + d7 * d5;
                    double d13 = box.minY + (box.maxY - box.minY) * d10;
                    double d14 = box.minZ + (box.maxZ - box.minZ) * d11 + d8 * d4;
                    BlockHitResult blockHitResult = MC.mc.world.raycast(new RaycastContext(new Vec3d(d12, d13, d14), vec3d, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, (Entity)MC.mc.player));
                    if (blockHitResult.getType() == HitResult.Type.MISS) {
                        ++n2;
                    }
                    ++n;
                    if (null == null) continue;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
            break;
        }
        return n == 0 ? 0.0f : (float)n2 / (float)n;
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private void m426() {
        BlockPos placePos = null;
        BlockPos basePos = null;
        BlockPos doublePos = null;
        EndCrystalEntity bestCrystal = null;
        float placeDmg = 0.0f;
        float crystalDmg = 0.0f;
        float baseDmg = 0.0f;
        List list = this.m679((Double)this.targetRange.getValue());
        if (list.isEmpty()) {
            this.blockPos6 = null;
            this.blockPos11 = null;
            this.blockPos13 = null;
            this.field61 = null;
            this.value162 = 0.0f;
            return;
        }
        Vec3d eyePos = MC.mc.player.getEyePos();
        Difficulty difficulty = MC.mc.world.getDifficulty();
        float mmXZ = this.maxMotionXZ.getFloat();
        float mmY = this.maxMotionY.getFloat();
        Vec3d selfVec = this.m1020(MC.mc.player, mmXZ, mmY, this.selfPredTicks.getInt());
        Box selfBox = this.m448(MC.mc.player.getBoundingBox(), selfVec, MC.mc.player.getEntityPos());
        Vec4f selfArmor = this.m1014(MC.mc.player);
        ArrayList<PositionData> targets = new ArrayList<PositionData>();
        for (Object o : list) {
            PlayerEntity target = (PlayerEntity)o;
            Vec3d tVec = this.m1020(target, mmXZ, mmY, this.extrapolate.getInt());
            Box tBox = this.m448(target.getBoundingBox(), tVec, target.getEntityPos());
            targets.add(new PositionData(target, tVec, tBox, this.m1014(target)));
        }
        Box searchBox = MC.mc.player.getBoundingBox().expand((Double)this.breakRange.getValue() + 1.0);
        for (Entity entity : MC.mc.world.getOtherEntities((Entity)MC.mc.player, searchBox)) {
            if (!(entity instanceof EndCrystalEntity)) continue;
            EndCrystalEntity crystal = (EndCrystalEntity)entity;
            if (!crystal.isAlive() || crystal.age < this.minExisted.getInt()) continue;
            Vec3d hitVec = this.m129(crystal.getEntityPos(), eyePos, (Double)this.breakRange.getValue());
            if (hitVec == null) continue;
            double distSq = eyePos.squaredDistanceTo(hitVec);
            if (distSq > (Double)this.breakRange.getValue() * (Double)this.breakRange.getValue()) continue;
            if (!MC.mc.player.canSee((Entity)crystal) && (!((Boolean)this.breakTWall.getValue()).booleanValue() || distSq > (Double)this.bWallRange.getValue() * (Double)this.bWallRange.getValue())) continue;
            float selfDmg = MC.mc.player.isCreative() ? 0.0f : this.m877(crystal.getEntityPos(), selfVec, selfBox, selfArmor, ((Boolean)this.ignoreTerrain.getValue()).booleanValue(), difficulty);
            if ((double)selfDmg > (Double)this.maxSelfDmg.getValue()) continue;
            if ((Double)this.reserve.getValue() > 0.0 && (double)selfDmg > (double)ItemUtil.m158(MC.mc.player) - (Double)this.reserve.getValue()) continue;
            for (PositionData pd : targets) {
                float dmg = this.m877(crystal.getEntityPos(), pd.getVec3d(), pd.box3(), pd.vec4f(), ((Boolean)this.ignoreTerrain.getValue()).booleanValue(), difficulty);
                if (dmg > crystalDmg && dmg >= (Double)this.minDamage.getValue() && (!((Boolean)this.balance.getValue()).booleanValue() || dmg >= selfDmg + (Double)this.balanceOffset.getValue())) {
                    crystalDmg = dmg;
                    bestCrystal = crystal;
                }
            }
        }
        for (Object o : this.m86((Double)this.placeRange.getValue() + 1.5, eyePos, true)) {
            BlockPos pos = (BlockPos)o;
            if (!this.m40(pos)) continue;
            Vec3d placeVec = this.m783(pos);
            Vec3d hitVec = this.m129(placeVec, eyePos, (Double)this.breakRange.getValue());
            if (hitVec == null || this.m269(pos, hitVec) || !this.m957(pos.down(), (Double)this.placeRange.getValue())) continue;
            float selfDmg = MC.mc.player.isCreative() ? 0.0f : this.m877(placeVec, selfVec, selfBox, selfArmor, ((Boolean)this.ignoreTerrain.getValue()).booleanValue(), difficulty);
            if ((double)selfDmg > (Double)this.maxSelfDmg.getValue()) continue;
            if ((Double)this.reserve.getValue() > 0.0 && (double)selfDmg > (double)ItemUtil.m158(MC.mc.player) - (Double)this.reserve.getValue()) continue;
            for (PositionData pd : targets) {
                float dmg = this.m877(placeVec, pd.getVec3d(), pd.box3(), pd.vec4f(), ((Boolean)this.ignoreTerrain.getValue()).booleanValue(), difficulty);
                if (dmg > placeDmg && dmg >= (Double)this.minDamage.getValue() && (!((Boolean)this.balance.getValue()).booleanValue() || dmg >= selfDmg + (Double)this.balanceOffset.getValue())) {
                    placeDmg = dmg;
                    placePos = pos;
                }
            }
        }
        if (((Boolean)this.basePlace.getValue()).booleanValue() && placeDmg <= (Double)this.overrideMax.getValue()) {
            for (Object o : this.m86((Double)this.basePlaceRange.getValue() + 1.5, eyePos, true)) {
                BlockPos pos = (BlockPos)o;
                BlockPos below = pos.down();
                if (!this.m662(below) || !this.m205(pos)) continue;
                Vec3d placeVec = this.m783(pos);
                Vec3d hitVec = this.m129(placeVec, eyePos, (Double)this.breakRange.getValue());
                if (hitVec == null || this.m269(pos, hitVec)) continue;
                float selfDmg = MC.mc.player.isCreative() ? 0.0f : this.m877(placeVec, selfVec, selfBox, selfArmor, ((Boolean)this.ignoreTerrain.getValue()).booleanValue(), difficulty);
                if ((double)selfDmg > (Double)this.baseMax.getValue()) continue;
                if ((Double)this.reserve.getValue() > 0.0 && (double)selfDmg > (double)ItemUtil.m158(MC.mc.player) - (Double)this.reserve.getValue()) continue;
                for (PositionData pd : targets) {
                    if (((Boolean)this.onlyBelow.getValue()).booleanValue() && (double)pos.getY() - 0.5 > pd.player().getY()) continue;
                    float dmg = this.m877(placeVec, pd.getVec3d(), pd.box3(), pd.vec4f(), ((Boolean)this.ignoreTerrain.getValue()).booleanValue(), difficulty);
                    boolean cond1 = dmg >= ItemUtil.m158(pd.player());
                    boolean cond2 = dmg >= (Double)this.baseMin.getValue() && (!((Boolean)this.baseBalance.getValue()).booleanValue() || dmg >= selfDmg);
                    if (dmg > placeDmg && dmg > baseDmg && (cond1 || cond2)) {
                        baseDmg = dmg;
                        basePos = below;
                        placePos = null;
                        doublePos = null;
                    }
                }
            }
        }
        if (this.placePosMode.getValue() == EMode6.Double && placePos != null) {
            BlockPos firstPos = placePos;
            block_double:
            for (Object o : this.m86((Double)this.placeRange.getValue() + 1.5, eyePos, true)) {
                BlockPos pos = (BlockPos)o;
                if (pos.equals((Object)firstPos) || !this.m40(pos)) continue;
                Vec3d placeVec = this.m783(pos);
                Vec3d hitVec = this.m129(placeVec, eyePos, (Double)this.breakRange.getValue());
                if (hitVec == null || this.m269(pos, hitVec) || !this.m957(pos.down(), (Double)this.placeRange.getValue())) continue;
                float selfDmg = MC.mc.player.isCreative() ? 0.0f : this.m877(placeVec, selfVec, selfBox, selfArmor, ((Boolean)this.ignoreTerrain.getValue()).booleanValue(), difficulty);
                if ((double)selfDmg > (Double)this.maxSelfDmg.getValue()) continue;
                if ((Double)this.reserve.getValue() > 0.0 && (double)selfDmg > (double)ItemUtil.m158(MC.mc.player) - (Double)this.reserve.getValue()) continue;
                for (PositionData pd : targets) {
                    float dmg = this.m877(placeVec, pd.getVec3d(), pd.box3(), pd.vec4f(), ((Boolean)this.ignoreTerrain.getValue()).booleanValue(), difficulty);
                    if (dmg >= (Double)this.minDamage.getValue() && (!((Boolean)this.balance.getValue()).booleanValue() || dmg >= selfDmg + (Double)this.balanceOffset.getValue())) {
                        doublePos = pos;
                        break block_double;
                    }
                }
            }
        }
        this.field61 = bestCrystal;
        this.blockPos6 = placePos;
        this.blockPos13 = basePos;
        this.blockPos11 = doublePos;
        this.value162 = placeDmg;
    }

    private void setObj112(Object object) {
        EndCrystalEntity endCrystalEntity;
        block17: {
            block18: {
                block16: {
                    block15: {
                        endCrystalEntity = (EndCrystalEntity)object;
                        Object var4_3 = null;
                        if (endCrystalEntity == null) break block15;
                        if (endCrystalEntity.isAlive()) break block16;
                    }
                    return;
                }
                if ((double)(System.currentTimeMillis() - this.time26) < (Double)this.breakDelay.getValue()) {
                    return;
                }
                Vec3d vec3d = this.m949(endCrystalEntity);
                double d = MC.mc.player.getEyePos().distanceTo(vec3d);
                if (d > (Double)this.breakRange.getValue()) {
                    return;
                }
                if (this.rotationBypass.getValue() == EMode2.ExtraHitbox) {
                    if (this.vec3d5 != null) {
                        if (!this.m563(endCrystalEntity, this.vec3d5)) {
                            return;
                        }
                    }
                }
                if (MC.mc.player.canSee((Entity)endCrystalEntity)) break block17;
                if (!((Boolean)this.breakTWall.getValue()).booleanValue()) break block18;
                if (!(d > (Double)this.bWallRange.getValue())) break block17;
            }
            return;
        }
        MC.mc.interactionManager.attackEntity((PlayerEntity)MC.mc.player, (Entity)endCrystalEntity);
        MC.mc.player.swingHand(this.getObj7(), false);
        if (((Boolean)this.remove.getValue()).booleanValue()) {
            MC.mc.world.removeEntity(endCrystalEntity.getId(), Entity.RemovalReason.KILLED);
        }
        this.time26 = System.currentTimeMillis();
        this.helper79.resetTimer();
        if (this.placeMode.getValue() == EMode5.Strong) {
            if (this.blockPos6 != null) {
                BlockPos blockPos = this.blockPos6;
                if (this.m532(blockPos, endCrystalEntity.getX(), endCrystalEntity.getY(), endCrystalEntity.getZ())) {
                    this.setObj33(blockPos);
                }
            }
        }
    }

    private boolean m758(Object object) {
        BlockPos blockPos3;
        BlockPos blockPos2 = (BlockPos)object;
        Object var4_3 = null;
        if (blockPos2 == null) {
            return false;
        }
        ArrayList<Box> arrayList = new ArrayList<Box>();
        arrayList.add(new Box(blockPos2));
        arrayList.add(new Box(blockPos2.up()));
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0) continue;
                blockPos3 = blockPos2.add(i, 0, j);
                arrayList.add(new Box(blockPos3));
                arrayList.add(new Box(blockPos3.up()));
                if (null == null) continue;
            }
            if (null == null) continue;
        }
        ArrayList<EndCrystalEntity> arrayList2 = new ArrayList<EndCrystalEntity>();
        for (Entity entity : MC.mc.world.getOtherEntities(null, new Box(blockPos2).expand(2.0))) {
            EndCrystalEntity endCrystalEntity;
            if (!(entity instanceof EndCrystalEntity) || !(endCrystalEntity = (EndCrystalEntity)entity).isAlive()) continue;
            for (Box box : arrayList) {
                if (endCrystalEntity.getBoundingBox().intersects(box)) {
                    if (arrayList2.contains(endCrystalEntity)) break;
                    arrayList2.add(endCrystalEntity);
                    if (null == null) break;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
            break;
        }
        if (arrayList2.isEmpty()) {
            return false;
        }
        Vec3d vec3d = MC.mc.player.getEyePos();
        Entity entityBest = null;
        double d = Double.MAX_VALUE;
        for (EndCrystalEntity endCrystalEntity : arrayList2) {
            double d2;
            Vec3d vec3d2 = this.m129(endCrystalEntity.getEntityPos(), vec3d, (Double)this.breakRange.getValue());
            if (vec3d2 == null || (d2 = vec3d.distanceTo(vec3d2)) > (Double)this.breakRange.getValue()) continue;
            if (this.rotationBypass.getValue() == EMode2.ExtraHitbox) {
                if (this.vec3d5 != null) {
                    if (!this.m563(endCrystalEntity, this.vec3d5)) continue;
                }
            }
            if (!MC.mc.player.canSee((Entity)endCrystalEntity)) {
                if (!((Boolean)this.breakTWall.getValue()).booleanValue()) continue;
                if (d2 > (Double)this.bWallRange.getValue()) continue;
            }
            if (d2 < d) {
                d = d2;
                entityBest = endCrystalEntity;
            }
            if (null == null) continue;
            break;
        }
        if (entityBest != null) {
            if (((Boolean)this.onBreak.getValue()).booleanValue()) {
                this.m551(this.m949(entityBest), entityBest);
            }
            MC.mc.interactionManager.attackEntity((PlayerEntity)MC.mc.player, (Entity)entityBest);
            MC.mc.player.swingHand(this.getObj7(), false);
            if (((Boolean)this.remove.getValue()).booleanValue()) {
                MC.mc.world.removeEntity(entityBest.getId(), Entity.RemovalReason.KILLED);
            }
            this.time26 = System.currentTimeMillis();
            this.helper79.resetTimer();
            return true;
        }
        return false;
    }

    private void m564(Object object, Object object2) {
        Hand hand = (Hand)object;
        BlockHitResult blockHitResult = (BlockHitResult)object2;
        Object var6_5 = null;
        if (((Boolean)this.onPlace.getValue()).booleanValue()) {
            if (this.vec3d7 != null) {
                float[] fArray = MathUtil.getLookAngles(MC.mc.player.getEyePos(), this.vec3d7);
                MC.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.LookAndOnGround(fArray[0], fArray[1], MC.mc.player.isOnGround(), MC.mc.player.horizontalCollision));
            }
        }
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractBlockC2SPacket(hand, blockHitResult, 0));
    }

    private void setObj33(Object object) {
        block13: {
            BlockPos blockPos = (BlockPos)object;
            Object var4_3 = null;
            if (this.placeMode.getValue() == EMode5.EmptySettingRenderer) {
                return;
            }
            if ((double)(System.currentTimeMillis() - this.time41) < (Double)this.placeDelay.getValue()) {
                return;
            }
            if (!this.m40(blockPos)) {
                return;
            }
            if (this.m982(blockPos)) {
                return;
            }
            Hand hand = this.prepareHand(Items.END_CRYSTAL);
            if (hand == null) {
                return;
            }
            BlockPos blockPos2 = blockPos.down();
            Direction direction = this.m779(blockPos2);
            if (direction == null) {
                return;
            }
            Vec3d vec3d = this.m997(blockPos2, direction);
            BlockHitResult blockHitResult = new BlockHitResult(vec3d, direction, blockPos2, false);
            if (((Boolean)this.onPlace.getValue()).booleanValue()) {
                this.vec3d7 = vec3d;
            }
            this.m564(hand, blockHitResult);
            MC.mc.player.swingHand(hand, false);
            if (!((Boolean)this.predictAttack.getValue()).booleanValue()) break block13;
            Entity entity = null;
            for (Entity entity2 : MC.mc.world.getEntities()) {
                block15: {
                    block14: {
                        if (entity == null) break block14;
                        if (entity2.getId() <= entity.getId()) break block15;
                    }
                    entity = entity2;
                }
                if (null == null) continue;
            }
            if (entity != null) {
                for (int i = 1; i <= 3; ++i) {
                    Entity entity2;
                    entity2 = new EndCrystalEntity((World)MC.mc.world, (double)blockPos.getX() + 0.5, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5);
                    entity2.setId(entity.getId() + i);
                    MC.mc.player.networkHandler.sendPacket((Packet)PlayerInteractEntityC2SPacket.attack((Entity)entity2, (boolean)MC.mc.player.isSneaking()));
                    if (null == null) continue;
                }
            }
        }
        this.m350();
        this.time41 = System.currentTimeMillis();
    }

    private void setObj65(Object object) {
        BlockPos blockPos;
        block9: {
            block8: {
                blockPos = (BlockPos)object;
                Object var4_3 = null;
                if ((double)(System.currentTimeMillis() - this.time8) < (Double)this.baseDelay.getValue()) {
                    return;
                }
                BlockState blockState = MC.mc.world.getBlockState(blockPos);
                if (blockState.isOf(Blocks.OBSIDIAN)) break block8;
                if (!blockState.isOf(Blocks.BEDROCK)) break block9;
            }
            return;
        }
        if (!this.m662(blockPos)) {
            return;
        }
        Hand hand = this.getObj22();
        if (hand == null) {
            return;
        }
        Direction direction = this.m999(blockPos, (Double)this.basePlaceRange.getValue());
        if (direction == null) {
            return;
        }
        BlockPos blockPos2 = blockPos.offset(direction);
        Direction direction2 = direction.getOpposite();
        Vec3d vec3d = this.m997(blockPos2, direction2);
        BlockHitResult blockHitResult = new BlockHitResult(vec3d, direction2, blockPos2, false);
        if (((Boolean)this.onPlace.getValue()).booleanValue()) {
            this.vec3d7 = vec3d;
        }
        this.m564(hand, blockHitResult);
        MC.mc.player.swingHand(hand, false);
        this.m350();
        this.time8 = System.currentTimeMillis();
    }

    private Vec3d m240(Object object) {
        BlockPos blockPos = (BlockPos)object;
        BlockPos blockPos2 = blockPos.down();
        Direction direction = this.m779(blockPos2);
        if (direction == null) {
            return Vec3d.ofCenter((Vec3i)blockPos2).add(0.0, 0.5, 0.0);
        }
        return this.m997(blockPos2, direction);
    }

    private Vec3d m4(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Direction direction = this.m999(blockPos, (Double)this.basePlaceRange.getValue());
        if (direction == null) {
            return Vec3d.ofCenter((Vec3i)blockPos);
        }
        BlockPos blockPos2 = blockPos.offset(direction);
        return this.m997(blockPos2, direction.getOpposite());
    }

    private void setObj106(Object object) {
        Vec3d vec3d = (Vec3d)object;
        Object var4_3 = null;
        if (vec3d == null) {
            return;
        }
        float[] fArray = MathUtil.getLookAngles(MC.mc.player.getEyePos(), vec3d);
        ClientSetting.RotateMode rotateMode = this.getRotateMode9();
        switch (rotateMode) {
            case NONE: {
                if (null == null) break;
            }
            case SMOOTH: {
                Client.mathUtil.setTargetRotation(fArray[0], fArray[1]);
                Client.mathUtil.setFloat6(ClientSetting.INSTANCE != null ? ClientSetting.INSTANCE.rotateSpeed.getFloat() : 45.0f);
                if (null == null) break;
            }
            case ONTICK: {
                Client.mathUtil.setRotationSilent(fArray[0], fArray[1]);
                if (null == null) break;
            }
            case rotateMode: {
                Client.mathUtil.setRotationVisible(fArray[0], fArray[1]);
                break;
            }
        }
    }

    private void setObj101(Object object) {
        Vec3d vec3d = (Vec3d)object;
        Object var4_3 = null;
        if (vec3d == null) {
            return;
        }
        switch (((EMode2)((Object)this.rotationBypass.getValue())).ordinal()) {
            case 0: {
                this.setObj106(vec3d);
                if (null == null) break;
            }
            case 1: {
                this.setObj106(vec3d);
                this.vec3d5 = vec3d;
                if (null == null) break;
            }
            case 2: {
                this.vec3d12 = vec3d;
                this.setObj106(vec3d);
                if (null == null) break;
            }
            case 3: {
                this.setObj106(vec3d);
                this.vec3d5 = vec3d;
                break;
            }
        }
    }

    private void m551(Object object, Object object2) {
        Vec3d vec3d = (Vec3d)object;
        EndCrystalEntity endCrystalEntity = (EndCrystalEntity)object2;
        Object var6_5 = null;
        switch (((EMode2)((Object)this.rotationBypass.getValue())).ordinal()) {
            case 0: {
                this.setObj106(vec3d);
                if (null == null) break;
            }
            case 1: {
                if (this.vec3d5 != null) {
                    this.setObj106(this.vec3d5);
                    if (null == null) break;
                }
                this.setObj106(new Vec3d(endCrystalEntity.getX(), endCrystalEntity.getY() - (Double)this.hitboxExpand.getValue(), endCrystalEntity.getZ()));
                if (null == null) break;
            }
            case 2: {
                if (this.vec3d12 != null) {
                    this.setObj106(this.vec3d12);
                    if (null == null) break;
                }
                this.setObj106(new Vec3d(endCrystalEntity.getX(), endCrystalEntity.getY(), endCrystalEntity.getZ()));
                if (null == null) break;
            }
            case 3: {
                if (this.vec3d5 != null) {
                    this.setObj106(this.vec3d5);
                    if (null == null) break;
                }
                this.setObj106(new Vec3d(endCrystalEntity.getX(), endCrystalEntity.getY(), endCrystalEntity.getZ()));
            }
        }
    }

    private boolean m563(Object object, Object object2) {
        EndCrystalEntity endCrystalEntity = (EndCrystalEntity)object;
        Vec3d vec3d = (Vec3d)object2;
        Object var6_5 = null;
        if (this.rotationBypass.getValue() != EMode2.ExtraHitbox) {
            return true;
        }
        double d = (Double)this.hitboxExpand.getValue();
        Box box = endCrystalEntity.getBoundingBox().stretch(0.0, d, 0.0).offset(0.0, -d, 0.0);
        Vec3d vec3d2 = MC.mc.player.getEyePos();
        float[] fArray = MathUtil.getLookAngles(vec3d2, vec3d);
        float f = fArray[1];
        float f2 = fArray[0];
        float f3 = MathHelper.cos((double)(-f2 * ((float)Math.PI / 180) - (float)Math.PI));
        float f4 = MathHelper.sin((double)(-f2 * ((float)Math.PI / 180) - (float)Math.PI));
        float f5 = -MathHelper.cos((double)(-f * ((float)Math.PI / 180)));
        float f6 = MathHelper.sin((double)(-f * ((float)Math.PI / 180)));
        Vec3d vec3d3 = new Vec3d((double)(f4 * f5), (double)f6, (double)(f3 * f5));
        Vec3d vec3d4 = vec3d2.add(vec3d3.multiply(((Double)this.breakRange.getValue()).doubleValue()));
        return box.raycast(vec3d2, vec3d4).isPresent();
    }

    private ClientSetting.RotateMode getRotateMode9() {
        Object var2_1 = null;
        if (this.rotateMode.getValue() == EMode3.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.RotateMode)((Object)ClientSetting.INSTANCE.rotateMode.getValue()) : ClientSetting.RotateMode.NONE;
        }
        return switch (((EMode3)((Object)this.rotateMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.RotateMode.NONE;
            case 2 -> ClientSetting.RotateMode.SMOOTH;
            case 3 -> ClientSetting.RotateMode.ONTICK;
            case 4 -> ClientSetting.RotateMode.rotateMode;
            default -> ClientSetting.RotateMode.NONE;
        };
    }

    private Hand prepareHand(Object object) {
        Item item = (Item)object;
        Object var4_3 = null;
        if (MC.mc.player.getOffHandStack().isOf(item)) {
            return Hand.OFF_HAND;
        }
        if (MC.mc.player.getMainHandStack().isOf(item)) {
            return Hand.MAIN_HAND;
        }
        ClientSetting.SwitchMode switchMode = this.getSwitchMode12();
        if (switchMode == ClientSetting.SwitchMode.NONE) {
            return null;
        }
        return Client.itemSwitcher.switchToItem((java.util.function.Predicate<ItemStack>)(itemStack -> itemStack.isOf(item)), (Object)switchMode) ? Hand.MAIN_HAND : null;
    }

    private Hand getObj22() {
        Object var2_1 = null;
        if (this.m994(MC.mc.player.getOffHandStack())) {
            return Hand.OFF_HAND;
        }
        if (this.m994(MC.mc.player.getMainHandStack())) {
            return Hand.MAIN_HAND;
        }
        ClientSetting.SwitchMode switchMode = this.getSwitchMode12();
        if (switchMode == ClientSetting.SwitchMode.NONE) {
            return null;
        }
        return Client.itemSwitcher.switchToItem((java.util.function.Predicate<ItemStack>)this::m994, (Object)switchMode) ? Hand.MAIN_HAND : null;
    }

    private boolean m994(ItemStack itemStack) {
        BlockItem blockItem;
        Item item = itemStack.getItem();
        Object var2_3 = null;
        return item instanceof BlockItem && (blockItem = (BlockItem)item).getBlock() == Blocks.OBSIDIAN;
    }

    private Hand getObj7() {
        return MC.mc.player.getOffHandStack().isOf(Items.END_CRYSTAL) ? Hand.OFF_HAND : Hand.MAIN_HAND;
    }

    private void m350() {
        block0: {
            ClientSetting.SwitchMode switchMode = this.getSwitchMode12();
            Object var2_2 = null;
            if (switchMode != ClientSetting.SwitchMode.SILENT && switchMode != ClientSetting.SwitchMode.INVENTORY) break block0;
            Client.itemSwitcher.restoreSlot();
        }
    }

    private ClientSetting.SwitchMode getSwitchMode12() {
        Object var2_1 = null;
        if (this.switchMode.getValue() == EMode4.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.SwitchMode)((Object)ClientSetting.INSTANCE.switchMode.getValue()) : ClientSetting.SwitchMode.NONE;
        }
        return switch (((EMode4)((Object)this.switchMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.SwitchMode.NONE;
            case 2 -> ClientSetting.SwitchMode.NORMAL;
            case 3 -> ClientSetting.SwitchMode.SILENT;
            case 4 -> ClientSetting.SwitchMode.INVENTORY;
            default -> ClientSetting.SwitchMode.NONE;
        };
    }

    private Vec3d m1020(Object object, float f, float f2, int n) {
        LivingEntity livingEntity = (LivingEntity)object;
        float f3 = f;
        float f4 = f2;
        int n2 = n;
        if (n2 <= 0) {
            return livingEntity.getEntityPos();
        }
        double d = MathHelper.clamp((double)(livingEntity.getX() - livingEntity.lastRenderX), (double)(-f3), (double)f3);
        double d2 = MathHelper.clamp((double)(livingEntity.getY() - livingEntity.lastRenderY), (double)(-f4), (double)f4);
        double d3 = MathHelper.clamp((double)(livingEntity.getZ() - livingEntity.lastRenderZ), (double)(-f3), (double)f3);
        return new Vec3d(livingEntity.getX() + d * (double)n2, livingEntity.getY() + d2 * (double)n2, livingEntity.getZ() + d3 * (double)n2);
    }

    private Box m448(Object object, Object object2, Object object3) {
        Box box = (Box)object;
        Vec3d vec3d = (Vec3d)object2;
        Vec3d vec3d2 = (Vec3d)object3;
        return box.offset(vec3d.x - vec3d2.x, vec3d.y - vec3d2.y, vec3d.z - vec3d2.z);
    }

    private Vec4f m1014(Object object) {
        LivingEntity livingEntity = (LivingEntity)object;
        float f = (float)livingEntity.getAttributeValue(EntityAttributes.ARMOR);
        float f2 = (float)livingEntity.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS);
        Object var4_5 = null;
        float f3 = 0.0f;
        block0: for (EquipmentSlot equipmentSlot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack itemStack = livingEntity.getEquippedStack(equipmentSlot);
            if (itemStack.isEmpty()) continue;
            for (Object2IntMap.Entry entry : itemStack.getEnchantments().getEnchantmentEntries()) {
                if (((RegistryEntry)entry.getKey()).matchesKey(Enchantments.PROTECTION)) {
                    f3 += (float)entry.getIntValue();
                    if (null == null) continue block0;
                }
                if (((RegistryEntry)entry.getKey()).matchesKey(Enchantments.BLAST_PROTECTION)) {
                    f3 += (float)(entry.getIntValue() * 2);
                    if (null == null) continue block0;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
        StatusEffectInstance statusEffectInstance = livingEntity.getStatusEffect(StatusEffects.RESISTANCE);
        float f4 = statusEffectInstance == null ? 0.0f : Math.min((float)(statusEffectInstance.getAmplifier() + 1) * 0.2f, 1.0f);
        return new Vec4f(f, f2, f3, f4);
    }

    private float m877(Object object, Object object2, Object object3, Object object4, boolean bl, Object object5) {
        Vec3d vec3d = (Vec3d)object;
        Vec3d vec3d2 = (Vec3d)object2;
        Box box = (Box)object3;
        Vec4f vec4f = (Vec4f)object4;
        boolean bl2 = bl;
        Difficulty difficulty = (Difficulty)object5;
        double d = vec3d2.x - vec3d.x;
        double d2 = vec3d2.y - vec3d.y;
        double d3 = vec3d2.z - vec3d.z;
        double d4 = Math.sqrt(d * d + d2 * d2 + d3 * d3);
        Object var14_17 = null;
        double d5 = d4 / 12.0;
        if (d5 >= 1.0) {
            return 0.0f;
        }
        float f = bl2 ? 1.0f : this.m1017(vec3d, box);
        if (f <= 0.0f) {
            return 0.0f;
        }
        double d6 = (1.0 - d5) * (double)f;
        float f2 = (float)((d6 * d6 + d6) / 2.0 * 84.0 + 1.0);
        f2 = switch (difficulty) {
            case Difficulty.EASY -> Math.min(f2 / 2.0f + 1.0f, f2);
            case Difficulty.HARD -> f2 * 1.5f;
            default -> f2;
        };
        float f3 = 2.0f + vec4f.value42() / 4.0f;
        float f4 = MathHelper.clamp((float)(vec4f.value41() - f2 / f3), (float)(vec4f.value41() * 0.2f), (float)20.0f);
        f2 *= 1.0f - f4 / 25.0f;
        if (vec4f.value43() > 0.0f) {
            f2 *= 1.0f - MathHelper.clamp((float)(vec4f.value43() * 0.04f), (float)0.0f, (float)0.8f);
        }
        return Math.max(f2 *= 1.0f - vec4f.getFloat44(), 0.0f);
    }

    private Vec3d m129(Object object, Object object2, double d) {
        Vec3d vec3d = (Vec3d)object;
        Vec3d vec3d2 = (Vec3d)object2;
        double d2 = d;
        double d3 = 2.0;
        double d4 = 0.5;
        double d5 = 0.1;
        double d6 = d2 * d2;
        Vec3d vec3d3 = null;
        Object var10_12 = null;
        double d7 = Double.MAX_VALUE;
        boolean bl = vec3d.x < vec3d2.x;
        boolean bl2 = vec3d.z < vec3d2.z;
        for (double d8 = 0.0; d8 <= d3; d8 += d5) {
            for (double d9 = 0.0; d9 <= d4; d9 += d5) {
                for (double d10 = 0.0; d10 <= d4; d10 += d5) {
                    double d11 = vec3d.y + d8;
                    if (d8 != 0.0) {
                        if (d11 > vec3d2.y) {
                            return vec3d3;
                        }
                    }
                    double d12 = vec3d.x + (bl ? d9 : -d9);
                    double d13 = vec3d.z + (bl2 ? d10 : -d10);
                    double d14 = (d12 - vec3d2.x) * (d12 - vec3d2.x) + (d11 - vec3d2.y) * (d11 - vec3d2.y) + (d13 - vec3d2.z) * (d13 - vec3d2.z);
                    double d15 = d9 + d8 + d10;
                    if (!(d14 <= d6)) continue;
                    if (!(d15 < d7)) continue;
                    d7 = d15;
                    vec3d3 = new Vec3d(d12, d11, d13);
                    if (null == null) continue;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
        return vec3d3;
    }

    private Vec3d m949(Object object) {
        EndCrystalEntity endCrystalEntity = (EndCrystalEntity)object;
        Vec3d vec3d = this.m129(endCrystalEntity.getEntityPos(), MC.mc.player.getEyePos(), (Double)this.breakRange.getValue());
        Object var4_4 = null;
        return vec3d != null ? vec3d : endCrystalEntity.getEntityPos();
    }

    @EventHandler
    private void setRenderLevelEvent(RenderLevelEvent renderLevelEvent) {
        Box box;
        int n;
        double d;
        boolean bl;
        Vec3d vec3d;
        this.matrix4f8 = new Matrix4f((Matrix4fc)renderLevelEvent.getMatrix4f3());
        this.matrix4f13 = new Matrix4f((Matrix4fc)renderLevelEvent.getMatrix4f());
        if (Module.isNotInGame()) {
            return;
        }
        BlockPos blockPos = this.blockPos6;
        BlockPos blockPos2 = this.blockPos13;
        Vec3d vec3d2 = blockPos != null ? Vec3d.ofCenter((Vec3i)blockPos.down()) : null;
        Vec3d vec3d3 = vec3d = blockPos2 != null ? Vec3d.ofCenter((Vec3i)blockPos2) : null;
        if (blockPos != null || blockPos2 != null) {
            this.time39 = System.currentTimeMillis();
        }
        if (vec3d2 == null && vec3d == null && this.value157 <= 0.001) {
            return;
        }
        boolean bl2 = bl = (double)(System.currentTimeMillis() - this.time39) < (Double)this.startFade.getValue() * 1000.0;
        this.value157 = (Double)this.fadeSpeed.getValue() >= 1.0 ? (bl ? 1.0 : 0.0) : (this.value157 += ((bl ? 1.0 : 0.0) - this.value157) * ((Double)this.fadeSpeed.getValue() / 10.0));
        if (this.value157 <= 0.001) {
            this.vec3d8 = null;
            this.vec3d11 = null;
            return;
        }
        double d2 = d = (Double)this.sliderSpeed.getValue() < 1.0 ? (Double)this.sliderSpeed.getValue() / 10.0 : 1.0;
        if (vec3d2 != null) {
            Vec3d vec3d4 = this.vec3d8 = this.vec3d8 == null ? vec3d2 : new Vec3d(this.vec3d8.x + (vec3d2.x - this.vec3d8.x) * d, this.vec3d8.y + (vec3d2.y - this.vec3d8.y) * d, this.vec3d8.z + (vec3d2.z - this.vec3d8.z) * d);
        }
        if (vec3d != null) {
            Vec3d vec3d5 = this.vec3d11 = this.vec3d11 == null ? vec3d : new Vec3d(this.vec3d11.x + (vec3d.x - this.vec3d11.x) * d, this.vec3d11.y + (vec3d.y - this.vec3d11.y) * d, this.vec3d11.z + (vec3d.z - this.vec3d11.z) * d);
        }
        if (!((Boolean)this.render.getValue()).booleanValue()) {
            return;
        }
        boolean bl3 = false;
        int n2 = (Integer)this.fill.getValue() >>> 24 & 0xFF;
        int n3 = (Integer)this.box.getValue() >>> 24 & 0xFF;
        int n4 = n2 > 0 ? VanillaTextHelper.m517((Integer)this.fill.getValue(), (int)((double)n2 * this.value157)) : 0;
        int n5 = n = n3 > 0 ? VanillaTextHelper.m517((Integer)this.box.getValue(), (int)((double)n3 * this.value157)) : 0;
        if (this.vec3d8 != null) {
            box = new Box(this.vec3d8.subtract(0.5, 0.5, 0.5), this.vec3d8.add(0.5, 0.5, 0.5));
            if (((Boolean)this.shrink.getValue()).booleanValue()) {
                box = box.expand((this.value157 - 1.0) * 0.5);
            }
            if (n4 != 0) {
                EspRenderLayers.drawBoxFilled(renderLevelEvent.getMatrix4f3(), box, n4, true);
                bl3 = true;
            }
            if (n != 0) {
                EspRenderLayers.drawBoxOutline(renderLevelEvent.getMatrix4f3(), box, n, true);
                bl3 = true;
            }
        }
        if (this.vec3d11 != null) {
            box = new Box(this.vec3d11.subtract(0.5, 0.5, 0.5), this.vec3d11.add(0.5, 0.5, 0.5));
            if (n4 != 0) {
                EspRenderLayers.drawBoxFilled(renderLevelEvent.getMatrix4f3(), box, n4, true);
                bl3 = true;
            }
            if (n != 0) {
                EspRenderLayers.drawBoxOutline(renderLevelEvent.getMatrix4f3(), box, n, true);
                bl3 = true;
            }
        }
        if (bl3) {
            EspRenderLayers.drawBuffers();
        }
    }

    @EventHandler
    private void setObj102(Render2DEvent render2DEvent) {
        if (Module.isNotInGame() || !((Boolean)this.render.getValue()).booleanValue() || !((Boolean)this.damageText.getValue()).booleanValue()) {
            return;
        }
        if (this.value162 <= 0.0f || this.vec3d8 == null) {
            return;
        }
        if (this.matrix4f8 == null || this.matrix4f13 == null) {
            return;
        }
        Vec3d vec3d = MC.mc.gameRenderer.getCamera().getCameraPos();
        Matrix4f matrix4f = new Matrix4f((Matrix4fc)this.matrix4f13).mul((Matrix4fc)this.matrix4f8);
        int n = MC.mc.getWindow().getScaledWidth();
        int n2 = MC.mc.getWindow().getScaledHeight();
        Vector4f vector4f = new Vector4f((float)(this.vec3d8.x - vec3d.x), (float)(this.vec3d8.y - vec3d.y), (float)(this.vec3d8.z - vec3d.z), 1.0f).mul((Matrix4fc)matrix4f);
        if (vector4f.w() <= 0.05f) {
            return;
        }
        float f = vector4f.x() / vector4f.w();
        float f2 = vector4f.y() / vector4f.w();
        if (Math.abs(f) > 1.2f || Math.abs(f2) > 1.2f) {
            return;
        }
        int n3 = (int)((f * 0.5f + 0.5f) * (float)n);
        int n4 = (int)((1.0f - (f2 * 0.5f + 0.5f)) * (float)n2);
        String string = this.decimalFormat2.format(this.value162);
        FontManager2 fontManager2 = Client.fontManager.renderer2();
        int n5 = fontManager2.getStringWidth(string);
        int n6 = n3 - n5 / 2;
        int n7 = n4 - fontManager2.getFontHeight() / 2;
        int n8 = (int)(255.0 * this.value157);
        if (n8 > 5) {
            fontManager2.drawText(render2DEvent.getDrawContext(), string, n6, n7, VanillaTextHelper.m517((Integer)this.textColor.getValue(), n8), true);
        }
    }

    public static void setText3(String string) {
        text3500 = string;
    }

    public static String getText11() {
        return text3500;
    }

    static {
        boolean bl = false;
        ZealotCrystalPlus.setText3(null);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode6 {
        Single,
        Double;


        private static EMode6[] getObjArray15() {
            return new EMode6[]{Single, Double};
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class PositionData {
        private final PlayerEntity player;
        private final Vec3d vec3d;
        private final Box box3;
        private final Vec4f vec4f;

        private PositionData(PlayerEntity playerEntity, Vec3d vec3d, Box box, Vec4f vec4f) {
            this.player = playerEntity;
            this.vec3d = vec3d;
            this.box3 = box;
            this.vec4f = vec4f;
        }

        public PlayerEntity player() {
            return this.player;
        }

        public Vec3d getVec3d() {
            return this.vec3d;
        }

        public Box box3() {
            return this.box3;
        }

        public Vec4f vec4f() {
            return this.vec4f;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Vec4f {
        private final float value41;
        private final float value42;
        private final float value43;
        private final float value44;

        private Vec4f(float f, float f2, float f3, float f4) {
            this.value41 = f;
            this.value42 = f2;
            this.value43 = f3;
            this.value44 = f4;
        }

        public float value41() {
            return this.value41;
        }

        public float value42() {
            return this.value42;
        }

        public float value43() {
            return this.value43;
        }

        public float getFloat44() {
            return this.value44;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode5 {
        EmptySettingRenderer,
        Weak,
        Strong;


        private static EMode5[] getObjArray7() {
            return new EMode5[]{EmptySettingRenderer, Weak, Strong};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode3 {
        DEFAULT,
        NONE,
        SMOOTH,
        ONTICK,
        field11;


        private static EMode3[] getObjArray13() {
            return new EMode3[]{DEFAULT, NONE, SMOOTH, ONTICK, field11};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode4 {
        DEFAULT,
        NONE,
        NORMAL,
        SILENT,
        INVENTORY;


        private static EMode4[] getObjArray6() {
            return new EMode4[]{DEFAULT, NONE, NORMAL, SILENT, INVENTORY};
        }
    }

    @Environment(value=EnvType.CLIENT)
    static enum PageMode {
        Place,
        Break,
        Base,
        Rotation,
        RayTrace,
        Prediction,
        Misc,
        Render;


        private static PageMode[] getPageModeArray2() {
            return new PageMode[]{Place, Break, Base, Rotation, RayTrace, Prediction, Misc, Render};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode {
        Normal,
        Sync,
        Both;


        private static EMode[] getObjArray3() {
            return new EMode[]{Normal, Sync, Both};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode2 {
        Off,
        ExtraHitbox,
        Point,
        PlaceAngle;


        private static EMode2[] getObjArray16() {
            return new EMode2[]{Off, ExtraHitbox, Point, PlaceAngle};
        }
    }
}