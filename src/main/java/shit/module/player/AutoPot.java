/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import java.lang.invoke.LambdaMetafactory;
import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.module.player.AutoArmor;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoPot
extends Module {
    public static AutoPot INSTANCE;
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 5.0, 0.0, 60.0, 0.1));
    private final BooleanSetting speed = (BooleanSetting)this.registerSetting(new BooleanSetting("Speed", false));
    private final BooleanSetting resistance = (BooleanSetting)this.registerSetting(new BooleanSetting("Resistance", false));
    private final BooleanSetting strength = (BooleanSetting)this.registerSetting(new BooleanSetting("Strength", false));
    private final BooleanSetting slowFalling = (BooleanSetting)this.registerSetting(new BooleanSetting("SlowFalling", false));
    private final NumberSetting frontTick = (NumberSetting)this.registerSetting(new NumberSetting("FrontTick", 20.0, 1.0, 100.0, 1.0));
    private final BooleanSetting usingPause = (BooleanSetting)this.registerSetting(new BooleanSetting("UsingPause", true));
    private final BooleanSetting onlyGround = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyGround", false));
    private final BooleanSetting inventorySwap = (BooleanSetting)this.registerSetting(new BooleanSetting("InventorySwap", true));
    private final BooleanSetting delayCast = (BooleanSetting)this.registerSetting(new BooleanSetting("DelayCast", true));
    private final NumberSetting castDelay = (NumberSetting)this.registerSetting(new NumberSetting("CastDelay", 2.0, 1.0, 10.0, 1.0));
    private final EnumSetting rotateMode = (EnumSetting)this.registerSetting(new EnumSetting("RotateMode", RotateMode.DEFAULT));
    private final Helper7 helper748 = new Helper7();
    private boolean flag77 = false;
    private RegistryEntry field66 = null;
    private int count135 = 0;

    public AutoPot() {
        super("AutoPot", "Automatically throws splash potions.", Category.PLAYER);
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        this.field66 = null;
        this.count135 = 0;
        this.flag77 = false;
        Client.itemSwitcher.restoreSlot();
    }

    @EventHandler
    private void setEvent2Inner52(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        this.flag77 = false;
        if (this.field66 != null) {
            this.flag77 = true;
            if (this.count135 > 0) {
                --this.count135;
                return;
            }
            this.executeThrow(this.field66);
            this.field66 = null;
            return;
        }
        if (!this.helper748.hasPassedMs((Double)this.delay.getValue() * 1000.0)) {
            return;
        }
        if (!ItemUtil.isSet84()) {
            return;
        }
        if (((Boolean)this.onlyGround.getValue()).booleanValue() && !MC.mc.player.isOnGround()) {
            return;
        }
        if (((Boolean)this.usingPause.getValue()).booleanValue() && MC.mc.player.isUsingItem()) {
            return;
        }
        if (((Boolean)this.resistance.getValue()).booleanValue() && this.m259(StatusEffects.RESISTANCE, true, 2) && this.m18(StatusEffects.RESISTANCE)) {
            return;
        }
        if (((Boolean)this.speed.getValue()).booleanValue() && this.m259(StatusEffects.SPEED, false, 0) && this.m18(StatusEffects.SPEED)) {
            return;
        }
        if (((Boolean)this.strength.getValue()).booleanValue() && this.m259(StatusEffects.STRENGTH, false, 0) && this.m18(StatusEffects.STRENGTH)) {
            return;
        }
        if (((Boolean)this.slowFalling.getValue()).booleanValue() && this.m259(StatusEffects.SLOW_FALLING, false, 0) && this.m18(StatusEffects.SLOW_FALLING)) {
            return;
        }
    }

    private boolean m259(Object object, boolean bl, int n) {
        RegistryEntry registryEntry = (RegistryEntry)object;
        boolean bl2 = bl;
        int n2 = n;
        StatusEffectInstance statusEffectInstance = MC.mc.player.getStatusEffect(registryEntry);
        boolean bl3 = false;
        if (statusEffectInstance == null) {
            return true;
        }
        if (bl2) {
            if (statusEffectInstance.getAmplifier() < n2) {
                return true;
            }
        }
        return statusEffectInstance.getDuration() <= this.frontTick.getInt() + 2;
    }

    private boolean m18(Object object) {
        block7: {
            RegistryEntry registryEntry;
            block6: {
                block4: {
                    block5: {
                        registryEntry = (RegistryEntry)object;
                        boolean bl = false;
                        if (AutoPot.m760(registryEntry) != -1) break block4;
                        if (!((Boolean)this.inventorySwap.getValue()).booleanValue()) break block5;
                        if (AutoPot.m264(registryEntry) != -1) break block4;
                    }
                    return false;
                }
                if (!((Boolean)this.delayCast.getValue()).booleanValue()) break block6;
                this.field66 = registryEntry;
                this.count135 = this.castDelay.getInt();
                this.flag77 = true;
                if (!false) break block7;
            }
            this.executeThrow(registryEntry);
        }
        return true;
    }

    /*
     * Unable to fully structure code
     */
    private void executeThrow(Object var1_1) {
        RegistryEntry var3 = (RegistryEntry)var1_1;
        ClientSetting.RotateMode var5 = this.getRotateMode4();
        float var6 = MC.mc.player.getYaw();
        switch (Lambda.counts22[var5.ordinal()]) {
            case 1: {
                Client.mathUtil.setRotationSilent(var6, 90.0f);
                break;
            }
            case 2: {
                Client.mathUtil.setRotationVisible(var6, 90.0f);
                break;
            }
            case 3: {
                Client.mathUtil.setRotationSilent(var6, 90.0f);
                break;
            }
        }
        if (((Boolean)this.inventorySwap.getValue()).booleanValue()) {
            int var7 = AutoPot.m264(var3);
            if (var7 != -1 && Client.itemSwitcher.switchToItem((java.util.function.Predicate<ItemStack>)(itemStack -> AutoPot.m489(itemStack, var3)), (Object)ClientSetting.SwitchMode.INVENTORY)) {
                MC.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, var6, 90.0f));
                Client.itemSwitcher.restoreSlot();
                this.setObj91(var5);
                this.helper748.resetTimer();
                return;
            }
        }
        if (AutoPot.m760(var3) != -1 && Client.itemSwitcher.switchToItem((java.util.function.Predicate<ItemStack>)(itemStack -> AutoPot.m489(itemStack, var3)), (Object)ClientSetting.SwitchMode.SILENT)) {
            MC.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, var6, 90.0f));
            Client.itemSwitcher.restoreSlot();
        }
        this.setObj91(var5);
        this.helper748.resetTimer();
    }

    /*
     * Unable to fully structure code
     */
    private void setObj91(Object var1_1) {
        ClientSetting.RotateMode var3 = (ClientSetting.RotateMode)var1_1;
        switch (Lambda.counts22[var3.ordinal()]) {
            case 1: {
                Client.mathUtil.resetRotationSilent();
                return;
            }
            case 2: {
                Client.mathUtil.resetRotationVisible();
                return;
            }
            default: {
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private ClientSetting.RotateMode getRotateMode4() {
        ClientSetting.RotateMode rotateMode;
        int n = AutoArmor.getSwitchFlag();
        Object object = this.rotateMode.getValue();
        if (n != 0) {
            if (object == RotateMode.DEFAULT) {
                ClientSetting.RotateMode rotateMode2;
                Object object2 = ClientSetting.INSTANCE;
                if (n != 0) {
                    if (object2 == null) {
                        rotateMode2 = ClientSetting.RotateMode.NONE;
                        return rotateMode2;
                    }
                    object2 = ClientSetting.INSTANCE.rotateMode.getValue();
                }
                rotateMode2 = (ClientSetting.RotateMode)((Object)object2);
                return rotateMode2;
            }
            object = this.rotateMode.getValue();
        }
        switch (((RotateMode)((Object)object)).ordinal()) {
            case 1: {
                rotateMode = ClientSetting.RotateMode.NONE;
                return rotateMode;
            }
            case 2: {
                rotateMode = ClientSetting.RotateMode.SMOOTH;
                return rotateMode;
            }
            case 3: {
                rotateMode = ClientSetting.RotateMode.ONTICK;
                return rotateMode;
            }
            case 4: {
                rotateMode = ClientSetting.RotateMode.rotateMode;
                return rotateMode;
            }
        }
        rotateMode = ClientSetting.RotateMode.NONE;
        return rotateMode;
    }

    private static boolean m489(Object object, Object object2) {
        ItemStack itemStack = (ItemStack)object;
        RegistryEntry registryEntry = (RegistryEntry)object2;
        int n = AutoArmor.getSwitchFlag();
        Object object3 = itemStack.getItem();
        if (n != 0) {
            if (object3 != Items.SPLASH_POTION) {
                return false;
            }
            object3 = itemStack.getOrDefault(DataComponentTypes.POTION_CONTENTS, (Object)PotionContentsComponent.DEFAULT);
        }
        PotionContentsComponent potionContentsComponent = (PotionContentsComponent)object3;
        for (StatusEffectInstance statusEffectInstance : potionContentsComponent.getEffects()) {
            if (statusEffectInstance.getEffectType() == registryEntry) {
                return true;
            }
            if (n != 0) continue;
        }
        return false;
    }

    public static int m760(Object object) {
        RegistryEntry registryEntry = (RegistryEntry)object;
        boolean bl = false;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = MC.mc.player.getInventory().getStack(i);
            if (!AutoPot.m489(itemStack, registryEntry)) continue;
            return i;
        }
        return -1;
    }

    public static int m264(Object object) {
        int n;
        block4: {
            RegistryEntry registryEntry = (RegistryEntry)object;
            int n2 = 35;
            int n3 = AutoArmor.getSwitchFlag();
            while (n2 >= 9) {
                ItemStack itemStack = MC.mc.player.getInventory().getStack(n2);
                if (n3 != 0) {
                    n = AutoPot.m489(itemStack, registryEntry) ? 1 : 0;
                    if (n3 == 0) break block4;
                    if (n != 0) {
                        return n2;
                    }
                    --n2;
                }
                if (n3 != 0) continue;
            }
            n = -1;
        }
        return n;
    }

    @Override
    public String getInfo() {
        int n = AutoArmor.getSwitchFlag();
        AutoPot autoPot = this;
        if (n != 0) {
            if (autoPot.field66 != null) {
                return "Throwing";
            }
            autoPot = this;
        }
        if (autoPot.flag77) {
            return "Active";
        }
        return null;
    }

    private static /* synthetic */ boolean cfrlam$executeThrow$1(RegistryEntry registryEntry, ItemStack itemStack) {
        return AutoPot.m489(itemStack, registryEntry);
    }

    private static /* synthetic */ boolean cfrlam$executeThrow$0(RegistryEntry registryEntry, ItemStack itemStack) {
        return AutoPot.m489(itemStack, registryEntry);
    }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts22 = new int[ClientSetting.RotateMode.values().length];

        static {
            try {
                Lambda.counts22[ClientSetting.RotateMode.ONTICK.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts22[ClientSetting.RotateMode.rotateMode.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts22[ClientSetting.RotateMode.SMOOTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum RotateMode {
      DEFAULT, NONE, SMOOTH, ONTICK, rotateMode11;

      private RotateMode() {}



        private static RotateMode[] getRotateModeArray2() {
            return new RotateMode[]{DEFAULT, NONE, SMOOTH, ONTICK, rotateMode11};
        }
    
   }
}

