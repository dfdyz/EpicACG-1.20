package com.dfdyz.epicacg.efmextra.skills.SAO;

import com.dfdyz.epicacg.registry.MySkills;
import com.dfdyz.epicacg.utils.EFUtils;
import com.dfdyz.epicacg.utils.SkillUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.BasicAttackEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;
import java.util.UUID;

import static com.dfdyz.epicacg.registry.MySkillDataKeys.BA_COMBO_COUNTER;
import static com.dfdyz.epicacg.registry.MySkillDataKeys.DASH_LOCKED;

public class SAOBasicAtkPatch  extends BasicAttack {
    private static final UUID EVENT_UUID = UUID.fromString("f34b839d-ab91-ac46-b61d-1c681c904319");

    public SAOBasicAtkPatch(SkillBuilder<? extends BasicAttack> builder) {
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

    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        if (!executer.getEventListener().triggerEvents(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, new BasicAttackEvent(executer))) {
            //System.out.println("BA");
            CapabilityItem cap = executer.getHoldingItemCapability(InteractionHand.MAIN_HAND);
            AnimationManager.AnimationAccessor<? extends StaticAnimation> attackMotion = null;
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

                SkillContainer wp = executer.getSkill(SkillSlots.WEAPON_INNATE);

                if (dashAttack) {
                    if(wp != null && wp.getSkill() == MySkills.SAO_SINGLESWORD_INTERNAL
                            && wp.getDataManager().getDataValue(DASH_LOCKED.get())){
                        comboCounter = comboSize - 2;
                    }
                    else {
                        comboCounter = comboSize - 3;
                    }
                } else {
                    comboCounter %= comboSize - 3;
                }

                attackMotion = combo.get(comboCounter);
                comboCounter = dashAttack ? 0 : comboCounter + 1;
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
