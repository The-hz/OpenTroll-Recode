/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import shit.Client;
import shit.command.CommandManager;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.MathUtil;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.module.player.SpeedMine;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.setting.StringSetting;
import shit.util.BlockUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoRegear
extends Module {
    public static AutoRegear INSTANCE;
    private final StringSetting kitName = (StringSetting)this.registerSetting(new StringSetting("KitName", "default"));
    private final EnumSetting rotateMode = (EnumSetting)this.registerSetting(new EnumSetting("RotateMode", RotateMode.DEFAULT));
    private final EnumSetting switchMode = (EnumSetting)this.registerSetting(new EnumSetting("SwitchMode", SwitchMode.DEFAULT));
    private final NumberSetting range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 4.0, 1.0, 6.0, 0.1));
    private final NumberSetting regearDelay = (NumberSetting)this.registerSetting(new NumberSetting("RegearDelay", 50.0, 0.0, 1000.0, 10.0));
    private final NumberSetting perAction = (NumberSetting)this.registerSetting(new NumberSetting("PerAction", 1.0, 1.0, 10.0, 1.0));
    private final NumberSetting openDelay = (NumberSetting)this.registerSetting(new NumberSetting("OpenDelay", 200.0, 0.0, 500.0, 10.0));
    private final NumberSetting placeDelay = (NumberSetting)this.registerSetting(new NumberSetting("PlaceDelay", 200.0, 0.0, 500.0, 10.0));
    private final NumberSetting enderChestDelay = (NumberSetting)this.registerSetting(new NumberSetting("EnderChestDelay", 150.0, 0.0, 500.0, 10.0));
    private final BooleanSetting place = (BooleanSetting)this.registerSetting(new BooleanSetting("Place", true));
    private final BooleanSetting take = (BooleanSetting)this.registerSetting(new BooleanSetting("Take", true));
    private final BooleanSetting returnExtras = (BooleanSetting)this.registerSetting(new BooleanSetting("ReturnExtras", true));
    private final BooleanSetting regearArmor = (BooleanSetting)this.registerSetting(new BooleanSetting("RegearArmor", true));
    private final BooleanSetting mine = (BooleanSetting)this.registerSetting(new BooleanSetting("Mine", true));
    private final BooleanSetting autoDisable = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoDisable", true));
    private final NumberSetting disableTime = (NumberSetting)this.registerSetting(new NumberSetting("DisableTime", 500.0, 0.0, 2000.0, 50.0));
    private final BooleanSetting enderChest = (BooleanSetting)this.registerSetting(new BooleanSetting("EnderChest", true));
    private final BooleanSetting smartSearch = (BooleanSetting)this.registerSetting(new BooleanSetting("SmartSearch", true));
    private final NumberSetting armorCoin = (NumberSetting)this.registerSetting(new NumberSetting("ArmorCoin", 1000.0, 1.0, 5000.0, 1.0));
    private final NumberSetting weaponCoin = (NumberSetting)this.registerSetting(new NumberSetting("WeaponCoin", 1000.0, 1.0, 5000.0, 1.0));
    private final NumberSetting regearItemCoin = (NumberSetting)this.registerSetting(new NumberSetting("RegearItemCoin", 1.0, 1.0, 5000.0, 1.0));
    public final BooleanSetting silentDisplay = (BooleanSetting)this.registerSetting(new BooleanSetting("SilentDisplay", true));
    private Type type11 = Type.IDLE;
    public BlockPos blockPos15 = null;
    private BlockPos blockPos10 = null;
    private boolean flag13 = false;
    public boolean flag39 = false;
    private long time56 = 0L;
    private long time15 = 0L;
    private long time28 = 0L;
    private long time23 = 0L;
    private final Map map27 = new HashMap();

    public AutoRegear() {
        super("AutoRegear", "Automatically regears from shulker boxes / ender chests.", Category.COMBAT);
        INSTANCE = this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet128() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isEnabled()) return false;
        if ((Boolean)AutoRegear.INSTANCE.silentDisplay.getValue() == false) return false;
        return true;
    }

    @Override
    public void onEnable() {
        block3: {
            block2: {
                this.m781();
                Object var2_1 = null;
                if (((Boolean)this.take.getValue()).booleanValue()) {
                    if (!this.m344((String)this.kitName.getValue())) {
                        CommandManager.sendFeedback("\u00a7c[AutoRegear] \u00a77Failed to load kit: " + (String)this.kitName.getValue() + ".kit");
                        this.setEnabled(false);
                        return;
                    }
                }
                BlockPos blockPos = this.getBlockPos6();
                if (blockPos == null) break block2;
                this.blockPos15 = blockPos;
                this.type11 = Type.OPENING_SHULKER;
                if (null == null) break block3;
            }
            this.type11 = Type.IDLE;
        }
    }

    @Override
    public void onDisable() {
        Client.mathUtil.resetRotation();
        Client.renderUtil3.restoreSlot();
        this.m781();
    }

    private void m781() {
        long l;
        this.type11 = Type.IDLE;
        this.blockPos15 = null;
        this.blockPos10 = null;
        this.flag13 = false;
        this.flag39 = false;
        this.time56 = l = System.currentTimeMillis();
        this.time15 = l;
        this.time28 = l;
        this.time23 = l;
        this.map27.clear();
    }

    @EventHandler
    private void setEvent2Inner40(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        switch (this.type11.ordinal()) {
            case 0: {
                this.m953();
                break;
            }
            case 1: {
                this.tickPlacingShulker();
                break;
            }
            case 2: {
                this.m791();
                break;
            }
            case 3: {
                this.m299();
                break;
            }
            case 6: {
                this.tickPlacingEchest();
                break;
            }
            case 4: {
                this.m202();
                break;
            }
            case 5: {
                this.m917();
                break;
            }
            case 7: {
                this.setEnabled(false);
            }
        }
    }

    private void m953() {
        BlockPos blockPos = this.getBlockPos6();
        Object var2_2 = null;
        if (blockPos != null) {
            this.blockPos15 = blockPos;
            this.type11 = Type.OPENING_SHULKER;
            return;
        }
        if (((Boolean)this.place.getValue()).booleanValue()) {
            if (this.getInt41() != -1) {
                this.type11 = Type.PLACING_SHULKER;
                return;
            }
        }
        if (((Boolean)this.enderChest.getValue()).booleanValue()) {
            BlockPos blockPos2 = this.getBlockPos2();
            if (blockPos2 != null) {
                this.blockPos15 = blockPos2;
                this.blockPos10 = blockPos2;
                this.type11 = Type.OPENING_ECHEST;
                return;
            }
            if (((Boolean)this.place.getValue()).booleanValue()) {
                if (this.m950(Items.ENDER_CHEST) != -1) {
                    this.type11 = Type.PLACING_ECHEST;
                    return;
                }
            }
        }
        if (((Boolean)this.autoDisable.getValue()).booleanValue()) {
            this.setEnabled(false);
        }
    }

    private void tickPlacingShulker() {
        Object var2_1 = null;
        if ((double)(System.currentTimeMillis() - this.time28) < (Double)this.placeDelay.getValue()) {
            return;
        }
        BlockPos blockPos = this.getBlockPos6();
        if (blockPos != null) {
            this.blockPos15 = blockPos;
            this.type11 = Type.OPENING_SHULKER;
            return;
        }
        int n = this.getInt41();
        if (n == -1) {
            this.type11 = Type.IDLE;
            return;
        }
        BlockPos blockPos2 = this.getBlockPos3();
        if (blockPos2 == null) {
            return;
        }
        this.m678(blockPos2, (java.util.function.Predicate<ItemStack>) itemStack -> {
            Item item = itemStack.getItem();
            Object var1_2 = null;
            if (!(item instanceof BlockItem)) return false;
            BlockItem blockItem = (BlockItem)item;
            if (!(blockItem.getBlock() instanceof ShulkerBoxBlock)) return false;
            return true;
        });
        this.blockPos15 = blockPos2;
        this.time28 = System.currentTimeMillis();
        this.type11 = Type.OPENING_SHULKER;
    }

    private void m791() {
        Object var2_1 = null;
        if (MC.mc.player.currentScreenHandler instanceof ShulkerBoxScreenHandler) {
            this.flag39 = (Boolean)this.silentDisplay.getValue();
            this.type11 = Type.INSIDE_SHULKER;
            this.blockPos10 = this.blockPos15;
            return;
        }
        if ((double)(System.currentTimeMillis() - this.time15) < (Double)this.openDelay.getValue()) {
            return;
        }
        if (this.blockPos15 != null) {
            this.setObj68(this.blockPos15);
            this.setObj114(this.blockPos15);
            this.time15 = System.currentTimeMillis();
        }
    }

    private void m299() {
        block23: {
            block22: {
                ShulkerBoxScreenHandler shulkerBoxScreenHandler;
                block19: {
                    block21: {
                        block20: {
                            block18: {
                                ScreenHandler screenHandler = MC.mc.player.currentScreenHandler;
                                Object var2_3 = null;
                                if (!(screenHandler instanceof ShulkerBoxScreenHandler)) break block18;
                                shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)screenHandler;
                                if (null == null) break block19;
                            }
                            this.flag39 = false;
                            this.flag13 = false;
                            if (((Boolean)this.mine.getValue()).booleanValue()) {
                                if (this.blockPos10 != null) {
                                    if (MC.mc.world.getBlockState(this.blockPos10).getBlock() instanceof ShulkerBoxBlock) {
                                        this.setObj64(this.blockPos10);
                                    }
                                }
                            }
                            this.blockPos10 = null;
                            this.blockPos15 = null;
                            this.time28 = System.currentTimeMillis();
                            if (!((Boolean)this.autoDisable.getValue()).booleanValue()) break block20;
                            this.type11 = Type.DONE;
                            if (null == null) break block21;
                        }
                        this.type11 = Type.IDLE;
                    }
                    return;
                }
                this.flag39 = (Boolean)this.silentDisplay.getValue();
                if (this.blockPos10 != null) {
                    this.setObj68(this.blockPos10);
                }
                if (!((Boolean)this.take.getValue()).booleanValue()) {
                    MC.mc.player.closeHandledScreen();
                    this.time28 = System.currentTimeMillis();
                    this.type11 = Type.DONE;
                    return;
                }
                if ((double)(System.currentTimeMillis() - this.time56) < (Double)this.regearDelay.getValue()) {
                    return;
                }
                if (((Boolean)this.regearArmor.getValue()).booleanValue()) {
                    if (!this.flag13) {
                        if (this.m257(shulkerBoxScreenHandler)) {
                            this.time56 = System.currentTimeMillis();
                            this.time23 = System.currentTimeMillis();
                            return;
                        }
                        this.flag13 = true;
                    }
                }
                boolean bl = false;
                for (int i = 0; i < this.perAction.getInt(); ++i) {
                    boolean bl2 = false;
                    if (((Boolean)this.returnExtras.getValue()).booleanValue()) {
                        bl2 = this.m870(shulkerBoxScreenHandler);
                    }
                    if (!bl2) {
                        bl2 = this.m72(shulkerBoxScreenHandler);
                    }
                    if (!bl2) {
                        bl2 = this.m156(shulkerBoxScreenHandler);
                    }
                    if (!bl2) break;
                    bl = true;
                    if (null == null) continue;
                    break;
                }
                if (!bl) break block22;
                this.time56 = System.currentTimeMillis();
                this.time23 = System.currentTimeMillis();
                if (null == null) break block23;
            }
            if (((Boolean)this.autoDisable.getValue()).booleanValue() && (double)(System.currentTimeMillis() - this.time23) > (Double)this.disableTime.getValue()) {
                MC.mc.player.closeHandledScreen();
                this.time28 = System.currentTimeMillis();
            }
        }
    }

    private void tickPlacingEchest() {
        Object var2_1 = null;
        if ((double)(System.currentTimeMillis() - this.time28) < (Double)this.placeDelay.getValue()) {
            return;
        }
        BlockPos blockPos = this.getBlockPos2();
        if (blockPos != null) {
            this.blockPos15 = blockPos;
            this.blockPos10 = blockPos;
            this.type11 = Type.OPENING_ECHEST;
            return;
        }
        int n = this.m950(Items.ENDER_CHEST);
        if (n == -1) {
            this.type11 = Type.IDLE;
            return;
        }
        BlockPos blockPos2 = this.getBlockPos3();
        if (blockPos2 == null) {
            return;
        }
        this.m678(blockPos2, (java.util.function.Predicate<ItemStack>) itemStack -> itemStack.isOf(Items.ENDER_CHEST));
        this.blockPos15 = blockPos2;
        this.blockPos10 = blockPos2;
        this.time28 = System.currentTimeMillis();
        this.type11 = Type.OPENING_ECHEST;
    }

    private void m202() {
        Object var2_1 = null;
        if (MC.mc.player.currentScreenHandler instanceof GenericContainerScreenHandler) {
            this.type11 = Type.INSIDE_ECHEST;
            return;
        }
        if ((double)(System.currentTimeMillis() - this.time15) < (Double)this.openDelay.getValue()) {
            return;
        }
        if (this.blockPos15 != null && MC.mc.world.getBlockState(this.blockPos15).getBlock() instanceof EnderChestBlock) {
            this.setObj68(this.blockPos15);
            this.setObj53(this.blockPos15);
            this.time15 = System.currentTimeMillis();
            return;
        }
        BlockPos blockPos = this.getBlockPos2();
        if (blockPos != null) {
            this.blockPos15 = blockPos;
            this.blockPos10 = blockPos;
            this.setObj68(this.blockPos15);
            this.setObj53(this.blockPos15);
            this.time15 = System.currentTimeMillis();
            return;
        }
        this.blockPos15 = null;
        this.blockPos10 = null;
        this.type11 = Type.IDLE;
    }

    private void m917() {
        block15: {
            block13: {
                block14: {
                    GenericContainerScreenHandler genericContainerScreenHandler;
                    block10: {
                        block12: {
                            block11: {
                                block9: {
                                    ScreenHandler screenHandler = MC.mc.player.currentScreenHandler;
                                    Object var2_3 = null;
                                    if (!(screenHandler instanceof GenericContainerScreenHandler)) break block9;
                                    genericContainerScreenHandler = (GenericContainerScreenHandler)screenHandler;
                                    if (null == null) break block10;
                                }
                                this.flag39 = false;
                                if (this.getInt41() == -1) break block11;
                                this.blockPos15 = null;
                                this.blockPos10 = null;
                                this.type11 = Type.PLACING_SHULKER;
                                if (null == null) break block12;
                            }
                            this.type11 = Type.DONE;
                        }
                        return;
                    }
                    this.flag39 = (Boolean)this.silentDisplay.getValue();
                    if ((double)(System.currentTimeMillis() - this.time56) < (Double)this.enderChestDelay.getValue()) {
                        return;
                    }
                    if (this.getInt41() != -1) {
                        if ((double)(System.currentTimeMillis() - this.time56) < (Double)this.enderChestDelay.getValue()) {
                            return;
                        }
                        MC.mc.player.closeHandledScreen();
                        this.time28 = System.currentTimeMillis();
                        return;
                    }
                    int n = this.m806(genericContainerScreenHandler);
                    if (n == -1) break block13;
                    if (!this.isSet139()) break block14;
                    this.m814(genericContainerScreenHandler.syncId, n, 0, SlotActionType.QUICK_MOVE);
                    this.time56 = System.currentTimeMillis();
                    if (null == null) break block15;
                }
                MC.mc.player.closeHandledScreen();
                this.type11 = Type.DONE;
                if (null == null) break block15;
            }
            MC.mc.player.closeHandledScreen();
            this.type11 = Type.DONE;
        }
    }

    private void setObj68(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Vec3d vec3d = Vec3d.ofCenter((Vec3i)blockPos);
        Object var4_4 = null;
        float[] fArray = MathUtil.getLookAngles(MC.mc.player.getEyePos(), vec3d);
        switch (Lambda.counts15[this.getRotateMode10().ordinal()]) {
            case 1: {
                Client.mathUtil.setTargetRotation(fArray[0], fArray[1]);
                Client.mathUtil.setFloat6((float)this.getDouble6());
                if (null == null) break;
            }
            case 2: {
                Client.mathUtil.setRotationSilent(fArray[0], fArray[1]);
                if (null == null) break;
            }
            case 3: {
                Client.mathUtil.setRotationVisible(fArray[0], fArray[1]);
                break;
            }
        }
    }

    private void m879() {
        Object var2_1 = null;
        switch (Lambda.counts15[this.getRotateMode10().ordinal()]) {
            case 2: {
                Client.mathUtil.resetRotationSilent();
                if (null == null) break;
            }
            case 3: {
                Client.mathUtil.resetRotationVisible();
                break;
            }
        }
    }

    private ClientSetting.RotateMode getRotateMode10() {
        RotateMode rotateMode = (RotateMode)((Object)this.rotateMode.getValue());
        Object var2_2 = null;
        if (rotateMode == RotateMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.RotateMode)((Object)ClientSetting.INSTANCE.rotateMode.getValue()) : ClientSetting.RotateMode.NONE;
        }
        return switch (rotateMode.ordinal()) {
            case 1 -> ClientSetting.RotateMode.NONE;
            case 2 -> ClientSetting.RotateMode.SMOOTH;
            case 3 -> ClientSetting.RotateMode.ONTICK;
            case 4 -> ClientSetting.RotateMode.rotateMode;
            default -> ClientSetting.RotateMode.NONE;
        };
    }

    private double getDouble6() {
        Object var2_1 = null;
        return ClientSetting.INSTANCE != null ? (Double)ClientSetting.INSTANCE.rotateSpeed.getValue() : 45.0;
    }

    private ClientSetting.SwitchMode getSwitchMode() {
        SwitchMode switchMode = (SwitchMode)((Object)this.switchMode.getValue());
        Object var2_2 = null;
        if (switchMode == SwitchMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.SwitchMode)((Object)ClientSetting.INSTANCE.switchMode.getValue()) : ClientSetting.SwitchMode.NONE;
        }
        return switch (switchMode.ordinal()) {
            case 1 -> ClientSetting.SwitchMode.NONE;
            case 2 -> ClientSetting.SwitchMode.NORMAL;
            case 3 -> ClientSetting.SwitchMode.SILENT;
            case 4 -> ClientSetting.SwitchMode.INVENTORY;
            default -> ClientSetting.SwitchMode.NONE;
        };
    }

    private void setObj53(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (MC.mc.interactionManager == null) {
            return;
        }
        Vec3d vec3d = Vec3d.ofCenter((Vec3i)blockPos).add(0.0, 0.5, 0.0);
        Direction direction = MC.mc.world.getBlockState(blockPos.up()).isAir() ? Direction.UP : Direction.NORTH;
        if (direction == Direction.NORTH) {
            vec3d = Vec3d.ofCenter((Vec3i)blockPos);
        }
        MC.mc.interactionManager.interactBlock(MC.mc.player, Hand.MAIN_HAND, new BlockHitResult(vec3d, direction, blockPos, false));
        MC.mc.player.swingHand(Hand.MAIN_HAND, false);
        this.m879();
    }

    private void setObj114(Object object) {
        BlockPos blockPos = (BlockPos)object;
        if (MC.mc.interactionManager == null) {
            return;
        }
        Vec3d vec3d = Vec3d.ofCenter((Vec3i)blockPos).add(0.0, 0.5, 0.0);
        MC.mc.interactionManager.interactBlock(MC.mc.player, Hand.MAIN_HAND, new BlockHitResult(vec3d, Direction.UP, blockPos, false));
        MC.mc.player.swingHand(Hand.MAIN_HAND, false);
    }

    private void m678(Object object, Object object2) {
        BlockUtil.Data data;
        ClientSetting.SwitchMode switchMode;
        BlockPos blockPos;
        block10: {
            Predicate predicate;
            block9: {
                blockPos = (BlockPos)object;
                predicate = (Predicate)object2;
                switchMode = this.getSwitchMode();
                Object var6_6 = null;
                if (switchMode != ClientSetting.SwitchMode.NONE) break block9;
                ItemStack itemStack = MC.mc.player.getInventory().getStack(MC.mc.player.getInventory().getSelectedSlot());
                if (!predicate.test(itemStack)) {
                    return;
                }
                if (null == null) break block10;
            }
            if (!Client.renderUtil3.switchToItem(predicate, (Object)switchMode)) {
                return;
            }
        }
        data = BlockUtil.m573(blockPos);
        if (data != null) {
            Vec3d vec3d = data.getVec3d5();
            if (MC.mc.player.getEyePos().distanceTo(vec3d) <= (Double)this.range.getValue()) {
                if (this.getRotateMode10() != ClientSetting.RotateMode.NONE) {
                    float[] fArray = MathUtil.getLookAngles(MC.mc.player.getEyePos(), vec3d);
                    MC.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.LookAndOnGround(fArray[0], fArray[1], MC.mc.player.isOnGround(), MC.mc.player.horizontalCollision));
                }
                BlockUtil.m859(blockPos, data, Hand.MAIN_HAND);
            }
        }
        if (switchMode == ClientSetting.SwitchMode.SILENT || switchMode == ClientSetting.SwitchMode.INVENTORY) {
            Client.renderUtil3.restoreSlot();
        }
        this.m879();
    }

    private void setObj64(Object object) {
        block5: {
            BlockPos blockPos;
            block4: {
                blockPos = (BlockPos)object;
                Object var4_3 = null;
                if (MC.mc.interactionManager == null) {
                    return;
                }
                if (SpeedMine.INSTANCE == null) break block4;
                if (!SpeedMine.INSTANCE.isEnabled()) break block4;
                SpeedMine.INSTANCE.setObj84(blockPos);
                if (null == null) break block5;
            }
            MC.mc.interactionManager.attackBlock(blockPos, Direction.UP);
        }
    }

    private BlockPos getBlockPos6() {
        block6: {
            block5: {
                Object var2_1 = null;
                if (MC.mc.player == null) break block5;
                if (MC.mc.world != null) break block6;
            }
            return null;
        }
        BlockPos blockPos = MC.mc.player.getBlockPos();
        int n = this.range.getInt();
        for (int i = -n; i <= n; ++i) {
            for (int j = -n; j <= n; ++j) {
                for (int k = -n; k <= n; ++k) {
                    BlockPos blockPos2 = blockPos.add(i, j, k);
                    if (!(MC.mc.world.getBlockState(blockPos2).getBlock() instanceof ShulkerBoxBlock)) continue;
                    if (!MC.mc.world.getBlockState(blockPos2.up()).isAir() || !MC.mc.world.getOtherEntities(null, new Box(blockPos2)).isEmpty()) continue;
                    return blockPos2;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
        return null;
    }

    private BlockPos getBlockPos2() {
        block6: {
            block5: {
                Object var2_1 = null;
                if (MC.mc.player == null) break block5;
                if (MC.mc.world != null) break block6;
            }
            return null;
        }
        BlockPos blockPos = MC.mc.player.getBlockPos();
        int n = this.range.getInt();
        for (int i = -n; i <= n; ++i) {
            for (int j = -n; j <= n; ++j) {
                for (int k = -n; k <= n; ++k) {
                    BlockPos blockPos2 = blockPos.add(i, j, k);
                    if (!(MC.mc.world.getBlockState(blockPos2).getBlock() instanceof EnderChestBlock) || !MC.mc.world.getBlockState(blockPos2.up()).isAir()) continue;
                    return blockPos2;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
        return null;
    }

    private BlockPos getBlockPos3() {
        block9: {
            block8: {
                Object var2_1 = null;
                if (MC.mc.player == null) break block8;
                if (MC.mc.world != null) break block9;
            }
            return null;
        }
        BlockPos blockPos = MC.mc.player.getBlockPos();
        int n = this.range.getInt();
        BlockPos blockPos2 = null;
        double d = Double.NEGATIVE_INFINITY;
        for (int i = -n; i <= n; ++i) {
            for (int j = -n; j <= n; ++j) {
                for (int k = -n; k <= n; ++k) {
                    BlockPos blockPos3 = blockPos.add(i, j, k);
                    if (!MC.mc.world.getBlockState(blockPos3).isReplaceable()) continue;
                    BlockState blockState = MC.mc.world.getBlockState(blockPos3.down());
                    if (blockState.isAir()) continue;
                    if (blockState.isReplaceable() || !MC.mc.world.getBlockState(blockPos3.up()).isAir() || !MC.mc.world.getOtherEntities(null, new Box(blockPos3)).isEmpty()) continue;
                    double d2 = MC.mc.player.squaredDistanceTo((double)blockPos3.getX() + 0.5, (double)blockPos3.getY() + 0.5, (double)blockPos3.getZ() + 0.5);
                    if (d2 < 1.0) continue;
                    if (d2 > (Double)this.range.getValue() * (Double)this.range.getValue()) continue;
                    int n2 = 0;
                    for (Direction direction : Direction.Type.HORIZONTAL) {
                        if (!MC.mc.world.getBlockState(blockPos3.offset(direction)).isReplaceable()) {
                            ++n2;
                        }
                        if (null == null) continue;
                    }
                    double d3 = (double)n2 * 0.5 - d2;
                    if (!(d3 > d)) continue;
                    d = d3;
                    blockPos2 = blockPos3;
                    if (null == null) continue;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
        return blockPos2;
    }

    private boolean m257(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Object var4_4 = null;
        for (int i = 36; i <= 39; ++i) {
            if (!this.map27.containsKey(i)) continue;
            Item item = (Item)this.map27.get(i);
            if (item == null) continue;
            if (item == Items.AIR || MC.mc.player.getInventory().getStack(i).getItem() == item) continue;
            for (int j = 0; j < 27; ++j) {
                if (shulkerBoxScreenHandler.getSlot(j).getStack().getItem() != item) continue;
                this.m814(shulkerBoxScreenHandler.syncId, j, 0, SlotActionType.QUICK_MOVE);
                return true;
            }
            if (null == null) continue;
        }
        return false;
    }

    private boolean m870(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Object var4_4 = null;
        for (int i = 0; i < 36; ++i) {
            int n = i < 9 ? 54 + i : 27 + (i - 9);
            ItemStack itemStack = shulkerBoxScreenHandler.getSlot(n).getStack();
            if (itemStack.isEmpty()) continue;
            Item item = itemStack.getItem();
            Item item2 = (Item)this.map27.getOrDefault(i, Items.AIR);
            if (this.m918(item, item2) || this.m684(shulkerBoxScreenHandler, item, i)) continue;
            if (item instanceof BlockItem) {
                BlockItem blockItem = (BlockItem)item;
                if (blockItem.getBlock() instanceof ShulkerBoxBlock) continue;
            }
            if (this.m199(shulkerBoxScreenHandler) == -1) continue;
            this.m814(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.QUICK_MOVE);
            return true;
        }
        return false;
    }

    private boolean m72(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Object var4_4 = null;
        for (int i = 0; i < 36; ++i) {
            Item item = (Item)this.map27.getOrDefault(i, Items.AIR);
            if (item == Items.AIR || item == null) continue;
            int n = i < 9 ? 54 + i : 27 + (i - 9);
            ItemStack itemStack = shulkerBoxScreenHandler.getSlot(n).getStack();
            if (this.m918(itemStack.getItem(), item)) continue;
            int n2 = this.m701(shulkerBoxScreenHandler, item, i);
            if (n2 == -1) continue;
            int n3 = n2 < 9 ? 54 + n2 : 27 + (n2 - 9);
            this.m814(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
            this.m814(shulkerBoxScreenHandler.syncId, n3, 0, SlotActionType.PICKUP);
            this.m814(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
            return true;
        }
        return false;
    }

    private boolean m156(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Iterator iterator = this.map27.entrySet().iterator();
        Object var4_4 = null;
        while (iterator.hasNext()) {
            int n;
            Map.Entry entry = (Map.Entry)iterator.next();
            int n2 = (Integer)entry.getKey();
            if (n2 >= 36) continue;
            Item item = (Item)entry.getValue();
            if (item == null || item == Items.AIR) continue;
            int n3 = n2 < 9 ? 54 + n2 : 27 + (n2 - 9);
            ItemStack itemStack = shulkerBoxScreenHandler.getSlot(n3).getStack();
            if (!itemStack.isEmpty()) {
                if (!this.m918(itemStack.getItem(), item) || itemStack.getCount() >= itemStack.getMaxCount()) continue;
                n = this.m1002(shulkerBoxScreenHandler, itemStack);
                if (n == -1) continue;
                this.m814(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
                this.m814(shulkerBoxScreenHandler.syncId, n3, 0, SlotActionType.PICKUP);
                if (!shulkerBoxScreenHandler.getCursorStack().isEmpty()) {
                    this.m814(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
                }
                return true;
            }
            n = this.m723(shulkerBoxScreenHandler, item);
            if (n == -1) continue;
            this.m814(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
            this.m814(shulkerBoxScreenHandler.syncId, n3, 0, SlotActionType.PICKUP);
            if (!shulkerBoxScreenHandler.getCursorStack().isEmpty()) {
                this.m814(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
            }
            return true;
        }
        return false;
    }

    private int m806(Object object) {
        int n;
        GenericContainerScreenHandler genericContainerScreenHandler = (GenericContainerScreenHandler)object;
        int n2 = Math.min(27, genericContainerScreenHandler.slots.size());
        Object var4_4 = null;
        if (!((Boolean)this.smartSearch.getValue()).booleanValue()) {
            for (int i = 0; i < n2; ++i) {
                if (!this.m203(genericContainerScreenHandler.getSlot(i).getStack())) continue;
                return i;
            }
            return -1;
        }
        List list = this.getList9();
        int n3 = -1;
        int n4 = -1;
        for (n = 0; n < n2; ++n) {
            ItemStack itemStack = genericContainerScreenHandler.getSlot(n).getStack();
            if (!this.m203(itemStack)) continue;
            int n5 = this.m682(itemStack, list);
            if (n5 <= n4) continue;
            n4 = n5;
            n3 = n;
            if (null == null) continue;
            break;
        }
        if (n3 == -1) {
            for (n = 0; n < n2; ++n) {
                if (!this.m203(genericContainerScreenHandler.getSlot(n).getStack())) continue;
                return n;
            }
        }
        return n3;
    }

    /*
     * Unable to fully structure code
     */
    private List getList9() {
        ArrayList list = new ArrayList();
        if (MC.mc.player == null) {
            return list;
        }
        Iterator iterator = this.map27.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            int n = (Integer)entry.getKey();
            Item item = (Item)entry.getValue();
            if (item != null && item != Items.AIR && n >= 0 && n < 40) {
                ItemStack itemStack = MC.mc.player.getInventory().getStack(n);
                boolean bl = !this.m918(itemStack.getItem(), item) || itemStack.isStackable() && itemStack.getCount() < itemStack.getMaxCount();
                if (bl) {
                    boolean bl2 = false;
                    for (Object o : list) {
                        Item item2 = (Item)o;
                        if (this.m918(item2, item)) {
                            bl2 = true;
                            break;
                        }
                    }
                    if (!bl2) {
                        list.add(item);
                    }
                }
            }
        }
        return list;
    }

    private int m682(Object object, Object object2) {
        ItemStack itemStack = (ItemStack)object;
        List list = (List)object2;
        int n = 0;
        Object var6_6 = null;
        for (Object o870 : this.m230(itemStack)) {
            ItemStack itemStack2 = (ItemStack)o870;
            if (itemStack2.isEmpty()) continue;
            Item item = itemStack2.getItem();
            for (Object o873 : list) {
                Item item2 = (Item)o873;
                if (this.m918(item2, item)) {
                    n += this.m974(item);
                    if (null == null) break;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
        return n;
    }

    private List m230(Object object) {
        ItemStack itemStack = (ItemStack)object;
        ArrayList arrayList = new ArrayList();
        Object var4_4 = null;
        ContainerComponent containerComponent = (ContainerComponent)itemStack.get(DataComponentTypes.CONTAINER);
        if (containerComponent != null) {
            containerComponent.stream().forEach(arrayList::add);
        }
        return arrayList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m724(Object object) {
        Item item = (Item)object;
        Object var4_3 = null;
        if (item == Items.NETHERITE_HELMET) return true;
        if (item == Items.NETHERITE_CHESTPLATE) return true;
        if (item == Items.NETHERITE_LEGGINGS) return true;
        if (item == Items.NETHERITE_BOOTS) return true;
        if (item == Items.DIAMOND_HELMET) return true;
        if (item == Items.DIAMOND_CHESTPLATE) return true;
        if (item == Items.DIAMOND_LEGGINGS) return true;
        if (item == Items.DIAMOND_BOOTS) return true;
        if (item == Items.ELYTRA) return true;
        if (item != Items.TURTLE_HELMET) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m667(Object object) {
        Item item = (Item)object;
        Object var4_3 = null;
        if (item == Items.NETHERITE_SWORD) return true;
        if (item == Items.DIAMOND_SWORD) return true;
        if (item == Items.IRON_SWORD) return true;
        if (item == Items.NETHERITE_PICKAXE) return true;
        if (item == Items.DIAMOND_PICKAXE) return true;
        if (item != Items.IRON_PICKAXE) return false;
        return true;
    }

    private int m974(Object object) {
        Item item = (Item)object;
        Object var4_3 = null;
        if (this.m724(item)) {
            return this.armorCoin.getInt();
        }
        if (this.m667(item)) {
            return this.weaponCoin.getInt();
        }
        return this.regearItemCoin.getInt();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m918(Object object, Object object2) {
        Item item = (Item)object;
        Item item2 = (Item)object2;
        Object var6_5 = null;
        if (item == item2) {
            return true;
        }
        boolean bl = item == Items.PISTON || item == Items.STICKY_PISTON;
        boolean bl2 = item2 == Items.PISTON || item2 == Items.STICKY_PISTON;
        if (!bl) return false;
        if (!bl2) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m203(Object object) {
        ItemStack itemStack = (ItemStack)object;
        Object var4_3 = null;
        if (itemStack.isEmpty()) return false;
        Item item = itemStack.getItem();
        if (!(item instanceof BlockItem)) return false;
        BlockItem blockItem = (BlockItem)item;
        if (!(blockItem.getBlock() instanceof ShulkerBoxBlock)) return false;
        return true;
    }

    private boolean m684(Object object, Object object2, int n) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Item item = (Item)object2;
        int n2 = n;
        Iterator iterator = this.map27.entrySet().iterator();
        Object var8_8 = null;
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            int n3 = (Integer)entry.getKey();
            if (n3 >= 36 || n3 == n2) continue;
            Item item2 = (Item)entry.getValue();
            if (!this.m918(item2, item)) continue;
            int n4 = n3 < 9 ? 54 + n3 : 27 + (n3 - 9);
            if (!this.m918(shulkerBoxScreenHandler.getSlot(n4).getStack().getItem(), item2)) {
                return true;
            }
            if (null == null) continue;
        }
        return false;
    }

    private int m701(Object object, Object object2, int n) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Item item = (Item)object2;
        int n2 = n;
        Object var8_8 = null;
        for (int i = 0; i < 36; ++i) {
            if (i == n2) continue;
            int n3 = i < 9 ? 54 + i : 27 + (i - 9);
            ItemStack itemStack = shulkerBoxScreenHandler.getSlot(n3).getStack();
            if (!this.m918(itemStack.getItem(), item) || this.m918(this.map27.getOrDefault(i, Items.AIR), item)) continue;
            return i;
        }
        return -1;
    }

    private int m723(Object object, Object object2) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Item item = (Item)object2;
        int n = -1;
        int n2 = -1;
        Object var6_7 = null;
        for (int i = 0; i < 27; ++i) {
            ItemStack itemStack = shulkerBoxScreenHandler.getSlot(i).getStack();
            if (!this.m918(itemStack.getItem(), item)) continue;
            if (itemStack.getCount() <= n2) continue;
            n2 = itemStack.getCount();
            n = i;
            if (null == null) continue;
        }
        return n;
    }

    private int m1002(Object object, Object object2) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        ItemStack itemStack = (ItemStack)object2;
        Object var6_6 = null;
        for (int i = 0; i < 27; ++i) {
            ItemStack itemStack2 = shulkerBoxScreenHandler.getSlot(i).getStack();
            if (itemStack2.getItem() != itemStack.getItem()) continue;
            if (!ItemStack.areItemsAndComponentsEqual((ItemStack)itemStack2, (ItemStack)itemStack)) continue;
            return i;
        }
        return -1;
    }

    private int m199(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Object var4_4 = null;
        for (int i = 0; i < 27; ++i) {
            if (!shulkerBoxScreenHandler.getSlot(i).getStack().isEmpty()) continue;
            return i;
        }
        return -1;
    }

    private int getInt41() {
        Object var2_1 = null;
        if (MC.mc.player == null) {
            return -1;
        }
        for (int i = 0; i < 36; ++i) {
            if (!this.m203(MC.mc.player.getInventory().getStack(i))) continue;
            return i;
        }
        return -1;
    }

    private int m950(Object object) {
        Item item = (Item)object;
        Object var4_3 = null;
        if (MC.mc.player == null) {
            return -1;
        }
        for (int i = 0; i < 36; ++i) {
            if (!MC.mc.player.getInventory().getStack(i).isOf(item)) continue;
            return i;
        }
        return -1;
    }

    private boolean isSet139() {
        Object var2_1 = null;
        if (MC.mc.player == null) {
            return false;
        }
        for (int i = 0; i < 36; ++i) {
            if (!MC.mc.player.getInventory().getStack(i).isEmpty()) continue;
            return true;
        }
        return false;
    }

    private void m814(int n, int n2, int n3, Object object) {
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        SlotActionType slotActionType = (SlotActionType)object;
        Object var10_9 = null;
        if (MC.mc.player != null) {
            if (MC.mc.interactionManager != null) {
                MC.mc.interactionManager.clickSlot(n4, n5, n6, slotActionType, (PlayerEntity)MC.mc.player);
            }
        }
    }

    private boolean m344(Object object) {
        String string = (String)object;
        this.map27.clear();
        Object var4_3 = null;
        try {
            String string2;
            File file = new File(MC.mc.runDirectory, "kissoo/kits/" + string + ".kit");
            if (!file.exists()) {
                return false;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(file), StandardCharsets.UTF_8));
            while ((string2 = bufferedReader.readLine()) != null) {
                String[] stringArray = string2.split(":");
                if (stringArray.length >= 3) {
                    int n = Integer.parseInt(stringArray[0].trim());
                    if (n == 40) continue;
                    String string3 = stringArray[1].trim() + ":" + stringArray[2].trim();
                    Item item = Registries.ITEM.getEntry(Identifier.of((String)string3)).map(RegistryEntry.Reference::value).orElse(null);
                    if (item != null) {
                        if (item != Items.AIR) {
                            this.map27.put(n, item);
                        }
                    }
                }
                if (null == null) continue;
            }
            bufferedReader.close();
            return !this.map27.isEmpty();
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static enum Type {
      IDLE, PLACING_SHULKER, OPENING_SHULKER, INSIDE_SHULKER, OPENING_ECHEST, INSIDE_ECHEST, PLACING_ECHEST, DONE;

      private Type() {}



        private static Type[] getTypeArray3() {
            return new Type[]{IDLE, PLACING_SHULKER, OPENING_SHULKER, INSIDE_SHULKER, OPENING_ECHEST, INSIDE_ECHEST, PLACING_ECHEST, DONE};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts15 = new int[ClientSetting.RotateMode.values().length];

        static {
            try {
                Lambda.counts15[ClientSetting.RotateMode.SMOOTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts15[ClientSetting.RotateMode.ONTICK.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts15[ClientSetting.RotateMode.rotateMode.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum RotateMode {
      DEFAULT, NONE, SMOOTH, ONTICK, rotateMode5;

      private RotateMode() {}



        private static RotateMode[] getRotateModeArray12() {
            return new RotateMode[]{DEFAULT, NONE, SMOOTH, ONTICK, rotateMode5};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum SwitchMode {
      DEFAULT, NONE, NORMAL, SILENT, INVENTORY;

      private SwitchMode() {}



        private static SwitchMode[] getSwitchModeArray6() {
            return new SwitchMode[]{DEFAULT, NONE, NORMAL, SILENT, INVENTORY};
        }
    
   }
}

