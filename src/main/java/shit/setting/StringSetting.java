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
public class StringSetting
extends Setting {
    public StringSetting(String string, String string2) {
        super(string, string2);
    }

    public StringSetting(String string, String string2, BooleanSupplier booleanSupplier, BiFunction biFunction, String string3, boolean bl) {
        super(string, string2, booleanSupplier, biFunction, string3, bl);
    }

    @Override
    public String getValueString() {
        return (String)this.getValue();
    }

    @Override
    public void setValueFromString(Object object) {
        String string = (String)object;
        this.setValueInternal(string);
    }
}

