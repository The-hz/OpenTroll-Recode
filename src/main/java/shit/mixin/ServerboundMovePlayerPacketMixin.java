/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import shit.api.PlayerMovePacketAccessor;

@Environment(value=EnvType.CLIENT)
@Mixin(value={PlayerMoveC2SPacket.class})
public class ServerboundMovePlayerPacketMixin
implements PlayerMovePacketAccessor {
    @Shadow
    @Mutable
    protected boolean onGround;
    @Shadow
    @Mutable
    protected double y;

    @Override
    public void setOnGround(boolean bl) {
        this.onGround = bl;
    }

    @Override
    public void setY(double d) {
        this.y = d;
    }
}

