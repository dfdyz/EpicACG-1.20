package com.dfdyz.epicacg.world.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class StopEffect extends MobEffect {
    public StopEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int lv) {
        return  true;
    }

    @Override
    public void applyEffectTick(LivingEntity owner, int lv) {
        owner.setDeltaMovement(Vec3.ZERO);
        owner.setPos(owner.xOld,owner.yOld,owner.zOld);
        owner.setSpeed(0);
        //EpicAddon.LOGGER.info("fff");
    }
}
