/*
 * Decompiled with CFR 0.152.
 */
package shit.type;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(value=EnvType.CLIENT)
public enum RenderShapeType {
      SHADOW, ROUND_RECT, ROUND_RECT_OUTLINE, RECT, TRIANGLE, TEXTURE, TEXT;

      private RenderShapeType() {}



    private static /* synthetic */ RenderShapeType[] getObjArray2() {
        return new RenderShapeType[]{SHADOW, ROUND_RECT, ROUND_RECT_OUTLINE, RECT, TRIANGLE, TEXTURE, TEXT};
    }

   }

