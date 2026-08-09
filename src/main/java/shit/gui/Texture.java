/*
 * Decompiled with CFR 0.152.
 */
package shit.gui;

import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.texture.AbstractTexture;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class Texture
extends AbstractTexture {
    private final boolean flag25;
    private final boolean flag58;

    public Texture(GpuTexture gpuTexture, GpuTextureView gpuTextureView, GpuSampler gpuSampler, boolean bl, boolean bl2) {
        Object var6_6 = null;
        this.glTexture = gpuTexture;
        this.glTextureView = gpuTextureView;
        this.sampler = gpuSampler;
        this.flag25 = bl;
        this.flag58 = bl2;
        Module.setTextArray9(new String[5]);
    }

    public Texture(GpuTexture gpuTexture, GpuTextureView gpuTextureView, GpuSampler gpuSampler) {
        this(gpuTexture, gpuTextureView, gpuSampler, true, true);
    }

    public void close() {
        block6: {
            Texture texture;
            block5: {
                boolean bl;
                block4: {
                    Object var1_1 = null;
                    bl = this.flag58;
                    if (null == null) break block4;
                    if (bl) {
                        this.sampler.close();
                    }
                    texture = this;
                    if (null == null) break block5;
                    bl = texture.flag25;
                }
                if (!bl) break block6;
                this.glTextureView.close();
                texture = this;
            }
            texture.glTexture.close();
        }
    }
}

