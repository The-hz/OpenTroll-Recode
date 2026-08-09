/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import java.util.ArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class VClip
extends Module {
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.Jump));
    private final NumberSetting height = (NumberSetting)this.registerSetting(new NumberSetting("Height", 3.0, 1.0, 5.0, 1.0));
    private final BooleanSetting useWindCharge = (BooleanSetting)this.registerSetting(new BooleanSetting("UseWindCharge", false));
    private final NumberSetting windChargeDelay = (NumberSetting)this.registerSetting(new NumberSetting("WindChargeDelay", 1.0, 0.0, 10.0, 1.0));
    private final BooleanSetting auto = (BooleanSetting)this.registerSetting(new BooleanSetting("Auto", false));
    private final NumberSetting checkRange = (NumberSetting)this.registerSetting(new NumberSetting("CheckRange", 1.0, 1.0, 3.0, 1.0));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 20.0, 0.0, 100.0, 5.0));
    private int count203;
    private boolean flag116;
    private boolean flag82;
    private int count202 = -1;

    public VClip() {
        super("VClip", "Clips the player through the ceiling.", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        this.count203 = 0;
        this.flag116 = false;
        this.flag82 = false;
        this.count202 = -1;
    }

    @Override
    public void onDisable() {
        this.flag116 = false;
        this.flag82 = false;
        this.count202 = -1;
        Client.renderUtil3.restoreSlot();
        Client.mathUtil.resetRotation();
    }

    @Override
    public String getInfo() {
        Object var2_1 = null;
        return (Boolean)this.auto.getValue() != false ? "Auto" : ((Mode)((Object)this.mode.getValue())).name();
    }

    @EventHandler
    private void setEvent2Inner16(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (this.count202 >= 0) {
            if (this.count202-- <= 0) {
                this.m190();
                this.count202 = -1;
            }
            return;
        }
        if (((Boolean)this.auto.getValue()).booleanValue()) {
            if (this.count203++ < this.delay.getInt()) {
                return;
            }
            this.count203 = 0;
            boolean bl = this.isSet41();
            if (bl && !this.flag116) {
                this.m140();
            }
            this.flag116 = bl;
            return;
        }
        if (!this.flag82) {
            this.flag82 = true;
            this.m140();
        }
    }

    private void m140() {
        block3: {
            block2: {
                Object var2_1 = null;
                if (!((Boolean)this.useWindCharge.getValue()).booleanValue()) break block2;
                if (!this.isSet125()) break block2;
                this.count202 = this.windChargeDelay.getInt();
                if (this.count202 > 0) break block3;
                this.m190();
                this.count202 = -1;
                if (null == null) break block3;
            }
            this.m190();
        }
    }

    private boolean isSet125() {
        float f = MC.mc.player.getYaw();
        Client.mathUtil.setRotationSilent(f, 90.0f);
        Object var2_2 = null;
        boolean bl = Client.renderUtil3.switchToItem((java.util.function.Predicate<ItemStack>)this::m588, (Object)ClientSetting.SwitchMode.INVENTORY);
        if (bl) {
            MC.mc.player.networkHandler.sendPacket((Packet)new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 0, f, 90.0f));
            MC.mc.player.swingHand(Hand.MAIN_HAND, false);
        }
        Client.renderUtil3.restoreSlot();
        Client.mathUtil.resetRotation();
        return bl;
    }

    private boolean isSet41() {
        int n;
        BlockPos blockPos = MC.mc.player.getBlockPos();
        Object var2_2 = null;
        int n2 = this.checkRange.getInt();
        if (!this.m674(MC.mc.world.getBlockState(blockPos.up(2)))) {
            return false;
        }
        boolean bl = false;
        for (n = 1; n <= n2; ++n) {
            if (!this.m674(MC.mc.world.getBlockState(blockPos.up(n)))) continue;
            bl = true;
            if (null == null) break;
            if (null == null) continue;
            break;
        }
        if (!bl) {
            return false;
        }
        for (n = n2 + 1; n <= n2 + 2; ++n) {
            if (this.m105(MC.mc.world.getBlockState(blockPos.up(n)))) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m674(Object object) {
        BlockState blockState = (BlockState)object;
        Object var4_3 = null;
        if (blockState.isAir()) return false;
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        }
        if (blockState.blocksMovement()) {
            return true;
        }
        if (blockState.isOf(Blocks.GLASS)) return true;
        if (blockState.isOf(Blocks.GLASS_PANE)) return true;
        if (blockState.isOf(Blocks.ICE)) return true;
        if (blockState.isOf(Blocks.PACKED_ICE)) return true;
        if (blockState.isOf(Blocks.BLUE_ICE)) return true;
        if (!blockState.isOf(Blocks.BARRIER)) return false;
        return true;
    }

    private boolean m105(Object object) {
        BlockState blockState;
        block7: {
            block6: {
                block5: {
                    block4: {
                        blockState = (BlockState)object;
                        Object var4_3 = null;
                        if (blockState.isAir()) break block4;
                        if (blockState.getFluidState().isEmpty()) break block5;
                    }
                    return true;
                }
                if (blockState.isOf(Blocks.GLASS)) break block6;
                if (blockState.isOf(Blocks.GLASS_PANE)) break block6;
                if (blockState.isOf(Blocks.ICE)) break block6;
                if (blockState.isOf(Blocks.PACKED_ICE)) break block6;
                if (blockState.isOf(Blocks.BLUE_ICE)) break block6;
                if (!blockState.isOf(Blocks.BARRIER)) break block7;
            }
            return false;
        }
        return !blockState.blocksMovement();
    }

    private void m190() {
        this.m298();
        Object var2_1 = null;
        if (!((Boolean)this.auto.getValue()).booleanValue()) {
            this.setEnabled(false);
        }
    }

    private void m298() {
        Object var2_1 = null;
        if (MC.mc.getNetworkHandler() == null) {
            return;
        }
        switch (((Mode)((Object)this.mode.getValue())).ordinal()) {
            case 0: {
                this.m1043();
                if (null == null) break;
            }
            case 1: {
                this.m458();
                if (null == null) break;
            }
            case 2: {
                this.m81();
                if (null == null) break;
            }
            case 3: {
                this.m224();
                if (null == null) break;
            }
            case 4: {
                this.m785();
                break;
            }
        }
    }

    private void m1043() {
        double d = MC.mc.player.getX();
        double d2 = MC.mc.player.getZ();
        double d3 = Math.round(MC.mc.player.getY());
        boolean bl = MC.mc.player.isOnGround();
        this.m689(d, d3, d2, bl);
        MC.mc.player.setPosition(d, d3 -= 0.005, d2);
        this.m689(d, d3, d2, bl);
        MC.mc.player.setPosition(d, d3 -= 1.5, d2);
        this.m689(d, d3, d2, bl);
    }

    private void m458() {
        this.m456(MC.mc.player.getX(), MC.mc.player.getY() + 3.0, MC.mc.player.getZ());
    }

    private void m81() {
        double d = MC.mc.player.getX();
        double d2 = MC.mc.player.getZ();
        double d3 = MC.mc.player.getY();
        this.m689(d, d3 + 0.4199999868869781, d2, false);
        this.m689(d, d3 + 0.7531999805212017, d2, false);
        this.m456(d, d3 + 1.0, d2);
    }

    private void m224() {
        this.m456(MC.mc.player.getX(), MC.mc.player.getY() + (Double)this.height.getValue(), MC.mc.player.getZ());
    }

    private void m785() {
        BlockPos blockPos = MC.mc.player.getBlockPos();
        BlockPos blockPos2 = blockPos.up(2);
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        Object var2_4 = null;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (i == 0 && j == 0) continue;
                BlockPos blockPos3 = blockPos2.add(i, 0, j);
                if (!this.m807(blockPos3)) continue;
                arrayList.add(blockPos3.up());
                if (null == null) continue;
            }
            if (null == null) continue;
            break;
        }
        if (this.m807(blockPos2)) {
            arrayList.add(blockPos2.up());
        }
        for (BlockPos blockPos4 : arrayList) {
            if (!this.m807(blockPos4)) {
                if (!this.m807(blockPos4.up())) {
                    if (!this.m807(blockPos4.up(2))) {
                        this.m456((double)blockPos4.getX() + 0.5, blockPos4.getY(), (double)blockPos4.getZ() + 0.5);
                        return;
                    }
                }
            }
            if (null == null) continue;
        }
    }

    private void m456(double d, double d2, double d3) {
        double d4 = d;
        double d5 = d2;
        double d6 = d3;
        MC.mc.player.setPosition(d4, d5, d6);
        this.m689(d4, d5, d6, true);
    }

    private void m689(double d, double d2, double d3, boolean bl) {
        double d4 = d;
        double d5 = d2;
        double d6 = d3;
        boolean bl2 = bl;
        MC.mc.getNetworkHandler().sendPacket((Packet)new PlayerMoveC2SPacket.PositionAndOnGround(d4, d5, d6, bl2, MC.mc.player.horizontalCollision));
    }

    private boolean m807(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        return !MC.mc.world.getBlockState(blockPos).isAir();
    }

    private boolean m588(ItemStack itemStack) {
        return itemStack.isOf(Items.WIND_CHARGE);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      Glitch, Teleport, Jump, SameCommand, Tne;

      private Mode() {}



        private static Mode[] getModeArray3() {
            return new Mode[]{Glitch, Teleport, Jump, SameCommand, Tne};
        }
    
   }
}

