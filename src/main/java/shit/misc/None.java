/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import shit.misc.ModuleState;
import shit.render.Outline;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.ColorSetting2;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.setting.Setting;
import shit.setting.StringSetting;
import shit.util.FontUtil2;

@Environment(value=EnvType.CLIENT)
public class None
extends ModuleState {
    private final TextRenderer field27;
    private final Setting setting;
    private boolean flag102;
    private boolean flag164;
    private boolean flag120;
    private boolean flag108;
    private boolean flag48;
    private boolean flag62;
    private boolean flag109;

    public None(TextRenderer textRenderer, Setting setting) {
        this.field27 = textRenderer;
        this.setting = setting;
        this.count119 = 13;
    }

    public boolean isSet66() {
        return this.setting.isVisible();
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Override
    public void m945(Object var1_1, int var2_2, int var3_3) {
        DrawContext drawContext = (DrawContext) var1_1;
        int n = var2_2;
        int n2 = var3_3;
        if (!this.isSet66()) {
            return;
        }
        this.m874();
        boolean hovered = this.checkState(n, n2, this.count108, this.count103, this.count141, 13);
        int color = (hovered || this.flag48 || this.flag62) ? 1714633543 : 858402874;
        drawContext.fill(this.count108, this.count103, this.count108 + this.count141, this.count103 + 13, color);
        this.setObj76(drawContext);
        if (this.flag109 && this.setting instanceof EnumSetting) {
            this.m397(drawContext, (EnumSetting) this.setting);
        }
        if (this.flag109 && this.setting instanceof ColorSetting) {
            this.m808(drawContext, (ColorSetting) this.setting);
        }
    }

    private void setObj76(Object object) {
        block3: {
            DrawContext drawContext;
            block7: {
                block6: {
                    Object object2;
                    block5: {
                        block4: {
                            block2: {
                                drawContext = (DrawContext)object;
                                object2 = this.setting;
                                Object var4_7 = null;
                                if (!(object2 instanceof BooleanSetting)) break block2;
                                BooleanSetting booleanSetting = (BooleanSetting)object2;
                                int n = (Boolean)booleanSetting.getValue() != false ? -1439005464 : -2005897104;
                                drawContext.fill(this.count108 + this.count141 - 13, this.count103 + 3, this.count108 + this.count141 - 3, this.count103 + 10, n);
                                FontUtil2.drawTextSimple(this.field27, drawContext, this.setting.getDisplayName(), this.count108 + 2, this.count103 + 3, -1184275);
                                break block3;
                            }
                            object2 = this.setting;
                            if (!(object2 instanceof NumberSetting)) break block4;
                            NumberSetting numberSetting = (NumberSetting)object2;
                            double d = numberSetting.getMax() - numberSetting.getMin();
                            double d2 = d <= 0.0 ? 0.0 : ((Double)numberSetting.getValue() - numberSetting.getMin()) / d;
                            int n = (int)((double)this.count141 * this.m94(d2));
                            drawContext.fill(this.count108, this.count103, this.count108 + n, this.count103 + 13, -1439005464);
                            FontUtil2.drawTextSimple(this.field27, drawContext, this.setting.getDisplayName() + " " + this.m961((Double)numberSetting.getValue()), this.count108 + 2, this.count103 + 3, -1);
                            break block3;
                        }
                        object2 = this.setting;
                        if (!(object2 instanceof ColorSetting)) break block5;
                        ColorSetting colorSetting = (ColorSetting)object2;
                        int n = (Integer)colorSetting.getValue();
                        drawContext.fill(this.count108 + this.count141 - 18, this.count103 + 2, this.count108 + this.count141 - 3, this.count103 + 11, -15394784);
                        drawContext.fill(this.count108 + this.count141 - 17, this.count103 + 3, this.count108 + this.count141 - 4, this.count103 + 10, n);
                        FontUtil2.drawTextSimple(this.field27, drawContext, this.setting.getDisplayName() + " " + this.m482(colorSetting), this.count108 + 2, this.count103 + 3, -1184275);
                        break block3;
                    }
                    object2 = this.setting;
                    if (!(object2 instanceof ColorSetting2)) break block6;
                    ColorSetting2 colorSetting2 = (ColorSetting2)object2;
                    object2 = this.flag48 ? Outline.m14("Press key/mouse...") : colorSetting2.getDisplayName();
                    String string = this.setting.getDisplayName() + " " + (String)object2;
                    FontUtil2.drawTextSimple(this.field27, drawContext, string, this.count108 + 2, this.count103 + 3, -1184275);
                    String string2 = "[" + Outline.m14(colorSetting2.getType().name()) + "]";
                    int n = this.count108 + this.count141 - this.field27.getWidth(string2) - 5;
                    FontUtil2.drawTextSimple(this.field27, drawContext, string2, n, this.count103 + 3, -7829368);
                    break block3;
                }
                if (!(this.setting instanceof StringSetting)) break block7;
                FontUtil2.drawTextSimple(this.field27, drawContext, this.setting.getDisplayName() + " " + String.valueOf(this.setting.getValue()) + (this.flag62 ? "_" : ""), this.count108 + 2, this.count103 + 3, -1184275);
                if (null == null) break block3;
            }
            FontUtil2.drawTextSimple(this.field27, drawContext, this.setting.getDisplayName() + " " + this.getText21(), this.count108 + 2, this.count103 + 3, -1184275);
        }
    }

    private void m397(Object object, Object object2) {
        DrawContext drawContext = (DrawContext)object;
        EnumSetting enumSetting = (EnumSetting)object2;
        Enum enum_ = (Enum)enumSetting.getValue();
        Enum[] enumArray = (Enum[])enum_.getDeclaringClass().getEnumConstants();
        int n = this.count103 + 13;
        Enum[] enumArray2 = enumArray;
        int n2 = enumArray2.length;
        Object var6_10 = null;
        for (int i = 0; i < n2; ++i) {
            Enum enum_2 = enumArray2[i];
            boolean bl = enum_2 == enum_;
            drawContext.fill(this.count108, n, this.count108 + this.count141, n + 13, bl ? this.m1031(-1439005464, 170) : -1440733907);
            FontUtil2.drawTextSimple(this.field27, drawContext, this.m983(enum_2), this.count108 + 6, n + 3, bl ? -1 : -2039584);
            n += 13;
            if (null == null) continue;
        }
    }

    private void m808(Object object, Object object2) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        DrawContext drawContext = (DrawContext)object;
        ColorSetting colorSetting = (ColorSetting)object2;
        int n8 = this.count103 + 13 + 2;
        int n9 = this.count108 + 4;
        int n10 = n8;
        int n11 = Math.max(32, this.count141 - 20);
        int n12 = 36;
        int n13 = this.count108 + 4;
        int n14 = n10 + n12 + 3;
        int n15 = n11;
        Object var6_13 = null;
        int n16 = n14 + 8;
        int n17 = (Integer)colorSetting.getValue();
        float[] fArray = Color.RGBtoHSB(n17 >>> 16 & 0xFF, n17 >>> 8 & 0xFF, n17 & 0xFF, null);
        int n18 = 12;
        int n19 = n11 / n18;
        for (n7 = 0; n7 < n18; ++n7) {
            float f = (float)n7 / (float)(n18 - 1);
            n6 = 0xFF000000 | Color.HSBtoRGB(fArray[0], f, 1.0f);
            n5 = 0xFF000000 | Color.HSBtoRGB(fArray[0], f, 0.0f);
            n4 = n9 + n7 * n19;
            n3 = n7 == n18 - 1 ? n9 + n11 : n4 + n19;
            drawContext.fillGradient(n4, n10, n3, n10 + n12, n6, n5);
            if (null == null) continue;
        }
        n7 = n9 + Math.round(fArray[1] * (float)n11);
        int n20 = n10 + Math.round((1.0f - fArray[2]) * (float)n12);
        drawContext.fill(n7 - 2, n20 - 2, n7 + 2, n20 + 2, -1);
        n6 = 24;
        n5 = Math.max(1, n15 / n6);
        for (n4 = 0; n4 < n6; ++n4) {
            n3 = 0xFF000000 | Color.HSBtoRGB((float)n4 / (float)n6, 1.0f, 1.0f);
            n2 = n13 + n4 * n5;
            n = n4 == n6 - 1 ? n13 + n15 : n2 + n5;
            drawContext.fill(n2, n14, n, n14 + 5, n3);
            if (null == null) continue;
        }
        n4 = n13 + Math.round(fArray[0] * (float)n15);
        drawContext.fill(n4 - 1, n14 - 1, n4 + 2, n14 + 6, -1);
        if (colorSetting.isSet30()) {
            int n21;
            n3 = n17 >>> 24 & 0xFF;
            n2 = n17 | 0xFF000000;
            n = 16;
            int n22 = Math.max(1, n15 / n);
            for (n21 = 0; n21 < n; ++n21) {
                int n23 = Math.round(255.0f * (float)n21 / (float)(n - 1));
                int n24 = n13 + n21 * n22;
                int n25 = n21 == n - 1 ? n13 + n15 : n24 + n22;
                drawContext.fill(n24, n16, n25, n16 + 5, n23 << 24 | n2 & 0xFFFFFF);
                if (null == null) continue;
            }
            n21 = n13 + Math.round((float)n3 / 255.0f * (float)n15);
            drawContext.fill(n21 - 1, n16 - 1, n21 + 2, n16 + 6, -1);
        }
    }

    @Override
    public boolean m851(double d, double d2, int n) {
        Setting setting;
        Setting setting2;
        double d3 = d;
        double d4 = d2;
        int n2 = n;
        Object var12_7 = null;
        if (!this.isSet66()) {
            return false;
        }
        if (this.flag48) {
            setting2 = this.setting;
            if (setting2 instanceof ColorSetting2) {
                setting = (ColorSetting2)setting2;
                if (n2 != 1) {
                    setting.setValueInternal(ColorSetting2.encodeKey(n2));
                    this.flag48 = false;
                    return true;
                }
            }
        }
        if (this.flag109) {
            setting2 = this.setting;
            if (setting2 instanceof EnumSetting) {
                setting = (EnumSetting)setting2;
                if (this.checkState(d3, d4, this.count108, this.count103 + 13, this.count141, this.count119 - 13)) {
                    this.m954(setting, d4);
                    return true;
                }
            }
        }
        if (this.flag109) {
            setting2 = this.setting;
            if (setting2 instanceof ColorSetting) {
                setting = (ColorSetting)setting2;
                if (this.checkState(d3, d4, this.count108, this.count103 + 13, this.count141, this.count119 - 13)) {
                    this.m312(setting, d3, d4);
                    return true;
                }
            }
        }
        if (!this.checkState(d3, d4, this.count108, this.count103, this.count141, 13)) {
            return false;
        }
        this.m405(n2, d3);
        return true;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public boolean m510(double var1_1, double var3_2, int var5_3) {
        boolean wasActive = this.flag102 || this.flag164 || this.flag120 || this.flag108;
        this.flag102 = false;
        this.flag164 = false;
        this.flag120 = false;
        this.flag108 = false;
        return wasActive;
    }

    @Override
    public boolean m855(double d, double d2, int n, double d3, double d4) {
        block7: {
            Setting setting;
            double d5;
            double d6;
            block6: {
                d6 = d;
                d5 = d2;
                Object var20_8 = null;
                if (this.flag102) {
                    setting = this.setting;
                    if (setting instanceof NumberSetting) {
                        NumberSetting numberSetting = (NumberSetting)setting;
                        this.m685(numberSetting, d6);
                        return true;
                    }
                }
                if (this.flag164) break block6;
                if (this.flag120) break block6;
                if (!this.flag108) break block7;
            }
            setting = this.setting;
            if (setting instanceof ColorSetting) {
                ColorSetting colorSetting = (ColorSetting)setting;
                this.m38(colorSetting, d6, d5);
                return true;
            }
        }
        return false;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public boolean m121(int var1_1, int var2_2, int var3_3) {
        int key = var1_1;
        if (this.flag48 && this.setting instanceof ColorSetting2) {
            ColorSetting2 cs = (ColorSetting2) this.setting;
            int v0 = (key == 256 || key == 261) ? -1 : key;
            cs.setValueInternal(v0);
            this.flag48 = false;
            return true;
        }
        if (this.flag62 && this.setting instanceof StringSetting) {
            StringSetting ss = (StringSetting) this.setting;
            if (key == 256 || key == 257) {
                this.flag62 = false;
                return true;
            }
            if (key == 259) {
                String s = (String) ss.getValue();
                if (!s.isEmpty()) {
                    ss.setValueInternal(s.substring(0, s.length() - 1));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean m650(int n, int n2) {
        char c = (char)n;
        Object var6_4 = null;
        if (this.flag62) {
            Setting setting = this.setting;
            if (setting instanceof StringSetting) {
                StringSetting stringSetting = (StringSetting)setting;
                if (c >= ' ') {
                    if (c != '\u007f') {
                        stringSetting.setValueInternal((String)stringSetting.getValue() + c);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void m405(int n, double d) {
        block5: {
            block11: {
                block12: {
                    Setting setting;
                    int n2;
                    block10: {
                        block8: {
                            NumberSetting numberSetting;
                            block9: {
                                double d2;
                                block6: {
                                    EnumSetting enumSetting;
                                    block7: {
                                        block4: {
                                            n2 = n;
                                            d2 = d;
                                            setting = this.setting;
                                            Object var8_6 = null;
                                            if (!(setting instanceof BooleanSetting)) break block4;
                                            BooleanSetting booleanSetting = (BooleanSetting)setting;
                                            booleanSetting.setValueInternal((Boolean)booleanSetting.getValue() == false);
                                            if (null == null) break block5;
                                        }
                                        setting = this.setting;
                                        if (!(setting instanceof EnumSetting)) break block6;
                                        enumSetting = (EnumSetting)setting;
                                        if (n2 != 1) break block7;
                                        boolean bl = this.flag109 = !this.flag109;
                                        if (null == null) break block5;
                                    }
                                    enumSetting.cycle();
                                    if (null == null) break block5;
                                }
                                setting = this.setting;
                                if (!(setting instanceof NumberSetting)) break block8;
                                numberSetting = (NumberSetting)setting;
                                if (n2 != 0) break block9;
                                this.flag102 = true;
                                this.m685(numberSetting, d2);
                                if (null == null) break block5;
                            }
                            numberSetting.setDouble((Double)numberSetting.getValue() - numberSetting.getStep());
                            if (null == null) break block5;
                        }
                        if (!(this.setting instanceof ColorSetting)) break block10;
                        if (n2 != 1) break block5;
                        boolean bl = this.flag109 = !this.flag109;
                        if (null == null) break block5;
                    }
                    setting = this.setting;
                    if (!(setting instanceof ColorSetting2)) break block11;
                    ColorSetting2 colorSetting2 = (ColorSetting2)setting;
                    if (n2 != 1) break block12;
                    colorSetting2.m348();
                    if (null == null) break block5;
                }
                this.flag48 = true;
                if (null == null) break block5;
            }
            if (this.setting instanceof StringSetting) {
                this.flag62 = true;
            }
        }
        this.m874();
    }

    private void m954(Object object, double d) {
        EnumSetting enumSetting = (EnumSetting)object;
        double d2 = d;
        Enum enum_ = (Enum)enumSetting.getValue();
        Enum[] enumArray = (Enum[])enum_.getDeclaringClass().getEnumConstants();
        Object var8_7 = null;
        int n = (int)((d2 - (double)(this.count103 + 13)) / 13.0);
        if (n >= 0 && n < enumArray.length) {
            enumSetting.setValueInternal(enumArray[n]);
        }
        this.flag109 = false;
        this.m874();
    }

    private void m685(Object object, double d) {
        NumberSetting numberSetting = (NumberSetting)object;
        double d2 = d;
        double d3 = (d2 - (double)this.count108) / Math.max(1.0, (double)this.count141);
        double d4 = numberSetting.getMin() + (numberSetting.getMax() - numberSetting.getMin()) * this.m94(d3);
        Object var8_7 = null;
        double d5 = numberSetting.getStep();
        if (d5 > 0.0) {
            d4 = (double)Math.round(d4 / d5) * d5;
        }
        numberSetting.setDouble(d4);
    }

    private void m312(Object object, double d, double d2) {
        double d3;
        double d4;
        ColorSetting colorSetting;
        block6: {
            int n;
            block7: {
                int n2;
                block5: {
                    int n3;
                    colorSetting = (ColorSetting)object;
                    d4 = d;
                    d3 = d2;
                    int n4 = n3 = this.count103 + 13 + 2;
                    int n5 = 36;
                    n2 = n4 + n5 + 3;
                    Object var12_11 = null;
                    n = n2 + 8;
                    if (!(d3 >= (double)n4)) break block5;
                    if (!(d3 <= (double)(n4 + n5))) break block5;
                    this.flag164 = true;
                    if (null == null) break block6;
                }
                if (!(d3 >= (double)n2)) break block7;
                if (!(d3 <= (double)(n2 + 5))) break block7;
                this.flag120 = true;
                if (null == null) break block6;
            }
            if (colorSetting.isSet30()) {
                if (d3 >= (double)n && d3 <= (double)(n + 5)) {
                    this.flag108 = true;
                }
            }
        }
        this.m38(colorSetting, d4, d3);
    }

    private void m38(Object object, double d, double d2) {
        float[] fArray;
        int n;
        ColorSetting colorSetting;
        block5: {
            int n2;
            int n3;
            double d3;
            block6: {
                block4: {
                    colorSetting = (ColorSetting)object;
                    d3 = d;
                    double d4 = d2;
                    int n4 = this.count103 + 13 + 2;
                    int n5 = this.count108 + 4;
                    int n6 = n4;
                    int n7 = Math.max(32, this.count141 - 20);
                    int n8 = 36;
                    n3 = this.count108 + 4;
                    int cfr_ignored_0 = n6 + n8 + 3;
                    n2 = n7;
                    int n9 = (Integer)colorSetting.getValue();
                    n = n9 >>> 24 & 0xFF;
                    int n10 = n9 >>> 16 & 0xFF;
                    Object var12_17 = null;
                    int n11 = n9 >>> 8 & 0xFF;
                    int n12 = n9 & 0xFF;
                    fArray = Color.RGBtoHSB(n10, n11, n12, null);
                    if (!this.flag164) break block4;
                    fArray[1] = (float)this.m94((d3 - (double)n5) / Math.max(1.0, (double)n7));
                    fArray[2] = 1.0f - (float)this.m94((d4 - (double)n6) / Math.max(1.0, (double)n8));
                    if (null == null) break block5;
                }
                if (!this.flag120) break block6;
                fArray[0] = (float)this.m94((d3 - (double)n3) / Math.max(1.0, (double)n2));
                if (null == null) break block5;
            }
            if (this.flag108) {
                n = this.m471((int)Math.round((d3 - (double)n3) / Math.max(1.0, (double)n2) * 255.0));
            }
        }
        int n13 = Color.HSBtoRGB(fArray[0], fArray[1], fArray[2]);
        colorSetting.setObj22(n << 24 | n13 & 0xFFFFFF);
    }

    private void m874() {
        block3: {
            block4: {
                block2: {
                    Object var2_1 = null;
                    if (!this.flag109) break block2;
                    Object object = this.setting;
                    if (!(object instanceof EnumSetting)) break block2;
                    EnumSetting enumSetting = (EnumSetting)object;
                    object = (Enum)enumSetting.getValue();
                    this.count119 = 13 + ((Enum[])((Enum)object).getDeclaringClass().getEnumConstants()).length * 13;
                    if (null == null) break block3;
                }
                if (!this.flag109) break block4;
                if (!(this.setting instanceof ColorSetting)) break block4;
                this.count119 = 77;
                if (null == null) break block3;
            }
            this.count119 = 13;
        }
    }

    private String getText21() {
        Object object = this.setting;
        Object var2_2 = null;
        if (object instanceof ColorSetting2) {
            ColorSetting2 colorSetting2 = (ColorSetting2)object;
            return colorSetting2.isBound() ? Integer.toString((Integer)colorSetting2.getValue()) : "none";
        }
        object = this.setting;
        if (object instanceof NumberSetting) {
            NumberSetting numberSetting = (NumberSetting)object;
            return this.m961((Double)numberSetting.getValue());
        }
        object = this.setting.getValue();
        if (object instanceof Enum) {
            Enum enum_ = (Enum)object;
            return this.m983(enum_);
        }
        return String.valueOf(this.setting.getValue());
    }

    private String m983(Object object) {
        Enum enum_ = (Enum)object;
        Object var4_3 = null;
        if (Outline.isSet130()) {
            return Outline.m130(enum_);
        }
        String string = enum_.name().toLowerCase().replace('_', ' ');
        StringBuilder stringBuilder = new StringBuilder(string.length());
        boolean bl = true;
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            stringBuilder.append(bl ? Character.toUpperCase(c) : c);
            bl = c == ' ';
            if (null == null) continue;
        }
        return stringBuilder.toString();
    }

    private String m482(Object object) {
        ColorSetting colorSetting = (ColorSetting)object;
        int n = (Integer)colorSetting.getValue();
        Object var4_4 = null;
        Object[] objectArray = new Object[1];
        objectArray[0] = colorSetting.isSet30() ? n : n & 0xFFFFFF;
        return String.format(colorSetting.isSet30() ? "#%08X" : "#%06X", objectArray);
    }

    private String m961(double d) {
        double d2 = d;
        Object var6_3 = null;
        return Math.abs(d2 - Math.rint(d2)) < 1.0E-4 ? Integer.toString((int)Math.rint(d2)) : String.format("%.1f", d2);
    }

    private double m94(double d) {
        double d2 = d;
        return Math.max(0.0, Math.min(1.0, d2));
    }

    private int m471(int n) {
        int n2 = n;
        return Math.max(0, Math.min(255, n2));
    }

    private int m1031(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        return n3 & 0xFFFFFF | this.m471(n4) << 24;
    }
}

