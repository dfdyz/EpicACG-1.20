package com.dfdyz.epicacg.client.render.item;

import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public class ItemRenderProperties {

    public static IClientItemExtensions firefly_sword(){
        return new IRProperty(FireFlySwordRenderer.INSTANCE);
    }

    public static class IRProperty implements IClientItemExtensions {
        private final MyISTER ister;
        public IRProperty(MyISTER ister){
            this.ister = ister;
        }

        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return ister;
        }

        @Override
        public @Nullable Font getFont(ItemStack stack, FontContext context) {
            return IClientItemExtensions.super.getFont(stack, context);
        }
    }

}
