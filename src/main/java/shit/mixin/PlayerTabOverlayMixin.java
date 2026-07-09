/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shit.module.misc.Friend;
import shit.module.misc.IRC;
import shit.module.render.ExtraTab;
import shit.render.ChatClient;

@Environment(value=EnvType.CLIENT)
@Mixin(value={PlayerListHud.class})
public class PlayerTabOverlayMixin {
    @Inject(method={"getPlayerName(Lnet/minecraft/client/network/PlayerListEntry;)Lnet/minecraft/text/Text;"}, at={@At(value="RETURN")}, cancellable=true)
    private void m896(PlayerListEntry playerListEntry, CallbackInfoReturnable callbackInfoReturnable) {
        String string = playerListEntry.getProfile().name();
        Text text = (Text)callbackInfoReturnable.getReturnValue();
        if (ChatClient.m404(string)) {
            String string2 = IRC.m518(string);
            MutableText mutableText = Text.empty().append((Text)Text.literal((String)string2)).append((Text)(text != null ? text : Text.literal((String)string)));
            callbackInfoReturnable.setReturnValue((Object)mutableText);
        } else if (Friend.m446(string)) {
            MutableText mutableText = Text.empty().append((Text)Text.literal((String)"\u00a7a[Friend] \u00a7r")).append((Text)(text != null ? text : Text.literal((String)string)));
            callbackInfoReturnable.setReturnValue((Object)mutableText);
        }
    }

    @Inject(method={"collectPlayerEntries()Ljava/util/List;"}, at={@At(value="RETURN")}, cancellable=true)
    private void setCallbackInfoReturnable3(CallbackInfoReturnable callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue((Object)ExtraTab.m762((List)callbackInfoReturnable.getReturnValue()));
    }
}

