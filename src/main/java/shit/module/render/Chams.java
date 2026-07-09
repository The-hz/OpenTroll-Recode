/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Chams
extends Module {
    public static Chams INSTANCE;
    public final BooleanSetting throughWall = (BooleanSetting)this.m28(new BooleanSetting("ThroughWall", true));
    private final BooleanSetting cancelVanilla = (BooleanSetting)this.m28(new BooleanSetting("CancelVanilla", false));
    private final NumberSetting modelAlpha = (NumberSetting)this.m28(new NumberSetting("ModelAlpha", 255.0, 0.0, 255.0, 1.0));
    private final BooleanSetting players = (BooleanSetting)this.m28(new BooleanSetting("Players", true));
    private final BooleanSetting mobs = (BooleanSetting)this.m28(new BooleanSetting("Mobs", true));
    private final BooleanSetting animals = (BooleanSetting)this.m28(new BooleanSetting("Animals", true));
    private final BooleanSetting slimes = (BooleanSetting)this.m28(new BooleanSetting("Slimes", true));
    private final BooleanSetting filled = (BooleanSetting)this.m28(new BooleanSetting("Filled", true));
    private final BooleanSetting outline = (BooleanSetting)this.m28(new BooleanSetting("Outline", true));
    private final NumberSetting lineWidth = (NumberSetting)this.m28(new NumberSetting("LineWidth", 1.5, 0.5, 8.0, 0.1));
    public final ColorSetting color = (ColorSetting)this.m28(new ColorSetting("Color", -1426063361));
    public final ColorSetting fillColor = (ColorSetting)this.m28(new ColorSetting("FillColor", 0x40FFFFFF));
    public final ColorSetting outlineColor = (ColorSetting)this.m28(new ColorSetting("OutlineColor", -855638017));
    public final ColorSetting hand = (ColorSetting)this.m28(new ColorSetting("Hand", -1));
    private final Map map49 = Collections.synchronizedMap(new WeakHashMap());
    private final Map map9 = Collections.synchronizedMap(new WeakHashMap());

    public Chams() {
        super("Chams", "Renders entity chams without crystal support.", Category.RENDER);
        INSTANCE = this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m720(Object object) {
        Entity entity = (Entity)object;
        Object var4_3 = null;
        if (!this.isSet19()) return false;
        if (Module.isSet37()) {
            return false;
        }
        if (entity instanceof EndCrystalEntity) {
            return false;
        }
        if (entity == MC.client3.player) {
            return false;
        }
        if (entity instanceof SlimeEntity) {
            return (Boolean)this.slimes.getObj();
        }
        if (entity instanceof PlayerEntity) {
            return (Boolean)this.players.getObj();
        }
        if (entity instanceof AnimalEntity) {
            return (Boolean)this.animals.getObj();
        }
        if (!(entity instanceof MobEntity)) return false;
        if ((Boolean)this.mobs.getObj() == false) return false;
        return true;
    }

    public void m295(Object object, Object object2) {
        block7: {
            LivingEntityRenderState livingEntityRenderState;
            block6: {
                Entity entity;
                block5: {
                    block4: {
                        entity = (Entity)object;
                        Object object3 = object2;
                        Object var6_5 = null;
                        if (!(object3 instanceof LivingEntityRenderState)) break block4;
                        livingEntityRenderState = (LivingEntityRenderState)object3;
                        if (null == null) break block5;
                    }
                    return;
                }
                if (!this.m720(entity)) break block6;
                this.map49.put(livingEntityRenderState, (Integer)this.color.getObj());
                if (null == null) break block7;
            }
            this.map49.remove(livingEntityRenderState);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m681(Object object) {
        Object object2 = object;
        Object var4_3 = null;
        if (!(object2 instanceof LivingEntityRenderState)) return false;
        LivingEntityRenderState livingEntityRenderState = (LivingEntityRenderState)object2;
        if (!this.map49.containsKey(livingEntityRenderState)) return false;
        return true;
    }

    public void m249(Object object, Object object2) {
        Object object3 = object;
        Object object4 = object2;
        Object var6_5 = null;
        if (object3 instanceof LivingEntityRenderState) {
            LivingEntityRenderState livingEntityRenderState = (LivingEntityRenderState)object3;
            this.map9.put(livingEntityRenderState, object4);
        }
    }

    public boolean m703(Object object, Object object2) {
        LivingEntityRenderState livingEntityRenderState;
        Object object3 = object;
        Object object4 = object2;
        Object var6_5 = null;
        return object3 instanceof LivingEntityRenderState && this.map9.get(livingEntityRenderState = (LivingEntityRenderState)object3) == object4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet15() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.filled.getObj() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet96() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.outline.getObj() == false) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet171() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.cancelVanilla.getObj() == false) return false;
        return true;
    }

    public int getInt4() {
        return this.modelAlpha.getInt50() << 24 | 0xFFFFFF;
    }

    public int getInt30() {
        return (Integer)this.fillColor.getObj();
    }

    public int getInt83() {
        return (Integer)this.outlineColor.getObj();
    }

    public float getFloat60() {
        return this.lineWidth.getFloat35();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet155() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if (Module.isSet37()) return false;
        if ((Integer)this.hand.getObj() == -1) return false;
        return true;
    }

    public int getInt11() {
        return (Integer)this.hand.getObj();
    }
}

