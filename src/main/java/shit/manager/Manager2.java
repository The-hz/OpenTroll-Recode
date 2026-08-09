/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.gui.Texture;
import shit.manager.Manager4;

@Environment(value=EnvType.CLIENT)
public class Manager2 {
    public static final Manager2 manager2 = new Manager2();
    public final Map map20 = new HashMap();

    private Manager2() {
    }

    public void m304() {
        block2: {
            Iterator iterator = this.map20.values().iterator();
            String string = Manager4.getText68();
            while (iterator.hasNext()) {
                Texture texture = (Texture)((Object)iterator.next());
                texture.close();
                if (string != null) {
                    if (string != null) continue;
                }
                break block2;
            }
            this.map20.clear();
        }
    }
}

