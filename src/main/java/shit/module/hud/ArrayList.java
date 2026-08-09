/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.gui.DrawContext
 */
package shit.module.hud;

import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import shit.Client;
import shit.api.Listener3;
import shit.module.Category;
import shit.module.Module;
import shit.module.hud.AbstractHudModule;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ArrayList
extends Module
implements Listener3 {
    private final EnumSetting side;
    private final EnumSetting mode;
    private final EnumSetting sortingMode;
    private final EnumSetting case_;
    private final BooleanSetting bindOnly;
    private final BooleanSetting background;
    private final BooleanSetting shadow;
    private final BooleanSetting rainbow;
    private final NumberSetting x;
    private final NumberSetting y;
    private final NumberSetting spacing;
    private final NumberSetting rainbowLength;
    private final NumberSetting indexedHue;
    private final NumberSetting saturation;
    private final NumberSetting brightness;
    private final ColorSetting color;
    private final ColorSetting backgroundColor;
    private final Map<Module, Inner> map24;

        public ArrayList() {
        super("ArrayList", "Draws enabled modules.", Category.HUD);
        this.side = (EnumSetting)this.registerSetting(new EnumSetting("Side", SideMode.RIGHT));
        this.mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.LEFT_TAG));
        this.sortingMode = (EnumSetting)this.registerSetting(new EnumSetting("SortingMode", SortingMode.LENGTH));
        this.case_ = (EnumSetting)this.registerSetting(new EnumSetting("Case", CaseMode.NORMAL));
        this.bindOnly = (BooleanSetting)this.registerSetting(new BooleanSetting("BindOnly", false));
        this.background = (BooleanSetting)this.registerSetting(new BooleanSetting("Background", true));
        this.shadow = (BooleanSetting)this.registerSetting(new BooleanSetting("Shadow", true));
        this.rainbow = (BooleanSetting)this.registerSetting(new BooleanSetting("Rainbow", true));
        this.x = (NumberSetting)this.registerSetting(new NumberSetting("X", 2.0, 0.0, 5000.0, 1.0));
        this.y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 2.0, 0.0, 5000.0, 1.0));
        this.spacing = (NumberSetting)this.registerSetting(new NumberSetting("Spacing", 1.0, 0.0, 5.0, 1.0));
        this.rainbowLength = (NumberSetting)this.registerSetting(new NumberSetting("RainbowLength", 10.0, 1.0, 20.0, 0.5));
        this.indexedHue = (NumberSetting)this.registerSetting(new NumberSetting("IndexedHue", 0.5, 0.0, 1.0, 0.05));
        this.saturation = (NumberSetting)this.registerSetting(new NumberSetting("Saturation", 0.5, 0.0, 1.0, 0.01));
        this.brightness = (NumberSetting)this.registerSetting(new NumberSetting("Brightness", 1.0, 0.0, 1.0, 0.01));
        this.color = (ColorSetting)this.registerSetting(new ColorSetting("Color", -7555876));
        this.backgroundColor = (ColorSetting)this.registerSetting(new ColorSetting("BackgroundColor", 1712658464));
        this.map24 = new HashMap();
        this.setEnabled(true);
    }

    @Override
    public int getHudX() {
        block3: {
            ArrayList arrayList;
            block2: {
                boolean bl = AbstractHudModule.isEditMode();
                arrayList = this;
                if (bl) break block2;
                if (arrayList.side.getValue() != SideMode.LEFT) break block3;
                arrayList = this;
            }
            return arrayList.x.getInt();
        }
        return Math.max(0, MC.mc.getWindow().getScaledWidth() - this.hudWidth() - this.x.getInt() - 2);
    }

    @Override
    public int getHudY() {
        return this.y.getInt();
    }

    @Override
    public int hudWidth() {
        return this.getList7().stream().mapToInt(moduleData -> Client.fontManager.renderer2().getStringWidth(this.m633(moduleData.module()))).max().orElse(60) + 6;
    }

    @Override
    public int getHudHeight() {
        int n;
        block2: {
            int n2 = Client.fontManager.renderer2().getFontHeight() + 2 + this.spacing.getInt();
            int n3 = 0;
            boolean bl = AbstractHudModule.isEditMode();
            for (ModuleData moduleData : this.getList7()) {
                n = n3 + Math.round((float)n2 * moduleData.value77());
                if (!bl) {
                    n3 = n;
                    if (!bl) continue;
                }
                break block2;
            }
            n = Math.max(1, n3);
        }
        return n;
    }

    @Override
    public void setHudPosition(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        int n5 = this.side.getValue() == SideMode.RIGHT ? MC.mc.getWindow().getScaledWidth() - this.hudWidth() - n3 - 2 : n3;
        this.x.setDouble(Math.max(0, n5));
        this.y.setDouble(Math.max(0, n4));
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void renderHud(Object var1_1, boolean var2_2) {
        DrawContext ctx = (DrawContext) var1_1;
        java.util.List<ModuleData> list = this.getList7();
        int y = this.y.getInt();
        int lineHeight = Client.fontManager.renderer2().getFontHeight() + 2 + this.spacing.getInt();
        int screenWidth = MC.mc.getWindow().getScaledWidth();
        for (int i = 0; i < list.size(); ++i) {
            ModuleData md = list.get(i);
            Module module = md.module();
            float value77 = md.value77();
            if (value77 <= 0.0f) continue;
            String text = this.m633(module);
            int textWidth = Client.fontManager.renderer2().getStringWidth(text);
            int offset = Math.round((float) textWidth * (1.0f - value77));
            int xpos;
            if (this.side.getValue() == SideMode.RIGHT) {
                xpos = screenWidth - textWidth - this.x.getInt() - 2 + offset;
            } else {
                xpos = this.x.getInt() + 2 - offset;
            }
            int color = ((Boolean) this.rainbow.getValue()) ? this.m823(i) : (Integer) this.color.getValue();
            int height = Math.max(1, Math.round((float) lineHeight * value77));
            if ((Boolean) this.background.getValue()) {
                ctx.fill(xpos - 2, y, xpos + textWidth + 2, y + height, ((Integer) this.backgroundColor.getValue()).intValue());
            }
            Object mode = this.mode.getValue();
            if (mode == Mode.LEFT_TAG) {
                ctx.fill(xpos - 4, y, xpos - 2, y + height, color);
            } else if (mode == Mode.RIGHT_TAG) {
                ctx.fill(xpos + textWidth + 2, y, xpos + textWidth + 4, y + height, color);
            } else if (mode == Mode.FRAME) {
                ctx.fill(xpos - 3, y, xpos - 2, y + height, color);
                ctx.fill(xpos + textWidth + 2, y, xpos + textWidth + 3, y + height, color);
                ctx.fill(xpos - 3, y, xpos + textWidth + 3, y + 1, color);
                ctx.fill(xpos - 3, y + height - 1, xpos + textWidth + 3, y + height, color);
            }
            Client.fontManager.renderer2().drawText(ctx, text, xpos, y, color, (Boolean) this.shadow.getValue());
            y += height;
        }
    }

    private List<ModuleData> getList7() {
        long l = System.currentTimeMillis();
        boolean bl = true;
        java.util.ArrayList<ModuleData> arrayList = new java.util.ArrayList<ModuleData>();
        for (Module module : Client.moduleManager.getModules()) {
            if (module == this || !module.isDrawn()) continue;
            Inner inner = this.map24.computeIfAbsent(module, Inner::new);
            inner.m88(this.m385(module), l);
            float f = inner.m462(l);
            if (f <= 0.0f) continue;
            arrayList.add(new ModuleData(module, f));
            if (true) continue;
        }
        arrayList.sort(this.comparator());
        return arrayList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m385(Object object) {
        Module module = (Module)object;
        boolean bl = AbstractHudModule.isEditMode();
        boolean bl2 = module.isEnabled();
        if (!bl) {
            if (!bl2) return false;
            bl2 = (Boolean)this.bindOnly.getValue();
        }
        if (bl) return bl2;
        if (!bl2) return true;
        bl2 = module.getKeyBindSetting().isBound();
        if (bl) return bl2;
        if (!bl2) return false;
        return true;
    }

    private Comparator<ModuleData> comparator() {
        return switch (((SortingMode)((Object)this.sortingMode.getValue())).ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> Comparator.comparingInt((ModuleData moduleData) -> Client.fontManager.renderer2().getStringWidth(this.m633(moduleData.module()))).reversed();
            case 1 -> Comparator.comparing((ModuleData moduleData) -> moduleData.module().getName().toLowerCase(Locale.ROOT));
            case 2 -> Comparator.comparingInt((ModuleData moduleData) -> moduleData.module().getCategory().ordinal());
        };
    }

    /*
     * Exception decompiling
     */
    private String m633(Object var1_1) {
        Module module = (Module) var1_1;
        String name = module.getDisplayName();
        String suffix = this.m562(module);
        String result = (suffix == null || suffix.isBlank()) ? name : name + " §7" + suffix;
        switch (((CaseMode) this.case_.getValue()).ordinal()) {
            case 1: return result.toLowerCase();
            case 2: return result.toUpperCase();
            default: return result;
        }
    }

    private String m562(Object object) {
        try {
            Module module = (Module)object;
            return module.getInfo();
        }
        catch (Exception exception) {
            return null;
        }
    }

    private int m823(int n) {
        int n2 = n;
        double d = (Double)this.rainbowLength.getValue() * 1000.0;
        float f = (float)((double)(System.currentTimeMillis() % (long)d) / d);
        float f2 = (float)(((double)f + (Double)this.indexedHue.getValue() * 0.05 * (double)n2) % 1.0);
        return 0xFF000000 | Color.HSBtoRGB(f2, this.saturation.getFloat(), this.brightness.getFloat()) & 0xFFFFFF;
    }

    @Environment(value=EnvType.CLIENT)
    public static enum SideMode {
        LEFT,
        RIGHT;


        private static SideMode[] getSideModeArray() {
            return new SideMode[]{LEFT, RIGHT};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum SortingMode {
        LENGTH,
        ALPHABET,
        CATEGORY;


        private static SortingMode[] getSortingModeArray() {
            return new SortingMode[]{LENGTH, ALPHABET, CATEGORY};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
        LEFT_TAG,
        RIGHT_TAG,
        FRAME;


        private static Mode[] getModeArray4() {
            return new Mode[]{LEFT_TAG, RIGHT_TAG, FRAME};
        }
    }

    @Environment(value=EnvType.CLIENT)
    public static enum CaseMode {
        NORMAL,
        LOWER,
        UPPER;


        private static CaseMode[] getCaseModeArray() {
            return new CaseMode[]{NORMAL, LOWER, UPPER};
        }
    }

    @Environment(value=EnvType.CLIENT)
    record ModuleData(Module module, float value77) {
    }

    @Environment(value=EnvType.CLIENT)
    static final class Inner {
        private boolean flag63;
        private float value166;
        private float value188;
        private long time47;

        private Inner(Module module) {
            this.flag63 = module.isEnabled();
            this.value188 = this.value166 = this.flag63 ? 1.0f : 0.0f;
            this.time47 = System.currentTimeMillis();
        }

        private void m88(boolean bl, long l) {
            boolean bl2 = bl;
            long l2 = l;
            boolean bl3 = true;
            if (this.flag63 == bl2) {
                return;
            }
            this.value166 = this.m462(l2);
            this.value188 = bl2 ? 1.0f : 0.0f;
            this.flag63 = bl2;
            this.time47 = l2;
        }

        private float m462(long l) {
            long l2 = l;
            float f = Math.max(0.0f, Math.min(1.0f, (float)(l2 - this.time47) / 300.0f));
            float f2 = this.flag63 ? Inner.m790(f) : Inner.m200(f);
            return this.value166 + (this.value188 - this.value166) * f2;
        }

        private static float m790(float f) {
            float f2 = f;
            float f3 = 1.0f - f2;
            return 1.0f - f3 * f3 * f3;
        }

        private static float m200(float f) {
            float f2 = f;
            return f2 * f2 * f2;
        }
    }
}
