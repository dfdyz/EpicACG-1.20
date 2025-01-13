package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSBasicAtkPatch;
import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSFallAttack;
import com.dfdyz.epicacg.efmextra.skills.MultiSpecialSkill;
import com.dfdyz.epicacg.efmextra.skills.SAO.SAOBasicAtkPatch;
import com.dfdyz.epicacg.efmextra.skills.SAO.SAOSingleSwordInternal;
import com.dfdyz.epicacg.efmextra.skills.SAO.SingleSwordSASkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.skill.SkillDataKey;

public class MySkillDataKeys {
    public static final ResourceLocation EF_DATA_KEYS = new ResourceLocation("epicfight", "skill_data_keys");
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(EF_DATA_KEYS, EpicACG.MODID);
    public static final RegistryObject<SkillDataKey<Integer>> BA_COMBO_COUNTER = DATA_KEYS.register("ba_combo_counter", () ->
            SkillDataKey.createIntKey(
            0, false, GSBasicAtkPatch.class, SAOBasicAtkPatch.class));

    public static final RegistryObject<SkillDataKey<Integer>> GS_FA_FALL_STATE = DATA_KEYS.register("gs_fa_fall_state", () ->
            SkillDataKey.createIntKey(
            0, false, GSFallAttack.class, GSBasicAtkPatch.class));

    public static final RegistryObject<SkillDataKey<Boolean>> DASH_LOCKED = DATA_KEYS.register("dash_locked", () ->
            SkillDataKey.createBooleanKey(false,
            false, SAOBasicAtkPatch.class, SAOSingleSwordInternal.class));

    public static final RegistryObject<SkillDataKey<Integer>> CHILD_SKILL_INDEX = DATA_KEYS.register("child_skill_idx", () ->
            SkillDataKey.createIntKey(
            0, true, MultiSpecialSkill.class));

    public static final RegistryObject<SkillDataKey<Boolean>> JCE_Invincible = DATA_KEYS.register("jce_invincible", () ->
            SkillDataKey.createBooleanKey(
            false, true, SingleSwordSASkills.class));


}
