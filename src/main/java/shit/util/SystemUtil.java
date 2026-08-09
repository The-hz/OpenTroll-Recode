/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import shit.manager.SystemInfoCollector;

@Environment(value=EnvType.CLIENT)
public final class SystemUtil {
    private SystemUtil() {
    }

    static String getText23() {
        return SystemUtil.m431(SystemUtil.getText59());
    }

    static byte[] getIntArray5() {
        String string = SystemUtil.getText59() + "|" + SystemUtil.getText36();
        return SystemUtil.m211(string);
    }

    static String getText3() {
        return SystemUtil.m431(SystemUtil.getText59() + "|" + SystemUtil.getText36());
    }

    private static String getText59() {
        return String.join((CharSequence)"|", SystemInfoCollector.m849("user.name"), SystemInfoCollector.m849("os.name"), SystemInfoCollector.m849("os.arch"), SystemInfoCollector.m576("COMPUTERNAME"), SystemInfoCollector.m576("PROCESSOR_IDENTIFIER"), SystemInfoCollector.m576("PROCESSOR_ARCHITECTURE"));
    }

    private static String getText36() {
        try {
            return FabricLoader.getInstance().getGameDir().toAbsolutePath().toString();
        }
        catch (Exception exception) {
            return "";
        }
    }

    private static byte[] m211(Object object) {
        try {
            String string = (String)object;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception exception) {
            return new byte[32];
        }
    }

    private static String m431(Object object) {
        String string = (String)object;
        return HexFormat.of().formatHex(SystemUtil.m211(string));
    }
}

