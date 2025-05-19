package com.dfdyz.epicacg.efmextra.skills.GenShinInternal;

import com.dfdyz.epicacg.EpicACG;

import com.dfdyz.epicacg.registry.MySkills;
import com.dfdyz.epicacg.utils.OjangUtils;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.*;

public class GSBowInternal extends Skill {
    public static SkillBuilder<GSBowInternal> GetBuilder(String registryName){
        return new SkillBuilder<GSBowInternal>()
                .setRegistryName(OjangUtils.newRL(EpicACG.MODID, registryName))
                .setCategory(SkillCategories.WEAPON_PASSIVE);
    }

    public GSBowInternal(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getSkillCapability().skillContainers[SkillSlots.AIR_ATTACK.universalOrdinal()].setSkill(MySkills.GS_Bow_FallAttackPatch);
        container.getExecutor().getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(MySkills.GS_Bow_BasicAttackPatch);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecutor().getSkillCapability().skillContainers[SkillSlots.AIR_ATTACK.universalOrdinal()].setSkill(EpicFightSkills.AIR_ATTACK);
        container.getExecutor().getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(EpicFightSkills.BASIC_ATTACK);

    }
}
