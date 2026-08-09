/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import shit.Client;
import shit.module.hud.AbstractHudModule;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class TextRadar
extends AbstractHudModule {
    private final BooleanSetting booleanSetting3 = (BooleanSetting)this.registerSetting(new BooleanSetting("Health", true));
    private final BooleanSetting booleanSetting8 = (BooleanSetting)this.registerSetting(new BooleanSetting("Ping", false));
    private final BooleanSetting booleanSetting7 = (BooleanSetting)this.registerSetting(new BooleanSetting("Effects", true));
    private final BooleanSetting booleanSetting9 = (BooleanSetting)this.registerSetting(new BooleanSetting("Distance", true));
    private final NumberSetting numberSetting2 = (NumberSetting)this.registerSetting(new NumberSetting("Range", 64.0, 8.0, 512.0, 1.0));
    private final NumberSetting numberSetting5 = (NumberSetting)this.registerSetting(new NumberSetting("MaxEntries", 8.0, 1.0, 32.0, 1.0));

    public TextRadar() {
        super("TextRadar", "Lists nearby players.", 6, 246);
    }

    @Override
    protected List lines() {
        int n;
        block12: {
            block11: {
                boolean bl = true;
                if (MC.mc.player == null) break block11;
                if (MC.mc.world != null) break block12;
            }
            return List.of("TextRadar N/A");
        }
        List<AbstractClientPlayerEntity> list = MC.mc.world.getPlayers().stream().filter(abstractClientPlayerEntity -> abstractClientPlayerEntity != MC.mc.player).filter(abstractClientPlayerEntity -> {
            boolean bl = AbstractHudModule.isEditMode();
            int m = abstractClientPlayerEntity.isAlive() ? 1 : 0;
            if (!bl) {
                if (m == 0) return 0 != 0;
                float f = MC.mc.player.distanceTo((Entity)abstractClientPlayerEntity) - this.numberSetting2.getFloat();
                m = f == 0.0f ? 0 : (f < 0.0f ? -1 : 1);
            }
            if (bl) return m != 0;
            if (m > 0) return 0 != 0;
            return 1 != 0;
        }).sorted(Comparator.comparingDouble(abstractClientPlayerEntity -> MC.mc.player.distanceTo((Entity)abstractClientPlayerEntity))).toList();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (n = 0; n < Math.min(this.numberSetting5.getInt(), list.size()); ++n) {
            AbstractClientPlayerEntity abstractClientPlayerEntity2 = list.get(n);
            StringBuilder stringBuilder = new StringBuilder();
            if (((Boolean)this.booleanSetting3.getValue()).booleanValue()) {
                stringBuilder.append(String.format(Locale.ROOT, "%.1f ", Float.valueOf(abstractClientPlayerEntity2.getHealth() + abstractClientPlayerEntity2.getAbsorptionAmount())));
            }
            stringBuilder.append(Client.manager.isFriend(abstractClientPlayerEntity2.getName().getString()) ? "[F] " : "");
            stringBuilder.append(abstractClientPlayerEntity2.getName().getString());
            if (((Boolean)this.booleanSetting8.getValue()).booleanValue()) {
                stringBuilder.append(" ").append(this.m794(abstractClientPlayerEntity2)).append("ms");
            }
            if (((Boolean)this.booleanSetting7.getValue()).booleanValue()) {
                if (abstractClientPlayerEntity2.hasStatusEffect(StatusEffects.WEAKNESS)) {
                    stringBuilder.append(" W");
                }
                if (abstractClientPlayerEntity2.hasStatusEffect(StatusEffects.STRENGTH)) {
                    stringBuilder.append(" S");
                }
            }
            if (((Boolean)this.booleanSetting9.getValue()).booleanValue()) {
                stringBuilder.append(String.format(Locale.ROOT, " %.1fm", Float.valueOf(MC.mc.player.distanceTo((Entity)abstractClientPlayerEntity2))));
            }
            arrayList.add(stringBuilder.toString());
            if (true) continue;
        }
        n = list.size() - arrayList.size();
        if (n > 0) {
            arrayList.add("...and " + n + " more");
        }
        return arrayList.isEmpty() ? List.of("TextRadar Empty") : arrayList;
    }

    private int m794(Object object) {
        AbstractClientPlayerEntity abstractClientPlayerEntity = (AbstractClientPlayerEntity)object;
        boolean bl = true;
        if (MC.mc.getNetworkHandler() == null) {
            return 0;
        }
        PlayerListEntry playerListEntry = MC.mc.getNetworkHandler().getPlayerListEntry(abstractClientPlayerEntity.getUuid());
        return playerListEntry == null ? 0 : playerListEntry.getLatency();
    }
}

