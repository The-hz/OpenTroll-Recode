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
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;
import shit.util.Util3;

@Environment(value=EnvType.CLIENT)
public class AutoMend
extends Module {
    public static AutoMend INSTANCE;
    private final BooleanSetting onlyBroken = (BooleanSetting)this.m28(new BooleanSetting("OnlyBroken", true));
    private final NumberSetting delay = (NumberSetting)this.m28(new NumberSetting("Delay", 3.0, 0.0, 20.0, 1.0));
    private final BooleanSetting usingPause = (BooleanSetting)this.m28(new BooleanSetting("UsingPause", true));
    private final BooleanSetting onlyGround = (BooleanSetting)this.m28(new BooleanSetting("OnlyGround", true));
    private final BooleanSetting autoDisable = (BooleanSetting)this.m28(new BooleanSetting("AutoDisable", true));
    private final EnumSetting rotateMode = (EnumSetting)this.m28(new EnumSetting("RotateMode", RotateMode.DEFAULT));
    private final EnumSetting switchMode = (EnumSetting)this.m28(new EnumSetting("SwitchMode", SwitchMode.DEFAULT));
    private int count176;

    public AutoMend() {
        super("AutoMend", "Throws experience bottles to mend armor.", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        Object var2_1 = null;
        if (Module.isSet37()) {
            this.setFlag3(false);
            return;
        }
        this.count176 = 0;
    }

    @Override
    public void m709() {
        Client.renderUtil3.m608();
        Client.mathUtil.m370();
    }

    @EventHandler
    private void onTick(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (((Boolean)this.usingPause.getObj()).booleanValue() && MC.client3.player.isUsingItem()) {
            return;
        }
        if (((Boolean)this.onlyGround.getObj()).booleanValue() && !MC.client3.player.isOnGround()) {
            return;
        }
        if (MC.client3.currentScreen != null) {
            return;
        }
        if (!this.shouldThrow()) {
            if (((Boolean)this.autoDisable.getObj()).booleanValue()) {
                this.setFlag3(false);
            }
            return;
        }
        if (this.count176 > 0) {
            --this.count176;
            return;
        }
        ClientSetting.RotateMode rotateMode = this.getRotateMode11();
        float f = MC.client3.player.getYaw();
        float f2 = 88.0f;
        switch (Lambda.counts29[rotateMode.ordinal()]) {
            case 1: {
                Client.mathUtil.m355(f, f2);
                Client.mathUtil.setFloat6(this.getFloat52());
                if (Client.mathUtil.getFloat51() > 5.0f) {
                    return;
                }
                f = Client.mathUtil.getFloat55();
                break;
            }
            case 2: {
                Client.mathUtil.m303(f, f2);
                break;
            }
            case 3: {
                Client.mathUtil.m468(f, f2);
                break;
            }
        }
        ClientSetting.SwitchMode switchMode = this.getSwitchMode5();
        boolean bl = Client.renderUtil3.m223((java.util.function.Predicate<ItemStack>)(itemStack -> itemStack.isOf(Items.EXPERIENCE_BOTTLE)), (Object)switchMode);
        if (!bl) {
            return;
        }
        float f3 = f;
        MC.client3.player.networkHandler.sendPacket((Packet)new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, f3, f2));
        ItemUtil.setObj25(Hand.MAIN_HAND);
        Client.renderUtil3.m608();
        if (rotateMode == ClientSetting.RotateMode.rotateMode) {
            Client.mathUtil.m2();
        } else if (rotateMode == ClientSetting.RotateMode.ONTICK) {
            Client.mathUtil.m844();
        } else if (rotateMode != ClientSetting.RotateMode.SMOOTH) {
            Client.mathUtil.m370();
        }
        this.count176 = this.delay.getInt50();
    }

    private boolean shouldThrow() {
        EquipmentSlot[] equipmentSlotArray;
        Object var2_1 = null;
        boolean bl = Util3.m189((java.util.function.Predicate<ItemStack>)(itemStack -> itemStack.isOf(Items.EXPERIENCE_BOTTLE)), true) != -1;
        if (!bl) {
            return false;
        }
        if (!((Boolean)this.onlyBroken.getObj()).booleanValue()) {
            return true;
        }
        for (EquipmentSlot equipmentSlot : equipmentSlotArray = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack itemStack2 = MC.client3.player.getEquippedStack(equipmentSlot);
            if (itemStack2.isEmpty()) continue;
            if (!itemStack2.isDamaged()) continue;
            return true;
        }
        return false;
    }

    private ClientSetting.RotateMode getRotateMode11() {
        Object var2_1 = null;
        if (this.rotateMode.getObj() == RotateMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.RotateMode)((Object)ClientSetting.INSTANCE.rotateMode.getObj()) : ClientSetting.RotateMode.ONTICK;
        }
        return switch (((RotateMode)((Object)this.rotateMode.getObj())).ordinal()) {
            case 1 -> ClientSetting.RotateMode.NONE;
            case 2 -> ClientSetting.RotateMode.SMOOTH;
            case 3 -> ClientSetting.RotateMode.ONTICK;
            case 4 -> ClientSetting.RotateMode.rotateMode;
            default -> ClientSetting.RotateMode.ONTICK;
        };
    }

    private float getFloat52() {
        Object var2_1 = null;
        return ClientSetting.INSTANCE != null ? ClientSetting.INSTANCE.rotateSpeed.getFloat35() : 45.0f;
    }

    private ClientSetting.SwitchMode getSwitchMode5() {
        Object var2_1 = null;
        if (this.switchMode.getObj() == SwitchMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.SwitchMode)((Object)ClientSetting.INSTANCE.switchMode.getObj()) : ClientSetting.SwitchMode.SILENT;
        }
        return switch (((SwitchMode)((Object)this.switchMode.getObj())).ordinal()) {
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

