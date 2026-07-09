/*
 * Decompiled with CFR 0.152.
 */
package shit.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.Client;
import shit.event.ModuleToggleEvent;
import shit.module.Category;
import shit.render.Outline;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting2;
import shit.setting.Setting;
import shit.util.MC;
import shit.util.Util4;

@Environment(value=EnvType.CLIENT)
public abstract class Module
implements MC {
    private final String text2598;
    private final String text1185;
    private final Category category;
    private final List list11;
    private final BooleanSetting booleanSetting2;
    private final ColorSetting2 colorSetting2;
    private boolean flag123;
    private static String[] texts8;

    protected Module(String string, String string2, Category category) {
        block0: {
            this.list11 = new ArrayList();
            this.booleanSetting2 = (BooleanSetting)this.m28(new BooleanSetting("Drawn", true));
            Object var4_4 = null;
            this.colorSetting2 = (ColorSetting2)this.m28(new ColorSetting2("Bind", -1));
            this.text2598 = string;
            this.text1185 = string2;
            this.category = category;
            if (null == null) break block0;
            Module.setTextArray9(new String[2]);
        }
    }

    protected Setting m28(Object object) {
        Setting setting = (Setting)object;
        this.list11.add(setting);
        return setting;
    }

    public void m84() {
        block0: {
            Object var2_1 = null;
            this.setFlag3(!this.flag123);
            if (Module.getTextArray9() != null) break block0;
            Category.setText7("x3Qbmc");
        }
    }

    public String getText57() {
        return null;
    }

    public void setFlag3(boolean bl) {
        boolean bl2;
        block7: {
            block6: {
                bl2 = bl;
                Object var4_3 = null;
                if (this.flag123 == bl2) {
                    return;
                }
                if (bl2) {
                    if (!Util4.isSet51()) {
                        return;
                    }
                }
                this.flag123 = bl2;
                if (!bl2) break block6;
                Client.eventBus.subscribe(this);
                try {
                    this.onEnable();
                } catch (RuntimeException e) {
                }
                if (null == null) break block7;
            }
            try {
                this.m709();
            } catch (RuntimeException e) {
            }
            Client.eventBus.setObj18(this);
        }
        Client.eventBus.m287(new ModuleToggleEvent(this, bl2));
    }

    public void onEnable() {
    }

    public void m709() {
    }

    public String getText69() {
        return this.text2598;
    }

    public String getText43() {
        return Outline.m14(this.text2598);
    }

    public String getText32() {
        return Outline.m14(this.text1185);
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isSet19() {
        return this.flag123;
    }

    public boolean isSet36() {
        return (Boolean)this.booleanSetting2.getObj();
    }

    public ColorSetting2 getColorSetting2() {
        return this.colorSetting2;
    }

    public java.util.List<shit.setting.Setting> getList8() {
        return Collections.unmodifiableList(this.list11);
    }

    public static boolean isSet37() {
        Object var1 = null;
        return MC.client3.player == null || MC.client3.world == null;
    }

    public static void setTextArray9(String[] stringArray) {
        texts8 = stringArray;
    }

    public static String[] getTextArray9() {
        return texts8;
    }

    static {
        boolean bl = false;
        Module.setTextArray9(new String[4]);
    }
}

