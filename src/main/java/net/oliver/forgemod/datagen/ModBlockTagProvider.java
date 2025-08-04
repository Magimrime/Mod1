package net.oliver.forgemod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.block.ModBlocks;
import net.oliver.forgemod.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ForgeMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE);

        tag(BlockTags.NEEDS_STONE_TOOL);

        tag(BlockTags.NEEDS_IRON_TOOL);

        tag(BlockTags.NEEDS_DIAMOND_TOOL);

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.WALNUT_LOG.get())
                .add(ModBlocks.WALNUT_WOOD.get())
                .add(ModBlocks.STRIPPED_WALNUT_LOG.get())
                .add(ModBlocks.STRIPPED_WALNUT_WOOD.get());
    }
}
