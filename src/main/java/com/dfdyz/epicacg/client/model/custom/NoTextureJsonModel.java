package com.dfdyz.epicacg.client.model.custom;

import com.dfdyz.epicacg.registry.MyModels;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class NoTextureJsonModel {
    public static class Triangle {
        public int x,y,z;
        public vec3f Normal;
        public void UpdateNormal(List<vec3f> pos){
            Vector3f p1 = pos.get(x-1).toBugJumpFormat();
            Vector3f p2 = pos.get(y-1).toBugJumpFormat();
            Vector3f p3 = pos.get(z-1).toBugJumpFormat();

            p1.sub(p2);  //v1
            p1.normalize();

            p2.sub(p3);  //v2
            p2.normalize();

            p1.cross(p2); //normal
            p1.normalize();

            Normal = new vec3f(p1.x(), p1.y(), p1.z());
        }
    }

    public static class vec3f {
        public float x,y,z;
        public vec3f(float x, float y, float z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public Vector3f toBugJumpFormat(){
            return new Vector3f(x,y,z);
        }
    }

    public List<vec3f> Positions = new ArrayList<>();
    public List<Triangle> Face = new ArrayList<>();

    static float u0 = 0;
    static float u1 = 1;
    static float v0 = 0;
    static float v1 = 1;

    public void render(VertexConsumer buffer, Quaternionf rot, Vector3f pos, float scale, Color color, int light){
        for (Triangle triangle : Face) {
            Vector3f vertex1 = Positions.get(triangle.x - 1).toBugJumpFormat();
            Vector3f vertex2 = Positions.get(triangle.y - 1).toBugJumpFormat();
            Vector3f vertex3 = Positions.get(triangle.z - 1).toBugJumpFormat();

            if(rot != null){
                vertex1.rotate(rot);
                vertex2.rotate(rot);
                vertex3.rotate(rot);
            }

            vertex1.mul(scale);
            vertex2.mul(scale);
            vertex3.mul(scale);

            vertex1.add(pos);
            vertex2.add(pos);
            vertex3.add(pos);

            // normal to color
            //Vector3f col_normal = triangle.Normal.toBugJumpFormat();
            //if(rot != null)  col_normal.rotate(rot);
            //col_normal.normalize();

            //buffer.vertex(vertex1.x(), vertex1.y(), vertex1.z()).color(offset,camYaw,camPitch,1).uv(u1, v1).uv2(light).endVertex();
            buffer.vertex(vertex1.x(), vertex1.y(), vertex1.z())
                    .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                    .uv(u0, v1)
                    .uv2(light)
                    .endVertex();
            buffer.vertex(vertex2.x(), vertex2.y(), vertex2.z())
                    .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                    .uv(u0, v0)
                    .uv2(light)
                    .endVertex();
            buffer.vertex(vertex3.x(), vertex3.y(), vertex3.z())
                    .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                    .uv(u1, v0)
                    .uv2(light)
                    .endVertex();
        }
    }

    public void renderNeg(VertexConsumer buffer, Quaternionf rot, Vector3f pos, float scale, Color color, int light){
        for (Triangle triangle : Face) {
            Vector3f vertex1 = Positions.get(triangle.x - 1).toBugJumpFormat();
            Vector3f vertex2 = Positions.get(triangle.y - 1).toBugJumpFormat();
            Vector3f vertex3 = Positions.get(triangle.z - 1).toBugJumpFormat();

            if(rot != null){
                vertex1.rotate(rot);
                vertex2.rotate(rot);
                vertex3.rotate(rot);
            }

            vertex1.mul(scale);
            vertex2.mul(scale);
            vertex3.mul(scale);

            vertex1.add(pos);
            vertex2.add(pos);
            vertex3.add(pos);

            // normal to color
            //Vector3f col_normal = triangle.Normal.toBugJumpFormat();
            //if(rot != null) col_normal.rotate(rot);
            //col_normal.normalize();

            //buffer.vertex(vertex1.x(), vertex1.y(), vertex1.z()).color(offset,camYaw,camPitch,1).uv(u1, v1).uv2(light).endVertex();
            buffer.vertex(vertex3.x(), vertex3.y(), vertex3.z())
                    .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                    .uv(u0, v1)
                    .uv2(light)
                    .endVertex();
            buffer.vertex(vertex2.x(), vertex2.y(), vertex2.z())
                    .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                    .uv(u0, v0)
                    .uv2(light)
                    .endVertex();
            buffer.vertex(vertex1.x(), vertex1.y(), vertex1.z())
                    .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                    .uv(u1, v0)
                    .uv2(light)
                    .endVertex();
        }
    }

    public static NoTextureJsonModel loadFromJson(ResourceLocation location){
        NoTextureJsonModel obj;
        try {
            Resource resource = Minecraft.getInstance().getResourceManager().getResource(location).get();
            InputStreamReader isr = new InputStreamReader(resource.open(), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            obj = gson.fromJson(isr,new TypeToken<NoTextureJsonModel>(){}.getType());

            for (int i = 0; i < obj.Face.size(); i++) {
                obj.Face.get(i).UpdateNormal(obj.Positions);
            }
        }catch(IOException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }
}
