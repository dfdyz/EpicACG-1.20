package com.dfdyz.epicacg.efmextra.skills;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;

public class TagSkill extends PassiveSkill {
    //private static float OrgStunShield = 0.0f;
    //public static final UUID EVENT_UUID = UUID.fromString("5d267390-b46f-41d4-940d-a1b2fb2481bd");
    public TagSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public static Skill.Builder<TagSkill> createBuilder(ResourceLocation resourceLocation, SkillCategory category) {
        return (new Skill.Builder<TagSkill>()).setCategory(category).setResource(Resource.NONE).setActivateType(ActivateType.TOGGLE);
    }

    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y) {

    }
}
