package com.dfdyz.epicacg.mixins.compat.oculus;

import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.google.common.collect.Sets;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

@Mixin(ParticleEngine.class)
public abstract class MixinParticleEngineA {
    @Redirect(
            method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;)V"},
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;keySet()Ljava/util/Set;"
            ),
            remap = false
    )
    private Set<ParticleRenderType> epicacg$selectParticlesToRender(Map<ParticleRenderType, Queue<Particle>> instance) {
        Set<ParticleRenderType> keySet = instance.keySet();
        return Sets.filter(keySet, (type) -> !(type instanceof PostParticleRenderType));
    }
}
