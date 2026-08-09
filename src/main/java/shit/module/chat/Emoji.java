/*
 * Decompiled with CFR 0.152.
 */
package shit.module.chat;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.module.chat.ChatTimestamp;

@Environment(value=EnvType.CLIENT)
public class Emoji
extends Module {
    public static Emoji INSTANCE;
    private static final Map map12 = new java.util.LinkedHashMap<>();

    public Emoji() {
        super("Emoji", "Replaces simple emoji aliases in outgoing chat.", Category.CHAT);
        INSTANCE = this;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String m528(Object object) {
        String string;
        String string2;
        block3: {
            String string3;
            string2 = string3 = (String)object;
            Iterator iterator = map12.entrySet().iterator();
            int[] nArray = ChatTimestamp.getIntArray2();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                string2 = string2.replace((CharSequence)entry.getKey(), (CharSequence)entry.getValue());
                if (nArray != null) {
                    if (nArray != null) continue;
                }
                break block3;
            }
            string = string2;
            if (nArray == null) return string;
            if (string.length() > 256) {
                string = string2.substring(0, 256);
                return string;
            }
        }
        string = string2;
        return string;
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

