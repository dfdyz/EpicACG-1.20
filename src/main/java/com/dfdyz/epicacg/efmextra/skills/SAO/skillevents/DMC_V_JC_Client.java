package com.dfdyz.epicacg.efmextra.skills.SAO.skillevents;

import com.dfdyz.epicacg.client.particle.DMC.AirWaveParticle;
import com.dfdyz.epicacg.client.particle.DMC.PhantomsParticle;
import com.dfdyz.epicacg.client.particle.DMC.SpaceBrokenParticle;
import com.dfdyz.epicacg.client.screeneffect.ColorDispersionEffect;
import com.dfdyz.epicacg.event.CameraEvents;
import com.dfdyz.epicacg.event.ScreenEffectEngine;
import com.dfdyz.epicacg.registry.Particles;
import com.dfdyz.epicacg.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static com.dfdyz.epicacg.registry.MyAnimations.DMC_V_PREV;

public class DMC_V_JC_Client {
    public static void prev(LivingEntityPatch<?> entityPatch){
        //System.out.println("Fired");
        if(entityPatch.getOriginal() instanceof Player)
            CameraEvents.SetAnim(DMC_V_PREV, entityPatch.getOriginal(), true);

        Vec3 pos = entityPatch.getOriginal().position();
        AirWaveParticle particle = new AirWaveParticle(
                Minecraft.getInstance().level, pos.x, pos.y, pos.z, 1, 5
        );

        RenderUtils.AddParticle(Minecraft.getInstance().level, particle);

        ColorDispersionEffect effect = new ColorDispersionEffect(pos);
        effect.lifetime = 12;
        effect.type = ColorDispersionEffect.Type.PREV;
        ScreenEffectEngine.PushScreenEffect(effect);
    }

    public static void HandleAtk1(LivingEntityPatch<?> entityPatch){
        //CameraEvents.SetAnim(SAO_RAPIER_SA2_CAM, (LivingEntity) entityPatch.getOriginal(), true);
        //System.out.println("Fired1");
        Vec3 pos = entityPatch.getOriginal().position();
        PhantomsParticle particle = new PhantomsParticle(
                Minecraft.getInstance().level, pos.x, pos.y, pos.z, entityPatch
        );
        particle.setLifetime(30);
        RenderUtils.AddParticle(Minecraft.getInstance().level, particle);
    }
    //public static OpenMatrix4f matrix4f = new OpenMatrix4f();
    public static void post1(LivingEntityPatch<?> entityPatch){
        Level worldIn = entityPatch.getOriginal().level();
        Vec3 pos = entityPatch.getOriginal().position();
        worldIn.addParticle(Particles.DMC_JC_BLADE_TRAIL.get() ,pos.x,pos.y,pos.z,0,0,0);
        //PostEffectEvent.PushPostEffectMiddle(RegPostEffect.WhiteFlush, 0.25f, pos);
        ColorDispersionEffect effect = new ColorDispersionEffect(pos);
        effect.lifetime = 58;
        ScreenEffectEngine.PushScreenEffect(effect);
    }

    public static void post2(LivingEntityPatch<?> entityPatch){
        Level worldIn = entityPatch.getOriginal().level();
        Vec3 pos = entityPatch.getOriginal().position();
        worldIn.addParticle(Particles.DMC_JC_BLADE_TRAIL.get() ,pos.x,pos.y,pos.z,0,0,0);
    }

    public static void post3(LivingEntityPatch<?> entityPatch){
        Level worldIn = entityPatch.getOriginal().level();
        Vec3 pos = entityPatch.getOriginal().position();
        RenderUtils.AddParticle((ClientLevel) worldIn, new SpaceBrokenParticle((ClientLevel) worldIn, pos.x, pos.y, pos.z, entityPatch.getOriginal().yBodyRot, 45, 0));
        RenderUtils.AddParticle((ClientLevel) worldIn, new SpaceBrokenParticle((ClientLevel) worldIn, pos.x, pos.y, pos.z, entityPatch.getOriginal().yBodyRot,45, 1));
    }

    public static void postAttack(LivingEntityPatch<?> entityPatch){
    }
}
