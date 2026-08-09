/*
 * Decompiled with CFR 0.152.
 */
package shit.module.render;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import shit.Client;
import shit.event.EventHandler;
import shit.event.PlayerEvent;
import shit.event.Render2DEvent;
import shit.event.RenderLevelEvent;
import shit.manager.FontManager2;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.Friend;
import shit.module.misc.IRC;
import shit.render.ChatClient;
import shit.setting.BooleanSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class NameTags
extends Module {
    public static NameTags INSTANCE;
    private final BooleanSetting ping = (BooleanSetting)this.registerSetting(new BooleanSetting("Ping", true));
    private final BooleanSetting health = (BooleanSetting)this.registerSetting(new BooleanSetting("Health", true));
    private final BooleanSetting pops = (BooleanSetting)this.registerSetting(new BooleanSetting("Pops", true));
    private final BooleanSetting armor = (BooleanSetting)this.registerSetting(new BooleanSetting("Armor", true));
    private final BooleanSetting hands = (BooleanSetting)this.registerSetting(new BooleanSetting("Hands", true));
    private final BooleanSetting onlyVisible = (BooleanSetting)this.registerSetting(new BooleanSetting("OnlyVisible", true));
    private final NumberSetting range = (NumberSetting)this.registerSetting(new NumberSetting("Range", 48.0, 8.0, 128.0, 1.0));
    private final NumberSetting maxScale = (NumberSetting)this.registerSetting(new NumberSetting("MaxScale", 1.0, 0.4, 3.0, 0.05));
    private final NumberSetting minScale = (NumberSetting)this.registerSetting(new NumberSetting("MinScale", 0.55, 0.2, 2.0, 0.05));
    private final DecimalFormat decimalFormat2 = new DecimalFormat("0.0");
    private final Map<java.util.UUID, Integer> map41 = new HashMap<>();
    private Matrix4f matrix4f10;
    private Matrix4f matrix4f5;

    public NameTags() {
        super("NameTags", "Renders projected player nametags with health, ping and equipment.", Category.RENDER);
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        this.map41.clear();
    }

    @EventHandler
    private void setPlayerEvent4(PlayerEvent playerEvent) {
        this.map41.merge(playerEvent.getPlayer4().getUuid(), 1, Integer::sum);
    }

    @EventHandler
    private void setRenderLevelEvent5(RenderLevelEvent renderLevelEvent) {
        this.matrix4f10 = new Matrix4f((Matrix4fc)renderLevelEvent.getMatrix4f3());
        this.matrix4f5 = new Matrix4f((Matrix4fc)renderLevelEvent.getMatrix4f());
    }

    @EventHandler
    private void setObj7(Render2DEvent render2DEvent) {
        if (Module.isNotInGame() || this.matrix4f10 == null || this.matrix4f5 == null) {
            return;
        }
        if (((Boolean)this.onlyVisible.getValue()).booleanValue() && MC.mc.currentScreen != null) {
            return;
        }
        Vec3d vec3d = MC.mc.gameRenderer.getCamera().getCameraPos();
        Matrix4f matrix4f = new Matrix4f((Matrix4fc)this.matrix4f5).mul((Matrix4fc)this.matrix4f10);
        int n = MC.mc.getWindow().getScaledWidth();
        int n2 = MC.mc.getWindow().getScaledHeight();
        for (PlayerEntity playerEntity : MC.mc.world.getPlayers()) {
            int[] nArray;
            if (playerEntity == MC.mc.player || (double)playerEntity.distanceTo((Entity)MC.mc.player) > (Double)this.range.getValue() || (nArray = this.m763(playerEntity, vec3d, matrix4f, n, n2)) == null) continue;
            this.m929(render2DEvent.getDrawContext(), playerEntity, nArray[0], nArray[1], this.m840(playerEntity.distanceTo((Entity)MC.mc.player)));
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean m254(Object object) {
        Entity entity = (Entity)object;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        Object var3_3 = null;
        if (INSTANCE == null) return false;
        if (!INSTANCE.isEnabled()) return false;
        if (!(entity instanceof PlayerEntity)) return false;
        if (entity == minecraftClient.player) return false;
        return true;
    }

    private int[] m763(Object object, Object object2, Object object3, int n, int n2) {
        float f;
        float f2;
        int n3;
        int n4;
        block5: {
            block4: {
                PlayerEntity playerEntity = (PlayerEntity)object;
                Vec3d vec3d = (Vec3d)object2;
                Matrix4f matrix4f = (Matrix4f)object3;
                n4 = n;
                n3 = n2;
                double d = playerEntity.getY() + (double)playerEntity.getHeight() + 0.45;
                Vector4f vector4f = new Vector4f((float)(playerEntity.getX() - vec3d.x), (float)(d - vec3d.y), (float)(playerEntity.getZ() - vec3d.z), 1.0f).mul((Matrix4fc)matrix4f);
                Object var12_13 = null;
                if (vector4f.w() <= 0.05f) {
                    return null;
                }
                f2 = vector4f.x() / vector4f.w();
                f = vector4f.y() / vector4f.w();
                if (Math.abs(f2) > 1.2f) break block4;
                if (!(Math.abs(f) > 1.2f)) break block5;
            }
            return null;
        }
        return new int[]{(int)((f2 * 0.5f + 0.5f) * (float)n4), (int)((1.0f - (f * 0.5f + 0.5f)) * (float)n3)};
    }

    private float m840(float f) {
        float f2 = f;
        float f3 = this.maxScale.getFloat();
        float f4 = Math.min(this.minScale.getFloat(), f3);
        float f5 = f3 / (1.0f + Math.max(0.0f, f2 - 4.0f) * 0.025f);
        return Math.max(f4, Math.min(f3, f5));
    }

    private void m929(Object object, Object object2, int n, int n2, float f) {
        DrawContext drawContext = (DrawContext)object;
        PlayerEntity playerEntity = (PlayerEntity)object2;
        int n3 = n;
        int n4 = n2;
        float f2 = f;
        drawContext.getMatrices().pushMatrix();
        drawContext.getMatrices().scale(f2);
        this.m729(drawContext, playerEntity, Math.round((float)n3 / f2), Math.round((float)n4 / f2));
        drawContext.getMatrices().popMatrix();
    }

    private void m729(Object object, Object object2, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        List list;
        FontManager2 fontManager2;
        int n8;
        int n9;
        PlayerEntity playerEntity;
        DrawContext drawContext;
        block5: {
            block4: {
                drawContext = (DrawContext)object;
                playerEntity = (PlayerEntity)object2;
                n9 = n;
                n8 = n2;
                fontManager2 = Client.fontManager.renderer2();
                list = this.m757(playerEntity);
                n7 = this.m463(fontManager2, list);
                n6 = fontManager2.getFontHeight();
                n5 = Math.max(24, n7 / 2 + 5);
                int n10 = n6 / 2 + 4;
                n4 = n8 - n10;
                n3 = n8 + n10 + 2;
                Object var10_17 = null;
                if (((Boolean)this.armor.getValue()).booleanValue()) break block4;
                if (!((Boolean)this.hands.getValue()).booleanValue()) break block5;
            }
            this.m266(drawContext, playerEntity, n9, n4 - 19);
        }
        drawContext.fill(n9 - n5, n4, n9 + n5, n3, -1441787883);
        drawContext.drawStrokedRectangle(n9 - n5, n4, n5 * 2, n3 - n4, 1713315624);
        int n11 = n3 - 2;
        int n12 = n5 * 2;
        float f = Math.max(0.0f, Math.min(1.0f, playerEntity.getHealth() / playerEntity.getMaxHealth()));
        int n13 = Math.round((float)n12 * f);
        drawContext.fill(n9 - n5, n11, n9 + n5, n3, 0x66000000);
        drawContext.fill(n9 - n5, n11, n9 - n5 + n13, n3, this.m236(playerEntity));
        float f2 = playerEntity.getAbsorptionAmount();
        if (f2 > 0.0f) {
            int n14 = Math.min(n12, Math.round((float)n12 * (f2 / playerEntity.getMaxHealth())));
            drawContext.fill(n9 - n5, n11 - 2, n9 - n5 + n14, n11, -1389536);
        }
        this.m280(drawContext, fontManager2, list, n9 - n7 / 2, n8 - n6 / 2);
    }

    private List m757(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        ArrayList<Data> arrayList = new ArrayList<Data>();
        String string = playerEntity.getName().getString();
        Object var4_5 = null;
        String string2 = IRC.m518(string);
        int n = this.m621(playerEntity);
        if (!string2.isEmpty()) {
            arrayList.add(new Data(string2, n));
        }
        arrayList.add(new Data(string, n));
        if (((Boolean)this.ping.getValue()).booleanValue()) {
            arrayList.add(new Data(" " + this.m811(playerEntity) + "ms", -5592406));
        }
        if (((Boolean)this.health.getValue()).booleanValue()) {
            float f = playerEntity.getHealth() + playerEntity.getAbsorptionAmount();
            arrayList.add(new Data(" " + this.decimalFormat2.format(f), this.m236(playerEntity)));
        }
        if (((Boolean)this.pops.getValue()).booleanValue()) {
            int n2 = this.map41.getOrDefault(playerEntity.getUuid(), 0);
            if (n2 > 0) {
                arrayList.add(new Data(" -" + n2, -43521));
            }
        }
        return arrayList;
    }

    private void m266(Object object, Object object2, int n, int n2) {
        DrawContext drawContext = (DrawContext)object;
        PlayerEntity playerEntity = (PlayerEntity)object2;
        int n3 = n;
        int n4 = n2;
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        Object var10_10 = null;
        if (((Boolean)this.hands.getValue()).booleanValue()) {
            arrayList.add(playerEntity.getStackInHand(Hand.OFF_HAND));
        }
        if (((Boolean)this.armor.getValue()).booleanValue()) {
            arrayList.add(playerEntity.getEquippedStack(EquipmentSlot.HEAD));
            arrayList.add(playerEntity.getEquippedStack(EquipmentSlot.CHEST));
            arrayList.add(playerEntity.getEquippedStack(EquipmentSlot.LEGS));
            arrayList.add(playerEntity.getEquippedStack(EquipmentSlot.FEET));
        }
        if (((Boolean)this.hands.getValue()).booleanValue()) {
            arrayList.add(playerEntity.getStackInHand(Hand.MAIN_HAND));
        }
        arrayList.removeIf(ItemStack::isEmpty);
        if (arrayList.isEmpty()) {
            return;
        }
        int n5 = n3 - arrayList.size() * 9;
        for (ItemStack itemStack : arrayList) {
            drawContext.drawItem(itemStack, n5, n4);
            drawContext.drawStackOverlay(MC.mc.textRenderer, itemStack, n5, n4);
            n5 += 18;
            if (null == null) continue;
        }
    }

    private void m280(Object object, Object object2, Object object3, int n, int n2) {
        DrawContext drawContext = (DrawContext)object;
        FontManager2 fontManager2 = (FontManager2)object2;
        List list = (List)object3;
        int n3 = n;
        int n4 = n2;
        int n5 = 0;
        Iterator iterator = list.iterator();
        Object var12_13 = null;
        while (iterator.hasNext()) {
            Data data = (Data)iterator.next();
            fontManager2.drawText(drawContext, data.text20(), n3 + n5, n4, data.count34(), true);
            n5 += fontManager2.getStringWidth(data.text20());
            if (null == null) continue;
        }
    }

    private int m463(Object object, Object object2) {
        FontManager2 fontManager2 = (FontManager2)object;
        List list = (List)object2;
        int n = 0;
        Iterator iterator = list.iterator();
        Object var6_7 = null;
        while (iterator.hasNext()) {
            Data data = (Data)iterator.next();
            n += fontManager2.getStringWidth(data.text20());
            if (null == null) continue;
        }
        return n;
    }

    private int m811(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        Object var4_3 = null;
        if (MC.mc.getNetworkHandler() == null) {
            return -1;
        }
        PlayerListEntry playerListEntry = MC.mc.getNetworkHandler().getPlayerListEntry(playerEntity.getUuid());
        return playerListEntry == null ? -1 : playerListEntry.getLatency();
    }

    private int m621(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        String string = playerEntity.getName().getString();
        Object var4_4 = null;
        if (ChatClient.m404(string)) {
            return ChatClient.m539(string, -5635841);
        }
        if (Friend.m446(string)) {
            return -6579201;
        }
        if (playerEntity.isInvisible()) {
            return -3618616;
        }
        if (playerEntity.isInSneakingPose()) {
            return -171;
        }
        if (!playerEntity.isAlive()) {
            return -43691;
        }
        return -1;
    }

    private int m236(Object object) {
        PlayerEntity playerEntity = (PlayerEntity)object;
        float f = Math.max(0.0f, Math.min(1.0f, playerEntity.getHealth() / playerEntity.getMaxHealth()));
        int n = (int)((1.0f - f) * 255.0f);
        int n2 = (int)(f * 255.0f);
        return 0xFF000000 | n << 16 | n2 << 8;
    }

    @Environment(value=EnvType.CLIENT)
    record Data(String text20, int count34) {
    }
}

