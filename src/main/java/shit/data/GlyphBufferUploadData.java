/*
 * Decompiled with CFR 0.152.
 */
package shit.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.misc.MiscGlyphBufferUtil;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public final class GlyphBufferUploadData  {
    private final MiscGlyphBufferUtil bufferUtil;
    private final MiscGlyphBufferUtil.Vec4f vec4f2;
    private final int count17;
    private final int count18;
    private final int count19;
    private final int count20;
    private final int count21;
    private static int[] counts23;

    public GlyphBufferUploadData(MiscGlyphBufferUtil bufferUtil, MiscGlyphBufferUtil.Vec4f vec4f, int n, int n2, int n3, int n4, int n5) {
        this.bufferUtil = bufferUtil;
        this.vec4f2 = vec4f;
        Object var8_8 = null;
        this.count17 = n;
        this.count18 = n2;
        this.count19 = n3;
        this.count20 = n4;
        this.count21 = n5;
        Module.setTextArray9(new String[5]);
    }

    public MiscGlyphBufferUtil bufferUtil() {
        return this.bufferUtil;
    }

    public MiscGlyphBufferUtil.Vec4f vec4f2() {
        return this.vec4f2;
    }

    public int getInt7() {
        return this.count17;
    }

    public int count18() {
        return this.count18;
    }

    public int count19() {
        return this.count19;
    }

    public int count20() {
        return this.count20;
    }

    public int count21() {
        return this.count21;
    }

    public static void setIntArray4(int[] nArray) {
        counts23 = nArray;
    }

    public static int[] getIntArray3() {
        return counts23;
    }

    static {
        GlyphBufferUploadData.setIntArray4(new int[5]);
    }
}

