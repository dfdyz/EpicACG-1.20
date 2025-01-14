package com.dfdyz.epicacg.efmextra.skills.SAO;

import com.dfdyz.epicacg.efmextra.skills.EpicACGSkillSlot;
import com.dfdyz.epicacg.efmextra.skills.IMutiSpecialSkill;
import com.dfdyz.epicacg.efmextra.skills.MultiSpecialSkill;
import com.dfdyz.epicacg.efmextra.skills.SimpleWeaponSASkill;
import com.dfdyz.epicacg.registry.MyAnimations;
import com.dfdyz.epicacg.registry.MySkills;
import com.dfdyz.epicacg.utils.SkillUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.AttackAnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;

import static com.dfdyz.epicacg.registry.MySkillDataKeys.CHILD_SKILL_INDEX;

public class RapierSpicialAttackSkill extends SimpleWeaponSASkill implements IMutiSpecialSkill {
    private final ArrayList<ResourceLocation> childSkills = new ArrayList<>();
    private final ArrayList<ResourceLocation> childSkills2 = new ArrayList<>();
    private final AttackAnimationProvider Normal;
    private final AttackAnimationProvider OnRun;

    public RapierSpicialAttackSkill(Builder builder) {
        super(builder);
        this.Normal = () -> (AttackAnimation) MyAnimations.SAO_RAPIER_SA2;
        this.OnRun = () -> (AttackAnimation) MyAnimations.SAO_RAPIER_SPECIAL_DASH;
        ResourceLocation name = this.getRegistryName();
        //ResourceLocation tex = new ResourceLocation(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png");
        childSkills.add(new ResourceLocation(name.getNamespace(), "textures/gui/skills/sao_rapier_skill.png"));
        childSkills.add(new ResourceLocation(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png"));
        childSkills2.add(new ResourceLocation(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png"));
    }

    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return (new Builder(resourceLocation)).setCategory(SkillSlots.WEAPON_INNATE.category()).setResource(Resource.STAMINA);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getSkillCapability()
                .skillContainers[EpicACGSkillSlot.SKILL_SELECTOR.universalOrdinal()]
                .setSkill(MySkills.MUTI_SPECIAL_ATTACK);
    }

    @Override
    public boolean checkExecuteCondition(PlayerPatch<?> executer) {
        boolean ok = false;
        SkillContainer skillContainer = executer.getSkill(SkillSlots.WEAPON_INNATE);
        int selected = executer.getSkill(EpicACGSkillSlot.SKILL_SELECTOR).getDataManager().getDataValue(CHILD_SKILL_INDEX.get());

        if(!executer.getOriginal().isSprinting()){
            ok = skillContainer.getStack() > (selected == 0 ? 1:0);
        }
        else{
            ok = skillContainer.getStack() > 0;
        }

        return ok || (executer.getOriginal()).isCreative();
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }


    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        SkillContainer skill = executer.getSkill(SkillSlots.WEAPON_INNATE);
        int selected = executer.getSkill(EpicACGSkillSlot.SKILL_SELECTOR).getDataManager().getDataValue(CHILD_SKILL_INDEX.get());

        if (executer.getOriginal().isSprinting()){
            executer.playAnimationSynchronized(this.OnRun.get(), 0.0F);
            this.setStackSynchronize(executer, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 1);
        }
        else {
            if(selected == 0){
                executer.playAnimationSynchronized(this.Normal.get(), 0.0F);
                this.setStackSynchronize(executer, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 2);
            }
            else {
                executer.playAnimationSynchronized(this.OnRun.get(), 0.0F);
                this.setStackSynchronize(executer, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 1);
            }
        }
        this.setDurationSynchronize(executer, this.maxDuration);
        skill.activate();
    }

    @Override
    public ArrayList<ResourceLocation> getSkillTextures(PlayerPatch<?> executer) {
        if(executer.getOriginal().isSprinting()){
            return childSkills2;
        }
        return childSkills;
    }

    @Override
    public boolean isSkillActive(PlayerPatch<?> executer, int idx) {
        boolean c = executer.getOriginal().isCreative();
        if(!executer.getOriginal().isSprinting()){
            if(idx == 0){
                return (executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() > 1 || c);
            }
            return (executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() > 0 || c);
        }
        else{
            return (executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() > 0 || c);
        }
    }


    public static class Builder extends SimpleWeaponInnateSkill.Builder {
        protected StaticAnimation attackAnimation;
        protected StaticAnimation attackAnimation2;

        public Builder(ResourceLocation resourceLocation) {
            super();
            this.registryName = resourceLocation;
            //this.maxStack = 3;
        }

        public Builder setCategory(SkillCategory category) {
            this.category = category;
            return this;
        }

        public Builder setMaxDuration(int maxDuration) {
            //this.maxDuration = maxDuration;
            return this;
        }

        public Builder setActivateType(ActivateType activateType) {
            this.activateType = activateType;
            return this;
        }
        public Builder setResource(Resource resource) {
            this.resource = resource;
            return this;
        }

        public Builder setAnimation(AttackAnimationProvider attackAnimation) {
            super.setAnimations(attackAnimation);
            return this;
        }
    }
}
