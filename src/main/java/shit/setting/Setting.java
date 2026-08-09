/*
 * Decompiled with CFR 0.152.
 */
package shit.setting;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Module;
import shit.render.Outline;

@Environment(value=EnvType.CLIENT)
public abstract class Setting {
    private final String settingName;
    private final Object defaultValueField;
    private final String text3213;
    private final BooleanSupplier visibilityPredicate;
    private final BiFunction biFunction;
    private final boolean flag133;
    private Object currentValue;
    private static String text508;

    protected Setting(String string, Object object3) {
        this(string, object3, () -> true, (object, object2) -> object2, "", false);
    }

    protected Setting(String string, Object object, BooleanSupplier visibilityPredicate, BiFunction biFunction, String string2, boolean bl) {
        this.settingName = string;
        this.defaultValueField = object;
        this.currentValue = object;
        this.visibilityPredicate = Objects.requireNonNullElseGet(visibilityPredicate, () -> () -> true);
        this.biFunction = Objects.requireNonNullElseGet(biFunction, () -> (a, b) -> b);
        this.text3213 = string2 != null ? string2 : "";
        this.flag133 = bl;
    }

    public String getName() {
        return this.settingName;
    }

    public String getDisplayName() {
        return Outline.m14(this.settingName);
    }

    public Object getDefaultValue() {
        return this.defaultValueField;
    }

    public boolean isVisible() {
        return this.visibilityPredicate.getAsBoolean();
    }

    public Object getValue() {
        return this.currentValue;
    }

    public void setValueInternal(Object object) {
        Object object2 = object;
        this.currentValue = this.biFunction.apply(this.currentValue, object2);
    }

    public abstract String getValueString();

    public abstract void setValueFromString(Object var1);

    public static void setText14(String string) {
        text508 = string;
    }

    public static String getText54() {
        return text508;
    }

    static {
        if (Setting.getText54() != null) {
            Setting.setText14("G2BL");
        }
    }
}

