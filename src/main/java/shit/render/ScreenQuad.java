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
import java.awt.Color;
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
import shit.util.BufferUtil2;
import shit.util.MC;
import shit.util.RenderUtil4;

@Environment(value=EnvType.CLIENT)
public class ScreenQuad {
    private static final int count68 = 0;
    private RenderPipeline renderPipeline24;
    private Framebuffer field36;

    private void m625() {
        Object var2_1 = null;
        if (this.renderPipeline24 == null) {
            this.renderPipeline24 = RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[]{RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET}).withLocation(BufferUtil2.m52("pipeline/filter")).withVertexShader(Identifier.ofVanilla((String)"core/screenquad")).withFragmentShader(BufferUtil2.m52("filter")).withUniform("FilterColor", UniformType.UNIFORM_BUFFER).withSampler("InputSampler").withCull(false).build();
        }
    }

    private void setObj97(Object object) {
        block5: {
            int n;
            int n2;
            block4: {
                Framebuffer framebuffer = (Framebuffer)object;
                n2 = framebuffer.textureWidth;
                n = framebuffer.textureHeight;
                Object var4_5 = null;
                if (this.field36 == null) {
                    this.field36 = new SimpleFramebuffer("Lumin Filter Input", n2, n, false);
                }
                if (this.field36.textureWidth != n2) break block4;
                if (this.field36.textureHeight == n) break block5;
            }
            this.field36.resize(n2, n);
        }
    }

    public void setObj3(Object object) {
        Color color = (Color)object;
        this.render2(MC.mc.getFramebuffer(), color);
    }

    public void render2(Object object, Object object2) {
        Color color;
        Framebuffer framebuffer;
        block17: {
            block16: {
                block15: {
                    block14: {
                        framebuffer = (Framebuffer)object;
                        color = (Color)object2;
                        this.m625();
                        Object var6_5 = null;
                        if (framebuffer == null || color == null) break block14;
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
        this.setObj97(framebuffer);
        if (this.field36.getColorAttachment() == null || this.field36.getColorAttachmentView() == null) {
            return;
        }
        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
        commandEncoder.copyTextureToTexture(framebuffer.getColorAttachment(), this.field36.getColorAttachment(), 0, 0, 0, 0, 0, framebuffer.textureWidth, framebuffer.textureHeight);
        GpuBufferSlice gpuBufferSlice = RenderUtil4.m1027("filter_color", "Lumin Filter UBO", count68, 4, new Vec4f(color));
        try (RenderPass renderPass = commandEncoder.createRenderPass(() -> "Lumin Filter", framebuffer.getColorAttachmentView(), OptionalInt.empty());){
            renderPass.setPipeline(this.renderPipeline24);
            RenderSystem.bindDefaultUniforms((RenderPass)renderPass);
            renderPass.setUniform("FilterColor", gpuBufferSlice);
            renderPass.bindTexture("InputSampler", this.field36.getColorAttachmentView(), RenderSystem.getSamplerCache().get(FilterMode.LINEAR));
            renderPass.draw(0, 3);
        }
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    static final class Vec4f 
    implements DynamicUniformStorage.Uploadable {
        private final float value34;
        private final float value35;
        private final float value36;
        private final float value37;

        private Vec4f(Color color) {
            this((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
        }

        private Vec4f(float f, float f2, float f3, float f4) {
            this.value34 = f;
            this.value35 = f2;
            this.value36 = f3;
            this.value37 = f4;
        }

        public void write(ByteBuffer byteBuffer) {
            Std140Builder.intoBuffer((ByteBuffer)byteBuffer).putVec4(this.value34, this.value35, this.value36, this.value37);
        }

        public float value34() {
            return this.value34;
        }

        public float value35() {
            return this.value35;
        }

        public float value36() {
            return this.value36;
        }

        public float getFloat34() {
            return this.value37;
        }
    }
}

