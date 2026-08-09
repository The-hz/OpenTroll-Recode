/*
 * Decompiled with CFR 0.152.
 */
package shit.module.chat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.DisconnectEvent;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.chat.ChatTimestamp;
import shit.setting.BooleanSetting;
import shit.util.MathUtil;
import shit.util.ChatUtils;

@Environment(value=EnvType.CLIENT)
public class LoginMessage
extends Module {
    private final BooleanSetting afterMoving = (BooleanSetting)this.registerSetting(new BooleanSetting("AfterMoving", false));
    private static final File file3 = null;
    private final List<String> list12 = new ArrayList<>();
    private boolean flag97;
    private boolean flag17;

    public LoginMessage() {
        super("LoginMessage", "Sends configured messages once after joining a world.", Category.CHAT);
    }

    @Override
    public void onEnable() {
        this.m184();
        this.flag97 = false;
        this.flag17 = false;
    }

    @Override
    public void onDisable() {
        this.list12.clear();
    }

    @EventHandler
    private void setDisconnectEvent5(DisconnectEvent disconnectEvent) {
        this.flag97 = false;
        this.flag17 = false;
    }

    @EventHandler
    private void setEvent2Inner29(TickEvent.PostTick event2Inner2) {
        if (Module.isNotInGame() || this.flag97) {
            return;
        }
        if (MathUtil.isMoving()) {
            this.flag17 = true;
        }
        if (((Boolean)this.afterMoving.getValue()).booleanValue() && !this.flag17) {
            return;
        }
        for (String string : this.list12) {
            if (string.startsWith("/")) {
                ChatUtils.sendChatCommand(string.substring(1));
                continue;
            }
            ChatUtils.sendChatMessage(string);
        }
        this.flag97 = true;
    }

    private void m184() {
        int[] nArray = ChatTimestamp.getIntArray2();
        this.list12.clear();
        int[] nArray2 = nArray;
        try {
            File file = file3;
            if (nArray2 != null) {
                if (!file.exists()) {
                    file3.createNewFile();
                    ChatUtils.sendClientMessage("[LoginMessage] Created loginmsg.txt. Add messages before enabling again.");
                    return;
                }
                file = file3;
            }
            for (String string : Files.readAllLines(file.toPath())) {
                String string2 = string.trim();
                if (nArray2 != null) {
                    boolean bl = string2.isEmpty();
                    if (nArray2 != null && !bl) {
                        bl = this.list12.add(string2);
                    }
                    if (nArray2 != null) continue;
                }
                break;
            }
        }
        catch (IOException iOException) {
            ChatUtils.sendClientMessage("[LoginMessage] Failed to load loginmsg.txt: " + iOException.getMessage());
        }
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

