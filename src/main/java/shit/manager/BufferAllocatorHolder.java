/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.BufferAllocator;

@Environment(value=EnvType.CLIENT)
public final class BufferAllocatorHolder {
    private static final int count201 = 0;
    private static final BufferAllocatorHolder manager5 = null;
    private final BufferAllocator field59;

    private BufferAllocatorHolder(int n) {
        this.field59 = new BufferAllocator(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    static {}
}

