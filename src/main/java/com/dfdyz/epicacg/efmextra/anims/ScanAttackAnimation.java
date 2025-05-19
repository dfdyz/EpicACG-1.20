package com.dfdyz.epicacg.efmextra.anims;

import com.dfdyz.epicacg.efmextra.anims.property.MyProperties;
import com.dfdyz.epicacg.event.RenderEvents;
import com.dfdyz.epicacg.utils.Function.ScanAttackConsumer;
import com.google.common.collect.Lists;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.datastruct.TypeFlexibleHashMap;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.List;

public class ScanAttackAnimation extends AttackAnimation{
    protected ScanAttackConsumer attackConsumer = (animation, entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase) -> {};

    public ScanAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends ScanAttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, accessor, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
    }

    public ScanAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, AnimationManager.AnimationAccessor<? extends ScanAttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, accessor, armature);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
    }

    public ScanAttackAnimation(float convertTime, AnimationManager.AnimationAccessor<? extends ScanAttackAnimation> accessor, AssetAccessor<? extends Armature> armature, Phase... phases) {
        super(convertTime, accessor, armature, phases);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
    }

    public ScanAttackAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @javax.annotation.Nullable Collider collider, Joint scanner, AnimationManager.AnimationAccessor<? extends ScanAttackAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(convertTime, accessor, armature,
                new Phase(0.0F, antic, contact, recovery, Float.MAX_VALUE, hand, scanner, collider));
        //Hjoint = shoot;
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        //this.Aid = aid;
    }


    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        super.begin(entitypatch);
        getScannedEntities(entitypatch).clear();
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean isEnd) {
        super.end(entitypatch, nextAnimation, isEnd);
        entitypatch.removeHurtEntities();
        getScannedEntities(entitypatch).clear();
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        if(entitypatch.isLogicalClient()){
            if(this.getProperty(MyProperties.INVISIBLE_PHASE).isPresent()){
                if(this.getProperty(MyProperties.INVISIBLE_PHASE).get()
                        .isInPhase(entitypatch.getAnimator()
                                .getPlayerFor(getAccessor()).getElapsedTime()
                        )){
                    RenderEvents.HiddenEntity(entitypatch.getOriginal(), 10);
                }
                else {
                    RenderEvents.UnhiddenEntity(entitypatch.getOriginal());
                }
            }


        }
    }

    public ScanAttackAnimation setAttackConsumer(ScanAttackConsumer consumer){
        attackConsumer = consumer;
        return this;
    }

    @Override
    public void modifyPose(DynamicAnimation animation, Pose pose, LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
            if (this.getProperty(AnimationProperty.ActionAnimationProperty.COORD).isEmpty()) {
                JointTransform jt = pose.orElseEmpty("Root");
                Vec3f jointPosition = jt.translation();
                OpenMatrix4f toRootTransformApplied = entitypatch.getArmature().searchJointByName("Root").getLocalTransform().removeTranslation();
                OpenMatrix4f toOrigin = OpenMatrix4f.invert(toRootTransformApplied, null);
                Vec3f worldPosition = OpenMatrix4f.transform3v(toRootTransformApplied, jointPosition, null);

                if(this.getProperty(MyProperties.MOVE_ROOT_PHASE).isPresent() && this.getProperty(MyProperties.MOVE_ROOT_PHASE).get().isInPhase(time)){

                }
                else {
                    worldPosition.x = 0.0F;
                    worldPosition.y = this.getProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL).orElse(false) && worldPosition.y > 0.0F ? 0.0F : worldPosition.y;
                    worldPosition.z = 0.0F;
                }


                OpenMatrix4f.transform3v(toOrigin, worldPosition, worldPosition);
                jointPosition.x = worldPosition.x;
                jointPosition.y = worldPosition.y;
                jointPosition.z = worldPosition.z;
            }


        AnimationProperty.PoseModifier modifier = this.getProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER).orElse(null);
        if (modifier != null) {
            modifier.modify(animation, pose, entitypatch, time, partialTicks);
        }
    }

    @Override
    protected Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> dynamicAnimation) {
        //entitypatch.getOriginal().setDeltaMovement(0,0,0);
        /*
        if (!shouldMove){
            return Vec3.ZERO;
        }*/
        Vec3 vec3 = super.getCoordVector(entitypatch, dynamicAnimation);

        var animPlayer = entitypatch.getAnimator().getPlayerFor(dynamicAnimation);
        if(animPlayer == null) return vec3;

        float t = animPlayer.getElapsedTime();
        if(this.getProperty(MyProperties.MOVE_ROOT_PHASE).isPresent() &&
                this.getProperty(MyProperties.MOVE_ROOT_PHASE).get().isInPhase(t)){
            vec3 = vec3.multiply(0,0,0);
        }

        return vec3;
    }

    @Override
    public void attackTick(LivingEntityPatch<?> entitypatch, AssetAccessor<? extends DynamicAnimation> animation) {
        AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(getAccessor());
        float elapsedTime = player.getElapsedTime();
        float prevElapsedTime = player.getPrevElapsedTime();
        EntityState state = this.getState(entitypatch, elapsedTime);
        EntityState prevState = this.getState(entitypatch, prevElapsedTime);
        Phase phase = this.getPhaseByTime(elapsedTime);
        if (state.getLevel() == 1 && !state.turningLocked() && entitypatch instanceof MobPatch<?> mobpatch) {
            mobpatch.getOriginal().getNavigation().stop();
            entitypatch.getOriginal().attackAnim = 2.0F;
            LivingEntity target = entitypatch.getTarget();
            if (target != null) {
                entitypatch.rotateTo(target, entitypatch.getYRotLimit(), false);
            }
        }

        if (prevState.attacking() || state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2) {
            if (!prevState.attacking()) {
                entitypatch.removeHurtEntities();
            }
            ScanTarget(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
        }

    }

    public static final AnimationVariables.SharedAnimationVariableKey<List<Entity>> SCANNED_ENTITY = AnimationVariables.shared((animator) -> {
        return Lists.newArrayList();
    }, false);


    public static List<Entity> getScannedEntities(LivingEntityPatch<?> entityPatch){
        return entityPatch.getAnimator().getVariables().getOrDefaultSharedVariable(SCANNED_ENTITY);
    }

    public void ScanTarget(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase){
        LivingEntity entity = entitypatch.getOriginal();

        float prevPoseTime = prevState.attacking() ? prevElapsedTime : phase.preDelay;
        float poseTime = state.attacking() ? elapsedTime : phase.contact;
        List<Entity> list = this.getPhaseByTime(elapsedTime)
                .getCollidingEntities(entitypatch, this, prevPoseTime, poseTime,
                        this.getPlaySpeed(entitypatch, this));


        if (list.size() > 0) {
            HitEntityList hitEntities = new HitEntityList(entitypatch, list, HitEntityList.Priority.DISTANCE);
            //int maxStrikes = 1;
            entitypatch.getOriginal().setLastHurtMob(list.get(0));

            while (entitypatch.getCurrenltyAttackedEntities().size() < getMaxStrikes(entitypatch, phase) && hitEntities.next()) {
                Entity e = hitEntities.getEntity();
                if(!e.isAlive()) continue;

                LivingEntity trueEntity = this.getTrueEntity(e);
                if (!entitypatch.isTargetInvulnerable(e) && trueEntity != null) {
                    if (e instanceof LivingEntity || e instanceof PartEntity) {
                        if (entity.hasLineOfSight(e) && !getScannedEntities(entitypatch).contains(e)) {
                            if(!entitypatch.getCurrenltyAttackedEntities().contains(trueEntity))
                                entitypatch.getCurrenltyAttackedEntities().add(trueEntity);
                            getScannedEntities(entitypatch).add(e);
                        }
                    }
                }
            }
        }
        if(!entitypatch.getCurrenltyAttackedEntities().contains(entitypatch.getOriginal())){
            entitypatch.getCurrenltyAttackedEntities().add(entitypatch.getOriginal());
        }
    }

    @Override
    public void hurtCollidingEntities(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {

    }

    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }

}
