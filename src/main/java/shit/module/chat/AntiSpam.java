/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket
 *  net.minecraft.network.packet.s2c.play.GameMessageS2CPacket
 */
package shit.module.chat;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.chat.ChatTimestamp;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.ChatUtils;

@Environment(value=EnvType.CLIENT)
public class AntiSpam
extends Module {
    private final EnumSetting mode;
    private final EnumSetting replace;
    private final BooleanSetting duplicates;
    private final NumberSetting dupeTimeout;
    private final BooleanSetting discord;
    private final BooleanSetting serverIps;
    private final BooleanSetting automated;
    private final BooleanSetting specialStart;
    private final BooleanSetting specialEnd;
    private final BooleanSetting greenText;
    private final BooleanSetting filterOwn;
    private final BooleanSetting filterDMs;
    private final BooleanSetting showBlocked;
    private final Map map23;
    private static final Pattern pattern7 = null;
    private static final Pattern pattern2 = null;
    private static final Pattern pattern6 = null;
    private static final Pattern pattern5 = null;
    private static final Pattern pattern4 = null;
    private static final Pattern pattern = null;

        public AntiSpam() {
        super("AntiSpam", "Filters spam, advertisements and duplicate chat messages.", Category.CHAT);
        this.mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.Hide));
        this.replace = (EnumSetting)this.registerSetting(new EnumSetting("Replace", EMode.Asterisks));
        this.duplicates = (BooleanSetting)this.registerSetting(new BooleanSetting("Duplicates", true));
        this.dupeTimeout = (NumberSetting)this.registerSetting(new NumberSetting("DupeTimeout", 30.0, 1.0, 600.0, 1.0));
        this.discord = (BooleanSetting)this.registerSetting(new BooleanSetting("Discord", true));
        this.serverIps = (BooleanSetting)this.registerSetting(new BooleanSetting("ServerIps", true));
        this.automated = (BooleanSetting)this.registerSetting(new BooleanSetting("Automated", true));
        this.specialStart = (BooleanSetting)this.registerSetting(new BooleanSetting("SpecialStart", true));
        this.specialEnd = (BooleanSetting)this.registerSetting(new BooleanSetting("SpecialEnd", true));
        this.greenText = (BooleanSetting)this.registerSetting(new BooleanSetting("GreenText", false));
        this.filterOwn = (BooleanSetting)this.registerSetting(new BooleanSetting("FilterOwn", false));
        this.filterDMs = (BooleanSetting)this.registerSetting(new BooleanSetting("FilterDMs", false));
        this.showBlocked = (BooleanSetting)this.registerSetting(new BooleanSetting("ShowBlocked", false));
        this.map23 = new LinkedHashMap();
    }

    @Override
    public void onDisable() {
        this.map23.clear();
    }

    @EventHandler(priority=1000)
    private void setPacketEventInner16(PacketEvent.PacketEventInner packetEventInner) {
        String string = this.m741(packetEventInner);
        if (string == null || string.isBlank()) {
            return;
        }
        this.m133();
        if (!((Boolean)this.filterOwn.getValue()).booleanValue() && this.m508(string)) {
            return;
        }
        if (!((Boolean)this.filterDMs.getValue()).booleanValue() && AntiSpam.m800(string)) {
            return;
        }
        String string2 = AntiSpam.m557(string);
        String string3 = null;
        Pattern pattern = null;
        if (((Boolean)this.duplicates.getValue()).booleanValue() && this.m739(string)) {
            string3 = "Duplicate";
        }
        if (string3 == null && ((Boolean)this.discord.getValue()).booleanValue() && pattern7.matcher(string2).find()) {
            string3 = "Discord";
            pattern = pattern7;
        }
        if (string3 == null && ((Boolean)this.serverIps.getValue()).booleanValue() && pattern2.matcher(string2).find()) {
            string3 = "ServerIp";
            pattern = pattern2;
        }
        if (string3 == null && ((Boolean)this.automated.getValue()).booleanValue() && pattern6.matcher(string2).find()) {
            string3 = "Automated";
            pattern = pattern6;
        }
        if (string3 == null && ((Boolean)this.specialStart.getValue()).booleanValue() && pattern5.matcher(string2).matches()) {
            string3 = "SpecialStart";
        }
        if (string3 == null && ((Boolean)this.specialEnd.getValue()).booleanValue() && pattern4.matcher(string2).matches()) {
            string3 = "SpecialEnd";
        }
        if (string3 == null && ((Boolean)this.greenText.getValue()).booleanValue() && AntiSpam.pattern.matcher(string2).matches()) {
            string3 = "GreenText";
        }
        if (string3 == null) {
            return;
        }
        packetEventInner.cancel();
        if (((Boolean)this.showBlocked.getValue()).booleanValue()) {
            ChatUtils.sendClientMessage("[AntiSpam] " + string3 + ": " + string);
        }
        if (this.mode.getValue() == Mode.Replace) {
            String string4 = pattern == null ? this.getText41() : pattern.matcher(string).replaceAll(this.getText41());
            ChatUtils.sendClientMessage(string4);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private String m741(Object object) {
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m739(Object object) {
        String string = (String)object;
        long l = System.currentTimeMillis();
        int[] nArray = ChatTimestamp.getIntArray2();
        Long l2 = (Long)this.map23.put(string, l);
        if (l2 == null) return 0 != 0;
        double d = (double)(l - l2) - (Double)this.dupeTimeout.getValue() * 1000.0;
        int n = d == 0.0 ? 0 : (d < 0.0 ? -1 : 1);
        if (nArray == null) return n != 0;
        if (n >= 0) return 0 != 0;
        return 1 != 0;
    }

    private void m133() {
        long l = System.currentTimeMillis();
        Iterator iterator = this.map23.entrySet().iterator();
        int[] nArray = ChatTimestamp.getIntArray2();
        block0: while (true) {
            int n = iterator.hasNext() ? 1 : 0;
            while (n != 0) {
                long l2 = l - (Long)((Map.Entry)iterator.next()).getValue() - 600000L;
                n = l2 == 0L ? 0 : (l2 < 0L ? -1 : 1);
                if (nArray == null) continue;
                if (n <= 0) continue block0;
                iterator.remove();
                if (nArray == null) break block0;
                continue block0;
            }
            break;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m508(Object object) {
        String string = (String)object;
        int[] nArray = ChatTimestamp.getIntArray2();
        if (MC.mc.player == null) return false;
        boolean bl = string.toLowerCase(Locale.ROOT).startsWith("<" + MC.mc.player.getName().getString().toLowerCase(Locale.ROOT) + ">");
        if (nArray == null) return bl;
        if (!bl) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean m800(Object object) {
        String string = (String)object;
        String string2 = string.toLowerCase(Locale.ROOT);
        int[] nArray = ChatTimestamp.getIntArray2();
        boolean bl = string2.contains(" whispers");
        if (nArray == null) return bl;
        if (bl) return true;
        bl = string2.contains(" tells you");
        if (nArray == null) return bl;
        if (bl) return true;
        bl = string2.contains(" -> ");
        if (nArray == null) return bl;
        if (bl) return true;
        bl = string2.startsWith("[me ->");
        if (nArray == null) return bl;
        if (!bl) return false;
        return true;
    }

    private static String m557(Object object) {
        String string = (String)object;
        return string.replaceFirst("^<[^>]+>\\s*", "");
    }

    private String getText41() {
        return this.replace.getValue() == EMode.Redacted ? "[redacted]" : "****";
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
        Hide,
        Replace;


        private static Mode[] getModeArray23() {
            return new Mode[]{Hide, Replace};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode {
        Redacted,
        Asterisks;


        private static EMode[] getObjArray10() {
            return new EMode[]{Redacted, Asterisks};
        }
    }
}
