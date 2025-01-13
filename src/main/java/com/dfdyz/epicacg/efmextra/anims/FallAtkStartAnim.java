package com.dfdyz.epicacg.efmextra.anims;

import com.dfdyz.epicacg.efmextra.skills.GenShinInternal.GSFallAttack;
import com.dfdyz.epicacg.registry.MySkillDataKeys;
import com.dfdyz.epicacg.registry.MySkills;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class FallAtkStartAnim extends ActionAnimation {
    public StaticAnimation Loop;
    public FallAtkStartAnim(float convertTime, String path, Armature model, StaticAnimation Loop){
        super(convertTime, path, model);
        this.Loop = Loop;
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
        if(FMLEnvironment.dist == Dist.CLIENT){
            this.addProperty(ClientAnimationProperties.PRIORITY, Layer.Priority.HIGHEST);
            this.addProperty(ClientAnimationProperties.LAYER_TYPE, Layer.LayerType.BASE_LAYER);
        }
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        super.begin(entitypatch);
        entitypatch.getOriginal().setDeltaMovement(0,0,0);
        entitypatch.getOriginal().setNoGravity(true);
        if (!entitypatch.isLogicalClient()){
            if(entitypatch instanceof ServerPlayerPatch){
                ServerPlayerPatch spp = (ServerPlayerPatch) entitypatch;
                SkillContainer container = spp.getSkill(SkillSlots.AIR_ATTACK);
                if(container.getSkill() == MySkills.GS_Bow_FallAttackPatch){
                    if(!container.getDataManager().hasData(MySkillDataKeys.GS_FA_FALL_STATE.get())){
                        container.getDataManager().registerData(MySkillDataKeys.GS_FA_FALL_STATE.get());
                    }
                    container.getDataManager().setData(MySkillDataKeys.GS_FA_FALL_STATE.get(), 0);
                    container.update();
                }
            }
        }
    }


    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        entitypatch.getOriginal().setDeltaMovement(0,0,0);
        entitypatch.getOriginal().setNoGravity(true);
    }

    @Override
    public void modifyPose(DynamicAnimation animation, Pose pose, LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
        JointTransform jt = pose.getOrDefaultTransform("Root");
        Vec3f jointPosition = jt.translation();
        OpenMatrix4f toRootTransformApplied = entitypatch.getArmature().searchJointByName("Root").getLocalTrasnform().removeTranslation();
        OpenMatrix4f toOrigin = OpenMatrix4f.invert(toRootTransformApplied, null);
        Vec3f worldPosition = OpenMatrix4f.transform3v(toRootTransformApplied, jointPosition, null);
        worldPosition.x = 0.0F;
        worldPosition.y = 0.0F;
        worldPosition.z = 0.0F;
        OpenMatrix4f.transform3v(toOrigin, worldPosition, worldPosition);
        jointPosition.x = worldPosition.x;
        jointPosition.y = worldPosition.y;
        jointPosition.z = worldPosition.z;
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, DynamicAnimation nextAnimation, boolean isEnd) {
        super.end(entitypatch, nextAnimation, isEnd);
        entitypatch.getOriginal().setNoGravity(false);
        if (!entitypatch.isLogicalClient()){
            if(entitypatch instanceof ServerPlayerPatch){
                ServerPlayerPatch spp = (ServerPlayerPatch) entitypatch;
                SkillContainer container = spp.getSkill(SkillSlots.AIR_ATTACK);
                if(container.getSkill() == MySkills.GS_Bow_FallAttackPatch){
                    if(!container.getDataManager().hasData(MySkillDataKeys.GS_FA_FALL_STATE.get())){
                        container.getDataManager().registerData(MySkillDataKeys.GS_FA_FALL_STATE.get());
                        container.getDataManager().setData(MySkillDataKeys.GS_FA_FALL_STATE.get(), 0);
                    }
                    int state = container.getDataManager().getDataValue(MySkillDataKeys.GS_FA_FALL_STATE.get());
                    if (state == 0){
                        container.getDataManager().setData(MySkillDataKeys.GS_FA_FALL_STATE.get(), 1);
                        entitypatch.getOriginal().setDeltaMovement(0,0,0);
                        entitypatch.getOriginal().setNoGravity(true);
                        spp.playAnimationSynchronized(Loop,0f);
                        //spp.getEventListener().triggerEvents(ATTACK_ANIMATION_END_EVENT, new FallAttackEvent(spp, entitypatch.currentlyAttackedEntity, this.getNamespaceId(),this.getId()));
                    }
                    container.update();
                }
            }
        }
    }
    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }

    @Override
    public void linkTick(LivingEntityPatch<?> entitypatch, DynamicAnimation linkAnimation) {
        super.linkTick(entitypatch, linkAnimation);
    }
}
