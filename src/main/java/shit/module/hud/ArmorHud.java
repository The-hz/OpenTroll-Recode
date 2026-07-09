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
import shit.manager.FontManager2;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ArmorHud
extends Module
implements Listener3 {
    private final NumberSetting x = (NumberSetting)this.m28(new NumberSetting("X", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.m28(new NumberSetting("Y", 54.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    public final BooleanSetting shadow = (BooleanSetting)this.m28(new BooleanSetting("Shadow", true));
    public final BooleanSetting durability = (BooleanSetting)this.m28(new BooleanSetting("Durability", true));

    public ArmorHud() {
        super("ArmorHud", "Placeholder armor hud.", Category.HUD);
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
        return 80;
    }

    @Override
    public int getInt28() {
        boolean bl = true;
        return (Boolean)this.durability.getObj() != false ? 28 : 16;
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
        boolean bl3 = true;
        int n2 = this.y.getInt50();
        if (MC.client3.player == null) {
            if (bl2) {
                drawContext.fill(n, n2, n + this.hudWidth(), n2 + this.getInt28(), 1427445792);
                Client.fontManager.renderer2().m5(drawContext, "Armor HUD", n + 4, n2 + 2, -1184275, (Boolean)this.shadow.getObj());
            }
            return;
        }
        int n3 = n;
        for (int i = 3; i >= 0; --i) {
            block8: {
                block7: {
                    ItemStack itemStack = MC.client3.player.getInventory().getStack(36 + i);
                    if (itemStack.isEmpty()) break block7;
                    drawContext.drawItem(itemStack, n3 + 2, n2);
                    drawContext.drawStackOverlay(MC.client3.textRenderer, itemStack, n3 + 2, n2);
                    if (!((Boolean)this.durability.getObj()).booleanValue()) break block8;
                    if (itemStack.getMaxDamage() <= 0) break block8;
                    int n4 = itemStack.getMaxDamage();
                    int n5 = itemStack.getDamage();
                    int n6 = (int)((float)(n4 - n5) / (float)n4 * 100.0f);
                    int n7 = this.m391(n6);
                    String string = n6 + "%";
                    FontManager2 fontManager2 = Client.fontManager.renderer2();
                    int n8 = fontManager2.m277(string);
                    int n9 = n3 + 10 - n8 / 2;
                    int n10 = n2 + 18;
                    fontManager2.m5(drawContext, string, n9, n10, n7, (Boolean)this.shadow.getObj());
                    if (true) break block8;
                }
                if (bl2) {
                    drawContext.fill(n3 + 2, n2, n3 + 18, n2 + 16, 0x22FFFFFF);
                }
            }
            n3 += 20;
            if (true) continue;
        }
    }

    private int m391(int n) {
        int n2 = n;
        float f = Math.max(0.0f, Math.min(1.0f, (float)n2 / 100.0f));
        int n3 = (int)(196.0f + -196.0f * f);
        int n4 = (int)(0.0f + 227.0f * f);
        int n5 = 0;
        return 0xFF000000 | n3 << 16 | n4 << 8 | n5;
    }
}

