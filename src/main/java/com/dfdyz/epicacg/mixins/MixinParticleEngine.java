package com.dfdyz.epicacg.mixins;


import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import java.util.Queue;

@Mixin(ParticleEngine.class)
public abstract class MixinParticleEngine {

    @Shadow
    @Final
    private Map<ParticleRenderType, Queue<Particle>> particles;


    @ModifyReceiver(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/particle/ParticleRenderType;begin(Lcom/mojang/blaze3d/vertex/BufferBuilder;Lnet/minecraft/client/renderer/texture/TextureManager;)V"))
    private ParticleRenderType injectRenderPre(ParticleRenderType particlerendertype, BufferBuilder bufferBuilder, TextureManager textureManager) {
        if (particlerendertype instanceof PostParticleRenderType pprt && this.particles.get(particlerendertype).size() > 0) {
            if(PostEffectPipelines.isActive()){
                pprt.callPipeline();
            }
        }
        return particlerendertype;
    }
}
