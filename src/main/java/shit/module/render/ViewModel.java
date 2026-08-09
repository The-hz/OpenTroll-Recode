/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class ViewModel
extends Module {
    public static ViewModel INSTANCE;
    public final BooleanSetting noSway = (BooleanSetting)this.registerSetting(new BooleanSetting("NoSway", false));
    public final BooleanSetting mainhandSwap = (BooleanSetting)this.registerSetting(new BooleanSetting("MainhandSwap", true));
    public final BooleanSetting offhandSwap = (BooleanSetting)this.registerSetting(new BooleanSetting("OffhandSwap", true));
    public final NumberSetting scaleMainX = (NumberSetting)this.registerSetting(new NumberSetting("ScaleMainX", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting scaleMainY = (NumberSetting)this.registerSetting(new NumberSetting("ScaleMainY", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting scaleMainZ = (NumberSetting)this.registerSetting(new NumberSetting("ScaleMainZ", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting positionMainX = (NumberSetting)this.registerSetting(new NumberSetting("PositionMainX", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting positionMainY = (NumberSetting)this.registerSetting(new NumberSetting("PositionMainY", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting positionMainZ = (NumberSetting)this.registerSetting(new NumberSetting("PositionMainZ", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting rotationMainX = (NumberSetting)this.registerSetting(new NumberSetting("RotationMainX", 0.0, -180.0, 180.0, 0.01));
    public final NumberSetting rotationMainY = (NumberSetting)this.registerSetting(new NumberSetting("RotationMainY", 0.0, -180.0, 180.0, 0.01));
    public final NumberSetting rotationMainZ = (NumberSetting)this.registerSetting(new NumberSetting("RotationMainZ", 0.0, -180.0, 180.0, 0.01));
    public final NumberSetting scaleOffX = (NumberSetting)this.registerSetting(new NumberSetting("ScaleOffX", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting scaleOffY = (NumberSetting)this.registerSetting(new NumberSetting("ScaleOffY", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting scaleOffZ = (NumberSetting)this.registerSetting(new NumberSetting("ScaleOffZ", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting positionOffX = (NumberSetting)this.registerSetting(new NumberSetting("PositionOffX", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting positionOffY = (NumberSetting)this.registerSetting(new NumberSetting("PositionOffY", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting positionOffZ = (NumberSetting)this.registerSetting(new NumberSetting("PositionOffZ", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting rotationOffX = (NumberSetting)this.registerSetting(new NumberSetting("RotationOffX", 0.0, -180.0, 180.0, 0.01));
    public final NumberSetting rotationOffY = (NumberSetting)this.registerSetting(new NumberSetting("RotationOffY", 0.0, -180.0, 180.0, 0.01));
    public final NumberSetting rotationOffZ = (NumberSetting)this.registerSetting(new NumberSetting("RotationOffZ", 0.0, -180.0, 180.0, 0.01));
    public final BooleanSetting swingSpeed = (BooleanSetting)this.registerSetting(new BooleanSetting("SwingSpeed", true));
    public final NumberSetting value = (NumberSetting)this.registerSetting(new NumberSetting("Value", 6.0, 1.0, 50.0, 1.0));

    public ViewModel() {
        super("ViewModel", "First-person held item transform editor.", Category.RENDER);
        INSTANCE = this;
    }
}

