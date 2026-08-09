/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import java.util.Arrays;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.Client;
import shit.command.Command;
import shit.command.CommandManager;
import shit.render.LineRenderer2;

@Environment(value=EnvType.CLIENT)
public class FriendCommand
extends Command {
    public FriendCommand() {
        super("friend", "Manages friends.", "friend <add|del|list> [name]");
    }

    @Override
    public String[] getSuggestions(int n, Object object) {
        block4: {
            String[] stringArray;
            block5: {
                int n2 = n;
                stringArray = (String[])object;
                boolean bl = false;
                if (n2 == 1) {
                    String string = stringArray.length > 1 ? stringArray[1].toLowerCase() : "";
                    return (String[])Arrays.stream(new String[]{"add", "del", "list"}).filter(string2 -> string2.startsWith(string)).toArray(String[]::new);
                }
                if (n2 != 2) break block4;
                String string = stringArray.length > 1 ? stringArray[1].toLowerCase() : "";
                if (string.equals("del")) break block5;
                if (!string.equals("remove")) break block4;
            }
            String string = stringArray.length > 2 ? stringArray[2].toLowerCase() : "";
            return (String[])Client.friendManager.getFriends().stream().filter(string2 -> ((String)string2).toLowerCase().startsWith(string)).toArray(String[]::new);
        }
        return new String[0];
    }

    @Override
    public void run(Object object) {
        String[] args = (String[])object;
        if (args.length < 1) {
            CommandManager.sendFeedback("Usage: " + Client.commandManager.getPrefix() + this.getText31());
        } else if (args[0].equalsIgnoreCase("list")) {
            CommandManager.sendFeedback("Friends: " + String.join(", ", Client.friendManager.getFriends()));
        } else if (args.length < 2) {
            CommandManager.sendFeedback("Missing name.");
        } else if (args[0].equalsIgnoreCase("add")) {
            Client.friendManager.addFriend(args[1]);
            CommandManager.sendFeedback("Added friend " + args[1] + ".");
        } else if (args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("remove")) {
            Client.friendManager.removeFriend(args[1]);
            CommandManager.sendFeedback("Removed friend " + args[1] + ".");
        } else {
            CommandManager.sendFeedback("Usage: " + Client.commandManager.getPrefix() + this.getText31());
        }
    }
}

