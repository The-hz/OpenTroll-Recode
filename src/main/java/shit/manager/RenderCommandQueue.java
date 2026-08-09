/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class RenderCommandQueue {
    public static RenderCommandQueue manager6 = new RenderCommandQueue();
    private final List list14 = new CopyOnWriteArrayList();
    private final AtomicLong atomicLong = new AtomicLong();

    private RenderCommandQueue() {
    }
}

