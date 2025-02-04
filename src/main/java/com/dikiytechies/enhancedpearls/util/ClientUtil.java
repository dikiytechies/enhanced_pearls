package com.dikiytechies.enhancedpearls.util;

import com.dikiytechies.enhancedpearls.client.ui.screen.TargetSelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ClientUtil {
    public static void openTargetSelection(PlayerEntity player, ItemStack itemStack) {
        Minecraft.getInstance().setScreen(new TargetSelectionScreen(player, itemStack));
    }
}
