package com.dfdyz.epicacg.client.screeneffect;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import com.dfdyz.epicacg.client.render.targets.TargetManager;
import com.dfdyz.epicacg.registry.PostPasses;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class ColorDispersionEffect extends ScreenEffectBase {
    public enum Type{
        PREV, POST
    }

    public Type type = Type.POST;

    public ColorDispersionEffect(Vec3 pos) {
        super(color_dispersion, pos);
        this.ppl = new CD_Pipeline(this);
        this.lifetime = 40;
    }
    static ResourceLocation color_dispersion = new ResourceLocation(EpicACG.MODID, "color_dispersion");
    public final SE_Pipeline ppl;

    public static class CD_Pipeline extends SE_Pipeline<ColorDispersionEffect>{
        public CD_Pipeline(ColorDispersionEffect effect) {
            super(color_dispersion, effect);
            priority = 100;
        }
        static ResourceLocation color_dispersion_tmp = new ResourceLocation(EpicACG.MODID, "color_dispersion_tmp");
        @Override
        public void PostEffectHandler() {
            //System.out.println("handle");
            RenderTarget tmp = TargetManager.getTarget(color_dispersion_tmp);
            PostPasses.blit.process(Minecraft.getInstance().getMainRenderTarget(), tmp);
            float t = Math.max(0, 1.f - (1.0f / effect.lifetime * effect.age));
            t = Mth.sqrt(t);


            float rm, gm, bm;

            if(effect.type == Type.POST){
                int phase = effect.age / 8;
                if(phase == 0){
                    rm = 0.7f; gm = 0.7f; bm = 1.1f;
                }
                else if(phase == 1){
                    rm = 0.5f; gm = 0.5f; bm = 1.2f;
                }
                else if(phase == 2){
                    rm = 0.8f; gm = 0.8f; bm = 1.2f;
                }
                else {
                    rm = 0f; gm = 0f; bm = 0f;
                }
            }
            else {
                rm = 0.5f; gm = 0.5f; bm = 1.2f;
            }


            PostPasses.color_dispersion.process(tmp, Minecraft.getInstance().getMainRenderTarget(),
                        1 + .15f * t,  1 + .075f * t, 1, rm, gm, bm
                    );
        }
    }

    @Override
    public PostEffectPipelines.Pipeline getPipeline() {
        //System.out.println("Get");
        return ppl;
    }
}
