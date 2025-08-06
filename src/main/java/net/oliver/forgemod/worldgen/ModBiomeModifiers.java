package net.oliver.forgemod.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.entity.ModEntities;
import net.oliver.forgemod.worldgen.biome.ModBiomes;

import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_WALNUT_TREE = registerKey("add_tree_walnut");

    public static final ResourceKey<BiomeModifier> SPAWN_DIRT_SNAIL = registerKey("spawn_dirt_snail");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_WALNUT_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(ModBiomes.WALNUT_BIOME)),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.WALNUT_PLACED_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        // Single snail spawner for all overworld biomes
        context.register(SPAWN_DIRT_SNAIL, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.SNAIL.get(), 50, 4, 7))
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(ForgeMod.MOD_ID, name));
    }
}
