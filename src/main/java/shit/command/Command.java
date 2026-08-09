/*
 * Decompiled with CFR 0.152.
 */
package shit.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public abstract class Command {
    private final String text2367;
    private final String text1315;
    private final String text487;
    private static int count237;

    protected Command(String string, String string2, String string3) {
        this.text2367 = string;
        this.text1315 = string2;
        this.text487 = string3;
        boolean bl = false;
        Module.setTextArray9(new String[4]);
    }

    public abstract void run(Object var1);

    public String[] getSuggestions(int n, Object object) {
        String[] cfr_ignored_0 = (String[])object;
        return new String[0];
    }

    public String getText48() {
        return this.text2367;
    }

    public String getText8() {
        return this.text1315;
    }

    public String getText31() {
        return this.text487;
    }

    public static void setInt17(int n) {
        count237 = n;
    }

    public static int getInt85() {
        return count237;
    }

    public static int getInt64() {
        boolean bl = false;
        return 40;
    }

    static {
        Command.setInt17(114);
    }
}

