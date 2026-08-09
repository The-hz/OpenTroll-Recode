/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import shit.misc.ModuleState;
import shit.misc.EasingAnimation;
import shit.misc.EmptySettingRenderer;
import shit.module.Module;
import shit.render.ScreenCopyRenderer;
import shit.setting.Setting;
import shit.type.EasingMode;
import shit.util.ClickGUI;
import shit.util.FontUtil2;
import shit.util.VanillaTextHelper;

@Environment(value=EnvType.CLIENT)
public class ModuleSettingRenderer
extends ModuleState {
    private final TextRenderer field42;
    private final Module module;
    private final List<EmptySettingRenderer> list22;
    private final EasingAnimation nUWed;
    private boolean flag182;
    private boolean flag161;
    private boolean flag165;
    private boolean flag74;
    private boolean flag57;
    private int count187;
    private int count81;
    private int count194;
    private int count213;
    private int count62;
    private int count75;
    private int count78;
    private int count227;
    private int count98;
    private int count144;
    private static int count89;

    public ModuleSettingRenderer(TextRenderer textRenderer, Module module) {
        int n = ModuleSettingRenderer.getInt52();
        this.list22 = new ArrayList();
        this.nUWed = new EasingAnimation(EasingMode.OUT_EXPO, 220.0f);
        this.field42 = textRenderer;
        this.module = module;
        this.count141 = 150;
        this.count108 = 120;
        this.count103 = 80;
        int n2 = n;
        this.flag162 = false;
        this.nUWed.setFloat(0.0f);
        for (Setting setting : module.getSettings()) {
            this.list22.add(new EmptySettingRenderer(textRenderer, setting));
            if (n2 != 0) continue;
        }
    }

    public void m419(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        this.count108 = n3;
        this.count103 = n4;
        this.flag162 = true;
    }

    public void m109() {
        this.flag162 = false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet60() {
        boolean bl = false;
        if (this.flag162) return true;
        if (!(this.nUWed.getFloat67() > 0.02f)) return false;
        return true;
    }

    @Override
    public void m945(Object object, int n, int n2) {
        block10: {
            int n3;
            int n4;
            int n5;
            int n6;
            DrawContext drawContext;
            block13: {
                block12: {
                    block11: {
                        drawContext = (DrawContext)object;
                        n6 = n;
                        n5 = n2;
                        int n7 = ModuleSettingRenderer.getInt52();
                        this.m438();
                        int n8 = this.getInt70();
                        n4 = n7;
                        float f = this.nUWed.m170(this.flag162 ? 1.0f : 0.0f);
                        n3 = Math.max(14, Math.round((float)n8 * f));
                        if (n4 == 0) break block11;
                        if (!ClickGUI.isSet109()) break block12;
                        ScreenCopyRenderer.m990(drawContext, ClickGUI.getInt6(), this.count108, this.count103, this.count141, n3, ClickGUI.getInt49(), ClickGUI.isSet22());
                    }
                    if (n4 != 0) break block13;
                    Module.setTextArray9(new String[2]);
                }
                drawContext.fill(this.count108, this.count103, this.count108 + this.count141, this.count103 + n3, ClickGUI.getInt49());
            }
            boolean bl = ClickGUI.isSet94();
            if (n4 != 0) {
                if (bl) {
                    drawContext.fill(this.count108, this.count103, this.count108 + this.count141, this.count103 + 14, ClickGUI.getInt80());
                }
                bl = ClickGUI.isSet43();
            }
            if (bl) {
                VanillaTextHelper.m795(drawContext, this.count108, this.count103, this.count141, n3, ClickGUI.getInt38());
            }
            FontUtil2.drawTextSimple(this.field42, drawContext, this.module.getDisplayName(), this.count108 + ClickGUI.getInt53(), this.count103 + 3, ClickGUI.getInt27());
            drawContext.enableScissor(this.count108, this.count103 + 14, this.count108 + this.count141, this.count103 + n3);
            for (EmptySettingRenderer none : this.list22) {
                block15: {
                    EmptySettingRenderer none2;
                    block16: {
                        int n9;
                        int n10;
                        block14: {
                            if (n4 == 0) break block10;
                            n10 = none.count103;
                            n9 = this.count103 + n3;
                            if (n4 == 0) break block14;
                            if (n10 >= n9) break block15;
                            none2 = none;
                            if (n4 == 0) break block16;
                            n10 = none2.count103 + none.count119;
                            n9 = this.count103 + 14;
                        }
                        if (n10 <= n9) break block15;
                        none2 = none;
                    }
                    none2.m945(drawContext, n6, n5);
                }
                if (n4 != 0) continue;
            }
            drawContext.disableScissor();
        }
    }

    @Override
    public boolean m851(double d, double d2, int n) {
        double d3 = d;
        double d4 = d2;
        int n2 = n;
        boolean bl = false;
        if (!this.checkState(d3, d4, this.count108, this.count103, this.count141, this.getInt68())) {
            return false;
        }
        if (n2 == 0) {
            if (this.m270(d3, d4)) {
                this.m712(d3, d4);
                return true;
            }
        }
        if (this.checkState(d3, d4, this.count108, this.count103, this.count141, 14)) {
            if (n2 == 1) {
                this.m109();
                return true;
            }
        }
        if (this.checkState(d3, d4, this.count108, this.count103, this.count141, 14)) {
            if (n2 == 0) {
                this.flag182 = true;
                this.count187 = (int)d3 - this.count108;
                this.count81 = (int)d4 - this.count103;
                return true;
            }
        }
        for (EmptySettingRenderer none : this.list22) {
            if (none.m851(d3, d4, n2)) {
                return true;
            }
            if (!false) continue;
        }
        return true;
    }

    @Override
    public boolean m510(double d, double d2, int n) {
        double d3 = d;
        double d4 = d2;
        int n2 = n;
        this.flag182 = false;
        this.flag74 = false;
        boolean bl = false;
        this.flag161 = false;
        this.flag57 = false;
        this.flag165 = false;
        for (EmptySettingRenderer none : this.list22) {
            if (none.m510(d3, d4, n2)) {
                return true;
            }
            if (!false) continue;
        }
        return this.checkState(d3, d4, this.count108, this.count103, this.count141, this.getInt68());
    }

    @Override
    public boolean m855(double d, double d2, int n, double d3, double d4) {
        double d5 = d;
        double d6 = d2;
        int n2 = n;
        double d7 = d3;
        double d8 = d4;
        boolean bl = false;
        if (this.isSet39()) {
            if (n2 == 0) {
                this.m113(d5, d6);
                return true;
            }
        }
        if (this.flag182) {
            if (n2 == 0) {
                this.count108 = (int)d5 - this.count187;
                this.count103 = (int)d6 - this.count81;
                return true;
            }
        }
        for (EmptySettingRenderer none : this.list22) {
            if (none.m855(d5, d6, n2, d7, d8)) {
                return true;
            }
            if (!false) continue;
        }
        return false;
    }

    public boolean m671(double d, double d2, double d3) {
        double d4 = d;
        double d5 = d2;
        double d6 = d3;
        int n = ModuleSettingRenderer.getInt52();
        int n2 = this.checkState(d4, d5, this.count108, this.count103, this.count141, this.getInt68()) ? 1 : 0;
        if (n != 0) {
            if (n2 == 0) {
                return false;
            }
            n2 = Math.max(0, this.getInt18() - Math.max(1, this.getInt70() - 14));
        }
        int n3 = n2;
        this.count144 = Math.max(0, Math.min(n3, this.count144 - (int)Math.round(d6 * (double)(13 + ClickGUI.getInt86()))));
        int n4 = n3;
        if (n != 0) {
            n4 = n4 > 0 ? 1 : 0;
        }
        return n4 != 0;
    }

    @Override
    public boolean m121(int n, int n2, int n3) {
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        Iterator iterator = this.list22.iterator();
        boolean bl = false;
        while (iterator.hasNext()) {
            EmptySettingRenderer none = (EmptySettingRenderer)iterator.next();
            if (none.m121(n4, n5, n6)) {
                return true;
            }
            if (!false) continue;
        }
        return false;
    }

    @Override
    public boolean m650(int n, int n2) {
        boolean bl;
        block3: {
            int n3 = n;
            int n4 = n2;
            Iterator iterator = this.list22.iterator();
            int n5 = ModuleSettingRenderer.getInt52();
            while (iterator.hasNext()) {
                block5: {
                    boolean bl2 = false;
                    block4: {
                        EmptySettingRenderer none = (EmptySettingRenderer)iterator.next();
                        bl = none.m650(n3, n4);
                        if (n5 == 0) break block3;
                        if (n5 == 0) break block4;
                        if (!bl) break block5;
                        bl2 = true;
                    }
                    return bl2;
                }
                if (n5 != 0) continue;
            }
            bl = false;
        }
        return bl;
    }

    private void m438() {
        int n = Math.max(0, this.getInt18() - Math.max(1, this.getInt70() - 14));
        this.count144 = Math.max(0, Math.min(n, this.count144));
        int n2 = this.count103 + 14 + ClickGUI.getInt86() - this.count144;
        int n3 = ModuleSettingRenderer.getInt52();
        for (EmptySettingRenderer none : this.list22) {
            int n4 = none.isSet66() ? 1 : 0;
            if (n3 != 0) {
                if (n4 == 0) continue;
                none.count108 = this.count108 + ClickGUI.getInt53();
                none.count103 = n2;
                none.count141 = this.count141 - ClickGUI.getInt53() * 2;
                n4 = n2 + (none.count119 + ClickGUI.getInt86());
            }
            n2 = n4;
            if (n3 != 0) continue;
        }
    }

    private int getInt70() {
        int n = ModuleSettingRenderer.getInt52();
        int n2 = this.count98;
        if (n != 0) {
            if (n2 > 0) {
                return Math.max(60, this.count98);
            }
            n2 = Math.max(60, Math.min(this.getInt18() + 14, 260));
        }
        return n2;
    }

    private int getInt68() {
        return Math.max(14, Math.round((float)this.getInt70() * Math.max(0.0f, this.nUWed.getFloat67())));
    }

    private int getInt18() {
        int n = 0;
        Iterator iterator = this.list22.iterator();
        boolean bl = false;
        while (iterator.hasNext()) {
            EmptySettingRenderer none = (EmptySettingRenderer)iterator.next();
            if (none.isSet66()) {
                ++n;
            }
            if (!false) continue;
        }
        int n2 = ClickGUI.getInt86();
        for (EmptySettingRenderer none : this.list22) {
            if (none.isSet66()) {
                n2 += none.count119 + ClickGUI.getInt86();
            }
            if (!false) continue;
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m270(double d, double d2) {
        double d3 = d;
        double d4 = d2;
        int n = this.getInt70();
        int n2 = ModuleSettingRenderer.getInt52();
        boolean bl = this.checkState(d3, d4, this.count108 - 3, this.count103 - 3, this.count141 + 6, n + 6);
        if (n2 != 0) {
            if (!bl) return false;
            bl = this.checkState(d3, d4, this.count108 + 3, this.count103 + 3, this.count141 - 6, n - 6);
        }
        if (n2 == 0) return bl;
        if (bl) return false;
        return true;
    }

    private void m712(double d, double d2) {
        double d3 = d;
        double d4 = d2;
        int n = this.getInt70();
        this.count194 = (int)d3;
        this.count213 = (int)d4;
        this.count62 = this.count108;
        this.count75 = this.count103;
        this.count78 = this.count141;
        boolean bl = false;
        this.count227 = n;
        this.flag74 = d3 <= (double)(this.count108 + 3);
        this.flag161 = d3 >= (double)(this.count108 + this.count141 - 3);
        this.flag57 = d4 <= (double)(this.count103 + 3);
        this.flag165 = d4 >= (double)(this.count103 + n - 3);
    }

    private void m113(double d, double d2) {
        block10: {
            int n;
            int n2;
            block9: {
                double d3 = d;
                double d4 = d2;
                int n3 = (int)d3 - this.count194;
                int n4 = ModuleSettingRenderer.getInt52();
                int n5 = (int)d4 - this.count213;
                n2 = this.flag161 ? 1 : 0;
                if (n4 != 0) {
                    if (n2 != 0) {
                        this.count141 = Math.max(100, this.count78 + n3);
                    }
                    n2 = this.flag74 ? 1 : 0;
                }
                if (n4 != 0) {
                    if (n2 != 0) {
                        n = Math.max(100, this.count78 - n3);
                        this.count108 = this.count62 + this.count78 - n;
                        this.count141 = n;
                    }
                    n2 = this.flag165 ? 1 : 0;
                }
                if (n4 != 0) {
                    if (n2 != 0) {
                        this.count98 = Math.max(60, this.count227 + n5);
                    }
                    n2 = this.flag57 ? 1 : 0;
                }
                if (n4 == 0) break block9;
                if (n2 == 0) break block10;
                n2 = Math.max(60, this.count227 - n5);
            }
            n = n2;
            this.count103 = this.count75 + this.count227 - n;
            this.count98 = n;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet39() {
        int n = ModuleSettingRenderer.getInt52();
        boolean bl = this.flag74;
        if (n == 0) return bl;
        if (bl) return true;
        bl = this.flag161;
        if (n == 0) return bl;
        if (bl) return true;
        bl = this.flag57;
        if (n == 0) return bl;
        if (bl) return true;
        bl = this.flag165;
        if (n == 0) return bl;
        if (!bl) return false;
        return true;
    }

    public static void setInt3(int n) {
        count89 = n;
    }

    public static int getInt44() {
        return count89;
    }

    public static int getInt52() {
        boolean bl = false;
        return 94;
    }

    static {
        long[] lArray = new long[17];
        ModuleSettingRenderer.setInt3(0);
    }
}

