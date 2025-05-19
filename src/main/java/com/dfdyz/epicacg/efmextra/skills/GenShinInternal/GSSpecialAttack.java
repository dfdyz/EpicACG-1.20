package com.dfdyz.epicacg.efmextra.skills.GenShinInternal;


import com.dfdyz.epicacg.efmextra.skills.SimpleWeaponSASkill;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class GSSpecialAttack extends SimpleWeaponSASkill {
    public GSSpecialAttack(Builder builder) {
        super(builder);
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }


    @Override
    public boolean checkExecuteCondition(SkillContainer container) {
        var executor = container.getExecutor();
        return ((executor.getOriginal()).isCreative())
                && Math.abs(executor.getOriginal().getDeltaMovement().y) <= 0.3f
                && closedGround(executor);
    }

    private boolean closedGround(PlayerPatch<?> executer){
        Vec3 epos = executer.getOriginal().position();
        ClipContext clipContext = new ClipContext(epos, epos.add(0,-2,0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, executer.getOriginal());
        Level level = executer.getOriginal().level();
        BlockHitResult result = level.clip(clipContext);
        return result.getType() == HitResult.Type.BLOCK;
    }
}
