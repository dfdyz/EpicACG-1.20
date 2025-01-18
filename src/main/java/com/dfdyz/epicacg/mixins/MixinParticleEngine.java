package com.dfdyz.epicacg.mixins;


import com.dfdyz.epicacg.client.render.pipeline.ParticleEngineHelper;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.irisshaders.iris.fantastic.ParticleRenderingPhase;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(ParticleEngine.class)
public abstract class MixinParticleEngine {

    @Shadow
    @Final
    private Map<ParticleRenderType, Queue<Particle>> particles;

    @Shadow
    @Final
    private TextureManager textureManager;

    @Unique
    private final PriorityQueue<ParticleEngineHelper.PostParticles>  epicacg$renderQueue = ParticleEngineHelper.createQueue();

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/culling/Frustum;)V",
            at = @At(value = "TAIL" ),
            remap = false
    )
    private void injectRenderPost(PoseStack posestack_, MultiBufferSource.BufferSource bufferSource, LightTexture lightTexture, Camera camera, float p_107341_, Frustum clippingHelper, CallbackInfo ci){
        if(!PostEffectPipelines.isActive()) return;

        //System.out.println("PostParticle Batch");

        lightTexture.turnOnLightLayer();
        RenderSystem.enableDepthTest();
        RenderSystem.activeTexture(33986);
        RenderSystem.activeTexture(33984);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.mulPoseMatrix(posestack_.last().pose());
        RenderSystem.applyModelViewMatrix();

        epicacg$renderQueue.clear();
        Iterator<?> rendertypes = particles.keySet().iterator();
        while (rendertypes.hasNext()){
            if(rendertypes.next() instanceof PostParticleRenderType pprt){
                epicacg$renderQueue.add(new ParticleEngineHelper.PostParticles(pprt, particles.get(pprt)));
            }
        }

        ParticleEngineHelper.PostParticles queueItem;
        PostParticleRenderType particlerendertype;
        while (!epicacg$renderQueue.isEmpty()) {
            queueItem = epicacg$renderQueue.poll();
            particlerendertype = queueItem.rt();

            //System.out.println("Now Render:"+particlerendertype.getClass().getName());

            RenderSystem.setShader(GameRenderer::getParticleShader);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = tesselator.getBuilder();
            particlerendertype.begin(bufferbuilder, textureManager);
            var particleIterator = queueItem.particles().iterator();

            Particle particle;
            while (particleIterator.hasNext()) {
                particle = particleIterator.next();
                if (clippingHelper == null
                        || !particle.shouldCull()
                        || clippingHelper.isVisible(particle.getBoundingBox())) {
                    try {
                        particlerendertype.callPipeline();
                        particle.render(bufferbuilder, camera, p_107341_);
                    } catch (Throwable var18) {
                        CrashReport crashreport = CrashReport.forThrowable(var18, "Rendering Particle" );
                        CrashReportCategory crashreportcategory = crashreport.addCategory("Particle being rendered" );
                        Objects.requireNonNull(particle);
                        crashreportcategory.setDetail("Particle", particle::toString);
                        Objects.requireNonNull(particlerendertype);
                        crashreportcategory.setDetail("Particle Type", particlerendertype::toString);
                        throw new ReportedException(crashreport);
                    }
                }
            }
            particlerendertype.end(tesselator);
        }

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        lightTexture.turnOffLightLayer();
    }



}
