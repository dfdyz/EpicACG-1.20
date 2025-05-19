package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.camera.CameraAnimation;
import com.dfdyz.epicacg.efmextra.anims.*;
import com.dfdyz.epicacg.efmextra.anims.property.MyProperties;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.skillevents.YoimiyaSkillFunction;
import com.dfdyz.epicacg.efmextra.skills.HSR.skillevents.ACHERON_SA_CLIENT;
import com.dfdyz.epicacg.efmextra.skills.SAO.skillevents.DMC_V_JC_Client;
import com.dfdyz.epicacg.efmextra.skills.SAO.skillevents.DMC_V_JC_Server;
import com.dfdyz.epicacg.efmextra.skills.SAO.skillevents.SAOSkillAnimUtils;
import com.dfdyz.epicacg.efmextra.weapon.WeaponCollider;
import com.dfdyz.epicacg.event.CameraEvents;
import com.dfdyz.epicacg.utils.MoveCoordFuncUtils;
import com.dfdyz.epicacg.utils.OjangUtils;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.awt.*;
import java.util.List;

import static com.dfdyz.epicacg.utils.MoveCoordFuncUtils.TraceLockedTargetEx;

public class MyAnimations {
    //public static List<CamAnim> CamAnimRegistry = Lists.newArrayList();
   public static AnimationManager.AnimationAccessor<DeferredDamageAttackAnimation> DMC5_V_JC;
   public static AnimationManager.AnimationAccessor<ScanAttackAnimation> GS_Yoimiya_Auto1;
   public static AnimationManager.AnimationAccessor<ScanAttackAnimation> GS_Yoimiya_Auto2;
   public static AnimationManager.AnimationAccessor<ScanAttackAnimation> GS_Yoimiya_Auto3;
   public static AnimationManager.AnimationAccessor<ScanAttackAnimation> GS_Yoimiya_Auto4;
   public static AnimationManager.AnimationAccessor<ScanAttackAnimation> GS_Yoimiya_Auto5;
   public static AnimationManager.AnimationAccessor<YoimiyaSAAnimation> GS_Yoimiya_SA;

   public static AnimationManager.AnimationAccessor<FallAtkStartAnim> GS_Yoimiya_FallAtk_Start;
   public static AnimationManager.AnimationAccessor<FallAtkFinalAnim> GS_Yoimiya_FallAtk_Last;
   public static AnimationManager.AnimationAccessor<FallAtkLoopAnim> GS_Yoimiya_FallAtk_Loop;


   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_SCYTHE_IDLE;
   public static AnimationManager.AnimationAccessor<MovementAnimation> SAO_SCYTHE_RUN;
   public static AnimationManager.AnimationAccessor<MovementAnimation> SAO_SCYTHE_WALK;

   public static AnimationManager.AnimationAccessor<BasicAttackAnimationEx> HSR_ACHERON_SA;

   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BATTLE_SCYTHE_AUTO1;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BATTLE_SCYTHE_AUTO2;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BATTLE_SCYTHE_AUTO3;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimationEx> BATTLE_SCYTHE_AUTO4;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> BATTLE_SCYTHE_AUTO5;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimationEx> BATTLE_SCYTHE_DASH;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimationEx> BATTLE_SCYTHE_SA1;

    //Dual blade
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_DUAL_SWORD_HOLD;
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_DUAL_SWORD_NORMAL;
   public static AnimationManager.AnimationAccessor<MovementAnimation> SAO_DUAL_SWORD_RUN;
   public static AnimationManager.AnimationAccessor<MovementAnimation> SAO_DUAL_SWORD_WALK;
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_SINGLE_SWORD_GUARD;
    //public static StaticAnimation SAO_DUAL_SWORD_WALK;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO1;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO2;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO3;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO4;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO5;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO6;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO7;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO8;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO9;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO10;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO11;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_DUAL_SWORD_AUTO12;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimationEx> SAO_DUAL_SWORD_DASH;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimationEx> SAO_DUAL_SWORD_SA1;

   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_RAPIER_IDLE;
   public static AnimationManager.AnimationAccessor<MovementAnimation> SAO_RAPIER_WALK;
   public static AnimationManager.AnimationAccessor<MovementAnimation> SAO_RAPIER_RUN;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AUTO1;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AUTO2;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AUTO3;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AUTO4;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AUTO5;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_AIR;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SAO_RAPIER_SPECIAL_DASH;
   public static AnimationManager.AnimationAccessor<DashAttackAnimation> SAO_RAPIER_DASH;
   public static AnimationManager.AnimationAccessor<DeferredDamageAttackAnimation> SAO_RAPIER_SA2;

   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GS_LAODENG_AUTO1;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GS_LAODENG_AUTO2;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GS_LAODENG_AUTO3;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GS_LAODENG_AUTO4;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimationEx> GS_LAODENG_AUTO5;
   public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GS_LAODENG_AUTO6;

   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_LONGSWORD_VARIANT_AUTO1;
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_LONGSWORD_VARIANT_AUTO2;
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_LONGSWORD_VARIANT_AUTO3;
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_LONGSWORD_VARIANT_AUTO4;
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_LONGSWORD_VARIANT_AUTO5;
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_LONGSWORD_VARIANT_DASH;

   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_LONGSWORD_VARIANT_IDLE;
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_LONGSWORD_VARIANT_RUN;
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_LONGSWORD_VARIANT_WALK;
   public static AnimationManager.AnimationAccessor<StaticAnimation> SAO_LONGSWORD_VARIANT_JUMP;

    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(EpicACG.MODID, MyAnimations::build);
    }

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> biped = Armatures.BIPED;

        /*
                builder.nextAccessor(  ,
                (accessor) ->
         */

                        //SCYTHES
        SAO_SCYTHE_IDLE = builder.nextAccessor("biped/battle_scythe/living/battle_scythe_idle",
                (accessor) -> new StaticAnimation(true, accessor, biped));
        SAO_SCYTHE_RUN = builder.nextAccessor("biped/battle_scythe/living/battle_scythe_run",
                (accessor) ->new MovementAnimation(true, accessor, biped));
        SAO_SCYTHE_WALK = builder.nextAccessor("biped/battle_scythe/living/battle_scythe_walk",
                (accessor) ->new MovementAnimation(true, accessor, biped));
        SAO_SINGLE_SWORD_GUARD = builder.nextAccessor("biped/battle_scythe/living/battle_scythe_idle",
                (accessor) -> new StaticAnimation(0.25F,
                        true, accessor, biped));

        BATTLE_SCYTHE_AUTO1 = builder.nextAccessor("biped/battle_scythe/battle_scythe_auto1",
                (accessor) ->new BasicAttackAnimation(0.08F, 0.2F, 0.4F, 0.5F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0.12f,0.4f, 8, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                )
                 */
         );

        BATTLE_SCYTHE_AUTO2 = builder.nextAccessor("biped/battle_scythe/battle_scythe_auto2",
                (accessor) ->new BasicAttackAnimation(0.08F, 0.2F, 0.3F, 0.4F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
        );
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0.12f,0.3F, 8, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );*/

        BATTLE_SCYTHE_AUTO3 = builder.nextAccessor("biped/battle_scythe/battle_scythe_auto3",
                (accessor) -> new BasicAttackAnimation(0.04F, 0.3F, 0.4F, 0.55F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0.1f,0.35f, 8, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );*/

        BATTLE_SCYTHE_AUTO4 = builder.nextAccessor("biped/battle_scythe/battle_scythe_auto4",
                (accessor) -> new BasicAttackAnimationEx(0.06F,  accessor, biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.2F, 0.3F, 0.35F, 0.4F, 0.4F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.4F, 0.5F, 0.55F, 0.6F, 0.6F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.6F, 0.9F, 1F, 1.1F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0.1f,0.95f, 10, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );
                 */

        BATTLE_SCYTHE_AUTO5 = builder.nextAccessor("biped/battle_scythe/battle_scythe_auto5",
                (accessor) -> new BasicAttackAnimation(0.06F, 0.25F, 0.4F, 0.8F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));

                /*.addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0.12f,0.35f, 8, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );

                 */

        BATTLE_SCYTHE_DASH = builder.nextAccessor("biped/battle_scythe/battle_scythe_dash",
                (accessor) -> new BasicAttackAnimationEx(0.1F,  accessor, biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.2F, 0.3F, 0.35F, 0.4F, 0.4F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.4F, 0.5F, 0.6F, 0.85F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.17f, 10, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );

                 */


        BATTLE_SCYTHE_SA1 = builder.nextAccessor("biped/battle_scythe/battle_scythe_sa1",
                (accessor) -> new BasicAttackAnimationEx(0.05F,  accessor, biped,
                new AttackAnimation.Phase(0.0F, 0.1833F, 0.2666F, 0.35F, 0.355F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.355F, 0.4333F, 0.4833F, 0.5416F, 0.5416F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.5416F, 0.58F, 0.6667F, 0.6667F, 0.6667F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.6667F, 0.7666F, 0.8833F, 0.8833F, 0.8833F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.8833F, 0.8833F, 1.05F, 1.05F, 1.05F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(1.05F, 1.05F, 1.15F, 1.6F, 1.6F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),

                new AttackAnimation.Phase(1.6F, 1.65F, 1.7666F, 1.9666F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT))
                .Lock(true, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NEUTRALIZE)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0f)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0f, 1.7f))
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, null)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, TraceLockedTargetEx(3))
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(0.7f))
                .addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, MyAnimations::empty_pose_modifier)
                .addEvents(new AnimationEvent.InTimeEvent[] {
                        AnimationEvent.InTimeEvent.create(0.4833f, (entitypatch, anim, objs) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.getOriginal().level()
                                    .addParticle(Particles.ENTITY_AFTER_IMG_EX.get(),
                                            Double.longBitsToDouble(entity.getId()), 0,
                                            Double.longBitsToDouble(BATTLE_SCYTHE_SA1.get().getId()),
                                            0.5, Double.longBitsToDouble(new Color(84, 16, 167, 50).getRGB()), Double.longBitsToDouble(35));
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(0.6666f, (entitypatch, anim, objs) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.getOriginal().level()
                                    .addParticle(Particles.ENTITY_AFTER_IMG_EX.get(),
                                            Double.longBitsToDouble(entity.getId()),0,
                                            Double.longBitsToDouble(BATTLE_SCYTHE_SA1.get().getId()),
                                            0.67f, Double.longBitsToDouble(new Color(84, 16, 167, 50).getRGB()), Double.longBitsToDouble(30));
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(1.4666f, (entitypatch, anim, objs) -> {
                            Entity entity = entitypatch.getOriginal();
                            entitypatch.getOriginal().level()
                                    .addParticle(Particles.ENTITY_AFTER_IMG_EX.get(),
                                            Double.longBitsToDouble(entity.getId()), 0,
                                            Double.longBitsToDouble(BATTLE_SCYTHE_SA1.get().getId()),
                                            1.4666f, Double.longBitsToDouble(new Color(84, 16, 167, 50).getRGB()), Double.longBitsToDouble(20));
                        }, AnimationEvent.Side.CLIENT)
                }));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.1F,0.25F, 8, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.4333F,0.4833F, 6, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.5800F,0.6667F, 6, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.7666F,1.05F, 8, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(1.6500F,1.7666F, 6, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );

                 */



        SAO_DUAL_SWORD_HOLD =builder.nextAccessor("biped/living/sao_dual_sword_hold",
                (accessor) ->  new StaticAnimation(true, accessor, biped));
        SAO_DUAL_SWORD_NORMAL = builder.nextAccessor("biped/living/sao_dual_sword_hold_normal",
                (accessor) -> new StaticAnimation(true, accessor, biped));
        SAO_DUAL_SWORD_RUN = builder.nextAccessor("biped/living/sao_dual_sword_hold_run",
                    (accessor) -> new MovementAnimation(true, accessor, biped));
        SAO_DUAL_SWORD_WALK = builder.nextAccessor("biped/living/sao_dual_sword_walk",
                (accessor) -> new MovementAnimation(true, accessor, biped));


        SAO_DUAL_SWORD_AUTO1 = builder.nextAccessor("biped/sao_dual_sword/sao_dual_sword_auto1",
                (accessor) ->new BasicAttackAnimation(0.05F, 0.1F, 0.2F, 0.3F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.05f,0.18f, 5, biped.get().toolR, InteractionHand.MAIN_HAND)
                ));

         */

        SAO_DUAL_SWORD_AUTO2 = builder.nextAccessor("biped/sao_dual_sword/sao_dual_sword_auto2",
                (accessor) ->new BasicAttackAnimation(0.05F, 0.01F, 0.2F, 0.3F, InteractionHand.OFF_HAND ,null, biped.get().toolL, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.01f,0.18f, 5, biped.get().toolL, InteractionHand.OFF_HAND)
                ));

         */

        SAO_DUAL_SWORD_AUTO3 = builder.nextAccessor("biped/sao_dual_sword/sao_dual_sword_auto3",
                (accessor) ->new BasicAttackAnimation(0.05F, 0.01F, 0.2F, 0.3F, InteractionHand.OFF_HAND ,null, biped.get().toolL, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.01f,0.18f, 5, biped.get().toolL, InteractionHand.OFF_HAND)
                ));

         */

        SAO_DUAL_SWORD_AUTO4 = builder.nextAccessor("biped/sao_dual_sword/sao_dual_sword_auto4",
                (accessor) ->new BasicAttackAnimation(0.05F, accessor, biped,
                new AttackAnimation.Phase(0.0F, 0.05F, 0.15F, 0.15F, 0.15F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.15F, 0.15F, 0.3F, 0.4F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.02f,0.15f, 5, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.12f,0.3f, 5, biped.get().toolL, InteractionHand.OFF_HAND)
                ));

         */
        //auto4:右手，antic:0.05F,contact:0.15F
        //auto4:左手，antic:0.15F,contact:0.3F

        SAO_DUAL_SWORD_AUTO5 = builder.nextAccessor("biped/sao_dual_sword/sao_dual_sword_auto5",
                (accessor) ->new BasicAttackAnimation(0.05F, accessor, biped,
                new AttackAnimation.Phase(0.0F, 0.15F, 0.2F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.2F, 0.2F, 0.25F, 0.3F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.1f,0.25f, 5, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.15f,0.35f, 5, biped.get().toolL, InteractionHand.OFF_HAND)
                ));

         */
        //auto5:右手，antic:0.15F,contact:0.2F
        //auto5:左手，antic:0.2F,contact:0.3F
        SAO_DUAL_SWORD_AUTO6 = builder.nextAccessor("biped/sao_dual_sword/sao_dual_sword_auto6"  ,
                (accessor) ->new BasicAttackAnimation(0.05F, accessor, biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.1F, 0.2F, 0.2F,
                        Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, BothHand())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.05f,0.25f, 5, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.05f,0.25f, 5, biped.get().toolL, InteractionHand.OFF_HAND)
                ));

         */
        //auto6:右手，antic:0.1F,contact:0.2F
        //auto6:左手，antic:0.1F,contact:0.2F
        SAO_DUAL_SWORD_AUTO7 = builder.nextAccessor( "biped/sao_dual_sword/sao_dual_sword_auto7" ,
                (accessor) ->new BasicAttackAnimation(0.05F, accessor, biped,
                new AttackAnimation.Phase(0.0F, 0.01F, 0.01F, 0.1F, 0.2F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, BothHand())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.15F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.31F)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.15f, 5, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.0f,0.15f, 5, biped.get().toolL, InteractionHand.OFF_HAND)
                ));

         */
        //auto7:右手，antic:0.01F,contact:0.1F
        //autO7:左手，antic:0.01F,contact:0.1F
        SAO_DUAL_SWORD_AUTO8 = builder.nextAccessor( "biped/sao_dual_sword/sao_dual_sword_auto8" ,
                (accessor) ->new BasicAttackAnimation(0.05F, accessor, biped,
                new AttackAnimation.Phase(0.0F, 0.05F, 0.05F, 0.1F, 0.15F, 0.15F, false, InteractionHand.MAIN_HAND, BothHand())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.85F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.15F, 0.15F, 0.15F, 0.2F, 0.2F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, BothHand())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.85F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.4F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.12f, 5, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.0f,0.12f, 5, biped.get().toolL, InteractionHand.OFF_HAND),
                        newTF(0.12f,0.25f, 5, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.12f,0.25f, 5, biped.get().toolL, InteractionHand.OFF_HAND)
                ));

         */
        //auto8:右手，antic:0.05F,contact:0.1F\antic:0.15F,contact:0.2F(单手两段攻击)
        //autO8:左手，antic:0.05F,contact:0.1F\antic:0.15F,contact:0.2F(单手两段攻击)
        SAO_DUAL_SWORD_AUTO9 = builder.nextAccessor( "biped/sao_dual_sword/sao_dual_sword_auto9" ,
                (accessor) ->new BasicAttackAnimation(0.05F,  accessor, biped,
                new AttackAnimation.Phase(0.0F, 0.01F, 0.01F , 0.1F, 0.2F, Float.MAX_VALUE, false, InteractionHand.MAIN_HAND, BothHand())
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.15f, 5, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.0f,0.15f, 5, biped.get().toolL, InteractionHand.OFF_HAND)
                ));

         */
        //auto9:右手，antic:0.01F,contact:0.1F
        //autO9:左手，antic:0.01F,contact:0.1F
        SAO_DUAL_SWORD_AUTO10 = builder.nextAccessor( "biped/sao_dual_sword/sao_dual_sword_auto10" ,
                (accessor) ->new BasicAttackAnimation(0.05F, 0.01F, 0.2F, 0.2F,null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.45F)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.15f, 5, biped.get().toolR, InteractionHand.MAIN_HAND)
                ));
         */
        SAO_DUAL_SWORD_AUTO11 = builder.nextAccessor( "biped/sao_dual_sword/sao_dual_sword_auto11",
                (accessor) ->new BasicAttackAnimation(0.05F, 0.01F,0.1F, 0.3F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.08F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.15f, 5, biped.get().toolR, InteractionHand.MAIN_HAND)
                ));

                 */

        SAO_DUAL_SWORD_AUTO12 = builder.nextAccessor( "biped/sao_dual_sword/sao_dual_sword_auto12" ,
                (accessor) ->new BasicAttackAnimation(0.05F, 0.01F, 0.1F, 0.6F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.15f, 5, biped.get().toolR, InteractionHand.MAIN_HAND)
                ));

                 */

        SAO_DUAL_SWORD_DASH = builder.nextAccessor( "biped/sao_dual_sword/sao_dual_sword_dash" ,
                (accessor) ->new BasicAttackAnimationEx(0.02F, accessor, biped,
                new AttackAnimation.Phase(0.0F, 0.07F, 0.6F, 0.7F,  0.7F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN),
                new AttackAnimation.Phase(0.7F, 0.75F, 0.9F, 0.9F,  0.9F, InteractionHand.OFF_HAND, biped.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN),
                new AttackAnimation.Phase(0.82F, 0.84F, 1.02F, 1.1666F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, null)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, null)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed( 2.1f)));
        /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0.0f,0.65f, 5, biped.get().toolR, InteractionHand.MAIN_HAND),
                        newTF(0.65f,0.95f, 5, biped.get().toolL, InteractionHand.OFF_HAND),
                        newTF(0.75f,1.05f, 5, biped.get().toolR, InteractionHand.MAIN_HAND)
                ));
         */

        SAO_DUAL_SWORD_SA1 = builder.nextAccessor( "biped/sao_dual_sword/sao_dual_sword_sa1" ,
                (accessor) ->new BasicAttackAnimationEx(0.1F, accessor, biped,
                new AttackAnimation.Phase(0.0F, 1.3F, 1.4F, 1.4F,  1.4F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)

                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN),
                new AttackAnimation.Phase(1.4F, 1.4F, 1.5F, 1.733F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.get().rootJoint, WeaponCollider.GenShin_Bow_FallAttack)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN))
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0F)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                //.addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, null)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, MoveCoordFuncUtils.TraceLockedTargetEx(7))
                //.addProperty(AnimationProperty.ActionAnimationProperty.COORD_GET, MoveCoordFunctions.WORLD_COORD)
                //.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed( 1f))
                .addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, MyAnimations::empty_pose_modifier)
                .addEvents(
                        AnimationEvent.InTimeEvent
                                .create(0.1F, (entitypatch, anim, objs) -> {
                                    SAOSkillAnimUtils.playSound(entitypatch.getOriginal(), Sounds.DualSword_SA1_1.get());
                                }, AnimationEvent.Side.SERVER),
                        AnimationEvent.InTimeEvent
                                .create(1.15F, (entitypatch, anim, objs) -> {
                                    SAOSkillAnimUtils.playSound(entitypatch.getOriginal(), Sounds.DualSword_SA1_2.get());
                                }, AnimationEvent.Side.SERVER),
                        AnimationEvent.InTimeEvent
                                .create(1.5F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE
                                        , AnimationEvent.Side.SERVER)
                                .params(new Vec3f(0.0F, -1F, 0F), biped.get().rootJoint, 5D, 0.7F),
                        AnimationEvent.InTimeEvent
                                .create(1.4F, (entitypatch, anim, objs) -> {
                                    SAOSkillAnimUtils.DualSwordSA.LandingStrike(entitypatch.getOriginal());
                                }, AnimationEvent.Side.CLIENT))
        );

        SAO_RAPIER_IDLE = builder.nextAccessor( "biped/sao_rapier/living/sao_rapier_idle" ,
                (accessor) ->new StaticAnimation(true, accessor, biped));
        SAO_RAPIER_WALK = builder.nextAccessor( "biped/sao_rapier/living/sao_rapier_walk" ,
                (accessor) ->new MovementAnimation(true, accessor, biped));
        SAO_RAPIER_RUN = builder.nextAccessor( "biped/sao_rapier/living/sao_rapier_run" ,
                (accessor) ->new MovementAnimation(true, accessor, biped));

        SAO_RAPIER_AUTO1 = builder.nextAccessor( "biped/sao_rapier/sao_rapier_auto1" ,
                (accessor) ->new BasicAttackAnimation(0.05F, 0.1F, 0.2F, 0.3F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.1f, 5, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );*/

        SAO_RAPIER_AUTO2 = builder.nextAccessor( "biped/sao_rapier/sao_rapier_auto2" ,
                (accessor) ->new BasicAttackAnimation(0.05F, 0.1F, 0.2F, 0.3F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.3f, 5, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );

                 */

        SAO_RAPIER_AUTO3 = builder.nextAccessor( "biped/sao_rapier/sao_rapier_auto3" ,
                (accessor) ->new BasicAttackAnimation(0.02F, 0.1F, 0.2F, 0.4F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.2f, 5,biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );
                 */

        SAO_RAPIER_AUTO4 = builder.nextAccessor( "biped/sao_rapier/sao_rapier_auto4" ,
                (accessor) ->new BasicAttackAnimation(0.05F,accessor, biped,
                new AttackAnimation.Phase(0.0F,0.1F,0.15F,0.2F,0.2F,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.2F,0.25F,0.35F,0.5F,Float.MAX_VALUE,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.1f, 5, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );*/

        SAO_RAPIER_AUTO5 = builder.nextAccessor( "biped/sao_rapier/sao_rapier_auto5" ,
                (accessor) ->new BasicAttackAnimation(0.02F, 0.2F, 0.3F, 0.65F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));
                /*
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.3f, 5, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );*/

        SAO_RAPIER_DASH  = builder.nextAccessor("biped/sao_rapier/sao_rapier_dash",
                (accessor) ->new DashAttackAnimation(0.02F, 0.1833F, 0.1833F, 0.3666F, 0.38F, WeaponCollider.SAO_RAPIER_DASH_SHORT, biped.get().rootJoint, accessor , biped)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(14.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, null)
        );
                /*
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0, 0.3F)).addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0.16f,0.32f, 5, biped.get().toolR, InteractionHand.MAIN_HAND)
                        )
                );

                 */

        SAO_RAPIER_AIR  = builder.nextAccessor("biped/sao_rapier/sao_rapier_air"  ,
                (accessor) ->new BasicAttackAnimation(0.12F, 0.133F, 0.05F, 0.2F, 0.3F, WeaponCollider.SAO_RAPIER_DASH_SHORT, biped.get().rootJoint, accessor, biped)
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0, 0.2F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
        );

        SAO_RAPIER_SPECIAL_DASH  = builder.nextAccessor( "biped/sao_rapier/sao_rapier_dash_long" ,
                (accessor) ->new BasicAttackAnimation(0.1F, 0.3F, 0.05F, 4.8333F, 5.0F, WeaponCollider.SAO_RAPIER_DASH, biped.get().rootJoint, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(14.7F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(114514))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.21F)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, null)
                .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, null)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0, 4.8333F))
                .addProperty(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, List.of(
                        AnimationEvent.SimpleEvent.create((ep, anim, objs) -> {
                            if(ep instanceof PlayerPatch){
                                ep.setMaxStunShield(114514.0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, AnimationEvent.Side.SERVER)
                ))
                .addProperty(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, List.of(
                        AnimationEvent.SimpleEvent.create((ep, anim, objs) -> {
                            if(ep instanceof PlayerPatch){
                                ep.setMaxStunShield(0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, AnimationEvent.Side.SERVER)
                ))
        );

        SAO_RAPIER_SA2  = builder.nextAccessor( "biped/sao_rapier/sao_rapier_sa2" ,
                (accessor) ->new DeferredDamageAttackAnimation(0.01F, 0.3f,0.72f, 1.48F, InteractionHand.MAIN_HAND, WeaponCollider.SAO_RAPIER_SCAN, biped.get().rootJoint, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0, 1.5f))
                .addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, MyAnimations::empty_pose_modifier)
                .addProperty(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, List.of(
                        AnimationEvent.SimpleEvent.create((ep, anim, objs) -> {
                            SAOSkillAnimUtils.RapierSA.prev(ep);
                        }, AnimationEvent.Side.CLIENT)
                ))
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.65f, (ep, anim, objs) -> {
                            SAOSkillAnimUtils.RapierSA.HandleAtk1(ep);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(1.15f, (ep, anim, objs) -> {
                            SAOSkillAnimUtils.RapierSA.post(ep);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.InTimeEvent.create(1.35f, (ep, anim, objs) -> {
                            SAOSkillAnimUtils.RapierSA.HandleAtk2(ep);
                        }, AnimationEvent.Side.BOTH)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(1.2f)));

        DMC5_V_JC  = builder.nextAccessor( "biped/dmc5_v_jc" ,
                (accessor) ->new DeferredDamageAttackAnimation(0.02F, 0.334f, 0.43f, 4.48F, InteractionHand.MAIN_HAND, WeaponCollider.DMC_JC, biped.get().rootJoint, accessor, biped)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0f)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                        .addProperty(MyProperties.MOVE_ROOT_PHASE, new MyProperties.SpecialPhase(0, 5.35f))
                        .addProperty(MyProperties.INVISIBLE_PHASE, new MyProperties.SpecialPhase(0.85f,2.8f))
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0, 4.48f))
                        .addProperty(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, List.of(
                                AnimationEvent.SimpleEvent.create((ep, anim, objs) -> {
                                    DMC_V_JC_Server.prev(ep);
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.SimpleEvent.create((ep, anim, objs) -> {
                                    DMC_V_JC_Client.prev(ep);
                                }, AnimationEvent.Side.CLIENT)
                        ))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.4f, (ep, anim, objs) -> {
                                    DMC_V_JC_Server.HandleAtk1(ep);
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.85f, (ep, anim, objs) -> {
                                    DMC_V_JC_Server.post1(ep);
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.0f, (ep, anim, objs) -> {
                                    DMC_V_JC_Server.post2(ep);
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(1.3f, (ep, anim, objs) -> {
                                    DMC_V_JC_Server.post3(ep);
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(3.733f, (ep, anim, objs) -> {
                                    DMC_V_JC_Server.postAttack(ep);
                                }, AnimationEvent.Side.SERVER),

                                AnimationEvent.InTimeEvent.create(0.4f, (ep, anim, objs) -> {
                                    DMC_V_JC_Client.HandleAtk1(ep);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(0.85f, (ep, anim, objs) -> {
                                    DMC_V_JC_Client.post1(ep);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.0f, (ep, anim, objs) -> {
                                    DMC_V_JC_Client.post2(ep);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(1.3f, (ep, anim, objs) -> {
                                    DMC_V_JC_Client.post3(ep);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.InTimeEvent.create(3.733f, (ep, anim, objs) -> {
                                    DMC_V_JC_Client.postAttack(ep);
                                }, AnimationEvent.Side.CLIENT)
                        )
                        .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, new AnimationEvent[]{
                                AnimationEvent.SimpleEvent.create((entityPatch, anim, objs) -> {
                                    if (entityPatch.getOriginal() instanceof Player) {
                                        (entityPatch.getOriginal()).getMainHandItem().getOrCreateTag().putBoolean("unsheathed", false);
                                    }
                                }, AnimationEvent.Side.CLIENT)
                        })
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(1)));

        HSR_ACHERON_SA = builder.nextAccessor( "biped/hsr_acheron/hsr_acheron_sa" ,
                (accessor) ->new BasicAttackAnimationEx(0.15F, accessor, biped,
                        new AttackAnimation.Phase(0.0F,0.5F,0.6F,0.7F,0.7F,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                        new AttackAnimation.Phase(0.7F,0.8833F,1F,1.2F,1.2F,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                        new AttackAnimation.Phase(1.2F,1.35F,1.5F,1.65F,1.65F,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                        new AttackAnimation.Phase(1.65F,1.75F,1.9F,2.0F,2.0F,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                        new AttackAnimation.Phase(2.0F,2.18F,2.28F,2.5F,2.5F,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                        new AttackAnimation.Phase(2.5F,3.1F,3.38F,3.45F,3.45F,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                        // 3.55 fire camera animation 2
                        new AttackAnimation.Phase(3.45F,3.95F,4.2F,5.3F,5.5167F,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                        // 4.91 fire final cut event
                ).Lock(true, false)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NEUTRALIZE)
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                        .addProperty(MyProperties.AFTER_DAMAGE_ENTITY, (anim, phase, attacker, target, dmg) -> {
                            target.addEffect(new MobEffectInstance(MobEffects.STOP.get(), 20, 1));
                        })
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0f)
                        .addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(0f, 5f))
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, null)
                        .addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, TraceLockedTargetEx(2))
                        .addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, MyAnimations::empty_pose_modifier)
                        .addProperty(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, List.of(
                                AnimationEvent.SimpleEvent.create((ep, anim, para) -> {
                                    ACHERON_SA_CLIENT.prev(ep);
                                }, AnimationEvent.Side.CLIENT)
                        ))
                        .addEvents(AnimationEvent.InTimeEvent.create(3.6f, (ep, anim, para) -> {
                                    ACHERON_SA_CLIENT.correct_camera(ep);
                                }, AnimationEvent.Side.CLIENT)
                        )
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(1f)));



        //todo
        /*
        SAO_LONGSWORD_VARIANT_AUTO1 = builder.nextAccessor(  ,
                (accessor) ->new BasicAttackAnimation(0.05F, 0.2F, 0.3F, 0.4F, null,
                biped.get().toolR, "biped/sao_longsword_variant/sao_longsword_variant_auto1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F);

        SAO_LONGSWORD_VARIANT_AUTO2 = builder.nextAccessor(  ,
                (accessor) ->new BasicAttackAnimation(0.05F, 0.2F, 0.3F, 0.4F, null,
                biped.get().toolR, "biped/sao_longsword_variant/sao_longsword_variant_auto2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
        ;

        SAO_LONGSWORD_VARIANT_AUTO3 = builder.nextAccessor(  ,
                (accessor) ->new BasicAttackAnimation(0.05F, "biped/sao_longsword_variant/sao_longsword_variant_auto3", biped,
                new AttackAnimation.Phase(0.0F,0.2F,0.3F,0.35F,0.35F,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.35F,0.4F,0.5F,0.55F,Float.MAX_VALUE,InteractionHand.MAIN_HAND,biped.get().toolR,null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
        ;

        SAO_LONGSWORD_VARIANT_AUTO4 = builder.nextAccessor(  ,
                (accessor) ->new BasicAttackAnimation(0.05F, 0.2F, 0.3F, 0.4F, null,
                biped.get().toolR, "biped/sao_longsword_variant/sao_longsword_variant_auto4", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
        ;

        SAO_LONGSWORD_VARIANT_AUTO5 = builder.nextAccessor(  ,
                (accessor) ->new BasicAttackAnimation(0.05F, 0.4F, 0.5F, 0.7F, null,
                biped.get().toolR, "biped/sao_longsword_variant/sao_longsword_variant_auto5", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
        ;

        SAO_LONGSWORD_VARIANT_DASH = builder.nextAccessor(  ,
                (accessor) ->new BasicAttackAnimation(0.05F, 0.4F, 0.5F, 0.7F, null,
                biped.get().toolR, "biped/sao_longsword_variant/sao_longsword_variant_dash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.SPARKS_SPLASH_HIT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
        ;

        SAO_LONGSWORD_VARIANT_IDLE = new StaticAnimation(true, "biped/sao_longsword_variant/living/sao_longsword_variant_idle", biped);
        SAO_LONGSWORD_VARIANT_WALK = new MovementAnimation(true, "biped/sao_longsword_variant/living/sao_longsword_variant_walk", biped);
        SAO_LONGSWORD_VARIANT_RUN = new MovementAnimation(true, "biped/sao_longsword_variant/living/sao_longsword_variant_run", biped);
        SAO_LONGSWORD_VARIANT_JUMP = new StaticAnimation(true, "biped/sao_longsword_variant/living/sao_longsword_variant_jump", biped);

         */
        registerGenShinImpact(builder);
    }

    public static void registerGenShinImpact(AnimationManager.AnimationBuilder builder){
        Armatures.ArmatureAccessor<HumanoidArmature> biped = Armatures.BIPED;

        GS_Yoimiya_Auto1 = builder.nextAccessor( "biped/gs_yoimiya/gs_yoimiya_auto1" ,
                (accessor) ->new ScanAttackAnimation(0.1F, 0,0.62F, 0.8333F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.get().rootJoint, accessor, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.4F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.get().toolL);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(0.585F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.get().toolL);
                        }, AnimationEvent.Side.BOTH)
                )
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2.75f)));

        GS_Yoimiya_Auto2 = builder.nextAccessor( "biped/gs_yoimiya/gs_yoimiya_auto2" ,
                (accessor) ->new ScanAttackAnimation(0.1F, 0,0.7F, 0.98F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.get().rootJoint, accessor, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.6F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.get().toolR);
                        }, AnimationEvent.Side.BOTH)
                ));

        GS_Yoimiya_Auto3 = builder.nextAccessor( "biped/gs_yoimiya/gs_yoimiya_auto3" ,
                (accessor) ->new ScanAttackAnimation(0.1F, 0,0.88F, 1.03F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.get().rootJoint, accessor, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2.95f))
                .addEvents(
                        AnimationEvent.InTimeEvent.create(0.84F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.get().toolL);
                        }, AnimationEvent.Side.BOTH)
                ));

        GS_Yoimiya_Auto4 = builder.nextAccessor( "biped/gs_yoimiya/gs_yoimiya_auto4" ,
                (accessor) ->new ScanAttackAnimation(0.05F, 0,2.12F, 2.733F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.get().rootJoint, accessor, biped)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                .addEvents(
                        AnimationEvent.InTimeEvent.create(1.2083F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.get().toolL);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(1.7916F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.get().toolR);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(2.0416F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.get().toolL);
                        }, AnimationEvent.Side.BOTH))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(3.1f)));

        GS_Yoimiya_Auto5 = builder.nextAccessor( "biped/gs_yoimiya/gs_yoimiya_auto5" ,
                (accessor) ->new ScanAttackAnimation(0.02F, 0,0.2F, 1.51F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.get().rootJoint, accessor, biped)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER,MSpeed( 3.1f))
                        .addEvents(AnimationEvent.InTimeEvent.create(0.7083F, (ep, anim, objs) -> {
                                    YoimiyaSkillFunction.BowShoot(ep, biped.get().toolL);
                                }, AnimationEvent.Side.BOTH)
                        ));

        GS_Yoimiya_SA = builder.nextAccessor( "biped/gs_yoimiya/gs_yoimiya_sa" ,
                (accessor) ->new YoimiyaSAAnimation(0.02F, 0.5F, 4.56F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.get().rootJoint, accessor, biped)
                        .addProperty(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, List.of(
                                AnimationEvent.SimpleEvent.create((ep, anim, objs) -> {
                                    CameraEvents.SetAnim(YOIMIYA_SA, ep.getOriginal(), true);
                                }, AnimationEvent.Side.CLIENT),
                                AnimationEvent.SimpleEvent.create((ep, anim, objs) -> {
                                    YoimiyaSkillFunction.YoimiyaSAFirework(ep);
                                    if(ep instanceof PlayerPatch){
                                        ep.setMaxStunShield(114514.0f);
                                        ep.setStunShield(ep.getMaxStunShield());
                                    }
                                }, AnimationEvent.Side.SERVER)

                        ))
                        .addProperty(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, List.of(
                                AnimationEvent.SimpleEvent.create((ep, anim, objs) -> {
                                    if(ep instanceof PlayerPatch){
                                        ep.setMaxStunShield(0f);
                                        ep.setStunShield(ep.getMaxStunShield());
                                    }
                                }, AnimationEvent.Side.SERVER)
                        ))
                        .addEvents(AnimationEvent.InTimeEvent.create(2.375F, (ep, anim, objs) -> {
                                    YoimiyaSkillFunction.YoimiyaSA(ep);
                                }, AnimationEvent.Side.SERVER))
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0f)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2.4f)));

        GS_Yoimiya_FallAtk_Last = builder.nextAccessor( "biped/gs_yoimiya/gs_yoimiya_fall_atk_last" ,
                (accessor) ->new FallAtkFinalAnim(0.05F, 0.8F, 1.5F, 2.1F, WeaponCollider.GenShin_Bow_FallAttack, biped.get().rootJoint, accessor, biped)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, Sounds.GENSHIN_BOW_FALLATK.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, Sounds.GENSHIN_BOW_FALLATK.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(114514))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1))
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(7f))
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1f, (ep, anim, objs) -> {
                                    YoimiyaSkillFunction.fallFinal(ep);
                                }, AnimationEvent.Side.SERVER)
                        ));

        GS_Yoimiya_FallAtk_Loop = builder.nextAccessor( "biped/gs_yoimiya/gs_yoimiya_fall_atk_loop" ,
                (accessor) ->new FallAtkLoopAnim(0.1f, accessor, biped, GS_Yoimiya_FallAtk_Last));

        GS_Yoimiya_FallAtk_Start = builder.nextAccessor( "biped/gs_yoimiya/gs_yoimiya_fall_atk_start" ,
                (accessor) ->new FallAtkStartAnim(0.1f,accessor, biped, GS_Yoimiya_FallAtk_Loop)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(3.6f)));

        //lh
        GS_LAODENG_AUTO1 = builder.nextAccessor(  "biped/laodeng/gs_laodeng_auto1",
                (accessor) ->new BasicAttackAnimation(0.08F, 0.2667F, 0.38F, 0.50F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.2F));

        GS_LAODENG_AUTO2 = builder.nextAccessor( "biped/laodeng/gs_laodeng_auto2" ,
                (accessor) ->new BasicAttackAnimation(0.05F, 0.05F, 0.17F, 0.3F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.2F));


        GS_LAODENG_AUTO3 = builder.nextAccessor( "biped/laodeng/gs_laodeng_auto3" ,
                (accessor) ->new BasicAttackAnimation(0.08F, 0.15F, 0.28F, 0.35F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F));

        //lh
        GS_LAODENG_AUTO4 = builder.nextAccessor( "biped/laodeng/gs_laodeng_auto4" ,
                (accessor) ->new BasicAttackAnimation(0.1F, 0.33F, 0.4333F, 0.55F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.2F));

        //lh
        GS_LAODENG_AUTO5 = builder.nextAccessor( "biped/laodeng/gs_laodeng_auto5" ,
                (accessor) ->new BasicAttackAnimationEx(0.06F,  accessor, biped,
                new AttackAnimation.Phase(0.1F, 0.45F, 0.5F, 0.5F, 0.5F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                        ,

                new AttackAnimation.Phase(0.5F, 0.5F, 0.59F, 0.59F, 0.59F, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.59F, 0.59F, 0.7F, 0.85F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT))
                .Lock(true, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.2F));

        GS_LAODENG_AUTO6 = builder.nextAccessor(  "biped/laodeng/gs_laodeng_auto6",
                (accessor) ->new BasicAttackAnimation(0.1F, 0.12F, 0.32F, 0.55F, null, biped.get().toolR, accessor, biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, Particles.BLACK_KNIGHT)
                //.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.2F));
    }


    public static AnimationProperty.PlaybackSpeedModifier MSpeed(float t){
        return (anim,entity,a,b,c) -> t;
    }


    public static List<TrailInfo> newTFL(TrailInfo... tfs){
        return Lists.newArrayList(tfs);
    }

    public static TrailInfo newTF(float start, float end, int lifetime, Joint joint, InteractionHand hand){
        JsonObject je = new JsonObject();
        je.addProperty("joint", joint.getName());
        je.addProperty("start_time", start);
        je.addProperty("end_time", end);
        je.addProperty("item_skin_hand", hand.toString());
        je.addProperty("lifetime", lifetime);
        //je.addProperty("fade_time", 0.1f);
        //System.out.println(je);
        return TrailInfo.deserialize(je);
    }

    public static AttackAnimation.JointColliderPair[] BothHand(){
        return new AttackAnimation.JointColliderPair[]{
                AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null)
        , AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null)};
    }

    static void empty_pose_modifier(DynamicAnimation var1, Pose var2, LivingEntityPatch<?> var3, float var4, float var5){

    }

    public static CameraAnimation YOIMIYA_SA;
    public static CameraAnimation SAO_RAPIER_SA2_CAM;
    public static CameraAnimation SAO_RAPIER_SA2_CAM2;
    public static CameraAnimation DMC_V_PREV;
    public static CameraAnimation HSR_ACHERON_SA_CAM1;
    public static void LoadCamAnims(){
        YOIMIYA_SA = CameraAnimation.load(OjangUtils.newRL(EpicACG.MODID, "camera_animation/yoimiya.json"));
        SAO_RAPIER_SA2_CAM = CameraAnimation.load(OjangUtils.newRL(EpicACG.MODID, "camera_animation/sao_rapier_sa2.json"));
        SAO_RAPIER_SA2_CAM2 = CameraAnimation.load(OjangUtils.newRL(EpicACG.MODID, "camera_animation/sao_rapier_sa2_post.json"));
        DMC_V_PREV = CameraAnimation.load(OjangUtils.newRL(EpicACG.MODID, "camera_animation/dmc_v_prev.json"));

        HSR_ACHERON_SA_CAM1 = CameraAnimation.load(OjangUtils.newRL(EpicACG.MODID, "camera_animation/hsr_acheron_sa1.json"));

    }

}
