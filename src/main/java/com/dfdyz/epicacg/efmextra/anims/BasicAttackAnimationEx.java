package com.dfdyz.epicacg.efmextra.anims;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Locale;

import static yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE;

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
    }

    public BasicAttackAnimationEx Lock(boolean turning, boolean interrupt_phase){
        reBindPhasesStates(turning, interrupt_phase);
        return this;
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



    protected void bindPhaseState(Phase phase, boolean turningLockedAlways, int interrupt_flag) {
        float preDelay = phase.preDelay;

        if (preDelay == 0.0F) {
            preDelay += 0.01F;
        }

        this.stateSpectrumBlueprint
                .newTimePair(phase.start, preDelay)
                .addState(EntityState.PHASE_LEVEL, 1)
                .newTimePair(phase.start, phase.contact + 0.01F)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .newTimePair(phase.start, phase.recovery)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.UPDATE_LIVING_MOTION, false)

                .newTimePair(phase.start, phase.end)
                .addState(EntityState.INACTION, true)
                .newTimePair(preDelay, phase.contact + 0.01F)
                .addState(EntityState.ATTACKING, true)
                .addState(EntityState.PHASE_LEVEL, 2)
                .newTimePair(phase.contact + 0.01F, phase.end)
                .addState(EntityState.PHASE_LEVEL, 3)
        ;

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
                    .addState(EntityState.CAN_BASIC_ATTACK, false);
        }
        else if(interrupt_flag == 2){
            this.stateSpectrumBlueprint
                    .newTimePair(0, phase.recovery)
                    .addState(EntityState.CAN_BASIC_ATTACK, false);
        }

    }


    @Override
    public void modifyPose(DynamicAnimation animation, Pose pose, LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
        super.modifyPose(animation, pose, entitypatch, time, partialTicks);
    }
}
