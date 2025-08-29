package net.oliver.forgemod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.oliver.forgemod.entity.npc.layer.VillagerTypeLayer;

@OnlyIn(Dist.CLIENT)
public class ModVillagerRenderer extends MobRenderer<Villager, ModVillagerModel<Villager>> {
    private static final ResourceLocation VILLAGER_BASE_SKIN = ResourceLocation.withDefaultNamespace("textures/entity/villager/villager.png");

    public ModVillagerRenderer(EntityRendererProvider.Context p_174437_) {
        super(p_174437_, new ModVillagerModel<>(p_174437_.bakeLayer(ModVillagerModel.LAYER_LOCATION)), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, p_174437_.getModelSet(), p_174437_.getItemInHandRenderer()));
        this.addLayer(new VillagerProfessionLayer<>(this, p_174437_.getResourceManager(), "villager"));
        this.addLayer(new CrossedArmsItemLayer<>(this, p_174437_.getItemInHandRenderer()));
        this.addLayer(new VillagerTypeLayer<>(this, new ModVillagerModel<>(p_174437_.bakeLayer(ModVillagerModel.LAYER_LOCATION))));
    }

    public ResourceLocation getTextureLocation(Villager pEntity) {
        return VILLAGER_BASE_SKIN;
    }

    protected void scale(Villager pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        float f = 0.9375F * pLivingEntity.getAgeScale();
        pPoseStack.scale(f, f, f);
    }

    protected float getShadowRadius(Villager pEntity) {
        float f = super.getShadowRadius(pEntity);
        return pEntity.isBaby() ? f * 0.5F : f;
    }
}