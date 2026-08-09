/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import java.util.concurrent.CopyOnWriteArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import shit.Client;
import shit.api.Listener3;
import shit.event.EventHandler;
import shit.event.ModuleToggleEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.hud.AbstractHudModule;
import shit.render.Outline;
import shit.render.Passthrough;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.RenderUtil3;

@Environment(value=EnvType.CLIENT)
public class Notification
extends Module
implements Listener3 {
    private static Notification INSTANCE;
    private final BooleanSetting moduleToggle = (BooleanSetting)this.registerSetting(new BooleanSetting("ModuleToggle", true));
    private final NumberSetting displayTime = (NumberSetting)this.registerSetting(new NumberSetting("DisplayTime", 1000.0, 500.0, 5000.0, 100.0));
    private final NumberSetting width = (NumberSetting)this.registerSetting(new NumberSetting("Width", 170.0, 100.0, 300.0, 5.0));
    private final NumberSetting height = (NumberSetting)this.registerSetting(new NumberSetting("Height", 28.0, 20.0, 48.0, 1.0));
    private final NumberSetting rectWidth = (NumberSetting)this.registerSetting(new NumberSetting("RectWidth", 2.0, 1.0, 8.0, 1.0));
    private final NumberSetting scale = (NumberSetting)this.registerSetting(new NumberSetting("Scale", 1.0, 0.5, 2.0, 0.1));
    private final ColorSetting enableColor = (ColorSetting)this.registerSetting(new ColorSetting("EnableColor", -13573454));
    private final ColorSetting disableColor = (ColorSetting)this.registerSetting(new ColorSetting("DisableColor", -2346446));
    private final ColorSetting infoColor = (ColorSetting)this.registerSetting(new ColorSetting("InfoColor", -10250607));
    private final ColorSetting backgroundColor = (ColorSetting)this.registerSetting(new ColorSetting("BackgroundColor", -1273752556));
    private final ColorSetting titleColor = (ColorSetting)this.registerSetting(new ColorSetting("TitleColor", -1));
    private final ColorSetting messageColor = (ColorSetting)this.registerSetting(new ColorSetting("MessageColor", -4868683));
    private final BooleanSetting blur = (BooleanSetting)this.registerSetting(new BooleanSetting("Blur", true));
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 2.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y;
    private final CopyOnWriteArrayList<Inner> copyOnWriteArrayList3;

    public Notification() {
        super("Notification", "Client notifications with staged animation.", Category.HUD);
        boolean bl = true;
        this.y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 2.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
        this.copyOnWriteArrayList3 = new CopyOnWriteArrayList();
        this.setEnabled(true);
        INSTANCE = this;
        if (Module.getTextArray9() == null) {
            AbstractHudModule.setFlag16(false);
        }
    }

    @EventHandler
    private void setModuleToggleEvent(ModuleToggleEvent moduleToggleEvent) {
        if (!((Boolean)this.moduleToggle.getValue()).booleanValue() || moduleToggleEvent.getModule() == this) {
            return;
        }
        if (!moduleToggleEvent.getModule().isDrawn()) {
            return;
        }
        String string = Outline.m168((Object)moduleToggleEvent.getModule().getCategory());
        String string2 = Outline.m14(moduleToggleEvent.isSet168() ? "Enabled" : "Disabled");
        String string3 = moduleToggleEvent.getModule().getDisplayName() + " " + string2;
        Type type = moduleToggleEvent.isSet168() ? Type.ENABLE : Type.DISABLE;
        this.copyOnWriteArrayList3.add(new Inner(string, string3, type, this.getLong13()));
    }

    @Override
    public int getHudX() {
        return Math.max(0, MC.mc.getWindow().getScaledWidth() - this.getInt51() - this.x.getInt());
    }

    @Override
    public int getHudY() {
        return this.y.getInt();
    }

    @Override
    public int hudWidth() {
        return this.getInt51();
    }

    @Override
    public int getHudHeight() {
        return this.getInt54();
    }

    @Override
    public void setHudPosition(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        this.x.setDouble(Math.max(0, MC.mc.getWindow().getScaledWidth() - this.getInt51() - n3));
        this.y.setDouble(Math.max(0, n4));
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void renderHud(Object var1_1, boolean var2_2) {
        DrawContext ctx = (DrawContext) var1_1;
        boolean editing = var2_2;
        float y = this.getHudY();
        float scale = this.scale.getFloat();
        if (editing && this.copyOnWriteArrayList3.isEmpty()) {
            this.m201(ctx, "Example", "Example Notification", Type.INFO, this.getHudX(), y, scale, 1.0f, 1.0f, 1.0f);
            return;
        }
        for (Inner inner : this.copyOnWriteArrayList3) {
            if (inner.isSet79()) {
                this.copyOnWriteArrayList3.remove(inner);
            }
            if (inner.value147 == 0.0f) {
                inner.value147 = y;
            } else {
                inner.value147 = inner.value147 + (y - inner.value147) * 0.3f;
            }
            float alpha = inner.getFloat22();
            float scaleX = inner.getFloat50();
            float scaleY = inner.getFloat14();
            int x = MC.mc.getWindow().getScaledWidth() - this.getInt51() - this.x.getInt();
            this.m201(ctx, inner.text2196, inner.text3188, inner.type8, x, inner.value147, scale, scaleX, alpha, scaleY);
            y += (float) (this.getInt54() + 4) * alpha;
        }
    }

    private void m201(Object object, Object object2, Object object3, Object object4, int n, float f, float f2, float f3, float f4, float f5) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        float f6;
        String string;
        String string2;
        DrawContext drawContext;
        block5: {
            int n7;
            int n8;
            block4: {
                drawContext = (DrawContext)object;
                string2 = (String)object2;
                string = (String)object3;
                Type type = (Type)((Object)object4);
                int n9 = n;
                float f7 = f;
                float f8 = f3;
                f6 = f5;
                int n10 = this.getInt51();
                n6 = this.getInt54();
                boolean bl = true;
                n5 = this.rectWidth.getInt();
                n8 = Math.max(1, Math.round((float)n10 * f8));
                n4 = n9 + n10 - n8;
                n3 = Math.round(f7);
                n2 = this.m646((Object)type);
                if (f6 <= 0.01f) {
                    RenderUtil3.m526(drawContext, n4, n3, n8, n6, n2);
                    return;
                }
                int n11 = (Integer)this.backgroundColor.getValue();
                int n12 = (int)((float)(n11 >>> 24 & 0xFF) * f6);
                n7 = n12 << 24 | n11 & 0xFFFFFF;
                if (!((Boolean)this.blur.getValue()).booleanValue()) break block4;
                Passthrough.m990(drawContext, 6, n4, n3, n8, n6, n7, false);
                if (true) break block5;
            }
            RenderUtil3.m526(drawContext, n4, n3, n8, n6, n7);
        }
        RenderUtil3.m526(drawContext, n4, n3, n5, n6, n2);
        int n13 = n4 + n5 + 6;
        int n14 = n3 + 4;
        int n15 = n3 + 4 + Client.fontManager.renderer2().getFontHeight() + 2;
        int n16 = (Integer)this.titleColor.getValue();
        int n17 = (Integer)this.messageColor.getValue();
        int n18 = Math.round((float)(n16 >>> 24 & 0xFF) * f6);
        int n19 = Math.round((float)(n17 >>> 24 & 0xFF) * f6);
        n16 = n18 << 24 | n16 & 0xFFFFFF;
        n17 = n19 << 24 | n17 & 0xFFFFFF;
        RenderUtil3.m454(MC.mc.textRenderer, drawContext, string2, n13, n14, n16, true);
        RenderUtil3.m454(MC.mc.textRenderer, drawContext, string, n13, n15, n17, false);
    }

    private int m646(Object object) {
        Type type = (Type)((Object)object);
        boolean bl = true;
        return switch (type.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> (Integer)this.enableColor.getValue();
            case 1 -> (Integer)this.disableColor.getValue();
            case 2 -> (Integer)this.infoColor.getValue();
        };
    }

    private int getInt51() {
        return Math.round(this.width.getFloat() * this.scale.getFloat());
    }

    private int getInt54() {
        return Math.round(this.height.getFloat() * this.scale.getFloat());
    }

    private long getLong13() {
        return ((Double)this.displayTime.getValue()).longValue() + 600L;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Type {
      ENABLE, DISABLE, INFO;

      private Type() {}



        private static Type[] getTypeArray8() {
            return new Type[]{ENABLE, DISABLE, INFO};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static final class Inner {
        private final String text2196;
        private final String text3188;
        private final Type type8;
        private final long time16;
        private final long time57 = System.currentTimeMillis();
        private float value147;

        private Inner(String string, String string2, Type type, long l) {
            this.text2196 = string;
            this.text3188 = string2;
            this.type8 = type;
            this.time16 = l;
        }

        private long getLong11() {
            return System.currentTimeMillis() - this.time57;
        }

        private boolean isSet79() {
            boolean bl = AbstractHudModule.isSet32();
            long l = this.getLong11() - this.time16;
            long l2 = l == 0L ? 0 : (l < 0L ? -1 : 1);
            if (!bl) {
                l2 = l2 > 0 ? (long)1 : (long)0;
            }
            return l2 > 0;
        }

        private float getFloat50() {
            long l = this.getLong11();
            boolean bl = true;
            long l2 = this.time16 - 300L;
            if (l < 300L) {
                return Inner.m637((float)l / 300.0f);
            }
            if (l > l2) {
                return 1.0f - Inner.m637((float)(l - l2) / 300.0f);
            }
            return 1.0f;
        }

        private float getFloat22() {
            long l = this.getLong11();
            boolean bl = true;
            long l2 = this.time16 - 300L;
            if (l < 200L) {
                return Inner.m637((float)l / 200.0f);
            }
            if (l > l2) {
                return 1.0f - Inner.m637((float)(l - l2) / 300.0f);
            }
            return 1.0f;
        }

        private float getFloat14() {
            long l = this.getLong11();
            long l2 = this.time16 - 500L;
            boolean bl = AbstractHudModule.isSet32();
            long l3 = l - 300L;
            long l4 = l3 == 0L ? 0 : (l3 < 0L ? -1 : 1);
            if (!bl) {
                if (l4 < 0) {
                    return 0.0f;
                }
                long l5 = l - 500L;
                l4 = l5 == 0L ? 0 : (l5 < 0L ? -1 : 1);
            }
            if (!bl) {
                if (l4 < 0) {
                    return Inner.m637((float)(l - 300L) / 200.0f);
                }
                l4 = l == l2 ? 0 : (l < l2 ? -1 : 1);
            }
            if (l4 > 0) {
                return 1.0f - Inner.m637((float)(l - l2) / 200.0f);
            }
            return 1.0f;
        }

        private static float m637(float f) {
            float f2 = f;
            float f3 = Math.max(0.0f, Math.min(1.0f, f2));
            float f4 = 1.0f - f3;
            return 1.0f - f4 * f4 * f4;
        }
    }
}

