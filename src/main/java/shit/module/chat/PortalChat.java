/*
 * Decompiled with CFR 0.152.
 */
package shit.module.chat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class PortalChat
extends Module {
    public static PortalChat INSTANCE;

    public PortalChat() {
        super("PortalChat", "Allows you to open GUIs while in portals.", Category.CHAT);
        INSTANCE = this;
    }
}

