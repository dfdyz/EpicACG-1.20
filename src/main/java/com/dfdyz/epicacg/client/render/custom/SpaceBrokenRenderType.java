package com.dfdyz.epicacg.client.render.custom;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.dfdyz.epicacg.client.render.targets.TargetManager;
import com.dfdyz.epicacg.registry.PostPasses;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import static com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines.*;

public class SpaceBrokenRenderType extends PostParticleRenderType {

    final int layer;
    final int vertex;
    public SpaceBrokenRenderType(ResourceLocation name, ResourceLocation texture, int layer, int vertexCount){
        super(name ,texture);
        this.layer = layer;
        vertex = vertexCount;
    }

    public SpaceBrokenRenderType(ResourceLocation name, int layer){
        super(name ,RenderUtils.GetTexture("particle/sparks"));
        this.layer = layer;
        vertex = 3;
    }

    @Override
    public void setupBufferBuilder(BufferBuilder bufferBuilder) {
        bufferBuilder.begin(vertex == 3 ? VertexFormat.Mode.TRIANGLES : VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
    }


    @Override
    public PostEffectPipelines.Pipeline getPipeline() {
        return layer == 0 ? ppl1 : ppl2;
    }

    static final PostEffectPipelines.Pipeline ppl1 =
            new Pipeline(new ResourceLocation(EpicACG.MODID, "space_broken_0"), 10);

    static final PostEffectPipelines.Pipeline ppl2 =
            new Pipeline(new ResourceLocation(EpicACG.MODID, "space_broken_1"), 11);

    public static class Pipeline extends PostEffectPipelines.Pipeline{
        public Pipeline(ResourceLocation name, int priority) {
            super(name);
            this.priority = priority;
        }

        //private ResourceLocation space_broken_mask = new ResourceLocation(EpicACG.MODID, "space_broken_mask_" + priority);
        private static final ResourceLocation tmpTarget = new ResourceLocation(EpicACG.MODID, "space_broken_tmp");

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

        void handlePasses(RenderTarget src)
        {
            RenderTarget tmp = TargetManager.getTarget(tmpTarget);
            RenderTarget main = Minecraft.getInstance().getMainRenderTarget();
            //doDepthCull(src, depth);
            PostPasses.space_broken.process(main, src, tmp);
            PostPasses.blit.process(tmp, main);
        }

        @Override
        public void PostEffectHandler() {
            //RenderTarget target = TargetManager.getTarget(space_broken_mask);
            handlePasses(bufferTarget);
        }
    }



}
