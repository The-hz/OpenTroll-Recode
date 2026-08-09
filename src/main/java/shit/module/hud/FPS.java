/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import shit.Client;
import shit.api.Listener3;
import shit.mixin.MinecraftAccessor;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class FPS
extends Module
implements Listener3 {
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 30.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final BooleanSetting shadow = (BooleanSetting)this.registerSetting(new BooleanSetting("Shadow", true));
    private final ColorSetting color = (ColorSetting)this.registerSetting(new ColorSetting("Color", -1184275));

    public FPS() {
        super("FPS", "Shows current FPS.", Category.HUD);
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
        return Client.fontManager.renderer2().getStringWidth(this.getText9());
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
        Client.fontManager.renderer2().drawText(drawContext, this.getText9(), this.x.getInt(), this.y.getInt(), (Integer)this.color.getValue(), (Boolean)this.shadow.getValue());
    }

    private String getText9() {
        return "FPS " + MinecraftAccessor.trollhack$getFps();
    }
}

