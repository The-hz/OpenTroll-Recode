/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import shit.Client;
import shit.command.CommandManager;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class VisualRange
extends Module {
    private final BooleanSetting friends = (BooleanSetting)this.registerSetting(new BooleanSetting("Friends", false));
    private final BooleanSetting leave = (BooleanSetting)this.registerSetting(new BooleanSetting("Leave", true));
    private final BooleanSetting sound = (BooleanSetting)this.registerSetting(new BooleanSetting("Sound", true));
    private final Map map35 = new HashMap();
    private boolean flag143;

    public VisualRange() {
        super("VisualRange", "Notifies when players enter or leave render distance.", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        this.map35.clear();
        this.flag143 = false;
    }

    @Override
    public void onDisable() {
        this.map35.clear();
        this.flag143 = false;
    }

    @EventHandler
    private void setEvent2Inner213(Event2.Event2Inner2 event2Inner2) {
        if (Module.isNotInGame()) {
            return;
        }
        HashMap<UUID, String> hashMap = new HashMap<UUID, String>();
        for (PlayerEntity object : MC.mc.world.getPlayers()) {
            if (object == MC.mc.player) continue;
            String string = object.getName().getString();
            hashMap.put(object.getUuid(), string);
            if (!this.flag143 || this.map35.containsKey(object.getUuid()) || !this.m435(string)) continue;
            CommandManager.sendFeedback(string + " entered visual range.");
            this.m29();
        }
        if (this.flag143 && ((Boolean)this.leave.getValue()).booleanValue()) {
            for (Object o : this.map35.entrySet()) {
                Map.Entry entry = (Map.Entry)o;
                if (hashMap.containsKey(entry.getKey())) continue;
                CommandManager.sendFeedback((String)entry.getValue() + " left visual range.");
                this.m29();
            }
        }
        this.map35.clear();
        this.map35.putAll(hashMap);
        this.flag143 = true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m435(Object object) {
        String string = (String)object;
        Object var4_3 = null;
        if ((Boolean)this.friends.getValue() != false) return true;
        if (Client.friendManager.isFriend(string)) return false;
        return true;
    }

    private void m29() {
        block3: {
            block2: {
                Object var2_1 = null;
                if (!((Boolean)this.sound.getValue()).booleanValue()) break block2;
                if (MC.mc.world == null) break block2;
                if (MC.mc.player != null) break block3;
            }
            return;
        }
        MC.mc.world.playSoundClient(MC.mc.player.getX(), MC.mc.player.getY(), MC.mc.player.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 1.6f, false);
    }
}

