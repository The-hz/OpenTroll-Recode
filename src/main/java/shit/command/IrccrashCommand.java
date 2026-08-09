/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.command.Command;
import shit.command.CommandManager;
import shit.render.ChatClient;

@Environment(value=EnvType.CLIENT)
public class IrccrashCommand
extends Command {
    public IrccrashCommand() {
        super("irccrash", "Crashes a user's client via IRC (admin only).", "irccrash <ircname>");
    }

    @Override
    public void run(Object object) {
        String[] stringArray = (String[])object;
        boolean bl = false;
        if (!ChatClient.isSet2()) {
            CommandManager.sendFeedback("\u00a7cYou are not an IRC admin.");
            return;
        }
        if (stringArray.length == 0) {
            CommandManager.sendFeedback("Usage: irccrash <ircname>");
            return;
        }
        ChatClient.send2(";crash " + stringArray[0]);
    }
}

