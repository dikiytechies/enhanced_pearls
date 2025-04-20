package com.dikiytechies.enhancedpearls.modcompat.mod.jei;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.dikiytechies.enhancedpearls.data.recipes.BlockInteractionRecipe;
import com.dikiytechies.enhancedpearls.data.recipes.ModRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.stream.Collectors;

@JeiPlugin
public class EnhancedPearlsJei implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() { return new ResourceLocation(EnhancedPearls.MOD_ID, "jei_plugin"); }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new BlockInteractionRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        registration.addRecipes(rm.getAllRecipesFor(ModRecipeTypes.BLOCK_RECIPE).stream()
                .filter(r -> r instanceof BlockInteractionRecipe).collect(Collectors.toList()), BlockInteractionRecipeCategory.UID);
    }
}
