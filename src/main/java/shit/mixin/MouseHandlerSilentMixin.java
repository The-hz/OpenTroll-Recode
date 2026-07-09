/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.module.combat.AntiRegear;
import shit.module.combat.AutoRegear;

@Environment(value=EnvType.CLIENT)
@Mixin(value={Mouse.class})
public class MouseHandlerSilentMixin {
    @Inject(method={"unlockCursor()V"}, at={@At(value="HEAD")}, cancellable=true)
    private void setCallbackInfo2(CallbackInfo callbackInfo) {
        if (AutoRegear.isSet128() || AntiRegear.isSet81()) {
            callbackInfo.cancel();
        }
    }
}

