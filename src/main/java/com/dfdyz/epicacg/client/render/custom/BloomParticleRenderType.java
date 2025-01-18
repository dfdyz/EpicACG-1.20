package com.dfdyz.epicacg.client.render.custom;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.dfdyz.epicacg.client.render.targets.ScaledTarget;
import com.dfdyz.epicacg.config.ClientConfig;
import com.dfdyz.epicacg.registry.PostPasses;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static net.minecraft.client.Minecraft.ON_OSX;

public class BloomParticleRenderType extends PostParticleRenderType {
    public BloomParticleRenderType(ResourceLocation renderTypeID, ResourceLocation tex) {
        super(renderTypeID, tex);
    }

    @Override
    public PostEffectPipelines.Pipeline getPipeline() {
        return ppl;
    }

    static final PostEffectPipelines.Pipeline ppl = new Pipeline(new ResourceLocation(EpicACG.MODID, "bloom_particle"));

    public static class Pipeline extends PostEffectPipelines.Pipeline{
        public Pipeline(ResourceLocation name) {
            super(name);
        }

        void handlePasses(RenderTarget src){
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL12.GL_LINEAR);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL12.GL_LINEAR);

            if(ClientConfig.cfg.BloomMode == 0){
                PostPasses.downSampler.process(src, blur[0]);    //2
                PostPasses.downSampler.process(blur[0], blur[1]);//4
                PostPasses.downSampler.process(blur[1], blur[2]);//8
                PostPasses.downSampler.process(blur[2], blur[3]);//16
                PostPasses.downSampler.process(blur[3], blur[4]);//32
                //PostEffects.downSampler.process(blur[4], blur[5]);//64

                //PostEffects.upSampler.process(blur[5], blur_[4], blur[4]);   // 64 -> 32_
                PostPasses.upSampler.process(blur[4], blur_[3], blur[3]);  // 32 -> 16_
                PostPasses.upSampler.process(blur_[3], blur_[2], blur[2]);   // 16 -> 8_
                PostPasses.upSampler.process(blur_[2], blur_[1], blur[1]);  // 8_ -> 4_
                PostPasses.upSampler.process(blur_[1], blur_[0], blur[0]);  // 4_ -> 2_

                PostPasses.unity_composite.process(blur_[0], temp, src, Minecraft.getInstance().getMainRenderTarget());

                PostPasses.blit.process(temp, Minecraft.getInstance().getMainRenderTarget());
            }
            else {
                PostPasses.blur.process(src, blur[0], 1, 0 ,3);     // src -> 2
                PostPasses.blur.process(blur[0], blur_[0], 0,1 ,3); // 2 -> 2_
                PostPasses.blur.process(blur_[0], blur[1], 1, 0 ,5); // 2_ -> 4
                PostPasses.blur.process(blur[1], blur_[1], 1, 0 ,5);     // 4 -> 4_
                PostPasses.blur.process(blur_[1], blur[2], 1, 0 ,7); // 4_ -> 8
                PostPasses.blur.process(blur[2], blur_[2], 1, 0 ,7);     // 8 -> 8_
                PostPasses.blur.process(blur_[2], blur[3], 1, 0 ,9); // 8_ -> 16
                PostPasses.blur.process(blur[3], blur_[3], 1, 0 ,9);     // 16 -> 16_

                PostPasses.ue_composite.process(src, temp, blur_[0], blur_[1], blur_[2], blur_[3]);

                PostPasses.blit.process(temp, Minecraft.getInstance().getMainRenderTarget());

            }

        }

        //private static ResourceLocation bloom_particle_target = new ResourceLocation(EpicACG.MODID, "bloom_particle_target");
        //private static ResourceLocation bloom_particle_blur = new ResourceLocation(EpicACG.MODID, "bloom_particle_blur");
        //private static ResourceLocation bloom_particle_temp = new ResourceLocation(EpicACG.MODID, "bloom_particle_temp");

        RenderTarget[] blur;
        RenderTarget[] blur_;
        RenderTarget temp;

        void initTargets(){
            int cnt = 5;

            if(blur == null){
                blur = new RenderTarget[cnt];
                float s = 1.f;
                for (int i = 0; i < blur.length; i++) {
                    s /= 2;
                    blur[i] = new ScaledTarget(s, s, bufferTarget.width, bufferTarget.height, false, ON_OSX);
                    blur[i].setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                    blur[i].clear(ON_OSX);
                    if(bufferTarget.isStencilEnabled()) blur[i].enableStencil();
                }
            }

            if(blur_ == null){
                blur_ = new RenderTarget[cnt-1];
                float s = 1.f;
                for (int i = 0; i < blur_.length; i++) {
                    s /= 2;
                    blur_[i] = new ScaledTarget(s, s, bufferTarget.width, bufferTarget.height, false, ON_OSX);
                    blur_[i].setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                    blur_[i].clear(ON_OSX);
                    if(bufferTarget.isStencilEnabled()) blur[i].enableStencil();
                }
            }

            if(temp == null){
                temp = createTempTarget(bufferTarget);
            }

            if(temp.width != bufferTarget.width || temp.height != bufferTarget.height){
                for (int i = 0; i < blur.length; i++) {
                    blur[i].resize(bufferTarget.width, bufferTarget.height, ON_OSX);
                }

                for (int i = 0; i < blur_.length; i++) {
                    blur_[i].resize(bufferTarget.width, bufferTarget.height, ON_OSX);
                }
                temp.resize(bufferTarget.width, bufferTarget.height, ON_OSX);
            }
        }

        @Override
        public void PostEffectHandler() {
            initTargets();
            handlePasses(bufferTarget);
            //oldHandel(target);
        }
    }

    private static int NumMul(int a, float b){
        return (int)(a * Math.max(Math.min(b, 1.5f), 0.8f));
    }

}
