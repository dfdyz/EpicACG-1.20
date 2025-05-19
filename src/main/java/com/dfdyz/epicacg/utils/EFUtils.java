package com.dfdyz.epicacg.utils;

import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class EFUtils {


    public static void InitBasicAttack(PlayerPatch<?> ep){
        var c =ep.getSkill(SkillSlots.BASIC_ATTACK).getDataManager();
        if(!c.hasData(SkillDataKeys.BASIC_ATTACK_ACTIVATE.get())){
            c.registerData(SkillDataKeys.BASIC_ATTACK_ACTIVATE.get());
        }
    }

    @FunctionalInterface
    public interface AttackAnimationProvider{
        public StaticAnimation get();
    }

}
