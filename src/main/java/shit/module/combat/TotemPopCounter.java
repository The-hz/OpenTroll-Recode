/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import shit.command.CommandManager;
import shit.event.DisconnectEvent;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PlayerEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class TotemPopCounter
extends Module {
    private final BooleanSetting self = (BooleanSetting)this.registerSetting(new BooleanSetting("Self", true));
    private final BooleanSetting death = (BooleanSetting)this.registerSetting(new BooleanSetting("Death", true));
    private final Map map36 = new HashMap();

    public TotemPopCounter() {
        super("TotemPopCounter", "Counts player totem pops.", Category.COMBAT);
    }

    @Override
    public void onDisable() {
        this.map36.clear();
    }

    @EventHandler
    private void setDisconnectEvent3(DisconnectEvent disconnectEvent) {
        this.map36.clear();
    }

    @EventHandler
    private void setPlayerEvent5(PlayerEvent playerEvent) {
        if (playerEvent.getPlayer4() == MC.mc.player && !((Boolean)this.self.getValue()).booleanValue()) {
            return;
        }
        int n = (Integer)this.map36.merge(playerEvent.getPlayer4().getUuid(), 1, (java.util.function.BiFunction<Integer, Integer, Integer>)Integer::sum);
        String string = playerEvent.getPlayer4() == MC.mc.player ? "You" : playerEvent.getPlayer4().getName().getString();
        CommandManager.sendFeedback(string + " popped " + n + " totem" + (n == 1 ? "." : "s."));
    }

    @EventHandler
    private void setEvent2Inner26(Event2.Event2Inner2 event2Inner2) {
        if (Module.isNotInGame() || !((Boolean)this.death.getValue()).booleanValue()) {
            return;
        }
        Iterator iterator = this.map36.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            PlayerEntity playerEntity = MC.mc.world.getPlayerByUuid((UUID)entry.getKey());
            if (playerEntity == null || playerEntity.isAlive()) continue;
            CommandManager.sendFeedback(playerEntity.getName().getString() + " died after popping " + String.valueOf(entry.getValue()) + " totems.");
            iterator.remove();
        }
    }
}

