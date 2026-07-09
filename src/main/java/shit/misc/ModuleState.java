/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public abstract class ModuleState {
    public static final int count118 = 0;
    public static final int count195 = 0;
    protected static final int count157 = 0;
    protected static final int count45 = 0;
    public static final int count57 = 0;
    public int count108;
    public int count103;
    public int count141;
    public int count119;
    public boolean flag162 = true;
    private static Module[] modules4;

    public abstract void m945(Object var1, int var2, int var3);

    public boolean m851(double d, double d2, int n) {
        return false;
    }

    public boolean m510(double d, double d2, int n) {
        return false;
    }

    public boolean m855(double d, double d2, int n, double d3, double d4) {
        return false;
    }

    public boolean m121(int n, int n2, int n3) {
        return false;
    }

    public boolean m650(int n, int n2) {
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected boolean m120(double d, double d2, int n, int n2, int n3, int n4) {
        double d3 = d;
        double d4 = d2;
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        Object var18_13 = null;
        if (!(d3 >= (double)n5)) return false;
        if (!(d3 <= (double)(n5 + n7))) return false;
        if (!(d4 >= (double)n6)) return false;
        if (!(d4 <= (double)(n6 + n8))) return false;
        return true;
    }

    public static void setModuleArray3(Module[] moduleArray) {
        modules4 = moduleArray;
    }

    public static Module[] getModuleArray2() {
        return modules4;
    }

    static {
        long[] lArray = new long[5];
        ModuleState.setModuleArray3(null);
    }
}

