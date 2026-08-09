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
import shit.manager.FrameListenerManager;

@Environment(value=EnvType.CLIENT)
public class TextureManager {
    public static final TextureManager manager2 = new TextureManager();
    public final Map map20 = new HashMap();

    private TextureManager() {
    }

    public void m304() {
        block2: {
            Iterator iterator = this.map20.values().iterator();
            String string = FrameListenerManager.getText68();
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

