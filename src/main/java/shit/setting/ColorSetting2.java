/*
 * Decompiled with CFR 0.152.
 */
package shit.setting;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.function.BooleanSupplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.GLFW;
import shit.render.Outline;
import shit.setting.Setting;

@Environment(value=EnvType.CLIENT)
public class ColorSetting2
extends Setting {
    private static final Map map22 = new java.util.LinkedHashMap();
    private Type type5 = Type.Toggle;

    public ColorSetting2(String string, int n) {
        super(string, n);
    }

    public ColorSetting2(String string, int n, BooleanSupplier booleanSupplier) {
        super(string, n, booleanSupplier, null, "", false);
    }

    public boolean isSet148() {
        return ((Integer) this.getValue()).intValue() != 0;
    }

    public static int m559(int n) {
        int n2 = n;
        return -100 - n2;
    }

    public static boolean m232(int n) {
        return n <= -100;
    }

    public static int m1003(int n) {
        int n2 = n;
        return -100 - n2;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public static Integer m911(Object var0) {
        String s = ((String) var0).trim();
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            Object v = map22.get(s.toUpperCase(Locale.ROOT));
            return v instanceof Integer ? (Integer) v : null;
        }
    }

    @Override
    public String getDisplayName() {
        String string;
        int n;
        String string2;
        int n2;
        block10: {
            block11: {
                Object object;
                block13: {
                    int n3;
                    block12: {
                        n2 = (Integer)this.getValue();
                        string2 = Setting.getText54();
                        n = this.isSet148() ? 1 : 0;
                        if (string2 == null) {
                            if (n == 0) {
                                return Outline.m14("None");
                            }
                            n = ColorSetting2.m232(n2) ? 1 : 0;
                        }
                        if (string2 != null) break block10;
                        if (n == 0) break block11;
                        n3 = ColorSetting2.m1003(n2);
                        if (string2 != null) break block12;
                        switch (n3) {
                            case 0: {
                                object = "Mouse Left";
                                break block13;
                            }
                            case 1: {
                                object = "Mouse Right";
                                break block13;
                            }
                            case 2: {
                                object = "Mouse Middle";
                                break block13;
                            }
                            case 3: {
                                object = "Mouse 4";
                                break block13;
                            }
                            case 4: {
                                object = "Mouse 5";
                                break block13;
                            }
                            default: {
                                n3 = ColorSetting2.m1003(n2);
                            }
                        }
                    }
                    object = "Mouse " + n3;
                }
                return (String) object;
            }
            n = n2;
        }
        String string3 = string = GLFW.glfwGetKeyName((int)n, (int)0);
        if (string2 == null) {
            string3 = string3 != null ? string.toUpperCase(Locale.ROOT) : ColorSetting2.m66(n2);
        }
        return string3;
    }

    public Type getType() {
        return this.type5;
    }

    public void setObj23(Object object) {
        Type type;
        this.type5 = type = (Type)((Object)object);
    }

    public void m348() {
        String string = Setting.getText54();
        Type type = this.type5;
        if (string == null) {
            type = type == Type.Toggle ? Type.Hold : Type.Toggle;
        }
        this.type5 = type;
    }

    @Override
    public String getValueString() {
        return String.valueOf(this.getValue()) + ":" + this.type5.name();
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Override
    public void setValueFromString(Object var1_1) {
        String s = (String) var1_1;
        try {
            if (s.contains(":")) {
                String[] parts = s.split(":");
                Integer kc = m911(parts[0]);
                this.setValueInternal(kc != null ? kc : (Integer) this.getDefaultValue());
                try {
                    this.setObj23(Type.valueOf(parts[1]));
                } catch (RuntimeException e) {
                    this.setObj23(Type.Toggle);
                }
            } else {
                Integer kc = m911(s);
                this.setValueInternal(kc != null ? kc : (Integer) this.getDefaultValue());
                this.setObj23(Type.Toggle);
            }
        } catch (RuntimeException e) {
            this.setValueInternal((Integer) this.getDefaultValue());
            this.setObj23(Type.Toggle);
        }
    }

    private static String m66(int n) {
        int n2;
        block3: {
            int n3 = n;
            Iterator iterator = map22.entrySet().iterator();
            String string = Setting.getText54();
            while (iterator.hasNext()) {
                block5: {
                    String string2;
                    block6: {
                        boolean bl = false;
                        Map.Entry entry;
                        block4: {
                            entry = (Map.Entry) iterator.next();
                            n2 = (Integer)entry.getValue();
                            if (string != null) break block3;
                            if (string != null) break block4;
                            if (n2 != n3) break block5;
                            string2 = (String)entry.getKey();
                            if (string != null) break block6;
                            bl = string2.startsWith("MOUSE");
                        }
                        if (bl) break block5;
                        string2 = ColorSetting2.m278((String)entry.getKey());
                    }
                    return string2;
                }
                if (string == null) continue;
            }
            n2 = n3;
        }
        return Integer.toString(n2);
    }

    private static String m278(Object object) {
        String string;
        block4: {
            String string2 = (String)object;
            String[] stringArray = string2.split("_");
            StringBuilder stringBuilder = new StringBuilder();
            String[] stringArray2 = stringArray;
            int n = stringArray2.length;
            String string3 = Setting.getText54();
            for (int i = 0; i < n; ++i) {
                String string4;
                string = string4 = stringArray2[i];
                if (string3 == null) {
                    StringBuilder stringBuilder2;
                    boolean bl = string.isEmpty();
                    if (string3 == null) {
                        if (bl) continue;
                        stringBuilder2 = stringBuilder;
                        if (string3 != null) continue;
                        bl = stringBuilder2.isEmpty();
                    }
                    if (!bl) {
                        stringBuilder.append(' ');
                    }
                    stringBuilder2 = stringBuilder.append(string4.charAt(0)).append(string4.substring(1).toLowerCase(Locale.ROOT));
                    if (string3 == null) continue;
                }
                break block4;
            }
            string = stringBuilder.toString();
        }
        return string;
    }

    private static String m398(Object object) {
        String string = (String)object;
        return string.toUpperCase(Locale.ROOT).replace("GLFW_KEY_", "").replace("GLFW_MOUSE_BUTTON_", "MOUSE_").replace("KEY_", "").replace("MOUSE_BUTTON_", "MOUSE_").replace("-", "_").replace(" ", "_").replace("__", "_");
    }

    /*
     * Unable to fully structure code
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    public static enum Type {
      Toggle, Hold;

      private Type() {}



        private static Type[] getTypeArray5() {
            return new Type[]{Toggle, Hold};
        }
    
   }
}

