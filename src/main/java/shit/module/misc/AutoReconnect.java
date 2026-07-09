/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.Render2DEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoReconnect
extends Module {
    private final NumberSetting delay = (NumberSetting)this.m28(new NumberSetting("Delay", 5.0, 0.5, 100.0, 0.5));
    private ServerInfo field18;
    private long time54 = -1L;
    private boolean flag119;

    public AutoReconnect() {
        super("AutoReconnect", "Automatically reconnects after being disconnected.", Category.MISC);
    }

    @Override
    public void m709() {
        this.time54 = -1L;
        this.flag119 = false;
    }

    @EventHandler
    private void setEvent2Inner214(Event2.Event2Inner2 event2Inner2) {
        ServerInfo serverInfo = MC.client3.getCurrentServerEntry();
        if (serverInfo != null) {
            this.field18 = serverInfo;
        }
        if (!(MC.client3.currentScreen instanceof DisconnectedScreen)) {
            this.time54 = -1L;
            this.flag119 = false;
            return;
        }
        if (this.field18 == null || this.flag119) {
            return;
        }
        if (this.time54 == -1L) {
            this.time54 = System.currentTimeMillis();
            return;
        }
        if ((double)(System.currentTimeMillis() - this.time54) < (Double)this.delay.getObj() * 1000.0) {
            return;
        }
        this.flag119 = true;
        ConnectScreen.connect((Screen)MC.client3.currentScreen, (MinecraftClient)MC.client3, (ServerAddress)ServerAddress.parse((String)this.field18.address), (ServerInfo)this.field18, (boolean)false, null);
    }

    @EventHandler
    private void setObj43(Render2DEvent render2DEvent) {
        if (!(MC.client3.currentScreen instanceof DisconnectedScreen) || this.field18 == null || this.time54 == -1L || this.flag119) {
            return;
        }
        long l = Math.max(0L, Math.round((Double)this.delay.getObj() * 1000.0) - (System.currentTimeMillis() - this.time54));
        String string = "Reconnecting in " + l + "ms";
        int n = (MC.client3.getWindow().getScaledWidth() - MC.client3.textRenderer.getWidth(string)) / 2;
        int n2 = MC.client3.getWindow().getScaledHeight() - 32;
        render2DEvent.getDrawContext().drawText(MC.client3.textRenderer, string, n, n2, 0xFFFFFF, true);
    }
}

