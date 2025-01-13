package com.dfdyz.epicacg.network.Client;

import com.dfdyz.epicacg.efmextra.skills.EpicACGSkillSlot;
import com.dfdyz.epicacg.efmextra.skills.MultiSpecialSkill;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.function.Supplier;

public class C_RollSkillSelect {
	private int dir;

	public C_RollSkillSelect() {
		this.dir = 0;
	}

	public C_RollSkillSelect(int dir) {
		this.dir = dir;
	}
	
	public static C_RollSkillSelect fromBytes(FriendlyByteBuf buf) {
		return new C_RollSkillSelect(buf.readInt());
	}
	
	public static void toBytes(C_RollSkillSelect msg, FriendlyByteBuf buf) {
		buf.writeInt(msg.dir);
	}
	
	public static void handle(C_RollSkillSelect msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer serverPlayer = ctx.get().getSender();
			ServerPlayerPatch playerpatch = (ServerPlayerPatch) serverPlayer.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
			if (playerpatch != null) {
				Skill _skill = playerpatch.getSkill(EpicACGSkillSlot.SKILL_SELECTOR).getSkill();
				if(_skill != null && _skill instanceof MultiSpecialSkill){
					MultiSpecialSkill skill = (MultiSpecialSkill) _skill;
					skill.RollSelect(playerpatch,msg.getDir());
				}
			}
		});
		ctx.get().setPacketHandled(true);
	}

	public int getDir(){
		return dir;
	}
}