package com.dfdyz.epicacg.efmextra.entitypatch;

import com.dfdyz.epicacg.world.entity.mob.TrashBinMasterEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

public class TrashBinMasterPatch extends LivingEntityPatch<TrashBinMasterEntity> {
    @Override
    public void initAnimator(Animator animator) {

    }

    @Override
    public void updateMotion(boolean b) {

    }

    @Override
    public StaticAnimation getHitAnimation(StunType stunType) {
        return null;
    }
}
