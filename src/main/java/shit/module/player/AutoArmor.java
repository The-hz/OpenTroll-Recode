/*
 * Decompiled with CFR 0.152.
 */
package shit.module.player;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.Render2DEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.movement.ElytraFly;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting2;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoArmor
extends Module {
    public static AutoArmor INSTANCE;
    private final EnumSetting head = (EnumSetting)this.registerSetting(new EnumSetting("Head", HeadMode.Protection));
    private final EnumSetting body = (EnumSetting)this.registerSetting(new EnumSetting("Body", HeadMode.Protection));
    private final EnumSetting tights = (EnumSetting)this.registerSetting(new EnumSetting("Tights", HeadMode.Protection));
    private final EnumSetting feet = (EnumSetting)this.registerSetting(new EnumSetting("Feet", HeadMode.Protection));
    private final BooleanSetting ignoreCurse = (BooleanSetting)this.registerSetting(new BooleanSetting("IgnoreCurse", true));
    private final BooleanSetting noMove = (BooleanSetting)this.registerSetting(new BooleanSetting("NoMove", false));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 3.0, 0.0, 10.0, 1.0));
    public final BooleanSetting autoElytra = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoElytra", true));
    private final EnumSetting hotbarSwap = (EnumSetting)this.registerSetting(new EnumSetting("HotbarSwap", HotbarSwapMode.Swap));
    private final EnumSetting inventorySwap = (EnumSetting)this.registerSetting(new EnumSetting("InventorySwap", InventorySwapMode.ClickSlot));
    private final BooleanSetting switchArmor = (BooleanSetting)this.registerSetting(new BooleanSetting("SwitchArmor", false));
    private final EnumSetting notifyMode = (EnumSetting)this.registerSetting(new EnumSetting("NotifyMode", EMode2.Hud));
    private final NumberSetting hudX = (NumberSetting)this.registerSetting(new NumberSetting("HudX", 50.0, 0.0, 100.0, 0.1, 0.1, () -> {
        boolean bl = false;
        return (Boolean)this.switchArmor.getValue() != false && this.notifyMode.getValue() == EMode2.Hud;
    }, null, "", false));
    private final NumberSetting hudY = (NumberSetting)this.registerSetting(new NumberSetting("HudY", 85.0, 0.0, 100.0, 0.1, 0.1, () -> {
        boolean bl = false;
        return (Boolean)this.switchArmor.getValue() != false && this.notifyMode.getValue() == EMode2.Hud;
    }, null, "", false));
    private final BooleanSetting autoSwitch = (BooleanSetting)this.registerSetting(new BooleanSetting("AutoSwitch", false));
    private final EnumSetting switchMode = (EnumSetting)this.registerSetting(new EnumSetting("SwitchMode", EMode.Item, () -> {
        boolean bl = false;
        if ((Boolean)this.switchArmor.getValue() == false) return false;
        if ((Boolean)this.autoSwitch.getValue() == false) return false;
        return true;
    }, null, "", false));
    private final NumberSetting enemySwordRange = (NumberSetting)this.registerSetting(new NumberSetting("EnemySwordRange", 10.0, 1.0, 20.0, 0.1, 0.1, () -> {
        boolean bl = false;
        if ((Boolean)this.switchArmor.getValue() == false) return false;
        if ((Boolean)this.autoSwitch.getValue() == false) return false;
        if (this.switchMode.getValue() != EMode.Item) return false;
        return true;
    }, null, "", false));
    private final ColorSetting2 switchArmorKey = (ColorSetting2)this.registerSetting(new ColorSetting2("SwitchArmorKey", -1, () -> {
        int n = AutoArmor.getSwitchFlag();
        boolean bl = (Boolean)this.switchArmor.getValue();
        if (n != 0) {
            if (!bl) return false;
            bl = (Boolean)this.autoSwitch.getValue();
        }
        if (n == 0) return bl;
        if (bl) return false;
        return true;
    }));
    private final BooleanSetting switchHead = (BooleanSetting)this.registerSetting(new BooleanSetting("SwitchHead", true));
    private final BooleanSetting switchChest = (BooleanSetting)this.registerSetting(new BooleanSetting("SwitchChest", true));
    private final BooleanSetting switchLegs = (BooleanSetting)this.registerSetting(new BooleanSetting("SwitchLegs", true));
    private final BooleanSetting switchFeet = (BooleanSetting)this.registerSetting(new BooleanSetting("SwitchFeet", true));
    private final NumberSetting switchDelay = (NumberSetting)this.registerSetting(new NumberSetting("SwitchDelay", 1.0, 0.0, 5.0, 1.0));
    private int count228 = 0;
    private boolean flag139 = false;
    private boolean flag93 = false;
    private List list32 = new ArrayList();
    private int count139 = 0;
    private Type type4 = Type.NONE;
    private Type type6 = Type.NONE;
    private Type type7 = Type.NONE;
    private static int count177;

    public AutoArmor() {
        super("AutoArmor", "Intelligently equips and manages your armor.", Category.PLAYER);
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        this.flag93 = false;
        this.list32.clear();
        this.type6 = Type.NONE;
        this.type7 = Type.NONE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet104() {
        int n = AutoArmor.getSwitchFlag();
        ClientPlayerEntity clientPlayerEntity = MC.mc.player;
        if (n != 0) {
            if (clientPlayerEntity == null) {
                return false;
            }
            clientPlayerEntity = MC.mc.player;
        }
        ItemStack itemStack = clientPlayerEntity.getMainHandStack();
        boolean bl = itemStack.isIn(ItemTags.SWORDS);
        if (n == 0) return bl;
        if (bl) return true;
        bl = itemStack.isIn(ItemTags.AXES);
        if (n == 0) return bl;
        if (!bl) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet145() {
        int n = AutoArmor.getSwitchFlag();
        ElytraFly elytraFly = ElytraFly.INSTANCE;
        if (n != 0) {
            if (elytraFly == null) return false;
            elytraFly = ElytraFly.INSTANCE;
        }
        boolean bl = elytraFly.isEnabled();
        if (n == 0) return bl;
        if (!bl) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet95() {
        boolean bl = false;
        if ((Boolean)this.autoElytra.getValue() == false) return false;
        if (!this.isSet145()) return false;
        return true;
    }

    private boolean isSet91() {
        boolean bl = false;
        if (MC.mc.player == null) {
            return false;
        }
        return MC.mc.player.getVelocity().horizontalLengthSquared() > 1.0E-4;
    }

    private void setObj75(Object object) {
        String string = (String)object;
        boolean bl = false;
        if (MC.mc.player == null) {
            return;
        }
        MC.mc.player.sendMessage((Text)Text.literal((String)("\u00a78[\u00a7dAutoArmor\u00a78] \u00a77" + string)), false);
    }

    @EventHandler
    public void setEvent2Inner(Event2.Event2Inner event2Inner) {
        if (MC.mc.player == null || MC.mc.world == null) {
            return;
        }
        if (((Boolean)this.switchArmor.getValue()).booleanValue()) {
            if (((Boolean)this.autoSwitch.getValue()).booleanValue()) {
                this.m623();
            } else if (MC.mc.currentScreen == null) {
                boolean bl;
                boolean bl2 = bl = (Integer)this.switchArmorKey.getValue() != -1 && InputUtil.isKeyPressed((Window)MC.mc.getWindow(), (int)((Integer)this.switchArmorKey.getValue()));
                if (bl) {
                    if (!this.flag139) {
                        this.flag139 = true;
                        Object[] objectArray = new Object[1];
                        objectArray[0] = null;
                        Object[] objectArray2 = objectArray;
                        this.setObj29(objectArray2[0]);
                    }
                } else {
                    this.flag139 = false;
                }
            }
        }
        if (this.flag93 && !this.list32.isEmpty()) {
            if (MC.mc.currentScreen != null) {
                return;
            }
            if (this.count139 > 0) {
                --this.count139;
                return;
            }
            Data data = (Data)this.list32.remove(0);
            this.setObj41(data);
            this.count139 = this.switchDelay.getInt();
            if (this.list32.isEmpty()) {
                this.flag93 = false;
                if (this.notifyMode.getValue() == EMode2.Chat) {
                    this.setObj75("Switched to " + (this.type4 == Type.BLAST_PROTECTION ? "Blast Protection" : "Protection"));
                }
            }
            return;
        }
        if (MC.mc.currentScreen != null) {
            return;
        }
        if (((Boolean)this.noMove.getValue()).booleanValue() && this.isSet91()) {
            return;
        }
        if (this.count228 > 0) {
            --this.count228;
        } else {
            this.count228 = this.delay.getInt();
            this.m963();
        }
        if (((Boolean)this.switchArmor.getValue()).booleanValue()) {
            this.type7 = this.getType3();
        }
    }

    @EventHandler
    public void setObj17(Render2DEvent render2DEvent) {
        int n;
        String string;
        if (MC.mc.player == null) {
            return;
        }
        if (!((Boolean)this.switchArmor.getValue()).booleanValue() || this.notifyMode.getValue() != EMode2.Hud) {
            return;
        }
        if (this.type7 == Type.NONE) {
            return;
        }
        if (this.type7 == Type.PROTECTION) {
            string = "Armor: Protection";
            n = 0x55FF55;
        } else {
            string = "Armor: Blast Protection";
            n = 0xFF5555;
        }
        DrawContext drawContext = render2DEvent.getDrawContext();
        int n2 = MC.mc.getWindow().getScaledWidth();
        int n3 = MC.mc.getWindow().getScaledHeight();
        int n4 = MC.mc.textRenderer.getWidth(string);
        int n5 = (int)((double)n2 * ((Double)this.hudX.getValue() / 100.0)) - n4 / 2;
        int n6 = (int)((double)n3 * ((Double)this.hudY.getValue() / 100.0));
        n5 = Math.max(0, Math.min(n5, n2 - n4));
        Objects.requireNonNull(MC.mc.textRenderer);
        n6 = Math.max(0, Math.min(n6, n3 - 9));
        drawContext.drawText(MC.mc.textRenderer, string, n5, n6, n, true);
    }

    /*
     * Exception decompiling
     */
    private void m963() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:461)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:251)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:673)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:56)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:722)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        if (MC.mc.player == null) {
            return;
        }
        java.util.HashMap<EquipmentSlot, int[]> map = new java.util.HashMap<EquipmentSlot, int[]>(4);
        map.put(EquipmentSlot.FEET, new int[]{36, this.m139(MC.mc.player.getInventory().getStack(36)), -1, -1});
        map.put(EquipmentSlot.LEGS, new int[]{37, this.m139(MC.mc.player.getInventory().getStack(37)), -1, -1});
        map.put(EquipmentSlot.CHEST, new int[]{38, this.m139(MC.mc.player.getInventory().getStack(38)), -1, -1});
        map.put(EquipmentSlot.HEAD, new int[]{39, this.m139(MC.mc.player.getInventory().getStack(39)), -1, -1});
        for (int var4 = 0; var4 < 36; ++var4) {
            ItemStack var5 = MC.mc.player.getInventory().getStack(var4);
            if (AutoArmor.m328(var5) == null) continue;
            boolean var6 = !var5.isOf(Items.ELYTRA) || this.isSet95();
            if (!var6) continue;
            int var7 = this.m139(var5);
            EquipmentSlot var8 = AutoArmor.m328(var5);
            if (var8 == null) continue;
            for (java.util.Map.Entry<EquipmentSlot, int[]> var10 : map.entrySet()) {
                if (this.isSet95() && var10.getKey() == EquipmentSlot.CHEST) {
                    ItemStack var11 = MC.mc.player.getInventory().getStack(38);
                    boolean var12 = !var11.isEmpty() && var11.isOf(Items.ELYTRA) && var11.getDamage() < var11.getMaxDamage();
                    boolean var13 = var5.isOf(Items.ELYTRA) && var5.getDamage() < var5.getMaxDamage();
                    int[] var14 = var10.getValue();
                    boolean var15 = var14[2] != -1 && !MC.mc.player.getInventory().getStack(var14[2]).isEmpty() && MC.mc.player.getInventory().getStack(var14[2]).isOf(Items.ELYTRA) && MC.mc.player.getInventory().getStack(var14[2]).getDamage() < MC.mc.player.getInventory().getStack(var14[2]).getMaxDamage();
                    if (!var12 && !var15 && var13) {
                        var14[2] = var4;
                    }
                } else if (var7 > 0 && var10.getKey() == var8) {
                    int[] var16 = var10.getValue();
                    if (var7 > var16[1] && var7 > var16[3]) {
                        var16[2] = var4;
                        var16[3] = var7;
                    }
                }
            }
        }
        for (java.util.Map.Entry<EquipmentSlot, int[]> var17 : map.entrySet()) {
            int[] var18 = var17.getValue();
            if (var18[2] == -1) continue;
            int var19 = var18[2];
            int var20 = 44 - var18[0];
            if (var19 < 9) {
                switch (((HotbarSwapMode)((Object)this.hotbarSwap.getValue())).ordinal()) {
                    case 0: {
                        MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, var20, var19, SlotActionType.SWAP, (PlayerEntity)MC.mc.player);
                        break;
                    }
                    case 1: {
                        int var21 = MC.mc.player.getInventory().getSelectedSlot();
                        MC.mc.player.getInventory().setSelectedSlot(var19);
                        MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, Hand.MAIN_HAND);
                        MC.mc.player.getInventory().setSelectedSlot(var21);
                    }
                }
            } else {
                switch (((InventorySwapMode)((Object)this.inventorySwap.getValue())).ordinal()) {
                    case 0: {
                        MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, var19, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                        MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, var20, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                        if (var18[1] != -1) {
                            MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, var19, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                        }
                        break;
                    }
                    case 1: {
                        MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, var19, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                        MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, Hand.MAIN_HAND);
                        MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, var19, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    }
                }
            }
            return;
        }
    }

    private void m623() {
        Type type = null;
        Type type2;
        int n;
        block14: {
            boolean bl;
            block21: {
                block19: {
                    block20: {
                        AutoArmor autoArmor;
                        block18: {
                            block17: {
                                block16: {
                                    MinecraftClient minecraftClient;
                                    block15: {
                                        n = AutoArmor.getSwitchFlag();
                                        minecraftClient = MC.mc;
                                        if (n == 0) break block15;
                                        if (minecraftClient.player == null) break block16;
                                        minecraftClient = MC.mc;
                                    }
                                    if (minecraftClient.world != null && !this.flag93) break block17;
                                }
                                return;
                            }
                            type2 = Type.BLAST_PROTECTION;
                            autoArmor = this;
                            if (n == 0) break block18;
                            if (autoArmor.switchMode.getValue() != EMode.Item) break block14;
                            autoArmor = this;
                        }
                        bl = autoArmor.isSet104();
                        if (n == 0) break block19;
                        if (!bl) break block20;
                        type2 = Type.PROTECTION;
                        if (n != 0) break block14;
                    }
                    bl = MC.mc.player.isUsingItem();
                }
                if (n == 0) break block21;
                if (!bl) break block14;
                bl = false;
            }
            boolean bl2 = bl;
            double d = (Double)this.enemySwordRange.getValue();
            for (PlayerEntity playerEntity : MC.mc.world.getPlayers()) {
                block24: {
                    block25: {
                        boolean bl3;
                        block26: {
                            PlayerEntity playerEntity2;
                            block23: {
                                int n2;
                                block22: {
                                    if (n == 0) break block14;
                                    PlayerEntity playerEntity3 = playerEntity;
                                    if (n != 0) {
                                        if (playerEntity3 == MC.mc.player) continue;
                                        playerEntity3 = playerEntity;
                                    }
                                    n2 = playerEntity3.isAlive() ? 1 : 0;
                                    if (n != 0) {
                                        if (n2 == 0) continue;
                                        n2 = Client.manager.isFriend(playerEntity.getNameForScoreboard()) ? 1 : 0;
                                    }
                                    if (n == 0) break block22;
                                    if (n2 != 0) continue;
                                    playerEntity2 = MC.mc.player;
                                    if (n == 0) break block23;
                                    double d2 = (double)playerEntity2.distanceTo((Entity)playerEntity) - d;
                                    n2 = d2 == 0.0 ? 0 : (d2 < 0.0 ? -1 : 1);
                                }
                                if (n2 > 0) break block24;
                                playerEntity2 = playerEntity;
                            }
                            ItemStack itemStack = playerEntity2.getMainHandStack();
                            ItemStack itemStack2 = playerEntity.getOffHandStack();
                            bl3 = itemStack.isIn(ItemTags.SWORDS);
                            if (n == 0) break block25;
                            if (bl3) break block26;
                            bl3 = itemStack2.isIn(ItemTags.SWORDS);
                            if (n == 0) break block25;
                            if (!bl3) break block24;
                        }
                        bl3 = bl2 = true;
                    }
                    if (n != 0) break;
                }
                if (n != 0) continue;
            }
            if (bl2) {
                type2 = Type.PROTECTION;
            }
        }
        Type type3 = type2;
        if (n != 0) {
            if (type3 == this.type6) {
                return;
            }
            type3 = type = this.getType3();
        }
        if (n != 0) {
            if (type == type2) {
                this.type6 = type2;
                return;
            }
            this.type6 = type2;
            this.setObj29((Object)type2);
        }
    }

    private void setObj29(Object object) {
        Type type;
        block10: {
            block9: {
                Type type2 = (Type)((Object)object);
                boolean bl = false;
                if (MC.mc.player == null || MC.mc.world == null) {
                    return;
                }
                this.list32.clear();
                if (type2 == null) break block9;
                type = type2;
                if (!false) break block10;
            }
            Type type3 = this.getType3();
            type = type3 == Type.BLAST_PROTECTION ? Type.PROTECTION : Type.BLAST_PROTECTION;
        }
        if (((Boolean)this.switchHead.getValue()).booleanValue()) {
            this.m568(EquipmentSlot.HEAD, 39, (Object)type);
        }
        if (((Boolean)this.switchChest.getValue()).booleanValue()) {
            this.m568(EquipmentSlot.CHEST, 38, (Object)type);
        }
        if (((Boolean)this.switchLegs.getValue()).booleanValue()) {
            this.m568(EquipmentSlot.LEGS, 37, (Object)type);
        }
        if (((Boolean)this.switchFeet.getValue()).booleanValue()) {
            this.m568(EquipmentSlot.FEET, 36, (Object)type);
        }
        if (!this.list32.isEmpty()) {
            this.flag93 = true;
            this.count139 = 0;
            this.type4 = type;
            this.setObj47((Object)type);
        }
    }

    private void setObj47(Object object) {
        Type type = (Type)((Object)object);
        boolean bl = false;
        HeadMode headMode = type == Type.BLAST_PROTECTION ? HeadMode.Blast : HeadMode.Protection;
        if (((Boolean)this.switchHead.getValue()).booleanValue()) {
            this.head.setValueInternal((Object)headMode);
        }
        if (((Boolean)this.switchChest.getValue()).booleanValue()) {
            this.body.setValueInternal((Object)headMode);
        }
        if (((Boolean)this.switchLegs.getValue()).booleanValue()) {
            this.tights.setValueInternal((Object)headMode);
        }
        if (((Boolean)this.switchFeet.getValue()).booleanValue()) {
            this.feet.setValueInternal((Object)headMode);
        }
    }

    private Type getType3() {
        int n;
        int n2;
        block8: {
            int[][] nArrayArray;
            n2 = 0;
            int n3 = 0;
            int n4 = AutoArmor.getSwitchFlag();
            int[][] nArrayArray2 = new int[4][];
            int[] nArray = new int[2];
            nArray[0] = 39;
            boolean bl = (Boolean)this.switchHead.getValue();
            if (n4 != 0) {
                bl = bl;
            }
            nArray[1] = bl ? 1 : 0;
            nArrayArray2[0] = nArray;
            int[] nArray2 = new int[2];
            nArray2[0] = 38;
            boolean bl2 = (Boolean)this.switchChest.getValue();
            if (n4 != 0) {
                bl2 = bl2;
            }
            nArray2[1] = bl2 ? 1 : 0;
            nArrayArray2[1] = nArray2;
            int[] nArray3 = new int[2];
            nArray3[0] = 37;
            boolean bl3 = (Boolean)this.switchLegs.getValue();
            if (n4 != 0) {
                bl3 = bl3;
            }
            nArray3[1] = bl3 ? 1 : 0;
            nArrayArray2[2] = nArray3;
            int[] nArray4 = new int[2];
            nArray4[0] = 36;
            boolean bl4 = (Boolean)this.switchFeet.getValue();
            if (n4 != 0) {
                bl4 = bl4;
            }
            nArray4[1] = bl4 ? 1 : 0;
            nArrayArray2[3] = nArray4;
            for (int[] nArray5 : nArrayArray = nArrayArray2) {
                n = nArray5[1];
                if (n4 != 0) {
                    Type type;
                    if (n == 0) continue;
                    Type type2 = type = this.m912(MC.mc.player.getInventory().getStack(nArray5[0]));
                    Type type3 = Type.PROTECTION;
                    if (n4 != 0) {
                        if (type2 == type3) {
                            ++n2;
                            if (n4 != 0) continue;
                        }
                        type2 = type;
                        type3 = Type.BLAST_PROTECTION;
                    }
                    if (type2 != type3) continue;
                    ++n3;
                    if (n4 != 0) continue;
                }
                break block8;
            }
            n = n3;
        }
        return n > n2 ? Type.BLAST_PROTECTION : Type.PROTECTION;
    }

    private void m568(Object object, int n, Object object2) {
        int n2;
        int n3;
        int n4;
        int n5;
        EquipmentSlot equipmentSlot;
        block10: {
            ItemStack itemStack;
            Type type;
            block13: {
                block12: {
                    ItemStack itemStack2;
                    block11: {
                        equipmentSlot = (EquipmentSlot)object;
                        n5 = n;
                        type = (Type)((Object)object2);
                        itemStack = MC.mc.player.getInventory().getStack(n5);
                        n4 = AutoArmor.getSwitchFlag();
                        itemStack2 = itemStack;
                        if (n4 == 0) break block11;
                        if (itemStack2.isEmpty()) break block12;
                        itemStack2 = itemStack;
                    }
                    if (AutoArmor.m328(itemStack2) != null) break block13;
                }
                return;
            }
            if (this.m912(itemStack) == type) {
                return;
            }
            n3 = -1;
            int n6 = -1;
            int n7 = 0;
            while (n7 < 36) {
                block19: {
                    block15: {
                        int n8 = 0;
                        block17: {
                            int n9;
                            ItemStack itemStack3;
                            block18: {
                                Object object3;
                                block16: {
                                    ItemStack itemStack4;
                                    block14: {
                                        itemStack4 = itemStack3 = MC.mc.player.getInventory().getStack(n7);
                                        if (n4 == 0) break block14;
                                        n2 = itemStack4.isEmpty() ? 1 : 0;
                                        if (n4 == 0) break block10;
                                        if (n2 != 0) break block15;
                                        itemStack4 = itemStack3;
                                    }
                                    if (AutoArmor.m328(itemStack4) != equipmentSlot) break block15;
                                    object3 = this.m912(itemStack3);
                                    if (n4 == 0) break block16;
                                    if (object3 != type) break block15;
                                    object3 = this.ignoreCurse.getValue();
                                }
                                n9 = ((Boolean)object3).booleanValue() ? 1 : 0;
                                if (n4 == 0) break block17;
                                if (n9 == 0) break block18;
                                n9 = this.m955(itemStack3) ? 1 : 0;
                                if (n4 == 0) break block17;
                                if (n9 != 0) break block15;
                            }
                            n9 = n8 = this.m337(itemStack3);
                        }
                        if (n4 == 0) break block19;
                        if (n8 > n6) {
                            n6 = n8;
                            n3 = n7;
                        }
                    }
                    ++n7;
                }
                if (n4 != 0) continue;
            }
            n2 = n3;
        }
        if (n4 != 0 && n2 != -1) {
            n2 = this.list32.add(new Data(equipmentSlot, n5, n3)) ? 1 : 0;
        }
    }

    private void setObj41(Object object) {
        block13: {
            int n;
            int n2;
            block12: {
                Data data = (Data)object;
                boolean bl = false;
                if (MC.mc.player == null || MC.mc.interactionManager == null) {
                    return;
                }
                n2 = 44 - data.count3;
                n = data.count4;
                if (n >= 9) break block12;
                switch (((HotbarSwapMode)((Object)this.hotbarSwap.getValue())).ordinal()) {
                    case 0: {
                        MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, n2, n, SlotActionType.SWAP, (PlayerEntity)MC.mc.player);
                        if (!false) break;
                    }
                    case 1: {
                        int n3 = MC.mc.player.getInventory().getSelectedSlot();
                        MC.mc.player.getInventory().setSelectedSlot(n);
                        MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, Hand.MAIN_HAND);
                        MC.mc.player.getInventory().setSelectedSlot(n3);
                    }
                }
                if (!false) break block13;
            }
            switch (((InventorySwapMode)((Object)this.inventorySwap.getValue())).ordinal()) {
                case 0: {
                    MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, n, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, n2, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, n, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    if (!false) break;
                }
                case 1: {
                    MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, n, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    MC.mc.interactionManager.interactItem((PlayerEntity)MC.mc.player, Hand.MAIN_HAND);
                    MC.mc.interactionManager.clickSlot(MC.mc.player.playerScreenHandler.syncId, n, 0, SlotActionType.PICKUP, (PlayerEntity)MC.mc.player);
                    break;
                }
            }
        }
    }

    private int m139(Object object) {
        int n;
        block24: {
            int n2;
            ItemStack itemStack;
            block27: {
                EquipmentSlot equipmentSlot;
                int n3;
                block26: {
                    ItemStack itemStack2;
                    block25: {
                        itemStack = (ItemStack)object;
                        n3 = AutoArmor.getSwitchFlag();
                        itemStack2 = itemStack;
                        if (n3 == 0) break block25;
                        if (AutoArmor.m328(itemStack2) != null) break block26;
                        itemStack2 = itemStack;
                    }
                    int n4 = itemStack2.isEmpty() ? 1 : 0;
                    if (n3 != 0) {
                        n4 = n4 != 0 ? -1 : 0;
                    }
                    return n4;
                }
                n2 = 0;
                ItemStack itemStack3 = itemStack;
                if (n3 != 0) {
                    if (itemStack3.isOf(Items.ELYTRA)) {
                        int n5 = itemStack.getDamage();
                        if (n3 != 0) {
                            if (n5 >= itemStack.getMaxDamage()) {
                                return 0;
                            }
                            n5 = 1;
                        }
                        n2 = n5;
                    }
                    itemStack3 = itemStack;
                }
                if ((equipmentSlot = AutoArmor.m328(itemStack3)) == null) {
                    return 0;
                }
                int n6 = 1;
                int n7 = 1;
                n = Lambda.counts16[equipmentSlot.ordinal()];
                if (n3 != 0) {
                    switch (n) {
                        case 1: {
                            if (this.head.getValue() == HeadMode.Protection) {
                                n7 = 2;
                                if (n3 != 0) break;
                            }
                            n6 = 2;
                            if (n3 != 0) break;
                        }
                        case 2: {
                            if (this.body.getValue() == HeadMode.Protection) {
                                n7 = 2;
                                if (n3 != 0) break;
                            }
                            n6 = 2;
                            if (n3 != 0) break;
                        }
                        case 3: {
                            if (this.tights.getValue() == HeadMode.Protection) {
                                n7 = 2;
                                if (n3 != 0) break;
                            }
                            n6 = 2;
                            if (n3 != 0) break;
                        }
                        case 4: {
                            if (this.feet.getValue() == HeadMode.Protection) {
                                n7 = 2;
                                if (n3 != 0) break;
                            }
                            n6 = 2;
                            break;
                        }
                    }
                    n = itemStack.hasEnchantments() ? 1 : 0;
                }
                if (n3 == 0) break block24;
                if (n == 0) break block27;
                ItemEnchantmentsComponent itemEnchantmentsComponent = itemStack.getEnchantments();
                for (Object2IntMap.Entry entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
                    block30: {
                        int n8 = 0;
                        block34: {
                            block33: {
                                block31: {
                                    RegistryEntry registryEntry;
                                    block32: {
                                        block28: {
                                            block29: {
                                                registryEntry = (RegistryEntry)entry.getKey();
                                                n = registryEntry.matchesKey(Enchantments.PROTECTION) ? 1 : 0;
                                                if (n3 == 0) break block24;
                                                if (n3 == 0) break block28;
                                                if (n == 0) break block29;
                                                n2 += entry.getIntValue() * n7;
                                                if (n3 != 0) break block30;
                                            }
                                            n8 = registryEntry.matchesKey(Enchantments.BLAST_PROTECTION) ? 1 : 0;
                                        }
                                        if (n3 == 0) break block31;
                                        if (n8 == 0) break block32;
                                        n2 += entry.getIntValue() * n6;
                                        if (n3 != 0) break block30;
                                    }
                                    n8 = registryEntry.matchesKey(Enchantments.BINDING_CURSE) ? 1 : 0;
                                }
                                if (n3 == 0) break block33;
                                if (n8 == 0) break block30;
                                n8 = ((Boolean)this.ignoreCurse.getValue()).booleanValue() ? 1 : 0;
                            }
                            if (n3 == 0) break block34;
                            if (n8 == 0) break block30;
                            n8 = -999;
                        }
                        return n8;
                    }
                    if (n3 != 0) continue;
                }
            }
            n = AutoArmor.m672(itemStack) + n2;
        }
        return n;
    }

    private int m337(Object object) {
        int n;
        block5: {
            ItemStack itemStack = (ItemStack)object;
            boolean bl = false;
            if (AutoArmor.m328(itemStack) == null) {
                return 0;
            }
            n = AutoArmor.m672(itemStack);
            if (!itemStack.hasEnchantments()) break block5;
            for (Object2IntMap.Entry entry : itemStack.getEnchantments().getEnchantmentEntries()) {
                block7: {
                    block6: {
                        RegistryEntry registryEntry = (RegistryEntry)entry.getKey();
                        if (registryEntry.matchesKey(Enchantments.PROTECTION)) break block6;
                        if (!registryEntry.matchesKey(Enchantments.BLAST_PROTECTION)) break block7;
                    }
                    n += entry.getIntValue();
                }
                if (!false) continue;
            }
        }
        return n;
    }

    private Type m912(Object object) {
        ItemStack itemStack;
        block12: {
            block11: {
                itemStack = (ItemStack)object;
                boolean bl = false;
                if (itemStack.isEmpty()) break block11;
                if (itemStack.hasEnchantments()) break block12;
            }
            return Type.NONE;
        }
        int n = 0;
        int n2 = 0;
        for (Object2IntMap.Entry entry : itemStack.getEnchantments().getEnchantmentEntries()) {
            block14: {
                RegistryEntry registryEntry;
                block13: {
                    registryEntry = (RegistryEntry)entry.getKey();
                    if (!registryEntry.matchesKey(Enchantments.PROTECTION)) break block13;
                    n = entry.getIntValue();
                    if (!false) break block14;
                }
                if (registryEntry.matchesKey(Enchantments.BLAST_PROTECTION)) {
                    n2 = entry.getIntValue();
                }
            }
            if (!false) continue;
            break;
        }
        if (n2 > n) {
            return Type.BLAST_PROTECTION;
        }
        if (n > 0) {
            return Type.PROTECTION;
        }
        if (n2 > 0) {
            return Type.BLAST_PROTECTION;
        }
        return Type.NONE;
    }

    private boolean m955(Object object) {
        boolean bl;
        block6: {
            ItemStack itemStack = (ItemStack)object;
            int n = AutoArmor.getSwitchFlag();
            ItemStack itemStack2 = itemStack;
            if (n != 0) {
                if (!itemStack2.hasEnchantments()) {
                    return false;
                }
                itemStack2 = itemStack;
            }
            for (Object2IntMap.Entry entry : itemStack2.getEnchantments().getEnchantmentEntries()) {
                block8: {
                    boolean bl2 = false;
                    block7: {
                        bl = ((RegistryEntry)entry.getKey()).matchesKey(Enchantments.BINDING_CURSE);
                        if (n == 0) break block6;
                        if (n == 0) break block7;
                        if (!bl) break block8;
                        bl2 = true;
                    }
                    return bl2;
                }
                if (n != 0) continue;
            }
            bl = false;
        }
        return bl;
    }

    private static int m672(Object object) {
        block30: {
            block29: {
                ItemStack itemStack;
                block28: {
                    block27: {
                        block26: {
                            block25: {
                                block24: {
                                    block23: {
                                        block22: {
                                            block21: {
                                                block20: {
                                                    block19: {
                                                        itemStack = (ItemStack)object;
                                                        boolean bl = false;
                                                        if (itemStack.isOf(Items.LEATHER_BOOTS)) break block19;
                                                        if (itemStack.isOf(Items.LEATHER_HELMET)) break block19;
                                                        if (itemStack.isOf(Items.CHAINMAIL_BOOTS)) break block19;
                                                        if (!itemStack.isOf(Items.GOLDEN_BOOTS)) break block20;
                                                    }
                                                    return 1;
                                                }
                                                if (itemStack.isOf(Items.LEATHER_LEGGINGS)) break block21;
                                                if (itemStack.isOf(Items.GOLDEN_HELMET)) break block21;
                                                if (itemStack.isOf(Items.IRON_HELMET)) break block21;
                                                if (itemStack.isOf(Items.IRON_BOOTS)) break block21;
                                                if (!itemStack.isOf(Items.TURTLE_HELMET)) break block22;
                                            }
                                            return 2;
                                        }
                                        if (itemStack.isOf(Items.LEATHER_CHESTPLATE)) break block23;
                                        if (itemStack.isOf(Items.GOLDEN_LEGGINGS)) break block23;
                                        if (itemStack.isOf(Items.DIAMOND_BOOTS)) break block23;
                                        if (itemStack.isOf(Items.DIAMOND_HELMET)) break block23;
                                        if (itemStack.isOf(Items.NETHERITE_HELMET)) break block23;
                                        if (!itemStack.isOf(Items.NETHERITE_BOOTS)) break block24;
                                    }
                                    return 3;
                                }
                                if (itemStack.isOf(Items.CHAINMAIL_LEGGINGS)) {
                                    return 4;
                                }
                                if (itemStack.isOf(Items.CHAINMAIL_CHESTPLATE)) break block25;
                                if (itemStack.isOf(Items.GOLDEN_CHESTPLATE)) break block25;
                                if (!itemStack.isOf(Items.IRON_LEGGINGS)) break block26;
                            }
                            return 5;
                        }
                        if (itemStack.isOf(Items.IRON_CHESTPLATE)) break block27;
                        if (itemStack.isOf(Items.DIAMOND_LEGGINGS)) break block27;
                        if (!itemStack.isOf(Items.NETHERITE_LEGGINGS)) break block28;
                    }
                    return 6;
                }
                if (itemStack.isOf(Items.DIAMOND_CHESTPLATE)) break block29;
                if (!itemStack.isOf(Items.NETHERITE_CHESTPLATE)) break block30;
            }
            return 8;
        }
        return 0;
    }

    private static EquipmentSlot m328(Object object) {
        block20: {
            block19: {
                ItemStack itemStack;
                block18: {
                    block17: {
                        block16: {
                            block15: {
                                block14: {
                                    block13: {
                                        itemStack = (ItemStack)object;
                                        boolean bl = false;
                                        if (itemStack.isOf(Items.ELYTRA)) {
                                            return EquipmentSlot.CHEST;
                                        }
                                        if (itemStack.isOf(Items.LEATHER_HELMET)) break block13;
                                        if (itemStack.isOf(Items.CHAINMAIL_HELMET)) break block13;
                                        if (itemStack.isOf(Items.GOLDEN_HELMET)) break block13;
                                        if (itemStack.isOf(Items.IRON_HELMET)) break block13;
                                        if (itemStack.isOf(Items.DIAMOND_HELMET)) break block13;
                                        if (itemStack.isOf(Items.NETHERITE_HELMET)) break block13;
                                        if (!itemStack.isOf(Items.TURTLE_HELMET)) break block14;
                                    }
                                    return EquipmentSlot.HEAD;
                                }
                                if (itemStack.isOf(Items.LEATHER_CHESTPLATE)) break block15;
                                if (itemStack.isOf(Items.CHAINMAIL_CHESTPLATE)) break block15;
                                if (itemStack.isOf(Items.GOLDEN_CHESTPLATE)) break block15;
                                if (itemStack.isOf(Items.IRON_CHESTPLATE)) break block15;
                                if (itemStack.isOf(Items.DIAMOND_CHESTPLATE)) break block15;
                                if (!itemStack.isOf(Items.NETHERITE_CHESTPLATE)) break block16;
                            }
                            return EquipmentSlot.CHEST;
                        }
                        if (itemStack.isOf(Items.LEATHER_LEGGINGS)) break block17;
                        if (itemStack.isOf(Items.CHAINMAIL_LEGGINGS)) break block17;
                        if (itemStack.isOf(Items.GOLDEN_LEGGINGS)) break block17;
                        if (itemStack.isOf(Items.IRON_LEGGINGS)) break block17;
                        if (itemStack.isOf(Items.DIAMOND_LEGGINGS)) break block17;
                        if (!itemStack.isOf(Items.NETHERITE_LEGGINGS)) break block18;
                    }
                    return EquipmentSlot.LEGS;
                }
                if (itemStack.isOf(Items.LEATHER_BOOTS)) break block19;
                if (itemStack.isOf(Items.CHAINMAIL_BOOTS)) break block19;
                if (itemStack.isOf(Items.GOLDEN_BOOTS)) break block19;
                if (itemStack.isOf(Items.IRON_BOOTS)) break block19;
                if (!itemStack.isOf(Items.DIAMOND_BOOTS) && !itemStack.isOf(Items.NETHERITE_BOOTS)) break block20;
            }
            return EquipmentSlot.FEET;
        }
        return null;
    }

    public static void setInt14(int n) {
        count177 = n;
    }

    public static int getInt84() {
        return count177;
    }

    public static int getSwitchFlag() {
        boolean bl = false;
        return 111;
    }

    static {
        boolean bl = false;
        String string = "CT,\u00a5z\u00f4G\u00ad\u0089F\u0011;\u0001w\u00b1\u0084\\\u00c6\u009fo\u00d2ge\u00a7[\u00ab\u001fK\n*\u00fcL\u00e9\u00ee\u0001\u0003\u008c\u00a5\u001e\u0004\u008d,N\u00fa\u0014\u00b4c\u008a6\u00dd\u0006l\u00e4\u00d83)\u00ba\u00a4\u00af\u0088\u0010\u000b\u00df\u00e8:\u0005\t\u00b2\u0095<\u00f2\u0010\\\u00c3\u00f4(\u007f\u00ea\u000f25\u001a\u0083\u0002\u001flOF\u000b*\u0016\u0001\u00ed\u00df\u00fd\u00fd\u008b\u0010s}\u0004\u00f1(D<\u0006\u00cd\u00f3\u0014\u0090\u00a5\u00d6\u0017\\{\u009b\u00fe\u0019\u00b5u\u00b0\u0007i\u00b0\u00cc\u00f9yP\u00fbVZ\u008b$](`\n>\u0098\u00e61\u009f\u00a8\u00a7gM\u0011\u000b\u00f8\u000e[\u00ee\u0094\u009d\u0093\u008a3\u00ed<\n\u0015\u0015E\u008b\u00ec\u009e0\u00d3g\u0089\u0004j\u00a5y\u00db\n\u00ca\u009fxy0d\u00f1\u00d3JD\u0006\u00c4\u00ddF\u0094\u0082\u00a7\f\u0001\u00aadzs\u000fN\u00d9\u00c1\u00b3\u00b1t\u000by\u00b55\u0090\u00af'\u00c0a\u00a0\u0090\u008f\ni!i\u000b\u00d2!Y\u001d!\n\u000b!O`\u00c6\u00f3\u0098z=\u00aa\u00e0%\n\u00ee_\u00d4\u009d\u00eb\u0094gF*N\u0004\u00c5\u008e\u0089\u0091\u000esz*`\u00baLS\u00a7\u008f\u00b3\u0081g\u00bb\u00fd\n5\u0017\u00e2\u00cd\u00a3\u00f9y\u0006\u00eey\t\u00b8s\u001e\u0018\u00a0!ZN\u0018\u0004r`\u007fM\u0005\u00f1\u0097\u00b3\u001f\u00d2,\u0012fB\u00c8\u0006n\u0002r\u0080\u00bfo\u00e0\u00bc\u0097\u009dF\u001b<\u0010b\u0016\ti\u001cl\u00de\u001a?V\u00c2\u00f6b\u00ef\u00fa\u00fcE\u008eGNf;\u00d5\u00ec\u00f7\r%\u00c1\u00a6<\u00da\u00e8\u00dd\u00ac\u00a1\u00ee\u00d7]\u00c1";
        int n = 363;
        int n2 = 10;
        AutoArmor.setInt14(0);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum InventorySwapMode {
      ClickSlot, Pick;

      private InventorySwapMode() {}



        private static InventorySwapMode[] getInventorySwapModeArray() {
            return new InventorySwapMode[]{ClickSlot, Pick};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    record Data(EquipmentSlot field3, int count3, int count4) {
    }

    @Environment(value=EnvType.CLIENT)
    public static enum HeadMode {
      Protection, Blast;

      private HeadMode() {}



        private static HeadMode[] getHeadModeArray() {
            return new HeadMode[]{Protection, Blast};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum HotbarSwapMode {
      Swap, Switch;

      private HotbarSwapMode() {}



        private static HotbarSwapMode[] getHotbarSwapModeArray() {
            return new HotbarSwapMode[]{Swap, Switch};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode {
      Item;

      private EMode() {}



        private static EMode[] getObjArray11() {
            return new EMode[]{Item};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts16 = new int[EquipmentSlot.values().length];

        static {
            try {
                Lambda.counts16[EquipmentSlot.HEAD.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts16[EquipmentSlot.CHEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts16[EquipmentSlot.LEGS.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts16[EquipmentSlot.FEET.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum EMode2 {
      Chat, Hud, None;

      private EMode2() {}



        private static EMode2[] getObjArray19() {
            return new EMode2[]{Chat, Hud, None};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static enum Type {
      PROTECTION, BLAST_PROTECTION, NONE;

      private Type() {}



        private static Type[] getTypeArray10() {
            return new Type[]{PROTECTION, BLAST_PROTECTION, NONE};
        }
    
   }
}

