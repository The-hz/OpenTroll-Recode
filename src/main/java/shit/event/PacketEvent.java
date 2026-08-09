/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.Packet;
import shit.event.Event;

@Environment(value=EnvType.CLIENT)
public class PacketEvent
extends Event {
    private final Packet packet;
    private static int count50;

    protected PacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public static void setInt4(int n) {
        count50 = n;
    }

    public static int getInt47() {
        return count50;
    }

    public static int getInt40() {
        int n = 71;
        if (71 == 0) {
            return 75;
        }
        return 0;
    }

    static {
        if (PacketEvent.getInt40() != 0) {
            PacketEvent.setInt4(71);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static class PacketEventInner2
    extends PacketEvent {
        public PacketEventInner2(Packet packet) {
            super(packet);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static class PacketEventInner
    extends PacketEvent {
        public PacketEventInner(Packet packet) {
            super(packet);
        }
    }
}

