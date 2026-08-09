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
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.misc.Stopwatch;
import shit.module.Category;
import shit.module.Module;
import shit.module.chat.ChatTimestamp;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.setting.StringSetting;
import shit.util.MC;
import shit.util.ChatUtils;

@Environment(value=EnvType.CLIENT)
public class AutoQMain
extends Module {
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 5.0, 0.0, 15.0, 0.1));
    private final BooleanSetting _2BCheck = (BooleanSetting)this.registerSetting(new BooleanSetting("2BCheck", true));
    private final StringSetting command = (StringSetting)this.registerSetting(new StringSetting("Command", "/queue main"));
    private final Stopwatch helper718 = new Stopwatch();

    public AutoQMain() {
        super("AutoQMain", "Automatically runs the main queue command in end queue worlds.", Category.CHAT);
    }

    @Override
    public void onEnable() {
        this.helper718.resetTimer();
    }

    @EventHandler
    private void setEvent2Inner4(TickEvent.PreTick event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (MC.mc.world.getRegistryKey() != World.END) {
            return;
        }
        if (MC.mc.world.getDifficulty() != Difficulty.PEACEFUL) {
            return;
        }
        if (((Boolean)this._2BCheck.getValue()).booleanValue() && !this.isSet58()) {
            return;
        }
        if (!this.helper718.hasPassedSeconds((Double)this.delay.getValue())) {
            return;
        }
        String string = (String)this.command.getValue();
        if (string.startsWith("/")) {
            ChatUtils.sendChatCommand(string.substring(1));
        } else {
            ChatUtils.sendChatMessage(string);
        }
        this.helper718.resetTimer();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet58() {
        String string;
        int[] nArray = ChatTimestamp.getIntArray2();
        ClientPlayNetworkHandler clientPlayNetworkHandler = MC.mc.getNetworkHandler();
        if (nArray != null) {
            if (clientPlayNetworkHandler == null) return false;
            clientPlayNetworkHandler = MC.mc.getNetworkHandler();
        }
        ServerInfo serverInfo = clientPlayNetworkHandler.getServerInfo();
        if (nArray != null) {
            if (serverInfo == null) {
                return false;
            }
            serverInfo = MC.mc.getNetworkHandler().getServerInfo();
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

