/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.player.AutoArmor;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ViewLock
extends Module {
    private final BooleanSetting yaw = (BooleanSetting)this.registerSetting(new BooleanSetting("Yaw", true));
    private final BooleanSetting pitch = (BooleanSetting)this.registerSetting(new BooleanSetting("Pitch", true));
    private final NumberSetting yawValue = (NumberSetting)this.registerSetting(new NumberSetting("YawValue", 0.0, -180.0, 180.0, 1.0));
    private final NumberSetting pitchValue = (NumberSetting)this.registerSetting(new NumberSetting("PitchValue", 0.0, -90.0, 90.0, 1.0));

    public ViewLock() {
        super("ViewLock", "Locks client view rotation to configured values.", Category.PLAYER);
    }

    @Override
    public void onEnable() {
        block3: {
            block2: {
                int n = AutoArmor.getSwitchFlag();
                if (n == 0) break block2;
                if (MC.mc.player == null) break block3;
                this.yawValue.setDouble(MC.mc.player.getYaw());
            }
            this.pitchValue.setDouble(MC.mc.player.getPitch());
        }
    }

    @EventHandler
    private void setEvent2Inner9(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (((Boolean)this.yaw.getValue()).booleanValue()) {
            MC.mc.player.setYaw(this.yawValue.getFloat());
        }
        if (((Boolean)this.pitch.getValue()).booleanValue()) {
            MC.mc.player.setPitch(this.pitchValue.getFloat());
        }
    }
}

