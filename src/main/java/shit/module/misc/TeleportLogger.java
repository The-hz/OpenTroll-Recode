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
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.CoordsLog;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class TeleportLogger
extends Module {
    private final BooleanSetting printAdd = (BooleanSetting)this.m28(new BooleanSetting("PrintAdd", true));
    private final BooleanSetting printRemove = (BooleanSetting)this.m28(new BooleanSetting("PrintRemove", true));
    private final BooleanSetting removeInRange = (BooleanSetting)this.m28(new BooleanSetting("RemoveInRange", true));
    private final NumberSetting minDistance = (NumberSetting)this.m28(new NumberSetting("MinDistance", 512.0, 64.0, 4096.0, 16.0));
    private final NumberSetting removeDistance = (NumberSetting)this.m28(new NumberSetting("RemoveDistance", 128.0, 16.0, 512.0, 16.0));
    private final Map map48 = new HashMap();

    public TeleportLogger() {
        super("TeleportLogger", "Logs players that appear far away.", Category.MISC);
    }

    @Override
    public void m709() {
        this.map48.clear();
    }

    @EventHandler
    private void setEvent2Inner230(Event2.Event2Inner2 event2Inner2) {
        if (Module.isSet37()) {
            return;
        }
        Iterator iterator = this.map48.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            PlayerEntity playerEntity = MC.client3.world.getPlayerByUuid((UUID)entry.getKey());
            if (playerEntity == null) {
                iterator.remove();
                continue;
            }
            if (!((Boolean)this.removeInRange.getObj()).booleanValue() || !((double)playerEntity.distanceTo((Entity)MC.client3.player) < (Double)this.removeDistance.getObj())) continue;
            if (((Boolean)this.printRemove.getObj()).booleanValue()) {
                Util2.setObj10("[TeleportLogger] Removed " + playerEntity.getName().getString() + ", now in range.");
            }
            iterator.remove();
        }
        for (PlayerEntity playerEntity : MC.client3.world.getPlayers()) {
            if (playerEntity == MC.client3.player || Client.manager.m258(playerEntity.getName().getString()) || (double)playerEntity.distanceTo((Entity)MC.client3.player) < (Double)this.minDistance.getObj() || this.map48.containsKey(playerEntity.getUuid())) continue;
            BlockPos blockPos = playerEntity.getBlockPos();
            this.map48.put(playerEntity.getUuid(), blockPos);
            int n = blockPos.getZ();
            int n2 = blockPos.getY();
            int n3 = blockPos.getX();
            String string = playerEntity.getName().getString() + " teleported at " + n3 + ", " + n2 + ", " + n;
            CoordsLog.setObj67(string);
            if (!((Boolean)this.printAdd.getObj()).booleanValue()) continue;
            Util2.setObj10("[TeleportLogger] " + string);
        }
    }
}

