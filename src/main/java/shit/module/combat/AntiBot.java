/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import shit.event.DisconnectEvent;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AntiBot
extends Module {
    public static AntiBot INSTANCE;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z0-9_]{1,16}$");
    private final BooleanSetting tabList = (BooleanSetting)this.m28(new BooleanSetting("TabList", true));
    private final BooleanSetting invalidName = (BooleanSetting)this.m28(new BooleanSetting("InvalidName", true));
    private final BooleanSetting zeroHealth = (BooleanSetting)this.m28(new BooleanSetting("ZeroHealth", true));
    private final BooleanSetting godHealth = (BooleanSetting)this.m28(new BooleanSetting("GodHealth", true));
    private final Set<UUID> knownBots = new HashSet<UUID>();

    public AntiBot() {
        super("AntiBot", "Detects fake players (NPCs/bots) so combat modules can skip them.", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        this.knownBots.clear();
    }

    @Override
    public void m709() {
        this.knownBots.clear();
    }

    @EventHandler
    private void setDisconnectEvent4(DisconnectEvent disconnectEvent) {
        this.knownBots.clear();
    }

    @EventHandler
    private void setEvent2Inner5(Event2.Event2Inner event2Inner) {
        if (Module.isSet37() || MC.client3.world == null || MC.client3.getNetworkHandler() == null) {
            return;
        }
        for (PlayerEntity playerEntity : MC.client3.world.getPlayers()) {
            if (playerEntity == MC.client3.player) continue;
            if (this.m421(playerEntity)) {
                this.knownBots.add(playerEntity.getUuid());
            } else {
                this.knownBots.remove(playerEntity.getUuid());
            }
        }
    }

    public static boolean m710(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        if (INSTANCE == null || !INSTANCE.isSet19()) return false;
        if (INSTANCE.knownBots.contains(playerEntity.getUuid())) return true;
        return INSTANCE.m421(playerEntity);
    }

    private boolean m421(PlayerEntity playerEntity) {
        if (((Boolean)this.tabList.getObj()).booleanValue()) {
            if (MC.client3.getNetworkHandler() != null && MC.client3.getNetworkHandler().getPlayerListEntry(playerEntity.getUuid()) == null) {
                return true;
            }
        }
        if (((Boolean)this.invalidName.getObj()).booleanValue()) {
            String string = playerEntity.getGameProfile() != null ? playerEntity.getGameProfile().name() : playerEntity.getName().getString();
            if (string == null || !NAME_PATTERN.matcher(string).matches()) {
                return true;
            }
        }
        if (((Boolean)this.zeroHealth.getObj()).booleanValue()) {
            if (playerEntity.getHealth() <= 0.0f && playerEntity.isAlive()) {
                return true;
            }
        }
        if (((Boolean)this.godHealth.getObj()).booleanValue()) {
            if (playerEntity.getMaxHealth() > 0.0f && playerEntity.getHealth() > playerEntity.getMaxHealth()) {
                return true;
            }
        }
        return false;
    }

    public boolean isBot(UUID uUID) {
        return this.knownBots.contains(uUID);
    }
}
