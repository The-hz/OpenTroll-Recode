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
    public final EnumSetting mode = (EnumSetting)this.m28(new EnumSetting("Mode", Mode.OldNCP));
    public final BooleanSetting onlyGround = (BooleanSetting)this.m28(new BooleanSetting("OnlyGround", true, () -> this.mode.getObj() != Mode.Ground, null, "", false));
    public final BooleanSetting onlyAuraActive = (BooleanSetting)this.m28(new BooleanSetting("OnlyAuraActive", false));
    public final BooleanSetting movePause = (BooleanSetting)this.m28(new BooleanSetting("MovePause", false));
    private final BooleanSetting setNoGround = (BooleanSetting)this.m28(new BooleanSetting("SetNoGround", false, () -> this.mode.getObj() == Mode.Ground, null, "", false));
    private final BooleanSetting autoJump = (BooleanSetting)this.m28(new BooleanSetting("AutoJump", true, () -> this.mode.getObj() == Mode.Ground, null, "", false));
    private final BooleanSetting mini = (BooleanSetting)this.m28(new BooleanSetting("Mini", true, () -> {
        Object var1_1 = null;
        if (this.mode.getObj() != Mode.Ground) return false;
        if ((Boolean)this.autoJump.getObj() == false) return false;
        return true;
    }, null, "", false));
    private final NumberSetting motionY = (NumberSetting)this.m28(new NumberSetting("MotionY", 0.05, 0.0, 1.0, 0.001, 0.001, () -> {
        Object var1_1 = null;
        if (this.mode.getObj() != Mode.Ground) return false;
        if ((Boolean)this.autoJump.getObj() == false) return false;
        return true;
    }, null, "", false));
    private final BooleanSetting airStuck = (BooleanSetting)this.m28(new BooleanSetting("AirStuck", false, () -> this.mode.getObj() == Mode.Ground, null, "", false));
    private final NumberSetting stuckTicks = (NumberSetting)this.m28(new NumberSetting("StuckTicks", 10.0, 1.0, 20.0, 1.0, 1.0, () -> {
        Object var1_1 = null;
        if (this.mode.getObj() != Mode.Ground) return false;
        if ((Boolean)this.airStuck.getObj() == false) return false;
        return true;
    }, null, "", false));
    private final BooleanSetting autoDisable = (BooleanSetting)this.m28(new BooleanSetting("AutoDisable", true, () -> this.mode.getObj() == Mode.Ground, null, "", false));
    private final BooleanSetting crawlingDisable = (BooleanSetting)this.m28(new BooleanSetting("CrawlingDisable", true, () -> this.mode.getObj() == Mode.Ground, null, "", false));
    public final BooleanSetting autoMode = (BooleanSetting)this.m28(new BooleanSetting("AutoMode", false));
    private boolean flag90;
    private boolean flag153;
    private boolean flag101;
    private int count216;

    public Criticals() {
        super("Criticals", "Forces critical hits using packet or ground modes.", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public String getText57() {
        Object var2_1 = null;
        if (((Boolean)this.autoMode.getObj()).booleanValue()) {
            return ((Mode)((Object)this.mode.getObj())).name() + (this.isSet42() ? " [Active]" : " [Idle]");
        }
        return ((Mode)((Object)this.mode.getObj())).name();
    }

    @Override
    public void onEnable() {
        block4: {
            this.flag101 = false;
            this.count216 = 0;
            this.flag153 = this.isSet42();
            Object var2_1 = null;
            this.flag90 = true;
            if (this.mode.getObj() == Mode.Ground) {
                if (this.isSet6()) {
                    if (((Boolean)this.autoDisable.getObj()).booleanValue()) {
                        this.setFlag3(false);
                    }
                    return;
                }
            }
            if (this.mode.getObj() != Mode.Ground || !((Boolean)this.autoJump.getObj()).booleanValue()) break block4;
            if (MC.client3.player != null && MC.client3.player.isOnGround()) {
                this.m534();
            }
        }
    }

    @EventHandler
    private void setPacketEventInner35(PacketEvent.PacketEventInner packetEventInner) {
        if (Module.isSet37()) {
            return;
        }
        if (packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket) {
            this.flag101 = false;
            this.count216 = 0;
        }
        if (this.mode.getObj() == Mode.Ground && ((Boolean)this.airStuck.getObj()).booleanValue() && this.flag101) {
            EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket;
            Packet packet = packetEventInner.getPacket();
            if (packet instanceof EntityVelocityUpdateS2CPacket && (entityVelocityUpdateS2CPacket = (EntityVelocityUpdateS2CPacket)packet).getEntityId() == MC.client3.player.getId()) {
                packetEventInner.m209();
            } else if (packetEventInner.getPacket() instanceof ExplosionS2CPacket) {
                packetEventInner.m209();
            }
        }
    }

    @EventHandler
    private void setPacketEventInner2(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (Module.isSet37() || MidClickPearl.flag80 || this.isSet99()) {
            return;
        }
        if (((Boolean)this.autoMode.getObj()).booleanValue() && !this.isSet42()) {
            return;
        }
        if (this.mode.getObj() == Mode.Ground) {
            if (((Boolean)this.setNoGround.getObj()).booleanValue() && packetEventInner2.getPacket() instanceof PlayerMoveC2SPacket) {
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
        if (((Boolean)this.onlyGround.getObj()).booleanValue() && !MC.client3.player.isOnGround() && !MC.client3.player.getAbilities().flying) {
            return;
        }
        if (MC.client3.player.isInLava() || MC.client3.player.isTouchingWater()) {
            return;
        }
        this.setObj83(entity);
    }

    @EventHandler
    private void setEvent2Inner35(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (((Boolean)this.autoMode.getObj()).booleanValue()) {
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
        if (this.mode.getObj() != Mode.Ground || this.isSet99()) {
            return;
        }
        if (MC.client3.player.isOnGround()) {
            this.flag101 = false;
            this.count216 = 0;
        }
        if (this.flag101 && ((Boolean)this.airStuck.getObj()).booleanValue()) {
            ++this.count216;
            if (this.count216 > this.stuckTicks.getInt50()) {
                this.flag101 = false;
            } else {
                MC.client3.player.setVelocity(Vec3d.ZERO);
            }
        }
        if (this.isSet6()) {
            if (((Boolean)this.autoDisable.getObj()).booleanValue()) {
                this.setFlag3(false);
            }
            return;
        }
        if (MC.client3.player.isOnGround() && ((Boolean)this.autoJump.getObj()).booleanValue() && this.flag90) {
            this.m534();
            this.flag90 = false;
            if (((Boolean)this.airStuck.getObj()).booleanValue()) {
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
        if (MC.client3.player == null) return false;
        if (!ItemUtil.m931(MC.client3.player)) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet99() {
        Module module = Client.moduleManager.m979("MaceSpoof");
        Object var2_2 = null;
        if (module != null) {
            if (module.isSet19()) {
                return true;
            }
        }
        if (((Boolean)this.onlyAuraActive.getObj()).booleanValue()) {
            Module module2 = Client.moduleManager.m979("KillAura");
            if (module2 == null) return true;
            if (!module2.isSet19()) {
                return true;
            }
        }
        if ((Boolean)this.movePause.getObj() == false) return false;
        if (!MathUtil.isSet7()) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet6() {
        Object var2_1 = null;
        if (((Boolean)this.crawlingDisable.getObj()).booleanValue()) {
            if (MC.client3.player.isCrawling()) return true;
        }
        if (!MathUtil.isSet7()) return false;
        if ((Boolean)this.autoDisable.getObj() == false) return false;
        return true;
    }

    private void m534() {
        block5: {
            block4: {
                Object var2_1 = null;
                if (!((Boolean)this.mini.getObj()).booleanValue()) break block4;
                MathUtil.setDouble7((Double)this.motionY.getObj());
                if (null == null) break block5;
            }
            if (MC.client3.player != null) {
                MC.client3.player.jump();
            }
        }
    }

    private void setObj83(Object object) {
        Entity entity = (Entity)object;
        Object var4_3 = null;
        switch (((Mode)((Object)this.mode.getObj())).ordinal()) {
            case 0: {
                this.m614(entity, 2.71875E-7, 0.0);
                if (null == null) break;
            }
            case 1: {
                MC.client3.player.addCritParticles(entity);
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
                MC.client3.player.addCritParticles(entity);
                this.m179(1.058293536E-5, false);
                this.m179(9.16580235E-6, false);
                this.m179(1.0371854E-7, false);
                if (null == null) break;
            }
            case 4: {
                MC.client3.player.addCritParticles(entity);
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
        MC.client3.player.addCritParticles(entity);
        this.m179(d3, false);
        this.m179(d4, false);
    }

    private void m179(double d, boolean bl) {
        double d2 = d;
        boolean bl2 = bl;
        MC.client3.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.PositionAndOnGround(MC.client3.player.getX(), MC.client3.player.getY() + d2, MC.client3.player.getZ(), bl2, MC.client3.player.horizontalCollision));
    }

    private Entity m450(Object object) {
        PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket = (PlayerInteractEntityC2SPacket)object;
        Object var4_3 = null;
        return MC.client3.world == null ? null : MC.client3.world.getEntityById(((ServerboundInteractPacketAccessor)playerInteractEntityC2SPacket).getEntityId());
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

