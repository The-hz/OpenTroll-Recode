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
import shit.api.Listener3;
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
implements Listener3 {
    private final NumberSetting x = (NumberSetting)this.m28(new NumberSetting("X", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.m28(new NumberSetting("Y", 42.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final BooleanSetting shadow = (BooleanSetting)this.m28(new BooleanSetting("Shadow", true));
    private final ColorSetting color = (ColorSetting)this.m28(new ColorSetting("Color", -1184275));

    public Ping() {
        super("Ping", "Shows server latency.", Category.HUD);
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
        return Client.fontManager.renderer2().m277(this.getText13());
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
        Client.fontManager.renderer2().m5(drawContext, this.getText13(), this.x.getInt50(), this.y.getInt50(), (Integer)this.color.getObj(), (Boolean)this.shadow.getObj());
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
                            bl = AbstractHudModule.isSet32();
                            minecraftClient = MC.client3;
                            if (bl) break block2;
                            if (minecraftClient.getNetworkHandler() == null) break block3;
                            minecraftClient = MC.client3;
                        }
                        if (bl) break block4;
                        if (minecraftClient.player == null) break block3;
                        minecraftClient = MC.client3;
                    }
                    playerListEntry = minecraftClient.getNetworkHandler().getPlayerListEntry(MC.client3.player.getUuid());
                    if (bl) break block5;
                    if (playerListEntry != null) break block6;
                }
                return "Ping 0ms";
            }
            playerListEntry = MC.client3.getNetworkHandler().getPlayerListEntry(MC.client3.player.getUuid());
        }
        return "Ping " + playerListEntry.getLatency() + "ms";
    }
}

