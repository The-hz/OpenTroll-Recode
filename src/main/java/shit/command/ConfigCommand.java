/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.Client;
import shit.command.Command;
import shit.command.CommandManager;
import shit.command.ToggleCommand;

@Environment(value=EnvType.CLIENT)
public class ConfigCommand
extends Command {
    public ConfigCommand() {
        super("config", "Saves, loads, and lists named configs.", "config <save|load|list> [name]");
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void run(Object var1_1) {
        String[] args = (String[])var1_1;
        if (args.length == 0) {
            this.m619();
            return;
        }
        String sub = args[0].toLowerCase(Locale.ROOT);
        switch (sub) {
            case "save": {
                if (args.length < 2 || args[1].isBlank()) {
                    CommandManager.sendFeedback("Usage: .config save <name> - saves current modules/settings to trollhack-recode/configs/<name>.txt");
                    return;
                }
                if (Client.configManager.m309(args[1])) {
                    CommandManager.sendFeedback("Saved config '" + args[1] + "'.");
                } else {
                    CommandManager.sendFeedback("Invalid config name. Use letters, numbers, dot, dash, or underscore.");
                }
                return;
            }
            case "load": {
                if (args.length < 2 || args[1].isBlank()) {
                    CommandManager.sendFeedback("Usage: .config load <name> - loads trollhack-recode/configs/<name>.txt");
                    return;
                }
                if (Client.configManager.m575(args[1])) {
                    CommandManager.sendFeedback("Loaded config '" + args[1] + "'.");
                } else {
                    CommandManager.sendFeedback("Config '" + args[1] + "' was not found.");
                }
                return;
            }
            case "list": {
                java.util.List profiles = Client.configManager.listProfiles();
                if (profiles.isEmpty()) {
                    CommandManager.sendFeedback("No named configs found. Save one with .config save <name>.");
                } else {
                    CommandManager.sendFeedback("Configs: " + String.join(", ", profiles));
                }
                return;
            }
            default:
                this.m619();
        }
    }

    @Override
    public String[] getSuggestions(int n, Object object) {
        int n2 = n;
        String[] stringArray = (String[])object;
        boolean bl = false;
        if (n2 == 1) {
            return new String[]{"save", "load", "list"};
        }
        if (n2 == 2) {
            if (stringArray.length > 1) {
                if ("load".equalsIgnoreCase(stringArray[1])) {
                    return (String[])Client.configManager.listProfiles().toArray(String[]::new);
                }
            }
        }
        return new String[0];
    }

    private void m619() {
        CommandManager.sendFeedback("Config commands:");
        CommandManager.sendFeedback(".config save <name> - save current modules/settings as a named config.");
        CommandManager.sendFeedback(".config load <name> - load a named config and apply module states.");
        CommandManager.sendFeedback(".config list - show saved named configs.");
    }
}

