/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.module.combat.Surround;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.BlockUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoTrap
extends Module {
    public static AutoTrap INSTANCE;
    public final EnumSetting targetMode = (EnumSetting)this.m28(new EnumSetting("TargetMode", TargetMode.Single));
    private final EnumSetting blockForHead = (EnumSetting)this.m28(new EnumSetting("BlockForHead", BlockForHeadMode.Anchor));
    private final NumberSetting delay = (NumberSetting)this.m28(new NumberSetting("Delay", 100.0, 0.0, 500.0, 1.0));
    private final NumberSetting range = (NumberSetting)this.m28(new NumberSetting("Range", 5.0, 1.0, 8.0, 0.1));
    private final NumberSetting placeRange = (NumberSetting)this.m28(new NumberSetting("PlaceRange", 4.0, 1.0, 6.0, 0.1));
    private final NumberSetting blocksPer = (NumberSetting)this.m28(new NumberSetting("BlocksPer", 1.0, 1.0, 8.0, 1.0));
    private final NumberSetting predictTicks = (NumberSetting)this.m28(new NumberSetting("PredictTicks", 2.0, 0.0, 50.0, 1.0));
    private final EnumSetting rotateMode = (EnumSetting)this.m28(new EnumSetting("RotateMode", Surround.EMode2.DEFAULT));
    private final EnumSetting switchMode = (EnumSetting)this.m28(new EnumSetting("SwitchMode", Surround.EMode.DEFAULT));
    private final BooleanSetting autoDisable = (BooleanSetting)this.m28(new BooleanSetting("AutoDisable", true));
    private final BooleanSetting helper = (BooleanSetting)this.m28(new BooleanSetting("Helper", true));
    private final BooleanSetting onlyCrawling = (BooleanSetting)this.m28(new BooleanSetting("OnlyCrawling", false));
    private final BooleanSetting checkElytra = (BooleanSetting)this.m28(new BooleanSetting("CheckElytra", false));
    private final BooleanSetting extend = (BooleanSetting)this.m28(new BooleanSetting("Extend", true));
    private final BooleanSetting antiStep = (BooleanSetting)this.m28(new BooleanSetting("AntiStep", false));
    private final BooleanSetting antiVClip = (BooleanSetting)this.m28(new BooleanSetting("AntiVClip", false));
    private final NumberSetting vClipHeight = (NumberSetting)this.m28(new NumberSetting("VClipHeight", 5.0, 3.0, 10.0, 1.0));
    private final BooleanSetting head = (BooleanSetting)this.m28(new BooleanSetting("Head", true));
    private final BooleanSetting headExtend = (BooleanSetting)this.m28(new BooleanSetting("HeadExtend", true));
    private final BooleanSetting chestUp = (BooleanSetting)this.m28(new BooleanSetting("ChestUp", true));
    private final BooleanSetting chest = (BooleanSetting)this.m28(new BooleanSetting("Chest", true));
    private final BooleanSetting onlyGround = (BooleanSetting)this.m28(new BooleanSetting("OnlyGround", false));
    private final BooleanSetting ignoreCrawling = (BooleanSetting)this.m28(new BooleanSetting("IgnoreCrawling", false));
    private final BooleanSetting legs = (BooleanSetting)this.m28(new BooleanSetting("Legs", false));
    private final BooleanSetting legAnchor = (BooleanSetting)this.m28(new BooleanSetting("LegAnchor", true));
    private final BooleanSetting down = (BooleanSetting)this.m28(new BooleanSetting("Down", false));
    private final BooleanSetting onlyHole = (BooleanSetting)this.m28(new BooleanSetting("OnlyHole", false));
    private final BooleanSetting skipBedrockHole = (BooleanSetting)this.m28(new BooleanSetting("SkipBedrockHole", true));
    private final BooleanSetting usingPause = (BooleanSetting)this.m28(new BooleanSetting("UsingPause", true));
    private final BooleanSetting selfGround = (BooleanSetting)this.m28(new BooleanSetting("SelfGround", true));
    private final List list4 = new ArrayList();
    private final List list24 = new ArrayList();
    private long time43;
    private int count175;
    private PlayerEntity player4;

    public AutoTrap() {
        super("AutoTrap", "Traps nearby enemies.", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        this.list4.clear();
        this.list24.clear();
        this.count175 = 0;
        this.player4 = null;
        this.time43 = 0L;
    }

    @Override
    public void m709() {
        Client.mathUtil.m370();
        Client.renderUtil3.m608();
        this.player4 = null;
    }

    @Override
    public String getText57() {
        Object var2_1 = null;
        return this.player4 != null ? this.player4.getName().getString() : null;
    }

    @EventHandler
    private void setEvent2Inner56(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (((Boolean)this.selfGround.getObj()).booleanValue() && !MC.client3.player.isOnGround()) {
            return;
        }
        if (((Boolean)this.usingPause.getObj()).booleanValue() && MC.client3.player.isUsingItem()) {
            return;
        }
        if (System.currentTimeMillis() - this.time43 < (long)this.delay.getInt50()) {
            return;
        }
        this.list4.clear();
        this.list24.clear();
        this.count175 = 0;
        this.player4 = null;
        List list = this.getList4();
        if (list.isEmpty()) {
            if (((Boolean)this.autoDisable.getObj()).booleanValue()) {
                this.setFlag3(false);
            }
            return;
        }
        boolean bl = false;
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            PlayerEntity playerEntity;
            this.player4 = playerEntity = (PlayerEntity)iterator.next();
            if (!this.m932(playerEntity)) continue;
            bl = true;
        }
        if (bl) {
            this.time43 = System.currentTimeMillis();
        }
    }

    private List getList4() {
        ArrayList<PlayerEntity> arrayList = new ArrayList<PlayerEntity>();
        double d = (Double)this.range.getObj() * (Double)this.range.getObj();
        Iterator iterator = MC.client3.world.getPlayers().iterator();
        Object var2_4 = null;
        while (iterator.hasNext()) {
            PlayerEntity playerEntity = (PlayerEntity)iterator.next();
            if (playerEntity == MC.client3.player) continue;
            if (!playerEntity.isAlive()) continue;
            if (playerEntity.isSpectator() || Client.manager.m258(playerEntity.getName().getString()) || MC.client3.player.squaredDistanceTo((Entity)playerEntity) > d) continue;
            arrayList.add(playerEntity);
            if (this.targetMode.getObj() != TargetMode.Single && null == null) continue;
        }
        return arrayList;
    }

    private boolean m932(Object object) {
        PlayerEntity playerEntity;
        block7: {
            block8: {
                playerEntity = (PlayerEntity)object;
                Object var4_3 = null;
                if (((Boolean)this.onlyHole.getObj()).booleanValue()) {
                    if (!this.m394(playerEntity.getBlockPos())) {
                        return false;
                    }
                }
                if (((Boolean)this.skipBedrockHole.getObj()).booleanValue()) {
                    if (this.m754(playerEntity.getBlockPos())) {
                        return false;
                    }
                }
                if (!((Boolean)this.onlyCrawling.getObj()).booleanValue()) break block7;
                if (this.m180(playerEntity)) break block7;
                if (!((Boolean)this.checkElytra.getObj()).booleanValue()) break block8;
                if (playerEntity.isGliding()) break block7;
            }
            return false;
        }
        Vec3d vec3d = (Double)this.predictTicks.getObj() > 0.0 ? playerEntity.getEntityPos().add(playerEntity.getVelocity().multiply(((Double)this.predictTicks.getObj()).doubleValue())) : playerEntity.getEntityPos();
        return this.m233(playerEntity, BlockPos.ofFloored((Position)vec3d));
    }

    private boolean m233(Object object, Object object2) {
        boolean bl;
        BlockPos blockPos;
        PlayerEntity playerEntity;
        block43: {
            BlockPos blockPos2;
            BlockPos blockPos3;
            int n;
            block45: {
                block44: {
                    int n2;
                    BlockPos blockPos4;
                    block42: {
                        block41: {
                            playerEntity = (PlayerEntity)object;
                            blockPos = (BlockPos)object2;
                            Object var6_5 = null;
                            if (blockPos == null) break block41;
                            if (!this.list4.contains(blockPos)) break block42;
                        }
                        return false;
                    }
                    this.list4.add(blockPos);
                    bl = false;
                    int n3 = this.m180(playerEntity) ? 1 : 2;
                    n = this.m180(playerEntity) ? 0 : 1;
                    if (((Boolean)this.legs.getObj()).booleanValue()) {
                        Direction[] directions = this.getDirectionArray();
                        n2 = directions.length;
                        for (int i = 0; i < n2; ++i) {
                            Direction direction = directions[i];
                            blockPos3 = blockPos.offset(direction);
                            if (this.tryPlaceBlock(blockPos3, (Boolean)this.legAnchor.getObj(), false, false)) {
                                bl = true;
                            }
                            if (!((Boolean)this.helper.getObj()).booleanValue()) continue;
                            if (BlockUtil.m573(blockPos3) != null) continue;
                            blockPos2 = this.m310(blockPos3);
                            if (blockPos2 == null) continue;
                            if (!this.m867(blockPos2)) continue;
                            bl = true;
                            if (null == null) continue;
                            break;
                        }
                    }
                    if (((Boolean)this.headExtend.getObj()).booleanValue()) {
                        for (int i = -1; i <= 1; ++i) {
                            for (n2 = -1; n2 <= 1; ++n2) {
                                if (i == 0 && n2 == 0) continue;
                                BlockPos blockPos5 = blockPos.add(i, 0, n2);
                                if (!this.m363(blockPos5)) continue;
                                BlockPos blockPos6 = blockPos5.up(n3);
                                boolean bl2 = this.blockForHead.getObj() == BlockForHeadMode.Anchor;
                                boolean bl3 = this.blockForHead.getObj() == BlockForHeadMode.Concrete;
                                boolean bl4 = this.blockForHead.getObj() == BlockForHeadMode.Web;
                                if (!this.tryPlaceBlock(blockPos6, bl2, bl3, bl4)) continue;
                                bl = true;
                                if (null == null) continue;
                            }
                            if (null == null) continue;
                            break;
                        }
                    }
                    if (((Boolean)this.head.getObj()).booleanValue()) {
                        blockPos4 = blockPos.up(n3);
                        if (BlockUtil.m573(blockPos4) == null) {
                            BlockPos blockPos7 = this.m310(blockPos4);
                            if (blockPos7 != null) {
                                if (this.m867(blockPos7)) {
                                    bl = true;
                                }
                            }
                        }
                        boolean bl5 = this.blockForHead.getObj() == BlockForHeadMode.Anchor;
                        boolean bl6 = this.blockForHead.getObj() == BlockForHeadMode.Concrete;
                        boolean bl7 = this.blockForHead.getObj() == BlockForHeadMode.Web;
                        if (this.tryPlaceBlock(blockPos4, bl5, bl6, bl7)) {
                            bl = true;
                        }
                    }
                    if (((Boolean)this.antiStep.getObj()).booleanValue()) {
                        blockPos4 = blockPos.up(3);
                        if (BlockUtil.m573(blockPos4) == null) {
                            BlockPos blockPos8 = this.m310(blockPos4);
                            if (blockPos8 != null) {
                                if (this.m867(blockPos8)) {
                                    bl = true;
                                }
                            }
                        }
                        if (this.m867(blockPos4)) {
                            bl = true;
                        }
                    }
                    if (((Boolean)this.antiVClip.getObj()).booleanValue()) {
                        for (int i = n3 + 1; i <= this.vClipHeight.getInt50(); ++i) {
                            BlockPos blockPos9 = blockPos.up(i);
                            if (!BlockUtil.m57(blockPos9)) break;
                            if (BlockUtil.m573(blockPos9) == null) {
                                BlockPos blockPos10 = this.m1018(blockPos9, Direction.DOWN);
                                if (blockPos10 != null) {
                                    if (this.m867(blockPos10)) {
                                        bl = true;
                                    }
                                }
                            }
                            if (!this.m867(blockPos9)) continue;
                            bl = true;
                            if (null == null) continue;
                            break;
                        }
                    }
                    if (((Boolean)this.down.getObj()).booleanValue()) {
                        BlockPos blockPos11 = blockPos.down();
                        if (this.m867(blockPos11)) {
                            bl = true;
                        }
                        if (BlockUtil.m573(blockPos11) == null) {
                            BlockPos blockPos12 = this.m310(blockPos11);
                            if (blockPos12 != null) {
                                if (this.m867(blockPos12)) {
                                    bl = true;
                                }
                            }
                        }
                    }
                    if (((Boolean)this.chestUp.getObj()).booleanValue()) {
                        for (Direction direction : this.getDirectionArray()) {
                            blockPos3 = blockPos.offset(direction).up(n3);
                            if (this.m867(blockPos3)) {
                                bl = true;
                            }
                            if (BlockUtil.m573(blockPos3) != null) continue;
                            blockPos2 = this.m310(blockPos3);
                            if (blockPos2 == null) continue;
                            if (!this.m867(blockPos2)) continue;
                            bl = true;
                            if (null == null) continue;
                            break;
                        }
                    }
                    if (!((Boolean)this.chest.getObj()).booleanValue()) break block43;
                    if (!((Boolean)this.onlyGround.getObj()).booleanValue()) break block44;
                    if (!playerEntity.isOnGround()) break block43;
                }
                if (!((Boolean)this.ignoreCrawling.getObj()).booleanValue()) break block45;
                if (this.m180(playerEntity)) break block43;
            }
            for (Direction direction : this.getDirectionArray()) {
                blockPos3 = blockPos.offset(direction).up(n);
                if (this.m867(blockPos3)) {
                    bl = true;
                }
                if (BlockUtil.m573(blockPos3) != null) continue;
                blockPos2 = this.m310(blockPos3);
                if (blockPos2 == null) continue;
                if (!this.m867(blockPos2)) continue;
                bl = true;
                if (null == null) continue;
                break;
            }
        }
        if (((Boolean)this.extend.getObj()).booleanValue()) {
            if (this.count175 < this.blocksPer.getInt50()) {
                for (int i = -1; i <= 1; ++i) {
                    for (int j = -1; j <= 1; ++j) {
                        if (i == 0 && j == 0) continue;
                        BlockPos blockPos13 = blockPos.add(i, 0, j);
                        if (!this.m363(blockPos13)) continue;
                        if (!this.m233(playerEntity, blockPos13)) continue;
                        bl = true;
                        if (null == null) continue;
                    }
                    if (null == null) continue;
                }
            }
        }
        return bl;
    }

    private boolean m867(Object object) {
        BlockPos blockPos = (BlockPos)object;
        return this.m639(blockPos, (Predicate<ItemStack>)this::m543);
    }

    private boolean tryPlaceBlock(Object object, boolean bl, boolean bl2, boolean bl3) {
        BlockPos blockPos = (BlockPos)object;
        boolean bl4 = bl;
        boolean bl5 = bl2;
        boolean bl6 = bl3;
        Object var10_9 = null;
        if (bl5) {
            return this.m639(blockPos, (Predicate<ItemStack>)this::m544);
        }
        if (bl6) {
            if (this.m43(Blocks.COBWEB)) {
                return this.m639(blockPos, (Predicate<ItemStack>)(itemStack -> this.m1005(itemStack, Blocks.COBWEB)));
            }
        }
        if (bl4) {
            if (this.m43(Blocks.RESPAWN_ANCHOR)) {
                return this.m639(blockPos, (Predicate<ItemStack>)(itemStack -> this.m1005(itemStack, Blocks.RESPAWN_ANCHOR)));
            }
        }
        return this.m867(blockPos);
    }

    private boolean m639(Object object, Object object2) {
        boolean bl;
        block13: {
            Predicate predicate;
            BlockPos blockPos;
            block12: {
                block11: {
                    blockPos = (BlockPos)object;
                    predicate = (Predicate)object2;
                    Object var6_5 = null;
                    if (blockPos == null) break block11;
                    if (!this.list24.contains(blockPos)) break block12;
                }
                return false;
            }
            if (this.count175 >= this.blocksPer.getInt50()) {
                return false;
            }
            if (!BlockUtil.m57(blockPos)) {
                return false;
            }
            if (MC.client3.player.getEyePos().distanceTo(Vec3d.ofCenter((Vec3i)blockPos)) > (Double)this.placeRange.getObj() + 1.5) {
                return false;
            }
            Box box = new Box(blockPos);
            if (MC.client3.player.getBoundingBox().intersects(box)) {
                return false;
            }
            for (Entity entity : MC.client3.world.getOtherEntities(null, box)) {
                if (entity.isAlive()) {
                    if (!entity.isSpectator()) {
                        if (entity.intersectionChecked) {
                            return false;
                        }
                    }
                }
                if (null == null) continue;
            }
            this.list24.add(blockPos);
            bl = BlockUtil.m1051(blockPos, predicate, (Object)this.getRotateMode3(), this.getFloat13(), (Object)this.getSwitchMode6(), (Double)this.placeRange.getObj());
            if (!bl) break block13;
            ++this.count175;
        }
        return bl;
    }

    private BlockPos m310(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object[] objectArray = new Object[2];
        objectArray[1] = null;
        objectArray[0] = blockPos;
        Object[] objectArray2 = objectArray;
        return this.m1018(objectArray2[0], objectArray2[1]);
    }

    private BlockPos m1018(Object object, Object object2) {
        BlockPos blockPos = (BlockPos)object;
        Direction direction = (Direction)object2;
        Object var6_5 = null;
        if (!((Boolean)this.helper.getObj()).booleanValue()) {
            return null;
        }
        for (Direction direction2 : Direction.values()) {
            if (direction2 == direction) continue;
            BlockPos blockPos2 = blockPos.offset(direction2);
            if (!BlockUtil.m57(blockPos2) || MC.client3.player.getBoundingBox().intersects(new Box(blockPos2)) || BlockUtil.m573(blockPos2) == null) continue;
            return blockPos2;
        }
        return null;
    }

    private boolean m363(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (MC.client3.player.getBoundingBox().intersects(new Box(blockPos))) {
            return false;
        }
        for (PlayerEntity playerEntity : MC.client3.world.getPlayers()) {
            if (playerEntity != MC.client3.player) {
                if (playerEntity.isAlive()) {
                    if (playerEntity.getBoundingBox().intersects(new Box(blockPos))) {
                        return true;
                    }
                }
            }
            if (null == null) continue;
        }
        return false;
    }

    private boolean m394(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (!this.m569(blockPos.down())) {
            return false;
        }
        int n = 0;
        for (Direction direction : this.getDirectionArray()) {
            if (!this.m569(blockPos.offset(direction))) continue;
            ++n;
            if (null == null) continue;
            break;
        }
        return n >= 4;
    }

    private boolean m754(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (!this.m333(blockPos.down())) {
            return false;
        }
        for (Direction direction : this.getDirectionArray()) {
            if (this.m333(blockPos.offset(direction))) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m569(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (MC.client3.world.getBlockState(blockPos).isAir()) return false;
        if (MC.client3.world.getBlockState(blockPos).isReplaceable()) return false;
        return true;
    }

    private boolean m333(Object object) {
        BlockPos blockPos = (BlockPos)object;
        return MC.client3.world.getBlockState(blockPos).isOf(Blocks.BEDROCK);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m180(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        Object var4_3 = null;
        if (playerEntity.getPose() == EntityPose.SWIMMING) return true;
        if (!(playerEntity.getHeight() < 1.0f)) return false;
        return true;
    }

    private boolean m43(Object object) {
        Block block = (Block)object;
        Object var4_4 = null;
        for (int i = 0; i < MC.client3.player.getInventory().size(); ++i) {
            ItemStack itemStack = MC.client3.player.getInventory().getStack(i);
            if (!this.m1005(itemStack, block)) continue;
            return true;
        }
        return false;
    }

    private boolean m543(ItemStack itemStack) {
        return this.m1005(itemStack, Blocks.OBSIDIAN);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m544(ItemStack itemStack) {
        Item item = itemStack.getItem();
        Object var2_3 = null;
        if (!(item instanceof BlockItem)) return false;
        BlockItem blockItem = (BlockItem)item;
        if (!(blockItem.getBlock() instanceof ConcretePowderBlock)) return false;
        return true;
    }

    private boolean m1005(Object object, Object object2) {
        BlockItem blockItem;
        ItemStack itemStack = (ItemStack)object;
        Block block = (Block)object2;
        Item item = itemStack.getItem();
        Object var6_6 = null;
        return item instanceof BlockItem && (blockItem = (BlockItem)item).getBlock() == block;
    }

    private Direction[] getDirectionArray() {
        return new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    }

    private ClientSetting.RotateMode getRotateMode3() {
        Object var2_1 = null;
        if (this.rotateMode.getObj() == Surround.EMode2.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.RotateMode)((Object)ClientSetting.INSTANCE.rotateMode.getObj()) : ClientSetting.RotateMode.NONE;
        }
        return switch ((Surround.EMode2)((Object)this.rotateMode.getObj())) {
            case Surround.EMode2.NONE -> ClientSetting.RotateMode.NONE;
            case Surround.EMode2.SMOOTH -> ClientSetting.RotateMode.SMOOTH;
            case Surround.EMode2.ONTICK -> ClientSetting.RotateMode.ONTICK;
            case Surround.EMode2.field10 -> ClientSetting.RotateMode.rotateMode;
            default -> ClientSetting.RotateMode.NONE;
        };
    }

    private ClientSetting.SwitchMode getSwitchMode6() {
        Object var2_1 = null;
        if (this.switchMode.getObj() == Surround.EMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.SwitchMode)((Object)ClientSetting.INSTANCE.switchMode.getObj()) : ClientSetting.SwitchMode.NONE;
        }
        return switch ((Surround.EMode)((Object)this.switchMode.getObj())) {
            case Surround.EMode.NONE -> ClientSetting.SwitchMode.NONE;
            case Surround.EMode.NORMAL -> ClientSetting.SwitchMode.NORMAL;
            case Surround.EMode.SILENT -> ClientSetting.SwitchMode.SILENT;
            case Surround.EMode.INVENTORY -> ClientSetting.SwitchMode.INVENTORY;
            default -> ClientSetting.SwitchMode.NONE;
        };
    }

    private float getFloat13() {
        Object var2_1 = null;
        return ClientSetting.INSTANCE != null ? ClientSetting.INSTANCE.rotateSpeed.getFloat35() : 45.0f;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum TargetMode {
      Single, Multi;

      private TargetMode() {}



        private static TargetMode[] getTargetModeArray() {
            return new TargetMode[]{Single, Multi};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum BlockForHeadMode {
      Obsidian, Anchor, Web, Concrete;

      private BlockForHeadMode() {}



        private static BlockForHeadMode[] getBlockForHeadModeArray() {
            return new BlockForHeadMode[]{Obsidian, Anchor, Web, Concrete};
        }
    
   }
}

