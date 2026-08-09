/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import shit.util.VanillaTextHelper;

@Environment(value=EnvType.CLIENT)
public final class FontUtil2 {
    private static boolean flag151;

    private FontUtil2() {
    }

    public static void drawTextSimple(Object object, Object object2, Object object3, int n, int n2, int n3) {
        TextRenderer textRenderer = (TextRenderer)object;
        DrawContext drawContext = (DrawContext)object2;
        String string = (String)object3;
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        VanillaTextHelper.m454(textRenderer, drawContext, string, n4, n5, n6, false);
    }

    public static void setFlag7(boolean bl) {
        flag151 = bl;
    }

    public static boolean isSet8() {
        return flag151;
    }

    public static boolean isSet101() {
        boolean bl = false;
        return true;
    }

    static {
        FontUtil2.setFlag7(true);
    }
}

