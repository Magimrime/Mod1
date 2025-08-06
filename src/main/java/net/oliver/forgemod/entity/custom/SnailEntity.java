package net.oliver.forgemod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import net.oliver.forgemod.entity.ModEntities;
import net.oliver.forgemod.item.ModItems;
import net.oliver.forgemod.worldgen.biome.ModBiomes;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SnailEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState curlAnimationState = new AnimationState();
    public final AnimationState peekAnimationState = new AnimationState();
    public final AnimationState uncurlAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int panicTicks = 0;
    private boolean shouldPeek = false;
    private static final EntityDataAccessor<Boolean> IS_PANICKED = SynchedEntityData.defineId(SnailEntity.class, EntityDataSerializers.BOOLEAN);
    private final List<Goal> storedGoals = new ArrayList<>(); // Store goals when curled

    public SnailEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_PANICKED, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(ModItems.WALNUT.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.08)
                .add(Attributes.FOLLOW_RANGE, 10);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ModItems.WALNUT.get());
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        this.panicTicks = 120;
        float adjustedAmount = this.isPanicked() || this.panicTicks > 0 ? pAmount * 0.2f : pAmount;
        return super.hurt(pSource, adjustedAmount);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.SNAIL.get().create(pLevel);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        // Don't run idle animation when panicked
        if (this.isPanicked()) {
            this.idleAnimationState.stop();
        }
    }

    public boolean isPanicked() {
        return this.entityData.get(IS_PANICKED);
    }

    private void clearGoals() {
        // Store current goals
        storedGoals.clear();
        this.goalSelector.getAvailableGoals().forEach(prioritizedGoal -> {
            storedGoals.add(prioritizedGoal.getGoal());
        });
        // Remove all goals except FloatGoal (priority 0)
        this.goalSelector.removeAllGoals(goal -> !(goal instanceof FloatGoal));
    }

    private void restoreGoals() {
        // Clear current goals (in case any were added)
        this.goalSelector.removeAllGoals(goal -> true);
        // Re-add FloatGoal first
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // Restore stored goals with their original priorities
        int priority = 1;
        for (Goal goal : storedGoals) {
            if (!(goal instanceof FloatGoal)) { // Skip FloatGoal as it's already added
                this.goalSelector.addGoal(priority++, goal);
            }
        }
        // If no goals were stored, re-register all goals
        if (storedGoals.isEmpty()) {
            this.goalSelector.addGoal(1, new PanicGoal(this, 0.0));
            this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
            this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(ModItems.WALNUT.get()), false));
            this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
            this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        }
    }


    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        // Manage panic ticks
        if (this.panicTicks > 0) {
            this.panicTicks--;
            if (!this.isPanicked()) {
                this.setPanicked(true);
            }
        } else if (this.isPanicked()) {
            this.setPanicked(false);
        }
    }

    private void setPanicked(boolean panicked) {
        this.entityData.set(IS_PANICKED, panicked);
        if (panicked) {
            // Clear goals and start curl animation
            this.clearGoals();
            this.idleAnimationState.stop();
            this.peekAnimationState.stop();
            this.uncurlAnimationState.stop();
            this.curlAnimationState.start(this.tickCount);
        } else {
            // Restore goals and start uncurl animation
            this.curlAnimationState.stop();
            this.uncurlAnimationState.start(this.tickCount);
            this.restoreGoals();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("PanicTicks", this.panicTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.panicTicks = tag.getInt("PanicTicks");
        if (this.panicTicks > 0 && !this.isPanicked()) {
            this.setPanicked(true);
        }
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnType) {
        ResourceKey<Biome> biomeKey = pLevel.getBiome(this.blockPosition()).unwrapKey().orElse(null);
        if (biomeKey == null || !biomeKey.equals(ModBiomes.WALNUT_BIOME)) {
            return false; // Only spawn in WALNUT_BIOME
        }

        // Require grass block below, like cows and sheep
        BlockState blockBelow = pLevel.getBlockState(this.blockPosition().below());
        if (!blockBelow.is(Blocks.GRASS_BLOCK)) {
            return false;
        }

        // Use default animal spawn rules (light level > 7, no collision)
        return super.checkSpawnRules(pLevel, pSpawnType);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        ResourceKey<Biome> biomeKey = pLevel.getBiome(this.blockPosition()).unwrapKey().orElse(null);
        if (biomeKey != null && biomeKey.equals(ModBiomes.WALNUT_BIOME)) {
        }

        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }


    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ARMADILLO_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.BAT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ARMADILLO_DEATH;
    }
}