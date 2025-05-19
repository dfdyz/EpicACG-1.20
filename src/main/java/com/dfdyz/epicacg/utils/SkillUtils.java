package com.dfdyz.epicacg.utils;

import net.minecraft.world.InteractionHand;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.awt.*;

public class SkillUtils {

    public static Skill getMainHandSkill(PlayerPatch pp){
        return pp.getHoldingItemCapability(InteractionHand.MAIN_HAND).getInnateSkill(pp, pp.getValidItemInHand(InteractionHand.MAIN_HAND));
    }


    public static void InitBasicAttack(PlayerPatch<?> ep){
        var c =ep.getSkill(SkillSlots.BASIC_ATTACK).getDataManager();
        if(!c.hasData(SkillDataKeys.BASIC_ATTACK_ACTIVATE.get())){
            c.registerData(SkillDataKeys.BASIC_ATTACK_ACTIVATE.get());
        }
    }

    public static void InitBasicAttack(LivingEntityPatch<?> ep){
        if (ep instanceof PlayerPatch<?> playerPatch) InitBasicAttack(playerPatch);
    }

    public static void InitBasicAttack(SkillContainer sc){
        var c = sc.getDataManager();
        if(!c.hasData(SkillDataKeys.BASIC_ATTACK_ACTIVATE.get())){
            c.registerData(SkillDataKeys.BASIC_ATTACK_ACTIVATE.get());
        }
    }

}
