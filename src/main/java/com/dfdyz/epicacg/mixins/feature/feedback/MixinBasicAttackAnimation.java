package com.dfdyz.epicacg.mixins.feature.feedback;

import com.dfdyz.epicacg.network.Netmgr;
import com.dfdyz.epicacg.network.Server.S_HitRumble;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import static com.dfdyz.epicacg.client.feedback.utils.RumbleUtils.PlayForHit;

@Mixin(value = AttackAnimation.class, remap = false)
public abstract class MixinBasicAttackAnimation {

    @Inject(method = "hurtCollidingEntities",
            at = @At(value = "INVOKE",
                    target = "Lyesman/epicfight/api/animation/types/AttackAnimation;getMaxStrikes(Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lyesman/epicfight/api/animation/types/AttackAnimation$Phase;)I"
            )
    )
    public void hurtCollidingEntitiesPatch(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime,
                                           EntityState prevState, EntityState state, AttackAnimation.Phase phase, CallbackInfo ci){
        if(entitypatch instanceof ServerPlayerPatch spp){
            Netmgr.sendToPlayer(new S_HitRumble(), spp.getOriginal());
        }
    }

}
