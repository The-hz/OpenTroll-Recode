/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlBackend;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11C;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.ShaderProgram;
import shit.module.Category;
import shit.module.Module;
import shit.render.Blur2;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.Pos;
import shit.util.RenderUtil2;

@Environment(value=EnvType.CLIENT)
public class Camera
extends Module {
    public static Camera INSTANCE;
    public final BooleanSetting cameraClip = (BooleanSetting)this.m28(new BooleanSetting("CameraClip", false));
    public final NumberSetting clipDistance = (NumberSetting)this.m28(new NumberSetting("ClipDistance", 4.0, 1.0, 20.0, 0.1, 1.0, () -> (Boolean)this.cameraClip.getObj(), null, "", false));
    public final BooleanSetting aspectRatio = (BooleanSetting)this.m28(new BooleanSetting("AspectRatio", false));
    public final NumberSetting ratio = (NumberSetting)this.m28(new NumberSetting("Ratio", 1.78, 0.1, 5.0, 0.01, 1.0, () -> (Boolean)this.aspectRatio.getObj(), null, "", false));
    public final BooleanSetting fov = (BooleanSetting)this.m28(new BooleanSetting("Fov", false));
    public final NumberSetting fovValue = (NumberSetting)this.m28(new NumberSetting("FovValue", 90.0, 30.0, 170.0, 1.0, 1.0, () -> (Boolean)this.fov.getObj(), null, "", false));
    public final NumberSetting itemFov = (NumberSetting)this.m28(new NumberSetting("ItemFov", 70.0, 30.0, 170.0, 1.0, 1.0, () -> (Boolean)this.fov.getObj(), null, "", false));
    public final BooleanSetting motionCamera = (BooleanSetting)this.m28(new BooleanSetting("MotionCamera", false));
    public final BooleanSetting motionNoFirstPerson = (BooleanSetting)this.m28(new BooleanSetting("MotionNoFirstPerson", true, () -> (Boolean)this.motionCamera.getObj(), null, "", false));
    public final NumberSetting motionFPSpeed = (NumberSetting)this.m28(new NumberSetting("MotionFPSpeed", 0.6, 0.0, 1.0, 0.01, 1.0, () -> (Boolean)this.motionCamera.getObj(), null, "", false));
    public final NumberSetting motionSpeed = (NumberSetting)this.m28(new NumberSetting("MotionSpeed", 0.3, 0.0, 1.0, 0.01, 1.0, () -> (Boolean)this.motionCamera.getObj(), null, "", false));
    private double value109;
    private double value124;
    private double value181;
    private double value130;
    private double value158;
    private double value190;
    public final BooleanSetting motionBlur = (BooleanSetting)this.m28(new BooleanSetting("MotionBlur", false));
    public final NumberSetting blurAmount = (NumberSetting)this.m28(new NumberSetting("BlurAmount", 50.0, 0.0, 99.0, 1.0, 1.0, () -> (Boolean)this.motionBlur.getObj(), null, "", false));
    private ShaderProgram shaderProgram6;
    private ShaderProgram shaderProgram7;
    private Blur2 blur23;
    private boolean flag51 = false;

    public Camera() {
        super("Camera", "Camera clip, FOV, motion camera and motion blur.", Category.RENDER);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        Object var2_1 = null;
        if (!this.isSet98()) {
            if (((Boolean)this.motionCamera.getObj()).booleanValue()) {
                this.value109 = MC.client3.player.getX();
                this.value124 = MC.client3.player.getY() + (double)MC.client3.player.getEyeHeight(MC.client3.player.getPose());
                this.value181 = MC.client3.player.getZ();
                this.value130 = this.value109;
                this.value158 = this.value124;
                this.value190 = this.value181;
            }
        }
    }

    @Override
    public void m709() {
        this.m51();
    }

    @EventHandler
    private void setEvent2Inner225(Event2.Event2Inner2 event2Inner2) {
        if (this.isSet98()) {
            return;
        }
        if (this.isSet74()) {
            this.value130 = this.value109;
            this.value158 = this.value124;
            this.value190 = this.value181;
            double d = MC.client3.options.getPerspective().isFirstPerson() ? (Double)this.motionFPSpeed.getObj() : (Double)this.motionSpeed.getObj();
            this.value109 = Camera.m980(this.value109, MC.client3.player.getX(), d);
            this.value124 = Camera.m980(this.value124, MC.client3.player.getY() + (double)MC.client3.player.getEyeHeight(MC.client3.player.getPose()), d);
            this.value181 = Camera.m980(this.value181, MC.client3.player.getZ(), d);
        }
        if (((Boolean)this.motionBlur.getObj()).booleanValue()) {
            if (!this.flag51) {
                this.m516();
            }
        } else if (this.flag51) {
            this.m51();
        }
    }

    public void m885() {
        block16: {
            block15: {
                block14: {
                    block13: {
                        Object var2_1 = null;
                        if (!this.isSet144()) break block13;
                        if (this.flag51) break block14;
                    }
                    return;
                }
                if (this.shaderProgram6 == null) break block15;
                if (this.shaderProgram7 != null && this.blur23 != null) break block16;
            }
            return;
        }
        try {
            int n;
            int n2;
            int n3;
            block18: {
                block17: {
                    MinecraftClient minecraftClient = MC.client3;
                    n3 = minecraftClient.getWindow().getFramebufferWidth();
                    n2 = minecraftClient.getWindow().getFramebufferHeight();
                    if (this.blur23.count174 != n3) break block17;
                    if (this.blur23.count162 == n2) break block18;
                }
                this.blur23.m216();
            }
            if ((n = this.getInt72()) <= 0) {
                return;
            }
            float f = (float)Math.min((Double)this.blurAmount.getObj(), 99.0) / 100.0f;
            int n4 = this.getInt81();
            if (n4 <= 0) {
                return;
            }
            GlStateManager._glBindFramebuffer((int)36160, (int)n4);
            GlStateManager._viewport((int)0, (int)0, (int)n3, (int)n2);
            GlStateManager._disableDepthTest();
            GlStateManager._disableBlend();
            this.shaderProgram6.m1045();
            Pos.m437(n, 0);
            this.shaderProgram6.m241("uCurrent", 0);
            Pos.m437(this.blur23.count170, 1);
            this.shaderProgram6.m241("uPrevious", 1);
            this.shaderProgram6.m82("uBlendFactor", f);
            RenderUtil2.m504();
            RenderUtil2.m149();
            RenderUtil2.m582();
            GlStateManager._glUseProgram((int)0);
            this.blur23.m554();
            this.blur23.m78();
            GlStateManager._disableDepthTest();
            GlStateManager._disableBlend();
            this.shaderProgram7.m1045();
            Pos.m437(n, 0);
            this.shaderProgram7.m241("uTexture", 0);
            RenderUtil2.m504();
            RenderUtil2.m149();
            RenderUtil2.m582();
            GlStateManager._glUseProgram((int)0);
            GlStateManager._glBindFramebuffer((int)36160, (int)n4);
            GlStateManager._viewport((int)0, (int)0, (int)n3, (int)n2);
            GlStateManager._enableBlend();
        }
        catch (Throwable throwable) {}
    }

    private void m516() {
        try {
            this.shaderProgram6 = new ShaderProgram("passthrough.vert", "motion_blur.frag");
            this.shaderProgram7 = new ShaderProgram("passthrough.vert", "passthrough.frag");
            this.blur23 = new Blur2(1.0);
            this.flag51 = true;
        }
        catch (Throwable throwable) {
            this.flag51 = false;
        }
    }

    private void m51() {
        Object var2_1 = null;
        if (this.blur23 != null) {
            this.blur23.m485();
            this.blur23 = null;
        }
        this.shaderProgram6 = null;
        this.shaderProgram7 = null;
        this.flag51 = false;
    }

    private int getInt81() {
        GlBackend glBackend = (GlBackend)RenderSystem.getDevice();
        Object var2_2 = null;
        GpuTexture gpuTexture = MC.client3.getFramebuffer().getColorAttachment();
        if (gpuTexture instanceof GlTexture) {
            GlTexture glTexture = (GlTexture)gpuTexture;
            return glTexture.getOrCreateFramebuffer(glBackend.getBufferManager(), null);
        }
        return 0;
    }

    private int getInt72() {
        GpuTexture gpuTexture = MC.client3.getFramebuffer().getColorAttachment();
        Object var2_3 = null;
        if (gpuTexture instanceof GlTexture) {
            GlTexture glTexture = (GlTexture)gpuTexture;
            int n = glTexture.getGlId();
            return GL11C.glIsTexture((int)n) ? n : 0;
        }
        return 0;
    }

    private static double m980(double d, double d2, double d3) {
        double d4 = d;
        double d5 = d2;
        double d6 = d3;
        return d4 + (d5 - d4) * MathHelper.clamp((double)d6, (double)0.0, (double)1.0);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet131() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.cameraClip.getObj() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet61() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.aspectRatio.getObj() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet70() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.fov.getObj() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet74() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.motionCamera.getObj() == false) return false;
        if ((Boolean)this.motionNoFirstPerson.getObj() == false) return true;
        if (MC.client3.options.getPerspective().isFirstPerson()) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet144() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.motionBlur.getObj() == false) return false;
        if (!this.flag51) return false;
        return true;
    }

    public double getDouble9() {
        return MathHelper.lerp((float)MC.client3.getRenderTickCounter().getTickProgress(true), (float)((float)this.value130), (float)((float)this.value109));
    }

    public double getDouble14() {
        return MathHelper.lerp((float)MC.client3.getRenderTickCounter().getTickProgress(true), (float)((float)this.value158), (float)((float)this.value124));
    }

    public double getDouble8() {
        return MathHelper.lerp((float)MC.client3.getRenderTickCounter().getTickProgress(true), (float)((float)this.value190), (float)((float)this.value181));
    }

    private boolean isSet98() {
        Object var2_1 = null;
        return MC.client3.player == null || MC.client3.world == null;
    }
}

