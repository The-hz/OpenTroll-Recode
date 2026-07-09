/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class Event {
    private boolean flag29;
    private static int count55;

    public boolean isSet85() {
        return this.flag29;
    }

    public void m209() {
        this.flag29 = true;
    }

    public static void setInt(int n) {
        count55 = n;
    }

    public static int getInt77() {
        return count55;
    }

    public static int getInt71() {
        int n = 27;
        if (27 == 0) {
            return 90;
        }
        return 0;
    }

    static {
        if (Event.getInt71() != 0) {
            Event.setInt(27);
        }
    }
}

