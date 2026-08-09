/*
 * Decompiled with CFR 0.152.
 */
package shit.render;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.nio.ByteBuffer;
import java.util.OptionalInt;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.MappableRingBuffer;
import net.minecraft.client.gl.RenderPipelines;
import org.joml.Vector3f;
import org.joml.Vector3fc;

@Environment(value=EnvType.CLIENT)
public final class Lightmap {
    public static MappableRingBuffer field33 = null;
    public static GpuTextureView gpuTextureView3 = null;

    public static void writeLightmap(float f, float f2, float f3) {
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        Object var7_6 = null;
        if (field33 == null || gpuTextureView3 == null) {
            return;
        }
        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
        try (GpuBuffer.MappedView mappedView = commandEncoder.mapBuffer(field33.getBlocking(), false, true);){
            Std140Builder std140Builder = Std140Builder.intoBuffer((ByteBuffer)mappedView.data());
            std140Builder.putFloat(1.0f);
            std140Builder.putFloat(0.0f);
            std140Builder.putFloat(0.0f);
            std140Builder.putFloat(0.0f);
            std140Builder.putFloat(0.0f);
            std140Builder.putFloat(0.0f);
            std140Builder.putFloat(1.0f);
            std140Builder.putVec3((Vector3fc)new Vector3f(0.0f, 0.0f, 0.0f));
            std140Builder.putVec3((Vector3fc)new Vector3f(f4, f5, f6));
        }
        RenderPass renderPass = commandEncoder.createRenderPass(() -> "lightmap_write", gpuTextureView3, OptionalInt.empty());
        try {
            renderPass.setPipeline(RenderPipelines.BILT_SCREEN_LIGHTMAP);
            RenderSystem.bindDefaultUniforms((RenderPass)renderPass);
            renderPass.setUniform("LightmapInfo", field33.getBlocking());
            renderPass.draw(0, 3);
        }
        finally {
            if (renderPass != null) {
                renderPass.close();
            }
        }
        field33.rotate();
    }
}

