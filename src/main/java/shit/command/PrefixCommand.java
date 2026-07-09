/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.Client;
import shit.command.Command;
import shit.command.CommandManager;

@Environment(value=EnvType.CLIENT)
public class PrefixCommand
extends Command {
    public PrefixCommand() {
        super("prefix", "Changes command prefix.", "prefix <prefix>");
    }

    @Override
    public void run(Object object) {
        String[] stringArray = (String[])object;
        boolean bl = false;
        if (stringArray.length < 1) {
            CommandManager.setObj21("Current prefix: " + Client.commandManager.getText10());
            return;
        }
        Client.commandManager.setObj8(stringArray[0]);
        CommandManager.setObj21("Prefix set to " + stringArray[0]);
    }
}

