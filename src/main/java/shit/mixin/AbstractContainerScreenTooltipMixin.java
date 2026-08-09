/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.module.misc.ShulkerViewer;

@Environment(value=EnvType.CLIENT)
@Mixin(value={HandledScreen.class})
public class AbstractContainerScreenTooltipMixin {
    @Shadow
    protected Slot focusedSlot;

    @Inject(method={"drawMouseoverTooltip(Lnet/minecraft/client/gui/DrawContext;II)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void m904(DrawContext drawContext, int n, int n2, CallbackInfo callbackInfo) {
        if (this.focusedSlot != null && this.focusedSlot.hasStack() && ShulkerViewer.m364(drawContext, this.focusedSlot.getStack(), n, n2)) {
            callbackInfo.cancel();
        }
    }
}

