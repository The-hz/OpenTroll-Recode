/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.player.AutoArmor;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoEat
extends Module {
    private final NumberSetting hunger = (NumberSetting)this.registerSetting(new NumberSetting("Hunger", 14.0, 1.0, 20.0, 1.0));
    private final NumberSetting health = (NumberSetting)this.registerSetting(new NumberSetting("Health", 10.0, 1.0, 20.0, 0.5));
    private final BooleanSetting gapples = (BooleanSetting)this.registerSetting(new BooleanSetting("Gapples", false));
    private int count165 = -1;
    private boolean flag105;

    public AutoEat() {
        super("AutoEat", "Automatically eats food from the hotbar.", Category.PLAYER);
    }

    @Override
    public void onDisable() {
        this.m722();
    }

    @EventHandler
    private void setEvent2Inner31(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (this.flag105) {
            if (this.isSet114() && this.m423(MC.mc.player.getMainHandStack())) {
                MC.mc.options.useKey.setPressed(true);
                if (!MC.mc.player.isUsingItem()) {
                    MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, Hand.MAIN_HAND);
                }
                return;
            }
            this.m722();
            return;
        }
        if (!this.isSet114()) {
            return;
        }
        int n = this.getInt45();
        if (n == -1) {
            return;
        }
        this.count165 = MC.mc.player.getInventory().getSelectedSlot();
        MC.mc.player.getInventory().setSelectedSlot(n);
        this.flag105 = true;
        MC.mc.options.useKey.setPressed(true);
        MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, Hand.MAIN_HAND);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet114() {
        boolean bl = false;
        if ((double)MC.mc.player.getHungerManager().getFoodLevel() <= (Double)this.hunger.getValue()) return true;
        if (!((double)(MC.mc.player.getHealth() + MC.mc.player.getAbsorptionAmount()) <= (Double)this.health.getValue())) return false;
        return true;
    }

    private int getInt45() {
        int n = -1;
        int n2 = -1;
        boolean bl = false;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = MC.mc.player.getInventory().getStack(i);
            FoodComponent foodComponent = (FoodComponent)itemStack.get(DataComponentTypes.FOOD);
            if (foodComponent == null) continue;
            int n3 = foodComponent.nutrition();
            if (((Boolean)this.gapples.getValue()).booleanValue()) {
                if (itemStack.getItem().toString().contains("golden")) {
                    n3 += 100;
                }
            }
            if (n3 <= n2) continue;
            n2 = n3;
            n = i;
            if (!false) continue;
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m423(Object object) {
        ItemStack itemStack = (ItemStack)object;
        int n = AutoArmor.getInt66();
        Object object2 = itemStack;
        if (n != 0) {
            if (itemStack.isEmpty()) return false;
            object2 = itemStack.get(DataComponentTypes.FOOD);
        }
        if (object2 == null) return false;
        return true;
    }

    private void m722() {
        boolean bl = false;
        if (MC.mc.options != null) {
            MC.mc.options.useKey.setPressed(false);
        }
        if (this.count165 != -1) {
            if (MC.mc.player != null) {
                MC.mc.player.getInventory().setSelectedSlot(this.count165);
            }
        }
        this.count165 = -1;
        this.flag105 = false;
    }
}

