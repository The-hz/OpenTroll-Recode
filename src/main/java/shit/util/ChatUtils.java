/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ChatUtils
implements MC {
    private static boolean flag100;

    public static void sendChatMessage(Object object) {
        String string;
        block8: {
            String string2;
            block7: {
                boolean bl;
                block6: {
                    block5: {
                        MinecraftClient minecraftClient;
                        block4: {
                            string = (String)object;
                            bl = ChatUtils.isAlwaysTrue();
                            minecraftClient = MC.mc;
                            if (bl) break block4;
                            if (minecraftClient.player == null) break block5;
                            minecraftClient = MC.mc;
                        }
                        if (minecraftClient.getNetworkHandler() != null) break block6;
                    }
                    return;
                }
                string2 = string;
                if (bl) break block7;
                if (string2.length() <= 256) break block8;
                string2 = string.substring(0, 256);
            }
            string = string2;
        }
        MC.mc.player.networkHandler.sendChatMessage(string);
    }

    public static void sendChatCommand(Object object) {
        String string;
        String string2;
        block8: {
            block7: {
                MinecraftClient minecraftClient;
                block6: {
                    string2 = (String)object;
                    boolean bl = false;
                    minecraftClient = MC.mc;
                    if (!false) break block6;
                    if (minecraftClient.player == null) break block7;
                    minecraftClient = MC.mc;
                }
                if (minecraftClient.getNetworkHandler() != null) break block8;
            }
            return;
        }
        String string3 = string2;
        if (false) {
            string3 = string = string3.startsWith("/") ? string2.substring(1) : string2;
        }
        if (false) {
            if (string.length() > 255) {
                string = string.substring(0, 255);
            }
            MC.mc.player.networkHandler.sendChatCommand(string);
        }
    }

    public static void sendClientMessage(Object object) {
        block3: {
            InGameHud inGameHud;
            String string;
            block2: {
                string = (String)object;
                boolean bl = false;
                inGameHud = MC.mc.inGameHud;
                if (!false) break block2;
                if (inGameHud == null) break block3;
                inGameHud = MC.mc.inGameHud;
            }
            inGameHud.getChatHud().addMessage((Text)Text.literal((String)string));
        }
    }

    public static void setFlag100(boolean bl) {
        flag100 = bl;
    }

    public static boolean getFlag100() {
        return flag100;
    }

    public static boolean isAlwaysTrue() {
        boolean bl = false;
        return true;
    }
}

