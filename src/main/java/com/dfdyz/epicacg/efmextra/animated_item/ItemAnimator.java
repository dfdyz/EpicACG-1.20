package com.dfdyz.epicacg.efmextra.animated_item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LayerOffAnimation;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Iterator;
import java.util.Map;

import static com.dfdyz.epicacg.efmextra.anims.property.MyProperties.ITEM_ANIMATION;

public class ItemAnimator implements IItemAnimator {
    protected final Armature armature;
    protected final StaticAnimation default_animation;
    protected StaticAnimation play;

    public ItemAnimator(Armature armature, StaticAnimation defaultAnimation) {
        this.armature = armature;
        default_animation = defaultAnimation;
    }

    protected DynamicAnimation getAnimation(DynamicAnimation animation){
        if(animation.getProperty(ITEM_ANIMATION).isPresent()){
            var a = animation.getProperty(ITEM_ANIMATION).get().get();
            if (a.getArmature() == armature){
                return a;
            }
        }
        return default_animation;
    }

    @Override
    public Pose getPose(LivingEntityPatch<?> entitypatch, float partialTicks) {
        var ap = entitypatch.getClientAnimator().baseLayer.animationPlayer;
        return this.getAnimation(ap.getAnimation())
                .getRawPose(ap.getPrevElapsedTime()
                        + (ap.getElapsedTime()  - ap.getPrevElapsedTime() ) * partialTicks);
    }
}
