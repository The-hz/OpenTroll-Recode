/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(value=EnvType.CLIENT)
@Mixin(value={WorldRenderer.class})
public interface LevelRendererAccessor {
    @Accessor(value="entityOutlineFramebuffer")
    public Framebuffer getEntityOutlineTarget();

    @Accessor(value="blockBreakingInfos")
    public Int2ObjectMap getDestroyingBlocks();
}

