/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderSetup;

@Environment(value=EnvType.CLIENT)
public final class RenderLayers {
    private static int count234;

    private RenderLayers() {
    }

    public static RenderLayer m79(String string, RenderSetup renderSetup) {
        return RenderLayer.of((String)string, (RenderSetup)renderSetup);
    }

    public static void setInt7(int n) {
        count234 = n;
    }

    public static int getInt57() {
        return count234;
    }

    public static int getInt78() {
        int n = 2;
        if (2 == 0) {
            return 117;
        }
        return 0;
    }

    static {
        if (RenderLayers.getInt78() == 0) {
            RenderLayers.setInt7(2);
        }
    }
}

