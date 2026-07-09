/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.EndCrystalEntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class CrystalChams
extends Module {
    public static CrystalChams INSTANCE;
    private final NumberSetting scale = (NumberSetting)this.m28(new NumberSetting("Scale", 1.0, 0.1, 4.0, 0.1));
    private final BooleanSetting cancelVanilla = (BooleanSetting)this.m28(new BooleanSetting("CancelVanilla", true));
    private final NumberSetting modelAlpha = (NumberSetting)this.m28(new NumberSetting("ModelAlpha", 255.0, 0.0, 255.0, 1.0));
    private final BooleanSetting filled = (BooleanSetting)this.m28(new BooleanSetting("Filled", true));
    private final BooleanSetting filledDepth = (BooleanSetting)this.m28(new BooleanSetting("FilledDepth", true));
    private final BooleanSetting outline = (BooleanSetting)this.m28(new BooleanSetting("Outline", true));
    private final BooleanSetting outlineDepth = (BooleanSetting)this.m28(new BooleanSetting("OutlineDepth", false));
    private final ColorSetting filledColor = (ColorSetting)this.m28(new ColorSetting("FilledColor", 1065746376));
    private final ColorSetting outlineColor = (ColorSetting)this.m28(new ColorSetting("OutlineColor", -930742328));
    private final NumberSetting lineWidth = (NumberSetting)this.m28(new NumberSetting("LineWidth", 2.0, 0.25, 8.0, 0.25));
    private final BooleanSetting throughWall = (BooleanSetting)this.m28(new BooleanSetting("ThroughWall", true));
    private final NumberSetting range = (NumberSetting)this.m28(new NumberSetting("Range", 16.0, 0.0, 64.0, 0.5));
    private final Set set4 = Collections.newSetFromMap(new WeakHashMap());
    private final Map map16 = Collections.synchronizedMap(new WeakHashMap());

    public CrystalChams() {
        super("CrystalChams", "Renders custom chams for end crystals.", Category.COMBAT);
        INSTANCE = this;
    }

    public static boolean m7(Object object) {
        EndCrystalEntity cfr_ignored_0 = (EndCrystalEntity)object;
        return false;
    }

    public boolean m866(Object object) {
        EndCrystalEntity endCrystalEntity;
        block5: {
            block4: {
                endCrystalEntity = (EndCrystalEntity)object;
                Object var4_3 = null;
                if (!this.isSet19()) break block4;
                if (!Module.isSet37()) break block5;
            }
            return false;
        }
        if (!endCrystalEntity.isAlive()) {
            return false;
        }
        double d = (Double)this.range.getObj() * (Double)this.range.getObj();
        return endCrystalEntity.squaredDistanceTo((Entity)MC.client3.player) <= d;
    }

    public void m938(Object object, Object object2) {
        block7: {
            EndCrystalEntityRenderState endCrystalEntityRenderState;
            block6: {
                EndCrystalEntity endCrystalEntity;
                block5: {
                    block4: {
                        endCrystalEntity = (EndCrystalEntity)object;
                        Object object3 = object2;
                        Object var6_5 = null;
                        if (!(object3 instanceof EndCrystalEntityRenderState)) break block4;
                        endCrystalEntityRenderState = (EndCrystalEntityRenderState)object3;
                        if (null == null) break block5;
                    }
                    return;
                }
                if (!this.m866(endCrystalEntity)) break block6;
                this.set4.add(endCrystalEntityRenderState);
                if (null == null) break block7;
            }
            this.set4.remove(endCrystalEntityRenderState);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m177(Object object) {
        Object object2 = object;
        Object var4_3 = null;
        if (!(object2 instanceof EndCrystalEntityRenderState)) return false;
        EndCrystalEntityRenderState endCrystalEntityRenderState = (EndCrystalEntityRenderState)object2;
        if (!this.set4.contains(endCrystalEntityRenderState)) return false;
        return true;
    }

    public void m411(Object object, Object object2) {
        Object object3 = object;
        Object object4 = object2;
        Object var6_5 = null;
        if (object3 instanceof EndCrystalEntityRenderState) {
            EndCrystalEntityRenderState endCrystalEntityRenderState = (EndCrystalEntityRenderState)object3;
            this.map16.put(endCrystalEntityRenderState, object4);
        }
    }

    public boolean m483(Object object, Object object2) {
        EndCrystalEntityRenderState endCrystalEntityRenderState;
        Object object3 = object;
        Object object4 = object2;
        Object var6_5 = null;
        return object3 instanceof EndCrystalEntityRenderState && this.map16.get(endCrystalEntityRenderState = (EndCrystalEntityRenderState)object3) == object4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet31() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.outline.getObj() == false) return false;
        return true;
    }

    public int getInt55() {
        Object var2_1 = null;
        return (Boolean)this.filled.getObj() != false ? (Integer)this.filledColor.getObj() : (Integer)this.filledColor.getObj() & 0xFFFFFF;
    }

    public int getInt3() {
        Object var2_1 = null;
        return (Boolean)this.outline.getObj() != false ? (Integer)this.outlineColor.getObj() : 0;
    }

    public float getFloat9() {
        return this.scale.getFloat35();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSet55() {
        Object var2_1 = null;
        if (!this.isSet19()) return false;
        if ((Boolean)this.filled.getObj() == false) return false;
        return true;
    }

    public float getFloat45() {
        return this.lineWidth.getFloat35();
    }

    public boolean isSet83() {
        return (Boolean)this.throughWall.getObj();
    }

    public boolean isSet47() {
        return (Boolean)this.cancelVanilla.getObj();
    }

    public int getInt65() {
        return this.modelAlpha.getInt50() << 24 | 0xFFFFFF;
    }
}

