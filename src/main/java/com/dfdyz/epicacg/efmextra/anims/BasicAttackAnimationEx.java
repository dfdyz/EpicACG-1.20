package com.dfdyz.epicacg.efmextra.anims;

import com.dfdyz.epicacg.efmextra.anims.property.MyProperties;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;

import java.util.List;

public class BasicAttackAnimationEx extends BasicAttackAnimation {

    public BasicAttackAnimationEx(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, contact, recovery, collider, colliderJoint, path, armature);
        reBindPhasesStates(false, false);
    }

    public BasicAttackAnimationEx(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature);
        reBindPhasesStates(false, false);
    }

    public BasicAttackAnimationEx(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, contact, recovery, hand, collider, colliderJoint, path, armature);
        reBindPhasesStates(false, false);
    }

    public BasicAttackAnimationEx(float convertTime, String path, Armature armature, Phase... phases) {
        super(convertTime, path, armature, phases);
        reBindPhasesStates(false, false);
        addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
        addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true);
    }

    public BasicAttackAnimationEx Lock(boolean turning, boolean interrupt_phase){
        reBindPhasesStates(turning, interrupt_phase);
        return this;
    }

    @Override
    protected void bindPhaseState(Phase phase) {
        super.bindPhaseState(phase);
    }

    private void reBindPhasesStates(boolean turningLockedAlways, boolean interrupt_phase){
        this.stateSpectrumBlueprint.clear();

        for (int i = 0; i < phases.length; i++) {
            if (!phases[i].noStateBind) {
                int interrupt_f = interrupt_phase ? 0 : 1;

                if(!interrupt_phase && i == phases.length-1){
                    interrupt_f = 2;
                }

                this.bindPhaseState(phases[i], turningLockedAlways || i != 0, interrupt_f);
            }
        }
    }

    @Override
    protected void hurtCollidingEntities(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {
        //super.hurtCollidingEntities(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
        LivingEntity entity = entitypatch.getOriginal();
        float prevPoseTime = prevState.attacking() ? prevElapsedTime : phase.preDelay;
        float poseTime = state.attacking() ? elapsedTime : phase.contact;
        List<Entity> list = this.getPhaseByTime(elapsedTime).getCollidingEntities(entitypatch, this, prevPoseTime, poseTime, this.getPlaySpeed(entitypatch, this));

        if (!list.isEmpty()) {
            HitEntityList hitEntities = new HitEntityList(entitypatch, list, phase.getProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY).orElse(HitEntityList.Priority.DISTANCE));
            int maxStrikes = this.getMaxStrikes(entitypatch, phase);

            while (entitypatch.getCurrenltyHurtEntities().size() < maxStrikes && hitEntities.next()) {
                Entity hitten = hitEntities.getEntity();
                LivingEntity trueEntity = this.getTrueEntity(hitten);

                if (trueEntity != null && trueEntity.isAlive() && !entitypatch.getCurrenltyAttackedEntities().contains(trueEntity) && !entitypatch.isTeammate(hitten)) {
                    if (hitten instanceof LivingEntity || hitten instanceof PartEntity) {
                        if (entity.hasLineOfSight(hitten)) {
                            EpicFightDamageSource damagesource = this.getEpicFightDamageSource(entitypatch, hitten, phase);
                            int prevInvulTime = hitten.invulnerableTime;
                            hitten.invulnerableTime = 0;

                            AttackResult attackResult = entitypatch.attack(damagesource, hitten, phase.hand);
                            hitten.invulnerableTime = prevInvulTime;

                            if (attackResult.resultType.dealtDamage()) {
                                hitten.level().playSound(null, hitten.getX(), hitten.getY(), hitten.getZ(), this.getHitSound(entitypatch, phase), hitten.getSoundSource(), 1.0F, 1.0F);
                                this.spawnHitParticle((ServerLevel)hitten.level(), entitypatch, hitten, phase);

                                getProperty(MyProperties.AFTER_DAMAGE_ENTITY).ifPresent(
                                        (a) ->
                                                a.invoke(this, phase, entitypatch, trueEntity, attackResult.damage)
                                );
                            }

                            entitypatch.getCurrenltyAttackedEntities().add(trueEntity);

                            if (attackResult.resultType.shouldCount()) {
                                entitypatch.getCurrenltyHurtEntities().add(trueEntity);
                            }
                        }
                    }
                }
            }
        }
    }



    protected void bindPhaseState(Phase phase, boolean turningLockedAlways, int interrupt_flag) {
        float preDelay = phase.preDelay;

        if (preDelay == 0.0F) {
            preDelay += 0.01F;
        }

        this.stateSpectrumBlueprint
                .newTimePair(phase.start, preDelay)
                .addState(EntityState.PHASE_LEVEL, 1)
                //.newTimePair(phase.start, phase.contact + 0.01F)
                //.addState(EntityState.CAN_SKILL_EXECUTION, false)
                .newTimePair(phase.start, phase.recovery)
                .addState(EntityState.MOVEMENT_LOCKED, true)

                .newTimePair(phase.start, phase.end)
                .addState(EntityState.INACTION, true)

                .newTimePair(preDelay, phase.contact + 0.01F)
                .addState(EntityState.ATTACKING, true)
                .addState(EntityState.PHASE_LEVEL, 2)

                .newTimePair(phase.contact + 0.01F, phase.end)
                .addState(EntityState.PHASE_LEVEL, 3);

        if(turningLockedAlways){
            this.stateSpectrumBlueprint
                    .newTimePair(phase.start, phase.end)
                    .addState(EntityState.TURNING_LOCKED, true);
        }
        else {
            this.stateSpectrumBlueprint
                    .newTimePair(phase.antic, phase.end)
                    .addState(EntityState.TURNING_LOCKED, true);
        }

        if(interrupt_flag == 0){
            this.stateSpectrumBlueprint
                    .newTimePair(phase.start, phase.recovery)
                    .addState(EntityState.CAN_SKILL_EXECUTION, false)
                    .addState(EntityState.UPDATE_LIVING_MOTION, false)
                    .addState(EntityState.CAN_BASIC_ATTACK, false);
        }
        else if(interrupt_flag == 2){
            this.stateSpectrumBlueprint
                    .newTimePair(0, phase.recovery)
                    .addState(EntityState.CAN_SKILL_EXECUTION, false)
                    .addState(EntityState.UPDATE_LIVING_MOTION, false)
                    .addState(EntityState.CAN_BASIC_ATTACK, false);
        }

    }


    /*
    @Override
    public void modifyPose(DynamicAnimation animation, Pose pose, LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
        //super.modifyPose(animation, pose, entitypatch, time, partialTicks);

        JointTransform jt = pose.getOrDefaultTransform("Root");
        Vec3f jointPosition = jt.translation();
        OpenMatrix4f toRootTransformApplied = entitypatch.getArmature().searchJointByName("Root").getLocalTrasnform().removeTranslation();
        OpenMatrix4f toOrigin = OpenMatrix4f.invert(toRootTransformApplied, (OpenMatrix4f)null);
        Vec3f worldPosition = OpenMatrix4f.transform3v(toRootTransformApplied, jointPosition, (Vec3f)null);



        OpenMatrix4f.transform3v(toOrigin, worldPosition, worldPosition);
        jointPosition.x = worldPosition.x;
        jointPosition.y = worldPosition.y;
        jointPosition.z = worldPosition.z;

        entitypatch.poseTick(animation, pose, time, partialTicks);
        this.getProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER).ifPresent((poseModifier) -> {
            poseModifier.modify(animation, pose, entitypatch, time, partialTicks);
        });


    }*/
}
