package com.dfdyz.epicacg.utils;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public class MathUtils {


    public static Vec3 ToCylindricalCoordinate(Vec3 in){
        double at = Math.atan2(-in.z ,in.x);
        double r = Math.sqrt(in.x*in.x + in.z*in.z);
        return new Vec3(r,at,in.y);  // r a h
    }

    public static final Double PI2 = Math.PI*2;
    public static Vec3 LerpMinCylindrical(Vec3 v1, Vec3 v2, float t){
        double v = (v2.y - v1.y)%PI2;

        if(v > 0){
            v -= PI2;
            v = Math.abs(v) > v + PI2 ? v+PI2 : v;
        }

        return new Vec3(v1.x*(1-t) + v2.x*t,
                v1.y + v*t,
                v1.z*(1-t) + v2.z*t);
    }

    public static Quaternionf fromEuler(float yaw, float pitch, float roll){
       return new Quaternionf().rotateZ(roll).rotateX(pitch).rotateY(yaw);
    }

    public static final Quaternionf Quat_One = new Quaternionf();


    public static float lerpBetween(float f1, float f2, float zero2one) {
        float f = 0.0F;

        for(f = f2 - f1; f < -180.0F; f += 360.0F) {
        }

        while(f >= 180.0F) {
            f -= 360.0F;
        }

        return f1 + zero2one * f;
    }


    public static Vec3 ToCartesianCoordinates(Vec3 in){
        return new Vec3(
                in.x*Math.cos(in.y),
                in.z,
                in.x*Math.sin(-in.y));
    }

    public static final float RADIANS_TO_DEGREES = (float) (Math.PI / 180);
    public static float toDegrees(float angrad){
        return angrad * RADIANS_TO_DEGREES;
    }

}
