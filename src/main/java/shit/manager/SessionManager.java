/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.util.ApiEndpoints2;
import shit.util.ApiEndpoints3;
import shit.util.SystemUtil;

@Environment(value=EnvType.CLIENT)
public final class SessionManager {
    private static final SessionManager sessionManager = new SessionManager();
    private static final AtomicBoolean atomicBoolean = null;
    private static final DateTimeFormatter dateTimeFormatter4 = null;
    private int count207 = 0;
    private double value140 = Double.NaN;
    private double value173 = Double.NaN;
    private double value132 = Double.NaN;
    private String text2445 = "";
    private String text627 = "";

    private SessionManager() {
    }

    public static void m744() {
        block0: {
            if (!atomicBoolean.compareAndSet(false, true)) break block0;
            Client.eventBus.subscribe(sessionManager);
        }
    }

    @EventHandler
    public void setEvent2Inner23(Event2.Event2Inner2 event2Inner2) {
        throw new UnsupportedOperationException("deobf: reconstruct from bytecode");
    }

    private boolean m490(Object object) {
        MinecraftClient minecraftClient;
        block5: {
            block4: {
                minecraftClient = (MinecraftClient)object;
                Object var4_3 = null;
                if (Double.isNaN(this.value140)) {
                    return true;
                }
                String string = SessionManager.m245(minecraftClient);
                String string2 = SessionManager.m1024(minecraftClient);
                if (!string.equals(this.text2445)) break block4;
                if (string2.equals(this.text627)) break block5;
            }
            return true;
        }
        double d = minecraftClient.player.getX() - this.value140;
        double d2 = minecraftClient.player.getY() - this.value173;
        double d3 = minecraftClient.player.getZ() - this.value132;
        return d * d + d2 * d2 + d3 * d3 >= 64.0;
    }

    private static String m245(Object object) {
        ServerInfo serverInfo;
        block5: {
            block4: {
                MinecraftClient minecraftClient = (MinecraftClient)object;
                Object var3_2 = null;
                if (minecraftClient.isIntegratedServerRunning()) {
                    return "SinglePlayer";
                }
                serverInfo = minecraftClient.getCurrentServerEntry();
                if (serverInfo == null) break block4;
                if (serverInfo.address == null) break block4;
                if (!serverInfo.address.isBlank()) break block5;
            }
            return "Unknown";
        }
        return serverInfo.address.trim();
    }

    private static String m1024(Object object) {
        try {
            MinecraftClient minecraftClient = (MinecraftClient)object;
            return minecraftClient.world.getRegistryKey().getValue().toString().toLowerCase(Locale.ROOT);
        }
        catch (Throwable throwable) {
            return "unknown";
        }
    }

    private static String m620(Object object) {
        String string;
        String string2 = string = (String)object;
        Object var3_3 = null;
        int n = -1;
        switch (string2.hashCode()) {
            case -1526768685: {
                if (!string2.equals("minecraft:the_nether")) break;
                n = 0;
                if (null == null) break;
            }
            case 1272296422: {
                if (!string2.equals("the_nether")) break;
                n = 1;
                if (null == null) break;
            }
            case -1048926120: {
                if (!string2.equals("nether")) break;
                n = 2;
                if (null == null) break;
            }
            case 1731133248: {
                if (!string2.equals("minecraft:the_end")) break;
                n = 3;
                if (null == null) break;
            }
            case -1350117363: {
                if (!string2.equals("the_end")) break;
                n = 4;
                if (null == null) break;
            }
            case 100571: {
                if (!string2.equals("end")) break;
                n = 5;
                if (null == null) break;
            }
            case 1104210353: {
                if (!string2.equals("minecraft:overworld")) break;
                n = 6;
                if (null == null) break;
            }
            case -745159874: {
                if (!string2.equals("overworld")) break;
                n = 7;
            }
        }
        return switch (n) {
            case 0, 1, 2 -> "\u5730\u72f1";
            case 3, 4, 5 -> "\u672b\u5730";
            case 6, 7 -> "\u4e3b\u4e16\u754c";
            default -> "\u672a\u77e5";
        };
    }

    private static void send(Object object, Object object2, Object object3, Object object4, double d, double d2, double d3) {
        throw new UnsupportedOperationException("deobf: reconstruct from bytecode");
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

