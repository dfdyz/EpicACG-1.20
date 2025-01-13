package com.dfdyz.epicacg.mixins;


import com.dfdyz.epicacg.client.render.pipeline.PostEffectPipelines;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(value = LevelRenderer.class)
public abstract class MixinLevelRenderer {

    @Inject(method = "renderLevel",
            at = @At(
                    //value = "FIELD",
                    //target = "Lnet/minecraft/client/renderer/LevelRenderer;transparencyChain:Lnet/minecraft/client/renderer/PostChain;",
                    //ordinal = 0

                    value = "CONSTANT",
                    args = { "stringValue=particles" }

            ))
    private void markRendered(CallbackInfo cbi){
        PostEffectPipelines.active();
    }


}
