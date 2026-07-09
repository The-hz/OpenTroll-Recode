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
import shit.util.RenderUtil3;

@Environment(value=EnvType.CLIENT)
public class Ambience
extends Module {
    public static Ambience INSTANCE;
    public final ColorSetting filter = (ColorSetting)this.m28(new ColorSetting("Filter", 0x14FFFFFF));
    public final BooleanSetting filterDraw = (BooleanSetting)this.m28(new BooleanSetting("FilterDraw", false));
    public final BooleanSetting customTime = (BooleanSetting)this.m28(new BooleanSetting("CustomTime", false));
    public final NumberSetting time = (NumberSetting)this.m28(new NumberSetting("Time", 0.0, 0.0, 24000.0, 1.0));
    public final BooleanSetting customWeather = (BooleanSetting)this.m28(new BooleanSetting("CustomWeather", false));
    public final EnumSetting weatherType = (EnumSetting)this.m28(new EnumSetting("WeatherType", EMode.CLEAR));
    public final NumberSetting intensity = (NumberSetting)this.m28(new NumberSetting("Intensity", 1.0, 0.0, 1.0, 0.05, 0.05, () -> {
        Object var1_1 = null;
        return (Boolean)this.customWeather.getObj() != false && this.weatherType.getObj() != EMode.CLEAR;
    }, null, "", false));
    public final ColorSetting fogColor = (ColorSetting)this.m28(new ColorSetting("FogColor", -4138753));
    public final BooleanSetting fogColorDraw = (BooleanSetting)this.m28(new BooleanSetting("FogColorDraw", false));
    public final ColorSetting skyColor = (ColorSetting)this.m28(new ColorSetting("SkyColor", -8933889));
    public final BooleanSetting skyColorDraw = (BooleanSetting)this.m28(new BooleanSetting("SkyColorDraw", false));
    public final ColorSetting cloudColor = (ColorSetting)this.m28(new ColorSetting("CloudColor", -1));
    public final BooleanSetting cloudColorDraw = (BooleanSetting)this.m28(new BooleanSetting("CloudColorDraw", false));
    public final ColorSetting worldColor = (ColorSetting)this.m28(new ColorSetting("WorldColor", -1));
    public final BooleanSetting worldColorDraw = (BooleanSetting)this.m28(new BooleanSetting("WorldColorDraw", false));
    public final BooleanSetting fogDistance = (BooleanSetting)this.m28(new BooleanSetting("FogDistance", false));
    public final NumberSetting fogStart = (NumberSetting)this.m28(new NumberSetting("FogStart", 50.0, 0.0, 1000.0, 1.0));
    public final NumberSetting fogEnd = (NumberSetting)this.m28(new NumberSetting("FogEnd", 100.0, 0.0, 1000.0, 1.0));
    public final BooleanSetting fullBright = (BooleanSetting)this.m28(new BooleanSetting("FullBright", false));
    public final BooleanSetting customLuminance = (BooleanSetting)this.m28(new BooleanSetting("CustomLuminance", false, () -> true, (bl, bl2) -> {
        Ambience.m289();
        return bl2;
    }, "", false));
    public final NumberSetting luminance = (NumberSetting)this.m28(new NumberSetting("Luminance", 15.0, 0.0, 15.0, 1.0));
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
        if (Module.isSet37()) {
            return;
        }
        this.time20 = MC.client3.world.getLevelProperties().getTimeOfDay();
        if (((Boolean)this.customTime.getObj()).booleanValue()) {
            this.m488();
        }
        this.value151 = MC.client3.world.getRainGradient(1.0f);
        this.value196 = MC.client3.world.getThunderGradient(1.0f);
        if (((Boolean)this.customWeather.getObj()).booleanValue()) {
            this.m12();
        }
    }

    @Override
    public void m709() {
        Object var2_1 = null;
        if (Module.isSet37()) {
            return;
        }
        MC.client3.world.getLevelProperties().setTimeOfDay(this.time20);
        MC.client3.world.setRainGradient(this.value151);
        MC.client3.world.setThunderGradient(this.value196);
        MC.client3.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
    }

    @EventHandler
    private void setEvent2Inner49(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (((Boolean)this.customTime.getObj()).booleanValue()) {
            this.m488();
        }
        if (((Boolean)this.customWeather.getObj()).booleanValue()) {
            this.m12();
        }
        if (((Boolean)this.fullBright.getObj()).booleanValue()) {
            MC.client3.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 400, 0, false, false, false));
        }
    }

    @EventHandler
    private void setPacketEventInner20(PacketEvent.PacketEventInner packetEventInner) {
        Packet packet;
        if (Module.isSet37()) {
            return;
        }
        if (((Boolean)this.customTime.getObj()).booleanValue() && (packet = packetEventInner.getPacket()) instanceof WorldTimeUpdateS2CPacket) {
            WorldTimeUpdateS2CPacket worldTimeUpdateS2CPacket = (WorldTimeUpdateS2CPacket)packet;
            this.time20 = worldTimeUpdateS2CPacket.timeOfDay();
            packetEventInner.m209();
        }
    }

    @EventHandler
    private void setObj44(Render2DEvent render2DEvent) {
        if (!((Boolean)this.filterDraw.getObj()).booleanValue()) {
            return;
        }
        RenderUtil3.m526(render2DEvent.getDrawContext(), 0, 0, MC.client3.getWindow().getScaledWidth(), MC.client3.getWindow().getScaledHeight(), (Integer)this.filter.getObj());
    }

    private void m488() {
        MC.client3.world.getLevelProperties().setTimeOfDay(((Double)this.time.getObj()).longValue());
    }

    private void m12() {
        float f = this.intensity.getFloat35();
        Object var2_2 = null;
        switch (((EMode)((Object)this.weatherType.getObj())).ordinal()) {
            case 0: {
                MC.client3.world.setRainGradient(0.0f);
                MC.client3.world.setThunderGradient(0.0f);
                if (null == null) break;
            }
            case 1: 
            case 2: {
                MC.client3.world.setRainGradient(f);
                MC.client3.world.setThunderGradient(0.0f);
                if (null == null) break;
            }
            case 3: {
                MC.client3.world.setRainGradient(f);
                MC.client3.world.setThunderGradient(f);
                break;
            }
        }
    }

    private static void m289() {
        Object var1 = null;
        if (MC.client3.world != null) {
            MC.client3.worldRenderer.reload();
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

