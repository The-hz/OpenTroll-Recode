/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class ModuleToggleEvent
extends Event {
    private final Module module2;
    private final boolean flag65;

    public ModuleToggleEvent(Module module, boolean bl) {
        this.module2 = module;
        this.flag65 = bl;
    }

    public Module getModule() {
        return this.module2;
    }

    public boolean isSet168() {
        return this.flag65;
    }
}

