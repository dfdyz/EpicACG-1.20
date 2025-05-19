package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.efmextra.skills.EpicACGSkillCategories;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSBasicAtkPatch;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSBowInternal;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSFallAttack;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSSpecialAttack;
import com.dfdyz.epicacg.efmextra.skills.MultiSpecialSkill;
import com.dfdyz.epicacg.efmextra.skills.SAO.*;
import com.dfdyz.epicacg.efmextra.skills.SimpleWeaponSASkill;
import com.dfdyz.epicacg.efmextra.skills.TagSkill;
import com.dfdyz.epicacg.utils.OjangUtils;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;

import static yesman.epicfight.skill.Skill.Resource.WEAPON_CHARGE;

public class MySkills {
    public static Skill MUTI_SPECIAL_ATTACK;


    public static Skill GS_Bow_FallAttackPatch;
    public static Skill GS_Bow_BasicAttackPatch;
    public static Skill GS_Bow_Internal;


    public static Skill SAO_SINGLESWORD_INTERNAL;
    public static Skill SAO_BASICATK_PATCH;
    public static Skill SAO_SINGLESWORD_SA;

    public static Skill BATTLE_SCYTHE_SA;

    public static Skill SAO_DUALSWORD;
    public static Skill SAO_SINGLESWORD;
    public static Skill SAO_RAPIER_A;
    public static Skill WEAPON_SKILL_RAPIER;

    public static Skill SAO_DUAL_SWORD_SA;
    public static Skill GS_YOIMIYA_SPECIALATK;

    public static void BuildSkills(SkillBuildEvent event){
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("Build EpicACG Skill");

        SkillBuildEvent.ModRegistryWorker modRegistry = event.createRegistryWorker(EpicACG.MODID);

        MUTI_SPECIAL_ATTACK = modRegistry.build("muti_sa", MultiSpecialSkill::new,
                PassiveSkill.createPassiveBuilder().setRegistryName(OjangUtils.newRL(EpicACG.MODID,"muti_sa"))
                        .setCategory(EpicACGSkillCategories.MutiSpecialAttack)
        );

        GS_YOIMIYA_SPECIALATK = modRegistry.build("gs_yoimiya_sa",GSSpecialAttack::new,
                SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder()
                        .setAnimations(MyAnimations.GS_Yoimiya_SA)
                        .setResource(WEAPON_CHARGE)
                        .setRegistryName(OjangUtils.newRL(EpicACG.MODID, "gs_yoimiya_sa"))).newProperty();
        GS_Bow_FallAttackPatch = modRegistry.build("gs_air_attack_patch", GSFallAttack::new,
                GSFallAttack.createBuilder()
                        .setCategory(SkillCategories.AIR_ATTACK)
                        .setRegistryName(OjangUtils.newRL(EpicACG.MODID,"gs_air_attack_patch"))
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setResource(Skill.Resource.STAMINA));

        GS_Bow_BasicAttackPatch = modRegistry.build("gs_basic_attack_patch", GSBasicAtkPatch::new,
                GSBasicAtkPatch.createBuilder()
                        .setCategory(SkillCategories.BASIC_ATTACK)
                        .setRegistryName(OjangUtils.newRL(EpicACG.MODID,"gs_basic_attack_patch"))
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setResource(Skill.Resource.NONE));


        GS_Bow_Internal = modRegistry.build("gs_bow_internal", GSBowInternal::new,
                GSBowInternal.GetBuilder("gs_bow_internal")
                        .setCategory(SkillCategories.WEAPON_PASSIVE)
                        //.setActivateType(Skill.)
                        .setResource(Skill.Resource.NONE));

        SAO_BASICATK_PATCH = modRegistry.build("sao_basic_attack_patch", SAOBasicAtkPatch::new,
                BasicAttack.createBasicAttackBuilder()
                        .setRegistryName(OjangUtils.newRL(EpicACG.MODID,"sao_basic_attack_patch")));

        SAO_SINGLESWORD_INTERNAL = modRegistry.build( "sao_single_sword_internal", SAOSingleSwordInternal::new,
                SAOSingleSwordInternal.createBuilder()
                        .setCategory(SkillCategories.WEAPON_PASSIVE)
                        .setRegistryName(OjangUtils.newRL(EpicACG.MODID, "sao_single_sword_internal"))
                        //.setActivateType(Skill.ActivateType.PASSIVE)
                        .setResource(Skill.Resource.NONE));
        SAO_SINGLESWORD_SA = modRegistry.build( "single_sword_sa", SingleSwordSASkills::new,
                SingleSwordSASkills.createBuilder(OjangUtils.newRL(EpicACG.MODID, "single_sword_sa"))
                        .setAnimations(Animations.SWORD_AUTO1)
                        .setCategory(SkillCategories.WEAPON_INNATE)
        ).newProperty();

        BATTLE_SCYTHE_SA = modRegistry.build( "battle_scythe_sa", SimpleWeaponSASkill::new,
                (SimpleWeaponInnateSkill.Builder) SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder()
                        .setAnimations(MyAnimations.BATTLE_SCYTHE_SA1)
                        .setResource(WEAPON_CHARGE)
                        .setRegistryName(OjangUtils.newRL(EpicACG.MODID, "battle_scythe_sa"))
        ).newProperty();

        SAO_SINGLESWORD = modRegistry.build( "sao_single_sword", TagSkill::new,
                TagSkill.createBuilder(
                        OjangUtils.newRL(EpicACG.MODID,"sao_single_sword"),
                        EpicACGSkillCategories.SAO_SINGLE_SWORD
                ));
        SAO_DUALSWORD = modRegistry.build( "sao_dual_sword_skill", DualBladeSkill::new,
                DualBladeSkill.createBuilder(OjangUtils.newRL(EpicACG.MODID,"sao_dual_sword_skill"))
                        .setCreativeTab(Tab.TAB_ITEMS.get()));
        SAO_RAPIER_A = modRegistry.build("sao_rapier_skill", RapierSkill::new,
                RapierSkill.createBuilder(OjangUtils.newRL(EpicACG.MODID,"sao_rapier_skill"))
                        .setCreativeTab(Tab.TAB_ITEMS.get()));
        WEAPON_SKILL_RAPIER = modRegistry.build( "weapon_skill_rapier", RapierSpicialAttackSkill::new,
                RapierSpicialAttackSkill.create_Builder(OjangUtils.newRL(EpicACG.MODID, "weapon_skill_rapier"))
                        .setAnimations(MyAnimations.SAO_RAPIER_SPECIAL_DASH)
                        .setResource(WEAPON_CHARGE)
                        .setCategory(SkillCategories.WEAPON_INNATE)
        );
        SAO_DUAL_SWORD_SA = modRegistry.build( "sao_dual_sword_sa", SimpleWeaponSASkill::new,
                SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder()
                        .setAnimations(MyAnimations.SAO_DUAL_SWORD_SA1)
                        .setResource(WEAPON_CHARGE)
                        .setRegistryName(OjangUtils.newRL(EpicACG.MODID, "sao_dual_sword_sa"))).newProperty();
    }

}
