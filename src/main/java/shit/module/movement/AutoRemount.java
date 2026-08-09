/*
 * Decompiled with CFR 0.152.
 */
package shit.module.movement;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class AutoRemount
extends Module {
    private final NumberSetting range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 5.0, 1.0, 10.0, 0.5));
    private final BooleanSetting onlyLast = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyLast", true));
    private Entity entity2;

    public AutoRemount() {
        super("AutoRemount", "Automatically remounts nearby vehicles.", Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        this.entity2 = null;
    }

    @EventHandler
    private void setEvent2Inner226(Event2.Event2Inner2 event2Inner2) {
        if (Module.isNotInGame()) {
            return;
        }
        if (MC.mc.player.hasVehicle()) {
            this.entity2 = MC.mc.player.getVehicle();
            return;
        }
        Entity entity = null;
        if (((Boolean)this.onlyLast.getValue()).booleanValue() && this.entity2 != null && this.entity2.isAlive() && (double)MC.mc.player.distanceTo(this.entity2) <= (Double)this.range.getValue()) {
            entity = this.entity2;
        } else if (!((Boolean)this.onlyLast.getValue()).booleanValue()) {
            for (Entity entity2 : MC.mc.world.getEntities()) {
                if (entity2 == MC.mc.player || !entity2.hasPassengers() || (double)MC.mc.player.distanceTo(entity2) > (Double)this.range.getValue()) continue;
                entity = entity2;
                break;
            }
        }
        if (entity != null) {
            MC.mc.interactionManager.interactEntity((PlayerEntity)MC.mc.player, entity, Hand.MAIN_HAND);
        }
    }
}

