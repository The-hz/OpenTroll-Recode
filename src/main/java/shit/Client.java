/*
 * Decompiled with CFR 0.152.
 */
package shit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.command.CommandManager;
import shit.manager.ConfigManager;
import shit.manager.EventBus;
import shit.manager.FontManager;
import shit.manager.FriendManager;
import shit.manager.ModuleManager;
import shit.misc.RenderInitHelper;
import shit.misc.TimerScaleManager;
import shit.misc.Logger;
import shit.misc.Logger2;
import shit.misc.MathUtil;
import shit.misc.ItemSwitcher;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public final class Client {
    public static final EventBus eventBus = new EventBus();
    public static final ModuleManager moduleManager = new ModuleManager();
    public static final CommandManager commandManager = new CommandManager();
    public static final ConfigManager configManager = new ConfigManager();
    public static final FriendManager friendManager = new FriendManager();
    public static final FontManager fontManager = new FontManager();
    public static final RenderInitHelper helper = new RenderInitHelper();
    public static final MathUtil mathUtil = new MathUtil();
    public static final ItemSwitcher itemSwitcher = new ItemSwitcher();
    public static final TimerScaleManager timerScale = new TimerScaleManager();
    private static String[] texts2;

    private Client() {
    }

    public static void init() {
        block0: {
            fontManager.init();
            mathUtil.init();
            helper.init();
            moduleManager.init();
            commandManager.init();
            configManager.init();
            Object var1 = null;
            Logger.logger2.info("{} client initialized.", (Object)"TrollHack-Recode");
            if (null == null) break block0;
            Module.setTextArray9(new String[1]);
        }
    }

    /*
     * Unable to fully structure code
     */
    static {}

    public static void setTextArray2(String[] stringArray) {
        texts2 = stringArray;
    }

    public static String[] getTextArray7() {
        return texts2;
    }
}

