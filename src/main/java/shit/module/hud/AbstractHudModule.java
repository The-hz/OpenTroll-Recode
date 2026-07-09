/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import shit.Client;
import shit.api.Listener3;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public abstract class AbstractHudModule
extends Module
implements Listener3 {
    private final NumberSetting x;
    private final NumberSetting y;
    protected final BooleanSetting shadow = (BooleanSetting)this.m28(new BooleanSetting("Shadow", true));
    protected final ColorSetting color = (ColorSetting)this.m28(new ColorSetting("Color", -1184275));
    protected final ColorSetting accent = (ColorSetting)this.m28(new ColorSetting("Accent", -9971969));
    private static boolean flag22;

    protected AbstractHudModule(String string, String string2, int n, int n2) {
        super(string, string2, Category.HUD);
        this.x = (NumberSetting)this.m28(new NumberSetting("X", n, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
        this.y = (NumberSetting)this.m28(new NumberSetting("Y", n2, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
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
        int n;
        block2: {
            int n2 = 0;
            Iterator iterator = this.lines().iterator();
            boolean bl = AbstractHudModule.isSet32();
            while (iterator.hasNext()) {
                String string = (String)iterator.next();
                n = Math.max(n2, Client.fontManager.renderer2().m277(string));
                if (!bl) {
                    n2 = n;
                    if (!bl) continue;
                }
                break block2;
            }
            n = n2;
        }
        return n;
    }

    @Override
    public int getInt28() {
        return Math.max(1, this.lines().size()) * this.getInt75();
    }

    @Override
    public void m274(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        this.x.setObj85(n3);
        this.y.setObj85(n4);
    }

    @Override
    public void m368(Object object, boolean n) {
        int n2;
        boolean bl;
        List<String> list;
        DrawContext drawContext;
        block5: {
            block6: {
                drawContext = (DrawContext)object;
                int n3 = n ? 1 : 0;
                list = this.lines();
                bl = AbstractHudModule.isSet32();
                n2 = list.isEmpty() ? 1 : 0;
                if (bl) break block5;
                if (n2 == 0) break block6;
                n2 = n3;
                if (bl) break block5;
                if (n2 != 0) {
                    list = List.of(this.getText43());
                }
            }
            n2 = this.y.getInt50();
        }
        int n4 = n2;
        for (String string : list) {
            Client.fontManager.renderer2().m5(drawContext, string, this.x.getInt50(), n4, (Integer)this.color.getObj(), (Boolean)this.shadow.getObj());
            n4 += this.getInt75();
            if (!bl) continue;
        }
    }

    protected int getInt75() {
        return Client.fontManager.renderer2().getInt19() + 1;
    }

    protected abstract List lines();

    public static void setFlag16(boolean bl) {
        flag22 = bl;
    }

    public static boolean isSet23() {
        return flag22;
    }

    public static boolean isSet32() {
        boolean bl = true;
        return !true;
    }

    static {
        boolean bl = false;
        String string = "\u00a4\u0090l\u0086\u0006\u0095\u008d\u0019\u00e2\u00a6i";
        int n = 11;
        int n2 = 4;
        AbstractHudModule.setFlag16(true);
    }
}

