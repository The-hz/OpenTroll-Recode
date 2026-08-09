/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import shit.Client;
import shit.api.Listener7;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.mixin.ServerboundInteractPacketAccessor;
import shit.module.Category;
import shit.module.Module;
import shit.module.combat.MidClickPearl;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;
import shit.util.MathUtil;

@Environment(value=EnvType.CLIENT)
public class Criticals
extends Module {
    public static Criticals INSTANCE;
    public final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.OldNCP));
    public final BooleanSetting onlyGround = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyGround", true, () -> this.mode.getValue() != Mode.Ground, null, "", false));
    public final BooleanSetting onlyAuraActive = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyAuraActive", false));
    public final BooleanSetting movePause = (BooleanSetting)this.registerSetting(new BooleanSetting("MovePause", false));
    private final BooleanSetting setNoGround = (BooleanSetting)this.registerSetting(new BooleanSetting("SetNoGround", false, () -> this.mode.getValue() == Mode.Ground, null, "", false));
    private final BooleanSetting autoJump = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoJump", true, () -> this.mode.getValue() == Mode.Ground, null, "", false));
    private final BooleanSetting mini = (BooleanSetting)this.registerSetting(new BooleanSetting("Mini", true, () -> {
        Object var1_1 = null;
        if (this.mode.getValue() != Mode.Ground) return false;
        if ((Boolean)this.autoJump.getValue() == false) return false;
        return true;
    }, null, "", false));
    private final NumberSetting motionY = (NumberSetting)this.registerSetting(new NumberSetting("MotionY", 0.05, 0.0, 1.0, 0.001, 0.001, () -> {
        Object var1_1 = null;
        if (this.mode.getValue() != Mode.Ground) return false;
        if ((Boolean)this.autoJump.getValue() == false) return false;
        return true;
    }, null, "", false));
    private final BooleanSetting airStuck = (BooleanSetting)this.registerSetting(new BooleanSetting("AirStuck", false, () -> this.mode.getValue() == Mode.Ground, null, "", false));
    private final NumberSetting stuckTicks = (NumberSetting)this.registerSetting(new NumberSetting("StuckTicks", 10.0, 1.0, 20.0, 1.0, 1.0, () -> {
        Object var1_1 = null;
        if (this.mode.getValue() != Mode.Ground) return false;
        if ((Boolean)this.airStuck.getValue() == false) return false;
        return true;
    }, null, "", false));
    private final BooleanSetting autoDisable = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoDisable", true, () -> this.mode.getValue() == Mode.Ground, null, "", false));
    private final BooleanSetting crawlingDisable = (BooleanSetting)this.registerSetting(new BooleanSetting("CrawlingDisable", true, () -> this.mode.getValue() == Mode.Ground, null, "", false));
    public final BooleanSetting autoMode = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoMode", false));
    private boolean flag90;
    private boolean flag153;
    private boolean flag101;
    private int count216;

    public Criticals() {
        super("Criticals", "Forces critical hits using packet or ground modes.", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public String getInfo() {
        Object var2_1 = null;
        if (((Boolean)this.autoMode.getValue()).booleanValue()) {
            return ((Mode)((Object)this.mode.getValue())).name() + (this.isSet42() ? " [Active]" : " [Idle]");
        }
        return ((Mode)((Object)this.mode.getValue())).name();
    }

    @Override
    public void onEnable() {
        block4: {
            this.flag101 = false;
            this.count216 = 0;
            this.flag153 = this.isSet42();
            Object var2_1 = null;
            this.flag90 = true;
            if (this.mode.getValue() == Mode.Ground) {
                if (this.isSet6()) {
                    if (((Boolean)this.autoDisable.getValue()).booleanValue()) {
                        this.setEnabled(false);
                    }
                    return;
                }
            }
            if (this.mode.getValue() != Mode.Ground || !((Boolean)this.autoJump.getValue()).booleanValue()) break block4;
            if (MC.mc.player != null && MC.mc.player.isOnGround()) {
                this.m534();
            }
        }
    }

    @EventHandler
    private void setPacketEventInner35(PacketEvent.PacketEventInner packetEventInner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket) {
            this.flag101 = false;
            this.count216 = 0;
        }
        if (this.mode.getValue() == Mode.Ground && ((Boolean)this.airStuck.getValue()).booleanValue() && this.flag101) {
            EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket;
            Packet packet = packetEventInner.getPacket();
            if (packet instanceof EntityVelocityUpdateS2CPacket && (entityVelocityUpdateS2CPacket = (EntityVelocityUpdateS2CPacket)packet).getEntityId() == MC.mc.player.getId()) {
                packetEventInner.cancel();
            } else if (packetEventInner.getPacket() instanceof ExplosionS2CPacket) {
                packetEventInner.cancel();
            }
        }
    }

    @EventHandler
    private void setPacketEventInner2(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (Module.isNotInGame() || MidClickPearl.flag80 || this.isSet99()) {
            return;
        }
        if (((Boolean)this.autoMode.getValue()).booleanValue() && !this.isSet42()) {
            return;
        }
        if (this.mode.getValue() == Mode.Ground) {
            if (((Boolean)this.setNoGround.getValue()).booleanValue() && packetEventInner2.getPacket() instanceof PlayerMoveC2SPacket) {
                ((Listener7)packetEventInner2.getPacket()).setOnGround(false);
            }
            return;
        }
        Packet packet = packetEventInner2.getPacket();
        if (!(packet instanceof PlayerInteractEntityC2SPacket)) {
            return;
        }
        PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket = (PlayerInteractEntityC2SPacket)packet;
        if (!this.m467(playerInteractEntityC2SPacket)) {
            return;
        }
        Entity entity = this.m450(playerInteractEntityC2SPacket);
        if (entity == null || entity instanceof EndCrystalEntity) {
            return;
        }
        if (((Boolean)this.onlyGround.getValue()).booleanValue() && !MC.mc.player.isOnGround() && !MC.mc.player.getAbilities().flying) {
            return;
        }
        if (MC.mc.player.isInLava() || MC.mc.player.isTouchingWater()) {
            return;
        }
        this.setObj83(entity);
    }

    @EventHandler
    private void setEvent2Inner35(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        if (((Boolean)this.autoMode.getValue()).booleanValue()) {
            boolean bl = this.isSet42();
            if (!bl) {
                this.flag153 = false;
                return;
            }
            if (!this.flag153) {
                this.flag90 = true;
                this.flag153 = true;
            }
        }
        if (this.mode.getValue() != Mode.Ground || this.isSet99()) {
            return;
        }
        if (MC.mc.player.isOnGround()) {
            this.flag101 = false;
            this.count216 = 0;
        }
        if (this.flag101 && ((Boolean)this.airStuck.getValue()).booleanValue()) {
            ++this.count216;
            if (this.count216 > this.stuckTicks.getInt()) {
                this.flag101 = false;
            } else {
                MC.mc.player.setVelocity(Vec3d.ZERO);
            }
        }
        if (this.isSet6()) {
            if (((Boolean)this.autoDisable.getValue()).booleanValue()) {
                this.setEnabled(false);
            }
            return;
        }
        if (MC.mc.player.isOnGround() && ((Boolean)this.autoJump.getValue()).booleanValue() && this.flag90) {
            this.m534();
            this.flag90 = false;
            if (((Boolean)this.airStuck.getValue()).booleanValue()) {
                this.flag101 = true;
                this.count216 = 0;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet42() {
        Object var2_1 = null;
        if (MC.mc.player == null) return false;
        if (!ItemUtil.m931(MC.mc.player)) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet99() {
        Module module = Client.moduleManager.getModule("MaceSpoof");
        Object var2_2 = null;
        if (module != null) {
            if (module.isEnabled()) {
                return true;
            }
        }
        if (((Boolean)this.onlyAuraActive.getValue()).booleanValue()) {
            Module module2 = Client.moduleManager.getModule("KillAura");
            if (module2 == null) return true;
            if (!module2.isEnabled()) {
                return true;
            }
        }
        if ((Boolean)this.movePause.getValue() == false) return false;
        if (!MathUtil.isSet7()) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet6() {
        Object var2_1 = null;
        if (((Boolean)this.crawlingDisable.getValue()).booleanValue()) {
            if (MC.mc.player.isCrawling()) return true;
        }
        if (!MathUtil.isSet7()) return false;
        if ((Boolean)this.autoDisable.getValue() == false) return false;
        return true;
    }

    private void m534() {
        block5: {
            block4: {
                Object var2_1 = null;
                if (!((Boolean)this.mini.getValue()).booleanValue()) break block4;
                MathUtil.setDouble7((Double)this.motionY.getValue());
                if (null == null) break block5;
            }
            if (MC.mc.player != null) {
                MC.mc.player.jump();
            }
        }
    }

    private void setObj83(Object object) {
        Entity entity = (Entity)object;
        Object var4_3 = null;
        switch (((Mode)((Object)this.mode.getValue())).ordinal()) {
            case 0: {
                this.m614(entity, 2.71875E-7, 0.0);
                if (null == null) break;
            }
            case 1: {
                MC.mc.player.addCritParticles(entity);
                this.m179(0.062600301692775, false);
                this.m179(0.07260029960661, false);
                this.m179(0.0, false);
                this.m179(0.0, false);
                if (null == null) break;
            }
            case 2: {
                this.m614(entity, 0.0625, 0.0);
                if (null == null) break;
            }
            case 3: {
                MC.mc.player.addCritParticles(entity);
                this.m179(1.058293536E-5, false);
                this.m179(9.16580235E-6, false);
                this.m179(1.0371854E-7, false);
                if (null == null) break;
            }
            case 4: {
                MC.mc.player.addCritParticles(entity);
                this.m179(0.0045, true);
                this.m179(1.52121E-4, false);
                this.m179(0.3, false);
                this.m179(0.025, false);
                if (null == null) break;
            }
            case 5: {
                this.m614(entity, 5.0E-4, 1.0E-4);
                if (null == null) break;
            }
            case 7: {
                if (MathUtil.isSet7() || !MathUtil.isSet132()) {
                    return;
                }
                this.m179(0.0, true);
                this.m179(0.0625, false);
                this.m179(0.045, false);
                break;
            }
        }
    }

    private void m614(Object object, double d, double d2) {
        Entity entity = (Entity)object;
        double d3 = d;
        double d4 = d2;
        MC.mc.player.addCritParticles(entity);
        this.m179(d3, false);
        this.m179(d4, false);
    }

    private void m179(double d, boolean bl) {
        double d2 = d;
        boolean bl2 = bl;
        MC.mc.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.PositionAndOnGround(MC.mc.player.getX(), MC.mc.player.getY() + d2, MC.mc.player.getZ(), bl2, MC.mc.player.horizontalCollision));
    }

    private Entity m450(Object object) {
        PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket = (PlayerInteractEntityC2SPacket)object;
        Object var4_3 = null;
        return MC.mc.world == null ? null : MC.mc.world.getEntityById(((ServerboundInteractPacketAccessor)playerInteractEntityC2SPacket).getEntityId());
    }

    private boolean m467(Object object) {
        PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket = (PlayerInteractEntityC2SPacket)object;
        boolean[] blArray = new boolean[]{false};
        playerInteractEntityC2SPacket.handle((PlayerInteractEntityC2SPacket.Handler)new ItemHelper(this, blArray));
        return blArray[0];
    }

    @Environment(value=EnvType.CLIENT)
    static class ItemHelper
    implements PlayerInteractEntityC2SPacket.Handler {
        final boolean[] flags4;

        ItemHelper(Criticals criticals, boolean[] blArray) {
            this.flags4 = blArray;
        }

        public void interact(Hand hand) {
        }

        public void interactAt(Hand hand, Vec3d vec3d) {
        }

        public void attack() {
            this.flags4[0] = true;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      UpdatedNCP, Strict, NCP, OldNCP, Hypixel2K22, Packet, Ground, BBTT;

      private Mode() {}



        private static Mode[] getModeArray10() {
            return new Mode[]{UpdatedNCP, Strict, NCP, OldNCP, Hypixel2K22, Packet, Ground, BBTT};
        }
    
   }
}

