/*
 * Decompiled with CFR 0.152.
 */
package shit.module.chat;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.LambdaMetafactory;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.chat.ChatTimestamp;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.setting.StringSetting;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class Spammer
extends Module {
    private final EnumSetting order = (EnumSetting)this.m28(new EnumSetting("Order", OrderMode.RANDOM));
    private final NumberSetting delay = (NumberSetting)this.m28(new NumberSetting("Delay", 10.0, 1.0, 180.0, 1.0));
    private final BooleanSetting loadFromURL = (BooleanSetting)this.m28(new BooleanSetting("LoadFromURL", false));
    private final StringSetting remoteURL = (StringSetting)this.m28(new StringSetting("RemoteURL", "unchanged"));
    private final List list27 = new ArrayList();
    private final Random random2 = new Random();
    private int count226 = 0;
    private long time60 = 0L;
    private static final File file = null;

    public Spammer() {
        super("Spammer", "Spams messages from a file on a set delay.", Category.CHAT);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Override
    public void onEnable() {
        this.list27.clear();
        this.count226 = 0;
        if (((Boolean)this.loadFromURL.getObj()).booleanValue()) {
            String url = (String)this.remoteURL.getObj();
            if (url.equals("unchanged")) {
                Util2.setObj10("\u00a7c[Spammer] Change RemoteURL in ClickGUI first!");
                this.setFlag3(false);
                return;
            }
            Thread thread = new Thread(() -> this.cfrlam$onEnable$0(url), "spammer-loader");
            thread.setDaemon(true);
            thread.start();
            return;
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException iOException) {}
            Util2.setObj10("\u00a7c[Spammer] spammer.txt not found, created empty file. Add messages to it.");
            this.setFlag3(false);
            return;
        }
        try {
            for (String string : Files.readAllLines(file.toPath())) {
                String string2 = string.trim();
                if (string2.isEmpty()) continue;
                this.list27.add(string2);
            }
            if (this.list27.isEmpty()) {
                Util2.setObj10("\u00a7c[Spammer] spammer.txt is empty!");
                this.setFlag3(false);
                return;
            }
            Util2.setObj10("\u00a7a[Spammer] Loaded " + this.list27.size() + " messages.");
        }
        catch (IOException iOException) {
            Util2.setObj10("\u00a7c[Spammer] Failed to read spammer.txt: " + iOException.getMessage());
            this.setFlag3(false);
        }
    }

    @Override
    public void m709() {
        this.list27.clear();
        this.count226 = 0;
    }

    @EventHandler
    private void setEvent2Inner28(Event2.Event2Inner2 event2Inner2) {
        String string;
        long l;
        if (Module.isSet37() || this.list27.isEmpty()) {
            return;
        }
        long l2 = System.currentTimeMillis();
        if (l2 - this.time60 < (l = (long)((Double)this.delay.getObj() * 1000.0))) {
            return;
        }
        this.time60 = l2;
        String string2 = string = this.order.getObj() == OrderMode.IN_ORDER ? this.getText49() : this.getText12();
        if (string.startsWith("/")) {
            Util2.setObj14(string.substring(1));
        } else {
            Util2.setObj62(string);
        }
    }

    private String getText49() {
        String string = (String)this.list27.get(this.count226 % this.list27.size());
        ++this.count226;
        return string;
    }

    private String getText12() {
        Spammer spammer;
        String result = null;
        block4: {
            int n = 0;
            int[] nArray = ChatTimestamp.getIntArray2();
            int n2 = this.list27.size();
            if (nArray != null) {
                if (n2 == 1) {
                    return (String)this.list27.get(0);
                }
                n2 = n = this.count226;
            }
            while (this.count226 == n) {
                spammer = this;
                if (nArray != null) {
                    spammer.count226 = this.random2.nextInt(this.list27.size());
                    if (nArray != null) continue;
                }
                break block4;
            }
            result = (String)this.list27.get(this.count226);
        }
        return result;
    }

    private /* synthetic */ void cfrlam$onEnable$0(String string) {
        int[] nArray = ChatTimestamp.getIntArray2();
        try {
            int n;
            block6: {
                URL uRL = new URL(string);
                String string2 = new String(uRL.openStream().readAllBytes());
                String[] stringArray = string2.split("\n");
                int n2 = stringArray.length;
                int n3 = 0;
                while (n3 < n2) {
                    String string3 = stringArray[n3];
                    String string4 = string3.trim();
                    if (nArray != null) {
                        n = string4.isEmpty() ? 1 : 0;
                        if (nArray == null) break block6;
                        if (n == 0) {
                            this.list27.add(string4);
                        }
                        ++n3;
                    }
                    if (nArray != null) continue;
                }
                n = this.list27.size();
            }
            Util2.setObj10("\u00a7a[Spammer] Loaded " + n + " remote messages.");
        }
        catch (Exception exception) {
            Util2.setObj10("\u00a7c[Spammer] Failed to load URL: " + exception.getMessage());
            this.setFlag3(false);
        }
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    public static enum OrderMode {
      IN_ORDER, RANDOM;

      private OrderMode() {}



        private static OrderMode[] getOrderModeArray() {
            return new OrderMode[]{IN_ORDER, RANDOM};
        }
    
   }
}

