/*
 * Decompiled with CFR 0.152.
 */
package shit.setting;

import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.setting.Setting;

@Environment(value=EnvType.CLIENT)
public class ColorSetting
extends Setting {
    private final boolean flag60;
    private static final String a = "0x";

    public ColorSetting(String string, int n) {
        this(string, n, true, null, null, "", false);
    }

    public ColorSetting(String string, int n, boolean bl, BooleanSupplier booleanSupplier, BiFunction biFunction, String string2, boolean bl2) {
        super(string, n, booleanSupplier, biFunction, string2, bl2);
        this.flag60 = bl;
    }

    public boolean isSet30() {
        return this.flag60;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public void setObj22(Object var1_1) {
        int color = (Integer) var1_1;
        if (this.flag60) {
            super.setObj94(color);
        } else {
            super.setObj94(color | -16777216);
        }
    }

    @Override
    public String getText29() {
        return Integer.toUnsignedString((Integer)this.getObj(), 16);
    }

    @Override
    public void setObj58(Object object) {
        String string = (String)object;
        String string2 = Setting.getText54();
        String string3 = string;
        if (string2 == null) {
            if (string3 == null) {
                return;
            }
            string3 = string;
        }
        if (string2 == null) {
            string3 = string3.startsWith(a) ? string.substring(2) : string;
        }
        String string4 = string3;
        try {
            this.setObj22((int)Long.parseLong(string4, 16));
        }
        catch (NumberFormatException numberFormatException) {
            this.setObj22((Integer)this.getObj20());
        }
    }

    @Override
    public void setObj94(Object object) {
        Object object2 = object;
        this.setObj22((Integer)object2);
    }
}

