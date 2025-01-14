package com.dfdyz.epicacg.client.render.item;

import com.dfdyz.epicacg.utils.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FireFlySwordRenderer extends MyISTER{
    public static final FireFlySwordRenderer INSTANCE = new FireFlySwordRenderer();
    public static BakedModel layer0;
    public static BakedModel layer1;

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext transformType, @NotNull PoseStack matrix, @NotNull MultiBufferSource buffer, int light, int overlayLight) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        boolean cull = true;
        RenderType rendertype = ItemBlockRenderTypes.getRenderType(stack, cull);
        VertexConsumer vertexconsumer;
        if (cull) {
            vertexconsumer = ItemRenderer.getFoilBufferDirect(buffer, rendertype, true, stack.hasFoil());
        } else {
            vertexconsumer = ItemRenderer.getFoilBuffer(buffer, rendertype, true, stack.hasFoil());
        }

        //BakedModel org = itemRenderer.getModel(stack, null, null, 0);
        matrix.pushPose();
        //ForgeHooksClient.handleCameraTransforms(matrix, org, transformType, false);
        if(layer0 != null && layer1 != null){
            itemRenderer.renderModelLists(layer0, stack, light, overlayLight, matrix, vertexconsumer);

            if(transformType.firstPerson())
                itemRenderer.renderModelLists(layer1, stack, RenderUtils.EmissiveLightPos,  RenderUtils.EmissiveLightPos, matrix, vertexconsumer);
            else
                itemRenderer.renderModelLists(layer1, stack, RenderUtils.EmissiveLightPos, overlayLight, matrix, vertexconsumer);
        }

        //matrix.popPose();
        matrix.popPose(); //for Perspective
    }
}
