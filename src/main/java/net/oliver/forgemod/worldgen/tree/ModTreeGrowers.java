package net.oliver.forgemod.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.oliver.forgemod.ForgeMod;
import net.oliver.forgemod.worldgen.ModConfiguredFeatures;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower WALNUT = new TreeGrower(ForgeMod.MOD_ID + ":walnut",
            Optional.empty(), Optional.of(ModConfiguredFeatures.WALNUT_KEY),Optional.empty());
}
