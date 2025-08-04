package net.oliver.forgemod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.block.ModBlocks;
import net.oliver.forgemod.item.ModItems;
import net.oliver.forgemod.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture,
                              CompletableFuture<TagLookup<Block>> lookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, lookupCompletableFuture, ForgeMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        tag(ItemTags.TRIMMABLE_ARMOR);

        tag(ItemTags.TRIM_MATERIALS);

        tag(ItemTags.TRIM_TEMPLATES);

        tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.WALNUT_LOG.get().asItem())
                .add(ModBlocks.WALNUT_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_WALNUT_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_WALNUT_WOOD.get().asItem());

        tag(ItemTags.PLANKS)
                .add(ModBlocks.WALNUT_PLANKS.get().asItem());

    }
}
