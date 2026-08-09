/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;
import shit.module.hud.AbstractHudModule;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Biome
extends AbstractHudModule {
    public Biome() {
        super("Biome", "Shows current biome.", 6, 186);
    }

    @Override
    protected List lines() {
        ClientWorld clientWorld;
        block4: {
            block5: {
                block3: {
                    MinecraftClient minecraftClient;
                    boolean bl;
                    block2: {
                        bl = AbstractHudModule.isSet32();
                        minecraftClient = MC.mc;
                        if (bl) break block2;
                        if (minecraftClient.player == null) break block3;
                        minecraftClient = MC.mc;
                    }
                    clientWorld = minecraftClient.world;
                    if (bl) break block4;
                    if (clientWorld != null) break block5;
                }
                return List.of("Biome N/A");
            }
            clientWorld = MC.mc.world;
        }
        Optional optional = clientWorld.getBiome(BlockPos.ofFloored((Position)MC.mc.player.getEntityPos())).getKey();
        return List.of("Biome " + optional.map(registryKey -> ((net.minecraft.registry.RegistryKey)registryKey).getValue().getPath()).orElse("unknown"));
    }
}

