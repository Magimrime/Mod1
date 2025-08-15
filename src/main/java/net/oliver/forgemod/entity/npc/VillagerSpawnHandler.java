package net.oliver.forgemod.entity.npc;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;

@Mod.EventBusSubscriber(modid = "oliveforgemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VillagerSpawnHandler {
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Villager villager) {
            VillagerType type = ModVillagerType.byBiome(event.getLevel().getBiome(villager.blockPosition()));
            VillagerData currentData = villager.getVillagerData();
            VillagerData newData = new VillagerData(type, currentData.getProfession(), currentData.getLevel());
            villager.setVillagerData(newData);
            System.out.println("Villager spawned at " + villager.blockPosition() + ", Biome: " +
                    event.getLevel().getBiome(villager.blockPosition()).unwrapKey().map(ResourceKey::location).orElse(null) +
                    ", Type set to: " + type);
        }
    }
}