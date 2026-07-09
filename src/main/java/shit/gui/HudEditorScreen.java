/*
 * Decompiled with CFR 0.152.
 */
package shit.gui;

import java.util.Iterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import shit.Client;
import shit.api.Listener3;
import shit.manager.RenderManager;
import shit.module.Category;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class HudEditorScreen
extends Screen {
    private final RenderManager renderManager;
    private Listener3 listener3;
    private int count221;
    private int count126;
    private static String text721;
    private static final String b = null;

    public HudEditorScreen() {
        super((Text) Text.literal("HUD Editor"));
        this.renderManager = new RenderManager(net.minecraft.client.MinecraftClient.getInstance().textRenderer, Category.HUD, 0, 0);
    }

    protected void init() {
        Client.configManager.m1042();
        this.renderManager.count108 = 0;
        this.renderManager.count103 = 0;
    }

    public void render(DrawContext drawContext, int n, int n2, float f) {
        for (Module module : Client.moduleManager.getByCategory(Category.HUD)) {
            if (module instanceof Listener3 && module.isSet19()) {
                Listener3 listener3 = (Listener3) module;
                try {
                    listener3.m368(drawContext, true);
                    this.m969(drawContext, listener3);
                } catch (RuntimeException e) {
                }
            }
        }
        this.renderManager.m945(drawContext, n, n2);
    }

    public boolean mouseClicked(Click click, boolean bl) {
        if (this.renderManager.m3(click.x(), click.y(), click.button())) return true;
        if (this.renderManager.m851(click.x(), click.y(), click.button())) return true;
        java.util.List<Module> hud = Client.moduleManager.getByCategory(Category.HUD);
        for (int i = hud.size() - 1; i >= 0; --i) {
            Module module = hud.get(i);
            if (module instanceof Listener3 && module.isSet19()) {
                Listener3 listener3 = (Listener3) module;
                if (this.m55(click.x(), click.y(), listener3)) {
                    this.listener3 = listener3;
                    this.count221 = (int) click.x() - listener3.getInt12();
                    this.count126 = (int) click.y() - listener3.getInt5();
                    return true;
                }
            }
        }
        return super.mouseClicked(click, bl);
    }

    public boolean mouseReleased(Click click) {
        Object var2_2 = null;
        HudEditorScreen hudEditorScreen = this;
        if (null != null) {
            if (hudEditorScreen.renderManager.m103(click.x(), click.y(), click.button())) {
                return true;
            }
            hudEditorScreen = this;
        }
        if (null != null) {
            if (hudEditorScreen.listener3 != null) {
                this.listener3 = null;
                Client.configManager.m1042();
                return true;
            }
            hudEditorScreen = this;
        }
        boolean bl = hudEditorScreen.renderManager.m510(click.x(), click.y(), click.button());
        if (null != null) {
            if (bl) {
                return true;
            }
            bl = super.mouseReleased(click);
        }
        return bl;
    }

    public boolean mouseDragged(Click click, double d, double d2) {
        if (this.renderManager.m479(click.x(), click.y(), click.button(), d, d2)) return true;
        if (this.listener3 != null) {
            this.listener3.m274((int) click.x() - this.count221, (int) click.y() - this.count126);
            return true;
        }
        if (this.renderManager.m855(click.x(), click.y(), click.button(), d, d2)) return true;
        return super.mouseDragged(click, d, d2);
    }

    public boolean keyPressed(KeyInput keyInput) {
        if (this.renderManager.m121(keyInput.key(), keyInput.scancode(), keyInput.modifiers())) return true;
        return super.keyPressed(keyInput);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean charTyped(CharInput charInput) {
        String string = charInput.asString();
        if (string != null && !string.isEmpty() && this.renderManager.m650(string.charAt(0), charInput.modifiers())) {
            return true;
        }
        return super.charTyped(charInput);
    }

    public boolean shouldPause() {
        return false;
    }

    public void removed() {
        Client.configManager.m1042();
        super.removed();
    }

    public void renderBackground(DrawContext drawContext, int n, int n2, float f) {
    }

    private void m969(Object object, Object object2) {
        DrawContext drawContext = (DrawContext)object;
        Listener3 listener3 = (Listener3)object2;
        int n = listener3.getInt12();
        int n2 = listener3.getInt5();
        drawContext.fill(n - 1, n2 - 1, n + listener3.hudWidth() + 1, n2, -1439005464);
        drawContext.fill(n - 1, n2 + listener3.getInt28(), n + listener3.hudWidth() + 1, n2 + listener3.getInt28() + 1, -1439005464);
        drawContext.fill(n - 1, n2 - 1, n, n2 + listener3.getInt28() + 1, -1439005464);
        drawContext.fill(n + listener3.hudWidth(), n2 - 1, n + listener3.hudWidth() + 1, n2 + listener3.getInt28() + 1, -1439005464);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean m55(double d, double d2, Object object) {
        Listener3 listener3 = (Listener3) object;
        return d >= listener3.getInt12() && d <= (double) (listener3.getInt12() + listener3.hudWidth())
            && d2 >= listener3.getInt5() && d2 <= (double) (listener3.getInt5() + listener3.getInt28());
    }

    public static void setText8(String string) {
        text721 = string;
    }

    public static String getText20() {
        return text721;
    }

    /*
     * Handled unverifiable bytecode (illegal stack merge).
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    static {}
}

