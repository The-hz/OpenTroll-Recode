/*
 * Decompiled with CFR 0.152.
 */
package shit.misc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.GLFW;
import shit.module.hud.AbstractHudModule;
import shit.util.MC;

@Environment(value=EnvType.CLIENT)
public class CPS
extends AbstractHudModule {
    private final Deque deque3 = new ArrayDeque();
    private final Deque deque = new ArrayDeque();
    private boolean flag18;
    private boolean flag157;

    public CPS() {
        super("CPS", "Shows mouse clicks per second.", 6, 210);
    }

    @Override
    protected List lines() {
        boolean bl = true;
        long l = MC.client3.getWindow() == null ? 0L : MC.client3.getWindow().getHandle();
        long l2 = System.currentTimeMillis();
        if (l != 0L) {
            this.m695(l2, GLFW.glfwGetMouseButton((long)l, (int)0) == 1, this.deque3, true);
            this.m695(l2, GLFW.glfwGetMouseButton((long)l, (int)1) == 1, this.deque, false);
        }
        this.m769(l2, this.deque3);
        this.m769(l2, this.deque);
        return List.of("CPS " + this.deque3.size() + " | " + this.deque.size());
    }

    private void m695(long l, boolean bl, Object object, boolean bl2) {
        block3: {
            boolean bl3;
            block2: {
                long l2 = l;
                bl3 = bl;
                Deque deque = (Deque)object;
                boolean bl4 = bl2;
                boolean bl5 = true;
                boolean bl6 = bl4 ? this.flag18 : this.flag157;
                if (bl3) {
                    if (!bl6) {
                        deque.addLast(l2);
                    }
                }
                if (!bl4) break block2;
                this.flag18 = bl3;
                if (true) break block3;
            }
            this.flag157 = bl3;
        }
    }

    private void m769(long l, Object object) {
        long l2 = l;
        Deque deque = (Deque)object;
        boolean bl = true;
        while (!deque.isEmpty() && l2 - (Long)deque.peekFirst() > 1000L) {
            deque.removeFirst();
            if (true) continue;
            break;
        }
    }
}

