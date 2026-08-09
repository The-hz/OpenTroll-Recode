/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.LambdaMetafactory;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.lwjgl.system.MemoryUtil;
import shit.api.Listener2;
import shit.gui.Texture;
import shit.manager.GpuManager;
import shit.manager.Manager2;
import shit.manager.Manager4;
import shit.misc.RenderPipelines;
import shit.util.MC;
import shit.util.RenderUtil4;
import shit.util.Util;

@Environment(value=EnvType.CLIENT)
public class TextureRenderer
implements Listener2 {
    private final Map<Object, GpuManagerHolder> map34 = new LinkedHashMap<>();
    private boolean flag117 = false;
    private int count193;
    private int count88;
    private int count116;
    private int count148;
    private GpuBufferSlice gpuBufferSlice5;
    private int count180;
    private static String[] texts4;

    private TextureRenderer() {
    }

    public static TextureRenderer getTextureRenderer2() {
        return (TextureRenderer)Manager4.manager4.m276(new TextureRenderer());
    }

    public void m656(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        RenderUtil4.ColorData colorData = Util.m1033(n5, n6, n7, n8);
        this.flag117 = true;
        this.count193 = colorData.count30();
        this.count88 = colorData.count31();
        this.count116 = colorData.count32();
        this.count148 = colorData.count33();
    }

    public void m44() {
        this.flag117 = false;
    }

    public void m872(Object object, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object2) {
        Identifier identifier = (Identifier)object;
        float f10 = f;
        float f11 = f2;
        float f12 = f3;
        float f13 = f4;
        float f14 = f5;
        float f15 = f6;
        float f16 = f7;
        float f17 = f8;
        float f18 = f9;
        Color color = (Color)object2;
        this.m56(identifier, f10, f11, f12, f13, f14, f15, f16, f17, f18, color, false);
    }

    public void m56(Object object, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object2, boolean bl) {
        Identifier identifier = (Identifier)object;
        float f10 = f;
        float f11 = f2;
        float f12 = f3;
        float f13 = f4;
        float f14 = f5;
        float f15 = f6;
        float f16 = f7;
        float f17 = f8;
        float f18 = f9;
        Color color = (Color)object2;
        boolean bl2 = bl;
        this.addRoundedTexture(identifier, f10, f11, f12, f13, f14, f14, f14, f14, f15, f16, f17, f18, color, bl2);
    }

    public void m936(Object object, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object2) {
        Texture texture = (Texture)((Object)object);
        float f10 = f;
        float f11 = f2;
        float f12 = f3;
        float f13 = f4;
        float f14 = f5;
        float f15 = f6;
        float f16 = f7;
        float f17 = f8;
        float f18 = f9;
        Color color = (Color)object2;
        this.addRoundedTexture((Object)texture, f10, f11, f12, f13, f14, f14, f14, f14, f15, f16, f17, f18, color, true);
    }

    public void m1009(Object object, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, Object object2, boolean bl) {
        Identifier identifier = (Identifier)object;
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
        Color color = (Color)object2;
        boolean bl2 = bl;
        this.addRoundedTexture(identifier, f13, f14, f15, f16, f17, f18, f19, f20, f21, f22, f23, f24, color, bl2);
    }

    public void m474(Object object, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, Object object2) {
        Texture texture = (Texture)((Object)object);
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
        Color color = (Color)object2;
        this.addRoundedTexture((Object)texture, f13, f14, f15, f16, f17, f18, f19, f20, f21, f22, f23, f24, color, true);
    }

    private void addRoundedTexture(Object object2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, Object object3, boolean bl) {
        Object object4 = object2;
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
        Color color = (Color)object3;
        boolean bl2 = bl;
        GpuManagerHolder gpuManagerHolder = this.map34.computeIfAbsent(object4, object -> {
            GpuManagerHolder holder = new GpuManagerHolder(new GpuManager(16384L, 32));
            holder.flag67 = bl2;
            return holder;
        });
        gpuManagerHolder.gpuManager2.setLong(gpuManagerHolder.time34 + 224L);
        gpuManagerHolder.gpuManager2.ensureMapped();
        int n = ColorHelper.toAbgr((int)color.getRGB());
        float f25 = f13 + f15;
        float f26 = f14 + f16;
        long l = MemoryUtil.memAddress((ByteBuffer)gpuManagerHolder.gpuManager2.getByteBuffer());
        long l2 = l + gpuManagerHolder.time34;
        this.m21(l2, f13, f14, f21, f22, n, f13, f14, f25, f26, f17, f18, f19, f20);
        this.m21(l2 + 56L, f13, f26, f21, f24, n, f13, f14, f25, f26, f17, f18, f19, f20);
        this.m21(l2 + 112L, f25, f26, f23, f24, n, f13, f14, f25, f26, f17, f18, f19, f20);
        this.m21(l2 + 168L, f25, f14, f23, f22, n, f13, f14, f25, f26, f17, f18, f19, f20);
        gpuManagerHolder.time34 += 224L;
        gpuManagerHolder.count120 += 4;
    }

    private void m21(long l, float f, float f2, float f3, float f4, int n, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12) {
        long l2 = l;
        float f13 = f;
        float f14 = f2;
        float f15 = f3;
        float f16 = f4;
        int n2 = n;
        float f17 = f5;
        float f18 = f6;
        float f19 = f7;
        float f20 = f8;
        float f21 = f9;
        float f22 = f10;
        float f23 = f11;
        float f24 = f12;
        MemoryUtil.memPutFloat((long)l2, (float)f13);
        MemoryUtil.memPutFloat((long)(l2 + 4L), (float)f14);
        MemoryUtil.memPutFloat((long)(l2 + 8L), (float)0.0f);
        MemoryUtil.memPutInt((long)(l2 + 12L), (int)n2);
        MemoryUtil.memPutFloat((long)(l2 + 16L), (float)f15);
        MemoryUtil.memPutFloat((long)(l2 + 20L), (float)f16);
        MemoryUtil.memPutFloat((long)(l2 + 24L), (float)f17);
        MemoryUtil.memPutFloat((long)(l2 + 28L), (float)f18);
        MemoryUtil.memPutFloat((long)(l2 + 32L), (float)f19);
        MemoryUtil.memPutFloat((long)(l2 + 36L), (float)f20);
        MemoryUtil.memPutFloat((long)(l2 + 40L), (float)f21);
        MemoryUtil.memPutFloat((long)(l2 + 44L), (float)f22);
        MemoryUtil.memPutFloat((long)(l2 + 48L), (float)f23);
        MemoryUtil.memPutFloat((long)(l2 + 52L), (float)f24);
    }

    @Override
    public void draw2() {
        if (this.map34.isEmpty()) {
            return;
        }
        RenderUtil4.m486();
        GpuTextureView view = RenderUtil4.getGpuTextureView6();
        if (view == null) {
            return;
        }
        if (this.flag117 && !Util.isPositiveArea(this.count116, this.count148)) {
            return;
        }
        int indexCount = this.getInt61();
        if (indexCount == 0) {
            return;
        }
        GpuBufferSlice slice = RenderUtil4.getGpuBufferSlice2();
        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                () -> "Rounded Texture Draws", view, OptionalInt.empty(), null, OptionalDouble.empty())) {
            renderPass.setPipeline(RenderPipelines.renderPipeline16);
            if (this.flag117) {
                Util.enableScissor(renderPass, this.count193, this.count88, this.count116, this.count148);
            }
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", slice);
            renderPass.setIndexBuffer(RenderUtil4.m577(indexCount), RenderUtil4.getObj19());
            this.setObj52(renderPass);
        }
    }

    @Override
    public boolean isSet4() {
        this.gpuBufferSlice5 = null;
        this.count180 = 0;
        if (this.map34.isEmpty()) {
            return false;
        }
        if (this.flag117 && !Util.isPositiveArea(this.count116, this.count148)) {
            return false;
        }
        this.count180 = this.getInt61();
        if (this.count180 == 0) {
            return false;
        }
        RenderUtil4.m577(this.count180);
        this.gpuBufferSlice5 = RenderUtil4.getGpuBufferSlice2();
        return this.gpuBufferSlice5 != null;
    }

    @Override
    public void setObj103(Object object) {
        TextureRenderer textureRenderer;
        RenderPass renderPass;
        block4: {
            block5: {
                block3: {
                    String[] stringArray;
                    block2: {
                        renderPass = (RenderPass)object;
                        stringArray = TextureRenderer.getTextArray3();
                        textureRenderer = this;
                        if (stringArray == null) break block2;
                        if (textureRenderer.gpuBufferSlice5 == null) break block3;
                        textureRenderer = this;
                    }
                    if (stringArray == null) break block4;
                    if (textureRenderer.count180 != 0) break block5;
                }
                return;
            }
            renderPass.setIndexBuffer(RenderUtil4.m577(this.count180), RenderUtil4.getObj19());
            renderPass.setUniform("DynamicTransforms", this.gpuBufferSlice5);
            textureRenderer = this;
        }
        textureRenderer.setObj52(renderPass);
    }

    private int getInt61() {
        int n;
        block5: {
            int n2 = 0;
            String[] stringArray = TextureRenderer.getTextArray3();
            for (Map.Entry entry : this.map34.entrySet()) {
                block8: {
                    GpuManagerHolder gpuManagerHolder;
                    GpuManagerHolder gpuManagerHolder2;
                    block7: {
                        boolean bl = false;
                        block6: {
                            gpuManagerHolder2 = (GpuManagerHolder)entry.getValue();
                            gpuManagerHolder2.texture3 = null;
                            n = gpuManagerHolder2.count120;
                            if (stringArray == null) break block5;
                            if (stringArray == null) break block6;
                            if (n == 0) continue;
                            gpuManagerHolder = gpuManagerHolder2;
                            if (stringArray == null) break block7;
                            bl = gpuManagerHolder.gpuManager2.isMapped();
                        }
                        if (bl) {
                            gpuManagerHolder2.gpuManager2.unmap();
                        }
                        gpuManagerHolder2.texture3 = this.resolveTexture(entry.getKey(), gpuManagerHolder2.flag67);
                        if (stringArray == null) break block8;
                        gpuManagerHolder = gpuManagerHolder2;
                    }
                    if (gpuManagerHolder.texture3 == null) continue;
                    n2 = Math.max(n2, gpuManagerHolder2.count120 / 4 * 6);
                }
                if (stringArray != null) continue;
            }
            n = n2;
        }
        return n;
    }

    private Texture resolveTexture(Object object, boolean bl) {
        block6: {
            Object object2;
            block5: {
                boolean bl2;
                Object object3;
                block4: {
                    object3 = object;
                    boolean bl3 = bl;
                    String[] stringArray = TextureRenderer.getTextArray3();
                    bl2 = object3 instanceof Identifier;
                    if (stringArray == null) break block4;
                    if (bl2) {
                        Identifier identifier2 = (Identifier)object3;
                        return (Texture)Manager2.manager2.map20.computeIfAbsent(identifier2, identifier -> this.m596(identifier, bl3));
                    }
                    object2 = object3;
                    if (stringArray == null) break block5;
                    bl2 = object2 instanceof Texture;
                }
                if (!bl2) break block6;
                object2 = object3;
            }
            Texture texture = (Texture)((Object)object2);
            return texture;
        }
        return null;
    }

    private void setObj52(Object object) {
        RenderPass renderPass = (RenderPass)object;
        if (this.flag117) {
            if (!Util.enableScissor(renderPass, this.count193, this.count88, this.count116, this.count148)) {
                return;
            }
        } else {
            renderPass.disableScissor();
        }
        for (GpuManagerHolder holder : this.map34.values()) {
            if (holder.count120 == 0) {
                continue;
            }
            if (holder.texture3 == null) {
                continue;
            }
            int indexCount = holder.count120 / 4 * 6;
            Texture texture = holder.texture3;
            renderPass.setVertexBuffer(0, holder.gpuManager2.getGpuBuffer());
            renderPass.bindTexture("Sampler0", texture.getGlTextureView(), texture.getSampler());
            renderPass.drawIndexed(0, 0, indexCount, 1);
        }
    }

    private Texture m596(Object object, boolean useLinear) {
        Identifier identifier = (Identifier)object;
        AbstractTexture abstractTexture = MC.mc.getTextureManager().getTexture(identifier);
        try {
            GpuTexture gpuTexture = abstractTexture.getGlTexture();
            GpuTextureView gpuTextureView = abstractTexture.getGlTextureView();
            GpuSampler gpuSampler = abstractTexture.getSampler();
            return new Texture(gpuTexture, gpuTextureView, gpuSampler, false, false);
        } catch (Exception exception) {
            NativeImage nativeImage;
            try {
                ResourceManager resourceManager = MC.mc.getResourceManager();
                Resource resource = resourceManager.getResourceOrThrow(identifier);
                try (InputStream inputStream = resource.getInputStream()) {
                    nativeImage = NativeImage.read(inputStream);
                }
            } catch (IOException iOException) {
                nativeImage = MissingSprite.createImage();
            }
            GpuDevice gpuDevice = RenderSystem.getDevice();
            GpuTexture gpuTexture = gpuDevice.createTexture(
                identifier.toString(), 5, TextureFormat.RGBA8, nativeImage.getWidth(), nativeImage.getHeight(), 1, 1);
            gpuDevice.createCommandEncoder().writeToTexture(gpuTexture, nativeImage);
            GpuTextureView gpuTextureView = gpuDevice.createTextureView(gpuTexture);
            GpuSampler gpuSampler = RenderSystem.getSamplerCache().get(useLinear ? FilterMode.LINEAR : FilterMode.NEAREST);
            nativeImage.close();
            return new Texture(gpuTexture, gpuTextureView, gpuSampler, true, false);
        }
    }

    @Override
    public void m155() {
        block6: {
            Iterator iterator = this.map34.values().iterator();
            String[] stringArray = TextureRenderer.getTextArray3();
            while (iterator.hasNext()) {
                GpuManagerHolder gpuManagerHolder = (GpuManagerHolder)iterator.next();
                if (stringArray != null) {
                    GpuManagerHolder gpuManagerHolder2 = gpuManagerHolder;
                    if (stringArray != null) {
                        if (gpuManagerHolder2.count120 > 0) {
                            GpuManager gpuManager = gpuManagerHolder.gpuManager2;
                            if (stringArray != null) {
                                if (gpuManager.isMapped()) {
                                    gpuManagerHolder.gpuManager2.unmap();
                                }
                                gpuManager = gpuManagerHolder.gpuManager2;
                            }
                            gpuManager.advanceBuffer();
                        }
                        gpuManagerHolder.time34 = 0L;
                        gpuManagerHolder.count120 = 0;
                        gpuManagerHolder2 = gpuManagerHolder;
                    }
                    gpuManagerHolder2.texture3 = null;
                    if (stringArray != null) continue;
                }
                break block6;
            }
            this.gpuBufferSlice5 = null;
            this.count180 = 0;
        }
    }

    @Override
    public void m523() {
        block2: {
            String[] stringArray = TextureRenderer.getTextArray3();
            this.m155();
            String[] stringArray2 = stringArray;
            for (GpuManagerHolder gpuManagerHolder : this.map34.values()) {
                gpuManagerHolder.gpuManager2.flush();
                if (stringArray2 != null) {
                    if (stringArray2 != null) continue;
                }
                break block2;
            }
            this.map34.clear();
            Manager2.manager2.m304();
            Manager4.manager4.setObj100(this);
        }
    }

    private static /* synthetic */ String cfrlam$draw$1() {
        return "Rounded Texture Draws";
    }

    public static void setTextArray6(String[] stringArray) {
        texts4 = stringArray;
    }

    public static String[] getTextArray3() {
        return texts4;
    }

    static {
        boolean bl = false;
        String string = "\\\u00f0\u00bb\u00eb\u0014}\r\u00d2ow}}N\u00f1|bR\u0015J\u00ff\u00b6\u00d1\u00c4\u00d88\u009f\b e\u0014\u008a&\u0082kO\u00f9\u00e9P/";
        int n = 39;
        TextureRenderer.setTextArray6(new String[3]);
    }

    @Environment(value=EnvType.CLIENT)
    static final class GpuManagerHolder {
        final GpuManager gpuManager2;
        long time34 = 0L;
        int count120 = 0;
        boolean flag67;
        Texture texture3;

        private GpuManagerHolder(GpuManager gpuManager) {
            this.gpuManager2 = gpuManager;
        }
    }
}

