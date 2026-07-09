/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ReverseStep
extends Module {
    private final NumberSetting height = (NumberSetting)this.m28(new NumberSetting("Height", 2.0, 0.25, 8.0, 0.1));
    private final NumberSetting speed = (NumberSetting)this.m28(new NumberSetting("Speed", 1.0, 0.1, 8.0, 0.1));
    private final Helper7 helper731 = new Helper7();

    public ReverseStep() {
        super("ReverseStep", "Pulls you down block edges faster.", Category.MOVEMENT);
    }

    @EventHandler
    private void setPacketEventInner10(PacketEvent.PacketEventInner packetEventInner) {
        if (packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket) {
            this.helper731.m533();
        }
    }

    @EventHandler
    private void setEvent2Inner10(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (!this.helper731.m114(3.0)) {
            return;
        }
        if (MC.client3.options.sneakKey.isPressed() || MC.client3.options.jumpKey.isPressed()) {
            return;
        }
        if (MC.client3.player.isGliding() || MC.client3.player.getAbilities().flying || MC.client3.player.isClimbing() || MC.client3.player.isTouchingWater() || MC.client3.player.isInLava()) {
            return;
        }
        if (!MC.client3.player.isOnGround() || MC.client3.player.getVelocity().y > 0.0) {
            return;
        }
        double d = this.getDouble19();
        if (d >= 0.25 && d <= (Double)this.height.getObj()) {
            MC.client3.player.setVelocity(MC.client3.player.getVelocity().x, MC.client3.player.getVelocity().y - (Double)this.speed.getObj(), MC.client3.player.getVelocity().z);
        }
    }

    private double getDouble19() {
        BlockPos blockPos = MC.client3.player.getBlockPos();
        int n = 1;
        Object var2_3 = null;
        while ((double)n <= Math.ceil((Double)this.height.getObj()) + 1.0) {
            BlockPos blockPos2 = blockPos.down(n);
            if (!MC.client3.world.getBlockState(blockPos2).getCollisionShape((BlockView)MC.client3.world, blockPos2).isEmpty()) {
                return MC.client3.player.getY() - ((double)blockPos2.getY() + 1.0);
            }
            ++n;
            if (null == null) continue;
        }
        return Double.MAX_VALUE;
    }
}

