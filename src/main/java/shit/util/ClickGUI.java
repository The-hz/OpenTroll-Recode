/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.Client;
import shit.module.Module;
import shit.util.FontUtil2;

@Environment(value=EnvType.CLIENT)
public final class ClickGUI {
    private static final String a = "ClickGUI";

    private ClickGUI() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getInt80() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        if (clickGUI == null) return -15394784;
        return (Integer) clickGUI.primaryColor.getObj();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getInt49() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        if (clickGUI == null) return 1713382701;
        return (Integer) clickGUI.backgroundColor.getObj();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getInt38() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        if (clickGUI == null) return -1439005464;
        return (Integer) clickGUI.accentColor.getObj();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getInt27() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        if (clickGUI == null) return -1;
        return (Integer) clickGUI.textColor.getObj();
    }

    public static int getInt10() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        int n = clickGUI == null ? 51 : Math.max(0, Math.min(255, clickGUI.hoverAlpha.getInt50()));
        int n2 = ClickGUI.getInt27();
        return n2 & 0xFFFFFF | n << 24;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet43() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        if (clickGUI == null) return true;
        return (Boolean) clickGUI.windowOutline.getObj();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet94() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        if (clickGUI == null) return true;
        return (Boolean) clickGUI.titleBar.getObj();
    }

    public static int getInt53() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        return clickGUI == null ? 4 : Math.max(0, Math.min(10, clickGUI.xMargin.getInt50()));
    }

    public static int getInt86() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        return clickGUI == null ? 1 : Math.max(0, Math.min(10, clickGUI.yMargin.getInt50()));
    }

    public static int getInt69() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        if (clickGUI == null) {
            return 0;
        }
        int n = Math.max(0, Math.min(255, (int)Math.round((Double)clickGUI.darkness.getObj() * 255.0)));
        return n << 24;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet109() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        if (clickGUI == null) return true;
        return (Boolean) clickGUI.blur.getObj();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet22() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        if (clickGUI == null) return false;
        return (Boolean) clickGUI.blurDebug.getObj();
    }

    public static int getInt6() {
        shit.module.client.ClickGUI clickGUI = ClickGUI.getObj23();
        return clickGUI == null ? 8 : Math.max(1, Math.min(20, clickGUI.blurRadius.getInt50()));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static shit.module.client.ClickGUI getObj23() {
        shit.module.client.ClickGUI clickGUI;
        Module module = Client.moduleManager.m979(a);
        boolean bl = FontUtil2.isSet101();
        Module module2 = module;
        if (!bl) {
            if (!(module2 instanceof shit.module.client.ClickGUI)) return null;
            module2 = module;
        }
        shit.module.client.ClickGUI clickGUI2 = clickGUI = (shit.module.client.ClickGUI)module2;
        return clickGUI2;
    }
}

