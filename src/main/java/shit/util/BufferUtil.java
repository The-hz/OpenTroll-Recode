/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.system.MemoryUtil;
import shit.manager.GpuManager;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class BufferUtil {
    public static void m514(long l, float f, float f2, float f3, float f4, int n) {
        block0: {
            long l2 = l;
            float f5 = f;
            float f6 = f2;
            float f7 = f3;
            float f8 = f4;
            int n2 = n;
            boolean bl = GpuManager.isSet62();
            MemoryUtil.memPutFloat((long)l2, (float)f5);
            boolean bl2 = bl;
            MemoryUtil.memPutFloat((long)(l2 + 4L), (float)f6);
            MemoryUtil.memPutFloat((long)(l2 + 8L), (float)0.0f);
            MemoryUtil.memPutFloat((long)(l2 + 12L), (float)f7);
            MemoryUtil.memPutFloat((long)(l2 + 16L), (float)f8);
            MemoryUtil.memPutInt((long)(l2 + 20L), (int)n2);
            if (bl2) break block0;
            Module.setTextArray9(new String[3]);
        }
    }
}

