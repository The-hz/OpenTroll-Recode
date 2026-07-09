/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
public class VoidESP
extends Module {
    private final BooleanSetting filled = (BooleanSetting)this.m28(new BooleanSetting("Filled", true));
    private final BooleanSetting outline = (BooleanSetting)this.m28(new BooleanSetting("Outline", true));
    private final ColorSetting color = (ColorSetting)this.m28(new ColorSetting("Color", -7036417));
    private final NumberSetting filledAlpha = (NumberSetting)this.m28(new NumberSetting("FilledAlpha", 127.0, 0.0, 255.0, 1.0));
    private final NumberSetting outlineAlpha = (NumberSetting)this.m28(new NumberSetting("OutlineAlpha", 255.0, 0.0, 255.0, 1.0));
    private final EnumSetting mode = (EnumSetting)this.m28(new EnumSetting("Mode", Mode.BLOCK_HOLE));
    private final NumberSetting range = (NumberSetting)this.m28(new NumberSetting("Range", 8.0, 4.0, 32.0, 1.0));
    private final BooleanSetting throughWall = (BooleanSetting)this.m28(new BooleanSetting("ThroughWall", true));
    private final List list6 = new ArrayList();
    private int count233;

    public VoidESP() {
        super("VoidESP", "Highlights holes leading to the void.", Category.RENDER);
    }

    @Override
    public String getText57() {
        return Integer.toString(this.list6.size());
    }

    @EventHandler
    private void setEvent2Inner46(Event2.Event2Inner event2Inner) {
        if (++this.count233 < 10) {
            return;
        }
        this.count233 = 0;
        this.m583();
    }

    @EventHandler
    private void setRenderLevelEvent11(RenderLevelEvent renderLevelEvent) {
        if (Module.isSet37() || this.list6.isEmpty()) {
            return;
        }
        for (Object o : this.list6) {
            Box box = (Box)o;
            if (((Boolean)this.filled.getObj()).booleanValue()) {
                EspRenderLayers.m69(renderLevelEvent.getMatrix4f3(), box, this.m921((Integer)this.color.getObj(), this.filledAlpha.getInt50()), (Boolean)this.throughWall.getObj());
            }
            if (!((Boolean)this.outline.getObj()).booleanValue()) continue;
            EspRenderLayers.m688(renderLevelEvent.getMatrix4f3(), box, this.m921((Integer)this.color.getObj(), this.outlineAlpha.getInt50()), (Boolean)this.throughWall.getObj());
        }
        EspRenderLayers.m125();
    }

    private void m583() {
        this.list6.clear();
        Object var2_1 = null;
        if (Module.isSet37()) {
            return;
        }
        int n = this.range.getInt50();
        BlockPos blockPos = MC.client3.player.getBlockPos();
        int n2 = n * n;
        for (int i = blockPos.getX() - n; i <= blockPos.getX() + n; ++i) {
            for (int j = blockPos.getZ() - n; j <= blockPos.getZ() + n; ++j) {
                double d = (double)i + 0.5 - MC.client3.player.getX();
                double d2 = (double)j + 0.5 - MC.client3.player.getZ();
                if (d * d + d2 * d2 > (double)n2 || !this.m384(i, j)) continue;
                BlockPos blockPos2 = this.mode.getObj() == Mode.BLOCK_VOID ? new BlockPos(i, -1, j) : new BlockPos(i, 0, j);
                Box box = switch (((Mode)((Object)this.mode.getObj())).ordinal()) {
                    default -> throw new MatchException(null, null);
                    case 2 -> new Box((double)blockPos2.getX(), 0.0, (double)blockPos2.getZ(), (double)blockPos2.getX() + 1.0, 0.02, (double)blockPos2.getZ() + 1.0);
                    case 0, 1 -> new Box(blockPos2);
                };
                this.list6.add(box);
                if (null == null) continue;
            }
            if (null == null) continue;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m384(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        Object var6_5 = null;
        if (!MC.client3.world.getBlockState(new BlockPos(n3, 0, n4)).isAir()) return false;
        if (!MC.client3.world.getBlockState(new BlockPos(n3, 1, n4)).isAir()) return false;
        if (!MC.client3.world.getBlockState(new BlockPos(n3, 2, n4)).isAir()) return false;
        return true;
    }

    private int m921(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        return Math.max(0, Math.min(255, n4)) << 24 | n3 & 0xFFFFFF;
    }

    @Environment(value=EnvType.CLIENT)
    static enum Mode {
      BLOCK_HOLE, BLOCK_VOID, FLAT;

      private Mode() {}



        private static Mode[] getModeArray21() {
            return new Mode[]{BLOCK_HOLE, BLOCK_VOID, FLAT};
        }
    
   }
}

