/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class ShadersUtil {
    public float value204;
    public float value192 = 1.0f;
    public float value183 = 0.5f;
    public float value134 = 80.0f;
    public float value131 = 10.0f;
    public float value135 = 0.6f;
    public float value128 = 5.0f;
    public float value156 = 5.0f;
    public float value111 = 1.0f;
    public float value176;
    public float value148;
    public float value115;
    public float value133;
    public float value116;
    public float value138;
    public int count83;
    private static boolean flag173;

    public ShadersUtil() {
        boolean bl = false;
        this.count83 = 0;
        Module.setTextArray9(new String[4]);
    }

    public static void setFlag5(boolean bl) {
        flag173 = bl;
    }

    public static boolean isSet12() {
        return flag173;
    }

    public static boolean isSet178() {
        boolean bl = false;
        return true;
    }

    static {
        ShadersUtil.setFlag5(true);
    }
}

