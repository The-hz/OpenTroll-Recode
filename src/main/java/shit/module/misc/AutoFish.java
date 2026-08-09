/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;
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

@Environment(value=EnvType.CLIENT)
public class AutoFish
extends Module {
    private final BooleanSetting autoCast = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoCast", true));
    private final NumberSetting castDelay = (NumberSetting)this.registerSetting(new NumberSetting("CastDelay", 5.0, 1.0, 20.0, 1.0));
    private final NumberSetting catchDelay = (NumberSetting)this.registerSetting(new NumberSetting("CatchDelay", 300.0, 50.0, 2000.0, 50.0));
    private final NumberSetting recastDelay = (NumberSetting)this.registerSetting(new NumberSetting("RecastDelay", 450.0, 50.0, 2000.0, 50.0));
    private final Helper7 helper742 = new Helper7();
    private Type type9 = Type.Idle;

    public AutoFish() {
        super("AutoFish", "Automatically catches and recasts fishing rods.", Category.MISC);
    }

    @Override
    public void onDisable() {
        this.type9 = Type.Idle;
    }

    @EventHandler
    private void setPacketEventInner9(PacketEvent.PacketEventInner packetEventInner) {
        Packet packet;
        if (Module.isNotInGame() || !((packet = packetEventInner.getPacket()) instanceof PlaySoundS2CPacket)) {
            return;
        }
        PlaySoundS2CPacket playSoundS2CPacket = (PlaySoundS2CPacket)packet;
        if (!this.isSet117() || MC.mc.player.fishHook == null) {
            return;
        }
        if (playSoundS2CPacket.getSound().value() == SoundEvents.ENTITY_FISHING_BOBBER_SPLASH) {
            this.type9 = Type.Catch;
            this.helper742.resetTimer();
        }
    }

    @EventHandler
    private void setEvent2Inner221(Event2.Event2Inner2 event2Inner2) {
        if (Module.isNotInGame()) {
            return;
        }
        if (!this.isSet117()) {
            this.type9 = Type.Idle;
            return;
        }
        FishingBobberEntity fishingBobberEntity = MC.mc.player.fishHook;
        if (fishingBobberEntity == null) {
            if (this.type9 == Type.Recast && this.helper742.hasPassedMs((Double)this.recastDelay.getValue())) {
                this.m732();
                this.type9 = Type.Idle;
                this.helper742.resetTimer();
            } else if (((Boolean)this.autoCast.getValue()).booleanValue() && this.type9 == Type.Idle && this.helper742.hasPassedSeconds((Double)this.castDelay.getValue())) {
                this.m732();
                this.helper742.resetTimer();
            }
            return;
        }
        if (this.type9 == Type.Catch && this.helper742.hasPassedMs((Double)this.catchDelay.getValue())) {
            this.m732();
            this.type9 = Type.Recast;
            this.helper742.resetTimer();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet117() {
        String string = IRC.getText7();
        boolean bl = MC.mc.player.getMainHandStack().isOf(Items.FISHING_ROD);
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.mc.player.getOffHandStack().isOf(Items.FISHING_ROD);
        if (string == null) return bl;
        if (!bl) return false;
        return true;
    }

    private void m732() {
        Hand hand = MC.mc.player.getOffHandStack().isOf(Items.FISHING_ROD) ? Hand.OFF_HAND : Hand.MAIN_HAND;
        MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, hand);
        MC.mc.player.swingHand(hand);
    }

    @Environment(value=EnvType.CLIENT)
    static enum Type {
      Idle, Catch, Recast;

      private Type() {}



        private static Type[] getTypeArray12() {
            return new Type[]{Idle, Catch, Recast};
        }
    
   }
}

