/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class Speed
extends Module {
    private final EnumSetting mode = (EnumSetting)this.m28(new EnumSetting("Mode", Mode.STRAFE));
    private final NumberSetting speed = (NumberSetting)this.m28(new NumberSetting("Speed", 1.0, 0.1, 5.0, 0.1));

    public Speed() {
        super("Speed", "Movement speed module shell.", Category.MOVEMENT);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      STRAFE, ON_GROUND, VANILLA;

      private Mode() {}



        private static Mode[] getModeArray2() {
            return new Mode[]{STRAFE, ON_GROUND, VANILLA};
        }
    
   }
}

