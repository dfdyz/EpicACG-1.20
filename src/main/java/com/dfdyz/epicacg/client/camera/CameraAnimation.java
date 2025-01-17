package com.dfdyz.epicacg.client.camera;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.utils.JsonUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CameraAnimation {

    public final FloatSheet x;
    public final FloatSheet y;
    public final FloatSheet z;
    public final FloatSheet rx;
    public final FloatSheet ry;
    public final FloatSheet rz;
    public final FloatSheet fov;
    public final float totalTime;
    public CameraAnimation(FloatSheet x, FloatSheet y, FloatSheet z, FloatSheet rx, FloatSheet ry, FloatSheet rz,FloatSheet fov) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.fov = fov;

        float tt = Math.max(x.getMaxTime(), y.getMaxTime());
        tt = Math.max(z.getMaxTime(), tt);
        tt = Math.max(rx.getMaxTime(), tt);
        tt = Math.max(ry.getMaxTime(), tt);
        tt = Math.max(fov.getMaxTime(), tt);
        totalTime = tt;
    }

    public Pose getPose(float time){
        return new Pose(x.getValueByTime(time),
                y.getValueByTime(time),
                z.getValueByTime(time),
                rx.getValueByTime(time),
                ry.getValueByTime(time),
                rz.getValueByTime(time),
                fov.getValueByTime(time)
        );
    }

    public static CameraAnimation load(ResourceLocation resourceLocation){
        Minecraft mc = Minecraft.getInstance();

        try {
            InputStream is = mc.getResourceManager().getResource(resourceLocation).get().open();
            OutputStream os = new ByteArrayOutputStream();;
            byte[] bytes=new byte[1024];
            int len;
            while ((len=is.read(bytes))!=-1){
                os.write(bytes,0,len);
            }
            is.close();
            String json = os.toString();
            JsonObject animJson = JsonParser.parseString(json).getAsJsonObject();

            FloatSheet x,y,z,rx,ry,rz,fov;
            float timeScale = 1.f;

            if(animJson.has("time_scale"))
                timeScale = animJson.get("time_scale").getAsFloat();

            // pos - time sheet
            JsonObject sheets = animJson.getAsJsonObject("pos");

            if(sheets.isJsonArray()){
                /*
                "pos": {
                    "x": {
                        "time": [],
                        "value": []
                    },
                    "y": {
                        "time": [],
                        "value": []
                    },
                    "z": {
                        "time": [],
                        "value": []
                    }
                }
                 */
                x = new FloatSheet();
                x.getFromJson(sheets.getAsJsonObject("x"), "value");
                y = new FloatSheet();
                y.getFromJson(sheets.getAsJsonObject("y"), "value");
                z = new FloatSheet();
                z.getFromJson(sheets.getAsJsonObject("z"), "value");
            }
            else {
                /*
                "pos": {
                    "time": [],
                    "x": [],
                    "y": [],
                    "z": []
                }
                 */
                x = new FloatSheet();
                y = new FloatSheet();
                z = new FloatSheet();
                x.getFromJson(sheets, "x");
                y.getFromJson(sheets, "y");
                z.getFromJson(sheets, "z");
            }

            // rot - time sheet
            sheets = animJson.getAsJsonObject("rot");
            rx = new FloatSheet();
            ry = new FloatSheet();
            rz = new FloatSheet();
            rx.getFromJson(sheets, "rx");
            ry.getFromJson(sheets, "ry");
            rz.getFromJson(sheets, "rz");

            //fov
            sheets = animJson.getAsJsonObject("fov");
            fov = new FloatSheet();
            fov.getFromJson(sheets, "value");

            x.scaleTimes(timeScale);
            y.scaleTimes(timeScale);
            z.scaleTimes(timeScale);
            rx.scaleTimes(timeScale);
            ry.scaleTimes(timeScale);
            rz.scaleTimes(timeScale);
            fov.scaleTimes(timeScale);

            EpicACG.LOGGER.info("Load Camera Animation: " + resourceLocation);
            return new CameraAnimation(x,y,z,rx,ry,rz,fov);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Pose{
        public final Vec3 pos;
        public final float rotY;
        public final float rotX;
        public final float rotZ;
        public final float fov;
        public Pose(Vec3 pos, float rotX, float rotY, float rotZ, float fov){
            this.pos = pos;
            this.rotY = rotY;
            this.rotX = rotX;
            this.rotZ = rotZ;
            this.fov = fov;
        }

        public Pose(float x, float y, float z, float rotX, float rotY,float rotZ,float fov){
            this(new Vec3(x,y,z), rotX, rotY, rotZ, fov);
        }

        @Override
        public String toString() {
            return "Pose{" +
                    "pos=" + pos +
                    ", rotY=" + rotY +
                    ", rotX=" + rotX +
                    ", rotZ=" + rotZ +
                    ", fov=" + fov +
                    '}';
        }
    }

    public static abstract class TimeSheet{
        public float[] timeSheet;
        public int getIndexByTime(float time){
            if (time <= 0) return 0;
            for (int i = 0; i < timeSheet.length; i++) {
                if(timeSheet[i] >= time) return Math.max(i-1, 0);
            }
            return timeSheet.length-1;
        }
        public void getFromJson(JsonObject json){
            timeSheet = JsonUtils.getAsFloatArray(json.getAsJsonArray("time"));
        }

        public float getMaxTime(){
            return timeSheet[timeSheet.length-1];
        }

        public void scaleTimes(float scale){
            for (int i = 0; i < timeSheet.length; i++) {
                timeSheet[i] /= scale;
            }
        }
    }


    public static class FloatSheet extends TimeSheet{
        public float[] floatSheet;
        public float getValueByTime(float time){
            int idx = getIndexByTime(time);

            if(idx == timeSheet.length-1){
                return floatSheet[idx];
            }
            else {
                float t = (timeSheet[idx+1] - timeSheet[idx]);
                if(t > 0.00001f){
                    t = (time - timeSheet[idx]) / t;
                    return floatSheet[idx] * (1.f - t) + floatSheet[idx+1] * t;
                }else {
                    return floatSheet[idx];
                }
            }
        }

        public void getFromJson(JsonObject json, String valueKey) {
            getFromJson(json);
            floatSheet = JsonUtils.getAsFloatArray(json.getAsJsonArray(valueKey));
        }
    }


}
