package com.dfdyz.epicacg.efmextra.skills.GenShinInternal;

import com.dfdyz.epicacg.EpicACG;

import com.dfdyz.epicacg.registry.MySkills;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class GSBowInternal extends Skill {
    public static Builder<GSBowInternal> GetBuilder(String registryName){
        return new Builder<GSBowInternal>()
                .setRegistryName(new ResourceLocation(EpicACG.MODID, registryName))
                .setCategory(SkillCategories.WEAPON_PASSIVE);
    }

    public GSBowInternal(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getSkillCapability().skillContainers[SkillSlots.AIR_ATTACK.universalOrdinal()].setSkill(MySkills.GS_Bow_FallAttackPatch);
        container.getExecuter().getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(MySkills.GS_Bow_BasicAttackPatch);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getSkillCapability().skillContainers[SkillSlots.AIR_ATTACK.universalOrdinal()].setSkill(EpicFightSkills.AIR_ATTACK);
        container.getExecuter().getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(EpicFightSkills.BASIC_ATTACK);

    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
    }
}
