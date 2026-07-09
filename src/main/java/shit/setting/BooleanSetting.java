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
public class BooleanSetting
extends Setting {
    private static String text3492;

    public BooleanSetting(String string, boolean bl) {
        super(string, bl);
    }

    public BooleanSetting(String string, boolean bl, BooleanSupplier booleanSupplier, BiFunction biFunction, String string2, boolean bl2) {
        super(string, bl, booleanSupplier, biFunction, string2, bl2);
    }

    @Override
    public String getText29() {
        return Boolean.toString((Boolean)this.getObj());
    }

    @Override
    public void setObj58(Object object) {
        String string = (String)object;
        this.setObj94(Boolean.parseBoolean(string));
    }

    public static void setText13(String string) {
        text3492 = string;
    }

    public static String getText46() {
        return text3492;
    }

    static {
        if (BooleanSetting.getText46() != null) {
            BooleanSetting.setText13("C1buC");
        }
    }
}

