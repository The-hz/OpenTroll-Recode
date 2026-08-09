/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.HashSet;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.StringSetting;
import shit.util.MC;
import shit.util.ChatUtils;

@Environment(value=EnvType.CLIENT)
public class AutoEZ
extends Module {
    private final BooleanSetting self = (BooleanSetting)this.registerSetting(new BooleanSetting("Self", false));
    private final StringSetting message = (StringSetting)this.registerSetting(new StringSetting("Message", "gg %s"));
    private final Set set3 = new HashSet();

    public AutoEZ() {
        super("AutoEZ", "Sends a message when nearby players die.", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        this.set3.clear();
    }

    @Override
    public void onDisable() {
        this.set3.clear();
    }

    @EventHandler
    private void setEvent2Inner22(TickEvent.PostTick event2Inner2) {
        if (Module.isNotInGame()) {
            return;
        }
        for (PlayerEntity playerEntity : MC.mc.world.getPlayers()) {
            if (playerEntity == MC.mc.player && !((Boolean)this.self.getValue()).booleanValue()) continue;
            if (playerEntity.isAlive()) {
                this.set3.add(playerEntity.getUuid());
                continue;
            }
            if (!this.set3.remove(playerEntity.getUuid())) continue;
            ChatUtils.sendChatMessage(String.format((String)this.message.getValue(), playerEntity.getName().getString()));
        }
    }
}

