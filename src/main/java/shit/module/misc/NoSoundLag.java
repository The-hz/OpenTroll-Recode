/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class NoSoundLag
extends Module {
    private final NumberSetting maxPerSecond = (NumberSetting)this.m28(new NumberSetting("MaxPerSecond", 80.0, 10.0, 500.0, 10.0));
    private long time17 = System.currentTimeMillis();
    private int count225;

    public NoSoundLag() {
        super("NoSoundLag", "Drops excessive sound packets.", Category.MISC);
    }

    @EventHandler(priority=1000)
    private void setPacketEventInner3(PacketEvent.PacketEventInner packetEventInner) {
        if (!(packetEventInner.getPacket() instanceof PlaySoundS2CPacket)) {
            return;
        }
        long l = System.currentTimeMillis();
        if (l - this.time17 >= 1000L) {
            this.time17 = l;
            this.count225 = 0;
        }
        if (++this.count225 > this.maxPerSecond.getInt50()) {
            packetEventInner.m209();
        }
    }
}

