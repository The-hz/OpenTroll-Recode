/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import shit.module.hud.AbstractHudModule;
import shit.setting.BooleanSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Durability
extends AbstractHudModule {
    private final BooleanSetting booleanSetting5 = (BooleanSetting)this.registerSetting(new BooleanSetting("ShowItemName", true));
    private final BooleanSetting booleanSetting4 = (BooleanSetting)this.registerSetting(new BooleanSetting("ShowOffhand", false));
    private final BooleanSetting booleanSetting = (BooleanSetting)this.registerSetting(new BooleanSetting("Percentage", true));

    public Durability() {
        super("Durability", "Shows held item durability.", 6, 222);
    }

    @Override
    protected List lines() {
        List<Object> list;
        block6: {
            boolean bl;
            ArrayList arrayList;
            block5: {
                boolean bl2 = AbstractHudModule.isSet32();
                if (MC.mc.player == null) {
                    return List.of("Durability N/A");
                }
                arrayList = new ArrayList();
                this.m371(arrayList, "MainHand", MC.mc.player.getStackInHand(Hand.MAIN_HAND));
                bl = (Boolean)this.booleanSetting4.getValue();
                if (bl2) break block5;
                if (bl) {
                    this.m371(arrayList, "OffHand", MC.mc.player.getStackInHand(Hand.OFF_HAND));
                }
                list = arrayList;
                if (bl2) break block6;
                bl = list.isEmpty();
            }
            list = bl ? List.of("Durability N/A") : arrayList;
        }
        return list;
    }

    private void m371(Object object, Object object2, Object object3) {
        ItemStack itemStack;
        String string;
        List list;
        block3: {
            block2: {
                list = (List)object;
                string = (String)object2;
                itemStack = (ItemStack)object3;
                boolean bl = true;
                if (itemStack.isEmpty()) break block2;
                if (itemStack.isDamageable()) break block3;
            }
            return;
        }
        int n = itemStack.getMaxDamage() - itemStack.getDamage();
        String string2 = (Boolean)this.booleanSetting.getValue() != false ? String.format(Locale.ROOT, "%.1f%%", Float.valueOf((float)n * 100.0f / (float)itemStack.getMaxDamage())) : n + "/" + itemStack.getMaxDamage();
        String string3 = (Boolean)this.booleanSetting5.getValue() != false ? itemStack.getName().getString() + " " : "";
        list.add(string + " " + string3 + string2);
    }
}

