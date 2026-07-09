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
public class SaveCommand
extends Command {
    public SaveCommand() {
        super("save", "Saves config.", "save");
    }

    @Override
    public void run(Object object) {
        String[] cfr_ignored_0 = (String[])object;
        Client.configManager.m1042();
        CommandManager.setObj21("Config saved.");
    }
}

