package net.oliver.forgemod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.entity.custom.SnailEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ForgeMod.MOD_ID);

    public static final RegistryObject<EntityType<SnailEntity>> SNAIL =
            ENTITY_TYPES.register("snail", () -> EntityType.Builder.of(SnailEntity::new, MobCategory.CREATURE)
                    .sized(0.35f, 0.35f).build("snail"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
