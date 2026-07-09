/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import com.mojang.blaze3d.systems.RenderPass;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.util.MC;
import shit.util.RenderUtil4;

@Environment(value=EnvType.CLIENT)
public final class Util {
    private Util() {
    }

    public static RenderUtil4.ColorData m74(float f, float f2, float f3, float f4) {
        float f5 = f;
        float f6 = f2;
        float f7 = f3;
        float f8 = f4;
        double d = RenderUtil4.getDouble18();
        int n = Util.getInt58();
        int n2 = (int)Math.round((double)f5 * d);
        int n3 = (int)Math.round((double)n - (double)(f6 + f8) * d);
        int n4 = Math.max(0, (int)Math.round((double)f7 * d));
        int n5 = Math.max(0, (int)Math.round((double)f8 * d));
        return Util.m1033(n2, n3, n4, n5);
    }

    public static RenderUtil4.ColorData m830(float f, float f2, float f3, float f4, float f5) {
        float f6 = f;
        float f7 = f2;
        float f8 = f3;
        float f9 = f4;
        float f10 = f5;
        double d = RenderUtil4.getDouble18();
        int n = (int)Math.round((double)f6 * d);
        int n2 = (int)Math.round((double)(f10 - f7 - f9) * d);
        int n3 = Math.max(0, (int)Math.round((double)f8 * d));
        int n4 = Math.max(0, (int)Math.round((double)f9 * d));
        return Util.m1033(n, n2, n3, n4);
    }

    public static RenderUtil4.ColorData m1033(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        int n9 = Util.getInt63();
        int n10 = Util.getInt58();
        int n11 = Math.clamp((long)n5, 0, n9);
        int n12 = Math.clamp((long)n6, 0, n10);
        int n13 = Math.clamp((long)(n5 + n7), 0, n9);
        int n14 = Math.clamp((long)(n6 + n8), 0, n10);
        return new RenderUtil4.ColorData(n11, n12, Math.max(0, n13 - n11), Math.max(0, n14 - n12));
    }

    public static boolean m101(Object object) {
        RenderUtil4.ColorData colorData = (RenderUtil4.ColorData)object;
        return Util.m843(colorData.count32(), colorData.count33());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m843(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        Object var5_4 = null;
        if (n3 <= 0) return false;
        if (n4 <= 0) return false;
        return true;
    }

    public static boolean m268(Object object, int n, int n2, int n3, int n4) {
        RenderPass renderPass = (RenderPass)object;
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        Object var11_10 = null;
        if (!Util.m843(n7, n8)) {
            return false;
        }
        renderPass.enableScissor(n5, n6, n7, n8);
        return true;
    }

    public static boolean m599(Object object, Object object2) {
        RenderPass renderPass = (RenderPass)object;
        RenderUtil4.ColorData colorData = (RenderUtil4.ColorData)object2;
        return Util.m268(renderPass, colorData.count30(), colorData.count31(), colorData.count32(), colorData.count33());
    }

    private static int getInt63() {
        RenderUtil4.AutoCloseableImpl autoCloseableImpl = RenderUtil4.getAutoCloseableImpl2();
        Object var1_1 = null;
        if (autoCloseableImpl != null) {
            return autoCloseableImpl.getInt25();
        }
        return MC.client3.getWindow().getFramebufferWidth();
    }

    private static int getInt58() {
        RenderUtil4.AutoCloseableImpl autoCloseableImpl = RenderUtil4.getAutoCloseableImpl2();
        Object var1_1 = null;
        if (autoCloseableImpl != null) {
            return autoCloseableImpl.getInt87();
        }
        return MC.client3.getWindow().getFramebufferHeight();
    }
}

