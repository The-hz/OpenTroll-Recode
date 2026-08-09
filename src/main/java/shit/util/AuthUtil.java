/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import shit.Client;
import shit.module.Module;
import shit.util.ApiEndpoints3;

@Environment(value=EnvType.CLIENT)
public final class AuthUtil {
    private static volatile boolean flag137 = false;
    private static volatile String text2966 = null;

    private AuthUtil() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m272(Object object) {
        Screen screen = (Screen)object;
        Object var3_2 = null;
        if (flag137) {
            return true;
        }
        if (screen instanceof shit.gui.Screen) {
            return true;
        }
        if (ApiEndpoints3.isSet46()) return true;
        if (!ApiEndpoints3.isSet68()) return false;
        return true;
    }

    public static void setObj59(Object object) {
        String string = (String)object;
        text2966 = AuthUtil.m445(string);
        ApiEndpoints3.m217();
        AuthUtil.m1035();
    }

    public static void setObj109(Object object) {
        MinecraftClient minecraftClient = (MinecraftClient)object;
        Object var3_2 = null;
        if (minecraftClient == null) {
            return;
        }
        if (ApiEndpoints3.isSet46()) {
            Client.configManager.setObj(minecraftClient);
            return;
        }
        if (ApiEndpoints3.isSet68()) {
            return;
        }
        AuthUtil.m1035();
        AuthUtil.m606(minecraftClient, text2966);
    }

    public static void m606(Object object, Object object2) {
        MinecraftClient minecraftClient = (MinecraftClient)object;
        String string = (String)object2;
        Object var5_4 = null;
        if (minecraftClient == null || flag137) {
            return;
        }
        if (string != null) {
            if (!string.isBlank()) {
                text2966 = string;
            }
        }
        if (minecraftClient.currentScreen instanceof shit.gui.Screen) {
            return;
        }
        flag137 = true;
        try {
            minecraftClient.setScreen((Screen)new shit.gui.Screen(text2966));
        }
        finally {
            flag137 = false;
        }
    }

    private static String m445(Object object) {
        String string;
        block3: {
            block2: {
                string = (String)object;
                Object var3_2 = null;
                if (string == null) break block2;
                if (!string.isBlank()) break block3;
            }
            return "Authentication required.";
        }
        return string;
    }

    private static void m1035() {
        Object var1 = null;
        try {
            if (Client.moduleManager == null) {
                return;
            }
            for (Module module : Client.moduleManager.getModules()) {
                if (module != null) {
                    if (module.isEnabled()) {
                        module.setEnabled(false);
                    }
                }
                if (null == null) continue;
                break;
            }
        }
        catch (Throwable throwable) {}
    }
}

