package net.oliver.forgemod;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.oliver.forgemod.block.ModBlocks;
import net.oliver.forgemod.effect.ModEffects;
import net.oliver.forgemod.enchantment.ModEnchantmentEffect;
import net.oliver.forgemod.entity.ModEntities;
import net.oliver.forgemod.item.ModCreativeModeTabs;
import net.oliver.forgemod.item.ModItems;
import net.oliver.forgemod.loot.ModLootModifiers;
import net.oliver.forgemod.potion.ModPotions;
import net.oliver.forgemod.sound.ModSounds;
import net.oliver.forgemod.worldgen.biome.ModBiomes;
import net.oliver.forgemod.worldgen.biome.ModTerrablender;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForgeMod.MOD_ID)
public class ForgeMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "oliveforgemod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ForgeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        //Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModSounds.register(modEventBus);

        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);

        ModEnchantmentEffect.register(modEventBus);
        ModEntities.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        ModTerrablender.registerBiomes();

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        //Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModItems.WALNUT.get(),0.6f);
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        }
    }
