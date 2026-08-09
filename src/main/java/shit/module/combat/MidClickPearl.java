/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import shit.setting.EnumSetting;
import shit.util.ItemUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class MidClickPearl
extends Module {
    public static MidClickPearl INSTANCE;
    public static boolean flag80;
    private final EnumSetting timing = (EnumSetting)this.registerSetting(new EnumSetting("Timing", TimingMode.ALL));
    private final EnumSetting rotateMode = (EnumSetting)this.registerSetting(new EnumSetting("RotateMode", RotateMode.DEFAULT));
    private final EnumSetting switchMode = (EnumSetting)this.registerSetting(new EnumSetting("SwitchMode", SwitchMode.DEFAULT));
    private boolean flag61;

    public MidClickPearl() {
        super("MidClickPearl", "Throws one ender pearl using global rotation and switch modes.", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        block3: {
            block2: {
                Object var2_1 = null;
                if (Module.isNotInGame()) break block2;
                if (this.isSet63()) break block3;
            }
            this.setEnabled(false);
            return;
        }
        this.flag61 = true;
    }

    @Override
    public void onDisable() {
        this.flag61 = false;
        flag80 = false;
        Client.itemSwitcher.restoreSlot();
        Client.mathUtil.resetRotation();
    }

    @EventHandler
    private void setEvent2Inner59(TickEvent.PreTick event2Inner) {
        if (this.timing.getValue() == TimingMode.POST) {
            return;
        }
        this.m141();
    }

    @EventHandler
    private void setEvent2Inner210(TickEvent.PostTick event2Inner2) {
        if (this.timing.getValue() == TimingMode.PRE) {
            return;
        }
        this.m141();
    }

    private void m141() {
        block5: {
            block4: {
                Object var2_1 = null;
                if (!this.flag61) break block4;
                if (!Module.isNotInGame()) break block5;
            }
            return;
        }
        if (this.isSet164()) {
            this.setEnabled(false);
        }
    }

    public boolean isSet164() {
        block8: {
            block9: {
                ClientSetting.RotateMode rotateMode;
                block7: {
                    rotateMode = this.getRotateMode8();
                    float f = MC.mc.player.getYaw();
                    float f2 = MC.mc.player.getPitch();
                    Object var2_4 = null;
                    switch (Lambda.counts12[rotateMode.ordinal()]) {
                        case 1: {
                            Client.mathUtil.setTargetRotation(f, f2);
                            Client.mathUtil.setFloat6(this.getFloat59());
                            if (Client.mathUtil.getFloat51() > 5.0f) {
                                return false;
                            }
                            f = Client.mathUtil.getFloat55();
                            f2 = Client.mathUtil.getPitch();
                            if (null == null) break;
                        }
                        case 2: {
                            Client.mathUtil.setRotationSilent(f, f2);
                            if (null == null) break;
                        }
                        case 3: {
                            Client.mathUtil.setRotationVisible(f, f2);
                            break;
                        }
                    }
                    ClientSetting.SwitchMode switchMode = this.getSwitchMode10();
                    boolean bl = Client.itemSwitcher.switchToItem((java.util.function.Predicate<ItemStack>)this::m178, (Object)switchMode);
                    if (!bl) {
                        return true;
                    }
                    flag80 = true;
                    MC.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, f, f2));
                    ItemUtil.setObj25(Hand.MAIN_HAND);
                    flag80 = false;
                    Client.itemSwitcher.restoreSlot();
                    if (rotateMode != ClientSetting.RotateMode.rotateMode) break block7;
                    Client.mathUtil.resetRotationVisible();
                    if (null == null) break block8;
                }
                if (rotateMode != ClientSetting.RotateMode.ONTICK) break block9;
                Client.mathUtil.resetRotationSilent();
                if (null == null) break block8;
            }
            Client.mathUtil.resetRotation();
        }
        return true;
    }

    private boolean isSet63() {
        Object var2_2 = null;
        for (int i = 0; i < MC.mc.player.getInventory().size(); ++i) {
            if (!MC.mc.player.getInventory().getStack(i).isOf(Items.ENDER_PEARL)) continue;
            return true;
        }
        return false;
    }

    private boolean m178(ItemStack itemStack) {
        return itemStack.isOf(Items.ENDER_PEARL);
    }

    private ClientSetting.RotateMode getRotateMode8() {
        Object var2_1 = null;
        if (this.rotateMode.getValue() == RotateMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.RotateMode)((Object)ClientSetting.INSTANCE.rotateMode.getValue()) : ClientSetting.RotateMode.NONE;
        }
        return switch (((RotateMode)((Object)this.rotateMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.RotateMode.NONE;
            case 2 -> ClientSetting.RotateMode.SMOOTH;
            case 3 -> ClientSetting.RotateMode.ONTICK;
            case 4 -> ClientSetting.RotateMode.rotateMode;
            default -> ClientSetting.RotateMode.NONE;
        };
    }

    private float getFloat59() {
        Object var2_1 = null;
        return ClientSetting.INSTANCE != null ? ClientSetting.INSTANCE.rotateSpeed.getFloat() : 45.0f;
    }

    private ClientSetting.SwitchMode getSwitchMode10() {
        Object var2_1 = null;
        if (this.switchMode.getValue() == SwitchMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.SwitchMode)((Object)ClientSetting.INSTANCE.switchMode.getValue()) : ClientSetting.SwitchMode.NONE;
        }
        return switch (((SwitchMode)((Object)this.switchMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.SwitchMode.NONE;
            case 2 -> ClientSetting.SwitchMode.NORMAL;
            case 3 -> ClientSetting.SwitchMode.SILENT;
            case 4 -> ClientSetting.SwitchMode.INVENTORY;
            default -> ClientSetting.SwitchMode.NONE;
        };
    }

    @Environment(value=EnvType.CLIENT)
    public static enum RotateMode {
      DEFAULT, NONE, SMOOTH, ONTICK, rotateMode7;

      private RotateMode() {}



        private static RotateMode[] getRotateModeArray10() {
            return new RotateMode[]{DEFAULT, NONE, SMOOTH, ONTICK, rotateMode7};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum TimingMode {
      PRE, POST, ALL;

      private TimingMode() {}



        private static TimingMode[] getTimingModeArray2() {
            return new TimingMode[]{PRE, POST, ALL};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts12 = new int[ClientSetting.RotateMode.values().length];

        static {
            try {
                Lambda.counts12[ClientSetting.RotateMode.SMOOTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts12[ClientSetting.RotateMode.ONTICK.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts12[ClientSetting.RotateMode.rotateMode.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum SwitchMode {
      DEFAULT, NONE, NORMAL, SILENT, INVENTORY;

      private SwitchMode() {}



        private static SwitchMode[] getSwitchModeArray() {
            return new SwitchMode[]{DEFAULT, NONE, NORMAL, SILENT, INVENTORY};
        }
    
   }
}

