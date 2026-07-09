/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import shit.Client;
import shit.api.Listener3;
import shit.module.Category;
import shit.module.Module;
import shit.module.hud.AbstractHudModule;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class InventoryViewer
extends Module
implements Listener3 {
    private final NumberSetting x = (NumberSetting)this.m28(new NumberSetting("X", 180.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.m28(new NumberSetting("Y", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final BooleanSetting background = (BooleanSetting)this.m28(new BooleanSetting("Background", true));
    private final BooleanSetting border = (BooleanSetting)this.m28(new BooleanSetting("Border", true));
    private final ColorSetting backgroundColor = (ColorSetting)this.m28(new ColorSetting("BackgroundColor", -2012213224));
    private final ColorSetting borderColor = (ColorSetting)this.m28(new ColorSetting("BorderColor", -9971969));

    public InventoryViewer() {
        super("InventoryViewer", "Shows the main inventory.", Category.HUD);
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
        return 162;
    }

    @Override
    public int getInt28() {
        return 54;
    }

    @Override
    public void m274(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        this.x.setObj85(n3);
        this.y.setObj85(n4);
    }

    @Override
    public void m368(Object object, boolean bl) {
        DrawContext drawContext = (DrawContext)object;
        boolean bl2 = bl;
        int n = this.x.getInt50();
        int n2 = this.y.getInt50();
        boolean bl3 = AbstractHudModule.isSet32();
        boolean bl4 = (Boolean)this.background.getObj();
        if (!bl3) {
            if (bl4) {
                drawContext.fill(n, n2, n + this.hudWidth(), n2 + this.getInt28(), ((Integer)this.backgroundColor.getObj()).intValue());
            }
            bl4 = (Boolean)this.border.getObj();
        }
        if (bl4) {
            drawContext.fill(n, n2, n + this.hudWidth(), n2 + 1, ((Integer)this.borderColor.getObj()).intValue());
            drawContext.fill(n, n2 + this.getInt28() - 1, n + this.hudWidth(), n2 + this.getInt28(), ((Integer)this.borderColor.getObj()).intValue());
            drawContext.fill(n, n2, n + 1, n2 + this.getInt28(), ((Integer)this.borderColor.getObj()).intValue());
            drawContext.fill(n + this.hudWidth() - 1, n2, n + this.hudWidth(), n2 + this.getInt28(), ((Integer)this.borderColor.getObj()).intValue());
        }
        if (MC.client3.player == null) {
            if (bl2) {
                Client.fontManager.renderer2().m5(drawContext, "Inventory", n + 4, n2 + 4, -1184275, true);
            }
            return;
        }
        for (int i = 9; i < 36; ++i) {
            ItemStack itemStack = MC.client3.player.getInventory().getStack(i);
            int n3 = itemStack.isEmpty() ? 1 : 0;
            if (!bl3) {
                if (n3 != 0) continue;
                n3 = i - 9;
            }
            int n4 = n3;
            int n5 = n + 1 + n4 % 9 * 18;
            int n6 = n2 + 1 + n4 / 9 * 18;
            drawContext.drawItem(itemStack, n5, n6);
            drawContext.drawStackOverlay(MC.client3.textRenderer, itemStack, n5, n6);
            if (!bl3) continue;
        }
    }
}

