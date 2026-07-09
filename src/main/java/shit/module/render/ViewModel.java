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
    public final BooleanSetting noSway = (BooleanSetting)this.m28(new BooleanSetting("NoSway", false));
    public final BooleanSetting mainhandSwap = (BooleanSetting)this.m28(new BooleanSetting("MainhandSwap", true));
    public final BooleanSetting offhandSwap = (BooleanSetting)this.m28(new BooleanSetting("OffhandSwap", true));
    public final NumberSetting scaleMainX = (NumberSetting)this.m28(new NumberSetting("ScaleMainX", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting scaleMainY = (NumberSetting)this.m28(new NumberSetting("ScaleMainY", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting scaleMainZ = (NumberSetting)this.m28(new NumberSetting("ScaleMainZ", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting positionMainX = (NumberSetting)this.m28(new NumberSetting("PositionMainX", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting positionMainY = (NumberSetting)this.m28(new NumberSetting("PositionMainY", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting positionMainZ = (NumberSetting)this.m28(new NumberSetting("PositionMainZ", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting rotationMainX = (NumberSetting)this.m28(new NumberSetting("RotationMainX", 0.0, -180.0, 180.0, 0.01));
    public final NumberSetting rotationMainY = (NumberSetting)this.m28(new NumberSetting("RotationMainY", 0.0, -180.0, 180.0, 0.01));
    public final NumberSetting rotationMainZ = (NumberSetting)this.m28(new NumberSetting("RotationMainZ", 0.0, -180.0, 180.0, 0.01));
    public final NumberSetting scaleOffX = (NumberSetting)this.m28(new NumberSetting("ScaleOffX", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting scaleOffY = (NumberSetting)this.m28(new NumberSetting("ScaleOffY", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting scaleOffZ = (NumberSetting)this.m28(new NumberSetting("ScaleOffZ", 1.0, 0.1, 5.0, 0.01));
    public final NumberSetting positionOffX = (NumberSetting)this.m28(new NumberSetting("PositionOffX", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting positionOffY = (NumberSetting)this.m28(new NumberSetting("PositionOffY", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting positionOffZ = (NumberSetting)this.m28(new NumberSetting("PositionOffZ", 0.0, -3.0, 3.0, 0.01));
    public final NumberSetting rotationOffX = (NumberSetting)this.m28(new NumberSetting("RotationOffX", 0.0, -180.0, 180.0, 0.01));
    public final NumberSetting rotationOffY = (NumberSetting)this.m28(new NumberSetting("RotationOffY", 0.0, -180.0, 180.0, 0.01));
    public final NumberSetting rotationOffZ = (NumberSetting)this.m28(new NumberSetting("RotationOffZ", 0.0, -180.0, 180.0, 0.01));
    public final BooleanSetting swingSpeed = (BooleanSetting)this.m28(new BooleanSetting("SwingSpeed", true));
    public final NumberSetting value = (NumberSetting)this.m28(new NumberSetting("Value", 6.0, 1.0, 50.0, 1.0));

    public ViewModel() {
        super("ViewModel", "First-person held item transform editor.", Category.RENDER);
        INSTANCE = this;
    }
}

