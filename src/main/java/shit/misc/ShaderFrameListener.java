/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import com.mojang.blaze3d.systems.RenderPass;
import java.awt.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.api.RenderListener;
import shit.api.FrameListener;
import shit.manager.GlyphBufferManager;
import shit.manager.FrameListenerManager;
import shit.misc.FontBufferHolders;
import shit.render.ShaderRenderer;
import shit.render.TextureRenderer;
import shit.util.GpuPipelineFactory;

@Environment(value=EnvType.CLIENT)
public class ShaderFrameListener
implements FrameListener {
    private final RenderListener listener;
    private boolean flag33 = true;

    private ShaderFrameListener(long l) {
        this.listener = new ShaderRenderer(l);
    }

    private ShaderFrameListener() {
        this.listener = new ShaderRenderer();
    }

    public static ShaderFrameListener m444(long l) {
        long l2 = l;
        return (ShaderFrameListener)FrameListenerManager.manager4.addListener(new ShaderFrameListener(l2));
    }

    public static ShaderFrameListener getListener2Impl3() {
        return (ShaderFrameListener)FrameListenerManager.manager4.addListener(new ShaderFrameListener());
    }

    public void m1016(Object object, float f, float f2, float f3, Object object2, Object object3) {
        String string = (String)object;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        Color color = (Color)object2;
        GlyphBufferManager bufferUtilDataManager = (GlyphBufferManager)object3;
        this.m798();
        this.listener.render(string, f4, f5, f6, color, bufferUtilDataManager);
    }

    public void m321(Object object, float f, float f2, float f3, Object object2) {
        String string = (String)object;
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        Color color = (Color)object2;
        this.listener.render(string, f4, f5, f6, color, FontBufferHolders.bufferUtilDataManager8);
    }

    public float m567(float f) {
        float f2 = f;
        return this.listener.m542(f2, FontBufferHolders.bufferUtilDataManager8);
    }

    public float m540(float f, Object object) {
        float f2 = f;
        GlyphBufferManager bufferUtilDataManager = (GlyphBufferManager)object;
        return this.listener.m542(f2, bufferUtilDataManager);
    }

    public float m494(Object object, float f) {
        String string = (String)object;
        float f2 = f;
        return this.listener.m858(string, f2, FontBufferHolders.bufferUtilDataManager8);
    }

    public float m1019(Object object, float f, Object object2) {
        String string = (String)object;
        float f2 = f;
        GlyphBufferManager bufferUtilDataManager = (GlyphBufferManager)object2;
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
        GpuPipelineFactory.m486();
        this.listener.draw();
    }

    @Override
    public boolean prepareBuffers() {
        return this.listener.flag4();
    }

    @Override
    public void drawWithPass(Object object) {
        RenderPass renderPass = (RenderPass)object;
        this.listener.setObj46(renderPass);
    }

    @Override
    public void endFrame() {
        this.listener.tick();
    }

    @Override
    public void close() {
        block3: {
            block2: {
                String[] stringArray = TextureRenderer.getTextArray3();
                this.listener.m952();
                String[] stringArray2 = stringArray;
                ShaderFrameListener listener2Impl = this;
                if (stringArray2 == null) break block2;
                if (!listener2Impl.flag33) break block3;
                FrameListenerManager.manager4.removeListener(this);
                listener2Impl = this;
            }
            this.flag33 = false;
        }
    }

    private void m798() {
        block3: {
            block2: {
                String[] stringArray = TextureRenderer.getTextArray3();
                ShaderFrameListener listener2Impl = this;
                if (stringArray == null) break block2;
                if (listener2Impl.flag33) break block3;
                FrameListenerManager.manager4.addListener(this);
                listener2Impl = this;
            }
            this.flag33 = true;
        }
    }
}

