package com.dfdyz.epicacg.efmextra.anims.property;

import yesman.epicfight.api.animation.property.AnimationProperty;

public class MyProperties {
    public record SpecialPhase(float start, float end) {
        public boolean isInPhase(float t){
            return t >= start && t<=end;
        }
    }
    public static final AnimationProperty.ActionAnimationProperty<SpecialPhase> MOVE_ROOT_PHASE = new AnimationProperty.ActionAnimationProperty();
    public static final AnimationProperty.ActionAnimationProperty<SpecialPhase> INVISIBLE_PHASE = new AnimationProperty.ActionAnimationProperty();
}
