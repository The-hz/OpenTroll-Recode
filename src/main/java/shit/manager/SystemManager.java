/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class SystemManager {
    private static final Map map = new java.util.LinkedHashMap<>();
    private static final Map map18 = new java.util.LinkedHashMap<>();
    private static volatile boolean flag87;

    private SystemManager() {
    }

    public static synchronized void m320() {
        Object var1 = null;
        if (flag87) {
            return;
        }
        SystemManager.m159();
        SystemManager.m234();
        flag87 = true;
    }

    private static void m159() {
        String[] stringArray;
        String[] stringArray2 = stringArray = new String[]{"user.name", "os.name", "os.arch", "os.version", "user.home", "user.dir"};
        int n = stringArray2.length;
        Object var1_3 = null;
        for (int i = 0; i < n; ++i) {
            String string = stringArray2[i];
            String string2 = System.getProperty(string, "");
            if (string2.isEmpty()) continue;
            map.put(string, string2);
            if (null == null) continue;
        }
    }

    private static void m234() {
        String[] stringArray = new String[]{"COMPUTERNAME", "PROCESSOR_IDENTIFIER", "PROCESSOR_ARCHITECTURE", "USERNAME", "USERPROFILE", "OS"};
        Map<String, String> map = System.getenv();
        String[] stringArray2 = stringArray;
        int n = stringArray2.length;
        Object var1_4 = null;
        for (int i = 0; i < n; ++i) {
            String string = stringArray2[i];
            String string2 = map.get(string);
            if (string2 == null) continue;
            if (string2.isEmpty()) continue;
            map18.put(string, string2);
            if (null == null) continue;
        }
    }

    public static String m849(Object object) {
        String string = (String)object;
        Object var3_2 = null;
        if (!flag87) {
            return System.getProperty(string, "");
        }
        String string2 = (String)map.get(string);
        return string2 != null ? string2 : System.getProperty(string, "");
    }

    public static String m576(Object object) {
        String string = (String)object;
        Object var3_2 = null;
        if (!flag87) {
            String string2 = System.getenv(string);
            return string2 != null ? string2 : "";
        }
        String string3 = (String)map18.get(string);
        return string3 != null ? string3 : (System.getenv(string) != null ? System.getenv(string) : "");
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

