/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.Util3;

@Environment(value=EnvType.CLIENT)
public class ChestStealer
extends Module {
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.TOGGLE));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 250.0, 0.0, 1000.0, 25.0));
    private final BooleanSetting shulkersOnly = (BooleanSetting)this.registerSetting(new BooleanSetting("ShulkersOnly", false));
    private final Helper7 helper725 = new Helper7();
    private boolean flag83;

    public ChestStealer() {
        super("ChestStealer", "Moves items from containers into your inventory.", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        this.flag83 = this.mode.getValue() != Mode.MANUAL;
        this.helper725.resetTimer();
    }

    @Override
    public void onDisable() {
        this.flag83 = false;
    }

    @EventHandler
    private void setEvent2Inner27(Event2.Event2Inner2 event2Inner2) {
        if (Module.isNotInGame() || !Util3.isSet48()) {
            return;
        }
        if (this.mode.getValue() == Mode.ALWAYS) {
            this.flag83 = true;
        }
        if (!this.flag83 || !this.helper725.hasPassedMs((Double)this.delay.getValue())) {
            return;
        }
        int n = Util3.getInt20();
        for (int i = 0; i < n; ++i) {
            BlockItem blockItem;
            Item item;
            Slot slot = (Slot)MC.mc.player.currentScreenHandler.slots.get(i);
            if (!slot.hasStack() || ((Boolean)this.shulkersOnly.getValue()).booleanValue() && (!((item = slot.getStack().getItem()) instanceof BlockItem) || !((blockItem = (BlockItem)item).getBlock() instanceof ShulkerBoxBlock))) continue;
            MC.mc.interactionManager.clickSlot(MC.mc.player.currentScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, (PlayerEntity)MC.mc.player);
            this.helper725.resetTimer();
            return;
        }
        if (this.mode.getValue() == Mode.TOGGLE) {
            this.flag83 = false;
        }
    }

    private static MatchException a(MatchException matchException) {
        return matchException;
    }

    @Environment(value=EnvType.CLIENT)
    static enum Mode {
      ALWAYS, TOGGLE, MANUAL;

      private Mode() {}



        private static Mode[] getModeArray16() {
            return new Mode[]{ALWAYS, TOGGLE, MANUAL};
        }
    
   }
}

