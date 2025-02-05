package com.dfdyz.epicacg.client.render.custom;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.dfdyz.epicacg.client.render.targets.TargetManager;
import com.dfdyz.epicacg.registry.PostPasses;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import static com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines.getSource;
import static net.minecraft.client.Minecraft.ON_OSX;

public class SubSpaceRenderType extends PostParticleRenderType {
    public SubSpaceRenderType(ResourceLocation renderTypeID, ResourceLocation texture) {
        super(renderTypeID, texture);
    }

    public RenderTarget getTarget(){
        if(ppl.getBufferTarget() == null){
            var ret = TargetManager.getTarget(ppl.name);
            ret.setClearColor(0,0,0, 1.f);
            ret.clear(ON_OSX);
            return ret;
        }
        return ppl.getBufferTarget();
    }

    @Override
    public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
        RenderSystem.enableBlend();
        RenderSystem.enableCull();
        Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.setShader(this::getShader);

        if(texture != null) RenderUtils.GLSetTexture(texture);
        getPipeline().start();
        setupBufferBuilder(bufferBuilder);
    }

    @Override
    public void setupBufferBuilder(BufferBuilder bufferBuilder) {
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
    }

    @Override
    public PostEffectPipelines.Pipeline getPipeline() {
        return ppl;
    }

    public static final PPL ppl = new PPL(new ResourceLocation(EpicACG.MODID, "sub_space"));

    public static class PPL extends PostEffectPipelines.Pipeline {
        public PPL(ResourceLocation name) {
            super(name);
        }

        public RenderTarget getBufferTarget(){
            return bufferTarget;
        }

        @Override
        public void suspend() {
            if(PostEffectPipelines.isActive()){
                //System.out.println("aaaaa");
                bufferTarget.unbindWrite();
                bufferTarget.unbindRead();
                RenderTarget rt = getSource();
                rt.bindWrite(false);
            }
            else {
                //bufferTarget.clear(Minecraft.ON_OSX);
                getSource().bindWrite(false);
            }
        }

        private static final ResourceLocation tmpTarget
                = new ResourceLocation(EpicACG.MODID, "sub_space_tmp");

        @Override
        public void start() {
            if(started){
                if(PostEffectPipelines.isActive()){
                    //ClientCommands.Debug();
                    //bufferTarget.copyDepthFrom(getSource());
                    bufferTarget.bindWrite(false);
                }
            }
            else {
                if(bufferTarget == null){
                    bufferTarget = TargetManager.getTarget(name);
                    bufferTarget.clear(ON_OSX);
                }

                //RenderTarget main = getSource();
                if(PostEffectPipelines.isActive()){
                    //System.out.println("push")
                    //bufferTarget.copyDepthFrom(main);
                    PostEffectPipelines.PostEffectQueue.add(this);
                    bufferTarget.bindWrite(false);
                    started = true;
                }
                //System.out.println("push");
            }
        }

        @Override
        public void PostEffectHandler() {
            var mask =  SubMaskRenderType.ppl.getBufferTarget();
            if(mask == null) return;
            RenderTarget tmp = TargetManager.getTarget(tmpTarget);
            RenderTarget main = Minecraft.getInstance().getMainRenderTarget();
            PostPasses.mask_composite.process(main, mask, bufferTarget, tmp);
            PostPasses.blit.process(tmp, main);
            TargetManager.ReleaseTarget(tmpTarget);
        }
    }




}
