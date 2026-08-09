/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

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
import net.minecraft.util.math.ColorHelper;
import org.lwjgl.system.MemoryUtil;
import shit.api.FrameListener;
import shit.manager.GpuManager;
import shit.manager.FrameListenerManager;
import shit.misc.RenderPipelines;
import shit.render.TextureRenderer;
import shit.util.GpuPipelineFactory;
import shit.util.Util;

@Environment(value=EnvType.CLIENT)
public class RoundRectRenderer
implements FrameListener {
    private final GpuManager gpuManager5 = new GpuManager(16384L, 32);
    private boolean flag124 = false;
    private int count91;
    private int count47;
    private int count128;
    private int count219;
    private long time62 = 0L;
    private int count124 = 0;
    private GpuPipelineFactory.Data data5;

    private RoundRectRenderer() {
    }

    public static RoundRectRenderer getRoundRectRenderer2() {
        return (RoundRectRenderer)FrameListenerManager.manager4.addListener(new RoundRectRenderer());
    }

    public void m293(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Object object) {
        float f9 = f;
        float f10 = f2;
        float f11 = f3;
        float f12 = f4;
        float f13 = f5;
        float f14 = f6;
        float f15 = f7;
        float f16 = f8;
        Color color = (Color)object;
        this.m756(f9, f10, f11, f12, f13, f14, f15, f16, color, color, color, color);
    }

    public void m756(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Object object, Object object2, Object object3, Object object4) {
        float f9 = f;
        float f10 = f2;
        float f11 = f3;
        float f12 = f4;
        float f13 = f5;
        float f14 = f6;
        float f15 = f7;
        float f16 = f8;
        Color color = (Color)object;
        Color color2 = (Color)object2;
        Color color3 = (Color)object3;
        Color color4 = (Color)object4;
        this.gpuManager5.setLong(this.time62 + 192L);
        this.gpuManager5.ensureMapped();
        float f17 = f9 + f11;
        float f18 = f10 + f12;
        int n = ColorHelper.toAbgr((int)color.getRGB());
        int n2 = ColorHelper.toAbgr((int)color2.getRGB());
        int n3 = ColorHelper.toAbgr((int)color3.getRGB());
        int n4 = ColorHelper.toAbgr((int)color4.getRGB());
        this.m591(f9, f10, f9, f10, f17, f18, f13, f14, f15, f16, n);
        this.m591(f9, f18, f9, f10, f17, f18, f13, f14, f15, f16, n2);
        this.m591(f17, f18, f9, f10, f17, f18, f13, f14, f15, f16, n3);
        this.m591(f17, f10, f9, f10, f17, f18, f13, f14, f15, f16, n4);
    }

    private void m591(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, int n) {
        float f11 = f;
        float f12 = f2;
        float f13 = f3;
        float f14 = f4;
        float f15 = f5;
        float f16 = f6;
        float f17 = f7;
        float f18 = f8;
        float f19 = f9;
        float f20 = f10;
        int n2 = n;
        long l = MemoryUtil.memAddress((ByteBuffer)this.gpuManager5.getByteBuffer());
        long l2 = l + this.time62;
        MemoryUtil.memPutFloat((long)l2, (float)f11);
        MemoryUtil.memPutFloat((long)(l2 + 4L), (float)f12);
        MemoryUtil.memPutFloat((long)(l2 + 8L), (float)0.0f);
        MemoryUtil.memPutInt((long)(l2 + 12L), (int)n2);
        MemoryUtil.memPutFloat((long)(l2 + 16L), (float)f13);
        MemoryUtil.memPutFloat((long)(l2 + 20L), (float)f14);
        MemoryUtil.memPutFloat((long)(l2 + 24L), (float)f15);
        MemoryUtil.memPutFloat((long)(l2 + 28L), (float)f16);
        MemoryUtil.memPutFloat((long)(l2 + 32L), (float)f17);
        MemoryUtil.memPutFloat((long)(l2 + 36L), (float)f18);
        MemoryUtil.memPutFloat((long)(l2 + 40L), (float)f19);
        MemoryUtil.memPutFloat((long)(l2 + 44L), (float)f20);
        this.time62 += 48L;
        ++this.count124;
    }

    @Override
    public void draw2() {
        if (this.count124 == 0) {
            return;
        }
        if (this.gpuManager5.isMapped()) {
            this.gpuManager5.unmap();
        }
        GpuPipelineFactory.Data data = GpuPipelineFactory.m1023(this.count124);
        if (data == null) {
            return;
        }
        if (data.getGpuTextureView3() == null) {
            return;
        }
        if (this.flag124 && !Util.isPositiveArea(this.count128, this.count219)) {
            return;
        }
        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                () -> "Round Rect Draw", data.getGpuTextureView3(), OptionalInt.empty(),
                data.getGpuTextureView2(), OptionalDouble.empty())) {
            renderPass.setPipeline(RenderPipelines.renderPipeline17);
            if (this.flag124) {
                Util.enableScissor(renderPass, this.count91, this.count47, this.count128, this.count219);
            }
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", data.gpuBufferSlice3());
            this.m764(renderPass, data);
        }
    }

    @Override
    public boolean prepareBuffers() {
        this.data5 = null;
        if (this.count124 == 0) {
            return false;
        }
        if (this.gpuManager5.isMapped()) {
            this.gpuManager5.unmap();
        }
        if (this.flag124 && !Util.isPositiveArea(this.count128, this.count219)) {
            return false;
        }
        this.data5 = GpuPipelineFactory.m369(this.count124, false);
        if (this.data5 == null) {
            return false;
        }
        return this.data5.getGpuTextureView3() != null;
    }

    @Override
    public void drawWithPass(Object object) {
        RenderPass renderPass = (RenderPass)object;
        String[] stringArray = TextureRenderer.getTextArray3();
        RoundRectRenderer roundRectRenderer = this;
        if (stringArray != null) {
            if (roundRectRenderer.data5 == null) {
                return;
            }
            renderPass.setUniform("DynamicTransforms", this.data5.gpuBufferSlice3());
            roundRectRenderer = this;
        }
        roundRectRenderer.m764(renderPass, this.data5);
    }

    private void m764(Object object, Object object2) {
        int n;
        int n2;
        int n3;
        int n4;
        RenderPass renderPass;
        block3: {
            GpuPipelineFactory.Data data;
            RenderPass renderPass2;
            block4: {
                block2: {
                    boolean bl;
                    block1: {
                        renderPass2 = (RenderPass)object;
                        data = (GpuPipelineFactory.Data)object2;
                        String[] stringArray = TextureRenderer.getTextArray3();
                        bl = this.flag124;
                        if (stringArray == null) break block1;
                        if (!bl) break block2;
                        renderPass = renderPass2;
                        n4 = this.count91;
                        n3 = this.count47;
                        n2 = this.count128;
                        n = this.count219;
                        if (stringArray == null) break block3;
                        bl = Util.enableScissor(renderPass, n4, n3, n2, n);
                    }
                    if (!bl) {
                        return;
                    }
                    break block4;
                }
                renderPass2.disableScissor();
            }
            renderPass2.setVertexBuffer(0, this.gpuManager5.getGpuBuffer());
            renderPass2.setIndexBuffer(GpuPipelineFactory.m577(data.getInt()), GpuPipelineFactory.getIndexType());
            renderPass = renderPass2;
            n4 = 0;
            n3 = 0;
            n2 = data.getInt();
            n = 1;
        }
        renderPass.drawIndexed(n4, n3, n2, n);
    }

    @Override
    public void endFrame() {
        String[] stringArray = TextureRenderer.getTextArray3();
        RoundRectRenderer roundRectRenderer = this;
        if (stringArray != null) {
            if (roundRectRenderer.count124 > 0) {
                GpuManager gpuManager = this.gpuManager5;
                if (stringArray != null) {
                    if (gpuManager.isMapped()) {
                        this.gpuManager5.unmap();
                    }
                    gpuManager = this.gpuManager5;
                }
                gpuManager.advanceBuffer();
            }
            this.count124 = 0;
            this.time62 = 0L;
            roundRectRenderer = this;
        }
        roundRectRenderer.data5 = null;
    }

    @Override
    public void close() {
        this.gpuManager5.flush();
        FrameListenerManager.manager4.removeListener(this);
    }

    public void m776(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        GpuPipelineFactory.ColorData colorData = Util.m1033(n5, n6, n7, n8);
        this.flag124 = true;
        this.count91 = colorData.count30();
        this.count47 = colorData.count31();
        this.count128 = colorData.count32();
        this.count219 = colorData.count33();
    }

    public void m761() {
        this.flag124 = false;
    }

    private static /* synthetic */ String cfrlam$draw$0() {
        return "Round Rect Draw";
    }
}

