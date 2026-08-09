/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.CartographyTableBlock;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.CrafterBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.LoomBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SmithingTableBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import shit.Client;
import shit.misc.MathUtil;
import shit.module.client.ClientSetting;
import shit.module.render.PlaceRender;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public final class BlockUtil
implements MC {
    public static boolean flag47 = false;

    private BlockUtil() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void m868(Object object, Object object2, Object object3) {
        BlockPos blockPos = (BlockPos)object;
        Hand hand = (Hand)object2;
        BlockHitResult blockHitResult = (BlockHitResult)object3;
        MinecraftClient minecraftClient = MC.mc;
        if (minecraftClient.player == null || minecraftClient.interactionManager == null || minecraftClient.world == null) {
            return;
        }
        boolean bl2 = minecraftClient.player.isSneaking();
        if (!bl2 && BlockUtil.m32(minecraftClient.world.getBlockState(blockHitResult.getBlockPos()))) {
            return;
        }
        flag47 = true;
        try {
            minecraftClient.interactionManager.interactBlock(minecraftClient.player, hand, blockHitResult);
        }
        finally {
            flag47 = false;
        }
        minecraftClient.player.swingHand(hand, false);
        PlaceRender.setObj20(blockPos);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void m859(Object object, Object object2, Object object3) {
        BlockPos blockPos = (BlockPos)object;
        Data data = (Data)object2;
        Hand hand = (Hand)object3;
        boolean bl = false;
        MinecraftClient minecraftClient = MC.mc;
        if (minecraftClient.interactionManager == null) {
            return;
        }
        boolean bl2 = data.flag();
        boolean bl3 = bl2;
        PlayerInput playerInput = MC.mc.player.input.playerInput;
        boolean bl4 = bl3;
        if (!bl4) return;
        BlockUtil.setObj50(playerInput);
    }

    private static void m853(Object object, boolean bl) {
        PlayerInput playerInput = (PlayerInput)object;
        boolean bl2 = bl;
        PlayerInput playerInput2 = new PlayerInput(playerInput.forward(), playerInput.backward(), playerInput.left(), playerInput.right(), playerInput.jump(), bl2, playerInput.sprint());
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerInputC2SPacket(playerInput2));
        MC.mc.player.input.playerInput = playerInput2;
    }

    private static void setObj50(Object object) {
        PlayerInput playerInput = (PlayerInput)object;
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerInputC2SPacket(playerInput));
        MC.mc.player.input.playerInput = playerInput;
    }

    public static Data m573(Object object) {
        Data data;
        BlockPos blockPos = (BlockPos)object;
        Data data2 = BlockUtil.m951(blockPos, false);
        boolean bl = false;
        Data data3 = data2;
        if (false) {
            if (data3 != null) {
                return data2;
            }
            data3 = BlockUtil.m772(blockPos, false);
        }
        Data data4 = data = data3;
        if (false) {
            if (data4 != null) {
                return data;
            }
            data4 = data2 = BlockUtil.m951(blockPos, true);
        }
        if (false) {
            if (data4 != null) {
                return data2;
            }
            data4 = BlockUtil.m772(blockPos, true);
        }
        return data4;
    }

    private static Data m951(Object object, boolean bl) {
        BlockPos blockPos = (BlockPos)object;
        boolean bl2 = bl;
        Direction[] directionArray = Direction.values();
        boolean bl3 = Util2.isSet69();
        for (Direction direction : directionArray) {
            boolean bl4;
            BlockPos blockPos2 = blockPos.offset(direction);
            boolean bl5 = BlockUtil.m572(blockPos2);
            if (!bl3) {
                if (!bl5) continue;
                bl5 = BlockUtil.m32(MC.mc.world.getBlockState(blockPos2));
            }
            if ((bl4 = bl5) != bl2) continue;
            return new Data(blockPos2, direction.getOpposite(), bl4);
        }
        return null;
    }

    private static Data m772(Object object, boolean bl) {
        block5: {
            Direction[] directionArray;
            int n;
            int n2;
            Direction direction;
            BlockPos blockPos;
            BlockPos blockPos2 = (BlockPos)object;
            boolean bl2 = bl;
            Direction[] directionArray2 = Direction.values();
            int n3 = directionArray2.length;
            int n4 = 0;
            boolean bl3 = false;
            if (n4 >= n3) break block5;
            Direction direction2 = directionArray2[n4];
            while (BlockUtil.m57(blockPos = blockPos2.offset(direction = direction2)) && (n2 = 0) < (n = (directionArray = Direction.values()).length)) {
                block6: {
                    boolean bl4;
                    boolean bl5;
                    BlockPos blockPos3;
                    Direction direction3;
                    block7: {
                        direction2 = direction3 = directionArray[n2];
                        if (!false) continue;
                        if (direction2 == direction.getOpposite()) break block6;
                        blockPos3 = blockPos.offset(direction3);
                        bl5 = BlockUtil.m572(blockPos3);
                        if (!false) break block7;
                        if (!bl5) break block6;
                        bl5 = BlockUtil.m32(MC.mc.world.getBlockState(blockPos3));
                    }
                    if ((bl4 = bl5) == bl2) {
                        return new Data(blockPos3, direction3.getOpposite(), bl4);
                    }
                }
                ++n2;
                break;
            }
            ++n4;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m572(Object object) {
        BlockPos blockPos = (BlockPos)object;
        BlockState blockState = MC.mc.world.getBlockState(blockPos);
        boolean bl = Util2.isSet69();
        boolean bl2 = blockState.isAir();
        if (!bl) {
            if (bl2) return false;
            bl2 = blockState.isLiquid();
        }
        if (!bl) {
            if (bl2) return false;
            bl2 = blockState.getFluidState().isEmpty();
        }
        if (!bl) {
            if (!bl2) return false;
            bl2 = blockState.isReplaceable();
        }
        if (bl) return bl2;
        if (bl2) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m57(Object object) {
        BlockPos blockPos = (BlockPos)object;
        BlockState blockState = MC.mc.world.getBlockState(blockPos);
        boolean bl = Util2.isSet69();
        boolean bl2 = blockState.isAir();
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = blockState.isReplaceable();
        if (bl) return bl2;
        if (!bl2) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m32(Object object) {
        BlockState blockState = (BlockState)object;
        Block block = blockState.getBlock();
        boolean bl = Util2.isSet69();
        boolean bl2 = block instanceof AbstractChestBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof ShulkerBoxBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof BarrelBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof CraftingTableBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof AbstractFurnaceBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof AnvilBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof EnchantingTableBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof BrewingStandBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof BeaconBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof HopperBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof DispenserBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof CrafterBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof GrindstoneBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof CartographyTableBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof LoomBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof SmithingTableBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof StonecutterBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof LecternBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof NoteBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof BedBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof ButtonBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof LeverBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof DoorBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof TrapdoorBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof FenceGateBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof ChiseledBookshelfBlock;
        if (bl) return bl2;
        if (bl2) return true;
        bl2 = block instanceof BlockWithEntity;
        if (bl) return bl2;
        if (!bl2) return false;
        return true;
    }

    /*
     * Unable to fully structure code
     */
    public static boolean m1051(Object var0, Object var1_1, Object var2_2, float var3_3, Object var4_4, double var5_5) {
        BlockPos blockPos = (BlockPos)var0;
        Predicate predicate = (Predicate)var1_1;
        ClientSetting.RotateMode rotateMode = (ClientSetting.RotateMode)((Object)var2_2);
        float rotateSpeed = var3_3;
        ClientSetting.SwitchMode switchMode = (ClientSetting.SwitchMode)((Object)var4_4);
        double maxReach = var5_5;
        if (MC.mc.player == null) {
            return false;
        }
        if (MC.mc.interactionManager == null) {
            return false;
        }
        if (MC.mc.world == null) {
            return false;
        }
        if (!BlockUtil.m57(blockPos)) {
            return false;
        }
        Data data = BlockUtil.m573(blockPos);
        if (data == null) {
            return false;
        }
        Vec3d vec3d = data.getVec3d5();
        if (MC.mc.player.getEyePos().distanceTo(vec3d) - maxReach > 0.0) {
            return false;
        }
        if (rotateMode != ClientSetting.RotateMode.NONE && !BlockUtil.m262(vec3d, rotateMode, rotateSpeed)) {
            return false;
        }
        if (!Client.renderUtil3.switchToItem(predicate, switchMode)) {
            return false;
        }
        boolean silentSwitch = switchMode == ClientSetting.SwitchMode.SILENT || switchMode == ClientSetting.SwitchMode.INVENTORY;
        BlockUtil.m859(blockPos, data, Hand.MAIN_HAND);
        if (silentSwitch) {
            Client.renderUtil3.restoreSlot();
        }
        if (rotateMode == ClientSetting.RotateMode.rotateMode) {
            Client.mathUtil.resetRotationVisible();
            return true;
        }
        if (rotateMode == ClientSetting.RotateMode.ONTICK) {
            Client.mathUtil.resetRotationSilent();
        }
        return true;
    }

    public static boolean m262(Object object, Object object2, float f) {
        Vec3d vec3d = (Vec3d)object;
        ClientSetting.RotateMode rotateMode = (ClientSetting.RotateMode)((Object)object2);
        float f2 = f;
        float[] fArray = MathUtil.getLookAngles(MC.mc.player.getEyePos(), vec3d);
        boolean bl = false;
        int bl2 = Lambda.counts26[rotateMode.ordinal()];
        if (false) {
            switch (bl2) {
                case 1: {
                    Client.mathUtil.setRotationSilent(fArray[0], fArray[1]);
                    return true;
                }
                case 2: {
                    Client.mathUtil.setRotationVisible(fArray[0], fArray[1]);
                    return true;
                }
                case 3: {
                    Client.mathUtil.m355(fArray[0], fArray[1]);
                    Client.mathUtil.setFloat6(f2);
                    float f3 = Client.mathUtil.getFloat51() - 10.0f;
                    float f4 = f3 == 0.0f ? 0 : (f3 < 0.0f ? -1 : 1);
                    if (false) {
                        f4 = f4 <= 0 ? 1.0f : 0.0f;
                    }
                    return f4 != 0.0f;
                }
            }
            bl2 = 1;
        }
        return bl2 != 0;
    }

    @Environment(value=EnvType.CLIENT)
    public record Data(BlockPos blockPos2, Direction direction, boolean flag) {
        public Vec3d getVec3d5() {
            return Vec3d.ofCenter((Vec3i)this.blockPos2).add((double)this.direction.getOffsetX() * 0.5, (double)this.direction.getOffsetY() * 0.5, (double)this.direction.getOffsetZ() * 0.5);
        }

        public BlockHitResult getObj13() {
            return new BlockHitResult(this.getVec3d5(), this.direction, this.blockPos2, false);
        }
    }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts26 = new int[ClientSetting.RotateMode.values().length];

        static {
            try {
                Lambda.counts26[ClientSetting.RotateMode.ONTICK.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts26[ClientSetting.RotateMode.rotateMode.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts26[ClientSetting.RotateMode.SMOOTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }
}

