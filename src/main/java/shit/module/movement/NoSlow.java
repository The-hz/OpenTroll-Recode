/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class NoSlow
extends Module {
    public static NoSlow INSTANCE;
    private final EnumSetting mode = (EnumSetting)this.m28(new EnumSetting("Mode", Mode.Vanilla));
    private final BooleanSetting soulSand = (BooleanSetting)this.m28(new BooleanSetting("SoulSand", true));
    private final BooleanSetting sneak = (BooleanSetting)this.m28(new BooleanSetting("Sneak", false));
    private final EnumSetting gTSpeed = (EnumSetting)this.m28(new EnumSetting("GT Speed", EMode.NORMAL, () -> this.mode.getObj() == Mode.GrimTick, null, "", false));
    private final BooleanSetting gTFood = (BooleanSetting)this.m28(new BooleanSetting("GT Food", true, () -> this.mode.getObj() == Mode.GrimTick, null, "", false));
    private final BooleanSetting gTBow = (BooleanSetting)this.m28(new BooleanSetting("GT Bow", true, () -> this.mode.getObj() == Mode.GrimTick, null, "", false));
    private final BooleanSetting gTCrossbow = (BooleanSetting)this.m28(new BooleanSetting("GT Crossbow", true, () -> this.mode.getObj() == Mode.GrimTick, null, "", false));
    private boolean flag72 = false;
    private Type2 type22 = Type2.NONE;
    private int count140 = 0;
    private final Queue queue2 = new ConcurrentLinkedQueue();
    private boolean flag32 = false;
    private boolean flag86 = false;
    private int count63 = 0;
    private static String[] texts7;

    public NoSlow() {
        super("NoSlow", "Prevents item use, soul sand, and sneak slowdown.", Category.MOVEMENT);
        INSTANCE = this;
    }

    @Override
    public void m709() {
        super.m709();
        this.type22 = Type2.NONE;
        this.count140 = 0;
        this.m1021();
        this.m447();
    }

    private boolean m609(Object object) {
        UseAction useAction = (UseAction)object;
        Object var4_3 = null;
        return useAction == UseAction.EAT || useAction == UseAction.DRINK;
    }

    private void m1021() {
        this.type22 = Type2.NONE;
        Object var2_1 = null;
        while (!this.queue2.isEmpty()) {
            Packet packet = (Packet)this.queue2.poll();
            if (packet == null) continue;
            if (MC.client3.player == null) continue;
            MC.client3.player.networkHandler.sendPacket(packet);
            if (null == null) continue;
            break;
        }
        if (MC.client3.player != null) {
            MC.client3.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
        }
    }

    public boolean isSet105() {
        block14: {
            block13: {
                block12: {
                    block11: {
                        Object var2_1 = null;
                        if (MC.client3.player == null) break block11;
                        if (!MC.client3.player.getActiveItem().isEmpty()) break block12;
                    }
                    return false;
                }
                UseAction useAction = MC.client3.player.getActiveItem().getUseAction();
                if (!this.m609(useAction)) break block13;
                if (MC.client3.player.getItemUseTimeLeft() > 0) break block14;
            }
            return false;
        }
        Hand hand = MC.client3.player.getActiveHand() == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
        if (this.m609(MC.client3.player.getStackInHand(hand).getUseAction())) {
            return false;
        }
        if (this.type22 != Type2.EATING) {
            MC.client3.options.useKey.setPressed(false);
        }
        if (this.type22 == Type2.NONE) {
            this.type22 = Type2.CANCEL_C0F;
            boolean bl = MC.client3.player.currentScreenHandler != MC.client3.player.playerScreenHandler;
            if (bl) {
                MC.client3.player.networkHandler.sendPacket((Packet)new CloseHandledScreenC2SPacket(MC.client3.player.currentScreenHandler.syncId));
            }
            return false;
        }
        if (this.type22 == Type2.EATING) {
            MC.client3.player.setSprinting(true);
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet121() {
        Object var2_1 = null;
        if (MC.client3.player == null) {
            return false;
        }
        ItemStack itemStack = MC.client3.player.getMainHandStack();
        ItemStack itemStack2 = MC.client3.player.getOffHandStack();
        if (itemStack.isOf(Items.GOLDEN_APPLE)) return true;
        if (itemStack2.isOf(Items.GOLDEN_APPLE)) return true;
        if (itemStack.isOf(Items.ENCHANTED_GOLDEN_APPLE)) return true;
        if (itemStack2.isOf(Items.ENCHANTED_GOLDEN_APPLE)) return true;
        if (itemStack.isOf(Items.POTION)) return true;
        if (!itemStack2.isOf(Items.POTION)) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m967(Object object) {
        Item item = (Item)object;
        Object var4_3 = null;
        if (MC.client3.player == null) {
            return false;
        }
        ItemStack itemStack = MC.client3.player.getMainHandStack();
        ItemStack itemStack2 = MC.client3.player.getOffHandStack();
        if (itemStack.isOf(item)) return true;
        if (!itemStack2.isOf(item)) return false;
        return true;
    }

    private void m447() {
        this.flag32 = false;
        this.flag86 = false;
        this.count63 = 0;
    }

    public boolean isSet13() {
        Object var2_1 = null;
        if (MC.client3.player == null) {
            return false;
        }
        if (!MC.client3.player.isUsingItem()) {
            return false;
        }
        if (!((Boolean)this.gTFood.getObj()).booleanValue()) {
            if (this.isSet121()) {
                return false;
            }
        }
        if (!((Boolean)this.gTBow.getObj()).booleanValue()) {
            if (this.m967(Items.BOW)) {
                return false;
            }
        }
        if (!((Boolean)this.gTCrossbow.getObj()).booleanValue()) {
            if (this.m967(Items.CROSSBOW)) {
                return false;
            }
        }
        if (this.isSet121()) {
            if (MC.client3.player.getItemUseTimeLeft() > 30) {
                return false;
            }
        }
        int n = MC.client3.player.getItemUseTimeLeft();
        boolean bl = ((EMode)((Object)this.gTSpeed.getObj())).m675(n);
        if (bl) {
            if (!this.flag32) {
                this.flag86 = true;
                this.count63 = 0;
                this.flag32 = true;
                return false;
            }
            if (this.flag86) {
                --this.count63;
                if (this.count63 <= 0) {
                    this.flag86 = false;
                    MC.client3.player.setSprinting(true);
                    return true;
                }
                return false;
            }
            MC.client3.player.setSprinting(true);
            return true;
        }
        this.flag32 = false;
        this.flag86 = false;
        this.count63 = 0;
        return false;
    }

    @EventHandler
    private void setEvent2Inner17(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        this.flag72 = MC.client3.player.isUsingItem();
        if (this.mode.getObj() == Mode.Grim) {
            if (this.type22 != Type2.EATING) {
                this.count140 = 0;
            } else if (MC.client3.player.isUsingItem()) {
                this.count140 = 0;
            } else {
                ++this.count140;
                if (this.count140 >= 5) {
                    this.m1021();
                }
            }
        }
        if (this.mode.getObj() == Mode.GrimTick && !MC.client3.player.isUsingItem()) {
            this.m447();
        }
    }

    @EventHandler
    public void setPacketEventInner220(PacketEvent.PacketEventInner2 packetEventInner2) {
        if (Module.isSet37()) {
            return;
        }
        if (this.mode.getObj() == Mode.Grim) {
            PlayerActionC2SPacket playerActionC2SPacket;
            Packet packet = packetEventInner2.getPacket();
            if (packet instanceof CommonPongC2SPacket && this.type22 != Type2.NONE) {
                packetEventInner2.m209();
                this.queue2.add(packet);
                if (this.type22 == Type2.CANCEL_C0F) {
                    this.type22 = Type2.SWAP_HANDS;
                    MC.client3.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
                }
            }
            if (packet instanceof PlayerActionC2SPacket && (playerActionC2SPacket = (PlayerActionC2SPacket)packet).getAction() == PlayerActionC2SPacket.Action.RELEASE_USE_ITEM && this.type22 == Type2.EATING) {
                this.m1021();
            }
        }
    }

    @EventHandler
    public void setPacketEventInner(PacketEvent.PacketEventInner packetEventInner) {
        if (Module.isSet37()) {
            return;
        }
        if (this.mode.getObj() == Mode.Grim && packetEventInner.getPacket() instanceof ScreenHandlerSlotUpdateS2CPacket && this.type22 == Type2.SWAP_HANDS) {
            MC.client3.options.useKey.setPressed(true);
            this.type22 = Type2.EATING;
        }
    }

    public boolean isSet64() {
        Object var2_1 = null;
        if (!this.isSet19()) {
            return false;
        }
        if (this.mode.getObj() == Mode.None) {
            return false;
        }
        return switch (((Mode)((Object)this.mode.getObj())).ordinal()) {
            case 1 -> this.isSet105();
            case 2 -> this.isSet13();
            case 0 -> true;
            default -> false;
        };
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet87() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.soulSand.getObj() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet72() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.sneak.getObj() == false) return false;
        return true;
    }

    public static void setTextArray5(String[] stringArray) {
        texts7 = stringArray;
    }

    public static String[] getTextArray() {
        return texts7;
    }

    static {
        String string = "\u0007\u008c\u0082\u0093\t\u00c0\u0090\u00a0\u00050U\u00b7\u00e7Y\u0006o\u00e0\u00d4}\u00c1\u00d7\u0004+\u0003\b\b\u0006\u00f7\u00af\u009c\u00e6\u00d5O1\u00e3\u00de\u00ae\u001et\u00fb\u0099\u00ba\u00be\u00ce\b\u00f5x\u00fd\u00af\u00bf\u00a6I0\u001d\u00e7\u00deE\u00cd\u00b5\u00f1+\u00bd\u00ff(\u00da\u00c0\u007fu\u00de\u00f1\u00f4\u0011\u00b3B\u00ed\u00aa\u00da\u0094P\u00eb\u0098\u007f\u000e\b\u0096\u0010sc\u00eb\u0002\u0080|";
        int n = 92;
        int n2 = 8;
        NoSlow.setTextArray5(null);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      Vanilla, Grim, GrimTick, None;

      private Mode() {}



        private static Mode[] getModeArray17() {
            return new Mode[]{Vanilla, Grim, GrimTick, None};
        }
    
   }


    @Environment(value=EnvType.CLIENT)
    public static enum EMode {
      NORMAL, AGGRESSIVE, FAST, EXTREME, FULL;

      private EMode() {}



        
        public boolean m675(int n) {
            int n2 = n;
            Object var4_3 = null;
            return n2 % 5 != 0;
        }

        private static EMode[] getObjArray9() {
            return new EMode[]{NORMAL, AGGRESSIVE, FAST, EXTREME, FULL};
        }
    
   }


    @Environment(value=EnvType.CLIENT)
    static enum Type2 {
      NONE, CANCEL_C0F, SWAP_HANDS, EATING;

      private Type2() {}



        private static Type2[] getType2Array() {
            return new Type2[]{NONE, CANCEL_C0F, SWAP_HANDS, EATING};
        }
    
   }



}

