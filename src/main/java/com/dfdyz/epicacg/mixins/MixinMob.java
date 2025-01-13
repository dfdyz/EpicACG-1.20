package com.dfdyz.epicacg.mixins;


import com.dfdyz.epicacg.registry.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Mob.class)
public abstract class MixinMob {
    @Inject(at = @At(value = "HEAD"), method = "serverAiStep()V", cancellable = true)
    private void serverAiStep(CallbackInfo info) {
        Mob self = (Mob)((Object)this);
        MobEffectInstance e = self.getEffect(MobEffects.STOP.get());
        if(e != null){
            info.cancel();
        }
    }
}
