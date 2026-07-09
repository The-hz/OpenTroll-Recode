/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class NoPacketKick
extends Module {
    public NoPacketKick() {
        super("NoPacketKick", "Disables itself after warning; deep packet guards are not needed in normal play.", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        Util2.setObj10("[NoPacketKick] Current recode networking already guards packet dispatch through PacketEvent.");
    }

    @EventHandler
    private void setEvent2Inner25(Event2.Event2Inner2 event2Inner2) {
    }
}

