/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.textures.GpuTexture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.memory.ObjectAllocator;
import shit.misc.ShaderProgram;
import shit.render.Blur2;
import shit.util.ShadersUtil;

@Environment(value=EnvType.CLIENT)
public class Passthrough2 {
    private ShaderProgram shaderProgram2;
    private ShaderProgram shaderProgram3;
    private Blur2 blur22;
    private static final String[] a = new String[0];
    private static final String[] b = new String[0];
    private static final long[] c = new long[0];
    private static final Integer[] d = new Integer[0];

    public void m487(Object object, Object object2, Object object3, boolean bl) {
        Framebuffer framebuffer = (Framebuffer)object;
        ObjectAllocator cfr_ignored_0 = (ObjectAllocator)object2;
        ShadersUtil shadersUtil = (ShadersUtil)object3;
        this.m427(framebuffer, shadersUtil);
    }

    public void m1012() {
        block3: {
            Blur2 blur2;
            block2: {
                boolean bl = ShadersUtil.isSet178();
                blur2 = this.blur22;
                if (bl) break block2;
                if (blur2 == null) break block3;
                blur2 = this.blur22;
            }
            blur2.m485();
            this.blur22 = null;
        }
    }

    private void m427(Object object, Object object2) {
        block3: {
            GpuTexture gpuTexture;
            block2: {
                Framebuffer framebuffer = (Framebuffer)object;
                ShadersUtil shadersUtil = (ShadersUtil)object2;
                GpuTexture gpuTexture2 = framebuffer.getColorAttachment();
                boolean bl = false;
                gpuTexture = gpuTexture2;
                if (!false) break block2;
                if (!(gpuTexture instanceof GlTexture)) break block3;
                gpuTexture = gpuTexture2;
            }
            GlTexture glTexture = (GlTexture)gpuTexture;
        }
    }

    private void m162() {
        block11: {
            block10: {
                Passthrough2 passthrough2;
                block9: {
                    ShaderProgram shaderProgram;
                    block8: {
                        boolean bl = false;
                        shaderProgram = this.shaderProgram2;
                        if (!false) break block8;
                        if (shaderProgram == null) {
                            this.shaderProgram2 = new ShaderProgram("passthrough.vert", "entity_outline_fast.frag");
                        }
                        passthrough2 = this;
                        if (!false) break block9;
                        shaderProgram = passthrough2.shaderProgram3;
                    }
                    if (shaderProgram == null) {
                        this.shaderProgram3 = new ShaderProgram("passthrough.vert", "passthrough.frag");
                    }
                    passthrough2 = this;
                }
                if (!false) break block10;
                if (passthrough2.blur22 != null) break block11;
                passthrough2 = this;
            }
            this.blur22 = new Blur2(1.0);
        }
    }

    /*
     * Exception decompiling
     */
    static {}

    private static int a(int n, long l) {
        int n2 = n ^ (int)(l & 0x7FFFL) ^ 0x6111;
        if (d[n2] == null) {
            Passthrough2.d[n2] = (int)(c[n2] ^ l);
        }
        return d[n2];
    }
}

