/*
 * Decompiled with CFR 0.152.
 */
package shit.module.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.misc.NumberSetting;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.ColorSetting2;
import shit.setting.EnumSetting;
import shit.setting.StringSetting;

@Environment(value=EnvType.CLIENT)
public class SettingTest
extends Module {
    public final BooleanSetting boolean_ = (BooleanSetting)this.registerSetting(new BooleanSetting("Boolean", true));
    public final NumberSetting slider = (NumberSetting)this.registerSetting(new NumberSetting("Slider", 5.0, 0.0, 10.0, 0.5));
    public final EnumSetting enum_ = (EnumSetting)this.registerSetting(new EnumSetting("Enum", EnumMode.ONE));
    public final ColorSetting color = (ColorSetting)this.registerSetting(new ColorSetting("Color", -12942104));
    public final ColorSetting alphaColor = (ColorSetting)this.registerSetting(new ColorSetting("AlphaColor", -2009430808));
    public final ColorSetting noAlphaColor = (ColorSetting)this.registerSetting(new ColorSetting("NoAlphaColor", -12200829, false, null, null, "", false));
    public final StringSetting string = (StringSetting)this.registerSetting(new StringSetting("String", "text"));
    public final ColorSetting2 key = (ColorSetting2)this.registerSetting(new ColorSetting2("Key", 82));

    public SettingTest() {
        super("SettingTest", "Debug module for every setting type.", Category.CLIENT);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EnumMode {
      ONE, TWO, THREE;

      private EnumMode() {}



        private static EnumMode[] getEnumModeArray() {
            return new EnumMode[]{ONE, TWO, THREE};
        }
    
   }
}

