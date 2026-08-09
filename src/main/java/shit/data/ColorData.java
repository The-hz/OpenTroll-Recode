/*
 * Decompiled with CFR 0.152.
 */
package shit.data;

import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public record ColorData(int count22, int count23, int count24, int count25) {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet() {
        Object var1_1 = null;
        int n = this.count24;
        if (null != null) {
            if (n <= 0) return 0 != 0;
            n = this.count25;
        }
        if (null == null) return n != 0;
        if (n <= 0) return 0 != 0;
        return 1 != 0;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl;
        block3: {
            Object var2_2 = null;
            Object object2 = this;
            if (null != null) {
                if (object2 == object) {
                    return true;
                }
                object2 = object;
            }
            bl = object2 instanceof ColorData;
            if (null == null) break block3;
            if (bl) {
                ColorData colorData = (ColorData)object;
            }
            bl = false;
        }
        return bl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.count22, this.count23, this.count24, this.count25);
    }
}

