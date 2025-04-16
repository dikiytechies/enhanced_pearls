package com.dikiytechies.enhancedpearls.network.client;

import com.dikiytechies.enhancedpearls.init.EnchantmentsInit;
import com.dikiytechies.enhancedpearls.network.ModPackets;
import com.dikiytechies.enhancedpearls.network.server.TrOpenTargetSelection;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class ClOpenTargetSelection {
    private final ItemStack itemStack;

    public ClOpenTargetSelection(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    public static void encode(ClOpenTargetSelection msg, PacketBuffer buf) {
        buf.writeItem(msg.itemStack);
    }
    public static ClOpenTargetSelection decode(PacketBuffer buf) {
        return new ClOpenTargetSelection(buf.readItem());
    }
    public static void handle(ClOpenTargetSelection msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                List<ServerPlayerEntity> players = new ArrayList<>(player.level.getServer().getPlayerList().getPlayers());
                Iterator<ServerPlayerEntity> playersIterator = players.iterator();
                while (playersIterator.hasNext()) {
                    ServerPlayerEntity serverPlayer = playersIterator.next();
                    if (serverPlayer.isSpectator() || serverPlayer == player) playersIterator.remove();
                }
                if (!((ServerWorld) player.level).getPlayers(serverPlayerEntity -> !serverPlayerEntity.isSpectator() && serverPlayerEntity != player).isEmpty() ||
                        (!players.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsInit.MULTIDIMENSIONAL.get(), msg.itemStack) > 0))
                    ModPackets.sendToClient(new TrOpenTargetSelection(msg.itemStack), ctx.get().getSender());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
