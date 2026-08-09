/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.hud.AbstractHudModule;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ServerBrand
extends AbstractHudModule {
    public ServerBrand() {
        super("ServerBrand", "Shows the server brand.", 6, 126);
    }

    @Override
    protected List lines() {
        boolean bl = true;
        if (MC.mc.getServer() != null) {
            return List.of("Brand integrated");
        }
        if (MC.mc.getCurrentServerEntry() != null) {
            return List.of("Brand " + MC.mc.getCurrentServerEntry().address);
        }
        return List.of("Brand N/A");
    }
}

