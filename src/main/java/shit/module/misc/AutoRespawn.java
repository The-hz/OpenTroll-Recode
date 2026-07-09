/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoRespawn
extends Module {
    private final BooleanSetting deathScreen = (BooleanSetting)this.m28(new BooleanSetting("DeathScreen", false));

    public AutoRespawn() {
        super("AutoRespawn", "Respawns automatically after death.", Category.MISC);
    }

    @EventHandler
    private void setEvent2Inner218(Event2.Event2Inner2 event2Inner2) {
        if (!((Boolean)this.deathScreen.getObj()).booleanValue() && MC.client3.player != null && MC.client3.player.isDead()) {
            MC.client3.player.requestRespawn();
        }
    }
}

