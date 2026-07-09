/*
 * Decompiled with CFR 0.152.
 */
package shit.module.chat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.module.chat.ChatTimestamp;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class ExtraChatHistory
extends Module {
    public static ExtraChatHistory INSTANCE;
    public final NumberSetting maxMessages = (NumberSetting)this.m28(new NumberSetting("MaxMessages", 1000.0, 100.0, 5000.0, 100.0));

    public ExtraChatHistory() {
        super("ExtraChatHistory", "Show more messages in the chat history.", Category.CHAT);
        INSTANCE = this;
    }

    public int getInt33() {
        return this.isSet19() ? this.maxMessages.getInt50() : 100;
    }
}

