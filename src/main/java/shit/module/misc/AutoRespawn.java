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
    private final BooleanSetting deathScreen = (BooleanSetting)this.registerSetting(new BooleanSetting("DeathScreen", false));

    public AutoRespawn() {
        super("AutoRespawn", "Respawns automatically after death.", Category.MISC);
    }

    @EventHandler
    private void setEvent2Inner218(Event2.Event2Inner2 event2Inner2) {
        if (!((Boolean)this.deathScreen.getValue()).booleanValue() && MC.mc.player != null && MC.mc.player.isDead()) {
            MC.mc.player.requestRespawn();
        }
    }
}

