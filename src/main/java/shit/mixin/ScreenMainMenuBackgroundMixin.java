/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.manager.ShaderProgramManager;

@Environment(value=EnvType.CLIENT)
@Mixin(value={Screen.class})
public class ScreenMainMenuBackgroundMixin {
    @Inject(method={"renderBackground(Lnet/minecraft/client/gui/DrawContext;IIF)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void m901(DrawContext drawContext, int n, int n2, float f, CallbackInfo callbackInfo) {
        if (!((Object) this instanceof net.minecraft.client.gui.screen.world.SelectWorldScreen)
                && !((Object) this instanceof net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen)) {
            return;
        }
        if (ShaderProgramManager.m252(drawContext)) {
            callbackInfo.cancel();
        }
    }
}

