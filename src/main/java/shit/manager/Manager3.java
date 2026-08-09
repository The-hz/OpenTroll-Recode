/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.manager.Manager4;
import shit.module.Module;
import shit.util.RenderUtil4;

@Environment(value=EnvType.CLIENT)
public class Manager3 {
    public static final Manager3 manager3 = new Manager3();
    private final List list31 = new ArrayList();

    private Manager3() {
    }

    public synchronized RenderUtil4.AutoCloseableImpl m401(Object object) {
        RenderUtil4.AutoCloseableImpl autoCloseableImpl = (RenderUtil4.AutoCloseableImpl)object;
        this.list31.add(autoCloseableImpl);
        return autoCloseableImpl;
    }

    public synchronized void setObj93(Object object) {
        RenderUtil4.AutoCloseableImpl autoCloseableImpl = (RenderUtil4.AutoCloseableImpl)object;
        this.list31.remove(autoCloseableImpl);
    }

    public synchronized void m726() {
        block3: {
            block2: {
                Iterator iterator = List.copyOf(this.list31).iterator();
                String string = Manager4.getText68();
                while (iterator.hasNext()) {
                    RenderUtil4.AutoCloseableImpl autoCloseableImpl = (RenderUtil4.AutoCloseableImpl)iterator.next();
                    autoCloseableImpl.close();
                    if (string != null) {
                        if (string != null) continue;
                    }
                    break block2;
                }
                this.list31.clear();
            }
            if (Module.getTextArray9() != null) break block3;
            Manager4.setText("gD33wb");
        }
    }
}

