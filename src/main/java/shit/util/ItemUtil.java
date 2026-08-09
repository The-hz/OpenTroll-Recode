/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class ItemUtil
implements MC {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet84() {
        boolean bl = false;
        Screen screen = MC.mc.currentScreen;
        if (false) {
            if (screen == null) return true;
            screen = MC.mc.currentScreen;
        }
        boolean bl2 = screen instanceof OptionsScreen;
        if (!false) return bl2;
        if (bl2) return true;
        bl2 = MC.mc.currentScreen instanceof ChatScreen;
        if (!false) return bl2;
        if (bl2) return true;
        bl2 = MC.mc.currentScreen instanceof InventoryScreen;
        if (!false) return bl2;
        if (bl2) return true;
        bl2 = MC.mc.currentScreen instanceof GameMenuScreen;
        if (!false) return bl2;
        if (!bl2) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m931(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        ItemStack itemStack = playerEntity.getMainHandStack();
        boolean bl = false;
        boolean bl2 = itemStack.isIn(ItemTags.SWORDS);
        if (!false) return bl2;
        if (bl2) return true;
        bl2 = itemStack.isIn(ItemTags.AXES);
        if (!false) return bl2;
        if (bl2) return true;
        bl2 = itemStack.getItem() instanceof MaceItem;
        if (!false) return bl2;
        if (bl2) return true;
        bl2 = itemStack.getItem() instanceof TridentItem;
        if (!false) return bl2;
        if (!bl2) return false;
        return true;
    }

    public static boolean isInGame() {
        ClientWorld clientWorld;
        block6: {
            block7: {
                block5: {
                    MinecraftClient minecraftClient;
                    block4: {
                        boolean bl = false;
                        minecraftClient = MC.mc;
                        if (!false) break block4;
                        if (minecraftClient.player == null) break block5;
                        minecraftClient = MC.mc;
                    }
                    clientWorld = minecraftClient.world;
                    if (!false) break block6;
                    if (clientWorld != null) break block7;
                }
                return false;
            }
            clientWorld = MC.mc.world;
        }
        boolean bl = clientWorld.isSpaceEmpty((Entity)MC.mc.player, MC.mc.player.getBoundingBox());
        if (false) {
            bl = !bl;
        }
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int m239(Object object) {
        ItemStack itemStack = (ItemStack)object;
        boolean bl = Util2.isAlwaysTrue();
        int n = itemStack.getDamage();
        int n2 = itemStack.getMaxDamage();
        if (!bl) {
            if (n == n2) {
                return 100;
            }
            n = itemStack.getMaxDamage();
            n2 = itemStack.getDamage();
        }
        int n3 = (int)((double)(n - n2) / Math.max(0.1, (double)itemStack.getMaxDamage()) * 100.0);
        return n3;
    }

    public static boolean m749(Object object, int n) {
        EquipmentSlot[] equipmentSlotArray;
        PlayerEntity playerEntity = (PlayerEntity)object;
        int n2 = n;
        EquipmentSlot[] equipmentSlotArray2 = equipmentSlotArray = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        int n3 = equipmentSlotArray2.length;
        boolean bl = Util2.isAlwaysTrue();
        for (int i = 0; i < n3; ++i) {
            int n4;
            block7: {
                ItemStack itemStack;
                block8: {
                    block6: {
                        ItemStack itemStack2;
                        block5: {
                            EquipmentSlot equipmentSlot = equipmentSlotArray2[i];
                            itemStack2 = itemStack = playerEntity.getEquippedStack(equipmentSlot);
                            if (bl) break block5;
                            if (itemStack2 == null) break block6;
                            itemStack2 = itemStack;
                        }
                        n4 = itemStack2.isEmpty() ? 1 : 0;
                        if (bl) break block7;
                        if (n4 == 0) break block8;
                    }
                    return true;
                }
                n4 = ItemUtil.m239(itemStack);
            }
            if (!bl) {
                if (n4 >= n2) continue;
                n4 = 1;
            }
            return n4 != 0;
        }
        return false;
    }

    public static float m158(Object object) {
        block3: {
            Entity entity;
            block2: {
                Entity entity2 = (Entity)object;
                boolean bl = false;
                entity = entity2;
                if (!false) break block2;
                if (!(entity instanceof LivingEntity)) break block3;
                entity = entity2;
            }
            LivingEntity livingEntity = (LivingEntity)entity;
            return livingEntity.getHealth() + livingEntity.getAbsorptionAmount();
        }
        return 0.0f;
    }

    public static void setObj25(Object object) {
        block3: {
            ClientPlayerEntity clientPlayerEntity;
            Hand hand;
            block2: {
                hand = (Hand)object;
                boolean bl = false;
                clientPlayerEntity = MC.mc.player;
                if (!false) break block2;
                if (clientPlayerEntity == null) break block3;
                clientPlayerEntity = MC.mc.player;
            }
            clientPlayerEntity.swingHand(hand);
        }
    }
}

