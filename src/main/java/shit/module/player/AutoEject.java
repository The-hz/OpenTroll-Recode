/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.misc.Stopwatch;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;
import shit.setting.StringSetting;
import shit.util.MC;
import shit.util.InventoryClickHelper;

@Environment(value=EnvType.CLIENT)
public class AutoEject
extends Module {
    private final StringSetting items = (StringSetting)this.registerSetting(new StringSetting("Items", "minecraft:egg,minecraft:snowball"));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 100.0, 0.0, 1000.0, 10.0));
    private final Stopwatch helper72 = new Stopwatch();

    public AutoEject() {
        super("AutoEject", "Drops configured items from your inventory.", Category.PLAYER);
    }

    @EventHandler
    private void setEvent2Inner220(TickEvent.PostTick event2Inner2) {
        if (Module.isNotInGame() || !this.helper72.hasPassedMs((Double)this.delay.getValue())) {
            return;
        }
        for (int i = 0; i < MC.mc.player.currentScreenHandler.slots.size(); ++i) {
            Slot slot = (Slot)MC.mc.player.currentScreenHandler.slots.get(i);
            if (slot.inventory != MC.mc.player.getInventory() || !slot.hasStack() || !this.m208(slot.getStack())) continue;
            InventoryClickHelper.m235(i, 1, SlotActionType.THROW);
            this.helper72.resetTimer();
            return;
        }
    }

    private boolean m208(Object object) {
        ItemStack itemStack = (ItemStack)object;
        String string = Registries.ITEM.getId(itemStack.getItem()).toString();
        String[] stringArray = ((String)this.items.getValue()).split(",");
        boolean bl = false;
        for (String string2 : stringArray) {
            if (!string.equalsIgnoreCase(string2.trim())) continue;
            return true;
        }
        return false;
    }
}

