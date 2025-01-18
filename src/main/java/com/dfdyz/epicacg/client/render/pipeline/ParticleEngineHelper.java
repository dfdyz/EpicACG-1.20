package com.dfdyz.epicacg.client.render.pipeline;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ParticleEngineHelper {

    public record PostParticles(PostParticleRenderType rt, Queue<Particle> particles) implements Comparable<PostParticles>{
        public static PostParticles of(PostParticleRenderType rt, Queue<Particle> particles){
            return new PostParticles(rt, particles);
        }

        @Override
        public int compareTo(PostParticles other) {
            return Integer.compare(other.rt.priority, this.rt.priority);  // 降序排列
        }
    }

    public static PriorityQueue<PostParticles> createQueue() {
        return new PriorityQueue<>();
    }
}
