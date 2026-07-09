/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;
import shit.Client;
import shit.event.DisconnectEvent;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.CoordsLog;
import shit.setting.BooleanSetting;
import shit.setting.StringSetting;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class LogoutLogger
extends Module {
    private final BooleanSetting print = (BooleanSetting)this.m28(new BooleanSetting("Print", true));
    private final BooleanSetting eZLog = (BooleanSetting)this.m28(new BooleanSetting("EZLog", false));
    private final StringSetting eZMessage = (StringSetting)this.m28(new StringSetting("EZMessage", "Ez log %s!"));
    private final Map map29 = new HashMap();

    public LogoutLogger() {
        super("LogoutLogger", "Logs where visible players leave the server.", Category.MISC);
    }

    @Override
    public void onEnable() {
        this.map29.clear();
    }

    @Override
    public void m709() {
        this.map29.clear();
    }

    @EventHandler
    private void setDisconnectEvent4(DisconnectEvent disconnectEvent) {
        this.map29.clear();
    }

    @EventHandler
    private void setEvent2Inner235(Event2.Event2Inner2 event2Inner2) {
        if (Module.isSet37() || MC.client3.getNetworkHandler() == null) {
            return;
        }
        HashSet<UUID> hashSet = new HashSet<UUID>();
        for (net.minecraft.client.network.AbstractClientPlayerEntity object : MC.client3.world.getPlayers()) {
            if (object == MC.client3.player || !object.isAlive() || object.isSpectator() || Client.manager.m258(object.getName().getString()) || MC.client3.getNetworkHandler().getPlayerListEntry(object.getUuid()) == null) continue;
            hashSet.add(object.getUuid());
            this.map29.put(object.getUuid(), new Data(object.getName().getString(), object.getBlockPos()));
        }
        Iterator<Object> iterator = this.map29.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry object;
            object = (Map.Entry)iterator.next();
            UUID uUID = (UUID)object.getKey();
            if (hashSet.contains(uUID) || MC.client3.getNetworkHandler().getPlayerListEntry(uUID) != null) continue;
            Data data = (Data)object.getValue();
            int n = data.getBlockPos().getZ();
            int n2 = data.getBlockPos().getY();
            int n3 = data.getBlockPos().getX();
            String string = data.text() + " logged out at " + n3 + ", " + n2 + ", " + n;
            CoordsLog.setObj30(string);
            if (((Boolean)this.print.getObj()).booleanValue()) {
                Util2.setObj10("[LogoutLogger] " + string);
            }
            if (((Boolean)this.eZLog.getObj()).booleanValue()) {
                Util2.setObj62(String.format((String)this.eZMessage.getObj(), data.text()));
            }
            iterator.remove();
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Data  {
        private final String text;
        private final BlockPos blockPos;

        private Data(String string, BlockPos blockPos) {
            this.text = string;
            this.blockPos = blockPos;
        }

        public String text() {
            return this.text;
        }

        public BlockPos getBlockPos() {
            return this.blockPos;
        }
    }
}

