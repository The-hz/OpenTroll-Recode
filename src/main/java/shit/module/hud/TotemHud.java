/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import shit.api.Listener3;
import shit.module.Category;
import shit.module.Module;
import shit.module.hud.AbstractHudModule;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class TotemHud
extends Module
implements Listener3 {
    private final NumberSetting x = (NumberSetting)this.m28(new NumberSetting("X", 10.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.m28(new NumberSetting("Y", 10.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));

    public TotemHud() {
        super("TotemHud", "Displays totem count.", Category.HUD);
    }

    @Override
    public int getInt12() {
        return this.x.getInt50();
    }

    @Override
    public int getInt5() {
        return this.y.getInt50();
    }

    @Override
    public int hudWidth() {
        return 20;
    }

    @Override
    public int getInt28() {
        return 28;
    }

    @Override
    public void m274(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        this.x.setObj85(n3);
        this.y.setObj85(n4);
    }

    @Override
    public void m368(Object object, boolean n) {
        DrawContext drawContext = (DrawContext)object;
        int n2 = n ? 1 : 0;
        boolean bl = AbstractHudModule.isSet32();
        int n3 = n2;
        if (!bl) {
            if (n3 == 0 && MC.client3.player == null) {
                return;
            }
            n3 = this.x.getInt50();
        }
        int n4 = n3;
        int n5 = this.y.getInt50();
        int n6 = n2;
        if (!bl) {
            n6 = n6 != 0 ? 3 : this.getInt9();
        }
        int n7 = n6;
        ItemStack itemStack = Items.TOTEM_OF_UNDYING.getDefaultStack();
        drawContext.drawItem(itemStack, n4, n5);
        drawContext.drawStackOverlay(MC.client3.textRenderer, itemStack, n4, n5, Integer.toString(n7));
    }

    private int getInt9() {
        ItemStack itemStack;
        int n;
        boolean bl;
        block7: {
            bl = AbstractHudModule.isSet32();
            if (MC.client3.player == null) {
                return 0;
            }
            n = 0;
            int n2 = 0;
            while (n2 < 36) {
                ItemStack itemStack2 = MC.client3.player.getInventory().getStack(n2);
                if (!bl) {
                    itemStack = itemStack2;
                    if (bl) break block7;
                    if (itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
                        n += itemStack2.getCount();
                    }
                    ++n2;
                }
                if (!bl) continue;
            }
            itemStack = MC.client3.player.getOffHandStack();
        }
        ItemStack itemStack3 = itemStack;
        int n2 = itemStack3.isOf(Items.TOTEM_OF_UNDYING) ? 1 : 0;
        if (!bl) {
            if (n2 != 0) {
                n += itemStack3.getCount();
            }
            n2 = n;
        }
        return n2;
    }
}

