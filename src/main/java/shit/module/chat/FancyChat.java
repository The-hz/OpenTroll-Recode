/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package shit.module.chat;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.module.Category;
import shit.module.Module;
import shit.module.chat.ChatTimestamp;
import shit.setting.BooleanSetting;

@Environment(value=EnvType.CLIENT)
public class FancyChat
extends Module {
    public static FancyChat INSTANCE;
    private final BooleanSetting uwu;
    private final BooleanSetting leet;
    private final BooleanSetting green;
    private final BooleanSetting blue;
    private final BooleanSetting mock;
    private final BooleanSetting randomCase;
    private final Random random;

        public FancyChat() {
        super("FancyChat", "Transforms messages before they are sent.", Category.CHAT);
        this.uwu = (BooleanSetting)this.registerSetting(new BooleanSetting("Uwu", true));
        this.leet = (BooleanSetting)this.registerSetting(new BooleanSetting("Leet", false));
        this.green = (BooleanSetting)this.registerSetting(new BooleanSetting("Green", false));
        this.blue = (BooleanSetting)this.registerSetting(new BooleanSetting("Blue", false));
        this.mock = (BooleanSetting)this.registerSetting(new BooleanSetting("Mock", false));
        this.randomCase = (BooleanSetting)this.registerSetting(new BooleanSetting("RandomCase", true));
        this.random = new Random();
    }

    @Override
    public String getInfo() {
        StringBuilder stringBuilder;
        block8: {
            stringBuilder = new StringBuilder();
            int[] nArray = ChatTimestamp.getIntArray2();
            boolean bl = (Boolean)this.uwu.getValue();
            if (nArray != null) {
                if (bl) {
                    stringBuilder.append("uwu ");
                }
                bl = (Boolean)this.leet.getValue();
            }
            if (nArray != null) {
                if (bl) {
                    stringBuilder.append("1337 ");
                }
                bl = (Boolean)this.mock.getValue();
            }
            if (nArray != null) {
                if (bl) {
                    stringBuilder.append("mOcK ");
                }
                bl = (Boolean)this.green.getValue();
            }
            if (nArray != null) {
                if (bl) {
                    stringBuilder.append("> ");
                }
                bl = (Boolean)this.blue.getValue();
            }
            if (!bl) break block8;
            stringBuilder.append("` ");
        }
        return stringBuilder.toString().trim();
    }

    public String m342(Object object) {
        Object object2;
        block13: {
            int n;
            Object object3;
            block12: {
                String string = (String)object;
                object3 = string;
                int[] nArray = ChatTimestamp.getIntArray2();
                n = ((Boolean)this.uwu.getValue()).booleanValue() ? 1 : 0;
                if (nArray != null) {
                    if (n != 0) {
                        object3 = FancyChat.m857(object3);
                    }
                    n = ((Boolean)this.leet.getValue()).booleanValue() ? 1 : 0;
                }
                if (nArray != null) {
                    if (n != 0) {
                        object3 = this.m326(object3);
                    }
                    n = ((Boolean)this.mock.getValue()).booleanValue() ? 1 : 0;
                }
                if (nArray != null) {
                    if (n != 0) {
                        object3 = this.m349(object3);
                    }
                    n = ((Boolean)this.green.getValue()).booleanValue() ? 1 : 0;
                }
                if (nArray != null) {
                    if (n != 0) {
                        object3 = "> " + (String)object3;
                    }
                    n = ((Boolean)this.blue.getValue()).booleanValue() ? 1 : 0;
                }
                if (nArray == null) break block12;
                if (n != 0) {
                    object3 = "`" + (String)object3;
                }
                object2 = object3;
                if (nArray == null) break block13;
                n = ((String)object2).length();
            }
            object2 = n > 256 ? ((String)object3).substring(0, 256) : object3;
        }
        return (String)object2;
    }

    private String m326(Object object) {
        StringBuilder stringBuilder;
        block12: {
            String string = (String)object;
            StringBuilder stringBuilder2 = new StringBuilder(string.length());
            char[] cArray = string.toCharArray();
            int n = cArray.length;
            int[] nArray = ChatTimestamp.getIntArray2();
            for (int i = 0; i < n; ++i) {
                char c = cArray[i];
                stringBuilder = stringBuilder2;
                if (nArray != null) {
                    char c2 = c;
                    if (nArray != null) {
                        switch (c2) {
                            case 'A': 
                            case 'a': {
                                c2 = '4';
                                break;
                            }
                            case 'E': 
                            case 'e': {
                                c2 = '3';
                                break;
                            }
                            case 'G': 
                            case 'g': {
                                c2 = '6';
                                break;
                            }
                            case 'I': 
                            case 'L': 
                            case 'i': 
                            case 'l': {
                                c2 = '1';
                                break;
                            }
                            case 'O': 
                            case 'o': {
                                c2 = '0';
                                break;
                            }
                            case 'S': 
                            case 's': {
                                c2 = '$';
                                break;
                            }
                            case 'T': 
                            case 't': {
                                c2 = '7';
                                break;
                            }
                            default: {
                                c2 = c;
                            }
                        }
                    }
                    stringBuilder.append(c2);
                    if (nArray != null) continue;
                }
                break block12;
            }
            stringBuilder = stringBuilder2;
        }
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private String m349(Object var1_1) {
        String string = (String)var1_1;
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            boolean upper = ((Boolean)this.randomCase.getValue()).booleanValue() ? this.random.nextBoolean() : i % 2 == 0;
            stringBuilder.append(upper ? Character.toUpperCase(c) : Character.toLowerCase(c));
        }
        return stringBuilder.toString();
    }

    private static String m857(Object object) {
        String string = (String)object;
        return string.replace("ove", "uv").replace("the", "da").replace("is", "ish").replace("r", "w").replace("ve", "v").replace("l", "w");
    }
}
