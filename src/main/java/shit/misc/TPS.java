/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.hud.AbstractHudModule;

@Environment(value=EnvType.CLIENT)
public class TPS
extends AbstractHudModule {
    private final Deque deque2 = new ArrayDeque();
    private long time31 = -1L;
    private float value199 = 20.0f;

    public TPS() {
        super("TPS", "Shows estimated server TPS.", 6, 114);
    }

    @EventHandler
    private void setPacketEventInner33(PacketEvent.PacketEventInner packetEventInner) {
        if (!(packetEventInner.getPacket() instanceof WorldTimeUpdateS2CPacket)) {
            return;
        }
        long l = System.currentTimeMillis();
        if (this.time31 > 0L) {
            long l2 = Math.max(1L, l - this.time31);
            float f = Math.min(20.0f, 1000.0f / (float)l2);
            this.deque2.addLast(Float.valueOf(f));
            while (this.deque2.size() > 40) {
                this.deque2.removeFirst();
            }
            float f2 = 0.0f;
            Iterator iterator = this.deque2.iterator();
            while (iterator.hasNext()) {
                float f3 = ((Float)iterator.next()).floatValue();
                f2 += f3;
            }
            this.value199 = this.deque2.isEmpty() ? 20.0f : f2 / (float)this.deque2.size();
        }
        this.time31 = l;
    }

    @Override
    protected List lines() {
        return List.of(String.format(Locale.ROOT, "TPS %.2f", Float.valueOf(this.value199)));
    }
}

