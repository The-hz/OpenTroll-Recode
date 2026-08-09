/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.api.Listener2;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class Manager4 {
    public static final Manager4 manager4 = new Manager4();
    private final List list18 = new ArrayList();
    private static String text3376;

    private Manager4() {
    }

    public synchronized Listener2 addListener(Object object) {
        Listener2 listener2 = (Listener2)object;
        this.list18.add(listener2);
        return listener2;
    }

    public synchronized void removeListener(Object object) {
        Listener2 listener2 = (Listener2)object;
        this.list18.remove(listener2);
    }

    public synchronized void closeAll() {
        block2: {
            Iterator iterator = List.copyOf(this.list18).iterator();
            String string = Manager4.getText68();
            while (iterator.hasNext()) {
                Listener2 listener2 = (Listener2)iterator.next();
                listener2.close();
                if (string != null) {
                    if (string != null) continue;
                    Module.setTextArray9(new String[5]);
                    break;
                }
                break block2;
            }
            this.list18.clear();
        }
    }

    static {
        Manager4.setText("PqcA1");
    }

    public static void setText(String string) {
        text3376 = string;
    }

    public static String getText68() {
        return text3376;
    }
}

