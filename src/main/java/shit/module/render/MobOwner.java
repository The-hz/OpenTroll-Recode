/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.text.Text;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class MobOwner
extends Module {
    private final BooleanSetting speed = (BooleanSetting)this.m28(new BooleanSetting("Speed", true));
    private final BooleanSetting jump = (BooleanSetting)this.m28(new BooleanSetting("Jump", true));
    private final Map map11 = new HashMap();

    public MobOwner() {
        super("MobOwner", "Displays the owner of tamed mobs.", Category.RENDER);
    }

    @Override
    public void m709() {
        this.m948();
        this.map11.clear();
    }

    @EventHandler
    private void onTick2(Event2.Event2Inner2 event2Inner2) {
        if (Module.isSet37()) {
            return;
        }
        for (Entity entity : MC.client3.world.getEntities()) {
            Tameable tameable;
            block6: {
                block5: {
                    if (!(entity instanceof Tameable)) break block5;
                    tameable = (Tameable)entity;
                    if (this.m282(entity)) break block6;
                }
                this.setObj2(entity);
                continue;
            }
            LivingEntity livingEntity = tameable.getOwner();
            String string = livingEntity != null ? livingEntity.getName().getString() : "Unknown";
            String string2 = this.m316(entity);
            String string3 = string;
            String string4 = "Owner: " + string3 + string2;
            this.map11.computeIfAbsent(entity.getUuid(), uUID -> new Data(entity.getCustomName(), entity.isCustomNameVisible()));
            entity.setCustomName((Text)Text.literal((String)string4));
            entity.setCustomNameVisible(true);
        }
    }

    private boolean m282(Object object) {
        Entity entity = (Entity)object;
        Object var4_3 = null;
        if (entity instanceof TameableEntity) {
            TameableEntity tameableEntity = (TameableEntity)entity;
            return tameableEntity.isTamed();
        }
        if (entity instanceof AbstractHorseEntity) {
            AbstractHorseEntity abstractHorseEntity = (AbstractHorseEntity)entity;
            return abstractHorseEntity.isTame();
        }
        return false;
    }

    private String m316(Object object) {
        AbstractHorseEntity abstractHorseEntity;
        block6: {
            block5: {
                Entity entity = (Entity)object;
                Object var4_3 = null;
                if (!(entity instanceof AbstractHorseEntity)) break block5;
                abstractHorseEntity = (AbstractHorseEntity)entity;
                if (null == null) break block6;
            }
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (((Boolean)this.speed.getObj()).booleanValue()) {
            stringBuilder.append(" S: ").append(this.m717(43.17 * abstractHorseEntity.getAttributeValue(EntityAttributes.MOVEMENT_SPEED)));
        }
        if (((Boolean)this.jump.getObj()).booleanValue()) {
            double d = abstractHorseEntity.getAttributeValue(EntityAttributes.JUMP_STRENGTH);
            double d2 = -0.1817584952 * Math.pow(d, 3.0) + 3.689713992 * Math.pow(d, 2.0) + 2.128599134 * d - 0.343930367;
            stringBuilder.append(" J: ").append(this.m717(d2));
        }
        return stringBuilder.toString();
    }

    private double m717(double d) {
        double d2 = d;
        return (double)Math.round(d2 * 100.0) / 100.0;
    }

    private void m948() {
        Object var2_1 = null;
        if (MC.client3.world == null) {
            return;
        }
        for (Entity entity : MC.client3.world.getEntities()) {
            this.setObj2(entity);
            if (null == null) continue;
        }
    }

    private void setObj2(Object object) {
        Entity entity = (Entity)object;
        Data data = (Data)this.map11.remove(entity.getUuid());
        Object var4_4 = null;
        if (data == null) {
            return;
        }
        entity.setCustomName(data.field4());
        entity.setCustomNameVisible(data.flag7());
    }

    @Environment(value=EnvType.CLIENT)
    record Data(Text field4, boolean flag7) {
    }
}

