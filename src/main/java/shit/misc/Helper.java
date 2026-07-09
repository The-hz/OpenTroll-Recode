/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.manager.RenderLevelEventManager;
import shit.manager.RenderLevelEventManager2;
import shit.render.Passthrough;
import shit.util.RenderUtil4;
import shit.util.Timer;

@Environment(value=EnvType.CLIENT)
public class Helper {
    private boolean flag167;

    public void m25() {
        Object var2_1 = null;
        if (this.flag167) {
            return;
        }
        this.flag167 = true;
        RenderLevelEventManager2.m605();
        RenderLevelEventManager.m958();
    }

    public void m818() {
        RenderUtil4.m981();
        Timer.m537();
        RenderUtil4.m353();
    }

    public void m27() {
        Timer.m816();
        Passthrough.m638();
    }
}

