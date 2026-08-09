/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.lang.invoke.LambdaMetafactory;
import java.nio.ByteBuffer;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TextureTransform;
import net.minecraft.util.math.ColorHelper;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;
import shit.api.Listener2;
import shit.manager.GpuManager;
import shit.manager.Manager4;
import shit.misc.RenderPipelines;
import shit.module.Module;
import shit.render.TextureRenderer;
import shit.util.RenderUtil4;
import shit.util.Util;

@Environment(value=EnvType.CLIENT)
public class TriangleRenderer
implements Listener2 {
    private final GpuManager gpuManager3 = new GpuManager(16384L, 32);
    private long time55 = 0L;
    private int count67 = 0;
    private boolean flag110 = false;
    private int count130;
    private int count86;
    private int count96;
    private int count222;
    private GpuBufferSlice gpuBufferSlice6;

    private TriangleRenderer() {
    }

    public static TriangleRenderer getTriangleRenderer2() {
        return (TriangleRenderer)Manager4.manager4.addListener(new TriangleRenderer());
    }

    public void m1056(float f, float f2, float f3, float f4, Object object) {
        block0: {
            float f5 = f;
            float f6 = f2;
            float f7 = f3;
            float f8 = f4;
            Color color = (Color)object;
            this.gpuManager3.setLong(this.time55 + 48L);
            this.gpuManager3.ensureMapped();
            int n = ColorHelper.toAbgr((int)color.getRGB());
            float f9 = Math.clamp(f8, 0.0f, 1.0f);
            float f10 = f5 + f7;
            float f11 = f6;
            float f12 = f5 - f7;
            float f13 = f6 - f7;
            float f14 = f5 - f7;
            float f15 = f6 + f7;
            float f16 = f5;
            float f17 = f6 + f7;
            float f18 = f5 - f7;
            float f19 = f6 - f7;
            float f20 = f5 + f7;
            float f21 = f6 - f7;
            float f22 = f12 + (f18 - f12) * f9;
            float f23 = f13 + (f19 - f13) * f9;
            float f24 = f14 + (f20 - f14) * f9;
            String[] stringArray = TextureRenderer.getTextArray3();
            float f25 = f15 + (f21 - f15) * f9;
            float f26 = f10 + (f16 - f10) * f9;
            float f27 = f11 + (f17 - f11) * f9;
            this.m1047(f22, f23, n);
            String[] stringArray2 = stringArray;
            this.m1047(f24, f25, n);
            this.m1047(f26, f27, n);
            if (stringArray2 != null) break block0;
            Module.setTextArray9(new String[3]);
        }
    }

    private void m1047(float f, float f2, int n) {
        float f3 = f;
        float f4 = f2;
        int n2 = n;
        long l = MemoryUtil.memAddress((ByteBuffer)this.gpuManager3.getByteBuffer());
        long l2 = l + this.time55;
        MemoryUtil.memPutFloat((long)l2, (float)f3);
        MemoryUtil.memPutFloat((long)(l2 + 4L), (float)f4);
        MemoryUtil.memPutFloat((long)(l2 + 8L), (float)0.0f);
        MemoryUtil.memPutInt((long)(l2 + 12L), (int)n2);
        this.time55 += 16L;
        ++this.count67;
    }

    public void m670(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        RenderUtil4.ColorData colorData = Util.m1033(n5, n6, n7, n8);
        this.flag110 = true;
        this.count130 = colorData.count30();
        this.count86 = colorData.count31();
        this.count96 = colorData.count32();
        this.count222 = colorData.count33();
    }

    public void m941() {
        this.flag110 = false;
    }

    @Override
    public void draw2() {
        if (this.count67 == 0) {
            return;
        }
        if (this.gpuManager3.isMapped()) {
            this.gpuManager3.unmap();
        }
        RenderUtil4.m486();
        GpuTextureView view = RenderUtil4.getGpuTextureView6();
        GpuTextureView view2 = RenderUtil4.getGpuTextureView4();
        if (view == null) {
            return;
        }
        if (this.flag110 && !Util.isPositiveArea(this.count96, this.count222)) {
            return;
        }
        GpuBufferSlice slice = RenderUtil4.m998(
                RenderSystem.getModelViewMatrix(),
                new Vector4f(1.0F, 1.0F, 1.0F, 1.0F),
                new Vector3f(0.0F, 0.0F, 0.0F),
                TextureTransform.DEFAULT_TEXTURING.getTransformSupplier());
        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                () -> "Triangle Draw", view, OptionalInt.empty(), view2, OptionalDouble.empty())) {
            renderPass.setPipeline(RenderPipelines.renderPipeline9);
            if (this.flag110) {
                Util.enableScissor(renderPass, this.count130, this.count86, this.count96, this.count222);
            }
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", slice);
            this.setObj80(renderPass);
        }
    }

    @Override
    public boolean prepareBuffers() {
        this.gpuBufferSlice6 = null;
        if (this.count67 == 0) {
            return false;
        }
        if (this.gpuManager3.isMapped()) {
            this.gpuManager3.unmap();
        }
        if (this.flag110 && !Util.isPositiveArea(this.count96, this.count222)) {
            return false;
        }
        this.gpuBufferSlice6 = RenderUtil4.getGpuBufferSlice2();
        return this.gpuBufferSlice6 != null;
    }

    @Override
    public void drawWithPass(Object object) {
        RenderPass renderPass = (RenderPass)object;
        String[] stringArray = TextureRenderer.getTextArray3();
        TriangleRenderer triangleRenderer = this;
        if (stringArray != null) {
            if (triangleRenderer.gpuBufferSlice6 == null) {
                return;
            }
            renderPass.setUniform("DynamicTransforms", this.gpuBufferSlice6);
            triangleRenderer = this;
        }
        triangleRenderer.setObj80(renderPass);
    }

    private void setObj80(Object object) {
        int n;
        int n2;
        RenderPass renderPass;
        block3: {
            RenderPass renderPass2;
            block4: {
                block2: {
                    boolean bl;
                    block1: {
                        renderPass2 = (RenderPass)object;
                        String[] stringArray = TextureRenderer.getTextArray3();
                        bl = this.flag110;
                        if (stringArray == null) break block1;
                        if (!bl) break block2;
                        renderPass = renderPass2;
                        n2 = this.count130;
                        n = this.count86;
                        if (stringArray == null) break block3;
                        bl = Util.enableScissor(renderPass, n2, n, this.count96, this.count222);
                    }
                    if (!bl) {
                        return;
                    }
                    break block4;
                }
                renderPass2.disableScissor();
            }
            renderPass2.setVertexBuffer(0, this.gpuManager3.getGpuBuffer());
            renderPass = renderPass2;
            n2 = 0;
            n = this.count67;
        }
        renderPass.draw(n2, n);
    }

    @Override
    public void endFrame() {
        String[] stringArray = TextureRenderer.getTextArray3();
        TriangleRenderer triangleRenderer = this;
        if (stringArray != null) {
            if (triangleRenderer.count67 > 0) {
                GpuManager gpuManager = this.gpuManager3;
                if (stringArray != null) {
                    if (gpuManager.isMapped()) {
                        this.gpuManager3.unmap();
                    }
                    gpuManager = this.gpuManager3;
                }
                gpuManager.advanceBuffer();
            }
            this.count67 = 0;
            this.time55 = 0L;
            triangleRenderer = this;
        }
        triangleRenderer.gpuBufferSlice6 = null;
    }

    @Override
    public void close() {
        this.endFrame();
        this.gpuManager3.flush();
        Manager4.manager4.removeListener(this);
    }

    private static /* synthetic */ String cfrlam$draw$0() {
        return "Triangle Draw";
    }
}

