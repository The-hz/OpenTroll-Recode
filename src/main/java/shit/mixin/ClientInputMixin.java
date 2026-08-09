/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shit.Client;
import shit.module.client.ClientSetting;
import shit.module.movement.AutoSprint;

@Environment(value=EnvType.CLIENT)
@Mixin(value={Input.class})
public class ClientInputMixin {
    private boolean shouldMovementSync() {
        boolean bl = ClientSetting.INSTANCE != null && (Boolean)ClientSetting.INSTANCE.movementSync.getValue() != false;
        boolean bl2 = AutoSprint.INSTANCE != null && AutoSprint.INSTANCE.isSet160();
        return (bl || bl2) && Client.mathUtil.hasPendingRotation();
    }

    @Inject(method={"getMovementInput()Lnet/minecraft/util/math/Vec2f;"}, at={@At(value="RETURN")}, cancellable=true)
    private void setCallbackInfoReturnable5(CallbackInfoReturnable callbackInfoReturnable) {
        if (!this.shouldMovementSync()) {
            return;
        }
        if (MinecraftClient.getInstance().player == null) {
            return;
        }
        Vec2f vec2f = (Vec2f)callbackInfoReturnable.getReturnValue();
        float f = MinecraftClient.getInstance().player.getYaw();
        float f2 = Client.mathUtil.getFloat55();
        float f3 = f - f2;
        double d = Math.toRadians(f3);
        double d2 = Math.cos(d);
        double d3 = Math.sin(d);
        double d4 = vec2f.x;
        double d5 = vec2f.y;
        float f4 = Math.round(d4 * d2 - d5 * d3);
        float f5 = Math.round(d5 * d2 + d4 * d3);
        callbackInfoReturnable.setReturnValue((Object)new Vec2f(f4, f5));
    }

    @Inject(method={"hasForwardMovement()Z"}, at={@At(value="HEAD")}, cancellable=true)
    private void setCallbackInfoReturnable4(CallbackInfoReturnable callbackInfoReturnable) {
        Input input = (Input)(Object)this;
        Vec2f vec2f = input.getMovementInput();
        boolean bl = false;
        if (AutoSprint.INSTANCE != null) {
            bl = AutoSprint.INSTANCE.isSet142();
        }
        if (bl) {
            callbackInfoReturnable.setReturnValue((Object)(vec2f.lengthSquared() > 1.0E-5f));
        } else {
            callbackInfoReturnable.setReturnValue((Object)(vec2f.y > 1.0E-5f));
        }
    }
}

