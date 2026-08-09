/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import java.util.Iterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import shit.Client;
import shit.api.Listener3;
import shit.gui.HudEditorScreen;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public final class RenderUtil {
    private RenderUtil() {
    }

    public static void setObj31(Object object) {
        DrawContext drawContext = (DrawContext)object;
        if (MinecraftClient.getInstance().currentScreen instanceof HudEditorScreen) {
            return;
        }
        for (Module module : Client.moduleManager.getModules()) {
            if (module instanceof Listener3 && module.isEnabled()) {
                try {
                    ((Listener3) module).renderHud(drawContext, false);
                } catch (UnsupportedOperationException stub) {
                } catch (RuntimeException e) {
                }
            }
        }
    }
}

