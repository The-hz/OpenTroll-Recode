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
import shit.api.Listener2;
import shit.manager.GpuManager;
import shit.manager.Manager4;
import shit.misc.RenderPipelines;
import shit.module.Module;
import shit.render.TextureRenderer;
import shit.util.RenderUtil4;
import shit.util.Util;

@Environment(value=EnvType.CLIENT)
public class Shadow
implements Listener2 {
    private final GpuManager gpuManager7 = new GpuManager(16384L, 32);
    private boolean flag89 = false;
    private int count210;
    private int count168;
    private int count169;
    private int count43;
    private long time37 = 0L;
    private int count94 = 0;
    private RenderUtil4.Data data;

    private Shadow() {
    }

    public static Shadow getShadow2() {
        return (Shadow)Manager4.manager4.addListener(new Shadow());
    }

    public void m536(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object) {
        block0: {
            float f10 = f;
            float f11 = f2;
            float f12 = f3;
            float f13 = f4;
            float f14 = f5;
            float f15 = f6;
            float f16 = f7;
            float f17 = f8;
            float f18 = f9;
            Color color = (Color)object;
            this.gpuManager7.setLong(this.time37 + 192L);
            this.gpuManager7.ensureMapped();
            float f19 = f10 - f18;
            float f20 = f11 - f18;
            float f21 = f10 + f12 + f18;
            float f22 = f11 + f13 + f18;
            float f23 = f10 + f12;
            TextureRenderer.getTextArray3();
            float f24 = f11 + f13;
            int n = ColorHelper.toAbgr((int)color.getRGB());
            this.m374(f19, f20, f10, f11, f23, f24, f14, f15, f16, f17, f18, n);
            this.m374(f19, f22, f10, f11, f23, f24, f14, f15, f16, f17, f18, n);
            this.m374(f21, f22, f10, f11, f23, f24, f14, f15, f16, f17, f18, n);
            this.m374(f21, f20, f10, f11, f23, f24, f14, f15, f16, f17, f18, n);
            if (Module.getTextArray9() != null) break block0;
            TextureRenderer.setTextArray6(new String[2]);
        }
    }

    private void m374(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, int n) {
        float f12 = f;
        float f13 = f2;
        float f14 = f3;
        float f15 = f4;
        float f16 = f5;
        float f17 = f6;
        float f18 = f7;
        float f19 = f8;
        float f20 = f9;
        float f21 = f10;
        float f22 = f11;
        int n2 = n;
        long l = MemoryUtil.memAddress((ByteBuffer)this.gpuManager7.getByteBuffer());
        long l2 = l + this.time37;
        MemoryUtil.memPutFloat((long)l2, (float)f12);
        MemoryUtil.memPutFloat((long)(l2 + 4L), (float)f13);
        MemoryUtil.memPutFloat((long)(l2 + 8L), (float)f22);
        MemoryUtil.memPutInt((long)(l2 + 12L), (int)n2);
        MemoryUtil.memPutFloat((long)(l2 + 16L), (float)f14);
        MemoryUtil.memPutFloat((long)(l2 + 20L), (float)f15);
        MemoryUtil.memPutFloat((long)(l2 + 24L), (float)f16);
        MemoryUtil.memPutFloat((long)(l2 + 28L), (float)f17);
        MemoryUtil.memPutFloat((long)(l2 + 32L), (float)f18);
        MemoryUtil.memPutFloat((long)(l2 + 36L), (float)f19);
        MemoryUtil.memPutFloat((long)(l2 + 40L), (float)f20);
        MemoryUtil.memPutFloat((long)(l2 + 44L), (float)f21);
        this.time37 += 48L;
        ++this.count94;
    }

    public void m535(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        RenderUtil4.ColorData colorData = Util.m1033(n5, n6, n7, n8);
        this.flag89 = true;
        this.count210 = colorData.count30();
        this.count168 = colorData.count31();
        this.count169 = colorData.count32();
        this.count43 = colorData.count33();
    }

    public void m226() {
        this.flag89 = false;
    }

    @Override
    public void draw2() {
        if (this.count94 == 0) {
            return;
        }
        if (this.gpuManager7.isMapped()) {
            this.gpuManager7.unmap();
        }
        RenderUtil4.Data data = RenderUtil4.m1023(this.count94);
        if (data == null) {
            return;
        }
        if (data.getGpuTextureView3() == null) {
            return;
        }
        if (this.flag89 && !Util.isPositiveArea(this.count169, this.count43)) {
            return;
        }
        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                () -> "Lumin Shadow Draw", data.getGpuTextureView3(), OptionalInt.empty(),
                data.getGpuTextureView2(), OptionalDouble.empty())) {
            renderPass.setPipeline(RenderPipelines.renderPipeline23);
            if (this.flag89) {
                Util.enableScissor(renderPass, this.count210, this.count168, this.count169, this.count43);
            }
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", data.gpuBufferSlice3());
            this.m36(renderPass, data);
        }
    }

    @Override
    public boolean prepareBuffers() {
        this.data = null;
        if (this.count94 == 0) {
            return false;
        }
        if (this.gpuManager7.isMapped()) {
            this.gpuManager7.unmap();
        }
        if (this.flag89 && !Util.isPositiveArea(this.count169, this.count43)) {
            return false;
        }
        this.data = RenderUtil4.m369(this.count94, false);
        if (this.data == null) {
            return false;
        }
        return this.data.getGpuTextureView3() != null;
    }

    @Override
    public void drawWithPass(Object object) {
        RenderPass renderPass = (RenderPass)object;
        String[] stringArray = TextureRenderer.getTextArray3();
        Shadow shadow = this;
        if (stringArray != null) {
            if (shadow.data == null) {
                return;
            }
            renderPass.setUniform("DynamicTransforms", this.data.gpuBufferSlice3());
            shadow = this;
        }
        shadow.m36(renderPass, this.data);
    }

    private void m36(Object object, Object object2) {
        int n;
        int n2;
        int n3;
        int n4;
        RenderPass renderPass;
        block3: {
            RenderUtil4.Data data;
            RenderPass renderPass2;
            block4: {
                block2: {
                    boolean bl;
                    block1: {
                        renderPass2 = (RenderPass)object;
                        data = (RenderUtil4.Data)object2;
                        String[] stringArray = TextureRenderer.getTextArray3();
                        bl = this.flag89;
                        if (stringArray == null) break block1;
                        if (!bl) break block2;
                        renderPass = renderPass2;
                        n4 = this.count210;
                        n3 = this.count168;
                        n2 = this.count169;
                        n = this.count43;
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
            renderPass2.setVertexBuffer(0, this.gpuManager7.getGpuBuffer());
            renderPass2.setIndexBuffer(RenderUtil4.m577(data.getInt()), RenderUtil4.getIndexType());
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
        Shadow shadow = this;
        if (stringArray != null) {
            if (shadow.count94 > 0) {
                GpuManager gpuManager = this.gpuManager7;
                if (stringArray != null) {
                    if (gpuManager.isMapped()) {
                        this.gpuManager7.unmap();
                    }
                    gpuManager = this.gpuManager7;
                }
                gpuManager.advanceBuffer();
            }
            this.count94 = 0;
            this.time37 = 0L;
            shadow = this;
        }
        shadow.data = null;
    }

    @Override
    public void close() {
        this.gpuManager7.flush();
        Manager4.manager4.removeListener(this);
    }

    private static /* synthetic */ String cfrlam$draw$0() {
        return "Lumin Shadow Draw";
    }
}

