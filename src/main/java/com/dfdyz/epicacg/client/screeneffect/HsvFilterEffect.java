package com.dfdyz.epicacg.client.screeneffect;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.render.targets.TargetManager;
import com.dfdyz.epicacg.registry.PostPasses;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class HsvFilterEffect extends ScreenEffectBase {
    public enum Type{
        PREV, POST
    }

    public Type type = Type.POST;
    public float alpha = 1;

    public HsvFilterEffect(Vec3 pos, int lt) {
        super(hsv_filter, pos);
        this.ppl = new HsvFilter_Pipeline(this);
        lifetime = lt;
    }

    static final float recovery = 15;

    float getTimeChannel(){
        if(age < 5){
            return age / 5f;
        }
        else return Math.max(Math.min(1, (lifetime - age) / recovery), 0);
    }

    public float getAlpha(){
        return alpha * getTimeChannel();
    }


    static ResourceLocation hsv_filter = new ResourceLocation(EpicACG.MODID, "hsv_filter");
    public final SE_Pipeline ppl;

    @Override
    public boolean shouldPost(Camera camera, Frustum clippingHelper) {
        double distance = pos.subtract(camera.getPosition()).length();
        if(distance >= 24) alpha = (float) Math.max(0, -0.125*distance+4);
        return distance < 32 && alpha >= 0.01f;
    }

    public static class HsvFilter_Pipeline extends SE_Pipeline<HsvFilterEffect>{
        public HsvFilter_Pipeline(HsvFilterEffect effect) {
            super(hsv_filter, effect);
            priority = 101;
        }
        static ResourceLocation hsv_filter_tmp = new ResourceLocation(EpicACG.MODID, "hsv_filter_tmp");
        @Override
        public void PostEffectHandler() {
            RenderTarget tmp = TargetManager.getTarget(hsv_filter_tmp);
            PostPasses.blit.process(Minecraft.getInstance().getMainRenderTarget(), tmp);
            //System.out.println("handle");
            PostPasses.hsv_filter.process(tmp, Minecraft.getInstance().getMainRenderTarget(), 1, 0.97f, effect.getAlpha());
        }
    }

    @Override
    public SE_Pipeline getPipeline() {
        //System.out.println("Get");
        return ppl;
    }
}
