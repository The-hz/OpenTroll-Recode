/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.event.StartAttackEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.setting.BooleanSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoTool
extends Module {
    private final BooleanSetting weapon = (BooleanSetting)this.registerSetting(new BooleanSetting("Weapon", false));
    private final BooleanSetting silent = (BooleanSetting)this.registerSetting(new BooleanSetting("Silent", false));
    private int count58 = -1;

    public AutoTool() {
        super("AutoTool", "Switches to the best hotbar tool or weapon.", Category.MISC);
    }

    @Override
    public void onDisable() {
        this.m604();
    }

    @EventHandler
    private void setPacketEventInner211(PacketEvent.PacketEventInner2 packetEventInner2) {
        PlayerActionC2SPacket playerActionC2SPacket;
        if (Module.isNotInGame()) {
            return;
        }
        Packet packet = packetEventInner2.getPacket();
        if (packet instanceof PlayerActionC2SPacket && (playerActionC2SPacket = (PlayerActionC2SPacket)packet).getAction() == PlayerActionC2SPacket.Action.START_DESTROY_BLOCK) {
            this.setObj35(playerActionC2SPacket.getPos());
        }
    }

    @EventHandler
    private void setStartAttackEvent(StartAttackEvent startAttackEvent) {
        EntityHitResult entityHitResult;
        if (Module.isNotInGame() || !((Boolean)this.weapon.getValue()).booleanValue()) {
            return;
        }
        HitResult hitResult = MC.mc.crosshairTarget;
        if (hitResult instanceof EntityHitResult && (entityHitResult = (EntityHitResult)hitResult).getEntity() instanceof LivingEntity) {
            this.m60();
        }
    }

    private void setObj35(Object object) {
        block4: {
            BlockPos blockPos = (BlockPos)object;
            BlockState blockState = MC.mc.world.getBlockState(blockPos);
            int n = -1;
            float f = MC.mc.player.getMainHandStack().getMiningSpeedMultiplier(blockState);
            int n2 = 0;
            String string = IRC.getText7();
            while (n2 < 9) {
                ItemStack itemStack = MC.mc.player.getInventory().getStack(n2);
                float f2 = itemStack.getMiningSpeedMultiplier(blockState);
                if (string != null) {
                    if (string != null) {
                        if (f2 > f) {
                            f = f2;
                            n = n2;
                        }
                        ++n2;
                    }
                    if (string != null) continue;
                }
                break block4;
            }
            this.setInt9(n);
        }
    }

    private void m60() {
        block4: {
            int n = -1;
            double d = this.m971(MC.mc.player.getMainHandStack());
            int n2 = 0;
            String string = IRC.getText7();
            while (n2 < 9) {
                ItemStack itemStack = MC.mc.player.getInventory().getStack(n2);
                double d2 = this.m971(itemStack);
                if (string != null) {
                    if (string != null) {
                        if (d2 > d) {
                            d = d2;
                            n = n2;
                        }
                        ++n2;
                    }
                    if (string != null) continue;
                }
                break block4;
            }
            this.setInt9(n);
        }
    }

    private double m971(Object object) {
        ItemStack itemStack = (ItemStack)object;
        String string = IRC.getText7();
        boolean bl = itemStack.isEmpty();
        if (string != null) {
            if (bl) {
                return 0.0;
            }
            bl = itemStack.isIn(ItemTags.SWORDS);
        }
        if (string != null) {
            if (bl) {
                return 3.0 + (double)itemStack.getDamage();
            }
            bl = itemStack.isIn(ItemTags.AXES);
        }
        if (bl) {
            return 2.0 + (double)itemStack.getDamage();
        }
        return 0.0;
    }

    private void setInt9(int n) {
        int n2;
        AutoTool autoTool;
        block9: {
            block10: {
                int n3;
                block8: {
                    String string;
                    block6: {
                        block7: {
                            block5: {
                                block4: {
                                    n2 = n;
                                    string = IRC.getText7();
                                    n3 = n2;
                                    if (string == null) break block4;
                                    if (n3 < 0) break block5;
                                    n3 = n2;
                                }
                                if (string == null) break block6;
                                if (n3 != MC.mc.player.getInventory().getSelectedSlot()) break block7;
                            }
                            return;
                        }
                        n3 = ((Boolean)this.silent.getValue()).booleanValue() ? 1 : 0;
                    }
                    if (string == null) break block8;
                    if (n3 == 0) break block9;
                    autoTool = this;
                    if (string == null) break block10;
                    n3 = autoTool.count58;
                }
                if (n3 != -1) break block9;
                autoTool = this;
            }
            autoTool.count58 = MC.mc.player.getInventory().getSelectedSlot();
        }
        MC.mc.player.getInventory().setSelectedSlot(n2);
    }

    private void m604() {
        AutoTool autoTool;
        block2: {
            block3: {
                ClientPlayerEntity clientPlayerEntity;
                block4: {
                    String string = IRC.getText7();
                    autoTool = this;
                    if (string == null) break block2;
                    if (autoTool.count58 == -1) break block3;
                    clientPlayerEntity = MC.mc.player;
                    if (string == null) break block4;
                    if (clientPlayerEntity == null) break block3;
                    clientPlayerEntity = MC.mc.player;
                }
                clientPlayerEntity.getInventory().setSelectedSlot(this.count58);
            }
            autoTool = this;
        }
        autoTool.count58 = -1;
    }
}

