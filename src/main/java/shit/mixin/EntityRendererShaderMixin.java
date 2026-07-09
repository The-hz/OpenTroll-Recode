/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EndCrystalEntityRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.module.combat.CrystalChams;
import shit.module.render.Chams;
import shit.module.render.Shaders;

@Environment(value=EnvType.CLIENT)
@Mixin(value={EntityRenderer.class})
public class EntityRendererShaderMixin {
    @Inject(method={"updateRenderState(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/entity/state/EntityRenderState;F)V"}, at={@At(value="TAIL")})
    private void m905(Entity entity, EntityRenderState entityRenderState, float f, CallbackInfo callbackInfo) {
        Shaders shaders;
        Object chams;
        CrystalChams crystalChams = CrystalChams.INSTANCE;
        if (entity instanceof EndCrystalEntity) {
            chams = (EndCrystalEntity)entity;
            if (entityRenderState instanceof EndCrystalEntityRenderState && crystalChams != null) {
                crystalChams.m938(chams, entityRenderState);
            }
        }
        Chams chamsMod = Chams.INSTANCE;
        if (chamsMod != null) {
            chamsMod.m295(entity, entityRenderState);
        }
        if ((shaders = Shaders.INSTANCE) == null || !shaders.isSet19()) {
            return;
        }
        if (!shaders.m459(entity)) {
            return;
        }
        int n = shaders.m743(entity);
        if (n != 0) {
            entityRenderState.outlineColor = ColorHelper.fullAlpha((int)n);
            shaders.flag126 = true;
        }
    }
}

