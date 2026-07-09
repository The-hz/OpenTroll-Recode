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
import com.mojang.blaze3d.textures.GpuTextureView;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.DynamicUniformStorage;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import shit.module.Module;
import shit.render.Blur;
import shit.util.BufferUtil2;
import shit.util.MC;
import shit.util.RenderUtil4;

@Environment(value=EnvType.CLIENT)
public class GlslSandbox
implements AutoCloseable {
    private static final int count185 = 0;
    private final Map map25 = new HashMap();
    private long time68 = Util.getMeasuringTimeMs();

    private RenderPipeline getOrCreatePipeline(Object object) {
        Identifier identifier2 = (Identifier)object;
        return ((Map<Identifier, RenderPipeline>)this.map25).computeIfAbsent(identifier2, identifier -> RenderPipeline.builder((RenderPipeline.Snippet[])new RenderPipeline.Snippet[]{RenderPipelines.POST_EFFECT_PROCESSOR_SNIPPET}).withLocation(Identifier.of((String)identifier.getNamespace(), (String)("pipelines/glsl_sandbox/" + identifier.getPath().replace('/', '_')))).withVertexShader(Identifier.ofVanilla((String)"core/screenquad")).withFragmentShader(identifier).withUniform("GlslSandboxInfo", UniformType.UNIFORM_BUFFER).withCull(false).build());
    }

    public void render3(Object object, double d, double d2, long l) {
        int n;
        int n2;
        GpuTextureView gpuTextureView;
        long l2;
        double d3;
        double d4;
        Identifier identifier;
        block12: {
            block11: {
                identifier = (Identifier)object;
                d4 = d;
                d3 = d2;
                l2 = l;
                gpuTextureView = RenderUtil4.getGpuTextureView6();
                Object var16_10 = null;
                if (gpuTextureView == null) {
                    return;
                }
                RenderUtil4.AutoCloseableImpl autoCloseableImpl = RenderUtil4.getAutoCloseableImpl2();
                n2 = autoCloseableImpl != null ? autoCloseableImpl.getInt25() : MC.client3.getFramebuffer().textureWidth;
                n = autoCloseableImpl != null ? autoCloseableImpl.getInt87() : MC.client3.getFramebuffer().textureHeight;
                if (n2 <= 0) break block11;
                if (n > 0) break block12;
            }
            return;
        }
        float f = (float)n2 / RenderUtil4.getFloat57();
        float f2 = (float)n / RenderUtil4.getFloat53();
        float f3 = (float)d4 * f;
        float f4 = (float)d3 * f2;
        float f5 = f3 / (float)n2;
        float f6 = ((float)n - 1.0f - f4) / (float)n;
        float f7 = (float)(Util.getMeasuringTimeMs() - l2) / 1000.0f;
        GpuBufferSlice gpuBufferSlice = RenderUtil4.m1027("glsl_sandbox_info", "Lumin GLSL Sandbox UBO", count185, 4, new Vec7f(n2, n, f7, f5, f6, f3, f4));
        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
        try (RenderPass renderPass = commandEncoder.createRenderPass(() -> "Lumin GLSL Sandbox", gpuTextureView, OptionalInt.empty(), RenderUtil4.getGpuTextureView4(), OptionalDouble.empty());){
            renderPass.setPipeline(this.getOrCreatePipeline(identifier));
            RenderSystem.bindDefaultUniforms((RenderPass)renderPass);
            renderPass.setUniform("GlslSandboxInfo", gpuBufferSlice);
            renderPass.draw(0, 3);
        }
    }

    @Override
    public void close() {
        this.map25.clear();
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    static final class Vec7f 
    implements DynamicUniformStorage.Uploadable {
        private final float value70;
        private final float value71;
        private final float value72;
        private final float value73;
        private final float value74;
        private final float value75;
        private final float value76;

        private Vec7f(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            this.value70 = f;
            this.value71 = f2;
            this.value72 = f3;
            this.value73 = f4;
            this.value74 = f5;
            this.value75 = f6;
            this.value76 = f7;
        }

        public void write(ByteBuffer byteBuffer) {
            block0: {
                Std140Builder.intoBuffer((ByteBuffer)byteBuffer).putVec4(this.value70, this.value71, this.value72, 0.0f).putVec4(this.value73, this.value74, this.value75, this.value76);
                if (Module.getTextArray9() != null) break block0;
                Blur.setTextArray7(new String[1]);
            }
        }

        public float value70() {
            return this.value70;
        }

        public float getFloat31() {
            return this.value71;
        }

        public float value72() {
            return this.value72;
        }

        public float value73() {
            return this.value73;
        }

        public float getFloat29() {
            return this.value74;
        }

        public float getFloat18() {
            return this.value75;
        }

        public float value76() {
            return this.value76;
        }
    }
}

