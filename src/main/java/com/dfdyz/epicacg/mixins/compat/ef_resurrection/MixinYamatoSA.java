package com.dfdyz.epicacg.mixins.compat.ef_resurrection;


//@Mixin(value = YamatoSkill.class, remap = false)
public abstract class MixinYamatoSA {
    /*
    private static UUID EVENT_HOLDER = ReflectionUtils.GetField(YamatoSkill.class, "EVENT_UUID");


    @Inject(method = "lambda$onInitiate$5", remap = false,
            at = @At("HEAD"), cancellable = true)
    public void epicacg$HurtEventPatch(SkillContainer container, HurtEvent.Pre event, CallbackInfo ci){
        if(container != null){
            boolean inv = container.getDataManager().getDataValue(JCE_Invincible.get());
            if(inv){
                event.setResult(AttackResult.ResultType.BLOCKED);
                event.setCanceled(true);
                ci.cancel();
            }
        }
    }

    @Inject(method = "onInitiate", remap = false,
            at = @At("HEAD"))
    public void epicacg$InitPatch(SkillContainer container, CallbackInfo ci){
        container.getDataManager().registerData(JCE_Invincible.get());
        container.getDataManager().setData(JCE_Invincible.get(), false);
    }


    @Inject(method = "executeOnServer", remap = false,
        at = @At("HEAD"),
            cancellable = true)
    public void epicacg$SkillPatch(ServerPlayerPatch executer, FriendlyByteBuf args, CallbackInfo ci){
        if(epicACG_1_20$JCE(executer, (Skill)((Object)this))) ci.cancel();
    }

    @Unique
    private static boolean epicACG_1_20$JCE(ServerPlayerPatch executer, Skill thiz){
        var container = executer.getSkill(thiz);
        float maxSta =  executer.getMaxStamina();
        float stamina = executer.getStamina();
        if(maxSta <= 0){
            stamina = 0;
            maxSta = 1;
        }
        //executer.playAnimationSynchronized(MyAnimations.DMC5_V_JC, 0.05F);
        //executer.setStamina(stamina - 0.5f * maxSta);
        //Skill.setStackSynchronize(executer, thiz, 0);
        //executer.resetActionTick();

        if(container.getStack() >= thiz.getMaxStack()-1 && stamina / maxSta > 0.5f){
            executer.playAnimationSynchronized(MyAnimations.DMC5_V_JC, 0.05F);
            executer.setStamina(stamina - 0.5f * maxSta);
            Skill.setSkillStackSynchronize(executer, thiz, 0);
            executer.resetActionTick();
            return true;
        }
        return false;
    }*/
}
