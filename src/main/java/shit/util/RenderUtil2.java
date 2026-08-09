/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.system.MemoryUtil;
import shit.module.Module;
import shit.util.Pos;

@Environment(value=EnvType.CLIENT)
public final class RenderUtil2 {
    private static final BufferUtil bufferUtil4 = new BufferUtil();
    private static final BufferUtil bufferUtil5 = new BufferUtil();
    private static final MatrixStack matrixStack2 = new MatrixStack();

    private RenderUtil2() {
    }

    public static void m504() {
        bufferUtil4.setObj86(matrixStack2);
    }

    public static void m149() {
        bufferUtil4.m636();
    }

    public static void m73(float f, float f2, float f3, float f4, float f5, float f6) {
        block0: {
            float f7 = f;
            float f8 = f2;
            float f9 = f3;
            float f10 = f4;
            float f11 = f5;
            float f12 = f6;
            float f13 = f7 / f11 * 2.0f - 1.0f;
            float f14 = (f7 + f9) / f11 * 2.0f - 1.0f;
            float f15 = 1.0f - f8 / f12 * 2.0f;
            float f16 = 1.0f - (f8 + f10) / f12 * 2.0f;
            bufferUtil5.m192();
            String string = Pos.getText67();
            bufferUtil5.m733(bufferUtil5.m343(f13, f16).getInt88(), bufferUtil5.m343(f13, f15).getInt88(), bufferUtil5.m343(f14, f15).getInt88(), bufferUtil5.m343(f14, f16).getInt88());
            String string2 = string;
            bufferUtil5.m283();
            bufferUtil5.setObj86(matrixStack2);
            bufferUtil5.m636();
            bufferUtil5.m379();
            if (string2 == null) break block0;
            Module.setTextArray9(new String[4]);
        }
    }

    public static void m582() {
        bufferUtil4.m379();
    }

    static {
        bufferUtil4.m192();
        bufferUtil4.m733(bufferUtil4.m343(-1.0, -1.0).getInt88(), bufferUtil4.m343(-1.0, 1.0).getInt88(), bufferUtil4.m343(1.0, 1.0).getInt88(), bufferUtil4.m343(1.0, -1.0).getInt88());
        bufferUtil4.m283();
    }

    @Environment(value=EnvType.CLIENT)
    static class BufferUtil {
        private final int count51;
        private final int count85;
        private final int count155;
        private final ByteBuffer byteBuffer3;
        private final long time59;
        private final ByteBuffer byteBuffer4;
        private final long time12;
        private long time24;
        private int count107;
        private int count178;
        private boolean flag104;
        private boolean flag23;

        private BufferUtil() {
            int n = GL11C.glGetInteger((int)34229);
            int n2 = 8;
            this.byteBuffer3 = BufferUtils.createByteBuffer((int)(n2 * 3 * 256 * 4));
            this.time59 = MemoryUtil.memAddress0((Buffer)this.byteBuffer3);
            this.byteBuffer4 = BufferUtils.createByteBuffer((int)6144);
            this.time12 = MemoryUtil.memAddress0((Buffer)this.byteBuffer4);
            this.count51 = GlStateManager._glGenVertexArrays();
            Pos.setInt6(this.count51);
            this.count85 = GlStateManager._glGenBuffers();
            GlStateManager._glBindBuffer((int)34962, (int)this.count85);
            this.count155 = GlStateManager._glGenBuffers();
            Pos.setInt12(this.count155);
            GlStateManager._enableVertexAttribArray((int)0);
            GlStateManager._vertexAttribPointer((int)0, (int)2, (int)5126, (boolean)false, (int)n2, (long)0L);
            Pos.setInt6(n);
        }

        private void m192() {
            this.time24 = this.time59;
            this.count107 = 0;
            this.count178 = 0;
            this.flag104 = true;
        }

        private BufferUtil m343(double d, double d2) {
            double d3 = d;
            double d4 = d2;
            long l = this.time24;
            MemoryUtil.memPutFloat((long)l, (float)((float)d3));
            MemoryUtil.memPutFloat((long)(l + 4L), (float)((float)d4));
            this.time24 += 8L;
            return this;
        }

        private int getInt88() {
            return this.count107++;
        }

        private void m733(int n, int n2, int n3, int n4) {
            int n5 = n;
            int n6 = n2;
            int n7 = n3;
            int n8 = n4;
            long l = this.time12 + (long)this.count178 * 4L;
            MemoryUtil.memPutInt((long)l, (int)n5);
            MemoryUtil.memPutInt((long)(l + 4L), (int)n6);
            MemoryUtil.memPutInt((long)(l + 8L), (int)n7);
            MemoryUtil.memPutInt((long)(l + 12L), (int)n7);
            MemoryUtil.memPutInt((long)(l + 16L), (int)n8);
            MemoryUtil.memPutInt((long)(l + 20L), (int)n5);
            this.count178 += 6;
        }

        private void m283() {
            if (this.count178 > 0) {
                int n = GL11C.glGetInteger((int)34229);
                Pos.setInt6(this.count51);
                GlStateManager._glBindBuffer((int)34962, (int)this.count85);
                GlStateManager._glBufferData((int)34962, (ByteBuffer)this.byteBuffer3.limit((int)(this.time24 - this.time59)), (int)35048);
                Pos.setInt12(this.count155);
                GlStateManager._glBufferData((int)34963, (ByteBuffer)this.byteBuffer4.limit(this.count178 * 4), (int)35048);
                Pos.setInt6(n);
            }
            this.flag104 = false;
        }

        private void setObj86(Object object) {
            MatrixStack cfr_ignored_0 = (MatrixStack)object;
            GlStateManager._disableCull();
            this.flag23 = true;
        }

        private void m636() {
            block8: {
                int n;
                String string;
                block7: {
                    string = Pos.getText67();
                    n = this.flag104 ? 1 : 0;
                    if (string == null) {
                        if (n != 0) {
                            this.m283();
                        }
                        n = this.count178;
                    }
                    if (string != null) break block7;
                    if (n <= 0) break block8;
                    n = this.flag23 ? 1 : 0;
                }
                if (string == null) {
                    if (n == 0) {
                        this.setObj86(matrixStack2);
                    }
                    n = GL11C.glGetInteger((int)34229);
                }
                int n2 = n;
                Pos.setInt6(this.count51);
                GlStateManager._drawElements((int)4, (int)this.count178, (int)5125, (long)0L);
                Pos.setInt6(n2);
            }
        }

        private void m379() {
            this.flag23 = false;
        }
    }
}

