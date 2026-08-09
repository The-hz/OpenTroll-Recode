/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import shit.event.EventHandler;
import shit.event.RenderLevelEvent;
import shit.module.Category;
import shit.module.Module;
import shit.render.EspRenderLayers;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class SelectionHighlight
extends Module {
    private final NumberSetting expand = (NumberSetting)this.registerSetting(new NumberSetting("Expand", 0.002, 0.0, 1.0, 0.001));
    private final BooleanSetting entity = (BooleanSetting)this.registerSetting(new BooleanSetting("Entity", true));
    private final BooleanSetting depth = (BooleanSetting)this.registerSetting(new BooleanSetting("Depth", false));
    private final BooleanSetting filled = (BooleanSetting)this.registerSetting(new BooleanSetting("Filled", true));
    private final BooleanSetting outline = (BooleanSetting)this.registerSetting(new BooleanSetting("Outline", true));
    private final ColorSetting color = (ColorSetting)this.registerSetting(new ColorSetting("Color", -1));
    private final NumberSetting filledAlpha = (NumberSetting)this.registerSetting(new NumberSetting("FilledAlpha", 63.0, 0.0, 255.0, 1.0));
    private final NumberSetting outlineAlpha = (NumberSetting)this.registerSetting(new NumberSetting("OutlineAlpha", 200.0, 0.0, 255.0, 1.0));

    public SelectionHighlight() {
        super("SelectionHighlight", "Highlights the object you are looking at.", Category.RENDER);
    }

    @EventHandler
    private void setRenderLevelEvent7(RenderLevelEvent var1) {
        if (!Module.isNotInGame() && MC.mc.crosshairTarget != null && MC.mc.crosshairTarget.getType() != HitResult.Type.MISS) {
            Box var2 = null;
            if (MC.mc.crosshairTarget instanceof EntityHitResult var3 && (Boolean)this.entity.getValue()) {
                var2 = var3.getEntity().getBoundingBox().expand((Double)this.expand.getValue());
            } else if (MC.mc.crosshairTarget instanceof BlockHitResult var4) {
                var2 = MC.mc.world.getBlockState(var4.getBlockPos()).getOutlineShape(MC.mc.world, var4.getBlockPos()).getBoundingBox().offset(var4.getBlockPos()).expand((Double)this.expand.getValue());
            }
            if (var2 != null) {
                boolean var6 = !(Boolean)this.depth.getValue();
                if ((Boolean)this.filled.getValue()) {
                    EspRenderLayers.drawBoxFilled(var1.getMatrix4f3(), var2, this.m116((Integer)this.color.getValue(), this.filledAlpha.getInt()), var6);
                }
                if ((Boolean)this.outline.getValue()) {
                    EspRenderLayers.drawBoxOutline(var1.getMatrix4f3(), var2, this.m116((Integer)this.color.getValue(), this.outlineAlpha.getInt()), var6);
                }
                EspRenderLayers.drawBuffers();
            }
        }
    }

    private int m116(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        return Math.max(0, Math.min(255, n4)) << 24 | n3 & 0xFFFFFF;
    }
}

