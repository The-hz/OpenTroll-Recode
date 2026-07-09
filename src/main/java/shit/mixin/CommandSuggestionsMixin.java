/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.Client;
import shit.command.Command;

@Environment(value=EnvType.CLIENT)
@Mixin(value={ChatInputSuggestor.class})
public abstract class CommandSuggestionsMixin {
    @Final
    @Shadow
    TextFieldWidget textField;
    @Shadow
    CompletableFuture pendingSuggestions;

    @Shadow
    public abstract void show(boolean var1);

    @Inject(method={"refresh()V"}, at={@At(value="HEAD")}, cancellable=true)
    private void setCallbackInfo4(CallbackInfo callbackInfo) {
        String string = Client.commandManager.getText10();
        String string2 = this.textField.getText();
        if (!string2.startsWith(string)) {
            return;
        }
        int n2 = this.textField.getCursor();
        String string3 = string2.substring(0, n2);
        int n3 = 0;
        Matcher matcher = Pattern.compile("\\s+").matcher(string3);
        while (matcher.find()) {
            n3 = matcher.end();
        }
        SuggestionsBuilder suggestionsBuilder = new SuggestionsBuilder(string3, n3);
        string3.substring(string.length());
        List<String> list = List.of(string3.split(" ", -1));
        int n4 = (int)string3.chars().filter(n -> n == 32).count();
        if (n4 == 0) {
            for (Command command : Client.commandManager.getCollection()) {
                String string4 = string + command.getText48();
                if (!string4.startsWith(string3)) continue;
                suggestionsBuilder.suggest(string4 + " ");
            }
        } else {
            String string5 = list.get(0).substring(string.length());
            Command command = null;
            for (Command cmd : Client.commandManager.getCollection()) {
                if (!cmd.getText48().equalsIgnoreCase(string5)) continue;
                command = cmd;
                break;
            }
            if (command == null) {
                return;
            }
            String[] stringArray = command.getSuggestions(n4, list.toArray(new String[0]));
            if (stringArray == null || stringArray.length == 0) {
                return;
            }
            for (String string6 : stringArray) {
                suggestionsBuilder.suggest(string6);
            }
        }
        this.pendingSuggestions = suggestionsBuilder.buildFuture();
        this.show(false);
        callbackInfo.cancel();
    }
}

