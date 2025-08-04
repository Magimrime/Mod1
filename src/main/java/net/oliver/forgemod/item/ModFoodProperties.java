package net.oliver.forgemod.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties WALNUT = new FoodProperties.Builder().nutrition(2).saturationModifier(0.125f).fast().build();
}
