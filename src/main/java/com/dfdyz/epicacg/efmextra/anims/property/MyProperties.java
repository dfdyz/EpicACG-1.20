package com.dfdyz.epicacg.efmextra.anims.property;

import com.mojang.datafixers.util.Pair;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.HashMap;
import java.util.function.Supplier;

public class MyProperties {
    public record SpecialPhase(float start, float end) {
        public boolean isInPhase(float t){
            return t >= start && t<=end;
        }
    }

    public static final AnimationProperty.ActionAnimationProperty<SpecialPhase> MOVE_ROOT_PHASE = new AnimationProperty.ActionAnimationProperty<>();
    public static final AnimationProperty.ActionAnimationProperty<SpecialPhase> INVISIBLE_PHASE = new AnimationProperty.ActionAnimationProperty<>();

    public static final AnimationProperty.AttackAnimationProperty<OnAttackHandlerFunc> AFTER_DAMAGE_ENTITY = new AnimationProperty.AttackAnimationProperty<>();

    public static final AnimationProperty.StaticAnimationProperty<Supplier<StaticAnimation>> ITEM_ANIMATION = new AnimationProperty.StaticAnimationProperty<>();



}
