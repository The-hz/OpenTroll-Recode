/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package shit.module.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class ClientSetting
extends Module {
    public static ClientSetting INSTANCE;
    public final EnumSetting rotateMode;
    public final NumberSetting rotateSpeed;
    public final BooleanSetting movementSync;
    public final EnumSetting switchMode;
    public final ColorSetting prefixColor;
    public final BooleanSetting chinese;
    public final NumberSetting renderScale;
    public final BooleanSetting fontAntiAliasing;
    private static int[] counts9;

        public ClientSetting() {
        super("ClientSetting", "Global client settings.", Category.CLIENT);
        this.rotateMode = (EnumSetting)this.m28(new EnumSetting("RotateMode", RotateMode.NONE));
        this.rotateSpeed = (NumberSetting)this.m28(new NumberSetting("RotateSpeed", 45.0, 1.0, 180.0, 5.0));
        this.movementSync = (BooleanSetting)this.m28(new BooleanSetting("MovementSync", false));
        this.switchMode = (EnumSetting)this.m28(new EnumSetting("SwitchMode", SwitchMode.NONE));
        this.prefixColor = (ColorSetting)this.m28(new ColorSetting("PrefixColor", -11141121));
        this.chinese = (BooleanSetting)this.m28(new BooleanSetting("Chinese", false));
        this.renderScale = (NumberSetting)this.m28(new NumberSetting("RenderScale", 1.0, 0.5, 3.0, 0.05, 0.01, null, null, "", false));
        this.fontAntiAliasing = (BooleanSetting)this.m28(new BooleanSetting("FontAntiAliasing", true));
    }

    public double getDouble7() {
        return (Double)this.renderScale.getObj();
    }

    public static void setIntArray3(int[] nArray) {
        counts9 = nArray;
    }

    public static int[] getIntArray() {
        return counts9;
    }

    static {
        boolean bl = false;
        ClientSetting.setIntArray3(new int[5]);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum SwitchMode {
        NONE,
        NORMAL,
        SILENT,
        INVENTORY;


        private static SwitchMode[] getSwitchModeArray3() {
            return new SwitchMode[]{NONE, NORMAL, SILENT, INVENTORY};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum RotateMode {
        NONE,
        SMOOTH,
        ONTICK,
        rotateMode;


        private static RotateMode[] getRotateModeArray() {
            return new RotateMode[]{NONE, SMOOTH, ONTICK, rotateMode};
        }
    }
}
