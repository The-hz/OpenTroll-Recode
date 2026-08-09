/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.Iterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.misc.Stopwatch;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.Friend;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class KillAura
extends Module {
    public static KillAura INSTANCE;
    private final EnumSetting target = (EnumSetting)this.registerSetting(new EnumSetting("Target", TargetMode.Players));
    private final NumberSetting range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 4.5, 1.0, 6.0, 0.1));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 50.0, 0.0, 1000.0, 1.0));
    private final BooleanSetting rotate = (BooleanSetting)this.registerSetting(new BooleanSetting("Rotate", true));
    private final BooleanSetting onlyWeapon = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyWeapon", false));
    private final BooleanSetting skipCrystals = (BooleanSetting)this.registerSetting(new BooleanSetting("SkipCrystals", true));
    private final BooleanSetting pauseIfUsing = (BooleanSetting)this.registerSetting(new BooleanSetting("PauseIfUsing", true));
    private final BooleanSetting onlyGround = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyGround", false));
    private final Stopwatch helper711 = new Stopwatch();

    public KillAura() {
        super("KillAura", "Attacks nearby entities within range with cooldown and optional rotation.", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public String getInfo() {
        return ((TargetMode)((Object)this.target.getValue())).name();
    }

    @Override
    public void onEnable() {
        this.helper711.resetTimer();
    }

    @EventHandler
    private void setEvent2Inner25(TickEvent.PreTick event2Inner) {
        if (Module.isNotInGame() || MC.mc.interactionManager == null) {
            return;
        }
        if (((Boolean)this.onlyGround.getValue()).booleanValue() && !MC.mc.player.isOnGround()) {
            return;
        }
        if (((Boolean)this.pauseIfUsing.getValue()).booleanValue() && MC.mc.player.isUsingItem()) {
            return;
        }
        if (((Boolean)this.onlyWeapon.getValue()).booleanValue() && !ItemUtil.m931(MC.mc.player)) {
            return;
        }
        if (!this.helper711.hasPassedMs(((Double)this.delay.getValue()).doubleValue())) {
            return;
        }
        Entity entity = this.m453();
        if (entity == null) {
            return;
        }
        if (((Boolean)this.rotate.getValue()).booleanValue()) {
            this.m426(entity);
        }
        MC.mc.interactionManager.attackEntity(MC.mc.player, entity);
        MC.mc.player.swingHand(Hand.MAIN_HAND);
        this.helper711.resetTimer();
    }

    private Entity m453() {
        Entity entity = null;
        double d = ((Double)this.range.getValue()).doubleValue();
        double d2 = Double.MAX_VALUE;
        TargetMode targetMode = (TargetMode)((Object)this.target.getValue());
        Iterator iterator = MC.mc.world.getEntities().iterator();
        while (iterator.hasNext()) {
            Entity entity2 = (Entity)iterator.next();
            if (!this.m289(entity2, targetMode)) continue;
            if (((Boolean)this.skipCrystals.getValue()).booleanValue() && entity2 instanceof EndCrystalEntity) continue;
            double d3 = MC.mc.player.distanceTo(entity2);
            if (d3 > d || d3 >= d2) continue;
            d2 = d3;
            entity = entity2;
        }
        return entity;
    }

    private boolean m289(Entity entity, TargetMode targetMode) {
        if (entity == MC.mc.player) {
            return false;
        }
        if (!(entity instanceof LivingEntity)) {
            return false;
        }
        LivingEntity livingEntity = (LivingEntity)entity;
        if (!livingEntity.isAlive() || livingEntity.isSpectator()) {
            return false;
        }
        switch (targetMode) {
            case Players: {
                if (!(livingEntity instanceof PlayerEntity)) return false;
                PlayerEntity playerEntity = (PlayerEntity)livingEntity;
                if (Friend.m446(playerEntity.getGameProfile().name())) return false;
                if (AntiBot.INSTANCE != null && AntiBot.INSTANCE.isEnabled() && AntiBot.m710(playerEntity)) return false;
                return true;
            }
            case Mobs: {
                return livingEntity instanceof HostileEntity;
            }
            case Passive: {
                return livingEntity instanceof PassiveEntity;
            }
            case All: {
                return livingEntity instanceof PlayerEntity || livingEntity instanceof MobEntity;
            }
        }
        return false;
    }

    private void m426(Entity entity) {
        double d = entity.getX() - MC.mc.player.getX();
        double d2 = entity.getEyeY() - MC.mc.player.getEyeY();
        double d3 = entity.getZ() - MC.mc.player.getZ();
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)Math.toDegrees(Math.atan2(d3, d)) - 90.0f;
        float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d4)));
        MC.mc.player.setYaw(f);
        MC.mc.player.setPitch(f2);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum TargetMode {
        Players, Mobs, Passive, All;

        private TargetMode() {}

        private static TargetMode[] getTargetModeArray() {
            return new TargetMode[]{Players, Mobs, Passive, All};
        }
    }
}
