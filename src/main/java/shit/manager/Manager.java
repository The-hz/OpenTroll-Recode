/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public class Manager {
    private final Set set8 = new TreeSet(String.CASE_INSENSITIVE_ORDER);

    public boolean addFriend(Object object) {
        String string = (String)object;
        return this.set8.add(string);
    }

    public boolean removeFriend(Object object) {
        String string = (String)object;
        return this.set8.remove(string);
    }

    public boolean isFriend(Object object) {
        String string = (String)object;
        return this.set8.contains(string);
    }

    public Set getFriends() {
        return Collections.unmodifiableSet(this.set8);
    }

    public void clearFriends() {
        this.set8.clear();
    }
}

