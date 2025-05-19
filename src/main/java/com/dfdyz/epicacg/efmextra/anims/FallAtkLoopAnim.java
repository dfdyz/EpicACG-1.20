package com.dfdyz.epicacg.efmextra.anims;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.JointMask;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class FallAtkLoopAnim extends ActionAnimation {
    private AnimationManager.AnimationAccessor<? extends StaticAnimation> atk;
    //private final float fallSpeed;
    public FallAtkLoopAnim(float convertTime, AnimationManager.AnimationAccessor<? extends FallAtkLoopAnim> accessor, AssetAccessor<? extends Armature> armature, AnimationManager.AnimationAccessor<? extends StaticAnimation> atk){
        super(convertTime ,accessor,armature);
        this.atk = atk;
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);

        if(FMLEnvironment.dist == Dist.CLIENT){
            this.addProperty(ClientAnimationProperties.PRIORITY, Layer.Priority.HIGHEST);
            //this.addProperty(ClientAnimationProperties.LAYER_TYPE, Layer.LayerType.COMPOSITE_LAYER);
        }
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        entitypatch.getOriginal().setDeltaMovement(0,0,0);
        entitypatch.getOriginal().move(MoverType.SELF, new Vec3(0,-2,0));
        if(!entitypatch.isLogicalClient()){
            if(shouldAtk(entitypatch)){
                entitypatch.playAnimationSynchronized(atk,0);
            }
        }
        else {

        }
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        super.begin(entitypatch);
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        super.end(entitypatch, nextAnimation, isEnd);
        entitypatch.getOriginal().setDeltaMovement(0,-2,0);
    }

    @Override
    public void modifyPose(DynamicAnimation animation, Pose pose, LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
        //super.modifyPose(animation, pose, entitypatch, time, partialTicks);
    }

    private boolean castGround(LivingEntity entity, float length, Vec3 offset){
        Vec3 epos = entity.position();
        ClipContext clipContext = new ClipContext(
                epos.add(offset),
                epos.add(offset).add(0,-length,0),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY,
                entity);
        BlockHitResult result = entity.level().clip(clipContext);
        return result.getType() == HitResult.Type.BLOCK;
    }

    private boolean shouldAtk(LivingEntityPatch<?> entitypatch){
        /*
        Vec3 epos = entitypatch.getOriginal().position();
        ClipContext clipContext = new ClipContext(
                epos.add(0, 1, 0),
                epos.add(0,-3.3,0),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY,
                entitypatch.getOriginal());
        Level level = entitypatch.getOriginal().level;
        BlockHitResult result = level.clip(clipContext);*/

        //System.out.println(result.getType());
        return castGround(entitypatch.getOriginal(), 4.3f, new Vec3(0.3, 1, 0.3))
                || castGround(entitypatch.getOriginal(), 4.3f, new Vec3(-0.3, 1, 0.3))
                || castGround(entitypatch.getOriginal(), 4.3f, new Vec3(-0.3, 1, -0.3))
                || castGround(entitypatch.getOriginal(), 4.3f, new Vec3(0.3, 1, -0.3))
                ;

                //result.getType() == HitResult.Type.BLOCK;
                //|| entitypatch.getOriginal().getDeltaMovement().length() < 0.1f;
    }


    @Override
    public Pose getPoseByTime(LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
        float patchedTime = time % getTotalTimeReal();
        return super.getPoseByTime(entitypatch, patchedTime, partialTicks);
    }

    private float getTotalTimeReal(){
        return super.getTotalTime();
    }

    @Override
    public float getTotalTime() {
        return Float.MAX_VALUE;
    }

}
