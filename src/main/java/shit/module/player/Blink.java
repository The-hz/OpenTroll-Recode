/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import java.util.ArrayDeque;
import java.util.Queue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.player.AutoArmor;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Blink
extends Module {
    private final BooleanSetting cancelPackets = (BooleanSetting)this.m28(new BooleanSetting("CancelPackets", false));
    private final BooleanSetting actions = (BooleanSetting)this.m28(new BooleanSetting("Actions", false));
    private final BooleanSetting autoPulse = (BooleanSetting)this.m28(new BooleanSetting("AutoPulse", true));
    private final NumberSetting pulsePackets = (NumberSetting)this.m28(new NumberSetting("PulsePackets", 20.0, 1.0, 100.0, 1.0));
    private final Queue queue = new ArrayDeque();
    private boolean flag141;

    public Blink() {
        super("Blink", "Queues movement packets until disabled or pulsed.", Category.PLAYER);
    }

    @Override
    public void m709() {
        this.m500();
    }

    @EventHandler
    private void setPacketEventInner214(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (this.flag141 || !this.m306(packetEventInner2.getPacket())) {
            return;
        }
        packetEventInner2.m209();
        if (!((Boolean)this.cancelPackets.getObj()).booleanValue()) {
            this.queue.add(packetEventInner2.getPacket());
        }
        if (((Boolean)this.autoPulse.getObj()).booleanValue() && this.queue.size() >= this.pulsePackets.getInt50()) {
            this.m500();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m306(Object object) {
        Packet packet = (Packet)object;
        boolean bl = false;
        if (packet instanceof PlayerMoveC2SPacket) {
            return true;
        }
        if ((Boolean)this.actions.getObj() == false) return false;
        if (packet instanceof PlayerActionC2SPacket) return true;
        if (packet instanceof PlayerInteractItemC2SPacket) return true;
        if (!(packet instanceof PlayerInteractBlockC2SPacket)) return false;
        return true;
    }

    private void m500() {
        int n;
        block8: {
            block7: {
                n = AutoArmor.getInt66();
                if (n == 0) break block7;
                if (MC.client3.player != null && MC.client3.player.networkHandler != null) break block8;
                this.queue.clear();
            }
            return;
        }
        this.flag141 = true;
        try {
            while (!this.queue.isEmpty()) {
                MC.client3.player.networkHandler.sendPacket((Packet)this.queue.poll());
                if (n != 0 && n != 0) continue;
                break;
            }
        }
        finally {
            this.flag141 = false;
            this.queue.clear();
        }
    }

    @Override
    public String getText57() {
        return Integer.toString(this.queue.size());
    }
}

