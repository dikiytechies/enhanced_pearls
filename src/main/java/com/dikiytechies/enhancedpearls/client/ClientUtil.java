package com.dikiytechies.enhancedpearls.client;

import com.dikiytechies.enhancedpearls.client.ui.screen.TargetSelectionScreen;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ClientUtil {
    public static void openTargetSelection(PlayerEntity player, ItemStack itemStack) {
        Minecraft.getInstance().setScreen(new TargetSelectionScreen(player, itemStack));
    }
    //https://github.com/StandoByte/Ripples-of-the-Past/blob/1.16.5/src/main/java/com/github/standobyte/jojo/client/ClientUtil.java #369
    public static void renderPlayerFace(MatrixStack matrixStack, int x, int y, ResourceLocation playerFace) {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bind(playerFace);

        AbstractGui.blit(matrixStack, x, y, 16, 16, 16, 16, 128, 128);
        if (mc.options.getModelParts().contains(PlayerModelPart.HAT)) {
            matrixStack.pushPose();
            matrixStack.translate(x, y, 0);
            matrixStack.scale(9F/8F, 9F/8F, 0);
            matrixStack.translate(-1, -1, 0);
            AbstractGui.blit(matrixStack, 0, 0, 80, 16, 16, 16, 128, 128);
            matrixStack.popPose();
        }
    }
}
