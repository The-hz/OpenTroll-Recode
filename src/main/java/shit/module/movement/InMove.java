/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class InMove
extends Module {
    public static InMove INSTANCE;
    private final BooleanSetting allowSneak = (BooleanSetting)this.registerSetting(new BooleanSetting("AllowSneak", false));

    public InMove() {
        super("InMove", "Allows moving while in any container GUI.", Category.MOVEMENT);
        INSTANCE = this;
    }

    @EventHandler
    private void setEvent2Inner48(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (MC.mc.currentScreen != null && !(MC.mc.currentScreen instanceof ChatScreen)) {
            for (KeyBinding keyBinding : new KeyBinding[]{MC.mc.options.backKey, MC.mc.options.leftKey, MC.mc.options.rightKey}) {
                keyBinding.setPressed(this.m920(keyBinding));
            }
            MC.mc.options.jumpKey.setPressed(this.m920(MC.mc.options.jumpKey));
            MC.mc.options.forwardKey.setPressed(this.m920(MC.mc.options.forwardKey));
            MC.mc.options.sprintKey.setPressed(this.m920(MC.mc.options.sprintKey));
            if (((Boolean)this.allowSneak.getValue()).booleanValue()) {
                MC.mc.options.sneakKey.setPressed(this.m920(MC.mc.options.sneakKey));
            }
        }
    }

    private boolean m920(Object object) {
        KeyBinding keyBinding = (KeyBinding)object;
        Object var4_3 = null;
        if (MC.mc.getWindow() == null) {
            return false;
        }
        InputUtil.Key key = keyBinding.getDefaultKey();
        int n = key.getCode();
        if (n == -1 || key == InputUtil.UNKNOWN_KEY) {
            return false;
        }
        return InputUtil.isKeyPressed((Window)MC.mc.getWindow(), (int)n);
    }
}

