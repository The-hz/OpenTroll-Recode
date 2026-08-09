/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.function.Predicate;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import shit.module.client.ClientSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class RenderUtil3
implements MC {
    private int count100 = -1;
    private int count220 = -1;
    private boolean flag36;

    public boolean isSwitching() {
        Object var2_1 = null;
        return this.count100 != -1;
    }

    public boolean switchToItem(Object object, Object object2) {
        Predicate predicate = (Predicate)object;
        ClientSetting.SwitchMode switchMode = (ClientSetting.SwitchMode)((Object)object2);
        Object var6_5 = null;
        if (MC.mc.player == null) {
            return false;
        }
        if (this.isSwitching()) {
            return true;
        }
        int n = MC.mc.player.getInventory().getSelectedSlot();
        if (predicate.test(MC.mc.player.getInventory().getStack(n))) {
            return true;
        }
        if (switchMode == ClientSetting.SwitchMode.NONE) {
            return false;
        }
        if (switchMode == ClientSetting.SwitchMode.INVENTORY) {
            int n2 = this.m144(predicate, 0, 36);
            if (n2 == -1) {
                return false;
            }
            this.count100 = n;
            this.count220 = this.m126(n2);
            this.flag36 = true;
            MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, this.count220, n, SlotActionType.SWAP, (PlayerEntity)MC.mc.player);
            return true;
        }
        int n3 = this.m144(predicate, 0, 9);
        if (n3 == -1) {
            return false;
        }
        this.count100 = n;
        this.flag36 = false;
        MC.mc.player.getInventory().setSelectedSlot(n3);
        MC.mc.player.networkHandler.sendPacket((Packet)new UpdateSelectedSlotC2SPacket(n3));
        return true;
    }

    public void restoreSlot() {
        block5: {
            block4: {
                Object var2_1 = null;
                if (!this.isSwitching() || MC.mc.player == null) {
                    return;
                }
                if (!this.flag36) break block4;
                int n = MC.mc.player.getInventory().getSelectedSlot();
                MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, this.count220, n, SlotActionType.SWAP, (PlayerEntity)MC.mc.player);
                if (null == null) break block5;
            }
            MC.mc.player.getInventory().setSelectedSlot(this.count100);
            MC.mc.player.networkHandler.sendPacket((Packet)new UpdateSelectedSlotC2SPacket(this.count100));
        }
        this.count100 = -1;
        this.count220 = -1;
        this.flag36 = false;
    }

    private int m126(int n) {
        int n2 = n;
        Object var4_3 = null;
        return n2 < 9 ? n2 + 36 : n2;
    }

    private int m144(Object object, int n, int n2) {
        Predicate predicate = (Predicate)object;
        int n3 = n;
        int n4 = n2;
        Object var8_8 = null;
        for (int i = n3; i < n4; ++i) {
            if (!predicate.test(MC.mc.player.getInventory().getStack(i))) continue;
            return i;
        }
        return -1;
    }
}

