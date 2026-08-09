/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class PacketLogger
extends Module {
    private final BooleanSetting send = (BooleanSetting)this.registerSetting(new BooleanSetting("Send", true));
    private final BooleanSetting receive = (BooleanSetting)this.registerSetting(new BooleanSetting("Receive", false));
    private final BooleanSetting chat = (BooleanSetting)this.registerSetting(new BooleanSetting("Chat", false));

    public PacketLogger() {
        super("PacketLogger", "Logs packet class names to console or chat.", Category.PLAYER);
    }

    @EventHandler
    private void setPacketEventInner219(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (((Boolean)this.send.getValue()).booleanValue()) {
            Object[] objectArray = new Object[2];
            objectArray[1] = packetEventInner2.getPacket().getClass().getSimpleName();
            objectArray[0] = "S";
            Object[] objectArray2 = objectArray;
            this.m493(objectArray2[0], objectArray2[1]);
        }
    }

    @EventHandler
    private void setPacketEventInner19(PacketEvent.PacketEventInner packetEventInner) {
        if (((Boolean)this.receive.getValue()).booleanValue()) {
            Object[] objectArray = new Object[2];
            objectArray[1] = packetEventInner.getPacket().getClass().getSimpleName();
            objectArray[0] = "R";
            Object[] objectArray2 = objectArray;
            this.m493(objectArray2[0], objectArray2[1]);
        }
    }

    private void m493(Object object, Object object2) {
        block0: {
            String string = (String)object;
            String string2 = (String)object2;
            String string3 = "[PacketLogger/" + string + "] " + string2;
            System.out.println(string3);
            if (!((Boolean)this.chat.getValue()).booleanValue()) break block0;
            Util2.sendClientMessage(string3);
        }
    }
}

