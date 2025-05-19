package com.dfdyz.epicacg.efmextra.skills.GenShinInternal;


import com.dfdyz.epicacg.registry.MySkills;
import com.dfdyz.epicacg.utils.SkillUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.BasicAttackEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

import static com.dfdyz.epicacg.registry.MySkillDataKeys.BA_COMBO_COUNTER;

public class GSBasicAtkPatch extends Skill {

    private static final UUID EVENT_UUID = UUID.fromString("10f7b428-63f8-4867-2615-9bf8edbecd97");;

    public GSBasicAtkPatch(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }
    public void onInitiate(SkillContainer container) {
        SkillUtils.InitBasicAttack(container);
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID, (event) -> {
            if (!event.getAnimation().get().isBasicAttackAnimation()) {
                container.getDataManager().setData(BA_COMBO_COUNTER.get(), 0);
            }
        });
    }

    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
    }

    public boolean isExecutableState(PlayerPatch<?> executer) {
        EntityState playerState = executer.getEntityState();
        Player player = executer.getOriginal();
        return !player.isSpectator() && playerState.canBasicAttack();
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        var executer = container.getServerExecutor();
        if (!executer.getEventListener().triggerEvents(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, new BasicAttackEvent(executer))) {

            if(MySkills.GS_Bow_FallAttackPatch.isExecutableState(executer)){
                MySkills.GS_Bow_FallAttackPatch.executeOnServer(container, args);
                return;
            }

            CapabilityItem cap = executer.getHoldingItemCapability(InteractionHand.MAIN_HAND);
            AssetAccessor<? extends StaticAnimation> attackMotion = null;
            ServerPlayer player = executer.getOriginal();
            SkillDataManager dataManager = executer.getSkill(SkillSlots.BASIC_ATTACK).getDataManager();
            int comboCounter = dataManager.getDataValue(BA_COMBO_COUNTER.get());
            if (player.isPassenger()) {
                Entity entity = player.getVehicle();
                if (entity instanceof PlayerRideableJumping && ((PlayerRideableJumping)entity).canJump() && cap.availableOnHorse() && cap.getMountAttackMotion() != null) {
                    comboCounter %= cap.getMountAttackMotion().size();
                    attackMotion = cap.getMountAttackMotion().get(comboCounter);
                    ++comboCounter;
                }
            } else {
                var combo = cap.getAutoAttackMotion(executer);
                int comboSize = combo.size();
                boolean dashAttack = player.isSprinting();
                boolean fallAttack = player.getDeltaMovement().y <= -0.3D;

                if(fallAttack){
                    Vec3 epos = executer.getOriginal().position();
                    ClipContext clipContext = new ClipContext(epos, epos.add(0,-3.6,0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, executer.getOriginal());
                    Level level = executer.getOriginal().level();
                    BlockHitResult result = level.clip(clipContext);
                    fallAttack = result.getType() == HitResult.Type.MISS || result.getType() == HitResult.Type.ENTITY;
                    //System.out.println(fallAttack);
                }

                if (dashAttack) {
                    comboCounter = comboSize - 2;
                }
                else if(fallAttack) {
                    comboCounter = comboSize - 1;
                }
                else{
                    comboCounter %= comboSize - 2;
                }

                attackMotion = combo.get(comboCounter);
                comboCounter = (dashAttack || fallAttack) ? 0 : comboCounter + 1;
            }

            dataManager.setData(BA_COMBO_COUNTER.get(), comboCounter);
            if (attackMotion != null) {
                executer.playAnimationSynchronized(attackMotion, 0.0F);
            }

            executer.updateEntityState();
        }
    }


    public void updateContainer(SkillContainer container) {
        if (container.getExecutor().getTickSinceLastAction() > 50 && container.getDataManager().getDataValue(BA_COMBO_COUNTER.get()) > 0) {
            container.getDataManager().setData(BA_COMBO_COUNTER.get(), 0);
        }
    }
}
