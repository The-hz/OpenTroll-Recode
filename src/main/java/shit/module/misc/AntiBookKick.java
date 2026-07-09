/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class AntiBookKick
extends Module {
    private final BooleanSetting notify = (BooleanSetting)this.m28(new BooleanSetting("Notify", true));

    public AntiBookKick() {
        super("AntiBookKick", "Cancels book edit packets to avoid book kick exploits.", Category.MISC);
    }

    @EventHandler(priority=1000)
    private void setPacketEventInner216(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (packetEventInner2.getPacket() instanceof BookUpdateC2SPacket) {
            packetEventInner2.m209();
            if (((Boolean)this.notify.getObj()).booleanValue()) {
                Util2.setObj10("[AntiBookKick] Cancelled book edit packet.");
            }
        }
    }
}

