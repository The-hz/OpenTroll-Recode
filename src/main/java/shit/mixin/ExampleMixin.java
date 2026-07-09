/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

@Environment(value=EnvType.CLIENT)
@Mixin(value={MinecraftServer.class})
public class ExampleMixin {
}

