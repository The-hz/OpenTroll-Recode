/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.lang.invoke.LambdaMetafactory;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.ColorHelper;
import org.lwjgl.system.MemoryUtil;
import shit.api.Listener;
import shit.data.BufferUtilData;
import shit.manager.BufferUtilDataManager;
import shit.manager.GpuManager;
import shit.misc.BufferUtil;
import shit.misc.RenderPipelines;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.util.RenderUtil4;
import shit.util.Util;

@Environment(value=EnvType.CLIENT)
public class ShaderRenderer
implements Listener {
    private final long time71;
    private final Map map33 = new LinkedHashMap();
    private final Map map28 = new Cache(this, 64, 0.75f, true);
    private final Map map2 = new Cache2(this, 64, 0.75f, true);
    private boolean flag12 = false;
    private int count48;
    private int count90;
    private int count214;
    private int count236;
    private GpuBufferSlice gpuBufferSlice4;
    private int count206;
    private static Module[] modules;

    public ShaderRenderer(long l) {
        this.time71 = l;
    }

    public ShaderRenderer() {
        this(65536L);
    }

    @Override
    public void m883(Object object, float f, float f2, float f3, Object object2, Object object3) {
        BufferUtilDataManager bufferUtilDataManager;
        Color color;
        float f4;
        float f5;
        float f6;
        String string;
        block4: {
            block3: {
                int n;
                block2: {
                    string = (String)object;
                    f6 = f;
                    f5 = f2;
                    f4 = f3;
                    color = (Color)object2;
                    bufferUtilDataManager = (BufferUtilDataManager)object3;
                    Module[] moduleArray = ShaderRenderer.getModuleArray();
                    n = string.isEmpty() ? 1 : 0;
                    if (moduleArray == null) break block2;
                    if (n != 0) break block3;
                    n = color.getAlpha();
                }
                if (n != 0) break block4;
            }
            return;
        }
        bufferUtilDataManager.setObj108(string);
        this.m834(this.m777(string, bufferUtilDataManager), f6, f5, f4, ColorHelper.toAbgr((int)color.getRGB()));
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void m227(Object var1_1, float var2_2, float var3_3, float var4_4, Object var5_5, Object var6_6, Object var7_7) {
        String string = (String)var1_1;
        float x = var2_2;
        float y = var3_3;
        float scale = var4_4;
        Color startColor = (Color)var5_5;
        Color endColor = (Color)var6_6;
        BufferUtilDataManager bufferUtilDataManager = (BufferUtilDataManager)var7_7;
        if (string.isEmpty()) {
            return;
        }
        if (startColor.getAlpha() == 0 && endColor.getAlpha() == 0) {
            return;
        }
        bufferUtilDataManager.setObj108(string);
        Inner inner = this.m777(string, bufferUtilDataManager);
        float width = inner.flag28 ? inner.value107 : this.m393(string, bufferUtilDataManager);
        this.m219(inner, x, y, scale, width, startColor.getRGB(), endColor.getRGB());
    }

    private Inner m777(Object object, Object object2) {
        Inner inner;
        BufferUtilDataManagerData bufferUtilDataManagerData;
        block2: {
            long l;
            long l2;
            BufferUtilDataManager bufferUtilDataManager;
            String string;
            block3: {
                Inner inner2;
                block4: {
                    Inner inner3;
                    block5: {
                        string = (String)object;
                        bufferUtilDataManager = (BufferUtilDataManager)object2;
                        l2 = bufferUtilDataManager.getLong15();
                        l = bufferUtilDataManager.getLong14();
                        bufferUtilDataManagerData = new BufferUtilDataManagerData(bufferUtilDataManager, string);
                        Module[] moduleArray = ShaderRenderer.getModuleArray();
                        inner = inner3 = (Inner)this.map28.get(bufferUtilDataManagerData);
                        if (moduleArray == null) break block2;
                        if (inner == null) break block3;
                        inner = inner3;
                        if (moduleArray == null) break block2;
                        if (inner.time53 != l) break block3;
                        inner2 = inner3;
                        if (moduleArray == null) break block4;
                        if (inner2.flag28) break block5;
                        inner = inner3;
                        if (moduleArray == null) break block2;
                        if (inner.time11 != l2) break block3;
                    }
                    inner2 = inner3;
                }
                return inner2;
            }
            inner = this.m324(string, bufferUtilDataManager, l2, l);
        }
        Inner inner4 = inner;
        this.map28.put(bufferUtilDataManagerData, inner4);
        return inner4;
    }

    private Inner m324(Object object, Object object2, long l, long l2) {
        int n;
        int n2;
        int n3;
        boolean bl;
        Module[] moduleArray;
        float f;
        LinkedHashMap<shit.misc.BufferUtil, BufferUtilHolder> linkedHashMap;
        long l3;
        long l4;
        block8: {
            String string = (String)object;
            BufferUtilDataManager bufferUtilDataManager = (BufferUtilDataManager)object2;
            l4 = l;
            l3 = l2;
            linkedHashMap = new LinkedHashMap<shit.misc.BufferUtil, BufferUtilHolder>();
            float f2 = 0.0f;
            float f3 = 0.0f;
            f = 0.0f;
            float f4 = (float)bufferUtilDataManager.bufferUtil2.count200 * 0.35f;
            float f5 = (float)bufferUtilDataManager.bufferUtil2.count69 * 0.35f;
            moduleArray = ShaderRenderer.getModuleArray();
            bl = true;
            n3 = 0;
            for (int i = 0; i < string.length(); ++i) {
                BufferUtilData bufferUtilData;
                block10: {
                    block9: {
                        int n4 = 0;
                        n = n2 = string.charAt(i);
                        if (moduleArray == null) break block8;
                        int n5 = 32;
                        if (moduleArray != null) {
                            if (n == n5) {
                                f2 += 3.0f;
                                if (moduleArray != null) continue;
                            }
                            n4 = n2;
                            n5 = 10;
                        }
                        if (n4 == n5) {
                            f = Math.max(f, f2);
                            f2 = 0.0f;
                            f3 += f5;
                            if (moduleArray != null) continue;
                        }
                        bufferUtilData = bufferUtilDataManager.m705(n2);
                        if (moduleArray == null) break block9;
                        if (bufferUtilData != null) break block10;
                        bl = false;
                    }
                    if (moduleArray != null) continue;
                }
                float f6 = f2;
                float f7 = f6 + (float)bufferUtilData.getInt7() * 0.35f;
                float f8 = f3 + f4 + (float)bufferUtilData.count20() * 0.35f;
                float f9 = f8 + (float)bufferUtilData.count18() * 0.35f;
                float f10 = (float)bufferUtilData.count21() * 0.35f + 0.0f;
                linkedHashMap.computeIfAbsent(bufferUtilData.bufferUtil(), BufferUtilHolder::new).m631(f6, f8, f7, f9, bufferUtilData.vec4f2(), f2, f2 + (float)bufferUtilData.count21() * 0.35f);
                f2 += f10;
                ++n3;
                if (moduleArray != null) continue;
            }
            f = Math.max(f, f2);
            n = linkedHashMap.size();
        }
        BufferUtilHolder2[] bufferUtilHolder2Array = new BufferUtilHolder2[n];
        n2 = 0;
        for (BufferUtilHolder bufferUtilHolder : linkedHashMap.values()) {
            bufferUtilHolder2Array[n2++] = bufferUtilHolder.getBufferUtilHolder2();
            if (moduleArray != null) continue;
        }
        return new Inner(bufferUtilHolder2Array, n3, f, bl, l4, l3);
    }

    private void m834(Object object, float f, float f2, float f3, int n) {
        Inner inner = (Inner)object;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        int n2 = n;
        Module[] moduleArray = ShaderRenderer.getModuleArray();
        Inner inner2 = inner;
        if (moduleArray != null) {
            if (inner2.count104 == 0) {
                return;
            }
            inner2 = inner;
        }
        BufferUtilHolder2[] bufferUtilHolder2Array = inner2.bufferUtilHolder2s;
        int n3 = bufferUtilHolder2Array.length;
        int n4 = 0;
        while (n4 < n3) {
            block5: {
                BufferUtilHolder2 bufferUtilHolder2 = bufferUtilHolder2Array[n4];
                BufferUtil bufferUtil = this.batchFor(bufferUtilHolder2.bufferUtil2);
                long l = bufferUtil.m673(bufferUtilHolder2.count43);
                float[] fArray = bufferUtilHolder2.values2;
                for (int i = 0; i < bufferUtilHolder2.count43; ++i) {
                    ShaderRenderer.m307(l, f4, f5, f6, fArray, i * 10, n2, n2);
                    l += 96L;
                    if (moduleArray != null) {
                        if (moduleArray != null) continue;
                    }
                    break block5;
                }
                ++n4;
            }
            if (moduleArray != null) continue;
        }
    }

    private void m219(Object object, float f, float f2, float f3, float f4, int n, int n2) {
        Inner inner = (Inner)object;
        float f5 = f;
        float f6 = f2;
        float f7 = f3;
        float f8 = f4;
        int n3 = n;
        int n4 = n2;
        Module[] moduleArray = ShaderRenderer.getModuleArray();
        if (inner.count104 == 0) {
            return;
        }
        float f9 = Math.max(f8, 1.0f);
        BufferUtilHolder2[] bufferUtilHolder2Array = inner.bufferUtilHolder2s;
        int n5 = bufferUtilHolder2Array.length;
        int n6 = 0;
        while (n6 < n5) {
            block4: {
                BufferUtilHolder2 bufferUtilHolder2 = bufferUtilHolder2Array[n6];
                BufferUtil bufferUtil = this.batchFor(bufferUtilHolder2.bufferUtil2);
                long l = bufferUtil.m673(bufferUtilHolder2.count43);
                float[] fArray = bufferUtilHolder2.values2;
                for (int i = 0; i < bufferUtilHolder2.count43; ++i) {
                    int n7 = i * 10;
                    int n8 = ColorHelper.toAbgr((int)ColorHelper.lerp((float)ShaderRenderer.m934(fArray[n7 + 8] / f9), (int)n3, (int)n4));
                    int n9 = ColorHelper.toAbgr((int)ColorHelper.lerp((float)ShaderRenderer.m934(fArray[n7 + 9] / f9), (int)n3, (int)n4));
                    ShaderRenderer.m307(l, f5, f6, f7, fArray, n7, n8, n9);
                    l += 96L;
                    if (moduleArray != null) {
                        if (moduleArray != null) continue;
                    }
                    break block4;
                }
                ++n6;
            }
            if (moduleArray != null) continue;
        }
    }

    private BufferUtil batchFor(Object object) {
        shit.misc.BufferUtil bufferUtil2 = (shit.misc.BufferUtil)object;
        return (BufferUtil)this.map33.computeIfAbsent(bufferUtil2, bufferUtil -> new BufferUtil(new GpuManager(this.time71, 32)));
    }

    private static void m307(long l, float f, float f2, float f3, Object object, int n, int n2, int n3) {
        long l2 = l;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        float[] fArray = (float[])object;
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        float f7 = f4 + fArray[n4] * f6;
        float f8 = f5 + fArray[n4 + 1] * f6;
        float f9 = f4 + fArray[n4 + 2] * f6;
        float f10 = f5 + fArray[n4 + 3] * f6;
        float f11 = fArray[n4 + 4];
        float f12 = fArray[n4 + 5];
        float f13 = fArray[n4 + 6];
        float f14 = fArray[n4 + 7];
        shit.util.BufferUtil.m514(l2, f7, f8, f11, f12, n5);
        shit.util.BufferUtil.m514(l2 + 24L, f7, f10, f11, f14, n5);
        shit.util.BufferUtil.m514(l2 + 48L, f9, f10, f13, f14, n6);
        shit.util.BufferUtil.m514(l2 + 72L, f9, f8, f13, f12, n6);
    }

    private float m393(Object object, Object object2) {
        Module[] moduleArray;
        BufferUtilDataManagerData bufferUtilDataManagerData;
        BufferUtilDataManager bufferUtilDataManager;
        String string;
        block8: {
            Float f;
            block7: {
                Float f2;
                string = (String)object;
                bufferUtilDataManager = (BufferUtilDataManager)object2;
                bufferUtilDataManagerData = new BufferUtilDataManagerData(bufferUtilDataManager, string);
                moduleArray = ShaderRenderer.getModuleArray();
                f = f2 = (Float)this.map2.get(bufferUtilDataManagerData);
                if (moduleArray == null) break block7;
                if (f == null) break block8;
                f = f2;
            }
            return f.floatValue();
        }
        float f = 0.0f;
        float f3 = 0.0f;
        for (int i = 0; i < string.length(); ++i) {
            int n;
            int n2 = n = string.charAt(i);
            int n3 = 32;
            if (moduleArray != null) {
                if (n2 == n3) {
                    f3 += 3.0f;
                    if (moduleArray != null) continue;
                }
                n2 = n;
                n3 = 10;
            }
            if (n2 == n3) {
                f = Math.max(f, f3);
                f3 = 0.0f;
                if (moduleArray != null) continue;
            }
            f3 += (float)bufferUtilDataManager.m360(n) * 0.35f + 0.0f;
            if (moduleArray != null) continue;
        }
        float f4 = Math.max(f, f3);
        this.map2.put(bufferUtilDataManagerData, Float.valueOf(f4));
        return f4;
    }

    private static float m934(float f) {
        float f2 = f;
        return Math.clamp(f2, 0.0f, 1.0f);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void draw() {
        if (this.map33.isEmpty()) {
            return;
        }
        RenderUtil4.m486();
        com.mojang.blaze3d.textures.GpuTextureView colorView = RenderUtil4.getGpuTextureView6();
        com.mojang.blaze3d.textures.GpuTextureView depthView = RenderUtil4.getGpuTextureView4();
        if (colorView == null) {
            return;
        }
        if (this.flag12 && !Util.isPositiveArea(this.count214, this.count236)) {
            return;
        }
        int indexCount = this.getInt29();
        if (indexCount == 0) {
            return;
        }
        GpuBufferSlice dynamicTransforms = RenderUtil4.getGpuBufferSlice2();
        GpuBuffer indexBuffer = RenderUtil4.m577(indexCount);
        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Lumin TTF Draws", colorView, OptionalInt.empty(), depthView, OptionalDouble.empty())) {
            renderPass.setPipeline(((Boolean)ClientSetting.INSTANCE.fontAntiAliasing.getValue()).booleanValue() ? RenderPipelines.renderPipeline3 : RenderPipelines.renderPipeline18);
            if (this.flag12) {
                Util.enableScissor(renderPass, this.count48, this.count90, this.count214, this.count236);
            }
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", dynamicTransforms);
            renderPass.setIndexBuffer(indexBuffer, RenderUtil4.getObj19());
            this.setObj34(renderPass);
        }
    }

    @Override
    public boolean flag4() {
        this.gpuBufferSlice4 = null;
        this.count206 = 0;
        if (this.map33.isEmpty()) {
            return false;
        }
        if (this.flag12 && !Util.isPositiveArea(this.count214, this.count236)) {
            return false;
        }
        this.count206 = this.getInt29();
        if (this.count206 == 0) {
            return false;
        }
        RenderUtil4.m577(this.count206);
        this.gpuBufferSlice4 = RenderUtil4.getGpuBufferSlice2();
        return this.gpuBufferSlice4 != null;
    }

    @Override
    public void setObj46(Object object) {
        int n;
        RenderPass renderPass;
        block4: {
            block5: {
                block3: {
                    ShaderRenderer shaderRenderer;
                    Module[] moduleArray;
                    block2: {
                        renderPass = (RenderPass)object;
                        moduleArray = ShaderRenderer.getModuleArray();
                        shaderRenderer = this;
                        if (moduleArray == null) break block2;
                        if (shaderRenderer.gpuBufferSlice4 == null) break block3;
                        shaderRenderer = this;
                    }
                    n = shaderRenderer.count206;
                    if (moduleArray == null) break block4;
                    if (n != 0) break block5;
                }
                return;
            }
            n = this.count206;
        }
        GpuBuffer gpuBuffer = RenderUtil4.m577(n);
        renderPass.setIndexBuffer(gpuBuffer, RenderUtil4.getObj19());
        renderPass.setUniform("DynamicTransforms", this.gpuBufferSlice4);
        this.setObj34(renderPass);
    }

    private int getInt29() {
        int max = 0;
        Iterator iterator = this.map33.values().iterator();
        while (iterator.hasNext()) {
            BufferUtil bufferUtil = (BufferUtil)iterator.next();
            if (bufferUtil.time65 == 0L) {
                continue;
            }
            if (bufferUtil.gpuManager.isMapped()) {
                bufferUtil.gpuManager.unmap();
                bufferUtil.time38 = 0L;
            }
            int count = (int)(bufferUtil.time65 / 24L);
            max = Math.max(max, count / 4 * 6);
        }
        return max;
    }

    private void setObj34(Object var1_1) {
        RenderPass renderPass = (RenderPass)var1_1;
        if (this.flag12) {
            if (!Util.enableScissor(renderPass, this.count48, this.count90, this.count214, this.count236)) {
                return;
            }
        } else {
            renderPass.disableScissor();
        }
        Iterator iterator = this.map33.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            shit.misc.BufferUtil bufferUtil = (shit.misc.BufferUtil)entry.getKey();
            BufferUtil bufferUtil2 = (BufferUtil)entry.getValue();
            if (bufferUtil2.time65 == 0L) {
                continue;
            }
            int count = (int)(bufferUtil2.time65 / 24L);
            int indices = count / 4 * 6;
            renderPass.setVertexBuffer(0, bufferUtil2.gpuManager.getGpuBuffer());
            renderPass.bindTexture("Sampler0", bufferUtil.getTexture().getGlTextureView(), bufferUtil.getTexture().getSampler());
            renderPass.drawIndexed(0, 0, indices, 1);
        }
    }

    @Override
    public void tick() {
        block6: {
            Iterator iterator = this.map33.values().iterator();
            Module[] moduleArray = ShaderRenderer.getModuleArray();
            while (iterator.hasNext()) {
                BufferUtil bufferUtil = (BufferUtil)iterator.next();
                if (moduleArray != null) {
                    BufferUtil bufferUtil2 = bufferUtil;
                    if (moduleArray != null) {
                        if (bufferUtil2.time65 > 0L) {
                            GpuManager gpuManager = bufferUtil.gpuManager;
                            if (moduleArray != null) {
                                if (gpuManager.isMapped()) {
                                    bufferUtil.gpuManager.unmap();
                                    bufferUtil.time38 = 0L;
                                }
                                gpuManager = bufferUtil.gpuManager;
                            }
                            gpuManager.advanceBuffer();
                        }
                        bufferUtil2 = bufferUtil;
                    }
                    bufferUtil2.time65 = 0L;
                    if (moduleArray != null) continue;
                }
                break block6;
            }
            this.gpuBufferSlice4 = null;
            this.count206 = 0;
        }
    }

    @Override
    public void m952() {
        block2: {
            this.tick();
            Iterator iterator = this.map33.values().iterator();
            Module[] moduleArray = ShaderRenderer.getModuleArray();
            while (iterator.hasNext()) {
                BufferUtil bufferUtil = (BufferUtil)iterator.next();
                bufferUtil.gpuManager.flush();
                if (moduleArray != null) {
                    if (moduleArray != null) continue;
                }
                break block2;
            }
            this.map33.clear();
            this.map28.clear();
            this.map2.clear();
        }
    }

    @Override
    public float m542(float f, Object object) {
        float f2 = f;
        BufferUtilDataManager bufferUtilDataManager = (BufferUtilDataManager)object;
        return (float)bufferUtilDataManager.bufferUtil2.count69 * 0.35f * f2;
    }

    @Override
    public float m858(Object object, float f, Object object2) {
        String string = (String)object;
        float f2 = f;
        BufferUtilDataManager bufferUtilDataManager = (BufferUtilDataManager)object2;
        if (string.isEmpty()) {
            return 0.0f;
        }
        return this.m393(string, bufferUtilDataManager) * f2;
    }

    @Override
    public void m635(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        RenderUtil4.ColorData colorData = Util.m1033(n5, n6, n7, n8);
        this.flag12 = true;
        this.count48 = colorData.count30();
        this.count90 = colorData.count31();
        this.count214 = colorData.count32();
        this.count236 = colorData.count33();
    }

    @Override
    public void m181() {
        this.flag12 = false;
    }

    private static /* synthetic */ String cfrlam$draw$1() {
        return "Lumin TTF Draws";
    }

    public static void setModuleArray4(Module[] moduleArray) {
        modules = moduleArray;
    }

    public static Module[] getModuleArray() {
        return modules;
    }

    static {
        boolean bl = false;
        ShaderRenderer.setModuleArray4(new Module[4]);
    }

    @Environment(value=EnvType.CLIENT)
    static final class BufferUtil {
        final GpuManager gpuManager;
        long time65 = 0L;
        long time38 = 0L;

        private BufferUtil(GpuManager gpuManager) {
            this.gpuManager = gpuManager;
        }

        private long m673(int n) {
            long base = this.time65;
            long end = base + (long)n * 96L;
            this.gpuManager.setLong(end);
            if (this.gpuManager.isMapped()) {
                if (this.time38 == 0L) {
                    this.time38 = MemoryUtil.memAddress(this.gpuManager.getByteBuffer());
                }
            } else {
                this.gpuManager.ensureMapped();
                this.time38 = MemoryUtil.memAddress(this.gpuManager.getByteBuffer());
            }
            this.time65 = end;
            return this.time38 + base;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static class Cache
    extends LinkedHashMap {
        final ShaderRenderer shaderRenderer;

        Cache(ShaderRenderer shaderRenderer, int n, float f, boolean bl) {
            super(n, f, bl);
            this.shaderRenderer = shaderRenderer;
        }

        protected boolean removeEldestEntry(Map.Entry entry) {
            Module[] moduleArray = ShaderRenderer.getModuleArray();
            int n = this.size();
            boolean bl = false;
            if (moduleArray != null) {
                bl = n > 256;
            }
            return bl;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static class Cache2
    extends LinkedHashMap {
        final ShaderRenderer shaderRenderer2;

        Cache2(ShaderRenderer shaderRenderer, int n, float f, boolean bl) {
            super(n, f, bl);
            this.shaderRenderer2 = shaderRenderer;
        }

        protected boolean removeEldestEntry(Map.Entry entry) {
            Module[] moduleArray = ShaderRenderer.getModuleArray();
            int n = this.size();
            boolean bl = false;
            if (moduleArray != null) {
                bl = n > 256;
            }
            return bl;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class BufferUtilHolder2 {
        final shit.misc.BufferUtil bufferUtil2;
        final float[] values2;
        final int count43;

        private BufferUtilHolder2(shit.misc.BufferUtil bufferUtil, float[] fArray, int n) {
            this.bufferUtil2 = bufferUtil;
            this.values2 = fArray;
            this.count43 = n;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class BufferUtilDataManagerData  {
        private final BufferUtilDataManager bufferUtilDataManager2;
        private final String text11;

        private BufferUtilDataManagerData(BufferUtilDataManager bufferUtilDataManager, String string) {
            this.bufferUtilDataManager2 = bufferUtilDataManager;
            this.text11 = string;
        }

        public BufferUtilDataManager bufferUtilDataManager2() {
            return this.bufferUtilDataManager2;
        }

        public String getText27() {
            return this.text11;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Inner {
        final BufferUtilHolder2[] bufferUtilHolder2s;
        final int count104;
        final float value107;
        final boolean flag28;
        final long time11;
        final long time53;

        private Inner(BufferUtilHolder2[] bufferUtilHolder2Array, int n, float f, boolean bl, long l, long l2) {
            this.bufferUtilHolder2s = bufferUtilHolder2Array;
            this.count104 = n;
            this.value107 = f;
            this.flag28 = bl;
            this.time11 = l;
            this.time53 = l2;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class BufferUtilHolder {
        private final shit.misc.BufferUtil bufferUtil3;
        private float[] values4 = new float[160];
        private int count97;

        private BufferUtilHolder(shit.misc.BufferUtil bufferUtil) {
            this.bufferUtil3 = bufferUtil;
        }

        private void m631(float f, float f2, float f3, float f4, Object object, float f5, float f6) {
            float f7 = f;
            float f8 = f2;
            float f9 = f3;
            float f10 = f4;
            shit.misc.BufferUtil.Vec4f vec4f = (shit.misc.BufferUtil.Vec4f)object;
            float f11 = f5;
            float f12 = f6;
            this.setInt10(this.count97 + 1);
            int n = this.count97 * 10;
            this.values4[n] = f7;
            this.values4[n + 1] = f8;
            this.values4[n + 2] = f9;
            this.values4[n + 3] = f10;
            this.values4[n + 4] = vec4f.getFloat23();
            this.values4[n + 5] = vec4f.value46();
            this.values4[n + 6] = vec4f.value47();
            this.values4[n + 7] = vec4f.getFloat30();
            this.values4[n + 8] = f11;
            this.values4[n + 9] = f12;
            ++this.count97;
        }

        private void setInt10(int n) {
            int n2 = n;
            int n3 = n2 * 10;
            Module[] moduleArray = ShaderRenderer.getModuleArray();
            if (moduleArray != null) {
                if (n3 <= this.values4.length) {
                    return;
                }
                this.values4 = Arrays.copyOf(this.values4, Math.max(n3, this.values4.length * 2));
            }
        }

        private BufferUtilHolder2 getBufferUtilHolder2() {
            int n = this.count97 * 10;
            return new BufferUtilHolder2(this.bufferUtil3, n == this.values4.length ? this.values4 : Arrays.copyOf(this.values4, n), this.count97);
        }
    }
}

