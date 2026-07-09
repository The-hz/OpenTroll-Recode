/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.EventHandler;
import shit.event.HookTotemParticleInitEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;

@Environment(value=EnvType.CLIENT)
public class TotemParticle
extends Module {
    public static TotemParticle INSTANCE;
    private final NumberSetting velocityXZ = (NumberSetting)this.m28(new NumberSetting("VelocityXZ", 100.0, 0.0, 500.0, 1.0));
    private final NumberSetting velocityY = (NumberSetting)this.m28(new NumberSetting("VelocityY", 100.0, 0.0, 500.0, 1.0));
    private final ColorSetting color = (ColorSetting)this.m28(new ColorSetting("Color", -1));
    private final ColorSetting color2 = (ColorSetting)this.m28(new ColorSetting("Color2", -16777216));
    private final Random random5 = new Random();

    public TotemParticle() {
        super("TotemParticle", "Customizes totem death particle color and velocity.", Category.RENDER);
        INSTANCE = this;
    }

    @EventHandler
    private void setHookTotemParticleInitEvent(HookTotemParticleInitEvent hookTotemParticleInitEvent) {
        hookTotemParticleInitEvent.m209();
        hookTotemParticleInitEvent.value129 *= (Double)this.velocityXZ.getObj() / 100.0;
        hookTotemParticleInitEvent.value200 *= (Double)this.velocityY.getObj() / 100.0;
        hookTotemParticleInitEvent.value123 *= (Double)this.velocityXZ.getObj() / 100.0;
        hookTotemParticleInitEvent.count184 = TotemParticle.m630((Integer)this.color.getObj(), (Integer)this.color2.getObj(), this.random5.nextFloat());
    }

    private static int m630(int n, int n2, float f) {
        int n3 = n;
        int n4 = n2;
        float f2 = f;
        int n5 = TotemParticle.m970(n3 >> 24 & 0xFF, n4 >> 24 & 0xFF, f2);
        int n6 = TotemParticle.m970(n3 >> 16 & 0xFF, n4 >> 16 & 0xFF, f2);
        int n7 = TotemParticle.m970(n3 >> 8 & 0xFF, n4 >> 8 & 0xFF, f2);
        int n8 = TotemParticle.m970(n3 & 0xFF, n4 & 0xFF, f2);
        return n5 << 24 | n6 << 16 | n7 << 8 | n8;
    }

    private static int m970(int n, int n2, float f) {
        int n3 = n;
        int n4 = n2;
        float f2 = f;
        return Math.round((float)n3 + (float)(n4 - n3) * f2);
    }
}

