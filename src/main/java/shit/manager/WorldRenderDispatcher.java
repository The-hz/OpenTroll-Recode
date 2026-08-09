/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4d;
import org.joml.Vector4f;
import shit.Client;
import shit.event.EventHandler;
import shit.event.RenderLevelEvent;
import shit.util.MC;
import shit.util.GpuPipelineFactory;

@Environment(value=EnvType.CLIENT)
public final class WorldRenderDispatcher
implements MC {
    private static final WorldRenderDispatcher renderLevelEventManager = new WorldRenderDispatcher();
    private Matrix4f matrix4f;
    private Matrix4f matrix4f7;

    private WorldRenderDispatcher() {
        Client.eventBus.subscribe(this);
    }

    public static void m958() {
    }

    @EventHandler(priority=999)
    private void setRenderLevelEvent6(RenderLevelEvent renderLevelEvent) {
        this.matrix4f = new Matrix4f((Matrix4fc)renderLevelEvent.getMatrix4f3());
        this.matrix4f7 = new Matrix4f((Matrix4fc)renderLevelEvent.getMatrix4f());
    }

    private static Matrix4f getMatrix4f2() {
        Matrix4f matrix4f;
        Matrix4f matrix4f2;
        block3: {
            block2: {
                matrix4f2 = WorldRenderDispatcher.renderLevelEventManager.matrix4f;
                Object var1_1 = null;
                matrix4f = WorldRenderDispatcher.renderLevelEventManager.matrix4f7;
                if (matrix4f2 == null) break block2;
                if (matrix4f != null) break block3;
            }
            return null;
        }
        return new Matrix4f((Matrix4fc)matrix4f).mul((Matrix4fc)matrix4f2);
    }

    public static Vector4d m676(Object object) {
        Vec3d vec3d;
        Box box = (Box)object;
        Matrix4f matrix4f = WorldRenderDispatcher.getMatrix4f2();
        if (matrix4f == null) {
            return null;
        }
        int[] nArray = new int[]{0, 0, MC.mc.getWindow().getFramebufferWidth(), MC.mc.getWindow().getFramebufferHeight()};
        Vector4d vector4d = WorldRenderDispatcher.m212(nArray, matrix4f, box, vec3d = MC.mc.gameRenderer.getCamera().getCameraPos());
        if (vector4d == null) {
            return null;
        }
        double d = GpuPipelineFactory.getDouble18();
        vector4d.x /= d;
        vector4d.y /= d;
        vector4d.z /= d;
        vector4d.w /= d;
        return vector4d;
    }

    public static Vector4d m212(Object object, Object object2, Object object3, Object object4) {
        int[] nArray = (int[])object;
        Matrix4f matrix4f = (Matrix4f)object2;
        Box box = (Box)object3;
        Vec3d vec3d = (Vec3d)object4;
        Vector4f vector4f = new Vector4f();
        Object var9_9 = null;
        Vector4d vector4d = null;
        boolean bl = false;
        for (int i = 0; i < 8; ++i) {
            Vector3f vector3f = new Vector3f(((i & 1) == 0 ? (float)box.minX : (float)box.maxX) - (float)vec3d.x, ((i & 2) == 0 ? (float)box.minY : (float)box.maxY) - (float)vec3d.y, ((i & 4) == 0 ? (float)box.minZ : (float)box.maxZ) - (float)vec3d.z);
            matrix4f.project((Vector3fc)vector3f, nArray, vector4f);
            vector4f.y = (float)nArray[3] - vector4f.y;
            if (!Float.isFinite(vector4f.x)) continue;
            if (!Float.isFinite(vector4f.y)) continue;
            if (!Float.isFinite(vector4f.z)) {
                if (null == null) continue;
            }
            if (vector4f.z < 0.0f) continue;
            if (vector4f.z > 1.0f && null == null) continue;
            bl = true;
            if (vector4d == null) {
                vector4d = new Vector4d((double)vector4f.x, (double)vector4f.y, (double)vector4f.x, (double)vector4f.y);
                if (null == null) continue;
            }
            vector4d.x = Math.min(vector4d.x, (double)vector4f.x);
            vector4d.y = Math.min(vector4d.y, (double)vector4f.y);
            vector4d.z = Math.max(vector4d.z, (double)vector4f.x);
            vector4d.w = Math.max(vector4d.w, (double)vector4f.y);
            if (null == null) continue;
        }
        return bl ? vector4d : null;
    }

    public static Vec3d m845(Object object, float f) {
        Entity entity = (Entity)object;
        float f2 = f;
        double d = MathHelper.lerp((double)f2, (double)entity.lastRenderX, (double)entity.getX());
        double d2 = MathHelper.lerp((double)f2, (double)entity.lastRenderY, (double)entity.getY());
        double d3 = MathHelper.lerp((double)f2, (double)entity.lastRenderZ, (double)entity.getZ());
        return new Vec3d(d, d2, d3);
    }
}

