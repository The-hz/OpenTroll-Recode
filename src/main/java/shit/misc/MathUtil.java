/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import shit.Client;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class MathUtil
implements MC {
    private float value136;
    private float value163;
    private float value108;
    private float value137;
    private boolean flag19;
    private boolean flag142;
    private int count218;
    private float value144;
    private float value149;
    private float value191;
    private float value171;
    private float value182;
    private float value197;
    private int count59;
    private boolean flag50;
    private float value175;
    private float value179;
    private Type type2 = Type.NONE;
    private static String text3112;

    public Type getType2() {
        return this.type2;
    }

    public void m935() {
        Client.eventBus.subscribe(this);
    }

    @EventHandler(priority=-999)
    private void setPacketEventInner26(PacketEvent.PacketEventInner2 packetEventInner2) {
        PlayerMoveC2SPacket playerMoveC2SPacket;
        Packet packet = packetEventInner2.getPacket();
        if (packet instanceof PlayerMoveC2SPacket && (playerMoveC2SPacket = (PlayerMoveC2SPacket)packet).changesLook() && this.flag142) {
            this.value136 = playerMoveC2SPacket.getYaw(this.value136);
            this.value163 = playerMoveC2SPacket.getPitch(this.value163);
            this.m19(this.value136, this.value163);
        }
    }

    @EventHandler(priority=100)
    private void setEvent2Inner24(Event2.Event2Inner2 event2Inner2) {
        if (this.flag19) {
            this.m19(this.value136, this.value163);
            if (this.count218 > 0) {
                --this.count218;
                if (this.count218 <= 0) {
                    this.flag19 = false;
                }
            }
        }
        this.type2 = Type.NONE;
    }

    private void m19(float f, float f2) {
        float f3 = f;
        float f4 = f2;
        Object var6_5 = null;
        if (MC.client3.player == null) {
            return;
        }
        int n = MC.client3.player.age;
        if (n != this.count59) {
            this.count59 = n;
            this.value197 = this.value182;
            this.value171 = this.value191;
            this.value149 = this.value144;
            this.value191 = this.m308(f3, this.value171);
            this.value144 = f3;
            this.value182 = f4;
        }
    }

    private float m308(float f, float f2) {
        float f3;
        float f4;
        float f5;
        block12: {
            block11: {
                float f6;
                block9: {
                    block10: {
                        f5 = f;
                        f4 = f6 = f2;
                        double d = MC.client3.player.getX() - MC.client3.player.lastRenderX;
                        Object var6_7 = null;
                        double d2 = MC.client3.player.getZ() - MC.client3.player.lastRenderZ;
                        if (!(d * d + d2 * d2 > (double)0.0025f)) break block9;
                        f3 = (float)Math.toDegrees(Math.atan2(d2, d)) - 90.0f;
                        float f7 = Math.abs(MathHelper.wrapDegrees((float)f5) - f3);
                        if (!(95.0f < f7)) break block10;
                        if (!(f7 < 265.0f)) break block10;
                        f4 = f3 - 180.0f;
                        if (null == null) break block9;
                    }
                    f4 = f3;
                }
                if (MC.client3.player.handSwinging) {
                    f4 = f5;
                }
                f4 = f6 + MathHelper.wrapDegrees((float)(f4 - f6)) * 0.3f;
                f3 = MathHelper.wrapDegrees((float)(f5 - f4));
                if (!(f3 < -75.0f)) break block11;
                f3 = -75.0f;
                if (null == null) break block12;
            }
            if (f3 >= 75.0f) {
                f3 = 75.0f;
            }
        }
        f4 = f5 - f3;
        if (f3 * f3 > 2500.0f) {
            f4 += f3 * 0.2f;
        }
        return f4;
    }

    public void m303(float f, float f2) {
        float f3 = f;
        float f4 = f2;
        Object var6_5 = null;
        if (MC.client3.player == null) {
            return;
        }
        this.type2 = Type.CUSTOM;
        this.flag142 = true;
        MC.client3.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.LookAndOnGround(f3, f4, MC.client3.player.isOnGround(), MC.client3.player.horizontalCollision));
        this.flag142 = false;
        this.value136 = f3;
        this.value163 = f4;
        this.flag19 = true;
        this.count218 = 10;
    }

    public void m11(float f, float f2) {
        float f3 = f;
        float f4 = f2;
        Object var6_5 = null;
        if (MC.client3.player == null) {
            return;
        }
        this.flag142 = true;
        MC.client3.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.LookAndOnGround(f3, f4, MC.client3.player.isOnGround(), MC.client3.player.horizontalCollision));
        this.flag142 = false;
        this.value136 = f3;
        this.value163 = f4;
    }

    public void m24(float f, float f2) {
        float f3 = f;
        float f4 = f2;
        Object var6_5 = null;
        if (this.type2 != Type.CUSTOM) {
            this.value136 = f3;
            this.value163 = f4;
            this.flag19 = true;
            this.count218 = 2;
            this.type2 = Type.SPRINT;
        }
    }

    public void m468(float f, float f2) {
        float f3 = f;
        float f4 = f2;
        Object var6_5 = null;
        if (MC.client3.player == null) {
            return;
        }
        this.type2 = Type.CUSTOM;
        this.flag142 = true;
        MC.client3.player.networkHandler.sendPacket((Packet)new PlayerMoveC2SPacket.Full(MC.client3.player.getX(), MC.client3.player.getY(), MC.client3.player.getZ(), f3, f4, MC.client3.player.isOnGround(), MC.client3.player.horizontalCollision));
        this.flag142 = false;
        this.value136 = f3;
        this.value163 = f4;
        this.flag19 = true;
        this.count218 = 20;
    }

    public void setFloat6(float f) {
        float f2 = f;
        Object var4_3 = null;
        if (!this.flag19 || MC.client3.player == null) {
            return;
        }
        this.type2 = Type.CUSTOM;
        float f3 = MathHelper.wrapDegrees((float)(this.value108 - this.value136));
        float f4 = this.value137 - this.value163;
        double d = Math.sqrt(f3 * f3 + f4 * f4);
        if (d < 0.1) {
            this.value136 = this.value108;
            this.value163 = this.value137;
            this.m19(this.value136, this.value163);
            return;
        }
        double d2 = (double)Math.abs(f3) / d;
        double d3 = (double)Math.abs(f4) / d;
        float f5 = (float)MathUtil.m15(f3, (double)(-f2) * d2, (double)f2 * d2);
        float f6 = (float)MathUtil.m15(f4, (double)(-f2) * d3, (double)f2 * d3);
        this.value136 += f5;
        this.value163 = MathUtil.m357(this.value163 + f6, -90.0f, 90.0f);
        this.m19(this.value136, this.value163);
    }

    public float getFloat51() {
        Object var2_1 = null;
        if (!this.flag19) {
            return 0.0f;
        }
        float f = MathHelper.wrapDegrees((float)(this.value108 - this.value136));
        float f2 = this.value137 - this.value163;
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    public void m844() {
        if (!this.flag19 || MC.client3.player == null) {
            return;
        }
        this.type2 = Type.NONE;
        this.m303(MC.client3.player.getYaw(), MC.client3.player.getPitch());
        this.flag19 = false;
        this.count218 = 0;
    }

    public void m2() {
        if (!this.flag19 || MC.client3.player == null) {
            return;
        }
        this.type2 = Type.NONE;
        this.m468(MC.client3.player.getYaw(), MC.client3.player.getPitch());
        this.flag19 = false;
        this.count218 = 0;
    }

    public void m370() {
        this.flag19 = false;
        this.count218 = 0;
        this.flag50 = false;
        this.type2 = Type.NONE;
    }

    public boolean isSet111() {
        return this.flag19;
    }

    public float getFloat25() {
        return this.value144;
    }

    public float getFloat56() {
        return this.value149;
    }

    public float getFloat19() {
        return this.value191;
    }

    public float getFloat42() {
        return this.value171;
    }

    public float getFloat65() {
        return this.value182;
    }

    public float getFloat63() {
        return this.value197;
    }

    public float getFloat55() {
        return this.value136;
    }

    public float getFloat58() {
        return this.value163;
    }

    public void m355(float f, float f2) {
        float f3 = f;
        float f4 = f2;
        this.type2 = Type.CUSTOM;
        this.value108 = f3;
        this.value137 = MathUtil.m357(f4, -90.0f, 90.0f);
        this.flag19 = true;
        this.count218 = 10;
    }

    public void setObj37(Object object) {
        ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)object;
        Object var4_3 = null;
        if (this.flag50) {
            return;
        }
        this.flag50 = true;
        this.value175 = clientPlayerEntity.getYaw();
        this.value179 = clientPlayerEntity.getPitch();
        clientPlayerEntity.setYaw(this.value136);
        clientPlayerEntity.setPitch(this.value163);
    }

    public void setObj39(Object object) {
        ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity)object;
        Object var4_3 = null;
        if (!this.flag50) {
            return;
        }
        clientPlayerEntity.setYaw(this.value175);
        clientPlayerEntity.setPitch(this.value179);
        this.flag50 = false;
    }

    public static float[] m547(Object object, Object object2) {
        Vec3d vec3d = (Vec3d)object;
        Vec3d vec3d2 = (Vec3d)object2;
        double d = vec3d2.x - vec3d.x;
        double d2 = vec3d2.y - vec3d.y;
        double d3 = vec3d2.z - vec3d.z;
        double d4 = Math.sqrt(d * d + d3 * d3);
        float f = (float)Math.toDegrees(Math.atan2(d3, d)) - 90.0f;
        float f2 = (float)(-Math.toDegrees(Math.atan2(d2, d4)));
        return new float[]{MathHelper.wrapDegrees((float)f), MathUtil.m357(f2, -90.0f, 90.0f)};
    }

    private static float m357(float f, float f2, float f3) {
        float f4 = f;
        float f5 = f2;
        float f6 = f3;
        return Math.max(f5, Math.min(f6, f4));
    }

    private static double m15(double d, double d2, double d3) {
        double d4 = d;
        double d5 = d2;
        double d6 = d3;
        return Math.max(d5, Math.min(d6, d4));
    }

    public static void setText4(String string) {
        text3112 = string;
    }

    public static String getText55() {
        return text3112;
    }

    static {
        long[] lArray = new long[4];
        boolean bl = false;
        MathUtil.setText4(null);
    }

    @Environment(value=EnvType.CLIENT)
    public static enum Type {
      NONE, SPRINT, CUSTOM;

      private Type() {}



        private static Type[] getTypeArray11() {
            return new Type[]{NONE, SPRINT, CUSTOM};
        }
    
   }
}

