package com.dfdyz.epicacg.utils;

import com.google.common.collect.Maps;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Map;

public class DeathParticleHandler {
    public static DeathParticleConfig config = new DeathParticleConfig();

    public static class DeathParticleConfig{
        public Map<String, ParticleTransform> custom = Maps.newHashMap();
        public float default_density = 5;
        public void clear(){
            custom.clear();
        }
    }



    public static final Map<Integer, ParticleTransformed> TransformPool = Maps.newHashMap();
    public static class ParticleTransformed {
        public final Vec3 minV;
        public final Vec3 maxV;
        public final Vec3 rot;
        public final Vec3 offset;
        public final float density;
        private boolean handled = false;
        public ParticleTransformed(Vec3 offset, Vec3 minV, Vec3 maxV, Vec3 rotation, float density){
            this.minV = minV;
            this.maxV = maxV;
            this.offset = offset;
            rot = rotation;
            this.density = density;
        }
        public ParticleTransformed(float x, float y, float z, float mx, float my, float mz, float Mx, float My, float Mz, float rx, float ry , float rz, float density){
            this(new Vec3(x,y,z),new Vec3(mx,my,mz),new Vec3(Mx,My,Mz),new Vec3(rx,ry,rz), density);
        }
        public void setHandled(){handled = true;}
        public boolean isHandled() {return handled;}
    }


    public static class ParticleTransform{
        public final Vec3 minV;
        public final Vec3 maxV;
        public final Vec3 offset;
        public final Vec3 rot;
        public final int type;
        public final float density;

        //public ParticleTransform[] boxes;

        /*
        type
            0: just hit box
            1: hit box & body rot
            2: custom box & body rot
            3: custom box & custom rot
            4: custom box & custom rot & body rot
            5: muti box
         */
        public ParticleTransform(Vec3 center, Vec3 minV, Vec3 maxV, Vec3 rotation, float density ,int type){
            this.minV = minV;
            this.maxV = maxV;
            this.offset = center;
            rot = rotation;
            this.type = type;
            this.density = density;
            //boxes = new ParticleTransform[0];
        }

        /*
        public ParticleTransform(Vec3 center, Vec3 minV, Vec3 maxV, Vec3 rotation, float density ,int type, ParticleTransform... boxes){
            this.minV = minV;
            this.maxV = maxV;
            this.offset = center;
            rot = rotation;
            this.type = type;
            this.density = density;
            this.boxes = boxes;
        }*/

        public ParticleTransform(int type){
            this(Vec3.ZERO,Vec3.ZERO,Vec3.ZERO,Vec3.ZERO, 5, type);
        }

        public ParticleTransform(float x, float y, float z, float mx, float my, float mz, float Mx, float My, float Mz, float rx, float ry , float rz, int type){
            this(new Vec3(x,y,z),new Vec3(mx,my,mz),new Vec3(Mx,My,Mz),new Vec3(rx,ry,rz), 5, type);
        }
    }
}
