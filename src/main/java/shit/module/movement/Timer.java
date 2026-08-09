/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import java.text.DecimalFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.module.player.AutoArmor;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.type.EaseMode;

@Environment(value=EnvType.CLIENT)
public class Timer
extends Module {
    public static Timer INSTANCE;
    public final NumberSetting speed = (NumberSetting)this.registerSetting(new NumberSetting("Speed", 1.0, 0.1, 5.0, 0.01));
    private final DecimalFormat decimalFormat2 = new DecimalFormat("0.0");
    private final BooleanSetting tickShift = (BooleanSetting)this.registerSetting(new BooleanSetting("TickShift", true));
    private final NumberSetting shiftTimer = (NumberSetting)this.registerSetting(new NumberSetting("ShiftTimer", 2.0, 1.0, 10.0, 0.1, 0.1, () -> (Boolean)this.tickShift.getValue(), null, "", false));
    private final NumberSetting charge = (NumberSetting)this.registerSetting(new NumberSetting("Charge", 2000.0, 1.0, 10000.0, 50.0, 50.0, () -> (Boolean)this.tickShift.getValue(), null, "", false));
    private final NumberSetting minCharge = (NumberSetting)this.registerSetting(new NumberSetting("MinCharge", 500.0, 1.0, 10000.0, 50.0, 50.0, () -> (Boolean)this.tickShift.getValue(), null, "", false));
    private final BooleanSetting smooth = (BooleanSetting)this.registerSetting(new BooleanSetting("Smooth", true, () -> (Boolean)this.tickShift.getValue(), null, "", false));
    private final EnumSetting ease = (EnumSetting)this.registerSetting(new EnumSetting("Ease", EaseMode.CubicInOut, () -> {
        boolean bl = false;
        if ((Boolean)this.smooth.getValue() == false) return false;
        if ((Boolean)this.tickShift.getValue() == false) return false;
        return true;
    }, null, "", false));
    private final BooleanSetting reset = (BooleanSetting)this.registerSetting(new BooleanSetting("Reset", true, () -> (Boolean)this.tickShift.getValue(), null, "", false));
    private final BooleanSetting indicator = (BooleanSetting)this.registerSetting(new BooleanSetting("Indicator", true, () -> (Boolean)this.tickShift.getValue(), null, "", false));
    private final ColorSetting completed = (ColorSetting)this.registerSetting(new ColorSetting("Completed", -16711936, true, () -> {
        int n = AutoArmor.getSwitchFlag();
        boolean bl = (Boolean)this.indicator.getValue();
        if (n != 0) {
            if (!bl) return false;
            bl = (Boolean)this.tickShift.getValue();
        }
        if (n == 0) return bl;
        if (!bl) return false;
        return true;
    }, null, "", false));
    private final ColorSetting charging = (ColorSetting)this.registerSetting(new ColorSetting("Charging", -65536, true, () -> {
        int n = AutoArmor.getSwitchFlag();
        boolean bl = (Boolean)this.indicator.getValue();
        if (n != 0) {
            if (!bl) return false;
            bl = (Boolean)this.tickShift.getValue();
        }
        if (n == 0) return bl;
        if (!bl) return false;
        return true;
    }, null, "", false));
    private final NumberSetting yOffset = (NumberSetting)this.registerSetting(new NumberSetting("YOffset", 0.0, -200.0, 200.0, 1.0, 1.0, () -> {
        boolean bl = false;
        if ((Boolean)this.indicator.getValue() == false) return false;
        if ((Boolean)this.tickShift.getValue() == false) return false;
        return true;
    }, null, "", false));
    private final Helper7 helper726 = new Helper7();
    private final Helper7 helper712 = new Helper7();
    private final shit.misc.Timer timer3 = new shit.misc.Timer(500L);
    private long time42 = 0L;
    private boolean flag46 = false;

    public Timer() {
        super("Timer", "Speeds up the game clock.", Category.MOVEMENT);
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Client.timerScale.m502();
    }

    @Override
    public String getInfo() {
        boolean bl = false;
        if (((Boolean)this.tickShift.getValue()).booleanValue()) {
            double d = this.flag46 ? (double)Math.max(this.time42 - this.helper712.getElapsed(), 0L) : (double)this.helper726.getElapsed();
            double d2 = (Double)this.charge.getValue();
            double d3 = Math.min(d / d2 * 100.0, 100.0);
            return this.decimalFormat2.format(d3) + "%";
        }
        return String.valueOf(this.speed.getValue()) + "x";
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Client.timerScale.m502();
    }

    @EventHandler
    private void setEvent2Inner39(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        Client.timerScale.m64();
    }

    @EventHandler
    public void setPacketEventInner7(PacketEvent.PacketEventInner packetEventInner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket) {
            this.time42 = 0L;
        }
    }
}

