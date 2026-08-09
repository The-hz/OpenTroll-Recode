/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.gui.screen.Screen
 */
package shit.module.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import shit.gui.ClickGUIScreen;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ClickGUI
extends Module {
    public final NumberSetting scale;
    public final BooleanSetting windowOutline;
    public final BooleanSetting titleBar;
    public final NumberSetting xMargin;
    public final NumberSetting yMargin;
    public final NumberSetting darkness;
    public final NumberSetting fadeInTime;
    public final NumberSetting fadeOutTime;
    public final ColorSetting primaryColor;
    public final ColorSetting backgroundColor;
    public final ColorSetting textColor;
    public final ColorSetting accentColor;
    public final NumberSetting hoverAlpha;
    public final BooleanSetting blur;
    public final BooleanSetting blurDebug;
    public final NumberSetting blurRadius;
    public final EnumSetting animation;

        public ClickGUI() {
        super("ClickGUI", "Opens the click gui.", Category.CLIENT);
        this.scale = (NumberSetting)this.registerSetting(new NumberSetting("Scale", 100.0, 50.0, 400.0, 5.0));
        this.windowOutline = (BooleanSetting)this.registerSetting(new BooleanSetting("WindowOutline", true));
        this.titleBar = (BooleanSetting)this.registerSetting(new BooleanSetting("TitleBar", true));
        this.xMargin = (NumberSetting)this.registerSetting(new NumberSetting("XMargin", 4.0, 0.0, 10.0, 0.5));
        this.yMargin = (NumberSetting)this.registerSetting(new NumberSetting("YMargin", 1.0, 0.0, 10.0, 0.5));
        this.darkness = (NumberSetting)this.registerSetting(new NumberSetting("Darkness", 0.0, 0.0, 1.0, 0.05));
        this.fadeInTime = (NumberSetting)this.registerSetting(new NumberSetting("FadeInTime", 0.4, 0.0, 1.0, 0.05));
        this.fadeOutTime = (NumberSetting)this.registerSetting(new NumberSetting("FadeOutTime", 0.4, 0.0, 1.0, 0.05));
        this.primaryColor = (ColorSetting)this.registerSetting(new ColorSetting("PrimaryColor", -7555876));
        this.backgroundColor = (ColorSetting)this.registerSetting(new ColorSetting("BackgroundColor", -1607983068));
        this.textColor = (ColorSetting)this.registerSetting(new ColorSetting("TextColor", -1283));
        this.accentColor = (ColorSetting)this.registerSetting(new ColorSetting("AccentColor", -7555876));
        this.hoverAlpha = (NumberSetting)this.registerSetting(new NumberSetting("HoverAlpha", 32.0, 0.0, 255.0, 1.0));
        this.blur = (BooleanSetting)this.registerSetting(new BooleanSetting("Blur", true));
        this.blurDebug = (BooleanSetting)this.registerSetting(new BooleanSetting("BlurDebug", false));
        this.blurRadius = (NumberSetting)this.registerSetting(new NumberSetting("BlurRadius", 6.0, 1.0, 20.0, 1.0));
        this.animation = (EnumSetting)this.registerSetting(new EnumSetting("Animation", AnimationMode.EXPO));
        this.getKeyBindSetting().setValueInternal(344);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void onEnable() {
        if (MC.mc.currentScreen == null) {
            MC.mc.setScreen((Screen) new ClickGUIScreen());
        }
        this.setEnabled(false);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum AnimationMode {
        NONE,
        CUBIC,
        EXPO;


        private static AnimationMode[] getAnimationModeArray() {
            return new AnimationMode[]{NONE, CUBIC, EXPO};
        }
    }
}
