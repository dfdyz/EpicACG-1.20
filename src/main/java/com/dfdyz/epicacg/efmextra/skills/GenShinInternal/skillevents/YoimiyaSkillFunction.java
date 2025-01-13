package com.dfdyz.epicacg.efmextra.skills.GenShinInternal.skillevents;


import com.dfdyz.epicacg.config.ClientConfig;
import com.dfdyz.epicacg.efmextra.anims.ScanAttackAnimation;
import com.dfdyz.epicacg.registry.Particles;
import com.dfdyz.epicacg.registry.Sounds;
import com.dfdyz.epicacg.utils.GlobalVal;
import com.dfdyz.epicacg.world.entity.projectile.GenShinArrow;
import com.dfdyz.epicacg.world.entity.projectile.YoimiyaSAArrow;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class YoimiyaSkillFunction {
    public static void BowShoot(LivingEntityPatch<?> entitypatch, Joint joint){
        Level worldIn = entitypatch.getOriginal().level();

        if(entitypatch.isLogicalClient()){
            //Vec3 vec3 = getPosByTick(entitypatch,0.4f,"Tool_L");
            Vec3 handPos = getJointWorldPos(entitypatch,joint);
            worldIn.addParticle(Particles.GENSHIN_BOW.get() ,handPos.x,handPos.y,handPos.z,0,0,0);
            //PostEffectEvent.PushPostEffectHighest(RegPostEffect.WhiteFlush, 0.3f);
        }
        else {
            if(entitypatch.getCurrenltyAttackedEntities().size() > 0){
                Vec3 position = entitypatch.getOriginal().position();
                Entity target = null;

                if(entitypatch.getTarget() != null){
                    target = entitypatch.getTarget();
                }
                else {
                    if(ScanAttackAnimation.getScannedEntities(entitypatch).size() > 0)
                        target = ScanAttackAnimation.getScannedEntities(entitypatch).get(0);
                }

                float dmgboost = (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, entitypatch.getValidItemInHand(InteractionHand.MAIN_HAND)))*0.2f;

                if(target != null && target.isAlive() && !target.equals(entitypatch.getOriginal())){
                    Vec3 shootTarget = target.getBoundingBox().getCenter();
                    //shootTarget = new Vec3(shootTarget.x,target.getEyeY(),shootTarget.z);
                    Vec3 center = position.add(0,entitypatch.getOriginal().getEyeHeight(),0);
                    Vec3 shootVec = shootTarget.subtract(center);
                    Vec3 shootPos = center.add((new Vec3(shootVec.x,0,shootVec.z)).normalize());

                    GenShinArrow projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                    projectile.setPos(shootPos);
                    projectile.pickup = AbstractArrow.Pickup.DISALLOWED;

                    projectile.setDmg((float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2333f*(dmgboost+1));
                    projectile.shoot(shootVec.x(), shootVec.y(), shootVec.z(), 4.2f, 1.0f);
                    worldIn.addFreshEntity(projectile);
                }
                else {
                    float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);
                    Vec3 shootVec = new Vec3(Math.cos(ang), 0 , Math.sin(ang));
                    Vec3 shootPos = position.add(shootVec.x,entitypatch.getOriginal().getEyeHeight(),shootVec.z);

                    GenShinArrow projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                    projectile.setPos(shootPos);
                    projectile.pickup = AbstractArrow.Pickup.DISALLOWED;

                    projectile.setDmg((float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2333f*(dmgboost+1));

                    projectile.shoot(shootVec.x(), 0.1f, shootVec.z(), 4.2f, 1.0f);
                    worldIn.addFreshEntity(projectile);
                }

                entitypatch.playSound(Sounds.GENSHIN_BOW.get(), 0.0F, 0.0F);
            }
        }
    }


    public static void getJointPos(int nameId, int animId ,float time, String joint){

        //System.out.println("fuck")

            //Vec3 position = entitypatch.getOriginal().getPosition(1);
            //System.out.println(handPos.subtract(position) + "  " + position);

                /*
                GenShinArrow projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                projectile.setPos(shootPos);
                projectile.pickup = AbstractArrow.Pickup.DISALLOWED;
                projectile.setDmg((float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2333f);
                projectile.shoot(shootVec.x(), 0.1f, shootVec.z(), 4.2f, 1.0f);
                worldIn.addFreshEntity(projectile);

                /*
                GenShinArrow projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                projectile.setPos(shootPos);
                projectile.pickup = AbstractArrow.Pickup.DISALLOWED;
                projectile.setDmg((float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2333f);
                projectile.shoot(shootVec.x(), shootVec.y(), shootVec.z(), 4.2f, 1.0f);
                worldIn.addFreshEntity(projectile);
                */


            /*
            if(worldIn instanceof ServerLevel){
                //Vec3 vec3 = getPosByTick(entitypatch,0.4f,"Tool_L");
                ((ServerLevel)worldIn).sendParticles(Particles.GENSHIN_BOW.get() ,handPos.x,handPos.y,handPos.z,0,1D,1D,0.9019607D,1D);
            }*/

            //entitypatch.playSound(EpicAddonSounds.GENSHIN_BOW, 0.0F, 0.0F);

    }


    public static final Vec3[] Positions = new Vec3[]{
            new Vec3(-6.5,1.1,-2.6),
            new Vec3(-5.85,0.2,0.39),
            new Vec3(-3.9,0.8,3.9),
            new Vec3(-3.9,1.3,-4.2),

            new Vec3(-5.2,1.2,-2.6),
            new Vec3(-3.9,0.4,2.6),
            new Vec3(-5.85,0.2,0.39),
            new Vec3(-5.2,1.2,-2.6),
    };

    public static final int[] lifetimes = {
            3,3,3,3,1,2,2,1
    };
    public static void YoimiyaSAFirework(LivingEntityPatch<?> entitypatch){
        Entity entity = entitypatch.getOriginal();
        Level worldIn = entity.level();

        if (worldIn instanceof ServerLevel){
            float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);

            Vec3 Center = entity.position();

            for (int i=0; i<Positions.length; ++i) {
                Vec3 spos = Center.add(Positions[i].yRot(-ang));
                ((ServerLevel) worldIn).sendParticles(Particles.GS_YOIMIYA_SA.get(),spos.x,spos.y+0.2f,spos.z,0,lifetimes[i],i,1,1D);
            }
        }

        if(ClientConfig.cfg.EnableGenShinVoice) {
            SoundEvent[] sounds = new SoundEvent[]{
                    Sounds.Yoimiya_Skill1.get(),
                    Sounds.Yoimiya_Skill2.get(),
                    Sounds.Yoimiya_Skill3.get()
            };

            entitypatch.playSound(sounds[Math.abs(GlobalVal.random.nextInt())%3],0.0F, 0.0F);
        }
    }

    public static void YoimiyaSA(LivingEntityPatch<?> entitypatch){
        Level worldIn = entitypatch.getOriginal().level();
        Vec3 handPos = getJointWorldPos(entitypatch, Armatures.BIPED.toolL);

        float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);
        Vec3 shootVec = new Vec3(Math.cos(ang), -1.2, Math.sin(ang));
        Vec3 shootPos = handPos.add(shootVec.x,0,shootVec.z);

        YoimiyaSAArrow projectile = new YoimiyaSAArrow(worldIn, shootPos, entitypatch.getOriginal());
        projectile.shoot(shootVec.x*2, shootVec.y*2, shootVec.z*2, 4.2f, 1.0f);
        float dmgboost = (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, entitypatch.getValidItemInHand(InteractionHand.MAIN_HAND)))*0.2f;
        float dmg = (float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE);
        projectile.setDmg(dmg*(dmgboost+1));
        projectile.setExpRadio(5.5F);
        worldIn.addFreshEntity(projectile);
    }

    public static Vec3 getJointWorldPos(LivingEntityPatch entitypatch, Joint joint){
        Animator animator = entitypatch.getAnimator();
        Armature armature = entitypatch.getArmature();

        Pose pose = animator.getPose(0.5f);

        Vec3 pos = entitypatch.getOriginal().position();//entitypatch.getModelMatrix(1.0F);
        OpenMatrix4f modelTf = OpenMatrix4f.createTranslation((float)pos.x, (float)pos.y, (float)pos.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entitypatch.getModelMatrix(1)));

        OpenMatrix4f JointTf = new OpenMatrix4f(armature.getBindedTransformFor(pose, joint)).mulFront(modelTf);

        return OpenMatrix4f.transform(JointTf,Vec3.ZERO);
    }

    public static void SendParticle(Level level, ParticleOptions particle, Vec3 pos){

        if(level instanceof ServerLevel){
            //Vec3 vec3 = getPosByTick(entitypatch,0.4f,"Tool_L");
            ((ServerLevel)level).sendParticles(particle ,pos.x,pos.y,pos.z,0,1D,1D,0.9019607D,1D);
        }
    }

    public static void fallFinal(LivingEntityPatch<?> ep){
        YoimiyaSkillFunction.SendParticle(
                ep.getOriginal().level(),
                Particles.GENSHIN_BOW_LANDING.get(),
                ep.getOriginal().position()
        );
    }

}
