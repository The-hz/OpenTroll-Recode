/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import java.nio.ByteBuffer;
import java.util.OptionalInt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.DynamicUniformStorage;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gl.UniformType;
import net.minecraft.util.Identifier;
import shit.util.ResourceLoader;
import shit.util.MC;
import shit.util.GpuPipelineFactory;

@Environment(value=EnvType.CLIENT)
public class Fxaa {
    private static final Identifier field19 = null;
    private static final Identifier field15 = null;
    private static final int count129 = 0;
    private RenderPipeline renderPipeline4;
    private Framebuffer field31;

    private void m1030() {
        Object var2_1 = null;
        if (this.renderPipeline4 == null) {
            this.renderPipeline4 = RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[]{RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET}).withLocation(ResourceLoader.m52("pipeline/fxaa")).withVertexShader(field19).withFragmentShader(field15).withUniform("FxaaInfo", UniformType.UNIFORM_BUFFER).withSampler("InputSampler").withCull(false).build();
        }
    }

    private void setObj57(Object object) {
        block5: {
            int n;
            int n2;
            block4: {
                Framebuffer framebuffer = (Framebuffer)object;
                n2 = framebuffer.textureWidth;
                n = framebuffer.textureHeight;
                Object var4_5 = null;
                if (this.field31 == null) {
                    this.field31 = new SimpleFramebuffer("Lumin FXAA Input", n2, n, false);
                }
                if (this.field31.textureWidth != n2) break block4;
                if (this.field31.textureHeight == n) break block5;
            }
            this.field31.resize(n2, n);
        }
    }

    public void m869() {
        this.render5(MC.mc.getFramebuffer());
    }

    public void render5(Object object) {
        Framebuffer framebuffer;
        block17: {
            block16: {
                block15: {
                    block14: {
                        framebuffer = (Framebuffer)object;
                        this.m1030();
                        Object var4_3 = null;
                        if (framebuffer == null) break block14;
                        if (framebuffer.textureWidth <= 0) break block14;
                        if (framebuffer.textureHeight > 0) break block15;
                    }
                    return;
                }
                if (framebuffer.getColorAttachment() == null) break block16;
                if (framebuffer.getColorAttachmentView() != null) break block17;
            }
            return;
        }
        this.setObj57(framebuffer);
        if (this.field31.getColorAttachment() == null || this.field31.getColorAttachmentView() == null) {
            return;
        }
        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
        commandEncoder.copyTextureToTexture(framebuffer.getColorAttachment(), this.field31.getColorAttachment(), 0, 0, 0, 0, 0, framebuffer.textureWidth, framebuffer.textureHeight);
        GpuBufferSlice gpuBufferSlice = GpuPipelineFactory.m1027("fxaa_info", "Lumin FXAA UBO", count129, 4, new Vec2f(framebuffer.textureWidth, framebuffer.textureHeight));
        try (RenderPass renderPass = commandEncoder.createRenderPass(() -> "Lumin FXAA", framebuffer.getColorAttachmentView(), OptionalInt.empty());){
            renderPass.setPipeline(this.renderPipeline4);
            RenderSystem.bindDefaultUniforms((RenderPass)renderPass);
            renderPass.setUniform("FxaaInfo", gpuBufferSlice);
            renderPass.bindTexture("InputSampler", this.field31.getColorAttachmentView(), RenderSystem.getSamplerCache().get(FilterMode.LINEAR));
            renderPass.draw(0, 3);
        }
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    static final class Vec2f 
    implements DynamicUniformStorage.Uploadable {
        private final float value51;
        private final float value52;

        private Vec2f(float f, float f2) {
            this.value51 = f;
            this.value52 = f2;
        }

        public void write(ByteBuffer byteBuffer) {
            Std140Builder.intoBuffer((ByteBuffer)byteBuffer).putVec4(this.value51, this.value52, 1.0f / this.value51, 1.0f / this.value52);
        }

        public float getFloat5() {
            return this.value51;
        }

        public float value52() {
            return this.value52;
        }
    }
}

