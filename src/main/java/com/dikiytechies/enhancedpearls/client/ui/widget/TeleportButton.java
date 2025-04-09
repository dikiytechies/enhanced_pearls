package com.dikiytechies.enhancedpearls.client.ui.widget;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITargetedTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;

import static com.dikiytechies.enhancedpearls.client.ClientUtil.renderPlayerFace;

public class TeleportButton extends Button {
    private final PlayerEntity player;
    private final ResourceLocation targetTexture;
    public TeleportButton(int x, int y, int width, int height, PlayerEntity player, ResourceLocation targetTexture, IPressable event, Button.ITooltip tooltip) {
        super(x, y, width, height, ITextComponent.nullToEmpty(null), event, tooltip);
        this.player = player;
        this.targetTexture = targetTexture;
    }
    public TeleportButton(int x, int y, int width, int height, PlayerEntity player, ResourceLocation targetTexture, IPressable event) {
        super(x, y, width, height, ITextComponent.nullToEmpty(null), event);
        this.player = player;
        this.targetTexture = targetTexture;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float value) {
        super.render(matrixStack, mouseX, mouseY, value);
        if (this.visible) {
            renderPlayerFace(matrixStack, x + (int) (width / 4.5f), y + (int) (height / 4.5f), targetTexture);
            if (isMouseOver(mouseX, mouseY)) {
                Minecraft minecraft = Minecraft.getInstance();
                minecraft.getTextureManager().bind(BUTTON_LOCATION);
                this.blit(matrixStack, x, y, 0, 28, width, height, 56, 56);
            }
        }
        if (this.isHovered()) {
            this.renderToolTip(matrixStack, mouseX, mouseY);
        }
    }
    public static final ResourceLocation BUTTON_LOCATION = new ResourceLocation(EnhancedPearls.MOD_ID, "textures/gui/selection_grid_button.png");
    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float value) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bind(BUTTON_LOCATION);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, x, y, 0, 0, width, height, 56, 56);
    }
}
