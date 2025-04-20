package com.dikiytechies.enhancedpearls.mixin;

import com.dikiytechies.enhancedpearls.init.EnchantmentsInit;
import com.google.common.collect.ImmutableList;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.Validate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements IInventory, INameable {
    @Shadow @Final public NonNullList<ItemStack> items = NonNullList.withSize(36, ItemStack.EMPTY);
    @Shadow @Final public NonNullList<ItemStack> armor = NonNullList.withSize(4, ItemStack.EMPTY);
    @Shadow @Final public NonNullList<ItemStack> offhand = NonNullList.withSize(1, ItemStack.EMPTY);
    @Shadow @Final private List<NonNullList<ItemStack>> compartments = ImmutableList.of(this.items, this.armor, this.offhand);
    @Shadow @Final public PlayerEntity player;

    @Redirect(method = "dropAll", at = @At(value = "INVOKE", target = "Ljava/util/List;set(ILjava/lang/Object;)Ljava/lang/Object;"))
    public <E> E set(List<E> instance, int i, E e) {
        Validate.notNull(e);
        if (instance.get(i) instanceof ItemStack && EnchantmentHelper.getItemEnchantmentLevel(EnchantmentsInit.POSTMORTEM.get(), ((ItemStack) instance.get(i))) > 0) {
            return instance.get(i);
        } else return instance.set(i, e);
    }
}
