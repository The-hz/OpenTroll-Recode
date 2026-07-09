/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.event.Render2DEvent;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class LagNotifier
extends Module {
    public static boolean flag53;
    private final NumberSetting timeout = (NumberSetting)this.m28(new NumberSetting("Timeout", 1.4, 0.5, 10.0, 0.1));
    private final BooleanSetting pauseAutoWalk = (BooleanSetting)this.m28(new BooleanSetting("PauseAutoWalk", true));
    private final Helper7 helper74 = new Helper7();

    public LagNotifier() {
        super("LagNotifier", "Shows server lag and lagback warnings.", Category.PLAYER);
    }

    @Override
    public void m709() {
        flag53 = false;
    }

    @EventHandler
    private void setPacketEventInner30(PacketEvent.PacketEventInner packetEventInner) {
        this.helper74.m533();
        if (packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket) {
            flag53 = true;
        }
    }

    @EventHandler
    private void setObj115(Render2DEvent render2DEvent) {
        if (this.helper74.m114((Double)this.timeout.getObj())) {
            String string = "Server not responding (" + String.format("%.1f", (double)this.helper74.getLong12() / 1000.0) + "s)";
            int n = MC.client3.getWindow().getScaledWidth() / 2 - MC.client3.textRenderer.getWidth(string) / 2;
            render2DEvent.getDrawContext().drawText(MC.client3.textRenderer, string, n, 18, -43691, true);
        } else {
            flag53 = false;
        }
    }
}

