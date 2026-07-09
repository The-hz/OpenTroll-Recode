/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import shit.data.BufferData;
import shit.module.Module;
import shit.render.ShaderRenderer;

@Environment(value=EnvType.CLIENT)
public class BufferUtil2 {
    private final ByteBuffer byteBuffer2;
    private final STBTTFontinfo field14;
    private final int count76;
    public final float value119;
    public final int count200;
    public final int count69;
    private static final String a = "";

    public BufferUtil2(Identifier identifier, int n, int n2) {
        this.byteBuffer2 = shit.util.BufferUtil2.m37(identifier);
        this.field14 = STBTTFontinfo.create();
        if (!STBTruetype.stbtt_InitFont((STBTTFontinfo)this.field14, (ByteBuffer)this.byteBuffer2)) {
            MemoryUtil.memFree((Buffer)this.byteBuffer2);
            throw new IllegalStateException(a + String.valueOf(identifier));
        }
        this.count76 = n2;
        this.value119 = STBTruetype.stbtt_ScaleForPixelHeight((STBTTFontinfo)this.field14, (float)(n - n2 * 2));
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.callocInt(1);
            IntBuffer intBuffer2 = memoryStack.callocInt(1);
            IntBuffer intBuffer3 = memoryStack.mallocInt(1);
            STBTruetype.stbtt_GetFontVMetrics((STBTTFontinfo)this.field14, (IntBuffer)intBuffer, (IntBuffer)intBuffer2, (IntBuffer)intBuffer3);
            int n3 = intBuffer.get();
            int n4 = intBuffer2.get();
            int n5 = intBuffer3.get();
            this.count200 = (int)((float)n3 * this.value119);
            this.count69 = (int)((float)(n3 - n4 + n5) * this.value119);
        }
    }

    /*
     * Unable to fully structure code
     */
    public synchronized BufferData m728(int var1_1) {
        int glyph = STBTruetype.stbtt_FindGlyphIndex((STBTTFontinfo)this.field14, (int)var1_1);
        byte onedge = -128;
        float pixelDistScale = -128.0f / (float)this.count76;
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer width = memoryStack.callocInt(1);
            IntBuffer height = memoryStack.callocInt(1);
            IntBuffer xoff = memoryStack.callocInt(1);
            IntBuffer yoff = memoryStack.callocInt(1);
            ByteBuffer sdf = STBTruetype.stbtt_GetGlyphSDF((STBTTFontinfo)this.field14, (float)this.value119, (int)glyph, (int)this.count76, (byte)onedge, (float)pixelDistScale, (IntBuffer)width, (IntBuffer)height, (IntBuffer)xoff, (IntBuffer)yoff);
            IntBuffer advance = memoryStack.callocInt(1);
            IntBuffer bearing = memoryStack.callocInt(1);
            STBTruetype.stbtt_GetGlyphHMetrics((STBTTFontinfo)this.field14, (int)glyph, (IntBuffer)advance, (IntBuffer)bearing);
            return new BufferData((ByteBuffer)sdf, (int)width.get(), (int)height.get(), (int)xoff.get(), (int)yoff.get(), (int)((float)advance.get() * this.value119));
        }
    }

    /*
     * Unable to fully structure code
     */
    public synchronized int m768(char var1_1) {
        int glyph = STBTruetype.stbtt_FindGlyphIndex((STBTTFontinfo)this.field14, (int)var1_1);
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer advance = memoryStack.callocInt(1);
            IntBuffer bearing = memoryStack.callocInt(1);
            STBTruetype.stbtt_GetGlyphHMetrics((STBTTFontinfo)this.field14, (int)glyph, (IntBuffer)advance, (IntBuffer)bearing);
            return (int)((float)advance.get() * this.value119);
        }
    }

    public void m127() {
        MemoryUtil.memFree((Buffer)this.byteBuffer2);
    }

    /*
     * Handled unverifiable bytecode (illegal stack merge).
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    static {}
}

