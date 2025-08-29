package net.oliver.forgemod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fml.common.Mod;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.block.ModBlocks;
import net.oliver.forgemod.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        stairBuilder(ModBlocks.WALNUT_STAIRS.get(), Ingredient.of(ModBlocks.WALNUT_PLANKS.get())).group("walnut")
                .unlockedBy(getHasName(ModBlocks.WALNUT_PLANKS.get()), has(ModBlocks.WALNUT_PLANKS.get())).save(pRecipeOutput);
        slab(pRecipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WALNUT_SLAB.get(), ModBlocks.WALNUT_PLANKS.get());

        buttonBuilder(ModBlocks.WALNUT_BUTTON.get(), Ingredient.of(ModBlocks.WALNUT_PLANKS.get())).group("walnut")
                .unlockedBy(getHasName(ModBlocks.WALNUT_PLANKS.get()), has(ModBlocks.WALNUT_PLANKS.get())).save(pRecipeOutput);
        pressurePlate(pRecipeOutput, ModBlocks.WALNUT_PRESSURE_PLATE.get(), ModBlocks.WALNUT_PLANKS.get());

        fenceBuilder(ModBlocks.WALNUT_FENCE.get(), Ingredient.of(ModBlocks.WALNUT_PLANKS.get())).group("walnut")
                .unlockedBy(getHasName(ModBlocks.WALNUT_PLANKS.get()), has(ModBlocks.WALNUT_PLANKS.get())).save(pRecipeOutput);
        fenceGateBuilder(ModBlocks.WALNUT_FENCE_GATE.get(), Ingredient.of(ModBlocks.WALNUT_PLANKS.get())).group("walnut")
                .unlockedBy(getHasName(ModBlocks.WALNUT_PLANKS.get()), has(ModBlocks.WALNUT_PLANKS.get())).save(pRecipeOutput);

        doorBuilder(ModBlocks.WALNUT_DOOR.get(), Ingredient.of(ModBlocks.WALNUT_PLANKS.get())).group("walnut")
                .unlockedBy(getHasName(ModBlocks.WALNUT_PLANKS.get()), has(ModBlocks.WALNUT_PLANKS.get())).save(pRecipeOutput);
        trapdoorBuilder(ModBlocks.WALNUT_TRAPDOOR.get(), Ingredient.of(ModBlocks.WALNUT_PLANKS.get())).group("walnut")
                .unlockedBy(getHasName(ModBlocks.WALNUT_PLANKS.get()), has(ModBlocks.WALNUT_PLANKS.get())).save(pRecipeOutput);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, ForgeMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
