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
    private final String text265;
    private final Object field47;
    private final String text3213;
    private final BooleanSupplier booleanSupplier;
    private final BiFunction biFunction;
    private final boolean flag133;
    private Object field48;
    private static String text508;

    protected Setting(String string, Object object3) {
        this(string, object3, () -> true, (object, object2) -> object2, "", false);
    }

    protected Setting(String string, Object object, BooleanSupplier booleanSupplier, BiFunction biFunction, String string2, boolean bl) {
        this.text265 = string;
        this.field47 = object;
        this.field48 = object;
        this.booleanSupplier = Objects.requireNonNullElseGet(booleanSupplier, () -> () -> true);
        this.biFunction = Objects.requireNonNullElseGet(biFunction, () -> (a, b) -> b);
        this.text3213 = string2 != null ? string2 : "";
        this.flag133 = bl;
    }

    public String getName() {
        return this.text265;
    }

    public String getDisplayName() {
        return Outline.m14(this.text265);
    }

    public Object getDefaultValue() {
        return this.field47;
    }

    public boolean isVisible() {
        return this.booleanSupplier.getAsBoolean();
    }

    public Object getValue() {
        return this.field48;
    }

    public void setValueInternal(Object object) {
        Object object2 = object;
        this.field48 = this.biFunction.apply(this.field48, object2);
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

