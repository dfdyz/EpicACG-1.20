package com.dfdyz.epicacg.config;


import com.dfdyz.epicacg.utils.DeathParticleHandler;
import com.google.common.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jline.utils.InputStreamReader;
import org.slf4j.Logger;

import java.io.*;


public class ClientConfig {
    public static ClientConfigValue cfg = new ClientConfigValue();

    private static Logger LOGGER = LogUtils.getLogger();

    static {

    }

    public static String ReadString(String FileName) {
        String str = "";

        File file = new File(FileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return "";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                str = readFromFile(FileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return str;
    }

    public static void Load(boolean isReload){
        String cfgpath,json;

        //load trail
        LOGGER.info("EpicAddon:Loading Sword Trail Item");

/*
        cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonTrailItem.json").toString();
        json = ReadString(cfgpath);

        //LOGGER.info(json);

        if(json != ""){
            RenderConfig.TrailItem.clear();
            RenderConfig.TrailItem = CommonConfig.GSON.fromJson(json, new TypeToken<Map<String,Trail>>(){}.getType());
        }
        else{
            WriteString(cfgpath, CommonConfig.GSON.toJson(RenderConfig.TrailItem));
        }
        if(isReload && ClientConfig.cfg.EnableAutoMerge) Config2SkinReloader.Merge();


        LOGGER.info("EpicAddon:Loading Health Bar Modifier");
        cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonHealthBar.json").toString();
        json = ReadString(cfgpath);
        //LOGGER.info(json);
        if(json != ""){
            RenderConfig.HealthBarEntity.clear();
            RenderConfig.HealthBarEntity = CommonConfig.GSON.fromJson(json, new TypeToken<Map<String, HealthBarStyle>>(){}.getType());
        }
        else{
            WriteString(cfgpath, CommonConfig.GSON.toJson(RenderConfig.HealthBarEntity));
        }
        */

        LOGGER.info("EpicAddon:Loading Death Particle Modifier");
        cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonDeathParticle.json").toString();
        json = ReadString(cfgpath);
        //LOGGER.info(json);
        if(json != ""){
            DeathParticleHandler.config.clear();
            DeathParticleHandler.config = CommonConfig.GSON.fromJson(json, new TypeToken<DeathParticleHandler.DeathParticleConfig>(){}.getType());
        }
        else{
            WriteString(cfgpath, CommonConfig.GSON.toJson(DeathParticleHandler.config));
        }

/*
        LOGGER.info("EpicAddon:Loading Common Config");
        cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonCommon.json").toString();
        json = ReadString(cfgpath);
        //LOGGER.info(json);
        if(json != ""){
            try {
                cfg = CommonConfig.GSON.fromJson(json, new TypeToken<ClientConfigValue>(){}.getType());
            } catch (JsonSyntaxException e) {
                WriteString(cfgpath, CommonConfig.GSON.toJson(cfg));
                throw new RuntimeException(e);
            }
        }
        else{
            WriteString(cfgpath, CommonConfig.GSON.toJson(cfg));
        }
*/
        //GlobalVal.ANG = cfg.InitialAngle;

        //RenderConfig.TrailItem = GSON.fromJson(json, new TypeToken<Map<String,Trail>>(){}.getType());
        //LOGGER.info("JSON JSON\n"+GSON.toJson(RenderConfig.TrailItem)+"\nJSON JSON");
    }

    public static void SaveClientCfg(){
        String cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonCommon.json").toString();
        LOGGER.info("EpicAddon:Save Common Config");
        WriteString(cfgpath, CommonConfig.GSON.toJson(cfg));
    }

    public static void WriteString(String FileName,String str){
        try(FileOutputStream fos = new FileOutputStream(FileName);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);){
            bw.write(str);
            bw.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFromFile(String s) throws IOException {
        InputStream inputStream = new FileInputStream(new File(s));
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
