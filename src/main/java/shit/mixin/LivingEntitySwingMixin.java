/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shit.module.player.HandSwing;
import shit.module.player.SwingLimiter;
import shit.module.render.ViewModel;

@Environment(value=EnvType.CLIENT)
@Mixin(value={LivingEntity.class})
public class LivingEntitySwingMixin {
    @Inject(method={"getHandSwingDuration()I"}, at={@At(value="HEAD")}, cancellable=true)
    private void trollhack$swingDuration(CallbackInfoReturnable callbackInfoReturnable) {
        HandSwing handSwing = HandSwing.INSTANCE;
        if (handSwing != null && handSwing.isSet40()) {
            callbackInfoReturnable.setReturnValue((Object)handSwing.getInt89());
            return;
        }
        SwingLimiter swingLimiter = SwingLimiter.INSTANCE;
        if (swingLimiter != null && swingLimiter.isEnabled()) {
            callbackInfoReturnable.setReturnValue((Object)swingLimiter.getInt2());
            return;
        }
        ViewModel viewModel = ViewModel.INSTANCE;
        if (viewModel != null && viewModel.isEnabled() && ((Boolean)viewModel.swingSpeed.getValue()).booleanValue()) {
            callbackInfoReturnable.setReturnValue((Object)viewModel.value.getInt());
        }
    }
}

