/*
 * Decompiled with CFR 0.152.
 */
package shit.data;

import java.nio.ByteBuffer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public final class BufferUploadData  {
    private final ByteBuffer byteBuffer;
    private final int count38;
    private final int count39;
    private final int count40;
    private final int count41;
    private final int count42;

    public BufferUploadData(ByteBuffer byteBuffer, int n, int n2, int n3, int n4, int n5) {
        this.byteBuffer = byteBuffer;
        this.count38 = n;
        this.count39 = n2;
        this.count40 = n3;
        this.count41 = n4;
        this.count42 = n5;
    }

    public ByteBuffer byteBuffer() {
        return this.byteBuffer;
    }

    public int getInt32() {
        return this.count38;
    }

    public int getInt42() {
        return this.count39;
    }

    public int count40() {
        return this.count40;
    }

    public int count41() {
        return this.count41;
    }

    public int getInt37() {
        return this.count42;
    }
}

