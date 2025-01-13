package com.dfdyz.epicacg.utils.Function;


import com.dfdyz.epicacg.efmextra.anims.ScanAttackAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@FunctionalInterface
public interface ScanAttackConsumer {
    void consume(ScanAttackAnimation animation, LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, AttackAnimation.Phase phase);

    default ScanAttackConsumer andThen(ScanAttackConsumer after) {
        Objects.requireNonNull(after);
        return (animation, entitypatch, prevET, eT, prevState, state, phase) -> {
            consume(animation, entitypatch, prevET, eT, prevState, state, phase);
            after.consume(animation, entitypatch, prevET, eT, prevState, state, phase);
        };
    }
}
