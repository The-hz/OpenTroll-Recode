/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.misc.Stopwatch;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class PacketLimiter
extends Module {
    private final NumberSetting packetsPerSecond = (NumberSetting)this.registerSetting(new NumberSetting("PacketsPerSecond", 80.0, 1.0, 300.0, 1.0));
    private final BooleanSetting movement = (BooleanSetting)this.registerSetting(new BooleanSetting("Movement", true));
    private final BooleanSetting actions = (BooleanSetting)this.registerSetting(new BooleanSetting("Actions", false));
    private final Stopwatch helper78 = new Stopwatch();
    private int count209;

    public PacketLimiter() {
        super("PacketLimiter", "Drops selected outgoing packets above a per-second limit.", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        this.count209 = 0;
        this.helper78.resetTimer();
    }

    @EventHandler(priority=900)
    private void setPacketEventInner25(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (!this.m322(packetEventInner2.getPacket())) {
            return;
        }
        if (this.helper78.hasPassedSeconds(1.0)) {
            this.count209 = 0;
            this.helper78.resetTimer();
        }
        if (++this.count209 > this.packetsPerSecond.getInt()) {
            packetEventInner2.cancel();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m322(Object object) {
        Packet packet = (Packet)object;
        boolean bl = false;
        if (((Boolean)this.movement.getValue()).booleanValue()) {
            if (packet instanceof PlayerMoveC2SPacket) {
                return true;
            }
        }
        if ((Boolean)this.actions.getValue() == false) return false;
        if (packet instanceof PlayerActionC2SPacket) return true;
        if (packet instanceof PlayerInteractItemC2SPacket) return true;
        if (!(packet instanceof PlayerInteractBlockC2SPacket)) return false;
        return true;
    }

    @Override
    public String getInfo() {
        return this.count209 + "/" + this.packetsPerSecond.getInt();
    }
}

