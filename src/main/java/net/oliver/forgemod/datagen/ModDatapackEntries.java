package net.oliver.forgemod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.JukeboxSong;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.enchantment.ModEnchantments;
import net.oliver.forgemod.sound.ModSounds;
import net.oliver.forgemod.trim.ModTrimMaterials;
import net.oliver.forgemod.trim.ModTrimPatterns;
import net.oliver.forgemod.worldgen.ModBiomeModifiers;
import net.oliver.forgemod.worldgen.ModConfiguredFeatures;
import net.oliver.forgemod.worldgen.ModPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDatapackEntries extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder();

    public ModDatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ForgeMod.MOD_ID));
    }
}

