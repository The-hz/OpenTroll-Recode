/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import com.mojang.blaze3d.systems.RenderPass;
import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.api.Listener;
import shit.api.Listener2;
import shit.manager.BufferUtilDataManager;
import shit.manager.Manager4;
import shit.misc.Helper5;
import shit.render.ShaderRenderer;
import shit.render.TextureRenderer;
import shit.util.RenderUtil4;

@Environment(value=EnvType.CLIENT)
public class Listener2Impl
implements Listener2 {
    private final Listener listener;
    private boolean flag33 = true;

    private Listener2Impl(long l) {
        this.listener = new ShaderRenderer(l);
    }

    private Listener2Impl() {
        this.listener = new ShaderRenderer();
    }

    public static Listener2Impl m444(long l) {
        long l2 = l;
        return (Listener2Impl)Manager4.manager4.m276(new Listener2Impl(l2));
    }

    public static Listener2Impl getListener2Impl3() {
        return (Listener2Impl)Manager4.manager4.m276(new Listener2Impl());
    }

    public void m1016(Object object, float f, float f2, float f3, Object object2, Object object3) {
        String string = (String)object;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        Color color = (Color)object2;
        BufferUtilDataManager bufferUtilDataManager = (BufferUtilDataManager)object3;
        this.m798();
        this.listener.render(string, f4, f5, f6, color, bufferUtilDataManager);
    }

    public void m321(Object object, float f, float f2, float f3, Object object2) {
        String string = (String)object;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        Color color = (Color)object2;
        this.listener.render(string, f4, f5, f6, color, Helper5.bufferUtilDataManager8);
    }

    public float m567(float f) {
        float f2 = f;
        return this.listener.m542(f2, Helper5.bufferUtilDataManager8);
    }

    public float m540(float f, Object object) {
        float f2 = f;
        BufferUtilDataManager bufferUtilDataManager = (BufferUtilDataManager)object;
        return this.listener.m542(f2, bufferUtilDataManager);
    }

    public float m494(Object object, float f) {
        String string = (String)object;
        float f2 = f;
        return this.listener.m858(string, f2, Helper5.bufferUtilDataManager8);
    }

    public float m1019(Object object, float f, Object object2) {
        String string = (String)object;
        float f2 = f;
        BufferUtilDataManager bufferUtilDataManager = (BufferUtilDataManager)object2;
        return this.listener.m858(string, f2, bufferUtilDataManager);
    }

    public void m96(int n, int n2, int n3, int n4) {
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        this.listener.m635(n5, n6, n7, n8);
    }

    public void m937() {
        this.listener.m181();
    }

    @Override
    public void draw2() {
        RenderUtil4.m486();
        this.listener.draw();
    }

    @Override
    public boolean isSet4() {
        return this.listener.flag4();
    }

    @Override
    public void setObj103(Object object) {
        RenderPass renderPass = (RenderPass)object;
        this.listener.setObj46(renderPass);
    }

    @Override
    public void endFrame() {
        this.listener.tick();
    }

    @Override
    public void m523() {
        block3: {
            block2: {
                String[] stringArray = TextureRenderer.getTextArray3();
                this.listener.m952();
                String[] stringArray2 = stringArray;
                Listener2Impl listener2Impl = this;
                if (stringArray2 == null) break block2;
                if (!listener2Impl.flag33) break block3;
                Manager4.manager4.setObj100(this);
                listener2Impl = this;
            }
            this.flag33 = false;
        }
    }

    private void m798() {
        block3: {
            block2: {
                String[] stringArray = TextureRenderer.getTextArray3();
                Listener2Impl listener2Impl = this;
                if (stringArray == null) break block2;
                if (listener2Impl.flag33) break block3;
                Manager4.manager4.m276(this);
                listener2Impl = this;
            }
            this.flag33 = true;
        }
    }
}

