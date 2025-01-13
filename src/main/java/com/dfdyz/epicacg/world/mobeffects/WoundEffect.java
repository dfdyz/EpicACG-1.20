package com.dfdyz.epicacg.world.mobeffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.utils.math.MathUtils;

public class WoundEffect extends MobEffect {
    public WoundEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int lv) {
        return duration <= 1;
    }

    @Override
    public void applyEffectTick(LivingEntity owner, int lv) {
        float dmg = Math.max(owner.getMaxHealth() * 0.05f, lv/10f);
        System.out.println(owner.getMaxHealth() * 0.05f + ", " + lv/10f);
        dmg += 0.4f*(owner.getMaxHealth()-owner.getHealth());
        owner.hurt(owner.level().damageSources().generic(), dmg);
    }
}
