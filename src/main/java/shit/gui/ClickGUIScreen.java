/*
 * Decompiled with CFR 0.152.
 */
package shit.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.input.CharInput;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import shit.Client;
import shit.manager.FontManager2;
import shit.manager.RenderManager;
import shit.module.Category;
import shit.module.Module;
import shit.render.Outline;
import shit.util.ClickGUI;
import shit.util.FontUtil2;

@Environment(value=EnvType.CLIENT)
public class ClickGUIScreen
extends Screen {
    private static final List<shit.manager.RenderManager> list28 = new java.util.ArrayList<>();
    private final List<shit.manager.RenderManager> list19 = list28;
    private String text2036 = "";
    private long time3;
    private static final String a = null;

    public ClickGUIScreen() {
        super((Text) Text.literal("ClickGUI"));
    }

    protected void init() {
        Client.configManager.m1042();
        if (this.list19.isEmpty()) {
            int n2 = 0;
            for (Category category : Category.values()) {
                if (category == Category.HUD) {
                    continue;
                }
                this.list19.add(new RenderManager(net.minecraft.client.MinecraftClient.getInstance().textRenderer, category, n2, 0));
                n2 += 80;
            }
        }
    }

    public void render(DrawContext drawContext, int n, int n2, float f) {
        int n4 = ClickGUI.getInt69();
        if (n4 >>> 24 > 0) {
            drawContext.fill(0, 0, this.width, this.height, n4);
        }
        for (RenderManager renderManager : this.list19) {
            renderManager.m213(drawContext, n, n2);
        }
        for (RenderManager renderManager : this.list19) {
            renderManager.m147(drawContext, n, n2);
        }
        this.setObj49(drawContext);
        Object object = this.m669(n, n2);
        if (object == null || ((String) object).isEmpty()) {
            return;
        }
        int n5 = Client.fontManager.renderer2().getStringWidth(object);
        int n6 = Math.min(n + 8, this.width - n5 - 8);
        int n7 = Math.min(n2 + 10, this.height - 15);
        drawContext.fill(n6 - 3, n7 - 3, n6 + n5 + 3, n7 + 11, -871032800);
        FontUtil2.drawTextSimple(this.textRenderer, drawContext, object, n6, n7, -1184275);
    }

    public boolean mouseClicked(Click click, boolean bl) {
        for (int i = this.list19.size() - 1; i >= 0; --i) {
            if (this.list19.get(i).m3(click.x(), click.y(), click.button())) {
                return true;
            }
        }
        for (int i = this.list19.size() - 1; i >= 0; --i) {
            if (this.list19.get(i).m851(click.x(), click.y(), click.button())) {
                return true;
            }
        }
        return super.mouseClicked(click, bl);
    }

    public boolean mouseReleased(Click click) {
        for (int i = this.list19.size() - 1; i >= 0; --i) {
            if (this.list19.get(i).m103(click.x(), click.y(), click.button())) {
                return true;
            }
        }
        for (int i = this.list19.size() - 1; i >= 0; --i) {
            if (this.list19.get(i).m510(click.x(), click.y(), click.button())) {
                return true;
            }
        }
        return super.mouseReleased(click);
    }

    public boolean mouseDragged(Click click, double d, double d2) {
        for (int i = this.list19.size() - 1; i >= 0; --i) {
            if (this.list19.get(i).m479(click.x(), click.y(), click.button(), d, d2)) {
                return true;
            }
        }
        for (int i = this.list19.size() - 1; i >= 0; --i) {
            if (this.list19.get(i).m855(click.x(), click.y(), click.button(), d, d2)) {
                return true;
            }
        }
        return super.mouseDragged(click, d, d2);
    }

    public boolean mouseScrolled(double d, double d2, double d3, double d4) {
        boolean bl;
        block9: {
            block8: {
                int n;
                block11: {
                    boolean bl2;
                    block10: {
                        int n2;
                        block5: {
                            block4: {
                                block7: {
                                    boolean bl3 = false;
                                    block6: {
                                        n = this.list19.size() - 1;
                                        boolean bl4 = false;
                                        if (n < 0) break block4;
                                        n2 = ((RenderManager)this.list19.get(n)).m865(d, d2, d4) ? '\u0001' : '\u0000';
                                        if (!false) break block5;
                                        if (!false) break block6;
                                        if (n2 == 0) break block7;
                                        bl3 = true;
                                    }
                                    return bl3;
                                }
                                --n;
                            }
                            n2 = this.list19.size() - 1;
                        }
                        if ((n = n2) < 0) break block8;
                        bl = ((RenderManager)this.list19.get(n)).m117(d, d2, d4);
                        if (!false) break block9;
                        if (!false) break block10;
                        if (!bl) break block11;
                        bl2 = true;
                    }
                    return bl2;
                }
                --n;
            }
            bl = super.mouseScrolled(d, d2, d3, d4);
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean keyPressed(KeyInput keyInput) {
        int n = this.list19.size() - 1;
        if (n >= 0 && ((RenderManager) this.list19.get(n)).m121(keyInput.key(), keyInput.scancode(), keyInput.modifiers())) {
            return true;
        }
        return super.keyPressed(keyInput);
    }

    public boolean charTyped(CharInput charInput) {
        String string = charInput.asString();
        if (string == null || string.isEmpty()) {
            return super.charTyped(charInput);
        }
        char c3 = string.charAt(0);
        for (int i = this.list19.size() - 1; i >= 0; --i) {
            if (((RenderManager) this.list19.get(i)).m650(c3, charInput.modifiers())) {
                return true;
            }
        }
        if (Character.isLetterOrDigit(c3) || c3 == ' ' || c3 == '_' || c3 == '-') {
            this.setObj70(this.text2036 + c3);
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

    private String m669(int n, int n2) {
        for (int n3 = this.list19.size() - 1; n3 >= 0; --n3) {
            String string = ((RenderManager)this.list19.get(n3)).m329(n, n2);
            if (string != null && !string.isEmpty()) {
                return string;
            }
        }
        return "";
    }

    private void setObj70(Object object) {
        String string = (String)object;
        boolean bl = false;
        String string2 = string;
        if (false) {
            string2 = string2 == null ? "" : string;
        }
        this.text2036 = string2;
        this.time3 = System.currentTimeMillis();
        String string3 = this.m406(this.text2036);
        Iterator iterator = this.list19.iterator();
        if (iterator.hasNext()) {
            RenderManager renderManager = (RenderManager)iterator.next();
            renderManager.setObj87(string3);
        }
    }

    private void setObj49(Object object) {
        DrawContext drawContext = (DrawContext)object;
        boolean bl = FontUtil2.isSet101();
        if (this.text2036.isBlank()) {
            return;
        }
        long l = System.currentTimeMillis() - this.time3;
        long l2 = l - 5000L;
        long l3 = l2 == 0L ? (char)'\u0000' : (l2 < 0L ? (-1) : '\u0001');
        if (!bl) {
            if (l3 > 0) {
                return;
            }
            l3 = Math.max(0, 255 - (int)((double)l / 5000.0 * 255.0));
        }
        long l4 = l3;
        int n = (int)(l4 << 24 | ClickGUI.getInt27() & 0xFFFFFF);
        FontManager2 fontManager2 = Client.fontManager.renderer(18.0f);
        int n2 = fontManager2.getStringWidth(this.text2036);
        int n3 = fontManager2.getFontHeight();
        fontManager2.drawText(drawContext, this.text2036, (this.width - n2) / 2, (this.height - n3) / 2, n, true);
    }

    private String m406(Object object) {
        String string = (String)object;
        return string.replace(" ", "").toLowerCase(Locale.ROOT);
    }

    /*
     * Handled unverifiable bytecode (illegal stack merge).
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    static {}
}

