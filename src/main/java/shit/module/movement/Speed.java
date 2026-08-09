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
import shit.event.PacketEvent;
import shit.util.MathUtil;
import shit.module.Category;
import shit.module.Module;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Speed
extends Module {
    public static Speed INSTANCE;
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.STRAFE));
    private final NumberSetting speed = (NumberSetting)this.registerSetting(new NumberSetting("Speed", 1.0, 0.1, 5.0, 0.1));
    private final NumberSetting boost = (NumberSetting)this.registerSetting(new NumberSetting("Boost", 0.0, 0.0, 2.0, 0.05));
    private boolean flag88;

    public Speed() {
        super("Speed", "Increases movement speed via strafe / on-ground / vanilla modes.", Category.MOVEMENT);
        INSTANCE = this;
    }

    @Override
    public String getInfo() {
        return ((Mode)((Object)this.mode.getValue())).name();
    }

    @Override
    public void onEnable() {
        this.flag88 = false;
    }

    @EventHandler
    private void setEvent2Inner15(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        double d = ((Double)this.speed.getValue()).doubleValue();
        Mode mode = (Mode)((Object)this.mode.getValue());
        switch (mode) {
            case STRAFE: {
                if (!MC.mc.player.isOnGround()) break;
                if (!MathUtil.isSet7()) break;
                double[] dArray = MathUtil.m246(d);
                MC.mc.player.setVelocity(dArray[0], MC.mc.player.getVelocity().y, dArray[1]);
                break;
            }
            case ON_GROUND: {
                if (!MC.mc.player.isOnGround()) break;
                if (!MathUtil.isSet7()) break;
                double[] dArray = MathUtil.m246(d);
                MC.mc.player.setVelocity(dArray[0], 0.0, dArray[1]);
                if (MC.mc.options.jumpKey.isPressed()) {
                    MC.mc.player.jump();
                }
                break;
            }
            case VANILLA: {
                MC.mc.player.setSprinting(true);
                if (!MathUtil.isSet7()) break;
                double[] dArray = MathUtil.m246(d + ((Double)this.boost.getValue()).doubleValue());
                MC.mc.player.setVelocity(dArray[0], MC.mc.player.getVelocity().y, dArray[1]);
                break;
            }
        }
    }

    @EventHandler
    private void setPacketEventInner25(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (Module.isNotInGame()) {
            return;
        }
        if (this.mode.getValue() != Mode.ON_GROUND) {
            return;
        }
        if (!(packetEventInner2.getPacket() instanceof PlayerMoveC2SPacket)) {
            return;
        }
        if (!MC.mc.player.isOnGround()) {
            return;
        }
        if (this.flag88) {
            packetEventInner2.cancel();
        }
        this.flag88 = !this.flag88;
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
