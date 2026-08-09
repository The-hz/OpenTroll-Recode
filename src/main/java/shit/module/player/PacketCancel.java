/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.player.AutoArmor;
import shit.setting.StringSetting;

@Environment(value=EnvType.CLIENT)
public class PacketCancel
extends Module {
    private final StringSetting sendContains = (StringSetting)this.registerSetting(new StringSetting("SendContains", "None"));
    private final StringSetting receiveContains = (StringSetting)this.registerSetting(new StringSetting("ReceiveContains", "None"));

    public PacketCancel() {
        super("PacketCancel", "Cancels packets whose class names contain configured text.", Category.PLAYER);
    }

    @EventHandler(priority=999)
    private void setPacketEventInner27(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (this.m475((String)this.sendContains.getValue(), packetEventInner2.getPacket().getClass().getSimpleName())) {
            packetEventInner2.cancel();
        }
    }

    @EventHandler(priority=999)
    private void setPacketEventInner31(PacketEvent.PacketEventInner packetEventInner) {
        if (this.m475((String)this.receiveContains.getValue(), packetEventInner.getPacket().getClass().getSimpleName())) {
            packetEventInner.cancel();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m475(Object object, Object object2) {
        String string = (String)object;
        String string2 = (String)object2;
        int n = AutoArmor.getSwitchFlag();
        String string3 = string;
        if (n != 0) {
            if (string3 == null) return false;
            string3 = string;
        }
        boolean bl = string3.isBlank();
        if (n != 0) {
            if (bl) return false;
            bl = "None".equalsIgnoreCase(string);
        }
        if (n != 0) {
            if (bl) return false;
            bl = string2.toLowerCase().contains(string.toLowerCase());
        }
        if (n == 0) return bl;
        if (!bl) return false;
        return true;
    }
}

