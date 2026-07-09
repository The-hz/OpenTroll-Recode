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
    private final NumberSetting delay = (NumberSetting)this.m28(new NumberSetting("Delay", 50.0, 5.0, 200.0, 1.0));
    private final NumberSetting variation = (NumberSetting)this.m28(new NumberSetting("Variation", 25.0, 0.0, 100.0, 1.0));
    private final NumberSetting idleMinutes = (NumberSetting)this.m28(new NumberSetting("IdleMinutes", 0.0, 0.0, 30.0, 1.0));
    private final BooleanSetting autoReply = (BooleanSetting)this.m28(new BooleanSetting("AutoReply", true));
    private final BooleanSetting swing = (BooleanSetting)this.m28(new BooleanSetting("Swing", true));
    private final BooleanSetting jump = (BooleanSetting)this.m28(new BooleanSetting("Jump", true));
    private final BooleanSetting turn = (BooleanSetting)this.m28(new BooleanSetting("Turn", true));
    private final BooleanSetting walk = (BooleanSetting)this.m28(new BooleanSetting("Walk", false));
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
                String string = IRC.getText7();
                this.helper717.m533();
                this.helper741.m533();
                this.count72 = this.delay.getInt50();
                this.count151 = 0;
                String string2 = string;
                if (string2 == null) break block2;
                if (MC.client3.player == null) break block3;
                this.value202 = MC.client3.player.getX();
                this.value184 = MC.client3.player.getY();
            }
            this.value177 = MC.client3.player.getZ();
        }
    }

    @Override
    public void m709() {
        String string = IRC.getText7();
        if (string != null) {
            if (MC.client3.options != null) {
                MC.client3.options.forwardKey.setPressed(false);
            }
            this.count151 = 0;
        }
    }

    @EventHandler
    private void setPacketEventInner36(PacketEvent.PacketEventInner packetEventInner) {
        if (!((Boolean)this.autoReply.getObj()).booleanValue()) {
            return;
        }
        String string = this.m1006(packetEventInner);
        if (string == null) {
            return;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.contains(" whispers") || string2.contains(" tells you") || string2.contains(" -> ")) {
            Util2.setObj14("r I am currently AFK.");
        }
    }

    @EventHandler
    private void setEvent2Inner222(Event2.Event2Inner2 event2Inner2) {
        if (Module.isSet37()) {
            return;
        }
        this.m852();
        if ((Double)this.idleMinutes.getObj() > 0.0 && !this.helper741.m114((Double)this.idleMinutes.getObj() * 60.0)) {
            return;
        }
        if (this.count151 > 0) {
            --this.count151;
            MC.client3.options.forwardKey.setPressed(true);
            if (this.count151 == 0) {
                MC.client3.options.forwardKey.setPressed(false);
            }
        }
        if (!this.helper717.m336((long)this.count72 * 50L)) {
            return;
        }
        this.count72 = this.delay.getInt50() + (this.variation.getInt50() <= 0 ? 0 : this.random3.nextInt(this.variation.getInt50() + 1));
        this.helper717.m533();
        if (((Boolean)this.swing.getObj()).booleanValue()) {
            MC.client3.player.swingHand(Hand.MAIN_HAND);
        }
        if (((Boolean)this.jump.getObj()).booleanValue() && MC.client3.player.isOnGround()) {
            MC.client3.player.jump();
        }
        if (((Boolean)this.turn.getObj()).booleanValue()) {
            MC.client3.player.setYaw(this.random3.nextFloat() * 360.0f - 180.0f);
        }
        if (((Boolean)this.walk.getObj()).booleanValue()) {
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
                        double d2 = Math.abs(MC.client3.player.getX() - this.value202);
                        double d3 = Math.abs(MC.client3.player.getY() - this.value184);
                        double d4 = Math.abs(MC.client3.player.getZ() - this.value177);
                        String string = IRC.getText7();
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
                this.helper741.m533();
            }
            this.value202 = MC.client3.player.getX();
            this.value184 = MC.client3.player.getY();
            antiAFK = this;
        }
        antiAFK.value177 = MC.client3.player.getZ();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet108() {
        String string = IRC.getText7();
        boolean bl = MC.client3.options.forwardKey.isPressed();
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.client3.options.backKey.isPressed();
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.client3.options.leftKey.isPressed();
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.client3.options.rightKey.isPressed();
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.client3.options.jumpKey.isPressed();
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.client3.options.sneakKey.isPressed();
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
                string2 = IRC.getText7();
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

