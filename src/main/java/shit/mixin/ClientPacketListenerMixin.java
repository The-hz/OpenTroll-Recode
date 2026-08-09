/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPosition;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.module.exploit.Disabler;
import shit.module.movement.Velocity;
import shit.module.player.NoRotate;

@Environment(value=EnvType.CLIENT)
@Mixin(value={ClientPlayNetworkHandler.class})
public class ClientPacketListenerMixin {
    @Inject(method={"onEntityVelocityUpdate(Lnet/minecraft/network/packet/s2c/play/EntityVelocityUpdateS2CPacket;)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/network/PacketApplyBatcher;)V", shift=At.Shift.AFTER)}, cancellable=true)
    private void m909(EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket, CallbackInfo callbackInfo) {
        Velocity velocity = Velocity.INSTANCE;
        if (velocity == null || !velocity.isSet16()) {
            return;
        }
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient.world == null) {
            return;
        }
        Entity entity = minecraftClient.world.getEntityById(entityVelocityUpdateS2CPacket.getEntityId());
        if (!velocity.m402(entity)) {
            return;
        }
        Vec3d vec3d = velocity.m601(entityVelocityUpdateS2CPacket.getVelocity(), false);
        if (vec3d != null) {
            entity.setVelocityClient(vec3d);
        }
        callbackInfo.cancel();
    }

    @Inject(method={"onExplosion(Lnet/minecraft/network/packet/s2c/play/ExplosionS2CPacket;)V"}, at={@At(value="INVOKE", target="Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V")}, cancellable=true)
    private void m908(ExplosionS2CPacket explosionS2CPacket, CallbackInfo callbackInfo) {
        Velocity velocity = Velocity.INSTANCE;
        if (velocity == null || !velocity.isSet16()) {
            return;
        }
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
        if (clientPlayerEntity == null) {
            return;
        }
        explosionS2CPacket.playerKnockback().ifPresent(vec3d -> {
            Vec3d vec3d2 = velocity.m601(vec3d, true);
            if (vec3d2 != null) {
                clientPlayerEntity.addVelocityInternal(vec3d2);
            }
        });
        callbackInfo.cancel();
    }

    @Inject(method={"onPlayerPositionLook(Lnet/minecraft/network/packet/s2c/play/PlayerPositionLookS2CPacket;)V"}, at={@At(value="INVOKE", target="Lnet/minecraft/network/NetworkThreadUtils;forceMainThread(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;Lnet/minecraft/network/PacketApplyBatcher;)V", shift=At.Shift.AFTER)}, cancellable=true)
    private void m886(PlayerPositionLookS2CPacket playerPositionLookS2CPacket, CallbackInfo callbackInfo) {
        boolean bl;
        Disabler disabler = Disabler.INSTANCE;
        NoRotate noRotate = NoRotate.INSTANCE;
        boolean bl2 = bl = noRotate != null && noRotate.isEnabled();
        if (!bl) {
            boolean bl3 = bl = disabler != null && disabler.isEnabled() && (Boolean)disabler.s2CRotate.getValue() != false;
        }
        if (!bl) {
            return;
        }
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
        if (clientPlayerEntity == null) {
            return;
        }
        callbackInfo.cancel();
        float f = clientPlayerEntity.getYaw();
        float f2 = clientPlayerEntity.getPitch();
        float f3 = clientPlayerEntity.lastYaw;
        float f4 = clientPlayerEntity.lastPitch;
        EntityPosition entityPosition = EntityPosition.fromEntity((Entity)clientPlayerEntity);
        EntityPosition entityPosition2 = EntityPosition.apply((EntityPosition)entityPosition, (EntityPosition)playerPositionLookS2CPacket.change(), (Set)playerPositionLookS2CPacket.relatives());
        if (!clientPlayerEntity.hasVehicle()) {
            EntityPosition entityPosition3 = new EntityPosition(clientPlayerEntity.getLastRenderPos(), Vec3d.ZERO, f3, f4);
            EntityPosition entityPosition4 = EntityPosition.apply((EntityPosition)entityPosition3, (EntityPosition)playerPositionLookS2CPacket.change(), (Set)playerPositionLookS2CPacket.relatives());
            clientPlayerEntity.setPosition(entityPosition2.position());
            clientPlayerEntity.setVelocity(entityPosition2.deltaMovement());
            clientPlayerEntity.setYaw(f);
            clientPlayerEntity.setPitch(f2);
            clientPlayerEntity.setLastPositionAndAngles(entityPosition4.position(), f3, f4);
        }
        boolean bl4 = disabler != null && disabler.isEnabled() && (Boolean)disabler.applyYaw.getValue() != false;
        float f5 = bl4 ? entityPosition2.yaw() : f;
        float f6 = bl4 ? entityPosition2.pitch() : f2;
        ClientPlayNetworkHandler clientPlayNetworkHandler = (ClientPlayNetworkHandler)(Object)this;
        clientPlayNetworkHandler.getConnection().send((Packet)new TeleportConfirmC2SPacket(playerPositionLookS2CPacket.teleportId()));
        clientPlayNetworkHandler.getConnection().send((Packet)new PlayerMoveC2SPacket.Full(clientPlayerEntity.getX(), clientPlayerEntity.getY(), clientPlayerEntity.getZ(), f5, f6, false, false));
    }
}

