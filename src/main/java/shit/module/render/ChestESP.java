/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.event.RenderLevelEvent;
import shit.module.Category;
import shit.module.Module;
import shit.render.EspRenderLayers;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ChestESP
extends Module {
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.OUTLINE));
    private final ColorSetting color = (ColorSetting)this.registerSetting(new ColorSetting("Color", -1427720118));
    private final NumberSetting range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 64.0, 8.0, 256.0, 8.0));
    private final BooleanSetting throughWall = (BooleanSetting)this.registerSetting(new BooleanSetting("ThroughWall", true));
    private final List list17 = new ArrayList();
    private int count159;

    public ChestESP() {
        super("ChestESP", "Highlights nearby containers.", Category.RENDER);
    }

    @Override
    public String getInfo() {
        return Integer.toString(this.list17.size());
    }

    @EventHandler
    private void setEvent2Inner53(TickEvent.PreTick event2Inner) {
        if (++this.count159 < 20) {
            return;
        }
        this.count159 = 0;
        this.m251();
    }

    @EventHandler
    private void setRenderLevelEvent10(RenderLevelEvent renderLevelEvent) {
        if (Module.isNotInGame() || this.list17.isEmpty()) {
            return;
        }
        for (Object blockPosObj : this.list17) {
            BlockPos blockPos = (BlockPos)blockPosObj;
            Box box = MC.mc.world.getBlockState(blockPos).getOutlineShape((BlockView)MC.mc.world, blockPos).getBoundingBox().offset(blockPos);
            if (box.getLengthX() <= 0.0 || box.getLengthY() <= 0.0 || box.getLengthZ() <= 0.0) {
                box = new Box(blockPos);
            }
            if (this.mode.getValue() == Mode.FILL || this.mode.getValue() == Mode.BOTH) {
                EspRenderLayers.drawBoxFilled(renderLevelEvent.getMatrix4f3(), box, (Integer)this.color.getValue(), (Boolean)this.throughWall.getValue());
            }
            if (this.mode.getValue() != Mode.OUTLINE && this.mode.getValue() != Mode.BOTH) continue;
            EspRenderLayers.drawBoxOutline(renderLevelEvent.getMatrix4f3(), box, (Integer)this.color.getValue(), (Boolean)this.throughWall.getValue());
        }
        EspRenderLayers.drawBuffers();
    }

    private void m251() {
        this.list17.clear();
        Object var2_1 = null;
        if (Module.isNotInGame()) {
            return;
        }
        int n = Math.max(1, (int)Math.ceil((Double)this.range.getValue() / 16.0));
        int n2 = MC.mc.player.getChunkPos().x;
        int n3 = MC.mc.player.getChunkPos().z;
        double d = (Double)this.range.getValue() * (Double)this.range.getValue();
        for (int i = n2 - n; i <= n2 + n; ++i) {
            for (int j = n3 - n; j <= n3 + n; ++j) {
                for (BlockEntity blockEntity : MC.mc.world.getChunk(i, j).getBlockEntities().values()) {
                    BlockPos blockPos = blockEntity.getPos();
                    if (MC.mc.player.squaredDistanceTo((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) > d) continue;
                    if (this.m655(MC.mc.world.getBlockState(blockPos).getBlock())) {
                        this.list17.add(blockPos.toImmutable());
                    }
                    if (null == null) continue;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m655(Object object) {
        Block block = (Block)object;
        Object var4_3 = null;
        if (block instanceof ChestBlock) return true;
        if (block instanceof EnderChestBlock) return true;
        if (block instanceof BarrelBlock) return true;
        if (block instanceof ShulkerBoxBlock) return true;
        if (block instanceof HopperBlock) return true;
        if (block instanceof DispenserBlock) return true;
        if (block instanceof DropperBlock) return true;
        if (!(block instanceof AbstractFurnaceBlock)) return false;
        return true;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      OUTLINE, FILL, BOTH;

      private Mode() {}



        private static Mode[] getModeArray6() {
            return new Mode[]{OUTLINE, FILL, BOTH};
        }
    
   }
}

