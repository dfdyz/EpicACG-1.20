package com.dfdyz.epicacg.efmextra.skills.HSR.skillevents;

import com.dfdyz.epicacg.client.particle.HSR.BlackHoleParticle;
import com.dfdyz.epicacg.client.particle.HSR.GravLensParticle;
import com.dfdyz.epicacg.client.particle.HSR.SubBlackHoleParticle;
import com.dfdyz.epicacg.client.particle.HSR.SubSpaceParticle;
import com.dfdyz.epicacg.client.screeneffect.HsvFilterEffect;
import com.dfdyz.epicacg.event.CameraEvents;
import com.dfdyz.epicacg.event.ScreenEffectEngine;
import com.dfdyz.epicacg.registry.MyAnimations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static com.dfdyz.epicacg.event.CameraEvents.UpdateCoord;


public class ACHERON_SA_CLIENT {

    public static void prev(LivingEntityPatch<?> entityPatch){
        var e = entityPatch.getOriginal();
        if(e instanceof Player) {
            CameraEvents.SetAnim(MyAnimations.HSR_ACHERON_SA_CAM1, entityPatch, true);
        }

        Vec3 pos;

        var target = entityPatch.getTarget();
        if(target != null && target.isAlive()){
            pos = target.position();
        }
        else  pos = entityPatch.getOriginal().position();

        HsvFilterEffect effect = new HsvFilterEffect(pos, 135);
        ScreenEffectEngine.PushScreenEffect(effect);

        BlackHoleParticle bhp = new BlackHoleParticle((ClientLevel) e.level(), pos, 120);
        SubSpaceParticle ssp = new SubSpaceParticle((ClientLevel) e.level(), pos, 120);
        GravLensParticle glp = new GravLensParticle((ClientLevel) e.level(), pos, 120);

        var arm = new Vec3(30, 0, 0).yRot((float) Math.toRadians(-e.getYRot()- 90 - 35));
        SubBlackHoleParticle sbhp = new SubBlackHoleParticle((ClientLevel) e.level(), pos.add(arm), 140);

        Minecraft.getInstance().particleEngine.add(ssp);
        Minecraft.getInstance().particleEngine.add(sbhp);
        Minecraft.getInstance().particleEngine.add(glp);
        Minecraft.getInstance().particleEngine.add(bhp);
    }

    // f218 t3.6333
    public static void correct_camera(LivingEntityPatch<?> entityPatch){
        if(entityPatch.getOriginal() instanceof Player) {
            UpdateCoord(entityPatch.getOriginal());
        }
    }




}
