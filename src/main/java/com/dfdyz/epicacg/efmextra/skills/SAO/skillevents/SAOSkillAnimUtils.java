package com.dfdyz.epicacg.efmextra.skills.SAO.skillevents;

import com.dfdyz.epicacg.client.particle.DMC.AirWaveParticle;
import com.dfdyz.epicacg.client.particle.DMC.PhantomsParticle;
import com.dfdyz.epicacg.client.particle.DMC.SpaceBrokenParticle;
import com.dfdyz.epicacg.client.particle.SAO.LandingStrikeParticle;
import com.dfdyz.epicacg.client.screeneffect.ColorDispersionEffect;
import com.dfdyz.epicacg.efmextra.anims.DeferredDamageAttackAnimation;
import com.dfdyz.epicacg.efmextra.skills.SAO.SingleSwordSASkills;
import com.dfdyz.epicacg.event.CameraEvents;
import com.dfdyz.epicacg.event.ScreenEffectEngine;
import com.dfdyz.epicacg.registry.MobEffects;
import com.dfdyz.epicacg.registry.Particles;
import com.dfdyz.epicacg.registry.Sounds;
import com.dfdyz.epicacg.utils.RenderUtils;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageSources;
import yesman.epicfight.world.entity.eventlistener.DealtDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.HashMap;
import java.util.List;

import static com.dfdyz.epicacg.registry.MyAnimations.*;
import static com.dfdyz.epicacg.registry.MySkillDataKeys.JCE_Invincible;

public class SAOSkillAnimUtils {
    /*
    public static final AnimationEvent.AnimationEventConsumer FRACTURE_GROUND_SIMPLE = (entitypatch, animation, params) -> {
        Vec3 position = (entitypatch.getOriginal()).position();
        OpenMatrix4f modelTransform = entitypatch.getArmature().getBindedTransformFor(animation.getPoseByTime(entitypatch, (Float)params[3], 1.0F), (Joint)params[1]).mulFront(OpenMatrix4f.createTranslation((float)position.x, (float)position.y, (float)position.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(entitypatch.getModelMatrix(1.0F))));
        Level level = (entitypatch.getOriginal()).level;
        Vec3 weaponEdge = OpenMatrix4f.transform(modelTransform, ((Vec3f)params[0]).toDoubleVector());
        BlockHitResult hitResult = level.clip(new ClipContext(position.add(0.0, 0.1, 0.0), weaponEdge, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entitypatch.getOriginal()));
        Vec3 slamStartPos;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            Direction direction = hitResult.getDirection();
            BlockPos collidePos = hitResult.getBlockPos().offset(direction.getStepX(), direction.getStepY(), direction.getStepZ());
            if (!LevelUtil.canTransferShockWave(level, collidePos, level.getBlockState(collidePos))) {
                collidePos = collidePos.below();
            }

            slamStartPos = new Vec3(collidePos.getX(), collidePos.getY(), collidePos.getZ());
        } else {
            slamStartPos = weaponEdge.subtract(0.0, 1.0, 0.0);
        }

        LevelUtil.circleSlamFracture(entitypatch.getOriginal(), level, slamStartPos, (Double)params[2], true, false);
    };*/


    public static void playSound(Entity entity, SoundEvent soundEvent){
        //System.out.println("?????");
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), soundEvent, entity.getSoundSource(), 1.0F, 1.0F);
    }

    public static class RapierSA {
        public static void prev(LivingEntityPatch<?> entityPatch){
            CameraEvents.SetAnim(SAO_RAPIER_SA2_CAM, entityPatch.getOriginal(), true);
        }
        public static void HandleAtk1(LivingEntityPatch<?> entityPatch){
            if(entityPatch.isLogicalClient()){
            }
            else {
                if(entityPatch.getCurrenltyAttackedEntities().size() > 0){
                    entityPatch.getCurrenltyAttackedEntities().forEach((entity)->{
                        if(entity != null && entity.isAlive() && entity.equals(entityPatch.getOriginal())) return;
                        entity.addEffect(new MobEffectInstance(MobEffects.STOP.get(), 13, 1));
                    });
                }
            }
        }

        public static void HandleAtk2(LivingEntityPatch<?> entityPatch){
            if(entityPatch.isLogicalClient()){
            }
            else {
                List<Entity> EntityMap = DeferredDamageAttackAnimation.getScannedEntities(entityPatch);
                if(EntityMap.size() > 0){
                    EntityMap.forEach(
                            (entity)->{
                                if(entity != null && entity.isAlive() && entity.equals(entityPatch.getOriginal())) return;
                                HurtEntity(entityPatch, entity, SAO_RAPIER_SA2, 2.5f,0.15f);
                            });
                }
            }
        }

        public static void post(LivingEntityPatch<?> entityPatch){
            //entityPatch.getOriginal().setInvisible(false);
            CameraEvents.SetAnim(SAO_RAPIER_SA2_CAM2, entityPatch.getOriginal(), true);
        }
    }

    public static class DualSwordSA{

        public static void LandingStrike(Entity entity){
            LandingStrikeParticle particle = new LandingStrikeParticle(
                    (ClientLevel) entity.level(), entity.position().add(0, .8f, 0));
            particle.setColor(0, 246.f / 255.f, 1f);
            RenderUtils.AddParticle((ClientLevel) entity.level(), particle);
        }

    }

    public static void HurtEntity(LivingEntityPatch<?> attacker, Entity target, StaticAnimation animation, float damageRate, float cutRate){
        EpicFightDamageSource source = attacker.getDamageSource(animation, InteractionHand.MAIN_HAND);
        LivingEntity rootEntity = getTrueEntity(target);

        var level = attacker.getOriginal().level();
        if(level.isClientSide) return;

        var modifier = new ValueModifier(rootEntity.getMaxHealth() * 0.05f, damageRate, Float.NaN);
        if(source.getDamageModifier() == null){
            source.setDamageModifier(modifier);
        }
        else {
            source.getDamageModifier().merge(modifier);
        }

        int prevInvulTime = target.invulnerableTime;
        target.invulnerableTime = 0;
        AttackResult attackResult = attacker.attack(source, target, InteractionHand.MAIN_HAND);

        if(rootEntity != null){
            //float dmg = rootEntity.getMaxHealth() * 0.05f;
            float health = rootEntity.getHealth();
            float dmg = cutRate * (rootEntity.getMaxHealth() - health);
            target.invulnerableTime = 0;
            target.hurt(level.damageSources().generic(), dmg);
        }
        target.invulnerableTime = prevInvulTime;
    }

    public static LivingEntity getTrueEntity(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity;
        } else {
            if (entity instanceof PartEntity<?> partEntity) {
                Entity parentEntity = partEntity.getParent();
                if (parentEntity instanceof LivingEntity livingEntity) {
                    return livingEntity;
                }
            }

            return null;
        }
    }

}
