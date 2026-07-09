/*
 * Decompiled with CFR 0.152.
 */
package shit.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public final class ResourceEntry  {
    private final Identifier field9;
    private final boolean flag11;
    private final String text21;
    private final long time2;

    public ResourceEntry(Identifier identifier, boolean bl, String string, long l) {
        block0: {
            this.field9 = identifier;
            Object var6_5 = null;
            this.flag11 = bl;
            this.text21 = string;
            this.time2 = l;
            if (null == null) break block0;
            Module.setTextArray9(new String[4]);
        }
    }

    public boolean isSet3() {
        Object var1_1 = null;
        return System.currentTimeMillis() - this.time2 > 1800000L;
    }

    public Identifier field9() {
        return this.field9;
    }

    public boolean flag11() {
        return this.flag11;
    }

    public String getText() {
        return this.text21;
    }

    public long getLong8() {
        return this.time2;
    }
}

