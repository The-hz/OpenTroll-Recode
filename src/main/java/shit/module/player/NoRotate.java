/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class NoRotate
extends Module {
    public static NoRotate INSTANCE;

    public NoRotate() {
        super("NoRotate", "Blocks server-side rotation changes when supported by mixins.", Category.PLAYER);
        INSTANCE = this;
    }
}

