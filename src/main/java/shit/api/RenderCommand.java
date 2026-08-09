/*
 * Decompiled with CFR 0.152.
 */
package shit.api;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.api.TextureResource;
import shit.data.ColorData;
import shit.data.Vec4f;
import shit.manager.GlyphBufferManager;
import shit.type.RenderShapeType;

@Environment(value=EnvType.CLIENT)
public interface RenderCommand {
    public int layer();

    public long sequence();

    public RenderShapeType getObj15();

    public Vec4f bounds();

    public ColorData scissor();

    default public Vec4f getVec4f2() {
        return this.bounds();
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Vec4fData3 
    implements RenderCommand {
        private final int layer;
        private final long sequence;
        private final Vec4f bounds;
        private final ColorData scissor;
        private final float value;
        private final float value2;
        private final float value3;
        private final float value4;
        private final float value5;
        private final Color color;

        public Vec4fData3(int n, long l, Vec4f vec4f, ColorData colorData, float f, float f2, float f3, float f4, float f5, Color color) {
            this.layer = n;
            this.sequence = l;
            this.bounds = vec4f;
            this.scissor = colorData;
            this.value = f;
            this.value2 = f2;
            this.value3 = f3;
            this.value4 = f4;
            this.value5 = f5;
            this.color = color;
        }

        @Override
        public Vec4f getVec4f2() {
            float f = Math.max(0.0f, this.value5 * 0.5f);
            return Vec4f.m186(this.bounds.value6() - f, this.bounds.value7() - f, this.bounds.value8() + f * 2.0f, this.bounds.value9() + f * 2.0f);
        }

        @Override
        public RenderShapeType getObj15() {
            return RenderShapeType.ROUND_RECT_OUTLINE;
        }

        @Override
        public int layer() {
            return this.layer;
        }

        @Override
        public long sequence() {
            return this.sequence;
        }

        @Override
        public Vec4f bounds() {
            return this.bounds;
        }

        @Override
        public ColorData scissor() {
            return this.scissor;
        }

        public float value() {
            return this.value;
        }

        public float value2() {
            return this.value2;
        }

        public float value3() {
            return this.value3;
        }

        public float value4() {
            return this.value4;
        }

        public float getFloat36() {
            return this.value5;
        }

        public Color color() {
            return this.color;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Vec4fData2 
    implements RenderCommand {
        private final int layer;
        private final long sequence;
        private final Vec4f bounds;
        private final ColorData scissor;
        private final Color color2;
        private final Color color3;
        private final Color color4;
        private final Color color5;

        public Vec4fData2(int n, long l, Vec4f vec4f, ColorData colorData, Color color, Color color2, Color color3, Color color4) {
            this.layer = n;
            this.sequence = l;
            this.bounds = vec4f;
            this.scissor = colorData;
            this.color2 = color;
            this.color3 = color2;
            this.color4 = color3;
            this.color5 = color4;
        }

        @Override
        public RenderShapeType getObj15() {
            return RenderShapeType.RECT;
        }

        @Override
        public int layer() {
            return this.layer;
        }

        @Override
        public long sequence() {
            return this.sequence;
        }

        @Override
        public Vec4f bounds() {
            return this.bounds;
        }

        @Override
        public ColorData scissor() {
            return this.scissor;
        }

        public Color color2() {
            return this.color2;
        }

        public Color color3() {
            return this.color3;
        }

        public Color color4() {
            return this.color4;
        }

        public Color getColor() {
            return this.color5;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Vec4fData5 
    implements RenderCommand {
        private final int layer;
        private final long sequence;
        private final Vec4f bounds;
        private final ColorData scissor;
        private final String text10;
        private final float value15;
        private final float value16;
        private final float value17;
        private final Color color12;
        private final GlyphBufferManager bufferUtilDataManager;

        public Vec4fData5(int n, long l, Vec4f vec4f, ColorData colorData, String string, float f, float f2, float f3, Color color, GlyphBufferManager bufferUtilDataManager) {
            this.layer = n;
            this.sequence = l;
            this.bounds = vec4f;
            this.scissor = colorData;
            this.text10 = string;
            this.value15 = f;
            this.value16 = f2;
            this.value17 = f3;
            this.color12 = color;
            this.bufferUtilDataManager = bufferUtilDataManager;
        }

        @Override
        public RenderShapeType getObj15() {
            return RenderShapeType.TEXT;
        }

        @Override
        public int layer() {
            return this.layer;
        }

        @Override
        public long sequence() {
            return this.sequence;
        }

        @Override
        public Vec4f bounds() {
            return this.bounds;
        }

        @Override
        public ColorData scissor() {
            return this.scissor;
        }

        public String getText17() {
            return this.text10;
        }

        public float getFloat11() {
            return this.value15;
        }

        public float value16() {
            return this.value16;
        }

        public float value17() {
            return this.value17;
        }

        public Color color12() {
            return this.color12;
        }

        public GlyphBufferManager getBufferUtilDataManager() {
            return this.bufferUtilDataManager;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Vec4fData6 
    implements RenderCommand {
        private final int layer;
        private final long sequence;
        private final Vec4f bounds;
        private final ColorData scissor;
        private final float value29;
        private final float value30;
        private final float value31;
        private final float value32;
        private final float value33;
        private final Color color13;

        public Vec4fData6(int n, long l, Vec4f vec4f, ColorData colorData, float f, float f2, float f3, float f4, float f5, Color color) {
            this.layer = n;
            this.sequence = l;
            this.bounds = vec4f;
            this.scissor = colorData;
            this.value29 = f;
            this.value30 = f2;
            this.value31 = f3;
            this.value32 = f4;
            this.value33 = f5;
            this.color13 = color;
        }

        @Override
        public Vec4f getVec4f2() {
            float f = Math.max(0.0f, this.value33);
            return Vec4f.m186(this.bounds.value6() - f, this.bounds.value7() - f, this.bounds.value8() + f * 2.0f, this.bounds.value9() + f * 2.0f);
        }

        @Override
        public RenderShapeType getObj15() {
            return RenderShapeType.SHADOW;
        }

        @Override
        public int layer() {
            return this.layer;
        }

        @Override
        public long sequence() {
            return this.sequence;
        }

        @Override
        public Vec4f bounds() {
            return this.bounds;
        }

        @Override
        public ColorData scissor() {
            return this.scissor;
        }

        public float getFloat40() {
            return this.value29;
        }

        public float getFloat10() {
            return this.value30;
        }

        public float getFloat46() {
            return this.value31;
        }

        public float value32() {
            return this.value32;
        }

        public float value33() {
            return this.value33;
        }

        public Color color13() {
            return this.color13;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Vec4fData 
    implements RenderCommand {
        private final int layer;
        private final long sequence;
        private final Vec4f bounds;
        private final ColorData scissor;
        private final TextureResource listener6;
        private final float value58;
        private final float value59;
        private final float value60;
        private final float value61;
        private final float value62;
        private final float value63;
        private final float value64;
        private final float value65;
        private final Color color20;

        public Vec4fData(int n, long l, Vec4f vec4f, ColorData colorData, TextureResource listener6, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Color color) {
            this.layer = n;
            this.sequence = l;
            this.bounds = vec4f;
            this.scissor = colorData;
            this.listener6 = listener6;
            this.value58 = f;
            this.value59 = f2;
            this.value60 = f3;
            this.value61 = f4;
            this.value62 = f5;
            this.value63 = f6;
            this.value64 = f7;
            this.value65 = f8;
            this.color20 = color;
        }

        @Override
        public RenderShapeType getObj15() {
            return RenderShapeType.TEXTURE;
        }

        @Override
        public int layer() {
            return this.layer;
        }

        @Override
        public long sequence() {
            return this.sequence;
        }

        @Override
        public Vec4f bounds() {
            return this.bounds;
        }

        @Override
        public ColorData scissor() {
            return this.scissor;
        }

        public TextureResource listener6() {
            return this.listener6;
        }

        public float value58() {
            return this.value58;
        }

        public float value59() {
            return this.value59;
        }

        public float getFloat12() {
            return this.value60;
        }

        public float value61() {
            return this.value61;
        }

        public float value62() {
            return this.value62;
        }

        public float value63() {
            return this.value63;
        }

        public float getFloat39() {
            return this.value64;
        }

        public float value65() {
            return this.value65;
        }

        public Color color20() {
            return this.color20;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Vec4fData7 
    implements RenderCommand {
        private final int layer;
        private final long sequence;
        private final Vec4f bounds;
        private final ColorData scissor;
        private final float value78;
        private final float value79;
        private final float value80;
        private final float value81;
        private final Color color21;
        private final Color color22;
        private final Color color23;
        private final Color color24;

        public Vec4fData7(int n, long l, Vec4f vec4f, ColorData colorData, float f, float f2, float f3, float f4, Color color, Color color2, Color color3, Color color4) {
            this.layer = n;
            this.sequence = l;
            this.bounds = vec4f;
            this.scissor = colorData;
            this.value78 = f;
            this.value79 = f2;
            this.value80 = f3;
            this.value81 = f4;
            this.color21 = color;
            this.color22 = color2;
            this.color23 = color3;
            this.color24 = color4;
        }

        @Override
        public RenderShapeType getObj15() {
            return RenderShapeType.ROUND_RECT;
        }

        @Override
        public int layer() {
            return this.layer;
        }

        @Override
        public long sequence() {
            return this.sequence;
        }

        @Override
        public Vec4f bounds() {
            return this.bounds;
        }

        @Override
        public ColorData scissor() {
            return this.scissor;
        }

        public float value78() {
            return this.value78;
        }

        public float value79() {
            return this.value79;
        }

        public float getFloat24() {
            return this.value80;
        }

        public float getFloat6() {
            return this.value81;
        }

        public Color getColor9() {
            return this.color21;
        }

        public Color getColor7() {
            return this.color22;
        }

        public Color color23() {
            return this.color23;
        }

        public Color color24() {
            return this.color24;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Vec4fData4 
    implements RenderCommand {
        private final int layer;
        private final long sequence;
        private final Vec4f bounds;
        private final ColorData scissor;
        private final float value82;
        private final float value83;
        private final float value84;
        private final float value85;
        private final Color color25;

        public Vec4fData4(int n, long l, Vec4f vec4f, ColorData colorData, float f, float f2, float f3, float f4, Color color) {
            this.layer = n;
            this.sequence = l;
            this.bounds = vec4f;
            this.scissor = colorData;
            this.value82 = f;
            this.value83 = f2;
            this.value84 = f3;
            this.value85 = f4;
            this.color25 = color;
        }

        @Override
        public RenderShapeType getObj15() {
            return RenderShapeType.TRIANGLE;
        }

        @Override
        public int layer() {
            return this.layer;
        }

        @Override
        public long sequence() {
            return this.sequence;
        }

        @Override
        public Vec4f bounds() {
            return this.bounds;
        }

        @Override
        public ColorData scissor() {
            return this.scissor;
        }

        public float value82() {
            return this.value82;
        }

        public float value83() {
            return this.value83;
        }

        public float value84() {
            return this.value84;
        }

        public float value85() {
            return this.value85;
        }

        public Color getColor3() {
            return this.color25;
        }
    }
}

