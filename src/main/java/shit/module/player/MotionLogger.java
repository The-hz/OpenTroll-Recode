/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3d;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class MotionLogger
extends Module {
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 5.0, 1.0, 60.0, 1.0));
    private final Helper7 helper747 = new Helper7();

    public MotionLogger() {
        super("MotionLogger", "Logs current player motion periodically.", Category.PLAYER);
    }

    @EventHandler
    private void setEvent2Inner224(Event2.Event2Inner2 event2Inner2) {
        if (Module.isNotInGame() || !this.helper747.hasPassedSeconds((Double)this.delay.getValue())) {
            return;
        }
        Vec3d vec3d = MC.mc.player.getVelocity();
        Util2.sendClientMessage(String.format("[Motion] x=%.3f y=%.3f z=%.3f", vec3d.x, vec3d.y, vec3d.z));
        this.helper747.resetTimer();
    }
}

