package com.dfdyz.epicacg.efmextra.skills;

import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.ArrayList;

public interface IMutiSpecialSkill {
    ArrayList<ResourceLocation> getSkillTextures(PlayerPatch<?> executer);
    boolean isSkillActive(PlayerPatch<?> executer, int idx);
}
