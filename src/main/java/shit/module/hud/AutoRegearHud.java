/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import shit.Client;
import shit.api.HudModule;
import shit.module.Category;
import shit.module.Module;
import shit.module.combat.AutoRegear;
import shit.module.hud.AbstractHudModule;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoRegearHud
extends Module
implements HudModule {
    public static AutoRegearHud INSTANCE;
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 100.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 100.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));

    public AutoRegearHud() {
        super("AutoRegearHud", "Draggable silent container display for AutoRegear.", Category.HUD);
        INSTANCE = this;
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
        int n = Client.fontManager.renderer2().getFontHeight() + 6;
        return n + 54 + 4;
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
     * Could not resolve type clashes
     */
    @Override
    public void renderHud(Object var1_1, boolean var2_2) {
        DrawContext ctx = (DrawContext) var1_1;
        boolean editing = var2_2;
        int x = this.x.getInt();
        int y = this.y.getInt();
        int titleH = Client.fontManager.renderer2().getFontHeight() + 6;
        int totalH = this.getHudHeight();
        if (editing && !this.isSet118()) {
            drawBox(ctx, x, y, totalH, titleH);
            Client.fontManager.renderer2().drawText(ctx, "AutoRegear", x + 6, y + 4, -9971969, true);
            return;
        }
        if (!this.isSet118()) {
            return;
        }
        String title;
        if (MC.mc.player.currentScreenHandler instanceof net.minecraft.screen.GenericContainerScreenHandler) {
            title = "Ender Chest";
        } else if (MC.mc.player.currentScreenHandler instanceof net.minecraft.screen.ShulkerBoxScreenHandler) {
            title = "Shulker Box";
        } else {
            return;
        }
        drawBox(ctx, x, y, totalH, titleH);
        Client.fontManager.renderer2().drawText(ctx, title, x + 6, y + 4, -9971969, true);
        for (int i = 0; i < 27; ++i) {
            net.minecraft.item.ItemStack stack = MC.mc.player.currentScreenHandler.getSlot(i).getStack();
            if (stack.isEmpty()) continue;
            int sx = x + 1 + i % 9 * 18;
            int sy = y + titleH + i / 9 * 18;
            ctx.drawItem(stack, sx, sy);
            ctx.drawStackOverlay(MC.mc.textRenderer, stack, sx, sy);
        }
    }

    private static void drawBox(DrawContext ctx, int x, int y, int totalH, int titleH) {
        ctx.fill(x, y, x + 162, y + totalH, -1441787880);
        ctx.fill(x, y, x + 162, y + 1, -9971969);
        ctx.fill(x, y + totalH - 1, x + 162, y + totalH, -9971969);
        ctx.fill(x, y, x + 1, y + totalH, -9971969);
        ctx.fill(x + 161, y, x + 162, y + totalH, -9971969);
        ctx.fill(x, y + titleH - 1, x + 162, y + titleH, -13619144);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet118() {
        boolean bl = AbstractHudModule.isEditMode();
        if (MC.mc.player == null) return false;
        AutoRegear autoRegear = AutoRegear.INSTANCE;
        if (!bl) {
            if (autoRegear == null) return false;
            autoRegear = AutoRegear.INSTANCE;
        }
        boolean bl2 = autoRegear.isEnabled();
        if (!bl) {
            if (!bl2) return false;
            bl2 = AutoRegear.INSTANCE.flag39;
        }
        if (bl) return bl2;
        if (!bl2) return false;
        return true;
    }
}

