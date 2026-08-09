/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.player.AutoArmor;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Replenish
extends Module {
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.QuickMove));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 2.0, 0.0, 5.0, 0.01));
    private final NumberSetting min = (NumberSetting)this.registerSetting(new NumberSetting("Min", 50.0, 1.0, 100.0, 1.0));
    private final NumberSetting forceDelay = (NumberSetting)this.registerSetting(new NumberSetting("ForceDelay", 0.2, 0.0, 4.0, 0.01));
    private final NumberSetting forceMin = (NumberSetting)this.registerSetting(new NumberSetting("ForceMin", 16.0, 1.0, 100.0, 1.0));
    private long time22 = 0L;

    public Replenish() {
        super("Replenish", "Replenishes hotbar items from inventory.", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        this.time22 = 0L;
    }

    @EventHandler
    private void setEvent2Inner58(TickEvent.PreTick event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        for (int i = 0; i < 9; ++i) {
            if (!this.m34(i)) continue;
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private boolean m34(int var1_1) {
        int var3 = var1_1;
        ItemStack var5 = MC.mc.player.getInventory().getStack(var3);
        if (var5.isEmpty()) {
            return false;
        }
        if (!var5.isStackable()) {
            return false;
        }
        int var6 = (int)((double)var5.getCount() / (double)var5.getMaxCount() * 100.0);
        if ((double)var6 > (Double)this.min.getValue()) {
            return false;
        }
        for (int var7 = 9; var7 < 36; ++var7) {
            ItemStack var8 = MC.mc.player.getInventory().getStack(var7);
            if (var8.isEmpty() || !Replenish.m395(var5, var8)) continue;
            long var9 = System.currentTimeMillis();
            if ((double)var6 > (Double)this.forceMin.getValue()) {
                if (var9 - this.time22 - (long)((Double)this.delay.getValue() * 1000.0) < 0L) {
                    return false;
                }
            } else if (var9 - this.time22 - (long)((Double)this.forceDelay.getValue() * 1000.0) < 0L) {
                return false;
            }
            int var11 = MC.mc.player.playerScreenHandler.syncId;
            switch (((Mode)((Object)this.mode.getValue())).ordinal()) {
                case 0: {
                    MC.mc.interactionManager.clickSlot(var11, var7, 0, SlotActionType.QUICK_MOVE, (PlayerEntity)MC.mc.player);
                    break;
                }
                case 1: {
                    MC.mc.interactionManager.clickSlot(var11, var7, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    MC.mc.interactionManager.clickSlot(var11, var3 + 36, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    MC.mc.interactionManager.clickSlot(var11, var7, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    break;
                }
            }
            this.time22 = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean m395(Object object, Object object2) {
        ItemStack itemStack = (ItemStack)object;
        ItemStack itemStack2 = (ItemStack)object2;
        boolean bl = false;
        if (itemStack.isEmpty()) return false;
        if (itemStack2.isEmpty()) {
            return false;
        }
        if (!ItemStack.areItemsAndComponentsEqual((ItemStack)itemStack, (ItemStack)itemStack2)) {
            return false;
        }
        if (!itemStack.isStackable()) return false;
        if (itemStack.getCount() >= itemStack.getMaxCount()) return false;
        return true;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      QuickMove, ClickSlot;

      private Mode() {}



        private static Mode[] getModeArray24() {
            return new Mode[]{QuickMove, ClickSlot};
        }
    
   }
}

