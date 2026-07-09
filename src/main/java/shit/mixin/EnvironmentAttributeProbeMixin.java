/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeInterpolator;
import net.minecraft.world.attribute.EnvironmentAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shit.module.render.Ambience;

@Environment(value=EnvType.CLIENT)
@Mixin(value={EnvironmentAttributeInterpolator.class})
public class EnvironmentAttributeProbeMixin {
    @Inject(method={"get(Lnet/minecraft/world/attribute/EnvironmentAttribute;F)Ljava/lang/Object;"}, at={@At(value="RETURN")}, cancellable=true)
    private void m897(EnvironmentAttribute environmentAttribute, float f, CallbackInfoReturnable callbackInfoReturnable) {
        Ambience ambience = Ambience.INSTANCE;
        if (ambience == null || !ambience.isSet19()) {
            return;
        }
        if (environmentAttribute == EnvironmentAttributes.FOG_COLOR_VISUAL && ((Boolean)ambience.fogColorDraw.getObj()).booleanValue()) {
            callbackInfoReturnable.setReturnValue(ambience.fogColor.getObj());
        } else if (environmentAttribute == EnvironmentAttributes.SKY_COLOR_VISUAL && ((Boolean)ambience.skyColorDraw.getObj()).booleanValue()) {
            callbackInfoReturnable.setReturnValue(ambience.skyColor.getObj());
        } else if (environmentAttribute == EnvironmentAttributes.CLOUD_COLOR_VISUAL && ((Boolean)ambience.cloudColorDraw.getObj()).booleanValue()) {
            callbackInfoReturnable.setReturnValue(ambience.cloudColor.getObj());
        } else if (environmentAttribute == EnvironmentAttributes.SKY_LIGHT_COLOR_VISUAL && ((Boolean)ambience.worldColorDraw.getObj()).booleanValue()) {
            callbackInfoReturnable.setReturnValue(ambience.worldColor.getObj());
        }
    }
}

