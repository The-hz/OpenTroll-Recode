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
import shit.render.TextureRenderer;
import shit.util.RenderUtil4;
import shit.util.Util;

@Environment(value=EnvType.CLIENT)
public class Outline3
implements Listener2 {
    private final GpuManager gpuManager4 = new GpuManager(16384L, 32);
    private boolean flag70 = false;
    private int count66;
    private int count82;
    private int count114;
    private int count136;
    private long time44 = 0L;
    private int count171 = 0;
    private RenderUtil4.Data data3;

    private Outline3() {
    }

    public static Outline3 getOutline32() {
        return (Outline3)Manager4.manager4.m276(new Outline3());
    }

    public void m465(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object) {
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
        this.m263(f10, f11, f12, f13, f14, f15, f16, f17, f18, color, color, color, color);
    }

    public void m271(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object, Object object2) {
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
        Color color2 = (Color)object2;
        this.m263(f10, f11, f12, f13, f14, f15, f16, f17, f18, color, color2, color2, color);
    }

    public void m314(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object, Object object2) {
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
        Color color2 = (Color)object2;
        this.m263(f10, f11, f12, f13, f14, f15, f16, f17, f18, color, color, color2, color2);
    }

    public void m263(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object, Object object2, Object object3, Object object4) {
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
        Color color2 = (Color)object2;
        Color color3 = (Color)object3;
        Color color4 = (Color)object4;
        String[] stringArray = TextureRenderer.getTextArray3();
        float f19 = f18;
        float f20 = 0.0f;
        if (stringArray != null) {
            if (f19 <= f20) {
                return;
            }
            this.gpuManager4.setLong(this.time44 + 208L);
            this.gpuManager4.ensureMapped();
            f19 = f18;
            f20 = 0.5f;
        }
        float f21 = f19 * f20;
        float f22 = f10 + f12;
        float f23 = f11 + f13;
        float f24 = f10 - f21;
        float f25 = f11 - f21;
        float f26 = f22 + f21;
        float f27 = f23 + f21;
        int n = ColorHelper.toAbgr((int)color.getRGB());
        int n2 = ColorHelper.toAbgr((int)color2.getRGB());
        int n3 = ColorHelper.toAbgr((int)color3.getRGB());
        int n4 = ColorHelper.toAbgr((int)color4.getRGB());
        this.m215(f24, f25, f10, f11, f22, f23, f14, f15, f16, f17, f18, n);
        this.m215(f24, f27, f10, f11, f22, f23, f14, f15, f16, f17, f18, n2);
        this.m215(f26, f27, f10, f11, f22, f23, f14, f15, f16, f17, f18, n3);
        this.m215(f26, f25, f10, f11, f22, f23, f14, f15, f16, f17, f18, n4);
    }

    private void m215(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, int n) {
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
        long l = MemoryUtil.memAddress((ByteBuffer)this.gpuManager4.getByteBuffer());
        long l2 = l + this.time44;
        MemoryUtil.memPutFloat((long)l2, (float)f12);
        MemoryUtil.memPutFloat((long)(l2 + 4L), (float)f13);
        MemoryUtil.memPutFloat((long)(l2 + 8L), (float)0.0f);
        MemoryUtil.memPutInt((long)(l2 + 12L), (int)n2);
        MemoryUtil.memPutFloat((long)(l2 + 16L), (float)f14);
        MemoryUtil.memPutFloat((long)(l2 + 20L), (float)f15);
        MemoryUtil.memPutFloat((long)(l2 + 24L), (float)f16);
        MemoryUtil.memPutFloat((long)(l2 + 28L), (float)f17);
        MemoryUtil.memPutFloat((long)(l2 + 32L), (float)f18);
        MemoryUtil.memPutFloat((long)(l2 + 36L), (float)f19);
        MemoryUtil.memPutFloat((long)(l2 + 40L), (float)f20);
        MemoryUtil.memPutFloat((long)(l2 + 44L), (float)f21);
        MemoryUtil.memPutFloat((long)(l2 + 48L), (float)f22);
        this.time44 += 52L;
        ++this.count171;
    }

    public void m649(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        RenderUtil4.ColorData colorData = Util.m1033(n5, n6, n7, n8);
        this.flag70 = true;
        this.count66 = colorData.count30();
        this.count82 = colorData.count31();
        this.count114 = colorData.count32();
        this.count136 = colorData.count33();
    }

    public void m648() {
        this.flag70 = false;
    }

    @Override
    public void draw2() {
        if (this.count171 == 0) {
            return;
        }
        if (this.gpuManager4.isMapped()) {
            this.gpuManager4.unmap();
        }
        RenderUtil4.Data data = RenderUtil4.m1023(this.count171);
        if (data == null) {
            return;
        }
        if (data.getGpuTextureView3() == null) {
            return;
        }
        if (this.flag70 && !Util.isPositiveArea(this.count114, this.count136)) {
            return;
        }
        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                () -> "Round Rect Outline Draw", data.getGpuTextureView3(), OptionalInt.empty(),
                data.getGpuTextureView2(), OptionalDouble.empty())) {
            renderPass.setPipeline(RenderPipelines.renderPipeline13);
            if (this.flag70) {
                Util.enableScissor(renderPass, this.count66, this.count82, this.count114, this.count136);
            }
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", data.gpuBufferSlice3());
            this.m755(renderPass, data);
        }
    }

    @Override
    public boolean isSet4() {
        this.data3 = null;
        if (this.count171 == 0) {
            return false;
        }
        if (this.gpuManager4.isMapped()) {
            this.gpuManager4.unmap();
        }
        if (this.flag70 && !Util.isPositiveArea(this.count114, this.count136)) {
            return false;
        }
        this.data3 = RenderUtil4.m369(this.count171, false);
        if (this.data3 == null) {
            return false;
        }
        return this.data3.getGpuTextureView3() != null;
    }

    @Override
    public void setObj103(Object object) {
        RenderPass renderPass = (RenderPass)object;
        String[] stringArray = TextureRenderer.getTextArray3();
        Outline3 outline3 = this;
        if (stringArray != null) {
            if (outline3.data3 == null) {
                return;
            }
            renderPass.setUniform("DynamicTransforms", this.data3.gpuBufferSlice3());
            outline3 = this;
        }
        outline3.m755(renderPass, this.data3);
    }

    private void m755(Object object, Object object2) {
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
                        bl = this.flag70;
                        if (stringArray == null) break block1;
                        if (!bl) break block2;
                        renderPass = renderPass2;
                        n4 = this.count66;
                        n3 = this.count82;
                        n2 = this.count114;
                        n = this.count136;
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
            renderPass2.setVertexBuffer(0, this.gpuManager4.getGpuBuffer());
            renderPass2.setIndexBuffer(RenderUtil4.m577(data.getInt()), RenderUtil4.getObj19());
            renderPass = renderPass2;
            n4 = 0;
            n3 = 0;
            n2 = data.getInt();
            n = 1;
        }
        renderPass.drawIndexed(n4, n3, n2, n);
    }

    @Override
    public void m155() {
        String[] stringArray = TextureRenderer.getTextArray3();
        Outline3 outline3 = this;
        if (stringArray != null) {
            if (outline3.count171 > 0) {
                GpuManager gpuManager = this.gpuManager4;
                if (stringArray != null) {
                    if (gpuManager.isMapped()) {
                        this.gpuManager4.unmap();
                    }
                    gpuManager = this.gpuManager4;
                }
                gpuManager.advanceBuffer();
            }
            this.count171 = 0;
            this.time44 = 0L;
            outline3 = this;
        }
        outline3.data3 = null;
    }

    @Override
    public void m523() {
        this.gpuManager4.flush();
        Manager4.manager4.setObj100(this);
    }

    private static /* synthetic */ String cfrlam$draw$0() {
        return "Round Rect Outline Draw";
    }
}

