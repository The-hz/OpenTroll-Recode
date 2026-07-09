/*
 * Decompiled with CFR 0.152.
 */
package shit.module.chat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.module.chat.ChatTimestamp;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.setting.StringSetting;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class AutoQMain
extends Module {
    private final NumberSetting delay = (NumberSetting)this.m28(new NumberSetting("Delay", 5.0, 0.0, 15.0, 0.1));
    private final BooleanSetting _2BCheck = (BooleanSetting)this.m28(new BooleanSetting("2BCheck", true));
    private final StringSetting command = (StringSetting)this.m28(new StringSetting("Command", "/queue main"));
    private final Helper7 helper718 = new Helper7();

    public AutoQMain() {
        super("AutoQMain", "Automatically runs the main queue command in end queue worlds.", Category.CHAT);
    }

    @Override
    public void onEnable() {
        this.helper718.m533();
    }

    @EventHandler
    private void setEvent2Inner4(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (MC.client3.world.getRegistryKey() != World.END) {
            return;
        }
        if (MC.client3.world.getDifficulty() != Difficulty.PEACEFUL) {
            return;
        }
        if (((Boolean)this._2BCheck.getObj()).booleanValue() && !this.isSet58()) {
            return;
        }
        if (!this.helper718.m114((Double)this.delay.getObj())) {
            return;
        }
        String string = (String)this.command.getObj();
        if (string.startsWith("/")) {
            Util2.setObj14(string.substring(1));
        } else {
            Util2.setObj62(string);
        }
        this.helper718.m533();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet58() {
        String string;
        int[] nArray = ChatTimestamp.getIntArray2();
        ClientPlayNetworkHandler clientPlayNetworkHandler = MC.client3.getNetworkHandler();
        if (nArray != null) {
            if (clientPlayNetworkHandler == null) return false;
            clientPlayNetworkHandler = MC.client3.getNetworkHandler();
        }
        ServerInfo serverInfo = clientPlayNetworkHandler.getServerInfo();
        if (nArray != null) {
            if (serverInfo == null) {
                return false;
            }
            serverInfo = MC.client3.getNetworkHandler().getServerInfo();
        }
        String string2 = string = serverInfo.address;
        if (nArray != null) {
            if (string2 == null) return false;
            string2 = string.toLowerCase();
        }
        boolean bl = string2.contains("2b2t");
        if (nArray == null) return bl;
        if (!bl) return false;
        return true;
    }
}

