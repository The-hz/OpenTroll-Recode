/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import net.minecraft.client.render.entity.state.EndCrystalEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.module.combat.CrystalChams;
import shit.module.render.Chams;
import shit.render.EspRenderLayers;

@Environment(value=EnvType.CLIENT)
@Mixin(value={ModelCommandRenderer.class})
public class ModelFeatureRendererChamsMixin {
    @Shadow
    @Final
    private MatrixStack matrices;
    private static RenderLayer fillThrough;
    private static RenderLayer linesThrough;

    @Inject(method={"render(Lnet/minecraft/client/render/command/OrderedRenderCommandQueueImpl$ModelCommand;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/render/OutlineVertexConsumerProvider;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void m900(OrderedRenderCommandQueueImpl.ModelCommand modelCommand, RenderLayer renderLayer, VertexConsumer vertexConsumer, OutlineVertexConsumerProvider outlineVertexConsumerProvider, VertexConsumerProvider.Immediate immediate, CallbackInfo callbackInfo) {
        Object object = modelCommand.state();
        Chams chams = Chams.INSTANCE;
        if (chams != null && chams.m681(object) && chams.m703(object, modelCommand.model())) {
            this.renderChamsGeometry(modelCommand, immediate, chams.isSet15(), chams.isSet96(), (Boolean)chams.throughWall.getObj(), chams.getInt30(), chams.getInt83(), chams.getFloat60(), false);
            if (chams.isSet171()) {
                callbackInfo.cancel();
            }
            return;
        }
        CrystalChams crystalChams = CrystalChams.INSTANCE;
        if (crystalChams != null && crystalChams.m177(object) && crystalChams.m483(object, modelCommand.model())) {
            this.renderChamsGeometry(modelCommand, immediate, crystalChams.isSet55(), crystalChams.isSet31(), crystalChams.isSet83(), crystalChams.getInt55(), crystalChams.getInt3(), crystalChams.getFloat45(), true);
            if (crystalChams.isSet47()) {
                callbackInfo.cancel();
            }
        }
    }

    private void renderChamsGeometry(OrderedRenderCommandQueueImpl.ModelCommand modelCommand, VertexConsumerProvider.Immediate immediate, boolean bl, boolean bl2, boolean bl3, int n, int n2, float f, boolean bl4) {
        EndCrystalEntityRenderState endCrystalEntityRenderState;
        Model model = modelCommand.model();
        this.matrices.push();
        this.matrices.peek().copy(modelCommand.matricesEntry());
        boolean bl5 = false;
        boolean bl6 = false;
        Object object = modelCommand.state();
        if (object instanceof EndCrystalEntityRenderState) {
            endCrystalEntityRenderState = (EndCrystalEntityRenderState)object;
            bl5 = true;
            bl6 = endCrystalEntityRenderState.baseVisible;
            endCrystalEntityRenderState.baseVisible = false;
        }
        model.setAngles(modelCommand.state());
        if (bl) {
            this.renderFilledModel(model, immediate.getBuffer(ModelFeatureRendererChamsMixin.fillType(bl3)), n, bl4);
        }
        if (bl2) {
            this.renderWireframe(model, immediate.getBuffer(ModelFeatureRendererChamsMixin.linesType(bl3)), n2, f, bl4);
        }
        if (bl5 && (object = modelCommand.state()) instanceof EndCrystalEntityRenderState) {
            endCrystalEntityRenderState = (EndCrystalEntityRenderState)object;
            endCrystalEntityRenderState.baseVisible = bl6;
        }
        this.matrices.pop();
    }

    private void renderFilledModel(Model model, VertexConsumer vertexConsumer, int n, boolean bl) {
        if (ColorHelper.getAlpha((int)n) <= 0) {
            return;
        }
        model.getRootPart().forEachCuboid(this.matrices, (entry, string, n2, cuboid) -> {
            if (bl && ModelFeatureRendererChamsMixin.isCrystalBase(string)) {
                return;
            }
            if (ModelFeatureRendererChamsMixin.isPlayerOuterLayer(string)) {
                return;
            }
            for (ModelPart.Quad quad : cuboid.sides) {
                ModelPart.Vertex[] vertexArray;
                for (ModelPart.Vertex vertex : vertexArray = quad.vertices()) {
                    vertexConsumer.vertex(entry, vertex.worldX(), vertex.worldY(), vertex.worldZ()).color(n);
                }
            }
        });
    }

    private void renderWireframe(Model model, VertexConsumer vertexConsumer, int n, float f, boolean bl) {
        if (ColorHelper.getAlpha((int)n) <= 0) {
            return;
        }
        model.getRootPart().forEachCuboid(this.matrices, (entry, string, n2, cuboid) -> {
            if (bl && ModelFeatureRendererChamsMixin.isCrystalBase(string)) {
                return;
            }
            if (ModelFeatureRendererChamsMixin.isPlayerOuterLayer(string)) {
                return;
            }
            for (ModelPart.Quad quad : cuboid.sides) {
                ModelPart.Vertex[] vertexArray = quad.vertices();
                for (int i = 0; i < vertexArray.length; ++i) {
                    ModelPart.Vertex vertex = vertexArray[i];
                    ModelPart.Vertex vertex2 = vertexArray[(i + 1) % vertexArray.length];
                    this.emitLine(vertexConsumer, entry, vertex, vertex2, n, f);
                }
            }
        });
    }

    private void emitLine(VertexConsumer vertexConsumer, MatrixStack.Entry entry, ModelPart.Vertex vertex, ModelPart.Vertex vertex2, int n, float f) {
        float f2;
        float f3;
        float f4 = vertex2.worldX() - vertex.worldX();
        float f5 = (float)Math.sqrt(f4 * f4 + (f3 = vertex2.worldY() - vertex.worldY()) * f3 + (f2 = vertex2.worldZ() - vertex.worldZ()) * f2);
        if (f5 == 0.0f) {
            f5 = 1.0f;
        }
        vertexConsumer.vertex(entry, vertex.worldX(), vertex.worldY(), vertex.worldZ()).color(n).normal(entry, f4 /= f5, f3 /= f5, f2 /= f5).lineWidth(f);
        vertexConsumer.vertex(entry, vertex2.worldX(), vertex2.worldY(), vertex2.worldZ()).color(n).normal(entry, f4, f3, f2).lineWidth(f);
    }

    private static RenderLayer fillType(boolean bl) {
        if (!bl) {
            return RenderLayers.debugQuads();
        }
        if (fillThrough == null) {
            Object[] objectArray = new Object[2];
            objectArray[1] = "chams_fill_through";
            objectArray[0] = RenderLayers.debugQuads();
            Object[] objectArray2 = objectArray;
            fillThrough = EspRenderLayers.m373(objectArray2[0], objectArray2[1]);
        }
        return fillThrough;
    }

    private static RenderLayer linesType(boolean bl) {
        if (!bl) {
            return RenderLayers.lines();
        }
        if (linesThrough == null) {
            Object[] objectArray = new Object[2];
            objectArray[1] = "chams_lines_through";
            objectArray[0] = RenderLayers.lines();
            Object[] objectArray2 = objectArray;
            linesThrough = EspRenderLayers.m373(objectArray2[0], objectArray2[1]);
        }
        return linesThrough;
    }

    private static boolean isPlayerOuterLayer(String string) {
        String string2 = string.toLowerCase(Locale.ROOT);
        return string2.contains("hat") || string2.contains("jacket") || string2.contains("sleeve") || string2.contains("pants");
    }

    private static boolean isCrystalBase(String string) {
        return string.toLowerCase(Locale.ROOT).contains("base");
    }
}

