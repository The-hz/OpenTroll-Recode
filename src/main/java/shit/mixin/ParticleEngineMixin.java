/*
 * Decompiled with CFR 0.152.
 */
package shit.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.ElderGuardianParticle;
import net.minecraft.client.particle.ExplosionLargeParticle;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SpellParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shit.module.render.NoRender;

@Environment(value=EnvType.CLIENT)
@Mixin(value={ParticleManager.class})
public class ParticleEngineMixin {
    @Inject(method={"addParticle(Lnet/minecraft/client/particle/Particle;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void trollhack$noParticle(Particle particle, CallbackInfo callbackInfo) {
        boolean bl;
        NoRender noRender = NoRender.INSTANCE;
        if (noRender == null || !noRender.isEnabled() || particle == null) {
            return;
        }
        boolean bl2 = bl = (Boolean)noRender.effect.getValue() != false && particle instanceof SpellParticle || (Boolean)noRender.guardian.getValue() != false && particle instanceof ElderGuardianParticle || (Boolean)noRender.explosions.getValue() != false && particle instanceof ExplosionLargeParticle || (Boolean)noRender.campFire.getValue() != false && particle instanceof CampfireSmokeParticle || (Boolean)noRender.fireworks.getValue() != false && particle.getClass().getEnclosingClass() == FireworksSparkParticle.class;
        if (bl) {
            callbackInfo.cancel();
        }
    }
}

