/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.TravelHeadEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.MathUtil;

@Environment(value=EnvType.CLIENT)
public class Flight
extends Module {
    private final NumberSetting speed = (NumberSetting)this.registerSetting(new NumberSetting("Speed", 1.0, 0.0, 10.0, 0.1));
    private final NumberSetting upSpeed = (NumberSetting)this.registerSetting(new NumberSetting("UpSpeed", 1.0, 0.1, 10.0, 0.05));
    private final NumberSetting downSpeed = (NumberSetting)this.registerSetting(new NumberSetting("DownSpeed", 1.0, 0.1, 10.0, 0.05));
    private final NumberSetting glideSpeed = (NumberSetting)this.registerSetting(new NumberSetting("GlideSpeed", 0.05, 0.0, 0.3, 0.01));
    private final BooleanSetting antiKick = (BooleanSetting)this.registerSetting(new BooleanSetting("AntiKick", true));
    private int count152;

    public Flight() {
        super("Flight", "Allows free movement through the air.", Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        this.count152 = 0;
    }

    @EventHandler
    private void setTravelHeadEvent3(TravelHeadEvent travelHeadEvent) {
        if (Module.isNotInGame() || MC.mc.player.isGliding() || MC.mc.player.hasVehicle()) {
            return;
        }
        double d = 0.0;
        double d2 = 0.0;
        if (MathUtil.isMoving()) {
            double[] dArray = MathUtil.getMotionVector((Double)this.speed.getValue());
            d = dArray[0];
            d2 = dArray[1];
        }
        double d3 = -((Double)this.glideSpeed.getValue()).doubleValue();
        if (MC.mc.options.jumpKey.isPressed() && !MC.mc.options.sneakKey.isPressed()) {
            d3 = (Double)this.upSpeed.getValue();
        } else if (MC.mc.options.sneakKey.isPressed() && !MC.mc.options.jumpKey.isPressed()) {
            d3 = -((Double)this.downSpeed.getValue()).doubleValue();
        }
        travelHeadEvent.setDouble5(d);
        travelHeadEvent.setDouble3(d3);
        travelHeadEvent.setDouble6(d2);
        travelHeadEvent.cancel();
    }

    @EventHandler
    private void setEvent2Inner6(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame() || !((Boolean)this.antiKick.getValue()).booleanValue() || MC.mc.player.isOnGround()) {
            return;
        }
        if (++this.count152 < 50) {
            return;
        }
        this.count152 = 0;
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.PositionAndOnGround(MC.mc.player.getX(), MC.mc.player.getY() - 0.04, MC.mc.player.getZ(), false, MC.mc.player.horizontalCollision));
    }
}

