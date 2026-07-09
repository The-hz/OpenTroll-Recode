/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.block.ShulkerBoxBlock
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.BlockItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.Items
 *  net.minecraft.item.PotionItem
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket
 *  net.minecraft.registry.Registries
 *  net.minecraft.registry.entry.RegistryEntry$Reference
 *  net.minecraft.screen.ScreenHandler
 *  net.minecraft.screen.ShulkerBoxScreenHandler
 *  net.minecraft.screen.slot.SlotActionType
 *  net.minecraft.util.Hand
 *  net.minecraft.util.Identifier
 *  net.minecraft.util.hit.BlockHitResult
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Direction
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.math.Vec3i
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
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
import shit.module.combat.AutoRegear;
import shit.module.player.SpeedMine;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.setting.StringSetting;
import shit.util.MC;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Environment(value=EnvType.CLIENT)
public class AntiRegear
extends Module {
    public static AntiRegear INSTANCE;
    public boolean flag38 = false;
    private final EnumSetting mode;
    private final StringSetting profile;
    private final EnumSetting rotateMode;
    private final NumberSetting searchRadius;
    private final NumberSetting interactRange;
    private final NumberSetting friendRange;
    private final NumberSetting safeTime;
    private final NumberSetting actionInterval;
    private final NumberSetting batchActions;
    private final NumberSetting closeDelay;
    private final NumberSetting weaponCoin;
    private final NumberSetting armorCoin;
    private final NumberSetting potCoin;
    private final NumberSetting itemCoin;
    private final NumberSetting stealThreshold;
    private final BooleanSetting regearArmor;
    private final BooleanSetting returnExtras;
    private final BooleanSetting mineAfter;
    private final BooleanSetting continuous;
    private final BooleanSetting requireEnemyNearby;
    private final NumberSetting enemyRange;
    private final BooleanSetting silentDisplay;
    private BlockPos blockPos8 = null;
    private BlockPos blockPos16 = null;
    private boolean flag152 = false;
    private boolean flag156 = false;
    private Mode mode2 = null;
    private boolean flag115 = false;
    private boolean flag122 = false;
    private boolean flag118 = false;
    private final List list23;
    private int count146 = 0;
    private long time50 = 0L;
    private long time5 = 0L;
    private long time70 = 0L;
    private final Set set6;
    private final Set set7;
    private final Map map21;
    private BlockPos blockPos18 = null;
    private final Map map14;

            public AntiRegear() {
        super("AntiRegear", "Steals or wastes gear from nearby enemy shulker boxes.", Category.COMBAT);
        this.flag38 = false;
        this.mode = new EnumSetting("Mode", Mode.SMART);
        this.profile = (StringSetting)this.m28(new StringSetting("Profile", "default"));
        this.rotateMode = (EnumSetting)this.m28(new EnumSetting("RotateMode", RotateMode.DEFAULT));
        this.searchRadius = (NumberSetting)this.m28(new NumberSetting("SearchRadius", 6.0, 1.0, 10.0, 0.5));
        this.interactRange = (NumberSetting)this.m28(new NumberSetting("InteractRange", 4.5, 2.0, 6.0, 0.1));
        this.friendRange = (NumberSetting)this.m28(new NumberSetting("FriendRange", 8.0, 0.0, 20.0, 0.5));
        this.safeTime = (NumberSetting)this.m28(new NumberSetting("SafeTime", 10.0, 1.0, 60.0, 1.0));
        this.actionInterval = (NumberSetting)this.m28(new NumberSetting("ActionInterval", 50.0, 0.0, 1000.0, 10.0));
        this.batchActions = (NumberSetting)this.m28(new NumberSetting("BatchActions", 1.0, 1.0, 10.0, 1.0));
        this.closeDelay = (NumberSetting)this.m28(new NumberSetting("CloseDelay", 1000.0, 100.0, 3000.0, 50.0));
        this.weaponCoin = (NumberSetting)this.m28(new NumberSetting("WeaponCoin", 100.0, 1.0, 1000.0, 1.0));
        this.armorCoin = (NumberSetting)this.m28(new NumberSetting("ArmorCoin", 50.0, 1.0, 1000.0, 1.0));
        this.potCoin = (NumberSetting)this.m28(new NumberSetting("PotCoin", 10.0, 1.0, 1000.0, 1.0));
        this.itemCoin = (NumberSetting)this.m28(new NumberSetting("ItemCoin", 1.0, 1.0, 1000.0, 1.0));
        this.stealThreshold = (NumberSetting)this.m28(new NumberSetting("StealThreshold", 200.0, 0.0, 5000.0, 10.0));
        this.regearArmor = (BooleanSetting)this.m28(new BooleanSetting("RegearArmor", true));
        this.returnExtras = (BooleanSetting)this.m28(new BooleanSetting("ReturnExtras", true));
        this.mineAfter = (BooleanSetting)this.m28(new BooleanSetting("MineAfter", true));
        this.continuous = (BooleanSetting)this.m28(new BooleanSetting("Continuous", true));
        this.requireEnemyNearby = (BooleanSetting)this.m28(new BooleanSetting("RequireEnemyNearby", true));
        this.enemyRange = (NumberSetting)this.m28(new NumberSetting("EnemyRange", 15.0, 5.0, 50.0, 0.5));
        this.silentDisplay = (BooleanSetting)this.m28(new BooleanSetting("SilentDisplay", true));
        this.list23 = new ArrayList();
        this.set6 = new HashSet();
        this.set7 = new HashSet();
        this.map21 = new HashMap();
        this.map14 = new HashMap();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet81() {
        Object var1 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isSet19()) return false;
        if ((Boolean)AntiRegear.INSTANCE.silentDisplay.getObj() == false) return false;
        if (!AntiRegear.INSTANCE.flag38) return false;
        return true;
    }

    @Override
    public void onEnable() {
        Object var2_1 = null;
        if (Module.isSet37()) {
            return;
        }
        this.m580();
        if (this.mode.getObj() != Mode.THROW) {
            if (!this.m362((String)this.profile.getObj())) {
                this.setObj12("\u00a7cFailed to load profile: " + (String)this.profile.getObj() + ".kit");
                this.setFlag3(false);
            }
        }
    }

    @Override
    public void m709() {
        Client.mathUtil.m370();
        Object var2_1 = null;
        this.flag38 = false;
        if (MC.client3.player != null) {
            if (this.flag152) {
                MC.client3.player.closeHandledScreen();
            }
        }
        this.m580();
    }

    private void m580() {
        long l;
        this.flag38 = false;
        this.flag152 = false;
        this.flag156 = false;
        this.blockPos8 = null;
        this.blockPos16 = null;
        this.mode2 = null;
        this.flag115 = false;
        this.flag122 = false;
        this.flag118 = false;
        this.list23.clear();
        this.count146 = 0;
        this.time50 = l = System.currentTimeMillis();
        this.time5 = l;
        this.time70 = l;
    }

    @EventHandler
    private void setEvent2Inner57(Event2.Event2Inner event2Inner) {
        if (MC.client3.player == null || MC.client3.world == null || MC.client3.getNetworkHandler() == null || MC.client3.interactionManager == null) {
            return;
        }
        this.updateSafeBoxes();
        if (((Boolean)this.requireEnemyNearby.getObj()).booleanValue() && !this.isSet21()) {
            if (this.flag152 && MC.client3.player.currentScreenHandler instanceof ShulkerBoxScreenHandler) {
                MC.client3.player.closeHandledScreen();
            }
            this.flag38 = false;
            return;
        }
        ScreenHandler screenHandler = MC.client3.player.currentScreenHandler;
        if (screenHandler instanceof ShulkerBoxScreenHandler) {
            ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)screenHandler;
            this.setObj95(shulkerBoxScreenHandler);
        } else {
            this.m607();
        }
    }

    private void setObj95(Object object) {
        block14: {
            ShulkerBoxScreenHandler shulkerBoxScreenHandler;
            block13: {
                block12: {
                    Mode mode;
                    block10: {
                        block11: {
                            shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
                            this.flag152 = true;
                            Object var4_3 = null;
                            if (((Boolean)this.silentDisplay.getObj()).booleanValue()) {
                                this.flag38 = true;
                            }
                            if (this.blockPos8 != null) {
                                this.setObj48(this.blockPos8);
                            }
                            if (this.flag156) break block10;
                            this.time5 = System.currentTimeMillis();
                            this.time70 = System.currentTimeMillis();
                            this.flag156 = true;
                            if (this.mode.getObj() != Mode.SMART) break block11;
                            this.mode2 = mode = this.getMode();
                            this.flag115 = mode == Mode.STEALER;
                            this.flag122 = false;
                            if (null == null) break block10;
                        }
                        this.mode2 = (Mode)((Object)this.mode.getObj());
                        this.flag115 = false;
                        this.flag122 = false;
                    }
                    if ((double)(System.currentTimeMillis() - this.time5) > (Double)this.closeDelay.getObj()) {
                        this.m305();
                        return;
                    }
                    if (System.currentTimeMillis() - this.time50 < (long)this.actionInterval.getInt50()) {
                        return;
                    }
                    Mode mode3 = mode = this.mode2 != null ? this.mode2 : (Mode)((Object)this.mode.getObj());
                    if (mode == Mode.THROW) break block12;
                    if (!this.flag122) break block13;
                }
                if (this.m107(shulkerBoxScreenHandler)) break block14;
                this.m305();
                if (null == null) break block14;
            }
            this.setObj13(shulkerBoxScreenHandler);
        }
    }

    private void m607() {
        BlockPos blockPos;
        block11: {
            block12: {
                Object var2_1 = null;
                if (this.flag38) {
                    this.flag38 = false;
                }
                if (!this.flag152) break block11;
                this.flag152 = false;
                this.flag156 = false;
                this.flag118 = false;
                this.list23.clear();
                this.count146 = 0;
                if (this.blockPos8 != null) {
                    this.blockPos16 = this.blockPos8;
                    this.set6.add(this.blockPos8);
                    this.set7.add(this.blockPos8);
                    if (((Boolean)this.mineAfter.getObj()).booleanValue()) {
                        if (MC.client3.world.getBlockState(this.blockPos8).getBlock() instanceof ShulkerBoxBlock) {
                            if (SpeedMine.INSTANCE != null) {
                                if (SpeedMine.INSTANCE.isSet19()) {
                                    SpeedMine.INSTANCE.setObj84(this.blockPos8);
                                }
                            }
                        }
                    }
                }
                this.m382();
                if (!((Boolean)this.continuous.getObj()).booleanValue()) break block12;
                this.blockPos8 = null;
                this.blockPos16 = null;
                this.mode2 = null;
                this.flag115 = false;
                this.flag122 = false;
                if (null == null) break block11;
            }
            this.setFlag3(false);
            return;
        }
        if ((blockPos = this.getBlockPos8()) == null) {
            return;
        }
        double d = MC.client3.player.squaredDistanceTo((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
        if (d > (Double)this.interactRange.getObj() * (Double)this.interactRange.getObj()) {
            return;
        }
        this.blockPos8 = blockPos;
        this.setObj48(blockPos);
        Vec3d vec3d = Vec3d.ofCenter((Vec3i)blockPos).add(0.0, 0.5, 0.0);
        MC.client3.interactionManager.interactBlock(MC.client3.player, Hand.MAIN_HAND, new BlockHitResult(vec3d, Direction.UP, blockPos, false));
        MC.client3.player.swingHand(Hand.MAIN_HAND, false);
        this.m382();
        this.time50 = System.currentTimeMillis();
    }

    private boolean m107(Object object) {
        boolean bl;
        block2: {
            ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
            Object var4_4 = null;
            bl = false;
            for (int i = 0; i < this.batchActions.getInt50(); ++i) {
                int n = -1;
                for (int j = 0; j < 27; ++j) {
                    if (shulkerBoxScreenHandler.getSlot(j).getStack().isEmpty()) continue;
                    n = j;
                    if (null == null) break;
                    if (null == null) continue;
                    break;
                }
                if (n == -1) break;
                this.m16(shulkerBoxScreenHandler.syncId, n, 1, SlotActionType.THROW);
                bl = true;
                if (null == null) continue;
                break;
            }
            if (!bl) break block2;
            this.time50 = System.currentTimeMillis();
            this.time70 = System.currentTimeMillis();
        }
        return bl;
    }

    private void setObj13(Object object) {
        block9: {
            block8: {
                ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
                Object var4_3 = null;
                if (((Boolean)this.regearArmor.getObj()).booleanValue()) {
                    if (!this.flag118) {
                        this.setObj107(shulkerBoxScreenHandler);
                        return;
                    }
                }
                boolean bl = false;
                if (((Boolean)this.returnExtras.getObj()).booleanValue()) {
                    bl |= this.m745(shulkerBoxScreenHandler);
                }
                if (!bl) {
                    bl |= this.m89(shulkerBoxScreenHandler);
                }
                if (!bl) {
                    bl |= this.m813(shulkerBoxScreenHandler);
                }
                if (!bl) break block8;
                this.time50 = System.currentTimeMillis();
                this.time70 = System.currentTimeMillis();
                if (null == null) break block9;
            }
            if (this.flag115) {
                if (!this.flag122) {
                    this.flag122 = true;
                    this.time50 = System.currentTimeMillis();
                    this.time5 = System.currentTimeMillis();
                    this.time70 = System.currentTimeMillis();
                    return;
                }
            }
            if ((double)(System.currentTimeMillis() - this.time70) > (Double)this.closeDelay.getObj() / 2.0) {
                this.m305();
            }
        }
    }

    private void setObj107(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Object var4_3 = null;
        if (this.list23.isEmpty()) {
            if (this.count146 == 0) {
                this.buildArmorActionQueue(shulkerBoxScreenHandler);
                if (this.list23.isEmpty()) {
                    this.flag118 = true;
                    return;
                }
            }
        }
        if (this.count146 >= this.list23.size()) {
            this.flag118 = true;
            this.list23.clear();
            this.count146 = 0;
            this.time70 = System.currentTimeMillis();
            return;
        }
        for (int i = 0; i < this.batchActions.getInt50(); ++i) {
            if (this.count146 >= this.list23.size()) break;
            ((Runnable)this.list23.get(this.count146)).run();
            ++this.count146;
            if (null == null) continue;
        }
        this.time50 = System.currentTimeMillis();
        this.time70 = System.currentTimeMillis();
    }

    private void buildArmorActionQueue(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        this.list23.clear();
        Object var4_4 = null;
        for (int i = 36; i < 40; ++i) {
            int n;
            ItemStack itemStack;
            if (!this.map14.containsKey(i)) continue;
            Item item = (Item)this.map14.get(i);
            if (item == null || item == Items.AIR || (itemStack = MC.client3.player.getInventory().getStack(i)).getItem() == item) continue;
            int n2 = -1;
            for (n = 0; n < 27; ++n) {
                if (shulkerBoxScreenHandler.getSlot(n).getStack().getItem() != item) continue;
                n2 = n;
                if (null == null) break;
                if (null == null) continue;
                break;
            }
            if (n2 == -1) continue;
            n = n2;
            int n4 = n;
            int n3 = MC.client3.player.getInventory().getSelectedSlot();
            this.list23.add((Runnable)(() -> this.m16(shulkerBoxScreenHandler.syncId, n4, n3, SlotActionType.SWAP)));
            this.list23.add((Runnable)(() -> {
                float[] fArray = new float[]{Client.mathUtil.getFloat55(), Client.mathUtil.getFloat58()};
                MC.client3.player.networkHandler.sendPacket((Packet)new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, fArray[0], fArray[1]));
            }));
            this.list23.add((Runnable)(() -> this.m16(shulkerBoxScreenHandler.syncId, n4, n3, SlotActionType.SWAP)));
            if (null == null) continue;
        }
    }

    private boolean m745(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Object var4_4 = null;
        for (int i = 0; i < 36; ++i) {
            int n = i < 9 ? 54 + i : 27 + (i - 9);
            ItemStack itemStack = shulkerBoxScreenHandler.getSlot(n).getStack();
            if (itemStack.isEmpty()) continue;
            Item item = itemStack.getItem();
            Item item2 = (Item)this.map14.getOrDefault(i, Items.AIR);
            if (this.m507(item, item2) || this.m742(shulkerBoxScreenHandler, item, i)) continue;
            if (item instanceof BlockItem) {
                BlockItem blockItem = (BlockItem)item;
                if (blockItem.getBlock() instanceof ShulkerBoxBlock) continue;
            }
            if (this.m436(shulkerBoxScreenHandler) == -1) continue;
            this.m16(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.QUICK_MOVE);
            return true;
        }
        return false;
    }

    private boolean m89(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Object var4_4 = null;
        for (int i = 0; i < 36; ++i) {
            Item item = (Item)this.map14.getOrDefault(i, Items.AIR);
            if (item == null || item == Items.AIR) continue;
            int n = i < 9 ? 54 + i : 27 + (i - 9);
            ItemStack itemStack = shulkerBoxScreenHandler.getSlot(n).getStack();
            if (this.m507(itemStack.getItem(), item)) continue;
            int n2 = this.m1050(shulkerBoxScreenHandler, item, i);
            if (n2 == -1) continue;
            int n3 = n2 < 9 ? 54 + n2 : 27 + (n2 - 9);
            this.m16(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
            this.m16(shulkerBoxScreenHandler.syncId, n3, 0, SlotActionType.PICKUP);
            this.m16(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
            return true;
        }
        return false;
    }

    private boolean m813(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Iterator iterator = this.map14.entrySet().iterator();
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
                if (!this.m507(itemStack.getItem(), item) || itemStack.getCount() >= itemStack.getMaxCount()) continue;
                n = this.m1034(shulkerBoxScreenHandler, itemStack);
                if (n == -1) continue;
                this.m16(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
                this.m16(shulkerBoxScreenHandler.syncId, n3, 0, SlotActionType.PICKUP);
                if (!shulkerBoxScreenHandler.getCursorStack().isEmpty()) {
                    this.m16(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
                }
                return true;
            }
            n = this.m541(shulkerBoxScreenHandler, item);
            if (n == -1) continue;
            this.m16(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
            this.m16(shulkerBoxScreenHandler.syncId, n3, 0, SlotActionType.PICKUP);
            if (!shulkerBoxScreenHandler.getCursorStack().isEmpty()) {
                this.m16(shulkerBoxScreenHandler.syncId, n, 0, SlotActionType.PICKUP);
            }
            return true;
        }
        return false;
    }

    private Mode getMode() {
        Object var2_1 = null;
        if (this.map14.isEmpty()) {
            return Mode.THROW;
        }
        int n = 0;
        for (Map.Entry entry : (Set<Map.Entry>)this.map14.entrySet()) {
            block8: {
                ItemStack itemStack;
                Item item;
                block7: {
                    int n2 = (Integer)entry.getKey();
                    item = (Item)entry.getValue();
                    if (item == null || item == Items.AIR || n2 >= 40) continue;
                    itemStack = MC.client3.player.getInventory().getStack(n2);
                    if (this.m507(itemStack.getItem(), item)) break block7;
                    n += this.m627(item);
                    if (null == null) break block8;
                }
                if (itemStack.isStackable()) {
                    if (itemStack.getCount() < itemStack.getMaxCount()) {
                        n += this.m627(item) / 2;
                    }
                }
            }
            if (null == null) continue;
        }
        return n >= this.stealThreshold.getInt50() ? Mode.STEALER : Mode.THROW;
    }

    private int m627(Object object) {
        Item item = (Item)object;
        Object var4_3 = null;
        if (this.m965(item)) {
            return this.weaponCoin.getInt50();
        }
        if (this.m1049(item)) {
            return this.armorCoin.getInt50();
        }
        if (this.m1036(item)) {
            return this.potCoin.getInt50();
        }
        return this.itemCoin.getInt50();
    }

    private void updateSafeBoxes() {
        long l = System.currentTimeMillis();
        long l2 = (long)((Double)this.safeTime.getObj() * 1000.0);
        Object var2_3 = null;
        if (AutoRegear.INSTANCE != null) {
            if (AutoRegear.INSTANCE.blockPos15 != null) {
                BlockPos blockPos2 = AutoRegear.INSTANCE.blockPos15;
                if (!blockPos2.equals((Object)this.blockPos18)) {
                    this.map21.put(blockPos2, l);
                    this.blockPos18 = blockPos2;
                }
            }
        }
        this.map21.entrySet().removeIf(entry -> {
            Object var5_3 = null;
            boolean bl = l - (Long)((Map.Entry)entry).getValue() > l2;
            boolean bl2 = !(MC.client3.world.getBlockState((BlockPos)((Map.Entry)entry).getKey()).getBlock() instanceof ShulkerBoxBlock);
            if (bl) return true;
            if (!bl2) return false;
            return true;
        });
        this.set7.removeIf(blockPos -> {
            Object var1_1 = null;
            return !(MC.client3.world.getBlockState((BlockPos)blockPos).getBlock() instanceof ShulkerBoxBlock);
        });
    }

    private boolean isSet21() {
        block8: {
            block7: {
                Object var2_1 = null;
                if (MC.client3.world == null) break block7;
                if (MC.client3.player != null) break block8;
            }
            return false;
        }
        double d = (Double)this.enemyRange.getObj() * (Double)this.enemyRange.getObj();
        for (PlayerEntity playerEntity : MC.client3.world.getPlayers()) {
            if (playerEntity == MC.client3.player) continue;
            if (!playerEntity.isAlive()) continue;
            if (playerEntity.isSpectator()) continue;
            try {
                if (Client.manager.m258(playerEntity.getName().getString()) && null == null) continue;
            }
            catch (Exception exception) {
            }
            if (MC.client3.player.squaredDistanceTo((Entity)playerEntity) <= d) {
                return true;
            }
            if (null == null) continue;
        }
        return false;
    }

    private BlockPos getBlockPos8() {
        block8: {
            block7: {
                Object var2_1 = null;
                if (MC.client3.player == null) break block7;
                if (MC.client3.world != null) break block8;
            }
            return null;
        }
        BlockPos blockPos = MC.client3.player.getBlockPos();
        int n = this.searchRadius.getInt50();
        BlockPos blockPos2 = null;
        double d = Double.MAX_VALUE;
        for (int i = -n; i <= n; ++i) {
            for (int j = -n; j <= n; ++j) {
                for (int k = -n; k <= n; ++k) {
                    BlockPos blockPos3 = blockPos.add(i, j, k);
                    if (!(MC.client3.world.getBlockState(blockPos3).getBlock() instanceof ShulkerBoxBlock) || !MC.client3.world.getBlockState(blockPos3.up()).isAir() || this.map21.containsKey(blockPos3) || this.set7.contains(blockPos3) || this.set6.contains(blockPos3)) continue;
                    if (AutoRegear.INSTANCE != null) {
                        if (blockPos3.equals((Object)AutoRegear.INSTANCE.blockPos15)) continue;
                    }
                    if (this.m30(blockPos3)) continue;
                    double d2 = MC.client3.player.squaredDistanceTo((double)blockPos3.getX() + 0.5, (double)blockPos3.getY() + 0.5, (double)blockPos3.getZ() + 0.5);
                    if (!(d2 < d)) continue;
                    d = d2;
                    blockPos2 = blockPos3;
                    if (null == null) continue;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
        return blockPos2;
    }

    private boolean m30(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (MC.client3.world == null) {
            return false;
        }
        double d = (Double)this.friendRange.getObj() * (Double)this.friendRange.getObj();
        for (PlayerEntity playerEntity : MC.client3.world.getPlayers()) {
            if (playerEntity == MC.client3.player) continue;
            try {
                if (!Client.manager.m258(playerEntity.getName().getString()) && null == null) continue;
            }
            catch (Exception exception) {
                if (null == null) continue;
            }
            if (playerEntity.squaredDistanceTo((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) <= d) {
                return true;
            }
            if (null == null) continue;
        }
        return false;
    }

    private void m305() {
        block11: {
            block10: {
                Object var2_1 = null;
                if (this.blockPos8 != null) {
                    this.set6.add(this.blockPos8);
                    this.set7.add(this.blockPos8);
                    if (((Boolean)this.mineAfter.getObj()).booleanValue()) {
                        if (MC.client3.world.getBlockState(this.blockPos8).getBlock() instanceof ShulkerBoxBlock) {
                            if (SpeedMine.INSTANCE != null) {
                                if (SpeedMine.INSTANCE.isSet19()) {
                                    SpeedMine.INSTANCE.setObj84(this.blockPos8);
                                }
                            }
                        }
                    }
                }
                this.flag38 = false;
                if (MC.client3.player != null) {
                    if (MC.client3.player.currentScreenHandler instanceof ShulkerBoxScreenHandler) {
                        MC.client3.player.closeHandledScreen();
                    }
                }
                this.m382();
                if (((Boolean)this.continuous.getObj()).booleanValue()) break block10;
                this.setFlag3(false);
                if (null == null) break block11;
            }
            this.flag152 = false;
            this.flag156 = false;
            this.blockPos8 = null;
            this.blockPos16 = null;
            this.mode2 = null;
            this.flag115 = false;
            this.flag122 = false;
            this.flag118 = false;
            this.list23.clear();
            this.count146 = 0;
        }
    }

    private void setObj48(Object object) {
        BlockPos blockPos = (BlockPos)object;
        ClientSetting.RotateMode rotateMode = this.getRotateMode14();
        Object var4_4 = null;
        if (rotateMode == ClientSetting.RotateMode.NONE) {
            return;
        }
        Vec3d vec3d = Vec3d.ofCenter((Vec3i)blockPos);
        float[] fArray = MathUtil.m547(MC.client3.player.getEyePos(), vec3d);
        switch (Lambda.counts24[rotateMode.ordinal()]) {
            case 1: {
                Client.mathUtil.m355(fArray[0], fArray[1]);
                Client.mathUtil.setFloat6((float)this.getDouble15());
                if (null == null) break;
            }
            case 2: {
                Client.mathUtil.m303(fArray[0], fArray[1]);
                if (null == null) break;
            }
            case 3: {
                Client.mathUtil.m468(fArray[0], fArray[1]);
                break;
            }
        }
    }

    private void m382() {
        ClientSetting.RotateMode rotateMode = this.getRotateMode14();
        Object var2_2 = null;
        switch (Lambda.counts24[rotateMode.ordinal()]) {
            case 2: {
                Client.mathUtil.m844();
                if (null == null) break;
            }
            case 3: {
                Client.mathUtil.m2();
                break;
            }
        }
    }

    private ClientSetting.RotateMode getRotateMode14() {
        RotateMode rotateMode = (RotateMode)((Object)this.rotateMode.getObj());
        Object var2_2 = null;
        if (rotateMode == RotateMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.RotateMode)((Object)ClientSetting.INSTANCE.rotateMode.getObj()) : ClientSetting.RotateMode.NONE;
        }
        return switch (rotateMode.ordinal()) {
            case 1 -> ClientSetting.RotateMode.NONE;
            case 2 -> ClientSetting.RotateMode.SMOOTH;
            case 3 -> ClientSetting.RotateMode.ONTICK;
            case 4 -> ClientSetting.RotateMode.rotateMode;
            default -> ClientSetting.RotateMode.NONE;
        };
    }

    private double getDouble15() {
        Object var2_1 = null;
        return ClientSetting.INSTANCE != null ? (Double)ClientSetting.INSTANCE.rotateSpeed.getObj() : 45.0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m1049(Object object) {
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
    private boolean m965(Object object) {
        Item item = (Item)object;
        Object var4_3 = null;
        if (item == Items.NETHERITE_SWORD) return true;
        if (item == Items.DIAMOND_SWORD) return true;
        if (item == Items.IRON_SWORD) return true;
        if (item == Items.NETHERITE_PICKAXE) return true;
        if (item == Items.DIAMOND_PICKAXE) return true;
        if (item == Items.IRON_PICKAXE) return true;
        if (item == Items.NETHERITE_AXE) return true;
        if (item != Items.DIAMOND_AXE) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m1036(Object object) {
        Item item = (Item)object;
        Object var4_3 = null;
        if (item instanceof PotionItem) return true;
        if (item == Items.POTION) return true;
        if (item == Items.SPLASH_POTION) return true;
        if (item != Items.LINGERING_POTION) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m507(Object object, Object object2) {
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

    private boolean m742(Object object, Object object2, int n) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Item item = (Item)object2;
        int n2 = n;
        Iterator iterator = this.map14.entrySet().iterator();
        Object var8_8 = null;
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            int n3 = (Integer)entry.getKey();
            if (n3 >= 36 || n3 == n2) continue;
            Item item2 = (Item)entry.getValue();
            if (!this.m507(item2, item)) continue;
            int n4 = n3 < 9 ? 54 + n3 : 27 + (n3 - 9);
            ItemStack itemStack = shulkerBoxScreenHandler.getSlot(n4).getStack();
            if (!this.m507(itemStack.getItem(), item2)) {
                return true;
            }
            if (null == null) continue;
        }
        return false;
    }

    private int m1050(Object object, Object object2, int n) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Item item = (Item)object2;
        int n2 = n;
        Object var8_8 = null;
        for (int i = 0; i < 36; ++i) {
            if (i == n2) continue;
            int n3 = i < 9 ? 54 + i : 27 + (i - 9);
            ItemStack itemStack = shulkerBoxScreenHandler.getSlot(n3).getStack();
            if (!this.m507(itemStack.getItem(), item) || this.m507(this.map14.getOrDefault(i, Items.AIR), item)) continue;
            return i;
        }
        return -1;
    }

    private int m541(Object object, Object object2) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Item item = (Item)object2;
        int n = -1;
        int n2 = -1;
        Object var6_7 = null;
        for (int i = 0; i < 27; ++i) {
            ItemStack itemStack = shulkerBoxScreenHandler.getSlot(i).getStack();
            if (!this.m507(itemStack.getItem(), item)) continue;
            if (itemStack.getCount() <= n2) continue;
            n2 = itemStack.getCount();
            n = i;
            if (null == null) continue;
        }
        return n;
    }

    private int m1034(Object object, Object object2) {
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

    private int m436(Object object) {
        ShulkerBoxScreenHandler shulkerBoxScreenHandler = (ShulkerBoxScreenHandler)object;
        Object var4_4 = null;
        for (int i = 0; i < 27; ++i) {
            if (!shulkerBoxScreenHandler.getSlot(i).getStack().isEmpty()) continue;
            return i;
        }
        return -1;
    }

    private void m16(int n, int n2, int n3, Object object) {
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        SlotActionType slotActionType = (SlotActionType)object;
        Object var10_9 = null;
        if (MC.client3.player != null) {
            if (MC.client3.interactionManager != null) {
                MC.client3.interactionManager.clickSlot(n4, n5, n6, slotActionType, (PlayerEntity)MC.client3.player);
            }
        }
    }

    private boolean m362(Object object) {
        String string = (String)object;
        this.map14.clear();
        Object var4_3 = null;
        try {
            String string2;
            File file = new File(MC.client3.runDirectory, "kissoo/kits/" + string + ".kit");
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
                            this.map14.put(n, item);
                        }
                    }
                }
                if (null == null) continue;
            }
            bufferedReader.close();
            return !this.map14.isEmpty() || this.mode.getObj() == Mode.THROW;
        }
        catch (Exception exception) {
            return false;
        }
    }

    private void setObj12(Object object) {
        String string = (String)object;
        CommandManager.setObj21("\u00a7b[AntiRegear] \u00a77" + string);
    }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts24 = new int[ClientSetting.RotateMode.values().length];

        static {
            try {
                Lambda.counts24[ClientSetting.RotateMode.SMOOTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts24[ClientSetting.RotateMode.ONTICK.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts24[ClientSetting.RotateMode.rotateMode.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum RotateMode {
        DEFAULT,
        NONE,
        SMOOTH,
        ONTICK,
        rotateMode2;


        private static RotateMode[] getRotateModeArray4() {
            return new RotateMode[]{DEFAULT, NONE, SMOOTH, ONTICK, rotateMode2};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
        STEALER,
        THROW,
        SMART;


        private static Mode[] getModeArray9() {
            return new Mode[]{STEALER, THROW, SMART};
        }
    }
}
