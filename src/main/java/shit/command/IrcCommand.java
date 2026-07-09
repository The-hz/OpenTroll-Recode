/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.command.Command;
import shit.command.CommandManager;
import shit.module.misc.IRC;
import shit.render.ChatClient;

@Environment(value=EnvType.CLIENT)
public class IrcCommand
extends Command {
    public IrcCommand() {
        super("irc", "Sends a message to the NekoTeam IRC relay.", "irc <message>");
    }

    @Override
    public void run(Object object) {
        CharSequence[] charSequenceArray = (String[])object;
        boolean bl = false;
        if (charSequenceArray.length == 0) {
            CommandManager.setObj21("Usage: irc <message>");
            return;
        }
        if (IRC.INSTANCE == null || !IRC.INSTANCE.isSet19()) {
            CommandManager.setObj21("\u00a7cIRC module is not enabled. Enable it first.");
            return;
        }
        ChatClient.send2(String.join((CharSequence)" ", charSequenceArray));
    }
}

