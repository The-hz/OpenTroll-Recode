/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import com.mojang.blaze3d.opengl.GlStateManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL20C;

@Environment(value=EnvType.CLIENT)
public final class Pos {
    public static int count160;
    private static int count52;
    private static String text3050;
    private static final String b;

    private Pos() {
    }

    public static void setInt6(int n) {
        int n2 = n;
        GlStateManager._glBindVertexArray((int)n2);
    }

    public static void setInt12(int n) {
        int n2 = n;
        if (n2 != 0) {
            count52 = count160;
        }
        GlStateManager._glBindBuffer((int)34963, (int)(n2 != 0 ? n2 : count52));
    }

    public static void setInt19(int n) {
        int n2 = n;
        GlStateManager.glCompileShader((int)n2);
        if (GlStateManager.glGetShaderi((int)n2, (int)35713) == 0) {
            throw new IllegalStateException(GlStateManager.glGetShaderInfoLog((int)n2, (int)4096));
        }
    }

    public static void m875(int n, int n2, int n3) {
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        GlStateManager.glAttachShader((int)n4, (int)n5);
        GlStateManager.glAttachShader((int)n4, (int)n6);
        GL20C.glBindAttribLocation((int)n4, (int)0, (CharSequence)b);
        GlStateManager.glLinkProgram((int)n4);
        if (GL20C.glGetProgrami((int)n4, (int)35714) == 0) {
            throw new IllegalStateException(GL20C.glGetProgramInfoLog((int)n4, (int)4096));
        }
    }

    public static int m338(int n, Object object) {
        int n2 = n;
        String string = (String)object;
        return GlStateManager._glGetUniformLocation((int)n2, (CharSequence)string);
    }

    public static void m437(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        GlStateManager._activeTexture((int)(33984 + n4));
        GlStateManager._bindTexture((int)n3);
    }

    public static void setText12(String string) {
        text3050 = string;
    }

    public static String getText67() {
        return text3050;
    }

    static {
        b = "pos";
    }
}

