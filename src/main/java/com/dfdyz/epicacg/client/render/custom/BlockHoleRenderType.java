package com.dfdyz.epicacg.client.render.custom;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.client.render.pipeline.PostParticleRenderType;
import com.dfdyz.epicacg.client.render.targets.TargetManager;
import com.dfdyz.epicacg.registry.PostPasses;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import static com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines.getSource;

public class BlockHoleRenderType extends PostParticleRenderType {
    public BlockHoleRenderType(ResourceLocation renderTypeID, ResourceLocation texture) {
        super(renderTypeID, texture);
    }

    @Override
    public PostEffectPipelines.Pipeline getPipeline() {
        return ppl;
    }

    static final PPL ppl = new PPL(new ResourceLocation(EpicACG.MODID, "black_hole"));

    public static class PPL extends PostEffectPipelines.Pipeline {
        public PPL(ResourceLocation name) {
            super(name);
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
                = new ResourceLocation(EpicACG.MODID, "black_hole_tmp");

        @Override
        public void PostEffectHandler() {
            RenderTarget tmp = TargetManager.getTarget(tmpTarget);
            RenderTarget main = Minecraft.getInstance().getMainRenderTarget();
            //doDepthCull(src, depth);
            //System.out.println("Handle");
            PostPasses.black_hole.process(main, bufferTarget, tmp);
            PostPasses.blit.process(tmp, main);
        }
    }


}
