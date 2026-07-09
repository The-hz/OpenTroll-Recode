/*
 * Decompiled with CFR 0.152.
 */
package shit.module.chat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.util.Hand;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.module.chat.ChatTimestamp;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.StringSetting;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class AutoCope
extends Module {
    private final EnumSetting mode = (EnumSetting)this.m28(new EnumSetting("Mode", Mode.Internal));
    private final StringSetting copeReply = (StringSetting)this.m28(new StringSetting("CopeReply", "cope $NAME"));
    private final BooleanSetting reply = (BooleanSetting)this.m28(new BooleanSetting("Reply", false));
    private static final File file2 = null;
    private static final String[] texts = new String[0];
    private final Random random7 = new Random();
    private final Helper7 helper719 = new Helper7();
    private List list29 = List.of(texts);

    public AutoCope() {
        super("AutoCope", "Sends excuses on death and optional cope replies.", Category.CHAT);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void onEnable() {
        this.list29 = this.mode.getObj() != Mode.External ? List.of(texts) : this.getList();
        this.helper719.setLong3(3000L);
    }

    @EventHandler
    private void setPacketEventInner21(PacketEvent.PacketEventInner packetEventInner) {
        String string;
        if (Module.isSet37()) {
            return;
        }
        Object object = packetEventInner.getPacket();
        if (object instanceof HealthUpdateS2CPacket) {
            HealthUpdateS2CPacket healthUpdateS2CPacket = (HealthUpdateS2CPacket)object;
            if (healthUpdateS2CPacket.getHealth() <= 0.0f && this.helper719.m114(3.0) && !this.isSet49()) {
                Util2.setObj62(this.getText53());
                this.helper719.m533();
            }
            return;
        }
        if (!((Boolean)this.reply.getObj()).booleanValue()) {
            return;
        }
        String string2 = this.m511(packetEventInner);
        if (string2 == null || MC.client3.player == null) {
            return;
        }
        object = string2.toLowerCase(Locale.ROOT);
        if (!((String)object).contains(string = MC.client3.player.getName().getString().toLowerCase(Locale.ROOT))) {
            return;
        }
        if (!(((String)object).contains("bad") || ((String)object).contains("noob") || ((String)object).contains("skill") || ((String)object).contains("cope") || ((String)object).contains("ez"))) {
            return;
        }
        String string3 = AutoCope.m773(string2);
        Util2.setObj62(((String)this.copeReply.getObj()).replace("$NAME", string3));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet49() {
        int[] nArray = ChatTimestamp.getIntArray2();
        boolean bl = MC.client3.player.getStackInHand(Hand.MAIN_HAND).isOf(Items.TOTEM_OF_UNDYING);
        if (nArray == null) return bl;
        if (bl) return true;
        bl = MC.client3.player.getStackInHand(Hand.OFF_HAND).isOf(Items.TOTEM_OF_UNDYING);
        if (nArray == null) return bl;
        if (!bl) return false;
        return true;
    }

    private String getText53() {
        int[] nArray = ChatTimestamp.getIntArray2();
        List list = this.list29;
        if (nArray != null) {
            if (list.isEmpty()) {
                return texts[this.random7.nextInt(texts.length)];
            }
            list = (List)this.list29.get(this.random7.nextInt(this.list29.size()));
        }
        return (String)((Object)list);
    }

    private List getList() {
        int[] nArray = ChatTimestamp.getIntArray2();
        try {
            List<String> list;
            block16: {
                boolean bl;
                ArrayList<String> arrayList;
                block13: {
                    block15: {
                        block14: {
                            boolean bl2 = file2.exists();
                            if (nArray == null) break block14;
                            if (bl2) break block15;
                            bl2 = file2.createNewFile();
                        }
                        return List.of(texts);
                    }
                    arrayList = new ArrayList<String>();
                    for (String string : Files.readAllLines(file2.toPath())) {
                        String string2 = string.trim();
                        bl = string2.isEmpty();
                        if (nArray != null) {
                            if (nArray != null && !bl) {
                                arrayList.add(string2);
                            }
                            if (nArray != null) continue;
                        }
                        break block13;
                    }
                    list = arrayList;
                    if (nArray == null) break block16;
                    bl = list.isEmpty();
                }
                list = bl ? List.of(texts) : arrayList;
            }
            return list;
        }
        catch (IOException iOException) {
            return List.of(texts);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private String m511(Object object) {
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
     * Unable to fully structure code
     */
    private static String m773(Object var0) {
        String string = (String)var0;
        if (string.startsWith("<") && string.contains(">")) {
            return string.substring(1, string.indexOf(62));
        }
        int n = string.indexOf(32);
        return n > 0 ? string.substring(0, n) : "you";
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      Internal, External;

      private Mode() {}



        private static Mode[] getModeArray8() {
            return new Mode[]{Internal, External};
        }
    
   }
}

