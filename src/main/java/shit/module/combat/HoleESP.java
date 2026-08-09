/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import shit.event.Event2;
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
public class HoleESP
extends Module {
    private final BooleanSetting obbyHole = (BooleanSetting)this.registerSetting(new BooleanSetting("ObbyHole", true));
    private final BooleanSetting _2BlocksHole = (BooleanSetting)this.registerSetting(new BooleanSetting("2BlocksHole", true));
    private final BooleanSetting _4BlocksHole = (BooleanSetting)this.registerSetting(new BooleanSetting("4BlocksHole", true));
    private final BooleanSetting trappedHole = (BooleanSetting)this.registerSetting(new BooleanSetting("TrappedHole", true));
    private final ColorSetting bedrockColor = (ColorSetting)this.registerSetting(new ColorSetting("BedrockColor", -1440743649));
    private final ColorSetting obbyColor = (ColorSetting)this.registerSetting(new ColorSetting("ObbyColor", -1426063585));
    private final ColorSetting _2BlocksColor = (ColorSetting)this.registerSetting(new ColorSetting("2BlocksColor", -1426096353));
    private final ColorSetting _4BlocksColor = (ColorSetting)this.registerSetting(new ColorSetting("4BlocksColor", -1426096353));
    private final ColorSetting trappedColor = (ColorSetting)this.registerSetting(new ColorSetting("TrappedColor", -1426120929));
    private final EnumSetting renderMode = (EnumSetting)this.registerSetting(new EnumSetting("RenderMode", RenderMode.Glow));
    private final BooleanSetting filled = (BooleanSetting)this.registerSetting(new BooleanSetting("Filled", true));
    private final BooleanSetting outline = (BooleanSetting)this.registerSetting(new BooleanSetting("Outline", true));
    private final NumberSetting filledAlpha = (NumberSetting)this.registerSetting(new NumberSetting("FilledAlpha", 63.0, 0.0, 255.0, 1.0, 1.0, () -> (Boolean)this.filled.getValue(), null, "", false));
    private final NumberSetting outlineAlpha = (NumberSetting)this.registerSetting(new NumberSetting("OutlineAlpha", 255.0, 0.0, 255.0, 1.0, 1.0, () -> (Boolean)this.outline.getValue(), null, "", false));
    private final NumberSetting glowHeight = (NumberSetting)this.registerSetting(new NumberSetting("GlowHeight", 1.0, 0.25, 4.0, 0.25, 0.25, () -> this.renderMode.getValue() == RenderMode.Glow, null, "", false));
    private final NumberSetting range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 16.0, 4.0, 32.0, 1.0));
    private final NumberSetting verticalRange = (NumberSetting)this.registerSetting(new NumberSetting("VerticalRange", 8.0, 4.0, 16.0, 1.0));
    private final BooleanSetting throughWall = (BooleanSetting)this.registerSetting(new BooleanSetting("ThroughWall", true));
    private final List list10 = new ArrayList();
    private int count101;

    public HoleESP() {
        super("HoleESP", "Shows safe holes for crystal PvP.", Category.COMBAT);
    }

    @Override
    public String getInfo() {
        return Integer.toString(this.list10.size());
    }

    @EventHandler
    private void setEvent2Inner45(Event2.Event2Inner event2Inner) {
        if (++this.count101 >= 10) {
            this.count101 = 0;
            this.m383();
        }
    }

    @EventHandler
    private void setRenderLevelEvent12(RenderLevelEvent renderLevelEvent) {
        if (Module.isNotInGame()) {
            return;
        }
        for (Object dataObj : this.list10) {
            Data data = (Data)dataObj;
            Box box = null;
            switch (((RenderMode)((Object)this.renderMode.getValue())).ordinal()) {
                default: {
                    throw new MatchException(null, null);
                }
                case 1: {
                    Box box2 = new Box(data.box4.minX, data.box4.minY, data.box4.minZ, data.box4.maxX, data.box4.minY + 0.02, data.box4.maxZ);
                    break;
                }
                case 3: {
                    Box box2 = new Box(data.box4.minX, data.box4.minY - 1.0, data.box4.minZ, data.box4.maxX, data.box4.minY, data.box4.maxZ);
                    break;
                }
                case 2: {
                    Box box2 = data.box4;
                    break;
                }
                case 0: {
                    Box box2 = box = new Box(data.box4.minX, data.box4.minY, data.box4.minZ, data.box4.maxX, data.box4.minY + (Double)this.glowHeight.getValue(), data.box4.maxZ);
                }
            }
            if (((Boolean)this.filled.getValue()).booleanValue()) {
                EspRenderLayers.m69(renderLevelEvent.getMatrix4f3(), box, this.m827(data.count8, this.filledAlpha.getInt()), (Boolean)this.throughWall.getValue());
            }
            if (!((Boolean)this.outline.getValue()).booleanValue()) continue;
            EspRenderLayers.m688(renderLevelEvent.getMatrix4f3(), box, this.m827(data.count8, this.outlineAlpha.getInt()), (Boolean)this.throughWall.getValue());
        }
        EspRenderLayers.m125();
    }

    private void m383() {
        this.list10.clear();
        Object var2_1 = null;
        if (Module.isNotInGame()) {
            return;
        }
        int n = this.range.getInt();
        int n2 = this.verticalRange.getInt();
        BlockPos blockPos = MC.mc.player.getBlockPos();
        HashSet<BlockPos> hashSet = new HashSet<BlockPos>();
        for (int i = -n; i <= n; ++i) {
            for (int j = -n2; j <= n2; ++j) {
                for (int k = -n; k <= n; ++k) {
                    BlockPos blockPos2 = blockPos.add(i, j, k);
                    if (MC.mc.player.squaredDistanceTo((double)blockPos2.getX() + 0.5, (double)blockPos2.getY() + 0.5, (double)blockPos2.getZ() + 0.5) > (double)(n * n) || hashSet.contains(blockPos2)) continue;
                    Data data = this.m67(blockPos2);
                    if (data != null) {
                        this.list10.add(data);
                        hashSet.add(blockPos2);
                        if (null == null) continue;
                    }
                    Data data2 = this.m704(blockPos2, hashSet);
                    if (data2 != null) {
                        this.list10.add(data2);
                        hashSet.add(blockPos2);
                        hashSet.add(BlockPos.ofFloored((double)(data2.box4.maxX - 0.5), (double)blockPos2.getY(), (double)(data2.box4.maxZ - 0.5)));
                        if (null == null) continue;
                    }
                    Data data3 = this.m100(blockPos2, hashSet);
                    if (data3 == null) continue;
                    this.list10.add(data3);
                    hashSet.add(blockPos2);
                    hashSet.add(blockPos2.east());
                    hashSet.add(blockPos2.south());
                    hashSet.add(blockPos2.east().south());
                    if (null == null) continue;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
    }

    private Data m67(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (!this.m665(blockPos)) {
            return null;
        }
        BlockPos[] blockPosArray = new BlockPos[]{blockPos.down(), blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west()};
        Type type = this.m738(blockPosArray);
        if (type == Type.None) {
            return null;
        }
        if (type == Type.Obby) {
            if (!((Boolean)this.obbyHole.getValue()).booleanValue()) {
                return null;
            }
        }
        boolean bl = !this.m440(blockPos.up(2));
        if (bl) {
            if (!((Boolean)this.trappedHole.getValue()).booleanValue()) {
                return null;
            }
        }
        int n = bl ? (Integer)this.trappedColor.getValue() : (type == Type.Bedrock ? (Integer)this.bedrockColor.getValue() : (Integer)this.obbyColor.getValue());
        return new Data(new Box(blockPos), n);
    }

    private Data m704(Object object, Object object2) {
        Set set;
        BlockPos blockPos;
        block4: {
            block3: {
                blockPos = (BlockPos)object;
                set = (Set)object2;
                Object var6_5 = null;
                if (!((Boolean)this._2BlocksHole.getValue()).booleanValue()) break block3;
                if (this.m665(blockPos)) break block4;
            }
            return null;
        }
        for (BlockPos blockPos2 : new BlockPos[]{blockPos.east(), blockPos.south()}) {
            Box box;
            if (set.contains(blockPos2)) continue;
            if (!this.m665(blockPos2) || !this.m480(box = this.m160(blockPos, blockPos2))) continue;
            return new Data(box, (Integer)this._2BlocksColor.getValue());
        }
        return null;
    }

    private Data m100(Object object, Object object2) {
        BlockPos blockPos;
        block10: {
            block9: {
                BlockPos blockPos2;
                BlockPos blockPos3;
                BlockPos blockPos4;
                BlockPos blockPos5;
                block8: {
                    block7: {
                        blockPos = (BlockPos)object;
                        Set set = (Set)object2;
                        Object var6_5 = null;
                        if (!((Boolean)this._4BlocksHole.getValue()).booleanValue()) {
                            return null;
                        }
                        blockPos5 = blockPos;
                        blockPos4 = blockPos.east();
                        blockPos3 = blockPos.south();
                        blockPos2 = blockPos.east().south();
                        if (set.contains(blockPos4)) break block7;
                        if (set.contains(blockPos3)) break block7;
                        if (!set.contains(blockPos2)) break block8;
                    }
                    return null;
                }
                if (!this.m665(blockPos5)) break block9;
                if (!this.m665(blockPos4)) break block9;
                if (this.m665(blockPos3) && this.m665(blockPos2)) break block10;
            }
            return null;
        }
        Box box = new Box((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), (double)blockPos.getX() + 2.0, (double)blockPos.getY() + 1.0, (double)blockPos.getZ() + 2.0);
        return this.m480(box) ? new Data(box, (Integer)this._4BlocksColor.getValue()) : null;
    }

    private boolean m480(Object object) {
        int n;
        Box box = (Box)object;
        int n2 = (int)Math.floor(box.minX);
        int n3 = (int)Math.ceil(box.maxX) - 1;
        int n4 = (int)Math.floor(box.minZ);
        int n5 = (int)Math.ceil(box.maxZ) - 1;
        int n6 = (int)Math.floor(box.minY);
        Object var4_9 = null;
        for (n = n2; n <= n3; ++n) {
            for (int i = n4; i <= n5; ++i) {
                if (this.m663(new BlockPos(n, n6 - 1, i))) continue;
                return false;
            }
            if (null == null) continue;
        }
        for (n = n2; n <= n3; ++n) {
            if (!this.m663(new BlockPos(n, n6, n4 - 1))) {
                return false;
            }
            if (this.m663(new BlockPos(n, n6, n5 + 1))) continue;
            return false;
        }
        for (n = n4; n <= n5; ++n) {
            if (!this.m663(new BlockPos(n2 - 1, n6, n))) {
                return false;
            }
            if (this.m663(new BlockPos(n3 + 1, n6, n))) continue;
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m665(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (!this.m440(blockPos)) return false;
        if (!this.m440(blockPos.up())) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m440(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (MC.mc.world == null) return false;
        if (!MC.mc.world.getBlockState(blockPos).isAir()) return false;
        return true;
    }

    private Type m738(Object object) {
        BlockPos[] blockPosArray = (BlockPos[])object;
        boolean bl = true;
        BlockPos[] blockPosArray2 = blockPosArray;
        Object var4_5 = null;
        for (BlockPos blockPos : blockPosArray2) {
            Block block = MC.mc.world.getBlockState(blockPos).getBlock();
            if (!this.m65(block)) {
                return Type.None;
            }
            if (block == Blocks.BEDROCK) continue;
            bl = false;
            if (null == null) continue;
        }
        return bl ? Type.Bedrock : Type.Obby;
    }

    private boolean m663(Object object) {
        BlockPos blockPos = (BlockPos)object;
        return this.m65(MC.mc.world.getBlockState(blockPos).getBlock());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m65(Object object) {
        Block block = (Block)object;
        Object var4_3 = null;
        if (block == Blocks.BEDROCK) return true;
        if (block == Blocks.OBSIDIAN) return true;
        if (block == Blocks.CRYING_OBSIDIAN) return true;
        if (block != Blocks.RESPAWN_ANCHOR) return false;
        return true;
    }

    private Box m160(Object object, Object object2) {
        BlockPos blockPos = (BlockPos)object;
        BlockPos blockPos2 = (BlockPos)object2;
        int n = Math.min(blockPos.getX(), blockPos2.getX());
        int n2 = Math.min(blockPos.getZ(), blockPos2.getZ());
        int n3 = Math.max(blockPos.getX(), blockPos2.getX()) + 1;
        int n4 = Math.max(blockPos.getZ(), blockPos2.getZ()) + 1;
        return new Box((double)n, (double)blockPos.getY(), (double)n2, (double)n3, (double)blockPos.getY() + 1.0, (double)n4);
    }

    private int m827(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        return Math.max(0, Math.min(255, n4)) << 24 | n3 & 0xFFFFFF;
    }

    @Environment(value=EnvType.CLIENT)
    static enum RenderMode {
      Glow, Flat, BlockHole, BlockFloor;

      private RenderMode() {}



        private static RenderMode[] getRenderModeArray() {
            return new RenderMode[]{Glow, Flat, BlockHole, BlockFloor};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    record Data(Box box4, int count8) {
    }

    @Environment(value=EnvType.CLIENT)
    static enum Type {
      None, Obby, Bedrock;

      private Type() {}



        private static Type[] getTypeArray9() {
            return new Type[]{None, Obby, Bedrock};
        }
    
   }
}

