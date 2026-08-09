/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import java.nio.ByteBuffer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlBackend;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.Window;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL30C;
import shit.misc.Logger;
import shit.misc.ShaderProgram;
import shit.module.Module;
import shit.render.Blur2;
import shit.render.StringDecryptor;
import shit.util.Pos;
import shit.util.GlBufferHelper;
import shit.util.Timer;

@Environment(value=EnvType.CLIENT)
public final class ScreenCopyRenderer {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static ShaderProgram shaderProgram5;
    private static ShaderProgram shaderProgram;
    private static ShaderProgram shaderProgram4;
    private static Blur2 blur2;
    private static Blur2 blur25;
    private static Blur2 blur24;
    private static long time21;
    private static long time27;
    private static boolean flag71;

    private ScreenCopyRenderer() {
    }

    public static void m990(Object object, int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        block11: {
            boolean bl2;
            boolean bl3;
            int n7;
            int n8;
            int n9;
            int n10;
            int n11;
            int n12;
            DrawContext drawContext;
            block14: {
                int n13;
                block15: {
                    block13: {
                        int n14;
                        block12: {
                            drawContext = (DrawContext)object;
                            n12 = n;
                            n11 = n2;
                            n10 = n3;
                            n9 = n4;
                            n8 = n5;
                            n7 = n6;
                            bl3 = bl;
                            bl2 = ScreenCopyRenderer.isSet38();
                            n13 = n9;
                            n14 = 1;
                            if (!bl2) break block12;
                            if (n13 <= n14) break block13;
                            n13 = n8;
                            if (!bl2) break block14;
                            n14 = 1;
                        }
                        if (n13 > n14) break block15;
                    }
                    return;
                }
                n13 = 0;
            }
            try {
                int n15;
                int n16;
                int n17;
                int n18;
                int n19;
                DrawContext drawContext2;
                block17: {
                    int n20;
                    block16: {
                        int n21;
                        drawContext.drawDeferredElements();
                        ScreenCopyRenderer.m183();
                        n20 = n21 = ScreenCopyRenderer.getInt21();
                        if (!bl2) break block16;
                        if (n20 == 0) {
                            drawContext.fill(n11, n10, n11 + n9, n10 + n8, n7);
                            return;
                        }
                        ScreenCopyRenderer.m(n21, n12, n11, n10, n9, n8, bl3);
                        ScreenCopyRenderer.m595(n21, client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
                        ScreenCopyRenderer.m645(n11, n10, n9, n8);
                        drawContext2 = drawContext;
                        n19 = n11;
                        n18 = n10;
                        n17 = n11 + n9;
                        n16 = n10 + n8;
                        n15 = n7;
                        if (!bl2) break block17;
                        drawContext2.fill(n19, n18, n17, n16, n15);
                        n20 = bl3 ? 1 : 0;
                    }
                    if (n20 == 0) break block11;
                    drawContext2 = drawContext;
                    n19 = n11;
                    n18 = n10;
                    n17 = n9;
                    n16 = n8;
                    n15 = -13369498;
                }
                ScreenCopyRenderer.m696(drawContext2, n19, n18, n17, n16, n15);
            }
            catch (Throwable throwable) {
                ScreenCopyRenderer.m122(bl3, "failed: {}", new Object[]{throwable.toString()});
                ScreenCopyRenderer.m595(ScreenCopyRenderer.getInt74(), client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
                DrawContext drawContext3 = drawContext;
                int n22 = n11;
                int n23 = n10;
                int n24 = n11 + n9;
                int n25 = n10 + n8;
                int n26 = n7;
                if (bl2) {
                    drawContext3.fill(n22, n23, n24, n25, n26);
                    if (!bl3) break block11;
                    drawContext3 = drawContext;
                    n22 = n11;
                    n23 = n10;
                    n24 = n9;
                    n25 = n8;
                    n26 = -52429;
                }
                ScreenCopyRenderer.m696(drawContext3, n22, n23, n24, n25, n26);
            }
        }
    }

    public static void m638() {
        boolean bl = false;
        if (blur2 != null) {
            blur2.m216();
        }
        if (blur25 != null) {
            blur25.m216();
        }
        if (blur24 != null) {
            blur24.m216();
        }
        time21 = -1L;
        if (Module.getTextArray9() == null) {
            ScreenCopyRenderer.setFlag15(!false);
        }
    }

    private static void m183() {
        boolean bl = ScreenCopyRenderer.isSet38();
        ShaderProgram shaderProgram = shaderProgram5;
        if (bl) {
            if (shaderProgram != null) {
                return;
            }
            shaderProgram5 = new ShaderProgram("passthrough.vert", "kawase_blur.frag");
            ScreenCopyRenderer.shaderProgram = new ShaderProgram("passthrough.vert", "passthrough.frag");
            shaderProgram = new ShaderProgram("passthrough.vert", "screen_passthrough.frag");
        }
        shaderProgram4 = shaderProgram;
        blur2 = new Blur2(1.0);
        blur25 = new Blur2(0.5);
        blur24 = new Blur2(0.5);
    }

    private static void m(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        int fbo = n;
        int radius = n2;
        boolean debug = bl;
        Window window = client.getWindow();
        if (blur2.count174 != window.getFramebufferWidth() || blur2.count162 != window.getFramebufferHeight()) {
            blur2.m216();
        }
        if (blur25.count174 != Math.max(1, window.getFramebufferWidth() / 2) || blur25.count162 != Math.max(1, window.getFramebufferHeight() / 2)) {
            blur25.m216();
            blur24.m216();
        }
        long l = Timer.getLong10();
        if (time21 == l) {
            return;
        }
        time21 = l;
        int tex = ScreenCopyRenderer.getInt46();
        if (tex > 0) {
            ScreenCopyRenderer.m683(tex, blur2);
        } else {
            ScreenCopyRenderer.m110(fbo, blur2);
        }
        int iterations = Math.max(2, Math.min(6, radius / 3));
        if (debug) {
            ScreenCopyRenderer.m683(blur2.count170, blur25);
        }
        ScreenCopyRenderer.m657(blur25, blur2.count170, blur2.count174, blur2.count162, 1.0f);
        for (int i = 0; i < iterations; ++i) {
            float offset = (float)i + 1.5f;
            ScreenCopyRenderer.m657(blur24, blur25.count170, blur25.count174, blur25.count162, offset);
            ScreenCopyRenderer.m657(blur25, blur24.count170, blur24.count174, blur24.count162, offset);
        }
        ScreenCopyRenderer.m657(blur24, blur25.count170, blur25.count174, blur25.count162, 1.0f);
        GlStateManager._glUseProgram((int)0);
    }

    private static void m683(int n, Object object) {
        int n2 = n;
        Blur2 blur2 = (Blur2)object;
        blur2.m554();
        blur2.m78();
        GlStateManager._disableDepthTest();
        GlStateManager._disableBlend();
        shaderProgram.m1045();
        Pos.m437(n2, 0);
        shaderProgram.m241("uTexture", 0);
        GlBufferHelper.m504();
        GlBufferHelper.m149();
        GlBufferHelper.m582();
        GlStateManager._glUseProgram((int)0);
    }

    private static void m657(Object object, int n, int n2, int n3, float f) {
        Blur2 blur2 = (Blur2)object;
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        float f2 = f;
        blur2.m554();
        blur2.m78();
        GlStateManager._disableDepthTest();
        GlStateManager._disableBlend();
        shaderProgram5.m1045();
        Pos.m437(n4, 0);
        shaderProgram5.m241("uTexture", 0);
        shaderProgram5.m700("texelSize", 1.0 / (double)n5, 1.0 / (double)n6);
        shaderProgram5.m82("offset", f2);
        GlBufferHelper.m504();
        GlBufferHelper.m149();
        GlBufferHelper.m582();
    }

    private static void m645(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        Window window = client.getWindow();
        float f = (float)window.getFramebufferWidth() / (float)window.getScaledWidth();
        float f2 = (float)window.getFramebufferHeight() / (float)window.getScaledHeight();
        GlStateManager._disableDepthTest();
        GlStateManager._disableBlend();
        shaderProgram4.m1045();
        Pos.m437(ScreenCopyRenderer.blur24.count170, 0);
        shaderProgram4.m241("uTexture", 0);
        GlBufferHelper.m73((float)n5 * f, (float)n6 * f2, (float)n7 * f, (float)n8 * f2, window.getFramebufferWidth(), window.getFramebufferHeight());
        GlStateManager._glUseProgram((int)0);
        GlStateManager._enableBlend();
    }

    private static int m148(float f) {
        float f2 = f;
        Window window = client.getWindow();
        float f3 = (float)window.getFramebufferWidth() / (float)window.getScaledWidth();
        return Math.round(f2 * f3);
    }

    private static int m123(float f) {
        float f2 = f;
        Window window = client.getWindow();
        float f3 = (float)window.getFramebufferHeight() / (float)window.getScaledHeight();
        return Math.round((float)window.getFramebufferHeight() - f2 * f3);
    }

    private static void m110(int n, Object object) {
        int n2 = n;
        Blur2 blur2 = (Blur2)object;
        int n3 = GL30C.glGetInteger((int)36010);
        int n4 = GL30C.glGetInteger((int)36006);
        GL30C.glBindFramebuffer((int)36008, (int)n2);
        GL30C.glBindFramebuffer((int)36009, (int)blur2.getInt34());
        GL30C.glReadBuffer((int)36064);
        GL30C.glDrawBuffer((int)36064);
        GL30C.glBlitFramebuffer((int)0, (int)0, (int)client.getWindow().getFramebufferWidth(), (int)client.getWindow().getFramebufferHeight(), (int)0, (int)0, (int)blur2.count174, (int)blur2.count162, (int)16384, (int)9729);
        GL30C.glBindFramebuffer((int)36008, (int)n3);
        GL30C.glBindFramebuffer((int)36009, (int)n4);
    }

    private static int getInt21() {
        GpuTexture gpuTexture = client.getFramebuffer().getColorAttachment();
        boolean bl = ScreenCopyRenderer.isSet38();
        int n = gpuTexture instanceof GlTexture ? 1 : 0;
        if (bl) {
            if (n != 0) {
                GlTexture glTexture = (GlTexture)gpuTexture;
                return glTexture.getOrCreateFramebuffer(((GlBackend)RenderSystem.getDevice()).getBufferManager(), null);
            }
            n = 0;
        }
        return n;
    }

    private static int getInt46() {
        GpuTexture gpuTexture = client.getFramebuffer().getColorAttachment();
        boolean bl = false;
        if (gpuTexture instanceof GlTexture) {
            GlTexture glTexture = (GlTexture)gpuTexture;
            int n = glTexture.getGlId();
            return GL11C.glIsTexture((int)n) ? n : 0;
        }
        return 0;
    }

    private static int getInt74() {
        try {
            return ScreenCopyRenderer.getInt21();
        }
        catch (Throwable throwable) {
            return 0;
        }
    }

    private static void m595(int n, int n2, int n3) {
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        GL30C.glBindFramebuffer((int)36160, (int)n4);
        GlStateManager._viewport((int)0, (int)0, (int)n5, (int)n6);
        GlStateManager._activeTexture((int)33984);
    }

    private static void m696(Object object, int n, int n2, int n3, int n4, int n5) {
        DrawContext drawContext = (DrawContext)object;
        int n6 = n;
        int n7 = n2;
        int n8 = n3;
        int n9 = n4;
        int n10 = n5;
        drawContext.fill(n6, n7, n6 + n8, n7 + 1, n10);
        drawContext.fill(n6, n7 + n9 - 1, n6 + n8, n7 + n9, n10);
        drawContext.fill(n6, n7, n6 + 1, n7 + n9, n10);
        drawContext.fill(n6 + n8 - 1, n7, n6 + n8, n7 + n9, n10);
    }

    private static void m122(boolean bl, Object object, Object object2) {
        boolean bl2 = bl;
        String string = (String)object;
        Object[] objectArray = (Object[])object2;
        boolean bl3 = ScreenCopyRenderer.isSet38();
        if (!bl2) {
            return;
        }
        long l = System.currentTimeMillis();
        long l2 = l - time27;
        if (bl3) {
            if (l2 < 1000L) {
                return;
            }
            l2 = l;
        }
        time27 = l2;
        Logger.logger2.info("[BlurDebug] " + string, objectArray);
    }

    private static String m495(Object object) {
        Blur2 blur2 = (Blur2)object;
        String string = ScreenCopyRenderer.m711(blur2.count170);
        int n = blur2.count162;
        int n2 = blur2.count174;
        int n3 = blur2.count170;
        int n4 = blur2.getInt34();
        return "fbo=" + n4 + ",tex=" + n3 + ",size=" + n2 + "x" + n + ",texInfo=" + string;
    }

    private static String m1013(int n) {
        Object object;
        block11: {
            int n2;
            block10: {
                int n3 = n;
                int n4 = GL30C.glGetInteger((int)36010);
                int n5 = GL30C.glGetInteger((int)36006);
                GL30C.glBindFramebuffer((int)36009, (int)n3);
                int n6 = GL30C.glCheckFramebufferStatus((int)36009);
                GL30C.glBindFramebuffer((int)36008, (int)n4);
                boolean bl = ScreenCopyRenderer.isSet38();
                GL30C.glBindFramebuffer((int)36009, (int)n5);
                boolean bl2 = bl;
                n2 = n6;
                if (!bl2) break block10;
                switch (n2) {
                    case 36053: {
                        object = "complete";
                        break block11;
                    }
                    case 33305: {
                        object = "undefined";
                        break block11;
                    }
                    case 36054: {
                        object = "incomplete_attachment";
                        break block11;
                    }
                    case 36055: {
                        object = "missing_attachment";
                        break block11;
                    }
                    case 36059: {
                        object = "incomplete_draw_buffer";
                        break block11;
                    }
                    case 36060: {
                        object = "incomplete_read_buffer";
                        break block11;
                    }
                    case 36061: {
                        object = "unsupported";
                        break block11;
                    }
                    case 36182: {
                        object = "incomplete_multisample";
                        break block11;
                    }
                    default: {
                        n2 = n6;
                    }
                }
            }
            object = "0x" + Integer.toHexString(n2);
        }
        return (String)object;
    }

    private static String m711(int n) {
        int n2;
        int n3 = n;
        boolean bl = ScreenCopyRenderer.isSet38();
        int n4 = n3;
        if (bl) {
            if (n4 <= 0) {
                return n3 + "(none)";
            }
            n4 = GL11C.glIsTexture((int)n3) ? 1 : 0;
        }
        int n5 = n2 = n4;
        if (bl) {
            if (n5 == 0) {
                return n3 + "(not_gl_texture)";
            }
            n5 = GL11C.glGetInteger((int)32873);
        }
        int n6 = n5;
        GlStateManager._bindTexture((int)n3);
        int n7 = GL11C.glGetTexLevelParameteri((int)3553, (int)0, (int)4096);
        int n8 = GL11C.glGetTexLevelParameteri((int)3553, (int)0, (int)4097);
        int n9 = GL11C.glGetTexLevelParameteri((int)3553, (int)0, (int)4099);
        GlStateManager._bindTexture((int)n6);
        String string = Integer.toHexString(n9);
        int n10 = n8;
        int n11 = n7;
        return n3 + "(ok," + n11 + "x" + n10 + ",if=0x" + string + ")";
    }

    private static String m143(int n, int n2, int n3, int n4, int n5) {
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        block3: {
            block2: {
                n10 = n;
                n9 = n2;
                n8 = n3;
                n7 = n4;
                n6 = n5;
                boolean bl = false;
                if (n10 <= 0) break block2;
                if (n7 <= 0) break block2;
                if (n6 > 0) break block3;
            }
            return "unavailable";
        }
        int n11 = Math.max(0, Math.min(n7 - 1, n9));
        int n12 = Math.max(0, Math.min(n6 - 1, n8));
        int n13 = GL30C.glGetInteger((int)36010);
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)4);
        GL30C.glBindFramebuffer((int)36008, (int)n10);
        GL11C.glReadBuffer((int)36064);
        GL11C.glReadPixels((int)n11, (int)n12, (int)1, (int)1, (int)6408, (int)5121, (ByteBuffer)byteBuffer);
        GL30C.glBindFramebuffer((int)36008, (int)n13);
        String string = ScreenCopyRenderer.m87(byteBuffer.get(3));
        String string2 = ScreenCopyRenderer.m87(byteBuffer.get(2));
        String string3 = ScreenCopyRenderer.m87(byteBuffer.get(1));
        String string4 = ScreenCopyRenderer.m87(byteBuffer.get(0));
        int n14 = n12;
        int n15 = n11;
        return "(" + n15 + "," + n14 + ")=#" + string4 + string3 + string2 + string;
    }

    private static String m87(int n) {
        int n2 = n;
        return String.format("%02X", n2 & 0xFF);
    }

    private static String getText14() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        for (int i = 0; i < 16; ++i) {
            int n = GL11C.glGetError();
            if (n == 0) break;
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(',');
            }
            stringBuilder.append(ScreenCopyRenderer.m31(n));
            if (!false) continue;
            break;
        }
        return stringBuilder.isEmpty() ? "none" : stringBuilder.toString();
    }

    private static String m31(int n) {
        int n2 = n;
        boolean bl = false;
        return switch (n2) {
            case 1280 -> "GL_INVALID_ENUM";
            case 1281 -> "GL_INVALID_VALUE";
            case 1282 -> "GL_INVALID_OPERATION";
            case 1286 -> "GL_INVALID_FRAMEBUFFER_OPERATION";
            case 1285 -> "GL_OUT_OF_MEMORY";
            default -> "0x" + Integer.toHexString(n2);
        };
    }

    /*
     * Unable to fully structure code
     */
    static {}

    public static void setFlag15(boolean bl) {
        flag71 = bl;
    }

    public static boolean isSet180() {
        return flag71;
    }

    public static boolean isSet38() {
        boolean bl = false;
        return true;
    }
}

