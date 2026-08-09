/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import shit.Client;
import shit.api.HudModule;
import shit.module.Category;
import shit.module.Module;
import shit.module.hud.AbstractHudModule;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Ping
extends Module
implements HudModule {
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 42.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final BooleanSetting shadow = (BooleanSetting)this.registerSetting(new BooleanSetting("Shadow", true));
    private final ColorSetting color = (ColorSetting)this.registerSetting(new ColorSetting("Color", -1184275));

    public Ping() {
        super("Ping", "Shows server latency.", Category.HUD);
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
        return Client.fontManager.renderer2().getStringWidth(this.getText13());
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
        Client.fontManager.renderer2().drawText(drawContext, this.getText13(), this.x.getInt(), this.y.getInt(), (Integer)this.color.getValue(), (Boolean)this.shadow.getValue());
    }

    private String getText13() {
        PlayerListEntry playerListEntry;
        block5: {
            block6: {
                block3: {
                    MinecraftClient minecraftClient;
                    boolean bl;
                    block4: {
                        block2: {
                            bl = AbstractHudModule.isEditMode();
                            minecraftClient = MC.mc;
                            if (bl) break block2;
                            if (minecraftClient.getNetworkHandler() == null) break block3;
                            minecraftClient = MC.mc;
                        }
                        if (bl) break block4;
                        if (minecraftClient.player == null) break block3;
                        minecraftClient = MC.mc;
                    }
                    playerListEntry = minecraftClient.getNetworkHandler().getPlayerListEntry(MC.mc.player.getUuid());
                    if (bl) break block5;
                    if (playerListEntry != null) break block6;
                }
                return "Ping 0ms";
            }
            playerListEntry = MC.mc.getNetworkHandler().getPlayerListEntry(MC.mc.player.getUuid());
        }
        return "Ping " + playerListEntry.getLatency() + "ms";
    }
}

