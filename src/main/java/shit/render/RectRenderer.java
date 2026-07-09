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
public class RectRenderer
implements Listener2 {
    private final GpuManager gpuManager8 = new GpuManager(16384L, 32);
    private long time4 = 0L;
    private int count44 = 0;
    private boolean flag59 = false;
    private int count181;
    private int count65;
    private int count106;
    private int count122;
    private RenderUtil4.Data data4;

    private RectRenderer() {
    }

    public static RectRenderer getRectRenderer() {
        return (RectRenderer)Manager4.manager4.m276(new RectRenderer());
    }

    public void m697(float f, float f2, float f3, float f4, Object object) {
        float f5 = f;
        float f6 = f2;
        float f7 = f3;
        float f8 = f4;
        Color color = (Color)object;
        this.m828(f5, f6, f7, f8, color, color, color, color);
    }

    public void m828(float f, float f2, float f3, float f4, Object object, Object object2, Object object3, Object object4) {
        float f5 = f;
        float f6 = f2;
        float f7 = f3;
        float f8 = f4;
        Color color = (Color)object;
        Color color2 = (Color)object2;
        Color color3 = (Color)object3;
        Color color4 = (Color)object4;
        this.gpuManager8.setLong(this.time4 + 64L);
        this.gpuManager8.m691();
        int n = ColorHelper.toAbgr((int)color.getRGB());
        int n2 = ColorHelper.toAbgr((int)color2.getRGB());
        int n3 = ColorHelper.toAbgr((int)color3.getRGB());
        int n4 = ColorHelper.toAbgr((int)color4.getRGB());
        this.m70(f5, f6, n);
        this.m70(f5, f6 + f8, n2);
        this.m70(f5 + f7, f6 + f8, n3);
        this.m70(f5 + f7, f6, n4);
    }

    private void m70(float f, float f2, int n) {
        float f3 = f;
        float f4 = f2;
        int n2 = n;
        long l = MemoryUtil.memAddress((ByteBuffer)this.gpuManager8.getByteBuffer());
        long l2 = l + this.time4;
        MemoryUtil.memPutFloat((long)l2, (float)f3);
        MemoryUtil.memPutFloat((long)(l2 + 4L), (float)f4);
        MemoryUtil.memPutFloat((long)(l2 + 8L), (float)0.0f);
        MemoryUtil.memPutInt((long)(l2 + 12L), (int)n2);
        this.time4 += 16L;
        ++this.count44;
    }

    public void m1032(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        RenderUtil4.ColorData colorData = Util.m1033(n5, n6, n7, n8);
        this.flag59 = true;
        this.count181 = colorData.count30();
        this.count65 = colorData.count31();
        this.count106 = colorData.count32();
        this.count122 = colorData.count33();
    }

    public void m376() {
        this.flag59 = false;
    }

    @Override
    public void draw2() {
        if (this.count44 == 0) {
            return;
        }
        if (this.gpuManager8.isSet135()) {
            this.gpuManager8.m587();
        }
        RenderUtil4.Data data = RenderUtil4.m1023(this.count44);
        if (data == null) {
            return;
        }
        if (data.getGpuTextureView3() == null) {
            return;
        }
        if (this.flag59 && !Util.m843(this.count106, this.count122)) {
            return;
        }
        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                () -> "Rect Draw", data.getGpuTextureView3(), OptionalInt.empty(),
                data.getGpuTextureView2(), OptionalDouble.empty())) {
            renderPass.setPipeline(RenderPipelines.renderPipeline11);
            if (this.flag59) {
                Util.m268(renderPass, this.count181, this.count65, this.count106, this.count122);
            }
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", data.gpuBufferSlice3());
            this.m35(renderPass, data);
        }
    }

    @Override
    public boolean isSet4() {
        this.data4 = null;
        if (this.count44 == 0) {
            return false;
        }
        if (this.gpuManager8.isSet135()) {
            this.gpuManager8.m587();
        }
        if (this.flag59 && !Util.m843(this.count106, this.count122)) {
            return false;
        }
        this.data4 = RenderUtil4.m369(this.count44, false);
        if (this.data4 == null) {
            return false;
        }
        return this.data4.getGpuTextureView3() != null;
    }

    @Override
    public void setObj103(Object object) {
        RenderPass renderPass = (RenderPass)object;
        String[] stringArray = TextureRenderer.getTextArray3();
        RectRenderer rectRenderer = this;
        if (stringArray != null) {
            if (rectRenderer.data4 == null) {
                return;
            }
            renderPass.setUniform("DynamicTransforms", this.data4.gpuBufferSlice3());
            rectRenderer = this;
        }
        rectRenderer.m35(renderPass, this.data4);
    }

    private void m35(Object object, Object object2) {
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
                        bl = this.flag59;
                        if (stringArray == null) break block1;
                        if (!bl) break block2;
                        renderPass = renderPass2;
                        n4 = this.count181;
                        n3 = this.count65;
                        n2 = this.count106;
                        n = this.count122;
                        if (stringArray == null) break block3;
                        bl = Util.m268(renderPass, n4, n3, n2, n);
                    }
                    if (!bl) {
                        return;
                    }
                    break block4;
                }
                renderPass2.disableScissor();
            }
            renderPass2.setVertexBuffer(0, this.gpuManager8.getGpuBuffer());
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
        RectRenderer rectRenderer = this;
        if (stringArray != null) {
            if (rectRenderer.count44 > 0) {
                GpuManager gpuManager = this.gpuManager8;
                if (stringArray != null) {
                    if (gpuManager.isSet135()) {
                        this.gpuManager8.m587();
                    }
                    gpuManager = this.gpuManager8;
                }
                gpuManager.m470();
            }
            this.count44 = 0;
            this.time4 = 0L;
            rectRenderer = this;
        }
        rectRenderer.data4 = null;
    }

    @Override
    public void m523() {
        this.m155();
        this.gpuManager8.m145();
        Manager4.manager4.setObj100(this);
    }

    private static /* synthetic */ String cfrlam$draw$0() {
        return "Rect Draw";
    }
}

