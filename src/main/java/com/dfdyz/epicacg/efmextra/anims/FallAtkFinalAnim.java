package com.dfdyz.epicacg.efmextra.anims;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public class FallAtkFinalAnim extends BasicAttackAnimation {
    public FallAtkFinalAnim(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, Joint index, String path, Armature model) {
        super(convertTime, antic, contact, recovery, collider, index, path, model);
    }

    public FallAtkFinalAnim(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint index, String path, Armature model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, model);
    }

    public FallAtkFinalAnim(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint index, String path, Armature model) {
        super(convertTime, antic, contact, recovery, hand, collider, index, path, model);

    }

    @Override
    public void linkTick(LivingEntityPatch<?> entitypatch, DynamicAnimation linkAnimation) {
        super.linkTick(entitypatch, linkAnimation);
    }

    /*
    Old

    @Override
    public void linkTick(LivingEntityPatch<?> entitypatch, LinkAnimation linkAnimation) {
        super.linkTick(entitypatch, linkAnimation);
        
    }
 */

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        
    }
}
