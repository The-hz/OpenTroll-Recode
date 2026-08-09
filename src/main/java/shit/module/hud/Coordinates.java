/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.world.World;
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
public class Coordinates
extends Module
implements Listener3 {
    private final BooleanSetting shadow = (BooleanSetting)this.registerSetting(new BooleanSetting("Shadow", true));
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 18.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final ColorSetting color = (ColorSetting)this.registerSetting(new ColorSetting("Color", -1644826));

    public Coordinates() {
        super("Coordinates", "Shows player coordinates.", Category.HUD);
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
        return Client.fontManager.renderer2().getStringWidth(this.getText38());
    }

    @Override
    public int getHudHeight() {
        return Client.fontManager.renderer2().getFontHeight();
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
        boolean bl3 = AbstractHudModule.isSet32();
        if (!bl3) {
            if (Module.isNotInGame() && !bl2) {
                return;
            }
            Client.fontManager.renderer2().drawText(drawContext, this.getText38(), this.x.getInt(), this.y.getInt(), (Integer)this.color.getValue(), (Boolean)this.shadow.getValue());
        }
    }

    private String getText38() {
        int n;
        boolean bl = AbstractHudModule.isSet32();
        int n2 = Module.isNotInGame() ? 1 : 0;
        if (!bl) {
            if (n2 != 0) {
                return "XYZ 0, 0, 0";
            }
            n2 = MC.mc.player.getBlockX();
        }
        int n3 = n2;
        int n4 = MC.mc.player.getBlockY();
        int n5 = MC.mc.player.getBlockZ();
        boolean bl2 = MC.mc.world.getRegistryKey() == World.NETHER;
        float f = bl2 ? 8.0f : 0.125f;
        int n6 = (int)(MC.mc.player.getX() * (double)f);
        int n7 = n = (int)(MC.mc.player.getZ() * (double)f);
        int n8 = n6;
        int n9 = n5;
        int n10 = n4;
        int n11 = n3;
        return "XYZ " + n11 + ", " + n10 + ", " + n9 + " [" + n8 + ", " + n7 + "]";
    }
}

