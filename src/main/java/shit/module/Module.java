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
import shit.render.I18nHelper;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting2;
import shit.setting.Setting;
import shit.util.MC;
import shit.util.AuthGate;

@Environment(value=EnvType.CLIENT)
public abstract class Module
implements MC {
    private final String moduleName;
    private final String moduleDescription;
    private final Category category;
    private final List settingsList;
    private final BooleanSetting drawnSetting;
    private final ColorSetting2 colorSetting2;
    private boolean enabled;
    private static String[] texts8;

    protected Module(String string, String string2, Category category) {
        block0: {
            this.settingsList = new ArrayList();
            this.drawnSetting = (BooleanSetting)this.registerSetting(new BooleanSetting("Drawn", true));
            Object var4_4 = null;
            this.colorSetting2 = (ColorSetting2)this.registerSetting(new ColorSetting2("Bind", -1));
            this.moduleName = string;
            this.moduleDescription = string2;
            this.category = category;
            if (null == null) break block0;
            Module.setTextArray9(new String[2]);
        }
    }

    protected Setting registerSetting(Object object) {
        Setting setting = (Setting)object;
        this.settingsList.add(setting);
        return setting;
    }

    public void toggle() {
        block0: {
            Object var2_1 = null;
            this.setEnabled(!this.enabled);
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
                if (this.enabled == bl2) {
                    return;
                }
                if (bl2) {
                    if (!AuthGate.isSet51()) {
                        return;
                    }
                }
                this.enabled = bl2;
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
        return this.moduleName;
    }

    public String getDisplayName() {
        return I18nHelper.m14(this.moduleName);
    }

    public String getDisplayDescription() {
        return I18nHelper.m14(this.moduleDescription);
    }

    public Category getCategory() {
        return this.category;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isDrawn() {
        return (Boolean)this.drawnSetting.getValue();
    }

    public ColorSetting2 getKeyBindSetting() {
        return this.colorSetting2;
    }

    public java.util.List<shit.setting.Setting> getSettings() {
        return Collections.unmodifiableList(this.settingsList);
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

