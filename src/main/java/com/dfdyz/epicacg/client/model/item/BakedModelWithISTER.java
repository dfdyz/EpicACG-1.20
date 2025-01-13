package com.dfdyz.epicacg.client.model.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.data.ModelData;
import org.antlr.v4.runtime.misc.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BakedModelWithISTER implements BakedModel {
    private final BakedModel parent;

    public BakedModelWithISTER(BakedModel parent){
        this.parent = parent;
    }

    @Override
    public List<BakedQuad> getQuads(@org.jetbrains.annotations.Nullable BlockState state, @org.jetbrains.annotations.Nullable Direction side, @org.jetbrains.annotations.NotNull RandomSource rand, @org.jetbrains.annotations.NotNull ModelData data, @org.jetbrains.annotations.Nullable RenderType renderType) {
        return parent.getQuads(state, side, rand, data, renderType);
    }

    @Override
    public List<BakedQuad> getQuads(@org.jetbrains.annotations.Nullable BlockState blockState, @org.jetbrains.annotations.Nullable Direction direction, RandomSource randomSource) {
        return parent.getQuads(blockState, direction, randomSource);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return parent.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return parent.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return parent.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return parent.getParticleIcon();
    }

    @Override
    public @org.jetbrains.annotations.NotNull ItemTransforms getTransforms() {
        return parent.getTransforms();
    }

    @Override
    public @org.jetbrains.annotations.NotNull TextureAtlasSprite getParticleIcon(@NotNull ModelData data) {
        return parent.getParticleIcon(data);
    }

    @Override
    public @org.jetbrains.annotations.NotNull ItemOverrides getOverrides() {
        return parent.getOverrides();
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(@org.jetbrains.annotations.NotNull BlockState state, @org.jetbrains.annotations.NotNull RandomSource rand, @org.jetbrains.annotations.NotNull ModelData data) {
        return parent.getRenderTypes(state, rand, data);
    }

    @Override
    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        return parent.getRenderPasses(itemStack, fabulous);
    }

    @Override
    public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
        return parent.getRenderTypes(itemStack, fabulous);
    }
}
