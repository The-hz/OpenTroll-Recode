/*
 * Decompiled with CFR 0.152.
 */
package shit.module.misc;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import shit.Client;
import shit.command.CommandManager;
import shit.event.Event2;
import shit.event.EventHandler;
import shit.event.PacketEvent;
import shit.event.Render2DEvent;
import shit.event.RenderLevelEvent;
import shit.misc.Helper7;
import shit.module.Category;
import shit.module.Module;
import shit.module.misc.IRC;
import shit.render.EspRenderLayers;
import shit.setting.BooleanSetting;
import shit.setting.ColorSetting;
import shit.setting.ColorSetting2;
import shit.setting.NumberSetting;
import shit.setting.StringSetting;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class Punctuation
extends Module {
    public static Punctuation INSTANCE;
    private final BooleanSetting sound = (BooleanSetting)this.m28(new BooleanSetting("Sound", true));
    private final NumberSetting clearTime = (NumberSetting)this.m28(new NumberSetting("ClearTime", 10.0, 0.0, 100.0, 0.1));
    private final ColorSetting color = (ColorSetting)this.m28(new ColorSetting("Color", 0x64FFFFFF));
    private final ColorSetting2 spotKey = (ColorSetting2)this.m28(new ColorSetting2("SpotKey", -1));
    private final StringSetting encryptKey = (StringSetting)this.m28(new StringSetting("EncryptKey", "IDKWTFTHIS"));
    public final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    private boolean flag135 = false;
    private Matrix4f matrix4f2;
    private Matrix4f matrix4f12;

    public Punctuation() {
        super("Punctuation", "Encrypted coordinate marking system.", Category.MISC);
        INSTANCE = this;
    }

    @Override
    public void m709() {
        this.concurrentHashMap.clear();
    }

    @EventHandler
    private void onTick8(Event2.Event2Inner event2Inner) {
        if (Module.isSet37()) {
            return;
        }
        this.concurrentHashMap.values().removeIf(data -> ((Data)data).getHelper7().m114((Double)this.clearTime.getObj()));
        int n = (Integer)this.spotKey.getObj();
        if (n == -1 || MC.client3.currentScreen != null) {
            this.flag135 = false;
            return;
        }
        boolean bl2 = ColorSetting2.m232(n) ? GLFW.glfwGetMouseButton((long)MC.client3.getWindow().getHandle(), (int)ColorSetting2.m1003(n)) == 1 : InputUtil.isKeyPressed((Window)MC.client3.getWindow(), (int)n);
        if (bl2 && !this.flag135) {
            this.m611();
        }
        this.flag135 = bl2;
    }

    private void m611() {
        String string;
        int n;
        BlockHitResult blockHitResult;
        String string2;
        block11: {
            block10: {
                HitResult hitResult;
                block9: {
                    HitResult hitResult2;
                    Entity entity = MC.client3.getCameraEntity();
                    string2 = IRC.getText7();
                    Entity entity2 = entity;
                    if (string2 != null) {
                        if (entity2 == null) {
                            return;
                        }
                        entity2 = entity;
                    }
                    hitResult = hitResult2 = entity2.raycast(256.0, 0.0f, false);
                    if (string2 == null) break block9;
                    if (!(hitResult instanceof BlockHitResult)) break block10;
                    hitResult = hitResult2;
                }
                blockHitResult = (BlockHitResult)hitResult;
                if (string2 != null) break block11;
            }
            return;
        }
        BlockPos blockPos = blockHitResult.getBlockPos();
        int n2 = n = ((Integer)this.color.getObj()).intValue();
        int n3 = blockPos.getZ();
        int n4 = blockPos.getY();
        int n5 = blockPos.getX();
        String string3 = string = this.m668("EnemyHere{" + n5 + "," + n4 + "," + n3 + "," + n2 + "}");
        if (string2 != null) {
            if (string3 == null) {
                CommandManager.setObj21("\u00a7c[Punctuation] \u52a0\u5bc6\u5931\u8d25");
                return;
            }
            string3 = MC.client3.player.getName().getString();
        }
        String string4 = string3;
        this.concurrentHashMap.put(string4, new Data(string4, blockPos, n, new Helper7()));
        if (string2 != null) {
            if (((Boolean)this.sound.getObj()).booleanValue()) {
                this.m702();
            }
            int n6 = blockPos.getZ();
            int n7 = blockPos.getY();
            int n8 = blockPos.getX();
            CommandManager.setObj21(string4 + " \u00a77marked \u00a7f(" + n8 + ", " + n7 + ", " + n6 + ")");
            MC.client3.player.networkHandler.sendChatMessage(string);
        }
    }

    @EventHandler
    private void setPacketEventInner6(PacketEvent.PacketEventInner packetEventInner) {
        GameMessageS2CPacket gameMessageS2CPacket;
        if (Module.isSet37()) {
            return;
        }
        Object object = packetEventInner.getPacket();
        if (object instanceof GameMessageS2CPacket && (gameMessageS2CPacket = (GameMessageS2CPacket)object).content() != null) {
            this.m916(gameMessageS2CPacket.content().getString(), gameMessageS2CPacket.content().getString());
        } else {
            object = packetEventInner.getPacket();
            if (object instanceof ChatMessageS2CPacket) {
                ChatMessageS2CPacket chatMessageS2CPacket = (ChatMessageS2CPacket)object;
                object = chatMessageS2CPacket.body().content();
                this.m916(object, object);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public boolean m916(Object var1_1, Object var2_2) {
        String raw = (String)var1_1;
        if (raw == null) {
            return false;
        }
        String cleaned = raw.replaceAll("\u00a7[a-zA-Z0-9]", "");
        String[] tokens = cleaned.split("\\s+");
        Matcher coordMatcher = null;
        int x = 0;
        for (String token : tokens) {
            String t = token.replaceAll("[^a-zA-Z0-9+/=]", "");
            if (t.length() < 10) continue;
            String decoded = this.m850(t);
            if (decoded == null || !decoded.contains("EnemyHere")) continue;
            Matcher matcher = Pattern.compile("\\{(-?\\d+),(-?\\d+),(-?\\d+),(-?\\d+)\\}").matcher(decoded);
            if (matcher.find()) {
                x = Integer.parseInt(matcher.group(1));
                coordMatcher = matcher;
                break;
            }
        }
        if (coordMatcher == null) {
            return false;
        }
        int y = Integer.parseInt(coordMatcher.group(2));
        int z = Integer.parseInt(coordMatcher.group(3));
        int color = Integer.parseInt(coordMatcher.group(4));
        Matcher nameMatcher = Pattern.compile("<(.*?)>").matcher(raw);
        String name = nameMatcher.find() ? nameMatcher.group(1) : cleaned.split("\\s+")[0].replaceAll("[^a-zA-Z0-9_]", "");
        if (name.isEmpty()) {
            name = "unknown";
        }
        this.concurrentHashMap.put(name, new Data(name, new BlockPos(x, y, z), color, new Helper7()));
        if (((Boolean)this.sound.getObj()).booleanValue()) {
            this.m702();
        }
        CommandManager.setObj21(name + " \u00a77marked \u00a7f(" + x + ", " + y + ", " + z + ")");
        return true;
    }

    @EventHandler
    private void setRenderLevelEvent4(RenderLevelEvent renderLevelEvent) {
        this.matrix4f2 = new Matrix4f((Matrix4fc)renderLevelEvent.getMatrix4f3());
        this.matrix4f12 = new Matrix4f((Matrix4fc)renderLevelEvent.getMatrix4f());
        if (Module.isSet37() || this.concurrentHashMap.isEmpty()) {
            return;
        }
        for (Data data : (java.util.Collection<Data>)this.concurrentHashMap.values()) {
            Box box = new Box((double)data.getBlockPos4().getX() + 0.25, -60.0, (double)data.getBlockPos4().getZ() + 0.25, (double)data.getBlockPos4().getX() + 0.75, 320.0, (double)data.getBlockPos4().getZ() + 0.75);
            int n = data.count15();
            EspRenderLayers.m69(renderLevelEvent.getMatrix4f3(), box, n, true);
            EspRenderLayers.m688(renderLevelEvent.getMatrix4f3(), box, n | 0xFF000000, true);
        }
        EspRenderLayers.m125();
    }

    @EventHandler
    private void setObj105(Render2DEvent render2DEvent) {
        if (Module.isSet37() || this.concurrentHashMap.isEmpty()) {
            return;
        }
        if (this.matrix4f2 == null || this.matrix4f12 == null) {
            return;
        }
        Vec3d vec3d = MC.client3.gameRenderer.getCamera().getCameraPos();
        Matrix4f matrix4f = new Matrix4f((Matrix4fc)this.matrix4f12).mul((Matrix4fc)this.matrix4f2);
        int n = MC.client3.getWindow().getScaledWidth();
        int n2 = MC.client3.getWindow().getScaledHeight();
        for (Data data : (java.util.Collection<Data>)this.concurrentHashMap.values()) {
            int[] nArray = Punctuation.m132((double)data.getBlockPos4().getX() + 0.5, (double)data.getBlockPos4().getY() + 1.5, (double)data.getBlockPos4().getZ() + 0.5, vec3d, matrix4f, n, n2);
            if (nArray == null) continue;
            int n3 = data.getBlockPos4().getZ();
            int n4 = data.getBlockPos4().getY();
            int n5 = data.getBlockPos4().getX();
            String string = data.text13();
            String string2 = "\u00a7a" + string + " \u00a7f(" + n5 + ", " + n4 + ", " + n3 + ")";
            int n6 = Client.fontManager.renderer2().m277(string2);
            Client.fontManager.renderer2().m5(render2DEvent.getDrawContext(), string2, nArray[0] - n6 / 2, nArray[1], -1, true);
        }
    }

    public String m668(Object object) {
        String string = (String)object;
        String string2 = IRC.getText7();
        try {
            Matcher matcher = Pattern.compile("EnemyHere\\{(-?\\d+),(-?\\d+),(-?\\d+),(-?\\d+)\\}").matcher(string);
            int n = matcher.find() ? 1 : 0;
            if (string2 != null) {
                if (n == 0) {
                    return null;
                }
                n = Integer.parseInt(matcher.group(1));
            }
            int n2 = n;
            int n3 = Integer.parseInt(matcher.group(2));
            int n4 = Integer.parseInt(matcher.group(3));
            int n5 = Integer.parseInt(matcher.group(4));
            byte[] byArray = new byte[15];
            byArray[0] = 1;
            Punctuation.m204(byArray, 1, n2);
            Punctuation.m634(byArray, 5, (short)n3);
            Punctuation.m204(byArray, 7, n4);
            Punctuation.m204(byArray, 11, n5);
            byte[] byArray2 = new byte[8];
            new SecureRandom().nextBytes(byArray2);
            byte[] byArray3 = Punctuation.m793(byArray, (String)this.encryptKey.getObj(), Punctuation.m847(byArray2));
            byte[] byArray4 = new byte[8 + byArray3.length];
            System.arraycopy(byArray2, 0, byArray4, 0, 8);
            System.arraycopy(byArray3, 0, byArray4, 8, byArray3.length);
            return Base64.getEncoder().encodeToString(byArray4);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public String m850(Object object) {
        String string = (String)object;
        String string2 = IRC.getText7();
        try {
            int n;
            byte[] byArray;
            byte[] byArray2 = byArray = Base64.getDecoder().decode(string);
            if (string2 != null) {
                if (byArray2.length != 23) {
                    return null;
                }
                byArray2 = Arrays.copyOf(byArray, 8);
            }
            byte[] byArray3 = byArray2;
            byte[] byArray4 = Punctuation.m793(Arrays.copyOfRange(byArray, 8, byArray.length), (String)this.encryptKey.getObj(), Punctuation.m847(byArray3));
            int n2 = byArray4[0];
            if (string2 != null) {
                if (n2 != 1) {
                    return null;
                }
                n2 = Punctuation.m527(byArray4, 1);
            }
            int n3 = n2;
            short s = Punctuation.m415(byArray4, 5);
            int n4 = Punctuation.m527(byArray4, 7);
            int n5 = n = Punctuation.m527(byArray4, 11);
            int n6 = n4;
            short s2 = s;
            int n7 = n3;
            return "EnemyHere{" + n7 + "," + s2 + "," + n6 + "," + n5 + "}";
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static byte[] m793(Object object, Object object2, Object object3) {
        byte[] byArray = (byte[])object;
        String string = (String)object2;
        String string2 = (String)object3;
        String string3 = IRC.getText7();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(string.getBytes(StandardCharsets.UTF_8));
            messageDigest.update(string2.getBytes(StandardCharsets.UTF_8));
            byte[] byArray2 = messageDigest.digest();
            byte[] byArray3 = new byte[byArray.length];
            for (int i = 0; i < byArray.length; ++i) {
                block8: {
                    int n;
                    block7: {
                        n = i;
                        if (string3 == null) break block7;
                        if (n <= 0) break block8;
                        n = i % 32;
                    }
                    if (n == 0) {
                        messageDigest.reset();
                        messageDigest.update(byArray2);
                        messageDigest.update((byte)(i / 32));
                        byArray2 = messageDigest.digest();
                    }
                }
                byArray3[i] = (byte)(byArray[i] ^ byArray2[i % 32]);
                if (string3 != null) continue;
            }
            return byArray3;
        }
        catch (Exception exception) {
            return (byte[])byArray.clone();
        }
    }

    private static void m204(Object object, int n, int n2) {
        byte[] byArray = (byte[])object;
        int n3 = n;
        int n4 = n2;
        byArray[n3] = (byte)(n4 >> 24);
        byArray[n3 + 1] = (byte)(n4 >> 16);
        byArray[n3 + 2] = (byte)(n4 >> 8);
        byArray[n3 + 3] = (byte)n4;
    }

    private static void m634(Object object, int n, int n2) {
        byte[] byArray = (byte[])object;
        int n3 = n;
        int n4 = n2;
        byArray[n3] = (byte)(n4 >> 8);
        byArray[n3 + 1] = (byte)n4;
    }

    private static int m527(Object object, int n) {
        byte[] byArray = (byte[])object;
        int n2 = n;
        return (byArray[n2] & 0xFF) << 24 | (byArray[n2 + 1] & 0xFF) << 16 | (byArray[n2 + 2] & 0xFF) << 8 | byArray[n2 + 3] & 0xFF;
    }

    private static short m415(Object object, int n) {
        byte[] byArray = (byte[])object;
        int n2 = n;
        return (short)((byArray[n2] & 0xFF) << 8 | byArray[n2 + 1] & 0xFF);
    }

    private static String m847(Object object) {
        StringBuilder stringBuilder;
        block2: {
            byte[] byArray = (byte[])object;
            StringBuilder stringBuilder2 = new StringBuilder(byArray.length * 2);
            byte[] byArray2 = byArray;
            int n = byArray2.length;
            String string = IRC.getText7();
            for (int i = 0; i < n; ++i) {
                byte by = byArray2[i];
                stringBuilder = stringBuilder2.append(String.format("%02x", by));
                if (string != null) {
                    if (string != null) continue;
                }
                break block2;
            }
            stringBuilder = stringBuilder2;
        }
        return stringBuilder.toString();
    }

    private void m702() {
        ClientWorld clientWorld;
        block4: {
            block5: {
                block3: {
                    MinecraftClient minecraftClient;
                    String string;
                    block2: {
                        string = IRC.getText7();
                        minecraftClient = MC.client3;
                        if (string == null) break block2;
                        if (minecraftClient.player == null) break block3;
                        minecraftClient = MC.client3;
                    }
                    clientWorld = minecraftClient.world;
                    if (string == null) break block4;
                    if (clientWorld != null) break block5;
                }
                return;
            }
            clientWorld = MC.client3.world;
        }
        clientWorld.playSoundClient(MC.client3.player.getX(), MC.client3.player.getY(), MC.client3.player.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 1.9f, false);
    }

    private static int[] m132(double d, double d2, double d3, Object object, Object object2, int n, int n2) {
        float f;
        float f2;
        float f3;
        int n3;
        int n4;
        block9: {
            block10: {
                block6: {
                    String string;
                    block8: {
                        block7: {
                            block5: {
                                double d4 = d;
                                double d5 = d2;
                                double d6 = d3;
                                Vec3d vec3d = (Vec3d)object;
                                Matrix4f matrix4f = (Matrix4f)object2;
                                n4 = n;
                                n3 = n2;
                                Vector4f vector4f = new Vector4f((float)(d4 - vec3d.x), (float)(d5 - vec3d.y), (float)(d6 - vec3d.z), 1.0f).mul((Matrix4fc)matrix4f);
                                string = IRC.getText7();
                                float f4 = vector4f.w();
                                float f5 = 0.05f;
                                if (string != null) {
                                    if (f4 <= f5) {
                                        return null;
                                    }
                                    f4 = vector4f.x();
                                    f5 = vector4f.w();
                                }
                                f3 = f4 / f5;
                                f2 = vector4f.y() / vector4f.w();
                                float f6 = f3 - -1.2f;
                                f = f6 == 0.0f ? 0 : (f6 < 0.0f ? -1 : 1);
                                if (string == null) break block5;
                                if (f < 0) break block6;
                                float f7 = f3 - 1.2f;
                                f = f7 == 0.0f ? 0 : (f7 > 0.0f ? 1 : -1);
                            }
                            if (string == null) break block7;
                            if (f > 0) break block6;
                            float f8 = f2 - -1.2f;
                            f = f8 == 0.0f ? 0 : (f8 < 0.0f ? -1 : 1);
                        }
                        if (string == null) break block8;
                        if (f < 0) break block6;
                        float f9 = f2 - 1.2f;
                        f = f9 == 0.0f ? 0 : (f9 > 0.0f ? 1 : -1);
                    }
                    if (string == null) break block9;
                    if (f <= 0) break block10;
                }
                return null;
            }
            f = 2;
        }
        int[] nArray = new int[(int)f];
        nArray[0] = (int)((f3 * 0.5f + 0.5f) * (float)n4);
        nArray[1] = (int)((1.0f - (f2 * 0.5f + 0.5f)) * (float)n3);
        return nArray;
    }

    @Environment(value=EnvType.CLIENT)
    public static final class Data  {
        private final String text13;
        private final BlockPos blockPos4;
        private final int count15;
        private final Helper7 helper7;

        public Data(String string, BlockPos blockPos, int n, Helper7 helper7) {
            this.text13 = string;
            this.blockPos4 = blockPos;
            this.count15 = n;
            this.helper7 = helper7;
        }

        public String text13() {
            return this.text13;
        }

        public BlockPos getBlockPos4() {
            return this.blockPos4;
        }

        public int count15() {
            return this.count15;
        }

        public Helper7 getHelper7() {
            return this.helper7;
        }
    }
}

