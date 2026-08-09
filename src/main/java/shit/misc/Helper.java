/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.manager.WorldRenderDispatcher;
import shit.manager.WorldRenderDispatcher2;
import shit.render.ScreenCopyRenderer;
import shit.util.GpuPipelineFactory;
import shit.util.Timer;

@Environment(value=EnvType.CLIENT)
public class Helper {
    private boolean flag167;

    public void init() {
        Object var2_1 = null;
        if (this.flag167) {
            return;
        }
        this.flag167 = true;
        WorldRenderDispatcher2.m605();
        WorldRenderDispatcher.m958();
    }

    public void m818() {
        GpuPipelineFactory.m981();
        Timer.m537();
        GpuPipelineFactory.m353();
    }

    public void m27() {
        Timer.m816();
        ScreenCopyRenderer.m638();
    }
}

