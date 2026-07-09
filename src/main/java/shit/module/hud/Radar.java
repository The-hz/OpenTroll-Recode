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
    private final NumberSetting x = (NumberSetting)this.m28(new NumberSetting("X", 350.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.m28(new NumberSetting("Y", 6.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting size = (NumberSetting)this.m28(new NumberSetting("Size", 100.0, 60.0, 240.0, 1.0));
    private final NumberSetting range = (NumberSetting)this.m28(new NumberSetting("Range", 64.0, 8.0, 512.0, 1.0));
    private final BooleanSetting players = (BooleanSetting)this.m28(new BooleanSetting("Players", true));
    private final BooleanSetting mobs = (BooleanSetting)this.m28(new BooleanSetting("Mobs", true));
    private final BooleanSetting rotate = (BooleanSetting)this.m28(new BooleanSetting("Rotate", true));
    private final ColorSetting background = (ColorSetting)this.m28(new ColorSetting("Background", -2012213224));
    private final ColorSetting border = (ColorSetting)this.m28(new ColorSetting("Border", -1184275));
    private final ColorSetting playerColor = (ColorSetting)this.m28(new ColorSetting("PlayerColor", -9971969));
    private final ColorSetting mobColor = (ColorSetting)this.m28(new ColorSetting("MobColor", -43691));
    private final ColorSetting selfColor = (ColorSetting)this.m28(new ColorSetting("SelfColor", -1));

    public Radar() {
        super("Radar", "Shows nearby entities on a 2D radar.", Category.HUD);
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
        return this.size.getInt50();
    }

    @Override
    public int getInt28() {
        return this.size.getInt50();
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
                n6 = this.x.getInt50();
                n5 = this.y.getInt50();
                n4 = this.size.getInt50();
                n3 = n6 + n4 / 2;
                boolean bl3 = true;
                n2 = n5 + n4 / 2;
                n = n4 / 2 - 3;
                drawContext.fill(n6, n5, n6 + n4, n5 + n4, ((Integer)this.background.getObj()).intValue());
                drawContext.fill(n6, n5, n6 + n4, n5 + 1, ((Integer)this.border.getObj()).intValue());
                drawContext.fill(n6, n5 + n4 - 1, n6 + n4, n5 + n4, ((Integer)this.border.getObj()).intValue());
                drawContext.fill(n6, n5, n6 + 1, n5 + n4, ((Integer)this.border.getObj()).intValue());
                drawContext.fill(n6 + n4 - 1, n5, n6 + n4, n5 + n4, ((Integer)this.border.getObj()).intValue());
                drawContext.fill(n3 - 1, n5 + 3, n3 + 1, n5 + n4 - 3, 0x55FFFFFF);
                drawContext.fill(n6 + 3, n2 - 1, n6 + n4 - 3, n2 + 1, 0x55FFFFFF);
                this.m83(drawContext, n3, n2, (Integer)this.selfColor.getObj(), 2);
                if (MC.client3.player == null) break block8;
                if (MC.client3.world != null) break block9;
            }
            if (bl2) {
                Client.fontManager.renderer2().m5(drawContext, "Radar", n6 + 4, n5 + 4, -1184275, true);
            }
            return;
        }
        double d = Math.toRadians(MC.client3.player.getYaw());
        double d2 = Math.sin(d);
        double d3 = Math.cos(d);
        for (Entity entity : MC.client3.world.getEntities()) {
            if (entity == MC.client3.player) continue;
            if (!(entity instanceof LivingEntity)) continue;
            if (entity.distanceTo((Entity)MC.client3.player) > this.range.getFloat35()) continue;
            boolean bl4 = entity instanceof PlayerEntity;
            if (bl4) {
                if (!((Boolean)this.players.getObj()).booleanValue()) continue;
            }
            if (!bl4) {
                if (!(entity instanceof MobEntity)) continue;
            }
            if (!bl4 && !((Boolean)this.mobs.getObj()).booleanValue()) continue;
            double d4 = entity.getX() - MC.client3.player.getX();
            double d5 = entity.getZ() - MC.client3.player.getZ();
            if (((Boolean)this.rotate.getObj()).booleanValue()) {
                double d6 = d4 * d3 + d5 * d2;
                double d7 = d5 * d3 - d4 * d2;
                d4 = d6;
                d5 = d7;
            }
            int n7 = n3 + (int)Math.round(d4 / (Double)this.range.getObj() * (double)n);
            int n8 = n2 + (int)Math.round(d5 / (Double)this.range.getObj() * (double)n);
            if (n7 < n6 + 2) continue;
            if (n7 > n6 + n4 - 3) continue;
            if (n8 < n5 + 2 || n8 > n5 + n4 - 3) continue;
            this.m83(drawContext, n7, n8, bl4 ? (Integer)this.playerColor.getObj() : (Integer)this.mobColor.getObj(), 2);
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

