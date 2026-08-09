/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import shit.Client;
import shit.api.Listener3;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.module.hud.AbstractHudModule;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class PlayerModel
extends Module
implements Listener3 {
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 350.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 112.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting width = (NumberSetting)this.registerSetting(new NumberSetting("Width", 50.0, 30.0, 160.0, 1.0));
    private final NumberSetting height = (NumberSetting)this.registerSetting(new NumberSetting("Height", 80.0, 50.0, 220.0, 1.0));
    private final BooleanSetting emulateYaw = (BooleanSetting)this.registerSetting(new BooleanSetting("EmulateYaw", true));
    private final BooleanSetting emulatePitch = (BooleanSetting)this.registerSetting(new BooleanSetting("EmulatePitch", true));
    private float value165 = 0.0f;
    private float value201 = 0.0f;
    private boolean flag150 = false;

    public PlayerModel() {
        super("PlayerModel", "Shows your player model.", Category.HUD);
    }

    @EventHandler
    private void setEvent2Inner18(Event2.Event2Inner event2Inner) {
        if (MC.mc.player == null) {
            this.flag150 = false;
            this.value165 = 0.0f;
            return;
        }
        float f = MC.mc.player.getYaw();
        if (!this.flag150) {
            this.value201 = f;
            this.flag150 = true;
        }
        float f2 = MathHelper.wrapDegrees((float)(f - this.value201));
        this.value201 = f;
        this.value165 += f2;
        this.value165 *= 0.82f;
        this.value165 = MathHelper.clamp((float)this.value165, (float)-30.0f, (float)30.0f);
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
        return this.width.getInt();
    }

    @Override
    public int getHudHeight() {
        return this.height.getInt();
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
     */
    @Override
    public void renderHud(Object var1_1, boolean var2_2) {
        DrawContext drawContext = (DrawContext)var1_1;
        int x = this.x.getInt();
        int y = this.y.getInt();
        if (MC.mc.player == null) {
            if (var2_2) {
                drawContext.fill(x, y, x + this.hudWidth(), y + this.getHudHeight(), 1427445792);
                Client.fontManager.renderer2().drawText(drawContext, "Player", x + 4, y + 4, -1184275, true);
            }
            return;
        }
        int x2 = x + this.width.getInt();
        int y2 = y + this.height.getInt();
        int size = Math.max(18, Math.min(this.width.getInt(), this.height.getInt()) / 2);
        float cx = (float)(x + x2) / 2.0f;
        float cy = (float)(y + y2) / 2.0f;
        float yaw = ((Boolean)this.emulateYaw.getValue()).booleanValue() ? this.value165 : 0.0f;
        float pitch = ((Boolean)this.emulatePitch.getValue()).booleanValue() ? MathHelper.clamp((float)MC.mc.player.getPitch(), (float)-30.0f, (float)30.0f) : 0.0f;
        float ex = cx - (float)Math.tan((double)(yaw / 20.0f)) * 40.0f;
        float ey = cy - (float)Math.tan((double)(-pitch / 20.0f)) * 40.0f;
        InventoryScreen.drawEntity(drawContext, x, y, x2, y2, size, 0.0f, ex, ey, MC.mc.player);
    }
}

