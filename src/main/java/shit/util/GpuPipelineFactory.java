/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.DynamicUniformStorage;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.render.ProjectionMatrix2;
import net.minecraft.client.render.TextureTransform;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.Identifier;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import shit.gui.Texture;
import shit.manager.AutoCloseableTracker;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.util.ResourceLoader;
import shit.util.MC;
import shit.util.RenderScissorHelper;

@Environment(value=EnvType.CLIENT)
public class GpuPipelineFactory {
    private static final ProjectionMatrix2 field25 = null;
    private static AutoCloseableImpl autoCloseableImpl2;
    private static long time7;
    private static String text597;

    public static GpuBufferSlice m1027(Object object, Object object2, int n, int n2, Object object3) {
        String string = (String)object;
        String string2 = (String)object2;
        int n3 = n;
        int n4 = n2;
        DynamicUniformStorage.Uploadable uploadable = (DynamicUniformStorage.Uploadable)object3;
        return Inner.write(string, string2, n3, n4, uploadable);
    }

    public static void m353() {
        Inner.m390();
    }

    public static void m981() {
        ++time7;
    }

    public static long getLong4() {
        return time7;
    }

    public static AutoCloseableImpl getAutoCloseableImpl2() {
        return autoCloseableImpl2;
    }

    public static double getDouble18() {
        return ClientSetting.INSTANCE.getDouble7();
    }

    public static float getFloat57() {
        return (float)((double)MC.mc.getWindow().getFramebufferWidth() / GpuPipelineFactory.getDouble18());
    }

    public static float getFloat53() {
        return (float)((double)MC.mc.getWindow().getFramebufferHeight() / GpuPipelineFactory.getDouble18());
    }

    public static double m1015(double d) {
        double d2 = d;
        return d2 * (double)MC.mc.getWindow().getScaleFactor() / GpuPipelineFactory.getDouble18();
    }

    public static double m171(double d) {
        double d2 = d;
        return d2 * (double)MC.mc.getWindow().getScaleFactor() / GpuPipelineFactory.getDouble18();
    }

    public static ColorData m13(float f, float f2, float f3, float f4) {
        float f5 = f;
        float f6 = f2;
        float f7 = f3;
        float f8 = f4;
        return RenderScissorHelper.m74(f5, f6, f7, f8);
    }

    public static ColorData m797(float f, float f2, float f3, float f4, float f5) {
        float f6 = f;
        float f7 = f2;
        float f8 = f3;
        float f9 = f4;
        float f10 = f5;
        return RenderScissorHelper.m830(f6, f7, f8, f9, f10);
    }

    public static void m486() {
        GpuBufferSlice gpuBufferSlice = field25.set(GpuPipelineFactory.getFloat57(), GpuPipelineFactory.getFloat53());
        RenderSystem.setProjectionMatrix((GpuBufferSlice)gpuBufferSlice, (ProjectionType)ProjectionType.ORTHOGRAPHIC);
    }

    public static GpuTextureView getGpuTextureView6() {
        block3: {
            AutoCloseableImpl autoCloseableImpl;
            block2: {
                Object var1 = null;
                autoCloseableImpl = autoCloseableImpl2;
                if (null == null) break block2;
                if (autoCloseableImpl == null) break block3;
                autoCloseableImpl = autoCloseableImpl2;
            }
            return autoCloseableImpl.getGpuTextureView();
        }
        return MC.mc.getFramebuffer().getColorAttachmentView();
    }

    public static GpuTextureView getGpuTextureView4() {
        block3: {
            AutoCloseableImpl autoCloseableImpl;
            block2: {
                Object var1 = null;
                autoCloseableImpl = autoCloseableImpl2;
                if (null == null) break block2;
                if (autoCloseableImpl == null) break block3;
                autoCloseableImpl = autoCloseableImpl2;
            }
            return autoCloseableImpl.getGpuTextureView5();
        }
        return MC.mc.getFramebuffer().getDepthAttachmentView();
    }

    public static Data m1023(int n) {
        int n2 = n;
        return GpuPipelineFactory.m369(n2, true);
    }

    public static Data m369(int n, boolean bl) {
        int n2 = n;
        boolean bl2 = bl;
        if (bl2) {
            GpuPipelineFactory.m486();
        }
        GpuTextureView gpuTextureView = GpuPipelineFactory.getGpuTextureView6();
        GpuTextureView gpuTextureView2 = GpuPipelineFactory.getGpuTextureView4();
        if (gpuTextureView == null) {
            return null;
        }
        int n3 = n2 / 4 * 6;
        GpuBuffer gpuBuffer = GpuPipelineFactory.m577(n3);
        GpuBufferSlice gpuBufferSlice = GpuPipelineFactory.m998(RenderSystem.getModelViewMatrix(), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector3f(0.0f, 0.0f, 0.0f), TextureTransform.DEFAULT_TEXTURING.getTransformSupplier());
        return new Data(gpuTextureView, gpuTextureView2, GpuPipelineFactory.getIndexType(), gpuBuffer, n3, gpuBufferSlice);
    }

    public static GpuBuffer m577(int n) {
        int n2 = n;
        RenderSystem.ShapeIndexBuffer shapeIndexBuffer = RenderSystem.getSequentialBuffer((VertexFormat.DrawMode)VertexFormat.DrawMode.QUADS);
        return shapeIndexBuffer.getIndexBuffer(n2);
    }

    public static VertexFormat.IndexType getIndexType() {
        RenderSystem.ShapeIndexBuffer shapeIndexBuffer = RenderSystem.getSequentialBuffer((VertexFormat.DrawMode)VertexFormat.DrawMode.QUADS);
        return shapeIndexBuffer.getIndexType();
    }

    public static GpuBufferSlice m998(Object object, Object object2, Object object3, Object object4) {
        Matrix4fc matrix4fc = (Matrix4fc)object;
        Vector4fc vector4fc = (Vector4fc)object2;
        Vector3fc vector3fc = (Vector3fc)object3;
        Matrix4fc matrix4fc2 = (Matrix4fc)object4;
        return RenderSystem.getDynamicUniforms().write(matrix4fc, vector4fc, vector3fc, matrix4fc2);
    }

    public static GpuBufferSlice getGpuBufferSlice2() {
        return GpuPipelineFactory.m998(RenderSystem.getModelViewMatrix(), new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), new Vector3f(0.0f, 0.0f, 0.0f), TextureTransform.DEFAULT_TEXTURING.getTransformSupplier());
    }

    /*
     * Handled unverifiable bytecode (illegal stack merge).
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    static {}

    public static void setText6(String string) {
        text597 = string;
    }

    public static String getText23() {
        return text597;
    }

    @Environment(value=EnvType.CLIENT)
    public static final class AutoCloseableImpl
    implements AutoCloseable {
        private Texture texture4;
        private GpuTexture gpuTexture;
        private GpuTextureView gpuTextureView4;
        private final Identifier field62;
        private final boolean flag30;
        private int count224;
        private int count188;
        private boolean flag179;

        private AutoCloseableImpl(String string, int n, int n2, boolean bl) {
            this.count224 = n;
            this.count188 = n2;
            this.flag30 = bl;
            this.field62 = ResourceLoader.m52("lumin-rt" + string);
            this.m996();
        }

        private void m996() {
            GpuTextureView gpuTextureView;
            GpuTexture gpuTexture;
            block3: {
                GpuDevice gpuDevice;
                block2: {
                    this.flag179 = false;
                    Object var2_1 = null;
                    gpuDevice = RenderSystem.getDevice();
                    gpuTexture = gpuDevice.createTexture("lumin-rt-color", 15, TextureFormat.RGBA8, this.count224, this.count188, 1, 1);
                    gpuTextureView = gpuDevice.createTextureView(gpuTexture);
                    AutoCloseableImpl autoCloseableImpl = this;
                    if (null == null) break block2;
                    if (!autoCloseableImpl.flag30) break block3;
                    this.gpuTexture = gpuDevice.createTexture("lumin-rt-depth", 15, TextureFormat.DEPTH32, this.count224, this.count188, 1, 1);
                    autoCloseableImpl = this;
                }
                this.gpuTextureView4 = gpuDevice.createTextureView(this.gpuTexture);
            }
            GpuSampler gpuSampler = RenderSystem.getDevice().createSampler(AddressMode.CLAMP_TO_EDGE, AddressMode.CLAMP_TO_EDGE, FilterMode.NEAREST, FilterMode.NEAREST, 1, OptionalDouble.empty());
            this.texture4 = new Texture(gpuTexture, gpuTextureView, gpuSampler);
            MC.mc.getTextureManager().registerTexture(this.field62, (AbstractTexture)this.getTexture2());
        }

        public GpuTextureView getGpuTextureView() {
            return this.texture4.getGlTextureView();
        }

        public GpuTextureView getGpuTextureView5() {
            return this.gpuTextureView4;
        }

        public int getInt25() {
            return this.count224;
        }

        public int getInt87() {
            return this.count188;
        }

        public Texture getTexture2() {
            return this.texture4;
        }

        private void m659() {
            Object var2_1 = null;
            AutoCloseableImpl autoCloseableImpl = this;
            if (null != null) {
                if (autoCloseableImpl.flag179) {
                    return;
                }
                this.flag179 = true;
                MC.mc.getTextureManager().destroyTexture(this.field62);
                autoCloseableImpl = this;
            }
            if (null != null) {
                if (autoCloseableImpl.gpuTextureView4 != null) {
                    this.gpuTextureView4.close();
                }
                autoCloseableImpl = this;
            }
            if (null != null) {
                if (autoCloseableImpl.gpuTexture != null) {
                    this.gpuTexture.close();
                }
                this.texture4 = null;
                this.gpuTextureView4 = null;
                autoCloseableImpl = this;
            }
            autoCloseableImpl.gpuTexture = null;
        }

        @Override
        public void close() {
            this.m659();
            AutoCloseableTracker.manager3.setObj93(this);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Data  {
        private final GpuTextureView gpuTextureView;
        private final GpuTextureView gpuTextureView2;
        private final VertexFormat.IndexType field5;
        private final GpuBuffer gpuBuffer;
        private final int count16;
        private final GpuBufferSlice gpuBufferSlice3;

        public Data(GpuTextureView gpuTextureView, GpuTextureView gpuTextureView2, VertexFormat.IndexType indexType, GpuBuffer gpuBuffer, int n, GpuBufferSlice gpuBufferSlice) {
            block0: {
                this.gpuTextureView = gpuTextureView;
                this.gpuTextureView2 = gpuTextureView2;
                this.field5 = indexType;
                this.gpuBuffer = gpuBuffer;
                this.count16 = n;
                this.gpuBufferSlice3 = gpuBufferSlice;
                if (Module.getTextArray9() != null) break block0;
                GpuPipelineFactory.setText6("T6lV9");
            }
        }

        public GpuTextureView getGpuTextureView3() {
            return this.gpuTextureView;
        }

        public GpuTextureView getGpuTextureView2() {
            return this.gpuTextureView2;
        }

        public VertexFormat.IndexType field5() {
            return this.field5;
        }

        public GpuBuffer gpuBuffer() {
            return this.gpuBuffer;
        }

        public int getInt() {
            return this.count16;
        }

        public GpuBufferSlice gpuBufferSlice3() {
            return this.gpuBufferSlice3;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public record ColorData(int count30, int count31, int count32, int count33) {
    }

    @Environment(value=EnvType.CLIENT)
    static final class Inner {
        private static final Map map19 = new HashMap();

        private Inner() {
        }

        private static GpuBufferSlice write(Object object, Object object2, int n, int n2, Object object3) {
            String string = (String)object;
            String string3 = (String)object2;
            int n3 = n;
            int n4 = n2;
            DynamicUniformStorage.Uploadable uploadable = (DynamicUniformStorage.Uploadable)object3;
            DynamicUniformStorage dynamicUniformStorage = (DynamicUniformStorage)map19.computeIfAbsent(string, string2 -> new DynamicUniformStorage(string3, n3, n4));
            return dynamicUniformStorage.write(uploadable);
        }

        private static void m390() {
            map19.values().forEach(v -> ((DynamicUniformStorage) v).clear());
        }

        private static void m339() {
            map19.values().forEach(v -> ((DynamicUniformStorage) v).close());
            map19.clear();
        }
    }
}

