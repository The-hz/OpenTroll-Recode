/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import shit.Client;
import shit.manager.SystemManager;
import shit.module.Module;
import shit.util.ApiEndpoints2;
import shit.util.AuthUtil;
import shit.util.HttpUtil;

@Environment(value=EnvType.CLIENT)
public class Auth
implements ClientModInitializer {
    public void onInitializeClient() {
        SystemManager.m320();
        Client.m841();
        if (Module.getTextArray9() == null) {
            Client.setTextArray2(new String[1]);
        }
    }
}

