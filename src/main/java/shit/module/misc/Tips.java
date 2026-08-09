/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.component.DataComponentTypes
 *  net.minecraft.component.type.PotionContentsComponent
 *  net.minecraft.entity.effect.StatusEffectInstance
 *  net.minecraft.entity.effect.StatusEffects
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket
 *  net.minecraft.registry.entry.RegistryEntry
 *  net.minecraft.sound.SoundCategory
 *  net.minecraft.sound.SoundEvents
 */
package shit.module.misc;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import shit.Client;
import shit.command.CommandManager;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.event.PlayerEvent;
import shit.event.Render2DEvent;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.render.LineRenderer2;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Tips
extends Module {
    public static Tips INSTANCE;
    private final BooleanSetting visualRange;
    private final BooleanSetting friends;
    private final BooleanSetting popCounter;
    private final BooleanSetting deathCoords;
    private final BooleanSetting serverLag;
    private final BooleanSetting lagBack;
    private final BooleanSetting potion;
    private final BooleanSetting resistanceLevelCheck;
    private final NumberSetting yOffset;
    private final BooleanSetting healthWarn;
    private final NumberSetting minHealth;
    private final NumberSetting xPos;
    private final NumberSetting yPos;
    private final BooleanSetting hWPlaySound;
    private final BooleanSetting blinkEffect;
    private final BooleanSetting showHealthText;
    private final BooleanSetting shadowText;
    private final NumberSetting iconSize;
    private final DecimalFormat decimalFormat2;
    private final Helper7 helper713;
    private final Helper7 helper743;
    private final Map map44;
    private final Map map37;
    private final Set set;
    private long time74;
    private boolean flag170;

        public Tips() {
        super("Tips", "Displays lag, visual range, pop counter and health warnings.", Category.MISC);
        this.visualRange = (BooleanSetting)this.registerSetting(new BooleanSetting("VisualRange", false));
        this.friends = (BooleanSetting)this.registerSetting(new BooleanSetting("Friends", false));
        this.popCounter = (BooleanSetting)this.registerSetting(new BooleanSetting("PopCounter", true));
        this.deathCoords = (BooleanSetting)this.registerSetting(new BooleanSetting("DeathCoords", true));
        this.serverLag = (BooleanSetting)this.registerSetting(new BooleanSetting("ServerLag", true));
        this.lagBack = (BooleanSetting)this.registerSetting(new BooleanSetting("LagBack", true));
        this.potion = (BooleanSetting)this.registerSetting(new BooleanSetting("Potion", true));
        this.resistanceLevelCheck = (BooleanSetting)this.registerSetting(new BooleanSetting("ResistanceLevelCheck", true));
        this.yOffset = (NumberSetting)this.registerSetting(new NumberSetting("YOffset", 0.0, -200.0, 200.0, 1.0));
        this.healthWarn = (BooleanSetting)this.registerSetting(new BooleanSetting("HealthWarn", true));
        this.minHealth = (NumberSetting)this.registerSetting(new NumberSetting("MinHealth", 6.0, 0.0, 20.0, 1.0));
        this.xPos = (NumberSetting)this.registerSetting(new NumberSetting("XPos", 469.0, 0.0, 1000.0, 1.0));
        this.yPos = (NumberSetting)this.registerSetting(new NumberSetting("YPos", 378.0, 0.0, 1000.0, 1.0));
        this.hWPlaySound = (BooleanSetting)this.registerSetting(new BooleanSetting("HWPlaySound", false));
        this.blinkEffect = (BooleanSetting)this.registerSetting(new BooleanSetting("BlinkEffect", true));
        this.showHealthText = (BooleanSetting)this.registerSetting(new BooleanSetting("ShowHealthText", true));
        this.shadowText = (BooleanSetting)this.registerSetting(new BooleanSetting("ShadowText", true));
        this.iconSize = (NumberSetting)this.registerSetting(new NumberSetting("IconSize", 32.0, 16.0, 64.0, 1.0));
        this.decimalFormat2 = new DecimalFormat("0.0");
        this.helper713 = new Helper7();
        this.helper743 = new Helper7();
        this.map44 = new HashMap();
        this.map37 = new HashMap();
        this.set = new HashSet();
    }

    @Override
    public void onEnable() {
        this.map37.clear();
        this.set.clear();
        this.flag170 = false;
    }

    @Override
    public void onDisable() {
        this.map37.clear();
        this.set.clear();
        this.flag170 = false;
    }

    @EventHandler
    private void setPacketEventInner4(PacketEvent.PacketEventInner packetEventInner) {
        this.helper713.resetTimer();
        if (packetEventInner.getPacket() instanceof PlayerPositionLookS2CPacket) {
            this.helper743.resetTimer();
        }
    }

    @EventHandler
    private void setEvent2Inner15(Event2.Event2Inner event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        this.m413();
        this.m915();
        this.m882();
    }

    @EventHandler
    private void setPlayerEvent2(PlayerEvent playerEvent) {
        if (!((Boolean)this.popCounter.getValue()).booleanValue()) {
            return;
        }
        PlayerEntity playerEntity = playerEvent.getPlayer4();
        int n = (Integer)this.map44.merge(playerEntity.getUuid(), 1, (java.util.function.BiFunction<Integer, Integer, Integer>)Integer::sum);
        String string = playerEntity == MC.mc.player ? "You" : playerEntity.getName().getString();
        String string2 = Tips.m346(n, "totem");
        int n2 = n;
        String string3 = string;
        this.m387("\u00a7f" + string3 + " \u00a7rpopped \u00a7f" + n2 + "\u00a7r " + string2 + ".", playerEntity);
    }

    @EventHandler
    private void setObj9(Render2DEvent render2DEvent) {
        float f;
        String string;
        if (Module.isNotInGame()) {
            return;
        }
        int n = MC.mc.getWindow().getScaledWidth() / 2;
        int n2 = 19;
        if (((Boolean)this.serverLag.getValue()).booleanValue() && this.helper713.hasPassedSeconds(1.4)) {
            this.m751(render2DEvent, "Server not responding (" + this.decimalFormat2.format((double)this.helper713.getElapsed() / 1000.0) + "s)", n, n2, -4325376);
            n2 += 9;
        }
        if (((Boolean)this.lagBack.getValue()).booleanValue() && !this.helper743.hasPassedSeconds(1.5)) {
            this.m751(render2DEvent, "Lagback (" + this.decimalFormat2.format((double)(1500L - this.helper743.getElapsed()) / 1000.0) + "s)", n, n2, -4325376);
        }
        if (((Boolean)this.potion.getValue()).booleanValue() && !(string = this.getText60()).isEmpty()) {
            int n3 = MC.mc.getWindow().getScaledHeight() / 2;
            this.m751(render2DEvent, string, n, n3 + 9 - this.yOffset.getInt(), -1);
        }
        if (((Boolean)this.healthWarn.getValue()).booleanValue() && (double)(f = MC.mc.player.getHealth() + MC.mc.player.getAbsorptionAmount()) <= (Double)this.minHealth.getValue()) {
            this.m966(render2DEvent, this.xPos.getInt(), this.yPos.getInt(), f);
        }
    }

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    private void m413() {
        if (!((Boolean)this.visualRange.getValue()).booleanValue()) {
            this.map37.clear();
            this.flag170 = false;
            return;
        }
        HashMap<UUID, String> current = new HashMap<UUID, String>();
        Iterator iterator = MC.mc.world.getPlayers().iterator();
        while (iterator.hasNext()) {
            PlayerEntity playerEntity = (PlayerEntity)iterator.next();
            if (playerEntity == MC.mc.player) continue;
            current.put(playerEntity.getUuid(), playerEntity.getName().getString());
            if (this.flag170 && !this.map37.containsKey(playerEntity.getUuid()) && this.m501(playerEntity)) {
                CommandManager.sendFeedback(this.m734(playerEntity) + "\u00a7f entered your visual range.");
                this.m839();
            }
        }
        if (this.flag170) {
            for (Object object : this.map37.entrySet()) {
                Map.Entry entry = (Map.Entry)object;
                if (current.containsKey(entry.getKey())) continue;
                CommandManager.sendFeedback("\u00a7f" + entry.getValue() + "\u00a7f left your visual range.");
                this.m839();
            }
        }
        this.map37.clear();
        this.map37.putAll(current);
        this.flag170 = true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m501(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        String string = IRC.getText7();
        boolean bl = (Boolean)this.friends.getValue();
        if (string == null) return bl;
        if (bl) return true;
        bl = Client.manager.isFriend(playerEntity.getName().getString());
        if (string == null) return bl;
        if (bl) return false;
        return true;
    }

    private String m734(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        String string = playerEntity.getName().getString();
        return Client.manager.isFriend(string) ? "\u00a7b" + string : "\u00a7f" + string;
    }

    private void m839() {
        MC.mc.world.playSoundClient(MC.mc.player.getX(), MC.mc.player.getY(), MC.mc.player.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 1.9f, false);
    }

    private void m915() {
        block5: {
            boolean bl;
            HashSet<UUID> hashSet = new HashSet<UUID>();
            Iterator iterator = MC.mc.world.getPlayers().iterator();
            String string = IRC.getText7();
            while (iterator.hasNext()) {
                block8: {
                    Tips tips;
                    PlayerEntity playerEntity;
                    block7: {
                        boolean bl2 = false;
                        block6: {
                            boolean bl3;
                            playerEntity = (PlayerEntity)iterator.next();
                            hashSet.add(playerEntity.getUuid());
                            bl = bl3 = playerEntity.isAlive();
                            if (string == null) break block5;
                            if (string == null) break block6;
                            if (bl) {
                                this.set.add(playerEntity.getUuid());
                                if (string != null) continue;
                            }
                            tips = this;
                            if (string == null) break block7;
                            bl2 = tips.set.remove(playerEntity.getUuid());
                        }
                        if (!bl2) break block8;
                        tips = this;
                    }
                    tips.setObj40(playerEntity);
                }
                if (string != null) continue;
            }
            bl = this.set.retainAll(hashSet);
        }
    }

    /*
     * Unable to fully structure code
     */
    private void setObj40(Object var1_1) {
        PlayerEntity playerEntity = (PlayerEntity)var1_1;
        if (((Boolean)this.popCounter.getValue()).booleanValue()) {
            int pops = (Integer)this.map44.getOrDefault(playerEntity.getUuid(), 0);
            String name = playerEntity == MC.mc.player ? "You" : playerEntity.getName().getString();
            if (pops > 0) {
                String suffix = Tips.m346(pops, "totem");
                this.m387("\u00a7f" + name + "\u00a7r died after popping \u00a7f" + pops + "\u00a7r " + suffix + ".", playerEntity);
            } else {
                this.m387("\u00a7f" + name + "\u00a7r died.", playerEntity);
            }
            this.map44.remove(playerEntity.getUuid());
        }
        if (((Boolean)this.deathCoords.getValue()).booleanValue()) {
            if (playerEntity != MC.mc.player) {
                return;
            }
            int x = playerEntity.getBlockX();
            int y = playerEntity.getBlockY();
            int z = playerEntity.getBlockZ();
            CommandManager.sendFeedback("\u00a74You died at " + x + ", " + y + ", " + z);
        }
    }

    private void m882() {
        block11: {
            long l;
            block10: {
                String string;
                block9: {
                    block8: {
                        boolean bl;
                        block7: {
                            string = IRC.getText7();
                            bl = (Boolean)this.healthWarn.getValue();
                            if (string == null) break block7;
                            if (!bl) break block8;
                            bl = (Boolean)this.hWPlaySound.getValue();
                        }
                        if (bl) break block9;
                    }
                    return;
                }
                float f = MC.mc.player.getHealth() + MC.mc.player.getAbsorptionAmount();
                if ((double)f > (Double)this.minHealth.getValue()) {
                    return;
                }
                l = System.currentTimeMillis();
                if (string == null) break block10;
                if (l - this.time74 <= 1000L) break block11;
                MC.mc.world.playSoundClient(MC.mc.player.getX(), MC.mc.player.getY(), MC.mc.player.getZ(), SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
            }
            this.time74 = l;
        }
    }

    private String getText60() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = IRC.getText7();
        int n = this.getInt8();
        if (string != null) {
            if (n > 0) {
                stringBuilder.append("\u00a7e").append(n);
            }
            this.m166(stringBuilder, StatusEffects.RESISTANCE, "\u00a79", (Boolean)this.resistanceLevelCheck.getValue());
            this.m166(stringBuilder, StatusEffects.STRENGTH, "\u00a74", false);
            this.m166(stringBuilder, StatusEffects.SPEED, "\u00a7b", false);
        }
        return stringBuilder.toString();
    }

    private int getInt8() {
        int n;
        block9: {
            String string = IRC.getText7();
            if (MC.mc.player == null) {
                return 0;
            }
            int n2 = 0;
            block0: for (int i = 0; i < 36; ++i) {
                ItemStack itemStack;
                Object object = itemStack = MC.mc.player.getInventory().getStack(i);
                if (string != null) {
                    n = itemStack.isEmpty() ? 1 : 0;
                    if (string == null) break block9;
                    if (n != 0) continue;
                    object = itemStack.getOrDefault(DataComponentTypes.POTION_CONTENTS, (Object)PotionContentsComponent.DEFAULT);
                }
                block1: while (true) {
                    PotionContentsComponent potionContentsComponent = (PotionContentsComponent)object;
                    for (StatusEffectInstance statusEffectInstance : potionContentsComponent.getEffects()) {
                        block11: {
                            block10: {
                                if (string == null) break block10;
                                object = statusEffectInstance.getEffectType();
                                if (string == null) continue block1;
                                if (object != StatusEffects.RESISTANCE) break block11;
                                ++n2;
                            }
                            if (string != null) continue block0;
                        }
                        if (string != null) continue;
                    }
                    break;
                }
                if (string != null) continue;
            }
            n = n2;
        }
        return n;
    }

    private void m166(Object object, Object object2, Object object3, boolean n) {
        block4: {
            StringBuilder stringBuilder;
            int n2;
            StatusEffectInstance statusEffectInstance;
            String string;
            StringBuilder stringBuilder2;
            block5: {
                String string2;
                block6: {
                    stringBuilder2 = (StringBuilder)object;
                    RegistryEntry registryEntry = (RegistryEntry)object2;
                    string = (String)object3;
                    int n3 = n ? 1 : 0;
                    statusEffectInstance = MC.mc.player.getStatusEffect(registryEntry);
                    string2 = IRC.getText7();
                    if (statusEffectInstance == null) break block4;
                    n2 = n3;
                    if (string2 == null) break block5;
                    if (n2 == 0) break block6;
                    n2 = statusEffectInstance.getAmplifier();
                    if (string2 == null) break block5;
                    if (n2 <= 0) break block4;
                }
                stringBuilder = stringBuilder2;
                if (string2 == null) break block4;
                n2 = stringBuilder.isEmpty() ? 1 : 0;
            }
            if (n2 == 0) {
                stringBuilder2.append(' ');
            }
            stringBuilder = stringBuilder2.append(string).append(statusEffectInstance.getDuration() / 20 + 1);
        }
    }

    /*
     * Exception decompiling
     */
    private void m966(Object var1_1, int var2_2, int var3_3, float var4_4) {
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
         *     at org.benf.cfr.reader.Driver.doClass(Driver.java:84)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:78)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        Render2DEvent render2DEvent = (Render2DEvent)var1_1;
        int px = var2_2;
        int py = var3_3;
        float health = var4_4;
        int size = this.iconSize.getInt();
        int blink = ((Boolean)this.blinkEffect.getValue()).booleanValue() ? (System.currentTimeMillis() % 1000L - 500L < 0L ? 1 : 0) : 1;
        int fillColor = blink != 0 ? -2130771968 : 1612718112;
        render2DEvent.getDrawContext().fill(px, py, px + size, py + size, fillColor);
        render2DEvent.getDrawContext().drawStrokedRectangle(px, py, size, size, -65536);
        Client.fontManager.renderer2().drawText(render2DEvent.getDrawContext(), "!", px + size / 2 - 3, py + size / 2 - 5, -65536, (Boolean)this.shadowText.getValue());
        if (!((Boolean)this.showHealthText.getValue()).booleanValue()) {
            return;
        }
        String healthStr = this.decimalFormat2.format(health);
        int w = Client.fontManager.renderer2().getStringWidth(healthStr);
        Client.fontManager.renderer2().drawText(render2DEvent.getDrawContext(), healthStr, px + size / 2 - w / 2, py + size + 3, this.m1022(health), (Boolean)this.shadowText.getValue());
    }

    private int m1022(float f) {
        float f2 = f;
        String string = IRC.getText7();
        double d = (Double)this.minHealth.getValue();
        double d2 = 0.0;
        if (string != null) {
            if (d <= d2) {
                return -65536;
            }
            d = f2;
            d2 = (Double)this.minHealth.getValue();
        }
        float f3 = (float)(d / d2);
        float f4 = f3 - 0.5f;
        float f5 = f4 == 0.0f ? 0 : (f4 < 0.0f ? -1 : 1);
        if (string != null) {
            if (f5 <= 0) {
                return -65536;
            }
            float f6 = f3 - 0.75f;
            f5 = f6 == 0.0f ? 0 : (f6 < 0.0f ? -1 : 1);
        }
        if (string != null) {
            if (f5 <= 0) {
                return -23296;
            }
            float f7 = f3 - 1.0f;
            f5 = f7 == 0.0f ? 0 : (f7 < 0.0f ? -1 : 1);
        }
        if (string != null) {
            if (f5 <= 0) {
                return -256;
            }
            f5 = -16711936;
        }
        return (int)f5;
    }

    private void m751(Object object, Object object2, int n, int n2, int n3) {
        Render2DEvent render2DEvent = (Render2DEvent)object;
        String string = (String)object2;
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        int n7 = Client.fontManager.renderer2().getStringWidth(string);
        Client.fontManager.renderer2().drawText(render2DEvent.getDrawContext(), string, n4 - n7 / 2, n5, n6, true);
    }

    private void m387(Object object, Object object2) {
        block0: {
            String string = (String)object;
            PlayerEntity cfr_ignored_0 = (PlayerEntity)object2;
            if (Module.isNotInGame()) break block0;
            CommandManager.sendFeedback(string);
        }
    }

    private static String m346(int n, Object object) {
        int n2 = n;
        String string = (String)object;
        return n2 == 1 ? string : string + "s";
    }
}
