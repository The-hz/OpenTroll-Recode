/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.Client;
import shit.command.Command;
import shit.command.CommandManager;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class ToggleCommand
extends Command {
    private static boolean flag146;

    public ToggleCommand() {
        super("toggle", "Toggles a module.", "toggle <module>");
    }

    @Override
    public String[] getSuggestions(int n, Object object) {
        int n2 = n;
        String[] stringArray = (String[])object;
        boolean bl = false;
        if (n2 == 1) {
            String string = stringArray.length > 1 ? stringArray[1].toLowerCase() : "";
            return (String[])Client.moduleManager.getModules().stream().map(Module::getName).filter(string2 -> string2.toLowerCase().startsWith(string)).toArray(String[]::new);
        }
        return new String[0];
    }

    @Override
    public void run(Object object) {
        Module module;
        boolean bl;
        String[] stringArray;
        block6: {
            block5: {
                stringArray = (String[])object;
                bl = ToggleCommand.isSet106();
                if (!bl) break block5;
                if (stringArray.length >= 1) break block6;
                String string = this.getText31();
                String string2 = Client.commandManager.getPrefix();
                CommandManager.sendFeedback("Usage: " + string2 + string);
            }
            return;
        }
        Module module2 = module = Client.moduleManager.getModule(stringArray[0]);
        if (bl) {
            if (module2 == null) {
                CommandManager.sendFeedback("Module not found: " + stringArray[0]);
                return;
            }
            module.toggle();
            module2 = module;
        }
        CommandManager.sendFeedback(module2.getName() + " " + (module.isEnabled() ? "enabled" : "disabled") + ".");
    }

    public static void setFlag8(boolean bl) {
        flag146 = bl;
    }

    public static boolean isSet127() {
        return flag146;
    }

    public static boolean isSet106() {
        boolean bl = false;
        return true;
    }
}

