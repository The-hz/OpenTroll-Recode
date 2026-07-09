/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import java.util.ArrayList;
import java.util.Comparator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.MoveEvent;
import shit.event.PacketEvent;
import shit.event.RenderLevelEvent;
import shit.misc.MathUtil;
import shit.module.Category;
import shit.module.Module;
import shit.render.EspRenderLayers;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class HoleSnap
extends Module {
    public static HoleSnap INSTANCE;
    private final EnumSetting speedMode = (EnumSetting)this.m28(new EnumSetting("SpeedMode", SpeedMode.Timer));
    private final NumberSetting timerSpeed = (NumberSetting)this.m28(new NumberSetting("TimerSpeed", 1.0, 0.1, 8.0, 0.1, 0.1, () -> this.speedMode.getObj() == SpeedMode.Timer, null, "", false));
    private final NumberSetting physicSpeed = (NumberSetting)this.m28(new NumberSetting("PhysicSpeed", 0.2, 0.01, 1.0, 0.01, 0.01, () -> this.speedMode.getObj() == SpeedMode.Physic, null, "", false));
    private final BooleanSetting up = (BooleanSetting)this.m28(new BooleanSetting("Up", true));
    private final NumberSetting range = (NumberSetting)this.m28(new NumberSetting("Range", 5.0, 1.0, 50.0, 1.0));
    private final NumberSetting timeOut = (NumberSetting)this.m28(new NumberSetting("TimeOut", 40.0, 0.0, 100.0, 1.0));
    private final BooleanSetting step = (BooleanSetting)this.m28(new BooleanSetting("Step", false));
    private final NumberSetting stepHeight = (NumberSetting)this.m28(new NumberSetting("StepHeight", 1.0, 0.0, 5.0, 0.5));
    private final BooleanSetting trappedHole = (BooleanSetting)this.m28(new BooleanSetting("TrappedHole", false));
    private final BooleanSetting render = (BooleanSetting)this.m28(new BooleanSetting("Render", true));
    private final NumberSetting renderHeight = (NumberSetting)this.m28(new NumberSetting("RenderHeight", 0.05, 0.01, 0.3, 0.01));
    private final ColorSetting bedrockColor = (ColorSetting)this.m28(new ColorSetting("BedrockColor", -1442775097));
    private final ColorSetting mixedColor = (ColorSetting)this.m28(new ColorSetting("MixedColor", -1442789377));
    private final ColorSetting doubleColor = (ColorSetting)this.m28(new ColorSetting("DoubleColor", -1426098176));
    private BlockPos blockPos20;
    private Vec3d vec3d9;
    private int count132;
    private int count56;
    private boolean flag49;
    private int count156;

    public HoleSnap() {
        super("HoleSnap", "Pulls you into the nearest safe hole.", Category.MOVEMENT);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        block5: {
            block4: {
                Object var2_1 = null;
                if (Module.isSet37()) {
                    this.setFlag3(false);
                    return;
                }
                this.count132 = 0;
                this.count56 = 0;
                this.flag49 = false;
                this.blockPos20 = this.findBestHole();
                this.vec3d9 = this.getVec3d4();
                if (this.blockPos20 == null) break block4;
                if (this.vec3d9 != null) break block5;
            }
            this.setFlag3(false);
        }
    }

    @Override
    public void m709() {
        this.blockPos20 = null;
        Object var2_1 = null;
        this.vec3d9 = null;
        this.count56 = 0;
        if (!Module.isSet37()) {
            if (this.flag49) {
                MC.client3.player.setVelocity(0.0, MC.client3.player.getVelocity().y, 0.0);
            }
        }
        this.setFloat2(0.6f);
        Client.helper4.m502();
    }

    @EventHandler
    private void setEvent2Inner51(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (++this.count132 > this.timeOut.getInt50()) {
            this.setFlag3(false);
            return;
        }
        this.blockPos20 = this.findBestHole();
        this.vec3d9 = this.getVec3d4();
        if (this.blockPos20 == null || this.vec3d9 == null || !MC.client3.player.isAlive() || MC.client3.player.isGliding()) {
            this.setFlag3(false);
            return;
        }
        if (this.speedMode.getObj() == SpeedMode.Timer) {
            Client.helper4.setFloat5(this.timerSpeed.getFloat35());
        }
        if (((Boolean)this.step.getObj()).booleanValue()) {
            this.setFloat2(this.stepHeight.getFloat35());
        }
        this.count56 = MC.client3.player.horizontalCollision ? ++this.count56 : 0;
        if (this.count56 > 8) {
            this.setFlag3(false);
        }
    }

    @EventHandler
    private void setMoveEvent5(MoveEvent moveEvent) {
        if (Module.isSet37() || this.blockPos20 == null || this.vec3d9 == null) {
            return;
        }
        Vec3d vec3d = MC.client3.player.getEntityPos();
        double d = Math.hypot(this.vec3d9.x - vec3d.x, this.vec3d9.z - vec3d.z);
        if (d < 0.08 && vec3d.y <= (double)this.blockPos20.getY() + 0.5) {
            this.setFlag3(false);
            return;
        }
        float f = MathUtil.m547(vec3d, this.vec3d9)[0];
        float f2 = f / 180.0f * (float)Math.PI;
        double d2 = this.speedMode.getObj() == SpeedMode.Physic ? Math.min((Double)this.physicSpeed.getObj(), d) : Math.min(0.2873, d);
        double d3 = -Math.sin(f2) * d2;
        double d4 = Math.cos(f2) * d2;
        moveEvent.setDouble2(d3);
        moveEvent.setDouble(d4);
        this.flag49 = true;
    }

    @EventHandler
    private void setPacketEventInner14(PacketEvent.PacketEventInner packetEventInner) {
        if (packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket) {
            this.setFlag3(false);
        }
    }

    @EventHandler
    private void setRenderLevelEvent2(RenderLevelEvent renderLevelEvent) {
        if (!((Boolean)this.render.getObj()).booleanValue() || Module.isSet37() || this.vec3d9 == null || this.blockPos20 == null) {
            return;
        }
        double d = this.vec3d9.x - 0.48;
        double d2 = this.vec3d9.z - 0.48;
        double d3 = (double)this.blockPos20.getY() + (Double)this.renderHeight.getObj();
        Box box = new Box(d, d3, d2, d + 0.96, d3 + 0.04, d2 + 0.96);
        EspRenderLayers.m69(renderLevelEvent.getMatrix4f3(), box, this.count156, true);
        EspRenderLayers.m688(renderLevelEvent.getMatrix4f3(), box.expand(0.002), this.count156 | 0x55000000, true);
        EspRenderLayers.m125();
    }

    private BlockPos findBestHole() {
        Vec3d vec3d = MC.client3.player.getEntityPos();
        int n = this.range.getInt50();
        boolean bl = (Boolean)this.up.getObj();
        ArrayList<PositionData> arrayList = new ArrayList<PositionData>();
        BlockPos blockPos = MC.client3.player.getBlockPos();
        Object var2_8 = null;
        for (int i = -n; i <= n; ++i) {
            for (int j = -3; j <= 3; ++j) {
                for (int k = -n; k <= n; ++k) {
                    BlockPos blockPos2 = blockPos.add(i, j, k);
                    if (!bl && (double)blockPos2.getY() > MC.client3.player.getY()) continue;
                    PositionData positionData2 = this.m1055(blockPos2);
                    if (positionData2 == null) continue;
                    arrayList.add(positionData2);
                    if (null == null) continue;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
            break;
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        arrayList.sort(Comparator.comparingInt(positionData -> -((PositionData)positionData).count36).thenComparingDouble(positionData -> vec3d.distanceTo(((PositionData)positionData).vec3d4)));
        PositionData positionData3 = (PositionData)arrayList.get(0);
        this.count156 = positionData3.count37;
        return positionData3.blockPos5;
    }

    private Vec3d getVec3d4() {
        Object var2_1 = null;
        if (this.blockPos20 == null) {
            return null;
        }
        PositionData positionData = this.m1055(this.blockPos20);
        return positionData == null ? null : positionData.vec3d4;
    }

    private PositionData m1055(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (!this.m153(blockPos)) {
            return null;
        }
        Type type = this.m719(blockPos);
        if (type != Type.None) {
            int n = type == Type.Bedrock ? 100 : 60;
            int n2 = type == Type.Bedrock ? (Integer)this.bedrockColor.getObj() : (Integer)this.mixedColor.getObj();
            return new PositionData(blockPos, Vec3d.ofBottomCenter((Vec3i)blockPos), n, n2);
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos2 = blockPos.offset(direction);
            if (!this.m153(blockPos2)) continue;
            if (this.m719(blockPos2) == Type.None || !this.m810(blockPos, blockPos2)) continue;
            Vec3d vec3d = new Vec3d(((double)(blockPos.getX() + blockPos2.getX()) + 1.0) / 2.0, (double)blockPos.getY(), ((double)(blockPos.getZ() + blockPos2.getZ()) + 1.0) / 2.0);
            return new PositionData(blockPos, vec3d, 40, (Integer)this.doubleColor.getObj());
        }
        return null;
    }

    private boolean m153(Object object) {
        BlockPos blockPos;
        block6: {
            block5: {
                blockPos = (BlockPos)object;
                Object var4_3 = null;
                if (!MC.client3.world.getBlockState(blockPos).isAir()) break block5;
                if (MC.client3.world.getBlockState(blockPos.up()).isAir()) break block6;
            }
            return false;
        }
        if (!((Boolean)this.trappedHole.getObj()).booleanValue()) {
            if (!MC.client3.world.getBlockState(blockPos.up(2)).isAir()) {
                return false;
            }
        }
        return true;
    }

    private Type m719(Object object) {
        BlockPos blockPos = (BlockPos)object;
        BlockPos[] blockPosArray = new BlockPos[]{blockPos.down(), blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west()};
        boolean bl = true;
        BlockPos[] blockPosArray2 = blockPosArray;
        Object var4_6 = null;
        for (BlockPos blockPos2 : blockPosArray2) {
            Block block = MC.client3.world.getBlockState(blockPos2).getBlock();
            if (!this.m549(block)) {
                return Type.None;
            }
            if (block == Blocks.BEDROCK) continue;
            bl = false;
            if (null == null) continue;
        }
        return bl ? Type.Bedrock : Type.Mixed;
    }

    private boolean m810(Object object, Object object2) {
        int n;
        BlockPos blockPos = (BlockPos)object;
        BlockPos blockPos2 = (BlockPos)object2;
        int n2 = Math.min(blockPos.getX(), blockPos2.getX());
        int n3 = Math.max(blockPos.getX(), blockPos2.getX());
        Object var6_7 = null;
        int n4 = Math.min(blockPos.getZ(), blockPos2.getZ());
        int n5 = Math.max(blockPos.getZ(), blockPos2.getZ());
        int n6 = blockPos.getY();
        for (n = n2; n <= n3; ++n) {
            for (int i = n4; i <= n5; ++i) {
                if (this.m549(MC.client3.world.getBlockState(new BlockPos(n, n6 - 1, i)).getBlock())) continue;
                return false;
            }
            if (null == null) continue;
        }
        for (n = n2; n <= n3; ++n) {
            if (!this.m549(MC.client3.world.getBlockState(new BlockPos(n, n6, n4 - 1)).getBlock())) {
                return false;
            }
            if (this.m549(MC.client3.world.getBlockState(new BlockPos(n, n6, n5 + 1)).getBlock())) continue;
            return false;
        }
        for (n = n4; n <= n5; ++n) {
            if (!this.m549(MC.client3.world.getBlockState(new BlockPos(n2 - 1, n6, n)).getBlock())) {
                return false;
            }
            if (this.m549(MC.client3.world.getBlockState(new BlockPos(n3 + 1, n6, n)).getBlock())) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m549(Object object) {
        Block block = (Block)object;
        Object var4_3 = null;
        if (block == Blocks.BEDROCK) return true;
        if (block == Blocks.OBSIDIAN) return true;
        if (block == Blocks.CRYING_OBSIDIAN) return true;
        if (block != Blocks.RESPAWN_ANCHOR) return false;
        return true;
    }

    private void setFloat2(float f) {
        float f2 = f;
        Object var4_3 = null;
        if (MC.client3.player != null) {
            if (MC.client3.player.getAttributeInstance(EntityAttributes.STEP_HEIGHT) != null) {
                MC.client3.player.getAttributeInstance(EntityAttributes.STEP_HEIGHT).setBaseValue((double)f2);
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum SpeedMode {
      Timer, Physic;

      private SpeedMode() {}



        private static SpeedMode[] getSpeedModeArray() {
            return new SpeedMode[]{Timer, Physic};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static enum Type {
      None, Mixed, Bedrock;

      private Type() {}



        private static Type[] getTypeArray13() {
            return new Type[]{None, Mixed, Bedrock};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    record PositionData(BlockPos blockPos5, Vec3d vec3d4, int count36, int count37) {
    }
}

