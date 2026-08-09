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
    private final BooleanSetting tabList = (BooleanSetting)this.registerSetting(new BooleanSetting("TabList", true));
    private final BooleanSetting invalidName = (BooleanSetting)this.registerSetting(new BooleanSetting("InvalidName", true));
    private final BooleanSetting zeroHealth = (BooleanSetting)this.registerSetting(new BooleanSetting("ZeroHealth", true));
    private final BooleanSetting godHealth = (BooleanSetting)this.registerSetting(new BooleanSetting("GodHealth", true));
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
    public void onDisable() {
        this.knownBots.clear();
    }

    @EventHandler
    private void setDisconnectEvent4(DisconnectEvent disconnectEvent) {
        this.knownBots.clear();
    }

    @EventHandler
    private void setEvent2Inner5(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame() || MC.mc.world == null || MC.mc.getNetworkHandler() == null) {
            return;
        }
        for (PlayerEntity playerEntity : MC.mc.world.getPlayers()) {
            if (playerEntity == MC.mc.player) continue;
            if (this.m421(playerEntity)) {
                this.knownBots.add(playerEntity.getUuid());
            } else {
                this.knownBots.remove(playerEntity.getUuid());
            }
        }
    }

    public static boolean m710(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        if (INSTANCE == null || !INSTANCE.isEnabled()) return false;
        if (INSTANCE.knownBots.contains(playerEntity.getUuid())) return true;
        return INSTANCE.m421(playerEntity);
    }

    private boolean m421(PlayerEntity playerEntity) {
        if (((Boolean)this.tabList.getValue()).booleanValue()) {
            if (MC.mc.getNetworkHandler() != null && MC.mc.getNetworkHandler().getPlayerListEntry(playerEntity.getUuid()) == null) {
                return true;
            }
        }
        if (((Boolean)this.invalidName.getValue()).booleanValue()) {
            String string = playerEntity.getGameProfile() != null ? playerEntity.getGameProfile().name() : playerEntity.getName().getString();
            if (string == null || !NAME_PATTERN.matcher(string).matches()) {
                return true;
            }
        }
        if (((Boolean)this.zeroHealth.getValue()).booleanValue()) {
            if (playerEntity.getHealth() <= 0.0f && playerEntity.isAlive()) {
                return true;
            }
        }
        if (((Boolean)this.godHealth.getValue()).booleanValue()) {
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
