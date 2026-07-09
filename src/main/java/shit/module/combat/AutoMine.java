/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.player.AutoArmor;
import shit.module.player.SpeedMine;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoMine
extends Module {
    public static AutoMine INSTANCE;
    public static final List list33 = new java.util.ArrayList<>();
    public final NumberSetting targetRange = (NumberSetting)this.m28(new NumberSetting("TargetRange", 6.0, 0.0, 8.0, 0.1));
    public final NumberSetting range = (NumberSetting)this.m28(new NumberSetting("Range", 6.0, 0.0, 8.0, 0.1));
    private final NumberSetting doubleDelay = (NumberSetting)this.m28(new NumberSetting("DoubleDelay", 100.0, 0.0, 1000.0, 1.0));
    private final BooleanSetting burrow = (BooleanSetting)this.m28(new BooleanSetting("Burrow", true));
    private final BooleanSetting head = (BooleanSetting)this.m28(new BooleanSetting("Head", true));
    private final BooleanSetting face = (BooleanSetting)this.m28(new BooleanSetting("Face", true));
    private final BooleanSetting down = (BooleanSetting)this.m28(new BooleanSetting("Down", false));
    private final BooleanSetting surround = (BooleanSetting)this.m28(new BooleanSetting("Surround", true));
    private final BooleanSetting priorityOwn = (BooleanSetting)this.m28(new BooleanSetting("PriorityOwn", true));
    private final BooleanSetting forceDouble = (BooleanSetting)this.m28(new BooleanSetting("ForceDouble", false));
    private long time45 = 0L;

    public AutoMine() {
        super("AutoMine", "Automatically mines blocks around enemies.", Category.COMBAT);
        INSTANCE = this;
    }

    @EventHandler
    private void setEvent2Inner34(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (SpeedMine.INSTANCE == null || !SpeedMine.INSTANCE.isSet19()) {
            return;
        }
        if (((Boolean)this.priorityOwn.getObj()).booleanValue() && SpeedMine.INSTANCE.isSet149()) {
            return;
        }
        if (SpeedMine.blockPos7 != null && !SpeedMine.blockPos7.equals((Object)SpeedMine.getBlockPos7())) {
            return;
        }
        PlayerEntity playerEntity = this.getPlayer();
        if (playerEntity == null) {
            return;
        }
        this.doBreak(playerEntity);
    }

    private void doBreak(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        BlockPos playerPos = playerEntity.getBlockPos();
        double[] offsets = new double[]{0.3, -0.3};

        ArrayList<Double> yList = new ArrayList<Double>();
        if (((Boolean)this.down.getObj()).booleanValue()) {
            yList.add(-0.8);
        }
        if (((Boolean)this.burrow.getObj()).booleanValue()) {
            yList.add(0.3);
        }
        if (((Boolean)this.face.getObj()).booleanValue()) {
            yList.add(1.1);
        }
        if (((Boolean)this.head.getObj()).booleanValue()) {
            yList.add(2.3);
        }
        for (double y : yList) {
            for (double dx : offsets) {
                for (double dz : offsets) {
                    BlockPos bp = BlockPos.ofFloored(playerEntity.getX() + dx, playerEntity.getY() + y, playerEntity.getZ() + dz);
                    if (this.m331(bp) && bp.equals((Object)SpeedMine.getBlockPos7())) {
                        return;
                    }
                }
            }
        }

        if (((Boolean)this.surround.getObj()).booleanValue()) {
            for (Direction direction : Direction.values()) {
                if (direction == Direction.UP || direction == Direction.DOWN) continue;
                BlockPos bp = playerPos.offset(direction);
                if (this.m331(bp) && bp.equals((Object)SpeedMine.getBlockPos7())) {
                    return;
                }
            }
        }

        ArrayList<Float> yList2 = new ArrayList<Float>();
        if (((Boolean)this.down.getObj()).booleanValue()) {
            yList2.add(-0.8f);
        }
        if (((Boolean)this.head.getObj()).booleanValue()) {
            yList2.add(2.3f);
        }
        if (((Boolean)this.burrow.getObj()).booleanValue()) {
            yList2.add(0.3f);
        }
        if (((Boolean)this.face.getObj()).booleanValue()) {
            yList2.add(1.1f);
        }

        for (float y : yList2) {
            for (double off : offsets) {
                BlockPos bp = BlockPos.ofFloored(playerEntity.getX() + off, playerEntity.getY() + y, playerEntity.getZ() + off);
                if (this.m331(bp)) {
                    SpeedMine.INSTANCE.setObj84(bp);
                    this.time45 = System.currentTimeMillis();
                    return;
                }
            }
        }

        for (float y : yList2) {
            for (double dx : offsets) {
                for (double dz : offsets) {
                    BlockPos bp = BlockPos.ofFloored(playerEntity.getX() + dx, playerEntity.getY() + y, playerEntity.getZ() + dz);
                    if (this.m331(bp)) {
                        SpeedMine.INSTANCE.setObj84(bp);
                        this.time45 = System.currentTimeMillis();
                        return;
                    }
                }
            }
        }

        if (((Boolean)this.surround.getObj()).booleanValue()) {
            for (Direction direction : Direction.values()) {
                if (direction == Direction.UP || direction == Direction.DOWN) continue;
                BlockPos bp = playerPos.offset(direction);
                if (MC.client3.player.getEyePos().distanceTo(Vec3d.ofCenter((Vec3i)bp)) <= (Double)this.range.getObj()
                        && (MC.client3.world.isAir(bp) || bp.equals((Object)SpeedMine.getBlockPos7()))
                        && this.m616(bp, false)
                        && !bp.equals((Object)SpeedMine.blockPos7)) {
                    return;
                }
            }

            ArrayList<BlockPos> strict = new ArrayList<BlockPos>();
            ArrayList<BlockPos> loose = new ArrayList<BlockPos>();
            for (Direction direction : Direction.values()) {
                if (direction == Direction.UP || direction == Direction.DOWN) continue;
                BlockPos bp = playerPos.offset(direction);
                if (MC.client3.player.getEyePos().distanceTo(Vec3d.ofCenter((Vec3i)bp)) <= (Double)this.range.getObj() && this.m331(bp) && !this.m157(bp)) {
                    if (this.m616(bp, true)) {
                        strict.add(bp);
                    }
                    if (this.m616(bp, false)) {
                        loose.add(bp);
                    }
                }
            }
            ArrayList<BlockPos> chosen = !strict.isEmpty() ? strict : loose;
            if (!chosen.isEmpty()) {
                BlockPos best = chosen.stream().min(Comparator.comparingDouble(b -> MC.client3.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter((Vec3i)b)))).orElse(null);
                if (best != null) {
                    SpeedMine.INSTANCE.setObj84(best);
                    this.time45 = System.currentTimeMillis();
                }
            }
        }

        if (((Boolean)this.forceDouble.getObj()).booleanValue()) {
            BlockPos current = SpeedMine.getBlockPos7();
            if (current != null) {
                BlockPos next = this.getNextBestTarget(playerEntity, current);
                if (next != null && !next.equals((Object)SpeedMine.blockPos7) && System.currentTimeMillis() - this.time45 >= (Double)this.doubleDelay.getObj()) {
                    SpeedMine.setObj27(next);
                }
            }
        }
    }

    private boolean m331(Object var1_1) {
        BlockPos blockPos = (BlockPos)var1_1;
        if (blockPos == null) {
            return false;
        }
        net.minecraft.block.BlockState blockState = MC.client3.world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof TrapdoorBlock && blockPos.equals((Object)SpeedMine.getBlockPos7())) {
            return false;
        }
        if (!list33.contains(blockState.getBlock())) {
            return false;
        }
        if (MC.client3.player.getEyePos().distanceTo(Vec3d.ofCenter((Vec3i)blockPos)) - (Double)SpeedMine.INSTANCE.range.getObj() > 0.0) {
            return false;
        }
        // Reachability guard SpeedMine.INSTANCE.m561(pos) == null is subsumed by the range check
        // above (m561 returns null only when distance > range), so it is a no-op here.
        if (blockPos.equals((Object)SpeedMine.blockPos7)) {
            return false;
        }
        return true;
    }

    private BlockPos getNextBestTarget(Object object, Object object2) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        BlockPos blockPos2 = (BlockPos)object2;
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        double[] dArray = new double[]{0.3, -0.3};
        boolean bl = false;
        ArrayList<Float> arrayList2 = new ArrayList<Float>();
        if (((Boolean)this.down.getObj()).booleanValue()) {
            arrayList2.add(Float.valueOf(-0.8f));
        }
        if (((Boolean)this.face.getObj()).booleanValue()) {
            arrayList2.add(Float.valueOf(1.1f));
        }
        if (((Boolean)this.head.getObj()).booleanValue()) {
            arrayList2.add(Float.valueOf(2.3f));
        }
        if (((Boolean)this.burrow.getObj()).booleanValue()) {
            arrayList2.add(Float.valueOf(0.3f));
        }
        Iterator<Float> iterator2 = arrayList2.iterator();
        while (iterator2.hasNext()) {
            float f = ((Float)iterator2.next()).floatValue();
            double[] dArray2 = dArray;
            int n = dArray2.length;
            for (int i = 0; i < n; ++i) {
                double d = dArray2[i];
                double[] dArray3 = dArray;
                int n2 = dArray3.length;
                for (int j = 0; j < n2; ++j) {
                    double d2 = dArray3[j];
                    BlockPos blockPos4 = BlockPos.ofFloored((double)(playerEntity.getX() + d), (double)(playerEntity.getY() + (double)f), (double)(playerEntity.getZ() + d2));
                    if (!this.m331(blockPos4)) continue;
                    if (blockPos4.equals((Object)blockPos2)) continue;
                    arrayList.add(blockPos4);
                    if (!false) continue;
                }
                if (!false) continue;
            }
            if (!false) continue;
            break;
        }
        if (((Boolean)this.surround.getObj()).booleanValue()) {
            BlockPos blockPos3 = playerEntity.getBlockPos();
            for (Direction direction : Direction.values()) {
                if (direction == Direction.UP || direction == Direction.DOWN) continue;
                BlockPos blockPos5 = blockPos3.offset(direction);
                if (!this.m331(blockPos5)) continue;
                if (blockPos5.equals((Object)blockPos2)) continue;
                if (this.m157(blockPos5)) continue;
                arrayList.add(blockPos5);
                if (!false) continue;
            }
        }
        return arrayList.stream().min(Comparator.comparingDouble(blockPos -> MC.client3.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter((Vec3i)blockPos)))).orElse(null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m616(Object object, boolean bl) {
        boolean bl2 = false;
        int n;
        BlockPos blockPos;
        block5: {
            block6: {
                Block block;
                Block block2;
                block7: {
                    Block block3;
                    blockPos = (BlockPos)object;
                    boolean bl3 = bl;
                    BlockPos blockPos2 = blockPos.down();
                    n = AutoArmor.getInt66();
                    bl2 = bl3;
                    if (n == 0) break block5;
                    if (!bl2) break block6;
                    block2 = block3 = MC.client3.world.getBlockState(blockPos2).getBlock();
                    block = Blocks.BEDROCK;
                    if (n == 0) break block7;
                    if (block2 == block) break block6;
                    block2 = block3;
                    block = Blocks.OBSIDIAN;
                }
                if (block2 != block) {
                    return false;
                }
            }
            bl2 = this.m45(blockPos);
        }
        if (n != 0) {
            if (!bl2) return false;
            bl2 = this.m45(blockPos.up());
        }
        if (n == 0) return bl2;
        if (!bl2) return false;
        return true;
    }

    private boolean m45(Object object) {
        boolean bl;
        block3: {
            BlockPos blockPos = (BlockPos)object;
            Iterator iterator = MC.client3.world.getNonSpectatingEntities(Entity.class, new Box(blockPos)).iterator();
            int n = AutoArmor.getInt66();
            while (iterator.hasNext()) {
                block5: {
                    boolean bl2 = false;
                    block6: {
                        block4: {
                            Entity entity = (Entity)iterator.next();
                            bl = entity instanceof ItemEntity;
                            if (n == 0) break block3;
                            if (n == 0) break block4;
                            if (bl) break block5;
                            bl2 = entity instanceof ArmorStandEntity;
                        }
                        if (n == 0) break block6;
                        if (bl2) break block5;
                        bl2 = false;
                    }
                    return bl2;
                }
                if (n != 0) continue;
            }
            bl = true;
        }
        return bl;
    }

    private boolean m157(Object object) {
        BlockPos blockPos = (BlockPos)object;
        BlockPos blockPos2 = MC.client3.player.getBlockPos();
        Direction[] directionArray = Direction.values();
        int n = directionArray.length;
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            Direction direction = directionArray[i];
            if (direction == Direction.UP || direction == Direction.DOWN || !blockPos2.offset(direction).equals((Object)blockPos)) continue;
            return true;
        }
        return false;
    }

    private PlayerEntity getPlayer() {
        PlayerEntity playerEntity = null;
        double d = (Double)this.targetRange.getObj();
        boolean bl = false;
        for (PlayerEntity playerEntity2 : MC.client3.world.getPlayers()) {
            if (playerEntity2 == MC.client3.player) continue;
            if (playerEntity2.isSpectator()) continue;
            if (playerEntity2.isCreative() || Client.manager.m258(playerEntity2.getName().getString())) continue;
            double d2 = MC.client3.player.distanceTo((Entity)playerEntity2);
            if (d2 < d) {
                d = d2;
                playerEntity = playerEntity2;
            }
            if (!false) continue;
        }
        return playerEntity;
    }

    /*
     * Exception decompiling
     */
    static {}
}

