/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.Client;
import shit.event.KeyPressEvent;
import shit.module.Module;
import shit.setting.ColorSetting2;

@Environment(value=EnvType.CLIENT)
@Mixin(value={Keyboard.class})
public class KeyboardHandlerMixin {
    @Inject(method={"onKey(JILnet/minecraft/client/input/KeyInput;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void trollhack$keyPress(long l, int n, KeyInput keyInput, CallbackInfo callbackInfo) {
        KeyPressEvent keyPressEvent = (KeyPressEvent)Client.eventBus.post(new KeyPressEvent(n, keyInput));
        if (keyPressEvent.isCancelled()) {
            callbackInfo.cancel();
            return;
        }
        if (keyInput.key() == -1) {
            return;
        }
        if (MinecraftClient.getInstance().currentScreen != null) {
            return;
        }
        for (Module module : Client.moduleManager.getModules()) {
            ColorSetting2 colorSetting2 = module.getKeyBindSetting();
            if (((Integer)colorSetting2.getValue()).intValue() != keyInput.key()) continue;
            if (n == 1) {
                if (colorSetting2.getType() == ColorSetting2.Type.Toggle) {
                    module.toggle();
                    continue;
                }
                if (colorSetting2.getType() != ColorSetting2.Type.Hold) continue;
                module.setEnabled(true);
                continue;
            }
            if (n != 0 || colorSetting2.getType() != ColorSetting2.Type.Hold) continue;
            module.setEnabled(false);
        }
    }
}

