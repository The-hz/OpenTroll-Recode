/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerModelPart;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.setting.BooleanSetting;
import shit.setting.EnumSetting;
import shit.setting.NumberSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class SkinBlinker
extends Module {
    private final BooleanSetting cape = (BooleanSetting)this.registerSetting(new BooleanSetting("Cape", false));
    private final BooleanSetting jacket = (BooleanSetting)this.registerSetting(new BooleanSetting("Jacket", true));
    private final BooleanSetting leftSleeve = (BooleanSetting)this.registerSetting(new BooleanSetting("LeftSleeve", true));
    private final BooleanSetting rightSleeve = (BooleanSetting)this.registerSetting(new BooleanSetting("RightSleeve", true));
    private final BooleanSetting leftPants = (BooleanSetting)this.registerSetting(new BooleanSetting("LeftPants", true));
    private final BooleanSetting rightPants = (BooleanSetting)this.registerSetting(new BooleanSetting("RightPants", true));
    private final BooleanSetting hat = (BooleanSetting)this.registerSetting(new BooleanSetting("Hat", true));
    private final EnumSetting mode = (EnumSetting)this.registerSetting(new EnumSetting("Mode", Mode.HORIZONTAL));
    private final NumberSetting delay = (NumberSetting)this.registerSetting(new NumberSetting("Delay", 100.0, 0.0, 500.0, 5.0));
    private final Helper7 helper75 = new Helper7();
    private final Random random6 = new Random();
    private int count231;
    private static final PlayerModelPart[] fields18 = new PlayerModelPart[0];
    private static final PlayerModelPart[] fields2 = new PlayerModelPart[0];

    public SkinBlinker() {
        super("SkinBlinker", "Rapidly toggles skin layers.", Category.MISC);
    }

    @Override
    public void onDisable() {
        PlayerModelPart[] playerModelPartArray = PlayerModelPart.values();
        int n = playerModelPartArray.length;
        String string = IRC.getText7();
        for (int i = 0; i < n; ++i) {
            PlayerModelPart playerModelPart = playerModelPartArray[i];
            MC.mc.options.setPlayerModelPart(playerModelPart, true);
            if (string != null) continue;
        }
    }

    @EventHandler
    private void setEvent2Inner215(Event2.Event2Inner2 event2Inner2) {
        PlayerModelPart playerModelPart;
        if (!this.helper75.hasPassedMs((Double)this.delay.getValue())) {
            return;
        }
        PlayerModelPart[] playerModelPartArray = this.mode.getValue() == Mode.VERTICAL ? fields2 : fields18;
        PlayerModelPart playerModelPart2 = playerModelPart = this.mode.getValue() == Mode.RANDOM ? PlayerModelPart.values()[this.random6.nextInt(PlayerModelPart.values().length)] : playerModelPartArray[this.count231++ % playerModelPartArray.length];
        if (this.m698(playerModelPart)) {
            MC.mc.options.setPlayerModelPart(playerModelPart, !MC.mc.options.isPlayerModelPartEnabled(playerModelPart));
        }
        this.helper75.resetTimer();
    }

    private boolean m698(Object object) {
        boolean bl = false;
        block10: {
            int[] object2;
            block9: {
                PlayerModelPart playerModelPart = (PlayerModelPart)object;
                String string = IRC.getText7();
                object2 = Lambda.counts6;
                if (string == null) break block9;
                switch (object2[playerModelPart.ordinal()]) {
                    default: {
                        throw new MatchException(null, null);
                    }
                    case 1: {
                        bl = (Boolean)this.cape.getValue();
                        break block10;
                    }
                    case 2: {
                        bl = (Boolean)this.jacket.getValue();
                        break block10;
                    }
                    case 3: {
                        bl = (Boolean)this.leftSleeve.getValue();
                        break block10;
                    }
                    case 4: {
                        bl = (Boolean)this.rightSleeve.getValue();
                        break block10;
                    }
                    case 5: {
                        bl = (Boolean)this.leftPants.getValue();
                        break block10;
                    }
                    case 6: {
                        bl = (Boolean)this.rightPants.getValue();
                        break block10;
                    }
                    case 7: {
                        bl = (Boolean)this.hat.getValue();
                        break block10;
                    }
                }
            }
        }
        return bl;
    }

    /*
     * Exception decompiling
     */
    static {}

    private static MatchException a(MatchException matchException) {
        return matchException;
    }

    @Environment(value=EnvType.CLIENT)
    static enum Mode {
      HORIZONTAL, VERTICAL, RANDOM;

      private Mode() {}



        private static Mode[] getModeArray15() {
            return new Mode[]{HORIZONTAL, VERTICAL, RANDOM};
        }
    
   }

    @Environment(value=EnvType.CLIENT)
    static class Lambda {
        static final int[] counts6 = new int[0];

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        static {}
    }
}

