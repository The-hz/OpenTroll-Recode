/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.MathUtil;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.module.player.AutoArmor;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.BlockUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Scaffold
extends Module {
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 0.0, 0.0, 10.0, 1.0));
    private final BooleanSetting rotate = (BooleanSetting)this.registerSetting(new BooleanSetting("Rotate", true));
    private final EnumSetting rotateMode = (EnumSetting)this.registerSetting(new EnumSetting("RotateMode", RotateMode.DEFAULT));
    private final BooleanSetting grim = (BooleanSetting)this.registerSetting(new BooleanSetting("Grim", true));
    private final BooleanSetting tower = (BooleanSetting)this.registerSetting(new BooleanSetting("Tower", true));
    private final BooleanSetting safeWalk = (BooleanSetting)this.registerSetting(new BooleanSetting("SafeWalk", true));
    private final BooleanSetting autoSwitch = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoSwitch", true));
    private final EnumSetting switchMode = (EnumSetting)this.registerSetting(new EnumSetting("SwitchMode", EMode.DEFAULT));
    private int count102;
    private long time33;
    private BlockPos blockPos17;
    private static final Direction[] directions = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP};

    public Scaffold() {
        super("Scaffold", "Places blocks under the player.", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        this.count102 = 0;
        this.time33 = 0L;
        this.blockPos17 = null;
    }

    @Override
    public void onDisable() {
        Client.mathUtil.resetRotation();
        Client.renderUtil3.restoreSlot();
        this.blockPos17 = null;
    }

    @EventHandler
    private void onTick5(Event2.Event2Inner event2Inner) {
        Hand hand;
        Data data;
        if (Module.isNotInGame()) {
            return;
        }
        if (((Boolean)this.safeWalk.getValue()).booleanValue()) {
            this.m169();
        }
        if (this.count102 > 0) {
            --this.count102;
            return;
        }
        BlockPos blockPos = BlockPos.ofFloored((double)MC.mc.player.getX(), (double)(MC.mc.player.getY() - 0.5), (double)MC.mc.player.getZ());
        if (!this.m134(blockPos)) {
            if (((Boolean)this.tower.getValue()).booleanValue() && MC.mc.player.input.playerInput.jump() && this.m134(blockPos.up())) {
                blockPos = blockPos.up();
            } else {
                this.blockPos17 = null;
                return;
            }
        }
        if ((data = this.m8(blockPos)) == null) {
            this.blockPos17 = null;
            return;
        }
        ClientSetting.SwitchMode switchMode = this.getSwitchMode11();
        boolean bl = false;
        if (((Boolean)this.autoSwitch.getValue()).booleanValue()) {
            boolean bl2 = Client.renderUtil3.switchToItem((java.util.function.Predicate<net.minecraft.item.ItemStack>)(itemStack -> itemStack.getItem() instanceof BlockItem), (Object)switchMode);
            if (!bl2) {
                this.blockPos17 = null;
                return;
            }
            hand = Hand.MAIN_HAND;
            bl = switchMode == ClientSetting.SwitchMode.SILENT || switchMode == ClientSetting.SwitchMode.INVENTORY;
        } else {
            hand = this.getObj14();
            if (hand == null) {
                this.blockPos17 = null;
                return;
            }
        }
        this.blockPos17 = blockPos;
        Vec3d vec3d = Vec3d.ofCenter((Vec3i)data.blockPos3).add((double)data.direction3.getOffsetX() * 0.5, (double)data.direction3.getOffsetY() * 0.5, (double)data.direction3.getOffsetZ() * 0.5);
        if (((Boolean)this.rotate.getValue()).booleanValue()) {
            float[] fArray = MathUtil.getLookAngles(MC.mc.player.getEyePos(), vec3d);
            fArray[1] = Math.max(fArray[1], 75.0f);
            ClientSetting.RotateMode rotateMode = this.getRotateMode13();
            switch (Lambda.counts17[rotateMode.ordinal()]) {
                case 1: {
                    this.m378(data, vec3d, hand);
                    break;
                }
                case 2: {
                    Client.mathUtil.m355(fArray[0], fArray[1]);
                    Client.mathUtil.setFloat6(this.getFloat4());
                    if (!(Client.mathUtil.getFloat51() <= 5.0f)) break;
                    if (((Boolean)this.grim.getValue()).booleanValue()) {
                        Client.mathUtil.setRotationPacket(Client.mathUtil.getFloat55(), Client.mathUtil.getFloat58());
                    }
                    this.m378(data, vec3d, hand);
                    break;
                }
                case 3: {
                    Client.mathUtil.setRotationSilent(fArray[0], fArray[1]);
                    this.m378(data, vec3d, hand);
                    Client.mathUtil.resetRotationSilent();
                    break;
                }
                case 4: {
                    Client.mathUtil.setRotationVisible(fArray[0], fArray[1]);
                    this.m378(data, vec3d, hand);
                    Client.mathUtil.resetRotationVisible();
                }
            }
        } else {
            this.m378(data, vec3d, hand);
        }
        if (bl) {
            Client.renderUtil3.restoreSlot();
        }
        this.count102 = this.delay.getInt();
        if (((Boolean)this.tower.getValue()).booleanValue() && MC.mc.player.input.playerInput.jump()) {
            this.m1010();
        }
    }

    private void m378(Object object, Object object2, Object object3) {
        Data data = (Data)object;
        Vec3d vec3d = (Vec3d)object2;
        Hand hand = (Hand)object3;
        BlockHitResult blockHitResult = new BlockHitResult(vec3d, data.direction3, data.blockPos3, false);
        BlockUtil.m868(this.blockPos17, hand, blockHitResult);
    }

    private void m1010() {
        if (!MC.mc.player.isOnGround()) {
            return;
        }
        long l = System.currentTimeMillis();
        MC.mc.player.setVelocity(MC.mc.player.getVelocity().x, 0.42, MC.mc.player.getVelocity().z);
        this.time33 = l;
    }

    private Data m8(Object object) {
        int n;
        Direction[] directionArray;
        net.minecraft.block.BlockState blockState0;
        BlockPos blockPos;
        Direction direction;
        int n2;
        int n3;
        int n4;
        Direction[] directionArray2;
        BlockPos blockPos2;
        block12: {
            blockPos2 = (BlockPos)object;
            directionArray2 = directions;
            n4 = directionArray2.length;
            n3 = AutoArmor.getInt66();
            n2 = 0;
            while (n2 < n4) {
                block13: {
                    block14: {
                        boolean bl;
                        block15: {
                            direction = directionArray2[n2];
                            blockPos = blockPos2.offset(direction);
                            blockState0 = MC.mc.world.getBlockState(blockPos);
                            if (n3 == 0) break block13;
                            n = blockState0.isAir() ? 1 : 0;
                            if (n3 == 0) break block12;
                            if (n != 0) break block14;
                            bl = blockState0.isLiquid();
                            if (n3 == 0) break block15;
                            if (bl) break block14;
                            bl = blockState0.getFluidState().isEmpty();
                        }
                        if (bl) {
                            return new Data(blockPos, direction.getOpposite());
                        }
                    }
                    ++n2;
                }
                if (n3 != 0) continue;
            }
            directionArray2 = directions;
            n4 = directionArray2.length;
            n = 0;
        }
        n2 = n;
        block1: while (true) {
            int n5 = n2;
            block2: while (n5 < n4) {
                block16: {
                    direction = directionArray2[n2];
                    blockPos = blockPos2.offset(direction);
                    if (!MC.mc.world.getBlockState(blockPos).isAir()) break block16;
                    directionArray = directions;
                    int n6 = directionArray.length;
                    int n7 = 0;
                    while (n7 < n6) {
                        block17: {
                            block18: {
                                boolean bl;
                                BlockPos blockPos3;
                                Direction direction2;
                                block19: {
                                    direction2 = directionArray[n7];
                                    blockPos3 = blockPos.offset(direction2);
                                    BlockState blockState = MC.mc.world.getBlockState(blockPos3);
                                    if (n3 == 0) break block17;
                                    n5 = blockState.isAir() ? 1 : 0;
                                    if (n3 == 0) continue block2;
                                    if (n5 != 0) break block18;
                                    bl = blockState.isLiquid();
                                    if (n3 == 0) break block19;
                                    if (bl) break block18;
                                    bl = blockState.getFluidState().isEmpty();
                                }
                                if (bl) {
                                    return new Data(blockPos3, direction2.getOpposite());
                                }
                            }
                            ++n7;
                        }
                        if (n3 != 0) continue;
                    }
                }
                ++n2;
                if (n3 != 0) continue block1;
            }
            break;
        }
        return null;
    }

    private boolean m134(Object object) {
        boolean bl;
        block5: {
            int n;
            BlockPos blockPos;
            block7: {
                boolean bl2;
                block8: {
                    block6: {
                        blockPos = (BlockPos)object;
                        BlockState blockState = MC.mc.world.getBlockState(blockPos);
                        n = AutoArmor.getInt66();
                        bl2 = blockState.isAir();
                        if (n == 0) break block6;
                        if (bl2) break block7;
                        bl2 = blockState.isReplaceable();
                    }
                    if (n == 0) break block8;
                    if (bl2) break block7;
                    bl2 = false;
                }
                return bl2;
            }
            Box box = new Box(blockPos);
            for (Entity entity : MC.mc.world.getOtherEntities((Entity)MC.mc.player, box)) {
                block10: {
                    boolean bl3 = false;
                    block12: {
                        block11: {
                            block9: {
                                bl = entity.isAlive();
                                if (n == 0) break block5;
                                if (n == 0) break block9;
                                if (!bl) break block10;
                                bl3 = entity.isSpectator();
                            }
                            if (n == 0) break block11;
                            if (bl3) break block10;
                            bl3 = entity.intersectionChecked;
                        }
                        if (n == 0) break block12;
                        if (!bl3) break block10;
                        bl3 = false;
                    }
                    return bl3;
                }
                if (n != 0) continue;
            }
            bl = true;
        }
        return bl;
    }

    private Hand getObj14() {
        boolean bl = false;
        if (MC.mc.player.getMainHandStack().getItem() instanceof BlockItem) {
            return Hand.MAIN_HAND;
        }
        if (MC.mc.player.getOffHandStack().getItem() instanceof BlockItem) {
            return Hand.OFF_HAND;
        }
        return null;
    }

    private void m169() {
        block3: {
            MinecraftClient minecraftClient;
            block5: {
                ClientPlayerEntity clientPlayerEntity;
                int n;
                block4: {
                    boolean bl;
                    block2: {
                        n = AutoArmor.getInt66();
                        bl = MC.mc.player.isOnGround();
                        if (n == 0) break block2;
                        if (!bl) break block3;
                        clientPlayerEntity = MC.mc.player;
                        if (n == 0) break block4;
                        bl = clientPlayerEntity.input.playerInput.sneak();
                    }
                    if (bl) break block3;
                    clientPlayerEntity = MC.mc.player;
                }
                BlockPos blockPos = BlockPos.ofFloored((double)clientPlayerEntity.getX(), (double)(MC.mc.player.getY() - 1.0), (double)MC.mc.player.getZ());
                minecraftClient = MC.mc;
                if (n == 0) break block5;
                if (!minecraftClient.world.getBlockState(blockPos).isAir()) break block3;
                minecraftClient = MC.mc;
            }
            minecraftClient.player.setVelocity(MC.mc.player.getVelocity().x * 0.7, MC.mc.player.getVelocity().y, MC.mc.player.getVelocity().z * 0.7);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private ClientSetting.RotateMode getRotateMode13() {
        ClientSetting.RotateMode rotateMode;
        int n = AutoArmor.getInt66();
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

    private float getFloat4() {
        boolean bl = false;
        if (ClientSetting.INSTANCE != null) {
            return ClientSetting.INSTANCE.rotateSpeed.getFloat();
        }
        return 45.0f;
    }

    private ClientSetting.SwitchMode getSwitchMode11() {
        boolean bl = false;
        if (this.switchMode.getValue() == EMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.SwitchMode)((Object)ClientSetting.INSTANCE.switchMode.getValue()) : ClientSetting.SwitchMode.NONE;
        }
        return switch (((EMode)((Object)this.switchMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.SwitchMode.NONE;
            case 2 -> ClientSetting.SwitchMode.NORMAL;
            case 3 -> ClientSetting.SwitchMode.SILENT;
            case 4 -> ClientSetting.SwitchMode.INVENTORY;
            default -> ClientSetting.SwitchMode.NONE;
        };
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode {
      DEFAULT, NONE, NORMAL, SILENT, INVENTORY;

      private EMode() {}



        private static EMode[] getObjArray() {
            return new EMode[]{DEFAULT, NONE, NORMAL, SILENT, INVENTORY};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static final class Data  {
        private final BlockPos blockPos3;
        private final Direction direction3;

        private Data(BlockPos blockPos, Direction direction) {
            this.blockPos3 = blockPos;
            this.direction3 = direction;
        }

        public BlockPos blockPos3() {
            return this.blockPos3;
        }

        public Direction getDirection() {
            return this.direction3;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum RotateMode {
      DEFAULT, NONE, SMOOTH, ONTICK, rotateMode12;

      private RotateMode() {}



        private static RotateMode[] getRotateModeArray11() {
            return new RotateMode[]{DEFAULT, NONE, SMOOTH, ONTICK, rotateMode12};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts17 = new int[ClientSetting.RotateMode.values().length];

        static {
            try {
                Lambda.counts17[ClientSetting.RotateMode.NONE.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts17[ClientSetting.RotateMode.SMOOTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts17[ClientSetting.RotateMode.ONTICK.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts17[ClientSetting.RotateMode.rotateMode.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }
}

