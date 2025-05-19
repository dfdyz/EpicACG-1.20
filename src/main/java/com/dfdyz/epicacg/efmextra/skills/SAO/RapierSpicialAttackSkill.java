package com.dfdyz.epicacg.efmextra.skills.SAO;

import com.dfdyz.epicacg.efmextra.skills.EpicACGSkillSlot;
import com.dfdyz.epicacg.efmextra.skills.IMutiSpecialSkill;
import com.dfdyz.epicacg.efmextra.skills.SimpleWeaponSASkill;
import com.dfdyz.epicacg.registry.MyAnimations;
import com.dfdyz.epicacg.registry.MySkills;
import com.dfdyz.epicacg.utils.OjangUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.ArrayList;

import static com.dfdyz.epicacg.registry.MySkillDataKeys.CHILD_SKILL_INDEX;

public class RapierSpicialAttackSkill extends SimpleWeaponSASkill implements IMutiSpecialSkill {
    private final ArrayList<ResourceLocation> childSkills = new ArrayList<>();
    private final ArrayList<ResourceLocation> childSkills2 = new ArrayList<>();
    private final AnimationManager.AnimationAccessor<? extends StaticAnimation> Normal;
    private final AnimationManager.AnimationAccessor<? extends StaticAnimation> OnRun;

    public RapierSpicialAttackSkill(_Builder builder) {
        super(builder);
        this.Normal = MyAnimations.SAO_RAPIER_SA2;
        this.OnRun = MyAnimations.SAO_RAPIER_SPECIAL_DASH;
        ResourceLocation name = this.getRegistryName();
        //ResourceLocation tex = OjangUtils.newRL(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png");
        childSkills.add(OjangUtils.newRL(name.getNamespace(), "textures/gui/skills/sao_rapier_skill.png"));
        childSkills.add(OjangUtils.newRL(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png"));
        childSkills2.add(OjangUtils.newRL(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png"));
    }

    public static _Builder create_Builder(ResourceLocation resourceLocation) {
        return (new _Builder(resourceLocation)).setCategory(SkillSlots.WEAPON_INNATE.category()).setResource(Resource.STAMINA);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getSkillCapability()
                .skillContainers[EpicACGSkillSlot.SKILL_SELECTOR.universalOrdinal()]
                .setSkill(MySkills.MUTI_SPECIAL_ATTACK);
    }


    @Override
    public boolean checkExecuteCondition(SkillContainer container) {
        var executer = container.getExecutor();
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
    public boolean canExecute(SkillContainer container) {
        return super.canExecute(container);
    }


    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        var executer = container.getServerExecutor();
        SkillContainer skill = executer.getSkill(SkillSlots.WEAPON_INNATE);
        int selected = executer.getSkill(EpicACGSkillSlot.SKILL_SELECTOR).getDataManager().getDataValue(CHILD_SKILL_INDEX.get());

        if (executer.getOriginal().isSprinting()){
            executer.playAnimationSynchronized(this.OnRun, 0.0F);
            this.setStackSynchronize(container, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 1);
        }
        else {
            if(selected == 0){
                executer.playAnimationSynchronized(this.Normal, 0.0F);
                this.setStackSynchronize(container, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 2);
            }
            else {
                executer.playAnimationSynchronized(this.OnRun, 0.0F);
                this.setStackSynchronize(container, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 1);
            }
        }
        this.setDurationSynchronize(container, this.maxDuration);
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


    public static class _Builder extends SimpleWeaponInnateSkill.Builder {
        public _Builder(ResourceLocation resourceLocation) {
            super();
            this.registryName = resourceLocation;
            //this.maxStack = 3;
        }
    }
}
