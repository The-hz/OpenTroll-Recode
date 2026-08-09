/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.entity.LivingEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.Items
 *  net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket
 *  net.minecraft.particle.ParticleEffect
 *  net.minecraft.particle.ParticleTypes
 *  net.minecraft.sound.SoundCategory
 *  net.minecraft.sound.SoundEvent
 *  net.minecraft.sound.SoundEvents
 */
package shit.module.misc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.event.PlayerEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class KillEffect
extends Module {
    public static KillEffect INSTANCE;
    private final BooleanSetting removeCorpse;
    private final BooleanSetting lightning;
    private final BooleanSetting fireWork;
    private final NumberSetting fireworkHeight;
    private final BooleanSetting levelUp;
    private final NumberSetting lMaxPitch;
    private final NumberSetting lMinPitch;
    private final BooleanSetting trident;
    private final NumberSetting tMaxPitch;
    private final NumberSetting tMinPitch;
    private final BooleanSetting maceSound;
    private final NumberSetting factor;
    private final BooleanSetting popLightning;
    private final Map map31;
    private final CopyOnWriteArrayList copyOnWriteArrayList;
    private final Random random8;

        public KillEffect() {
        super("KillEffect", "Plays local kill, pop and mace effects.", Category.MISC);
        this.removeCorpse = (BooleanSetting)this.registerSetting(new BooleanSetting("RemoveCorpse", false));
        this.lightning = (BooleanSetting)this.registerSetting(new BooleanSetting("Lightning", true));
        this.fireWork = (BooleanSetting)this.registerSetting(new BooleanSetting("FireWork", false));
        this.fireworkHeight = (NumberSetting)this.registerSetting(new NumberSetting("FireworkHeight", 4.0, 1.0, 12.0, 0.1));
        this.levelUp = (BooleanSetting)this.registerSetting(new BooleanSetting("LevelUp", true));
        this.lMaxPitch = (NumberSetting)this.registerSetting(new NumberSetting("LMaxPitch", 1.0, 0.0, 2.0, 0.1));
        this.lMinPitch = (NumberSetting)this.registerSetting(new NumberSetting("LMinPitch", 1.0, 0.0, 2.0, 0.1));
        this.trident = (BooleanSetting)this.registerSetting(new BooleanSetting("Trident", false));
        this.tMaxPitch = (NumberSetting)this.registerSetting(new NumberSetting("TMaxPitch", 1.0, 0.0, 2.0, 0.1));
        this.tMinPitch = (NumberSetting)this.registerSetting(new NumberSetting("TMinPitch", 1.0, 0.0, 2.0, 0.1));
        this.maceSound = (BooleanSetting)this.registerSetting(new BooleanSetting("MaceSound", true));
        this.factor = (NumberSetting)this.registerSetting(new NumberSetting("Factor", 1.0, 1.0, 10.0, 1.0));
        this.popLightning = (BooleanSetting)this.registerSetting(new BooleanSetting("PopLightning", true));
        this.map31 = new HashMap();
        this.copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.random8 = new Random();
    }

    @Override
    public void onDisable() {
        this.map31.clear();
        this.copyOnWriteArrayList.clear();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m210(Object object) {
        LivingEntity livingEntity;
        String string;
        LivingEntity livingEntity2;
        block6: {
            boolean bl;
            block5: {
                livingEntity2 = (LivingEntity)object;
                string = IRC.getConnectionId();
                bl = this.isEnabled();
                if (string != null) {
                    if (!bl) return false;
                    bl = (Boolean)this.removeCorpse.getValue();
                }
                if (string == null) break block5;
                if (!bl) return false;
                livingEntity = livingEntity2;
                if (string == null) break block6;
                bl = livingEntity instanceof PlayerEntity;
            }
            if (!bl) return false;
            livingEntity = livingEntity2;
        }
        if (string != null) {
            if (livingEntity == MC.mc.player) return false;
            livingEntity = livingEntity2;
        }
        boolean bl = livingEntity.isAlive();
        if (string == null) return bl;
        if (bl) return false;
        return true;
    }

    @EventHandler
    private void setEvent2Inner2(TickEvent.PostTick event2Inner2) {
        if (Module.isNotInGame()) {
            return;
        }
        this.m552();
        this.copyOnWriteArrayList.removeIf(holder -> ((KillEffectHolder)holder).isSet24());
    }

    @EventHandler
    private void setPacketEventInner23(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (Module.isNotInGame() || !((Boolean)this.maceSound.getValue()).booleanValue()) {
            return;
        }
        if (packetEventInner2.getPacket() instanceof PlayerInteractEntityC2SPacket && MC.mc.player.getMainHandStack().isOf(Items.MACE)) {
            MC.mc.world.playSoundClient(MC.mc.player.getX(), MC.mc.player.getY(), MC.mc.player.getZ(), this.random8.nextBoolean() ? SoundEvents.ITEM_MACE_SMASH_GROUND : SoundEvents.ITEM_MACE_SMASH_GROUND_HEAVY, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
        }
    }

    @EventHandler
    private void setPlayerEvent(PlayerEvent playerEvent) {
        if (Module.isNotInGame() || !((Boolean)this.popLightning.getValue()).booleanValue() || playerEvent.getPlayer4() == MC.mc.player) {
            return;
        }
        this.m294(playerEvent.getPlayer4().getX(), playerEvent.getPlayer4().getY(), playerEvent.getPlayer4().getZ());
    }

    private void m552() {
        Iterator iterator;
        String string;
        HashSet<UUID> hashSet;
        block8: {
            hashSet = new HashSet<UUID>();
            string = IRC.getConnectionId();
            iterator = MC.mc.world.getPlayers().iterator();
            block0: while (iterator.hasNext()) {
                block10: {
                    int n = 0;
                    PlayerEntity playerEntity;
                    block12: {
                        int n2;
                        block11: {
                            Boolean bl;
                            int n3;
                            block9: {
                                Boolean bl2;
                                playerEntity = (PlayerEntity)iterator.next();
                                if (string == null) break block8;
                                PlayerEntity playerEntity2 = playerEntity;
                                if (string != null) {
                                    if (playerEntity2 == MC.mc.player) continue;
                                    hashSet.add(playerEntity.getUuid());
                                    playerEntity2 = playerEntity;
                                }
                                n3 = playerEntity2.isAlive() ? 1 : 0;
                                bl = bl2 = (Boolean)this.map31.put(playerEntity.getUuid(), n3 != 0);
                                if (string == null) break block9;
                                if (bl == null) break block10;
                                bl = bl2;
                            }
                            n2 = bl.booleanValue() ? 1 : 0;
                            if (string == null) break block11;
                            if (n2 == 0) break block10;
                            n2 = n3;
                        }
                        if (string == null) break block12;
                        if (n2 != 0) break block10;
                        n2 = n = 0;
                    }
                    while (n < this.factor.getInt()) {
                        this.setObj61(playerEntity);
                        ++n;
                        if (string == null) continue block0;
                        if (string != null) continue;
                    }
                }
                if (string != null) continue;
            }
            iterator = this.map31.keySet().iterator();
        }
        block2: while (true) {
            boolean bl = iterator.hasNext();
            while (bl) {
                bl = hashSet.contains(iterator.next());
                if (string == null) continue;
                if (bl) continue block2;
                iterator.remove();
                if (string == null) break block2;
                continue block2;
            }
            break;
        }
    }

    private void setObj61(Object object) {
        block6: {
            PlayerEntity playerEntity = (PlayerEntity)object;
            double d = playerEntity.getX();
            double d2 = playerEntity.getY();
            double d3 = playerEntity.getZ();
            String string = IRC.getConnectionId();
            boolean bl = (Boolean)this.lightning.getValue();
            if (string != null) {
                if (bl) {
                    this.m294(d, d2, d3);
                }
                bl = (Boolean)this.fireWork.getValue();
            }
            if (string != null) {
                if (bl) {
                    this.copyOnWriteArrayList.add(new KillEffectHolder(this, d, d2, d3, (Double)this.fireworkHeight.getValue()));
                }
                bl = (Boolean)this.levelUp.getValue();
            }
            if (string != null) {
                if (bl) {
                    MC.mc.world.playSoundClient(d, d2, d3, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 100.0f, this.m924((Double)this.lMinPitch.getValue(), (Double)this.lMaxPitch.getValue()), false);
                }
                bl = (Boolean)this.trident.getValue();
            }
            if (!bl) break block6;
            MC.mc.world.playSoundClient(d, d2, d3, (SoundEvent)SoundEvents.ITEM_TRIDENT_THUNDER.value(), SoundCategory.MASTER, 999.0f, this.m924((Double)this.tMinPitch.getValue(), (Double)this.tMaxPitch.getValue()), false);
        }
    }

    private void m294(double d, double d2, double d3) {
        double d4 = d;
        double d5 = d2;
        double d6 = d3;
        String string = IRC.getConnectionId();
        MC.mc.world.playSoundClient(d4, d5, d6, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.WEATHER, 10000.0f, 0.8f + this.random8.nextFloat() * 0.2f, false);
        MC.mc.world.playSoundClient(d4, d5, d6, SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.WEATHER, 2.0f, 0.5f + this.random8.nextFloat() * 0.2f, false);
        String string2 = string;
        for (int i = 0; i < 20; ++i) {
            MC.mc.world.addParticleClient((ParticleEffect)ParticleTypes.ELECTRIC_SPARK, d4, d5 + this.random8.nextDouble() * 2.0, d6, this.random8.nextGaussian() * 0.08, this.random8.nextDouble() * 0.2, this.random8.nextGaussian() * 0.08);
            if (string2 != null) continue;
        }
    }

    private float m924(double d, double d2) {
        double d3 = d;
        double d4 = d2;
        double d5 = Math.min(d3, d4);
        double d6 = Math.max(d3, d4);
        return (float)(d5 + this.random8.nextDouble() * (d6 - d5));
    }

    @Environment(value=EnvType.CLIENT)
    static class KillEffectHolder {
        private final double value122;
        private final double value141;
        private final double value159;
        private final double value195;
        private int count153;
        final KillEffect killEffect;

        private KillEffectHolder(KillEffect killEffect, double d, double d2, double d3, double d4) {
            this.killEffect = killEffect;
            this.value122 = d;
            this.value141 = d2;
            this.value159 = d3;
            this.value195 = d4;
            MC.mc.world.playSoundClient(d, d2, d3, SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
        }

        /*
         * Unable to fully structure code
         */
        private boolean isSet24() {
            double d = this.value141 + (double)this.count153 * 0.28;
            MC.mc.world.addParticleClient((ParticleEffect)ParticleTypes.FIREWORK, this.value122, d, this.value159, 0.0, 0.28, 0.0);
            ++this.count153;
            if (d - this.value141 - this.value195 < 0.0 && (double)this.count153 - Math.ceil(this.value195 / 0.28) < 0.0) {
                return false;
            }
            MC.mc.world.addParticleClient((ParticleEffect)ParticleTypes.EXPLOSION_EMITTER, this.value122, d, this.value159, 0.0, 0.0, 0.0);
            for (int i = 0; i < 80; ++i) {
                MC.mc.world.addParticleClient((ParticleEffect)ParticleTypes.FIREWORK, this.value122, d, this.value159, (this.killEffect.random8.nextDouble() - 0.5) * 0.7, (this.killEffect.random8.nextDouble() - 0.15) * 0.7, (this.killEffect.random8.nextDouble() - 0.5) * 0.7);
            }
            MC.mc.world.playSoundClient(this.value122, d, this.value159, SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.PLAYERS, 2.0f, 1.0f, false);
            MC.mc.world.playSoundClient(this.value122, d, this.value159, SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE, SoundCategory.PLAYERS, 1.5f, 1.0f, false);
            return true;
        }
    }
}
