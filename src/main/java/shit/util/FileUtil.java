/*
 * Decompiled with CFR 0.152.
 */
package shit.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import shit.util.SystemUtil;

@Environment(value=EnvType.CLIENT)
final class FileUtil {
    private static final byte[] counts27 = new byte[0];
    private static final Path path4 = null;

    private FileUtil() {
    }

    static void m706(Object object, long l) {
        try {
            String string = (String)object;
            long l2 = l;
            String string2 = string + "|" + l2;
            byte[] byArray = FileUtil.m429(16);
            byte[] byArray2 = FileUtil.m429(12);
            byte[] byArray3 = FileUtil.m529(byArray);
            byte[] byArray4 = string2.getBytes(StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(1, (Key)new SecretKeySpec(byArray3, "AES"), new GCMParameterSpec(128, byArray2));
            byte[] byArray5 = cipher.doFinal(FileUtil.m995(byArray4, byArray3, byArray2));
            ByteBuffer byteBuffer = ByteBuffer.allocate(1 + byArray.length + byArray2.length + byArray5.length);
            byteBuffer.put((byte)1);
            byteBuffer.put(byArray);
            byteBuffer.put(byArray2);
            byteBuffer.put(byArray5);
            String string3 = Base64.getUrlEncoder().withoutPadding().encodeToString(byteBuffer.array());
            Files.createDirectories(path4.getParent(), new FileAttribute[0]);
            Files.writeString(path4, (CharSequence)string3, StandardCharsets.UTF_8, new OpenOption[0]);
        }
        catch (Exception exception) {}
    }

    static String getText56() {
        String[] stringArray = FileUtil.getTextArray4();
        Object var1_1 = null;
        return stringArray != null ? stringArray[0] : "";
    }

    static long getLong9() {
        String[] stringArray = FileUtil.getTextArray4();
        Object var1_1 = null;
        if (stringArray != null) {
            try {
                return Long.parseLong(stringArray[1]);
            }
            catch (NumberFormatException numberFormatException) {}
        }
        return 0L;
    }

    static boolean isSet20() {
        return Files.isRegularFile(path4, new LinkOption[0]);
    }

    private static String[] getTextArray4() {
        Object var1 = null;
        try {
            ByteBuffer byteBuffer;
            block9: {
                block8: {
                    if (!Files.isRegularFile(path4, new LinkOption[0])) {
                        return null;
                    }
                    String string = Files.readString(path4, StandardCharsets.UTF_8).trim();
                    if (string.isEmpty()) {
                        return null;
                    }
                    byte[] byArray = Base64.getUrlDecoder().decode(string);
                    byteBuffer = ByteBuffer.wrap(byArray);
                    int n = Byte.toUnsignedInt(byteBuffer.get());
                    if (n != 1) break block8;
                    if (byteBuffer.remaining() >= 28) break block9;
                }
                return null;
            }
            byte[] byArray = new byte[16];
            byte[] byArray2 = new byte[12];
            byteBuffer.get(byArray);
            byteBuffer.get(byArray2);
            byte[] byArray3 = new byte[byteBuffer.remaining()];
            byteBuffer.get(byArray3);
            byte[] byArray4 = FileUtil.m529(byArray);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(2, (Key)new SecretKeySpec(byArray4, "AES"), new GCMParameterSpec(128, byArray2));
            byte[] byArray5 = FileUtil.m995(cipher.doFinal(byArray3), byArray4, byArray2);
            String string = new String(byArray5, StandardCharsets.UTF_8);
            int n = string.indexOf(124);
            if (n <= 0) {
                return null;
            }
            return new String[]{string.substring(0, n), string.substring(n + 1)};
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static byte[] m529(Object object) {
        byte[] byArray = (byte[])object;
        byte[] byArray2 = SystemUtil.getIntArray5();
        byte[] byArray3 = new byte[counts27.length + byArray2.length];
        System.arraycopy(counts27, 0, byArray3, 0, counts27.length);
        System.arraycopy(byArray2, 0, byArray3, counts27.length, byArray2.length);
        PBEKeySpec pBEKeySpec = new PBEKeySpec(FileUtil.m686(byArray3).toCharArray(), byArray, 20000, 256);
        try {
            byte[] byArray4 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(pBEKeySpec).getEncoded();
            return byArray4;
        }
        catch (java.security.NoSuchAlgorithmException | java.security.spec.InvalidKeySpecException exception) {
            throw new RuntimeException(exception);
        }
        finally {
            pBEKeySpec.clearPassword();
        }
    }

    private static byte[] m995(Object object, Object object2, Object object3) {
        byte[] byArray = (byte[])object;
        byte[] byArray2 = (byte[])object2;
        byte[] byArray3 = (byte[])object3;
        byte[] byArray4 = Arrays.copyOf(byArray, byArray.length);
        Object var7_7 = null;
        for (int i = 0; i < byArray4.length; ++i) {
            int n = i;
            byArray4[n] = (byte)(byArray4[n] ^ (byArray2[i % byArray2.length] ^ byArray3[i % byArray3.length]));
            if (null == null) continue;
            break;
        }
        return byArray4;
    }

    private static byte[] m429(int n) {
        int n2 = n;
        byte[] byArray = new byte[n2];
        new SecureRandom().nextBytes(byArray);
        return byArray;
    }

    private static String m686(Object object) {
        byte[] byArray = (byte[])object;
        StringBuilder stringBuilder = new StringBuilder(byArray.length * 2);
        byte[] byArray2 = byArray;
        int n = byArray2.length;
        Object var3_6 = null;
        for (int i = 0; i < n; ++i) {
            byte by = byArray2[i];
            stringBuilder.append(String.format("%02x", by & 0xFF));
            if (null == null) continue;
            break;
        }
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     */
    static {}
}

