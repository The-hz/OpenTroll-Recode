/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.WorldRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.memory.ObjectAllocator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shit.Client;
import shit.event.InterceptEntityOutlineEvent;
import shit.module.render.Shaders;

@Environment(value=EnvType.CLIENT)
@Mixin(value={WorldRenderer.class})
public class LevelRendererShaderMixin {
    @Inject(method={"canDrawEntityOutlines()Z"}, at={@At(value="RETURN")}, cancellable=true)
    private void setCallbackInfoReturnable(CallbackInfoReturnable callbackInfoReturnable) {
        Shaders shaders = Shaders.INSTANCE;
        if (shaders != null && shaders.isSet19()) {
            callbackInfoReturnable.setReturnValue((Object)true);
        }
    }

    @Inject(method={"pushEntityRenders(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/state/WorldRenderState;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;)V"}, at={@At(value="HEAD")})
    private void m890(MatrixStack matrixStack, WorldRenderState worldRenderState, OrderedRenderCommandQueue orderedRenderCommandQueue, CallbackInfo callbackInfo) {
        Shaders shaders = Shaders.INSTANCE;
        if (shaders != null && shaders.isSet19()) {
            worldRenderState.hasOutline = true;
        }
    }

    @Inject(method={"drawEntityOutlinesFramebuffer()V"}, at={@At(value="HEAD")}, cancellable=true)
    private void setCallbackInfo(CallbackInfo callbackInfo) {
        Shaders shaders = Shaders.INSTANCE;
        if (shaders == null || !shaders.isSet19()) {
            return;
        }
        InterceptEntityOutlineEvent interceptEntityOutlineEvent = (InterceptEntityOutlineEvent)Client.eventBus.m287(new InterceptEntityOutlineEvent((WorldRenderer)(Object)this, ObjectAllocator.TRIVIAL));
        if (interceptEntityOutlineEvent.isSet85()) {
            callbackInfo.cancel();
        }
    }
}

