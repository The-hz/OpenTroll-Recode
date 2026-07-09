/*
 * Decompiled with CFR 0.152.
 */
package shit.manager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import shit.event.Event;
import shit.event.EventHandler;
import shit.module.Module;

@Environment(value=EnvType.CLIENT)
public class EventBus {
    private final Map map40 = new ConcurrentHashMap();
    private final Map map5 = new ConcurrentHashMap();
    private static final String a = null;

    @SuppressWarnings("unchecked")
    public void subscribe(Object object) {
        List<Data> handlers = (List<Data>) this.map5.computeIfAbsent(object, o -> this.m687(o));
        for (Data data : handlers) {
            List<Data> list = (List<Data>) this.map40.computeIfAbsent(data.class_(), k -> new CopyOnWriteArrayList());
            list.add(data);
            ((List<Data>) this.map40.get(data.class_())).sort(Comparator.comparingInt(Data::count).reversed());
        }
    }

    @SuppressWarnings("unchecked")
    public void setObj18(Object object) {
        List<Data> handlers = (List<Data>) this.map5.get(object);
        if (handlers == null) {
            return;
        }
        for (Data data : handlers) {
            List<Data> list = (List<Data>) this.map40.get(data.class_());
            if (list != null) {
                list.remove(data);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Event m287(Object object) {
        Event event = (Event) object;
        List<Data> list = (List<Data>) this.map40.get(event.getClass());
        if (list == null) {
            return event;
        }
        for (Data data : list) {
            data.setEvent(event);
            if (event.isSet85()) {
                break;
            }
        }
        return event;
    }

    private List m687(Object object) {
        ArrayList<Data> arrayList = new ArrayList<Data>();
        int n = 27;
        for (Class<?> clazz = object.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            for (Method method : clazz.getDeclaredMethods()) {
                EventHandler eventHandler = method.getAnnotation(EventHandler.class);
                if (!this.m111(method, eventHandler) || !Event.class.isAssignableFrom(method.getParameterTypes()[0])) continue;
                method.setAccessible(true);
                arrayList.add(new Data(object, method, method.getParameterTypes()[0], eventHandler != null ? eventHandler.priority() : 0));
                if (27 != 0) continue;
            }
            if (27 != 0) continue;
        }
        return arrayList;
    }

    private boolean m111(Object object, Object object2) {
        Method method;
        block7: {
            block6: {
                method = (Method)object;
                EventHandler eventHandler = (EventHandler)object2;
                int n = 27;
                if (eventHandler != null) {
                    return method.getParameterCount() == 1;
                }
                if (method.isBridge()) break block6;
                if (!method.isSynthetic()) break block7;
            }
            return false;
        }
        if (method.getParameterCount() != 1) {
            return false;
        }
        if (a == null || !method.getName().startsWith(a)) {
            return false;
        }
        return method.getReturnType() == Void.TYPE;
    }

    /*
     * Handled unverifiable bytecode (illegal stack merge).
     * Handled impossible loop by duplicating code
     * Enabled aggressive block sorting
     */
    static {}

    @Environment(value=EnvType.CLIENT)
    record Data(Object field, Method method, Class class_, int count) {
        private static final String a = null;

        private void setEvent(Event event) {
            try {
                this.method.invoke(this.field, event);
            }
            catch (java.lang.reflect.InvocationTargetException ite) {
                Throwable cause = ite.getCause();
                if (cause instanceof UnsupportedOperationException) {
                    return;
                }
                shit.misc.Logger.logger2.warn("Handler {}.{} failed: {}", this.method.getDeclaringClass().getSimpleName(),
                        this.method.getName(), String.valueOf(cause));
                return;
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                throw new IllegalStateException("Error dispatching " + event.getClass().getSimpleName(), reflectiveOperationException);
            }
        }

        /*
         * Handled unverifiable bytecode (illegal stack merge).
         * Handled impossible loop by duplicating code
         * Enabled aggressive block sorting
         */
        static {}
    }
}

