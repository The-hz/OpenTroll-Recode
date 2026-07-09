/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import shit.api.Listener4;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.mixin.ServerboundInteractPacketAccessor;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.setting.BooleanSetting;
import shit.setting.StringSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class FakePlayer
extends Module {
    public static FakePlayer INSTANCE;
    public static PlayerUtil playerUtil;
    private final StringSetting name = (StringSetting)this.m28(new StringSetting("Name", "FakePlayer"));
    private final BooleanSetting damage = (BooleanSetting)this.m28(new BooleanSetting("Damage", true));
    private final BooleanSetting autoTotem = (BooleanSetting)this.m28(new BooleanSetting("AutoTotem", true));
    private final BooleanSetting record_ = (BooleanSetting)this.m28(new BooleanSetting("Record", false));
    private final BooleanSetting play = (BooleanSetting)this.m28(new BooleanSetting("Play", false));
    private final List list = new ArrayList();
    private int count147;
    private boolean flag160 = false;
    private static final UUID field12 = UUID.fromString("66666666-6666-6666-6666-666666666666");

    public FakePlayer() {
        super("FakePlayer", "Spawns a client-side fake player for testing.", Category.MISC);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        String string = IRC.getText7();
        super.onEnable();
        String string2 = string;
        if (string2 != null) {
            if (Module.isSet37()) {
                this.setFlag3(false);
                return;
            }
            playerUtil = new PlayerUtil((PlayerEntity)MC.client3.player, (String)this.name.getObj());
            MC.client3.world.addEntity((Entity)playerUtil);
        }
    }

    @EventHandler
    private void setEvent2Inner11(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (playerUtil != null && playerUtil.getEntityWorld() == MC.client3.world) {
            if (((Boolean)this.autoTotem.getObj()).booleanValue()) {
                if (playerUtil.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
                    playerUtil.setStackInHand(Hand.OFF_HAND, new ItemStack((ItemConvertible)Items.TOTEM_OF_UNDYING));
                }
                if (playerUtil.getMainHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
                    playerUtil.setStackInHand(Hand.MAIN_HAND, new ItemStack((ItemConvertible)Items.TOTEM_OF_UNDYING));
                }
            }
            if ((Boolean)this.record_.getObj() != this.flag160 && ((Boolean)this.record_.getObj()).booleanValue()) {
                this.list.clear();
            }
            this.flag160 = (Boolean)this.record_.getObj();
            if (((Boolean)this.record_.getObj()).booleanValue()) {
                this.list.add(new Data(MC.client3.player.getX(), MC.client3.player.getY(), MC.client3.player.getZ(), MC.client3.player.getYaw(), MC.client3.player.getPitch()));
            }
            if (((Boolean)this.play.getObj()).booleanValue() && !this.list.isEmpty()) {
                ++this.count147;
                if (this.count147 >= this.list.size()) {
                    this.count147 = 0;
                }
                Data data = (Data)this.list.get(this.count147);
                playerUtil.setYaw(data.value56);
                playerUtil.setPitch(data.value57);
                playerUtil.setHeadYaw(data.value56);
                playerUtil.updatePosition(data.value53, data.value54, data.value55);
            }
            if (playerUtil.isDead()) {
                playerUtil.setHealth(playerUtil.getMaxHealth());
            }
        } else {
            this.setFlag3(false);
        }
    }

    @Override
    public void m709() {
        block3: {
            PlayerUtil playerUtil;
            block2: {
                String string = IRC.getText7();
                super.m709();
                String string2 = string;
                playerUtil = FakePlayer.playerUtil;
                if (string2 == null) break block2;
                if (playerUtil == null) break block3;
                playerUtil = FakePlayer.playerUtil;
            }
            playerUtil.remove(Entity.RemovalReason.KILLED);
            FakePlayer.playerUtil = null;
        }
    }

    @EventHandler
    public void setPacketEventInner24(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (Module.isSet37() || playerUtil == null || !((Boolean)this.damage.getObj()).booleanValue()) {
            return;
        }
        Object object = packetEventInner2.getPacket();
        if (object instanceof PlayerInteractEntityC2SPacket) {
            PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket = (PlayerInteractEntityC2SPacket)object;
            boolean[] blArray = new boolean[]{false};
            playerInteractEntityC2SPacket.handle((PlayerInteractEntityC2SPacket.Handler)new ItemHelper(this, blArray));
            if (blArray[0] != false && ((ServerboundInteractPacketAccessor)playerInteractEntityC2SPacket).getEntityId() == playerUtil.getId()) {
                boolean bl;
                packetEventInner2.m209();
                MC.client3.world.playSoundClient(playerUtil.getX(), playerUtil.getY(), playerUtil.getZ(), SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
                float f = (float)MC.client3.player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
                f += FakePlayer.m1044(MC.client3.player.getMainHandStack());
                boolean bl2 = bl = MC.client3.player.fallDistance > 0.0 && !MC.client3.player.isOnGround() && !MC.client3.player.isClimbing() && !MC.client3.player.isTouchingWater();
                if (bl) {
                    MC.client3.world.playSoundClient(playerUtil.getX(), playerUtil.getY(), playerUtil.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
                    MC.client3.player.addCritParticles((Entity)playerUtil);
                    f *= 1.5f;
                }
                if (FakePlayer.playerUtil.hurtTime <= 0) {
                    this.setFloat4(f);
                }
            }
        }
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.PacketEventInner packetEventInner) {
        if (Module.isSet37() || playerUtil == null || !((Boolean)this.damage.getObj()).booleanValue()) {
            return;
        }
        Packet packet = packetEventInner.getPacket();
        if (packet instanceof ExplosionS2CPacket) {
            float f;
            ExplosionS2CPacket explosionS2CPacket = (ExplosionS2CPacket)packet;
            Vec3d vec3d = new Vec3d(explosionS2CPacket.center().x, explosionS2CPacket.center().y, explosionS2CPacket.center().z);
            float f2 = explosionS2CPacket.radius();
            if (f2 <= 0.0f) {
                return;
            }
            float f3 = f2 * 2.0f;
            double d = Math.sqrt(playerUtil.squaredDistanceTo((Vec3d)vec3d));
            if (d >= (double)f3) {
                return;
            }
            double d2 = d / (double)f3;
            double d3 = (1.0 - d2) * 0.75;
            float f4 = (float)((d3 * d3 + d3) / 2.0 * 7.0 * (double)f3 + 1.0);
            float f5 = FakePlayer.m856((Object)playerUtil, f4);
            float f6 = f = FakePlayer.m881((Object)playerUtil, f5);
            MC.client3.execute(() -> {
                String string = IRC.getText7();
                if (string != null) {
                    if (playerUtil == null) {
                        return;
                    }
                    this.setFloat7(f6);
                }
            });
        }
    }

    private void setFloat4(float f) {
        float f2 = f;
        float f3 = FakePlayer.m856((Object)playerUtil, f2);
        float f4 = FakePlayer.m881((Object)playerUtil, f3);
        this.setFloat7(f4);
    }

    private void setFloat7(float f) {
        block12: {
            PlayerUtil playerUtil;
            block13: {
                boolean bl;
                block11: {
                    String string;
                    block10: {
                        float f2;
                        float f3;
                        block8: {
                            float f4;
                            block9: {
                                f4 = f;
                                string = IRC.getText7();
                                PlayerUtil playerUtil2 = FakePlayer.playerUtil;
                                if (string != null) {
                                    if (playerUtil2 == null) {
                                        return;
                                    }
                                    FakePlayer.playerUtil.serverDamage(MC.client3.world.getDamageSources().generic(), 0.1f);
                                    playerUtil2 = FakePlayer.playerUtil;
                                }
                                f3 = playerUtil2.getAbsorptionAmount();
                                f2 = f4;
                                if (string == null) break block8;
                                if (!(f3 >= f2)) break block9;
                                FakePlayer.playerUtil.setAbsorptionAmount(FakePlayer.playerUtil.getAbsorptionAmount() - f4);
                                if (string != null) break block10;
                            }
                            f3 = f4;
                            f2 = FakePlayer.playerUtil.getAbsorptionAmount();
                        }
                        float f5 = f3 - f2;
                        FakePlayer.playerUtil.setAbsorptionAmount(0.0f);
                        FakePlayer.playerUtil.setHealth(FakePlayer.playerUtil.getHealth() - f5);
                    }
                    bl = FakePlayer.playerUtil.isDead();
                    if (string == null) break block11;
                    if (!bl) break block12;
                    playerUtil = FakePlayer.playerUtil;
                    if (string == null) break block13;
                    bl = ((Listener4)((Object)playerUtil)).m642(MC.client3.world.getDamageSources().generic());
                }
                if (!bl) break block12;
                playerUtil = FakePlayer.playerUtil;
            }
            playerUtil.setHealth(10.0f);
            MC.client3.world.sendEntityStatus((Entity)FakePlayer.playerUtil, (byte)35);
        }
    }

    private static float m856(Object object, float f) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        float f2 = f;
        double d = playerEntity.getAttributeValue(EntityAttributes.ARMOR);
        double d2 = playerEntity.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS);
        float f3 = (float)(2.0 + d2 / 4.0);
        float f4 = MathHelper.clamp((float)((float)(d - (double)(f2 / f3))), (float)((float)(d * 0.2)), (float)20.0f);
        float f5 = f4 / 25.0f;
        return f2 * (1.0f - f5);
    }

    private static float m881(Object object, float f) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        float f2 = f;
        double d = FakePlayer.m878(playerEntity);
        String string = IRC.getText7();
        double d2 = d;
        double d3 = 0.0;
        if (string != null) {
            if (d2 <= d3) {
                return f2;
            }
            d2 = f2;
            d3 = 1.0 - Math.min(d / 25.0, 0.8);
        }
        return (float)(d2 * d3);
    }

    private static double m878(Object object) {
        double d;
        block2: {
            PlayerEntity playerEntity = (PlayerEntity)object;
            double d2 = 0.0;
            String string = IRC.getText7();
            for (EquipmentSlot equipmentSlot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
                d = d2 + FakePlayer.m610(playerEntity.getEquippedStack(equipmentSlot));
                if (string != null) {
                    d2 = d;
                    if (string != null) continue;
                }
                break block2;
            }
            d = Math.min(d2, 20.0);
        }
        return d;
    }

    private static double m610(Object object) {
        ItemEnchantmentsComponent itemEnchantmentsComponent;
        ItemStack itemStack = (ItemStack)object;
        String string = IRC.getText7();
        ItemStack itemStack2 = itemStack;
        if (string != null) {
            if (itemStack2.isEmpty()) {
                return 0.0;
            }
            itemStack2 = itemStack;
        }
        if ((itemEnchantmentsComponent = itemStack2.getEnchantments()).isEmpty()) {
            return 0.0;
        }
        double d = 0.0;
        for (Object2IntMap.Entry entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
            block9: {
                int n;
                block8: {
                    boolean bl;
                    block7: {
                        RegistryEntry registryEntry = (RegistryEntry)entry.getKey();
                        n = entry.getIntValue();
                        bl = registryEntry.matchesKey(Enchantments.PROTECTION);
                        if (string == null) break block7;
                        if (bl) break block8;
                        bl = registryEntry.matchesKey(Enchantments.BLAST_PROTECTION);
                    }
                    if (!bl) break block9;
                }
                d += (double)n * 4.0;
            }
            if (string != null) continue;
        }
        return d;
    }

    private static float m1044(Object object) {
        ItemEnchantmentsComponent itemEnchantmentsComponent;
        ItemStack itemStack = (ItemStack)object;
        String string = IRC.getText7();
        ItemStack itemStack2 = itemStack;
        if (string != null) {
            if (itemStack2.isEmpty()) {
                return 0.0f;
            }
            itemStack2 = itemStack;
        }
        ItemEnchantmentsComponent itemEnchantmentsComponent2 = itemEnchantmentsComponent = itemStack2.getEnchantments();
        if (string != null) {
            if (itemEnchantmentsComponent2.isEmpty()) {
                return 0.0f;
            }
            itemEnchantmentsComponent2 = itemEnchantmentsComponent;
        }
        for (Object2IntMap.Entry entry : itemEnchantmentsComponent2.getEnchantmentEntries()) {
            if (((RegistryEntry)entry.getKey()).matchesKey(Enchantments.SHARPNESS)) {
                return 0.5f * (float)entry.getIntValue() + 0.5f;
            }
            if (string != null) continue;
        }
        return 0.0f;
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    static class ItemHelper
    implements PlayerInteractEntityC2SPacket.Handler {
        final boolean[] flags3;

        ItemHelper(FakePlayer fakePlayer, boolean[] blArray) {
            this.flags3 = blArray;
        }

        public void interact(Hand hand) {
        }

        public void interactAt(Hand hand, Vec3d vec3d) {
        }

        public void attack() {
            this.flags3[0] = true;
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static class PlayerUtil
    extends OtherClientPlayerEntity {
        private boolean flag145;
        private final HungerManager field29 = new HungerManager();

        public PlayerUtil(PlayerEntity playerEntity, String string) {
            super(MC.client3.world, new GameProfile(field12, string));
            this.copyPositionAndRotation((Entity)playerEntity);
            this.setPitch(playerEntity.getPitch());
            this.setYaw(playerEntity.getYaw());
            this.lastX = playerEntity.lastX;
            this.lastY = playerEntity.lastY;
            this.lastZ = playerEntity.lastZ;
            this.lastYaw = playerEntity.lastYaw;
            this.lastPitch = playerEntity.lastPitch;
            this.bodyYaw = playerEntity.bodyYaw;
            this.headYaw = playerEntity.headYaw;
            this.lastHeadYaw = playerEntity.lastHeadYaw;
            this.limbAnimator.setSpeed(playerEntity.limbAnimator.getSpeed());
            this.limbAnimator.getAnimationProgress(playerEntity.limbAnimator.getAnimationProgress());
            this.setSneaking(playerEntity.isSneaking());
            this.setPose(playerEntity.getPose());
            this.setFlag(7, playerEntity.isGliding());
            this.flag145 = playerEntity.isOnGround();
            this.setOnGround(this.flag145);
            this.getInventory().clone(playerEntity.getInventory());
            this.setAbsorptionAmount(playerEntity.getAbsorptionAmount());
            this.setHealth(playerEntity.getHealth());
            this.setBoundingBox(playerEntity.getBoundingBox());
        }

        public boolean isOnGround() {
            return this.flag145;
        }

        public boolean isSpectator() {
            return false;
        }

        public boolean isCreative() {
            return false;
        }

        public HungerManager getHungerManager() {
            return this.field29;
        }
    }

    @Environment(value=EnvType.CLIENT)
    record Data(double value53, double value54, double value55, float value56, float value57) {
    }
}

