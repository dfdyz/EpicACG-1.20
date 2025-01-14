package com.dfdyz.epicacg.world.mobeffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

public class NoGravity extends MobEffect {
    public NoGravity(MobEffectCategory mobEffectCategory, int col) {
        super(mobEffectCategory, col);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int lv) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity owner, int lv) {
        if(!owner.isNoGravity()){
            Vec3 v = owner.getDeltaMovement();
            owner.setDeltaMovement(v.x,v.y+LivingEntity.DEFAULT_BASE_GRAVITY, v.z);
        }
    }
}
