/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.DisconnectEvent;
import shit.event.EventHandler;
import shit.event.InputTickEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoWalk
extends Module {
    private final EnumSetting direction = (EnumSetting)this.registerSetting(new EnumSetting("Direction", DirectionMode.Forward));
    private final BooleanSetting disconnect = (BooleanSetting)this.registerSetting(new BooleanSetting("Disconnect", true));

    public AutoWalk() {
        super("AutoWalk", "Automatically holds a movement direction.", Category.MOVEMENT);
    }

    @EventHandler
    private void setInputTickEvent3(InputTickEvent inputTickEvent) {
        if (Module.isNotInGame() || MC.mc.player.input == null) {
            return;
        }
        switch (((DirectionMode)((Object)this.direction.getValue())).ordinal()) {
            case 0: {
                MC.mc.options.forwardKey.setPressed(true);
                break;
            }
            case 1: {
                MC.mc.options.backKey.setPressed(true);
            }
        }
    }

    @EventHandler
    private void setDisconnectEvent(DisconnectEvent disconnectEvent) {
        if (((Boolean)this.disconnect.getValue()).booleanValue()) {
            this.setEnabled(false);
        }
    }

    @Override
    public void onDisable() {
        Object var2_1 = null;
        if (MC.mc.options != null) {
            MC.mc.options.forwardKey.setPressed(false);
            MC.mc.options.backKey.setPressed(false);
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum DirectionMode {
      Forward, Backward;

      private DirectionMode() {}



        private static DirectionMode[] getDirectionModeArray() {
            return new DirectionMode[]{Forward, Backward};
        }
    
   }
}

