/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class HandManager
extends Module {
    private final EnumSetting item = (EnumSetting)this.m28(new EnumSetting("Item", ItemMode.Totem));
    private final BooleanSetting safe = (BooleanSetting)this.m28(new BooleanSetting("Safe", true));
    private final NumberSetting health = (NumberSetting)this.m28(new NumberSetting("Health", 16.0, 0.0, 36.0, 0.1, 0.1, () -> (Boolean)this.safe.getObj(), null, "", false));
    private final BooleanSetting lethalCrystal = (BooleanSetting)this.m28(new BooleanSetting("LethalCrystal", true, () -> (Boolean)this.safe.getObj(), null, "", false));
    private final BooleanSetting gapSwitch = (BooleanSetting)this.m28(new BooleanSetting("GapSwitch", true));
    private final BooleanSetting always = (BooleanSetting)this.m28(new BooleanSetting("Always", false, () -> (Boolean)this.gapSwitch.getObj(), null, "", false));
    private final BooleanSetting gapTotem = (BooleanSetting)this.m28(new BooleanSetting("Gap-Totem", false, () -> (Boolean)this.gapSwitch.getObj(), null, "", false));
    private final BooleanSetting gapSword = (BooleanSetting)this.m28(new BooleanSetting("Gap-Sword", true, () -> (Boolean)this.gapSwitch.getObj(), null, "", false));
    private final BooleanSetting gapPickaxe = (BooleanSetting)this.m28(new BooleanSetting("Gap-Pickaxe", false, () -> (Boolean)this.gapSwitch.getObj(), null, "", false));
    private final EnumSetting swapMode = (EnumSetting)this.m28(new EnumSetting("SwapMode", SwapMode.OffhandSwap));
    private final NumberSetting delay = (NumberSetting)this.m28(new NumberSetting("Delay", 50.0, 0.0, 500.0, 1.0));
    private final Helper7 helper746 = new Helper7();

    public HandManager() {
        super("HandManager", "Manages offhand items using Forever Offhand logic.", Category.COMBAT);
    }

    @Override
    public String getText57() {
        return ((ItemMode)((Object)this.item.getObj())).name();
    }

    @EventHandler
    private void setEvent2Inner43(Event2.Event2Inner event2Inner) {
        this.m986();
    }

    @EventHandler
    private void setEvent2Inner212(Event2.Event2Inner2 event2Inner2) {
        this.m986();
    }

    /*
     * Unable to fully structure code
     */
    private void m986() {
        if (Module.isSet37()) {
            return;
        }
        if (MC.client3.interactionManager == null) {
            return;
        }
        if (!this.helper746.m432(((Double)this.delay.getObj()).doubleValue())) {
            return;
        }
        if (!ItemUtil.isSet84()) {
            return;
        }
        boolean bl = ((Boolean)this.safe.getObj()).booleanValue() && ((double)ItemUtil.m158(MC.client3.player) < ((Double)this.health.getObj()).doubleValue() || this.isSet140());
        if (bl) {
            if (this.m291(Items.TOTEM_OF_UNDYING)) {
                this.helper746.m533();
            }
            return;
        }
        if (this.isSet124()) {
            if (this.m291(Items.GOLDEN_APPLE)) {
                this.helper746.m533();
            }
            return;
        }
        Item item = null;
        switch ((ItemMode)((Object)this.item.getObj())) {
            case None: {
                item = null;
                break;
            }
            case Totem: {
                item = Items.TOTEM_OF_UNDYING;
                break;
            }
            case Crystal: {
                item = Items.END_CRYSTAL;
                break;
            }
            case Gapple: {
                item = Items.GOLDEN_APPLE;
                break;
            }
            case Shield: {
                item = Items.SHIELD;
                break;
            }
            case Chorus: {
                item = Items.CHORUS_FRUIT;
                break;
            }
        }
        if (item != null && this.m291(item)) {
            this.helper746.m533();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet124() {
        Object var2_1 = null;
        if ((Boolean)this.gapSwitch.getObj() == false) return false;
        if (!MC.client3.options.useKey.isPressed()) {
            return false;
        }
        ItemStack itemStack = MC.client3.player.getMainHandStack();
        Item item = itemStack.getItem();
        if (((Boolean)this.gapSword.getObj()).booleanValue()) {
            if (itemStack.isIn(ItemTags.SWORDS)) return true;
        }
        if (((Boolean)this.gapPickaxe.getObj()).booleanValue()) {
            if (itemStack.isIn(ItemTags.PICKAXES)) return true;
        }
        if (((Boolean)this.gapTotem.getObj()).booleanValue()) {
            if (item == Items.TOTEM_OF_UNDYING) return true;
        }
        if ((Boolean)this.always.getObj() == false) return false;
        if (item == Items.GOLDEN_APPLE) return false;
        if (item == Items.ENCHANTED_GOLDEN_APPLE) return false;
        return true;
    }

    private boolean isSet140() {
        block6: {
            block5: {
                Object var2_1 = null;
                if (!((Boolean)this.lethalCrystal.getObj()).booleanValue()) break block5;
                if (MC.client3.world != null) break block6;
            }
            return false;
        }
        double d = ItemUtil.m158(MC.client3.player);
        for (EndCrystalEntity endCrystalEntity : MC.client3.world.getNonSpectatingEntities(EndCrystalEntity.class, MC.client3.player.getBoundingBox().expand(12.0))) {
            double d2 = Math.sqrt(MC.client3.player.squaredDistanceTo((Entity)endCrystalEntity));
            double d3 = Math.max(0.0, 12.0 - d2) * 1.6;
            if (d3 >= d) {
                return true;
            }
            if (null == null) continue;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m291(Object object) {
        Item item = (Item)object;
        Object var4_3 = null;
        int n = item == Items.GOLDEN_APPLE ? this.getInt36() : this.m478(item);
        if (n == -1) {
            return false;
        }
        switch (((SwapMode)((Object)this.swapMode.getObj())).ordinal()) {
            case 0: {
                MC.client3.interactionManager.clickSlot(MC.client3.player.playerScreenHandler.syncId, n, 0, SlotActionType.PICKUP, (PlayerEntity)MC.client3.player);
                MC.client3.interactionManager.clickSlot(MC.client3.player.playerScreenHandler.syncId, 45, 0, SlotActionType.PICKUP, (PlayerEntity)MC.client3.player);
                MC.client3.interactionManager.clickSlot(MC.client3.player.playerScreenHandler.syncId, n, 0, SlotActionType.PICKUP, (PlayerEntity)MC.client3.player);
                if (null == null) return true;
            }
            case 1: {
                MC.client3.interactionManager.clickSlot(MC.client3.player.playerScreenHandler.syncId, n, 40, SlotActionType.SWAP, (PlayerEntity)MC.client3.player);
                if (null == null) return true;
            }
            case 2: {
                if (n > 44) {
                    return false;
                }
                if (n < 36) return false;
                int n2 = n - 36;
                int n3 = n2;
                if (n3 < 0) return false;
                if (n3 > 8) {
                    return false;
                }
                int n4 = MC.client3.player.getInventory().getSelectedSlot();
                MC.client3.player.getInventory().setSelectedSlot(n3);
                MC.client3.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
                MC.client3.player.getInventory().setSelectedSlot(n4);
            }
        }
        return true;
    }

    private int getInt36() {
        int n = this.m478(Items.ENCHANTED_GOLDEN_APPLE);
        Object var2_2 = null;
        return n != -1 ? n : this.m478(Items.GOLDEN_APPLE);
    }

    private int m478(Object object) {
        Item item = (Item)object;
        Item item2 = MC.client3.player.getOffHandStack().getItem();
        Object var4_4 = null;
        if (item == item2) {
            return -1;
        }
        if (item == Items.GOLDEN_APPLE && item2 == Items.ENCHANTED_GOLDEN_APPLE) {
            return -1;
        }
        for (int i = 35; i >= 0; --i) {
            ItemStack itemStack = MC.client3.player.getInventory().getStack(i);
            if (itemStack.getItem() != item) continue;
            return i < 9 ? i + 36 : i;
        }
        return -1;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum ItemMode {
      None, Totem, Crystal, Gapple, Shield, Chorus;

      private ItemMode() {}



        private static ItemMode[] getItemModeArray() {
            return new ItemMode[]{None, Totem, Crystal, Gapple, Shield, Chorus};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum SwapMode {
      ClickSlot, OffhandSwap, Pick;

      private SwapMode() {}



        private static SwapMode[] getSwapModeArray() {
            return new SwapMode[]{ClickSlot, OffhandSwap, Pick};
        }
    
   }
}

