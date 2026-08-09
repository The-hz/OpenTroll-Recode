/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.EndCrystalItem;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.util.Hand;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.mixin.MinecraftAccessor;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class FastUse
extends Module {
    private final NumberSetting multiUse = (NumberSetting)this.registerSetting(new NumberSetting("MultiUse", 1.0, 1.0, 10.0, 1.0));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 0.0, 0.0, 10.0, 1.0));
    private final BooleanSetting blocks = (BooleanSetting)this.registerSetting(new BooleanSetting("Blocks", false));
    private final BooleanSetting allItems = (BooleanSetting)this.registerSetting(new BooleanSetting("AllItems", false));
    private final BooleanSetting expBottles = (BooleanSetting)this.registerSetting(new BooleanSetting("ExpBottles", true));
    private final BooleanSetting endCrystals = (BooleanSetting)this.registerSetting(new BooleanSetting("EndCrystals", true));
    private final BooleanSetting fireworks = (BooleanSetting)this.registerSetting(new BooleanSetting("Fireworks", false));
    private final BooleanSetting potions = (BooleanSetting)this.registerSetting(new BooleanSetting("Potions", false));
    private final BooleanSetting bow = (BooleanSetting)this.registerSetting(new BooleanSetting("Bow", true));
    private final NumberSetting bowCharge = (NumberSetting)this.registerSetting(new NumberSetting("BowCharge", 3.0, 0.0, 20.0, 1.0));
    private Hand field32 = Hand.MAIN_HAND;

    public FastUse() {
        super("FastUse", "Reduces item use delay and can repeat use actions.", Category.PLAYER);
    }

    @EventHandler
    private void setPacketEventInner210(PacketEvent.PacketEventInner2 packetEventInner2) {
        Packet packet = packetEventInner2.getPacket();
        if (packet instanceof PlayerInteractItemC2SPacket) {
            PlayerInteractItemC2SPacket playerInteractItemC2SPacket = (PlayerInteractItemC2SPacket)packet;
            this.field32 = playerInteractItemC2SPacket.getHand();
        } else {
            packet = packetEventInner2.getPacket();
            if (packet instanceof PlayerInteractBlockC2SPacket) {
                PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket = (PlayerInteractBlockC2SPacket)packet;
                this.field32 = playerInteractBlockC2SPacket.getHand();
            }
        }
    }

    @EventHandler
    private void setEvent2Inner234(TickEvent.PostTick event2Inner2) {
        if (Module.isNotInGame() || MC.mc.interactionManager == null) {
            return;
        }
        ItemStack itemStack = MC.mc.player.getStackInHand(this.field32);
        if (itemStack.isEmpty() || !this.m746(itemStack)) {
            return;
        }
        ((MinecraftAccessor)MC.mc).setInt15(this.delay.getInt());
        if (((Boolean)this.bow.getValue()).booleanValue() && itemStack.getItem() instanceof BowItem && MC.mc.player.isUsingItem() && MC.mc.player.getItemUseTime() >= this.bowCharge.getInt()) {
            MC.mc.interactionManager.stopUsingItem((PlayerEntity)MC.mc.player);
            MC.mc.player.clearActiveItem();
            return;
        }
        if (!MC.mc.options.useKey.isPressed() || this.multiUse.getInt() <= 1) {
            return;
        }
        for (int i = 1; i < this.multiUse.getInt(); ++i) {
            MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, this.field32);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m746(Object object) {
        ItemStack itemStack = (ItemStack)object;
        boolean bl = false;
        if (((Boolean)this.allItems.getValue()).booleanValue()) {
            if (!(itemStack.getItem() instanceof BlockItem)) return true;
            if ((Boolean)this.blocks.getValue() == false) return false;
            return true;
        }
        if (((Boolean)this.blocks.getValue()).booleanValue()) {
            if (itemStack.getItem() instanceof BlockItem) {
                return true;
            }
        }
        if (((Boolean)this.expBottles.getValue()).booleanValue()) {
            if (itemStack.getItem() instanceof ExperienceBottleItem) {
                return true;
            }
        }
        if (((Boolean)this.endCrystals.getValue()).booleanValue()) {
            if (itemStack.getItem() instanceof EndCrystalItem) {
                return true;
            }
        }
        if (((Boolean)this.fireworks.getValue()).booleanValue()) {
            if (itemStack.getItem() instanceof FireworkRocketItem) {
                return true;
            }
        }
        if (((Boolean)this.potions.getValue()).booleanValue()) {
            if (itemStack.getItem() instanceof ThrowablePotionItem) {
                return true;
            }
        }
        if ((Boolean)this.bow.getValue() == false) return false;
        if (!(itemStack.getItem() instanceof BowItem)) return false;
        return true;
    }
}

