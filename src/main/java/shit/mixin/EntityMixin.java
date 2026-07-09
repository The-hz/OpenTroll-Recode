/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shit.module.movement.NoSlow;

@Environment(value=EnvType.CLIENT)
@Mixin(value={Entity.class})
public class EntityMixin {
    @Inject(method={"getVelocityMultiplier()F"}, at={@At(value="RETURN")}, cancellable=true)
    private void setCallbackInfoReturnable2(CallbackInfoReturnable callbackInfoReturnable) {
        if ((Object) this instanceof ClientPlayerEntity && NoSlow.INSTANCE != null && NoSlow.INSTANCE.isSet87()) {
            callbackInfoReturnable.setReturnValue((Object)Float.valueOf(1.0f));
        }
    }
}

