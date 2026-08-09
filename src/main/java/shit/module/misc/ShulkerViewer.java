/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import java.util.Iterator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import shit.Client;
import shit.event.EventHandler;
import shit.event.Render2DEvent;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class ShulkerViewer
extends Module {
    public static ShulkerViewer INSTANCE;
    private final NumberSetting range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 8.0, 2.0, 32.0, 0.5));

    public ShulkerViewer() {
        super("ShulkerViewer", "Previews the contents of shulker boxes held by nearby players.", Category.MISC);
        INSTANCE = this;
    }

    @EventHandler
    private void setObj111(Render2DEvent render2DEvent) {
        if (Module.isNotInGame()) {
            return;
        }
        int n = 5;
        int n2 = MC.mc.getWindow().getScaledHeight() / 2;
        for (PlayerEntity playerEntity : MC.mc.world.getPlayers()) {
            ContainerComponent containerComponent;
            ItemStack itemStack;
            if (playerEntity == MC.mc.player || (double)playerEntity.distanceTo((Entity)MC.mc.player) > (Double)this.range.getValue() || (itemStack = this.m131(playerEntity)) == null || (containerComponent = (ContainerComponent)itemStack.get(DataComponentTypes.CONTAINER)) == null) continue;
            Client.fontManager.renderer2().drawText(render2DEvent.getDrawContext(), playerEntity.getName().getString() + "'s Shulker:", n, n2, -1, true);
            n2 += 9;
            for (ItemStack itemStack2 : containerComponent.iterateNonEmpty()) {
                String string = "  " + itemStack2.getCount() + "x " + itemStack2.getName().getString();
                Client.fontManager.renderer2().drawText(render2DEvent.getDrawContext(), string, n, n2, -5592406, true);
                n2 += 9;
            }
            n2 += 4;
        }
    }

    private ItemStack m131(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        ItemStack itemStack = playerEntity.getStackInHand(Hand.MAIN_HAND);
        if (this.m297(itemStack)) {
            return itemStack;
        }
        ItemStack itemStack2 = playerEntity.getStackInHand(Hand.OFF_HAND);
        if (this.m297(itemStack2)) {
            return itemStack2;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m297(Object object) {
        Item item;
        ItemStack itemStack = (ItemStack)object;
        String string = IRC.getText7();
        ItemStack itemStack2 = itemStack;
        if (string != null) {
            if (itemStack2.isEmpty()) return false;
            itemStack2 = itemStack;
        }
        Item item2 = item = itemStack2.getItem();
        if (string != null) {
            if (!(item2 instanceof BlockItem)) return false;
            item2 = item;
        }
        BlockItem blockItem = (BlockItem)item2;
        boolean bl = blockItem.getBlock() instanceof ShulkerBoxBlock;
        if (string == null) return bl;
        if (!bl) return false;
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m364(Object object, Object object2, int n, int n2) {
        int n3;
        DrawContext drawContext = (DrawContext)object;
        ItemStack itemStack = (ItemStack)object2;
        int n4 = n;
        int n5 = n2;
        ShulkerViewer shulkerViewer = INSTANCE;
        String string = IRC.getText7();
        ShulkerViewer shulkerViewer2 = shulkerViewer;
        if (string != null) {
            if (shulkerViewer2 == null) return false;
            shulkerViewer2 = shulkerViewer;
        }
        boolean bl = shulkerViewer2.isEnabled();
        if (string == null) return bl;
        if (!bl) return false;
        Object object3 = shulkerViewer;
        if (string != null) {
            if (!((ShulkerViewer)object3).m297(itemStack)) {
                return false;
            }
            object3 = itemStack.get(DataComponentTypes.CONTAINER);
        }
        ContainerComponent containerComponent = (ContainerComponent)object3;
        int n6 = n4 + 12;
        int n7 = n5 + 12;
        int n8 = 170;
        int n9 = 61;
        drawContext.fill(n6 - 3, n7 - 13, n6 + n8, n7 + n9, -535818224);
        drawContext.drawStrokedRectangle(n6 - 3, n7 - 13, n8 + 3, n9 + 13, -8367873);
        Client.fontManager.renderer2().drawText(drawContext, itemStack.getName().getString(), n6, n7 - 10, -1, true);
        int n10 = 0;
        ContainerComponent containerComponent2 = containerComponent;
        if (string != null) {
            if (containerComponent2 == null) return 1 != 0;
            containerComponent2 = containerComponent;
        }
        Iterator iterator = containerComponent2.iterateNonEmpty().iterator();
        do {
            if (!iterator.hasNext()) return 1 != 0;
            ItemStack itemStack2 = (ItemStack)iterator.next();
            int n11 = n6 + n10 % 9 * 18;
            int n12 = n7 + n10 / 9 * 18;
            drawContext.drawItem(itemStack2, n11, n12);
            drawContext.drawStackOverlay(MC.mc.textRenderer, itemStack2, n11, n12);
            n3 = ++n10;
            if (string == null) return n3 != 0;
        } while (n3 < 27 && string != null);
        return 1 != 0;
    }
}

