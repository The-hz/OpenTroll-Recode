/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicInteger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.GpuSampler;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;
import org.lwjgl.system.MemoryUtil;
import shit.data.BufferData;
import shit.gui.Texture;
import shit.module.Module;
import shit.render.ShaderRenderer;
import shit.util.BufferUtil2;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class BufferUtil {
    private static final AtomicInteger atomicInteger2 = new AtomicInteger();
    private final Texture texture2;
    private final Identifier field50 = BufferUtil2.m52("ttf_atlas/" + atomicInteger2.getAndIncrement());
    private int count191 = 0;
    private int count87 = 0;
    private int count109 = 0;

    public BufferUtil(int n) {
        GpuTexture gpuTexture = RenderSystem.getDevice().createTexture(() -> "Lumin-TtfGlyphAtlas", 5, TextureFormat.RED8, 1024, 1024, 1, 1);
        GpuTextureView gpuTextureView = RenderSystem.getDevice().createTextureView(gpuTexture);
        GpuSampler gpuSampler = RenderSystem.getDevice().createSampler(AddressMode.CLAMP_TO_EDGE, AddressMode.CLAMP_TO_EDGE, FilterMode.LINEAR, FilterMode.LINEAR, 1, OptionalDouble.empty());
        this.texture2 = new Texture(gpuTexture, gpuTextureView, gpuSampler);
        BufferUtil.setObj72(gpuTexture);
        MC.mc.getTextureManager().registerTexture(this.field50, (AbstractTexture)this.texture2);
    }

    private static void setObj72(Object object) {
        GpuTexture gpuTexture = (GpuTexture)object;
        ByteBuffer byteBuffer = MemoryUtil.memAlloc((int)0x100000);
        try {
            MemoryUtil.memSet((long)MemoryUtil.memAddress((ByteBuffer)byteBuffer), (int)255, (long)0x100000L);
            RenderSystem.getDevice().createCommandEncoder().writeToTexture(gpuTexture, byteBuffer, NativeImage.Format.LUMINANCE, 0, 0, 0, 0, 1024, 1024);
        }
        finally {
            MemoryUtil.memFree((Buffer)byteBuffer);
        }
    }

    public Vec4f m359(Object object) {
        BufferData bufferData = (BufferData)object;
        Module[] moduleArray = ShaderRenderer.getModuleArray();
        BufferData bufferData2 = bufferData;
        if (moduleArray != null) {
            if (bufferData2.byteBuffer() == null) {
                return null;
            }
            bufferData2 = bufferData;
        }
        int n = bufferData2.getInt32() + 4;
        int n2 = bufferData.getInt42() + 4;
        int n3 = this.count191 + n;
        int n4 = 1024;
        if (moduleArray != null) {
            if (n3 >= n4) {
                this.count191 = 0;
                this.count87 += this.count109;
                this.count109 = 0;
            }
            n3 = this.count87 + n2;
            n4 = 1024;
        }
        if (moduleArray != null) {
            if (n3 >= n4) {
                return null;
            }
            n3 = this.count191;
            n4 = 2;
        }
        int n5 = n3 + n4;
        int n6 = this.count87 + 2;
        RenderSystem.getDevice().createCommandEncoder().writeToTexture(this.texture2.getGlTexture(), bufferData.byteBuffer(), NativeImage.Format.LUMINANCE, 0, 0, n5, n6, bufferData.getInt32(), bufferData.getInt42());
        Vec4f vec4f = new Vec4f((float)n5 / 1024.0f, (float)n6 / 1024.0f, (float)(n5 + bufferData.getInt32()) / 1024.0f, (float)(n6 + bufferData.getInt42()) / 1024.0f);
        this.count191 += n;
        this.count109 = Math.max(this.count109, n2);
        return vec4f;
    }

    public Texture getTexture() {
        return this.texture2;
    }

    public Identifier getObj18() {
        return this.field50;
    }

    public void m453() {
        MC.mc.getTextureManager().destroyTexture(this.field50);
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    public static final class Vec4f  {
        private final float value45;
        private final float value46;
        private final float value47;
        private final float value48;

        public Vec4f(float f, float f2, float f3, float f4) {
            this.value45 = f;
            this.value46 = f2;
            this.value47 = f3;
            this.value48 = f4;
        }

        public float getFloat23() {
            return this.value45;
        }

        public float value46() {
            return this.value46;
        }

        public float value47() {
            return this.value47;
        }

        public float getFloat30() {
            return this.value48;
        }
    }
}

