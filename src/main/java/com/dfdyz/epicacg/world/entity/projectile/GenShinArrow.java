package com.dfdyz.epicacg.world.entity.projectile;

import com.dfdyz.epicacg.registry.Entities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class GenShinArrow extends Arrow {
    private float dmg = 1;
    //private final int iii;
    public GenShinArrow(Level level) {
        super(Entities.GENSHIN_ARROW.get(), level);
        //iii = id;
    }

    public GenShinArrow(Level level,Entity owner) {
        super(Entities.GENSHIN_ARROW.get(), level);
        setOwner(owner);
        //iii = id;
    }

    public GenShinArrow(Level level, double x, double y, double z) {
        super(level,x,y,z);
        //iii = id;
    }

    public GenShinArrow(EntityType<GenShinArrow> type, Level level) {
        super(Entities.GENSHIN_ARROW.get(), level);
        //iii = 0;
    }

    public void setDmg(float dmg){
        this.dmg = dmg;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        //super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        float f = (float)this.getDeltaMovement().length();
        int i = Mth.ceil(Mth.clamp((double)f * this.getBaseDamage(), 0.0D, 2.147483647E9D));

        if (this.isCritArrow()) {
            long j = (long)this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(j + (long)i, 2147483647L);
        }
        Entity entity1 = this.getOwner();

        DamageSources damageSources = level().damageSources();
        Entity owner = this.getOwner();
        DamageSource damagesource;

        if(owner instanceof Player player){
            damagesource = damageSources.playerAttack(player);
        }
        else if (owner instanceof Mob mob){
            damagesource = damageSources.mobAttack(mob);
        }
        else {
            damagesource = damageSources.arrow(this, owner);
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.setSecondsOnFire(5);
        }

        entity.invulnerableTime = 0;
        if (entity.hurt(damagesource, dmg)) {
            entity.setInvulnerable(false);
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                if (!this.level().isClientSide && this.getPierceLevel() <= 0) {
                    livingentity.setArrowCount(livingentity.getArrowCount() + 1);
                }

                if (this.getKnockback() > 0) {
                    Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double) this.getKnockback() * 0.6D);
                    if (vec3.lengthSqr() > 0.0D) {
                        livingentity.push(vec3.x, 0.1D, vec3.z);
                    }
                }

                if(entity1 == null) return;

                if (!this.level().isClientSide && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
                }

                this.doPostHurtEffects(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer)entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

                EntityPatch entityPatch = entity1.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).orElse(null);
                if(entityPatch instanceof ServerPlayerPatch){
                    ServerPlayerPatch playerPatch = (ServerPlayerPatch) entityPatch;
                    SkillContainer skill = playerPatch.getSkill(SkillSlots.WEAPON_INNATE);
                    //System.out.println("233333");
                    if(skill != null){
                        //System.out.println(skill.getResource());
                        skill.getSkill().setConsumptionSynchronize(skill, skill.getResource()+3);
                        //skill.update();
                    }
                }
            }
        }
        this.playSound(SoundEvents.ARROW_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.discard();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Dmg", dmg);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.dmg = compoundTag.getFloat("Dmg");
    }
}
