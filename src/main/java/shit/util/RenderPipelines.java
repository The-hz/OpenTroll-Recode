/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.lang.invoke.LambdaMetafactory;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TextureTransform;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.lwjgl.system.MemoryUtil;
import shit.manager.GpuManager;
import shit.module.Module;
import shit.util.MC;
import shit.util.RenderUtil4;

@Environment(value=EnvType.CLIENT)
public final class RenderPipelines {
    private static final long time18;
    private static final boolean flag85;
    private static final ImmediateRenderer immediateRenderer6;
    private static final ImmediateRenderer immediateRenderer7;
    private static final ImmediateRenderer immediateRenderer5;
    private static final ImmediateRenderer immediateRenderer4;
    private static final ImmediateRenderer immediateRenderer2;
    private static int[] counts19;

    private RenderPipelines() {
    }

    public static ImmediateRendererHolder2 m407(Object object) {
        RenderPipeline renderPipeline = (RenderPipeline)object;
        Object[] objectArray = new Object[2];
        objectArray[1] = null;
        objectArray[0] = renderPipeline;
        Object[] objectArray2 = objectArray;
        return new ImmediateRendererHolder2(immediateRenderer6.m347(objectArray2[0], objectArray2[1]));
    }

    public static RenderUtil m90(Object object) {
        RenderPipeline renderPipeline = (RenderPipeline)object;
        Object[] objectArray = new Object[2];
        objectArray[1] = null;
        objectArray[0] = renderPipeline;
        Object[] objectArray2 = objectArray;
        return new RenderUtil(immediateRenderer2.m347(objectArray2[0], objectArray2[1]));
    }

    static {
        if (RenderPipelines.getIntArray6() != null) {
            RenderPipelines.setIntArray7(new int[5]);
        }
        time18 = 0x100000L;
        flag85 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
        immediateRenderer6 = new ImmediateRenderer(VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.QUADS);
        immediateRenderer7 = new ImmediateRenderer(VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.TRIANGLE_STRIP);
        immediateRenderer5 = new ImmediateRenderer(VertexFormats.POSITION_COLOR, VertexFormat.DrawMode.TRIANGLE_FAN);
        immediateRenderer4 = new ImmediateRenderer(VertexFormats.POSITION_TEXTURE_COLOR, VertexFormat.DrawMode.QUADS);
        immediateRenderer2 = new ImmediateRenderer(VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH, VertexFormat.DrawMode.LINES);
    }

    public static void setIntArray7(int[] nArray) {
        counts19 = nArray;
    }

    public static int[] getIntArray6() {
        return counts19;
    }

    private static MatchException a(MatchException matchException) {
        return matchException;
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Inner {
        private final ImmediateRenderer immediateRenderer8;

        private Inner(ImmediateRenderer immediateRenderer) {
            this.immediateRenderer8 = immediateRenderer;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class ImmediateRenderer {
        private final GpuManager gpuManager6;
        private final VertexFormat vertexFormat;
        private final VertexFormat.DrawMode field17;
        private final int count223;
        private final int count208;
        private final int count46;
        private final int count112;
        private final int count95;
        private final int count211;
        private final Vector3f vector3f = new Vector3f();
        private boolean flag96;
        private long time75;
        private long time58;
        private long time61;
        private int count92;
        private boolean flag106;
        private long time66;
        private RenderPipeline renderPipeline12;
        private Identifier field28;

        private ImmediateRenderer(VertexFormat vertexFormat, VertexFormat.DrawMode drawMode) {
            this.gpuManager6 = new GpuManager(0x100000L, 32);
            this.vertexFormat = vertexFormat;
            this.field17 = drawMode;
            this.count223 = vertexFormat.getVertexSize();
            this.count208 = ImmediateRenderer.m54(vertexFormat, VertexFormatElement.POSITION);
            this.count46 = ImmediateRenderer.m54(vertexFormat, VertexFormatElement.COLOR);
            this.count112 = ImmediateRenderer.m54(vertexFormat, VertexFormatElement.UV0);
            this.count95 = ImmediateRenderer.m54(vertexFormat, VertexFormatElement.NORMAL);
            this.count211 = ImmediateRenderer.m54(vertexFormat, VertexFormatElement.LINE_WIDTH);
        }

        private static int m54(Object object, Object object2) {
            VertexFormat vertexFormat = (VertexFormat)object;
            VertexFormatElement vertexFormatElement = (VertexFormatElement)object2;
            return vertexFormat.contains(vertexFormatElement) ? vertexFormat.getOffset(vertexFormatElement) : -1;
        }

        private ImmediateRenderer m347(Object object, Object object2) {
            RenderPipeline renderPipeline = (RenderPipeline)object;
            Identifier identifier = (Identifier)object2;
            int[] nArray = RenderPipelines.getIntArray6();
            ImmediateRenderer immediateRenderer = this;
            if (nArray == null) {
                if (immediateRenderer.flag96) {
                    throw new IllegalStateException("Immediate channel is already building");
                }
                this.flag96 = true;
                this.time75 = this.time58;
                this.time61 = this.time58;
                this.count92 = 0;
                this.renderPipeline12 = renderPipeline;
                this.field28 = identifier;
                this.gpuManager6.m691();
                immediateRenderer = this;
            }
            return immediateRenderer;
        }

        private void m690(Object object, float f, float f2, float f3) {
            ImmediateRenderer immediateRenderer;
            block4: {
                float f4;
                float f5;
                float f6;
                Matrix4f matrix4f;
                block5: {
                    block3: {
                        int n;
                        block2: {
                            matrix4f = (Matrix4f)object;
                            f6 = f;
                            f5 = f2;
                            f4 = f3;
                            int[] nArray = RenderPipelines.getIntArray6();
                            n = this.count208;
                            if (nArray != null) break block2;
                            if (n < 0) break block3;
                            immediateRenderer = this;
                            if (nArray != null) break block4;
                            n = immediateRenderer.isSet94() ? 1 : 0;
                        }
                        if (n != 0) break block5;
                    }
                    return;
                }
                matrix4f.transformPosition(f6, f5, f4, this.vector3f);
                immediateRenderer = this;
            }
            long l = immediateRenderer.time66 + (long)this.count208;
            MemoryUtil.memPutFloat((long)l, (float)this.vector3f.x);
            MemoryUtil.memPutFloat((long)(l + 4L), (float)this.vector3f.y);
            MemoryUtil.memPutFloat((long)(l + 8L), (float)this.vector3f.z);
        }

        private void setInt8(int var1_1) {
            int color = var1_1;
            if (this.count46 < 0) {
                return;
            }
            if (!this.isSet94()) {
                return;
            }
            int abgr = ColorHelper.toAbgr(color);
            long addr = this.time66 + (long)this.count46;
            int value = RenderPipelines.flag85 ? abgr : Integer.reverseBytes(abgr);
            MemoryUtil.memPutInt(addr, value);
        }

        private void m1007(float f, float f2) {
            ImmediateRenderer immediateRenderer;
            float f3;
            float f4;
            block4: {
                block5: {
                    block3: {
                        int n;
                        block2: {
                            f4 = f;
                            f3 = f2;
                            int[] nArray = RenderPipelines.getIntArray6();
                            n = this.count112;
                            if (nArray != null) break block2;
                            if (n < 0) break block3;
                            immediateRenderer = this;
                            if (nArray != null) break block4;
                            n = immediateRenderer.isSet94() ? 1 : 0;
                        }
                        if (n != 0) break block5;
                    }
                    return;
                }
                immediateRenderer = this;
            }
            long l = immediateRenderer.time66 + (long)this.count112;
            MemoryUtil.memPutFloat((long)l, (float)f4);
            MemoryUtil.memPutFloat((long)(l + 4L), (float)f3);
        }

        private void m341(float f, float f2, float f3) {
            ImmediateRenderer immediateRenderer;
            float f4;
            float f5;
            float f6;
            block4: {
                block5: {
                    block3: {
                        int n;
                        block2: {
                            f6 = f;
                            f5 = f2;
                            f4 = f3;
                            int[] nArray = RenderPipelines.getIntArray6();
                            n = this.count95;
                            if (nArray != null) break block2;
                            if (n < 0) break block3;
                            immediateRenderer = this;
                            if (nArray != null) break block4;
                            n = immediateRenderer.isSet94() ? 1 : 0;
                        }
                        if (n != 0) break block5;
                    }
                    return;
                }
                immediateRenderer = this;
            }
            long l = immediateRenderer.time66 + (long)this.count95;
            MemoryUtil.memPutByte((long)l, (byte)ImmediateRenderer.m396(f6));
            MemoryUtil.memPutByte((long)(l + 1L), (byte)ImmediateRenderer.m396(f5));
            MemoryUtil.memPutByte((long)(l + 2L), (byte)ImmediateRenderer.m396(f4));
        }

        private void setFloat3(float f) {
            ImmediateRenderer immediateRenderer;
            float f2;
            block4: {
                block5: {
                    block3: {
                        int n;
                        block2: {
                            f2 = f;
                            int[] nArray = RenderPipelines.getIntArray6();
                            n = this.count211;
                            if (nArray != null) break block2;
                            if (n < 0) break block3;
                            immediateRenderer = this;
                            if (nArray != null) break block4;
                            n = immediateRenderer.isSet94() ? 1 : 0;
                        }
                        if (n != 0) break block5;
                    }
                    return;
                }
                immediateRenderer = this;
            }
            MemoryUtil.memPutFloat((long)(immediateRenderer.time66 + (long)this.count211), (float)f2);
        }

        private void m770() {
            long l;
            int[] nArray;
            block7: {
                block8: {
                    block6: {
                        boolean bl;
                        block5: {
                            nArray = RenderPipelines.getIntArray6();
                            bl = this.flag96;
                            if (nArray != null) break block5;
                            if (!bl) break block6;
                            l = this.time66;
                            if (nArray != null) break block7;
                            long l2 = l - 0L;
                            bl = (l2 == 0L ? 0 : (l2 < 0L ? -1 : 1)) != 0;
                        }
                        if (bl) break block8;
                    }
                    return;
                }
                l = this.time66;
            }
            long l3 = l;
            this.time75 += (long)this.count223;
            ++this.count92;
            ImmediateRenderer immediateRenderer = this;
            if (nArray == null) {
                if (immediateRenderer.field17 == VertexFormat.DrawMode.LINES) {
                    long l4 = MemoryUtil.memAddress((ByteBuffer)this.gpuManager6.getByteBuffer()) + this.time75;
                    MemoryUtil.memCopy((long)l3, (long)l4, (long)this.count223);
                    this.time75 += (long)this.count223;
                    ++this.count92;
                }
                immediateRenderer = this;
            }
            immediateRenderer.time66 = 0L;
        }

        private boolean isSet94() {
            if (!this.flag96) {
                return false;
            }
            if (this.time66 != 0L) {
                return true;
            }
            long need = this.field17 == VertexFormat.DrawMode.LINES ? (long)this.count223 * 2L : (long)this.count223;
            this.gpuManager6.setLong(this.time75 + need);
            if (!this.gpuManager6.isSet135()) {
                this.gpuManager6.m691();
            }
            this.time66 = MemoryUtil.memAddress(this.gpuManager6.getByteBuffer()) + this.time75;
            return true;
        }

        private void drawAndReset() {
            int drew = 0;
            long savedTime = this.time75;
            try {
                if (this.count92 > 0) {
                    if (this.gpuManager6.isSet135()) {
                        this.gpuManager6.m587();
                    }
                    com.mojang.blaze3d.textures.GpuTextureView colorView = RenderUtil4.getGpuTextureView6();
                    com.mojang.blaze3d.textures.GpuTextureView depthView = RenderUtil4.getGpuTextureView4();
                    if (colorView != null) {
                        com.mojang.blaze3d.buffers.GpuBufferSlice uniforms = RenderSystem.getDynamicUniforms().write(
                                RenderSystem.getModelViewMatrix(),
                                new Vector4f(1.0f, 1.0f, 1.0f, 1.0f),
                                new Vector3f(0.0f, 0.0f, 0.0f),
                                TextureTransform.DEFAULT_TEXTURING.getTransformSupplier());
                        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                                () -> "Lumin Immediate Draw", colorView, OptionalInt.empty(), depthView, OptionalDouble.empty())) {
                            renderPass.setPipeline(this.renderPipeline12);
                            RenderSystem.bindDefaultUniforms(renderPass);
                            renderPass.setUniform("DynamicTransforms", uniforms);
                            renderPass.setVertexBuffer(0, this.gpuManager6.getGpuBuffer());
                            if (this.field28 != null) {
                                net.minecraft.client.texture.AbstractTexture texture = MC.mc.getTextureManager().getTexture(this.field28);
                                renderPass.bindTexture("Sampler0", texture.getGlTextureView(), texture.getSampler());
                            }
                            switch (Lambda.counts11[this.field17.ordinal()]) {
                                case 1:
                                case 2: {
                                    int indexCount = this.field17.getIndexCount(this.count92);
                                    if (indexCount > 0) {
                                        RenderSystem.ShapeIndexBuffer shapeIndexBuffer = RenderSystem.getSequentialBuffer(this.field17);
                                        com.mojang.blaze3d.buffers.GpuBuffer indexBuffer = shapeIndexBuffer.getIndexBuffer(indexCount);
                                        renderPass.setIndexBuffer(indexBuffer, shapeIndexBuffer.getIndexType());
                                        renderPass.drawIndexed(Math.toIntExact(this.time61 / (long)this.count223), 0, indexCount, 1);
                                        drew = 1;
                                    }
                                    break;
                                }
                                default:
                                    renderPass.draw(Math.toIntExact(this.time61 / (long)this.count223), this.count92);
                                    drew = 1;
                            }
                        }
                    }
                }
            } finally {
                if (this.gpuManager6.isSet135()) {
                    this.gpuManager6.m587();
                }
                if (drew != 0) {
                    this.flag106 = true;
                    this.time58 = savedTime;
                }
                this.flag96 = false;
                this.time75 = this.time58;
                this.time61 = this.time58;
                this.count92 = 0;
                this.time66 = 0L;
                this.renderPipeline12 = null;
                this.field28 = null;
            }
        }

        private void m26() {
            block6: {
                ImmediateRenderer immediateRenderer;
                boolean bl;
                block5: {
                    int[] nArray = RenderPipelines.getIntArray6();
                    bl = this.gpuManager6.isSet135();
                    if (nArray != null) break block5;
                    if (bl) {
                        this.gpuManager6.m587();
                    }
                    immediateRenderer = this;
                    if (nArray != null) break block6;
                    bl = immediateRenderer.flag106;
                }
                if (bl) {
                    this.gpuManager6.m470();
                }
                this.flag106 = false;
                this.time58 = 0L;
                this.time75 = 0L;
                immediateRenderer = this;
            }
            this.time61 = 0L;
        }

        private static byte m396(float f) {
            float f2 = f;
            float f3 = MathHelper.clamp((float)f2, (float)-1.0f, (float)1.0f);
            return (byte)((int)(f3 * 127.0f) & 0xFF);
        }

        private static /* synthetic */ String cfrlam$drawAndReset$0() {
            return "Lumin Immediate Draw";
        }
    }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts11 = new int[VertexFormat.DrawMode.values().length];

        static {
            try {
                Lambda.counts11[VertexFormat.DrawMode.LINES.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts11[VertexFormat.DrawMode.QUADS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class RenderUtil {
        private final ImmediateRenderer immediateRenderer3;
        private final Vector3f vector3f2 = new Vector3f();

        private RenderUtil(ImmediateRenderer immediateRenderer) {
            this.immediateRenderer3 = immediateRenderer;
        }

        public void m439(Object object, Object object2, float f, float f2, float f3, int n, float f4, float f5, float f6, float f7) {
            block0: {
                Matrix4f matrix4f = (Matrix4f)object;
                MatrixStack.Entry entry = (MatrixStack.Entry)object2;
                float f8 = f;
                float f9 = f2;
                float f10 = f3;
                int n2 = n;
                float f11 = f4;
                float f12 = f5;
                float f13 = f6;
                float f14 = f7;
                int[] nArray = RenderPipelines.getIntArray6();
                this.immediateRenderer3.m690(matrix4f, f8, f9, f10);
                this.immediateRenderer3.setInt8(n2);
                int[] nArray2 = nArray;
                entry.transformNormal(f11, f12, f13, this.vector3f2).normalize();
                this.immediateRenderer3.m341(this.vector3f2.x, this.vector3f2.y, this.vector3f2.z);
                this.immediateRenderer3.setFloat3(f14);
                this.immediateRenderer3.m770();
                if (nArray2 == null) break block0;
                Module.setTextArray9(new String[5]);
            }
        }

        public void m48() {
            this.immediateRenderer3.drawAndReset();
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class ImmediateRendererHolder3 {
        private final ImmediateRenderer immediateRenderer9;

        private ImmediateRendererHolder3(ImmediateRenderer immediateRenderer) {
            this.immediateRenderer9 = immediateRenderer;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class ImmediateRendererHolder2 {
        private final ImmediateRenderer immediateRenderer;

        private ImmediateRendererHolder2(ImmediateRenderer immediateRenderer) {
            this.immediateRenderer = immediateRenderer;
        }

        public void m530(Object object, float f, float f2, float f3, int n) {
            Matrix4f matrix4f = (Matrix4f)object;
            float f4 = f;
            float f5 = f2;
            float f6 = f3;
            int n2 = n;
            this.immediateRenderer.m690(matrix4f, f4, f5, f6);
            this.immediateRenderer.setInt8(n2);
            this.immediateRenderer.m770();
        }

        public void m512() {
            this.immediateRenderer.drawAndReset();
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class ImmediateRendererHolder {
        private final ImmediateRenderer immediateRenderer10;

        private ImmediateRendererHolder(ImmediateRenderer immediateRenderer) {
            this.immediateRenderer10 = immediateRenderer;
        }
    }
}

