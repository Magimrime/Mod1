package net.oliver.forgemod.worldgen.biome;

import net.minecraft.resources.ResourceLocation;
import net.oliver.forgemod.ForgeMod;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(ResourceLocation.fromNamespaceAndPath(ForgeMod.MOD_ID, "overworld"), 5));
    }
}
