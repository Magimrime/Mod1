package net.oliver.forgemod.entity.npc;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.init.EntityInit;

@EventBusSubscriber(modid = ForgeMod.MOD_ID, bus = Bus.MOD)
public class WalnutVillager extends Villager {
    public WalnutVillager(EntityType<? extends WalnutVillager> entityType, Level level) {
        super(entityType, level);
    }

    public WalnutVillager(Level level) {
        super(EntityInit.WALNUT_VILLAGER.get(), level);
    }

    @Override
    protected Component getTypeName() {
        var professionId = ForgeRegistries.VILLAGER_PROFESSIONS.getKey(getVillagerData().getProfession());
        var professionNamespace = !"minecraft".equals(professionId.getNamespace()) ? professionId.getNamespace() + '.' : "";
        var professionName = professionNamespace + professionId.getPath();
        return Component.translatable(EntityType.VILLAGER.getDescriptionId() + '.' + professionName);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.VILLAGER.getDefaultLootTable();
    }

    public static EntityType<WalnutVillager> createEntityType() {
        return null;
    }

    public void finalizeSpawn(ServerLevel level, DifficultyInstance currentDifficultyAt, MobSpawnType mobSpawnType, Object o, Object o1) {
    }
}