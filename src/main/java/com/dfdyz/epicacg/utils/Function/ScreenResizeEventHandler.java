package com.dfdyz.epicacg.utils.Function;


import com.dfdyz.epicacg.efmextra.anims.ScanAttackAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@FunctionalInterface
public interface ScreenResizeEventHandler {
    void consume(float w, float h);

    default ScreenResizeEventHandler andThen(ScreenResizeEventHandler after) {
        Objects.requireNonNull(after);
        return (w, h) -> {
            consume(w, h);
            after.consume(w, h);
        };
    }
}
