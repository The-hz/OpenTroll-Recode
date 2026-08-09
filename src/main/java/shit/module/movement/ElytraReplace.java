/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundEvents;
import shit.command.CommandManager;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;
import shit.util.Util3;

@Environment(value=EnvType.CLIENT)
public class ElytraReplace
extends Module {
    private final NumberSetting durability = (NumberSetting)this.registerSetting(new NumberSetting("Durability", 7.0, 1.0, 50.0, 1.0));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 250.0, 0.0, 1000.0, 10.0));
    private final BooleanSetting warn = (BooleanSetting)this.registerSetting(new BooleanSetting("Warn", true));
    private final BooleanSetting sound = (BooleanSetting)this.registerSetting(new BooleanSetting("Sound", false));
    private final Helper7 helper739 = new Helper7();
    private boolean flag132 = true;
    private int count117;

    public ElytraReplace() {
        super("ElytraReplace", "Replaces low durability elytras from inventory.", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        this.flag132 = true;
        this.helper739.resetTimer();
    }

    @EventHandler
    private void onTick7(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame() || !this.helper739.hasPassedMs((Double)this.delay.getValue())) {
            return;
        }
        this.m237();
        ItemStack itemStack2 = MC.mc.player.getEquippedStack(EquipmentSlot.CHEST);
        if (!this.m461(itemStack2)) {
            return;
        }
        int n = Util3.m189((java.util.function.Predicate<ItemStack>)(itemStack -> {
            Object var2_2 = null;
            if (!itemStack.isOf(Items.ELYTRA)) return false;
            if (this.m461(itemStack)) return false;
            return true;
        }), true);
        if (n == -1) {
            if (this.flag132 && ((Boolean)this.warn.getValue()).booleanValue()) {
                CommandManager.sendFeedback("Your last elytra reached the durability threshold.");
                if (((Boolean)this.sound.getValue()).booleanValue()) {
                    MC.mc.player.playSoundIfNotSilent(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP);
                }
                this.flag132 = false;
            }
            return;
        }
        Util3.m235(6, 0, SlotActionType.QUICK_MOVE);
        Util3.m235(Util3.m589(n), 0, SlotActionType.QUICK_MOVE);
        if (((Boolean)this.warn.getValue()).booleanValue()) {
            CommandManager.sendFeedback("Equipped replacement elytra. Remaining: " + Math.max(0, this.count117 - 1));
        }
        if (((Boolean)this.sound.getValue()).booleanValue()) {
            MC.mc.player.playSoundIfNotSilent(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP);
        }
        this.helper739.resetTimer();
    }

    private void m237() {
        this.count117 = 0;
        Object var2_2 = null;
        for (int i = 0; i < 36; ++i) {
            ItemStack itemStack = MC.mc.player.getInventory().getStack(i);
            if (!itemStack.isOf(Items.ELYTRA)) continue;
            if (this.m461(itemStack)) continue;
            ++this.count117;
            if (null == null) continue;
            break;
        }
        if (this.count117 > 0) {
            this.flag132 = true;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m461(Object object) {
        ItemStack itemStack = (ItemStack)object;
        Object var4_3 = null;
        if (itemStack.isEmpty()) return false;
        if (!itemStack.isOf(Items.ELYTRA)) return false;
        if (!itemStack.isDamageable()) return false;
        if (!((double)ItemUtil.m239(itemStack) <= (Double)this.durability.getValue())) return false;
        return true;
    }

    @Override
    public String getInfo() {
        return Integer.toString(this.count117);
    }
}

