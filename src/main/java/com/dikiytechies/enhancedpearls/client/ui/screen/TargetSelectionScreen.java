package com.dikiytechies.enhancedpearls.client.ui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@OnlyIn(Dist.CLIENT)
public class TargetSelectionScreen extends Screen {
    private Button button;
    private final PlayerEntity player;
    private final ItemStack itemStack;
    public TargetSelectionScreen(PlayerEntity player, ItemStack itemStack) {
        super(StringTextComponent.EMPTY);
        this.player = player;
        this.itemStack = itemStack;
    }

    @Override
    protected void init() {
        /*int yMin = height / 10;
        int yMax = height - yMin;
        int xMin = width / 5;
        int xMax = width - xMin;
        int gridScale = 30; //(height + width) / 50;
        int gridSpace = 15; //(height + width) / 40;
        super.init();
        for (int i = xMin; i < xMax; i += gridScale + gridSpace) {
            for (int j = yMin; j < yMax; j += gridScale + gridSpace) {
                this.button = this.addButton(new Button(i, j, gridScale, gridScale, new TranslationTextComponent("a"), (asa) -> {
                }));
            }
        }*/
        List<NetworkPlayerInfo> playerList = new ArrayList<>(Minecraft.getInstance().getConnection().getOnlinePlayers());
        initGrid(playerList);
    }
    private void initGrid(List<NetworkPlayerInfo> players) {
        int amount = players.size();
        int gridScale = 24;
        int gridSpace = 6;
        float yMult = height / 540.0f;
        int yMiddle = height / 2;
        int yMin = (int) (yMiddle - 222 * yMult);
        int xMiddle = width / 2;
        int xMin = (int) (xMiddle - (gridScale + gridSpace) * 4.5f) + 3;
        for (int j = 0; j < MathHelper.clamp(Math.ceil(amount / 9.0), 1 ,15); j++) {
            int maxInColumn = j + 1 == MathHelper.clamp(Math.ceil(amount / 9.0), 1 ,15) && amount % 9 != 0? amount % 9: 9;
            for (int i = 0; i < maxInColumn; i++) {
                int finalI = i;
                int finalJ = j;
                this.button = this.addButton(new Button(xMin + i * (gridScale + gridSpace), yMin + j * (gridScale + gridSpace), gridScale, gridScale, new TranslationTextComponent(""), (button_teleport) -> teleport(finalI, finalJ, player, players)));
            }
        }
    }
    private void teleport(int i, int j, PlayerEntity player, List<NetworkPlayerInfo> players) {
        player.level.playSound(player, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        player.moveTo(player.level.getServer().getPlayerList().getPlayer(players.get(j * 9 + i).getProfile().getId()).position());
        player.level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        if (!player.isCreative()) itemStack.shrink(1);
        this.onClose();
    }
    //TODO packets, tooltip, button texture


    @Override
    public boolean isPauseScreen() { return false; }
}
