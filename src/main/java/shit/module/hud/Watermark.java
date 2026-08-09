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
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.setting.StringSetting;

@Environment(value=EnvType.CLIENT)
public class Watermark
extends Module
implements Listener3 {
    private final StringSetting text = (StringSetting)this.registerSetting(new StringSetting("Text", "TrollHack-Recode"));
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.CLIENT_VERSION));
    private final BooleanSetting info = (BooleanSetting)this.registerSetting(new BooleanSetting("Info", false));
    private final BooleanSetting shadow = (BooleanSetting)this.registerSetting(new BooleanSetting("Shadow", true));
    private final ColorSetting color = (ColorSetting)this.registerSetting(new ColorSetting("Color", -1));
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));

    public Watermark() {
        super("Watermark", "Draws the client name.", Category.HUD);
        this.setEnabled(true);
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
        return Client.fontManager.renderer2().getStringWidth(this.getText62());
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
        Client.fontManager.renderer2().drawText(drawContext, this.getText62(), this.x.getInt(), this.y.getInt(), (Integer)this.color.getValue(), (Boolean)this.shadow.getValue());
    }

    private String getText62() {
        String string = "";
        switch (((Mode)((Object)this.mode.getValue())).ordinal()) {
            default: {
                throw new MatchException(null, null);
            }
            case 0: {
                String string2 = (String)this.text.getValue();
                break;
            }
            case 1: {
                String string2 = "TrollHack-Recode";
                break;
            }
            case 2: {
                String string2 = string = "TrollHack-Recode 1.0.0";
            }
        }
        if (!((Boolean)this.info.getValue()).booleanValue()) {
            return string;
        }
        return string + " | " + MinecraftAccessor.trollhack$getFps() + " FPS";
    }

    private static MatchException a(MatchException matchException) {
        return matchException;
    }

    @Environment(value=EnvType.CLIENT)
    static enum Mode {
      CUSTOM, CLIENT, CLIENT_VERSION;

      private Mode() {}



        private static Mode[] getModeArray12() {
            return new Mode[]{CUSTOM, CLIENT, CLIENT_VERSION};
        }
    
   }
}

