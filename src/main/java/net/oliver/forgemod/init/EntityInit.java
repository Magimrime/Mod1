package net.oliver.forgemod.init;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.entity.npc.WalnutVillager;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ForgeMod.MOD_ID);

    public static final RegistryObject<EntityType<WalnutVillager>> WALNUT_VILLAGER = REGISTRY.register("walnut_villager", WalnutVillager::createEntityType);
}
