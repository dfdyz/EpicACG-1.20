package com.dfdyz.epicacg.client.particle.ZZZ;


import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class ArcParticle extends TextureSheetParticle {

    protected ArcParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
        super(level, x, y, z, xd, yd, zd);
        this.x = x;
        this.y = y+0.5d;
        this.z = z;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera  , float pt) {
        // W.I.P
    }


    public static class LineRenderer{
        List<Vec3> points = Lists.newArrayList();

        public void SetPoints(List<Vec3> points){
            this.points = points;
        }

        public void AddPoint(Vec3 point){
            this.points.add(point);
        }

        public boolean AddPoint(int idx, Vec3 point){
            if(idx >=0 && idx < this.points.size()){
                this.points.set(idx, point);
                return true;
            }else {
                return false;
            }
        }

        public void Clear(){
            points.clear();
        }

        // col width

        public void Render(VertexConsumer vertexConsumer, Camera camera){
            if(this.points.size() < 2) return;

            Vec3 camPos = camera.getPosition();

            Vec3 strt, end, vec, see, up, last = Vec3.ZERO, tmp;
            Vec3 PosA, PosB;
            int strt_idx = 1;
            for (int i=1; i<points.size(); ++i){
                strt = points.get(i-1);
                end = points.get(i);

                vec = end.subtract(strt);
                see = strt.subtract(camPos);

                double w = 0.2f;

                if(i == strt_idx){
                    if(vec.length() == 0f || see.length() == 0f) {
                        ++strt_idx;
                        continue;
                    }
                    see = see.normalize();
                    up = vec.cross(see);
                    if(up.length() == 0f){
                        ++strt_idx;
                        continue;
                    }

                    last = up = up.normalize();

                    up = up.multiply(w,w,w);

                    PosA = strt.add(up);
                    PosB = strt.subtract(up);
                    // pos tex col light
                    vertexConsumer
                            .vertex(PosA.x, PosA.y, PosA.z)
                            .uv(0, 1)
                            .color(1f,1f,1f,1f)
                            .uv2(RenderUtils.EmissiveLightPos);
                    vertexConsumer
                            .vertex(PosB.x, PosB.y, PosB.z)
                            .uv(0, 0)
                            .color(1f,1f,1f,1f)
                            .uv2(RenderUtils.EmissiveLightPos);
                }
                else {
                    if(vec.length() == 0f || see.length() == 0f) {
                        continue;
                    }

                    up = vec.cross(see);
                    if(up.length() == 0f){
                        ++strt_idx;
                        continue;
                    }

                    up = up.normalize();
                    tmp = up.add(last);

                    w = f(tmp.length());
                    tmp = tmp.normalize().multiply(w,w,w);

                    PosA = strt.add(tmp);
                    PosB = strt.subtract(tmp);

                    vertexConsumer
                            .vertex(PosB.x, PosB.y, PosB.z)
                            .uv(1, 0)
                            .color(1f,1f,1f,1f)
                            .uv2(RenderUtils.EmissiveLightPos);
                    vertexConsumer
                            .vertex(PosA.x, PosA.y, PosA.z)
                            .uv(1, 1)
                            .color(1f,1f,1f,1f)
                            .uv2(RenderUtils.EmissiveLightPos);

                    vertexConsumer
                            .vertex(PosA.x, PosA.y, PosA.z)
                            .uv(0, 1)
                            .color(1f,1f,1f,1f)
                            .uv2(RenderUtils.EmissiveLightPos);
                    vertexConsumer
                            .vertex(PosB.x, PosB.y, PosB.z)
                            .uv(0, 0)
                            .color(1f,1f,1f,1f)
                            .uv2(RenderUtils.EmissiveLightPos);
                }

                if(i == this.points.size() - 1){
                    vertexConsumer
                            .vertex(PosB.x, PosB.y, PosB.z)
                            .uv(1, 0)
                            .color(1f,1f,1f,1f)
                            .uv2(RenderUtils.EmissiveLightPos);
                    vertexConsumer
                            .vertex(PosA.x, PosA.y, PosA.z)
                            .uv(1, 1)
                            .color(1f,1f,1f,1f)
                            .uv2(RenderUtils.EmissiveLightPos);
                }
            }
        }


        private double f(double x){
            return 2 - x;
        }


    }










}
