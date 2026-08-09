/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.manager.FrameListenerManager;
import shit.module.Module;
import shit.util.GpuPipelineFactory;

@Environment(value=EnvType.CLIENT)
public class AutoCloseableTracker {
    public static final AutoCloseableTracker manager3 = new AutoCloseableTracker();
    private final List list31 = new ArrayList();

    private AutoCloseableTracker() {
    }

    public synchronized GpuPipelineFactory.AutoCloseableImpl m401(Object object) {
        GpuPipelineFactory.AutoCloseableImpl autoCloseableImpl = (GpuPipelineFactory.AutoCloseableImpl)object;
        this.list31.add(autoCloseableImpl);
        return autoCloseableImpl;
    }

    public synchronized void setObj93(Object object) {
        GpuPipelineFactory.AutoCloseableImpl autoCloseableImpl = (GpuPipelineFactory.AutoCloseableImpl)object;
        this.list31.remove(autoCloseableImpl);
    }

    public synchronized void m726() {
        block3: {
            block2: {
                Iterator iterator = List.copyOf(this.list31).iterator();
                String string = FrameListenerManager.getText68();
                while (iterator.hasNext()) {
                    GpuPipelineFactory.AutoCloseableImpl autoCloseableImpl = (GpuPipelineFactory.AutoCloseableImpl)iterator.next();
                    autoCloseableImpl.close();
                    if (string != null) {
                        if (string != null) continue;
                    }
                    break block2;
                }
                this.list31.clear();
            }
            if (Module.getTextArray9() != null) break block3;
            FrameListenerManager.setText("gD33wb");
        }
    }
}

