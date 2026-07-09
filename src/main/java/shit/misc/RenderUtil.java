/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import shit.manager.RenderManager2;
import shit.misc.ModuleState;
import shit.misc.NUWed;
import shit.module.Module;
import shit.type.Enum_NuWsin;
import shit.util.ClickGUI;
import shit.util.FontUtil2;
import shit.util.RenderUtil3;

@Environment(value=EnvType.CLIENT)
public class RenderUtil
extends ModuleState {
    private final TextRenderer field51;
    private final Module module3;
    private final RenderManager2 renderManager2;
    private final NUWed field24;
    private final NUWed field67;

    public RenderUtil(TextRenderer textRenderer, Module module, RenderManager2 renderManager2) {
        block0: {
            this.field24 = new NUWed(Enum_NuWsin.OUT_QUART, 250.0f);
            this.field67 = new NUWed(Enum_NuWsin.OUT_CUBIC, 180.0f);
            this.field51 = textRenderer;
            this.module3 = module;
            this.renderManager2 = renderManager2;
            this.count119 = 13;
            if (Module.getTextArray9() != null) break block0;
            ModuleState.setModuleArray3(new Module[1]);
        }
    }

    @Override
    public void m945(Object object, int n, int n2) {
        block2: {
            DrawContext drawContext = (DrawContext)object;
            int n3 = n;
            int n4 = n2;
            boolean bl = this.m120(n3, n4, this.count108, this.count103, this.count141, this.count119);
            Object var8_8 = null;
            float f = this.field24.m170(this.module3.isSet19() ? 1.0f : 0.0f);
            float f2 = this.field67.m170(bl ? 1.0f : 0.0f);
            drawContext.fill(this.count108, this.count103, this.count108 + this.count141, this.count103 + this.count119, RenderUtil3.m517(ClickGUI.getInt49(), 102));
            if (f > 0.01f) {
                drawContext.fill(this.count108, this.count103, this.count108 + Math.max(1, (int)((float)this.count141 * f)), this.count103 + this.count119, RenderUtil3.m517(ClickGUI.getInt38(), 170));
            }
            if (f2 > 0.01f) {
                drawContext.fill(this.count108, this.count103, this.count108 + this.count141, this.count103 + this.count119, RenderUtil3.m23(ClickGUI.getInt10(), f2));
            }
            int n5 = this.module3.isSet19() ? -1 : (bl ? ClickGUI.getInt27() : RenderUtil3.m517(ClickGUI.getInt27(), 232));
            FontUtil2.m640(this.field51, drawContext, this.module3.getText43(), this.count108 + 2 + Math.round(2.0f * f2), this.count103 + 3, n5);
            if (null == null) break block2;
            Module.setTextArray9(new String[3]);
        }
    }

    @Override
    public boolean m851(double d, double d2, int n) {
        block3: {
            double d3 = d;
            double d4 = d2;
            int n2 = n;
            Object var12_7 = null;
            if (!this.flag162) {
                return false;
            }
            if (!this.m120(d3, d4, this.count108, this.count103, this.count141, this.count119)) {
                return false;
            }
            if (n2 == 0) {
                this.module3.m84();
            }
            if (n2 != 1) break block3;
            this.renderManager2.m419((int)d3, (int)d4);
        }
        return true;
    }

    public RenderManager2 getRenderManager2() {
        return this.renderManager2;
    }

    public Module getModule2() {
        return this.module3;
    }
}

