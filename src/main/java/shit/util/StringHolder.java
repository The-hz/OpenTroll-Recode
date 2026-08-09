/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class StringHolder {
    private static String text804;

    public static void setText10(String string) {
        text804 = string;
    }

    public static String getText4() {
        return text804;
    }
}

