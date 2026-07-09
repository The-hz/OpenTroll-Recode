/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.command.Command;
import shit.command.CommandManager;
import shit.command.ToggleCommand;
import shit.render.ChatClient;

@Environment(value=EnvType.CLIENT)
public class IrckickCommand
extends Command {
    public IrckickCommand() {
        super("irckick", "Kicks a user from the IRC relay (admin only).", "irckick <ircname>");
    }

    @Override
    public void run(Object object) {
        String[] stringArray;
        block6: {
            int n;
            String[] stringArray2;
            block5: {
                stringArray2 = (String[])object;
                boolean bl = ToggleCommand.isSet106();
                n = ChatClient.isSet2() ? 1 : 0;
                if (!bl) break block5;
                if (n == 0) {
                    CommandManager.setObj21("\u00a7cYou are not an IRC admin.");
                    return;
                }
                stringArray = stringArray2;
                if (!bl) break block6;
                n = stringArray.length;
            }
            if (n == 0) {
                CommandManager.setObj21("Usage: irckick <ircname>");
                return;
            }
            stringArray = stringArray2;
        }
        ChatClient.send2(";kick " + stringArray[0]);
    }
}

