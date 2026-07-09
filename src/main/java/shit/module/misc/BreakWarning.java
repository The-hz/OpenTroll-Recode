/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import shit.Client;
import shit.command.CommandManager;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class BreakWarning
extends Module {
    private final BooleanSetting ignoreFriends = (BooleanSetting)this.m28(new BooleanSetting("IgnoreFriends", true));
    private final EnumSetting burrow = (EnumSetting)this.m28(new EnumSetting("Burrow", BurrowMode.ANY));
    private final EnumSetting surround = (EnumSetting)this.m28(new EnumSetting("Surround", BurrowMode.ANY));
    private final EnumSetting nearby = (EnumSetting)this.m28(new EnumSetting("Nearby", BurrowMode.OFF));
    private final NumberSetting range = (NumberSetting)this.m28(new NumberSetting("Range", 8.0, 1.0, 16.0, 1.0));
    private final Map map3 = new HashMap();

    public BreakWarning() {
        super("BreakWarning", "Warns when nearby players break defensive blocks.", Category.MISC);
    }

    @EventHandler
    private void setPacketEventInner34(PacketEvent.PacketEventInner packetEventInner) {
        PlayerEntity playerEntity;
        Packet packet;
        if (Module.isSet37() || !((packet = packetEventInner.getPacket()) instanceof BlockBreakingProgressS2CPacket)) {
            return;
        }
        BlockBreakingProgressS2CPacket blockBreakingProgressS2CPacket = (BlockBreakingProgressS2CPacket)packet;
        if (blockBreakingProgressS2CPacket.getProgress() < 0) {
            return;
        }
        net.minecraft.entity.Entity entity = MC.client3.world.getEntityById(blockBreakingProgressS2CPacket.getEntityId());
        if (!(entity instanceof PlayerEntity) || (playerEntity = (PlayerEntity)entity) == MC.client3.player) {
            return;
        }
        if (((Boolean)this.ignoreFriends.getObj()).booleanValue() && Client.manager.m258(playerEntity.getName().getString())) {
            return;
        }
        BlockPos blockPos = blockBreakingProgressS2CPacket.getPos();
        Block block = MC.client3.world.getBlockState(blockPos).getBlock();
        if (((BurrowMode)((Object)this.burrow.getObj())).m880(block) && blockPos.equals((Object)MC.client3.player.getBlockPos())) {
            this.m661("burrow", playerEntity, "is breaking your burrow");
            return;
        }
        if (((BurrowMode)((Object)this.surround.getObj())).m880(block) && this.m1004(blockPos)) {
            this.m661("surround:" + blockPos.asLong(), playerEntity, "is breaking your surround");
            return;
        }
        if (((BurrowMode)((Object)this.nearby.getObj())).m880(block) && MC.client3.player.getBlockPos().getSquaredDistance((Vec3i)blockPos) <= (Double)this.range.getObj() * (Double)this.range.getObj()) {
            this.m661("nearby:" + blockPos.asLong(), playerEntity, "is breaking blocks nearby");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m1004(Object object) {
        BlockPos blockPos = (BlockPos)object;
        BlockPos blockPos2 = MC.client3.player.getBlockPos();
        String string = IRC.getText7();
        int n = blockPos.getY();
        int n2 = blockPos2.getY();
        if (string != null) {
            if (n != n2) return 0 != 0;
            n = Math.abs(blockPos.getX() - blockPos2.getX()) + Math.abs(blockPos.getZ() - blockPos2.getZ());
            if (string == null) return n != 0;
            n2 = 1;
        }
        if (n != n2) return 0 != 0;
        return 1 != 0;
    }

    private void m661(Object object, Object object2, Object object3) {
        String string = (String)object;
        PlayerEntity playerEntity = (PlayerEntity)object2;
        String string2 = (String)object3;
        String string3 = string + ":" + String.valueOf(playerEntity.getUuid());
        String string4 = IRC.getText7();
        long l = System.currentTimeMillis();
        if (string4 != null) {
            if (l - (Long)this.map3.getOrDefault(string3, 0L) < 1000L) {
                return;
            }
            this.map3.put(string3, l);
            CommandManager.setObj21(playerEntity.getName().getString() + " " + string2 + ".");
        }
    }

    @Environment(value=EnvType.CLIENT)
    static enum BurrowMode {
      OFF, ANY, OBSIDIAN;

      private BurrowMode() {}



        boolean m880(Object object) {
            Block block = (Block)object;
            return switch (this.ordinal()) {
                default -> throw new MatchException(null, null);
                case 0 -> false;
                case 1 -> true;
                case 2 -> block == Blocks.OBSIDIAN;
            };
        }

        private static BurrowMode[] getBurrowModeArray() {
            return new BurrowMode[]{OFF, ANY, OBSIDIAN};
        }

        private static MatchException a(MatchException matchException) {
            return matchException;
        }
    
   }
}

