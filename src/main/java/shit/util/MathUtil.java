/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffects;
import shit.util.MC;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public class MathUtil
implements MC {
    /*
     * Enabled aggressive block sorting
     */
    public static boolean isMoving() {
        float f;
        boolean bl = Util2.isAlwaysTrue();
        ClientPlayerEntity clientPlayerEntity = MC.mc.player;
        if (!bl) {
            if (clientPlayerEntity == null) {
                return false;
            }
            clientPlayerEntity = MC.mc.player;
        }
        float f2 = (f = clientPlayerEntity.sidewaysSpeed - 0.0f) == 0.0f ? 0 : (f > 0.0f ? 1 : -1);
        if (bl) return f2 != 0;
        if (f2 == 0) {
            float f3 = MC.mc.player.forwardSpeed - 0.0f;
            f2 = f3 == 0.0f ? 0 : (f3 > 0.0f ? 1 : -1);
            if (bl) return f2 != 0;
            if (f2 == 0) {
                f2 = 0.0f;
                return f2 != 0;
            }
        }
        f2 = 1.0f;
        return f2 != 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean isMovingHorizontally() {
        boolean bl = false;
        ClientPlayerEntity clientPlayerEntity = MC.mc.player;
        double d = clientPlayerEntity.getVelocity().x - 0.0;
        double d2 = d == 0.0 ? 0 : (d > 0.0 ? 1 : -1);
        return d2 != 0;
    }

    public static double[] getMotionVector(double d) {
        double d2 = d;
        boolean bl = false;
        ClientPlayerEntity clientPlayerEntity = MC.mc.player;
        if (false) {
            if (clientPlayerEntity == null) {
                return new double[]{0.0, 0.0};
            }
            clientPlayerEntity = MC.mc.player;
        }
        float f = clientPlayerEntity.forwardSpeed;
        float f2 = MC.mc.player.sidewaysSpeed;
        return MathUtil.m381(d2, f, f2);
    }

    private static double[] m381(double d, float f, float f2) {
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        double d2;
        block12: {
            block13: {
                float f8;
                block18: {
                    boolean bl;
                    block19: {
                        block17: {
                            block16: {
                                block14: {
                                    float f9;
                                    block15: {
                                        d2 = d;
                                        f7 = f;
                                        f6 = f2;
                                        bl = Util2.isAlwaysTrue();
                                        ClientPlayerEntity clientPlayerEntity = MC.mc.player;
                                        if (!bl) {
                                            if (clientPlayerEntity == null) {
                                                return new double[]{0.0, 0.0};
                                            }
                                            clientPlayerEntity = MC.mc.player;
                                        }
                                        f5 = clientPlayerEntity.getYaw();
                                        f4 = f7;
                                        f3 = 0.0f;
                                        if (bl) break block12;
                                        if (f4 == f3) break block13;
                                        float f10 = f6 - 0.0f;
                                        f8 = f10 == 0.0f ? 0 : (f10 > 0.0f ? 1 : -1);
                                        if (bl) break block14;
                                        if (f8 <= 0) break block15;
                                        float f11 = f7;
                                        if (!bl) {
                                            f11 = f11 > 0.0f ? -45.0f : 45.0f;
                                        }
                                        f5 += f11;
                                        if (!bl) break block16;
                                    }
                                    f8 = (f9 = f6 - 0.0f) == 0.0f ? 0 : (f9 < 0.0f ? -1 : 1);
                                }
                                if (bl) break block17;
                                if (f8 < 0) {
                                    float f12 = f7;
                                    if (!bl) {
                                        f12 = f12 > 0.0f ? 45.0f : -45.0f;
                                    }
                                    f5 += f12;
                                }
                            }
                            f6 = 0.0f;
                            float f13 = f7 - 0.0f;
                            f8 = f13 == 0.0f ? 0 : (f13 > 0.0f ? 1 : -1);
                        }
                        if (bl) break block18;
                        if (f8 <= 0) break block19;
                        f7 = 1.0f;
                        if (!bl) break block13;
                    }
                    f4 = f7;
                    f3 = 0.0f;
                    if (bl) break block12;
                    float f14 = f4 - f3;
                    f8 = f14 == 0.0f ? 0 : (f14 < 0.0f ? -1 : 1);
                }
                if (f8 < 0) {
                    f7 = -1.0f;
                }
            }
            f4 = f5;
            f3 = 90.0f;
        }
        double d3 = Math.sin(Math.toRadians(f4 + f3));
        double d4 = Math.cos(Math.toRadians(f5 + 90.0f));
        double d5 = (double)f7 * d2 * d4 + (double)f6 * d2 * d3;
        double d6 = (double)f7 * d2 * d3 - (double)f6 * d2 * d4;
        return new double[]{d5, d6};
    }

    public static void setMotionX(double d) {
        block3: {
            ClientPlayerEntity clientPlayerEntity;
            double d2;
            block2: {
                d2 = d;
                boolean bl = Util2.isAlwaysTrue();
                clientPlayerEntity = MC.mc.player;
                if (bl) break block2;
                if (clientPlayerEntity == null) break block3;
                clientPlayerEntity = MC.mc.player;
            }
            clientPlayerEntity.setVelocity(d2, MC.mc.player.getVelocity().y, MC.mc.player.getVelocity().z);
        }
    }

    public static void setMotionY(double d) {
        block3: {
            ClientPlayerEntity clientPlayerEntity;
            double d2;
            block2: {
                d2 = d;
                boolean bl = false;
                clientPlayerEntity = MC.mc.player;
                if (!false) break block2;
                if (clientPlayerEntity == null) break block3;
                clientPlayerEntity = MC.mc.player;
            }
            clientPlayerEntity.setVelocity(MC.mc.player.getVelocity().x, d2, MC.mc.player.getVelocity().z);
        }
    }

    public static void setMotionZ(double d) {
        block3: {
            ClientPlayerEntity clientPlayerEntity;
            double d2;
            block2: {
                d2 = d;
                boolean bl = false;
                clientPlayerEntity = MC.mc.player;
                if (!false) break block2;
                if (clientPlayerEntity == null) break block3;
                clientPlayerEntity = MC.mc.player;
            }
            clientPlayerEntity.setVelocity(MC.mc.player.getVelocity().x, MC.mc.player.getVelocity().y, d2);
        }
    }

    public static double applySpeedModifiers(boolean bl, double d) {
        boolean bl2;
        double d2;
        block9: {
            block10: {
                int n;
                boolean bl3 = bl;
                d2 = d;
                boolean bl4 = Util2.isAlwaysTrue();
                ClientPlayerEntity clientPlayerEntity = MC.mc.player;
                if (!bl4) {
                    if (clientPlayerEntity == null) {
                        return d2;
                    }
                    clientPlayerEntity = MC.mc.player;
                }
                bl2 = clientPlayerEntity.hasStatusEffect(StatusEffects.SPEED);
                if (!bl4) {
                    if (bl2) {
                        n = MC.mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier();
                        d2 *= 1.0 + 0.2 * (double)(n + 1);
                    }
                    bl2 = bl3;
                }
                if (bl4) break block9;
                if (!bl2) break block10;
                bl2 = MC.mc.player.hasStatusEffect(StatusEffects.SLOWNESS);
                if (bl4) break block9;
                if (bl2) {
                    n = MC.mc.player.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier();
                    d2 /= 1.0 + 0.2 * (double)(n + 1);
                }
            }
            bl2 = MC.mc.player.isInSneakingPose();
        }
        if (bl2) {
            d2 /= 5.0;
        }
        return d2;
    }
}

