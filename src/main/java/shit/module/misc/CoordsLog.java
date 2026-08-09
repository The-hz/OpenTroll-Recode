/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class CoordsLog
extends Module {
    private final BooleanSetting death = (BooleanSetting)this.registerSetting(new BooleanSetting("Death", true));
    private final BooleanSetting autoLog = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoLog", false));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 15.0, 1.0, 300.0, 1.0));
    private final Helper7 helper714 = new Helper7();
    private String x = "";
    private boolean flag114;

    public CoordsLog() {
        super("CoordsLog", "Logs your coordinates on death or interval.", Category.MISC);
    }

    @Override
    public void onEnable() {
        this.helper714.resetTimer();
        this.x = "";
        this.flag114 = false;
    }

    @EventHandler
    private void setEvent2Inner217(Event2.Event2Inner2 event2Inner2) {
        if (Module.isNotInGame()) {
            return;
        }
        if (((Boolean)this.autoLog.getValue()).booleanValue() && this.helper714.hasPassedSeconds((Double)this.delay.getValue())) {
            String string = this.m469("Auto");
            if (!string.equals(this.x)) {
                CoordsLog.setObj67(string);
                this.x = string;
            }
            this.helper714.resetTimer();
        }
        if (((Boolean)this.death.getValue()).booleanValue()) {
            boolean bl;
            boolean bl2 = bl = MC.mc.player.isDead() || MC.mc.player.getHealth() <= 0.0f;
            if (bl && !this.flag114) {
                CoordsLog.setObj67(this.m469("Death"));
                this.flag114 = true;
            } else if (!bl) {
                this.flag114 = false;
            }
        }
    }

    static void setObj67(Object object) {
        String string = (String)object;
        Util2.sendClientMessage("[CoordsLog] " + string);
        CoordsLog.setObj42(string);
    }

    static void setObj30(Object object) {
        String string = (String)object;
        CoordsLog.setObj42(string);
    }

    private static void setObj42(Object object) {
        try {
            String string = (String)object;
            Path path = Path.of("trollhack-logs", new String[0]);
            Files.createDirectories(path, new FileAttribute[0]);
            Files.writeString(path.resolve("coords.log"), (CharSequence)(string + System.lineSeparator()), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
        catch (IOException iOException) {}
    }

    private String m469(Object object) {
        String string = (String)object;
        int n = MC.mc.player.getBlockZ();
        int n2 = MC.mc.player.getBlockY();
        int n3 = MC.mc.player.getBlockX();
        return string + " " + n3 + ", " + n2 + ", " + n;
    }
}

