/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import shit.Client;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.CoordsLog;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.ChatUtils;

@Environment(value=EnvType.CLIENT)
public class TeleportLogger
extends Module {
    private final BooleanSetting printAdd = (BooleanSetting)this.registerSetting(new BooleanSetting("PrintAdd", true));
    private final BooleanSetting printRemove = (BooleanSetting)this.registerSetting(new BooleanSetting("PrintRemove", true));
    private final BooleanSetting removeInRange = (BooleanSetting)this.registerSetting(new BooleanSetting("RemoveInRange", true));
    private final NumberSetting minDistance = (NumberSetting)this.registerSetting(new NumberSetting("MinDistance", 512.0, 64.0, 4096.0, 16.0));
    private final NumberSetting removeDistance = (NumberSetting)this.registerSetting(new NumberSetting("RemoveDistance", 128.0, 16.0, 512.0, 16.0));
    private final Map map48 = new HashMap();

    public TeleportLogger() {
        super("TeleportLogger", "Logs players that appear far away.", Category.MISC);
    }

    @Override
    public void onDisable() {
        this.map48.clear();
    }

    @EventHandler
    private void setEvent2Inner230(TickEvent.PostTick event2Inner2) {
        if (Module.isNotInGame()) {
            return;
        }
        Iterator iterator = this.map48.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            PlayerEntity playerEntity = MC.mc.world.getPlayerByUuid((UUID)entry.getKey());
            if (playerEntity == null) {
                iterator.remove();
                continue;
            }
            if (!((Boolean)this.removeInRange.getValue()).booleanValue() || !((double)playerEntity.distanceTo((Entity)MC.mc.player) < (Double)this.removeDistance.getValue())) continue;
            if (((Boolean)this.printRemove.getValue()).booleanValue()) {
                ChatUtils.sendClientMessage("[TeleportLogger] Removed " + playerEntity.getName().getString() + ", now in range.");
            }
            iterator.remove();
        }
        for (PlayerEntity playerEntity : MC.mc.world.getPlayers()) {
            if (playerEntity == MC.mc.player || Client.friendManager.isFriend(playerEntity.getName().getString()) || (double)playerEntity.distanceTo((Entity)MC.mc.player) < (Double)this.minDistance.getValue() || this.map48.containsKey(playerEntity.getUuid())) continue;
            BlockPos blockPos = playerEntity.getBlockPos();
            this.map48.put(playerEntity.getUuid(), blockPos);
            int n = blockPos.getZ();
            int n2 = blockPos.getY();
            int n3 = blockPos.getX();
            String string = playerEntity.getName().getString() + " teleported at " + n3 + ", " + n2 + ", " + n;
            CoordsLog.setObj67(string);
            if (!((Boolean)this.printAdd.getValue()).booleanValue()) continue;
            ChatUtils.sendClientMessage("[TeleportLogger] " + string);
        }
    }
}

