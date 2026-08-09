/*
 * Decompiled with CFR 0.152.
 */
package shit.module.chat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.EnumSetting;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class ChatTimestamp
extends Module {
    private final EnumSetting separator = (EnumSetting)this.registerSetting(new EnumSetting("Separator", SeparatorMode.Arrows));
    private final EnumSetting time = (EnumSetting)this.registerSetting(new EnumSetting("Time", TimeMode.HHMM));
    private final EnumSetting unit = (EnumSetting)this.registerSetting(new EnumSetting("Unit", UnitMode.unitMode2));
    private static int[] counts25;

    public ChatTimestamp() {
        super("ChatTimestamp", "Adds a local timestamp to incoming chat messages.", Category.CHAT);
    }

    @EventHandler(priority=-1000)
    private void setPacketEventInner17(PacketEvent.PacketEventInner packetEventInner) {
        String string = this.m389(packetEventInner);
        if (string == null) {
            return;
        }
        packetEventInner.cancel();
        Util2.sendClientMessage(this.getText64() + string);
    }

    /*
     * Enabled aggressive block sorting
     */
    private String m389(Object object) {
        String string;
        ChatMessageS2CPacket chatMessageS2CPacket;
        Packet packet;
        int[] nArray;
        block7: {
            boolean bl;
            Packet packet2;
            block6: {
                PacketEvent.PacketEventInner packetEventInner = (PacketEvent.PacketEventInner)object;
                packet2 = packetEventInner.getPacket();
                nArray = ChatTimestamp.getIntArray2();
                bl = packet2 instanceof GameMessageS2CPacket;
                if (nArray == null) break block6;
                if (bl) {
                    GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packet2;
                    return gameMessageS2CPacket.content().getString();
                }
                packet = packet2 = packetEventInner.getPacket();
                if (nArray == null) break block7;
                bl = packet instanceof ChatMessageS2CPacket;
            }
            if (!bl) return null;
            packet = packet2;
        }
        ChatMessageS2CPacket chatMessageS2CPacket2 = chatMessageS2CPacket = (ChatMessageS2CPacket)packet;
        if (nArray != null) {
            if (chatMessageS2CPacket2.unsignedContent() != null) {
                string = chatMessageS2CPacket.unsignedContent().getString();
                return string;
            }
            chatMessageS2CPacket2 = chatMessageS2CPacket;
        }
        string = chatMessageS2CPacket2.body().content();
        return string;
    }

    private String getText64() {
        String string;
        block5: {
            Object object;
            block4: {
                int[] nArray = ChatTimestamp.getIntArray2();
                object = (TimeMode)((Object)this.time.getValue());
                if (nArray == null) break block4;
                switch (((TimeMode)object).ordinal()) {
                    default: {
                        throw new MatchException(null, null);
                    }
                    case 0: {
                        string = this.unit.getValue() == UnitMode.unitMode ? "hh:mm a" : "HH:mm";
                        break block5;
                    }
                    case 1: {
                        object = this.unit.getValue();
                    }
                }
            }
            string = object == UnitMode.unitMode ? "hh:mm:ss a" : "HH:mm:ss";
        }
        String string2 = string;
        String string3 = LocalTime.now().format(DateTimeFormatter.ofPattern(string2));
        SeparatorMode separatorMode = (SeparatorMode)((Object)this.separator.getValue());
        return separatorMode.text648 + string3 + separatorMode.text888 + " ";
    }

    public static void setIntArray5(int[] nArray) {
        counts25 = nArray;
    }

    public static int[] getIntArray2() {
        return counts25;
    }

    static {
        int n = 49;
        ChatTimestamp.setIntArray5(new int[5]);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum SeparatorMode {
      Arrows, Square, Curly, Round, None;

      private SeparatorMode() {}


        private final String text648 = null;
        private final String text888 = null;

        /*
         * WARNING - void declaration
         */

        private static SeparatorMode[] getSeparatorModeArray2() {
            return new SeparatorMode[]{Arrows, Square, Curly, Round, None};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum UnitMode {
      unitMode, unitMode2;

      private UnitMode() {}



        private static UnitMode[] getUnitModeArray() {
            return new UnitMode[]{unitMode, unitMode2};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum TimeMode {
      HHMM, HHMMSS;

      private TimeMode() {}



        private static TimeMode[] getTimeModeArray() {
            return new TimeMode[]{HHMM, HHMMSS};
        }
    
   }
}

