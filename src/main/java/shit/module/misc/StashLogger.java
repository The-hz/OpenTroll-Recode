/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import shit.command.CommandManager;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class StashLogger
extends Module {
    private final BooleanSetting chat = (BooleanSetting)this.m28(new BooleanSetting("Chat", true));
    private final BooleanSetting sound = (BooleanSetting)this.m28(new BooleanSetting("Sound", true));
    private final BooleanSetting chests = (BooleanSetting)this.m28(new BooleanSetting("Chests", true));
    private final NumberSetting minChests = (NumberSetting)this.m28(new NumberSetting("MinChests", 5.0, 1.0, 20.0, 1.0));
    private final BooleanSetting shulkers = (BooleanSetting)this.m28(new BooleanSetting("Shulkers", true));
    private final NumberSetting minShulkers = (NumberSetting)this.m28(new NumberSetting("MinShulkers", 1.0, 1.0, 20.0, 1.0));
    private final BooleanSetting hoppers = (BooleanSetting)this.m28(new BooleanSetting("Hoppers", true));
    private final NumberSetting minHoppers = (NumberSetting)this.m28(new NumberSetting("MinHoppers", 5.0, 1.0, 20.0, 1.0));
    private final BooleanSetting dispensers = (BooleanSetting)this.m28(new BooleanSetting("Dispensers", true));
    private final NumberSetting minDispensers = (NumberSetting)this.m28(new NumberSetting("MinDispensers", 5.0, 1.0, 20.0, 1.0));
    private final NumberSetting scanDelay = (NumberSetting)this.m28(new NumberSetting("ScanDelay", 3.0, 1.0, 30.0, 1.0));
    private final Helper7 helper738 = new Helper7();
    private final Set set2 = new HashSet();
    private final Map map6 = new HashMap();

    public StashLogger() {
        super("StashLogger", "Logs dense storage groups in loaded chunks.", Category.MISC);
    }

    @Override
    public void m709() {
        this.set2.clear();
        this.map6.clear();
    }

    @EventHandler
    private void setEvent2Inner232(Event2.Event2Inner2 event2Inner2) {
        if (Module.isSet37() || !this.helper738.m114((Double)this.scanDelay.getObj())) {
            return;
        }
        this.helper738.m533();
        int n = (Integer)MC.client3.options.getViewDistance().getValue();
        ChunkPos chunkPos = MC.client3.player.getChunkPos();
        for (int i = chunkPos.x - n; i <= chunkPos.x + n; ++i) {
            for (int j = chunkPos.z - n; j <= chunkPos.z + n; ++j) {
                if (!MC.client3.world.isChunkLoaded(i, j)) continue;
                for (BlockEntity blockEntity : MC.client3.world.getChunk(i, j).getBlockEntities().values()) {
                    this.log(blockEntity);
                }
            }
        }
    }

    private void log(Object object) {
        block6: {
            boolean bl;
            BlockUtil blockUtil;
            String string;
            BlockPos blockPos;
            block5: {
                BlockEntity blockEntity = (BlockEntity)object;
                blockPos = blockEntity.getPos();
                string = IRC.getText7();
                Object object2 = this.set2;
                if (string != null) {
                    if (!((Set)object2).add(blockPos.toImmutable())) {
                        return;
                    }
                    object2 = this.map6.computeIfAbsent(ChunkPos.toLong((BlockPos)blockPos), l -> new BlockUtil(this));
                }
                blockUtil = (BlockUtil)object2;
                blockUtil.setObj66(blockEntity);
                bl = blockUtil.flag169;
                if (string == null) break block5;
                if (!bl) break block6;
                blockUtil.flag169 = false;
                bl = (Boolean)this.chat.getObj();
            }
            if (string != null) {
                if (bl) {
                    CommandManager.setObj21(blockPos.toShortString() + " " + String.valueOf(blockUtil));
                }
                bl = (Boolean)this.sound.getObj();
            }
            if (bl) {
                MC.client3.player.playSoundIfNotSilent(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP);
            }
        }
    }

    @Environment(value=EnvType.CLIENT)
    static class BlockUtil {
        private int count190;
        private int count73;
        private int count182;
        private int count154;
        private boolean flag169;
        final StashLogger stashLogger;

        private BlockUtil(StashLogger stashLogger) {
            this.stashLogger = stashLogger;
        }

        /*
         * Unable to fully structure code
         */
        private void setObj66(Object var1_1) {
            BlockEntity blockEntity = (BlockEntity)var1_1;
            net.minecraft.block.Block block = blockEntity.getCachedState().getBlock();
            if (((Boolean)this.stashLogger.chests.getObj()).booleanValue() && (block instanceof ChestBlock || block instanceof BarrelBlock)) {
                ++this.count190;
            } else if (((Boolean)this.stashLogger.shulkers.getObj()).booleanValue() && block instanceof ShulkerBoxBlock) {
                ++this.count73;
            } else if (((Boolean)this.stashLogger.hoppers.getObj()).booleanValue() && block instanceof HopperBlock) {
                ++this.count182;
            } else if (((Boolean)this.stashLogger.dispensers.getObj()).booleanValue() && (block instanceof DispenserBlock || block instanceof DropperBlock)) {
                ++this.count154;
            }
            this.flag169 = this.count190 >= this.stashLogger.minChests.getInt50() || this.count73 >= this.stashLogger.minShulkers.getInt50() || this.count182 >= this.stashLogger.minHoppers.getInt50() || this.count154 >= this.stashLogger.minDispensers.getInt50();
        }

        public String toString() {
            int n = this.count182;
            int n2 = this.count154;
            int n3 = this.count73;
            int n4 = this.count190;
            return "(" + n4 + " chests, " + n3 + " shulkers, " + n2 + " dispensers, " + n + " hoppers)";
        }
    }
}

