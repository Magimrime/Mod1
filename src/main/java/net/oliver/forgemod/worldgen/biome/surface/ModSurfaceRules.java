package net.oliver.forgemod.worldgen.biome.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.oliver.forgemod.worldgen.biome.ModBiomes;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);

    public static SurfaceRules.RuleSource makeRules() {
        // Condition for surface level (Y >= -1, above water)
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        // Grass block on surface, dirt below (matches Plains)
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK),
                DIRT
        );

        return SurfaceRules.sequence(
                // Apply grass and dirt surface only in WALNUT_BIOME
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.WALNUT_BIOME),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface))
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}