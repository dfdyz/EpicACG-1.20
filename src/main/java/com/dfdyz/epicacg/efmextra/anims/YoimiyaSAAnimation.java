package com.dfdyz.epicacg.efmextra.anims;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import javax.annotation.Nullable;
import java.util.Locale;

public class YoimiyaSAAnimation extends AttackAnimation {
    //private final int Aid;
    //public final String Hjoint;

    public YoimiyaSAAnimation(float convertTime, float antic, float recovery, InteractionHand hand, @Nullable Collider collider, Joint scanner, String path, Armature model) {
        super(convertTime, path, model,
                new Phase(0.0F, 0f, antic, recovery, Float.MAX_VALUE, hand, scanner, collider));

        //Hjoint = shoot;
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        //this.Aid = aid;
    }

    /*
    @Override
    public void setLinkAnimation(Pose pose1, float timeModifier, LivingEntityPatch<?> entitypatch, LinkAnimation dest) {
        float extTime = Math.max(this.convertTime + timeModifier, 0.0F);
        if (entitypatch instanceof PlayerPatch<?> playerpatch) {
            Phase phase = this.getPhaseByTime(playerpatch.getAnimator().getPlayerFor(this).getElapsedTime());
            //PhaseAccessor phaseAccessor = (PhaseAccessor)phase;
            extTime *= this.totalTime * playerpatch.getAttackSpeed(phase.getHand());
        }

        extTime = Math.max(extTime - this.convertTime, 0.0F);
        super.setLinkAnimation(pose1, extTime, entitypatch, dest);
    }*/

    @Override
    public void modifyPose(DynamicAnimation animation, Pose pose, LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
        JointTransform jt = pose.getOrDefaultTransform("Root");
        Vec3f jointPosition = jt.translation();
        OpenMatrix4f toRootTransformApplied = entitypatch.getArmature().searchJointByName("Root").getLocalTrasnform().removeTranslation();
        OpenMatrix4f toOrigin = OpenMatrix4f.invert(toRootTransformApplied, (OpenMatrix4f)null);
        Vec3f worldPosition = OpenMatrix4f.transform3v(toRootTransformApplied, jointPosition, (Vec3f)null);
        worldPosition.x = 0.0F;
        worldPosition.y = 0.0F;
        worldPosition.z = 0.0F;
        OpenMatrix4f.transform3v(toOrigin, worldPosition, worldPosition);
        jointPosition.x = worldPosition.x;
        jointPosition.y = worldPosition.y;
        jointPosition.z = worldPosition.z;
    }

    @Override
    protected Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        entitypatch.getOriginal().setDeltaMovement(0,0,0);
        entitypatch.getOriginal().setNoGravity(true);
        Vec3 vec3 = super.getCoordVector(entitypatch, dynamicAnimation);
        entitypatch.getOriginal().setNoGravity(false);
        return vec3.multiply(1,2,1);
    }

    @Override
    protected void attackTick(LivingEntityPatch<?> entitypatch, DynamicAnimation animation) {
        /*
        if (!entitypatch.isLogicalClient()) {
            AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
            float elapsedTime = player.getElapsedTime();
            float prevElapsedTime = player.getPrevElapsedTime();
            EntityState state = this.getState(entitypatch,elapsedTime);
            EntityState prevState = this.getState(entitypatch,prevElapsedTime);
            Phase phase = this.getPhaseByTime(elapsedTime);

            if (state.getLevel() == 1 && !state.turningLocked()) {
                if (entitypatch instanceof MobPatch) {
                    ((Mob)entitypatch.getOriginal()).getNavigation().stop();
                    entitypatch.getOriginal().attackAnim = 2;
                    LivingEntity target = entitypatch.getTarget();

                    if (target != null) {
                        entitypatch.rotateTo(target, entitypatch.getYRotLimit(), false);
                    }
                }
            } else if (prevState.attacking() || state.attacking() || (prevState.getLevel() < 2 && state.getLevel() > 2)) {
                if (!prevState.attacking()) {
                    //entitypatch.playSound(this.getSwingSound(entitypatch, phase), 0.0F, 0.0F);
                    entitypatch.getCurrenltyAttackedEntities().clear();
                }

                //EpicAddon.LOGGER.info(String.valueOf(prevElapsedTime));
                //this.ScanTarget(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
            }
        }

         */
        AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
        float elapsedTime = player.getElapsedTime();
        float prevElapsedTime = player.getPrevElapsedTime();
        EntityState state = this.getState(entitypatch, elapsedTime);
        EntityState prevState = this.getState(entitypatch, prevElapsedTime);
        Phase phase = this.getPhaseByTime(elapsedTime);
        if (state.getLevel() == 1 && !state.turningLocked() && entitypatch instanceof MobPatch) {
            ((Mob)entitypatch.getOriginal()).getNavigation().stop();
            ((LivingEntity)entitypatch.getOriginal()).attackAnim = 2.0F;
            LivingEntity target = entitypatch.getTarget();
            if (target != null) {
                entitypatch.rotateTo(target, entitypatch.getYRotLimit(), false);
            }
        }

        if (prevState.attacking() || state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2) {
            if (!prevState.attacking() || phase != this.getPhaseByTime(prevElapsedTime) && (state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2)) {
                //entitypatch.playSound(this.getSwingSound(entitypatch, phase), 0.0F, 0.0F);
                entitypatch.getCurrenltyAttackedEntities().clear();
            }

            //this.hurtCollidingEntities(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
        }

    }

    //entitypatch.currentlyAttackedEntity.add(entitypatch.getOriginal());

    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }
}
