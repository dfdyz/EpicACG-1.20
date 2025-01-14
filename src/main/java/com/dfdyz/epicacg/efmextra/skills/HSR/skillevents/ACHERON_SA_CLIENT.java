package com.dfdyz.epicacg.efmextra.skills.HSR.skillevents;

import com.dfdyz.epicacg.client.particle.DMC.AirWaveParticle;
import com.dfdyz.epicacg.client.screeneffect.ColorDispersionEffect;
import com.dfdyz.epicacg.client.screeneffect.HsvFilterEffect;
import com.dfdyz.epicacg.client.shaderpasses.HsvFilter;
import com.dfdyz.epicacg.event.CameraEvents;
import com.dfdyz.epicacg.event.ScreenEffectEngine;
import com.dfdyz.epicacg.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import static com.dfdyz.epicacg.registry.MyAnimations.DMC_V_PREV;

public class ACHERON_SA_CLIENT {

    public static void prev(LivingEntityPatch<?> entityPatch){
        Vec3 pos = entityPatch.getOriginal().position();
        // todo
        // add some post effect particle
        HsvFilterEffect effect = new HsvFilterEffect(pos, 135);
        ScreenEffectEngine.PushScreenEffect(effect);
    }




}
