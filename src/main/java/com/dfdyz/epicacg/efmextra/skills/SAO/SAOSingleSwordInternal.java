package com.dfdyz.epicacg.efmextra.skills.SAO;

import com.dfdyz.epicacg.registry.MySkills;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class SAOSingleSwordInternal extends Skill {
    private static final UUID EVENT_UUID = UUID.fromString("ce7c276b-ab8b-0955-713f-93347cdce622");

    public SAOSingleSwordInternal(SkillBuilder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        container.getExecutor().getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(MySkills.SAO_BASICATK_PATCH);
        container.getExecutor().getEventListener().addEventListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID,
                (event) ->
                {
                });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecutor().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
        container.getExecutor().getSkillCapability().skillContainers[SkillSlots.BASIC_ATTACK.universalOrdinal()].setSkill(EpicFightSkills.BASIC_ATTACK);
    }
}
