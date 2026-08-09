/*
 * Decompiled with CFR 0.152.
 */
package shit.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public interface HudModule {
    public int getHudX();

    public int getHudY();

    public int hudWidth();

    public int getHudHeight();

    public void setHudPosition(int var1, int var2);

    public void renderHud(Object var1, boolean var2);
}

