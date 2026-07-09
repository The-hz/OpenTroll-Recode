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
import shit.misc.Helper7;
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
import shit.util.RenderUtil3;

@Environment(value=EnvType.CLIENT)
public class PlaceRender
extends Module {
    public static final Map map47 = new java.util.LinkedHashMap<>();
    public static PlaceRender INSTANCE;
    private final NumberSetting placeRender = (NumberSetting)this.m28(new NumberSetting("FadeTime", 500.0, 0.0, 3000.0, 1.0));
    private final NumberSetting timeOut = (NumberSetting)this.m28(new NumberSetting("TimeOut", 500.0, 0.0, 3000.0, 1.0));
    private final ColorSetting box = (ColorSetting)this.m28(new ColorSetting("Box", -1));
    private final BooleanSetting boxDraw = (BooleanSetting)this.m28(new BooleanSetting("BoxDraw", true));
    private final ColorSetting fill = (ColorSetting)this.m28(new ColorSetting("Fill", 0x64FFFFFF));
    private final BooleanSetting fillDraw = (BooleanSetting)this.m28(new BooleanSetting("FillDraw", true));
    private final ColorSetting tryPlaceBox = (ColorSetting)this.m28(new ColorSetting("TryPlaceBox", -5066062));
    private final BooleanSetting tryBoxDraw = (BooleanSetting)this.m28(new BooleanSetting("TryBoxDraw", true));
    private final ColorSetting tryPlaceFill = (ColorSetting)this.m28(new ColorSetting("TryPlaceFill", -1644202121));
    private final BooleanSetting tryFillDraw = (BooleanSetting)this.m28(new BooleanSetting("TryFillDraw", true));
    private final BooleanSetting noFail = (BooleanSetting)this.m28(new BooleanSetting("NoFail", false));
    private final BooleanSetting through = (BooleanSetting)this.m28(new BooleanSetting("Through", true));
    private final EnumSetting ease = (EnumSetting)this.m28(new EnumSetting("Ease", EaseMode.CubicInOut));
    private final EnumSetting mode = (EnumSetting)this.m28(new EnumSetting("Mode", Mode.All));

    public PlaceRender() {
        super("PlaceRender", "Renders recently placed blocks.", Category.RENDER);
        INSTANCE = this;
        this.setFlag3(true);
    }

    @Override
    public void m709() {
        map47.clear();
        super.m709();
    }

    public static void setObj20(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var3_2 = null;
        if (INSTANCE != null) {
            if (INSTANCE.isSet19()) {
                INSTANCE.setObj98(blockPos);
            }
        }
    }

    @EventHandler
    private void onRender3D(RenderLevelEvent renderLevelEvent) {
        if (Module.isSet37() || map47.isEmpty()) {
            return;
        }
        Matrix4f matrix4f = renderLevelEvent.getMatrix4f3();
        map47.values().removeIf(mathUtil -> ((MathUtil)mathUtil).m506(matrix4f));
        EspRenderLayers.m125();
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
        private final Helper7 helper723;
        private boolean flag27;
        final PlaceRender placeRender;

        public MathUtil(PlaceRender placeRender, BlockPos blockPos) {
            this.placeRender = placeRender;
            this.timer = new Timer(((Double)this.placeRender.placeRender.getObj()).longValue());
            this.helper723 = new Helper7();
            this.flag27 = true;
            this.blockPos19 = blockPos;
        }

        public boolean m506(Object object) {
            int n;
            Matrix4f matrix4f = (Matrix4f)object;
            Object var4_3 = null;
            if (this.flag27) {
                if (!((Boolean)this.placeRender.noFail.getObj()).booleanValue()) {
                    if (MC.client3.world.getBlockState(this.blockPos19).isAir()) {
                        if (!this.helper723.m432((Double)this.placeRender.timeOut.getObj())) {
                            this.timer.m136();
                            Box box = new Box(this.blockPos19);
                            if (((Boolean)this.placeRender.tryFillDraw.getObj()).booleanValue()) {
                                EspRenderLayers.m69(matrix4f, box, (Integer)this.placeRender.tryPlaceFill.getObj(), (Boolean)this.placeRender.through.getObj());
                            }
                            if (((Boolean)this.placeRender.tryBoxDraw.getObj()).booleanValue()) {
                                EspRenderLayers.m688(matrix4f, box, (Integer)this.placeRender.tryPlaceBox.getObj(), (Boolean)this.placeRender.through.getObj());
                            }
                        }
                        return false;
                    }
                }
                this.flag27 = false;
            }
            double d = this.timer.m1037((Object)((EaseMode)((Object)this.placeRender.ease.getObj())));
            if (d == 1.0) {
                return true;
            }
            double d2 = this.placeRender.mode.getObj() != Mode.Fade && this.placeRender.mode.getObj() != Mode.All ? 1.0 : 1.0 - d;
            double d3 = this.placeRender.mode.getObj() != Mode.Shrink && this.placeRender.mode.getObj() != Mode.All ? 0.0 : d;
            Box box = new Box(this.blockPos19).expand(-d3 * 0.5);
            if (((Boolean)this.placeRender.fillDraw.getObj()).booleanValue()) {
                n = (Integer)this.placeRender.fill.getObj();
                EspRenderLayers.m69(matrix4f, box, RenderUtil3.m517(n, (int)((double)(n >>> 24 & 0xFF) * d2)), (Boolean)this.placeRender.through.getObj());
            }
            if (((Boolean)this.placeRender.boxDraw.getObj()).booleanValue()) {
                n = (Integer)this.placeRender.box.getObj();
                EspRenderLayers.m688(matrix4f, box, RenderUtil3.m517(n, (int)((double)(n >>> 24 & 0xFF) * d2)), (Boolean)this.placeRender.through.getObj());
            }
            return false;
        }
    }
}

