package com.dfdyz.epicacg.efmextra.anims.property;

import net.minecraft.world.entity.LivingEntity;
import org.openjdk.nashorn.internal.objects.annotations.Function;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@FunctionalInterface
public interface OnAttackHandlerFunc {
    @Function
    void invoke(AttackAnimation animation, AttackAnimation.Phase phase, LivingEntityPatch<?> attacker, LivingEntity target, float damage);
}
