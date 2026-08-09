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
            this.booleanSetting2 = (BooleanSetting)this.registerSetting(new BooleanSetting("Drawn", true));
            Object var4_4 = null;
            this.colorSetting2 = (ColorSetting2)this.registerSetting(new ColorSetting2("Bind", -1));
            this.text2598 = string;
            this.text1185 = string2;
            this.category = category;
            if (null == null) break block0;
            Module.setTextArray9(new String[2]);
        }
    }

    protected Setting registerSetting(Object object) {
        Setting setting = (Setting)object;
        this.list11.add(setting);
        return setting;
    }

    public void toggle() {
        block0: {
            Object var2_1 = null;
            this.setEnabled(!this.flag123);
            if (Module.getTextArray9() != null) break block0;
            Category.setText7("x3Qbmc");
        }
    }

    public String getInfo() {
        return null;
    }

    public void setEnabled(boolean bl) {
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
                this.onDisable();
            } catch (RuntimeException e) {
            }
            Client.eventBus.unsubscribe(this);
        }
        Client.eventBus.post(new ModuleToggleEvent(this, bl2));
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public String getName() {
        return this.text2598;
    }

    public String getDisplayName() {
        return Outline.m14(this.text2598);
    }

    public String getDisplayDescription() {
        return Outline.m14(this.text1185);
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isEnabled() {
        return this.flag123;
    }

    public boolean isDrawn() {
        return (Boolean)this.booleanSetting2.getValue();
    }

    public ColorSetting2 getKeyBindSetting() {
        return this.colorSetting2;
    }

    public java.util.List<shit.setting.Setting> getSettings() {
        return Collections.unmodifiableList(this.list11);
    }

    public static boolean isNotInGame() {
        Object var1 = null;
        return MC.mc.player == null || MC.mc.world == null;
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

