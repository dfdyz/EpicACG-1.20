package com.dfdyz.epicacg.efmextra.anims;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.List;

public class DeferredDamageAttackAnimation extends ScanAttackAnimation {


    public DeferredDamageAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature);
    }

    public DeferredDamageAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
    }

    public DeferredDamageAttackAnimation(float convertTime, String path, Armature armature, Phase... phases) {
        super(convertTime, path, armature, phases);
    }

    public DeferredDamageAttackAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint scanner, String path, Armature model) {
        super(convertTime, antic, contact, recovery, hand, collider, scanner, path, model);
        addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
    }

    @Override
    public void attackTick(LivingEntityPatch<?> entitypatch, DynamicAnimation animation) {
        AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
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
            this.ScanTarget(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
        }
    }

    @Override
    protected void bindPhaseState(Phase phase) {

        this.stateSpectrumBlueprint
                .newTimePair(phase.start, phase.preDelay)
                .addState(EntityState.PHASE_LEVEL, 1)
                .newTimePair(phase.start, phase.recovery)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.UPDATE_LIVING_MOTION, false)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .newTimePair(phase.start, phase.end)
                .addState(EntityState.INACTION, true)
                .newTimePair(phase.preDelay, phase.contact + 0.01F)
                .addState(EntityState.ATTACKING, true)
                .addState(EntityState.PHASE_LEVEL, 2)
                .newTimePair(phase.contact + 0.01F, phase.end)
                .addState(EntityState.PHASE_LEVEL, 3)
                .newTimePair(phase.start, phase.end)
                .addState(EntityState.TURNING_LOCKED, true);

    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        super.begin(entitypatch);
        entitypatch.removeHurtEntities();
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, DynamicAnimation nextAnimation, boolean isEnd) {
        super.end(entitypatch, nextAnimation, isEnd);
        entitypatch.removeHurtEntities();
    }
}
