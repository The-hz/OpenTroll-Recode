/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.misc.Stopwatch;
import shit.module.Category;
import shit.module.Module;
import shit.module.player.AutoArmor;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;
import shit.util.InventoryClickHelper;

@Environment(value=EnvType.CLIENT)
public class ItemSaver
extends Module {
    private final NumberSetting durability = (NumberSetting)this.registerSetting(new NumberSetting("Durability", 5.0, 1.0, 50.0, 1.0));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 50.0, 0.0, 1000.0, 5.0));
    private final BooleanSetting dropIfFull = (BooleanSetting)this.registerSetting(new BooleanSetting("DropIfFull", false));
    private final Stopwatch helper722 = new Stopwatch();

    public ItemSaver() {
        super("ItemSaver", "Protects low durability held items.", Category.PLAYER);
    }

    @EventHandler
    private void onTick6(TickEvent.PostTick event2Inner2) {
        if (Module.isNotInGame() || !this.helper722.hasPassedMs((Double)this.delay.getValue())) {
            return;
        }
        ItemStack itemStack = MC.mc.player.getMainHandStack();
        if (!this.m1038(itemStack)) {
            return;
        }
        int n = MC.mc.player.getInventory().getSelectedSlot();
        int n2 = InventoryClickHelper.m189((java.util.function.Predicate<ItemStack>)(itemStack2 -> {
            boolean bl = false;
            if (itemStack2.isEmpty()) return false;
            if (itemStack2.getItem() != itemStack.getItem()) return false;
            if (this.m1038(itemStack2)) return false;
            return true;
        }), false);
        if (n2 != -1) {
            InventoryClickHelper.m334(n2, n);
            this.helper722.resetTimer();
            return;
        }
        int n3 = InventoryClickHelper.m189((java.util.function.Predicate<ItemStack>)ItemStack::isEmpty, false);
        if (n3 != -1) {
            InventoryClickHelper.m334(n3, n);
            this.helper722.resetTimer();
        } else if (((Boolean)this.dropIfFull.getValue()).booleanValue()) {
            MC.mc.player.dropSelectedItem(false);
            this.helper722.resetTimer();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m1038(Object object) {
        ItemStack itemStack = (ItemStack)object;
        int n = AutoArmor.getSwitchFlag();
        int n2 = itemStack.isEmpty() ? 1 : 0;
        if (n != 0) {
            if (n2 != 0) return false;
            n2 = itemStack.isDamageable() ? 1 : 0;
        }
        if (n != 0) {
            if (n2 == 0) return false;
            double d = (double)ItemUtil.m239(itemStack) - (Double)this.durability.getValue();
            n2 = d == 0.0 ? 0 : (d < 0.0 ? (-1) : 1);
        }
        if (n == 0) return n2 != 0;
        if (n2 > 0) return false;
        return true;
    }
}

