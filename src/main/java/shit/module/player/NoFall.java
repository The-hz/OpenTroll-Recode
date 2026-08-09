/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import shit.api.Listener7;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.InputTickEvent;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class NoFall
extends Module {
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.Packet));
    private final NumberSetting distance = (NumberSetting)this.registerSetting(new NumberSetting("Distance", 3.0, 0.0, 8.0, 0.1));
    private boolean flag55;
    private int count163;

    public NoFall() {
        super("NoFall", "Prevents taking fall damage.", Category.PLAYER);
    }

    @Override
    public void onDisable() {
        this.flag55 = false;
        this.count163 = 0;
        super.onDisable();
    }

    @Override
    public String getInfo() {
        return ((Mode)((Object)this.mode.getValue())).name();
    }

    @EventHandler
    private void setEvent2Inner8(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (this.mode.getValue() == Mode.Exploit && !MC.mc.player.isOnGround()) {
            this.count163 = MC.mc.player.fallDistance > (double)((Double)this.distance.getValue()).floatValue() ? (this.count163 = this.count163 + 1) : 0;
        }
    }

    @EventHandler
    private void setInputTickEvent2(InputTickEvent inputTickEvent) {
        if (Module.isNotInGame()) {
            return;
        }
        if (this.mode.getValue() == Mode.Exploit && this.flag55) {
            MC.mc.player.input.jump();
            this.flag55 = false;
        }
    }

    @EventHandler
    public void setPacketEventInner215(PacketEvent.PacketEventInner2 packetEventInner2) {
        PlayerMoveC2SPacket playerMoveC2SPacket;
        Packet packet;
        if (Module.isNotInGame()) {
            return;
        }
        ItemStack itemStack = MC.mc.player.getEquippedStack(EquipmentSlot.CHEST);
        if (itemStack != null && itemStack.isOf(Items.ELYTRA)) {
            return;
        }
        if (this.mode.getValue() == Mode.Packet && (packet = packetEventInner2.getPacket()) instanceof PlayerMoveC2SPacket) {
            playerMoveC2SPacket = (PlayerMoveC2SPacket)packet;
            if (MC.mc.player.fallDistance >= (double)((Double)this.distance.getValue()).floatValue()) {
                ((Listener7)playerMoveC2SPacket).setOnGround(true);
            }
        }
        if (this.mode.getValue() == Mode.Exploit && (packet = packetEventInner2.getPacket()) instanceof PlayerMoveC2SPacket) {
            playerMoveC2SPacket = (PlayerMoveC2SPacket)packet;
            if (this.flag55 && this.count163 == 0) {
                packetEventInner2.cancel();
                return;
            }
            if (MC.mc.player.isOnGround() && this.count163 > 0 && !this.flag55) {
                ((Listener7)playerMoveC2SPacket).setY(MC.mc.player.getY() + 10.0);
                this.flag55 = true;
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      Packet, Exploit;

      private Mode() {}



        private static Mode[] getModeArray18() {
            return new Mode[]{Packet, Exploit};
        }
    
   }
}

