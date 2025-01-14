package com.dfdyz.epicacg.client.screeneffect;

import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class ScreenEffectBase {
    public int age = 0;
    public int lifetime = 20;

    public Vec3 pos;

    public final ResourceLocation ID;

    public ScreenEffectBase(ResourceLocation id, Vec3 pos) {
        ID = id;
        this.pos = pos;
    }

    public void tick(){
        if(++age > lifetime){
        }
    }

    public boolean shouldRemoved(){
        return age > lifetime;
    }

    public void setRemove(){
        age = lifetime + 1;
    }

    public abstract PostEffectPipelines.Pipeline getPipeline();

    public boolean shouldPost(Camera camera, Frustum clippingHelper){
        if(clippingHelper != null && clippingHelper.isVisible(getAABB())
                && camera.getPosition().subtract(pos).length() < 32){
            return true;
        }
        return false;
    }


    public AABB getAABB(){
        return new AABB(pos.subtract(0.2, 0.2, 0.2), pos.add(0.2, 0.2, 0.2));
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    public abstract static class SE_Pipeline<E extends ScreenEffectBase> extends PostEffectPipelines.Pipeline{
        public final E effect;
        public SE_Pipeline(ResourceLocation name, E effect) {
            super(name);
            this.effect = effect;
        }

        @Override
        public void HandlePostEffect(){
            PostEffectHandler();
        }
    }
}
