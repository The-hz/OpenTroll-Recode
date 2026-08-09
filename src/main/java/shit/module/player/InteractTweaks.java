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
import shit.event.TickEvent;
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
        this.noEntityTrace = (BooleanSetting)this.registerSetting(new BooleanSetting("NoEntityTrace", true));
        this.onlyPickaxe = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyPickaxe", true));
        this.multiTask = (BooleanSetting)this.registerSetting(new BooleanSetting("MultiTask", true));
        this.respawn = (BooleanSetting)this.registerSetting(new BooleanSetting("Respawn", true));
        this.ignoreBedrock = (BooleanSetting)this.registerSetting(new BooleanSetting("IgnoreBedrock", false));
        this.noMineAbort = (BooleanSetting)this.registerSetting(new BooleanSetting("NoMineAbort", false));
        this.noMineReset = (BooleanSetting)this.registerSetting(new BooleanSetting("NoMineReset", false));
        this.noMineDelay = (BooleanSetting)this.registerSetting(new BooleanSetting("NoMineDelay", false));
        this.noInteract = (BooleanSetting)this.registerSetting(new BooleanSetting("NoInteract", false));
        this.switchEat = (BooleanSetting)this.registerSetting(new BooleanSetting("SwitchEat", false));
        this.sword = (BooleanSetting)this.registerSetting(new BooleanSetting("Sword", true));
        this.pickaxe = (BooleanSetting)this.registerSetting(new BooleanSetting("Pickaxe", true));
        this.crystal = (BooleanSetting)this.registerSetting(new BooleanSetting("Crystal", true));
        this.totem = (BooleanSetting)this.registerSetting(new BooleanSetting("Totem", true));
        this.reach = (BooleanSetting)this.registerSetting(new BooleanSetting("Reach", false));
        this.blockRange = (NumberSetting)this.registerSetting(new NumberSetting("BlockRange", 5.0, 0.0, 15.0, 0.1));
        this.entityRange = (NumberSetting)this.registerSetting(new NumberSetting("EntityRange", 5.0, 0.0, 15.0, 0.1));
        this.useDelay = (NumberSetting)this.registerSetting(new NumberSetting("UseDelay", 4.0, 0.0, 4.0, 1.0));
        this.autoGapple = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoGapple", false));
        this.health = (NumberSetting)this.registerSetting(new NumberSetting("Health", 10.0, 1.0, 20.0, 0.5));
        this.offhand = (BooleanSetting)this.registerSetting(new BooleanSetting("Offhand", true));
        this.cooldown = (NumberSetting)this.registerSetting(new NumberSetting("Cooldown", 20.0, 0.0, 60.0, 1.0));
        this.timeout = (NumberSetting)this.registerSetting(new NumberSetting("Timeout", 60.0, 30.0, 100.0, 1.0));
        this.effectWait = (NumberSetting)this.registerSetting(new NumberSetting("EffectWait", 5.0, 0.0, 20.0, 1.0));
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
    public void onDisable() {
        block6: {
            block5: {
                boolean bl = false;
                if (this.flag155) break block5;
                if (!this.flag163) break block6;
            }
            this.setFlag4(false);
        }
        if (this.flag99) {
            if (MC.mc.player != null) {
                MC.mc.player.getInventory().setSelectedSlot(this.count164);
                this.flag99 = false;
            }
        }
        this.count60 = 0;
    }

    @EventHandler
    private void setEvent2Inner60(TickEvent.PreTick event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (this.count60 > 0) {
            --this.count60;
        }
        if (((Boolean)this.respawn.getValue()).booleanValue() && MC.mc.currentScreen instanceof DeathScreen) {
            MC.mc.player.requestRespawn();
            MC.mc.setScreen(null);
        }
        int n = 4 - this.useDelay.getInt();
        if (((MinecraftAccessor)MC.mc).getInt82() <= n) {
            ((MinecraftAccessor)MC.mc).setInt15(0);
        }
        if (this.isSet11()) {
            return;
        }
        this.m944();
    }

    @EventHandler
    private void setPacketEventInner22(PacketEvent.PacketEventInner2 packetEventInner2) {
        Packet packet;
        if (Module.isNotInGame()) {
            return;
        }
        if (((Boolean)this.noInteract.getValue()).booleanValue() && (packet = packetEventInner2.getPacket()) instanceof PlayerInteractBlockC2SPacket) {
            PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket = (PlayerInteractBlockC2SPacket)packet;
            BlockPos blockPos = playerInteractBlockC2SPacket.getBlockHitResult().getBlockPos();
            Block block = MC.mc.world.getBlockState(blockPos).getBlock();
            if (!MC.mc.player.isSneaking() && (block instanceof ChestBlock || block instanceof EnderChestBlock || block instanceof ShulkerBoxBlock || block instanceof AnvilBlock)) {
                packetEventInner2.cancel();
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet11() {
        int n = AutoArmor.getSwitchFlag();
        int n2 = ((Boolean)this.autoGapple.getValue()).booleanValue() ? 1 : 0;
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
                if (n3 < this.effectWait.getInt()) return 1 != 0;
                this.flag163 = false;
                float f = MC.mc.player.getHealth() + MC.mc.player.getAbsorptionAmount();
                double d2 = (double)f - (Double)this.health.getValue();
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
        float f = MC.mc.player.getHealth() + MC.mc.player.getAbsorptionAmount();
        double d3 = (double)f - (Double)this.health.getValue();
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
        this.flag14 = MC.mc.player.hasStatusEffect(StatusEffects.ABSORPTION);
        this.value152 = MC.mc.player.getAbsorptionAmount();
        this.flag177 = MC.mc.options.useKey.isPressed();
        this.count173 = 0;
        if (((Boolean)this.offhand.getValue()).booleanValue() && this.m499(MC.mc.player.getOffHandStack().getItem())) {
            this.flag155 = true;
            this.count70 = -1;
            MC.mc.options.useKey.setPressed(true);
            MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, Hand.OFF_HAND);
            return true;
        }
        if (this.m499(MC.mc.player.getMainHandStack().getItem())) {
            this.flag155 = true;
            this.count70 = -1;
            MC.mc.options.useKey.setPressed(true);
            MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, Hand.MAIN_HAND);
            return true;
        }
        int var3 = this.getInt14();
        if (var3 == -1) {
            return false;
        }
        this.count70 = MC.mc.player.getInventory().getSelectedSlot();
        MC.mc.player.getInventory().setSelectedSlot(var3);
        this.flag155 = true;
        MC.mc.options.useKey.setPressed(true);
        MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, Hand.MAIN_HAND);
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
            MC.mc.options.useKey.setPressed(false);
            if (this.count70 != -1 && this.count70 != MC.mc.player.getInventory().getSelectedSlot()) {
                MC.mc.player.getInventory().setSelectedSlot(this.count70);
                this.count70 = -1;
            }
        } else if (this.count173 >= this.timeout.getInt()) {
            this.setFlag4(true);
        } else {
            boolean var3 = this.m499(MC.mc.player.getMainHandStack().getItem());
            boolean var4 = ((Boolean)this.offhand.getValue()).booleanValue() && this.m499(MC.mc.player.getOffHandStack().getItem());
            if (!var3 && !var4) {
                int var5 = this.getInt14();
                if (var5 == -1) {
                    this.setFlag4(false);
                    return;
                }
                MC.mc.player.getInventory().setSelectedSlot(var5);
            }
            MC.mc.options.useKey.setPressed(true);
            if (!MC.mc.player.isUsingItem()) {
                Hand var6 = var4 ? Hand.OFF_HAND : Hand.MAIN_HAND;
                MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, var6);
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
        boolean var3 = MC.mc.player.hasStatusEffect(StatusEffects.ABSORPTION);
        float var4 = MC.mc.player.getAbsorptionAmount();
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
            this.count60 = this.cooldown.getInt();
        }
        if (this.count70 != -1 && MC.mc.player != null) {
            if (this.count70 != MC.mc.player.getInventory().getSelectedSlot()) {
                MC.mc.player.getInventory().setSelectedSlot(this.count70);
            }
        }
        if (!this.flag177) {
            MC.mc.options.useKey.setPressed(false);
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
            int n2 = AutoArmor.getSwitchFlag();
            for (int i = 0; i < 9; ++i) {
                n = this.m499(MC.mc.player.getInventory().getStack(i).getItem()) ? 1 : 0;
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
        int n = AutoArmor.getSwitchFlag();
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
        if (((Boolean)this.offhand.getValue()).booleanValue() && this.m499(MC.mc.player.getOffHandStack().getItem())) {
            return true;
        }
        if (this.m499(MC.mc.player.getMainHandStack().getItem())) {
            return true;
        }
        return this.getInt14() != -1;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private void m944() {
        if (!((Boolean)this.switchEat.getValue()).booleanValue()) {
            if (this.flag99) {
                MC.mc.player.getInventory().setSelectedSlot(this.count164);
                this.flag99 = false;
            }
            return;
        }
        if (!this.flag99) {
            Item var7 = MC.mc.player.getMainHandStack().getItem();
            ItemStack var4 = MC.mc.player.getMainHandStack();
            boolean var5 = var4.isIn(ItemTags.PICKAXES) && ((Boolean)this.pickaxe.getValue()).booleanValue() || var4.isIn(ItemTags.SWORDS) && ((Boolean)this.sword.getValue()).booleanValue() || var7 == Items.END_CRYSTAL && ((Boolean)this.crystal.getValue()).booleanValue() || var7 == Items.TOTEM_OF_UNDYING && ((Boolean)this.totem.getValue()).booleanValue();
            if (!var5) {
                return;
            }
            if (!MC.mc.options.useKey.isPressed()) {
                return;
            }
            if (this.m499(MC.mc.player.getOffHandStack().getItem())) {
                return;
            }
            int var6 = this.getInt14();
            if (var6 == -1) {
                return;
            }
            this.count164 = MC.mc.player.getInventory().getSelectedSlot();
            this.flag99 = true;
            MC.mc.player.getInventory().setSelectedSlot(var6);
            return;
        }
        if (MC.mc.options.useKey.isPressed()) {
            if (this.m499(MC.mc.player.getMainHandStack().getItem())) {
                return;
            }
            int var3 = this.getInt14();
            if (var3 != -1) {
                MC.mc.player.getInventory().setSelectedSlot(var3);
            } else {
                MC.mc.player.getInventory().setSelectedSlot(this.count164);
                this.flag99 = false;
            }
            return;
        }
        MC.mc.player.getInventory().setSelectedSlot(this.count164);
        this.flag99 = false;
    }

    @Override
    public String getInfo() {
        block8: {
            int n;
            block7: {
                int n2 = AutoArmor.getSwitchFlag();
                n = this.flag155 ? 1 : 0;
                if (n2 != 0) {
                    if (n != 0) {
                        return "Eating " + this.count173 + "t";
                    }
                    n = this.flag163 ? 1 : 0;
                }
                if (n2 != 0) {
                    if (n != 0) {
                        return "Wait " + this.count110 + "/" + this.effectWait.getInt();
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
