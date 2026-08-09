/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class CustomVertexFormats {
    private static final int count161 = 0;
    private static final int count172 = 0;
    private static final int count199 = 0;
    public static final VertexFormatElement vertexFormatElement = null;
    public static final VertexFormatElement vertexFormatElement3 = null;
    public static final VertexFormatElement vertexFormatElement2 = null;
    public static final VertexFormat vertexFormat4 = null;
    public static final VertexFormat vertexFormat2 = null;
    public static final VertexFormat vertexFormat3 = null;

    private static int getInt73() {
        return CustomVertexFormats.m498(0);
    }

    private static int m498(int n) {
        int n2 = n;
        for (int i = Math.max(0, n2); i < 32; ++i) {
            if (VertexFormatElement.byId((int)i) != null) continue;
            return i;
        }
        throw new IllegalStateException("VertexFormatElement count limit exceeded");
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

