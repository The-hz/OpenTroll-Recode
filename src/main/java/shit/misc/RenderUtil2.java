/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import shit.Client;
import shit.manager.FontManager2;
import shit.module.Module;
import shit.type.Enum_NuWsin;
import shit.util.RenderUtil3;

@Environment(value=EnvType.CLIENT)
public class RenderUtil2 {
    public static float value172;
    private final String text3150;
    private final Runnable runnable;
    private float value117;
    private float value180;
    private float value186;
    private final float value193;
    private Type type12 = Type.NONE;
    private Type type3 = Type.NONE;
    private long time32 = System.currentTimeMillis();
    private static int[] counts13;

    public RenderUtil2(String string, float f, Runnable runnable) {
        this.text3150 = string;
        this.value193 = f;
        this.runnable = runnable;
    }

    public String getText71() {
        return this.text3150;
    }

    public void m803(float f, float f2, float f3) {
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        this.value117 = f4;
        this.value180 = f5;
        this.value186 = f6;
    }

    public void setObj113(Object object) {
        // Reconstructed from bytecode: draw the button background (animated hover color) and its label.
        DrawContext drawContext = (DrawContext) object;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        float f = 1.0f;
        int n3 = RenderUtil2.m837(RenderUtil2.m255((Object) this.type3), RenderUtil2.m255((Object) this.type12), Enum_NuWsin.OUT_EXPO.m984(Enum_NuWsin.m164(this.time32, 300.0f)));
        float f2 = value172;
        RenderUtil3.m919(drawContext, Math.round(this.value117 + 1.0f), Math.round(this.value180 + 1.0f), Math.round(this.value117 + f2 + 2.0f), Math.round(this.value180 + f + 2.0f), -1606401984);
        RenderUtil3.m919(drawContext, Math.round(this.value117), Math.round(this.value180), Math.round(this.value117 + f2), Math.round(this.value180 + f), n3);
        String string2 = this.text3150.isEmpty() ? "" : this.text3150.substring(0, 1);
        String string = this.text3150.length() > 1 ? this.text3150.substring(1) : "";
        int n2 = Math.round(this.value117);
        int n = Math.round(this.value180 + f + 5.0f);
        if (Client.fontManager.isSet89()) {
            TextRenderer tr = minecraftClient.textRenderer;
            drawContext.drawText(tr, string2, n2, n, -1663446, true);
            drawContext.drawText(tr, string, n2 + tr.getWidth(string2), n, -1, true);
        } else {
            FontManager2 fm = Client.fontManager.renderer2();
            int n4 = Math.round(Client.fontManager.getFloat47());
            fm.m5(drawContext, string2, n2, n + n4, -1663446, true);
            fm.m5(drawContext, string, n2 + fm.m277(string2), n + n4, -1, true);
        }
    }

    /*
     * Unable to fully structure code
     */
    public boolean m49(double var1_1, double var3_2) {
        // Reconstructed (opaque ** GOTO folded): is the cursor within this menu button's bounds?
        double x = var1_1;
        double y = var3_2;
        float lineH = Client.fontManager.isSet89() ? 9.0f : (float) Client.fontManager.renderer2().getInt19();
        float h = 6.0f + lineH;
        return x >= (double) this.value117 && x <= (double) (this.value117 + this.value186)
            && y >= (double) this.value180 && y <= (double) (this.value180 + h);
    }

    public void m194(double d, double d2) {
        block5: {
            block4: {
                double d3 = d;
                double d4 = d2;
                Object var10_5 = null;
                if (!this.m49(d3, d4)) break block4;
                if (this.type12 != Type.NONE) break block5;
                this.setObj55((Object)Type.HOVER);
                if (null == null) break block5;
            }
            if (this.type12 == Type.HOVER) {
                this.setObj55((Object)Type.NONE);
            }
        }
    }

    public void m421(double d, double d2, int n) {
        double d3 = d;
        double d4 = d2;
        int n2 = n;
        Object var12_7 = null;
        if (n2 == 0) {
            if (this.m49(d3, d4)) {
                this.setObj55((Object)Type.CLICK);
            }
        }
    }

    public void m946(double d, double d2, int n) {
        double d3 = d;
        double d4 = d2;
        int n2 = n;
        Object var12_7 = null;
        if (n2 == 0) {
            if (this.type12 == Type.CLICK) {
                this.runnable.run();
            }
            this.setObj55((Object)(this.m49(d3, d4) ? Type.HOVER : Type.NONE));
        }
    }

    private void setObj55(Object object) {
        Type type = (Type)((Object)object);
        this.type3 = this.type12;
        this.type12 = type;
        this.time32 = System.currentTimeMillis();
    }

    private static int m255(Object object) {
        Type type = (Type)((Object)object);
        Object var3_2 = null;
        return switch (type.ordinal()) {
            case 1 -> -2655961;
            case 2 -> -5480160;
            default -> -4737097;
        };
    }

    private static int m837(int n, int n2, float f) {
        int n3 = n;
        int n4 = n2;
        float f2 = f;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        int n8 = n3 >> 24 & 0xFF;
        int n9 = n4 >> 16 & 0xFF;
        int n10 = n4 >> 8 & 0xFF;
        int n11 = n4 & 0xFF;
        int n12 = n4 >> 24 & 0xFF;
        int n13 = Math.round((float)n5 + (float)(n9 - n5) * f2);
        int n14 = Math.round((float)n6 + (float)(n10 - n6) * f2);
        int n15 = Math.round((float)n7 + (float)(n11 - n7) * f2);
        int n16 = Math.round((float)n8 + (float)(n12 - n8) * f2);
        return n16 << 24 | n13 << 16 | n14 << 8 | n15;
    }

    static {
        long[] lArray = new long[17];
        boolean bl = false;
        RenderUtil2.setIntArray2(null);
        value172 = 60.0f;
    }

    public static void setIntArray2(int[] nArray) {
        counts13 = nArray;
    }

    public static int[] getIntArray8() {
        return counts13;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Type {
      NONE, HOVER, CLICK;

      private Type() {}



        private static Type[] getTypeArray6() {
            return new Type[]{NONE, HOVER, CLICK};
        }
    
   }
}

