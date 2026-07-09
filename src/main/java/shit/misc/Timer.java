/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.misc.Helper7;
import shit.type.EaseMode;

@Environment(value=EnvType.CLIENT)
public class Timer {
    public long time30;
    private long time48;

    public Timer(long l) {
        this.time30 = l;
        this.m136();
    }

    public void m136() {
        this.time48 = System.currentTimeMillis();
    }

    public boolean isSet45() {
        String[] stringArray = Helper7.getTextArray10();
        long l = this.getLong2() - this.time30;
        long l2 = l == 0L ? 0 : (l < 0L ? -1 : 1);
        if (stringArray == null) {
            l2 = l2 >= 0 ? (long)1 : (long)0;
        }
        return l2 != 0;
    }

    protected long getLong2() {
        return System.currentTimeMillis() - this.time48;
    }

    public void setLong4(long l) {
        long l2;
        this.time30 = l2 = l;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public double getDouble4() {
        String[] stringArray = Helper7.getTextArray10();
        Timer timer = this;
        if (stringArray == null) {
            if (timer.isSet45()) {
                return 1.0;
            }
            timer = this;
        }
        double d = (double)timer.getLong2() / (double)this.time30;
        return d;
    }

    public double m1037(Object object) {
        EaseMode easeMode = (EaseMode)((Object)object);
        return easeMode.m799(this.getDouble4());
    }
}

