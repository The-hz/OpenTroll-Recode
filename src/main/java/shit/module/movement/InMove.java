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
    private final BooleanSetting allowSneak = (BooleanSetting)this.m28(new BooleanSetting("AllowSneak", false));

    public InMove() {
        super("InMove", "Allows moving while in any container GUI.", Category.MOVEMENT);
        INSTANCE = this;
    }

    @EventHandler
    private void setEvent2Inner48(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (MC.client3.currentScreen != null && !(MC.client3.currentScreen instanceof ChatScreen)) {
            for (KeyBinding keyBinding : new KeyBinding[]{MC.client3.options.backKey, MC.client3.options.leftKey, MC.client3.options.rightKey}) {
                keyBinding.setPressed(this.m920(keyBinding));
            }
            MC.client3.options.jumpKey.setPressed(this.m920(MC.client3.options.jumpKey));
            MC.client3.options.forwardKey.setPressed(this.m920(MC.client3.options.forwardKey));
            MC.client3.options.sprintKey.setPressed(this.m920(MC.client3.options.sprintKey));
            if (((Boolean)this.allowSneak.getObj()).booleanValue()) {
                MC.client3.options.sneakKey.setPressed(this.m920(MC.client3.options.sneakKey));
            }
        }
    }

    private boolean m920(Object object) {
        KeyBinding keyBinding = (KeyBinding)object;
        Object var4_3 = null;
        if (MC.client3.getWindow() == null) {
            return false;
        }
        InputUtil.Key key = keyBinding.getDefaultKey();
        int n = key.getCode();
        if (n == -1 || key == InputUtil.UNKNOWN_KEY) {
            return false;
        }
        return InputUtil.isKeyPressed((Window)MC.client3.getWindow(), (int)n);
    }
}

