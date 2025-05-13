package com.dikiytechies.enhancedpearls.network.server;

import com.dikiytechies.enhancedpearls.client.ClientUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;
public class TrOpenTargetSelection {
    private final ItemStack itemStack;

    public TrOpenTargetSelection(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    public static void encode(TrOpenTargetSelection msg, PacketBuffer buf) {
        buf.writeItem(msg.itemStack);
    }
    public static TrOpenTargetSelection decode(PacketBuffer buf) {
        return new TrOpenTargetSelection(buf.readItem());
    }
    public static void handle(TrOpenTargetSelection msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientUtil.openTargetSelection(msg.itemStack);
        });
        ctx.get().setPacketHandled(true);
    }
}
