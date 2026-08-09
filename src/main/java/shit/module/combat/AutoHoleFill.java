/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.misc.Stopwatch;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.BlockUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoHoleFill
extends Module {
    private final NumberSetting range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 4.0, 1.0, 6.0, 0.5));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 100.0, 0.0, 1000.0, 10.0));
    private final NumberSetting blocks = (NumberSetting)this.registerSetting(new NumberSetting("Blocks", 1.0, 1.0, 8.0, 1.0));
    private final EnumSetting rotateMode = (EnumSetting)this.registerSetting(new EnumSetting("RotateMode", RotateMode.DEFAULT));
    private final EnumSetting switchMode = (EnumSetting)this.registerSetting(new EnumSetting("SwitchMode", SwitchMode.DEFAULT));
    private final Stopwatch helper710 = new Stopwatch();

    public AutoHoleFill() {
        super("AutoHoleFill", "Fills nearby safe holes with obsidian.", Category.COMBAT);
    }

    @EventHandler
    private void onTick4(TickEvent.PreTick event2Inner) {
        if (Module.isNotInGame() || !this.helper710.hasPassedMs((Double)this.delay.getValue())) {
            return;
        }
        int n = 0;
        BlockPos blockPos = MC.mc.player.getBlockPos();
        int n2 = this.range.getInt();
        for (BlockPos blockPos2 : BlockPos.iterate((BlockPos)blockPos.add(-n2, -1, -n2), (BlockPos)blockPos.add(n2, 1, n2))) {
            if (!this.m505(blockPos2) || !BlockUtil.m1051(blockPos2.toImmutable(), (java.util.function.Predicate<net.minecraft.item.ItemStack>)(itemStack -> {
                BlockItem blockItem;
                Item item = itemStack.getItem();
                Object var1_2 = null;
                return item instanceof BlockItem && (blockItem = (BlockItem)item).getBlock() == Blocks.OBSIDIAN;
            }), (Object)this.getRotateMode12(), ClientSetting.INSTANCE != null ? ClientSetting.INSTANCE.rotateSpeed.getFloat() : 45.0f, (Object)this.getSwitchMode2(), 5.0) || ++n < this.blocks.getInt()) continue;
            this.helper710.resetTimer();
            return;
        }
        if (n > 0) {
            this.helper710.resetTimer();
        }
    }

    private boolean m505(Object object) {
        BlockPos blockPos;
        block7: {
            block6: {
                blockPos = (BlockPos)object;
                Object var4_3 = null;
                if (!MC.mc.world.isAir(blockPos)) break block6;
                if (MC.mc.world.isAir(blockPos.up())) break block7;
            }
            return false;
        }
        if (!this.m804(blockPos.down())) {
            return false;
        }
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (!this.m804(blockPos.offset(direction))) {
                return false;
            }
            if (null == null) continue;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m804(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (MC.mc.world.getBlockState(blockPos).isOf(Blocks.OBSIDIAN)) return true;
        if (!MC.mc.world.getBlockState(blockPos).isOf(Blocks.BEDROCK)) return false;
        return true;
    }

    private ClientSetting.RotateMode getRotateMode12() {
        Object var2_1 = null;
        if (this.rotateMode.getValue() == RotateMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.RotateMode)((Object)ClientSetting.INSTANCE.rotateMode.getValue()) : ClientSetting.RotateMode.NONE;
        }
        return switch (((RotateMode)((Object)this.rotateMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.RotateMode.NONE;
            case 2 -> ClientSetting.RotateMode.SMOOTH;
            case 3 -> ClientSetting.RotateMode.ONTICK;
            case 4 -> ClientSetting.RotateMode.rotateMode;
            default -> ClientSetting.RotateMode.NONE;
        };
    }

    private ClientSetting.SwitchMode getSwitchMode2() {
        Object var2_1 = null;
        if (this.switchMode.getValue() == SwitchMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.SwitchMode)((Object)ClientSetting.INSTANCE.switchMode.getValue()) : ClientSetting.SwitchMode.NORMAL;
        }
        return switch (((SwitchMode)((Object)this.switchMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.SwitchMode.NONE;
            case 2 -> ClientSetting.SwitchMode.NORMAL;
            case 3 -> ClientSetting.SwitchMode.SILENT;
            case 4 -> ClientSetting.SwitchMode.INVENTORY;
            default -> ClientSetting.SwitchMode.NORMAL;
        };
    }

    @Environment(value=EnvType.CLIENT)
    public static enum RotateMode {
      DEFAULT, NONE, SMOOTH, ONTICK, rotateMode3;

      private RotateMode() {}



        private static RotateMode[] getRotateModeArray9() {
            return new RotateMode[]{DEFAULT, NONE, SMOOTH, ONTICK, rotateMode3};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum SwitchMode {
      DEFAULT, NONE, NORMAL, SILENT, INVENTORY;

      private SwitchMode() {}



        private static SwitchMode[] getSwitchModeArray4() {
            return new SwitchMode[]{DEFAULT, NONE, NORMAL, SILENT, INVENTORY};
        }
    
   }
}

