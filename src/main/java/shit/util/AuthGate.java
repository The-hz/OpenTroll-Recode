/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.util.ApiEndpoints3;

@Environment(value=EnvType.CLIENT)
public final class AuthGate {
    private static final String a = null;

    private AuthGate() {
    }

    public static boolean isSet51() {
        return ApiEndpoints3.isSet46();
    }

    private static MatchException a(MatchException matchException) {
        return matchException;
    }
}

