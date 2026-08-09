/*
 * Decompiled with CFR 0.152.
 */
package shit.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import shit.gui.Texture;

@Environment(value=EnvType.CLIENT)
public sealed interface TextureResource {

    @Environment(value=EnvType.CLIENT)
    public record TextureData(Texture texture) implements TextureResource
    {
    }

    @Environment(value=EnvType.CLIENT)
    public static final class ResourceEntry 
    implements TextureResource {
        private final Identifier field8;
        private final boolean flag9;

        public ResourceEntry(Identifier identifier, boolean bl) {
            this.field8 = identifier;
            this.flag9 = bl;
        }

        public Identifier field8() {
            return this.field8;
        }

        public boolean isSet56() {
            return this.flag9;
        }
    }
}

