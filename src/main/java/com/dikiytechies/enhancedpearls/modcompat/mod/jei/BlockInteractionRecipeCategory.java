package com.dikiytechies.enhancedpearls.modcompat.mod.jei;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import com.dikiytechies.enhancedpearls.data.recipes.BlockInteractionRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class BlockInteractionRecipeCategory implements IRecipeCategory<BlockInteractionRecipe>  {
    public static final ResourceLocation UID = new ResourceLocation(EnhancedPearls.MOD_ID, "block");
    public static final ResourceLocation BACKGROUND_BLOCK = new ResourceLocation(EnhancedPearls.MOD_ID, "textures/gui/jei/block_interaction.png");
    private final IDrawable icon;
    private final IDrawable background;
    public BlockInteractionRecipeCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(new ItemStack(Blocks.LODESTONE));
        this.background = helper.drawableBuilder(BACKGROUND_BLOCK, 0, 0, 125, 22).setTextureSize(128, 128).build();
    }
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends BlockInteractionRecipe> getRecipeClass() {
        return BlockInteractionRecipe.class;
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("enhanced_pearls.gui.jei.block_interaction").getString();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(BlockInteractionRecipe recipe, IIngredients iIngredients) {
        iIngredients.setInputIngredients(recipe.getIngredients());
        iIngredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, BlockInteractionRecipe lodeStoneInteractionRecipe, IIngredients iIngredients) {
        iRecipeLayout.getItemStacks().init(0, true, 0, 2);
        iRecipeLayout.getItemStacks().init(1, true, 49, 2);
        iRecipeLayout.getItemStacks().init(2, false, 107, 2);
        iRecipeLayout.getItemStacks().set(iIngredients);
    }
}
