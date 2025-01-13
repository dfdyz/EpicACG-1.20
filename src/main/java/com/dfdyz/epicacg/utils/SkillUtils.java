package com.dfdyz.epicacg.utils;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.client.model.ItemSkin;
import yesman.epicfight.api.client.model.ItemSkins;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.awt.*;

public class SkillUtils {

    public static Skill getMainHandSkill(PlayerPatch pp){
        return pp.getHoldingItemCapability(InteractionHand.MAIN_HAND).getInnateSkill(pp, pp.getValidItemInHand(InteractionHand.MAIN_HAND));
    }


    public static Color getItemTrailColor(LivingEntityPatch entityPatch, InteractionHand hand){
        Color color = null;
        if(!entityPatch.getValidItemInHand(hand).isEmpty()){
            ItemSkin is = ItemSkins.getItemSkin(entityPatch.getValidItemInHand(hand).getItem());
            if(is != null){
                //if(getIte)
            }
        }

        return color;
    }

}
