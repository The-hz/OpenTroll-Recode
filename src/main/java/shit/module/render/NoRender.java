/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  net.minecraft.network.packet.s2c.play.ClearTitleS2CPacket
 *  net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket
 *  net.minecraft.network.packet.s2c.play.TitleS2CPacket
 */
package shit.module.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.packet.s2c.play.ClearTitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.module.Category;
import shit.module.Module;
import shit.setting.BooleanSetting;

@Environment(value=EnvType.CLIENT)
public class NoRender
extends Module {
    public static NoRender INSTANCE;
    public final BooleanSetting potionsIcon;
    public final BooleanSetting portal;
    public final BooleanSetting nausea;
    public final BooleanSetting guiToast;
    public final BooleanSetting fireOverlay;
    public final BooleanSetting waterOverlay;
    public final BooleanSetting blockOverlay;
    public final BooleanSetting hurtCam;
    public final BooleanSetting totem;
    public final BooleanSetting weather;
    public final BooleanSetting darkness;
    public final BooleanSetting fog;
    public final BooleanSetting blindness;
    public final BooleanSetting entityFire;
    public final BooleanSetting invisible;
    public final BooleanSetting potions;
    public final BooleanSetting xP;
    public final BooleanSetting arrows;
    public final BooleanSetting eggs;
    public final BooleanSetting items;
    public final BooleanSetting armorParts;
    public final BooleanSetting armorTrim;
    public final BooleanSetting armorGlint;
    public final BooleanSetting witherHearts;
    public final BooleanSetting effect;
    public final BooleanSetting guardian;
    public final BooleanSetting explosions;
    public final BooleanSetting campFire;
    public final BooleanSetting fireworks;
    public final BooleanSetting title;
    private static int[] counts;

        public NoRender() {
        super("NoRender", "Disables overlays, particles and entity visuals.", Category.RENDER);
        this.potionsIcon = (BooleanSetting)this.m28(new BooleanSetting("PotionsIcon", false));
        this.portal = (BooleanSetting)this.m28(new BooleanSetting("Portal", true));
        this.nausea = (BooleanSetting)this.m28(new BooleanSetting("Nausea", true));
        this.guiToast = (BooleanSetting)this.m28(new BooleanSetting("GuiToast", false));
        this.fireOverlay = (BooleanSetting)this.m28(new BooleanSetting("FireOverlay", true));
        this.waterOverlay = (BooleanSetting)this.m28(new BooleanSetting("WaterOverlay", true));
        this.blockOverlay = (BooleanSetting)this.m28(new BooleanSetting("BlockOverlay", true));
        this.hurtCam = (BooleanSetting)this.m28(new BooleanSetting("HurtCam", true));
        this.totem = (BooleanSetting)this.m28(new BooleanSetting("Totem", true));
        this.weather = (BooleanSetting)this.m28(new BooleanSetting("Weather", true));
        this.darkness = (BooleanSetting)this.m28(new BooleanSetting("Darkness", true));
        this.fog = (BooleanSetting)this.m28(new BooleanSetting("Fog", false));
        this.blindness = (BooleanSetting)this.m28(new BooleanSetting("Blindness", true));
        this.entityFire = (BooleanSetting)this.m28(new BooleanSetting("EntityFire", true));
        this.invisible = (BooleanSetting)this.m28(new BooleanSetting("Invisible", false));
        this.potions = (BooleanSetting)this.m28(new BooleanSetting("Potions", true));
        this.xP = (BooleanSetting)this.m28(new BooleanSetting("XP", true));
        this.arrows = (BooleanSetting)this.m28(new BooleanSetting("Arrows", false));
        this.eggs = (BooleanSetting)this.m28(new BooleanSetting("Eggs", false));
        this.items = (BooleanSetting)this.m28(new BooleanSetting("Items", false));
        this.armorParts = (BooleanSetting)this.m28(new BooleanSetting("ArmorParts", false));
        this.armorTrim = (BooleanSetting)this.m28(new BooleanSetting("ArmorTrim", false));
        this.armorGlint = (BooleanSetting)this.m28(new BooleanSetting("ArmorGlint", false));
        this.witherHearts = (BooleanSetting)this.m28(new BooleanSetting("WitherHearts", true));
        this.effect = (BooleanSetting)this.m28(new BooleanSetting("Effect", true));
        this.guardian = (BooleanSetting)this.m28(new BooleanSetting("Guardian", false));
        this.explosions = (BooleanSetting)this.m28(new BooleanSetting("Explosions", true));
        this.campFire = (BooleanSetting)this.m28(new BooleanSetting("CampFire", false));
        this.fireworks = (BooleanSetting)this.m28(new BooleanSetting("Fireworks", false));
        this.title = (BooleanSetting)this.m28(new BooleanSetting("Title", false));
    }

    @EventHandler
    private void setPacketEventInner32(PacketEvent.PacketEventInner packetEventInner) {
        if (!((Boolean)this.title.getObj()).booleanValue()) {
            return;
        }
        if (packetEventInner.getPacket() instanceof TitleS2CPacket || packetEventInner.getPacket() instanceof TitleFadeS2CPacket || packetEventInner.getPacket() instanceof ClearTitleS2CPacket) {
            packetEventInner.m209();
        }
    }

    public static void setIntArray6(int[] nArray) {
        counts = nArray;
    }

    public static int[] getIntArray7() {
        return counts;
    }

    static {
        boolean bl = false;
        String string = "b\u009a\u00c1\u001d\u00e8L\u00f47\u00a8\b\u00b51\u00f9\u00b4v\u0010\u00982\u0007\u00d9q\u0081\u009c-1D\u0003\u00a7\u00c5\u0094\f\u00940~\u0099DJ_\u0083F\\\u0088q\u0005\fn\u00a27\u00a1\f\u007f\u00ee\u00af\u00e8\u001f\u00a2\u000e\u009el\u00ff\u00cd\u0005\u0006\u00f9\u0006D\u00e3T9\fx\u00e0O-\u0014\u008b\u00a4!\u00d3\u0016^\u00ef\b\u000fV.\u00ea!s\u007fF\u0006E\u0091\u00aa\u00e0\u00fe\u0098\n\u009d\u00c8\u00a3\u00ce\u0098\u00dd\b\u008d\u009a\u0019\u0002P\u0083\b\u00b0\u00ceQma\u00c1\u0002\u001b\u000b\u0006\u00f7g\b\u001b\u00125\u00a1\u00bf\u00b2\u008b\u0006M\u000e;\u008fN9\u0007k\u00d1\u00ca\u00cc\u00ae\u008a\u0083\u0005\u00d9\u00bb\u0018B\u00fe\u0005\u00ac\u00f6\u0019T\u00fb\t\u00af\u00c7j/\u00e4\u00f1D\u001f\u00a4\u0006\u009e\u0011r\u00c9q\u0096\bM\u00b0\u009e-\u00e3\u00ff\u000bL\n'\u00efi\u00b3\u00fd\u009dE\u0092\u00f6\u0094\t\u00c5\u00d0R\u0088\b\u00e5+e\u00b80\bL7\u00b1\u00fb.\u0093^\u00fc0\u00d0\u0083\u00e9ri\u00b1\u009aQ\f\u00cac\\2W\u00c2\u00a0\u000b!\u0081?\u00f3\u00d3\u009f\u00ce\u001emn\u00aa\u00ed\u00ed\u00a4WV\u00bc?=\u00bbQ\b\u00c6B\u0084\u00b2\u0084\u00c2\u00f1\u00cb\t\u00fa\u00f4*\u00a2l\u00cb\u00c1\u0099\u0010\u00045\u00eb\u00a5\u0098\u0007\u00b4J\u00c3\u00e9?\u009bW\u000bB\u00a9=\t\u00b6\u00a7\u00b27\u00942\u0017";
        int n = 300;
        NoRender.setIntArray6(null);
    }
}
