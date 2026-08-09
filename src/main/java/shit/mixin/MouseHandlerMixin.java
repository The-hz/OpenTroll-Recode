/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Mouse;
import net.minecraft.client.input.MouseInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.Client;
import shit.module.Module;
import shit.setting.ColorSetting2;

@Environment(value=EnvType.CLIENT)
@Mixin(value={Mouse.class})
public class MouseHandlerMixin {
    @Inject(method={"onMouseButton(JLnet/minecraft/client/input/MouseInput;I)V"}, at={@At(value="HEAD")})
    private void trollhack$onMousePress(long l, MouseInput mouseInput, int n, CallbackInfo callbackInfo) {
        int n2 = ColorSetting2.m559(mouseInput.button());
        for (Module module : Client.moduleManager.getModules()) {
            ColorSetting2 colorSetting2 = module.getKeyBindSetting();
            if ((Integer)colorSetting2.getValue() != n2) continue;
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

