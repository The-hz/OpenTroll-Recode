/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public final class Util3
implements MC {
    private Util3() {
    }

    public static int m189(Object object, boolean n) {
        Predicate predicate = (Predicate)object;
        int n2 = n ? 1 : 0;
        boolean bl = Util2.isSet69();
        int n3 = n2;
        if (!bl) {
            n3 = n3 != 0 ? 0 : 9;
        }
        return Util3.m864(predicate, n3, 36);
    }

    public static int m864(Object object, int n, int n2) {
        int n3;
        block5: {
            Predicate predicate = (Predicate)object;
            int n4 = n;
            int n5 = n2;
            boolean bl = Util2.isSet69();
            if (MC.mc.player == null) {
                return -1;
            }
            int n6 = n4;
            while (n6 < n5) {
                ItemStack itemStack = MC.mc.player.getInventory().getStack(n6);
                if (!bl) {
                    n3 = predicate.test(itemStack) ? 1 : 0;
                    if (bl) break block5;
                    if (n3 != 0) {
                        return n6;
                    }
                    ++n6;
                }
                if (!bl) continue;
            }
            n3 = -1;
        }
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int m589(int n) {
        int n2;
        int n3 = n;
        boolean bl = Util2.isSet69();
        int n4 = n3;
        int n5 = 9;
        if (!bl) {
            if (n4 >= n5) {
                n2 = n3;
                return n2;
            }
            n4 = 36;
            n5 = n3;
        }
        n2 = n4 + n5;
        return n2;
    }

    public static void m235(int n, int n2, Object object) {
        ClientPlayerInteractionManager clientPlayerInteractionManager;
        SlotActionType slotActionType;
        int n3;
        int n4;
        block4: {
            block5: {
                block3: {
                    MinecraftClient minecraftClient;
                    block2: {
                        n4 = n;
                        n3 = n2;
                        slotActionType = (SlotActionType)object;
                        boolean bl = false;
                        minecraftClient = MC.mc;
                        if (!false) break block2;
                        if (minecraftClient.player == null) break block3;
                        minecraftClient = MC.mc;
                    }
                    clientPlayerInteractionManager = minecraftClient.interactionManager;
                    if (!false) break block4;
                    if (clientPlayerInteractionManager != null) break block5;
                }
                return;
            }
            clientPlayerInteractionManager = MC.mc.interactionManager;
        }
        clientPlayerInteractionManager.clickSlot(MC.mc.player.currentScreenHandler.syncId, n4, n3, slotActionType, (PlayerEntity)MC.mc.player);
    }

    public static boolean m334(int n, int n2) {
        int n3;
        int n4;
        block4: {
            int n5;
            int n6;
            block5: {
                int n7;
                block2: {
                    block3: {
                        n6 = n;
                        n5 = n2;
                        boolean bl = false;
                        n7 = n6;
                        if (!false) break block2;
                        if (n7 < 0) break block3;
                        n7 = n5;
                        if (!false) break block2;
                        if (n7 < 0) break block3;
                        n4 = n5;
                        n3 = 9;
                        if (!false) break block4;
                        if (n4 < n3) break block5;
                    }
                    n7 = 0;
                }
                return n7 != 0;
            }
            n4 = Util3.m589(n6);
            n3 = n5;
        }
        Util3.m235(n4, n3, SlotActionType.SWAP);
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet48() {
        MinecraftClient minecraftClient;
        block5: {
            boolean bl;
            block4: {
                boolean bl2 = Util2.isSet69();
                MinecraftClient minecraftClient2 = MC.mc;
                if (!bl2) {
                    if (minecraftClient2.player == null) return false;
                    minecraftClient2 = MC.mc;
                }
                bl = minecraftClient2.currentScreen instanceof HandledScreen;
                if (bl2) break block4;
                if (!bl) return false;
                minecraftClient = MC.mc;
                if (bl2) break block5;
                bl = minecraftClient.currentScreen instanceof InventoryScreen;
            }
            if (bl) return false;
            minecraftClient = MC.mc;
        }
        if (minecraftClient.player.currentScreenHandler == MC.mc.player.playerScreenHandler) return false;
        return true;
    }

    public static int getInt20() {
        boolean bl = false;
        int n = Util3.isSet48() ? 1 : 0;
        if (false) {
            if (n == 0) {
                return 0;
            }
            n = Math.max(0, MC.mc.player.currentScreenHandler.slots.size() - 36);
        }
        return n;
    }
}

