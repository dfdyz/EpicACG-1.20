package com.dfdyz.epicacg.client.feedback.rumble;

import com.dfdyz.epicacg.client.feedback.FeedBackManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class RumbleUtils {


    @OnlyIn(Dist.CLIENT)
    public static void playForSlamFracture(Level level, Vec3 center, double radius){
        var mc = Minecraft.getInstance();
        if(level.equals(mc.level)){
            try {
                var pos = mc.player.position();
                var dist = pos.subtract(center).length();
                float s;
                if(dist <= radius){
                    s = 1f;
                }
                else {
                    s = (float) Math.max((dist-radius)/8, 0);
                }

                FeedBackManager.rumble(FeedBackManager.RumbleType.GROUND_SHAKE,
                        new SimpleRumbleClip(s, s, (int) (1000 * Math.max(radius / 16, 1)), 100)
                );
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
    }


}
