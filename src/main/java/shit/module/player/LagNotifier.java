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
import shit.misc.Stopwatch;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class LagNotifier
extends Module {
    public static boolean flag53;
    private final NumberSetting timeout = (NumberSetting)this.registerSetting(new NumberSetting("Timeout", 1.4, 0.5, 10.0, 0.1));
    private final BooleanSetting pauseAutoWalk = (BooleanSetting)this.registerSetting(new BooleanSetting("PauseAutoWalk", true));
    private final Stopwatch helper74 = new Stopwatch();

    public LagNotifier() {
        super("LagNotifier", "Shows server lag and lagback warnings.", Category.PLAYER);
    }

    @Override
    public void onDisable() {
        flag53 = false;
    }

    @EventHandler
    private void setPacketEventInner30(PacketEvent.PacketEventInner packetEventInner) {
        this.helper74.resetTimer();
        if (packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket) {
            flag53 = true;
        }
    }

    @EventHandler
    private void setObj115(Render2DEvent render2DEvent) {
        if (this.helper74.hasPassedSeconds((Double)this.timeout.getValue())) {
            String string = "Server not responding (" + String.format("%.1f", (double)this.helper74.getElapsed() / 1000.0) + "s)";
            int n = MC.mc.getWindow().getScaledWidth() / 2 - MC.mc.textRenderer.getWidth(string) / 2;
            render2DEvent.getDrawContext().drawText(MC.mc.textRenderer, string, n, 18, -43691, true);
        } else {
            flag53 = false;
        }
    }
}

