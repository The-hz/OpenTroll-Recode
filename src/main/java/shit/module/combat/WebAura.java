/*
 * Decompiled with CFR 0.152.
 */
package shit.module.combat;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import shit.Client;
import shit.event.TickEvent;
import shit.event.EventHandler;
import shit.misc.Stopwatch;
import shit.module.Category;
import shit.module.Module;
import shit.module.client.ClientSetting;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.BlockUtil;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class WebAura
extends Module {
    public static WebAura INSTANCE;
    private final NumberSetting placeDelay = (NumberSetting)this.registerSetting(new NumberSetting("PlaceDelay", 50.0, 0.0, 500.0, 1.0));
    private final NumberSetting blocksPer = (NumberSetting)this.registerSetting(new NumberSetting("BlocksPer", 2.0, 1.0, 10.0, 1.0));
    private final NumberSetting predictTicks = (NumberSetting)this.registerSetting(new NumberSetting("PredictTicks", 2.0, 0.0, 50.0, 1.0));
    private final NumberSetting maxWebs = (NumberSetting)this.registerSetting(new NumberSetting("MaxWebs", 2.0, 1.0, 8.0, 1.0));
    private final NumberSetting offset = (NumberSetting)this.registerSetting(new NumberSetting("Offset", 0.25, 0.0, 0.3, 0.01));
    private final NumberSetting placeRange = (NumberSetting)this.registerSetting(new NumberSetting("PlaceRange", 5.0, 0.5, 6.0, 0.1));
    private final NumberSetting targetRange = (NumberSetting)this.registerSetting(new NumberSetting("TargetRange", 8.0, 0.5, 8.0, 0.1));
    private final BooleanSetting feet = (BooleanSetting)this.registerSetting(new BooleanSetting("Feet", true));
    private final BooleanSetting feetExtend = (BooleanSetting)this.registerSetting(new BooleanSetting("FeetExtend", true));
    private final BooleanSetting face = (BooleanSetting)this.registerSetting(new BooleanSetting("Face", true));
    private final BooleanSetting down = (BooleanSetting)this.registerSetting(new BooleanSetting("Down", true));
    private final BooleanSetting usingPause = (BooleanSetting)this.registerSetting(new BooleanSetting("UsingPause", true));
    private final EnumSetting rotateMode = (EnumSetting)this.registerSetting(new EnumSetting("RotateMode", RotateMode.DEFAULT));
    private final EnumSetting switchMode = (EnumSetting)this.registerSetting(new EnumSetting("SwitchMode", SwitchMode.DEFAULT));
    private final EnumSetting timing = (EnumSetting)this.registerSetting(new EnumSetting("Timing", TimingMode.ALL));
    private final Stopwatch helper734 = new Stopwatch();
    private final List list36 = new ArrayList();
    private int count183;

    public WebAura() {
        super("WebAura", "Places cobwebs on nearby enemies.", Category.COMBAT);
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        this.list36.clear();
        this.count183 = 0;
    }

    @EventHandler
    private void setEvent2Inner14(TickEvent.PreTick event2Inner) {
        if (this.timing.getValue() == TimingMode.POST) {
            return;
        }
        this.m876();
    }

    @EventHandler
    private void setEvent2Inner229(TickEvent.PostTick event2Inner2) {
        if (this.timing.getValue() == TimingMode.PRE) {
            return;
        }
        this.m876();
    }

    private void m876() {
        Object var2_1 = null;
        if (Module.isNotInGame()) {
            return;
        }
        if (((Boolean)this.usingPause.getValue()).booleanValue()) {
            if (MC.mc.player.isUsingItem()) {
                return;
            }
        }
        if (!this.helper734.hasPassedMs((Double)this.placeDelay.getValue())) {
            return;
        }
        if (this.getInt67() == -1) {
            return;
        }
        this.list36.clear();
        this.count183 = 0;
        double cfr_ignored_0 = (Double)this.targetRange.getValue() * (Double)this.targetRange.getValue();
        block0: for (PlayerEntity playerEntity : MC.mc.world.getPlayers()) {
            Object object;
            float f;
            int n;
            int n2;
            float[] fArray;
            if (playerEntity == MC.mc.player) continue;
            if (!playerEntity.isAlive()) continue;
            if (playerEntity.isSpectator() || Client.friendManager.isFriend(playerEntity.getName().getString())) continue;
            Vec3d vec3d = (Double)this.predictTicks.getValue() > 0.0 ? playerEntity.getEntityPos().add(playerEntity.getVelocity().multiply(((Double)this.predictTicks.getValue()).doubleValue())) : playerEntity.getEntityPos();
            double d = vec3d.x;
            double d2 = vec3d.y;
            double d3 = vec3d.z;
            int n3 = 0;
            float f2 = this.offset.getFloat();
            for (float f3 : new float[]{0.0f, f2, -f2}) {
                fArray = new float[]{0.0f, f2, -f2};
                n2 = fArray.length;
                for (n = 0; n < n2; ++n) {
                    f = fArray[n];
                    object = new float[]{0.0f, 1.0f, -1.0f};
                    int n4 = ((float[])object).length;
                    for (int i = 0; i < n4; ++i) {
                        float f4 = ((float[])object)[i];
                        BlockPos blockPos = BlockPos.ofFloored((double)(d + (double)f3), (double)(d2 + (double)f4), (double)(d3 + (double)f));
                        if (!this.m654(blockPos, playerEntity) || !MC.mc.world.getBlockState(blockPos).isOf(Blocks.COBWEB)) continue;
                        ++n3;
                        if (null == null) continue;
                    }
                    if (null == null) continue;
                }
                if (null == null) continue;
                break;
            }
            if (((Boolean)this.feet.getValue()).booleanValue()) {
                if (this.m753(BlockPos.ofFloored((double)d, (double)d2, (double)d3), playerEntity)) {
                    ++n3;
                }
            }
            if (((Boolean)this.down.getValue()).booleanValue()) {
                this.m753(BlockPos.ofFloored((double)d, (double)(d2 - 0.8), (double)d3), playerEntity);
            }
            if (n3 < this.maxWebs.getInt()) {
                if (((Boolean)this.feetExtend.getValue()).booleanValue()) {
                    for (float f3 : new float[]{0.0f, f2, -f2}) {
                        fArray = new float[]{0.0f, f2, -f2};
                        n2 = fArray.length;
                        for (n = 0; n < n2; ++n) {
                            f = fArray[n];
                            if (f3 == 0.0f && f == 0.0f) continue;
                            object = BlockPos.ofFloored((double)(d + (double)f3), (double)d2, (double)(d3 + (double)f));
                            if (!this.m654(object, playerEntity)) continue;
                            if (!this.m753(object, playerEntity)) continue;
                            if (++n3 >= this.maxWebs.getInt()) continue block0;
                            if (null == null) continue;
                        }
                        if (null == null) continue;
                        break;
                    }
                }
                if (((Boolean)this.face.getValue()).booleanValue()) {
                    for (float f3 : new float[]{0.0f, f2, -f2}) {
                        fArray = new float[]{0.0f, f2, -f2};
                        n2 = fArray.length;
                        for (n = 0; n < n2; ++n) {
                            f = fArray[n];
                            object = BlockPos.ofFloored((double)(d + (double)f3), (double)(d2 + 1.1), (double)(d3 + (double)f));
                            if (!this.m654(object, playerEntity)) continue;
                            if (!this.m753(object, playerEntity)) continue;
                            if (++n3 >= this.maxWebs.getInt()) continue block0;
                            if (null == null) continue;
                        }
                        if (null == null) continue;
                    }
                }
            }
            if (null == null) continue;
        }
    }

    private boolean m753(Object object, Object object2) {
        boolean bl;
        block5: {
            BlockPos blockPos = (BlockPos)object;
            PlayerEntity playerEntity = (PlayerEntity)object2;
            Object var6_5 = null;
            if (this.list36.contains(blockPos)) {
                return false;
            }
            this.list36.add(blockPos);
            if (this.count183 >= this.blocksPer.getInt()) {
                return false;
            }
            if (!BlockUtil.m57(blockPos)) {
                return false;
            }
            if (!this.m654(blockPos, playerEntity)) {
                return false;
            }
            if (MC.mc.player.getEyePos().distanceTo(Vec3d.ofCenter((Vec3i)blockPos)) > (Double)this.placeRange.getValue() + 1.5) {
                return false;
            }
            bl = BlockUtil.m1051(blockPos, (java.util.function.Predicate<ItemStack>)this::m836, (Object)this.getRotateMode(), this.getFloat26(), (Object)this.getSwitchMode9(), (Double)this.placeRange.getValue());
            if (!bl) break block5;
            ++this.count183;
            this.helper734.resetTimer();
        }
        return bl;
    }

    private boolean m654(Object object, Object object2) {
        BlockPos blockPos = (BlockPos)object;
        PlayerEntity playerEntity = (PlayerEntity)object2;
        return new Box(blockPos).intersects(playerEntity.getBoundingBox());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean m836(ItemStack itemStack) {
        Object var2_2 = null;
        if (itemStack.getItem() == Items.COBWEB) return true;
        Item item = itemStack.getItem();
        if (!(item instanceof BlockItem)) return false;
        BlockItem blockItem = (BlockItem)item;
        if (blockItem.getBlock() != Blocks.COBWEB) return false;
        return true;
    }

    private int getInt67() {
        int n;
        Object var2_2 = null;
        for (n = 0; n < 9; ++n) {
            if (!this.m836(MC.mc.player.getInventory().getStack(n))) continue;
            return n;
        }
        for (n = 9; n < 36; ++n) {
            if (!this.m836(MC.mc.player.getInventory().getStack(n))) continue;
            return n;
        }
        return -1;
    }

    private ClientSetting.RotateMode getRotateMode() {
        Object var2_1 = null;
        if (this.rotateMode.getValue() == RotateMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.RotateMode)((Object)ClientSetting.INSTANCE.rotateMode.getValue()) : ClientSetting.RotateMode.ONTICK;
        }
        return switch (((RotateMode)((Object)this.rotateMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.RotateMode.NONE;
            case 2 -> ClientSetting.RotateMode.SMOOTH;
            case 3 -> ClientSetting.RotateMode.ONTICK;
            case 4 -> ClientSetting.RotateMode.rotateMode;
            default -> ClientSetting.RotateMode.NONE;
        };
    }

    private float getFloat26() {
        Object var2_1 = null;
        return ClientSetting.INSTANCE != null ? ClientSetting.INSTANCE.rotateSpeed.getFloat() : 45.0f;
    }

    private ClientSetting.SwitchMode getSwitchMode9() {
        Object var2_1 = null;
        if (this.switchMode.getValue() == SwitchMode.DEFAULT) {
            return ClientSetting.INSTANCE != null ? (ClientSetting.SwitchMode)((Object)ClientSetting.INSTANCE.switchMode.getValue()) : ClientSetting.SwitchMode.SILENT;
        }
        return switch (((SwitchMode)((Object)this.switchMode.getValue())).ordinal()) {
            case 1 -> ClientSetting.SwitchMode.NONE;
            case 2 -> ClientSetting.SwitchMode.NORMAL;
            case 3 -> ClientSetting.SwitchMode.SILENT;
            case 4 -> ClientSetting.SwitchMode.INVENTORY;
            default -> ClientSetting.SwitchMode.SILENT;
        };
    }

    @Environment(value=EnvType.CLIENT)
    public static enum SwitchMode {
      DEFAULT, NONE, NORMAL, SILENT, INVENTORY;

      private SwitchMode() {}



        private static SwitchMode[] getSwitchModeArray2() {
            return new SwitchMode[]{DEFAULT, NONE, NORMAL, SILENT, INVENTORY};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum RotateMode {
      DEFAULT, NONE, SMOOTH, ONTICK, rotateMode8;

      private RotateMode() {}



        private static RotateMode[] getRotateModeArray13() {
            return new RotateMode[]{DEFAULT, NONE, SMOOTH, ONTICK, rotateMode8};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    public static enum TimingMode {
      PRE, POST, ALL;

      private TimingMode() {}



        private static TimingMode[] getTimingModeArray3() {
            return new TimingMode[]{PRE, POST, ALL};
        }
    
   }
}

