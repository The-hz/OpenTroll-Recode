/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.system.MemoryUtil;
import shit.util.GpuPipelineFactory;

@Environment(value=EnvType.CLIENT)
public class GpuManager {
    private final int count142;
    private final GpuBuffer[] gpuBuffers = new GpuBuffer[3];
    private final int[] counts5 = new int[3];
    private final List list7 = new ArrayList();
    private GpuBuffer.MappedView mappedView;
    private int count134;
    private boolean flag159;
    private long time29 = Long.MIN_VALUE;
    private static boolean flag64;

    public GpuManager(long l, @GpuBuffer.Usage int n) {
        int n2 = GpuManager.m9(l);
        this.count142 = 0x1A | n;
        for (int i = 0; i < this.gpuBuffers.length; ++i) {
            this.gpuBuffers[i] = this.createBuffer(i, n2);
            this.counts5[i] = n2;
        }
    }

    public int getInt15() {
        return this.counts5[this.count134];
    }

    public boolean isMapped() {
        return this.flag159;
    }

    public ByteBuffer getByteBuffer() {
        boolean bl = false;
        if (this.mappedView == null) {
            throw new IllegalStateException("LuminRingBuffer is not mapped");
        }
        return this.mappedView.data();
    }

    public void setLong(long l) {
        long l2 = l;
        boolean bl = false;
        if (l2 <= (long)this.getInt15()) {
            return;
        }
        this.setInt2(GpuManager.m20(this.getInt15(), l2));
    }

    public void ensureMapped() {
        boolean bl = false;
        if (this.flag159) {
            return;
        }
        this.m560();
        this.mappedView = RenderSystem.getDevice().createCommandEncoder().mapBuffer(this.getGpuBuffer(), false, true);
        this.flag159 = true;
    }

    public void unmap() {
        boolean bl = false;
        if (!this.flag159) {
            return;
        }
        this.mappedView.close();
        this.mappedView = null;
        this.flag159 = false;
    }

    public void advanceBuffer() {
        this.m560();
        this.count134 = (this.count134 + 1) % this.gpuBuffers.length;
    }

    public GpuBuffer getGpuBuffer() {
        return this.gpuBuffers[this.count134];
    }

    public void flush() {
        block4: {
            boolean bl = GpuManager.isSet62();
            GpuManager gpuManager = this;
            if (bl) {
                if (gpuManager.flag159) {
                    this.unmap();
                }
                gpuManager = this;
            }
            for (GpuBuffer gpuBuffer : gpuManager.gpuBuffers) {
                gpuBuffer.close();
                if (bl) {
                    if (bl) continue;
                }
                break block4;
            }
            this.m1039();
        }
    }

    private void setInt2(int n) {
        block18: {
            int n2;
            ByteBuffer byteBuffer;
            GpuBuffer gpuBuffer2 = null;
            block17: {
                GpuBuffer gpuBuffer;
                int n3;
                boolean bl;
                block16: {
                    ByteBuffer byteBuffer2;
                    block15: {
                        ByteBuffer byteBuffer3;
                        int n4;
                        block14: {
                            int n5;
                            block13: {
                                n4 = n;
                                int n6 = this.counts5[this.count134];
                                bl = GpuManager.isSet62();
                                if (n4 <= n6) {
                                    return;
                                }
                                byteBuffer3 = null;
                                n3 = Math.min(n6, n4);
                                n5 = this.flag159 ? 1 : 0;
                                if (!bl) break block13;
                                if (n5 == 0) break block14;
                                n5 = n3;
                            }
                            if (n5 > 0) {
                                byteBuffer = this.mappedView.data();
                                byteBuffer3 = MemoryUtil.memAlloc((int)n3);
                                MemoryUtil.memCopy((long)MemoryUtil.memAddress((ByteBuffer)byteBuffer), (long)MemoryUtil.memAddress((ByteBuffer)byteBuffer3), (long)n3);
                            }
                        }
                        GpuManager gpuManager = this;
                        if (bl) {
                            if (gpuManager.flag159) {
                                this.unmap();
                            }
                            gpuManager = this;
                        }
                        gpuBuffer2 = gpuManager.gpuBuffers[this.count134];
                        this.gpuBuffers[this.count134] = gpuBuffer = this.createBuffer(this.count134, n4);
                        this.counts5[this.count134] = n4;
                        byteBuffer2 = byteBuffer3;
                        if (!bl) break block15;
                        if (byteBuffer2 == null) break block16;
                        try {
                            this.ensureMapped();
                            MemoryUtil.memCopy((long)MemoryUtil.memAddress((ByteBuffer)byteBuffer3), (long)MemoryUtil.memAddress((ByteBuffer)this.mappedView.data()), (long)n3);
                            byteBuffer2 = byteBuffer3;
                        }
                        catch (Throwable throwable) {
                            MemoryUtil.memFree((Buffer)byteBuffer3);
                            throw throwable;
                        }
                    }
                    MemoryUtil.memFree((Buffer)byteBuffer2);
                    if (bl) break block17;
                }
                n2 = n3;
                if (!bl) break block18;
                if (n2 > 0) {
                    RenderSystem.getDevice().createCommandEncoder().copyToBuffer(gpuBuffer2.slice(0, n3), gpuBuffer.slice(0, n3));
                }
            }
            n2 = this.list7.add(gpuBuffer2) ? 1 : 0;
        }
    }

    private void m560() {
        long l = GpuPipelineFactory.getLong4();
        boolean bl = GpuManager.isSet62();
        GpuManager gpuManager = this;
        if (bl) {
            if (gpuManager.time29 == l) {
                return;
            }
            this.time29 = l;
            gpuManager = this;
        }
        gpuManager.m1039();
    }

    private void m1039() {
        boolean bl = false;
        if (this.list7.isEmpty()) {
            return;
        }
        this.list7.forEach(o -> ((GpuBuffer)o).close());
        this.list7.clear();
    }

    private static int m9(long l) {
        long l2 = l;
        int n = Math.toIntExact(l2);
        if (n <= 0) {
            throw new IllegalArgumentException("size must be positive");
        }
        return n;
    }

    private static int m20(int n, long l) {
        int n2 = n;
        long l2 = l;
        int n3 = GpuManager.m9(l2);
        boolean bl = false;
        int n4 = n2;
        while (n3 > n4) {
            n4 = Math.multiplyExact(n4, 2);
            if (!false) continue;
        }
        return n4;
    }

    private GpuBuffer createBuffer(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        return RenderSystem.getDevice().createBuffer(() -> "lumin-ring-buffer #" + n3, this.count142, (long)n4);
    }

    public static void setFlag14(boolean bl) {
        flag64 = bl;
    }

    public static boolean isSet29() {
        return flag64;
    }

    public static boolean isSet62() {
        boolean bl = false;
        return true;
    }

    static {
        boolean bl = false;
        GpuManager.setFlag14(false);
    }
}

