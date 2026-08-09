/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.lang.reflect.Method;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.hud.AbstractHudModule;

@Environment(value=EnvType.CLIENT)
public class BaritoneProcess
extends AbstractHudModule {
    public BaritoneProcess() {
        super("BaritoneProcess", "Shows the current Baritone process when Baritone is loaded.", 6, 258);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected List lines() {
        String string = this.getText24();
        boolean bl = AbstractHudModule.isSet32();
        String string2 = string;
        if (bl) return List.of("Baritone " + string2);
        if (string2 != null) {
            string2 = string;
            if (bl) return List.of("Baritone " + string2);
            if (!string2.isBlank()) {
                string2 = string;
                return List.of("Baritone " + string2);
            }
        }
        string2 = "N/A";
        return List.of("Baritone " + string2);
    }

    private String getText24() {
        boolean bl = true;
        try {
            Class<?> clazz = Class.forName("baritone.api.BaritoneAPI");
            Object object = clazz.getMethod("getProvider", new Class[0]).invoke(null, new Object[0]);
            Object object2 = object.getClass().getMethod("getPrimaryBaritone", new Class[0]).invoke(object, new Object[0]);
            Object object3 = object2.getClass().getMethod("getPathingControlManager", new Class[0]).invoke(object2, new Object[0]);
            Method method = object3.getClass().getMethod("mostRecentInControl", new Class[0]);
            Object object4 = method.invoke(object3, new Object[0]);
            Object object5 = object4.getClass().getMethod("orElse", Object.class).invoke(object4, new Object[]{null});
            if (object5 == null) {
                return null;
            }
            String string = object5.getClass().getSimpleName();
            return string.endsWith("Process") ? string.substring(0, string.length() - "Process".length()) : string;
        }
        catch (LinkageError | ReflectiveOperationException throwable) {
            return null;
        }
    }
}

