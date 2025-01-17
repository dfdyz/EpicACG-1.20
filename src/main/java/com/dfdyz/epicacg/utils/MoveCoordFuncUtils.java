package com.dfdyz.epicacg.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class MoveCoordFuncUtils {
    public static MoveCoordFunctions.MoveCoordSetter TraceLockedTargetEx(float totalDistance){
        return (self, entitypatch, transformSheet) -> {
            LivingEntity attackTarget = entitypatch.getTarget();
            if (attackTarget != null && !(Boolean)self.getRealAnimation().getProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false)) {
                TransformSheet transform = self.getCoord().copyAll();
                Keyframe[] keyframes = transform.getKeyframes();
                int startFrame = 0;
                int endFrame = keyframes.length - 1;
                Vec3f keyLast = keyframes[endFrame].transform().translation();
                Vec3 pos = entitypatch.getOriginal().position();
                Vec3 targetpos = attackTarget.position();
                Vec3 toTarget = targetpos.subtract(pos);
                Vec3 viewVec = entitypatch.getOriginal().getViewVector(1.0F);

                float horizontalDistance = Math.max((float)toTarget.horizontalDistance() - (attackTarget.getBbWidth() + entitypatch.getOriginal().getBbWidth()) * 0.75F, 0.0F);
                Vec3f worldPosition = new Vec3f(keyLast.x, 0.0F, -horizontalDistance);
                float scale = Math.min(worldPosition.length() / totalDistance, 2);
                if (scale > 1.0F) {
                    float dot = (float)toTarget.normalize().dot(viewVec.normalize());
                    scale = Math.max(scale * dot, 1.0F);
                }

                for(int i = startFrame; i <= endFrame; ++i) {
                    Vec3f translation = keyframes[i].transform().translation();
                    if (translation.z < 0.0F) {
                        translation.z *= scale;
                    }
                }

                transformSheet.readFrom(transform);
            } else {
                transformSheet.readFrom(self.getCoord().copyAll());
            }
        };
    }
}
