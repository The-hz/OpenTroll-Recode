/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import java.util.Locale;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.util.Hand;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class AntiAFK
extends Module {
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 50.0, 5.0, 200.0, 1.0));
    private final NumberSetting variation = (NumberSetting)this.registerSetting(new NumberSetting("Variation", 25.0, 0.0, 100.0, 1.0));
    private final NumberSetting idleMinutes = (NumberSetting)this.registerSetting(new NumberSetting("IdleMinutes", 0.0, 0.0, 30.0, 1.0));
    private final BooleanSetting autoReply = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoReply", true));
    private final BooleanSetting swing = (BooleanSetting)this.registerSetting(new BooleanSetting("Swing", true));
    private final BooleanSetting jump = (BooleanSetting)this.registerSetting(new BooleanSetting("Jump", true));
    private final BooleanSetting turn = (BooleanSetting)this.registerSetting(new BooleanSetting("Turn", true));
    private final BooleanSetting walk = (BooleanSetting)this.registerSetting(new BooleanSetting("Walk", false));
    private final Helper7 helper717 = new Helper7();
    private final Helper7 helper741 = new Helper7();
    private final Random random3 = new Random();
    private int count72 = 50;
    private int count151;
    private double value202;
    private double value184;
    private double value177;

    public AntiAFK() {
        super("AntiAFK", "Performs small actions to avoid AFK kicks.", Category.MISC);
    }

    @Override
    public void onEnable() {
        block3: {
            block2: {
                String string = IRC.getConnectionId();
                this.helper717.resetTimer();
                this.helper741.resetTimer();
                this.count72 = this.delay.getInt();
                this.count151 = 0;
                String string2 = string;
                if (string2 == null) break block2;
                if (MC.mc.player == null) break block3;
                this.value202 = MC.mc.player.getX();
                this.value184 = MC.mc.player.getY();
            }
            this.value177 = MC.mc.player.getZ();
        }
    }

    @Override
    public void onDisable() {
        String string = IRC.getConnectionId();
        if (string != null) {
            if (MC.mc.options != null) {
                MC.mc.options.forwardKey.setPressed(false);
            }
            this.count151 = 0;
        }
    }

    @EventHandler
    private void setPacketEventInner36(PacketEvent.PacketEventInner packetEventInner) {
        if (!((Boolean)this.autoReply.getValue()).booleanValue()) {
            return;
        }
        String string = this.m1006(packetEventInner);
        if (string == null) {
            return;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.contains(" whispers") || string2.contains(" tells you") || string2.contains(" -> ")) {
            Util2.sendChatCommand("r I am currently AFK.");
        }
    }

    @EventHandler
    private void setEvent2Inner222(Event2.Event2Inner2 event2Inner2) {
        if (Module.isNotInGame()) {
            return;
        }
        this.m852();
        if ((Double)this.idleMinutes.getValue() > 0.0 && !this.helper741.hasPassedSeconds((Double)this.idleMinutes.getValue() * 60.0)) {
            return;
        }
        if (this.count151 > 0) {
            --this.count151;
            MC.mc.options.forwardKey.setPressed(true);
            if (this.count151 == 0) {
                MC.mc.options.forwardKey.setPressed(false);
            }
        }
        if (!this.helper717.hasPassedMillis((long)this.count72 * 50L)) {
            return;
        }
        this.count72 = this.delay.getInt() + (this.variation.getInt() <= 0 ? 0 : this.random3.nextInt(this.variation.getInt() + 1));
        this.helper717.resetTimer();
        if (((Boolean)this.swing.getValue()).booleanValue()) {
            MC.mc.player.swingHand(Hand.MAIN_HAND);
        }
        if (((Boolean)this.jump.getValue()).booleanValue() && MC.mc.player.isOnGround()) {
            MC.mc.player.jump();
        }
        if (((Boolean)this.turn.getValue()).booleanValue()) {
            MC.mc.player.setYaw(this.random3.nextFloat() * 360.0f - 180.0f);
        }
        if (((Boolean)this.walk.getValue()).booleanValue()) {
            this.count151 = 10 + this.random3.nextInt(30);
        }
    }

    private void m852() {
        AntiAFK antiAFK;
        block4: {
            block5: {
                block3: {
                    double d;
                    block2: {
                        double d2 = Math.abs(MC.mc.player.getX() - this.value202);
                        double d3 = Math.abs(MC.mc.player.getY() - this.value184);
                        double d4 = Math.abs(MC.mc.player.getZ() - this.value177);
                        String string = IRC.getConnectionId();
                        double d5 = d2 + d3 + d4 - 0.01;
                        d = d5 == 0.0 ? 0 : (d5 > 0.0 ? 1 : -1);
                        if (string == null) break block2;
                        if (d > 0) break block3;
                        antiAFK = this;
                        if (string == null) break block4;
                        d = antiAFK.isSet108() ? 1.0 : 0.0;
                    }
                    if (d == 0.0) break block5;
                }
                this.helper741.resetTimer();
            }
            this.value202 = MC.mc.player.getX();
            this.value184 = MC.mc.player.getY();
            antiAFK = this;
        }
        antiAFK.value177 = MC.mc.player.getZ();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet108() {
        String string = IRC.getConnectionId();
        boolean bl = MC.mc.options.forwardKey.isPressed();
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.mc.options.backKey.isPressed();
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.mc.options.leftKey.isPressed();
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.mc.options.rightKey.isPressed();
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.mc.options.jumpKey.isPressed();
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.mc.options.sneakKey.isPressed();
        if (string == null) return bl;
        if (!bl) return false;
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private String m1006(Object object) {
        String string;
        ChatMessageS2CPacket chatMessageS2CPacket;
        Packet packet;
        String string2;
        block7: {
            boolean bl;
            Packet packet2;
            block6: {
                PacketEvent.PacketEventInner packetEventInner = (PacketEvent.PacketEventInner)object;
                packet2 = packetEventInner.getPacket();
                string2 = IRC.getConnectionId();
                bl = packet2 instanceof GameMessageS2CPacket;
                if (string2 == null) break block6;
                if (bl) {
                    GameMessageS2CPacket gameMessageS2CPacket = (GameMessageS2CPacket)packet2;
                    return gameMessageS2CPacket.content().getString();
                }
                packet = packet2 = packetEventInner.getPacket();
                if (string2 == null) break block7;
                bl = packet instanceof ChatMessageS2CPacket;
            }
            if (!bl) return null;
            packet = packet2;
        }
        ChatMessageS2CPacket chatMessageS2CPacket2 = chatMessageS2CPacket = (ChatMessageS2CPacket)packet;
        if (string2 != null) {
            if (chatMessageS2CPacket2.unsignedContent() != null) {
                string = chatMessageS2CPacket.unsignedContent().getString();
                return string;
            }
            chatMessageS2CPacket2 = chatMessageS2CPacket;
        }
        string = chatMessageS2CPacket2.body().content();
        return string;
    }
}

