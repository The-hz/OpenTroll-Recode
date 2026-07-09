/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.block.Blocks
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.projectile.FishingBobberEntity
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
 *  net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket$Action
 *  net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$Full
 *  net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket
 *  net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Box
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Direction$Axis
 *  net.minecraft.util.math.Direction$AxisDirection
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.module.movement.NoSlow;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;
import shit.util.MathUtil;

@Environment(value=EnvType.CLIENT)
public class Velocity
extends Module {
    public static Velocity INSTANCE;
    private final EnumSetting mode;
    private final NumberSetting lagPause;
    private final BooleanSetting ignorePearlLag;
    private final NumberSetting phaseTime;
    private final BooleanSetting noRotation;
    private final BooleanSetting flagInWall;
    private final BooleanSetting whilePushOut;
    private final BooleanSetting static_;
    private final BooleanSetting cancelAll;
    private final NumberSetting horizontal;
    private final NumberSetting vertical;
    private final BooleanSetting whileLiquid;
    private final BooleanSetting fallFlying;
    public final BooleanSetting noClimb;
    public final BooleanSetting noWaterPush;
    public final BooleanSetting noEntityPush;
    public final BooleanSetting noBlockPush;
    private final BooleanSetting noFishBob;
    public final Helper7 helper711;
    private final Helper7 helper735;
    private boolean flag26;
    private boolean flag130;

        public Velocity() {
        super("Velocity", "Reduces or cancels knockback.", Category.MOVEMENT);
        this.mode = (EnumSetting)this.m28(new EnumSetting("Mode", Mode.Modify));
        this.lagPause = (NumberSetting)this.m28(new NumberSetting("LagPause", 50.0, 0.0, 500.0, 10.0));
        this.ignorePearlLag = (BooleanSetting)this.m28(new BooleanSetting("IgnorePearlLag", true));
        this.phaseTime = (NumberSetting)this.m28(new NumberSetting("PhaseTime", 250.0, 0.0, 1000.0, 50.0));
        this.noRotation = (BooleanSetting)this.m28(new BooleanSetting("NoRotation", false));
        this.flagInWall = (BooleanSetting)this.m28(new BooleanSetting("FlagInWall", false));
        this.whilePushOut = (BooleanSetting)this.m28(new BooleanSetting("WhilePushOut", false));
        this.static_ = (BooleanSetting)this.m28(new BooleanSetting("Static", false));
        this.cancelAll = (BooleanSetting)this.m28(new BooleanSetting("CancelAll", false));
        this.horizontal = (NumberSetting)this.m28(new NumberSetting("Horizontal", 0.0, 0.0, 100.0, 1.0));
        this.vertical = (NumberSetting)this.m28(new NumberSetting("Vertical", 0.0, 0.0, 100.0, 1.0));
        this.whileLiquid = (BooleanSetting)this.m28(new BooleanSetting("WhileLiquid", false));
        this.fallFlying = (BooleanSetting)this.m28(new BooleanSetting("FallFlying", false));
        this.noClimb = (BooleanSetting)this.m28(new BooleanSetting("NoClimb", false));
        this.noWaterPush = (BooleanSetting)this.m28(new BooleanSetting("NoWaterPush", false));
        this.noEntityPush = (BooleanSetting)this.m28(new BooleanSetting("NoEntityPush", true));
        this.noBlockPush = (BooleanSetting)this.m28(new BooleanSetting("NoBlockPush", true));
        this.noFishBob = (BooleanSetting)this.m28(new BooleanSetting("NoFishBob", true));
        this.helper711 = new Helper7();
        this.helper735 = new Helper7();
    }

    @Override
    public String getText57() {
        Object var2_1 = null;
        if (this.mode.getObj() == Mode.None) {
            return null;
        }
        String string = ((Mode)((Object)this.mode.getObj())).name();
        String string2 = (Boolean)this.cancelAll.getObj() != false ? "Cancel" : this.horizontal.getInt50() + "%, " + this.vertical.getInt50() + "%";
        return string + ", " + string2;
    }

    @Override
    public void m709() {
        super.m709();
        this.flag26 = false;
        this.flag130 = false;
    }

    public boolean m402(Object object) {
        Entity entity = (Entity)object;
        Object var4_3 = null;
        return MC.client3.player != null && entity == MC.client3.player;
    }

    public boolean isSet16() {
        Object var2_1 = null;
        return this.isSet19() && this.mode.getObj() != Mode.None;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Vec3d m601(Object object, boolean bl) {
        Vec3d vec3d = (Vec3d)object;
        Object var6_4 = null;
        if (MC.client3.player == null) {
            return vec3d;
        }
        if (!this.isSet16()) {
            return vec3d;
        }
        if (this.mode.getObj() == Mode.None) {
            return vec3d;
        }
        if (MC.client3.player.isInFluid()) {
            if (!((Boolean)this.whileLiquid.getObj()).booleanValue()) {
                return vec3d;
            }
        }
        if (MC.client3.player.isGliding()) {
            if (!((Boolean)this.fallFlying.getObj()).booleanValue()) {
                return vec3d;
            }
        }
        switch (((Mode)((Object)this.mode.getObj())).ordinal()) {
            case 1: {
                if (!this.helper735.m432((Double)this.lagPause.getObj())) {
                    return vec3d;
                }
                boolean bl2 = ItemUtil.isSet26();
                boolean bl3 = this.getBlockPos5() != null;
                if (!bl2) {
                    if (!bl3) {
                        if (!((Boolean)this.static_.getObj()).booleanValue()) return vec3d;
                        if (!MathUtil.isSet132()) {
                            return vec3d;
                        }
                    }
                }
                if (vec3d.x != 0.0 || vec3d.z != 0.0) {
                    this.flag26 = true;
                }
                if (null == null) break;
            }
            case 2: {
                if (!this.helper735.m432((Double)this.lagPause.getObj())) {
                    return vec3d;
                }
                if (!ItemUtil.isSet26()) {
                    return vec3d;
                }
                if (vec3d.x == 0.0) {
                    if (vec3d.z == 0.0) break;
                }
                this.flag26 = true;
                break;
            }
        }
        if (((Boolean)this.cancelAll.getObj()).booleanValue()) {
            return null;
        }
        double d = (Double)this.horizontal.getObj() / 100.0;
        double d2 = (Double)this.vertical.getObj() / 100.0;
        return new Vec3d(vec3d.x * d, vec3d.y * d2, vec3d.z * d);
    }

    @EventHandler
    public void setPacketEventInner12(PacketEvent.PacketEventInner packetEventInner) {
        Entity entity;
        EntityStatusS2CPacket entityStatusS2CPacket;
        Packet packet;
        if (packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket && (!((Boolean)this.ignorePearlLag.getObj()).booleanValue() || this.helper711.m432((Double)this.phaseTime.getObj()))) {
            this.helper735.m533();
        }
        if (Module.isSet37()) {
            return;
        }
        if (((Boolean)this.noFishBob.getObj()).booleanValue() && (packet = packetEventInner.getPacket()) instanceof EntityStatusS2CPacket && (entityStatusS2CPacket = (EntityStatusS2CPacket)packet).getStatus() == 31 && MC.client3.world != null && (entity = entityStatusS2CPacket.getEntity((World)MC.client3.world)) instanceof FishingBobberEntity && ((FishingBobberEntity)entity).getHookedEntity() == MC.client3.player) {
            packetEventInner.m209();
        }
    }

    @EventHandler
    public void setEvent2Inner21(Event2.Event2Inner event2Inner) {
        boolean bl;
        if (Module.isSet37()) {
            return;
        }
        if (this.mode.getObj() != Mode.Grim && this.mode.getObj() != Mode.Wall) {
            return;
        }
        if (((Boolean)this.flagInWall.getObj()).booleanValue()) {
            this.flag130 = false;
            double d = (double)MC.client3.player.getWidth() * 0.35;
            this.m410(MC.client3.player.getX() - d, MC.client3.player.getZ() + d);
            this.m410(MC.client3.player.getX() - d, MC.client3.player.getZ() - d);
            this.m410(MC.client3.player.getX() + d, MC.client3.player.getZ() - d);
            this.m410(MC.client3.player.getX() + d, MC.client3.player.getZ() + d);
        }
        if (!this.flag26) {
            return;
        }
        boolean bl2 = this.helper735.m432((Double)this.lagPause.getObj());
        boolean bl3 = bl = (Boolean)this.flagInWall.getObj() == false || !this.flag130 || (Boolean)this.whilePushOut.getObj() != false || !ItemUtil.isSet26();
        if (bl2 && bl) {
            float f = MC.client3.player.getYaw();
            float f2 = this.isSet173() ? 89.0f : MC.client3.player.getPitch();
            MC.client3.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.Full(MC.client3.player.getX(), MC.client3.player.getY(), MC.client3.player.getZ(), f, f2, MC.client3.player.isOnGround(), MC.client3.player.horizontalCollision));
            BlockPos blockPos = this.getBlockPos5();
            if (blockPos != null) {
                MC.client3.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, MC.client3.player.getHorizontalFacing().getOpposite()));
            }
            this.flag26 = false;
        }
    }

    private BlockPos getBlockPos5() {
        block3: {
            block2: {
                Object var2_1 = null;
                if (MC.client3.player == null) break block2;
                if (MC.client3.world != null) break block3;
            }
            return null;
        }
        BlockPos blockPos = MC.client3.player.getBlockPos().down();
        return MC.client3.world.getBlockState(blockPos).isOf(Blocks.OBSIDIAN) ? blockPos : null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet173() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if (this.mode.getObj() != Mode.Grim) {
            if (this.mode.getObj() != Mode.Wall) return false;
        }
        if (!ItemUtil.isSet26()) return false;
        if ((Boolean)this.noRotation.getObj() == false) return false;
        return true;
    }

    private void m410(double d, double d2) {
        block3: {
            double d3 = d;
            double d4 = d2;
            Object var10_5 = null;
            if (MC.client3.player == null || MC.client3.world == null) {
                return;
            }
            BlockPos blockPos = BlockPos.ofFloored((double)d3, (double)MC.client3.player.getY(), (double)d4);
            if (!this.m433(blockPos)) {
                return;
            }
            double d5 = d3 - (double)blockPos.getX();
            double d6 = d4 - (double)blockPos.getZ();
            double d7 = Double.MAX_VALUE;
            Direction direction = null;
            for (Direction direction2 : new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}) {
                double d8 = direction2.getAxis() == Direction.Axis.X ? d5 : d6;
                double d9 = direction2.getDirection() == Direction.AxisDirection.POSITIVE ? 1.0 - d8 : d8;
                if (!(d9 < d7) || this.m433(blockPos.offset(direction2))) continue;
                d7 = d9;
                direction = direction2;
                if (null == null) continue;
            }
            if (direction == null) break block3;
            this.flag130 = true;
        }
    }

    private boolean m433(Object object) {
        BlockPos blockPos;
        block3: {
            block2: {
                blockPos = (BlockPos)object;
                Object var4_3 = null;
                if (MC.client3.player == null) break block2;
                if (MC.client3.world != null) break block3;
            }
            return false;
        }
        Box box = MC.client3.player.getBoundingBox();
        Box box2 = new Box((double)blockPos.getX(), box.minY, (double)blockPos.getZ(), (double)blockPos.getX() + 1.0, box.maxY, (double)blockPos.getZ() + 1.0).contract(1.0E-7);
        return !MC.client3.world.isBlockSpaceEmpty((Entity)MC.client3.player, box2);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
        Modify,
        Grim,
        Wall,
        None;


        private static Mode[] getModeArray14() {
            return new Mode[]{Modify, Grim, Wall, None};
        }
    }
}
