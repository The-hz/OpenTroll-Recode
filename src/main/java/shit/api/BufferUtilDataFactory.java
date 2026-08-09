/*
 * Decompiled with CFR 0.152.
 */
package shit.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.data.GlyphBufferUploadData;

@Environment(value=EnvType.CLIENT)
public interface BufferUtilDataFactory {
    public void setInt18(int var1);

    public GlyphBufferUploadData m705(int var1);
}

