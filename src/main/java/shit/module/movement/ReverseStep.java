/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.misc.Stopwatch;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ReverseStep
extends Module {
    private final NumberSetting height = (NumberSetting)this.registerSetting(new NumberSetting("Height", 2.0, 0.25, 8.0, 0.1));
    private final NumberSetting speed = (NumberSetting)this.registerSetting(new NumberSetting("Speed", 1.0, 0.1, 8.0, 0.1));
    private final Stopwatch helper731 = new Stopwatch();

    public ReverseStep() {
        super("ReverseStep", "Pulls you down block edges faster.", Category.MOVEMENT);
    }

    @EventHandler
    private void setPacketEventInner10(PacketEvent.PacketEventInner packetEventInner) {
        if (packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket) {
            this.helper731.resetTimer();
        }
    }

    @EventHandler
    private void setEvent2Inner10(TickEvent.PreTick event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (!this.helper731.hasPassedSeconds(3.0)) {
            return;
        }
        if (MC.mc.options.sneakKey.isPressed() || MC.mc.options.jumpKey.isPressed()) {
            return;
        }
        if (MC.mc.player.isGliding() || MC.mc.player.getAbilities().flying || MC.mc.player.isClimbing() || MC.mc.player.isTouchingWater() || MC.mc.player.isInLava()) {
            return;
        }
        if (!MC.mc.player.isOnGround() || MC.mc.player.getVelocity().y > 0.0) {
            return;
        }
        double d = this.getDouble19();
        if (d >= 0.25 && d <= (Double)this.height.getValue()) {
            MC.mc.player.setVelocity(MC.mc.player.getVelocity().x, MC.mc.player.getVelocity().y - (Double)this.speed.getValue(), MC.mc.player.getVelocity().z);
        }
    }

    private double getDouble19() {
        BlockPos blockPos = MC.mc.player.getBlockPos();
        int n = 1;
        Object var2_3 = null;
        while ((double)n <= Math.ceil((Double)this.height.getValue()) + 1.0) {
            BlockPos blockPos2 = blockPos.down(n);
            if (!MC.mc.world.getBlockState(blockPos2).getCollisionShape((BlockView)MC.mc.world, blockPos2).isEmpty()) {
                return MC.mc.player.getY() - ((double)blockPos2.getY() + 1.0);
            }
            ++n;
            if (null == null) continue;
        }
        return Double.MAX_VALUE;
    }
}

