/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.DisconnectEvent;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.StringSetting;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class AutoKit
extends Module {
    private final StringSetting kitName = (StringSetting)this.m28(new StringSetting("KitName", ""));
    private boolean flag140;
    private boolean flag40;
    private long time49;

    public AutoKit() {
        super("AutoKit", "Automatically runs /kit after joining or dying.", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        block0: {
            if (Module.isSet37()) break block0;
            this.setLong2(3000L);
        }
    }

    @Override
    public void m709() {
        this.flag140 = false;
        this.flag40 = false;
        this.time49 = 0L;
    }

    @EventHandler
    private void setDisconnectEvent6(DisconnectEvent disconnectEvent) {
        this.flag140 = false;
        this.flag40 = false;
    }

    @EventHandler
    private void setEvent2Inner228(Event2.Event2Inner2 event2Inner2) {
        if (Module.isSet37()) {
            this.flag140 = false;
            return;
        }
        if (!this.flag140) {
            this.flag140 = true;
            this.setLong2(3000L);
        }
        if (MC.client3.player.isDead()) {
            this.setLong2(1500L);
            return;
        }
        if (!this.flag40 || System.currentTimeMillis() < this.time49) {
            return;
        }
        String string = (String)this.kitName.getObj();
        if (string != null && !string.isBlank()) {
            Util2.setObj14("kit " + string.trim());
        }
        this.flag40 = false;
    }

    private void setLong2(long l) {
        long l2 = l;
        this.flag40 = true;
        this.time49 = System.currentTimeMillis() + l2;
    }
}

