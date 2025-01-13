package com.dfdyz.epicacg.utils;

import com.dfdyz.epicacg.EpicACG;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec2f;
import yesman.epicfight.api.utils.math.Vec3f;

public class RenderUtils {
    public static void GLSetTexture(ResourceLocation texture){
        TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
        AbstractTexture abstracttexture = texturemanager.getTexture(texture);
        RenderSystem.bindTexture(abstracttexture.getId());
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        RenderSystem.setShaderTexture(0, abstracttexture.getId());
    }

    public static ResourceLocation GetTexture(String path){
        return new ResourceLocation(EpicACG.MODID, "textures/" + path + ".png");
    }

    public static class Quad{
        private final Vec3f[] vertexs = new Vec3f[4];
        public final Vec2f[] uvs = new Vec2f[4];
        public Quad(float sizeX, float sizeY){
            float x = sizeX / 2;
            float z = -sizeY / 2;


            vertexs[0] = new Vec3f(x, 0, z);
            vertexs[1] = new Vec3f(-x, 0, z);
            vertexs[2] = new Vec3f(-x, 0, -z);
            vertexs[3] = new Vec3f(x, 0, -z);
            uvs[0] = new Vec2f(1, 1);
            uvs[1] = new Vec2f(0, 1);
            uvs[2] = new Vec2f(0, 0);
            uvs[3] = new Vec2f(1, 0);
        }

        public Quad rotate(Vec3f rotAxis, float angle){
            for (int i = 0; i < vertexs.length; i++) {
                vertexs[i].rotate(angle, rotAxis);
            }
            return this;
        }

        public Quad move(float x, float y, float z){
            for (int i = 0; i < vertexs.length; i++) {
                vertexs[i] = vertexs[i].add(x,y,z);
            }
            return this;
        }

        public Vec3f GetVertex(int idx, OpenMatrix4f matrix4f){
            return OpenMatrix4f.transform3v(matrix4f, vertexs[idx], new Vec3f());
        }

        public void PushVertex(VertexConsumer buffer, Vec3f camPos, OpenMatrix4f tf, float rCol, float gCol, float bCol, float alpha, int lightCol) {

            Vec3f pos;
            for (int i = 0; i < vertexs.length; i++) {
                pos = GetVertex(i, tf).sub(camPos);
                //System.out.println(i + ": " + pos);
                buffer.vertex(pos.x, pos.y, pos.z).color(rCol, gCol, bCol, alpha).uv(uvs[i].x, uvs[i].y).uv2(lightCol).endVertex();
            }

            /*
            for (int i = vertexs.length-1; i >=0; --i) {
                pos = GetVertex(i, tf).sub(camPos);
                //System.out.println(i + ": " + pos);
                buffer.vertex(pos.x, pos.y, pos.z).color(rCol, gCol, bCol, alpha).uv(uvs[i].x, uvs[i].y).uv2(lightCol).endVertex();
            }
             */
        }

    }

    public static final int EmissiveLightPos = 15728880;

    @OnlyIn(Dist.CLIENT)
    public static void AddParticle(ClientLevel level, Particle particle){
        try {
            Minecraft mc  = Minecraft.getInstance();
            Camera camera = mc.gameRenderer.getMainCamera();
            if(mc.level != level){
                EpicACG.LOGGER.info("[ParticleEngine]Different Level!");
            }
            if (camera.isInitialized() && mc.particleEngine != null) {
                if (camera.getPosition().distanceToSqr(particle.x, particle.y, particle.z) < 1024.0D) {
                    mc.particleEngine.add(particle);
                }
            }
        }catch (Exception e){

        }
    }

    // pos tex col light
    public static void RenderQuadFaceOnCamera(VertexConsumer vertexConsumer, Camera camera,
                                              float posX, float posY, float posZ,
                                              float r, float g, float b, float a,
                                              float scale, float pt){
        Vector3f[] avector3f = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)};

        Vec3 camPos =  camera.getPosition();
        float x = (float) (posX - camPos.x());
        float y = (float) (posY - camPos.y());
        float z = (float) (posZ - camPos.z());

        Quaternionf camRot = camera.rotation();
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.mul(scale);
            vector3f.add(0,0,-0.2f);
            vector3f.rotate(camRot);
            vector3f.add(x, y, z);
        }
        int j = RenderUtils.EmissiveLightPos;
        vertexConsumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).color(r,g,b,a).uv(0, 0).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).color(r,g,b,a).uv(0, 1).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).color(r,g,b,a).uv(1, 1).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).color(r,g,b,a).uv(1, 0).uv2(j).endVertex();

    }


    // pos tex col light
    public static void RenderQuadFaceOnCamera2(VertexConsumer vertexConsumer, Camera camera,
                                              float posX, float posY, float posZ,
                                              float r, float g, float b, float a,
                                              float scale){
        Vector3f[] avector3f = new Vector3f[]{
                new Vector3f(-1.0F, -1.0F, 0.0F),
                new Vector3f(-1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, 1.0F, 0.0F),
                new Vector3f(1.0F, -1.0F, 0.0F)};

        Vec3 camPos =  camera.getPosition();
        float x = (float) (posX - camPos.x());
        float y = (float) (posY - camPos.y());
        float z = (float) (posZ - camPos.z());

        Quaternionf camRot = camera.rotation();
        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.mul(scale);
            vector3f.add(0,0,-0.2f);
            vector3f.rotate(camRot);
            vector3f.add(x, y, z);
        }
        int j = RenderUtils.EmissiveLightPos;
        vertexConsumer.vertex(avector3f[0].x(), avector3f[0].y(), avector3f[0].z()).uv(0, 0).color(r,g,b,a).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[1].x(), avector3f[1].y(), avector3f[1].z()).uv(0, 1).color(r,g,b,a).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[2].x(), avector3f[2].y(), avector3f[2].z()).uv(1, 1).color(r,g,b,a).uv2(j).endVertex();
        vertexConsumer.vertex(avector3f[3].x(), avector3f[3].y(), avector3f[3].z()).uv(1, 0).color(r,g,b,a).uv2(j).endVertex();

    }


}
