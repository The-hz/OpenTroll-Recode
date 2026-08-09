/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.client.MinecraftClient
 *  net.minecraft.client.gl.Framebuffer
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.ItemEntity
 *  net.minecraft.entity.decoration.EndCrystalEntity
 *  net.minecraft.entity.mob.HostileEntity
 *  net.minecraft.entity.passive.AnimalEntity
 *  net.minecraft.entity.player.PlayerEntity
 *  net.minecraft.entity.projectile.thrown.EnderPearlEntity
 *  net.minecraft.entity.projectile.thrown.ExperienceBottleEntity
 *  net.minecraft.util.math.ColorHelper
 */
package shit.module.render;

import java.awt.Color;
import java.lang.runtime.SwitchBootstraps;
import java.util.Objects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.util.math.ColorHelper;
import shit.event.EventHandler;
import shit.event.InterceptEntityOutlineEvent;
import shit.mixin.LevelRendererAccessor;
import shit.module.Category;
import shit.module.Module;
import shit.render.Passthrough2;
import shit.render.ShaderEffect;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.ShadersUtil;

@Environment(value=EnvType.CLIENT)
public class Shaders
extends Module {
    public static Shaders INSTANCE;
    private static final long time77;
    public final NumberSetting range;
    public final BooleanSetting players;
    public final BooleanSetting self;
    public final BooleanSetting hostiles;
    public final BooleanSetting passives;
    public final BooleanSetting crystals;
    public final BooleanSetting items;
    public final BooleanSetting xP;
    public final BooleanSetting pearls;
    public final NumberSetting width;
    public final NumberSetting outlineAlpha;
    public final EnumSetting fillMode;
    public final NumberSetting fillAlpha;
    public final ColorSetting color;
    public final NumberSetting gradientFactor;
    public final ColorSetting gradientColor;
    public final NumberSetting flowLayers;
    public final NumberSetting flowFactor;
    public final NumberSetting liquidLayers;
    public final NumberSetting liquidFactor;
    public final NumberSetting quality;
    public final NumberSetting octaves;
    public final NumberSetting noiseScale;
    public final NumberSetting secondaryAlpha;
    public final BooleanSetting smokeGlow;
    public final ColorSetting smokeOutlineColor1;
    public final ColorSetting smokeOutlineColor2;
    public final ColorSetting fillColor2;
    public final ColorSetting fillColor3;
    private final Passthrough2 passthrough2;
    public volatile boolean flag126;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean m473(Object object) {
        FillMode fillMode = (FillMode)((Object)object);
        Object var3_2 = null;
        if (fillMode == FillMode.SMOKE) return true;
        if (fillMode == FillMode.SNOW) return true;
        if (fillMode != FillMode.FADE) return false;
        return true;
    }

        public Shaders() {
        super("Shaders", "Renders outline shaders over entities", Category.RENDER);
        this.range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 30.0, 0.0, 250.0));
        this.players = (BooleanSetting)this.registerSetting(new BooleanSetting("Players", true));
        this.self = (BooleanSetting)this.registerSetting(new BooleanSetting("Self", true));
        this.hostiles = (BooleanSetting)this.registerSetting(new BooleanSetting("Hostiles", true));
        this.passives = (BooleanSetting)this.registerSetting(new BooleanSetting("Passives", true));
        this.crystals = (BooleanSetting)this.registerSetting(new BooleanSetting("Crystals", true));
        this.items = (BooleanSetting)this.registerSetting(new BooleanSetting("Items", true));
        this.xP = (BooleanSetting)this.registerSetting(new BooleanSetting("XP", true));
        this.pearls = (BooleanSetting)this.registerSetting(new BooleanSetting("Pearls", true));
        this.width = (NumberSetting)this.registerSetting(new NumberSetting("Width", 1.0, 0.0, 5.0, 0.1));
        this.outlineAlpha = (NumberSetting)this.registerSetting(new NumberSetting("OutlineAlpha", 1.0, 0.01, 1.0, 0.01));
        this.fillMode = (EnumSetting)this.registerSetting(new EnumSetting("FillMode", FillMode.DEFAULT));
        this.fillAlpha = (NumberSetting)this.registerSetting(new NumberSetting("FillAlpha", 0.5, 0.0, 1.0, 0.01));
        this.color = (ColorSetting)this.registerSetting(new ColorSetting("Color", -12285697));
        this.gradientFactor = (NumberSetting)this.registerSetting(new NumberSetting("GradientFactor", 5.0, 0.1, 10.0, 0.1));
        this.gradientColor = (ColorSetting)this.registerSetting(new ColorSetting("GradientColor", -1));
        this.flowLayers = (NumberSetting)this.registerSetting(new NumberSetting("FlowLayers", 10.0, 1.0, 10.0, 1.0));
        this.flowFactor = (NumberSetting)this.registerSetting(new NumberSetting("FlowFactor", 0.6, 0.1, 1.0, 0.05));
        this.liquidLayers = (NumberSetting)this.registerSetting(new NumberSetting("LiquidLayers", 5.0, 1.0, 10.0, 1.0));
        this.liquidFactor = (NumberSetting)this.registerSetting(new NumberSetting("LiquidFactor", 5.0, 1.0, 10.0, 0.5));
        this.quality = (NumberSetting)this.registerSetting(new NumberSetting("Quality", 1.0, 0.1, 3.0, 0.1));
        this.octaves = (NumberSetting)this.registerSetting(new NumberSetting("Octaves", 4.0, 1.0, 8.0, 1.0));
        this.noiseScale = (NumberSetting)this.registerSetting(new NumberSetting("NoiseScale", 1.0, 0.1, 10.0, 0.1));
        this.secondaryAlpha = (NumberSetting)this.registerSetting(new NumberSetting("SecondaryAlpha", 0.5, 0.0, 1.0, 0.01));
        this.smokeGlow = (BooleanSetting)this.registerSetting(new BooleanSetting("SmokeGlow", false));
        this.smokeOutlineColor1 = (ColorSetting)this.registerSetting(new ColorSetting("SmokeOutlineColor1", -5592406));
        this.smokeOutlineColor2 = (ColorSetting)this.registerSetting(new ColorSetting("SmokeOutlineColor2", -12303292));
        this.fillColor2 = (ColorSetting)this.registerSetting(new ColorSetting("FillColor2", -3355444));
        this.fillColor3 = (ColorSetting)this.registerSetting(new ColorSetting("FillColor3", -10066330));
        this.passthrough2 = new Passthrough2();
        this.flag126 = false;
    }

    @Override
    public void onDisable() {
        this.passthrough2.m1012();
        this.flag126 = false;
    }

    public boolean m459(Object object) {
        Entity entity = (Entity)object;
        if (Module.isNotInGame()) {
            return false;
        }
        double range = (Double)this.range.getValue() * (Double)this.range.getValue();
        if (entity.squaredDistanceTo(MC.mc.player) > range) {
            return false;
        }
        if (entity instanceof PlayerEntity) {
            if (entity == MC.mc.player) {
                return (Boolean)this.self.getValue();
            }
            return (Boolean)this.players.getValue();
        }
        if (entity instanceof HostileEntity) {
            return (Boolean)this.hostiles.getValue();
        }
        if (entity instanceof AnimalEntity) {
            return (Boolean)this.passives.getValue();
        }
        if (entity instanceof ItemEntity) {
            return (Boolean)this.items.getValue();
        }
        if (entity instanceof ExperienceBottleEntity) {
            return (Boolean)this.xP.getValue();
        }
        if (entity instanceof EnderPearlEntity) {
            return (Boolean)this.pearls.getValue();
        }
        if (entity instanceof EndCrystalEntity) {
            return (Boolean)this.crystals.getValue();
        }
        return false;
    }

    public int m743(Object object) {
        Entity cfr_ignored_0 = (Entity)object;
        return (Integer)this.color.getValue() | 0xFF000000;
    }

    @EventHandler
    private void setInterceptEntityOutlineEvent(InterceptEntityOutlineEvent interceptEntityOutlineEvent) {
        Object object;
        boolean bl = this.flag126;
        this.flag126 = false;
        if (Module.isNotInGame()) {
            return;
        }
        if (!bl) {
            interceptEntityOutlineEvent.cancel();
            return;
        }
        LevelRendererAccessor levelRendererAccessor = (LevelRendererAccessor)interceptEntityOutlineEvent.getObj17();
        Framebuffer framebuffer = levelRendererAccessor.getEntityOutlineTarget();
        if (framebuffer == null) {
            return;
        }
        FillMode fillMode = (FillMode)((Object)this.fillMode.getValue());
        if (Shaders.m473((Object)fillMode)) {
            object = switch (fillMode.ordinal()) {
                case 5 -> ShaderEffect.Type.Smoke;
                case 6 -> ShaderEffect.Type.Snow;
                case 7 -> ShaderEffect.Type.Fade;
                default -> ShaderEffect.Type.Default;
            };
            ShaderEffect.Data data = new ShaderEffect.Data(this.quality.getFloat(), this.width.getFloat(), (Boolean)this.smokeGlow.getValue(), this.fillAlpha.getFloat() * 255.0f, this.secondaryAlpha.getFloat() * 255.0f, this.gradientFactor.getFloat(), this.noiseScale.getFloat(), this.octaves.getFloat());
            ShaderEffect.Data4 data4 = new ShaderEffect.Data4(Shaders.m1008((Integer)this.color.getValue()), Shaders.m1008((Integer)this.smokeOutlineColor1.getValue()), Shaders.m1008((Integer)this.smokeOutlineColor2.getValue()), Shaders.m1008((Integer)this.color.getValue()), Shaders.m1008((Integer)this.fillColor2.getValue()), Shaders.m1008((Integer)this.fillColor3.getValue()));
            ShaderEffect.shaderEffect.m987(framebuffer, object, data, data4);
        } else {
            ShadersUtil shadersUtil = new ShadersUtil();
            shadersUtil.value204 = System.currentTimeMillis() - time77;
            shadersUtil.value192 = this.width.getFloat();
            shadersUtil.value111 = this.outlineAlpha.getFloat();
            shadersUtil.count83 = fillMode.ordinal();
            shadersUtil.value183 = this.fillAlpha.getFloat();
            shadersUtil.value176 = MC.mc.getWindow().getFramebufferWidth();
            shadersUtil.value148 = MC.mc.getWindow().getFramebufferHeight();
            int n = (Integer)this.gradientColor.getValue();
            shadersUtil.value115 = (float)ColorHelper.getRed((int)n) / 255.0f;
            shadersUtil.value133 = (float)ColorHelper.getGreen((int)n) / 255.0f;
            shadersUtil.value116 = (float)ColorHelper.getBlue((int)n) / 255.0f;
            shadersUtil.value138 = (float)ColorHelper.getAlpha((int)n) / 255.0f;
            shadersUtil.value134 = this.gradientFactor.getFloat() * 16.0f;
            shadersUtil.value131 = this.flowLayers.getFloat();
            shadersUtil.value135 = this.flowFactor.getFloat();
            shadersUtil.value128 = this.liquidLayers.getFloat();
            shadersUtil.value156 = this.liquidFactor.getFloat();
            this.passthrough2.m487(framebuffer, interceptEntityOutlineEvent.getObj5(), shadersUtil, false);
        }
        Framebuffer framebuffer2 = MinecraftClient.getInstance().getFramebuffer();
        if (framebuffer2 != null) {
            framebuffer.drawBlit(framebuffer2.getColorAttachmentView());
        }
        interceptEntityOutlineEvent.cancel();
    }

    private static Color m1008(int n) {
        int n2 = n;
        return new Color(ColorHelper.getRed((int)n2), ColorHelper.getGreen((int)n2), ColorHelper.getBlue((int)n2), ColorHelper.getAlpha((int)n2));
    }

    static {
        time77 = System.currentTimeMillis();
    }

    @Environment(value=EnvType.CLIENT)
    public static enum FillMode {
        DEFAULT,
        GRADIENT,
        FLOW,
        LIQUID,
        RAINBOW,
        SMOKE,
        SNOW,
        FADE;


        private static FillMode[] getFillModeArray() {
            return new FillMode[]{DEFAULT, GRADIENT, FLOW, LIQUID, RAINBOW, SMOKE, SNOW, FADE};
        }
    }
}
