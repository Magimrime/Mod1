package net.oliver.forgemod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ForgeMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WALNUT_TAB = CREATIVE_MODE_TABS.register("walnut_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WALNUT.get()))
                    .title(Component.translatable("creativetab.oliveforgemod.walnut"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.WALNUT.get());

                        output.accept(ModBlocks.WALNUT_LOG.get());
                        output.accept(ModBlocks.WALNUT_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_WALNUT_LOG.get());
                        output.accept(ModBlocks.STRIPPED_WALNUT_WOOD.get());

                        output.accept(ModBlocks.WALNUT_PLANKS.get());
                        output.accept(ModBlocks.WALNUT_SAPLING.get());

                        output.accept(ModBlocks.WALNUT_STAIRS.get());
                        output.accept(ModBlocks.WALNUT_SLAB.get());

                        output.accept(ModBlocks.WALNUT_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.WALNUT_BUTTON.get());

                        output.accept(ModBlocks.WALNUT_FENCE.get());
                        output.accept(ModBlocks.WALNUT_FENCE_GATE.get());

                        output.accept(ModBlocks.WALNUT_DOOR.get());
                        output.accept(ModBlocks.WALNUT_TRAPDOOR.get());

                        output.accept(ModItems.SNAIL_SPAWN_EGG.get());
                    }).build());

    public static void register (IEventBus eventBus){
                            CREATIVE_MODE_TABS.register(eventBus);
    }
}