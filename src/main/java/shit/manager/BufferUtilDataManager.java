/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.lang.invoke.LambdaMetafactory;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.lwjgl.stb.STBTruetype;
import shit.api.BufferUtilDataListener;
import shit.data.BufferData;
import shit.data.BufferUtilData;
import shit.misc.BufferUtil;
import shit.misc.BufferUtil2;
import shit.module.Module;
import shit.render.ShaderRenderer;

@Environment(value=EnvType.CLIENT)
public class BufferUtilDataManager
implements BufferUtilDataListener {
    private static final AtomicInteger atomicInteger = new AtomicInteger();
    private static final ExecutorService executorService = null;
    public final BufferUtil2 bufferUtil2;
    private final BufferUtilData[] bufferUtilDatas = new BufferUtilData[128];
    private final int[] counts21 = new int[128];
    private final boolean[] flags2 = new boolean[128];
    private final HashMap hashMap2 = new HashMap();
    private final HashMap hashMap3 = new HashMap();
    private final HashMap hashMap = new HashMap();
    private final Set set9 = new LinkedHashSet();
    private final List list35 = new ArrayList();
    private BufferUtil bufferUtil;
    private int count54 = 0;
    private long time35;
    private long time6;
    private static int count212;
    private static long time14;
    private static long time52;
    private static int count49;
    private static final String a = null;

    public BufferUtilDataManager(Identifier identifier) {
        this.bufferUtil2 = new BufferUtil2(identifier, 48, 4);
        Arrays.fill(this.counts21, Integer.MIN_VALUE);
    }

    @Override
    public void setInt18(int n) {
        int n2 = n;
        this.setInt13(n2);
    }

    public void setInt13(int var1_1) {
        int ch = var1_1;
        if (this.m275(ch)) {
            return;
        }
        CompletableFuture completableFuture = this.m243(ch);
        this.set9.remove(Character.valueOf((char)ch));
        BufferData bufferData;
        try {
            bufferData = completableFuture != null ? (BufferData)completableFuture.join() : this.bufferUtil2.m728(ch);
        } catch (RuntimeException runtimeException) {
            bufferData = this.bufferUtil2.m728(ch);
        }
        this.m42(ch, bufferData);
    }

    public int m360(int n) {
        Module[] moduleArray;
        char c;
        block8: {
            BufferUtilData bufferUtilData;
            block7: {
                c = (char)n;
                BufferUtilData bufferUtilData2 = this.m705(c);
                moduleArray = ShaderRenderer.getModuleArray();
                bufferUtilData = bufferUtilData2;
                if (moduleArray == null) break block7;
                if (bufferUtilData == null) break block8;
                bufferUtilData = bufferUtilData2;
            }
            return bufferUtilData.count21();
        }
        int n2 = BufferUtilDataManager.m63(c) ? 1 : 0;
        if (moduleArray != null) {
            if (n2 != 0) {
                int n3;
                int n4 = n3 = this.counts21[c];
                if (moduleArray != null) {
                    if (n4 != Integer.MIN_VALUE) {
                        return n3;
                    }
                    this.counts21[c] = n3 = this.bufferUtil2.m768(c);
                    n4 = n3;
                }
                return n4;
            }
            n2 = (Integer)this.hashMap3.computeIfAbsent(Character.valueOf(c), (java.util.function.Function<Character, Integer>)this.bufferUtil2::m768);
        }
        return n2;
    }

    public void setObj63(Object object) {
        String string = (String)object;
        this.m947();
        this.requestMissingChars(string);
    }

    public void setObj108(Object object) {
        String string = (String)object;
        this.requestMissingChars(string);
        this.m947();
    }

    private void requestMissingChars(Object object) {
        String string = (String)object;
        int n = 0;
        Module[] moduleArray = ShaderRenderer.getModuleArray();
        while (n < string.length()) {
            block3: {
                block4: {
                    char c;
                    BufferUtilDataManager bufferUtilDataManager;
                    char c2;
                    block7: {
                        char c3;
                        block6: {
                            block5: {
                                c2 = string.charAt(n);
                                if (moduleArray == null) break block3;
                                if (c2 == ' ') break block4;
                                c3 = c2;
                                if (moduleArray == null) break block5;
                                if (c3 == '\n') break block4;
                                c3 = (char)(this.m275(c2) ? 1 : 0);
                            }
                            if (moduleArray == null) break block6;
                            if (c3 != '\u0000') break block4;
                            bufferUtilDataManager = this;
                            c = c2;
                            if (moduleArray == null) break block7;
                            c3 = (char)(bufferUtilDataManager.m666(c) ? 1 : 0);
                        }
                        if (c3 != '\u0000' && moduleArray != null) break block4;
                        bufferUtilDataManager = this;
                        c = c2;
                    }
                    bufferUtilDataManager.m418(c, CompletableFuture.supplyAsync(() -> this.bufferUtil2.m728(c2), executorService));
                }
                ++n;
            }
            if (moduleArray != null) continue;
        }
    }

    public void m947() {
        Set set;
        Module[] moduleArray;
        block25: {
            boolean bl;
            block22: {
                block24: {
                    boolean bl2;
                    block23: {
                        Module[] moduleArray2 = ShaderRenderer.getModuleArray();
                        this.m522();
                        moduleArray = moduleArray2;
                        bl2 = this.hashMap.isEmpty();
                        if (moduleArray == null) break block23;
                        if (!bl2) break block24;
                        bl2 = this.set9.isEmpty();
                    }
                    if (bl2) {
                        return;
                    }
                }
                ArrayList<Character> arrayList = null;
                for (Map.Entry entry : (java.util.Set<Map.Entry>)this.hashMap.entrySet()) {
                    bl = ((CompletableFuture)entry.getValue()).isDone();
                    if (moduleArray != null) {
                        if (bl) {
                            ArrayList<Character> arrayList2 = arrayList;
                            if (moduleArray != null) {
                                if (arrayList2 == null) {
                                    arrayList = new ArrayList<Character>();
                                }
                                arrayList2 = arrayList;
                            }
                            arrayList2.add((Character)entry.getKey());
                        }
                        if (moduleArray != null) continue;
                    }
                    break block22;
                }
                if (arrayList != null) {
                    this.set9.addAll(arrayList);
                }
                set = this.set9;
                if (moduleArray == null) break block25;
                bl = set.isEmpty();
            }
            if (bl) {
                return;
            }
            set = this.set9;
        }
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            block27: {
                int n;
                block29: {
                    CompletableFuture completableFuture;
                    int c;
                    block28: {
                        CompletableFuture completableFuture2;
                        block26: {
                            int c2 = this.isSet107() ? 1 : 0;
                            if (moduleArray != null) {
                                if (c2 == 0) break;
                                c2 = ((Character)((Object)iterator.next())).charValue();
                            }
                            c = c2;
                            iterator.remove();
                            completableFuture2 = completableFuture = this.m243(c);
                            if (moduleArray == null) break block26;
                            if (completableFuture2 == null) break block27;
                            completableFuture2 = completableFuture;
                        }
                        n = completableFuture2.isCompletedExceptionally() ? 1 : 0;
                        if (moduleArray == null) break block28;
                        if (n != 0) break block27;
                        n = completableFuture.isCancelled() ? 1 : 0;
                    }
                    if (moduleArray == null) break block29;
                    if (n != 0) break block27;
                    this.m42(c, (BufferData)completableFuture.join());
                    n = count49 + 1;
                }
                count49 = n;
            }
            if (moduleArray != null) continue;
        }
    }

    public long getLong15() {
        return this.time35;
    }

    public long getLong14() {
        return this.time6;
    }

    private void m522() {
        long l = time14;
        Module[] moduleArray = ShaderRenderer.getModuleArray();
        long l2 = l - time52;
        long l3 = l2 == 0L ? (int)0 : (l2 < 0L ? (int)-1 : 1);
        if (moduleArray != null) {
            if (l3 == 0) {
                return;
            }
            time52 = l;
            l3 = 0;
        }
        count49 = (int)l3;
    }

    private boolean isSet107() {
        Module[] moduleArray = ShaderRenderer.getModuleArray();
        int n = count49;
        if (moduleArray != null) {
            n = n < count212 ? 1 : 0;
        }
        return n != 0;
    }

    private void m42(int n, Object object) {
        BufferUtil.Vec4f vec4f;
        Module[] moduleArray;
        BufferData bufferData;
        int n2;
        block10: {
            block9: {
                BufferData bufferData2;
                block8: {
                    n2 = n;
                    bufferData = (BufferData)object;
                    moduleArray = ShaderRenderer.getModuleArray();
                    bufferData2 = bufferData;
                    if (moduleArray == null) break block8;
                    if (bufferData2 == null) break block9;
                    bufferData2 = bufferData;
                }
                if (bufferData2.byteBuffer() != null) break block10;
            }
            return;
        }
        BufferUtil bufferUtil = this.bufferUtil;
        if (moduleArray != null) {
            if (bufferUtil == null) {
                this.m434();
            }
            bufferUtil = this.bufferUtil;
        }
        BufferUtil.Vec4f vec4f2 = vec4f = bufferUtil.m359(bufferData);
        if (moduleArray != null) {
            if (vec4f2 == null) {
                this.m434();
                vec4f = this.bufferUtil.m359(bufferData);
            }
            vec4f2 = vec4f;
        }
        if (vec4f2 != null) {
            BufferUtilData bufferUtilData = new BufferUtilData(this.bufferUtil, vec4f, bufferData.getInt32(), bufferData.getInt42(), bufferData.count40(), bufferData.count41(), bufferData.getInt37());
            this.m643(n2, bufferUtilData);
            ++this.time35;
        }
        this.setObj54(bufferData);
    }

    private void m434() {
        this.bufferUtil = new BufferUtil(this.count54);
        this.list35.add(this.bufferUtil);
        ++this.count54;
    }

    private void setObj54(Object object) {
        block3: {
            ByteBuffer byteBuffer;
            block4: {
                BufferData bufferData;
                Module[] moduleArray;
                BufferData bufferData2;
                block2: {
                    bufferData2 = (BufferData)object;
                    moduleArray = ShaderRenderer.getModuleArray();
                    bufferData = bufferData2;
                    if (moduleArray == null) break block2;
                    if (bufferData == null) break block3;
                    bufferData = bufferData2;
                }
                byteBuffer = bufferData.byteBuffer();
                if (moduleArray == null) break block4;
                if (byteBuffer == null) break block3;
                byteBuffer = bufferData2.byteBuffer();
            }
            STBTruetype.stbtt_FreeSDF((ByteBuffer)byteBuffer);
        }
    }

    @Override
    public BufferUtilData m705(int n) {
        int n2 = n;
        if (BufferUtilDataManager.m63(n2)) {
            return this.bufferUtilDatas[n2];
        }
        return (BufferUtilData)this.hashMap2.get(Character.valueOf((char)n2));
    }

    private void m643(int n, Object object) {
        block2: {
            BufferUtilData bufferUtilData;
            char c;
            block1: {
                Module[] moduleArray;
                block0: {
                    c = (char)n;
                    bufferUtilData = (BufferUtilData)object;
                    moduleArray = ShaderRenderer.getModuleArray();
                    if (moduleArray == null) break block0;
                    if (!BufferUtilDataManager.m63(c)) break block1;
                    this.bufferUtilDatas[c] = bufferUtilData;
                }
                if (moduleArray != null) break block2;
            }
            this.hashMap2.put(Character.valueOf(c), bufferUtilData);
        }
    }

    private boolean m275(int n) {
        int n2 = n;
        return this.m705(n2) != null;
    }

    private void m418(int n, Object object) {
        block3: {
            block2: {
                char c = (char)n;
                CompletableFuture completableFuture = (CompletableFuture)object;
                Module[] moduleArray = ShaderRenderer.getModuleArray();
                BufferUtilDataManager bufferUtilDataManager = this;
                if (moduleArray == null) break block2;
                bufferUtilDataManager.hashMap.put(Character.valueOf(c), completableFuture);
                if (!BufferUtilDataManager.m63(c)) break block3;
                bufferUtilDataManager = this;
            }
            this.flags2[(char)n] = true;
        }
    }

    private CompletableFuture m243(int n) {
        CompletableFuture completableFuture;
        block0: {
            char c = (char)n;
            completableFuture = (CompletableFuture)this.hashMap.remove(Character.valueOf(c));
            if (!BufferUtilDataManager.m63(c)) break block0;
            this.flags2[c] = false;
        }
        return completableFuture;
    }

    private boolean m666(int n) {
        char c = (char)n;
        Module[] moduleArray = ShaderRenderer.getModuleArray();
        boolean bl = BufferUtilDataManager.m63(c);
        if (moduleArray != null) {
            if (bl) {
                return this.flags2[c];
            }
            bl = this.hashMap.containsKey(Character.valueOf(c));
        }
        return bl;
    }

    private static boolean m63(int n) {
        int bl = n;
        Module[] moduleArray = ShaderRenderer.getModuleArray();
        boolean bl2 = false;
        if (moduleArray != null) {
            bl2 = bl < 128;
        }
        return bl2;
    }

    private static /* synthetic */ Thread cfrlam$static$0(Runnable runnable) {
        Thread thread = new Thread(runnable, a + atomicInteger.incrementAndGet());
        thread.setDaemon(true);
        return thread;
    }

    /*
     * Handled unverifiable bytecode (illegal stack merge).
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    static {}
}

