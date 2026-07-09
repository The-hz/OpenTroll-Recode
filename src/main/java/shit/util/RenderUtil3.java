/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import shit.Client;

@Environment(value=EnvType.CLIENT)
public final class RenderUtil3 {
    private RenderUtil3() {
    }

    public static void m526(Object object, int n, int n2, int n3, int n4, int n5) {
        DrawContext drawContext = (DrawContext)object;
        int n6 = n;
        int n7 = n2;
        int n8 = n3;
        int n9 = n4;
        int n10 = n5;
        Object var13_12 = null;
        if (n8 <= 0 || n9 <= 0) {
            return;
        }
        drawContext.fill(n6, n7, n6 + n8, n7 + n9, n10);
    }

    public static void m919(Object object, int n, int n2, int n3, int n4, int n5) {
        DrawContext drawContext = (DrawContext)object;
        int n6 = n;
        int n7 = n2;
        int n8 = n3;
        int n9 = n4;
        int n10 = n5;
        drawContext.fill(n6, n7, n8, n9, n10);
    }

    public static void m795(Object object, int n, int n2, int n3, int n4, int n5) {
        DrawContext drawContext = (DrawContext)object;
        int n6 = n;
        int n7 = n2;
        int n8 = n3;
        int n9 = n4;
        int n10 = n5;
        Object var13_12 = null;
        if (n8 <= 0 || n9 <= 0) {
            return;
        }
        drawContext.fill(n6, n7, n6 + n8, n7 + 1, n10);
        drawContext.fill(n6, n7 + n9 - 1, n6 + n8, n7 + n9, n10);
        drawContext.fill(n6, n7, n6 + 1, n7 + n9, n10);
        drawContext.fill(n6 + n8 - 1, n7, n6 + n8, n7 + n9, n10);
    }

    public static void m454(Object object, Object object2, Object object3, int n, int n2, int n3, boolean bl) {
        block3: {
            boolean bl2;
            int n4;
            int n5;
            int n6;
            String string;
            DrawContext drawContext;
            block2: {
                TextRenderer textRenderer = (TextRenderer)object;
                drawContext = (DrawContext)object2;
                string = (String)object3;
                n6 = n;
                n5 = n2;
                n4 = n3;
                bl2 = bl;
                Object var15_14 = null;
                if (!Client.fontManager.isSet89()) break block2;
                drawContext.drawText(textRenderer, string, n6, n5, n4, bl2);
                if (null == null) break block3;
            }
            Client.fontManager.renderer2().m5(drawContext, string, n6, n5 + Math.round(Client.fontManager.getFloat47()), n4, bl2);
        }
    }

    public static int m517(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        return n3 & 0xFFFFFF | RenderUtil3.m600(n4) << 24;
    }

    public static int m23(int n, float f) {
        int n2 = n;
        float f2 = f;
        int n3 = (int)((float)(n2 >>> 24 & 0xFF) * Math.max(0.0f, Math.min(1.0f, f2)));
        return RenderUtil3.m517(n2, n3);
    }

    private static int m600(int n) {
        int n2 = n;
        return Math.max(0, Math.min(255, n2));
    }
}

