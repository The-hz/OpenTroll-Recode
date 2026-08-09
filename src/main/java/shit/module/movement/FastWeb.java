/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import shit.Client;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;
import shit.util.MathUtil;

@Environment(value=EnvType.CLIENT)
public class FastWeb
extends Module {
    public static FastWeb INSTANCE;
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.Vanilla));
    private final BooleanSetting onlySneak = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlySneak", true));
    private final BooleanSetting grim = (BooleanSetting)this.registerSetting(new BooleanSetting("Grim", false));
    private final BooleanSetting abortPacket = (BooleanSetting)this.registerSetting(new BooleanSetting("AbortPacket", true));
    private final NumberSetting xZSpeed = (NumberSetting)this.registerSetting(new NumberSetting("XZSpeed", 25.0, 0.0, 100.0, 0.1));
    private final NumberSetting ySpeed = (NumberSetting)this.registerSetting(new NumberSetting("YSpeed", 100.0, 0.0, 100.0, 0.1));
    private final NumberSetting speed = (NumberSetting)this.registerSetting(new NumberSetting("Speed", 3.0, 0.0, 8.0, 0.1));
    private boolean flag94;

    public FastWeb() {
        super("FastWeb", "Removes or changes cobweb slowdown.", Category.MOVEMENT);
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        this.flag94 = false;
        Client.timerScale.m64();
    }

    @Override
    public String getInfo() {
        return ((Mode)((Object)this.mode.getValue())).name();
    }

    public Mode getMode2() {
        return (Mode)((Object)this.mode.getValue());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet116() {
        Object var2_1 = null;
        if (Module.isNotInGame()) {
            return false;
        }
        if ((Boolean)this.onlySneak.getValue() == false) return true;
        if (!MC.mc.player.isInSneakingPose()) return false;
        return true;
    }

    public double getDouble() {
        return (Double)this.xZSpeed.getValue() / 100.0;
    }

    public double getDouble17() {
        return (Double)this.ySpeed.getValue() / 100.0;
    }

    private boolean isSet88() {
        Object var2_1 = null;
        if (Module.isNotInGame()) {
            return false;
        }
        if (MC.mc.player.isOnGround()) {
            return false;
        }
        if (!this.isSet116()) {
            return false;
        }
        return this.isInWeb2();
    }

    @EventHandler
    private void setEvent2Inner42(TickEvent.PreTick event2Inner) {
        if (Module.isNotInGame()) {
            return;
        }
        this.flag94 = this.isSet88();
        if (!this.flag94) {
            Client.timerScale.m64();
        } else if (this.mode.getValue() == Mode.Vanilla) {
            MathUtil.setMotionY(-((Double)this.speed.getValue()).doubleValue());
        } else if (this.mode.getValue() == Mode.Strict) {
            Client.timerScale.setFloat5(this.speed.getFloat());
        }
        if (((Boolean)this.grim.getValue()).booleanValue() && this.isSet116()) {
            for (BlockPos blockPos : this.getList5()) {
                if (((Boolean)this.abortPacket.getValue()).booleanValue()) {
                    MC.mc.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, blockPos, Direction.DOWN));
                }
                MC.mc.player.networkHandler.sendPacket((Packet)new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.DOWN));
            }
        }
    }

    private List<BlockPos> getList5() {
        int n;
        ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
        Object var2_4 = null;
        for (int i = n = 2; i > -2; --i) {
            for (int j = n; j > -2; --j) {
                for (int k = n; k > -2; --k) {
                    BlockPos blockPos = BlockPos.ofFloored((double)(MC.mc.player.getX() + (double)i), (double)(MC.mc.player.getY() + (double)j), (double)(MC.mc.player.getZ() + (double)k));
                    Vec3d vec3d = Vec3d.ofCenter((Vec3i)blockPos);
                    if (MC.mc.player.getEntityPos().distanceTo(vec3d) > 1.0) {
                        if (MC.mc.player.getEyePos().distanceTo(vec3d) > 1.0) continue;
                    }
                    if (!MC.mc.world.getBlockState(blockPos).isOf(Blocks.COBWEB)) continue;
                    arrayList.add(blockPos);
                    if (null == null) continue;
                }
                if (null == null) continue;
            }
            if (null == null) continue;
        }
        return arrayList;
    }

    private boolean isInWeb2() {
        return MC.mc.world.getStatesInBox(MC.mc.player.getBoundingBox()).anyMatch(blockState -> blockState.isOf(Blocks.COBWEB));
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Mode {
      Vanilla, Strict, Custom, Ignore;

      private Mode() {}



        private static Mode[] getModeArray11() {
            return new Mode[]{Vanilla, Strict, Custom, Ignore};
        }
    
   }
}

