package com.dfdyz.epicacg.efmextra.skills.SAO.skillevents;

import com.dfdyz.epicacg.efmextra.anims.DeferredDamageAttackAnimation;
import com.dfdyz.epicacg.efmextra.skills.SAO.SingleSwordSASkills;
import com.dfdyz.epicacg.registry.MobEffects;
import com.dfdyz.epicacg.registry.Sounds;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;

import static com.dfdyz.epicacg.efmextra.skills.SAO.skillevents.SAOSkillAnimUtils.HurtEntity;
import static com.dfdyz.epicacg.efmextra.skills.SAO.skillevents.SAOSkillAnimUtils.playSound;
import static com.dfdyz.epicacg.registry.MyAnimations.DMC5_V_JC;
import static com.dfdyz.epicacg.registry.MySkillDataKeys.JCE_Invincible;

public class DMC_V_JC_Server {

    public static void prev(LivingEntityPatch<?> entityPatch){
        //System.out.println("AAAAA");
        if(entityPatch instanceof ServerPlayerPatch pp){
            SkillContainer sc = pp.getSkill(SkillSlots.WEAPON_INNATE);
            if(sc.getDataManager().hasData(JCE_Invincible.get())){
                sc.getDataManager().setData(JCE_Invincible.get(), true);
            }
        }
    }

    public static void HandleAtk1(LivingEntityPatch<?> entityPatch){
        //CameraEvents.SetAnim(SAO_RAPIER_SA2_CAM, (LivingEntity) entityPatch.getOriginal(), true);

        if(entityPatch.getCurrenltyAttackedEntities().size() > 0){
            //System.out.println("????");
            entityPatch.getCurrenltyAttackedEntities().forEach((entity)->{
                if(entity != null
                        && entity.isAlive()
                        && entity.equals(entityPatch.getOriginal())
                        && entity.distanceTo(entityPatch.getOriginal()) < 9
                ) return;
                entity.addEffect(new MobEffectInstance(MobEffects.STOP.get(), 67, 1));
            });
        }

    }
    //public static OpenMatrix4f matrix4f = new OpenMatrix4f();
    public static void post1(LivingEntityPatch<?> entityPatch){
        playSound(entityPatch.getOriginal(), Sounds.DMC5_JC_1.get());
    }

    public static void post2(LivingEntityPatch<?> entityPatch){
    }

    public static void post3(LivingEntityPatch<?> entityPatch){

    }

    public static void postAttack(LivingEntityPatch<?> entityPatch){
        playSound(entityPatch.getOriginal(), Sounds.DMC5_JC_2.get());

        List<Entity> EntityMap = DeferredDamageAttackAnimation.getScannedEntities(entityPatch);
        if(EntityMap.size() > 0){
            EntityMap.forEach(
                    (entity) -> {
                        if(entity != null
                                && entity.isAlive()
                                && entity.equals(entityPatch.getOriginal())
                                && entity.distanceTo(entityPatch.getOriginal()) < 9
                        ) return;
                        HurtEntity(entityPatch, entity,DMC5_V_JC, 2f,0.25f);
                    }
            );
        }

        if(entityPatch instanceof ServerPlayerPatch pp){
            SkillContainer sc = pp.getSkill(SkillSlots.WEAPON_INNATE);
            if(sc.getDataManager().hasData(JCE_Invincible.get())){
                sc.getDataManager().setData(JCE_Invincible.get(), false);
            }
        }
    }
}
