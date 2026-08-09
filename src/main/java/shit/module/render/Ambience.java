/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.event.Render2DEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.VanillaTextHelper;

@Environment(value=EnvType.CLIENT)
public class Ambience
extends Module {
    public static Ambience INSTANCE;
    public final ColorSetting filter = (ColorSetting)this.registerSetting(new ColorSetting("Filter", 0x14FFFFFF));
    public final BooleanSetting filterDraw = (BooleanSetting)this.registerSetting(new BooleanSetting("FilterDraw", false));
    public final BooleanSetting customTime = (BooleanSetting)this.registerSetting(new BooleanSetting("CustomTime", false));
    public final NumberSetting time = (NumberSetting)this.registerSetting(new NumberSetting("Time", 0.0, 0.0, 24000.0, 1.0));
    public final BooleanSetting customWeather = (BooleanSetting)this.registerSetting(new BooleanSetting("CustomWeather", false));
    public final EnumSetting weatherType = (EnumSetting)this.registerSetting(new EnumSetting("WeatherType", EMode.CLEAR));
    public final NumberSetting intensity = (NumberSetting)this.registerSetting(new NumberSetting("Intensity", 1.0, 0.0, 1.0, 0.05, 0.05, () -> {
        Object var1_1 = null;
        return (Boolean)this.customWeather.getValue() != false && this.weatherType.getValue() != EMode.CLEAR;
    }, null, "", false));
    public final ColorSetting fogColor = (ColorSetting)this.registerSetting(new ColorSetting("FogColor", -4138753));
    public final BooleanSetting fogColorDraw = (BooleanSetting)this.registerSetting(new BooleanSetting("FogColorDraw", false));
    public final ColorSetting skyColor = (ColorSetting)this.registerSetting(new ColorSetting("SkyColor", -8933889));
    public final BooleanSetting skyColorDraw = (BooleanSetting)this.registerSetting(new BooleanSetting("SkyColorDraw", false));
    public final ColorSetting cloudColor = (ColorSetting)this.registerSetting(new ColorSetting("CloudColor", -1));
    public final BooleanSetting cloudColorDraw = (BooleanSetting)this.registerSetting(new BooleanSetting("CloudColorDraw", false));
    public final ColorSetting worldColor = (ColorSetting)this.registerSetting(new ColorSetting("WorldColor", -1));
    public final BooleanSetting worldColorDraw = (BooleanSetting)this.registerSetting(new BooleanSetting("WorldColorDraw", false));
    public final BooleanSetting fogDistance = (BooleanSetting)this.registerSetting(new BooleanSetting("FogDistance", false));
    public final NumberSetting fogStart = (NumberSetting)this.registerSetting(new NumberSetting("FogStart", 50.0, 0.0, 1000.0, 1.0));
    public final NumberSetting fogEnd = (NumberSetting)this.registerSetting(new NumberSetting("FogEnd", 100.0, 0.0, 1000.0, 1.0));
    public final BooleanSetting fullBright = (BooleanSetting)this.registerSetting(new BooleanSetting("FullBright", false));
    public final BooleanSetting customLuminance = (BooleanSetting)this.registerSetting(new BooleanSetting("CustomLuminance", false, () -> true, (bl, bl2) -> {
        Ambience.m289();
        return bl2;
    }, "", false));
    public final NumberSetting luminance = (NumberSetting)this.registerSetting(new NumberSetting("Luminance", 15.0, 0.0, 15.0, 1.0));
    private long time20;
    private float value151;
    private float value196;

    public Ambience() {
        super("Ambience", "Custom time, weather, fog and sky colors.", Category.RENDER);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        Object var2_1 = null;
        if (Module.isNotInGame()) {
            return;
        }
        this.time20 = MC.mc.world.getLevelProperties().getTimeOfDay();
        if (((Boolean)this.customTime.getValue()).booleanValue()) {
            this.m488();
        }
        this.value151 = MC.mc.world.getRainGradient(1.0f);
        this.value196 = MC.mc.world.getThunderGradient(1.0f);
        if (((Boolean)this.customWeather.getValue()).booleanValue()) {
            this.m12();
        }
    }

    @Override
    public void onDisable() {
        Object var2_1 = null;
        if (Module.isNotInGame()) {
            return;
        }
        MC.mc.world.getLevelProperties().setTimeOfDay(this.time20);
        MC.mc.world.setRainGradient(this.value151);
        MC.mc.world.setThunderGradient(this.value196);
        MC.mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }

    @EventHandler
    private void setEvent2Inner49(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (((Boolean)this.customTime.getValue()).booleanValue()) {
            this.m488();
        }
        if (((Boolean)this.customWeather.getValue()).booleanValue()) {
            this.m12();
        }
        if (((Boolean)this.fullBright.getValue()).booleanValue()) {
            MC.mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false, false));
        }
    }

    @EventHandler
    private void setPacketEventInner20(PacketEvent.PacketEventInner packetEventInner) {
        Packet packet;
        if (Module.isNotInGame()) {
            return;
        }
        if (((Boolean)this.customTime.getValue()).booleanValue() && (packet = packetEventInner.getPacket()) instanceof WorldTimeUpdateS2CPacket) {
            WorldTimeUpdateS2CPacket worldTimeUpdateS2CPacket = (WorldTimeUpdateS2CPacket)packet;
            this.time20 = worldTimeUpdateS2CPacket.timeOfDay();
            packetEventInner.cancel();
        }
    }

    @EventHandler
    private void setObj44(Render2DEvent render2DEvent) {
        if (!((Boolean)this.filterDraw.getValue()).booleanValue()) {
            return;
        }
        VanillaTextHelper.m526(render2DEvent.getDrawContext(), 0, 0, MC.mc.getWindow().getScaledWidth(), MC.mc.getWindow().getScaledHeight(), (Integer)this.filter.getValue());
    }

    private void m488() {
        MC.mc.world.getLevelProperties().setTimeOfDay(((Double)this.time.getValue()).longValue());
    }

    private void m12() {
        float f = this.intensity.getFloat();
        Object var2_2 = null;
        switch (((EMode)((Object)this.weatherType.getValue())).ordinal()) {
            case 0: {
                MC.mc.world.setRainGradient(0.0f);
                MC.mc.world.setThunderGradient(0.0f);
                if (null == null) break;
            }
            case 1: 
            case 2: {
                MC.mc.world.setRainGradient(f);
                MC.mc.world.setThunderGradient(0.0f);
                if (null == null) break;
            }
            case 3: {
                MC.mc.world.setRainGradient(f);
                MC.mc.world.setThunderGradient(f);
                break;
            }
        }
    }

    private static void m289() {
        Object var1 = null;
        if (MC.mc.world != null) {
            MC.mc.worldRenderer.reload();
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode {
      CLEAR, RAIN, SNOW, STORM;

      private EMode() {}



        private static EMode[] getObjArray5() {
            return new EMode[]{CLEAR, RAIN, SNOW, STORM};
        }
    
   }
}

