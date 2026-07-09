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

    default public boolean isSet4() {
        return false;
    }

    default public void setObj103(Object object) {
        RenderPass cfr_ignored_0 = (RenderPass)object;
    }

    public void m155();

    public void m523();
}

