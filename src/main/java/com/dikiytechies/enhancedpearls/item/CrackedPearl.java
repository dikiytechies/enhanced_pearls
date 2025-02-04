package com.dikiytechies.enhancedpearls.item;

import com.dikiytechies.enhancedpearls.util.ClientUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class CrackedPearl extends Item {
    public CrackedPearl(Properties properties) { super(properties); }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ClientUtil.openTargetSelection(player, itemStack);
        return ActionResult.success(itemStack);
    }
}
