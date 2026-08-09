/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.module.render.ViewModel;

@Environment(value=EnvType.CLIENT)
@Mixin(value={HeldItemRenderer.class})
public class ItemInHandRendererMixin {
    @Shadow
    @Mutable
    private ItemStack mainHand;
    @Shadow
    @Mutable
    private ItemStack offHand;
    @Shadow
    @Mutable
    private float equipProgressMainHand;
    @Shadow
    @Mutable
    private float lastEquipProgressMainHand;
    @Shadow
    @Mutable
    private float equipProgressOffHand;
    @Shadow
    @Mutable
    private float lastEquipProgressOffHand;
    @Unique
    private static final ThreadLocal trollhack$capturedHand = new ThreadLocal();

    @Inject(method={"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"}, at={@At(value="HEAD")})
    private void trollhack$instantSwap(float f, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, ClientPlayerEntity clientPlayerEntity, int n, CallbackInfo callbackInfo) {
        ViewModel viewModel = ViewModel.INSTANCE;
        if (viewModel == null || !viewModel.isEnabled()) {
            return;
        }
        if (!((Boolean)viewModel.mainhandSwap.getValue()).booleanValue()) {
            this.mainHand = clientPlayerEntity.getMainHandStack();
            this.equipProgressMainHand = 1.0f;
            this.lastEquipProgressMainHand = 1.0f;
        }
        if (!((Boolean)viewModel.offhandSwap.getValue()).booleanValue()) {
            this.offHand = clientPlayerEntity.getOffHandStack();
            this.equipProgressOffHand = 1.0f;
            this.lastEquipProgressOffHand = 1.0f;
        }
    }

    @Inject(method={"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V"}, at={@At(value="HEAD")})
    private void trollhack$captureHand(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float f2, Hand hand, float f3, ItemStack itemStack, float f4, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int n, CallbackInfo callbackInfo) {
        trollhack$capturedHand.set(hand);
    }

    @Inject(method={"renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemDisplayContext;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;I)V"}, at={@At(value="HEAD")})
    private void trollhack$transform(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int n, CallbackInfo callbackInfo) {
        ViewModel viewModel = ViewModel.INSTANCE;
        if (viewModel == null || !viewModel.isEnabled()) {
            return;
        }
        Hand hand = (Hand)trollhack$capturedHand.get();
        if (hand == null) {
            return;
        }
        if (hand == Hand.MAIN_HAND) {
            matrixStack.translate(viewModel.positionMainX.getFloat(), viewModel.positionMainY.getFloat(), viewModel.positionMainZ.getFloat());
            matrixStack.scale(viewModel.scaleMainX.getFloat(), viewModel.scaleMainY.getFloat(), viewModel.scaleMainZ.getFloat());
            matrixStack.multiply((Quaternionfc)RotationAxis.POSITIVE_X.rotationDegrees(viewModel.rotationMainX.getFloat()));
            matrixStack.multiply((Quaternionfc)RotationAxis.POSITIVE_Y.rotationDegrees(viewModel.rotationMainY.getFloat()));
            matrixStack.multiply((Quaternionfc)RotationAxis.POSITIVE_Z.rotationDegrees(viewModel.rotationMainZ.getFloat()));
        } else {
            matrixStack.translate(viewModel.positionOffX.getFloat(), viewModel.positionOffY.getFloat(), viewModel.positionOffZ.getFloat());
            matrixStack.scale(viewModel.scaleOffX.getFloat(), viewModel.scaleOffY.getFloat(), viewModel.scaleOffZ.getFloat());
            matrixStack.multiply((Quaternionfc)RotationAxis.POSITIVE_X.rotationDegrees(viewModel.rotationOffX.getFloat()));
            matrixStack.multiply((Quaternionfc)RotationAxis.POSITIVE_Y.rotationDegrees(viewModel.rotationOffY.getFloat()));
            matrixStack.multiply((Quaternionfc)RotationAxis.POSITIVE_Z.rotationDegrees(viewModel.rotationOffZ.getFloat()));
        }
    }

    @Redirect(method={"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/math/RotationAxis;rotationDegrees(F)Lorg/joml/Quaternionf;"))
    private Quaternionf trollhack$noSway(RotationAxis rotationAxis, float f) {
        ViewModel viewModel = ViewModel.INSTANCE;
        if (viewModel != null && viewModel.isEnabled() && ((Boolean)viewModel.noSway.getValue()).booleanValue()) {
            return new Quaternionf();
        }
        return rotationAxis.rotationDegrees(f);
    }
}

