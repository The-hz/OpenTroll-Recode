/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import shit.module.hud.AbstractHudModule;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class EntityList
extends AbstractHudModule {
    private final NumberSetting numberSetting4 = (NumberSetting)this.registerSetting(new NumberSetting("Range", 64.0, 8.0, 512.0, 1.0));
    private final NumberSetting numberSetting = (NumberSetting)this.registerSetting(new NumberSetting("MaxEntries", 8.0, 1.0, 32.0, 1.0));

    public EntityList() {
        super("EntityList", "Lists nearby entities.", 6, 234);
    }

    /*
     * Unable to fully structure code
     */
    @Override
    protected List lines() {
        if (MC.mc.player == null) {
            return List.of("EntityList N/A");
        }
        if (MC.mc.world == null) {
            return List.of("EntityList N/A");
        }
        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
        for (Entity entity : MC.mc.world.getEntities()) {
            if (entity == MC.mc.player) continue;
            if (entity.distanceTo(MC.mc.player) > this.numberSetting4.getFloat()) continue;
            String name = this.m735(entity);
            int count = entity instanceof ItemEntity ? ((ItemEntity)entity).getStack().getCount() : 1;
            treeMap.merge(name, count, Integer::sum);
        }
        ArrayList<String> list = new ArrayList<String>();
        int index = 0;
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            if (index++ >= this.numberSetting.getInt()) break;
            list.add(entry.getKey() + " x" + entry.getValue());
        }
        int more = treeMap.size() - list.size();
        if (more > 0) {
            list.add("...and " + more + " more");
        }
        return list.isEmpty() ? List.of("EntityList Empty") : list;
    }

    private String m735(Object object) {
        Entity entity = (Entity)object;
        boolean bl = true;
        if (entity instanceof ItemEntity) {
            ItemEntity itemEntity = (ItemEntity)entity;
            return itemEntity.getStack().getName().getString();
        }
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            return livingEntity.getName().getString();
        }
        return entity.getType().getName().getString();
    }
}

