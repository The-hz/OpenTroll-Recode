/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.GameMode;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.setting.EnumSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class FakeGameMode
extends Module {
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.CREATIVE));
    private GameMode field20;

    public FakeGameMode() {
        super("FakeGameMode", "Changes the client-side displayed game mode.", Category.MISC);
    }

    @Override
    public void onEnable() {
        block0: {
            if (MC.mc.interactionManager == null) break block0;
            this.field20 = MC.mc.interactionManager.getCurrentGameMode();
        }
    }

    @Override
    public void onDisable() {
        block4: {
            block6: {
                FakeGameMode fakeGameMode;
                block5: {
                    String string = IRC.getText7();
                    if (string == null) break block4;
                    if (MC.mc.interactionManager == null) break block5;
                    fakeGameMode = this;
                    if (string == null) break block6;
                    if (fakeGameMode.field20 != null) {
                        MC.mc.interactionManager.setGameMode(this.field20);
                    }
                }
                fakeGameMode = this;
            }
            this.field20 = null;
        }
    }

    @EventHandler
    private void setEvent2Inner50(Event2.Event2Inner event2Inner) {
        if (MC.mc.interactionManager != null) {
            MC.mc.interactionManager.setGameMode(((Mode)((Object)this.mode.getValue())).field22);
        }
    }

    @Override
    public String getInfo() {
        return ((Mode)((Object)this.mode.getValue())).name();
    }

    @Environment(value=EnvType.CLIENT)
    static enum Mode {
      SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR;

      private Mode() {}


        private final GameMode field22 = null;

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */

        private static Mode[] getModeArray13() {
            return new Mode[]{SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR};
        }
    
   }
}

