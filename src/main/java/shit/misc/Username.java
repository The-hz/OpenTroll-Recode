/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import shit.module.hud.AbstractHudModule;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Username
extends AbstractHudModule {
    public Username() {
        super("Username", "Shows your player name.", 6, 78);
    }

    @Override
    protected List lines() {
        boolean bl = AbstractHudModule.isSet32();
        ClientPlayerEntity clientPlayerEntity = MC.client3.player;
        if (!bl) {
            if (clientPlayerEntity == null) {
                return List.of("Username Player");
            }
            clientPlayerEntity = MC.client3.player;
        }
        return List.of("Username " + clientPlayerEntity.getName().getString());
    }
}

