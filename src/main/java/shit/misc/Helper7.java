/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class Helper7 {
    private long time40 = -1L;
    private static String[] texts10;

    public Helper7() {
        this.m533();
    }

    public void m533() {
        this.time40 = System.currentTimeMillis();
    }

    public long getLong12() {
        return System.currentTimeMillis() - this.time40;
    }

    public boolean m114(double d) {
        double d2 = d;
        return this.m336((long)(d2 * 1000.0));
    }

    public boolean m432(double d) {
        double d2 = d;
        return this.m336((long)d2);
    }

    public void setLong3(long l) {
        long l2 = l;
        this.time40 = System.currentTimeMillis() - l2;
    }

    public boolean m336(long l) {
        return System.currentTimeMillis() - this.time40 >= l;
    }

    public static void setTextArray3(String[] stringArray) {
        texts10 = stringArray;
    }

    public static String[] getTextArray10() {
        return texts10;
    }

    static {
        block0: {
            if (Helper7.getTextArray10() == null) break block0;
            Helper7.setTextArray3(new String[3]);
        }
    }
}

