/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import shit.Client;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.event.RenderLevelEvent;
import shit.module.Module;
import shit.util.BufferUtil2;
import shit.util.RenderPipelines;

@Environment(value=EnvType.CLIENT)
public final class RenderLevelEventManager2 {
    public static final RenderLevelEventManager2 renderLevelEventManager2 = new RenderLevelEventManager2();
    private static final RenderPipeline renderPipeline15 = null;
    private static final RenderPipeline renderPipeline19 = null;
    private final List list5 = new ArrayList();
    private final List list15 = new ArrayList();
    private final List list2 = new ArrayList();
    private final List list34 = new ArrayList();
    private final List list37 = new ArrayList();
    private final List list21 = new ArrayList();

    private RenderLevelEventManager2() {
        Client.eventBus.subscribe(this);
    }

    public static void m605() {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet100() {
        int n = 71;
        if (!this.list5.isEmpty()) return false;
        if (!this.list15.isEmpty()) return false;
        if (!this.list2.isEmpty()) return false;
        if (!this.list34.isEmpty()) return false;
        if (!this.list37.isEmpty()) return false;
        if (!this.list21.isEmpty()) return false;
        return true;
    }

    public void m195() {
        block0: {
            int n = PacketEvent.getInt40();
            this.list5.clear();
            int n2 = n;
            this.list15.clear();
            this.list2.clear();
            this.list34.clear();
            this.list37.clear();
            this.list21.clear();
            if (n2 == 0) break block0;
            Module.setTextArray9(new String[1]);
        }
    }

    public void m315(Object object, int n) {
        Box box = (Box)object;
        int n2 = n;
        this.m345(box, n2, n2);
    }

    public void m345(Object object, int n, int n2) {
        Box box = (Box)object;
        int n3 = n;
        int n4 = n2;
        this.list15.add(new Data2(box, n3, n4));
    }

    public void m972(Object object, Object object2, int n) {
        MatrixStack matrixStack = (MatrixStack)object;
        Box box = (Box)object2;
        int n2 = n;
        this.m484(matrixStack, box, n2, 2.0f);
    }

    public void m750(Object object, int n) {
        Box box = (Box)object;
        int n2 = n;
        this.m422(box, n2, 2.0f);
    }

    public void m484(Object object, Object object2, int n, float f) {
        MatrixStack cfr_ignored_0 = (MatrixStack)object;
        Box box = (Box)object2;
        int n2 = n;
        float f2 = f;
        this.list34.add(new Data4(box, n2, f2));
    }

    public void m422(Object object, int n, float f) {
        Box box = (Box)object;
        int n2 = n;
        float f2 = f;
        this.list34.add(new Data4(box, n2, f2));
    }

    public void m176(Object object, Object object2, int n, float f) {
        Vec3d vec3d = (Vec3d)object;
        Vec3d vec3d2 = (Vec3d)object2;
        int n2 = n;
        float f2 = f;
        int n3 = 71;
        if (vec3d.squaredDistanceTo(vec3d2) < 1.0E-6) {
            return;
        }
        this.list21.add(new PositionData(vec3d, vec3d2, n2, f2));
    }

    public void m284(Object object, Object object2) {
        Matrix4f matrix4f = (Matrix4f)object;
        Vec3d vec3d = (Vec3d)object2;
        int n = PacketEvent.getInt40();
        RenderLevelEventManager2 renderLevelEventManager2 = this;
        if (n == 0) {
            if (renderLevelEventManager2.isSet100()) {
                return;
            }
            try {
                this.m17();
                this.m725(matrix4f, vec3d);
                this.m826(matrix4f, vec3d);
                renderLevelEventManager2 = this;
            }
            catch (Throwable throwable) {
                this.m195();
                throw throwable;
            }
        }
        renderLevelEventManager2.m195();
    }

    @EventHandler(priority=-999)
    private void setRenderLevelEvent9(RenderLevelEvent renderLevelEvent) {
        this.m284(renderLevelEvent.getMatrix4f3(), renderLevelEvent.getObj4().getCameraPos());
    }

    private void m17() {
    }

    private void m725(Object object, Object object2) {
        Matrix4f matrix4f = (Matrix4f)object;
        Vec3d vec3d = (Vec3d)object2;
        int n = 71;
        if (this.list15.isEmpty() && this.list2.isEmpty()) {
            return;
        }
        RenderPipelines.ImmediateRendererHolder2 immediateRendererHolder2 = shit.util.RenderPipelines.m407(renderPipeline15);
        for (Object record : this.list15) {
            this.m992(immediateRendererHolder2, matrix4f, vec3d, record);
            if (71 != 0) continue;
        }
        for (Object record : this.list2) {
            this.m146(immediateRendererHolder2, matrix4f, vec3d, record);
            if (71 != 0) continue;
        }
        immediateRendererHolder2.m512();
    }

    private void m826(Object object, Object object2) {
        block13: {
            Object record;
            Iterator iterator;
            MatrixStack.Entry entry;
            RenderPipelines.RenderUtil renderUtil;
            int n;
            Vec3d vec3d;
            Matrix4f matrix4f;
            block12: {
                block11: {
                    block15: {
                        boolean bl;
                        block16: {
                            block14: {
                                matrix4f = (Matrix4f)object;
                                vec3d = (Vec3d)object2;
                                n = PacketEvent.getInt40();
                                bl = this.list34.isEmpty();
                                if (n != 0) break block14;
                                if (!bl) break block15;
                                bl = this.list37.isEmpty();
                            }
                            if (n != 0) break block16;
                            if (!bl) break block15;
                            bl = this.list21.isEmpty();
                        }
                        if (bl) {
                            return;
                        }
                    }
                    renderUtil = shit.util.RenderPipelines.m90(renderPipeline19);
                    MatrixStack matrixStack = new MatrixStack();
                    matrixStack.multiplyPositionMatrix((Matrix4fc)matrix4f);
                    entry = matrixStack.peek();
                    iterator = this.list34.iterator();
                    while (iterator.hasNext()) {
                        record = (Data4)iterator.next();
                        this.m492(renderUtil, matrix4f, entry, vec3d, record);
                        if (n == 0) {
                            if (n == 0) continue;
                        }
                        break block11;
                    }
                    iterator = this.list37.iterator();
                }
                while (iterator.hasNext()) {
                    record = (Data3)iterator.next();
                    this.m327(renderUtil, matrix4f, entry, vec3d, record);
                    if (n == 0) {
                        if (n == 0) continue;
                    }
                    break block12;
                }
                iterator = this.list21.iterator();
            }
            while (iterator.hasNext()) {
                record = (PositionData)iterator.next();
                this.m730(renderUtil, matrix4f, entry, vec3d, record);
                if (n == 0) {
                    if (n == 0) continue;
                }
                break block13;
            }
            renderUtil.m48();
        }
    }

    private void m992(Object object, Object object2, Object object3, Object object4) {
        RenderPipelines.ImmediateRendererHolder2 immediateRendererHolder2 = (RenderPipelines.ImmediateRendererHolder2)object;
        Matrix4f matrix4f = (Matrix4f)object2;
        Vec3d vec3d = (Vec3d)object3;
        Data2 data2 = (Data2)object4;
        Box box = data2.getBox();
        float f = (float)(box.minX - vec3d.x);
        float f2 = (float)(box.minY - vec3d.y);
        float f3 = (float)(box.minZ - vec3d.z);
        float f4 = (float)(box.maxX - vec3d.x);
        float f5 = (float)(box.maxY - vec3d.y);
        float f6 = (float)(box.maxZ - vec3d.z);
        this.m98(immediateRendererHolder2, matrix4f, f, f2, f3, data2.count28(), f, f2, f6, data2.count28(), f4, f2, f6, data2.count28(), f4, f2, f3, data2.count28());
        this.m98(immediateRendererHolder2, matrix4f, f, f5, f3, data2.count29(), f4, f5, f3, data2.count29(), f4, f5, f6, data2.count29(), f, f5, f6, data2.count29());
        this.m98(immediateRendererHolder2, matrix4f, f, f2, f3, data2.count28(), f4, f2, f3, data2.count28(), f4, f5, f3, data2.count29(), f, f5, f3, data2.count29());
        this.m98(immediateRendererHolder2, matrix4f, f4, f2, f3, data2.count28(), f4, f2, f6, data2.count28(), f4, f5, f6, data2.count29(), f4, f5, f3, data2.count29());
        this.m98(immediateRendererHolder2, matrix4f, f, f2, f6, data2.count28(), f, f5, f6, data2.count29(), f4, f5, f6, data2.count29(), f4, f2, f6, data2.count28());
        this.m98(immediateRendererHolder2, matrix4f, f, f2, f3, data2.count28(), f, f5, f3, data2.count29(), f, f5, f6, data2.count29(), f, f2, f6, data2.count28());
    }

    private void m146(Object object, Object object2, Object object3, Object object4) {
        RenderPipelines.ImmediateRendererHolder2 immediateRendererHolder2 = (RenderPipelines.ImmediateRendererHolder2)object;
        Matrix4f matrix4f = (Matrix4f)object2;
        Vec3d vec3d = (Vec3d)object3;
        Data data = (Data)object4;
        Box box = data.box();
        float f = (float)(box.minX - vec3d.x);
        float f2 = (float)(box.minY - vec3d.y);
        int n = 71;
        float f3 = (float)(box.minZ - vec3d.z);
        float f4 = (float)(box.maxX - vec3d.x);
        float f5 = (float)(box.maxY - vec3d.y);
        float f6 = (float)(box.maxZ - vec3d.z);
        switch (Lambda.counts20[data.getDirection3().ordinal()]) {
            case 1: {
                this.m98(immediateRendererHolder2, matrix4f, f, f2, f3, data.count2(), f4, f2, f3, data.count2(), f4, f2, f6, data.count2(), f, f2, f6, data.count2());
                if (71 != 0) break;
            }
            case 2: {
                this.m98(immediateRendererHolder2, matrix4f, f, f2, f3, data.count2(), f, f5, f3, data.count2(), f4, f5, f3, data.count2(), f4, f2, f3, data.count2());
                if (71 != 0) break;
            }
            case 3: {
                this.m98(immediateRendererHolder2, matrix4f, f4, f2, f3, data.count2(), f4, f5, f3, data.count2(), f4, f5, f6, data.count2(), f4, f2, f6, data.count2());
                if (71 != 0) break;
            }
            case 4: {
                this.m98(immediateRendererHolder2, matrix4f, f, f2, f6, data.count2(), f4, f2, f6, data.count2(), f4, f5, f6, data.count2(), f, f5, f6, data.count2());
                if (71 != 0) break;
            }
            case 5: {
                this.m98(immediateRendererHolder2, matrix4f, f, f2, f3, data.count2(), f, f2, f6, data.count2(), f, f5, f6, data.count2(), f, f5, f3, data.count2());
                if (71 != 0) break;
            }
            case 6: {
                this.m98(immediateRendererHolder2, matrix4f, f, f5, f3, data.count2(), f, f5, f6, data.count2(), f4, f5, f6, data.count2(), f4, f5, f3, data.count2());
                break;
            }
        }
    }

    private void m492(Object object, Object object2, Object object3, Object object4, Object object5) {
        RenderPipelines.RenderUtil renderUtil = (RenderPipelines.RenderUtil)object;
        Matrix4f matrix4f = (Matrix4f)object2;
        MatrixStack.Entry entry = (MatrixStack.Entry)object3;
        Vec3d vec3d = (Vec3d)object4;
        Data4 data4 = (Data4)object5;
        Box box = data4.box5();
        float f = (float)(box.minX - vec3d.x);
        float f2 = (float)(box.minY - vec3d.y);
        float f3 = (float)(box.minZ - vec3d.z);
        float f4 = (float)(box.maxX - vec3d.x);
        float f5 = (float)(box.maxY - vec3d.y);
        float f6 = (float)(box.maxZ - vec3d.z);
        this.m198(renderUtil, matrix4f, entry, f, f2, f3, f4, f2, f3, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f4, f2, f3, f4, f2, f6, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f4, f2, f6, f, f2, f6, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f, f2, f6, f, f2, f3, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f, f5, f3, f4, f5, f3, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f4, f5, f3, f4, f5, f6, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f4, f5, f6, f, f5, f6, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f, f5, f6, f, f5, f3, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f, f2, f3, f, f5, f3, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f4, f2, f3, f4, f5, f3, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f4, f2, f6, f4, f5, f6, data4.getInt22(), data4.value49());
        this.m198(renderUtil, matrix4f, entry, f, f2, f6, f, f5, f6, data4.getInt22(), data4.value49());
    }

    private void m327(Object var1_1, Object var2_2, Object var3_3, Object var4_4, Object var5_5) {
        RenderPipelines.RenderUtil renderUtil = (RenderPipelines.RenderUtil)var1_1;
        Matrix4f matrix4f = (Matrix4f)var2_2;
        MatrixStack.Entry entry = (MatrixStack.Entry)var3_3;
        Vec3d vec3d = (Vec3d)var4_4;
        Data3 data3 = (Data3)var5_5;
        Box box = data3.box7();
        float minX = (float)(box.minX - vec3d.x);
        float minY = (float)(box.minY - vec3d.y);
        float minZ = (float)(box.minZ - vec3d.z);
        float maxX = (float)(box.maxX - vec3d.x);
        float maxY = (float)(box.maxY - vec3d.y);
        float maxZ = (float)(box.maxZ - vec3d.z);
        int color = data3.getInt16();
        float thickness = data3.getFloat38();
        switch (Lambda.counts20[data3.direction4().ordinal()]) {
            case 1: {
                this.m198(renderUtil, matrix4f, entry, minX, minY, minZ, maxX, minY, minZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, maxX, minY, minZ, maxX, minY, maxZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, maxX, minY, maxZ, minX, minY, maxZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, minX, minY, maxZ, minX, minY, minZ, color, thickness);
                break;
            }
            case 2: {
                this.m198(renderUtil, matrix4f, entry, maxX, minY, minZ, maxX, maxY, minZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, minX, minY, minZ, minX, maxY, minZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, maxX, minY, minZ, minX, minY, minZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, maxX, maxY, minZ, minX, maxY, minZ, color, thickness);
                break;
            }
            case 3: {
                this.m198(renderUtil, matrix4f, entry, maxX, minY, minZ, maxX, maxY, minZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, maxX, minY, maxZ, maxX, maxY, maxZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, maxX, maxY, maxZ, maxX, maxY, minZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, maxX, minY, maxZ, maxX, minY, minZ, color, thickness);
                break;
            }
            case 4: {
                this.m198(renderUtil, matrix4f, entry, minX, minY, maxZ, minX, maxY, maxZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, maxX, minY, maxZ, maxX, maxY, maxZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, minX, minY, maxZ, maxX, minY, maxZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, minX, maxY, maxZ, maxX, maxY, maxZ, color, thickness);
                break;
            }
            case 5: {
                this.m198(renderUtil, matrix4f, entry, minX, minY, minZ, minX, maxY, minZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, minX, minY, maxZ, minX, maxY, maxZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, minX, maxY, maxZ, minX, maxY, minZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, minX, minY, maxZ, minX, minY, minZ, color, thickness);
                break;
            }
            case 6: {
                this.m198(renderUtil, matrix4f, entry, minX, maxY, minZ, maxX, maxY, minZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, maxX, maxY, minZ, maxX, maxY, maxZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, maxX, maxY, maxZ, minX, maxY, maxZ, color, thickness);
                this.m198(renderUtil, matrix4f, entry, minX, maxY, maxZ, minX, maxY, minZ, color, thickness);
                break;
            }
        }
    }

    private void m730(Object object, Object object2, Object object3, Object object4, Object object5) {
        RenderPipelines.RenderUtil renderUtil = (RenderPipelines.RenderUtil)object;
        Matrix4f matrix4f = (Matrix4f)object2;
        MatrixStack.Entry entry = (MatrixStack.Entry)object3;
        Vec3d vec3d = (Vec3d)object4;
        PositionData positionData = (PositionData)object5;
        Vec3d vec3d2 = positionData.vec3d2().subtract(vec3d);
        Vec3d vec3d3 = positionData.vec3d3().subtract(vec3d);
        this.m198(renderUtil, matrix4f, entry, (float)vec3d2.x, (float)vec3d2.y, (float)vec3d2.z, (float)vec3d3.x, (float)vec3d3.y, (float)vec3d3.z, positionData.count14(), positionData.value50());
    }

    private void m98(Object object, Object object2, float f, float f2, float f3, int n, float f4, float f5, float f6, int n2, float f7, float f8, float f9, int n3, float f10, float f11, float f12, int n4) {
        RenderPipelines.ImmediateRendererHolder2 immediateRendererHolder2 = (RenderPipelines.ImmediateRendererHolder2)object;
        Matrix4f matrix4f = (Matrix4f)object2;
        float f13 = f;
        float f14 = f2;
        float f15 = f3;
        int n5 = n;
        float f16 = f4;
        float f17 = f5;
        float f18 = f6;
        int n6 = n2;
        float f19 = f7;
        float f20 = f8;
        float f21 = f9;
        int n7 = n3;
        float f22 = f10;
        float f23 = f11;
        float f24 = f12;
        int n8 = n4;
        immediateRendererHolder2.m530(matrix4f, f13, f14, f15, n5);
        immediateRendererHolder2.m530(matrix4f, f16, f17, f18, n6);
        immediateRendererHolder2.m530(matrix4f, f19, f20, f21, n7);
        immediateRendererHolder2.m530(matrix4f, f22, f23, f24, n8);
    }

    private void m198(Object object, Object object2, Object object3, float f, float f2, float f3, float f4, float f5, float f6, int n, float f7) {
        RenderPipelines.RenderUtil renderUtil = (RenderPipelines.RenderUtil)object;
        Matrix4f matrix4f = (Matrix4f)object2;
        MatrixStack.Entry entry = (MatrixStack.Entry)object3;
        float f8 = f;
        float f9 = f2;
        float f10 = f3;
        float f11 = f4;
        float f12 = f5;
        float f13 = f6;
        int n2 = n;
        float f14 = f7;
        Vector3f vector3f = this.m910(f8, f9, f10, f11, f12, f13);
        renderUtil.m439(matrix4f, entry, f8, f9, f10, n2, vector3f.x, vector3f.y, vector3f.z, f14);
        renderUtil.m439(matrix4f, entry, f11, f12, f13, n2, vector3f.x, vector3f.y, vector3f.z, f14);
    }

    private Vector3f m910(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f4;
        float f8 = f;
        float f9 = f7 - f8;
        float f10 = f5;
        float f11 = f2;
        float f12 = f10 - f11;
        float f13 = f6;
        float f14 = f3;
        float f15 = f13 - f14;
        float f16 = MathHelper.sqrt((float)(f9 * f9 + f12 * f12 + f15 * f15));
        if (f16 <= 1.0E-5f) {
            return new Vector3f(0.0f, 1.0f, 0.0f);
        }
        return new Vector3f(f9 / f16, f12 / f16, f15 / f16);
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts20 = new int[Direction.values().length];

        static {
            try {
                Lambda.counts20[Direction.DOWN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts20[Direction.NORTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts20[Direction.EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts20[Direction.SOUTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts20[Direction.WEST.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Lambda.counts20[Direction.UP.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Data  {
        private final Box box;
        private final int count2;
        private final Direction direction2;

        private Data(Box box, int n, Direction direction) {
            this.box = box;
            this.count2 = n;
            this.direction2 = direction;
        }

        public Box box() {
            return this.box;
        }

        public int count2() {
            return this.count2;
        }

        public Direction getDirection3() {
            return this.direction2;
        }
    }

    @Environment(value=EnvType.CLIENT)
    record Data5(Box box2, double value10) {
    }

    @Environment(value=EnvType.CLIENT)
    static final class Data4  {
        private final Box box5;
        private final int count13;
        private final float value49;

        private Data4(Box box, int n, float f) {
            this.box5 = box;
            this.count13 = n;
            this.value49 = f;
        }

        public Box box5() {
            return this.box5;
        }

        public int getInt22() {
            return this.count13;
        }

        public float value49() {
            return this.value49;
        }
    }

    @Environment(value=EnvType.CLIENT)
    record PositionData(Vec3d vec3d2, Vec3d vec3d3, int count14, float value50) {
    }

    @Environment(value=EnvType.CLIENT)
    static final class Data2  {
        private final Box box6;
        private final int count28;
        private final int count29;

        private Data2(Box box, int n, int n2) {
            this.box6 = box;
            this.count28 = n;
            this.count29 = n2;
        }

        public Box getBox() {
            return this.box6;
        }

        public int count28() {
            return this.count28;
        }

        public int count29() {
            return this.count29;
        }
    }

    @Environment(value=EnvType.CLIENT)
    static final class Data3  {
        private final Box box7;
        private final int count35;
        private final float value106;
        private final Direction direction4;

        private Data3(Box box, int n, float f, Direction direction) {
            this.box7 = box;
            this.count35 = n;
            this.value106 = f;
            this.direction4 = direction;
        }

        public Box box7() {
            return this.box7;
        }

        public int getInt16() {
            return this.count35;
        }

        public float getFloat38() {
            return this.value106;
        }

        public Direction direction4() {
            return this.direction4;
        }
    }
}

