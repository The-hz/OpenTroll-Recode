/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Style;
import net.minecraft.util.Identifier;
import org.jspecify.annotations.Nullable;
import shit.manager.BufferUtilDataManager;
import shit.render.GlyphRenderer;
import shit.util.BufferUtil2;

@Environment(value=EnvType.CLIENT)
public final class FontUtil {
    private static final float value170 = 9.0f;
    private static final Identifier field60 = null;
    public static final float value118 = 0.0f;
    public static final float value174 = 3.0f;
    private static @Nullable BufferUtilDataManager bufferUtilDataManager6;

    private FontUtil() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static @Nullable BufferUtilDataManager getBufferUtilDataManager2() {
        boolean bl = GlyphRenderer.isSet134();
        BufferUtilDataManager bufferUtilDataManager = bufferUtilDataManager6;
        if (!bl) return bufferUtilDataManager;
        if (bufferUtilDataManager != null) {
            return bufferUtilDataManager6;
        }
        try {
            bufferUtilDataManager = bufferUtilDataManager6 = new BufferUtilDataManager(field60);
            return bufferUtilDataManager;
        }
        catch (RuntimeException runtimeException) {
            return null;
        }
    }

    public static float m789(Object object) {
        BufferUtilDataManager bufferUtilDataManager = (BufferUtilDataManager)object;
        return 9.0f / (float)bufferUtilDataManager.bufferUtil2.count69;
    }

    public static float m172(int n, Object object, @Nullable Object object2) {
        BufferUtilDataManager bufferUtilDataManager;
        Style style;
        int n2;
        block6: {
            block5: {
                n2 = n;
                style = (Style)object;
                bufferUtilDataManager = (BufferUtilDataManager)object2;
                boolean bl = false;
                if (Character.isWhitespace(n2)) {
                    return 3.0f + (style.isBold() ? 1.0f : 0.0f);
                }
                if (bufferUtilDataManager == null) {
                    return 0.0f;
                }
                if (n2 < 0) break block5;
                if (n2 <= 65535) break block6;
            }
            return 0.0f;
        }
        char c = (char)n2;
        return (float)bufferUtilDataManager.m360(c) * FontUtil.m789(bufferUtilDataManager) + 0.0f + (style.isBold() ? 1.0f : 0.0f);
    }

    private static /* synthetic */ boolean cfrlam$width$2(float[] fArray, BufferUtilDataManager bufferUtilDataManager, int n, Style style, int n2) {
        fArray[0] = fArray[0] + FontUtil.m172(n2, style, bufferUtilDataManager);
        return true;
    }

    private static /* synthetic */ boolean cfrlam$width$1(float[] fArray, BufferUtilDataManager bufferUtilDataManager, int n, Style style, int n2) {
        fArray[0] = fArray[0] + FontUtil.m172(n2, style, bufferUtilDataManager);
        return true;
    }

    private static /* synthetic */ boolean cfrlam$width$0(float[] fArray, BufferUtilDataManager bufferUtilDataManager, int n, Style style, int n2) {
        fArray[0] = fArray[0] + FontUtil.m172(n2, style, bufferUtilDataManager);
        return true;
    }

    /*
     * Handled unverifiable bytecode (illegal stack merge).
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    static {}
}

