/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.block.AnvilBlock
 *  net.minecraft.block.Block
 *  net.minecraft.block.ChestBlock
 *  net.minecraft.block.EnderChestBlock
 *  net.minecraft.block.ShulkerBoxBlock
 *  net.minecraft.client.gui.screen.DeathScreen
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Item
 *  net.minecraft.item.Items
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket
 *  net.minecraft.registry.tag.ItemTags
 *  net.minecraft.util.Hand
 *  net.minecraft.util.math.BlockPos
 */
package shit.module.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.mixin.MinecraftAccessor;
import shit.module.Category;
import shit.module.Module;
import shit.module.player.AutoArmor;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class InteractTweaks
extends Module {
    public static InteractTweaks INSTANCE;
    public final BooleanSetting noEntityTrace;
    public final BooleanSetting onlyPickaxe;
    public final BooleanSetting multiTask;
    public final BooleanSetting respawn;
    public final BooleanSetting ignoreBedrock;
    private final BooleanSetting noMineAbort;
    private final BooleanSetting noMineReset;
    private final BooleanSetting noMineDelay;
    private final BooleanSetting noInteract;
    public final BooleanSetting switchEat;
    public final BooleanSetting sword;
    public final BooleanSetting pickaxe;
    public final BooleanSetting crystal;
    public final BooleanSetting totem;
    private final BooleanSetting reach;
    public final NumberSetting blockRange;
    public final NumberSetting entityRange;
    private final NumberSetting useDelay;
    private final BooleanSetting autoGapple;
    private final NumberSetting health;
    private final BooleanSetting offhand;
    private final NumberSetting cooldown;
    private final NumberSetting timeout;
    private final NumberSetting effectWait;
    private boolean flag99;
    private int count164;
    private boolean flag155;
    private boolean flag163;
    private int count70;
    private boolean flag177;
    private int count60;
    private int count173;
    private int count110;
    private boolean flag14;
    private float value152;

        public InteractTweaks() {
        super("InteractTweaks", "Various interaction tweaks.", Category.PLAYER);
        this.noEntityTrace = (BooleanSetting)this.m28(new BooleanSetting("NoEntityTrace", true));
        this.onlyPickaxe = (BooleanSetting)this.m28(new BooleanSetting("OnlyPickaxe", true));
        this.multiTask = (BooleanSetting)this.m28(new BooleanSetting("MultiTask", true));
        this.respawn = (BooleanSetting)this.m28(new BooleanSetting("Respawn", true));
        this.ignoreBedrock = (BooleanSetting)this.m28(new BooleanSetting("IgnoreBedrock", false));
        this.noMineAbort = (BooleanSetting)this.m28(new BooleanSetting("NoMineAbort", false));
        this.noMineReset = (BooleanSetting)this.m28(new BooleanSetting("NoMineReset", false));
        this.noMineDelay = (BooleanSetting)this.m28(new BooleanSetting("NoMineDelay", false));
        this.noInteract = (BooleanSetting)this.m28(new BooleanSetting("NoInteract", false));
        this.switchEat = (BooleanSetting)this.m28(new BooleanSetting("SwitchEat", false));
        this.sword = (BooleanSetting)this.m28(new BooleanSetting("Sword", true));
        this.pickaxe = (BooleanSetting)this.m28(new BooleanSetting("Pickaxe", true));
        this.crystal = (BooleanSetting)this.m28(new BooleanSetting("Crystal", true));
        this.totem = (BooleanSetting)this.m28(new BooleanSetting("Totem", true));
        this.reach = (BooleanSetting)this.m28(new BooleanSetting("Reach", false));
        this.blockRange = (NumberSetting)this.m28(new NumberSetting("BlockRange", 5.0, 0.0, 15.0, 0.1));
        this.entityRange = (NumberSetting)this.m28(new NumberSetting("EntityRange", 5.0, 0.0, 15.0, 0.1));
        this.useDelay = (NumberSetting)this.m28(new NumberSetting("UseDelay", 4.0, 0.0, 4.0, 1.0));
        this.autoGapple = (BooleanSetting)this.m28(new BooleanSetting("AutoGapple", false));
        this.health = (NumberSetting)this.m28(new NumberSetting("Health", 10.0, 1.0, 20.0, 0.5));
        this.offhand = (BooleanSetting)this.m28(new BooleanSetting("Offhand", true));
        this.cooldown = (NumberSetting)this.m28(new NumberSetting("Cooldown", 20.0, 0.0, 60.0, 1.0));
        this.timeout = (NumberSetting)this.m28(new NumberSetting("Timeout", 60.0, 30.0, 100.0, 1.0));
        this.effectWait = (NumberSetting)this.m28(new NumberSetting("EffectWait", 5.0, 0.0, 20.0, 1.0));
        this.flag99 = false;
        this.count164 = 0;
        this.flag155 = false;
        this.flag163 = false;
        this.count70 = -1;
        this.flag177 = false;
        this.count60 = 0;
        this.count173 = 0;
        this.count110 = 0;
        this.flag14 = false;
        this.value152 = 0.0f;
    }

    @Override
    public void m709() {
        block6: {
            block5: {
                boolean bl = false;
                if (this.flag155) break block5;
                if (!this.flag163) break block6;
            }
            this.setFlag4(false);
        }
        if (this.flag99) {
            if (MC.client3.player != null) {
                MC.client3.player.getInventory().setSelectedSlot(this.count164);
                this.flag99 = false;
            }
        }
        this.count60 = 0;
    }

    @EventHandler
    private void setEvent2Inner60(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (this.count60 > 0) {
            --this.count60;
        }
        if (((Boolean)this.respawn.getObj()).booleanValue() && MC.client3.currentScreen instanceof DeathScreen) {
            MC.client3.player.requestRespawn();
            MC.client3.setScreen(null);
        }
        int n = 4 - this.useDelay.getInt50();
        if (((MinecraftAccessor)MC.client3).getInt82() <= n) {
            ((MinecraftAccessor)MC.client3).setInt15(0);
        }
        if (this.isSet11()) {
            return;
        }
        this.m944();
    }

    @EventHandler
    private void setPacketEventInner22(PacketEvent.PacketEventInner2 packetEventInner2) {
        Packet packet;
        if (Module.isSet37()) {
            return;
        }
        if (((Boolean)this.noInteract.getObj()).booleanValue() && (packet = packetEventInner2.getPacket()) instanceof PlayerInteractBlockC2SPacket) {
            PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket = (PlayerInteractBlockC2SPacket)packet;
            BlockPos blockPos = playerInteractBlockC2SPacket.getBlockHitResult().getBlockPos();
            Block block = MC.client3.world.getBlockState(blockPos).getBlock();
            if (!MC.client3.player.isSneaking() && (block instanceof ChestBlock || block instanceof EnderChestBlock || block instanceof ShulkerBoxBlock || block instanceof AnvilBlock)) {
                packetEventInner2.m209();
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet11() {
        int n = AutoArmor.getInt66();
        int n2 = ((Boolean)this.autoGapple.getObj()).booleanValue() ? 1 : 0;
        if (n != 0) {
            if (n2 == 0) {
                InteractTweaks interactTweaks = this;
                if (n != 0) {
                    if (!interactTweaks.flag155) {
                        boolean bl = this.flag163;
                        if (n == 0) return bl;
                        if (!bl) return false;
                    }
                    interactTweaks = this;
                }
                interactTweaks.setFlag4(false);
                return false;
            }
            n2 = this.flag163 ? 1 : 0;
        }
        if (n != 0) {
            if (n2 != 0) {
                int n3 = ++this.count110;
                if (n == 0) return n3 != 0;
                if (n3 < this.effectWait.getInt50()) return 1 != 0;
                this.flag163 = false;
                float f = MC.client3.player.getHealth() + MC.client3.player.getAbsorptionAmount();
                double d2 = (double)f - (Double)this.health.getObj();
                d2 = d2 == 0.0 ? 0 : (d2 < 0.0 ? -1 : 1);
                if (n == 0) return d2 != 0;
                if (d2 <= 0) {
                    d2 = this.isSet93() ? 1 : 0;
                    if (n == 0) return d2 != 0;
                    if (d2 != 0) {
                        this.isSet165();
                        return true;
                    }
                }
                this.setFlag4(true);
                d2 = 0.0;
                return d2 != 0;
            }
            n2 = this.flag155 ? 1 : 0;
        }
        if (n != 0) {
            if (n2 != 0) {
                this.m187();
                return true;
            }
            n2 = this.count60;
        }
        if (n == 0) return n2 != 0;
        if (n2 > 0) {
            return 0 != 0;
        }
        float f = MC.client3.player.getHealth() + MC.client3.player.getAbsorptionAmount();
        double d3 = (double)f - (Double)this.health.getObj();
        d3 = d3 == 0.0 ? 0 : (d3 < 0.0 ? -1 : 1);
        if (n == 0) return d3 != 0;
        if (d3 <= 0) {
            d3 = this.isSet93() ? 1 : 0;
            if (n == 0) return d3 != 0;
            if (d3 != 0) {
                return this.isSet165();
            }
        }
        d3 = 0.0;
        return d3 != 0;
    }

    /*
     * Unable to fully structure code
     */
    private boolean isSet165() {
        this.flag14 = MC.client3.player.hasStatusEffect(StatusEffects.ABSORPTION);
        this.value152 = MC.client3.player.getAbsorptionAmount();
        this.flag177 = MC.client3.options.useKey.isPressed();
        this.count173 = 0;
        if (((Boolean)this.offhand.getObj()).booleanValue() && this.m499(MC.client3.player.getOffHandStack().getItem())) {
            this.flag155 = true;
            this.count70 = -1;
            MC.client3.options.useKey.setPressed(true);
            MC.client3.interactionManager.interactItem((PlayerEntity)MC.client3.player, Hand.OFF_HAND);
            return true;
        }
        if (this.m499(MC.client3.player.getMainHandStack().getItem())) {
            this.flag155 = true;
            this.count70 = -1;
            MC.client3.options.useKey.setPressed(true);
            MC.client3.interactionManager.interactItem((PlayerEntity)MC.client3.player, Hand.MAIN_HAND);
            return true;
        }
        int var3 = this.getInt14();
        if (var3 == -1) {
            return false;
        }
        this.count70 = MC.client3.player.getInventory().getSelectedSlot();
        MC.client3.player.getInventory().setSelectedSlot(var3);
        this.flag155 = true;
        MC.client3.options.useKey.setPressed(true);
        MC.client3.interactionManager.interactItem((PlayerEntity)MC.client3.player, Hand.MAIN_HAND);
        return true;
    }

    /*
     * Unable to fully structure code
     */
    private void m187() {
        ++this.count173;
        if (this.isSet27()) {
            this.flag155 = false;
            this.flag163 = true;
            this.count110 = 0;
            MC.client3.options.useKey.setPressed(false);
            if (this.count70 != -1 && this.count70 != MC.client3.player.getInventory().getSelectedSlot()) {
                MC.client3.player.getInventory().setSelectedSlot(this.count70);
                this.count70 = -1;
            }
        } else if (this.count173 >= this.timeout.getInt50()) {
            this.setFlag4(true);
        } else {
            boolean var3 = this.m499(MC.client3.player.getMainHandStack().getItem());
            boolean var4 = ((Boolean)this.offhand.getObj()).booleanValue() && this.m499(MC.client3.player.getOffHandStack().getItem());
            if (!var3 && !var4) {
                int var5 = this.getInt14();
                if (var5 == -1) {
                    this.setFlag4(false);
                    return;
                }
                MC.client3.player.getInventory().setSelectedSlot(var5);
            }
            MC.client3.options.useKey.setPressed(true);
            if (!MC.client3.player.isUsingItem()) {
                Hand var6 = var4 ? Hand.OFF_HAND : Hand.MAIN_HAND;
                MC.client3.interactionManager.interactItem((PlayerEntity)MC.client3.player, var6);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    private boolean isSet27() {
        if (this.count173 < 10) {
            return false;
        }
        boolean var3 = MC.client3.player.hasStatusEffect(StatusEffects.ABSORPTION);
        float var4 = MC.client3.player.getAbsorptionAmount();
        if (!this.flag14 && var3) {
            return true;
        }
        if (this.flag14 && var3 && var4 - this.value152 > 0.0f) {
            return true;
        }
        return false;
    }

    private void setFlag4(boolean bl) {
        boolean bl2 = bl;
        boolean bl3 = false;
        if (bl2) {
            this.count60 = this.cooldown.getInt50();
        }
        if (this.count70 != -1 && MC.client3.player != null) {
            if (this.count70 != MC.client3.player.getInventory().getSelectedSlot()) {
                MC.client3.player.getInventory().setSelectedSlot(this.count70);
            }
        }
        if (!this.flag177) {
            MC.client3.options.useKey.setPressed(false);
        }
        this.flag155 = false;
        this.flag163 = false;
        this.count70 = -1;
        this.flag177 = false;
        this.count173 = 0;
        this.count110 = 0;
        this.flag14 = false;
        this.value152 = 0.0f;
    }

    private int getInt14() {
        int n;
        block3: {
            int n2 = AutoArmor.getInt66();
            for (int i = 0; i < 9; ++i) {
                n = this.m499(MC.client3.player.getInventory().getStack(i).getItem()) ? 1 : 0;
                if (n2 != 0) {
                    int n3 = 0;
                    if (n2 != 0) {
                        if (n == 0) continue;
                        n3 = i;
                    }
                    return n3;
                }
                break block3;
            }
            n = -1;
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m499(Object object) {
        Item item = (Item)object;
        int n = AutoArmor.getInt66();
        Item item2 = item;
        Item item3 = Items.ENCHANTED_GOLDEN_APPLE;
        if (n != 0) {
            if (item2 == item3) return true;
            item2 = item;
            item3 = Items.GOLDEN_APPLE;
        }
        if (item2 != item3) return false;
        return true;
    }

    /*
     * Unable to fully structure code
     */
    private boolean isSet93() {
        if (((Boolean)this.offhand.getObj()).booleanValue() && this.m499(MC.client3.player.getOffHandStack().getItem())) {
            return true;
        }
        if (this.m499(MC.client3.player.getMainHandStack().getItem())) {
            return true;
        }
        return this.getInt14() != -1;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private void m944() {
        if (!((Boolean)this.switchEat.getObj()).booleanValue()) {
            if (this.flag99) {
                MC.client3.player.getInventory().setSelectedSlot(this.count164);
                this.flag99 = false;
            }
            return;
        }
        if (!this.flag99) {
            Item var7 = MC.client3.player.getMainHandStack().getItem();
            ItemStack var4 = MC.client3.player.getMainHandStack();
            boolean var5 = var4.isIn(ItemTags.PICKAXES) && ((Boolean)this.pickaxe.getObj()).booleanValue() || var4.isIn(ItemTags.SWORDS) && ((Boolean)this.sword.getObj()).booleanValue() || var7 == Items.END_CRYSTAL && ((Boolean)this.crystal.getObj()).booleanValue() || var7 == Items.TOTEM_OF_UNDYING && ((Boolean)this.totem.getObj()).booleanValue();
            if (!var5) {
                return;
            }
            if (!MC.client3.options.useKey.isPressed()) {
                return;
            }
            if (this.m499(MC.client3.player.getOffHandStack().getItem())) {
                return;
            }
            int var6 = this.getInt14();
            if (var6 == -1) {
                return;
            }
            this.count164 = MC.client3.player.getInventory().getSelectedSlot();
            this.flag99 = true;
            MC.client3.player.getInventory().setSelectedSlot(var6);
            return;
        }
        if (MC.client3.options.useKey.isPressed()) {
            if (this.m499(MC.client3.player.getMainHandStack().getItem())) {
                return;
            }
            int var3 = this.getInt14();
            if (var3 != -1) {
                MC.client3.player.getInventory().setSelectedSlot(var3);
            } else {
                MC.client3.player.getInventory().setSelectedSlot(this.count164);
                this.flag99 = false;
            }
            return;
        }
        MC.client3.player.getInventory().setSelectedSlot(this.count164);
        this.flag99 = false;
    }

    @Override
    public String getText57() {
        block8: {
            int n;
            block7: {
                int n2 = AutoArmor.getInt66();
                n = this.flag155 ? 1 : 0;
                if (n2 != 0) {
                    if (n != 0) {
                        return "Eating " + this.count173 + "t";
                    }
                    n = this.flag163 ? 1 : 0;
                }
                if (n2 != 0) {
                    if (n != 0) {
                        return "Wait " + this.count110 + "/" + this.effectWait.getInt50();
                    }
                    n = this.count60;
                }
                if (n2 == 0) break block7;
                if (n <= 0) break block8;
                n = this.count60;
            }
            return "CD:" + n;
        }
        return null;
    }
}
