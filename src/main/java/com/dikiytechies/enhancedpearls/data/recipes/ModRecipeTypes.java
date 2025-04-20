package com.dikiytechies.enhancedpearls.data.recipes;

import com.dikiytechies.enhancedpearls.EnhancedPearls;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeTypes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EnhancedPearls.MOD_ID);

    public static final RegistryObject<BlockInteractionRecipe.Serializer> BLOCK_SERIALIZER =
            RECIPE_SERIALIZER.register("block", BlockInteractionRecipe.Serializer::new);
    public static IRecipeType<BlockInteractionRecipe> BLOCK_RECIPE =
            new BlockInteractionRecipe.BlockInteractionRecipeType();

    public static void register(IEventBus modEventBus) {
        RECIPE_SERIALIZER.register(modEventBus);
        Registry.register(Registry.RECIPE_TYPE, BlockInteractionRecipe.TYPE_ID, BLOCK_RECIPE);
    }
}
