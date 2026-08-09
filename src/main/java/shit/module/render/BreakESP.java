/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import java.text.DecimalFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.BlockBreakingInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import shit.Client;
import shit.event.EventHandler;
import shit.event.Render2DEvent;
import shit.event.RenderLevelEvent;
import shit.mixin.LevelRendererAccessor;
import shit.module.Category;
import shit.module.Module;
import shit.render.EspRenderLayers;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class BreakESP
extends Module {
    private final BooleanSetting self = (BooleanSetting)this.registerSetting(new BooleanSetting("Self", true));
    private final BooleanSetting other = (BooleanSetting)this.registerSetting(new BooleanSetting("Other", true));
    private final ColorSetting color = (ColorSetting)this.registerSetting(new ColorSetting("Color", -3756020));
    private final ColorSetting friendColor = (ColorSetting)this.registerSetting(new ColorSetting("FriendColor", -14799447));
    private final NumberSetting filledAlpha = (NumberSetting)this.registerSetting(new NumberSetting("FilledAlpha", 50.0, 0.0, 255.0, 1.0));
    private final NumberSetting outlineAlpha = (NumberSetting)this.registerSetting(new NumberSetting("OutlineAlpha", 255.0, 0.0, 255.0, 1.0));
    private final BooleanSetting throughWall = (BooleanSetting)this.registerSetting(new BooleanSetting("ThroughWall", true));
    private final BooleanSetting showName = (BooleanSetting)this.registerSetting(new BooleanSetting("ShowName", true));
    private final BooleanSetting showProgress = (BooleanSetting)this.registerSetting(new BooleanSetting("ShowProgress", true));
    private final DecimalFormat decimalFormat = new DecimalFormat("0");
    private Matrix4f matrix4f9;
    private Matrix4f matrix4f3;

    public BreakESP() {
        super("BreakESP", "Highlights blocks being broken nearby.", Category.RENDER);
    }

    @Override
    public String getInfo() {
        Object var2_1 = null;
        if (MC.mc.worldRenderer == null) {
            return null;
        }
        return Integer.toString(((LevelRendererAccessor)MC.mc.worldRenderer).getDestroyingBlocks().size());
    }

    @EventHandler
    private void setRenderLevelEvent8(RenderLevelEvent renderLevelEvent) {
        if (Module.isNotInGame() || MC.mc.worldRenderer == null) {
            return;
        }
        this.matrix4f9 = new Matrix4f((Matrix4fc)renderLevelEvent.getMatrix4f3());
        this.matrix4f3 = new Matrix4f((Matrix4fc)renderLevelEvent.getMatrix4f());
        for (BlockBreakingInfo blockBreakingInfo : (java.util.Collection<BlockBreakingInfo>)((LevelRendererAccessor)MC.mc.worldRenderer).getDestroyingBlocks().values()) {
            if (this.m173(blockBreakingInfo)) continue;
            BlockPos blockPos = blockBreakingInfo.getPos();
            double d = Math.max(0.05, Math.min(1.0, (double)(blockBreakingInfo.getStage() + 1) / 10.0));
            Box box = this.m91(blockPos, d);
            int n = this.m41(blockBreakingInfo);
            EspRenderLayers.drawBoxFilled(renderLevelEvent.getMatrix4f3(), box, BreakESP.m832(n, this.filledAlpha.getInt()), (Boolean)this.throughWall.getValue());
            EspRenderLayers.drawBoxOutline(renderLevelEvent.getMatrix4f3(), box, BreakESP.m832(n, this.outlineAlpha.getInt()), (Boolean)this.throughWall.getValue());
        }
        EspRenderLayers.drawBuffers();
    }

    @EventHandler
    private void setObj69(Render2DEvent render2DEvent) {
        if (Module.isNotInGame() || MC.mc.worldRenderer == null) {
            return;
        }
        if (!((Boolean)this.showName.getValue()).booleanValue() && !((Boolean)this.showProgress.getValue()).booleanValue()) {
            return;
        }
        if (this.matrix4f9 == null || this.matrix4f3 == null) {
            return;
        }
        Vec3d vec3d = MC.mc.gameRenderer.getCamera().getCameraPos();
        Matrix4f matrix4f = new Matrix4f((Matrix4fc)this.matrix4f3).mul((Matrix4fc)this.matrix4f9);
        int n = MC.mc.getWindow().getScaledWidth();
        int n2 = MC.mc.getWindow().getScaledHeight();
        for (BlockBreakingInfo blockBreakingInfo : (java.util.Collection<BlockBreakingInfo>)((LevelRendererAccessor)MC.mc.worldRenderer).getDestroyingBlocks().values()) {
            int n3;
            Object object;
            Entity entity;
            BlockPos blockPos;
            int[] nArray;
            if (this.m173(blockBreakingInfo) || (nArray = BreakESP.m313((double)(blockPos = blockBreakingInfo.getPos()).getX() + 0.5, (double)blockPos.getY() + 1.25, (double)blockPos.getZ() + 0.5, vec3d, matrix4f, n, n2)) == null) continue;
            int n4 = nArray[0];
            int n5 = nArray[1];
            if (((Boolean)this.showName.getValue()).booleanValue() && (entity = MC.mc.world.getEntityById(blockBreakingInfo.getActorId())) instanceof PlayerEntity) {
                object = (PlayerEntity)entity;
                String string = ((PlayerEntity)object).getName().getString();
                n3 = Client.manager.isFriend(string) ? -11184641 : -1;
                int n6 = Client.fontManager.renderer2().getStringWidth(string);
                Client.fontManager.renderer2().drawText(render2DEvent.getDrawContext(), string, n4 - n6 / 2, n5, n3, true);
                n5 += Client.fontManager.renderer2().getFontHeight() + 1;
            }
            if (!((Boolean)this.showProgress.getValue()).booleanValue()) continue;
            int n7 = (int)((double)(blockBreakingInfo.getStage() + 1) / 10.0 * 100.0);
            n7 = Math.min(100, Math.max(1, n7));
            object = n7 + "%";
            int n8 = Client.fontManager.renderer2().getStringWidth(object);
            n3 = BreakESP.m819(n7);
            Client.fontManager.renderer2().drawText(render2DEvent.getDrawContext(), object, n4 - n8 / 2, n5, n3, true);
        }
    }

    private boolean m173(Object object) {
        BlockBreakingInfo blockBreakingInfo = (BlockBreakingInfo)object;
        Object var4_3 = null;
        if (MC.mc.player == null) {
            return true;
        }
        if (blockBreakingInfo.getActorId() == MC.mc.player.getId()) {
            return (Boolean)this.self.getValue() == false;
        }
        return (Boolean)this.other.getValue() == false;
    }

    private int m41(Object object) {
        BlockBreakingInfo blockBreakingInfo = (BlockBreakingInfo)object;
        Object var4_3 = null;
        if (MC.mc.player == null) {
            return (Integer)this.color.getValue();
        }
        Entity entity = MC.mc.world.getEntityById(blockBreakingInfo.getActorId());
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)entity;
            if (Client.manager.isFriend(playerEntity.getName().getString())) {
                return (Integer)this.friendColor.getValue();
            }
        }
        return (Integer)this.color.getValue();
    }

    private Box m91(Object object, double d) {
        Box box;
        double d2;
        block7: {
            BlockPos blockPos;
            block6: {
                block5: {
                    VoxelShape voxelShape;
                    block4: {
                        blockPos = (BlockPos)object;
                        d2 = d;
                        voxelShape = MC.mc.world.getBlockState(blockPos).getOutlineShape((BlockView)MC.mc.world, blockPos);
                        Object var8_6 = null;
                        if (!voxelShape.isEmpty()) break block4;
                        box = new Box(blockPos);
                        if (null == null) break block5;
                    }
                    box = voxelShape.getBoundingBox().offset(blockPos);
                }
                if (box.getLengthX() <= 0.0) break block6;
                if (box.getLengthY() <= 0.0) break block6;
                if (!(box.getLengthZ() <= 0.0)) break block7;
            }
            box = new Box(blockPos);
        }
        double d3 = (box.minX + box.maxX) * 0.5;
        double d4 = (box.minY + box.maxY) * 0.5;
        double d5 = (box.minZ + box.maxZ) * 0.5;
        double d6 = box.getLengthX() * d2 * 0.5;
        double d7 = box.getLengthY() * d2 * 0.5;
        double d8 = box.getLengthZ() * d2 * 0.5;
        return new Box(d3 - d6, d4 - d7, d5 - d8, d3 + d6, d4 + d7, d5 + d8);
    }

    private static int m832(int n, int n2) {
        int n3 = n;
        int n4 = n2;
        return Math.max(0, Math.min(255, n4)) << 24 | n3 & 0xFFFFFF;
    }

    private static int m819(int n) {
        int n2 = n;
        Object var3_2 = null;
        if (n2 < 50) {
            return -52429;
        }
        if (n2 < 80) {
            return -22016;
        }
        return -13369532;
    }

    private static int[] m313(double d, double d2, double d3, Object object, Object object2, int n, int n2) {
        float f;
        float f2;
        int n3;
        int n4;
        block5: {
            block4: {
                double d4 = d;
                double d5 = d2;
                double d6 = d3;
                Vec3d vec3d = (Vec3d)object;
                Matrix4f matrix4f = (Matrix4f)object2;
                n4 = n;
                n3 = n2;
                Vector4f vector4f = new Vector4f((float)(d4 - vec3d.x), (float)(d5 - vec3d.y), (float)(d6 - vec3d.z), 1.0f).mul((Matrix4fc)matrix4f);
                Object var21_15 = null;
                if (vector4f.w() <= 0.05f) {
                    return null;
                }
                f2 = vector4f.x() / vector4f.w();
                f = vector4f.y() / vector4f.w();
                if (f2 < -1.2f) break block4;
                if (f2 > 1.2f) break block4;
                if (f < -1.2f) break block4;
                if (!(f > 1.2f)) break block5;
            }
            return null;
        }
        return new int[]{(int)((f2 * 0.5f + 0.5f) * (float)n4), (int)((1.0f - (f * 0.5f + 0.5f)) * (float)n3)};
    }
}

