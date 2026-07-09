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
    private final BooleanSetting shadow = (BooleanSetting)this.m28(new BooleanSetting("Shadow", true));
    private final NumberSetting x = (NumberSetting)this.m28(new NumberSetting("X", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.m28(new NumberSetting("Y", 18.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final ColorSetting color = (ColorSetting)this.m28(new ColorSetting("Color", -1644826));

    public Coordinates() {
        super("Coordinates", "Shows player coordinates.", Category.HUD);
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
        return Client.fontManager.renderer2().m277(this.getText38());
    }

    @Override
    public int getInt28() {
        return Client.fontManager.renderer2().getInt19();
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
        boolean bl3 = AbstractHudModule.isSet32();
        if (!bl3) {
            if (Module.isSet37() && !bl2) {
                return;
            }
            Client.fontManager.renderer2().m5(drawContext, this.getText38(), this.x.getInt50(), this.y.getInt50(), (Integer)this.color.getObj(), (Boolean)this.shadow.getObj());
        }
    }

    private String getText38() {
        int n;
        boolean bl = AbstractHudModule.isSet32();
        int n2 = Module.isSet37() ? 1 : 0;
        if (!bl) {
            if (n2 != 0) {
                return "XYZ 0, 0, 0";
            }
            n2 = MC.client3.player.getBlockX();
        }
        int n3 = n2;
        int n4 = MC.client3.player.getBlockY();
        int n5 = MC.client3.player.getBlockZ();
        boolean bl2 = MC.client3.world.getRegistryKey() == World.NETHER;
        float f = bl2 ? 8.0f : 0.125f;
        int n6 = (int)(MC.client3.player.getX() * (double)f);
        int n7 = n = (int)(MC.client3.player.getZ() * (double)f);
        int n8 = n6;
        int n9 = n5;
        int n10 = n4;
        int n11 = n3;
        return "XYZ " + n11 + ", " + n10 + ", " + n9 + " [" + n8 + ", " + n7 + "]";
    }
}

