/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.api.Listener2;
import shit.api.Listener6;
import shit.api.Vec4fListener;
import shit.data.ColorData;
import shit.data.Vec4f;
import shit.manager.BufferUtilDataManager;
import shit.misc.Listener2Impl;
import shit.misc.RenderPipelines;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.render.Outline3;
import shit.render.RectRenderer;
import shit.render.RoundRectRenderer;
import shit.render.Shadow;
import shit.render.TextureRenderer;
import shit.render.TriangleRenderer;
import shit.type.Enum_WGunhs;
import shit.util.RenderUtil4;

@Environment(value=EnvType.CLIENT)
public final class Listener2ImplManager
implements AutoCloseable {
    private static final Comparator comparator = null;
    private static final List list8 = new java.util.ArrayList<>();
    private final Vec4fHolderHolder vec4fHolderHolder;
    private final Int2ObjectMap int2ObjectMap2 = new Int2ObjectOpenHashMap();
    private final AutoCloseableImpl2 autoCloseableImpl2 = new AutoCloseableImpl2();
    private final Listener2Impl listener2Impl = Listener2Impl.m444(262144L);
    private long time46;
    private static String[] texts6;
    private static final String a = null;

    public Listener2ImplManager() {
        this(192);
    }

    public Listener2ImplManager(int n) {
        this.vec4fHolderHolder = new Vec4fHolderHolder(Math.max(1, n));
    }

    public Listener2Impl getListener2Impl2() {
        return this.listener2Impl;
    }

    public void m142() {
        block3: {
            block2: {
                this.vec4fHolderHolder.m503();
                ObjectIterator objectIterator = this.int2ObjectMap2.values().iterator();
                Object var2_2 = null;
                if (!objectIterator.hasNext()) break block2;
                ColorDataHolder colorDataHolder = (ColorDataHolder)objectIterator.next();
                colorDataHolder.m592();
                if (null == null) break block3;
            }
            this.time46 = 0L;
        }
    }

    public void m68() {
        Object var2_1 = null;
        Listener2ImplManager listener2ImplManager = this;
        if (null != null) {
            if (listener2ImplManager.vec4fHolderHolder.isSet67()) {
                return;
            }
            this.setObj28(Inner2.m335(this.vec4fHolderHolder.getList2()));
            listener2ImplManager = this;
        }
        listener2ImplManager.autoCloseableImpl2.clear();
    }

    private void setObj82(Object object) {
        Vec4fListener vec4fListener = (Vec4fListener)object;
        this.vec4fHolderHolder.add(vec4fListener);
    }

    private long getLong6() {
        return this.time46++;
    }

    private void setObj15(Object object) {
        Vec4fHolder vec4fHolder = (Vec4fHolder)object;
        Object var4_3 = null;
        if (null != null) {
            if (vec4fHolder.getInt76() == 0) {
                return;
            }
            this.setObj28(Inner2.m335(vec4fHolder.getList10()));
        }
    }

    private void setObj28(Object object) {
        block6: {
            ArrayList<AutoCloseableImplData> arrayList;
            block5: {
                List list = (List)object;
                Object var4_3 = null;
                if (list.isEmpty()) {
                    return;
                }
                arrayList = new ArrayList<AutoCloseableImplData>(list.size());
                Iterator iterator = list.iterator();
                if (!iterator.hasNext()) break block5;
                ColorDataHolder2 colorDataHolder2 = (ColorDataHolder2)iterator.next();
                AutoCloseableImplData autoCloseableImplData = this.m491(colorDataHolder2);
                if (null == null) break block6;
                if (autoCloseableImplData != null) {
                    arrayList.add(autoCloseableImplData);
                }
            }
            this.setObj74(arrayList);
        }
    }

    private AutoCloseableImplData m491(Object object) {
        ColorDataHolder2 colorDataHolder2;
        block7: {
            block5: {
                ColorData colorData;
                block6: {
                    ColorDataHolder2 colorDataHolder22;
                    block4: {
                        colorDataHolder2 = (ColorDataHolder2)object;
                        Object var4_3 = null;
                        colorDataHolder22 = colorDataHolder2;
                        if (null == null) break block4;
                        if (colorDataHolder22.getList3().isEmpty()) break block5;
                        colorDataHolder22 = colorDataHolder2;
                    }
                    colorData = colorDataHolder22.getColorData2();
                    if (null == null) break block6;
                    if (colorData == null) break block7;
                    colorData = colorDataHolder2.getColorData2();
                }
                if (colorData.isSet()) break block7;
            }
            return null;
        }
        AutoCloseableImpl autoCloseableImpl = this.autoCloseableImpl2.acquire((Object)colorDataHolder2.getObj3(), colorDataHolder2.getColorData2());
        Iterator iterator = colorDataHolder2.getList3().iterator();
        if (iterator.hasNext()) {
            Vec4fListener vec4fListener = (Vec4fListener)iterator.next();
            Listener2ImplManager.m165(vec4fListener, autoCloseableImpl);
        }
        return new AutoCloseableImplData(Listener2ImplManager.m197((Object)colorDataHolder2.getObj3()), autoCloseableImpl);
    }

    private void setObj74(Object object) {
        int n;
        int n2;
        AutoCloseableImplData autoCloseableImplData;
        RenderPipeline renderPipeline5 = null;
        ArrayList<AutoCloseableImplData> arrayList;
        block11: {
            block10: {
                List list = (List)object;
                Object var4_3 = null;
                if (null != null) {
                    if (list.isEmpty()) {
                        return;
                    }
                    RenderUtil4.m486();
                }
                arrayList = new ArrayList<AutoCloseableImplData>(list.size());
                Iterator iterator = list.iterator();
                if (!iterator.hasNext()) break block10;
                autoCloseableImplData = (AutoCloseableImplData)iterator.next();
                n2 = autoCloseableImplData.getAutoCloseableImpl().isSet163() ? 1 : 0;
                if (null == null) break block11;
                if (null != null && n2 != 0) {
                    arrayList.add(autoCloseableImplData);
                }
            }
            n2 = arrayList.isEmpty() ? 1 : 0;
        }
        if (null != null) {
            if (n2 != 0) {
                return;
            }
            n2 = 0;
        }
        if ((n = n2) < arrayList.size()) {
            int n3;
            Object object2 = renderPipeline5 = ((AutoCloseableImplData)arrayList.get(n)).renderPipeline();
            while ((n3 = n + 1) < arrayList.size()) {
                object2 = ((AutoCloseableImplData)arrayList.get(n3)).renderPipeline();
                if (null == null) continue;
                if (object2 != renderPipeline5) break;
                ++n3;
                break;
            }
            this.flushPipelineRun(renderPipeline5, arrayList, n, n3);
            n = n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void flushPipelineRun(Object object, Object object2, int n, int n2) {
        RenderPass renderPass;
        RenderPipeline renderPipeline = (RenderPipeline)object;
        List list = (List)object2;
        int n3 = n;
        int n4 = n2;
        GpuTextureView gpuTextureView = RenderUtil4.getGpuTextureView6();
        Object var10_10 = null;
        if (gpuTextureView == null) {
            return;
        }
        GpuTextureView gpuTextureView2 = renderPipeline == RenderPipelines.renderPipeline16 ? null : RenderUtil4.getGpuTextureView4();
        RenderPass renderPass2 = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> a, gpuTextureView, OptionalInt.empty(), gpuTextureView2, OptionalDouble.empty());
        try {
            renderPass2.setPipeline(renderPipeline);
            RenderSystem.bindDefaultUniforms((RenderPass)renderPass2);
            int n5 = n3;
            if (n5 < n4) {
                ((AutoCloseableImplData)list.get(n5)).getAutoCloseableImpl().setObj89(renderPass2);
                ++n5;
                return;
            }
            renderPass = renderPass2;
        }
        catch (Throwable throwable) {
            RenderPass renderPass3 = renderPass2;
            {
                renderPass3.close();
            }
            throw throwable;
        }
        renderPass.close();
    }

    private static RenderPipeline m197(Object object) {
        RenderPipeline renderPipeline;
        block10: {
            int n;
            block9: {
                Enum_WGunhs enum_WGunhs = (Enum_WGunhs)((Object)object);
                Object var3_2 = null;
                n = Lambda.counts10[enum_WGunhs.ordinal()];
                if (null == null) break block9;
                switch (n) {
                    default: {
                        throw new MatchException(null, null);
                    }
                    case 1: {
                        renderPipeline = RenderPipelines.renderPipeline23;
                        break block10;
                    }
                    case 2: {
                        renderPipeline = RenderPipelines.renderPipeline17;
                        break block10;
                    }
                    case 3: {
                        renderPipeline = RenderPipelines.renderPipeline13;
                        break block10;
                    }
                    case 4: {
                        renderPipeline = RenderPipelines.renderPipeline11;
                        break block10;
                    }
                    case 5: {
                        renderPipeline = RenderPipelines.renderPipeline9;
                        break block10;
                    }
                    case 6: {
                        renderPipeline = RenderPipelines.renderPipeline16;
                        break block10;
                    }
                    case 7: {
                        n = ((Boolean)ClientSetting.INSTANCE.fontAntiAliasing.getValue()).booleanValue() ? 1 : 0;
                    }
                }
            }
            renderPipeline = n != 0 ? RenderPipelines.renderPipeline3 : RenderPipelines.renderPipeline18;
        }
        return renderPipeline;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void m165(Object object, Object object2) {
        Vec4fListener vec4fListener = (Vec4fListener)object;
        AutoCloseableImpl autoCloseableImpl = (AutoCloseableImpl)object2;
        Vec4fListener vec4fListener2 = vec4fListener;
        Objects.requireNonNull(vec4fListener2);
        Vec4fListener vec4fListener3 = vec4fListener2;
        Object var5_5 = null;
        int n = 0;
        Vec4fListener vec4fListener4 = vec4fListener3;
        Vec4fListener.Vec4fData5 record = (Vec4fListener.Vec4fData5)vec4fListener4;
        autoCloseableImpl.getListener2Impl().m321(((Vec4fListener.Vec4fData5)record).getText17(), ((Vec4fListener.Vec4fData5)record).getFloat11(), ((Vec4fListener.Vec4fData5)record).value16(), ((Vec4fListener.Vec4fData5)record).value17(), ((Vec4fListener.Vec4fData5)record).color12());
    }

    @Override
    public void close() {
        this.m142();
        this.autoCloseableImpl2.close();
        this.listener2Impl.close();
        this.int2ObjectMap2.clear();
    }

    private /* synthetic */ ColorDataHolder cfrlam$layer$0(int n, int n2) {
        return new ColorDataHolder(this, n);
    }

    /*
     * Handled unverifiable bytecode (illegal stack merge).
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    static {}

    public static void setTextArray(String[] stringArray) {
        texts6 = stringArray;
    }

    public static String[] getTextArray2() {
        return texts6;
    }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts10 = new int[0];

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static {}
    }

    @Environment(value=EnvType.CLIENT)
    static final class ColorDataHolder2 {
        private final Enum_WGunhs field40;
        private final ColorData colorData;
        private final List list26 = new ArrayList();
        private Vec4f vec4f;

        private ColorDataHolder2(Enum_WGunhs enum_WGunhs, ColorData colorData) {
            this.field40 = enum_WGunhs;
            this.colorData = colorData;
        }

        private Enum_WGunhs getObj3() {
            return this.field40;
        }

        private ColorData getColorData2() {
            return this.colorData;
        }

        private List getList3() {
            return this.list26;
        }

        private Vec4f getVec4f() {
            return this.vec4f;
        }

        private ColorDataData2 getColorDataData2() {
            return new ColorDataData2(this.field40, this.colorData);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void setObj11(Object object) {
            Vec4fListener vec4fListener = (Vec4fListener)object;
            this.list26.add(vec4fListener);
            Object var4_3 = null;
            Vec4f vec4f = vec4fListener.getVec4f2();
            Vec4f vec4f2 = this.vec4f;
            Vec4f vec4f3 = vec4f2.m238(vec4f);
            this.vec4f = vec4f3;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void setObj38(Object object) {
            ColorDataHolder2 colorDataHolder2 = (ColorDataHolder2)object;
            Object var4_3 = null;
            boolean bl = colorDataHolder2.list26.isEmpty();
            Vec4f vec4f = this.vec4f;
            Vec4f vec4f2 = vec4f.m238(colorDataHolder2.vec4f);
            this.vec4f = vec4f2;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean isSet170() {
            Object var2_1 = null;
            ColorDataHolder2 colorDataHolder2 = this;
            if (null != null) {
                if (colorDataHolder2.list26.isEmpty()) return false;
                colorDataHolder2 = this;
            }
            ColorData colorData = colorDataHolder2.colorData;
            if (null != null) {
                if (colorData == null) return true;
                colorData = this.colorData;
            }
            boolean bl = colorData.isSet();
            if (null == null) return bl;
            if (!bl) return false;
            return true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        long getLong7() {
            Object var1_1 = null;
            List list = this.list26;
            if (null != null) {
                if (list.isEmpty()) {
                    return Long.MAX_VALUE;
                }
                list = (List)this.list26.getFirst();
            }
            long l = ((Vec4fListener)((Object)list)).sequence();
            return l;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Vec4fHolder2 {
        private final Vec4f vec4f;
        private final int count99;
        private final int count217;
        private final List list3 = new ArrayList();
        private Vec4fHolder2[] vec4fHolder2s;
        private int count167;

        private Vec4fHolder2(Vec4f vec4f, int n, int n2) {
            this.vec4f = vec4f;
            this.count99 = n;
            this.count217 = n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void setObj79(Object object) {
            Vec4fListener vec4fListener = (Vec4fListener)object;
            ++this.count167;
            Object var4_3 = null;
            Vec4fHolder2 vec4fHolder2 = this;
            int n = vec4fHolder2.list3.size();
            int n2 = 24;
            if (n >= n2) return;
            Vec4fHolder2 vec4fHolder22 = this;
            vec4fHolder22.m466();
        }

        private int getInt60() {
            return this.count167;
        }

        private void setObj81(Object object) {
            Vec4fHolder2[] vec4fHolder2Array;
            int n;
            int n2;
            List list = (List)object;
            list.addAll(this.list3);
            Object var4_3 = null;
            Vec4fHolder2[] vec4fHolder2Array2 = this.vec4fHolder2s;
            if (null != null) {
                if (vec4fHolder2Array2 == null) {
                    return;
                }
                vec4fHolder2Array2 = this.vec4fHolder2s;
            }
            if ((n2 = 0) < (n = (vec4fHolder2Array = vec4fHolder2Array2).length)) {
                Vec4fHolder2 vec4fHolder2 = vec4fHolder2Array[n2];
                vec4fHolder2.setObj81(list);
                ++n2;
            }
        }

        private boolean m452(Object object) {
            boolean bl;
            block5: {
                block4: {
                    Vec4fListener vec4fListener = (Vec4fListener)object;
                    Vec4fHolder2[] vec4fHolder2Array = this.vec4fHolder2s;
                    int n = vec4fHolder2Array.length;
                    Object var4_5 = null;
                    int n2 = 0;
                    if (n2 >= n) break block4;
                    Vec4fHolder2 vec4fHolder2 = vec4fHolder2Array[n2];
                    if (null == null) break block4;
                    bl = vec4fHolder2.vec4f.m416(vec4fListener.bounds());
                    if (null == null) break block5;
                    if (bl) {
                        vec4fHolder2.setObj79(vec4fListener);
                        return true;
                    }
                    ++n2;
                }
                bl = false;
            }
            return bl;
        }

        private void m466() {
            Vec4fListener vec4fListener;
            Object var2_1 = null;
            Vec4fHolder2 vec4fHolder2 = this;
            if (null != null) {
                if (vec4fHolder2.vec4fHolder2s != null) {
                    return;
                }
                vec4fHolder2 = this;
            }
            float f = vec4fHolder2.vec4f.value8() * 0.5f;
            float f2 = this.vec4f.value9() * 0.5f;
            this.vec4fHolder2s = new Vec4fHolder2[]{new Vec4fHolder2(Vec4f.m186(this.vec4f.value6(), this.vec4f.value7(), f, f2), this.count99 + 1, this.count217), new Vec4fHolder2(Vec4f.m186(this.vec4f.value6() + f, this.vec4f.value7(), f, f2), this.count99 + 1, this.count217), new Vec4fHolder2(Vec4f.m186(this.vec4f.value6(), this.vec4f.value7() + f2, f, f2), this.count99 + 1, this.count217), new Vec4fHolder2(Vec4f.m186(this.vec4f.value6() + f, this.vec4f.value7() + f2, f, f2), this.count99 + 1, this.count217)};
            Iterator iterator = this.list3.iterator();
            if (iterator.hasNext() && this.m452(vec4fListener = (Vec4fListener)iterator.next())) {
                iterator.remove();
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class AutoCloseableImpl
    implements AutoCloseable {
        private final Enum_WGunhs field40;
        private final Listener2 listener2;
        private ColorData colorData;

        private AutoCloseableImpl(Enum_WGunhs enum_WGunhs, Listener2 listener2) {
            this.field40 = enum_WGunhs;
            this.listener2 = listener2;
        }

        private static AutoCloseableImpl m737(Object object) {
            Enum_WGunhs enum_WGunhs = (Enum_WGunhs)((Object)object);
            return switch (Lambda.counts10[enum_WGunhs.ordinal()]) {
                default -> throw new MatchException(null, null);
                case 1 -> new AutoCloseableImpl(enum_WGunhs, Shadow.getShadow2());
                case 2 -> new AutoCloseableImpl(enum_WGunhs, RoundRectRenderer.getRoundRectRenderer2());
                case 3 -> new AutoCloseableImpl(enum_WGunhs, Outline3.getOutline32());
                case 4 -> new AutoCloseableImpl(enum_WGunhs, RectRenderer.getRectRenderer());
                case 5 -> new AutoCloseableImpl(enum_WGunhs, TriangleRenderer.getTriangleRenderer2());
                case 6 -> new AutoCloseableImpl(enum_WGunhs, TextureRenderer.getTextureRenderer2());
                case 7 -> new AutoCloseableImpl(enum_WGunhs, Listener2Impl.getListener2Impl3());
            };
        }

        private Enum_WGunhs getObj12() {
            return this.field40;
        }

        private ColorData getColorData3() {
            return this.colorData;
        }

        private Shadow getShadow() {
            return (Shadow)this.listener2;
        }

        private RoundRectRenderer getRoundRectRenderer() {
            return (RoundRectRenderer)this.listener2;
        }

        private Outline3 getOutline3() {
            return (Outline3)this.listener2;
        }

        private RectRenderer getRectRenderer2() {
            return (RectRenderer)this.listener2;
        }

        private TriangleRenderer getTriangleRenderer() {
            return (TriangleRenderer)this.listener2;
        }

        private TextureRenderer getTextureRenderer() {
            return (TextureRenderer)this.listener2;
        }

        private Listener2Impl getListener2Impl() {
            return (Listener2Impl)this.listener2;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void setObj4(Object object) {
            ColorData colorData;
            this.colorData = colorData = (ColorData)object;
            Object var4_3 = null;
            AutoCloseableImpl autoCloseableImpl = this;
            Listener2 listener2 = autoCloseableImpl.listener2;
            Objects.requireNonNull(listener2);
            Listener2 listener22 = listener2;
            int n = 0;
            Listener2 listener23 = listener22;
            Shadow shadow = (Shadow)listener23;
            shadow.m535(colorData.count22(), colorData.count23(), colorData.count24(), colorData.count25());
            RoundRectRenderer roundRectRenderer = (RoundRectRenderer)listener22;
            roundRectRenderer.m776(colorData.count22(), colorData.count23(), colorData.count24(), colorData.count25());
            Outline3 outline3 = (Outline3)listener22;
            outline3.m649(colorData.count22(), colorData.count23(), colorData.count24(), colorData.count25());
            RectRenderer rectRenderer = (RectRenderer)listener22;
            rectRenderer.m1032(colorData.count22(), colorData.count23(), colorData.count24(), colorData.count25());
            TriangleRenderer triangleRenderer = (TriangleRenderer)listener22;
            triangleRenderer.m670(colorData.count22(), colorData.count23(), colorData.count24(), colorData.count25());
            TextureRenderer textureRenderer = (TextureRenderer)listener22;
            textureRenderer.m656(colorData.count22(), colorData.count23(), colorData.count24(), colorData.count25());
            Listener2Impl listener2Impl = (Listener2Impl)listener22;
            listener2Impl.m96(colorData.count22(), colorData.count23(), colorData.count24(), colorData.count25());
        }

        /*
         * Enabled aggressive block sorting
         */
        private void m545() {
            Listener2 listener2 = this.listener2;
            Objects.requireNonNull(listener2);
            Listener2 listener22 = listener2;
            int n = 0;
            Object var2_3 = null;
            Listener2 listener23 = listener22;
            Shadow shadow = (Shadow)listener23;
            shadow.m226();
            RoundRectRenderer roundRectRenderer = (RoundRectRenderer)listener22;
            roundRectRenderer.m761();
            Outline3 outline3 = (Outline3)listener22;
            outline3.m648();
            RectRenderer rectRenderer = (RectRenderer)listener22;
            rectRenderer.m376();
            TriangleRenderer triangleRenderer = (TriangleRenderer)listener22;
            triangleRenderer.m941();
            TextureRenderer textureRenderer = (TextureRenderer)listener22;
            textureRenderer.m44();
            Listener2Impl listener2Impl = (Listener2Impl)listener22;
            listener2Impl.m937();
        }

        private boolean isSet163() {
            return this.listener2.prepareBuffers();
        }

        private void setObj89(Object object) {
            RenderPass renderPass = (RenderPass)object;
            this.listener2.drawWithPass(renderPass);
        }

        private void m476() {
            this.m545();
            this.listener2.endFrame();
        }

        @Override
        public void close() {
            this.listener2.close();
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class ColorDataData  {
        private final Enum_WGunhs field40;
        private final ColorData colorData;

        private ColorDataData(Enum_WGunhs enum_WGunhs, ColorData colorData) {
            this.field40 = enum_WGunhs;
            this.colorData = colorData;
        }

        public Enum_WGunhs field2() {
            return this.field40;
        }

        public ColorData colorData() {
            return this.colorData;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class AutoCloseableImplData  {
        private final RenderPipeline renderPipeline;
        private final AutoCloseableImpl autoCloseableImpl;

        private AutoCloseableImplData(RenderPipeline renderPipeline, AutoCloseableImpl autoCloseableImpl) {
            this.renderPipeline = renderPipeline;
            this.autoCloseableImpl = autoCloseableImpl;
        }

        public RenderPipeline renderPipeline() {
            return this.renderPipeline;
        }

        public AutoCloseableImpl getAutoCloseableImpl() {
            return this.autoCloseableImpl;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class AutoCloseableImpl2
    implements AutoCloseable {
        private final Map map10 = new Object2ObjectOpenHashMap();
        private final List list30 = new ArrayList();

        private AutoCloseableImpl2() {
        }

        private AutoCloseableImpl acquire(Object object, Object object2) {
            AutoCloseableImpl autoCloseableImpl;
            Enum_WGunhs enum_WGunhs = (Enum_WGunhs)((Object)object);
            ColorData colorData = (ColorData)object2;
            ColorDataData colorDataData2 = new ColorDataData(enum_WGunhs, colorData);
            Object var6_6 = null;
            Deque deque = (Deque)this.map10.computeIfAbsent(colorDataData2, colorDataData -> new ArrayDeque());
            AutoCloseableImpl autoCloseableImpl2 = autoCloseableImpl = (AutoCloseableImpl)deque.pollFirst();
            if (null != null) {
                if (autoCloseableImpl2 == null) {
                    autoCloseableImpl = AutoCloseableImpl.m737((Object)enum_WGunhs);
                }
                autoCloseableImpl.setObj4(colorData);
                this.list30.add(autoCloseableImpl);
                autoCloseableImpl2 = autoCloseableImpl;
            }
            return autoCloseableImpl2;
        }

        private void clear() {
            block3: {
                block2: {
                    Iterator iterator = this.list30.iterator();
                    Object var2_2 = null;
                    if (!iterator.hasNext()) break block2;
                    AutoCloseableImpl autoCloseableImpl = (AutoCloseableImpl)iterator.next();
                    autoCloseableImpl.m476();
                    ((Deque)this.map10.computeIfAbsent(new ColorDataData(autoCloseableImpl.getObj12(), autoCloseableImpl.getColorData3()), colorDataData -> new ArrayDeque())).addLast(autoCloseableImpl);
                    if (null == null) break block3;
                }
                this.list30.clear();
            }
        }

        @Override
        public void close() {
            block2: {
                this.clear();
                Object var1_1 = null;
                for (Object dequeObj : this.map10.values()) {
                    Deque deque = (Deque)dequeObj;
                    if (null != null) {
                        Iterator iterator = deque.iterator();
                        if (!iterator.hasNext()) break;
                        AutoCloseableImpl autoCloseableImpl = (AutoCloseableImpl)iterator.next();
                        autoCloseableImpl.close();
                        if (null == null) continue;
                        break;
                    }
                    break block2;
                }
                this.map10.clear();
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Inner2 {
        private Inner2() {
        }

        private static List m335(Object object) {
            Object object2;
            List resultList = null;
            int n11 = 0;
            block21: {
                ColorDataHolder2 colorDataHolder2;
                Enum_WGunhs enum_WGunhs;
                block24: {
                    int n;
                    boolean[] blArray;
                    int[] nArray;
                    ArrayList arrayList;
                    List list;
                    block23: {
                        int n2;
                        block22: {
                            int n3;
                            int n4;
                            block18: {
                                int n5;
                                int n6;
                                block20: {
                                    block19: {
                                        list = (List)object;
                                        n4 = list.size();
                                        Object var3_3 = null;
                                        if (n4 <= 1) {
                                            return list;
                                        }
                                        arrayList = new ArrayList(n4);
                                        int n7 = 0;
                                        if (n7 >= n4) break block19;
                                        n6 = arrayList.add(new ArrayList()) ? 1 : 0;
                                        if (null == null) break block20;
                                        ++n7;
                                    }
                                    n6 = n4;
                                }
                                nArray = new int[n6];
                                int n8 = n5 = 0;
                                while (n8 < n4) {
                                    object2 = (ColorDataHolder2)list.get(n5);
                                    n3 = n5 + 1;
                                    if (null != null) {
                                        int n9 = n3;
                                        if (n9 < n4) {
                                            ColorDataHolder2 colorDataHolder22 = (ColorDataHolder2)list.get(n9);
                                            if (null != null) {
                                                n8 = Inner2.m707(object2, colorDataHolder22) ? 1 : 0;
                                                if (null == null) continue;
                                                if (n8 != 0) {
                                                    ((List)arrayList.get(n5)).add(n9);
                                                    int n10 = n9;
                                                    nArray[n10] = nArray[n10] + 1;
                                                }
                                                ++n9;
                                            }
                                        }
                                        ++n5;
                                        break;
                                    }
                                    break block18;
                                }
                                n3 = n4;
                            }
                            blArray = new boolean[n3];
                            resultList = new ArrayList(n4);
                            enum_WGunhs = null;
                            n11 = 0;
                            if (n11 >= n4) break block21;
                            n2 = n = Inner2.m622(list, blArray, nArray, enum_WGunhs);
                            if (null == null) break block22;
                            if (n2 >= 0) break block23;
                            n2 = Inner2.m228(blArray);
                        }
                        n = n2;
                    }
                    colorDataHolder2 = (ColorDataHolder2)list.get(n);
                    n11 += Inner2.m99(n, arrayList, nArray, blArray);
                    ColorDataData2 colorDataData2 = colorDataHolder2.getColorDataData2();
                    int n12 = Inner2.m286(list, blArray, nArray, colorDataData2);
                    if (n12 < 0) break block24;
                    colorDataHolder2.setObj38((ColorDataHolder2)list.get(n12));
                    n11 += Inner2.m99(n12, arrayList, nArray, blArray);
                    if (null == null) break block21;
                }
                resultList.add(colorDataHolder2);
                enum_WGunhs = colorDataHolder2.getObj3();
            }
            return resultList;
        }

        private static boolean m707(Object object, Object object2) {
            ColorDataHolder2 colorDataHolder2 = (ColorDataHolder2)object;
            ColorDataHolder2 colorDataHolder22 = (ColorDataHolder2)object2;
            return colorDataHolder2.getVec4f().m942(colorDataHolder22.getVec4f());
        }

        private static int m622(Object object, Object object2, Object object3, Object object4) {
            int n;
            block10: {
                int n2;
                block9: {
                    int n3;
                    block12: {
                        Enum_WGunhs enum_WGunhs;
                        Enum_WGunhs enum_WGunhs2;
                        block15: {
                            List list;
                            block14: {
                                int n4 = 0;
                                block13: {
                                    block11: {
                                        list = (List)object;
                                        boolean[] blArray = (boolean[])object2;
                                        int[] nArray = (int[])object3;
                                        enum_WGunhs2 = (Enum_WGunhs)((Object)object4);
                                        n2 = -1;
                                        Object var9_9 = null;
                                        n3 = 0;
                                        if (n3 >= list.size()) break block9;
                                        n = blArray[n3] ? 1 : 0;
                                        if (null == null) break block10;
                                        if (null == null) break block11;
                                        if (n != 0) break block12;
                                        n4 = nArray[n3];
                                    }
                                    if (null != null) {
                                        if (n4 > 0) {
                                        }
                                        n4 = n2;
                                    }
                                    if (null == null) break block13;
                                    if (n4 >= 0) break block14;
                                    n4 = n3;
                                }
                                n2 = n4;
                            }
                            enum_WGunhs = enum_WGunhs2;
                            if (null == null) break block15;
                            if (enum_WGunhs == null) break block12;
                            enum_WGunhs = ((ColorDataHolder2)list.get(n3)).getObj3();
                        }
                        if (enum_WGunhs == enum_WGunhs2) {
                            return n3;
                        }
                    }
                    ++n3;
                }
                n = n2;
            }
            return n;
        }

        private static int m286(Object object, Object object2, Object object3, Object object4) {
            int n;
            block3: {
                block2: {
                    int n2;
                    block5: {
                        int n3 = 0;
                        block7: {
                            block6: {
                                ColorDataData2 colorDataData2;
                                List list;
                                block4: {
                                    list = (List)object;
                                    boolean[] blArray = (boolean[])object2;
                                    int[] nArray = (int[])object3;
                                    colorDataData2 = (ColorDataData2)object4;
                                    n2 = 0;
                                    Object var9_9 = null;
                                    if (n2 >= list.size()) break block2;
                                    n = blArray[n2] ? 1 : 0;
                                    if (null == null) break block3;
                                    if (null == null) break block4;
                                    if (n != 0) break block5;
                                    n3 = nArray[n2];
                                }
                                if (null == null) break block6;
                                if (n3 != 0) break block5;
                                n3 = ((ColorDataHolder2)list.get(n2)).getColorDataData2().equals(colorDataData2) ? 1 : 0;
                            }
                            if (null == null) break block7;
                            if (n3 == 0) break block5;
                            n3 = n2;
                        }
                        return n3;
                    }
                    ++n2;
                }
                n = -1;
            }
            return n;
        }

        private static int m228(Object object) {
            int n;
            block3: {
                block2: {
                    int n2;
                    block5: {
                        int n3 = 0;
                        block4: {
                            boolean[] blArray = (boolean[])object;
                            n2 = 0;
                            Object var3_3 = null;
                            if (n2 >= blArray.length) break block2;
                            n = blArray[n2] ? 1 : 0;
                            if (null == null) break block3;
                            if (null == null) break block4;
                            if (n != 0) break block5;
                            n3 = n2;
                        }
                        return n3;
                    }
                    ++n2;
                }
                n = -1;
            }
            return n;
        }

        private static int m99(int n, Object object, Object object2, Object object3) {
            int n2;
            block9: {
                block8: {
                    int n3;
                    int[] nArray;
                    List list;
                    int n4;
                    int n5;
                    boolean[] blArray;
                    boolean[] blArray2;
                    block6: {
                        block7: {
                            int n6;
                            block4: {
                                block5: {
                                    n4 = n;
                                    list = (List)object;
                                    nArray = (int[])object2;
                                    blArray2 = (boolean[])object3;
                                    Object var9_8 = null;
                                    n6 = n4;
                                    if (null == null) break block4;
                                    if (n6 < 0) break block5;
                                    blArray = blArray2;
                                    n5 = n4;
                                    if (null == null) break block6;
                                    if (!blArray[n5]) break block7;
                                }
                                n6 = 0;
                            }
                            return n6;
                        }
                        blArray = blArray2;
                        n5 = n4;
                    }
                    blArray[n5] = true;
                    Iterator iterator = ((List)list.get(n4)).iterator();
                    if (!iterator.hasNext()) break block8;
                    n2 = (Integer)iterator.next();
                    if (null == null) break block9;
                    int n7 = n3 = n2;
                    nArray[n7] = nArray[n7] - 1;
                }
                n2 = 1;
            }
            return n2;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class ColorDataHolder {
        private final Listener2ImplManager listener2ImplManager;
        private final int count235;
        private ColorData colorData;

        private ColorDataHolder(Listener2ImplManager listener2ImplManager, int n) {
            this.listener2ImplManager = listener2ImplManager;
            this.count235 = n;
        }

        public void m592() {
            this.colorData = null;
        }

        public void m641(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object) {
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
            this.listener2ImplManager.setObj82(new Vec4fListener.Vec4fData6(this.count235, this.listener2ImplManager.getLong6(), Vec4f.m186(f10, f11, f12, f13), this.colorData, f14, f15, f16, f17, f18, color));
        }

        public void m10(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Object object) {
            float f9 = f;
            float f10 = f2;
            float f11 = f3;
            float f12 = f4;
            float f13 = f5;
            float f14 = f6;
            float f15 = f7;
            float f16 = f8;
            Color color = (Color)object;
            this.m553(f9, f10, f11, f12, f13, f14, f15, f16, color, color, color, color);
        }

        public void m553(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Object object, Object object2, Object object3, Object object4) {
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
            this.listener2ImplManager.setObj82(new Vec4fListener.Vec4fData7(this.count235, this.listener2ImplManager.getLong6(), Vec4f.m186(f9, f10, f11, f12), this.colorData, f13, f14, f15, f16, color, color2, color3, color4));
        }

        public void m835(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object) {
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
            this.listener2ImplManager.setObj82(new Vec4fListener.Vec4fData3(this.count235, this.listener2ImplManager.getLong6(), Vec4f.m186(f10, f11, f12, f13), this.colorData, f14, f15, f16, f17, f18, color));
        }

        public void m759(float f, float f2, float f3, float f4, Object object) {
            float f5 = f;
            float f6 = f2;
            float f7 = f3;
            float f8 = f4;
            Color color = (Color)object;
            this.m477(f5, f6, f7, f8, color, color, color, color);
        }

        public void m477(float f, float f2, float f3, float f4, Object object, Object object2, Object object3, Object object4) {
            float f5 = f;
            float f6 = f2;
            float f7 = f3;
            float f8 = f4;
            Color color = (Color)object;
            Color color2 = (Color)object2;
            Color color3 = (Color)object3;
            Color color4 = (Color)object4;
            this.listener2ImplManager.setObj82(new Vec4fListener.Vec4fData2(this.count235, this.listener2ImplManager.getLong6(), Vec4f.m186(f5, f6, f7, f8), this.colorData, color, color2, color3, color4));
        }

        public void m653(Object object, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Object object2) {
            Listener6 listener6 = (Listener6)object;
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
            this.m677(listener6, f10, f11, f12, f13, f14, f14, f14, f14, f15, f16, f17, f18, color);
        }

        public void m677(Object object, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, Object object2) {
            Listener6 listener6 = (Listener6)object;
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
            this.listener2ImplManager.setObj82(new Vec4fListener.Vec4fData(this.count235, this.listener2ImplManager.getLong6(), Vec4f.m186(f13, f14, f15, f16), this.colorData, listener6, f17, f18, f19, f20, f21, f22, f23, f24, color));
        }

        public void m442(Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
            String string = (String)object;
            Object object7 = object2;
            Object object8 = object3;
            Object object9 = object4;
            Color color = (Color)object5;
            BufferUtilDataManager bufferUtilDataManager = (BufferUtilDataManager)object6;
            Listener2Impl listener2Impl = this.listener2ImplManager.getListener2Impl2();
            Object var14_14 = null;
            float f = bufferUtilDataManager != null ? listener2Impl.m1019(string, (float)object9, bufferUtilDataManager) : listener2Impl.m494(string, (float)object9);
            float f2 = bufferUtilDataManager != null ? listener2Impl.m540((float)object9, bufferUtilDataManager) : listener2Impl.m567((float)object9);
            this.listener2ImplManager.setObj82(new Vec4fListener.Vec4fData5(this.count235, this.listener2ImplManager.getLong6(), Vec4f.m186((float)object7, (float)object8, f, f2), this.colorData, string, (float)object7, (float)object8, (float)object9, color, bufferUtilDataManager));
            Module.setTextArray9(new String[5]);
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Vec4fHolderHolder {
        private final int count230;
        private final Int2ObjectMap int2ObjectMap = new Int2ObjectOpenHashMap();

        private Vec4fHolderHolder(int n) {
            this.count230 = n;
        }

        private void add(Object object) {
            Vec4fListener vec4fListener = (Vec4fListener)object;
            ((Vec4fHolder)this.int2ObjectMap.computeIfAbsent(vec4fListener.layer(), n -> new Vec4fHolder(this.count230))).setObj26(vec4fListener);
        }

        private boolean isSet67() {
            return this.int2ObjectMap.isEmpty();
        }

        private Vec4fHolder m603(int n) {
            int n2 = n;
            return (Vec4fHolder)this.int2ObjectMap.get(n2);
        }

        /*
         * Enabled aggressive block sorting
         */
        private List getList2() {
            ArrayList arrayList = new ArrayList(this.int2ObjectMap.keySet());
            Collections.sort(arrayList);
            ArrayList<ColorDataHolder2> arrayList2 = new ArrayList<ColorDataHolder2>();
            Iterator iterator = arrayList.iterator();
            Object var2_4 = null;
            boolean bl = iterator.hasNext();
            while (bl) {
                Vec4fHolder vec4fHolder;
                int n = (Integer)iterator.next();
                Vec4fHolder vec4fHolder2 = vec4fHolder = (Vec4fHolder)this.int2ObjectMap.get(n);
                Iterator iterator2 = vec4fHolder2.getList10().iterator();
                if (!iterator2.hasNext()) break;
                ColorDataHolder2 colorDataHolder2 = (ColorDataHolder2)iterator2.next();
                bl = colorDataHolder2.isSet170();
            }
            return arrayList2;
        }

        private void m503() {
            this.int2ObjectMap.clear();
        }

        private void setInt11(int n) {
            int n2 = n;
            this.int2ObjectMap.remove(n2);
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Vec4fHolder {
        private final int count158;
        private final List list13 = new ArrayList();
        private Vec4fHolder2 vec4fHolder2;
        private Vec4f vec4f;

        private Vec4fHolder(int n) {
            this.count158 = n;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void setObj26(Object object) {
            Vec4fListener vec4fListener = (Vec4fListener)object;
            Object var4_3 = null;
            Vec4fHolder vec4fHolder = this;
            vec4fHolder.vec4f = this.vec4f.m238(vec4fListener.bounds());
            Vec4fHolder2 vec4fHolder2 = this.vec4fHolder2;
            vec4fHolder2.setObj79(vec4fListener);
        }

        /*
         * Enabled aggressive block sorting
         */
        private int getInt76() {
            Object var2_1 = null;
            Vec4fHolder vec4fHolder = this;
            int n = vec4fHolder.list13.size();
            return n;
        }

        private List getList10() {
            ArrayList arrayList = new ArrayList(this.getInt76());
            Object var2_2 = null;
            Vec4fHolder2 vec4fHolder2 = this.vec4fHolder2;
            if (null != null) {
                if (vec4fHolder2 == null) {
                    arrayList.addAll(this.list13);
                }
                vec4fHolder2 = this.vec4fHolder2;
            }
            vec4fHolder2.setObj81(arrayList);
            arrayList.sort(comparator);
            return Inner.plan(arrayList);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void m33() {
            Object var2_1 = null;
            Vec4f vec4f = this.vec4f;
            Vec4f vec4f2 = Vec4fHolder.m991(vec4f);
            Vec4f vec4f3 = vec4f2;
            this.vec4fHolder2 = new Vec4fHolder2(vec4f3, 0, 5);
            Iterator iterator = this.list13.iterator();
            if (iterator.hasNext()) {
                Vec4fListener vec4fListener = (Vec4fListener)iterator.next();
                this.vec4fHolder2.setObj79(vec4fListener);
                return;
            }
            this.list13.clear();
        }

        private static Vec4f m991(Object object) {
            Vec4f vec4f = (Vec4f)object;
            float f = Math.max(Math.max(vec4f.value8(), vec4f.value9()), 1.0f);
            return Vec4f.m186(vec4f.value6() - 1.0f, vec4f.value7() - 1.0f, f + 2.0f, f + 2.0f);
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Inner {
        private Inner() {
        }

        private static List plan(Object object) {
            List list = (List)object;
            LinkedHashMap<ColorDataData2, ColorDataHolder2> linkedHashMap = new LinkedHashMap<ColorDataData2, ColorDataHolder2>();
            Object var3_3 = null;
            Iterator iterator = list.iterator();
            if (iterator.hasNext()) {
                Vec4fListener vec4fListener = (Vec4fListener)iterator.next();
                ColorDataData2 colorDataData2 = new ColorDataData2(vec4fListener.getObj15(), vec4fListener.scissor());
                linkedHashMap.computeIfAbsent(colorDataData2, arg_0 -> Inner.makeHolder0(vec4fListener, arg_0)).setObj11(vec4fListener);
            }
            ArrayList<ColorDataHolder2> arrayList = new ArrayList<ColorDataHolder2>(linkedHashMap.values());
            arrayList.sort(Comparator.<ColorDataHolder2>comparingInt(colorDataHolder2 -> list8.indexOf((Object)colorDataHolder2.getObj3())).thenComparingLong(cdh -> cdh.getLong7()));
            Iterator iterator2 = arrayList.iterator();
            if (iterator2.hasNext()) {
                ColorDataHolder2 colorDataHolder2 = (ColorDataHolder2)iterator2.next();
                colorDataHolder2.getList3().sort(comparator);
            }
            return arrayList;
        }

        private static ColorDataHolder2 makeHolder0(Vec4fListener vec4fListener, ColorDataData2 colorDataData2) {
            return new ColorDataHolder2(vec4fListener.getObj15(), vec4fListener.scissor());
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class ColorDataData2  {
        private final Enum_WGunhs field40;
        private final ColorData colorData;

        private ColorDataData2(Enum_WGunhs enum_WGunhs, ColorData colorData) {
            this.field40 = enum_WGunhs;
            this.colorData = colorData;
        }

        public Enum_WGunhs getObj6() {
            return this.field40;
        }

        public ColorData getColorData() {
            return this.colorData;
        }
    }
}

