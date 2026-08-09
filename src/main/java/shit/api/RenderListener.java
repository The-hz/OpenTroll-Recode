/*
 * Decompiled with CFR 0.152.
 */
package shit.api;

import com.mojang.blaze3d.systems.RenderPass;
import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.manager.GlyphBufferManager;

@Environment(value=EnvType.CLIENT)
public interface RenderListener {
    public void render(Object var1, float var2, float var3, float var4, Object var5, Object var6);

    default public void m227(Object object, float f, float f2, float f3, Object object2, Object object3, Object object4) {
        String string = (String)object;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        Color color = (Color)object2;
        Color cfr_ignored_0 = (Color)object3;
        GlyphBufferManager bufferUtilDataManager = (GlyphBufferManager)object4;
        this.render(string, f4, f5, f6, color, bufferUtilDataManager);
    }

    public void draw();

    default public boolean flag4() {
        return false;
    }

    default public void setObj46(Object object) {
        RenderPass cfr_ignored_0 = (RenderPass)object;
    }

    public void tick();

    public void m952();

    public float m542(float var1, Object var2);

    public float m858(Object var1, float var2, Object var3);

    default public void m635(int n, int n2, int n3, int n4) {
    }

    default public void m181() {
    }
}

