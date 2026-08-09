/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.command.OrderedRenderCommandQueueImpl;
import net.minecraft.client.render.command.RenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;
import shit.util.BufferUtil2;
import shit.util.MC;
import shit.util.RenderPipelines;

@Environment(value=EnvType.CLIENT)
public final class LineRenderer
implements MC {
    private static final MatrixStack matrixStack = new MatrixStack();
    private static final OrderedRenderCommandQueueImpl field55 = new OrderedRenderCommandQueueImpl();
    private static final RenderHelper3 renderHelper3 = new RenderHelper3();
    private static final RenderDispatcher field38 = null;
    private static final RenderPipeline renderPipeline22 = null;
    private static final RenderPipeline renderPipeline20 = null;
    private static Color color27;
    private static Color color26;
    private static float value125;
    private static RenderPipelines.ImmediateRendererHolder2 immediateRendererHolder2;
    private static RenderPipelines.RenderUtil renderUtil;
    private static Matrix4f matrix4f4;
    private static MatrixStack.Entry field54;
    private static double value160;
    private static double value150;
    private static double value161;

    private LineRenderer() {
    }

    public static void setObj90(Object object) {
        MatrixStack matrixStack = (MatrixStack)object;
        Object var3_2 = null;
        if (LineRenderer.isSet167()) {
            throw new IllegalStateException("Wireframe entity renderer is already batching");
        }
        LineRenderer.setObj96(matrixStack);
    }

    public static void m311() {
        if (!LineRenderer.isSet167()) {
            return;
        }
        LineRenderer.m647();
    }

    private static void m285(Object object, double d, Object object2, Object object3, float f) {
        Entity entity = (Entity)object;
        double d2 = d;
        Color color = (Color)object2;
        Color color2 = (Color)object3;
        float f2 = f;
        color27 = color;
        color26 = color2;
        Object var13_10 = null;
        value125 = f2;
        float f3 = entity.getEntityWorld().getTickManager().isFrozen() ? 1.0f : MC.mc.getRenderTickCounter().getTickProgress(true);
        value160 = MathHelper.lerp((double)f3, (double)entity.lastRenderX, (double)entity.getX());
        value150 = MathHelper.lerp((double)f3, (double)entity.lastRenderY, (double)entity.getY());
        value161 = MathHelper.lerp((double)f3, (double)entity.lastRenderZ, (double)entity.getZ());
        EntityRenderer entityRenderer = MC.mc.getEntityRenderDispatcher().getRenderer(entity);
        EntityRenderState entityRenderState = entityRenderer.getAndUpdateRenderState(entity, f3);
        Vec3d vec3d = entityRenderer.getPositionOffset(entityRenderState);
        value160 += vec3d.x;
        value150 += vec3d.y;
        value161 += vec3d.z;
        matrixStack.push();
        matrixStack.scale((float)d2, (float)d2, (float)d2);
        CameraRenderState cameraRenderState = MC.mc.gameRenderer.getEntityRenderStates().cameraRenderState;
        entityRenderer.render(entityRenderState, matrixStack, (OrderedRenderCommandQueue)field55, cameraRenderState);
        matrixStack.pop();
        field38.render();
        field55.onNextFrame();
        field38.endLayeredCustoms();
        LineRenderer.m647();
        renderHelper3.m1041();
    }

    private static boolean isSet167() {
        return immediateRendererHolder2 != null && renderUtil != null;
    }

    private static void setObj96(Object object) {
        MatrixStack matrixStack = (MatrixStack)object;
        MatrixStack.Entry entry = matrixStack.peek();
        matrix4f4 = entry.getPositionMatrix();
        field54 = entry;
        immediateRendererHolder2 = shit.util.RenderPipelines.m407(renderPipeline22);
        renderUtil = shit.util.RenderPipelines.m90(renderPipeline20);
    }

    private static void m647() {
        Object var1 = null;
        if (immediateRendererHolder2 != null) {
            immediateRendererHolder2.m512();
        }
        if (renderUtil != null) {
            renderUtil.m48();
        }
        immediateRendererHolder2 = null;
        renderUtil = null;
        matrix4f4 = null;
        field54 = null;
    }

    private static void m115(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12) {
        block1: {
            float f13 = f;
            float f14 = f2;
            float f15 = f3;
            float f16 = f4;
            float f17 = f5;
            float f18 = f6;
            float f19 = f7;
            float f20 = f8;
            float f21 = f9;
            float f22 = f10;
            float f23 = f11;
            float f24 = f12;
            Vec3d vec3d = MC.mc.getEntityRenderDispatcher().camera.getCameraPos();
            float f25 = (float)(value160 + (double)f13 - vec3d.x);
            float f26 = (float)(value150 + (double)f14 - vec3d.y);
            float f27 = (float)(value161 + (double)f15 - vec3d.z);
            Object var25_28 = null;
            float f28 = (float)(value160 + (double)f16 - vec3d.x);
            float f29 = (float)(value150 + (double)f17 - vec3d.y);
            float f30 = (float)(value161 + (double)f18 - vec3d.z);
            float f31 = (float)(value160 + (double)f19 - vec3d.x);
            float f32 = (float)(value150 + (double)f20 - vec3d.y);
            float f33 = (float)(value161 + (double)f21 - vec3d.z);
            float f34 = (float)(value160 + (double)f22 - vec3d.x);
            float f35 = (float)(value150 + (double)f23 - vec3d.y);
            float f36 = (float)(value161 + (double)f24 - vec3d.z);
            if (immediateRendererHolder2 != null) {
                immediateRendererHolder2.m530(matrix4f4, f25, f26, f27, color27.getRGB());
                immediateRendererHolder2.m530(matrix4f4, f28, f29, f30, color27.getRGB());
                immediateRendererHolder2.m530(matrix4f4, f31, f32, f33, color27.getRGB());
                immediateRendererHolder2.m530(matrix4f4, f34, f35, f36, color27.getRGB());
            }
            if (renderUtil == null) break block1;
            LineRenderer.m214(f25, f26, f27, f28, f29, f30);
            LineRenderer.m214(f28, f29, f30, f31, f32, f33);
            LineRenderer.m214(f31, f32, f33, f34, f35, f36);
            LineRenderer.m214(f34, f35, f36, f25, f26, f27);
        }
    }

    private static void m214(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f;
        float f8 = f2;
        float f9 = f3;
        float f10 = f4;
        float f11 = f5;
        float f12 = f6;
        Vector3f vector3f = LineRenderer.m403(f7, f8, f9, f10, f11, f12);
        renderUtil.m439(matrix4f4, field54, f7, f8, f9, color26.getRGB(), vector3f.x, vector3f.y, vector3f.z, value125);
        renderUtil.m439(matrix4f4, field54, f10, f11, f12, color26.getRGB(), vector3f.x, vector3f.y, vector3f.z, value125);
    }

    private static Vector3f m403(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f4;
        float f8 = f;
        float f9 = f7 - f8;
        float f10 = f5;
        float f11 = f2;
        float f12 = f10 - f11;
        float f13 = f6;
        float f14 = f3;
        float f15 = f13 - f14;
        float f16 = MathHelper.sqrt((float)(f9 * f9 + f12 * f12 + f15 * f15));
        if (f16 <= 1.0E-5f) {
            return new Vector3f(0.0f, 1.0f, 0.0f);
        }
        return new Vector3f(f9 / f16, f12 / f16, f15 / f16);
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    static final class RenderHelper2
    extends VertexConsumerProvider.Immediate {
        private static final RenderHelper2 renderHelper2 = new RenderHelper2();

        private RenderHelper2() {
            super(null, null);
        }

        public @NonNull VertexConsumer getBuffer(RenderLayer renderLayer) {
            return VertexConsumerImpl2.vertexConsumerImpl2;
        }

        public void draw() {
        }

        public void draw(@NonNull RenderLayer renderLayer) {
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class RenderHelper3
    extends VertexConsumerProvider.Immediate {
        private final Object2ObjectOpenHashMap object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();

        private RenderHelper3() {
            super(null, null);
        }

        public @NonNull VertexConsumer getBuffer(RenderLayer renderLayer) {
            return (VertexConsumer)this.object2ObjectOpenHashMap.computeIfAbsent((Object)renderLayer, object -> new VertexConsumerImpl());
        }

        public void draw() {
        }

        public void draw(@NonNull RenderLayer renderLayer) {
        }

        private void m1041() {
            ObjectIterator objectIterator = this.object2ObjectOpenHashMap.values().iterator();
            Object var2_2 = null;
            while (objectIterator.hasNext()) {
                VertexConsumerImpl vertexConsumerImpl = (VertexConsumerImpl)objectIterator.next();
                vertexConsumerImpl.m521();
                if (null == null) continue;
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class VertexConsumerImpl2
    implements VertexConsumer {
        private static final VertexConsumerImpl2 vertexConsumerImpl2 = new VertexConsumerImpl2();

        private VertexConsumerImpl2() {
        }

        public @NonNull VertexConsumer vertex(float f, float f2, float f3) {
            return this;
        }

        public @NonNull VertexConsumer color(int n, int n2, int n3, int n4) {
            return this;
        }

        public @NonNull VertexConsumer color(int n) {
            return this;
        }

        public @NonNull VertexConsumer texture(float f, float f2) {
            return this;
        }

        public @NonNull VertexConsumer overlay(int n, int n2) {
            return this;
        }

        public @NonNull VertexConsumer light(int n, int n2) {
            return this;
        }

        public @NonNull VertexConsumer normal(float f, float f2, float f3) {
            return this;
        }

        public @NonNull VertexConsumer lineWidth(float f) {
            return this;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class VertexConsumerImpl
    implements VertexConsumer {
        private final float[] values = new float[4];
        private final float[] values3 = new float[4];
        private final float[] values5 = new float[4];
        private int count77;

        private VertexConsumerImpl() {
        }

        public @NonNull VertexConsumer vertex(float f, float f2, float f3) {
            block0: {
                this.values[this.count77] = f;
                this.values3[this.count77] = f2;
                this.values5[this.count77] = f3;
                ++this.count77;
                Object var4_4 = null;
                if (this.count77 != 4) break block0;
                LineRenderer.m115(this.values[0], this.values3[0], this.values5[0], this.values[1], this.values3[1], this.values5[1], this.values[2], this.values3[2], this.values5[2], this.values[3], this.values3[3], this.values5[3]);
                this.count77 = 0;
            }
            return this;
        }

        public @NonNull VertexConsumer color(int n, int n2, int n3, int n4) {
            return this;
        }

        public @NonNull VertexConsumer color(int n) {
            return this;
        }

        public @NonNull VertexConsumer texture(float f, float f2) {
            return this;
        }

        public @NonNull VertexConsumer overlay(int n, int n2) {
            return this;
        }

        public @NonNull VertexConsumer light(int n, int n2) {
            return this;
        }

        public @NonNull VertexConsumer normal(float f, float f2, float f3) {
            return this;
        }

        public @NonNull VertexConsumer lineWidth(float f) {
            return this;
        }

        private void m521() {
            this.count77 = 0;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class RenderHelper
    extends OutlineVertexConsumerProvider {
        private static final RenderHelper renderHelper = new RenderHelper();

        private RenderHelper() {
        }

        public @NonNull VertexConsumer getBuffer(@NonNull RenderLayer renderLayer) {
            return VertexConsumerImpl2.vertexConsumerImpl2;
        }

        public void draw() {
        }
    }
}

