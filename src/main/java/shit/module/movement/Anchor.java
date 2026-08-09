/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import shit.event.EventHandler;
import shit.event.MoveEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.movement.AutoCenter;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Anchor
extends Module {
    private final BooleanSetting autoCenter = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoCenter", true));
    private final BooleanSetting stopY = (BooleanSetting)this.registerSetting(new BooleanSetting("StopY", true));
    private final BooleanSetting pitchTrigger = (BooleanSetting)this.registerSetting(new BooleanSetting("PitchTrigger", true));
    private final NumberSetting pitch = (NumberSetting)this.registerSetting(new NumberSetting("Pitch", 75.0, 0.0, 90.0, 1.0));
    private final NumberSetting yRange = (NumberSetting)this.registerSetting(new NumberSetting("YRange", 3.0, 1.0, 5.0, 1.0));

    public Anchor() {
        super("Anchor", "Stops horizontal movement above small holes.", Category.MOVEMENT);
    }

    @EventHandler
    private void setMoveEvent4(MoveEvent moveEvent) {
        if (Module.isNotInGame()) {
            return;
        }
        if (((Boolean)this.pitchTrigger.getValue()).booleanValue() && (double)MC.mc.player.getPitch() < (Double)this.pitch.getValue()) {
            return;
        }
        BlockPos blockPos = this.getBlockPos9();
        if (blockPos == null) {
            return;
        }
        double d = (double)blockPos.getX() + 0.5;
        double d2 = (double)blockPos.getZ() + 0.5;
        double d3 = Math.hypot(d - MC.mc.player.getX(), d2 - MC.mc.player.getZ());
        if (d3 > 0.18 && ((Boolean)this.autoCenter.getValue()).booleanValue()) {
            if (AutoCenter.INSTANCE != null) {
                AutoCenter.INSTANCE.m47();
            }
            return;
        }
        moveEvent.setDouble2(0.0);
        moveEvent.setDouble(0.0);
        if (((Boolean)this.stopY.getValue()).booleanValue() && MC.mc.player.isOnGround()) {
            moveEvent.setDouble4(-0.08);
        }
    }

    private BlockPos getBlockPos9() {
        BlockPos blockPos = MC.mc.player.getBlockPos();
        Object var2_3 = null;
        for (int i = 0; i <= this.yRange.getInt(); ++i) {
            BlockPos blockPos2 = blockPos.down(i);
            if (!this.m281(blockPos2)) continue;
            return blockPos2;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m281(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        if (!MC.mc.world.getBlockState(blockPos).isAir()) {
            return false;
        }
        if (!MC.mc.world.getBlockState(blockPos.up()).isAir()) {
            return false;
        }
        if (!this.m812(blockPos.down())) return false;
        if (!this.m812(blockPos.north())) return false;
        if (!this.m812(blockPos.south())) return false;
        if (!this.m812(blockPos.east())) return false;
        if (!this.m812(blockPos.west())) return false;
        return true;
    }

    private boolean m812(Object object) {
        BlockPos blockPos = (BlockPos)object;
        Object var4_3 = null;
        return !MC.mc.world.getBlockState(blockPos).getCollisionShape((BlockView)MC.mc.world, blockPos).isEmpty();
    }
}

