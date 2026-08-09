/*
 * Decompiled with CFR 0.152.
 */
package shit.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
@Environment(value=EnvType.CLIENT)
public @interface EventHandler {
    public int priority() default 0;
}

