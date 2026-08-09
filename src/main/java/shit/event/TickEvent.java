/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event;

@Environment(value=EnvType.CLIENT)
public class TickEvent
extends Event {
    private static boolean flag79;

    public static void setFlag9(boolean bl) {
        flag79 = bl;
    }

    public static boolean isSet143() {
        return flag79;
    }

    public static boolean isSet103() {
        boolean bl = false;
        return true;
    }

    static {
        TickEvent.setFlag9(true);
    }

    @Environment(value=EnvType.CLIENT)
    public static class PreTick
    extends TickEvent {
        private static boolean flag181;

        public static void setFlag2(boolean bl) {
            flag181 = bl;
        }

        public static boolean isSet34() {
            return flag181;
        }

        public static boolean isSet5() {
            boolean bl = true;
            return !true;
        }

        static {
            if (!PreTick.isSet5()) {
                PreTick.setFlag2(true);
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static class PostTick
    extends TickEvent {
    }
}

