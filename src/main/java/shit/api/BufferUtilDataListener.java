/*
 * Decompiled with CFR 0.152.
 */
package shit.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.data.BufferUtilData;

@Environment(value=EnvType.CLIENT)
public interface BufferUtilDataListener {
    public void setInt18(int var1);

    public BufferUtilData m705(int var1);
}

