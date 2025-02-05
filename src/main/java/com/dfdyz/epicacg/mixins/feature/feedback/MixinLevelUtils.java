package com.dfdyz.epicacg.mixins.feature.feedback;

import com.dfdyz.epicacg.client.feedback.rumble.RumbleUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.utils.LevelUtil;

@Mixin(value = LevelUtil.class, remap = false)
public class MixinLevelUtils {

    @Inject(method = "circleSlamFracture(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;DZZZ)Z",
        at = {@At("RETURN")}
    )
    private static void epicacg$circleSlamFracture(LivingEntity caster, Level level, Vec3 center, double radius, boolean noSound, boolean noParticle, boolean hurtEntities, CallbackInfoReturnable<Boolean> cir){
        if(cir.getReturnValue()){
            RumbleUtils.playForSlamFracture(level, center, radius);
        }
    }

}
