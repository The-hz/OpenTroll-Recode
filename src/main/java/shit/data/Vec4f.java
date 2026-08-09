/*
 * Decompiled with CFR 0.152.
 */
package shit.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public record Vec4f(float value6, float value7, float value8, float value9) {
    public static Vec4f m186(float f, float f2, float f3, float f4) {
        return new Vec4f(f, f2, Math.max(0.0f, f3), Math.max(0.0f, f4));
    }

    public static Vec4f m417(float f, float f2) {
        return new Vec4f(f, f2, 0.0f, 0.0f);
    }

    public float value99() {
        return this.value6 + this.value8;
    }

    public float getFloat54() {
        return this.value7 + this.value9;
    }

    public float getFloat49() {
        return this.value6 + this.value8 * 0.5f;
    }

    public float getFloat17() {
        return this.value7 + this.value9 * 0.5f;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean m942(Vec4f vec4f) {
        Object var2_2 = null;
        float f = this.value8 - 0.0f;
        float f2 = f == 0.0f ? 0 : (f < 0.0f ? -1 : 1);
        return f2 != 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean m416(Vec4f vec4f) {
        Object var2_2 = null;
        float f = vec4f.value6 - this.value6;
        float f2 = f == 0.0f ? 0 : (f > 0.0f ? 1 : -1);
        return f2 != 0;
    }

    public Vec4f m238(Vec4f vec4f) {
        float f = Math.min(this.value6, vec4f.value6);
        float f2 = Math.min(this.value7, vec4f.value7);
        float f3 = Math.max(this.value99(), vec4f.value99());
        float f4 = Math.max(this.getFloat54(), vec4f.getFloat54());
        return new Vec4f(f, f2, Math.max(0.0f, f3 - f), Math.max(0.0f, f4 - f2));
    }
}

