package com.dfdyz.epicacg.efmextra.skills;

import com.dfdyz.epicacg.EpicACG;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum EpicACGSkillSlot implements SkillSlot {
    SKILL_SELECTOR(EpicACGSkillCategories.MutiSpecialAttack),
    SAO_SINGLE_SWORD(EpicACGSkillCategories.SAO_SINGLE_SWORD);
    SkillCategory category;
    int id;

    private EpicACGSkillSlot(SkillCategory category) {
        this.category = category;
        this.id = SkillSlot.ENUM_MANAGER.assign(this);
    }

    public SkillCategory category() {
        return this.category;
    }

    public int universalOrdinal() {
        return this.id;
    }
}