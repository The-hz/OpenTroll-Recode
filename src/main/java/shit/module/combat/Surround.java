/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.Iterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.MoveEvent;
import shit.misc.MathUtil;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.BlockUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Surround
extends Module {
    public static Surround INSTANCE;
    public final EnumSetting page = (EnumSetting)this.registerSetting(new EnumSetting("Page", PageMode.General));
    public final NumberSetting placeDelay = (NumberSetting)this.registerSetting(new NumberSetting("PlaceDelay", 50.0, 0.0, 500.0, 1.0, 1.0, () -> this.page.getValue() == PageMode.General, null, "", false));
    private final NumberSetting blocksPer = (NumberSetting)this.registerSetting(new NumberSetting("BlocksPer", 1.0, 1.0, 8.0, 1.0, 1.0, () -> this.page.getValue() == PageMode.General, null, "", false));
    private final BooleanSetting mineDownward = (BooleanSetting)this.registerSetting(new BooleanSetting("MineDownward", false, () -> this.page.getValue() == PageMode.General, null, "", false));
    public final BooleanSetting extend = (BooleanSetting)this.registerSetting(new BooleanSetting("Extend", true, () -> this.page.getValue() == PageMode.General, null, "", false));
    public final BooleanSetting onlySelf = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlySelf", false, () -> {
        Object var1_1 = null;
        if (this.page.getValue() != PageMode.General) return false;
        if ((Boolean)this.extend.getValue() == false) return false;
        return true;
    }, null, "", false));
    private final BooleanSetting break_ = (BooleanSetting)this.registerSetting(new BooleanSetting("Break", true, () -> this.page.getValue() == PageMode.General, null, "", false));
    private final BooleanSetting eatingPause = (BooleanSetting)this.registerSetting(new BooleanSetting("EatingPause", true, () -> {
        Object var1_1 = null;
        if (this.page.getValue() != PageMode.General) return false;
        if ((Boolean)this.break_.getValue() == false) return false;
        return true;
    }, null, "", false));
    private final BooleanSetting center = (BooleanSetting)this.registerSetting(new BooleanSetting("Center", true, () -> this.page.getValue() == PageMode.General, null, "", false));
    private final BooleanSetting enderChest = (BooleanSetting)this.registerSetting(new BooleanSetting("EnderChest", true, () -> this.page.getValue() == PageMode.General, null, "", false));
    private final EnumSetting switchMode = (EnumSetting)this.registerSetting(new EnumSetting("SwitchMode", EMode.DEFAULT, () -> this.page.getValue() == PageMode.General, null, "", false));
    private final EnumSetting rotateMode = (EnumSetting)this.registerSetting(new EnumSetting("RotateMode", EMode2.DEFAULT, () -> this.page.getValue() == PageMode.Rotate, null, "", false));
    public final BooleanSetting inAir = (BooleanSetting)this.registerSetting(new BooleanSetting("InAir", true, () -> this.page.getValue() == PageMode.Check, null, "", false));
    private final BooleanSetting detectMining = (BooleanSetting)this.registerSetting(new BooleanSetting("DetectMining", false, () -> this.page.getValue() == PageMode.Check, null, "", false));
    private final BooleanSetting usingPause = (BooleanSetting)this.registerSetting(new BooleanSetting("UsingPause", true, () -> this.page.getValue() == PageMode.Check, null, "", false));
    private final BooleanSetting moveDisable = (BooleanSetting)this.registerSetting(new BooleanSetting("MoveDisable", true, () -> this.page.getValue() == PageMode.Check, null, "", false));
    private final BooleanSetting jumpDisable = (BooleanSetting)this.registerSetting(new BooleanSetting("JumpDisable", true, () -> this.page.getValue() == PageMode.Check, null, "", false));
    private double value126;
    private double value194;
    private double value164;
    private long time67;
    private int count64;
    private boolean flag125;

    public Surround() {
        super("Surround", "Surrounds you with Obsidian", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        block2: {
            block4: {
                block3: {
                    Object var2_1 = null;
                    if (!Module.isNotInGame()) break block2;
                    if (((Boolean)this.moveDisable.getValue()).booleanValue()) break block3;
                    if (!((Boolean)this.jumpDisable.getValue()).booleanValue()) break block4;
                }
                this.setEnabled(false);
            }
            return;
        }
        this.value126 = MC.mc.player.getX();
        this.value194 = MC.mc.player.getY();
        this.value164 = MC.mc.player.getZ();
        this.time67 = 0L;
        this.count64 = 0;
        this.flag125 = true;
    }

    @Override
    public void onDisable() {
        Client.mathUtil.resetRotation();
        Client.itemSwitcher.restoreSlot();
    }

    @EventHandler
    private void setEvent2Inner37(Event2.Event2Inner event2Inner) {
        boolean bl;
        if (Module.isNotInGame()) {
            return;
        }
        ClientSetting.SwitchMode switchMode = this.getSwitchMode7();
        boolean bl2 = bl = switchMode == ClientSetting.SwitchMode.INVENTORY;
        if (!bl && MC.mc.currentScreen instanceof HandledScreen) {
            return;
        }
        if (System.currentTimeMillis() - this.time67 < (long)this.placeDelay.getInt()) {
            return;
        }
        this.count64 = 0;
        if (!this.isSet77() && !MC.mc.player.input.playerInput.jump()) {
            this.value126 = MC.mc.player.getX();
            this.value194 = MC.mc.player.getY();
            this.value164 = MC.mc.player.getZ();
        }
        double d = MathHelper.sqrt((float)((float)MC.mc.player.squaredDistanceTo(this.value126, this.value194, this.value164)));
        if (this.getInt48() == -1) {
            this.setEnabled(false);
            return;
        }
        if (((Boolean)this.moveDisable.getValue()).booleanValue() && d > 1.0) {
            this.setEnabled(false);
            return;
        }
        if (((Boolean)this.jumpDisable.getValue()).booleanValue() && MC.mc.player.input.playerInput.jump()) {
            this.setEnabled(false);
            return;
        }
        if (((Boolean)this.usingPause.getValue()).booleanValue() && MC.mc.player.isUsingItem()) {
            return;
        }
        if (!((Boolean)this.inAir.getValue()).booleanValue() && !MC.mc.player.isOnGround()) {
            return;
        }
        BlockPos blockPos = BlockPos.ofFloored((double)MC.mc.player.getX(), (double)MC.mc.player.getY(), (double)MC.mc.player.getZ());
        this.setObj19(blockPos);
        this.setObj19(BlockPos.ofFloored((double)MC.mc.player.getX(), (double)(MC.mc.player.getY() + 0.8), (double)MC.mc.player.getZ()));
        if (this.count64 == 0) {
            Client.mathUtil.resetRotation();
        }
    }

    @EventHandler
    private void setMoveEvent2(MoveEvent moveEvent) {
        if (Module.isNotInGame() || !((Boolean)this.center.getValue()).booleanValue() || MC.mc.player.isGliding()) {
            return;
        }
        BlockPos blockPos = BlockPos.ofFloored((double)MC.mc.player.getX(), (double)MC.mc.player.getY(), (double)MC.mc.player.getZ());
        double d = MC.mc.player.getX() - (double)blockPos.getX() - 0.5;
        double d2 = MC.mc.player.getZ() - (double)blockPos.getZ() - 0.5;
        if (Math.abs(d) <= 0.2 && Math.abs(d2) <= 0.2) {
            if (this.flag125 && (MC.mc.player.isOnGround() || this.isSet77())) {
                moveEvent.setDouble2(0.0);
                moveEvent.setDouble(0.0);
                this.flag125 = false;
            }
        } else if (this.flag125) {
            Vec3d vec3d = Vec3d.ofCenter((Vec3i)blockPos);
            float[] fArray = MathUtil.getLookAngles(MC.mc.player.getEntityPos(), new Vec3d(vec3d.x, MC.mc.player.getY(), vec3d.z));
            float f = fArray[0] / 180.0f * (float)Math.PI;
            double d3 = MC.mc.player.getEntityPos().distanceTo(new Vec3d(vec3d.x, MC.mc.player.getY(), vec3d.z));
            double d4 = Math.min(0.2873, d3);
            moveEvent.setDouble2(-Math.sin(f) * d4);
            moveEvent.setDouble(Math.cos(f) * d4);
        }
    }

    public void setObj19(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Direction[] directionArray = Direction.values();
        int n = directionArray.length;
        Object var4_6 = null;
        for (int i = 0; i < n; ++i) {
            Direction direction = directionArray[i];
            if (direction == Direction.UP) continue;
            BlockPos blockPos2 = blockPos.offset(direction);
            this.setObj88(blockPos2);
            if (!((Boolean)this.extend.getValue()).booleanValue()) continue;
            if (!Surround.m626(blockPos2)) {
                if (((Boolean)this.onlySelf.getValue()).booleanValue() || !Surround.m612(blockPos2)) continue;
            }
            for (Direction direction2 : Direction.values()) {
                BlockPos blockPos3;
                block8: {
                    block7: {
                        if (direction2 == Direction.UP) continue;
                        blockPos3 = blockPos2.offset(direction2);
                        if (Surround.m626(blockPos3)) break block7;
                        if (((Boolean)this.onlySelf.getValue()).booleanValue() || !Surround.m612(blockPos3)) break block8;
                    }
                    for (Direction direction3 : Direction.values()) {
                        if (direction3 == Direction.UP) continue;
                        this.setObj88(blockPos3);
                        this.setObj88(blockPos3.offset(direction3));
                        if (null == null) continue;
                    }
                }
                this.setObj88(blockPos3);
                if (null == null) continue;
            }
            if (null == null) continue;
        }
    }

    private void setObj88(Object object) {
        block22: {
            ClientSetting.RotateMode rotateMode;
            block21: {
                ClientSetting.SwitchMode switchMode;
                boolean bl;
                BlockUtil.Data data;
                BlockPos blockPos = (BlockPos)object;
                Object var4_3 = null;
                if (blockPos == null) {
                    return;
                }
                if (this.count64 >= this.blocksPer.getInt()) {
                    return;
                }
                if (!this.m767(blockPos)) {
                    return;
                }
                if (((Boolean)this.mineDownward.getValue()).booleanValue()) {
                    if (MC.mc.interactionManager.isBreakingBlock()) {
                        BlockPos playerPos = BlockPos.ofFloored((double)MC.mc.player.getX(), (double)MC.mc.player.getY(), (double)MC.mc.player.getZ());
                        if (blockPos.equals((Object)playerPos.down())) {
                            return;
                        }
                    }
                }
                data = BlockUtil.m573(blockPos);
                if (data == null) {
                    return;
                }
                Vec3d vec3d = data.getVec3d5();
                if (MC.mc.player.getEyePos().distanceTo(vec3d) > 6.0) {
                    return;
                }
                rotateMode = this.getRotateMode2();
                if (rotateMode != ClientSetting.RotateMode.NONE) {
                    if (!this.m53(vec3d, (Object)rotateMode)) {
                        return;
                    }
                }
                if (((Boolean)this.break_.getValue()).booleanValue()) {
                    if (!this.m509(blockPos)) {
                        return;
                    }
                } else if (this.m930(blockPos)) {
                    return;
                }
                if (!(bl = Client.itemSwitcher.switchToItem((java.util.function.Predicate<ItemStack>)this::m727, (Object)(switchMode = this.getSwitchMode7())))) {
                    return;
                }
                boolean bl2 = switchMode == ClientSetting.SwitchMode.SILENT || switchMode == ClientSetting.SwitchMode.INVENTORY;
                BlockUtil.m859(blockPos, data, Hand.MAIN_HAND);
                this.time67 = System.currentTimeMillis();
                if (bl2) {
                    Client.itemSwitcher.restoreSlot();
                }
                if (rotateMode != ClientSetting.RotateMode.rotateMode) break block21;
                Client.mathUtil.resetRotationVisible();
                if (null == null) break block22;
            }
            if (rotateMode == ClientSetting.RotateMode.ONTICK) {
                Client.mathUtil.resetRotationSilent();
            }
        }
        ++this.count64;
    }

    private boolean m767(Object object) {
        BlockPos blockPos = (BlockPos)object;
        return BlockUtil.m57(blockPos);
    }

    private boolean m53(Object object, Object object2) {
        Vec3d vec3d = (Vec3d)object;
        ClientSetting.RotateMode rotateMode = (ClientSetting.RotateMode)((Object)object2);
        float[] fArray = MathUtil.getLookAngles(MC.mc.player.getEyePos(), vec3d);
        Object var6_6 = null;
        switch (Lambda.counts7[rotateMode.ordinal()]) {
            case 1: {
                Client.mathUtil.setRotationSilent(fArray[0], fArray[1]);
                return true;
            }
            case 2: {
                Client.mathUtil.setRotationVisible(fArray[0], fArray[1]);
                return true;
            }
            case 3: {
                Client.mathUtil.setTargetRotation(fArray[0], fArray[1]);
                Client.mathUtil.setFloat6(this.getFloat66());
                return Client.mathUtil.getFloat51() <= 10.0f;
            }
        }
        return true;
    }

    private boolean m509(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Box box = new Box(blockPos);
        Iterator iterator = MC.mc.world.getOtherEntities(null, box).iterator();
        Object var4_5 = null;
        while (iterator.hasNext()) {
            Entity entity = (Entity)iterator.next();
            if (!entity.isAlive()) continue;
            if (entity.isSpectator()) continue;
            if (entity instanceof EndCrystalEntity) {
                EndCrystalEntity endCrystalEntity = (EndCrystalEntity)entity;
                if (((Boolean)this.eatingPause.getValue()).booleanValue()) {
                    if (MC.mc.player.isUsingItem()) {
                        return false;
                    }
                }
                MC.mc.interactionManager.attackEntity((PlayerEntity)MC.mc.player, (Entity)endCrystalEntity);
                MC.mc.player.swingHand(Hand.MAIN_HAND);
                return true;
            }
            if (entity.intersectionChecked) {
                return false;
            }
            if (null == null) continue;
        }
        return true;
    }

    private boolean m930(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Box box = new Box(blockPos);
        Object var4_4 = null;
        for (Entity entity : MC.mc.world.getOtherEntities(null, box)) {
            if (entity.isAlive()) {
                if (!entity.isSpectator()) {
                    if (entity.intersectionChecked) {
                        return true;
                    }
                }
            }
            if (null == null) continue;
        }
        return false;
    }

    public static boolean m626(Object object) {
        BlockPos blockPos = (BlockPos)object;
        return MC.mc.player.getBoundingBox().intersects(new Box(blockPos));
    }

    public static boolean m612(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Iterator iterator = MC.mc.world.getPlayers().iterator();
        Object var3_3 = null;
        while (iterator.hasNext()) {
            PlayerEntity playerEntity = (PlayerEntity)iterator.next();
            if (playerEntity == MC.mc.player) continue;
            if (playerEntity.getBoundingBox().intersects(new Box(blockPos))) {
                return true;
            }
            if (null == null) continue;
        }
        return false;
    }

    private boolean m727(ItemStack itemStack) {
        BlockItem blockItem;
        block3: {
            block2: {
                Item item = itemStack.getItem();
                Object var2_3 = null;
                if (!(item instanceof BlockItem)) break block2;
                blockItem = (BlockItem)item;
                if (null == null) break block3;
            }
            return false;
        }
        return blockItem.getBlock() == Blocks.OBSIDIAN || (Boolean)this.enderChest.getValue() != false && blockItem.getBlock() == Blocks.ENDER_CHEST;
    }

    private int getInt48() {
        BlockItem blockItem;
        Item item;
        ItemStack itemStack;
        int n;
        Object var2_2 = null;
        for (n = 0; n < MC.mc.player.getInventory().size(); ++n) {
            itemStack = MC.mc.player.getInventory().getStack(n);
            item = itemStack.getItem();
            if (!(item instanceof BlockItem)) continue;
            blockItem = (BlockItem)item;
            if (blockItem.getBlock() != Blocks.OBSIDIAN) continue;
            return n;
        }
        if (((Boolean)this.enderChest.getValue()).booleanValue()) {
            for (n = 0; n < MC.mc.player.getInventory().size(); ++n) {
                itemStack = MC.mc.player.getInventory().getStack(n);
                item = itemStack.getItem();
                if (!(item instanceof BlockItem)) continue;
                blockItem = (BlockItem)item;
                if (blockItem.getBlock() != Blocks.ENDER_CHEST) continue;
                return n;
            }
        }
        return -1;
    }

    private boolean isSet77() {
        Object var2_1 = null;
        return MC.mc.player.getVelocity().horizontalLengthSquared() > 1.0E-6;
    }

    private ClientSetting.RotateMode getRotateMode2() {
        Object var2_1 = null;
        if (this.rotateMode.getValue() == EMode2.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.RotateMode)((Object)ClientSetting.INSTANCE.rotateMode.getValue()) : ClientSetting.RotateMode.NONE;
        }
        return switch (((EMode2)((Object)this.rotateMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.RotateMode.NONE;
            case 2 -> ClientSetting.RotateMode.SMOOTH;
            case 3 -> ClientSetting.RotateMode.ONTICK;
            case 4 -> ClientSetting.RotateMode.rotateMode;
            default -> ClientSetting.RotateMode.NONE;
        };
    }

    private ClientSetting.SwitchMode getSwitchMode7() {
        Object var2_1 = null;
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

    private float getFloat66() {
        Object var2_1 = null;
        return ClientSetting.INSTANCE != null ? ClientSetting.INSTANCE.rotateSpeed.getFloat() : 45.0f;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode2 {
      DEFAULT, NONE, SMOOTH, ONTICK, field10;

      private EMode2() {}



        private static EMode2[] getObjArray17() {
            return new EMode2[]{DEFAULT, NONE, SMOOTH, ONTICK, field10};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts7 = new int[ClientSetting.RotateMode.values().length];

        static {
            try {
                Lambda.counts7[ClientSetting.RotateMode.ONTICK.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts7[ClientSetting.RotateMode.rotateMode.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts7[ClientSetting.RotateMode.SMOOTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum PageMode {
      General, Rotate, Check;

      private PageMode() {}



        private static PageMode[] getPageModeArray() {
            return new PageMode[]{General, Rotate, Check};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode {
      DEFAULT, NONE, NORMAL, SILENT, INVENTORY;

      private EMode() {}



        private static EMode[] getObjArray8() {
            return new EMode[]{DEFAULT, NONE, NORMAL, SILENT, INVENTORY};
        }
    
   }
}

