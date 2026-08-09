/*
 * Decompiled with CFR 0.152.
 */
package shit.setting;

import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Module;
import shit.setting.Setting;

@Environment(value=EnvType.CLIENT)
public class EnumSetting
extends Setting {
    private final Class class_2;
    private static String[] texts9;

    public EnumSetting(String string, Enum enum_) {
        super(string, enum_);
        this.class_2 = enum_.getDeclaringClass();
    }

    public EnumSetting(String string, Enum enum_, BooleanSupplier booleanSupplier, BiFunction biFunction, String string2, boolean bl) {
        super(string, enum_, booleanSupplier, biFunction, string2, bl);
        this.class_2 = enum_.getDeclaringClass();
    }

    public void cycle() {
        block1: {
            Enum[] enumArray = (Enum[])this.class_2.getEnumConstants();
            int n = ((Enum)this.getValue()).ordinal() + 1;
            String string = Setting.getText54();
            int n2 = n;
            if (string == null) {
                n2 = n2 >= enumArray.length ? 0 : n;
            }
            this.setValueInternal(enumArray[n2]);
            if (Module.getTextArray9() != null) break block1;
            Setting.setText14("m4Tdhc");
        }
    }

    @Override
    public String getValueString() {
        return ((Enum)this.getValue()).name();
    }

    @Override
    public void setValueFromString(Object object) {
        String string = (String)object;
        String string2 = Setting.getText54();
        if (string2 == null) {
            if (string == null) {
                return;
            }
            try {
                this.setValueInternal(Enum.valueOf(this.class_2, string));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                this.setValueInternal((Enum)this.getDefaultValue());
            }
        }
    }

    public static void setTextArray8(String[] stringArray) {
        texts9 = stringArray;
    }

    public static String[] getTextArray8() {
        return texts9;
    }

    static {
        if (EnumSetting.getTextArray8() != null) {
            EnumSetting.setTextArray8(new String[2]);
        }
    }
}

