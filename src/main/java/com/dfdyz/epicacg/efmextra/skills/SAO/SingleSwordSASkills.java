package com.dfdyz.epicacg.efmextra.skills.SAO;

import com.dfdyz.epicacg.efmextra.skills.EpicACGSkillSlot;
import com.dfdyz.epicacg.efmextra.skills.IMutiSpecialSkill;
import com.dfdyz.epicacg.efmextra.skills.MultiSpecialSkill;
import com.dfdyz.epicacg.efmextra.skills.SimpleWeaponSASkill;
import com.dfdyz.epicacg.registry.MyAnimations;
import com.dfdyz.epicacg.registry.MySkills;
import com.dfdyz.epicacg.utils.EFUtils;
import com.dfdyz.epicacg.utils.OjangUtils;
import com.dfdyz.epicacg.utils.SkillUtils;
import com.google.common.collect.Lists;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.ArrayList;
import java.util.UUID;

import static com.dfdyz.epicacg.registry.MySkillDataKeys.CHILD_SKILL_INDEX;
import static com.dfdyz.epicacg.registry.MySkillDataKeys.JCE_Invincible;

public class SingleSwordSASkills  extends SimpleWeaponSASkill implements IMutiSpecialSkill {
    private final ArrayList<ResourceLocation> noPower = new ArrayList<>();
    private final ArrayList<ResourceLocation> morePower = new ArrayList<>();
    private final UUID EventUUID = UUID.fromString("eb69decf-48a1-5333-dacc-884fd345c02a");

    private final AnimationManager.AnimationAccessor<? extends AttackAnimation> noPowerAnimation1;
    private final AnimationManager.AnimationAccessor<? extends AttackAnimation> morePowerAnimation1;

    public SingleSwordSASkills(Builder builder) {
        super(builder);

        noPowerAnimation1 = MyAnimations.HSR_ACHERON_SA;
        //noPowerAnimation1 = () -> (AttackAnimation) Animations.SWEEPING_EDGE;
        morePowerAnimation1 = MyAnimations.DMC5_V_JC;

        ResourceLocation name = this.getRegistryName();
        noPower.add(OjangUtils.newRL(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png"));
        noPower.add(OjangUtils.newRL(name.getNamespace(), "textures/gui/skills/single/judgement_cut.png"));
        //noPower.add(OjangUtils.newRL(name.getNamespace(), "textures/gui/skills/single/judgement_cut.png"));
    }

    public static SimpleWeaponInnateSkill.Builder createBuilder(ResourceLocation resourceLocation) {
        return (SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder())
                .setCategory(SkillCategories.WEAPON_INNATE)
                .setRegistryName(resourceLocation);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getSkillCapability()
                .skillContainers[EpicACGSkillSlot.SKILL_SELECTOR.universalOrdinal()]
                .setSkill(MySkills.MUTI_SPECIAL_ATTACK);

        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EventUUID, (e)->{
            PlayerPatch playerPatch = e.getPlayerPatch();
            SkillContainer sc = playerPatch.getSkill(SkillSlots.WEAPON_INNATE);
            if(sc != null){
                boolean inv = sc.getDataManager().getDataValue(JCE_Invincible.get());
                if(inv){
                    e.setResult(AttackResult.ResultType.BLOCKED);
                    e.setCanceled(true);
                }
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getDataManager().setData(JCE_Invincible.get(), false);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EventUUID);
    }

    @Override
    public boolean checkExecuteCondition(SkillContainer container) {
        var executer = container.getExecutor();
        boolean ok;
        SkillContainer skillContainer = executer.getSkill(SkillSlots.WEAPON_INNATE);
        int selected = executer.getSkill(EpicACGSkillSlot.SKILL_SELECTOR).getDataManager().getDataValue(CHILD_SKILL_INDEX.get());
        ok = skillContainer.getStack() >= (selected == 0 ? 1:5);
        return ok || (executer.getOriginal()).isCreative();
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        //SkillContainer skill = executer.getSkill(SkillSlots.WEAPON_INNATE);
        var executer = container.getExecutor();
        int selected = MultiSpecialSkill.getSelected(executer);

        if(selected == 0){
            executer.playAnimationSynchronized(this.noPowerAnimation1, 0.0F);
            this.setStackSynchronize(container, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 1);
        }
        else {
            executer.playAnimationSynchronized(this.morePowerAnimation1, 0.0F);
            this.setStackSynchronize(container, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 5);
        }
    }

    @Override
    public ArrayList<ResourceLocation> getSkillTextures(PlayerPatch<?> executer) {
        return noPower;
    }

    @Override
    public boolean isSkillActive(PlayerPatch<?> executer, int idx) {
        if(executer.getOriginal().isCreative()) return true;

        if(idx == 0){
            return true;
        }
        else if(idx == 1){
            if (executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() >= 5){
                return true;
            }
        }
        return false;
    }
}
