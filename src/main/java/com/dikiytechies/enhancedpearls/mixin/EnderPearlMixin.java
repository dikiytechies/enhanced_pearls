package com.dikiytechies.enhancedpearls.mixin;

import com.dikiytechies.enhancedpearls.init.ItemsInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderPearlItem.class)
public abstract class EnderPearlMixin extends Item {
    public EnderPearlMixin(Item.Properties properties) { super(properties); }
    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState block = world.getBlockState(blockPos);
        if (block.getBlock() == Blocks.LODESTONE) {
            if (context.getPlayer() != null) {
                PlayerEntity player = context.getPlayer();
                if (player.abilities.instabuild) {
                    boolean conatinsItem = false;
                    for (int i = 0; i < 9; i++) {
                        if (player.inventory.getItem(i).getItem() == ItemsInit.CALIBRATED_PEARL.get()) conatinsItem = true;
                    }
                    world.playSound(player, blockPos, SoundEvents.END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (!conatinsItem) {
                        player.inventory.setItem(player.inventory.getSuitableHotbarSlot(), new ItemStack(ItemsInit.CALIBRATED_PEARL.get()));
                    }
                } else {
                    world.playSound(player, blockPos, SoundEvents.END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    context.getItemInHand().shrink(1);
                    player.inventory.add(new ItemStack(ItemsInit.CALIBRATED_PEARL.get()));
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}
