package com.dfdyz.epicacg.config;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.registry.Particles;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.client.model.ItemSkin;
import yesman.epicfight.particle.EpicFightParticles;

import java.util.Objects;

public class Trail {
    public float x,y,z,ex,ey,ez;
    public int r,g,b,a, lifetime;
    public int interpolations = 8;

    public String textureRegisterId = "";
    public String particleId = "";
    public Trail(float x, float y, float z, float ex, float ey, float ez,int r,int g,int b,int a){
        this.x = x;
        this.y = y;
        this.z = z;
        this.ex = ex;
        this.ey = ey;
        this.ez = ez;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.lifetime = 3;
    }
    public Trail(float x, float y, float z, float ex, float ey, float ez,int r,int g,int b,int a, String texture){
        this.x = x;
        this.y = y;
        this.z = z;
        this.ex = ex;
        this.ey = ey;
        this.ez = ez;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.lifetime = 3;
        textureRegisterId = texture;
    }

    public Trail(float x, float y, float z, float ex, float ey, float ez,int r,int g,int b,int a,int lifetime){
        this.x = x;
        this.y = y;
        this.z = z;
        this.ex = ex;
        this.ey = ey;
        this.ez = ez;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.lifetime = lifetime;
    }

    public Trail CopyColFrom(Trail col){
        this.r = col.r;
        this.g = col.g;
        this.b = col.b;
        this.a = col.a;
        return this;
    }

    public Trail CopyPosFrom(Trail pos){
        this.x = pos.x;
        this.y = pos.y;
        this.z = pos.z;
        this.ex = pos.ex;
        this.ey = pos.ey;
        this.ez = pos.ez;
        return this;
    }

    public Trail CopyFrom(Trail org){
        this.x = org.x;
        this.y = org.y;
        this.z = org.z;
        this.ex = org.ex;
        this.ey = org.ey;
        this.ez = org.ez;
        this.r = org.r;
        this.g = org.g;
        this.b = org.b;
        this.a = org.a;
        this.lifetime = org.lifetime;
        this.interpolations = org.interpolations;
        this.textureRegisterId = org.textureRegisterId;
        return this;
    }

    public Trail(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.ex = 0;
        this.ey = 0;
        this.ez = 0;
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.a = 0;
        this.lifetime = 0;
    }

    public void Clear(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.ex = 0;
        this.ey = 0;
        this.ez = 0;
        this.r = 0;
        this.g = 0;
        this.b = 0;
        this.a = 0;
        this.lifetime = 0;
        this.interpolations = 8;
        this.textureRegisterId = "";
    }

    public ItemSkin toItemSkin(){
        TrailInfo.Builder trailInfo = TrailInfo.builder();
        trailInfo.startPos(new Vec3(x, y, z));
        trailInfo.endPos(new Vec3(ex, ey, ez));
        trailInfo.r(r / 255f);
        trailInfo.g(g / 255f);
        trailInfo.b(b / 255f);
        trailInfo.lifetime(lifetime);
        trailInfo.interpolations(interpolations);

        if(textureRegisterId.equals("")) trailInfo.texture("epicfight:textures/particle/swing_trail.png");
        else trailInfo.texture(textureRegisterId);

        System.out.println(textureRegisterId);

        //(EpicFightParticles.SWING_TRAIL.get());
        SimpleParticleType particleType = Particles.BLOOM_TRAIL.get();
        if(particleId != "" && ForgeRegistries.PARTICLE_TYPES.containsKey(new ResourceLocation(particleId))){
            particleType = (SimpleParticleType) ForgeRegistries.PARTICLE_TYPES.getValue(new ResourceLocation(particleId));
        }

        trailInfo.type(particleType);

        ItemSkin itemSkin = new ItemSkin(trailInfo.create(), false);
        return itemSkin;
    }
}