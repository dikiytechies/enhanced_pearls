package com.dikiytechies.enhancedpearls.data.recipes;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;


public interface IBlockInteractionRecipe extends IRecipe<IInventory> {
    ResourceLocation TYPE_ID = new ResourceLocation(EnhancedPearls.MOD_ID, "block");

    @Override
    default @NotNull IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }
    
}
