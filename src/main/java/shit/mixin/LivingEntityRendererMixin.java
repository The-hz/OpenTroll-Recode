/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.Client;
import shit.module.render.Chams;

@Environment(value=EnvType.CLIENT)
@Mixin(value={LivingEntityRenderer.class})
public abstract class LivingEntityRendererMixin {
    @Shadow
    public abstract Identifier getTexture(LivingEntityRenderState var1);

    @Inject(method={"updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V"}, at={@At(value="TAIL")})
    private void m898(LivingEntity livingEntity, LivingEntityRenderState livingEntityRenderState, float f, CallbackInfo callbackInfo) {
        if (livingEntity == MinecraftClient.getInstance().player && Client.mathUtil.hasPendingRotation()) {
            float f2 = MathHelper.lerpAngleDegrees((float)f, (float)Client.mathUtil.getFloat42(), (float)Client.mathUtil.getFloat19());
            float f3 = MathHelper.lerpAngleDegrees((float)f, (float)Client.mathUtil.getFloat56(), (float)Client.mathUtil.getFloat25());
            float f4 = MathHelper.lerp((float)f, (float)Client.mathUtil.getFloat63(), (float)Client.mathUtil.getFloat65());
            livingEntityRenderState.bodyYaw = f2;
            livingEntityRenderState.relativeHeadYaw = MathHelper.wrapDegrees((float)(f3 - f2));
            livingEntityRenderState.pitch = f4;
        }
    }

    @Redirect(method={"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/RenderLayer;IIILnet/minecraft/client/texture/Sprite;ILnet/minecraft/client/render/command/ModelCommandRenderer$CrumblingOverlayCommand;)V"))
    private void m906(OrderedRenderCommandQueue orderedRenderCommandQueue, Model model, Object object, MatrixStack matrixStack, RenderLayer renderLayer, int n, int n2, int n3, Sprite sprite, int n4, ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand) {
        Chams chams = Chams.INSTANCE;
        if (chams != null && chams.m681(object) && object instanceof LivingEntityRenderState) {
            LivingEntityRenderState livingEntityRenderState = (LivingEntityRenderState)object;
            chams.m249(object, model);
            if (ColorHelper.getAlpha((int)chams.getInt4()) < 255) {
                renderLayer = RenderLayers.entityTranslucent((Identifier)(Object)this.getTexture(livingEntityRenderState));
            }
            n3 = ColorHelper.mix((int)n3, (int)chams.getInt4());
        }
        orderedRenderCommandQueue.submitModel(model, object, matrixStack, renderLayer, n, n2, n3, sprite, n4, crumblingOverlayCommand);
    }
}

