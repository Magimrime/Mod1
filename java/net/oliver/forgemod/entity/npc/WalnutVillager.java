package net.oliver.forgemod.entity.npc;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    public ItemStack getPickResult() {
        return new ItemStack(Items.VILLAGER_SPAWN_EGG);
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch();
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return EntityType.VILLAGER.getDefaultLootTable();
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(EntityInit.WALNUT_VILLAGER.get(), Villager.createAttributes().build());
    }

    public static EntityType<WalnutVillager> createEntityType() {
        var builder = EntityType.Builder.<WalnutVillager>of(WalnutVillager::new, MobCategory.MISC).sized(0.6F, 1.95F).clientTrackingRange(10);
        return builder.build("walnut_villager");
    }
}
