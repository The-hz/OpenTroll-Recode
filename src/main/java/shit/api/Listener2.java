/*
 * Decompiled with CFR 0.152.
 */
package shit.api;

import com.mojang.blaze3d.systems.RenderPass;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public interface Listener2 {
    public void draw2();

    default public boolean prepareBuffers() {
        return false;
    }

    default public void drawWithPass(Object object) {
        RenderPass cfr_ignored_0 = (RenderPass)object;
    }

    public void endFrame();

    public void close();
}

