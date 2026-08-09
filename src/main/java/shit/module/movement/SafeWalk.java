/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Box;
import shit.event.EventHandler;
import shit.event.InputTickEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class SafeWalk
extends Module {
    private final BooleanSetting checkFallDistance = (BooleanSetting)this.registerSetting(new BooleanSetting("CheckFallDistance", true));

    public SafeWalk() {
        super("SafeWalk", "Prevents walking off block edges.", Category.MOVEMENT);
    }

    @EventHandler
    private void setInputTickEvent4(InputTickEvent inputTickEvent) {
        if (Module.isNotInGame() || MC.mc.player.input == null || MC.mc.player.isSneaking() || MC.mc.player.isGliding()) {
            return;
        }
        PlayerInput playerInput = MC.mc.player.input.playerInput;
        if (!(playerInput.forward() || playerInput.backward() || playerInput.left() || playerInput.right())) {
            return;
        }
        if (((Boolean)this.checkFallDistance.getValue()).booleanValue() && this.isSet156()) {
            return;
        }
        MC.mc.player.input.playerInput = new PlayerInput(playerInput.forward(), playerInput.backward(), playerInput.left(), playerInput.right(), playerInput.jump(), true, playerInput.sprint());
    }

    private boolean isSet156() {
        double d = 0.0;
        Object var2_2 = null;
        double d2 = 0.0;
        if (MC.mc.options.forwardKey.isPressed()) {
            d2 = 0.15;
        }
        if (MC.mc.options.backKey.isPressed()) {
            d2 -= 0.15;
        }
        if (MC.mc.options.leftKey.isPressed()) {
            d = 0.15;
        }
        if (MC.mc.options.rightKey.isPressed()) {
            d -= 0.15;
        }
        float f = MC.mc.player.getYaw();
        double d3 = Math.sin(Math.toRadians(f));
        double d4 = Math.cos(Math.toRadians(f));
        double d5 = d * d4 - d2 * d3;
        double d6 = d2 * d4 + d * d3;
        Box box = MC.mc.player.getBoundingBox().offset(d5, -0.6, d6).contract(0.001);
        return !MC.mc.world.isSpaceEmpty((Entity)MC.mc.player, box);
    }
}

