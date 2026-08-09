/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import java.util.EnumMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlBackend;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.GlTexture;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL20C;
import shit.misc.ShaderProgram;
import shit.module.Module;
import shit.module.client.MainMenu;
import shit.render.ScreenCopyRenderer;
import shit.util.GlBufferHelper;

@Environment(value=EnvType.CLIENT)
public final class ShaderProgramHolder {
    private static final EnumMap<EMode, ShaderProgram> enumMap = new EnumMap<>(EMode.class);
    private static long time25;

    private ShaderProgramHolder() {
    }

    public static void m884() {
        time25 = System.currentTimeMillis();
    }

    public static boolean m252(Object object) {
        MainMenu mainMenu;
        DrawContext drawContext;
        block7: {
            boolean bl;
            block6: {
                block5: {
                    MainMenu mainMenu2;
                    boolean bl2;
                    block4: {
                        drawContext = (DrawContext)object;
                        mainMenu = MainMenu.INSTANCE;
                        bl2 = ScreenCopyRenderer.isSet38();
                        mainMenu2 = mainMenu;
                        if (!bl2) break block4;
                        if (mainMenu2 == null) break block5;
                        mainMenu2 = mainMenu;
                    }
                    bl = mainMenu2.isSet9();
                    if (!bl2) break block6;
                    if (bl) break block7;
                }
                bl = false;
            }
            return bl;
        }
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        try {
            ShaderProgramHolder.render4((Object)mainMenu.getObj8(), minecraftClient.getWindow().getFramebufferWidth(), minecraftClient.getWindow().getFramebufferHeight(), (double)drawContext.getScaledWindowWidth() / 2.0, (double)drawContext.getScaledWindowHeight() / 2.0);
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public static void render4(Object object, int n, int n2, double d, double d2) {
        block3: {
            EMode eMode2 = (EMode)((Object)object);
            int n3 = n;
            int n4 = n2;
            int n5 = GL20C.glGetInteger((int)35725);
            int n6 = GL20C.glGetInteger((int)36006);
            int[] nArray = new int[4];
            GL11C.glGetIntegerv((int)2978, (int[])nArray);
            boolean bl = GL11C.glIsEnabled((int)2929);
            boolean bl2 = GL11C.glIsEnabled((int)3042);
            boolean bl3 = GL11C.glIsEnabled((int)2884);
            boolean bl4 = GL11C.glGetBoolean((int)2930);
            boolean bl5 = false;
            int n7 = ShaderProgramHolder.getInt39();
            try {
                if (n7 > 0) {
                    GlStateManager._glBindFramebuffer((int)36160, (int)n7);
                }
                ShaderProgram shaderProgram = enumMap.computeIfAbsent(eMode2, eMode -> new ShaderProgram("menu/defaultvertex.vert.glsl", "menu/" + eMode.text1267));
                GlStateManager._viewport((int)0, (int)0, (int)n3, (int)n4);
                GlStateManager._disableDepthTest();
                GlStateManager._depthMask((boolean)false);
                GlStateManager._disableBlend();
                shaderProgram.m1045();
                shaderProgram.m700("resolution", n3, n4);
                shaderProgram.m82("time", (double)(System.currentTimeMillis() - time25) / 1000.0);
                GlBufferHelper.m504();
                GlBufferHelper.m149();
                GlBufferHelper.m582();
            }
            catch (Throwable throwable) {
                GlStateManager._glUseProgram((int)n5);
                GlStateManager._glBindFramebuffer((int)36160, (int)n6);
                GlStateManager._viewport((int)nArray[0], (int)nArray[1], (int)nArray[2], (int)nArray[3]);
                GlStateManager._depthMask((boolean)bl4);
                ShaderProgramHolder.m365(2929, bl);
                ShaderProgramHolder.m365(3042, bl2);
                ShaderProgramHolder.m365(2884, bl3);
                throw throwable;
            }
            GlStateManager._glUseProgram((int)n5);
            GlStateManager._glBindFramebuffer((int)36160, (int)n6);
            GlStateManager._viewport((int)nArray[0], (int)nArray[1], (int)nArray[2], (int)nArray[3]);
            GlStateManager._depthMask((boolean)bl4);
            ShaderProgramHolder.m365(2929, bl);
            ShaderProgramHolder.m365(3042, bl2);
            ShaderProgramHolder.m365(2884, bl3);
            if (!false) break block3;
            Module.setTextArray9(new String[4]);
        }
    }

    private static void m365(int n, boolean n2) {
        block14: {
            int n3;
            boolean bl;
            block12: {
                int n4;
                block13: {
                    n4 = n;
                    int n5 = n2 ? 1 : 0;
                    bl = ScreenCopyRenderer.isSet38();
                    n3 = n5;
                    if (!bl) break block12;
                    if (n3 == 0) break block13;
                    switch (n4) {
                        case 2929: {
                            GlStateManager._enableDepthTest();
                            if (bl) break;
                        }
                        case 3042: {
                            GlStateManager._enableBlend();
                            if (bl) break;
                        }
                        case 2884: {
                            GlStateManager._enableCull();
                            break;
                        }
                    }
                    if (bl) break block14;
                }
                n3 = n4;
            }
            switch (n3) {
                case 2929: {
                    GlStateManager._disableDepthTest();
                    if (bl) break;
                }
                case 3042: {
                    GlStateManager._disableBlend();
                    if (bl) break;
                }
                case 2884: {
                    GlStateManager._disableCull();
                    break;
                }
            }
        }
    }

    private static int getInt39() {
        boolean bl = false;
        try {
            GpuTexture gpuTexture = MinecraftClient.getInstance().getFramebuffer().getColorAttachment();
            if (gpuTexture instanceof GlTexture) {
                GlTexture glTexture = (GlTexture)gpuTexture;
                return glTexture.getOrCreateFramebuffer(((GlBackend)RenderSystem.getDevice()).getBufferManager(), null);
            }
        }
        catch (Throwable throwable) {}
        return 0;
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    public static enum EMode  {
        eMode("galaxy.frag.glsl"), eMode2("minecraft.frag.glsl"), eMode3("cubecave.frag.glsl"),
        eMode4("tube.frag.glsl"), eMode5("redlandscape.frag.glsl"), eMode6("bluegrid.frag.glsl"),
        eMode7("greennebula.frag.glsl"), eMode8("circuits.frag.glsl"), eMode9("matrix.frag.glsl"),
        eMode10("gridcave.frag.glsl"), eMode11("blackhole.frag.glsl"), eMode12("bluelandscape.frag.glsl"),
        eMode13("rectwaves.frag.glsl"), eMode14("planet.frag.glsl"), eMode15("purplegrid.frag.glsl"),
        eMode16("space.frag.glsl");

        private final String text1267;

        private EMode(String text1267) {
            this.text1267 = text1267;
        }

        

        /*
         * Unable to fully structure code
         */
        
    }
}

