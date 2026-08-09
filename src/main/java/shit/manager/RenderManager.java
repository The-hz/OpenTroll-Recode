/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import shit.Client;
import shit.manager.RenderManager2;
import shit.misc.ModuleState;
import shit.misc.NUWed;
import shit.misc.RenderUtil;
import shit.module.Category;
import shit.module.Module;
import shit.render.Outline;
import shit.render.Passthrough;
import shit.type.Enum_NuWsin;
import shit.util.ClickGUI;
import shit.util.FontUtil2;
import shit.util.RenderUtil3;

@Environment(value=EnvType.CLIENT)
public class RenderManager
extends ModuleState {
    private final TextRenderer field16;
    private final Category category;
    private final List<shit.misc.RenderUtil> list9 = new ArrayList<>();
    private boolean flag148;
    private boolean flag144;
    private boolean flag138;
    private boolean flag113;
    private boolean flag69;
    private int count192;
    private int count229;
    private int count80;
    private int count71;
    private int count113;
    private int count150;
    private int count149;
    private int count143;
    private int count185;
    private long time51;
    private boolean flag16;
    private boolean flag20;
    private final NUWed nUWed = new NUWed(Enum_NuWsin.OUT_CUBIC, 220.0f);

    public RenderManager(TextRenderer textRenderer, Category category, int n, int n2) {
        this.field16 = textRenderer;
        this.category = category;
        this.count108 = n;
        this.count103 = n2;
        this.count141 = 80;
        int n3 = RenderManager2.getInt52();
        this.count119 = 400;
        for (Module module : Client.moduleManager.getByCategory((Object)category)) {
            this.list9.add(new RenderUtil(textRenderer, module, new RenderManager2(textRenderer, module)));
            if (n3 != 0) continue;
        }
    }

    @Override
    public void m945(Object object, int n, int n2) {
        DrawContext drawContext = (DrawContext)object;
        int n3 = n;
        int n4 = n2;
        this.m213(drawContext, n3, n4);
        this.m147(drawContext, n3, n4);
    }

    public void m213(Object object, int n, int n2) {
        block10: {
            int n3;
            int n4;
            int n5;
            int n6;
            DrawContext drawContext;
            block9: {
                block8: {
                    drawContext = (DrawContext)object;
                    n6 = n;
                    n5 = n2;
                    this.m838();
                    n4 = 0;
                    float f = this.nUWed.m170(this.flag16 ? 0.0f : 1.0f);
                    int n7 = Math.max(100, this.count119);
                    n3 = 14 + Math.round((float)(n7 - 14) * f);
                    if (!ClickGUI.isSet109()) break block8;
                    Passthrough.m990(drawContext, ClickGUI.getInt6(), this.count108, this.count103, this.count141, n3, ClickGUI.getInt49(), ClickGUI.isSet22());
                    if (!false) break block9;
                }
                drawContext.fill(this.count108, this.count103, this.count108 + this.count141, this.count103 + n3, ClickGUI.getInt49());
            }
            if (ClickGUI.isSet94()) {
                drawContext.fill(this.count108, this.count103, this.count108 + this.count141, this.count103 + 14, ClickGUI.getInt80());
            }
            if (ClickGUI.isSet43()) {
                RenderUtil3.m795(drawContext, this.count108, this.count103, this.count141, n3, ClickGUI.getInt38());
            }
            FontUtil2.drawTextSimple(this.field16, drawContext, this.m736((Object)this.category), this.count108 + ClickGUI.getInt53(), this.count103 + 3, ClickGUI.getInt27());
            if (n3 > 15) {
                drawContext.enableScissor(this.count108, this.count103 + 14, this.count108 + this.count141, this.count103 + n3);
                for (RenderUtil renderUtil : this.list9) {
                    if (renderUtil.flag162) {
                        renderUtil.m945(drawContext, n6, n5);
                    }
                    if (!false) continue;
                }
                drawContext.disableScissor();
            }
            if (Module.getTextArray9() != null) break block10;
            RenderManager2.setInt3(++n4);
        }
    }

    public void m147(Object object, int n, int n2) {
        DrawContext drawContext = (DrawContext)object;
        int n3 = n;
        int n4 = n2;
        Iterator iterator = this.list9.iterator();
        int n5 = RenderManager2.getInt52();
        while (iterator.hasNext()) {
            block4: {
                RenderManager2 renderManager2;
                block3: {
                    RenderManager2 renderManager22;
                    RenderUtil renderUtil = (RenderUtil)iterator.next();
                    renderManager2 = renderManager22 = renderUtil.getRenderManager2();
                    if (n5 == 0) break block3;
                    if (!renderManager2.isSet60()) break block4;
                    renderManager2 = renderManager22;
                }
                renderManager2.m945(drawContext, n3, n4);
            }
            if (n5 != 0) continue;
        }
    }

    @Override
    public boolean m851(double d, double d2, int n) {
        int n2;
        double d3;
        double d4;
        block12: {
            block11: {
                d4 = d;
                d3 = d2;
                n2 = n;
                boolean bl = false;
                if (n2 == 0) {
                    if (this.m154(d4, d3)) {
                        this.m317(d4, d3);
                        return true;
                    }
                }
                if (this.checkState(d4, d3, this.count108, this.count103, this.count141, 14)) {
                    if (n2 == 0) {
                        this.m774();
                        this.flag148 = true;
                        this.flag20 = false;
                        this.count192 = (int)d4 - this.count108;
                        this.count229 = (int)d3 - this.count103;
                        return true;
                    }
                }
                if (this.checkState(d4, d3, this.count108, this.count103, this.count141, 14)) {
                    if (n2 == 1) {
                        this.flag16 = !this.flag16;
                        return true;
                    }
                }
                if (this.flag16) break block11;
                if (this.m928(d4, d3)) break block12;
            }
            return false;
        }
        for (RenderUtil renderUtil : this.list9) {
            if (!renderUtil.flag162) continue;
            if (renderUtil.m851(d4, d3, n2)) {
                return true;
            }
            if (!false) continue;
        }
        return false;
    }

    public boolean m3(double d, double d2, int n) {
        double d3 = d;
        double d4 = d2;
        int n2 = n;
        boolean bl = false;
        for (int i = this.list9.size() - 1; i >= 0; --i) {
            RenderManager2 renderManager2 = ((RenderUtil)this.list9.get(i)).getRenderManager2();
            if (!renderManager2.isSet60()) continue;
            if (!renderManager2.m851(d3, d4, n2)) continue;
            return true;
        }
        return false;
    }

    public boolean m103(double d, double d2, int n) {
        double d3 = d;
        double d4 = d2;
        int n2 = n;
        boolean bl = false;
        for (int i = this.list9.size() - 1; i >= 0; --i) {
            RenderManager2 renderManager2 = ((RenderUtil)this.list9.get(i)).getRenderManager2();
            if (!renderManager2.isSet60()) continue;
            if (!renderManager2.m510(d3, d4, n2)) continue;
            return true;
        }
        return false;
    }

    public boolean m479(double d, double d2, int n, double d3, double d4) {
        boolean bl;
        block3: {
            double d5 = d;
            double d6 = d2;
            int n2 = n;
            double d7 = d3;
            double d8 = d4;
            int n3 = this.list9.size() - 1;
            int n4 = RenderManager2.getInt52();
            while (n3 >= 0) {
                block4: {
                    block5: {
                        boolean bl2;
                        block6: {
                            RenderManager2 renderManager2 = ((RenderUtil)this.list9.get(n3)).getRenderManager2();
                            if (n4 == 0) break block4;
                            bl = renderManager2.isSet60();
                            if (n4 == 0) break block3;
                            if (!bl) break block5;
                            bl2 = renderManager2.m855(d5, d6, n2, d7, d8);
                            if (n4 == 0) break block6;
                            if (!bl2) break block5;
                            bl2 = true;
                        }
                        return bl2;
                    }
                    --n3;
                }
                if (n4 != 0) continue;
            }
            bl = false;
        }
        return bl;
    }

    public boolean m865(double d, double d2, double d3) {
        boolean bl;
        block3: {
            double d4 = d;
            double d5 = d2;
            double d6 = d3;
            int n = this.list9.size() - 1;
            int n2 = RenderManager2.getInt52();
            while (n >= 0) {
                block4: {
                    block5: {
                        boolean bl2;
                        block6: {
                            RenderManager2 renderManager2 = ((RenderUtil)this.list9.get(n)).getRenderManager2();
                            if (n2 == 0) break block4;
                            bl = renderManager2.isSet60();
                            if (n2 == 0) break block3;
                            if (!bl) break block5;
                            bl2 = renderManager2.m671(d4, d5, d6);
                            if (n2 == 0) break block6;
                            if (!bl2) break block5;
                            bl2 = true;
                        }
                        return bl2;
                    }
                    --n;
                }
                if (n2 != 0) continue;
            }
            bl = false;
        }
        return bl;
    }

    public boolean m117(double d, double d2, double d3) {
        int n;
        int n2;
        double d4;
        block14: {
            block15: {
                boolean bl;
                block12: {
                    block13: {
                        double d5;
                        double d6;
                        block8: {
                            d6 = d;
                            d5 = d2;
                            d4 = d3;
                            Iterator iterator = this.list9.iterator();
                            n2 = RenderManager2.getInt52();
                            while (iterator.hasNext()) {
                                block10: {
                                    boolean bl2 = false;
                                    block11: {
                                        block9: {
                                            RenderUtil renderUtil = (RenderUtil)iterator.next();
                                            RenderManager2 renderManager2 = renderUtil.getRenderManager2();
                                            bl = renderManager2.isSet60();
                                            if (n2 == 0) break block8;
                                            if (n2 == 0) break block9;
                                            if (!bl) break block10;
                                            bl2 = renderManager2.m671(d6, d5, d4);
                                        }
                                        if (n2 == 0) break block11;
                                        if (!bl2) break block10;
                                        bl2 = true;
                                    }
                                    return bl2;
                                }
                                if (n2 != 0) continue;
                            }
                            bl = this.flag16;
                        }
                        if (n2 == 0) break block12;
                        if (bl) break block13;
                        n = this.checkState(d6, d5, this.count108, this.count103, this.count141, this.getInt43()) ? 1 : 0;
                        if (n2 == 0) break block14;
                        if (n != 0) break block15;
                    }
                    bl = false;
                }
                return bl;
            }
            n = Math.max(0, this.getInt56() - Math.max(1, this.count119 - 14));
        }
        boolean bl = n != 0;
        this.count185 = Math.max(0, Math.min(bl ? 1 : 0, this.count185 - (int)Math.round(d4 * (double)(13 + ClickGUI.getInt86()))));
        boolean bl3 = bl;
        if (n2 != 0) {
            bl3 = bl3;
        }
        return bl3;
    }

    @Override
    public boolean m510(double d, double d2, int n) {
        boolean bl;
        block9: {
            RenderManager renderManager;
            int n2;
            int n3;
            double d3;
            double d4;
            block12: {
                block13: {
                    int n4;
                    block14: {
                        block15: {
                            block10: {
                                block11: {
                                    d4 = d;
                                    d3 = d2;
                                    n3 = n;
                                    n2 = RenderManager2.getInt52();
                                    n4 = this.flag148 ? 1 : 0;
                                    if (n2 == 0) break block10;
                                    if (n4 != 0) break block11;
                                    renderManager = this;
                                    if (n2 == 0) break block12;
                                    if (!renderManager.isSet154()) break block13;
                                }
                                n4 = this.flag148 ? 1 : 0;
                            }
                            if (n2 == 0) break block14;
                            if (n4 == 0) break block15;
                            n4 = n3;
                            if (n2 == 0) break block14;
                            if (n4 != 0) break block15;
                            n4 = this.flag20 ? 1 : 0;
                            if (n2 == 0) break block14;
                            if (n4 == 0) {
                                boolean bl2 = this.flag16;
                                if (n2 != 0) {
                                    bl2 = !bl2;
                                }
                                this.flag16 = bl2;
                            }
                        }
                        this.flag148 = false;
                        this.flag113 = false;
                        this.flag144 = false;
                        this.flag69 = false;
                        this.flag138 = false;
                        n4 = 1;
                    }
                    return n4 != 0;
                }
                renderManager = this;
            }
            for (RenderUtil renderUtil : renderManager.list9) {
                block17: {
                    boolean bl3 = false;
                    block18: {
                        block16: {
                            RenderManager2 renderManager2 = renderUtil.getRenderManager2();
                            bl = renderManager2.isSet60();
                            if (n2 == 0) break block9;
                            if (n2 == 0) break block16;
                            if (!bl) break block17;
                            bl3 = renderManager2.m510(d4, d3, n3);
                        }
                        if (n2 == 0) break block18;
                        if (!bl3) break block17;
                        bl3 = true;
                    }
                    return bl3;
                }
                if (n2 != 0) continue;
            }
            bl = false;
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public boolean m855(double var1_2, double var3_3, int var5_4, double var6_5, double var8_1) {
        return false;
    }

    public String m329(double d, double d2) {
        RenderManager renderManager;
        int n;
        double d3;
        double d4;
        block8: {
            block9: {
                block7: {
                    boolean bl;
                    block6: {
                        d4 = d;
                        d3 = d2;
                        n = RenderManager2.getInt52();
                        bl = this.flag16;
                        if (n == 0) break block6;
                        if (bl) break block7;
                        renderManager = this;
                        if (n == 0) break block8;
                        bl = renderManager.m928(d4, d3);
                    }
                    if (bl) break block9;
                }
                return "";
            }
            renderManager = this;
        }
        for (RenderUtil renderUtil : renderManager.list9) {
            boolean bl = renderUtil.flag162;
            if (n != 0) {
                if (!bl) continue;
                bl = this.checkState(d4, d3, renderUtil.count108, renderUtil.count103, renderUtil.count141, renderUtil.count119);
            }
            if (bl) {
                return renderUtil.getModule2().getDisplayDescription();
            }
            if (n != 0) continue;
        }
        return "";
    }

    @Override
    public boolean m121(int n, int n2, int n3) {
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        Iterator iterator = this.list9.iterator();
        boolean bl = false;
        while (iterator.hasNext()) {
            RenderUtil renderUtil = (RenderUtil)iterator.next();
            RenderManager2 renderManager2 = renderUtil.getRenderManager2();
            if (renderManager2.isSet60()) {
                if (renderManager2.m121(n4, n5, n6)) {
                    return true;
                }
            }
            if (!false) continue;
        }
        return false;
    }

    @Override
    public boolean m650(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        Iterator iterator = this.list9.iterator();
        boolean bl = false;
        while (iterator.hasNext()) {
            RenderUtil renderUtil = (RenderUtil)iterator.next();
            RenderManager2 renderManager2 = renderUtil.getRenderManager2();
            if (renderManager2.isSet60()) {
                if (renderManager2.m650(n3, n4)) {
                    return true;
                }
            }
            if (!false) continue;
        }
        return false;
    }

    private void m838() {
        int n = Math.max(0, this.getInt56() - Math.max(1, this.count119 - 14));
        this.count185 = Math.max(0, Math.min(n, this.count185));
        int n2 = this.count103 + 14 + ClickGUI.getInt86() - this.count185;
        boolean bl = false;
        for (RenderUtil renderUtil : this.list9) {
            if (!renderUtil.flag162) continue;
            renderUtil.count108 = this.count108 + ClickGUI.getInt53();
            renderUtil.count103 = n2;
            renderUtil.count141 = this.count141 - ClickGUI.getInt53() * 2;
            n2 += 13 + ClickGUI.getInt86();
            if (!false) continue;
        }
    }

    private int getInt56() {
        int n;
        block3: {
            int n2 = 0;
            int n3 = RenderManager2.getInt52();
            for (RenderUtil renderUtil : this.list9) {
                n = renderUtil.flag162 ? 1 : 0;
                if (n3 != 0) {
                    if (n != 0) {
                        ++n2;
                    }
                    if (n3 != 0) continue;
                }
                break block3;
            }
            n = ClickGUI.getInt86() + n2 * (13 + ClickGUI.getInt86());
        }
        return n;
    }

    private void m774() {
        block1: {
            long l;
            block0: {
                l = System.currentTimeMillis();
                boolean bl = false;
                if (l - this.time51 > 500L) break block0;
                this.count119 = Math.max(100, Math.min(400, 14 + this.getInt56()));
                this.count185 = 0;
                this.time51 = 0L;
                if (!false) break block1;
            }
            this.time51 = l;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public void setObj87(Object object) {
        String string = (String)object;
        Iterator iterator = this.list9.iterator();
        int n = RenderManager2.getInt52();
        do {
            boolean bl;
            RenderUtil renderUtil;
            block5: {
                block4: {
                    String string2;
                    block3: {
                        if (!iterator.hasNext()) return;
                        renderUtil = (RenderUtil)iterator.next();
                        string2 = string;
                        if (n == 0) break block3;
                        if (string2 == null) break block4;
                        string2 = string;
                    }
                    bl = string2.isEmpty();
                    if (n == 0) continue;
                    if (bl) break block4;
                    bl = this.m97(renderUtil.getModule2().getName()).contains(string);
                    if (n == 0) continue;
                    if (bl) break block4;
                    bl = this.m97(renderUtil.getModule2().getDisplayName()).contains(string);
                    if (n == 0) continue;
                    if (!bl) break block5;
                }
                bl = true;
                continue;
            }
            bl = renderUtil.flag162 = false;
        } while (n != 0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m154(double d, double d2) {
        double d3 = d;
        double d4 = d2;
        int n = this.getInt43();
        int n2 = RenderManager2.getInt52();
        boolean bl = this.flag16;
        if (n2 != 0) {
            if (bl) return false;
            bl = this.checkState(d3, d4, this.count108 - 3, this.count103 - 3, this.count141 + 6, n + 6);
        }
        if (n2 != 0) {
            if (!bl) return false;
            bl = this.checkState(d3, d4, this.count108 + 3, this.count103 + 3, this.count141 - 6, n - 6);
        }
        if (n2 == 0) return bl;
        if (bl) return false;
        return true;
    }

    private boolean m928(double d, double d2) {
        double d3 = d;
        double d4 = d2;
        return this.checkState(d3, d4, this.count108, this.count103 + 14, this.count141, Math.max(0, this.getInt43() - 14));
    }

    private int getInt43() {
        boolean bl = false;
        float f = this.flag16 ? this.nUWed.getFloat67() : this.nUWed.getFloat67();
        return 14 + Math.round((float)(Math.max(100, this.count119) - 14) * f);
    }

    private void m317(double d, double d2) {
        double d3 = d;
        double d4 = d2;
        this.count80 = (int)d3;
        this.count71 = (int)d4;
        this.count113 = this.count108;
        int n = RenderManager2.getInt52();
        this.count150 = this.count103;
        this.count149 = this.count141;
        this.count143 = this.count119;
        double d5 = d3 - (double)(this.count108 + 3);
        double d6 = d5 == 0.0 ? 0 : (d5 < 0.0 ? -1 : 1);
        if (n != 0) {
            d6 = d6 <= 0 ? 1.0 : 0.0;
        }
        this.flag113 = d6 != 0.0;
        double d7 = d3 - (double)(this.count108 + this.count141 - 3);
        double d8 = d7 == 0.0 ? 0 : (d7 > 0.0 ? 1 : -1);
        if (n != 0) {
            d8 = d8 >= 0 ? 1.0 : 0.0;
        }
        this.flag144 = d8 != 0.0;
        double d9 = d4 - (double)(this.count103 + 3);
        double d10 = d9 == 0.0 ? 0 : (d9 < 0.0 ? -1 : 1);
        if (n != 0) {
            d10 = d10 <= 0 ? 1.0 : 0.0;
        }
        this.flag69 = d10 != 0.0;
        double d11 = d4 - (double)(this.count103 + this.count119 - 3);
        double d12 = d11 == 0.0 ? 0 : (d11 > 0.0 ? 1 : -1);
        if (n != 0) {
            d12 = d12 >= 0 ? 1.0 : 0.0;
        }
        this.flag138 = d12 != 0.0;
    }

    private void m1029(double d, double d2) {
        int n;
        double d3 = d;
        double d4 = d2;
        int n2 = (int)d3 - this.count80;
        int n3 = (int)d4 - this.count71;
        boolean bl = false;
        if (this.flag144) {
            this.count141 = this.m586(this.count149 + n2, 80, 200);
        }
        if (this.flag138) {
            this.count119 = Math.max(100, this.count143 + n3);
        }
        if (this.flag113) {
            n = this.m586(this.count149 - n2, 80, 200);
            this.count108 = this.count113 + this.count149 - n;
            this.count141 = n;
        }
        if (this.flag69) {
            n = Math.max(100, this.count143 - n3);
            this.count103 = this.count150 + this.count143 - n;
            this.count119 = n;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean isSet154() {
        int n = RenderManager2.getInt52();
        boolean bl = this.flag113;
        if (n == 0) return bl;
        if (bl) return true;
        bl = this.flag144;
        if (n == 0) return bl;
        if (bl) return true;
        bl = this.flag69;
        if (n == 0) return bl;
        if (bl) return true;
        bl = this.flag138;
        if (n == 0) return bl;
        if (!bl) return false;
        return true;
    }

    private int m586(int n, int n2, int n3) {
        int n4 = n;
        int n5 = n2;
        int n6 = n3;
        return Math.max(n5, Math.min(n6, n4));
    }

    private String m97(Object object) {
        String string = (String)object;
        return string.replace(" ", "").toLowerCase(Locale.ROOT);
    }

    private String m736(Object object) {
        Category category = (Category)((Object)object);
        if (Outline.isSet130()) {
            return Outline.m168((Object)category);
        }
        String string = category.name().toLowerCase();
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }
}

