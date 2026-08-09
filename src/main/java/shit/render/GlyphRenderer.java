/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.util.IdentityHashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.BakedGlyph;
import net.minecraft.client.font.GlyphMetrics;
import net.minecraft.client.font.TextDrawable;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.text.Style;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;
import shit.api.SamplerCallback;
import shit.data.BufferUtilData;
import shit.manager.BufferUtilDataManager;
import shit.misc.BufferUtil;
import shit.misc.RenderPipelines;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.util.FontUtil;

@Environment(value=EnvType.CLIENT)
public final class GlyphRenderer
implements BakedGlyph {
    private static final Map map42 = new java.util.LinkedHashMap<>();
    private static final Map map15 = new java.util.LinkedHashMap<>();
    private final int count215;
    private final BufferUtilDataManager bufferUtilDataManager7;
    private final BufferUtilData bufferUtilData;
    private final GlyphMetrics field35;
    private static boolean flag158;

    private GlyphRenderer(int n, BufferUtilDataManager bufferUtilDataManager, BufferUtilData bufferUtilData) {
        this.count215 = n;
        this.bufferUtilDataManager7 = bufferUtilDataManager;
        this.bufferUtilData = bufferUtilData;
        this.field35 = new Vec1f(FontUtil.m172(n, Style.EMPTY, bufferUtilDataManager));
    }

    public GlyphMetrics getMetrics() {
        return this.field35;
    }

    public TextDrawable.DrawnGlyphRect create(float f, float f2, int n, int n2, Style style, float f3, float f4) {
        if (this.bufferUtilData == null) {
            return null;
        }
        return new GlyphRendererData(this, f, f2, n, n2, style, f3, f4);
    }

    private RenderLayer renderType() {
        boolean bl = false;
        if (this.bufferUtilData == null) {
            throw new IllegalStateException("Whitespace glyphs do not have render types");
        }
        Map map = (Boolean)ClientSetting.INSTANCE.fontAntiAliasing.getValue() != false ? map42 : map15;
        RenderPipeline renderPipeline = (Boolean)ClientSetting.INSTANCE.fontAntiAliasing.getValue() != false ? RenderPipelines.renderPipeline3 : RenderPipelines.renderPipeline18;
        String string = (Boolean)ClientSetting.INSTANCE.fontAntiAliasing.getValue() != false ? "lumin_ttf_text_aa" : "lumin_ttf_text_no_aa";
        return (RenderLayer)map.computeIfAbsent(this.bufferUtilData.bufferUtil(), bufferUtil -> RenderLayer.of((String)string, (RenderSetup)RenderSetup.builder((RenderPipeline)renderPipeline).texture("Sampler0", ((shit.misc.BufferUtil)bufferUtil).getObj18(), () -> ((shit.misc.BufferUtil)bufferUtil).getTexture().getSampler()).expectedBufferSize(786432).build()));
    }

    private float m106(float f) {
        float f2 = f;
        return f2 + (float)this.bufferUtilDataManager7.bufferUtil2.count200 * this.getFloat33();
    }

    private float getFloat33() {
        return FontUtil.m789(this.bufferUtilDataManager7);
    }

    private float m319(float f) {
        float f2 = f;
        return f2;
    }

    private static float m253(boolean bl) {
        boolean bl2 = bl;
        return bl2 ? 0.06f : 0.0f;
    }

    private float m260(float f) {
        float f2 = f;
        boolean bl = false;
        if (this.bufferUtilData == null) {
            return f2;
        }
        return this.m106(f2) + (float)this.bufferUtilData.count20() * this.getFloat33();
    }

    private float m846(float f, boolean bl, float f2, boolean bl2, boolean bl3) {
        float f3 = f;
        boolean bl4 = bl;
        float f4 = f2;
        boolean bl5 = bl2;
        boolean bl6 = bl3;
        boolean bl7 = GlyphRenderer.isSet134();
        if (this.bufferUtilData == null) {
            return f3 + this.field35.getAdvance(bl5);
        }
        float f5 = f3 + (float)this.bufferUtilData.getInt7() * this.getFloat33();
        boolean bl8 = bl4;
        if (bl7) {
            if (bl8) {
                f5 += f4;
            }
            bl8 = bl5;
        }
        if (bl7) {
            if (bl8) {
                f5 += GlyphRenderer.m253(true);
            }
            bl8 = bl6;
        }
        if (bl8) {
            f5 += 1.0f;
        }
        return f5;
    }

    private float m496(float f, boolean bl, float f2, boolean bl2) {
        float f3 = f;
        boolean bl3 = bl;
        float f4 = f2;
        boolean bl4 = bl2;
        float f5 = this.m260(f3) + (float)this.bufferUtilData.count18() * this.getFloat33();
        boolean bl5 = GlyphRenderer.isSet134();
        boolean bl6 = bl3;
        if (bl5) {
            if (bl6) {
                f5 += f4;
            }
            bl6 = bl4;
        }
        if (bl6) {
            f5 += GlyphRenderer.m253(true);
        }
        return f5;
    }

    private void m182(Object object, Object object2, Object object3, float f, float f2, float f3, int n, boolean bl) {
        Matrix4fc matrix4fc = (Matrix4fc)object;
        VertexConsumer vertexConsumer = (VertexConsumer)object2;
        GlyphRendererData glyphRendererData = (GlyphRendererData)object3;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        int n2 = n;
        boolean bl2 = bl;
        if (this.bufferUtilData == null) {
            return;
        }
        float f7 = glyphRendererData.value66 + f4;
        float f8 = f7 + (float)this.bufferUtilData.getInt7() * this.getFloat33();
        float f9 = this.m260(glyphRendererData.value67) + f5;
        float f10 = f9 + (float)this.bufferUtilData.count18() * this.getFloat33();
        float f11 = GlyphRenderer.m253(bl2);
        float f12 = glyphRendererData.field6.isItalic() ? 1.0f - 0.25f * (f9 - glyphRendererData.value67) : 0.0f;
        float f13 = glyphRendererData.field6.isItalic() ? 1.0f - 0.25f * (f10 - glyphRendererData.value67) : 0.0f;
        BufferUtil.Vec4f vec4f = this.bufferUtilData.vec4f2();
        vertexConsumer.vertex(matrix4fc, f7 + f12 - f11, f9 - f11, f6).texture(vec4f.getFloat23(), vec4f.value46()).color(n2);
        vertexConsumer.vertex(matrix4fc, f7 + f13 - f11, f10 + f11, f6).texture(vec4f.getFloat23(), vec4f.getFloat30()).color(n2);
        vertexConsumer.vertex(matrix4fc, f8 + f13 + f11, f10 + f11, f6).texture(vec4f.value47(), vec4f.getFloat30()).color(n2);
        vertexConsumer.vertex(matrix4fc, f8 + f12 + f11, f9 - f11, f6).texture(vec4f.value47(), vec4f.value46()).color(n2);
    }

    /*
     * Unable to fully structure code
     */
    static {}

    public static void setFlag6(boolean bl) {
        flag158 = bl;
    }

    public static boolean isSet141() {
        return flag158;
    }

    public static boolean isSet134() {
        boolean bl = false;
        return true;
    }

    @Environment(value=EnvType.CLIENT)
    record Vec1f(float value14) implements GlyphMetrics
    {
        public float getAdvance() {
            return this.value14;
        }

        public float getBoldOffset() {
            return 0.45f;
        }

        public float getShadowOffset() {
            return 0.45f;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class GlyphRendererData 
    implements TextDrawable.DrawnGlyphRect,
    SamplerCallback {
        private final GlyphRenderer glyphRenderer;
        private final float value66;
        private final float value67;
        private final int count26;
        private final int count27;
        private final Style field6;
        private final float value68;
        private final float value69;

        private GlyphRendererData(GlyphRenderer glyphRenderer, float f, float f2, int n, int n2, Style style, float f3, float f4) {
            this.glyphRenderer = glyphRenderer;
            this.value66 = f;
            this.value67 = f2;
            this.count26 = n;
            this.count27 = n2;
            this.field6 = style;
            this.value68 = f3;
            this.value69 = f4;
        }

        private boolean isSet57() {
            boolean bl = false;
            return this.count27 != 0;
        }

        public void render(Matrix4f matrix4f, VertexConsumer vertexConsumer, int n, boolean bl) {
            block8: {
                GlyphRendererData glyphRendererData;
                float f;
                block7: {
                    boolean bl2;
                    block6: {
                        block5: {
                            boolean bl3;
                            block4: {
                                bl2 = GlyphRenderer.isSet134();
                                bl3 = this.isSet57();
                                if (!bl2) break block4;
                                if (!bl3) break block5;
                                this.glyphRenderer.m182(matrix4f, vertexConsumer, this, this.value69, this.value69, 0.0f, this.count27, this.field6.isBold());
                                bl3 = bl;
                            }
                            float f2 = f = bl3 ? 0.0f : 0.03f;
                            if (bl2) break block6;
                            Module.setTextArray9(new String[2]);
                        }
                        f = 0.0f;
                    }
                    this.glyphRenderer.m182(matrix4f, vertexConsumer, this, 0.0f, 0.0f, f, this.count26, this.field6.isBold());
                    glyphRendererData = this;
                    if (!bl2) break block7;
                    if (!glyphRendererData.field6.isBold()) break block8;
                    glyphRendererData = this;
                }
                glyphRendererData.glyphRenderer.m182(matrix4f, vertexConsumer, this, this.value68, 0.0f, f + (bl ? 0.0f : 0.001f), this.count26, true);
            }
        }

        public RenderLayer getRenderLayer(TextRenderer.TextLayerType textLayerType) {
            return this.glyphRenderer.renderType();
        }

        public GpuTextureView textureView() {
            boolean bl = GlyphRenderer.isSet134();
            BufferUtilData bufferUtilData = this.glyphRenderer.bufferUtilData;
            if (bl) {
                if (bufferUtilData == null) {
                    throw new IllegalStateException("Whitespace glyphs do not have textures");
                }
                bufferUtilData = this.glyphRenderer.bufferUtilData;
            }
            return bufferUtilData.bufferUtil().getTexture().getGlTextureView();
        }

        @Override
        public GpuSampler getObj16() {
            boolean bl = GlyphRenderer.isSet134();
            BufferUtilData bufferUtilData = this.glyphRenderer.bufferUtilData;
            if (bl) {
                if (bufferUtilData == null) {
                    throw new IllegalStateException("Whitespace glyphs do not have samplers");
                }
                bufferUtilData = this.glyphRenderer.bufferUtilData;
            }
            return bufferUtilData.bufferUtil().getTexture().getSampler();
        }

        public RenderPipeline getPipeline() {
            return (Boolean)ClientSetting.INSTANCE.fontAntiAliasing.getValue() != false ? RenderPipelines.renderPipeline3 : RenderPipelines.renderPipeline18;
        }

        public float getEffectiveMinX() {
            return this.glyphRenderer.m319(this.value66);
        }

        public float getEffectiveMinY() {
            return this.glyphRenderer.m260(this.value67);
        }

        public float getEffectiveMaxX() {
            boolean bl = GlyphRenderer.isSet134();
            float f = this.glyphRenderer.m846(this.value66, this.isSet57(), this.value69, this.field6.isBold(), this.field6.isItalic());
            if (Module.getTextArray9() == null) {
                GlyphRenderer.setFlag6(!bl);
            }
            return f;
        }

        public float getRight() {
            return this.value66 + this.glyphRenderer.field35.getAdvance(this.field6.isBold());
        }

        public float getEffectiveMaxY() {
            return this.glyphRenderer.m496(this.value67, this.isSet57(), this.value69, this.field6.isBold());
        }

        public GlyphRenderer glyphRenderer() {
            return this.glyphRenderer;
        }

        public float value66() {
            return this.value66;
        }

        public float value67() {
            return this.value67;
        }

        public int count26() {
            return this.count26;
        }

        public int count27() {
            return this.count27;
        }

        public Style style() {
            return this.field6;
        }

        public float value68() {
            return this.value68;
        }

        public float value69() {
            return this.value69;
        }
    }
}

