/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import shit.Client;
import shit.api.Listener3;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Radar
extends Module
implements Listener3 {
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 350.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting size = (NumberSetting)this.registerSetting(new NumberSetting("Size", 100.0, 60.0, 240.0, 1.0));
    private final NumberSetting range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 64.0, 8.0, 512.0, 1.0));
    private final BooleanSetting players = (BooleanSetting)this.registerSetting(new BooleanSetting("Players", true));
    private final BooleanSetting mobs = (BooleanSetting)this.registerSetting(new BooleanSetting("Mobs", true));
    private final BooleanSetting rotate = (BooleanSetting)this.registerSetting(new BooleanSetting("Rotate", true));
    private final ColorSetting background = (ColorSetting)this.registerSetting(new ColorSetting("Background", -2012213224));
    private final ColorSetting border = (ColorSetting)this.registerSetting(new ColorSetting("Border", -1184275));
    private final ColorSetting playerColor = (ColorSetting)this.registerSetting(new ColorSetting("PlayerColor", -9971969));
    private final ColorSetting mobColor = (ColorSetting)this.registerSetting(new ColorSetting("MobColor", -43691));
    private final ColorSetting selfColor = (ColorSetting)this.registerSetting(new ColorSetting("SelfColor", -1));

    public Radar() {
        super("Radar", "Shows nearby entities on a 2D radar.", Category.HUD);
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
        return this.size.getInt();
    }

    @Override
    public int getHudHeight() {
        return this.size.getInt();
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
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        DrawContext drawContext;
        block9: {
            boolean bl2;
            block8: {
                drawContext = (DrawContext)object;
                bl2 = bl;
                n6 = this.x.getInt();
                n5 = this.y.getInt();
                n4 = this.size.getInt();
                n3 = n6 + n4 / 2;
                boolean bl3 = true;
                n2 = n5 + n4 / 2;
                n = n4 / 2 - 3;
                drawContext.fill(n6, n5, n6 + n4, n5 + n4, ((Integer)this.background.getValue()).intValue());
                drawContext.fill(n6, n5, n6 + n4, n5 + 1, ((Integer)this.border.getValue()).intValue());
                drawContext.fill(n6, n5 + n4 - 1, n6 + n4, n5 + n4, ((Integer)this.border.getValue()).intValue());
                drawContext.fill(n6, n5, n6 + 1, n5 + n4, ((Integer)this.border.getValue()).intValue());
                drawContext.fill(n6 + n4 - 1, n5, n6 + n4, n5 + n4, ((Integer)this.border.getValue()).intValue());
                drawContext.fill(n3 - 1, n5 + 3, n3 + 1, n5 + n4 - 3, 0x55FFFFFF);
                drawContext.fill(n6 + 3, n2 - 1, n6 + n4 - 3, n2 + 1, 0x55FFFFFF);
                this.m83(drawContext, n3, n2, (Integer)this.selfColor.getValue(), 2);
                if (MC.mc.player == null) break block8;
                if (MC.mc.world != null) break block9;
            }
            if (bl2) {
                Client.fontManager.renderer2().drawText(drawContext, "Radar", n6 + 4, n5 + 4, -1184275, true);
            }
            return;
        }
        double d = Math.toRadians(MC.mc.player.getYaw());
        double d2 = Math.sin(d);
        double d3 = Math.cos(d);
        for (Entity entity : MC.mc.world.getEntities()) {
            if (entity == MC.mc.player) continue;
            if (!(entity instanceof LivingEntity)) continue;
            if (entity.distanceTo((Entity)MC.mc.player) > this.range.getFloat()) continue;
            boolean bl4 = entity instanceof PlayerEntity;
            if (bl4) {
                if (!((Boolean)this.players.getValue()).booleanValue()) continue;
            }
            if (!bl4) {
                if (!(entity instanceof MobEntity)) continue;
            }
            if (!bl4 && !((Boolean)this.mobs.getValue()).booleanValue()) continue;
            double d4 = entity.getX() - MC.mc.player.getX();
            double d5 = entity.getZ() - MC.mc.player.getZ();
            if (((Boolean)this.rotate.getValue()).booleanValue()) {
                double d6 = d4 * d3 + d5 * d2;
                double d7 = d5 * d3 - d4 * d2;
                d4 = d6;
                d5 = d7;
            }
            int n7 = n3 + (int)Math.round(d4 / (Double)this.range.getValue() * (double)n);
            int n8 = n2 + (int)Math.round(d5 / (Double)this.range.getValue() * (double)n);
            if (n7 < n6 + 2) continue;
            if (n7 > n6 + n4 - 3) continue;
            if (n8 < n5 + 2 || n8 > n5 + n4 - 3) continue;
            this.m83(drawContext, n7, n8, bl4 ? (Integer)this.playerColor.getValue() : (Integer)this.mobColor.getValue(), 2);
            if (true) continue;
        }
    }

    private void m83(Object object, int n, int n2, int n3, int n4) {
        DrawContext drawContext = (DrawContext)object;
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        drawContext.fill(n5 - n8, n6 - n8, n5 + n8 + 1, n6 + n8 + 1, n7);
    }
}

