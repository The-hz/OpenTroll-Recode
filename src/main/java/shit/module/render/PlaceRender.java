/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;
import shit.event.EventHandler;
import shit.event.RenderLevelEvent;
import shit.misc.Stopwatch;
import shit.misc.NumberSetting;
import shit.misc.Timer;
import shit.module.Category;
import shit.module.Module;
import shit.render.EspRenderLayers;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.type.EaseMode;
import shit.util.MC;
import shit.util.VanillaTextHelper;

@Environment(value=EnvType.CLIENT)
public class PlaceRender
extends Module {
    public static final Map map47 = new java.util.LinkedHashMap<>();
    public static PlaceRender INSTANCE;
    private final NumberSetting placeRender = (NumberSetting)this.registerSetting(new NumberSetting("FadeTime", 500.0, 0.0, 3000.0, 1.0));
    private final NumberSetting timeOut = (NumberSetting)this.registerSetting(new NumberSetting("TimeOut", 500.0, 0.0, 3000.0, 1.0));
    private final ColorSetting box = (ColorSetting)this.registerSetting(new ColorSetting("Box", -1));
    private final BooleanSetting boxDraw = (BooleanSetting)this.registerSetting(new BooleanSetting("BoxDraw", true));
    private final ColorSetting fill = (ColorSetting)this.registerSetting(new ColorSetting("Fill", 0x64FFFFFF));
    private final BooleanSetting fillDraw = (BooleanSetting)this.registerSetting(new BooleanSetting("FillDraw", true));
    private final ColorSetting tryPlaceBox = (ColorSetting)this.registerSetting(new ColorSetting("TryPlaceBox", -5066062));
    private final BooleanSetting tryBoxDraw = (BooleanSetting)this.registerSetting(new BooleanSetting("TryBoxDraw", true));
    private final ColorSetting tryPlaceFill = (ColorSetting)this.registerSetting(new ColorSetting("TryPlaceFill", -1644202121));
    private final BooleanSetting tryFillDraw = (BooleanSetting)this.registerSetting(new BooleanSetting("TryFillDraw", true));
    private final BooleanSetting noFail = (BooleanSetting)this.registerSetting(new BooleanSetting("NoFail", false));
    private final BooleanSetting through = (BooleanSetting)this.registerSetting(new BooleanSetting("Through", true));
    private final EnumSetting ease = (EnumSetting)this.registerSetting(new EnumSetting("Ease", EaseMode.CubicInOut));
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.All));

    public PlaceRender() {
        super("PlaceRender", "Renders recently placed blocks.", Category.RENDER);
        INSTANCE = this;
        this.setEnabled(true);
    }

    @Override
    public void onDisable() {
        map47.clear();
        super.onDisable();
    }

    public static void setObj20(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var3_2 = null;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                INSTANCE.setObj98(blockPos);
            }
        }
    }

    @EventHandler
    private void onRender3D(RenderLevelEvent renderLevelEvent) {
        if (Module.isNotInGame() || map47.isEmpty()) {
            return;
        }
        Matrix4f matrix4f = renderLevelEvent.getMatrix4f3();
        map47.values().removeIf(mathUtil -> ((MathUtil)mathUtil).m506(matrix4f));
        EspRenderLayers.drawBuffers();
    }

    public void setObj98(Object object) {
        BlockPos blockPos = (BlockPos)object;
        BlockPos blockPos2 = blockPos.toImmutable();
        map47.put(blockPos2, new MathUtil(this, blockPos2));
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      Fade, Shrink, All;

      private Mode() {}



        private static Mode[] getModeArray5() {
            return new Mode[]{Fade, Shrink, All};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static class MathUtil {
        private final Timer timer;
        private final BlockPos blockPos19;
        private final Stopwatch helper723;
        private boolean flag27;
        final PlaceRender placeRender;

        public MathUtil(PlaceRender placeRender, BlockPos blockPos) {
            this.placeRender = placeRender;
            this.timer = new Timer(((Double)this.placeRender.placeRender.getValue()).longValue());
            this.helper723 = new Stopwatch();
            this.flag27 = true;
            this.blockPos19 = blockPos;
        }

        public boolean m506(Object object) {
            int n;
            Matrix4f matrix4f = (Matrix4f)object;
            Object var4_3 = null;
            if (this.flag27) {
                if (!((Boolean)this.placeRender.noFail.getValue()).booleanValue()) {
                    if (MC.mc.world.getBlockState(this.blockPos19).isAir()) {
                        if (!this.helper723.hasPassedMs((Double)this.placeRender.timeOut.getValue())) {
                            this.timer.m136();
                            Box box = new Box(this.blockPos19);
                            if (((Boolean)this.placeRender.tryFillDraw.getValue()).booleanValue()) {
                                EspRenderLayers.drawBoxFilled(matrix4f, box, (Integer)this.placeRender.tryPlaceFill.getValue(), (Boolean)this.placeRender.through.getValue());
                            }
                            if (((Boolean)this.placeRender.tryBoxDraw.getValue()).booleanValue()) {
                                EspRenderLayers.drawBoxOutline(matrix4f, box, (Integer)this.placeRender.tryPlaceBox.getValue(), (Boolean)this.placeRender.through.getValue());
                            }
                        }
                        return false;
                    }
                }
                this.flag27 = false;
            }
            double d = this.timer.m1037((Object)((EaseMode)((Object)this.placeRender.ease.getValue())));
            if (d == 1.0) {
                return true;
            }
            double d2 = this.placeRender.mode.getValue() != Mode.Fade && this.placeRender.mode.getValue() != Mode.All ? 1.0 : 1.0 - d;
            double d3 = this.placeRender.mode.getValue() != Mode.Shrink && this.placeRender.mode.getValue() != Mode.All ? 0.0 : d;
            Box box = new Box(this.blockPos19).expand(-d3 * 0.5);
            if (((Boolean)this.placeRender.fillDraw.getValue()).booleanValue()) {
                n = (Integer)this.placeRender.fill.getValue();
                EspRenderLayers.drawBoxFilled(matrix4f, box, VanillaTextHelper.m517(n, (int)((double)(n >>> 24 & 0xFF) * d2)), (Boolean)this.placeRender.through.getValue());
            }
            if (((Boolean)this.placeRender.boxDraw.getValue()).booleanValue()) {
                n = (Integer)this.placeRender.box.getValue();
                EspRenderLayers.drawBoxOutline(matrix4f, box, VanillaTextHelper.m517(n, (int)((double)(n >>> 24 & 0xFF) * d2)), (Boolean)this.placeRender.through.getValue());
            }
            return false;
        }
    }
}

