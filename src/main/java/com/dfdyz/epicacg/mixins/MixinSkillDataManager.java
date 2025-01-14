package com.dfdyz.epicacg.mixins;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataManager;

@Mixin(value = SkillDataManager.class, remap = false)
public abstract class MixinSkillDataManager {

    @Shadow
    public abstract boolean hasData(SkillDataKey<?> key);

    @Inject(method = "registerData", at = @At("HEAD"), cancellable = true)
    public void registerDataPatch(SkillDataKey<?> key, CallbackInfo ci){
        if(hasData(key)) ci.cancel();
    }


}
