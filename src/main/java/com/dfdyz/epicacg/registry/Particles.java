package com.dfdyz.epicacg.registry;

import com.dfdyz.epicacg.EpicACG;
import com.dfdyz.epicacg.client.particle.BloomTrailParticle;
import com.dfdyz.epicacg.client.particle.DMC.JudgementCutParticle;
import com.dfdyz.epicacg.client.particle.EntityAfterImgParticleEx;
import com.dfdyz.epicacg.client.particle.GenshinImpact.GenShinBowLandingParticle;
import com.dfdyz.epicacg.client.particle.GenshinImpact.GenShinBowLandingParticle2;
import com.dfdyz.epicacg.client.particle.GenshinImpact.GenShinBowLandingParticle3;
import com.dfdyz.epicacg.client.particle.GenshinImpact.GenShinBowShootParticle;
import com.dfdyz.epicacg.client.particle.MyTextureSheetParticle;
import com.dfdyz.epicacg.client.particle.SAO.*;
import com.dfdyz.epicacg.client.particle.GenshinImpact.YoimiyaSA.GsYoimiyaFirework;
import com.dfdyz.epicacg.utils.MyHitParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.particle.HitParticleType;

public class Particles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EpicACG.MODID);
    public static final RegistryObject<SimpleParticleType> SAO_DEATH = PARTICLES.register("sao_death", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SAO_DEATH_I = PARTICLES.register("sao_death_i", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GENSHIN_BOW = PARTICLES.register("genshin_bow", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GENSHIN_BOW_LANDING = PARTICLES.register("genshin_bow_landing", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GENSHIN_BOW_LANDING2 = PARTICLES.register("genshin_bow_landing2", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GENSHIN_BOW_LANDING3 = PARTICLES.register("genshin_bow_landing3", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GS_YOIMIYA_SA = PARTICLES.register("gs_yoimiya_sa", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> BLOOM_TRAIL = PARTICLES.register("bloom_trail", () -> new SimpleParticleType(true));
    public static final RegistryObject<HitParticleType> BLOOD_THIRSTY = PARTICLES.register("bloodthirsty_hit",() -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));
    public static final RegistryObject<HitParticleType> BLACK_KNIGHT = PARTICLES.register("blackknight_hit",() -> new HitParticleType(true, HitParticleType.CENTER_OF_TARGET, HitParticleType.ZERO));

    public static final RegistryObject<SimpleParticleType> SPARKS_SPLASH = PARTICLES.register("sparks_splash",() -> new SimpleParticleType(true));
    public static final RegistryObject<HitParticleType> SPARKS_SPLASH_HIT = PARTICLES.register("sparks_splash_hit",() -> new HitParticleType(true, HitParticleType.RANDOM_WITHIN_BOUNDING_BOX, MyHitParticleType.Atker2Tar));

    public static final RegistryObject<SimpleParticleType> ENTITY_AFTER_IMG_EX = PARTICLES.register("after_image_ex",() -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ALO_WING = PARTICLES.register("alo_wing", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> STAR_FLASH = PARTICLES.register("star_flash", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> PHOTON_TRAIL = PARTICLES.register("photon_trail", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> DMC_JC_BLADE_TRAIL = PARTICLES.register("dmc_jc_blade_trail", () -> new SimpleParticleType(true));

    @OnlyIn(Dist.CLIENT)
    public static void registryParticles(RegisterParticleProvidersEvent event){
        ParticleEngine PE = Minecraft.getInstance().particleEngine;

        PE.register(SAO_DEATH.get(), SAODeathParticle.Provider::new);
        PE.register(SAO_DEATH_I.get(), SAODeathParticleInternal.Provider::new);

        PE.register(GENSHIN_BOW.get(), GenShinBowShootParticle.Provider::new);
        PE.register(GENSHIN_BOW_LANDING.get(), GenShinBowLandingParticle.Provider::new);
        PE.register(GENSHIN_BOW_LANDING2.get(), GenShinBowLandingParticle2.Provider::new);
        PE.register(GENSHIN_BOW_LANDING3.get(), GenShinBowLandingParticle3.Provider::new);

        PE.register(BLOOM_TRAIL.get(), BloomTrailParticle.Provider::new);

        PE.register(GS_YOIMIYA_SA.get(), GsYoimiyaFirework.Provider::new);

        PE.register(BLACK_KNIGHT.get(), new MyTextureSheetParticle.BlackKnightProvider());
        PE.register(BLOOD_THIRSTY.get(), new MyTextureSheetParticle.BloodThirstyProvider());
        PE.register(SPARKS_SPLASH.get(), SparksSplashParticle.Provider::new);
        PE.register(SPARKS_SPLASH_HIT.get(), SparksSplashHitParticle.Provider::new);
        PE.register(ENTITY_AFTER_IMG_EX.get(), EntityAfterImgParticleEx.Provider::new);

        PE.register(ALO_WING.get(), KiritoWingParticle.Provider::new);
        PE.register(STAR_FLASH.get(), StarFlashParticle.Provider::new);
        PE.register(PHOTON_TRAIL.get(), PhotonTrailingParticle.Provider::new);

        PE.register(DMC_JC_BLADE_TRAIL.get(), JudgementCutParticle.Provider::new);
    }
}
