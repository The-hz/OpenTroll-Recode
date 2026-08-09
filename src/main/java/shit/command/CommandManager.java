/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import shit.command.BindCommand;
import shit.command.Command;
import shit.command.ConfigCommand;
import shit.command.FriendCommand;
import shit.command.HelpCommand;
import shit.command.IrcCommand;
import shit.command.IrccrashCommand;
import shit.command.IrckickCommand;
import shit.command.KitCommand;
import shit.command.LoadCommand;
import shit.command.PrefixCommand;
import shit.command.SaveCommand;
import shit.command.ToggleCommand;
import shit.module.client.ClientSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class CommandManager
implements MC {
    private final Map<String, Command> map4 = new LinkedHashMap<>();
    private String prefix = ".";

    public void init() {
        this.setPrefix(new HelpCommand());
        this.setPrefix(new ToggleCommand());
        this.setPrefix(new BindCommand());
        this.setPrefix(new FriendCommand());
        this.setPrefix(new PrefixCommand());
        this.setPrefix(new ConfigCommand());
        this.setPrefix(new SaveCommand());
        this.setPrefix(new LoadCommand());
        this.setPrefix(new KitCommand());
        this.setPrefix(new IrcCommand());
        this.setPrefix(new IrckickCommand());
        this.setPrefix(new IrccrashCommand());
    }

    public boolean tryExecute(Object object) {
        String string = (String)object;
        Object var4_3 = null;
        if (!string.startsWith(this.prefix)) {
            return false;
        }
        String string2 = string.substring(this.prefix.length()).trim();
        if (string2.isEmpty()) {
            return true;
        }
        String[] stringArray = string2.split("\\s+");
        Command command = (Command)this.map4.get(stringArray[0].toLowerCase(Locale.ROOT));
        if (command == null) {
            CommandManager.sendFeedback("Unknown command. Try " + this.prefix + "help.");
            return true;
        }
        String[] stringArray2 = new String[stringArray.length - 1];
        System.arraycopy(stringArray, 1, stringArray2, 0, stringArray2.length);
        try {
            command.run(stringArray2);
        } catch (RuntimeException e) {
            CommandManager.sendFeedback("Command '" + stringArray[0] + "' is not available in this build.");
        }
        return true;
    }

    public void setPrefix(Object object) {
        Command command = (Command)object;
        this.map4.put(command.getText48().toLowerCase(Locale.ROOT), command);
    }

    public Collection<Command> getCollection() {
        return this.map4.values();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setObj8(Object object) {
        block0: {
            String string = (String)object;
            Object var4_3 = null;
            if (string == null || string.isBlank()) break block0;
            this.prefix = string;
        }
    }

    public static void sendFeedback(Object object) {
        String string = (String)object;
        Object var3_2 = null;
        if (MC.mc.inGameHud == null) {
            return;
        }
        int n = ClientSetting.INSTANCE != null ? (Integer)ClientSetting.INSTANCE.prefixColor.getValue() : -11141121;
        int n2 = n & 0xFFFFFF;
        Style style = Style.EMPTY.withColor(TextColor.fromRgb((int)n2));
        MutableText mutableText = Text.literal((String)"[TrollHack-Recode] ").fillStyle(style);
        MutableText mutableText2 = Text.literal((String)string);
        MC.mc.inGameHud.getChatHud().addMessage((Text)mutableText.copy().append((Text)mutableText2));
    }
}

