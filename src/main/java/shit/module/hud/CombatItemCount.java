/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import shit.Client;
import shit.api.Listener3;
import shit.module.Category;
import shit.module.Module;
import shit.module.hud.AbstractHudModule;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class CombatItemCount
extends Module
implements Listener3 {
    private static final Data[] datas = new Data[]{
        new Data("Arrow", Items.ARROW), new Data("Crystal", Items.END_CRYSTAL),
        new Data("Gapple", Items.GOLDEN_APPLE), new Data("Totem", Items.TOTEM_OF_UNDYING),
        new Data("XP", Items.EXPERIENCE_BOTTLE), new Data("Pearl", Items.ENDER_PEARL),
        new Data("Chorus", Items.CHORUS_FRUIT), new Data("Bed", Items.WHITE_BED)};
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 180.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 66.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final BooleanSetting icons = (BooleanSetting)this.registerSetting(new BooleanSetting("Icons", true));
    private final BooleanSetting horizontal = (BooleanSetting)this.registerSetting(new BooleanSetting("Horizontal", true));

    public CombatItemCount() {
        super("CombatItemCount", "Counts common combat items.", Category.HUD);
    }

    @Override
    public int getHudX() {
        return this.x.getInt();
    }

    @Override
    public int getHudY() {
        return this.y.getInt();
    }

    @Override
    public int hudWidth() {
        boolean bl = true;
        return ((Boolean)this.icons.getValue()).booleanValue() ? (((Boolean)this.horizontal.getValue()).booleanValue() ? datas.length * 20 : 20) : 96;
    }

    @Override
    public int getHudHeight() {
        boolean bl = true;
        return ((Boolean)this.icons.getValue()).booleanValue() ? (((Boolean)this.horizontal.getValue()).booleanValue() ? 20 : datas.length * 20) : datas.length * (Client.fontManager.renderer2().getFontHeight() + 1);
    }

    @Override
    public void setHudPosition(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        this.x.setDouble(n3);
        this.y.setDouble(n4);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void renderHud(Object var1_1, boolean var2_2) {
        DrawContext ctx = (DrawContext) var1_1;
        boolean editing = var2_2;
        int x = this.x.getInt();
        int y = this.y.getInt();
        if (MC.mc.player == null) {
            if (editing) {
                Client.fontManager.renderer2().drawText(ctx, "Combat Items", x, y, -1184275, true);
            }
            return;
        }
        boolean icons = (Boolean) this.icons.getValue();
        boolean horizontal = (Boolean) this.horizontal.getValue();
        for (int i = 0; i < datas.length; ++i) {
            Data data = datas[i];
            int count = this.m787(data.item());
            int ix = x + (icons && horizontal ? i * 20 : 0);
            int iy = y + (icons ? (horizontal ? 0 : i * 20) : i * (Client.fontManager.renderer2().getFontHeight() + 1));
            if (icons) {
                net.minecraft.item.ItemStack stack = new net.minecraft.item.ItemStack((net.minecraft.item.ItemConvertible) data.item());
                ctx.drawItem(stack, ix + 2, iy + 2);
                ctx.drawStackOverlay(MC.mc.textRenderer, stack, ix + 2, iy + 2, Integer.toString(count));
            } else {
                Client.fontManager.renderer2().drawText(ctx, data.text2() + " x" + count, ix, iy, -1184275, true);
            }
        }
    }

    private int m787(Object object) {
        int n;
        block4: {
            Item item = (Item)object;
            int n2 = 0;
            int n3 = 0;
            boolean bl = AbstractHudModule.isSet32();
            while (n3 < MC.mc.player.getInventory().size()) {
                ItemStack itemStack = MC.mc.player.getInventory().getStack(n3);
                if (!bl) {
                    n = itemStack.isOf(item) ? '\u0001' : '\u0000';
                    if (bl) break block4;
                    if (n != 0) {
                        n2 += itemStack.getCount();
                    }
                    ++n3;
                }
                if (!bl) continue;
            }
            n = n2;
        }
        return n;
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    record Data(String text2, Item item) {
    }
}

