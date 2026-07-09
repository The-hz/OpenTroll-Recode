/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.client.ClientSetting;

@Environment(value=EnvType.CLIENT)
public final class Outline {
    private static final Map map17 = new java.util.LinkedHashMap<>();
    private static final Map map43 = new java.util.LinkedHashMap<>();

    private Outline() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isSet130() {
        ClientSetting clientSetting = ClientSetting.INSTANCE;
        Object var1_1 = null;
        if (clientSetting == null) return false;
        if ((Boolean)clientSetting.chinese.getObj() == false) return false;
        return true;
    }

    public static String m14(Object object) {
        String string;
        block3: {
            block2: {
                string = (String)object;
                Object var3_2 = null;
                if (!Outline.isSet130()) break block2;
                if (string == null) break block2;
                if (!string.isBlank()) break block3;
            }
            return string;
        }
        String string2 = (String)map17.get(string);
        return string2 != null ? string2 : Outline.m988(string);
    }

    public static String m168(Object object) {
        Category category = (Category)((Object)object);
        return Outline.m14(category.name());
    }

    public static String m130(Object object) {
        Enum enum_ = (Enum)object;
        return Outline.m14(enum_.name());
    }

    private static void m135(Object object, Object object2) {
        String string = (String)object;
        String string2 = (String)object2;
        map17.put(string, string2);
    }

    private static void m926(Object object, Object object2) {
        String string = (String)object;
        String string2 = (String)object2;
        map43.put(string.toLowerCase(Locale.ROOT), string2);
    }

    private static String m988(Object object) {
        String string = (String)object;
        String[] stringArray = string.replace('-', '_').replace(' ', '_').split("_");
        StringBuilder stringBuilder = new StringBuilder();
        String[] stringArray2 = stringArray;
        Object var3_5 = null;
        for (String string2 : stringArray2) {
            if (string2.isBlank()) continue;
            for (String string3 : Outline.m740(string2)) {
                if (string3.isBlank()) continue;
                String string4 = (String)map43.get(string3.toLowerCase(Locale.ROOT));
                stringBuilder.append(string4 != null ? string4 : string3);
                if (null == null) continue;
            }
            if (null == null) continue;
            break;
        }
        return stringBuilder.isEmpty() ? string : stringBuilder.toString();
    }

    private static String[] m740(Object object) {
        String string = (String)object;
        return string.replaceAll("([a-z])([A-Z])", "$1 $2").replaceAll("([A-Z]+)([A-Z][a-z])", "$1 $2").split(" ");
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

