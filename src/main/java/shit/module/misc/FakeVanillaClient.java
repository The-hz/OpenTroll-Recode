/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.BrandCustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class FakeVanillaClient
extends Module {
    public FakeVanillaClient() {
        super("FakeVanillaClient", "Suppresses modded client brand payloads.", Category.MISC);
    }

    @EventHandler(priority=1000)
    private void setPacketEventInner29(PacketEvent.PacketEventInner2 packetEventInner2) {
        CustomPayloadC2SPacket customPayloadC2SPacket;
        Packet packet = packetEventInner2.getPacket();
        if (packet instanceof CustomPayloadC2SPacket && (customPayloadC2SPacket = (CustomPayloadC2SPacket)packet).payload() instanceof BrandCustomPayload) {
            packetEventInner2.cancel();
        }
    }
}

