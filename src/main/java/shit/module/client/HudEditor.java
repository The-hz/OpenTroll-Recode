/*
 * Decompiled with CFR 0.152.
 */
package shit.module.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import shit.gui.HudEditorScreen;
import shit.module.Category;
import shit.module.Module;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class HudEditor
extends Module {
    public HudEditor() {
        super("HudEditor", "Edits HUD element positions.", Category.CLIENT);
        this.getColorSetting2().setObj94(-1);
    }

    @Override
    public void onEnable() {
        MC.client3.setScreen((Screen)new HudEditorScreen());
        this.setFlag3(false);
    }
}

