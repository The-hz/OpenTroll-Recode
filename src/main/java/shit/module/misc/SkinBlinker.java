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
    private final BooleanSetting cape = (BooleanSetting)this.m28(new BooleanSetting("Cape", false));
    private final BooleanSetting jacket = (BooleanSetting)this.m28(new BooleanSetting("Jacket", true));
    private final BooleanSetting leftSleeve = (BooleanSetting)this.m28(new BooleanSetting("LeftSleeve", true));
    private final BooleanSetting rightSleeve = (BooleanSetting)this.m28(new BooleanSetting("RightSleeve", true));
    private final BooleanSetting leftPants = (BooleanSetting)this.m28(new BooleanSetting("LeftPants", true));
    private final BooleanSetting rightPants = (BooleanSetting)this.m28(new BooleanSetting("RightPants", true));
    private final BooleanSetting hat = (BooleanSetting)this.m28(new BooleanSetting("Hat", true));
    private final EnumSetting mode = (EnumSetting)this.m28(new EnumSetting("Mode", Mode.HORIZONTAL));
    private final NumberSetting delay = (NumberSetting)this.m28(new NumberSetting("Delay", 100.0, 0.0, 500.0, 5.0));
    private final Helper7 helper75 = new Helper7();
    private final Random random6 = new Random();
    private int count231;
    private static final PlayerModelPart[] fields18 = new PlayerModelPart[0];
    private static final PlayerModelPart[] fields2 = new PlayerModelPart[0];

    public SkinBlinker() {
        super("SkinBlinker", "Rapidly toggles skin layers.", Category.MISC);
    }

    @Override
    public void m709() {
        PlayerModelPart[] playerModelPartArray = PlayerModelPart.values();
        int n = playerModelPartArray.length;
        String string = IRC.getText7();
        for (int i = 0; i < n; ++i) {
            PlayerModelPart playerModelPart = playerModelPartArray[i];
            MC.client3.options.setPlayerModelPart(playerModelPart, true);
            if (string != null) continue;
        }
    }

    @EventHandler
    private void setEvent2Inner215(Event2.Event2Inner2 event2Inner2) {
        PlayerModelPart playerModelPart;
        if (!this.helper75.m432((Double)this.delay.getObj())) {
            return;
        }
        PlayerModelPart[] playerModelPartArray = this.mode.getObj() == Mode.VERTICAL ? fields2 : fields18;
        PlayerModelPart playerModelPart2 = playerModelPart = this.mode.getObj() == Mode.RANDOM ? PlayerModelPart.values()[this.random6.nextInt(PlayerModelPart.values().length)] : playerModelPartArray[this.count231++ % playerModelPartArray.length];
        if (this.m698(playerModelPart)) {
            MC.client3.options.setPlayerModelPart(playerModelPart, !MC.client3.options.isPlayerModelPartEnabled(playerModelPart));
        }
        this.helper75.m533();
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
                        bl = (Boolean)this.cape.getObj();
                        break block10;
                    }
                    case 2: {
                        bl = (Boolean)this.jacket.getObj();
                        break block10;
                    }
                    case 3: {
                        bl = (Boolean)this.leftSleeve.getObj();
                        break block10;
                    }
                    case 4: {
                        bl = (Boolean)this.rightSleeve.getObj();
                        break block10;
                    }
                    case 5: {
                        bl = (Boolean)this.leftPants.getObj();
                        break block10;
                    }
                    case 6: {
                        bl = (Boolean)this.rightPants.getObj();
                        break block10;
                    }
                    case 7: {
                        bl = (Boolean)this.hat.getObj();
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

