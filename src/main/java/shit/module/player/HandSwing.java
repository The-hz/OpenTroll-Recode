/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.player.AutoArmor;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class HandSwing
extends Module {
    public static HandSwing INSTANCE;
    private final BooleanSetting cancelClient = (BooleanSetting)this.m28(new BooleanSetting("CancelClient", false));
    private final BooleanSetting cancelServer = (BooleanSetting)this.m28(new BooleanSetting("CancelServer", false));
    private final NumberSetting swingTicks = (NumberSetting)this.m28(new NumberSetting("SwingTicks", -1.0, -1.0, 20.0, 1.0));

    public HandSwing() {
        super("HandSwing", "Modifies hand swing animation and packets.", Category.PLAYER);
        INSTANCE = this;
    }

    @EventHandler(priority=1000)
    private void setPacketEventInner221(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (((Boolean)this.cancelServer.getObj()).booleanValue() && packetEventInner2.getPacket() instanceof HandSwingC2SPacket) {
            packetEventInner2.m209();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet40() {
        boolean bl = false;
        if (!this.isSet19()) return false;
        if ((Boolean)this.cancelClient.getObj() != false) return true;
        if (this.swingTicks.getInt50() == -1) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getInt89() {
        int n = AutoArmor.getInt66();
        HandSwing handSwing = this;
        if (n != 0) {
            if (((Boolean)handSwing.cancelClient.getObj()).booleanValue()) {
                return 0;
            }
            handSwing = this;
        }
        int n2 = handSwing.swingTicks.getInt50();
        return n2;
    }
}

