/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import java.util.Iterator;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import shit.Client;
import shit.command.CommandManager;
import shit.event.DisconnectEvent;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PlayerEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.ItemUtil;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class AutoLog
extends Module {
    public static boolean flag84;
    private final BooleanSetting logOnEnable = (BooleanSetting)this.m28(new BooleanSetting("LogOnEnable", false));
    private final BooleanSetting onPop = (BooleanSetting)this.m28(new BooleanSetting("OnPop", true));
    private final BooleanSetting lowArmor = (BooleanSetting)this.m28(new BooleanSetting("LowArmor", true));
    private final BooleanSetting totemLess = (BooleanSetting)this.m28(new BooleanSetting("TotemLess", true));
    private final NumberSetting totems = (NumberSetting)this.m28(new NumberSetting("Totems", 2.0, 0.0, 20.0, 1.0));
    private final BooleanSetting antiMace = (BooleanSetting)this.m28(new BooleanSetting("AntiMace", true));
    private final NumberSetting antiRange = (NumberSetting)this.m28(new NumberSetting("AntiRange", 15.0, 3.0, 30.0, 0.5));
    private final NumberSetting safeRange = (NumberSetting)this.m28(new NumberSetting("SafeRange", 3.0, 1.0, 20.0, 0.5));
    private final NumberSetting antiHRange = (NumberSetting)this.m28(new NumberSetting("AntiHRange", 8.0, 1.0, 20.0, 0.5));
    private final BooleanSetting ignoreElytra = (BooleanSetting)this.m28(new BooleanSetting("IgnoreElytra", true));
    private final BooleanSetting sendMessage = (BooleanSetting)this.m28(new BooleanSetting("SendMessage", true));
    private final NumberSetting msgRandomLen = (NumberSetting)this.m28(new NumberSetting("MsgRandomLen", 4.0, 0.0, 10.0, 1.0));
    private final BooleanSetting autoDisable = (BooleanSetting)this.m28(new BooleanSetting("AutoDisable", true));
    private final Random random4 = new Random();

    public AutoLog() {
        super("AutoLog", "Disconnects when unsafe conditions are detected.", Category.MISC);
    }

    @Override
    public void onEnable() {
        block3: {
            AutoLog autoLog;
            block2: {
                String string = IRC.getText7();
                autoLog = this;
                if (string == null) break block2;
                if (!((Boolean)autoLog.logOnEnable.getObj()).booleanValue()) break block3;
                autoLog = this;
            }
            autoLog.setObj73("Enabled");
        }
    }

    @EventHandler
    private void setEvent2Inner3(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        if (((Boolean)this.antiMace.getObj()).booleanValue() && this.isSet150()) {
            return;
        }
        this.m354();
    }

    @EventHandler
    private void setPlayerEvent3(PlayerEvent playerEvent) {
        if (((Boolean)this.onPop.getObj()).booleanValue() && playerEvent.getPlayer4() == MC.client3.player) {
            this.setObj73("Totem Popped");
        }
    }

    @EventHandler
    private void setDisconnectEvent2(DisconnectEvent disconnectEvent) {
        if (((Boolean)this.autoDisable.getObj()).booleanValue() && this.isSet19()) {
            this.setFlag3(false);
        }
    }

    private boolean isSet150() {
        Iterator iterator = MC.client3.world.getPlayers().iterator();
        String string = IRC.getText7();
        while (iterator.hasNext()) {
            PlayerEntity playerEntity;
            PlayerEntity playerEntity2 = playerEntity = (PlayerEntity)iterator.next();
            if (string != null) {
                if (playerEntity2 == MC.client3.player) continue;
                playerEntity2 = playerEntity;
            }
            boolean bl = playerEntity2.isAlive();
            if (string != null) {
                if (!bl) continue;
                bl = Client.manager.m258(playerEntity.getName().getString());
            }
            if (bl) continue;
            PlayerEntity playerEntity3 = playerEntity;
            if (string != null) {
                if (playerEntity3.getMainHandStack().getItem() != Items.MACE) continue;
                playerEntity3 = playerEntity;
            }
            double d = playerEntity3.getY() - MC.client3.player.getY();
            double d2 = Math.hypot(playerEntity.getX() - MC.client3.player.getX(), playerEntity.getZ() - MC.client3.player.getZ());
            double d3 = d - (Double)this.safeRange.getObj();
            double d4 = d3 == 0.0 ? 0 : (d3 < 0.0 ? -1 : 1);
            if (string != null) {
                if (d4 <= 0) continue;
                double d5 = d - (Double)this.antiRange.getObj();
                d4 = d5 == 0.0 ? 0 : (d5 > 0.0 ? 1 : -1);
            }
            if (string != null) {
                if (d4 > 0) continue;
                double d6 = d2 - (Double)this.antiHRange.getObj();
                d4 = d6 == 0.0 ? 0 : (d6 > 0.0 ? 1 : -1);
            }
            if (string != null) {
                if (d4 > 0) continue;
                d4 = ((Boolean)this.ignoreElytra.getObj()).booleanValue() ? 1.0 : 0.0;
            }
            if (string != null) {
                if (d4 != 0.0) {
                    d4 = this.m715(playerEntity) ? 1.0 : 0.0;
                    if (string != null) {
                        if (d4 != 0.0) continue;
                    }
                } else {
                    this.setObj60(playerEntity);
                    d4 = 1.0;
                }
            }
            return d4 != 0.0;
        }
        return false;
    }

    private void setObj60(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        String string = playerEntity.getName().getString();
        String string2 = IRC.getText7();
        AutoLog autoLog = this;
        if (string2 != null) {
            if (((Boolean)autoLog.sendMessage.getObj()).booleanValue()) {
                try {
                    Util2.setObj62("@" + string + " tried to mace me " + this.m694(this.msgRandomLen.getInt50()));
                }
                catch (Exception exception) {}
            }
            autoLog = this;
        }
        autoLog.setObj73("Mace: " + string);
    }

    /*
     * Unable to fully structure code
     */
    private void m354() {
        if (((Boolean)this.totemLess.getObj()).booleanValue()) {
            int n = this.getInt13();
            if (n <= this.totems.getInt50()) {
                this.setObj73("Low Totems (" + n + ")");
                return;
            }
        }
        if (!((Boolean)this.lowArmor.getObj()).booleanValue()) {
            return;
        }
        if (ItemUtil.m749(MC.client3.player, 5)) {
            this.setObj73("Armor Durability Low");
        }
    }

    private boolean m715(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        ItemStack itemStack = playerEntity.getEquippedStack(EquipmentSlot.CHEST);
        return itemStack.getItem() == Items.ELYTRA;
    }

    private int getInt13() {
        int n = 0;
        String string = IRC.getText7();
        for (ItemStack itemStack : MC.client3.player.getInventory().getMainStacks()) {
            if (itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
                n += itemStack.getCount();
            }
            if (string != null) continue;
        }
        return n;
    }

    private void setObj73(Object object) {
        String string;
        String string2;
        block6: {
            AutoLog autoLog;
            block7: {
                boolean bl;
                block5: {
                    string2 = (String)object;
                    flag84 = true;
                    string = IRC.getText7();
                    CommandManager.setObj21("\u00a74[AutoLog] \u00a7f" + string2);
                    bl = (Boolean)this.autoDisable.getObj();
                    if (string == null) break block5;
                    if (!bl) break block6;
                    autoLog = this;
                    if (string == null) break block7;
                    bl = autoLog.isSet19();
                }
                if (!bl) break block6;
                autoLog = this;
            }
            autoLog.setFlag3(false);
        }
        MinecraftClient minecraftClient = MC.client3;
        if (string != null) {
            if (minecraftClient.world != null) {
                MC.client3.world.disconnect((Text)Text.literal((String)string2));
            }
            minecraftClient = MC.client3;
        }
        minecraftClient.disconnect(null, false);
    }

    private String m694(int n) {
        StringBuilder stringBuilder;
        block3: {
            int n2 = n;
            String string = IRC.getText7();
            if (n2 <= 0) {
                return "";
            }
            StringBuilder stringBuilder2 = new StringBuilder("[");
            for (int i = 0; i < n2; ++i) {
                stringBuilder = stringBuilder2.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(this.random4.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length())));
                if (string != null) {
                    if (string != null) continue;
                }
                break block3;
            }
            stringBuilder = stringBuilder2.append(']');
        }
        return stringBuilder.toString();
    }
}

