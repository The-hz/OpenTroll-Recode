/*
 * Decompiled with CFR 0.152.
 */
package shit.module.hud;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import shit.Client;
import shit.api.Listener3;
import shit.manager.FontManager2;
import shit.module.Category;
import shit.module.Module;
import shit.module.hud.AbstractHudModule;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class IrcChatHUD
extends Module
implements Listener3 {
    public static IrcChatHUD INSTANCE;
    private final NumberSetting x = (NumberSetting)this.registerSetting(new NumberSetting("X", 10.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting y = (NumberSetting)this.registerSetting(new NumberSetting("Y", 200.0, 0.0, 5000.0, 1.0, 1.0, () -> false, null, "", false));
    private final NumberSetting width = (NumberSetting)this.registerSetting(new NumberSetting("Width", 260.0, 120.0, 640.0, 1.0));
    private final NumberSetting height = (NumberSetting)this.registerSetting(new NumberSetting("Height", 120.0, 50.0, 360.0, 1.0));
    private final BooleanSetting shadow = (BooleanSetting)this.registerSetting(new BooleanSetting("Shadow", true));
    private final CopyOnWriteArrayList copyOnWriteArrayList2 = new CopyOnWriteArrayList();
    private int count189 = 0;

    public IrcChatHUD() {
        super("IrcChatHUD", "Displays IRC messages in a dedicated HUD panel.", Category.HUD);
        INSTANCE = this;
    }

    public static boolean m578(Object object) {
        boolean bl;
        block4: {
            String string;
            block5: {
                block3: {
                    IrcChatHUD ircChatHUD;
                    boolean bl2;
                    block2: {
                        string = (String)object;
                        bl2 = AbstractHudModule.isSet32();
                        ircChatHUD = INSTANCE;
                        if (bl2) break block2;
                        if (ircChatHUD == null) break block3;
                        ircChatHUD = INSTANCE;
                    }
                    bl = ircChatHUD.isEnabled();
                    if (bl2) break block4;
                    if (bl) break block5;
                }
                return false;
            }
            INSTANCE.setObj77(string);
            bl = true;
        }
        return bl;
    }

    public void setObj77(Object object) {
        String string = (String)object;
        boolean bl = AbstractHudModule.isSet32();
        this.copyOnWriteArrayList2.add(string);
        boolean bl2 = bl;
        IrcChatHUD ircChatHUD = this;
        if (!bl2) {
            if (ircChatHUD.copyOnWriteArrayList2.size() > 300) {
                this.copyOnWriteArrayList2.remove(0);
            }
            ircChatHUD = this;
        }
        ircChatHUD.count189 = 0;
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
        return this.width.getInt();
    }

    @Override
    public int getHudHeight() {
        return this.height.getInt();
    }

    @Override
    public void setHudPosition(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        this.x.setDouble(n3);
        this.y.setDouble(n4);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void renderHud(Object var1_1, boolean var2_2) {
        DrawContext ctx = (DrawContext) var1_1;
        shit.manager.FontManager2 r = Client.fontManager.renderer2();
        int x = this.x.getInt();
        int y = this.y.getInt();
        int w = this.width.getInt();
        int h = this.height.getInt();
        int lineH = r.getFontHeight() + 1;
        int titleH = lineH + 2;
        ctx.fill(x, y, x + w, y + h, -1441787883);
        ctx.fill(x, y, x + w, y + titleH, -1155917270);
        r.drawText(ctx, "§dIRC Chat", x + 3, y + 2, -1, (Boolean) this.shadow.getValue());
        int contentTop = y + titleH;
        int contentH = h - titleH;
        int maxLines = contentH / lineH;
        java.util.List lines = this.m1054(w - 6);
        int size = lines.size();
        int maxScroll = Math.max(0, size - maxLines);
        this.count189 = Math.min(this.count189, maxScroll);
        int start = Math.max(0, size - maxLines - this.count189);
        int end = Math.min(size, start + maxLines);
        ctx.enableScissor(x, contentTop, x + w, y + h);
        int ly = contentTop + 2;
        for (int i = start; i < end; ++i) {
            r.drawText(ctx, (String) lines.get(i), x + 3, ly, -2236963, (Boolean) this.shadow.getValue());
            ly += lineH;
        }
        ctx.disableScissor();
        if (size > maxLines) {
            int sx = x + w - 3;
            ctx.fill(sx, contentTop, sx + 2, y + h, 0x44FFFFFF);
            float ratio = (float) maxLines / (float) size;
            int barH = Math.max(6, (int) ((float) contentH * ratio));
            float scrollFrac = maxScroll > 0 ? (float) this.count189 / (float) maxScroll : 0.0f;
            int barY = contentTop + (int) ((float) (contentH - barH) * (1.0f - scrollFrac));
            ctx.fill(sx, barY, sx + 2, barY + barH, -1430537285);
        }
    }

    private List m1054(int n) {
        int n2 = n;
        ArrayList<String> arrayList = new ArrayList<>(this.copyOnWriteArrayList2);
        boolean bl = true;
        ArrayList arrayList2 = new ArrayList();
        for (String string : arrayList) {
            arrayList2.addAll(this.m191(string, n2));
            if (true) continue;
            break;
        }
        return arrayList2;
    }

    private List m191(Object object, int n) {
        String string = (String)object;
        int n2 = n;
        FontManager2 fontManager2 = Client.fontManager.renderer2();
        boolean bl = true;
        ArrayList<String> arrayList = new ArrayList<String>();
        if (fontManager2.getStringWidth(IrcChatHUD.m519(string)) <= n2) {
            arrayList.add(string);
            return arrayList;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object object2 = "";
        int n3 = 0;
        while (n3 < string.length()) {
            char c = string.charAt(n3);
            if (c == '\u00a7') {
                if (n3 + 1 < string.length()) {
                    stringBuilder.append(c).append(string.charAt(n3 + 1));
                    object2 = String.valueOf('\u00a7') + string.charAt(n3 + 1);
                    n3 += 2;
                    if (true) continue;
                }
            }
            stringBuilder.append(c);
            if (fontManager2.getStringWidth(IrcChatHUD.m519(stringBuilder.toString())) > n2) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                arrayList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder((String)object2).append(c);
            }
            ++n3;
            if (true) continue;
            break;
        }
        if (!stringBuilder.isEmpty()) {
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    private static String m519(Object object) {
        String string = (String)object;
        return string.replaceAll("\u00a7.", "");
    }
}

