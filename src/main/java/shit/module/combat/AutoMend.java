/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import shit.Client;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;
import shit.util.InventoryClickHelper;

@Environment(value=EnvType.CLIENT)
public class AutoMend
extends Module {
    public static AutoMend INSTANCE;
    private final BooleanSetting onlyBroken = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyBroken", true));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 3.0, 0.0, 20.0, 1.0));
    private final BooleanSetting usingPause = (BooleanSetting)this.registerSetting(new BooleanSetting("UsingPause", true));
    private final BooleanSetting onlyGround = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyGround", true));
    private final BooleanSetting autoDisable = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoDisable", true));
    private final EnumSetting rotateMode = (EnumSetting)this.registerSetting(new EnumSetting("RotateMode", RotateMode.DEFAULT));
    private final EnumSetting switchMode = (EnumSetting)this.registerSetting(new EnumSetting("SwitchMode", SwitchMode.DEFAULT));
    private int count176;

    public AutoMend() {
        super("AutoMend", "Throws experience bottles to mend armor.", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        Object var2_1 = null;
        if (Module.isNotInGame()) {
            this.setEnabled(false);
            return;
        }
        this.count176 = 0;
    }

    @Override
    public void onDisable() {
        Client.itemSwitcher.restoreSlot();
        Client.mathUtil.resetRotation();
    }

    @EventHandler
    private void onTick(TickEvent.PreTick event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (((Boolean)this.usingPause.getValue()).booleanValue() && MC.mc.player.isUsingItem()) {
            return;
        }
        if (((Boolean)this.onlyGround.getValue()).booleanValue() && !MC.mc.player.isOnGround()) {
            return;
        }
        if (MC.mc.currentScreen != null) {
            return;
        }
        if (!this.shouldThrow()) {
            if (((Boolean)this.autoDisable.getValue()).booleanValue()) {
                this.setEnabled(false);
            }
            return;
        }
        if (this.count176 > 0) {
            --this.count176;
            return;
        }
        ClientSetting.RotateMode rotateMode = this.getRotateMode11();
        float f = MC.mc.player.getYaw();
        float f2 = 88.0f;
        switch (Lambda.counts29[rotateMode.ordinal()]) {
            case 1: {
                Client.mathUtil.setTargetRotation(f, f2);
                Client.mathUtil.setFloat6(this.getFloat52());
                if (Client.mathUtil.getFloat51() > 5.0f) {
                    return;
                }
                f = Client.mathUtil.getFloat55();
                break;
            }
            case 2: {
                Client.mathUtil.setRotationSilent(f, f2);
                break;
            }
            case 3: {
                Client.mathUtil.setRotationVisible(f, f2);
                break;
            }
        }
        ClientSetting.SwitchMode switchMode = this.getSwitchMode5();
        boolean bl = Client.itemSwitcher.switchToItem((java.util.function.Predicate<ItemStack>)(itemStack -> itemStack.isOf(Items.EXPERIENCE_BOTTLE)), (Object)switchMode);
        if (!bl) {
            return;
        }
        float f3 = f;
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, f3, f2));
        ItemUtil.setObj25(Hand.MAIN_HAND);
        Client.itemSwitcher.restoreSlot();
        if (rotateMode == ClientSetting.RotateMode.rotateMode) {
            Client.mathUtil.resetRotationVisible();
        } else if (rotateMode == ClientSetting.RotateMode.ONTICK) {
            Client.mathUtil.resetRotationSilent();
        } else if (rotateMode != ClientSetting.RotateMode.SMOOTH) {
            Client.mathUtil.resetRotation();
        }
        this.count176 = this.delay.getInt();
    }

    private boolean shouldThrow() {
        EquipmentSlot[] equipmentSlotArray;
        Object var2_1 = null;
        boolean bl = InventoryClickHelper.m189((java.util.function.Predicate<ItemStack>)(itemStack -> itemStack.isOf(Items.EXPERIENCE_BOTTLE)), true) != -1;
        if (!bl) {
            return false;
        }
        if (!((Boolean)this.onlyBroken.getValue()).booleanValue()) {
            return true;
        }
        for (EquipmentSlot equipmentSlot : equipmentSlotArray = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack itemStack2 = MC.mc.player.getEquippedStack(equipmentSlot);
            if (itemStack2.isEmpty()) continue;
            if (!itemStack2.isDamaged()) continue;
            return true;
        }
        return false;
    }

    private ClientSetting.RotateMode getRotateMode11() {
        Object var2_1 = null;
        if (this.rotateMode.getValue() == RotateMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.RotateMode)((Object)ClientSetting.INSTANCE.rotateMode.getValue()) : ClientSetting.RotateMode.ONTICK;
        }
        return switch (((RotateMode)((Object)this.rotateMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.RotateMode.NONE;
            case 2 -> ClientSetting.RotateMode.SMOOTH;
            case 3 -> ClientSetting.RotateMode.ONTICK;
            case 4 -> ClientSetting.RotateMode.rotateMode;
            default -> ClientSetting.RotateMode.ONTICK;
        };
    }

    private float getFloat52() {
        Object var2_1 = null;
        return ClientSetting.INSTANCE != null ? ClientSetting.INSTANCE.rotateSpeed.getFloat() : 45.0f;
    }

    private ClientSetting.SwitchMode getSwitchMode5() {
        Object var2_1 = null;
        if (this.switchMode.getValue() == SwitchMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.SwitchMode)((Object)ClientSetting.INSTANCE.switchMode.getValue()) : ClientSetting.SwitchMode.SILENT;
        }
        return switch (((SwitchMode)((Object)this.switchMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.SwitchMode.NONE;
            case 2 -> ClientSetting.SwitchMode.NORMAL;
            case 3 -> ClientSetting.SwitchMode.SILENT;
            case 4 -> ClientSetting.SwitchMode.INVENTORY;
            default -> ClientSetting.SwitchMode.SILENT;
        };
    }

    @Environment(value=EnvType.CLIENT)
    public static enum RotateMode {
      DEFAULT, NONE, SMOOTH, ONTICK, rotateMode4;

      private RotateMode() {}



        private static RotateMode[] getRotateModeArray7() {
            return new RotateMode[]{DEFAULT, NONE, SMOOTH, ONTICK, rotateMode4};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts29 = new int[ClientSetting.RotateMode.values().length];

        static {
            try {
                Lambda.counts29[ClientSetting.RotateMode.SMOOTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts29[ClientSetting.RotateMode.ONTICK.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts29[ClientSetting.RotateMode.rotateMode.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum SwitchMode {
      DEFAULT, NONE, NORMAL, SILENT, INVENTORY;

      private SwitchMode() {}



        private static SwitchMode[] getSwitchModeArray8() {
            return new SwitchMode[]{DEFAULT, NONE, NORMAL, SILENT, INVENTORY};
        }
    
   }
}

