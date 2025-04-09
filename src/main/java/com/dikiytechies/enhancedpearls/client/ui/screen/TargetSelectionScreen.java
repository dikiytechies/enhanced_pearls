package com.dikiytechies.enhancedpearls.client.ui.screen;

import com.dikiytechies.enhancedpearls.client.ui.widget.TeleportButton;
import com.dikiytechies.enhancedpearls.network.ModPackets;
import com.dikiytechies.enhancedpearls.network.client.ClTeleportPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.util.SoundEvents.*;

@OnlyIn(Dist.CLIENT)
public class TargetSelectionScreen extends Screen {
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
        ClientPlayNetHandler clientPlayNetHandler = this.minecraft.player.connection;
        List<NetworkPlayerInfo> playerList = new ArrayList<>(this.minecraft.player.connection.getOnlinePlayers());
        playerList.remove(clientPlayNetHandler.getPlayerInfo(player.getUUID()));
        initGrid(playerList);
    }
    private void initGrid(List<NetworkPlayerInfo> players) {
        int amount = players.size();
        int gridScale = 28;
        int gridSpace = 6;
        float yMult = height / 540.0f;
        int yMiddle = height / 2;
        int yMin = (int) (yMiddle - 222 * yMult);
        int xMiddle = width / 2;
        int xMin = (int) (xMiddle - (gridScale + gridSpace) * 4.5f) + 3;
        for (int j = 0; j < MathHelper.clamp(Math.ceil(amount / 9.0), 0 ,15); j++) {
            int maxInColumn = j + 1 == MathHelper.clamp(Math.ceil(amount / 9.0), 1 ,15) && amount % 9 != 0? amount % 9: 9;
            for (int i = 0; i < maxInColumn; i++) {
                int finalI = i;
                int finalJ = j;
                this.addButton(new TeleportButton(xMin + i * (gridScale + gridSpace), yMin + j * (gridScale + gridSpace), gridScale, gridScale,
                        player,
                        players.get(finalJ * 9 + finalI).getSkinLocation(),
                        (button_teleport) -> teleport(finalI, finalJ, players),
                        (Button button, MatrixStack matrixStack, int mouseX, int mouseY) -> {
                    if (button.active) {
                        this.renderTooltip(matrixStack,
                                this.minecraft.font.split(ITextProperties.of(players.get(finalJ * 9 + finalI).getProfile().getName()), 200), mouseX, mouseY);
                    }}));
            }
        }
    }
    private void teleport(int i, int j, List<NetworkPlayerInfo> players) {
        /*ModPackets.sendToServer(new ClTeleportPacket(player.getId(), player.level.getServer().getPlayerList().getPlayer(players.get(j * 9 + i).getProfile().getId()).getId()));
        if (!player.isCreative()) itemStack.shrink(1);
        this.onClose();*/
        /*player.level.playSound(player, player.blockPosition(), ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        player.moveTo(player.level.getServer().getPlayerList().getPlayer(players.get(j * 9 + i).getProfile().getId()).position());
        player.level.playSound(null, player.blockPosition(), ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        if (!player.isCreative()) itemStack.shrink(1);*/
        ModPackets.sendToServer(new ClTeleportPacket(players.get(j * 9 + i).getProfile().getId()));
        this.onClose();
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
