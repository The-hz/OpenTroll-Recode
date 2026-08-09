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
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 180.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final BooleanSetting background = (BooleanSetting)this.registerSetting(new BooleanSetting("Background", true));
    private final BooleanSetting border = (BooleanSetting)this.registerSetting(new BooleanSetting("Border", true));
    private final ColorSetting backgroundColor = (ColorSetting)this.registerSetting(new ColorSetting("BackgroundColor", -2012213224));
    private final ColorSetting borderColor = (ColorSetting)this.registerSetting(new ColorSetting("BorderColor", -9971969));

    public InventoryViewer() {
        super("InventoryViewer", "Shows the main inventory.", Category.HUD);
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
        return 162;
    }

    @Override
    public int getHudHeight() {
        return 54;
    }

    @Override
    public void setHudPosition(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        this.x.setDouble(n3);
        this.y.setDouble(n4);
    }

    @Override
    public void renderHud(Object object, boolean bl) {
        DrawContext drawContext = (DrawContext)object;
        boolean bl2 = bl;
        int n = this.x.getInt();
        int n2 = this.y.getInt();
        boolean bl3 = AbstractHudModule.isSet32();
        boolean bl4 = (Boolean)this.background.getValue();
        if (!bl3) {
            if (bl4) {
                drawContext.fill(n, n2, n + this.hudWidth(), n2 + this.getHudHeight(), ((Integer)this.backgroundColor.getValue()).intValue());
            }
            bl4 = (Boolean)this.border.getValue();
        }
        if (bl4) {
            drawContext.fill(n, n2, n + this.hudWidth(), n2 + 1, ((Integer)this.borderColor.getValue()).intValue());
            drawContext.fill(n, n2 + this.getHudHeight() - 1, n + this.hudWidth(), n2 + this.getHudHeight(), ((Integer)this.borderColor.getValue()).intValue());
            drawContext.fill(n, n2, n + 1, n2 + this.getHudHeight(), ((Integer)this.borderColor.getValue()).intValue());
            drawContext.fill(n + this.hudWidth() - 1, n2, n + this.hudWidth(), n2 + this.getHudHeight(), ((Integer)this.borderColor.getValue()).intValue());
        }
        if (MC.mc.player == null) {
            if (bl2) {
                Client.fontManager.renderer2().drawText(drawContext, "Inventory", n + 4, n2 + 4, -1184275, true);
            }
            return;
        }
        for (int i = 9; i < 36; ++i) {
            ItemStack itemStack = MC.mc.player.getInventory().getStack(i);
            int n3 = itemStack.isEmpty() ? 1 : 0;
            if (!bl3) {
                if (n3 != 0) continue;
                n3 = i - 9;
            }
            int n4 = n3;
            int n5 = n + 1 + n4 % 9 * 18;
            int n6 = n2 + 1 + n4 / 9 * 18;
            drawContext.drawItem(itemStack, n5, n6);
            drawContext.drawStackOverlay(MC.mc.textRenderer, itemStack, n5, n6);
            if (!bl3) continue;
        }
    }
}

