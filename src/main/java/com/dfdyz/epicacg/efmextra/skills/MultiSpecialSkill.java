package com.dfdyz.epicacg.efmextra.skills;

import com.dfdyz.epicacg.client.render.EpicACGRenderType;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;

import static com.dfdyz.epicacg.registry.MySkillDataKeys.CHILD_SKILL_INDEX;

public class MultiSpecialSkill extends PassiveSkill {
    public MultiSpecialSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public void RollSelect(ServerPlayerPatch playerPatch, int dir){
        SkillContainer container = playerPatch.getSkill(EpicACGSkillSlot.SKILL_SELECTOR);
        SkillDataManager datas = container.getDataManager();
        if(!datas.hasData(CHILD_SKILL_INDEX.get())){
            datas.registerData(CHILD_SKILL_INDEX.get());
        }
        int selected = 0;
        IMutiSpecialSkill sai = getSAInstance(container);
        if(sai != null){
            int size = sai.getSkillTextures(playerPatch).size();
            selected = Math.max(0,Math.min(datas.getDataValue(CHILD_SKILL_INDEX.get())+dir, size-1));
        }
        datas.setDataSync(CHILD_SKILL_INDEX.get(), selected, playerPatch.getOriginal());
    }

    public void onInitiate(SkillContainer container) {
    }

    public void onRemoved(SkillContainer container) {
        container.getDataManager().setData(CHILD_SKILL_INDEX.get(), 0);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        SkillContainer sa = container.getExecuter().getSkill(SkillSlots.WEAPON_INNATE);
        //System.out.println(sa != null && sa.getSkill() instanceof IMutiSpecialSkill);
        return sa != null && sa.getSkill() instanceof IMutiSpecialSkill;
    }

    protected IMutiSpecialSkill getSAInstance(SkillContainer container){
        Skill sa = container.getExecuter().getSkill(SkillSlots.WEAPON_INNATE).getSkill();
        if(sa != null && sa instanceof IMutiSpecialSkill) return (IMutiSpecialSkill) sa;
        else return null;
    }

    public static int getSelected(PlayerPatch executer){
        return executer.getSkill(EpicACGSkillSlot.SKILL_SELECTOR).getDataManager().getDataValue(CHILD_SKILL_INDEX.get());
    }

    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float _x, float _y) {
        Skill sa = container.getExecuter().getSkill(SkillSlots.WEAPON_INNATE).getSkill();
        //EpicAddon.LOGGER.info("?????");
        if(sa == null) return;
        //EpicAddon.LOGGER.info("11111");
        if(!(sa instanceof IMutiSpecialSkill)) return;
        //EpicAddon.LOGGER.info("?????");
        //Vec3f pos = new Vec3f(42F, 48F, 0.117F);
        //float scale = 0.078F;
        float x = 42F;
        float y = 48F;

        PoseStack matStackIn = guiGraphics.pose();

        Window sr = Minecraft.getInstance().getWindow();
        int width = sr.getGuiScaledWidth();
        int height = sr.getGuiScaledHeight();

        IMutiSpecialSkill saInstance = (IMutiSpecialSkill) sa;

        ArrayList<ResourceLocation> textures = saInstance.getSkillTextures(container.getExecuter());

        int active = Math.min(textures.size()-1,container.getDataManager().getDataValue(CHILD_SKILL_INDEX.get()));
        for(int i=0; i<textures.size(); ++i){
            ResourceLocation t = textures.get(i);
            //EpicAddon.LOGGER.info("?????");
            matStackIn.pushPose();

            boolean actived = active == i;
            float scale = actived ? 0.085F : 0.078F;
            double r = actived ? 35 : 30;

            matStackIn.scale(scale, scale, 1.0F);
            matStackIn.translate(0.0, (double)((float)gui.getSlidingProgression() * 1.0F / scale), 0.0);

            float scaleMultiply = 1.0F / scale;

            double a = (160.0 - 40*i)/180.0*Math.PI;
            double ox = r*Math.cos(a);
            double oy = -r*Math.sin(a);

            int canExeCol = FastColor.ARGB32.color(255,50,249,255);
            int cannotExeCol = FastColor.ARGB32.color(160,255,255,255);

            //System.out.println(FastColor.ARGB32.color(200,255,255,255));

            RenderSystem.setShaderTexture(0, actived ? EpicACGRenderType.ChildSkillSelected : EpicACGRenderType.ChildSkillnoSelected);
            drawTexturedModalRectFixCoord(gui, matStackIn.last().pose(), (float)(width - x + ox) * scaleMultiply, (float)(height - y + oy) * scaleMultiply, 0, 0, 255, 255
                    , FastColor.ARGB32.color(200,200,200,200));

            boolean canExe = saInstance.isSkillActive(container.getExecuter(), i);

            RenderSystem.setShaderTexture(0, t);
            drawTexturedModalRectFixCoord(gui,matStackIn.last().pose(), (float)(width - x + ox) * scaleMultiply, (float)(height - y + oy) * scaleMultiply, 0, 0, 255, 255
                    ,canExe ? canExeCol : cannotExeCol);
            //matStackIn.scale(scaleMultiply, scaleMultiply, 1.0F);
            matStackIn.popPose();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void drawOnGui(BattleModeGui gui, SkillContainer container, PoseStack matStackIn, float _x, float _y) {

        //gui.font.drawShadow(matStackIn, String.format("x%.1f", container.getDataManager().getDataValue(PENALTY)), (float)width - x, (float)height - y + 6.0F, 16777215);
    }

    public void drawTexturedModalRectFixCoord(BattleModeGui ingameGui, Matrix4f matrix, float xCoord, float yCoord, int minU, int minV, int maxU, int maxV, int col) {
        drawTexturedModalRectFixCoord(matrix, xCoord, yCoord, (float)maxU, (float)maxV, (float)ingameGui.getBlitOffset(), (float)minU, (float)minV, (float)maxU, (float)maxV, col);
    }

    public static void drawTexturedModalRectFixCoord(Matrix4f matrix, float minX, float minY, float maxX, float maxY, float z, float minU, float minV, float maxU, float maxV, int col) {
        float cor = 0.00390625F;
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(matrix, minX, minY + maxX, z).uv(minU * cor, (minV + maxV) * cor).color(col).endVertex();
        bufferbuilder.vertex(matrix, minX + maxY, minY + maxX, z).uv((minU + maxU) * cor, (minV + maxV) * cor).color(col).endVertex();
        bufferbuilder.vertex(matrix, minX + maxY, minY, z).uv((minU + maxU) * cor, minV * cor).color(col).endVertex();
        bufferbuilder.vertex(matrix, minX, minY, z).uv(minU * cor, minV * cor).color(col).endVertex();
        tessellator.end();
    }
}
