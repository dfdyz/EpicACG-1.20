package com.dfdyz.epicacg.world.entity.projectile;

import com.dfdyz.epicacg.registry.Entities;
import com.dfdyz.epicacg.utils.FireworkUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageSources;

import java.util.List;

public class YoimiyaSAArrow extends Arrow {
    //private float life = 200;
    protected float dmg = 15f;
    //public Vec3 vel = Vec3.ZERO;
    protected float expRadio = 2f;

    protected boolean shouldDestroy = false;

    public YoimiyaSAArrow(EntityType<? extends Arrow> type, Level level) {
        super(type, level);
        this.setRemainingFireTicks(0);
    }

    public YoimiyaSAArrow(Level level,Vec3 pos, Entity owner){
        super(Entities.GS_YoimiyaSA_ARROW.get(), level);
        this.setPos(pos);
        this.setOwner(owner);
        this.setRemainingFireTicks(0);
    }

    public YoimiyaSAArrow(Level level,double x, double y, double z){
        super(Entities.GS_YoimiyaSA_ARROW.get(), level);
        this.setPos(x,y,z);
        this.setRemainingFireTicks(0);
    }

    public YoimiyaSAArrow(Level level,double x, double y, double z,float dmg){
        super(Entities.GS_YoimiyaSA_ARROW.get(), level);
        this.setPos(x,y,z);
        this.dmg = dmg;
        this.setRemainingFireTicks(0);
    }

    public void setExpRadio(float r){
        expRadio = r;
    }

    @Override
    public void tick() {
        if (shouldDestroy) this.discard();

        super.tick();
        //this.moveTo(this.position().add(vel));
    }

    @Override
    public void shoot(double p_37266_, double p_37267_, double p_37268_, float p_37269_, float p_37270_) {
        super.shoot(p_37266_, p_37267_, p_37268_, p_37269_, p_37270_);
        this.clearFire();
        //this.vel = (new Vec3(p_37266_, p_37267_, p_37268_)).scale(p_37269_/20f);
    }

    public void setDmg(float dmg){
        this.dmg = dmg;
    }

    @Override
    public void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult.getType() != HitResult.Type.MISS){
            //System.out.println("sa arrow hit");
            //float radio = 2;
            Vec3 ExpBox = new Vec3(expRadio,expRadio,expRadio);
            Vec3 hitPos = hitResult.getLocation();
            //this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, hitPos.x, hitPos.y, hitPos.z, 0.0, 0.0D, 0.0D);

            if (this.level().isClientSide) {
                level().createFireworks(hitPos.x, hitPos.y, hitPos.z, 1,1,1, FireworkUtils.Explode);
                this.level().playLocalSound(hitPos.x, hitPos.y, hitPos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level().random.nextFloat() - this.level().random.nextFloat()) * 0.2F) * 0.7F, false);
            }

            List<Entity> list = this.level().getEntities(this, new AABB(hitPos.subtract(ExpBox),hitPos.add(ExpBox)));
            Entity owner = this.getOwner();

            for (Entity e : list) {
                if(!e.equals(owner) && !e.ignoreExplosion()){
                    if(e.distanceTo(this) <= expRadio){
                        e.hurt(this.GetDamageSource(), this.dmg);
                    }
                }
            }

            shouldDestroy = true;
        }
    }

    protected DamageSource GetDamageSource(){
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

        return damagesource;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Dmg", dmg);
        compoundTag.putFloat("ExpRadio", expRadio);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.dmg = compoundTag.getFloat("Dmg");
        this.expRadio = compoundTag.getFloat("ExpRadio");
    }
}
