/*
 * Decompiled with CFR 0.152.
 */
package shit.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.GpuSampler;

@Environment(value=EnvType.CLIENT)
public interface SamplerCallback {
    public GpuSampler getObj16();
}

