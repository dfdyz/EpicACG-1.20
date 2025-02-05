package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.client.shaderpasses.*;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@OnlyIn(Dist.CLIENT)
public class PostPasses {

    public static PostPassBase blit;
    public static Blur blur;
    public static PostPassBase composite;
    public static SpaceBroken space_broken;
    public static DepthCull depth_cull;
    public static DownSampling downSampler;
    public static UpSampling upSampler;
    public static UnityComposite unity_composite;
    public static UEComposite ue_composite;
    public static ColorDispersion color_dispersion;
    public static HsvFilter hsv_filter;
    public static BlackHole black_hole;
    public static MaskComposite mask_composite;


    public static void register(RegisterShadersEvent event){
        try {
            System.out.println("Load Shader");
            ResourceManager rm = Minecraft.getInstance().getResourceManager();
            composite = new PostPassBase("epicacg:composite", rm);
            blit = new PostPassBase("epicacg:blit",rm);
            downSampler = new DownSampling("epicacg:down_sampling",rm);
            upSampler = new UpSampling("epicacg:up_sampling",rm);
            unity_composite = new UnityComposite("epicacg:unity_composite",rm);
            ue_composite = new UEComposite("epicacg:ue_composite",rm);

            space_broken = new SpaceBroken("epicacg:space_broken",rm);
            depth_cull = new DepthCull("epicacg:depth_cull", rm);
            color_dispersion = new ColorDispersion("epicacg:color_dispersion", rm);
            black_hole = new BlackHole("epicacg:black_hole",rm);

            hsv_filter = new HsvFilter("epicacg:hsv_filter", rm);
            mask_composite = new MaskComposite("epicacg:mask_composite", rm);

            blur = new Blur(rm);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
