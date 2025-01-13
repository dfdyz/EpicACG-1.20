package com.dfdyz.epicacg.efmextra.skills;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

public class SimpleWeaponSASkill extends SimpleWeaponInnateSkill {
    public SimpleWeaponSASkill(Builder builder) {
        super(builder);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        String traslatableText = this.getTranslationKey();
        list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE)
                .append((Component.literal(String.format("[%.0f]", this.consumption))).withStyle(ChatFormatting.AQUA)));
        list.add((Component.translatable(traslatableText + ".tooltip")).withStyle(ChatFormatting.DARK_GRAY));

        return list;
    }
}
