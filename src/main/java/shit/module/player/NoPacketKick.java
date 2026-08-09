/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.MC;
import shit.util.ChatUtils;

@Environment(value=EnvType.CLIENT)
public class NoPacketKick
extends Module {
    public static NoPacketKick INSTANCE;
    private final BooleanSetting cancelDisconnect = (BooleanSetting)this.registerSetting(new BooleanSetting("CancelDisconnect", true));
    private final BooleanSetting cancelResourcePack = (BooleanSetting)this.registerSetting(new BooleanSetting("CancelResourcePack", true));
    private final BooleanSetting cancelCloseScreen = (BooleanSetting)this.registerSetting(new BooleanSetting("CancelCloseScreen", false));
    private final BooleanSetting logToChat = (BooleanSetting)this.registerSetting(new BooleanSetting("LogToChat", true));

    public NoPacketKick() {
        super("NoPacketKick", "Cancels incoming packets that could disconnect or trap the player.", Category.PLAYER);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        ChatUtils.sendClientMessage("[NoPacketKick] Active. Cancelling: disconnect=" + (Boolean)this.cancelDisconnect.getValue() + ", resourcePack=" + (Boolean)this.cancelResourcePack.getValue() + ", closeScreen=" + (Boolean)this.cancelCloseScreen.getValue());
    }

    @EventHandler(priority=2000)
    private void setPacketEventInner9(PacketEvent.PacketEventInner packetEventInner) {
        if (Module.isNotInGame()) {
            return;
        }
        Packet packet = packetEventInner.getPacket();
        if (packet == null) {
            return;
        }
        if (((Boolean)this.cancelDisconnect.getValue()).booleanValue() && packet instanceof DisconnectS2CPacket) {
            DisconnectS2CPacket disconnectS2CPacket = (DisconnectS2CPacket)packet;
            String string = disconnectS2CPacket.reason() != null ? disconnectS2CPacket.reason().getString() : "(no reason)";
            if (((Boolean)this.logToChat.getValue()).booleanValue()) {
                ChatUtils.sendClientMessage("\u00a7c[NoPacketKick] \u00a7fBlocked server disconnect: " + string);
            }
            packetEventInner.cancel();
            return;
        }
        if (((Boolean)this.cancelResourcePack.getValue()).booleanValue() && packet instanceof ResourcePackSendS2CPacket) {
            if (((Boolean)this.logToChat.getValue()).booleanValue()) {
                ChatUtils.sendClientMessage("\u00a7c[NoPacketKick] \u00a7fBlocked server resource pack prompt.");
            }
            packetEventInner.cancel();
            return;
        }
        if (((Boolean)this.cancelCloseScreen.getValue()).booleanValue() && packet instanceof CloseScreenS2CPacket) {
            if (MC.mc.currentScreen != null) {
                packetEventInner.cancel();
            }
        }
    }
}
