package com.dfdyz.epicacg.client.render.pipeline;

import com.dfdyz.epicacg.client.render.targets.TargetManager;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.PriorityQueue;
import java.util.Queue;

import static net.minecraft.client.Minecraft.ON_OSX;

public class PostEffectPipelines {
    //public static final HashMap<ResourceLocation, RenderTarget> TempTargets = Maps.newHashMap();
    //public static RenderTarget rtRecord;
    public static final Queue<Pipeline> PostEffectQueue = Queues.newConcurrentLinkedQueue();
    public static final PriorityQueue<Pipeline> PostEffectQueueInternal = Queues.newPriorityQueue();

    static ResourceLocation depth_target = new ResourceLocation("epicacg:depth_target");
    public static RenderTarget depth;

    public static void RenderPost(){
        if(!PostEffectQueue.isEmpty()){
            RenderSystem.enableBlend();

            depth = TargetManager.getTarget(depth_target);
            depth.copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
            depth.unbindWrite();
            //
            //RenderSystem.disableDepthTest();
            Pipeline renderType;

            PostEffectQueue.removeIf((ppl) -> {
                PostEffectQueueInternal.add(ppl);
                return true;
            });

            updateOrthoMatrix();
            while (!PostEffectQueueInternal.isEmpty()){
                renderType = PostEffectQueueInternal.poll();
                renderType.HandlePostEffect();
            }
            //RenderSystem.enableDepthTest();
            //RenderSystem.enableDepthTest();
            Minecraft.getInstance().getMainRenderTarget().copyDepthFrom(depth);
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
            //target = null;
        }

        close();
        TargetManager.ReleaseAll();
    }

    public static Matrix4f shaderOrthoMatrix;
    static void updateOrthoMatrix() {
        var mainTarget = Minecraft.getInstance().getMainRenderTarget();
        shaderOrthoMatrix = (new Matrix4f()).setOrtho(0.0F, (float)mainTarget.width, 0.0F, (float)mainTarget.height, 0.1F, 1000.0F);
    }

    //for fucking oculus
    private static boolean Active = false;

    public static boolean isActive(){ return Active; }
    public static void active(){
        //System.out.println("frame");
        Active = true;
    }

    public static void close(){
        Active = false;
    }

    public static RenderTarget getSource(){
        if(Minecraft.getInstance().levelRenderer.transparencyChain == null){
            return Minecraft.getInstance().getMainRenderTarget();
        }
        else {
            return Minecraft.getInstance().levelRenderer.getParticlesTarget();
        }
    }

    public static abstract class Pipeline implements Comparable<Pipeline>{
        protected boolean called = false;
        protected boolean started = false;
        protected RenderTarget bufferTarget;
        public final ResourceLocation name;

        public int priority = 0;

        @Override
        public int compareTo(Pipeline o) {
            if(priority > o.priority) return 1;
            else if(priority == o.priority) return 0;
            else return -1;
        }

        public Pipeline(ResourceLocation name){
            this.name = name;
        }

        public void start(){
            if(started){
                if(Active){
                    //ClientCommands.Debug();
                    bufferTarget.copyDepthFrom(getSource());
                    bufferTarget.bindWrite(false);
                }
            }
            else {
                if(bufferTarget == null){
                    bufferTarget = TargetManager.getTarget(name);
                    bufferTarget.clear(ON_OSX);
                }

                RenderTarget main = getSource();
                if(Active){
                    //System.out.println("push")
                    bufferTarget.copyDepthFrom(main);
                    PostEffectQueue.add(this);
                    bufferTarget.bindWrite(false);
                    started = true;
                }
                //System.out.println("push");
            }
        }

        public void call(){
            if(Active) {
                //ClientCommands.Debug();
                called = true;
            }
        }

        public void suspend(){
            if(Active){
                //System.out.println("aaaaa");
                bufferTarget.unbindWrite();
                bufferTarget.unbindRead();
                RenderTarget rt = getSource();
                rt.copyDepthFrom(bufferTarget);
                rt.bindWrite(false);
            }
            else {
                //bufferTarget.clear(Minecraft.ON_OSX);
                getSource().bindWrite(false);
            }
            //ClientCommands.Debug();
        }
        public abstract void PostEffectHandler();
        public void HandlePostEffect(){
            if(called) PostEffectHandler();
            bufferTarget = null;
            started = false;
            called = false;
        }
    }


}
