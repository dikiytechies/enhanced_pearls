package com.dikiytechies.enhancedpearls.item;

import com.dikiytechies.enhancedpearls.client.ClientUtil;
import com.dikiytechies.enhancedpearls.init.EnchantmentsInit;
import com.dikiytechies.enhancedpearls.network.ModPackets;
import com.dikiytechies.enhancedpearls.network.client.ClOpenTargetSelection;
import com.dikiytechies.enhancedpearls.network.client.ClTeleportPacket;
import com.dikiytechies.enhancedpearls.network.server.TrOpenTargetSelection;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EnhancedPearl extends Item {
    protected int enchantability = 15;
    public EnhancedPearl(Properties properties) { super(properties); }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (world.isClientSide()) {
            ModPackets.sendToServer(new ClOpenTargetSelection(itemStack));
        }
        return ActionResult.success(itemStack);
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return this.getItemStackLimit(itemStack) == 1;
    }
    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) ||
                enchantment == Enchantments.UNBREAKING;
    }
}
