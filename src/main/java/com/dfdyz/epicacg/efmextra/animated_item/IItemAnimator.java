package com.dfdyz.epicacg.efmextra.animated_item;

import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public interface IItemAnimator {

    Pose getPose(LivingEntityPatch<?> entitypatch, float partialTicks);

}
