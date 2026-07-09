/*
 * Decompiled with CFR 0.152.
 */
package shit.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public enum Category {
      CHAT, CLIENT, EXPLOIT, COMBAT, MISC, MOVEMENT, PLAYER, RENDER, HUD;

      private Category() {}


    private static String text72;

    private static /* synthetic */ Category[] getCategoryArray() {
        return new Category[]{CHAT, CLIENT, EXPLOIT, COMBAT, MISC, MOVEMENT, PLAYER, RENDER, HUD};
    }

    public static void setText7(String string) {
        text72 = string;
    }

    public static String getText34() {
        return text72;
    }

   }

