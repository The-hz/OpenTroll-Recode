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
    private final BooleanSetting autoCast = (BooleanSetting)this.m28(new BooleanSetting("AutoCast", true));
    private final NumberSetting castDelay = (NumberSetting)this.m28(new NumberSetting("CastDelay", 5.0, 1.0, 20.0, 1.0));
    private final NumberSetting catchDelay = (NumberSetting)this.m28(new NumberSetting("CatchDelay", 300.0, 50.0, 2000.0, 50.0));
    private final NumberSetting recastDelay = (NumberSetting)this.m28(new NumberSetting("RecastDelay", 450.0, 50.0, 2000.0, 50.0));
    private final Helper7 helper742 = new Helper7();
    private Type type9 = Type.Idle;

    public AutoFish() {
        super("AutoFish", "Automatically catches and recasts fishing rods.", Category.MISC);
    }

    @Override
    public void m709() {
        this.type9 = Type.Idle;
    }

    @EventHandler
    private void setPacketEventInner9(PacketEvent.PacketEventInner packetEventInner) {
        Packet packet;
        if (Module.isSet37() || !((packet = packetEventInner.getPacket()) instanceof PlaySoundS2CPacket)) {
            return;
        }
        PlaySoundS2CPacket playSoundS2CPacket = (PlaySoundS2CPacket)packet;
        if (!this.isSet117() || MC.client3.player.fishHook == null) {
            return;
        }
        if (playSoundS2CPacket.getSound().value() == SoundEvents.ENTITY_FISHING_BOBBER_SPLASH) {
            this.type9 = Type.Catch;
            this.helper742.m533();
        }
    }

    @EventHandler
    private void setEvent2Inner221(Event2.Event2Inner2 event2Inner2) {
        if (Module.isSet37()) {
            return;
        }
        if (!this.isSet117()) {
            this.type9 = Type.Idle;
            return;
        }
        FishingBobberEntity fishingBobberEntity = MC.client3.player.fishHook;
        if (fishingBobberEntity == null) {
            if (this.type9 == Type.Recast && this.helper742.m432((Double)this.recastDelay.getObj())) {
                this.m732();
                this.type9 = Type.Idle;
                this.helper742.m533();
            } else if (((Boolean)this.autoCast.getObj()).booleanValue() && this.type9 == Type.Idle && this.helper742.m114((Double)this.castDelay.getObj())) {
                this.m732();
                this.helper742.m533();
            }
            return;
        }
        if (this.type9 == Type.Catch && this.helper742.m432((Double)this.catchDelay.getObj())) {
            this.m732();
            this.type9 = Type.Recast;
            this.helper742.m533();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet117() {
        String string = IRC.getText7();
        boolean bl = MC.client3.player.getMainHandStack().isOf(Items.FISHING_ROD);
        if (string == null) return bl;
        if (bl) return true;
        bl = MC.client3.player.getOffHandStack().isOf(Items.FISHING_ROD);
        if (string == null) return bl;
        if (!bl) return false;
        return true;
    }

    private void m732() {
        Hand hand = MC.client3.player.getOffHandStack().isOf(Items.FISHING_ROD) ? Hand.OFF_HAND : Hand.MAIN_HAND;
        MC.client3.interactionManager.interactItem((PlayerEntity)MC.client3.player, hand);
        MC.client3.player.swingHand(hand);
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

