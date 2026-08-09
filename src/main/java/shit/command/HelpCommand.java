/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.Client;
import shit.command.Command;
import shit.command.CommandManager;
import shit.command.ToggleCommand;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class HelpCommand
extends Command {
    public HelpCommand() {
        super("help", "Lists commands.", "help");
    }

    @Override
    public void run(Object object) {
        String[] cfr_ignored_0 = (String[])object;
        String string = Client.commandManager.getPrefix();
        boolean bl = false;
        for (Command command : Client.commandManager.getCollection()) {
            CommandManager.sendFeedback(string + command.getText31() + " - " + command.getText8());
            if (!false) continue;
        }
        if (Module.getTextArray9() == null) {
            ToggleCommand.setFlag8(!false);
        }
    }
}

