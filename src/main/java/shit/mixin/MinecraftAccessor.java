/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(value=EnvType.CLIENT)
@Mixin(value={MinecraftClient.class})
public interface MinecraftAccessor {
    @Accessor(value="currentFps")
    public static int trollhack$getFps() {
        return 0;
    }

    @Accessor(value="itemUseCooldown")
    public int getInt82();

    @Accessor(value="itemUseCooldown")
    public void setInt15(int var1);

    @Accessor(value="currentScreen")
    public void trollhack$setScreen(Screen var1);
}

