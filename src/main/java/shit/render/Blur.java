/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.nio.ByteBuffer;
import java.util.OptionalInt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.DynamicUniformStorage;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import shit.module.Module;
import shit.util.BufferUtil2;
import shit.util.MC;
import shit.util.RenderUtil4;
import shit.util.Util;

@Environment(value=EnvType.CLIENT)
public class Blur {
    private static final Identifier field37 = null;
    private static final Identifier field21 = null;
    private static final int count74 = 0;
    private RenderPipeline renderPipeline10;
    private RenderPipeline renderPipeline5;
    private Framebuffer field57;
    private static String[] texts5;

    private void m288() {
        Object var2_1 = null;
        if (this.renderPipeline10 == null) {
            this.renderPipeline10 = RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[]{RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET}).withLocation(BufferUtil2.m52("pipeline/blur")).withVertexShader(field37).withFragmentShader(field37).withUniform("BlurUniforms", UniformType.UNIFORM_BUFFER).withSampler("InputSampler").withBlend(BlendFunction.TRANSLUCENT).withCull(false).build();
        }
    }

    private void m716() {
        Object var2_1 = null;
        if (this.renderPipeline5 == null) {
            this.renderPipeline5 = RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET}).withLocation(BufferUtil2.m52("pipeline/blur_3d_box")).withVertexShader(field21).withFragmentShader(field21).withUniform("BoxBlurUniforms", UniformType.UNIFORM_BUFFER).withSampler("InputSampler").withBlend(BlendFunction.TRANSLUCENT).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).withDepthWrite(false).withCull(false).build();
        }
    }

    public void render(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        int n;
        int n2;
        GpuTextureView gpuTextureView;
        GpuTexture gpuTexture;
        float f10;
        float f11;
        float f12;
        float f13;
        float f14;
        float f15;
        float f16;
        float f17;
        float f18;
        block17: {
            block16: {
                f18 = f;
                f17 = f2;
                f16 = f3;
                f15 = f4;
                f14 = f5;
                f13 = f6;
                f12 = f7;
                f11 = f8;
                f10 = f9;
                Object var20_19 = null;
                if (MC.mc.currentScreen != null) {
                    return;
                }
                this.m288();
                if (f16 <= 0.0f || f15 <= 0.0f) {
                    return;
                }
                Framebuffer framebuffer = MC.mc.getFramebuffer();
                RenderUtil4.AutoCloseableImpl autoCloseableImpl = RenderUtil4.getAutoCloseableImpl2();
                gpuTexture = autoCloseableImpl == null ? framebuffer.getColorAttachment() : (GpuTexture)(Object)autoCloseableImpl.getGpuTextureView();
                gpuTextureView = autoCloseableImpl == null ? framebuffer.getColorAttachmentView() : autoCloseableImpl.getGpuTextureView();
                n2 = autoCloseableImpl == null ? framebuffer.textureWidth : autoCloseableImpl.getInt25();
                n = autoCloseableImpl == null ? framebuffer.textureHeight : autoCloseableImpl.getInt87();
                if (n2 <= 0 || n <= 0 || gpuTexture == null || gpuTextureView == null) {
                    return;
                }
                if (this.field57 == null) {
                    this.field57 = new SimpleFramebuffer("Lumin Blur Input", n2, n, false);
                }
                if (this.field57.textureWidth != n2) break block16;
                if (this.field57.textureHeight == n) break block17;
            }
            this.field57.resize(n2, n);
        }
        if (this.field57.getColorAttachment() == null || this.field57.getColorAttachmentView() == null) {
            return;
        }
        RenderUtil4.ColorData colorData = RenderUtil4.m13(f18, f17, f16, f15);
        if (!Util.hasPositiveArea(colorData)) {
            return;
        }
        float f19 = (float)RenderUtil4.getDouble18();
        float f20 = f18 * f19;
        float f21 = (float)n - (f17 + f15) * f19;
        float f22 = f16 * f19;
        float f23 = f15 * f19;
        float f24 = Math.max(0.0f, f14 * f19);
        float f25 = Math.max(0.0f, f13 * f19);
        float f26 = Math.max(0.0f, f12 * f19);
        float f27 = Math.max(0.0f, f11 * f19);
        float f28 = Math.max(0.0f, f10);
        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
        commandEncoder.copyTextureToTexture(gpuTexture, this.field57.getColorAttachment(), 0, 0, 0, 0, 0, n2, n);
        GpuBufferSlice gpuBufferSlice = RenderUtil4.m1027("blur_uniforms", "Lumin Blur UBO", count74, 16, new Vec11f(n2, n, f28, f22, f23, f20, f21, f24, f25, f26, f27));
        try (RenderPass renderPass = commandEncoder.createRenderPass(() -> "Lumin Blur", gpuTextureView, OptionalInt.empty());){
            renderPass.setPipeline(this.renderPipeline10);
            Util.enableScissorFromColorData(renderPass, colorData);
            RenderSystem.bindDefaultUniforms((RenderPass)renderPass);
            renderPass.setUniform("BlurUniforms", gpuBufferSlice);
            renderPass.bindTexture("InputSampler", this.field57.getColorAttachmentView(), RenderSystem.getSamplerCache().get(FilterMode.LINEAR));
            renderPass.draw(0, 3);
        }
    }

    public void m824(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f;
        float f8 = f2;
        float f9 = f3;
        float f10 = f4;
        float f11 = f5;
        float f12 = f6;
        this.render(f7, f8, f9, f10, f11, f11, f11, f11, f12);
    }

    private void m923(Object object, Object object2, Object object3) {
        BufferBuilder bufferBuilder = (BufferBuilder)object;
        Matrix4f matrix4f = (Matrix4f)object2;
        Box box = (Box)object3;
        Vec3d vec3d = MC.mc.gameRenderer.getCamera().getCameraPos();
        float f = (float)(box.minX - vec3d.x);
        float f2 = (float)(box.minY - vec3d.y);
        float f3 = (float)(box.minZ - vec3d.z);
        float f4 = (float)(box.maxX - vec3d.x);
        float f5 = (float)(box.maxY - vec3d.y);
        float f6 = (float)(box.maxZ - vec3d.z);
        this.m302(bufferBuilder, matrix4f, f, f2, f3);
        this.m302(bufferBuilder, matrix4f, f, f2, f6);
        this.m302(bufferBuilder, matrix4f, f4, f2, f6);
        this.m302(bufferBuilder, matrix4f, f4, f2, f3);
        this.m302(bufferBuilder, matrix4f, f, f5, f3);
        this.m302(bufferBuilder, matrix4f, f4, f5, f3);
        this.m302(bufferBuilder, matrix4f, f4, f5, f6);
        this.m302(bufferBuilder, matrix4f, f, f5, f6);
        this.m302(bufferBuilder, matrix4f, f, f2, f3);
        this.m302(bufferBuilder, matrix4f, f, f5, f3);
        this.m302(bufferBuilder, matrix4f, f4, f5, f3);
        this.m302(bufferBuilder, matrix4f, f4, f2, f3);
        this.m302(bufferBuilder, matrix4f, f4, f2, f3);
        this.m302(bufferBuilder, matrix4f, f4, f5, f3);
        this.m302(bufferBuilder, matrix4f, f4, f5, f6);
        this.m302(bufferBuilder, matrix4f, f4, f2, f6);
        this.m302(bufferBuilder, matrix4f, f, f2, f6);
        this.m302(bufferBuilder, matrix4f, f4, f2, f6);
        this.m302(bufferBuilder, matrix4f, f4, f5, f6);
        this.m302(bufferBuilder, matrix4f, f, f5, f6);
        this.m302(bufferBuilder, matrix4f, f, f2, f3);
        this.m302(bufferBuilder, matrix4f, f, f2, f6);
        this.m302(bufferBuilder, matrix4f, f, f5, f6);
        this.m302(bufferBuilder, matrix4f, f, f5, f3);
    }

    private void m302(Object object, Object object2, float f, float f2, float f3) {
        BufferBuilder bufferBuilder = (BufferBuilder)object;
        Matrix4f matrix4f = (Matrix4f)object2;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        bufferBuilder.vertex((Matrix4fc)matrix4f, f4, f5, f6).color(-1);
    }

    private static /* synthetic */ String cfrlam$render3DBox$1() {
        return "Lumin 3D Box Blur";
    }

    /*
     * Unable to fully structure code
     */
    static {}

    public static void setTextArray7(String[] stringArray) {
        texts5 = stringArray;
    }

    public static String[] getTextArray6() {
        return texts5;
    }

    @Environment(value=EnvType.CLIENT)
    static final class Vec3f 
    implements DynamicUniformStorage.Uploadable {
        private final float value11;
        private final float value12;
        private final float value13;

        private Vec3f(float f, float f2, float f3) {
            this.value11 = f;
            this.value12 = f2;
            this.value13 = f3;
        }

        public void write(ByteBuffer byteBuffer) {
            Std140Builder.intoBuffer((ByteBuffer)byteBuffer).putVec4(this.value11, this.value12, this.value13, 0.0f);
        }

        public float getFloat28() {
            return this.value11;
        }

        public float getFloat41() {
            return this.value12;
        }

        public float value13() {
            return this.value13;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Vec11f 
    implements DynamicUniformStorage.Uploadable {
        private final float value18;
        private final float value19;
        private final float value20;
        private final float value21;
        private final float value22;
        private final float value23;
        private final float value24;
        private final float value25;
        private final float value26;
        private final float value27;
        private final float value28;

        private Vec11f(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11) {
            this.value18 = f;
            this.value19 = f2;
            this.value20 = f3;
            this.value21 = f4;
            this.value22 = f5;
            this.value23 = f6;
            this.value24 = f7;
            this.value25 = f8;
            this.value26 = f9;
            this.value27 = f10;
            this.value28 = f11;
        }

        public void write(ByteBuffer byteBuffer) {
            block0: {
                Std140Builder.intoBuffer((ByteBuffer)byteBuffer).putVec3(this.value18, this.value19, this.value20).putVec4(this.value21, this.value22, this.value23, this.value24).putVec4(this.value25, this.value26, this.value27, this.value28);
                Object var2_2 = null;
                if (null == null) break block0;
                Module.setTextArray9(new String[2]);
            }
        }

        public float value18() {
            return this.value18;
        }

        public float value19() {
            return this.value19;
        }

        public float value20() {
            return this.value20;
        }

        public float value21() {
            return this.value21;
        }

        public float value22() {
            return this.value22;
        }

        public float getFloat43() {
            return this.value23;
        }

        public float getFloat16() {
            return this.value24;
        }

        public float value25() {
            return this.value25;
        }

        public float value26() {
            return this.value26;
        }

        public float getFloat27() {
            return this.value27;
        }

        public float value28() {
            return this.value28;
        }
    }
}

