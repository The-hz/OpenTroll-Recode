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
public class NumberSetting
extends Setting {
    private final double value169;
    private final double value205;
    private final double value112;
    private final double value114;
    private static int[] counts2;

    public NumberSetting(String string, double d, double d2, double d3) {
        this(string, d, d2, d3, 1.0);
    }

    public NumberSetting(String string, double d, double d2, double d3, double d4) {
        this(string, d, d2, d3, d4, d4, null, null, "", false);
    }

    public NumberSetting(String string, double d, double d2, double d3, double d4, double d5, BooleanSupplier booleanSupplier, BiFunction biFunction, String string2, boolean bl) {
        super(string, d, booleanSupplier, biFunction, string2, bl);
        this.value169 = d2;
        this.value205 = d3;
        this.value112 = d4;
        this.value114 = d5;
    }

    public long getLong() {
        return ((Double)this.getObj()).longValue();
    }

    public double getDouble16() {
        return this.value169;
    }

    public double getDouble22() {
        return this.value205;
    }

    public double getDouble20() {
        return this.value112;
    }

    public void setObj85(Object object) {
        // DeBox erasure: callers pass int (autoboxed Integer) as well as Double -> accept any Number.
        double d = ((Number) object).doubleValue();
        super.setObj94(Math.max(this.value169, Math.min(this.value205, d)));
    }

    public int getInt50() {
        return ((Double)this.getObj()).intValue();
    }

    public float getFloat35() {
        return ((Double)this.getObj()).floatValue();
    }

    @Override
    public String getText29() {
        return Double.toString((Double)this.getObj());
    }

    @Override
    public void setObj58(Object object) {
        try {
            String string = (String)object;
            this.setObj85(Double.parseDouble(string));
        }
        catch (NumberFormatException numberFormatException) {
            this.setObj85((Double)this.getObj20());
        }
    }

    @Override
    public void setObj94(Object object) {
        Object object2 = object;
        this.setObj85((Double)object2);
    }

    public static void setIntArray(int[] nArray) {
        counts2 = nArray;
    }

    public static int[] getIntArray4() {
        return counts2;
    }

    static {
        if (NumberSetting.getIntArray4() != null) {
            NumberSetting.setIntArray(new int[2]);
        }
    }
}

