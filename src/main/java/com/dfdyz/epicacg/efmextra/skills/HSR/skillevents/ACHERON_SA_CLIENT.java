package com.dfdyz.epicacg.efmextra.skills.HSR.skillevents;

import com.dfdyz.epicacg.client.screeneffect.HsvFilterEffect;
import com.dfdyz.epicacg.event.CameraEvents;
import com.dfdyz.epicacg.event.ScreenEffectEngine;
import com.dfdyz.epicacg.registry.MyAnimations;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static com.dfdyz.epicacg.event.CameraEvents.UpdateCoord;


public class ACHERON_SA_CLIENT {

    public static void prev(LivingEntityPatch<?> entityPatch){
        if(entityPatch.getOriginal() instanceof Player) {
            CameraEvents.SetAnim(MyAnimations.HSR_ACHERON_SA_CAM1, entityPatch, true);
        }
        Vec3 pos = entityPatch.getOriginal().position();
        // todo
        // add some post effect particle
        HsvFilterEffect effect = new HsvFilterEffect(pos, 135);
        ScreenEffectEngine.PushScreenEffect(effect);
    }

    // f218 t3.6333
    public static void correct_camera(LivingEntityPatch<?> entityPatch){
        if(entityPatch.getOriginal() instanceof Player) {
            UpdateCoord(entityPatch.getOriginal());
        }
    }




}
