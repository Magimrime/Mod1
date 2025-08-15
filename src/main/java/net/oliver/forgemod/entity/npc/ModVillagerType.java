package net.oliver.forgemod.entity.npc;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import net.oliver.forgemod.worldgen.biome.ModBiomes;

import java.util.Map;

@Mod.EventBusSubscriber(modid = "oliveforgemod", bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModVillagerType {
    public static final ResourceKey<VillagerType> WALNUT_BIOME_KEY = ResourceKey.create(BuiltInRegistries.VILLAGER_TYPE.key(), ResourceLocation.fromNamespaceAndPath("oliveforgemod", "walnut_biome"));
    public static final ResourceKey<VillagerType> PLAINS_KEY = ResourceKey.create(BuiltInRegistries.VILLAGER_TYPE.key(), ResourceLocation.fromNamespaceAndPath("oliveforgemod", "plains"));
    public static VillagerType WALNUT_BIOME;
    public static VillagerType PLAINS;
    private final String name;
    private static final Map<ResourceKey<Biome>, VillagerType> BY_BIOME = Util.make(Maps.newHashMap(), p_35834_ -> {
        // Populated in RegisterEvent
    });

    public ModVillagerType(String pName) {
        this.name = pName;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @SubscribeEvent
    public static void registerVillagerTypes(RegisterEvent event) {
        event.register(BuiltInRegistries.VILLAGER_TYPE.key(), helper -> {
            WALNUT_BIOME = new VillagerType("walnut_biome");
            PLAINS = new VillagerType("plains");
            helper.register(WALNUT_BIOME_KEY, WALNUT_BIOME);
            helper.register(PLAINS_KEY, PLAINS);
            BY_BIOME.put(ModBiomes.WALNUT_BIOME, WALNUT_BIOME);
            System.out.println("Registered villager types: oliveforgemod:walnut_biome, oliveforgemod:plains");
        });
    }

    public static VillagerType byBiome(Holder<Biome> pBiome) {
        ResourceKey<Biome> biomeKey = pBiome.unwrapKey().orElse(null);
        System.out.println("Biome checked: " + (biomeKey != null ? biomeKey.location() : "null"));
        VillagerType type = pBiome.unwrapKey().map(BY_BIOME::get).orElse(PLAINS);
        System.out.println("Assigned villager type: " + type);
        return type;
    }
}